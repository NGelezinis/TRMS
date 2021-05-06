package com.revature.trms.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.trms.beans.User;
import com.revature.trms.services.UserService;

import io.javalin.http.Context;

@Service
public class UserController {
	@Autowired
	private UserService userService;
	
	
	private static Logger log = LogManager.getLogger(UserController.class);
	
	//registers a user
		public void register(Context ctx) {
			log.trace("Registering a user");
			User user = ctx.bodyAsClass(User.class);
			if (userService.addUser(user)) {
				log.trace("User registered");
				ctx.json(user);
				ctx.status(201);
			} else {
				log.trace("User already exists");
				ctx.status(409);
			}
		}
		//logs in
		public void login(Context ctx) {
			// We can get session information from the Context to use it elsewhere.
			if(ctx.sessionAttribute("User") != null) {
				log.trace("User already logged in");
				ctx.status(204);
				return;
			}
			User user = userService.getUser(ctx.formParam("username"));
			if (user == null) {
				log.trace("No User with given username found");
				ctx.status(401);
			} else if (user.getPassword().equals(ctx.formParam("password"))){
				log.trace("Logging in");
				ctx.sessionAttribute("User", user);
				ctx.json(user);
			} else {
				log.trace("Password does not match");
				ctx.status(401);
			}
		}
		
		public void logout(Context ctx) {
			log.trace("Logging out");
			ctx.req.getSession().invalidate();
		}

}
