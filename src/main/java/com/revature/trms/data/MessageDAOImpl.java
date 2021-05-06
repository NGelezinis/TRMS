package com.revature.trms.data;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.trms.beans.Message;

@Service
public class MessageDAOImpl implements MessageDAO {
	private CqlSession session;
	private SimpleStatementBuilder builder = new SimpleStatementBuilder("string");
	
	@Autowired
	public void setSession(CqlSession mySession) {
		this.session = mySession;
	}
	
	public void setSimpleStatementBuilder(SimpleStatementBuilder mySimpleStatementBuilder) {
		this.builder = mySimpleStatementBuilder;
	}

	//add a message
	@Override
	public void addMessage(Message message) {
		ObjectMapper o = new ObjectMapper();
		String json = null;
		try {
			json = o.writeValueAsString(message);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		String query = "Insert into Message json ?;";	
		builder.setQuery(query);
		SimpleStatement s = builder.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(json);
		session.execute(bound);
		
	}

	//return a list of messages based on the reciever
	@Override
	public List<Message> getMessages(String username) {
		List<Message> messages = new ArrayList<Message>();
		String query = "SELECT * FROM Message where reciever = ?";
		BoundStatement bound = session.prepare(query).bind(username);
		ResultSet rs = session.execute(bound);
		for(Row row: rs) {
			Message message = new Message();
			message.setReciever(row.getString("reciever"));
			message.setSender(row.getString("sender"));
			message.setMessage(row.getString("message"));
			message.setMessageId(row.getUuid("messageId"));
			message.setRead(row.getBoolean("read"));
			messages.add(message);
		};
		return messages;
	}

}
