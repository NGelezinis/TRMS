package com.revature.trms.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.trms.beans.User;
import com.revature.trms.data.UserDAO;
import com.revature.trms.data.UserDAOImpl;

@Service
public class UserServiceImpl implements UserService{
	private UserDAO userDAO;
	
	@Autowired
	public void setUserDAO(UserDAO myUserDAO) {
		this.userDAO = myUserDAO;
	}

	//adds a user
	@Override
	public boolean addUser(User user) {
		try {
			User user2 = this.getUser(user.getUsername());
			if(user2 != null) {
				System.out.println("user found");
				return false;
			}
			userDAO.addUser(user);
			return true;
		} catch (Exception e) {
			for(StackTraceElement s : e.getStackTrace()) {
				System.out.println(s);
			}
			return false;
		}
		
	}

	//gets a user based on a string that maps to the username
	@Override
	public User getUser(String username) {
		try {
			User user = userDAO.getUser(username);
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	//returns a list of users
	@Override
	public List<User> getUsers() {
		try {
			return new ArrayList<User>(userDAO.getUsers().values());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean updateUser(User user) {
		try {
			User user2 = userDAO.getUser(user.getUsername());
			if(user2 == null) {
				return false;
			}
			userDAO.updateUser(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
/*
	@Override
	public boolean removeUser(User user) {
		try {
			User user2 = userDAO.getUser(user.getUsername());
			if(user2 == null) {
				return false;
			}
			userDAO.removeUser(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
*/
}
