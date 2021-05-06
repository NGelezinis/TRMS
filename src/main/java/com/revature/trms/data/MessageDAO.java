package com.revature.trms.data;

import java.util.List;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.trms.beans.Message;

public interface MessageDAO {
	public void setSession(CqlSession mySession);
	public void setSimpleStatementBuilder(SimpleStatementBuilder mySimpleStatementBuilder);
	public void addMessage(Message message);
	public List<Message> getMessages(String username);
}
