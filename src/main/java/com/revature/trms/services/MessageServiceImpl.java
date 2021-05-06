package com.revature.trms.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.trms.beans.Message;
import com.revature.trms.data.MessageDAO;
import com.revature.trms.data.ReimbursementFormDAO;

@Service
public class MessageServiceImpl implements MessageService {
	MessageDAO messageDAO;
	
	@Autowired
	public void setMessageDAO(MessageDAO myMessageDAO) {
		this.messageDAO = myMessageDAO;
	}

	//add a message
	@Override
	public boolean addMessage(Message message) {
		try {
			messageDAO.addMessage(message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//returns a list of messages based on the reciever of the message
	@Override
	public List<Message> getMessages(String username) {
		try {
			return messageDAO.getMessages(username);
		} catch (Exception e) {
			return null;
		}
	}

}
