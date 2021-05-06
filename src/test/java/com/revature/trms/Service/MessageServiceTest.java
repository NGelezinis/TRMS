package com.revature.trms.Service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import com.revature.trms.beans.Message;
import com.revature.trms.data.MessageDAO;
import com.revature.trms.data.MessageDAOImpl;
import com.revature.trms.services.MessageService;
import com.revature.trms.services.MessageServiceImpl;

public class MessageServiceTest {
	private static MessageService messageService;
	private static MessageDAO messageDAO;
	
	@Before
	public void setUp() {
		messageService = new MessageServiceImpl();
		messageDAO = mock(MessageDAOImpl.class);
		messageService.setMessageDAO(messageDAO);
		
	}
	
	@After
	public void tearDown() {
		messageService = null;
		messageDAO = null;
	}
	
	@Test
	public void testAddMessage() {
		Message message = new Message();
		ArgumentCaptor<Message> captor = ArgumentCaptor.forClass(Message.class);
		messageService.addMessage(message);
		verify(messageDAO).addMessage(captor.capture());
		
	}
	
	@Test
	public void testGetMessages() {
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		messageService.getMessages("steve99312");
		verify(messageDAO).getMessages(captor.capture());
	}

}
