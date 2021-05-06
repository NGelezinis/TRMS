package com.revature.trms.data;

import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.trms.beans.ReimbursementForm;

public interface ReimbursementFormDAO {
	public void setSession(CqlSession mySession);
	public void setSimpleStatementBuilder(SimpleStatementBuilder mySimpleStatementBuilder);
	public void addReimbursementForm(ReimbursementForm reimbursementForm) throws Exception;
	public ReimbursementForm getReimbursementForm(UUID id);
	public List<ReimbursementForm> getReimbursementForms();
	public void updateReimbursementForm(ReimbursementForm reimbursementForm) throws Exception;
	public void deleteReimbursementForm(ReimbursementForm reimbursementForm);
	
}
