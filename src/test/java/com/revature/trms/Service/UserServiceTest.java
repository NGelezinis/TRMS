package com.revature.trms.Service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.util.reflection.Whitebox;

import com.revature.trms.beans.Department;
import com.revature.trms.beans.Type;
import com.revature.trms.beans.User;
import com.revature.trms.data.UserDAO;
import com.revature.trms.data.UserDAOImpl;
import com.revature.trms.services.UserService;
import com.revature.trms.services.UserServiceImpl;

public class UserServiceTest {
	private static UserService userService;
	private static UserDAO userDAO;
	//private static User user;
	//private static Map<String, User> users;
	
	@Before
	public void setUp() {
		userService = new UserServiceImpl();
		userDAO = mock(UserDAOImpl.class);
		Whitebox.setInternalState(userService, "userDAO", userDAO);
		
	}
	@After
	public void tearDown() {
		userService = null;
		userDAO = null;
	}
	
	
	@Test
	public void testAddUser() {
		User user = new User();
		user.setUsername("mpennywise");
		user.setPassword("779611");
		user.setName("Michael Pennywise");
		user.setType(Type.valueOf("EMPLOYEE"));
		user.setDepartment(Department.valueOf("RANDD"));
		user.setSupervisorUsername("bbentley");
		user.setTotalReimbursement(1000.0);
		user.setPendingReimbursement(100.0);
		user.setAwardedReimbursement(100.0);
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		//ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);
		//verify(userDAO).getUser(captor2.capture());
		when(userDAO.getUser(user.getUsername())).thenReturn(null);
		userService.addUser(user);
		verify(userDAO).addUser(captor.capture());
		
	}
	
	@Test
	public void testAddUserFail() {
		User user = new User();
		user.setUsername("mpennywise");
		user.setPassword("779611");
		user.setName("Michael Pennywise");
		user.setType(Type.valueOf("EMPLOYEE"));
		user.setDepartment(Department.valueOf("RANDD"));
		user.setSupervisorUsername("bbentley");
		user.setTotalReimbursement(1000.0);
		user.setPendingReimbursement(100.0);
		user.setAwardedReimbursement(100.0);
		//ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		//ArgumentCaptor<String> captor2 = ArgumentCaptor.forClass(String.class);
		//verify(userDAO).getUser(captor2.capture());
		when(userService.getUser(user.getUsername())).thenReturn(user);
		//verify(userDAO).addUser(captor.capture());
		assertFalse("A previous entry should return false", userService.addUser(user));
		
	}
	
	@Test
	public void testGetUser() {
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		userService.getUser("Tom111");
		verify(userDAO).getUser(captor.capture());
	}
	
	@Test
	public void testGetUsers() {
		Map<String, User> m = new HashMap();
		User user1 = new User();
		user1.setUsername("tom11");
		User user2 = new User();
		user2.setUsername("ben12");
		m.put("tom11", user1);
		m.put("ben12", user2);
		when(userDAO.getUsers()).thenReturn(m);
		userService.getUsers();
		assertTrue("the array should contain user1", userService.getUsers().contains(user1));
		assertTrue("the array should contain user2", userService.getUsers().contains(user2));
		
	}
	
	@Test
	public void testUpdateUser() {
		User user = new User();
		user.setUsername("mpennywise");
		user.setPassword("779611");
		user.setName("Michael Pennywise");
		user.setType(Type.valueOf("EMPLOYEE"));
		user.setDepartment(Department.valueOf("RANDD"));
		user.setSupervisorUsername("bbentley");
		user.setTotalReimbursement(1000.0);
		user.setPendingReimbursement(100.0);
		user.setAwardedReimbursement(100.0);
		when(userDAO.getUser(user.getUsername())).thenReturn(user);
		//ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		assertTrue("Should return true for an update if the user exists", userService.updateUser(user));
		//verify(userDAO.updateUser(captor.capture()));
		
	}
	@Test
	public void testUpdateUserFail() {
		User user = new User();
		user.setUsername("mpennywise");
		user.setPassword("779611");
		user.setName("Michael Pennywise");
		user.setType(Type.valueOf("EMPLOYEE"));
		user.setDepartment(Department.valueOf("RANDD"));
		user.setSupervisorUsername("bbentley");
		user.setTotalReimbursement(1000.0);
		user.setPendingReimbursement(100.0);
		user.setAwardedReimbursement(100.0);
		when(userDAO.getUser(user.getUsername())).thenReturn(null);
		ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		assertFalse("Should return false for an update if the user doesn't exist", userService.updateUser(user));
		//verify(userDAO).updateUser(captor.capture());
		
	}
	/*
	//we don't remove users
	@Test
	public void testRemoveUser() {
		User user = new User();
		user.setUsername("mpennywise");
		user.setPassword("779611");
		user.setName("Michael Pennywise");
		user.setType(Type.valueOf("EMPLOYEE"));
		user.setDepartment(Department.valueOf("RANDD"));
		user.setSupervisorUsername("bbentley");
		user.setTotalReimbursement(1000.0);
		user.setPendingReimbursement(100.0);
		user.setAwardedReimbursement(100.0);
		when(userDAO.getUser(user.getUsername())).thenReturn(user);
		assertTrue("Should return true for an update if the user exists", userService.updateUser(user));
	}
	@Test
	public void testRemoveUserFail() {
		User user = new User();
		user.setUsername("mpennywise");
		user.setPassword("779611");
		user.setName("Michael Pennywise");
		user.setType(Type.valueOf("EMPLOYEE"));
		user.setDepartment(Department.valueOf("RANDD"));
		user.setSupervisorUsername("bbentley");
		user.setTotalReimbursement(1000.0);
		user.setPendingReimbursement(100.0);
		user.setAwardedReimbursement(100.0);
		when(userDAO.getUser(user.getUsername())).thenReturn(null);
		assertFalse("Should return true for an update if the user doesn't exist", userService.updateUser(user));
	}
*/
}
