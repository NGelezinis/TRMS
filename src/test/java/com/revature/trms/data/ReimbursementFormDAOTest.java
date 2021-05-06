package com.revature.trms.data;

//import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
//import org.mockito.junit.MockitoJUnitRunner;

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
import com.revature.trms.beans.Department;
import com.revature.trms.beans.EventType;
import com.revature.trms.beans.Format;
import com.revature.trms.beans.ReimbursementForm;
import com.revature.trms.beans.Status;
import com.revature.trms.beans.Type;
import com.revature.trms.beans.User;
import com.datastax.oss.driver.api.core.CqlSession;

//@RunWith(MockitoJUnitRunner.class)
public class ReimbursementFormDAOTest {

	private ReimbursementFormDAO reimbursementFormDAO;
	private ReimbursementForm reimbursementForm;
	private List<ReimbursementForm> reimbursementForms;
	private CqlSession session;
	private PreparedStatement prepared;
	private BoundStatement bound;
	private ResultSet result;
	private Row row;
	private SimpleStatement simpleStatement;
	private SimpleStatementBuilder builder;

	@Before
	public void setUp() throws Exception {
		// set up mocks
		session = mock(CqlSession.class);
		prepared = mock(com.datastax.oss.driver.api.core.cql.PreparedStatement.class);
		bound = mock(BoundStatement.class);
		result = mock(ResultSet.class);
		row = mock(Row.class);
		simpleStatement = mock(SimpleStatement.class);
		builder = mock(SimpleStatementBuilder.class);
		this.reimbursementFormDAO = new ReimbursementFormDAOImpl();
		this.reimbursementFormDAO.setSession(session);
		this.reimbursementFormDAO.setSimpleStatementBuilder(builder);

		ReimbursementForm reimbursementForm = new ReimbursementForm();
		reimbursementForm.setId(UUID.randomUUID());
		reimbursementForm.setName("Ben");
		reimbursementForm.setUsername("b19");
		reimbursementForm.setDateSubmitted("3/7/2020");
		reimbursementForm.setDateOfEvent("4/5/2020");
		reimbursementForm.setTime("5:00");
		reimbursementForm.setLocation("1879 South Ave");
		reimbursementForm.setDescription("test1");
		reimbursementForm.setCost(100.0);
		reimbursementForm.setReimbursement(30.0);
		reimbursementForm.setFormat(Format.valueOf("PASSFAIL"));
		reimbursementForm.setType(EventType.valueOf("OTHER"));
		reimbursementForm.setJustification("test2");
		reimbursementForm.setDepartment(Department.valueOf("HR"));
		reimbursementForm.setSupervisor("h913");
		reimbursementForm.setBenCo("y888");
		reimbursementForm.setSupervisorApproval(Status.valueOf("PENDING"));
		reimbursementForm.setDeptHeadApproval(Status.valueOf("PENDING"));
		reimbursementForm.setBenCoApproval(Status.valueOf("PENDING"));
		reimbursementForm.setReasonForDeclining("test3");
		reimbursementForm.setUrgent(false);
		reimbursementForm.setReimbursed(false);
		reimbursementForm.setAttachments(new ArrayList<String>());
		reimbursementForm.setProjectedReimbursement(30.0);
		reimbursementForm.setBenCoReason("test4");
		reimbursementForm.setExceedsFundsAvailable(false);
		reimbursementForm.setDaysTillEvent(29);

		reimbursementForms = new ArrayList<ReimbursementForm>();
		reimbursementForms.add(reimbursementForm);

		ReimbursementForm reimbursementForm2 = new ReimbursementForm();
		reimbursementForm2.setId(UUID.randomUUID());
		reimbursementForm2.setName("Bob");
		reimbursementForm2.setUsername("b19");
		reimbursementForm2.setDateSubmitted("3/7/2020");
		reimbursementForm2.setDateOfEvent("4/5/2020");
		reimbursementForm2.setTime("5:00");
		reimbursementForm2.setLocation("1879 South Ave");
		reimbursementForm2.setDescription("test1");
		reimbursementForm2.setCost(100.0);
		reimbursementForm2.setReimbursement(30.0);
		reimbursementForm2.setFormat(Format.valueOf("PASSFAIL"));
		reimbursementForm2.setType(EventType.valueOf("OTHER"));
		reimbursementForm2.setJustification("test2");
		reimbursementForm2.setDepartment(Department.valueOf("HR"));
		reimbursementForm2.setSupervisor("h913");
		reimbursementForm2.setBenCo("y888");
		reimbursementForm2.setSupervisorApproval(Status.valueOf("PENDING"));
		reimbursementForm2.setDeptHeadApproval(Status.valueOf("PENDING"));
		reimbursementForm2.setBenCoApproval(Status.valueOf("PENDING"));
		reimbursementForm2.setReasonForDeclining("test3");
		reimbursementForm2.setUrgent(false);
		reimbursementForm2.setReimbursed(false);
		reimbursementForm2.setAttachments(new ArrayList<String>());
		reimbursementForm2.setProjectedReimbursement(30.0);
		reimbursementForm2.setBenCoReason("test4");
		reimbursementForm2.setExceedsFundsAvailable(false);
		reimbursementForm2.setDaysTillEvent(29);

		reimbursementForms.add(reimbursementForm2);
	}

	@Test
	public void testAddReimbursementForm() {
		// set up mocks
		
		ObjectMapper o = new ObjectMapper();
		String json = "ten";
		try {
			json = o.writeValueAsString(reimbursementForms.get(0));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		when(builder.setQuery(json)).thenReturn(builder);
		when(builder.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)).thenReturn(builder);
		when(builder.build()).thenReturn(simpleStatement);
		when(session.prepare(simpleStatement)).thenReturn(prepared);
		when(prepared.bind(json)).thenReturn(bound);
		when(session.execute(bound)).thenReturn(result);		
		
		
		
		when(builder.setQuery("DELETE FROM ReimbursementForm WHERE uuid = ?")).thenReturn(builder);
		when(builder.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)).thenReturn(builder);
		when(builder.build()).thenReturn(simpleStatement);
		when(session.prepare(simpleStatement)).thenReturn(prepared);
		when(prepared.bind(reimbursementForms.get(0).getId())).thenReturn(bound);
		when(session.execute(bound)).thenReturn(result);	
		reimbursementFormDAO.deleteReimbursementForm(reimbursementForms.get(0));
		verify(session).execute(bound);

	}

	@Test
	public void testGetReimbursementForm() {
		// mock the query sequence
		when(session.prepare("SELECT * FROM ReimbursementForm WHERE uuid = ?;")).thenReturn(prepared);
		when(prepared.bind(reimbursementForms.get(0).getId())).thenReturn(bound);
		when(session.execute(bound)).thenReturn(result);
		when(result.one()).thenReturn(row);

		// mock the row data returned

		//ReimbursementForm reimbursementForm = new ReimbursementForm();
		when(row.getUuid("id")).thenReturn(reimbursementForms.get(0).getId());
		when(row.getString("name")).thenReturn(reimbursementForms.get(0).getName());
		when(row.getString("username")).thenReturn(reimbursementForms.get(0).getUsername());
		when(row.getString("dateSubmitted")).thenReturn(reimbursementForms.get(0).getDateSubmitted());
		when(row.getString("dateOfEvent")).thenReturn(reimbursementForms.get(0).getDateOfEvent());
		when(row.getString("time")).thenReturn(reimbursementForms.get(0).getTime());
		when(row.getString("location")).thenReturn(reimbursementForms.get(0).getLocation());
		when(row.getString("description")).thenReturn(reimbursementForms.get(0).getDescription());
		when(row.getDouble("cost")).thenReturn(reimbursementForms.get(0).getCost());
		when(row.getDouble("reimbursement")).thenReturn(reimbursementForms.get(0).getReimbursement());
		when(row.getString("format")).thenReturn(reimbursementForms.get(0).getFormat().name());
		when(row.getString("type")).thenReturn(reimbursementForms.get(0).getType().name());
		when(row.getString("justification")).thenReturn(reimbursementForms.get(0).getJustification());
		when(row.getString("department")).thenReturn(reimbursementForms.get(0).getDepartment().name());
		when(row.getString("supervisor")).thenReturn(reimbursementForms.get(0).getSupervisor());
		when(row.getString("benCo")).thenReturn(reimbursementForms.get(0).getBenCo());
		when(row.getString("supervisorApproval")).thenReturn(reimbursementForms.get(0).getSupervisorApproval().name());
		when(row.getString("deptHeadApproval")).thenReturn(reimbursementForms.get(0).getDeptHeadApproval().name());
		when(row.getString("benCoApproval")).thenReturn(reimbursementForms.get(0).getBenCoApproval().name());
		when(row.getString("reasonForDeclining")).thenReturn(reimbursementForms.get(0).getReasonForDeclining());
		when(row.getBoolean("urgent")).thenReturn(reimbursementForms.get(0).isUrgent());
		when(row.getBoolean("reimbursed")).thenReturn(reimbursementForms.get(0).isReimbursed());
		when(row.getList("attachments", String.class)).thenReturn(reimbursementForms.get(0).getAttachments());
		when(row.getDouble("projectedReimbursement")).thenReturn(reimbursementForms.get(0).getProjectedReimbursement());
		when(row.getString("benCoReason")).thenReturn(reimbursementForms.get(0).getBenCoReason());
		when(row.getBoolean("exceedsFundsAvailable")).thenReturn(reimbursementForms.get(0).isExceedsFundsAvailable());
		when(row.getInt("daysTillEvent")).thenReturn(reimbursementForms.get(0).getDaysTillEvent());

		ReimbursementForm reimbursement = reimbursementFormDAO.getReimbursementForm(reimbursementForms.get(0).getId());
		assertEquals("Reimbursement id should be " + reimbursementForms.get(0).getId(), reimbursementForms.get(0).getId(),
				reimbursement.getId());

	}

	@Test
	public void testGetReimbursementForms() {
		Row row = mock(Row.class);
		Iterator<Row> iterator = mock(Iterator.class);

		// mock the query sequence
		when(session.execute("SELECT * FROM ReimbursementForm;")).thenReturn(result);

		when(iterator.hasNext()).thenReturn(true, true, false);
		when(iterator.next()).thenReturn(row, row);
		when(result.iterator()).thenReturn(iterator);
		
		
		
		
		ReimbursementForm reimbursementForm = new ReimbursementForm();
		when(row.getUuid("id")).thenReturn(reimbursementForms.get(0).getId(), reimbursementForms.get(1).getId());
		when(row.getString("name")).thenReturn(reimbursementForms.get(0).getName(), reimbursementForms.get(1).getName());
		when(row.getString("username")).thenReturn(reimbursementForms.get(0).getUsername(), reimbursementForms.get(1).getUsername());
		when(row.getString("dateSubmitted")).thenReturn(reimbursementForms.get(0).getDateSubmitted(), reimbursementForms.get(1).getDateSubmitted());
		when(row.getString("dateOfEvent")).thenReturn(reimbursementForms.get(0).getDateOfEvent(), reimbursementForms.get(1).getDateOfEvent());
		when(row.getString("time")).thenReturn(reimbursementForms.get(0).getTime(), reimbursementForms.get(1).getTime());
		when(row.getString("location")).thenReturn(reimbursementForms.get(0).getLocation(), reimbursementForms.get(1).getLocation());
		when(row.getString("description")).thenReturn(reimbursementForms.get(0).getDescription(), reimbursementForms.get(1).getDescription());
		when(row.getDouble("cost")).thenReturn(reimbursementForms.get(0).getCost(), reimbursementForms.get(1).getCost());
		when(row.getDouble("reimbursement")).thenReturn(reimbursementForms.get(0).getReimbursement(), reimbursementForms.get(1).getReimbursement());
		when(row.getString("format")).thenReturn(reimbursementForms.get(0).getFormat().name(), reimbursementForms.get(1).getFormat().name());
		when(row.getString("type")).thenReturn(reimbursementForms.get(0).getType().name(), reimbursementForms.get(1).getType().name());
		when(row.getString("justification")).thenReturn(reimbursementForms.get(0).getJustification(), reimbursementForms.get(1).getJustification());
		when(row.getString("department")).thenReturn(reimbursementForms.get(0).getDepartment().name(), reimbursementForms.get(1).getDepartment().name());
		when(row.getString("supervisor")).thenReturn(reimbursementForms.get(0).getSupervisor(), reimbursementForms.get(1).getSupervisor());
		when(row.getString("benCo")).thenReturn(reimbursementForms.get(0).getBenCo(), reimbursementForms.get(1).getBenCo());
		when(row.getString("supervisorApproval")).thenReturn(reimbursementForms.get(0).getSupervisorApproval().name(), reimbursementForms.get(1).getSupervisorApproval().name());
		when(row.getString("deptHeadApproval")).thenReturn(reimbursementForms.get(0).getDeptHeadApproval().name(), reimbursementForms.get(1).getDeptHeadApproval().name());
		when(row.getString("benCoApproval")).thenReturn(reimbursementForms.get(0).getBenCoApproval().name(), reimbursementForms.get(1).getBenCoApproval().name());
		when(row.getString("reasonForDeclining")).thenReturn(reimbursementForms.get(0).getReasonForDeclining(), reimbursementForms.get(1).getReasonForDeclining());
		when(row.getBoolean("urgent")).thenReturn(reimbursementForms.get(0).isUrgent(), reimbursementForms.get(1).isUrgent());
		when(row.getBoolean("reimbursed")).thenReturn(reimbursementForms.get(0).isReimbursed(), reimbursementForms.get(1).isReimbursed());
		when(row.getList("attachments", String.class)).thenReturn(reimbursementForms.get(0).getAttachments(), reimbursementForms.get(1).getAttachments());
		when(row.getDouble("projectedReimbursement")).thenReturn(reimbursementForms.get(0).getProjectedReimbursement(), reimbursementForms.get(1).getProjectedReimbursement());
		when(row.getString("benCoReason")).thenReturn(reimbursementForms.get(0).getBenCoReason(), reimbursementForms.get(1).getBenCoReason());
		when(row.getBoolean("exceedsFundsAvailable")).thenReturn(reimbursementForms.get(0).isExceedsFundsAvailable(), reimbursementForms.get(1).isExceedsFundsAvailable());
		when(row.getInt("daysTillEvent")).thenReturn(reimbursementForms.get(0).getDaysTillEvent(), reimbursementForms.get(1).getDaysTillEvent());



		List<ReimbursementForm> list = reimbursementFormDAO.getReimbursementForms();
		assertEquals("Reimbursements should have the same values", reimbursementForms.get(0).getId(), list.get(0).getId());
		assertEquals("Reimbursements should have the same values", reimbursementForms.get(1).getId(), list.get(1).getId());
	}

	@Test
	public void testRemoveReimbursementForm() {
		when(builder.setQuery("DELETE FROM ReimbursementForm WHERE uuid = ?")).thenReturn(builder);
		when(builder.setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM)).thenReturn(builder);
		when(builder.build()).thenReturn(simpleStatement);
		when(session.prepare(simpleStatement)).thenReturn(prepared);
		when(prepared.bind(reimbursementForms.get(0).getId())).thenReturn(bound);
		when(session.execute(bound)).thenReturn(result);	
		reimbursementFormDAO.deleteReimbursementForm(reimbursementForms.get(0));
		verify(session).execute(bound);
	}

}