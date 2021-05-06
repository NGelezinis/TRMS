package com.revature.trms.data;

public interface DbDAO {
	public void setupDatabase();
	public void deleteDatabase();
	public void setupUserTable();
	public void deleteUserTable();
	public void deleteUserTableForLogin();
	public void setupUserTableForLogin();
	public void setupReimbursementFormTable();
	public void deleteReimbursementFormTable();
	void setupMessageTable();
	void deleteMessageTable();
}
