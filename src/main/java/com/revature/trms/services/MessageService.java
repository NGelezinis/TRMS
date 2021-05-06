package com.revature.trms.services;

import java.util.List;

import com.revature.trms.beans.Message;
import com.revature.trms.data.MessageDAO;

public interface MessageService {
	public void setMessageDAO(MessageDAO myMessageDAO);
	public boolean addMessage(Message message);
	public List<Message> getMessages(String username);

}
