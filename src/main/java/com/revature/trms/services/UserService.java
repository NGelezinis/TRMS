package com.revature.trms.services;

import java.util.List;
import java.util.Map;

import com.revature.trms.beans.User;

public interface UserService {
	public boolean addUser(User user);
	public User getUser(String username);
	public List<User> getUsers();
	public boolean updateUser(User user);
	//public boolean removeUser(User user);

}
