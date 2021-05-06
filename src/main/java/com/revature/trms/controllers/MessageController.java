package com.revature.trms.controllers;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.revature.trms.aspects.Authorized;
import com.revature.trms.beans.Message;
import com.revature.trms.beans.User;
import com.revature.trms.services.MessageService;

import io.javalin.http.Context;

@Service
public class MessageController {
	@Autowired
	private MessageService messageService;
	
	private static Logger log = LogManager.getLogger(MessageController.class);
	
	@Authorized
	public void sendMessage(Context ctx) {
		try {
			User user = ctx.sessionAttribute("User");
			String words = ctx.formParam("message");
			UUID id = UUID.randomUUID();
			String reciever = ctx.formParam("reciever");
			if (user.getUsername().equals(reciever)) {
				ctx.status(400);
				return;
			}
			Message message = new Message();
			message.setMessage(words);
			message.setSender(user.getUsername());
			message.setReciever(reciever);
			message.setMessageId(id);
			message.setRead(false);
			messageService.addMessage(message);
			ctx.status(201);
			return;
		} catch (Exception e) {
			ctx.status(400);
			return;
		}
	}
	
	@Authorized
	public void getNewMessages(Context ctx) {
		User user = ctx.sessionAttribute("User");
		try {
			List<Message> messages = messageService.getMessages(user.getUsername()).stream()
					.filter((m) -> !m.isRead())
					.collect(Collectors.toList());;
			ctx.status(200);
			ctx.json(messages);
		} catch (Exception e) {
			ctx.status(400);
			return;
		}
	}
	
	@Authorized
	public void getAllMessages(Context ctx) {
		User user = ctx.sessionAttribute("User");
		try {
			List<Message> messages = messageService.getMessages(user.getUsername());
			ctx.status(200);
			ctx.json(messages);
		} catch (Exception e) {
			ctx.status(400);
			return;
		}
		
		
	}

}
