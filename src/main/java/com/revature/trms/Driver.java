package com.revature.trms;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.revature.trms.beans.Department;
import com.revature.trms.beans.EventType;
import com.revature.trms.beans.Type;
import com.revature.trms.beans.User;
import com.revature.trms.controllers.DbController;
import com.revature.trms.controllers.MessageController;
import com.revature.trms.controllers.ReimbursementFormController;
import com.revature.trms.controllers.UserController;
import com.revature.trms.data.DbDAO;
import com.revature.trms.data.DbDAOImpl;
import com.revature.trms.data.UserDAO;
import com.revature.trms.data.UserDAOImpl;
import com.revature.trms.services.UserService;
import com.revature.trms.services.UserServiceImpl;

import io.javalin.Javalin;

public class Driver {
	public static void main(String[] args) {

		Javalin app = Javalin.create().start(8080);	
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		DbController dbController = ac.getBean(DbController.class);
		UserController userController = ac.getBean(UserController.class);
		ReimbursementFormController reimbursementController = ac.getBean(ReimbursementFormController.class);
		MessageController messageController = ac.getBean(MessageController.class);
		app.post("/database", dbController::createDatabase);
		app.delete("/database", dbController::deleteDatabase);
		app.post("/database/Users", dbController::createUserTables);
		app.delete("/database/Users", dbController::deleteUserTables);
		app.post("/database/ReimbursementForms", dbController::createReimbursementTable);
		app.delete("/database/ReimbursementForms", dbController::deleteReimbursementTable);
		app.post("/database/Messages", dbController::createMessageTable);
		app.delete("/database/Messages", dbController::deleteMessageTable);
		app.post("/users", userController::register);
		app.put("/login", userController::login);
		app.delete("/login", userController::logout);
		app.post("/reimbursementForm", reimbursementController::submitReimbursementForm);
		app.delete("/reimbursementForm", reimbursementController::unSelectReimbursementForm);
		app.delete("/reimbursementForm/delete", reimbursementController::cancelReimbursementForm);
		app.get("/reimbursementForm/own", reimbursementController::retrieveOwnReimbursementForms);
		app.get("/reimbursementForm/pending", reimbursementController::retrievePendingReimbursementForms);
		app.get("/reimbursementForm/review", reimbursementController::retrievePastReimbursementForms);
		app.put("/reimbursementForm/documentation", reimbursementController::submitDocumentation);
		app.put("/reimbursementForm/skip", reimbursementController::submitDocumentationToSkipSupervisor);
		app.get("/reimbursementForm/documentation", reimbursementController::retrieveDocumentation);
		app.post("/reimbursementForm/own", reimbursementController::retrievePendingReimbursementFormForSubmittingDocumentation);
		app.post("/reimbursementForm/processing", reimbursementController::retrievePendingReimbursementFormForProcessing);
		app.put("/reimbursementForm/processing", reimbursementController::processReimbursementForm);
		app.put("/reimbursementForm/amount", reimbursementController::changeReimbursementAmount);
		app.put("/reimbursementForm/reimburse", reimbursementController::reimburse);
		app.post("/message", messageController::sendMessage);
		app.get("/message", messageController::getAllMessages);
		app.get("/check", reimbursementController::checkCurrentReimbursementForm);
		//app.get("/message/new", messageController::getNewMessages);
		/*
		User user = new User();
		user.setUsername("mpennywise");
		user.setPassword("779611");
		user.setName("Michael Pennywise");
		user.setType(Type.valueOf("EMPLOYEE"));
		user.setDepartment(Department.valueOf("RANDD"));
		user.setSupervisorUsername(null);
		user.setTotalReimbursement(1000.0);
		user.setPendingReimbursement(100.0);
		user.setAwardedReimbursement(100.0);
		udao.addUser(user);
		*/

	}

}
