package com.revature.trms.data;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.trms.beans.Department;
import com.revature.trms.beans.Type;
import com.revature.trms.beans.User;

@Service
public class UserDAOImpl implements UserDAO {
	private CqlSession session;
	private SimpleStatementBuilder builder = new SimpleStatementBuilder("string");
	
	@Autowired
	public void setSession(CqlSession mySession) {
		this.session = mySession;
	}
	
	public void setSimpleStatementBuilder(SimpleStatementBuilder mySimpleStatementBuilder) {
		this.builder = mySimpleStatementBuilder;
	}

	//add a user
	@Override
	public void addUser(User user) {
		ObjectMapper o = new ObjectMapper();
		String json = null;
		try {
			json = o.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String query = "Insert into LoginUser json ?;";
		builder.setQuery(query);
		SimpleStatement s = builder
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(json);
		session.execute(bound);
		
		/*
		String query2 = "Insert into LoginUser json ?;";
		
		SimpleStatement s2 = new SimpleStatementBuilder(query2)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound2 = session.prepare(s2).bind(json);
		session.execute(bound2);
		*/
	}

	//retrieve a map of users, no reason it isn't a list
	@Override
	public Map<String, User> getUsers() {
		Map<String, User> users = new HashMap<String, User>();
		String query = "SELECT * FROM LoginUser";
		ResultSet rs = session.execute(query);
		for(Row row: rs) {
			User user = new User();
			user.setUsername(row.getString("username"));
			user.setPassword(row.getString("password"));
			user.setName(row.getString("name"));
			user.setType(Type.valueOf(row.getString("type")));
			user.setDepartment(Department.valueOf(row.getString("department")));
			user.setTotalReimbursement(row.getDouble("TotalReimbursement"));
			user.setPendingReimbursement(row.getDouble("PendingReimbursement"));
			user.setAwardedReimbursement(row.getDouble("AwardedReimbursement"));
			user.setSupervisorUsername(row.getString("supervisorUsername"));
			users.put(user.getUsername(), user);
		};
		return users;
	}
	/*
	@Override
	public Map<String, User> getUsersByDepartment(String department) {
		Map<String, User> users = new HashMap<String, User>();
		String query = "SELECT * FROM User where department = ?";
		BoundStatement bound = session.prepare(query).bind(department);
		ResultSet rs = session.execute(bound);
		rs.forEach( row -> {
			User user = new User();
			user.setUsername(row.getString("username"));
			user.setPassword(row.getString("password"));
			user.setName(row.getString("name"));
			user.setType(Type.valueOf(row.getString("type")));
			user.setDepartment(Department.valueOf(row.getString("department")));
			user.setTotalReimbursement(row.getDouble("TotalReimbursement"));
			user.setPendingReimbursement(row.getDouble("PendingReimbursement"));
			user.setAwardedReimbursement(row.getDouble("AwardedReimbursement"));
			user.setSupervisorUsername(row.getString("supervisorUsername"));
			users.put(user.getUsername(), user);
		});
		return users;
	}
	
	@Override
	public Map<String, User> getUsersByDepartmentAndType(String department, String type) {
		Map<String, User> users = new HashMap<String, User>();
		String query = "SELECT * FROM User where department = ? and type = ?";
		BoundStatement bound = session.prepare(query).bind(department, type);
		ResultSet rs = session.execute(bound);
		rs.forEach( row -> {
			User user = new User();
			user.setUsername(row.getString("username"));
			user.setPassword(row.getString("password"));
			user.setName(row.getString("name"));
			user.setType(Type.valueOf(row.getString("type")));
			user.setDepartment(Department.valueOf(row.getString("department")));
			user.setTotalReimbursement(row.getDouble("TotalReimbursement"));
			user.setPendingReimbursement(row.getDouble("PendingReimbursement"));
			user.setAwardedReimbursement(row.getDouble("AwardedReimbursement"));
			user.setSupervisorUsername(row.getString("supervisorUsername"));
			users.put(user.getUsername(), user);
		});
		return users;
	}
*/
	//get a specific user, good for login and getting the submitter of the reimbursement form
	@Override
	public User getUser(String username) {
		User user = null;
		String query = "SELECT username, password, name, type, department, totalReimbursement, pendingReimbursement, awardedReimbursement, supervisorUsername FROM LoginUser where username = ?;";
		BoundStatement bound = session.prepare(query).bind(username);
		ResultSet rs = session.execute(bound);
		Row data = rs.one();
		if(data != null) {
			user = new User();
			user.setUsername(data.getString("username"));
			user.setPassword(data.getString("password"));
			user.setName(data.getString("name"));
			user.setType(Type.valueOf(data.getString("type")));
			user.setDepartment(Department.valueOf(data.getString("department")));
			user.setTotalReimbursement(data.getDouble("TotalReimbursement"));
			user.setPendingReimbursement(data.getDouble("PendingReimbursement"));
			user.setAwardedReimbursement(data.getDouble("AwardedReimbursement"));
			user.setSupervisorUsername(data.getString("supervisorUsername"));
		}
		return user;
	}
/*	
	public User getDepartmentHead(String department) {
		User user = null;
		String query = "SELECT username, password, name, type, department, totalReimbursement, pendingReimbursement, awardedReimbursement, supervisorUsername FROM User where department = ?;";
		BoundStatement bound = session.prepare(query).bind(department);
		ResultSet rs = session.execute(bound);
		Row data = rs.one();
		if(data != null) {
			user = new User();
			user.setUsername(data.getString("username"));
			user.setPassword(data.getString("password"));
			user.setName(data.getString("name"));
			user.setType(Type.valueOf(data.getString("type")));
			user.setDepartment(Department.valueOf(data.getString("department")));
			user.setTotalReimbursement(data.getDouble("TotalReimbursement"));
			user.setPendingReimbursement(data.getDouble("PendingReimbursement"));
			user.setAwardedReimbursement(data.getDouble("AwardedReimbursement"));
			user.setSupervisorUsername(data.getString("supervisorUsername"));
		}
		return user;
	}
*/
	//redirect to add
	@Override
	public void updateUser(User user) {
		addUser(user);
		
	}
/*
	@Override
	public void removeUser(User user) {
		String query = "DELETE FROM User WHERE department = ? and type = ? and username = ?";
		//CassandraUtil.getCassandraUtil().getSession().execute(sb.toString());
		SimpleStatement s = new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(user.getDepartment(), user.getType(), user.getUsername());
		session.execute(bound);
		
		String query2 = "DELETE FROM LoginUser WHERE username = ?";
		//CassandraUtil.getCassandraUtil().getSession().execute(sb.toString());
		SimpleStatement s2 = new SimpleStatementBuilder(query2)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound2 = session.prepare(s2).bind(user.getUsername());
		session.execute(bound2);
		
	}
*/
}
