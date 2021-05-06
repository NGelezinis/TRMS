package com.revature.trms.data;

//import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.trms.beans.Department;
import com.revature.trms.beans.Message;
import com.revature.trms.beans.Type;
import com.revature.trms.beans.User;
import com.datastax.oss.driver.api.core.CqlSession;

//@RunWith(MockitoJUnitRunner.class)
public class UserDAOTest {
	private UserDAO userDAO;
	private User user;
	private CqlSession session;
	private PreparedStatement prepared;
	private BoundStatement bound;
	private ResultSet result;
	private Row row;
	private SimpleStatement simpleStatement;
	private SimpleStatementBuilder builder;
	
	@Before
	public void setup() {
		// set up mocks
		userDAO = new UserDAOImpl();
		session = mock(CqlSession.class);
		prepared = mock(com.datastax.oss.driver.api.core.cql.PreparedStatement.class);
		bound = mock(BoundStatement.class);
		result = mock(ResultSet.class);
		row = mock(Row.class);
		simpleStatement = mock(SimpleStatement.class);
		builder = mock(SimpleStatementBuilder.class);
		
		userDAO.setSession(session);
		userDAO.setSimpleStatementBuilder(builder);
		
	}
	
	@After
	public void teardown() {
		session = null;
		prepared = null;
		bound = null;
		result = null;
		row = null;
	}
	
	@Test 
	public void testAddUser() {
		user = new User();
		user.setUsername("mpennywise");
		user.setPassword("779611");
		user.setName("Michael Pennywise");
		user.setType(Type.valueOf("EMPLOYEE"));
		user.setDepartment(Department.valueOf("RANDD"));
		user.setSupervisorUsername("bbentley");
		user.setTotalReimbursement(1000.0);
		user.setPendingReimbursement(100.0);
		user.setAwardedReimbursement(100.0);
		ObjectMapper o = new ObjectMapper();
		String json = "ten";
		try {
			json = o.writeValueAsString(user);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		when(builder.setQuery(json)).thenReturn(builder);
		when(builder.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)).thenReturn(builder);
		when(builder.build()).thenReturn(simpleStatement);
		when(session.prepare(simpleStatement)).thenReturn(prepared);
		when(prepared.bind(json)).thenReturn(bound);
		when(session.execute(bound)).thenReturn(result);		
		
		//verify query was executed
		userDAO.addUser(user);
		verify(session).execute(bound);
	}
	
	@Test 
	public void testGetUser() {
		user = new User();
		user.setUsername("mpennywise");
		user.setPassword("779611");
		user.setName("Michael Pennywise");
		user.setType(Type.valueOf("EMPLOYEE"));
		user.setDepartment(Department.valueOf("RANDD"));
		user.setSupervisorUsername("bbentley");
		user.setTotalReimbursement(1000.0);
		user.setPendingReimbursement(100.0);
		user.setAwardedReimbursement(100.0);
		when(session.prepare("SELECT username, password, name, type, department, totalReimbursement, pendingReimbursement, awardedReimbursement, supervisorUsername FROM LoginUser where username = ?;")).thenReturn(prepared);
		when(prepared.bind(user.getUsername())).thenReturn(bound);
		when(session.execute(bound)).thenReturn(result);
		when(result.one()).thenReturn(row);
		
		when(row.getString("username")).thenReturn(user.getUsername());
		when(row.getString("password")).thenReturn(user.getPassword());
		when(row.getString("name")).thenReturn(user.getName());
		when(row.getString("type")).thenReturn(user.getType().toString());
		when(row.getString("department")).thenReturn(user.getDepartment().toString());
		when(row.getString("supervisorUsername")).thenReturn(user.getSupervisorUsername());
		when(row.getDouble("totalReimbursement")).thenReturn(user.getTotalReimbursement());
		when(row.getDouble("pendingReimbursement")).thenReturn(user.getPendingReimbursement());
		when(row.getDouble("awardedReimbursement")).thenReturn(user.getAwardedReimbursement());

		assertEquals(user.getName(), userDAO.getUser(user.getUsername()).getName());
		verify(session).execute(bound);

	}
	
	@Test
	public void testGetUsers() {
		List<User> users = new ArrayList<User>();
		user = new User();
		user.setUsername("mpennywise");
		user.setPassword("779611");
		user.setName("Michael Pennywise");
		user.setType(Type.valueOf("EMPLOYEE"));
		user.setDepartment(Department.valueOf("RANDD"));
		user.setSupervisorUsername("bbentley");
		user.setTotalReimbursement(1000.0);
		user.setPendingReimbursement(000.0);
		user.setAwardedReimbursement(000.0);
		users.add(user);
		user = new User();
		user.setUsername("mperrywinkle");
		user.setPassword("779611");
		user.setName("Mary Perrywinkle");
		user.setType(Type.valueOf("EMPLOYEE"));
		user.setDepartment(Department.valueOf("RANDD"));
		user.setSupervisorUsername("bbentley");
		user.setTotalReimbursement(1000.0);
		user.setPendingReimbursement(000.0);
		user.setAwardedReimbursement(000.0);
		users.add(user);
		userDAO.setSession(session);
		
		//mock the query sequence
		Iterator<Row> iterator = mock(Iterator.class);
		Map<String, User> users2 = new HashMap<String, User>();
		when(session.execute("SELECT * FROM LoginUser")).thenReturn(result);
		
		when(iterator.hasNext()).thenReturn(true, true, false);
		when(iterator.next()).thenReturn(row, row);
		when(result.iterator()).thenReturn(iterator);
		when(row.getString("username")).thenReturn(users.get(0).getUsername(), users.get(1).getUsername());
		when(row.getString("password")).thenReturn(users.get(0).getPassword(), users.get(1).getPassword());
		when(row.getString("name")).thenReturn(users.get(0).getName(), users.get(1).getName());
		when(row.getString("type")).thenReturn(users.get(0).getType().toString(), users.get(1).getType().toString());
		when(row.getString("department")).thenReturn(users.get(0).getDepartment().toString(), users.get(1).getDepartment().toString());
		when(row.getDouble("totalReimbursement")).thenReturn(users.get(0).getTotalReimbursement(), users.get(1).getTotalReimbursement());
		when(row.getDouble("pendingReimbursement")).thenReturn(users.get(0).getPendingReimbursement(), users.get(1).getPendingReimbursement());
		when(row.getDouble("awardedReimbursement")).thenReturn(users.get(0).getAwardedReimbursement(), users.get(1).getAwardedReimbursement());
		when(row.getString("supervisorUsername")).thenReturn(users.get(0).getSupervisorUsername(), users.get(1).getSupervisorUsername());
		
		users2 = userDAO.getUsers();
		assertEquals("The new map contains the elements of the original list", users2.get(users.get(0).getUsername()).getName(), (users.get(0).getName()));
		assertEquals("The new map contains the elements of the original list", users2.get(users.get(1).getUsername()).getName(), (users.get(1).getName()));
	}
	

}
