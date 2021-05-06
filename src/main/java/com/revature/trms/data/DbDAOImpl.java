package com.revature.trms.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.config.DriverConfigLoader;

@Service
public class DbDAOImpl implements DbDAO{
	private CqlSession session;
	
	@Autowired
	public void setSession(CqlSession mySession) {
		this.session = mySession;
	}
	
	private static final Logger log = LogManager.getLogger(DbDAOImpl.class);

	@Override
	public void setupDatabase() {
		StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ").append("TRMS with replication = {")
				.append("'class':'SimpleStrategy','replication_factor':1};");
		DriverConfigLoader loader = DriverConfigLoader.fromClasspath("application.conf");
		CqlSession session2;
		try {
			session2 = CqlSession.builder().withConfigLoader(loader).build();
		} catch(Exception e) {
			log.error("Method threw exception: "+e);
			for(StackTraceElement s : e.getStackTrace()) {
				log.warn(s);
			}
			throw e;
		}
		session2.execute(sb.toString());
		
	}

	@Override
	public void deleteDatabase() {
		StringBuilder sb = new StringBuilder("Drop KEYSPACE IF EXISTS TRMS;");
		session.execute(sb.toString());
		
	}

	@Override
	public void setupUserTable() {
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS User (")
				.append("department text , username text, password text, name text, supervisorUsername text, type text,")
				.append("totalReimbursement double, pendingReimbursement double, awardedReimbursement double, primary key (department, type, username));");
		session.execute(sb.toString());
		
	}
	
	@Override
	public void setupUserTableForLogin() {
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS LoginUser (")
				.append(" username text primary key, password text, name text, department text, supervisorUsername text, type text,")
				.append("totalReimbursement double, pendingReimbursement double, awardedReimbursement double);");
		session.execute(sb.toString());
		
	}

	@Override
	public void deleteUserTable() {
		StringBuilder sb = new StringBuilder("Drop TABLE IF EXISTS User;");
		session.execute(sb.toString());
		
	}
	
	@Override
	public void deleteUserTableForLogin() {
		StringBuilder sb = new StringBuilder("Drop TABLE IF EXISTS LoginUser;");
		session.execute(sb.toString());
		
	}
	
	@Override
	public void setupReimbursementFormTable() {
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ReimbursementForm (")
				.append(" id uuid, name text, username text, dateSubmitted text, dateOfEvent text, time text, location text, description text, cost double, ")
				.append("reimbursement double, format text, type text, justification text, department text, supervisor text, benCo text, supervisorApproval text, ")
				.append("deptHeadApproval text, benCoApproval text, reasonForDeclining text, urgent boolean, reimbursed boolean, attachments list<text>,")
				.append("projectedReimbursement double, benCoReason text, exceedsFundsAvailable boolean, daysTillEvent int,")
				.append("primary key (id));");
		session.execute(sb.toString());
	}
	
	@Override
	public void deleteReimbursementFormTable() {
		StringBuilder sb = new StringBuilder("Drop TABLE IF EXISTS ReimbursementForm;");
		session.execute(sb.toString());
	}
	
	@Override
	public void setupMessageTable() {
		StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS Message (")
				.append(" reciever text, sender text, message text, messageId uuid, read boolean, ")
				.append("primary key (reciever, messageId));");
		session.execute(sb.toString());
	}
	
	@Override
	public void deleteMessageTable() {
		StringBuilder sb = new StringBuilder("Drop TABLE IF EXISTS Message;");
		session.execute(sb.toString());
	}


}
