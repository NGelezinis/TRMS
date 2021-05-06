package com.revature.trms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.trms.data.DbDAO;

@Service
public class DbServiceImpl implements DbService{
	@Autowired
	private DbDAO dbDAO;

	@Override
	public void createTRMSDatabase() {
		dbDAO.setupDatabase();
		
	}

	@Override
	public void deleteTRMSDatabase() {
		dbDAO.deleteDatabase();
		
	}

	@Override
	public void createUserTables() {
		dbDAO.setupUserTable();
		dbDAO.setupUserTableForLogin();
		
	}

	@Override
	public void deleteUserTables() {
		dbDAO.deleteUserTable();
		dbDAO.deleteUserTableForLogin();
		
	}

	@Override
	public void createReimbursementFormTable() {
		dbDAO.setupReimbursementFormTable();
		
	}

	@Override
	public void deletReimbursementFormTable() {
		dbDAO.deleteReimbursementFormTable();
		
	}
	
	@Override
	public void createMessageTable() {
		dbDAO.setupMessageTable();
		
	}

	@Override
	public void deleteMessageTable() {
		dbDAO.deleteMessageTable();
		
	}

}
