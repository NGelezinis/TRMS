package com.revature.trms.data;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.internal.util.reflection.Whitebox;

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
import com.revature.trms.beans.Message;
import com.revature.trms.beans.ReimbursementForm;
import com.revature.trms.beans.User;
import com.datastax.oss.driver.api.core.CqlSession;

//@RunWith(MockitoJUnitRunner.class)
public class MessageDAOTest {

	private MessageDAO messageDAO;
	private Message message;
	private List<Message> messagess;
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
		messageDAO = new MessageDAOImpl();
		session = mock(CqlSession.class);
		prepared = mock(com.datastax.oss.driver.api.core.cql.PreparedStatement.class);
		bound = mock(BoundStatement.class);
		result = mock(ResultSet.class);
		row = mock(Row.class);
		simpleStatement = mock(SimpleStatement.class);
		builder = mock(SimpleStatementBuilder.class);
		
		messageDAO.setSession(session);
		messageDAO.setSimpleStatementBuilder(builder);
		
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
	public void testAddMessage() {
		message = new Message();
		message.setMessageId(UUID.randomUUID());
		message.setMessage("test");
		message.setRead(false);
		message.setReciever("Tom");
		message.setSender("Bill");
		ObjectMapper o = new ObjectMapper();
		String json = "ten";
		try {
			json = o.writeValueAsString(message);
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
		messageDAO.addMessage(message);
		verify(session).execute(bound);


	}

	@Test
	public void testGetMessages() {
		List<Message> messages = new ArrayList<Message>();
		message = new Message();
		message.setMessageId(UUID.randomUUID());
		message.setMessage("test");
		message.setRead(false);
		message.setReciever("Tom");
		message.setSender("Bill");
		messages.add(message);
		message = new Message();
		message.setMessageId(UUID.randomUUID());
		message.setMessage("test");
		message.setRead(false);
		message.setReciever("Tom");
		message.setSender("Bob");
		messages.add(message);
		
		Iterator<Row> iterator = mock(Iterator.class);
		
		//inject mock session
		messageDAO.setSession(session);
		
		//mock the query sequence
		when(session.prepare("SELECT * FROM Message where reciever = ?")).thenReturn(prepared);
		when(prepared.bind(messages.get(0).getReciever())).thenReturn(bound);
		when(session.execute(bound)).thenReturn(result);
		
		when(iterator.hasNext()).thenReturn(true, true, false);
		when(iterator.next()).thenReturn(row, row);
		when(result.iterator()).thenReturn(iterator);
		
		when(row.getUuid("messageId")).thenReturn(messages.get(0).getMessageId(), messages.get(1).getMessageId());
		when(row.getString("message")).thenReturn(messages.get(0).getMessage(),messages.get(1).getMessage());
		when(row.getBoolean("read")).thenReturn(messages.get(0).isRead(),messages.get(1).isRead());
		when(row.getString("reciever")).thenReturn(messages.get(0).getReciever(),messages.get(1).getSender());
		when(row.getString("sender")).thenReturn(messages.get(0).getSender(),messages.get(1).getSender());
		
		List<Message> list = messageDAO.getMessages("Tom");
		assertEquals("The 2 message Lists should have the same messages", messages.get(0).getMessageId(), list.get(0).getMessageId());
		assertEquals("The 2 message Lists should have the same messages", messages.get(1).getMessageId(), list.get(1).getMessageId());


	}

}
