package apptest;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import softwarehuset.*;

public class TestAssignProjectLeader {

	@Test
	public void testAssignProjectLeader() throws Exception {
		Address add = new Address("roskildevej 2", "roskilde");
		Company com = new Company("SoftwareHuset", add);
		Executive ex = new Executive("name","Department1", com, "password");
		
		GregorianCalendar d1 = new GregorianCalendar();
		GregorianCalendar d2 = new GregorianCalendar();
		
		assertFalse(com.executiveIsLoggedIn());
		com.executiveLogin("password");
		assertTrue(com.executiveIsLoggedIn());
		
		com.createProject("p1", d1, d2);
		Project p1 = com.getSpecificProject("p1");
		assertEquals(com.getProjects().size(),1);
		
		Employee em = new Employee("Anders", "password", com, "Project Department");
		
		ex.assignProjectLeader(em, p1);
		
		assertEquals(p1.getProjectLeader(), em);
		
	}
	
	@Test
	public void testNotLoggedIn() throws Exception {
		Address add = new Address("Roskildevej", "Roskilde");
		Company com = new Company("Softwarehus", add);
		Executive ex = new Executive("Anders", "Department", com, "password");
		Employee em = new Employee("Anders", "password", com, "Department");
		Project p1 = new Project("p1");
		
		GregorianCalendar d1 = new GregorianCalendar();
		GregorianCalendar d2 = new GregorianCalendar();
		
		try {
			com.createProject("p1", d1, d2);
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Create project operation is not allowed if not executive.",e.getMessage());
			assertEquals("Create project",e.getOperation());
		}
		
		assertEquals(com.getProjects().size(),0);
		
		try {
			ex.assignProjectLeader(em, p1);
			fail("OperationNotAllowedException exception should have been thrown");
		} catch(OperationNotAllowedException e) {
			assertEquals("Assign project leader is not allowed if not executive.",e.getMessage());
			assertEquals("Assign project leader",e.getOperation());
		}
		
	}
	
	@Test
	public void testEmployeeNotFound() throws Exception {
		
		Address add = new Address("roskildevej 2", "roskilde");
		Company com = new Company("SoftwareHuset", add);
		Executive ex = new Executive("name","Department1", com, "password");
		
		GregorianCalendar d1 = new GregorianCalendar();
		GregorianCalendar d2 = new GregorianCalendar();
		
		assertFalse(com.executiveIsLoggedIn());
		com.executiveLogin("password");
		assertTrue(com.executiveIsLoggedIn());
		
		com.createProject("p1", d1, d2);
		Project p1 = com.getSpecificProject("p1");
		assertEquals(com.getProjects().size(),1);
		
		Employee em = null;
		
		try {
			ex.assignProjectLeader(em, p1);
			fail("OperationNotAllowedException exception should have been thrown");
		} catch(OperationNotAllowedException e) {
			assertEquals("Employee not found",e.getMessage());
			assertEquals("Employee not found",e.getOperation());
		}
		
	}

	@Test
	public void testProjectNotFound() throws Exception {
		Address add = new Address("roskildevej 2", "roskilde");
		Company com = new Company("SoftwareHuset", add);
		
		assertEquals(com.getSpecificProject("p1"), null);
		
	}
	
	@Test
	public void testChangeProjectLeader() throws Exception {
		
		Address add = new Address("roskildevej 2", "roskilde");
		Company com = new Company("SoftwareHuset", add);
		Executive ex = new Executive("name","Department1", com, "password");
		
		GregorianCalendar d1 = new GregorianCalendar();
		GregorianCalendar d2 = new GregorianCalendar();
		
		assertFalse(com.executiveIsLoggedIn());
		com.executiveLogin("password");
		assertTrue(com.executiveIsLoggedIn());
		
		com.createProject("p1", d1, d2);
		Project p1 = com.getSpecificProject("p1");
		assertEquals(com.getProjects().size(),1);
		
		Employee em = new Employee("Anders", "password", com, "Project Department");
		
		ex.assignProjectLeader(em, p1);
		
		assertEquals(p1.getProjectLeader(), em);
		
		Employee em2 = new Employee("Anders", "password", com, "Project 2 Department");
		
		ex.assignProjectLeader(em2, p1);
		
		assertEquals(p1.getProjectLeader(), em2);
		
	}
	
}