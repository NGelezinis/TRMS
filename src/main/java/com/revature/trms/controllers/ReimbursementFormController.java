package com.revature.trms.controllers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.trms.aspects.Authorized;
import com.revature.trms.beans.EventType;
import com.revature.trms.beans.Format;
import com.revature.trms.beans.ReimbursementForm;
import com.revature.trms.beans.Status;
import com.revature.trms.beans.Type;
import com.revature.trms.beans.User;
import com.revature.trms.services.ReimbursementFormService;
import com.revature.trms.services.UserService;
import com.revature.trms.utils.S3Util;

import io.javalin.http.Context;

@Service
public class ReimbursementFormController {
	@Autowired
	private ReimbursementFormService reimbursementService;
	@Autowired
	private UserService userService;
	
	private static Logger log = LogManager.getLogger(ReimbursementFormController.class);
	
	@Authorized
	public void submitReimbursementForm(Context ctx) {
		User user = ctx.sessionAttribute("User");
		List<User> benCos = userService.getUsers().stream()
				.filter((u) -> u.getType().equals(Type.BENEFITS_COORDINATOR))
				.collect(Collectors.toList());

		Collections.shuffle(benCos);
		while(user.getUsername().equals(benCos.get(0).getUsername())) {
			Collections.shuffle(benCos);
		}
		ReimbursementForm reimbursementForm = new ReimbursementForm();
		reimbursementForm.setId(UUID.randomUUID());
		reimbursementForm.setName(user.getName());
		reimbursementForm.setUsername(user.getUsername());
		reimbursementForm.setDateSubmitted(ctx.formParam("dateSubmitted"));
		reimbursementForm.setDateOfEvent(ctx.formParam("dateOfEvent"));
		reimbursementForm.setTime(ctx.formParam("time"));
		reimbursementForm.setLocation(ctx.formParam("location"));
		reimbursementForm.setDescription(ctx.formParam("description"));
		reimbursementForm.setCost(Double.parseDouble(ctx.formParam("cost")));
		reimbursementForm.setFormat(Format.valueOf(ctx.formParam("format")));
		reimbursementForm.setType(EventType.valueOf(ctx.formParam("type")));
		reimbursementForm.setJustification(ctx.formParam("justification"));
		reimbursementForm.setDepartment(user.getDepartment());
		reimbursementForm.setSupervisor(user.getSupervisorUsername());
		reimbursementForm.setBenCo(benCos.get(0).getUsername());
		reimbursementForm.setSupervisorApproval(Status.PENDING);
		reimbursementForm.setDeptHeadApproval(Status.PENDING);
		reimbursementForm.setBenCoApproval(Status.PENDING);
		reimbursementForm.setReasonForDeclining("pending");
		//reimbursementForm.setUrgent(Boolean.getBoolean(ctx.formParam("urgent")));
		reimbursementForm.setReimbursed(false);
		reimbursementForm.setAttachments(new ArrayList<String>());
		reimbursementForm.setReimbursement((reimbursementForm.getCost()*reimbursementForm.getType().reimbursementPercentage));
		if(((user.getTotalReimbursement()-user.getAwardedReimbursement())-user.getPendingReimbursement()) < reimbursementForm.getReimbursement()) {
			reimbursementForm.setReimbursement((user.getTotalReimbursement()-user.getAwardedReimbursement())-user.getPendingReimbursement());
		}
		if(reimbursementForm.getReimbursement() < 0.0) {
			reimbursementForm.setReimbursement(0.0);
		}
		reimbursementForm.setProjectedReimbursement(reimbursementForm.getReimbursement());
		reimbursementForm.setExceedsFundsAvailable(false);
		reimbursementForm.setBenCoReason("pending");
		reimbursementForm.setDaysTillEvent(Integer.valueOf(ctx.formParam("daysTillEvent")));
		if(reimbursementForm.getDaysTillEvent() < 7) {
			ctx.status(400);
			return;
		} else if(reimbursementForm.getDaysTillEvent() < 14) {
			reimbursementForm.setUrgent(true);
		} else {
			reimbursementForm.setUrgent(false);
		}
			
		if(reimbursementService.addReimbursementForm(reimbursementForm)) {
			user.setPendingReimbursement(user.getPendingReimbursement()+reimbursementForm.getReimbursement());
			userService.updateUser(user);
			ctx.status(201);
			ctx.json(reimbursementForm);
		} else {
			ctx.status(409);
		}
		return;
		
	}
	
	@Authorized
	public void submitDocumentation(Context ctx) {
		User user = ctx.sessionAttribute("User");
		ReimbursementForm reimbursementForm = null;
		try {
			String name = new StringBuilder(ctx.queryParam("name"))
					.append(".")
					.append(ctx.queryParam("type")).toString();
			byte[] bytes = ctx.bodyAsBytes();
			reimbursementForm = ctx.sessionAttribute("ReimbursementForm");
			reimbursementForm.getAttachments().add(name);
			reimbursementService.updateReimbursementForm(reimbursementForm);
			S3Util.getInstance().uploadToBucket(name, bytes);
			ctx.status(200);
			ctx.json(reimbursementForm);
			return;
		} catch (Exception e) {
			ctx.status(500);
			return;
		}
		
	}
	//to finish
	@Authorized
	public void submitDocumentationToSkipSupervisor(Context ctx) {
		User user = ctx.sessionAttribute("User");
		ReimbursementForm reimbursementForm = null;
		try {
			String name = new StringBuilder(ctx.queryParam("name"))
					.append(".")
					.append(ctx.queryParam("type")).toString();
			byte[] bytes = ctx.bodyAsBytes();
			reimbursementForm = ctx.sessionAttribute("ReimbursementForm");
			if(reimbursementForm.getSupervisorApproval().equals(Status.PENDING)) {
				S3Util.getInstance().uploadToBucket(name, bytes);
				User user2 = userService.getUser(reimbursementForm.getSupervisor());
				reimbursementService.processReimbursementForm(reimbursementForm, user2, Status.ACCEPTED, "no comment");
				ctx.status(200);
				ctx.json(reimbursementForm);
				return;
			}
		} catch (Exception e) {
			ctx.status(500);
			return;
		}
		//ctx.status(400);
		
	}
	@Authorized
	public void retrieveDocumentation(Context ctx) {
		ReimbursementForm reimbursementForm = null;
		try {
			reimbursementForm = ctx.sessionAttribute("ReimbursementForm");
			int fileNum = Integer.parseInt(ctx.formParam("fileNum"));
			if(reimbursementForm.getAttachments().size() == 0) {
				ctx.status(400);
				return;
			} else if (fileNum < 0) {
				fileNum = 0;
			} else if (fileNum >= reimbursementForm.getAttachments().size()) {
				fileNum = reimbursementForm.getAttachments().size()-1;
			}
			String name = reimbursementForm.getAttachments().get(fileNum);
			InputStream s = S3Util.getInstance().getObject(name);
			ctx.result(s);
		} catch (Exception e) {
			ctx.status(400);
			return;
		}
		
	}
	@Authorized
	public void processReimbursementForm(Context ctx) {
		User user = ctx.sessionAttribute("User");
		ReimbursementForm reimbursementForm = null;
		String reasonForDeclining = null;
		Status processing;
		try {
			System.out.println(ctx.formParam("processing"));
			processing = Status.valueOf(ctx.formParam("processing"));
			reimbursementForm = ctx.sessionAttribute("ReimbursementForm");
			if(reimbursementForm.getSupervisor().equals(user.getUsername()) && Status.DECLINED.equals(processing)) {
					reasonForDeclining = ctx.formParam("reasonForDeclining");
			} else {
				reasonForDeclining = "no comment";
			}
			if(reimbursementService.processReimbursementForm(reimbursementForm, user, processing, reasonForDeclining)) {
				ctx.status(200);
				ctx.json(reimbursementForm);
			} else {
				ctx.status(403);
			}
		} catch (Exception e) {
			ctx.status(400);
			return;
		}
		
	}
	@Authorized
	public void retrievePendingReimbursementFormForProcessing(Context ctx) {
		User user = ctx.sessionAttribute("User");
		List<ReimbursementForm> reimbursementForms = reimbursementService.getReimbursementFormsForProcessing(user);
		int formNum = Integer.parseInt(ctx.formParam("formNum"));
		if(formNum >= reimbursementForms.size()) {
			formNum = reimbursementForms.size() -1;
		} else if( formNum <= 0) {
			formNum = 0;
		}
		ReimbursementForm reimbursementForm = reimbursementForms.get(formNum);
		ctx.sessionAttribute("ReimbursementForm", reimbursementForm);
		ctx.json(reimbursementForm);
		
	}
	@Authorized
	public void retrievePendingReimbursementFormForSubmittingDocumentation(Context ctx) {
		User user = ctx.sessionAttribute("User");
		List<ReimbursementForm> reimbursementForms = reimbursementService.getReimbursementFormsForSelf(user);
		int formNum = Integer.parseInt(ctx.formParam("formNum"));
		if(formNum >= reimbursementForms.size()) {
			formNum = reimbursementForms.size() -1;
		} else if( formNum <= 0) {
			formNum = 0;
		}
		ReimbursementForm reimbursementForm = reimbursementForms.get(formNum);
		ctx.sessionAttribute("ReimbursementForm", reimbursementForm);
		System.out.println(ctx.sessionAttribute("ReimbursementForm").toString());
		ctx.json(reimbursementForm);
		
	}
	@Authorized
	public void retrieveOwnReimbursementForms(Context ctx) {
		User user = ctx.sessionAttribute("User");
		List<ReimbursementForm> reimbursementForms = reimbursementService.getReimbursementFormsForSelf(user);
		if(reimbursementForms != null) {
			ctx.status(200);
			ctx.json(reimbursementForms);
		} else {
			ctx.status(500);
		}
		
	}
	@Authorized
	public void retrievePendingReimbursementForms(Context ctx) {
		User user = ctx.sessionAttribute("User");
		List<ReimbursementForm> reimbursementForms = reimbursementService.getReimbursementFormsForProcessing(user);
		if(reimbursementForms != null) {
			ctx.status(200);
			ctx.json(reimbursementForms);
		} else {
			ctx.status(500);
		}
		
	}
	@Authorized
	public void retrievePastReimbursementForms(Context ctx) {
		User user = ctx.sessionAttribute("User");
		List<ReimbursementForm> reimbursementForms = reimbursementService.getReimbursementFormsForReview(user);
		if(reimbursementForms != null) {
			ctx.status(200);
			ctx.json(reimbursementForms);
		} else {
			ctx.status(500);
		}
		
	}
	@Authorized
	public void changeReimbursementAmount(Context ctx) {
		User user = ctx.sessionAttribute("User");
		try {
			ReimbursementForm reimbursementForm = ctx.sessionAttribute("ReimbursementForm");
			Double amount = Double.parseDouble(ctx.formParam("amount"));
			String reason = ctx.formParam("reason");
			if(reimbursementService.changeReimbursementAmount(user, reimbursementForm, amount, reason)) {
				ctx.status(200);
				return;
			} else {
				ctx.status(403);
				return;
			}
		} catch (Exception e) {
			ctx.status(400);
		}
		
	}
	@Authorized
	public void reimburse(Context ctx) {
		User user = ctx.sessionAttribute("User");
		try {
			ReimbursementForm reimbursementForm = ctx.sessionAttribute("ReimbursementForm");
			if(reimbursementService.reimburse(reimbursementForm, user)) {
				ctx.status(200);
			} else {
				ctx.status(403);
			}
		} catch (Exception e) {
			ctx.status(400);
		}
		
	}
	@Authorized
	public void cancelReimbursementForm(Context ctx) {
		User user = ctx.sessionAttribute("User");
		try {
			ReimbursementForm reimbursementForm = ctx.sessionAttribute("ReimbursementForm");
			if(!reimbursementForm.isReimbursed()) {
				reimbursementService.removeReimbursementForm(reimbursementForm);
				user.setPendingReimbursement(user.getPendingReimbursement()-reimbursementForm.getProjectedReimbursement());
				userService.updateUser(user);
			}
		} catch (Exception e) {
			ctx.status(400);
		}
		
	}
	@Authorized
	public void unSelectReimbursementForm(Context ctx) {
		User user = ctx.sessionAttribute("User");
		ctx.req.getSession().invalidate();
		ctx.sessionAttribute("User", user);
		ctx.status(204);
	}
	public void checkCurrentReimbursementForm(Context ctx) {
		try {
			ReimbursementForm  rf = ctx.sessionAttribute("ReimbursementForm");
			ctx.json(rf);
			ctx.status(200);
		} catch (Exception e) {
			ctx.status(400);
		}
	}
	

}
