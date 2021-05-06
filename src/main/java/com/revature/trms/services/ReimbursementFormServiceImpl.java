package com.revature.trms.services;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.trms.beans.Format;
import com.revature.trms.beans.Message;
import com.revature.trms.beans.ReimbursementForm;
import com.revature.trms.beans.Status;
import com.revature.trms.beans.Type;
import com.revature.trms.beans.User;
import com.revature.trms.data.MessageDAO;
import com.revature.trms.data.ReimbursementFormDAO;
import com.revature.trms.data.UserDAO;

@Service
public class ReimbursementFormServiceImpl implements ReimbursementFormService{
	private ReimbursementFormDAO reimbursementDAO;
	private UserDAO userDAO;
	private MessageDAO messageDAO;

	@Autowired
	public void setReimbursementFormDAO(ReimbursementFormDAO myReimbursementDAO) {
		this.reimbursementDAO = myReimbursementDAO;
	}
	@Autowired
	public void setUserDAO(UserDAO myUserDAO) {
		this.userDAO = myUserDAO;
	}
	@Autowired
	public void setMessageDAO(MessageDAO mymessageDAO) {
		this.messageDAO = mymessageDAO;
	}
	
	//adds a reimbursement form
	@Override
	public boolean addReimbursementForm(ReimbursementForm reimbursementForm) {
		try {
			reimbursementDAO.addReimbursementForm(reimbursementForm);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//retrieves a list of forms based solely on the submitter of the form
	@Override
	public List<ReimbursementForm> getReimbursementFormsForSelf(User user) {
		List<ReimbursementForm> forms = reimbursementDAO.getReimbursementForms().stream()
				.filter((u) -> u.getUsername().equals(user.getUsername()))
				.collect(Collectors.toList());
		return forms;
	}

	//gets a list of reimbursement forms based on what forms you can have access to but supervisor's and benCo's can't see their own
	//and only not reimbursed forms
	@Override
	public List<ReimbursementForm> getReimbursementFormsForProcessing(User user) {
		Predicate<ReimbursementForm> r1 = (reimbursementForm) -> (!reimbursementForm.isReimbursed());
		if(Type.EMPLOYEE.equals(user.getType())) {
			List<ReimbursementForm> forms = reimbursementDAO.getReimbursementForms().stream()
					.filter(r1.and((u) -> u.getUsername().equals(user.getUsername())))
					.collect(Collectors.toList());
			return forms;
		} else if (Type.SUPERVISOR.equals(user.getType())) {
			List<ReimbursementForm> forms = reimbursementDAO.getReimbursementForms().stream()
					.filter(r1.and((u) -> u.getSupervisor().equals(user.getUsername())))
					.collect(Collectors.toList());
			return forms;
		} else if (Type.DEPARTMENT_HEAD.equals(user.getType())) {
			List<ReimbursementForm> forms = reimbursementDAO.getReimbursementForms().stream()
					.filter(r1.and((u) -> u.getDepartment().equals(user.getDepartment())))
					.collect(Collectors.toList());
			return forms;
		} else if (Type.BENEFITS_COORDINATOR.equals(user.getType())) {
			List<ReimbursementForm> forms = reimbursementDAO.getReimbursementForms().stream()
					.filter(r1.and((u) -> u.getBenCo().equals(user.getUsername())))
					.collect(Collectors.toList());
			return forms;
		} else {
			return null;
		}
	}

	//same as above but only reimbursed forms
	@Override
	public List<ReimbursementForm> getReimbursementFormsForReview(User user) {
		Predicate<ReimbursementForm> r1 = (reimbursementForm) -> (reimbursementForm.isReimbursed());
		if(Type.EMPLOYEE.equals(user.getType())) {
			List<ReimbursementForm> forms = reimbursementDAO.getReimbursementForms().stream()
					.filter(r1.and((u) -> u.getUsername().equals(user.getUsername())))
					.collect(Collectors.toList());
			return forms;
		} else if (Type.SUPERVISOR.equals(user.getType())) {
			List<ReimbursementForm> forms = reimbursementDAO.getReimbursementForms().stream()
					.filter(r1.and((u) -> u.getSupervisor().equals(user.getUsername())))
					.collect(Collectors.toList());
			return forms;
		} else if (Type.DEPARTMENT_HEAD.equals(user.getType())) {
			List<ReimbursementForm> forms = reimbursementDAO.getReimbursementForms().stream()
					.filter(r1.and((u) -> u.getDepartment().equals(user.getDepartment())))
					.collect(Collectors.toList());
			return forms;
		} else if (Type.BENEFITS_COORDINATOR.equals(user.getType())) {
			List<ReimbursementForm> forms = reimbursementDAO.getReimbursementForms().stream()
					.filter(r1.and((u) -> u.getBenCo().equals(user.getUsername())))
					.collect(Collectors.toList());
			return forms;
		} else {
			return null;
		}
	}

	//processed a forms
	@Override
	public boolean processReimbursementForm(ReimbursementForm reimbursementForm, User user, Status process, String reasonForDeclining) {
		try {
			if(Type.DEPARTMENT_HEAD.equals(user.getType())) {
				if(reimbursementForm.getSupervisor().equals(user.getUsername()) && Status.PENDING.equals(reimbursementForm.getDeptHeadApproval())) {
					reimbursementForm.setSupervisorApproval(process);
					reimbursementForm.setDeptHeadApproval(process);
					if(Status.DECLINED.equals(process)) {
						reimbursementForm.setReasonForDeclining(reasonForDeclining);
					}
					reimbursementDAO.updateReimbursementForm(reimbursementForm);
					return true;
				} else if(Status.ACCEPTED.equals(reimbursementForm.getSupervisorApproval())) {
					reimbursementForm.setDeptHeadApproval(process);
					reimbursementDAO.updateReimbursementForm(reimbursementForm);
					return true;
				} else {
					return false;
				}
				
			} else if (reimbursementForm.getSupervisor().equals(user.getUsername()) && Status.PENDING.equals(reimbursementForm.getSupervisorApproval())) {
				reimbursementForm.setSupervisorApproval(process);
				if(Status.DECLINED.equals(process)) {
					reimbursementForm.setReasonForDeclining(reasonForDeclining);
				}
				User requestingUser = userDAO.getUser(reimbursementForm.getUsername());
				if(Type.DEPARTMENT_HEAD.equals(requestingUser.getType()) && Status.ACCEPTED.equals(process)) {
					reimbursementForm.setDeptHeadApproval(process);
				}
				reimbursementDAO.updateReimbursementForm(reimbursementForm);
				return true;
			} else if (reimbursementForm.getBenCo().equals(user.getUsername()) && Status.PENDING.equals(reimbursementForm.getBenCoApproval()) && Status.ACCEPTED.equals(reimbursementForm.getDeptHeadApproval())) {
				reimbursementForm.setBenCoApproval(process);
				reimbursementDAO.updateReimbursementForm(reimbursementForm);
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	//updates a reimbursement form
	@Override
	public boolean updateReimbursementForm(ReimbursementForm reimbursementForm) {
		try {
			reimbursementDAO.updateReimbursementForm(reimbursementForm);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//removes a reimbursement form
	@Override
	public boolean removeReimbursementForm(ReimbursementForm reimbursementForm) {
		try {
			reimbursementDAO.deleteReimbursementForm(reimbursementForm);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//reimburses the form
	@Override
	public boolean reimburse(ReimbursementForm reimbursementForm, User user) {
		User reimbursedUser = userDAO.getUser(reimbursementForm.getUsername());
		if (Status.ACCEPTED.equals(reimbursementForm.getBenCoApproval())) {
			if(Format.PRESENTATION.equals(reimbursementForm.getFormat()) && Type.DEPARTMENT_HEAD.equals(user.getType())) {
				reimbursementForm.setReimbursed(true);
				reimbursedUser.setAwardedReimbursement(reimbursedUser.getAwardedReimbursement() + reimbursementForm.getReimbursement());
				reimbursedUser.setPendingReimbursement(reimbursedUser.getPendingReimbursement() + reimbursementForm.getProjectedReimbursement());
				try {
					reimbursementDAO.updateReimbursementForm(reimbursementForm);
					userDAO.updateUser(reimbursedUser);
					return true;
				} catch (Exception e) {
					//e.printStackTrace();
					return false;
				}
			} else if (Type.BENEFITS_COORDINATOR.equals(user.getType()) && !Format.PRESENTATION.equals(reimbursementForm.getFormat())) {
				reimbursementForm.setReimbursed(true);
				reimbursedUser.setAwardedReimbursement(reimbursedUser.getAwardedReimbursement() + reimbursementForm.getReimbursement());
				reimbursedUser.setPendingReimbursement(reimbursedUser.getPendingReimbursement() + reimbursementForm.getProjectedReimbursement());
				try {
					reimbursementDAO.updateReimbursementForm(reimbursementForm);
					userDAO.updateUser(reimbursedUser);
					return true;
				} catch (Exception e) {
					//e.printStackTrace();
					return false;
				}
			}
			
		}
		return false;
	}
	
	//changes the amount user will be reimbursed and sends the requestor a message
	@Override
	public boolean changeReimbursementAmount(User user, ReimbursementForm reimbursementForm, double amount, String reason) {
		if(Type.BENEFITS_COORDINATOR.equals(user.getType()) && Status.ACCEPTED.equals(reimbursementForm.getDeptHeadApproval()) && Status.PENDING.equals(reimbursementForm.getBenCoApproval()) && !reimbursementForm.isReimbursed()) {
			reimbursementForm.setReimbursement(amount);
			reimbursementForm.setBenCoReason(reason);
			try {
				reimbursementDAO.updateReimbursementForm(reimbursementForm);
				Message message = new Message();
				message.setMessage("Your reimbursement amount has been changed for form " + reimbursementForm.getId());
				message.setMessageId(UUID.randomUUID());
				message.setReciever(reimbursementForm.getUsername());
				message.setSender(user.getUsername());
				message.setRead(false);
				messageDAO.addMessage(message);
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		return false;
		
	}
}
