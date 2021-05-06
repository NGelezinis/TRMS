package com.revature.trms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.trms.services.DbService;
import io.javalin.http.Context;

@Service
public class DbController {
	@Autowired
	private DbService dbservice;
	
	public void createDatabase(Context ctx) {
		dbservice.createTRMSDatabase();
	}
	public void deleteDatabase(Context ctx) {
		dbservice.deleteTRMSDatabase();
	}
	public void createUserTables(Context ctx) {
		dbservice.createUserTables();
	}
	public void deleteUserTables(Context ctx) {
		dbservice.deleteUserTables();
	}
	public void createReimbursementTable(Context ctx) {
		dbservice.createReimbursementFormTable();
	}
	public void deleteReimbursementTable(Context ctx) {
		dbservice.deletReimbursementFormTable();
	}
	public void createMessageTable(Context ctx) {
		dbservice.createMessageTable();
	}
	public void deleteMessageTable(Context ctx) {
		dbservice.deleteMessageTable();
	}
}
