package com.revature.trms.services;

public interface DbService {
	public void createTRMSDatabase();
	public void deleteTRMSDatabase();
	public void createUserTables();
	public void deleteUserTables();
	public void createReimbursementFormTable();
	public void deletReimbursementFormTable();
	void createMessageTable();
	void deleteMessageTable();

}
