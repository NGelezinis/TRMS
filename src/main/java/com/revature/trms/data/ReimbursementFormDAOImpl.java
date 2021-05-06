package com.revature.trms.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.trms.beans.Department;
import com.revature.trms.beans.EventType;
import com.revature.trms.beans.Format;
import com.revature.trms.beans.ReimbursementForm;
import com.revature.trms.beans.Status;

@Service
public class ReimbursementFormDAOImpl implements ReimbursementFormDAO{
	private CqlSession session;
	private SimpleStatementBuilder builder = new SimpleStatementBuilder("string");
	
	@Autowired
	public void setSession(CqlSession mySession) {
		this.session = mySession;
	}
	
	public void setSimpleStatementBuilder(SimpleStatementBuilder mySimpleStatementBuilder) {
		this.builder = mySimpleStatementBuilder;
	}

	//add a reimbursement form to the database
	@Override
	public void addReimbursementForm(ReimbursementForm reimbursementForm) throws Exception {
		ObjectMapper o = new ObjectMapper();
		String json = o.writeValueAsString(reimbursementForm);
		String query = "INSERT INTO ReimbursementForm json ?;";
		
		SimpleStatement s = new SimpleStatementBuilder(query)
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(json);
		session.execute(bound);
		
	}

	//get a reimbursement form from the database
	@Override
	public ReimbursementForm getReimbursementForm(UUID id) {
		String query = "SELECT * FROM ReimbursementForm WHERE uuid = ?;";
		PreparedStatement prepared = session.prepare(query);
		BoundStatement bound = prepared.bind(id);
		ResultSet rs = session.execute(bound);
		Row data = rs.one();
		if(data != null) {
			ReimbursementForm reimbursementForm = new ReimbursementForm();
			reimbursementForm.setId(data.getUuid("id"));
			reimbursementForm.setName(data.getString("name"));
			reimbursementForm.setUsername(data.getString("username"));
			reimbursementForm.setDateSubmitted(data.getString("dateSubmitted"));
			reimbursementForm.setDateOfEvent(data.getString("dateOfEvent"));
			reimbursementForm.setTime(data.getString("time"));
			reimbursementForm.setLocation(data.getString("location"));
			reimbursementForm.setDescription(data.getString("description"));
			reimbursementForm.setCost(data.getDouble("cost"));
			reimbursementForm.setReimbursement(data.getDouble("reimbursement"));
			reimbursementForm.setFormat(Format.valueOf(data.getString("format")));
			reimbursementForm.setType(EventType.valueOf(data.getString("type")));
			reimbursementForm.setJustification(data.getString("justification"));
			reimbursementForm.setDepartment(Department.valueOf(data.getString("department")));
			reimbursementForm.setSupervisor(data.getString("supervisor"));
			reimbursementForm.setBenCo(data.getString("benCo"));
			reimbursementForm.setSupervisorApproval(Status.valueOf(data.getString("supervisorApproval")));
			reimbursementForm.setDeptHeadApproval(Status.valueOf(data.getString("deptHeadApproval")));
			reimbursementForm.setBenCoApproval(Status.valueOf(data.getString("benCoApproval")));
			reimbursementForm.setReasonForDeclining(data.getString("reasonForDeclining"));
			reimbursementForm.setUrgent(data.getBoolean("urgent"));
			reimbursementForm.setReimbursed(data.getBoolean("reimbursed"));
			reimbursementForm.setAttachments(data.getList("attachments", String.class));
			reimbursementForm.setProjectedReimbursement(data.getDouble("projectedReimbursement"));
			reimbursementForm.setBenCoReason(data.getString("benCoReason"));
			reimbursementForm.setExceedsFundsAvailable(data.getBoolean("exceedsFundsAvailable"));
			reimbursementForm.setDaysTillEvent(data.getInt("daysTillEvent"));
			return reimbursementForm;
		};
		return null;
	}

	//get all the reimbursement Forms from the database, can't be in department only cause of benCos
	@Override
	public List<ReimbursementForm> getReimbursementForms() {
		List<ReimbursementForm> forms = new ArrayList<ReimbursementForm>();
		String query = "SELECT * FROM ReimbursementForm;";
		ResultSet rs = session.execute(query);
		for(Row data: rs) {
			ReimbursementForm reimbursementForm = new ReimbursementForm();
			reimbursementForm.setId(data.getUuid("id"));
			reimbursementForm.setName(data.getString("name"));
			reimbursementForm.setUsername(data.getString("username"));
			reimbursementForm.setDateSubmitted(data.getString("dateSubmitted"));
			reimbursementForm.setDateOfEvent(data.getString("dateOfEvent"));
			reimbursementForm.setTime(data.getString("time"));
			reimbursementForm.setLocation(data.getString("location"));
			reimbursementForm.setDescription(data.getString("description"));
			reimbursementForm.setCost(data.getDouble("cost"));
			reimbursementForm.setReimbursement(data.getDouble("reimbursement"));
			reimbursementForm.setFormat(Format.valueOf(data.getString("format")));
			reimbursementForm.setType(EventType.valueOf(data.getString("type")));
			reimbursementForm.setJustification(data.getString("justification"));
			reimbursementForm.setDepartment(Department.valueOf(data.getString("department")));
			reimbursementForm.setSupervisor(data.getString("supervisor"));
			reimbursementForm.setBenCo(data.getString("benCo"));
			reimbursementForm.setSupervisorApproval(Status.valueOf(data.getString("supervisorApproval")));
			reimbursementForm.setDeptHeadApproval(Status.valueOf(data.getString("deptHeadApproval")));
			reimbursementForm.setBenCoApproval(Status.valueOf(data.getString("benCoApproval")));
			reimbursementForm.setReasonForDeclining(data.getString("reasonForDeclining"));
			reimbursementForm.setUrgent(data.getBoolean("urgent"));
			reimbursementForm.setReimbursed(data.getBoolean("reimbursed"));
			reimbursementForm.setAttachments(data.getList("attachments", String.class));
			reimbursementForm.setProjectedReimbursement(data.getDouble("projectedReimbursement"));
			reimbursementForm.setBenCoReason(data.getString("benCoReason"));
			reimbursementForm.setExceedsFundsAvailable(data.getBoolean("exceedsFundsAvailable"));
			reimbursementForm.setDaysTillEvent(data.getInt("daysTillEvent"));
			forms.add(reimbursementForm);
		};
		return forms;
	}

	//redirect to add since add overwrites
	@Override
	public void updateReimbursementForm(ReimbursementForm reimbursementForm) throws Exception {
		this.addReimbursementForm(reimbursementForm);
		
	}

	//removes a form from the database
	@Override
	public void deleteReimbursementForm(ReimbursementForm reimbursementForm) {
		String query = "DELETE FROM ReimbursementForm WHERE uuid = ?";
		builder.setQuery(query);
		SimpleStatement s = builder
				.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(reimbursementForm.getId());
		session.execute(bound);
		
	}
	
}
