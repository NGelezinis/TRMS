package com.revature.trms.Service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.internal.util.reflection.Whitebox;

import com.revature.trms.beans.Department;
import com.revature.trms.beans.Format;
import com.revature.trms.beans.Message;
import com.revature.trms.beans.ReimbursementForm;
import com.revature.trms.beans.Status;
import com.revature.trms.beans.Type;
import com.revature.trms.beans.User;
import com.revature.trms.data.MessageDAO;
import com.revature.trms.data.MessageDAOImpl;
import com.revature.trms.data.ReimbursementFormDAO;
import com.revature.trms.data.ReimbursementFormDAOImpl;
import com.revature.trms.data.UserDAO;
import com.revature.trms.data.UserDAOImpl;
import com.revature.trms.services.MessageService;
import com.revature.trms.services.MessageServiceImpl;
import com.revature.trms.services.ReimbursementFormService;
import com.revature.trms.services.ReimbursementFormServiceImpl;
import com.revature.trms.services.UserServiceImpl;

public class ReimbursementFormServiceTest {
	private static ReimbursementFormService reimbursementFormService;
	private static ReimbursementFormDAO reimbursementFormDAO;
	private static UserDAO userDAO;
	private static MessageDAO messageDAO;
	
	@Before
	public void setUp() {
		reimbursementFormService = new ReimbursementFormServiceImpl();
		reimbursementFormDAO = mock(ReimbursementFormDAOImpl.class);
		reimbursementFormService.setReimbursementFormDAO(reimbursementFormDAO);
		userDAO = mock(UserDAO.class);
		reimbursementFormService.setUserDAO(userDAO);
		messageDAO = mock(MessageDAO.class);
		reimbursementFormService.setMessageDAO(messageDAO);
		
	}
	@After
	public void tearDown() {
		reimbursementFormService = null;
		reimbursementFormDAO = null;
		userDAO = null;
		messageDAO = null;
	}
	
	
	
	@Test
	public void testAddReimbursementForm() {
		ReimbursementForm rForm = new ReimbursementForm();
		
		try {
			ArgumentCaptor<ReimbursementForm> captor = ArgumentCaptor.forClass(ReimbursementForm.class);
			assertTrue("Should return true", reimbursementFormService.addReimbursementForm(rForm));
			verify(reimbursementFormDAO).addReimbursementForm(captor.capture());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testGetReimbursementFormsForSelf() {
		User user = new User();
		user.setUsername("tom444");
		List<ReimbursementForm> forms = new ArrayList<ReimbursementForm>();
		ReimbursementForm rForm = new ReimbursementForm();
		rForm.setUsername("tom444");
		forms.add(rForm);
		rForm = new ReimbursementForm();
		rForm.setUsername("tom444");
		forms.add(rForm);
		rForm = new ReimbursementForm();
		rForm.setUsername("tom444");
		forms.add(rForm);
		when(reimbursementFormDAO.getReimbursementForms()).thenReturn(forms);
		//ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		assertEquals("Should return the forms we get from the dao", forms.get(0), reimbursementFormService.getReimbursementFormsForSelf(user).get(0));
		assertEquals("Should return the forms we get from the dao", forms.get(1), reimbursementFormService.getReimbursementFormsForSelf(user).get(1));
		assertEquals("Should return the forms we get from the dao", forms.get(2), reimbursementFormService.getReimbursementFormsForSelf(user).get(2));
		
	}
	@Test
	public void testGetReimbursementFormsForProcessingEmployee() {
		User user = new User();
		user.setUsername("tom444");
		user.setType(Type.EMPLOYEE);
		List<ReimbursementForm> forms = new ArrayList<ReimbursementForm>();
		ReimbursementForm rForm = new ReimbursementForm();
		rForm.setUsername("tom444");
		forms.add(rForm);
		rForm = new ReimbursementForm();
		rForm.setUsername("tom444");
		forms.add(rForm);
		rForm = new ReimbursementForm();
		rForm.setUsername("tom444");
		forms.add(rForm);
		when(reimbursementFormDAO.getReimbursementForms()).thenReturn(forms);
		//ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
		assertEquals("Should return the forms we get from the dao", forms.get(0), reimbursementFormService.getReimbursementFormsForSelf(user).get(0));
		assertEquals("Should return the forms we get from the dao", forms.get(1), reimbursementFormService.getReimbursementFormsForSelf(user).get(1));
		assertEquals("Should return the forms we get from the dao", forms.get(2), reimbursementFormService.getReimbursementFormsForSelf(user).get(2));
	}
	@Test
	public void testGetReimbursementFormsForProcessingSupervisor() {
		User user = new User();
		user.setUsername("tom444");
		user.setType(Type.SUPERVISOR);
		List<ReimbursementForm> forms = new ArrayList<ReimbursementForm>();
		ReimbursementForm rForm = new ReimbursementForm();
		rForm.setSupervisor("tom444");
		rForm.setReimbursed(false);
		forms.add(rForm);
		rForm = new ReimbursementForm();
		rForm.setSupervisor("Bob333");
		rForm.setReimbursed(false);
		forms.add(rForm);
		when(reimbursementFormDAO.getReimbursementForms()).thenReturn(forms);
		assertEquals("Should be of size 1", 1, reimbursementFormService.getReimbursementFormsForProcessing(user).size());
		assertEquals("Should the first element should be the first element of forms", forms.get(0), reimbursementFormDAO.getReimbursementForms().get(0));
		
	}
	@Test
	public void testGetReimbursementFormsForProcessingDeptHead() {
		User user = new User();
		user.setUsername("tom444");
		user.setType(Type.DEPARTMENT_HEAD);
		user.setDepartment(Department.valueOf("HR"));
		List<ReimbursementForm> forms = new ArrayList<ReimbursementForm>();
		ReimbursementForm rForm = new ReimbursementForm();
		rForm.setDepartment(Department.valueOf("HR"));
		rForm.setReimbursed(false);
		forms.add(rForm);
		rForm = new ReimbursementForm();
		rForm.setDepartment(Department.valueOf("RANDD"));
		rForm.setReimbursed(false);
		forms.add(rForm);
		when(reimbursementFormDAO.getReimbursementForms()).thenReturn(forms);
		assertEquals("Should be of size 1", 1, reimbursementFormService.getReimbursementFormsForProcessing(user).size());
		assertEquals("Should the first element should be the first element of forms", forms.get(0), reimbursementFormDAO.getReimbursementForms().get(0));
		
	}
	@Test
	public void testGetReimbursementFormsForProcessingBenco() {
		User user = new User();
		user.setUsername("tom444");
		user.setType(Type.BENEFITS_COORDINATOR);
		List<ReimbursementForm> forms = new ArrayList<ReimbursementForm>();
		ReimbursementForm rForm = new ReimbursementForm();
		rForm.setBenCo("tom444");
		rForm.setReimbursed(false);
		forms.add(rForm);
		rForm = new ReimbursementForm();
		rForm.setBenCo("Bob333");
		rForm.setReimbursed(false);
		forms.add(rForm);
		when(reimbursementFormDAO.getReimbursementForms()).thenReturn(forms);
		assertEquals("Should be of size 1 since only 1 matches the mane of the BenCo", 1, reimbursementFormService.getReimbursementFormsForProcessing(user).size());
		assertEquals("Should the first element should be the first element of forms", forms.get(0), reimbursementFormDAO.getReimbursementForms().get(0));
	
	}
	@Test
	public void testGetReimbursementFormsForReviewSupervisor() {
		User user = new User();
		user.setUsername("tom444");
		user.setType(Type.SUPERVISOR);
		List<ReimbursementForm> forms = new ArrayList<ReimbursementForm>();
		ReimbursementForm rForm = new ReimbursementForm();
		rForm.setSupervisor("tom444");
		rForm.setReimbursed(true);
		forms.add(rForm);
		rForm = new ReimbursementForm();
		rForm.setSupervisor("tom444");
		rForm.setReimbursed(false);
		forms.add(rForm);
		when(reimbursementFormDAO.getReimbursementForms()).thenReturn(forms);
		assertEquals("Should be of size 1", 1, reimbursementFormService.getReimbursementFormsForProcessing(user).size());
		assertEquals("Should the first element should be the first element of forms", forms.get(0), reimbursementFormDAO.getReimbursementForms().get(0));
	}
	//@Test
	public void testProcessReimbursementFormSuccessSupervisor() {
		User user = new User();
		user.setUsername("tom444");
		ReimbursementForm rForm = new ReimbursementForm();
		rForm.setSupervisor("tom444");
		rForm.setSupervisorApproval(Status.PENDING);
		rForm.setDeptHeadApproval(Status.PENDING);
		rForm.setBenCoApproval(Status.PENDING);
		assertTrue("having a matching supervisor and a pending approval should return true", reimbursementFormService.processReimbursementForm(rForm, user, Status.ACCEPTED, "nothing"));
		
	}
	@Test
	public void testUpdateReimbursementFormSuccess() {
		ReimbursementForm rForm = new ReimbursementForm();
		rForm.setId(UUID.randomUUID());
		when(reimbursementFormDAO.getReimbursementForm(rForm.getId())).thenReturn(rForm);
		assertTrue("If it finds the form it should return true", reimbursementFormService.updateReimbursementForm(rForm));
	}
	@Test
	public void testUpdateReimbursementFormFail() {
		ReimbursementForm rForm = new ReimbursementForm();
		rForm.setId(UUID.randomUUID());
		when(reimbursementFormDAO.getReimbursementForm(rForm.getId())).thenReturn(null);
		assertTrue("If it does not find the form it should return false", reimbursementFormService.updateReimbursementForm(rForm));
	}
	@Test
	public void testRemoveReimbursementFormSuccess() {
		ReimbursementForm rForm = new ReimbursementForm();
		rForm.setId(UUID.randomUUID());
		when(reimbursementFormDAO.getReimbursementForm(rForm.getId())).thenReturn(rForm);
		assertTrue("If it finds the form it should return true", reimbursementFormService.removeReimbursementForm(rForm));
	}
	@Test
	public void testRemoveReimbursementFormFail() {
		ReimbursementForm rForm = new ReimbursementForm();
		rForm.setId(UUID.randomUUID());
		when(reimbursementFormDAO.getReimbursementForm(rForm.getId())).thenReturn(null);
		assertTrue("If it does not find the form it should return false", reimbursementFormService.removeReimbursementForm(rForm));
	}
	@Test
	public void testReimburseSuccessBenco() {
		ReimbursementForm rForm = new ReimbursementForm();
		User user = new User();
		user.setType(Type.BENEFITS_COORDINATOR);
		rForm.setSupervisorApproval(Status.ACCEPTED);
		rForm.setDeptHeadApproval(Status.ACCEPTED);
		rForm.setBenCoApproval(Status.ACCEPTED);
		rForm.setFormat(Format.PASSFAIL);
		rForm.setUsername("Tom");
		User user2 = new User();
		user2.setTotalReimbursement(0);
		user2.setPendingReimbursement(0);
		user2.setAwardedReimbursement(0);
		rForm.setReimbursement(0);
		rForm.setProjectedReimbursement(0);
		when(userDAO.getUser(rForm.getUsername())).thenReturn(user2);
		assertTrue(" It should return true", reimbursementFormService.reimburse(rForm, user));
	}
	@Test
	public void testReimburseSuccessDeptHead() {
		ReimbursementForm rForm = new ReimbursementForm();
		User user = new User();
		user.setType(Type.DEPARTMENT_HEAD);
		rForm.setSupervisorApproval(Status.ACCEPTED);
		rForm.setDeptHeadApproval(Status.ACCEPTED);
		rForm.setBenCoApproval(Status.ACCEPTED);
		rForm.setFormat(Format.PRESENTATION);
		User user2 = new User();
		user2.setTotalReimbursement(0);
		user2.setPendingReimbursement(0);
		user2.setAwardedReimbursement(0);
		rForm.setReimbursement(0);
		rForm.setProjectedReimbursement(0);
		when(userDAO.getUser(rForm.getUsername())).thenReturn(user2);
		assertTrue(" It should return true", reimbursementFormService.reimburse(rForm, user));
	}
	@Test
	public void testReimburseFailBenco() {
		ReimbursementForm rForm = new ReimbursementForm();
		User user = new User();
		user.setType(Type.BENEFITS_COORDINATOR);
		rForm.setSupervisorApproval(Status.ACCEPTED);
		rForm.setDeptHeadApproval(Status.ACCEPTED);
		rForm.setBenCoApproval(Status.ACCEPTED);
		rForm.setFormat(Format.PRESENTATION);
		assertFalse(" It should return false", reimbursementFormService.reimburse(rForm, user));
	}
	@Test
	public void testReimburseFailDeptHead() {
		ReimbursementForm rForm = new ReimbursementForm();
		User user = new User();
		user.setType(Type.DEPARTMENT_HEAD);
		rForm.setSupervisorApproval(Status.ACCEPTED);
		rForm.setDeptHeadApproval(Status.ACCEPTED);
		rForm.setBenCoApproval(Status.ACCEPTED);
		rForm.setFormat(Format.PASSFAIL);
		assertFalse(" It should return false", reimbursementFormService.reimburse(rForm, user));
	}
	@Test
	public void testRReimburseFailOther() {
		ReimbursementForm rForm = new ReimbursementForm();
		User user = new User();
		user.setType(Type.EMPLOYEE);
		rForm.setSupervisorApproval(Status.ACCEPTED);
		rForm.setDeptHeadApproval(Status.ACCEPTED);
		rForm.setBenCoApproval(Status.ACCEPTED);
		rForm.setFormat(Format.PASSFAIL);
		assertFalse(" It should return false", reimbursementFormService.reimburse(rForm, user));
	}
	@Test
	public void testChangeReimbursementAmountSuccess() {
		ReimbursementForm rForm = new ReimbursementForm();
		User user = new User();
		user.setType(Type.BENEFITS_COORDINATOR);
		rForm.setSupervisorApproval(Status.ACCEPTED);
		rForm.setDeptHeadApproval(Status.ACCEPTED);
		rForm.setBenCoApproval(Status.PENDING);
		rForm.setReimbursement(50.0);
		try {
			assertTrue(" It should return true since the requirements are met", reimbursementFormService.changeReimbursementAmount(user, rForm, 100.0, "test"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	@Test
	public void testChangeReimbursementAccountNotBenco() {
		ReimbursementForm rForm = new ReimbursementForm();
		User user = new User();
		user.setType(Type.SUPERVISOR);
		rForm.setSupervisorApproval(Status.ACCEPTED);
		rForm.setDeptHeadApproval(Status.ACCEPTED);
		rForm.setBenCoApproval(Status.PENDING);
		rForm.setReimbursement(50.0);
		try {
			assertFalse(" It should return false since the requirements are not met", reimbursementFormService.changeReimbursementAmount(user, rForm, 100.0, "test"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void testChangeReimbursementAmountnoDeptHeadApproval() {
		ReimbursementForm rForm = new ReimbursementForm();
		User user = new User();
		user.setType(Type.BENEFITS_COORDINATOR);
		rForm.setSupervisorApproval(Status.ACCEPTED);
		rForm.setDeptHeadApproval(Status.PENDING);
		rForm.setBenCoApproval(Status.PENDING);
		rForm.setReimbursement(50.0);
		try {
			assertFalse(" It should return false since the requirements are not met", reimbursementFormService.changeReimbursementAmount(user, rForm, 100.0, "test"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Test
	public void testChangeReimbursementAmountWithbenCoApproval() {
		ReimbursementForm rForm = new ReimbursementForm();
		User user = new User();
		user.setType(Type.BENEFITS_COORDINATOR);
		rForm.setSupervisorApproval(Status.ACCEPTED);
		rForm.setDeptHeadApproval(Status.ACCEPTED);
		rForm.setBenCoApproval(Status.ACCEPTED);
		rForm.setReimbursement(50.0);
		try {
			assertFalse(" It should return false since the requirements are not met", reimbursementFormService.changeReimbursementAmount(user, rForm, 100.0, "test"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
