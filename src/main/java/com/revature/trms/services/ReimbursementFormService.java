package com.revature.trms.services;

import java.util.List;

import com.revature.trms.beans.ReimbursementForm;
import com.revature.trms.beans.Status;
import com.revature.trms.beans.User;
import com.revature.trms.data.MessageDAO;
import com.revature.trms.data.ReimbursementFormDAO;
import com.revature.trms.data.UserDAO;

public interface ReimbursementFormService {

	public void setReimbursementFormDAO(ReimbursementFormDAO myReimbursementDAO);
	public void setUserDAO(UserDAO myUserDAO);
	public void setMessageDAO(MessageDAO mymessageDAO);
	
	public boolean addReimbursementForm(ReimbursementForm reimbursementForm);
	public List<ReimbursementForm> getReimbursementFormsForSelf(User user);
	public List<ReimbursementForm> getReimbursementFormsForProcessing(User user);
	public List<ReimbursementForm> getReimbursementFormsForReview(User user);
	public boolean processReimbursementForm(ReimbursementForm reimbursementForm, User user, Status process,  String reasonForDeclining);
	public boolean updateReimbursementForm(ReimbursementForm reimbursementForm);
	public boolean removeReimbursementForm(ReimbursementForm reimbursementForm);
	public boolean reimburse(ReimbursementForm reimbursementForm, User user);
	public boolean changeReimbursementAmount(User user, ReimbursementForm reimbursementForm, double amount, String reason) throws Exception;
}
