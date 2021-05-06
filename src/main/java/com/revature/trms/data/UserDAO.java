package com.revature.trms.data;

import java.util.Map;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.trms.beans.User;

public interface UserDAO {
	public void addUser(User user);
	public Map<String, User> getUsers();
	public User getUser(String username);
	public void updateUser(User user);
	//public void removeUser(User user);
	//Map<String, User> getUsersByDepartment(String department);
	//Map<String, User> getUsersByDepartmentAndType(String department, String type);
	public void setSession(CqlSession session);
	public void setSimpleStatementBuilder(SimpleStatementBuilder builder);

}
