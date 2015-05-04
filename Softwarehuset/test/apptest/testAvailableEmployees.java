package apptest;

import static org.junit.Assert.*;

import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import softwarehuset.Activity;
import softwarehuset.Address;
import softwarehuset.Company;
import softwarehuset.Employee;
import softwarehuset.Executive;
import softwarehuset.OperationNotAllowedException;
import softwarehuset.Project;

public class testAvailableEmployees {
	private Address add;
	private Company com;
	private Executive ex;
	private GregorianCalendar d1, d2, d3, d4, d5, d6, d7, d8;
	private Employee em, em2, em3, em4;
	private Project p1;
	
	@Before
	public void setUp() throws Exception {
		add = new Address("roskildevej 2", "roskilde");
		com = new Company("SoftwareHuset", add);
		ex = new Executive("name","Department1", com, "password");
		com.setExecutive(ex);
		
		em = com.createEmployee("Anders", "password", "Project Department");
		em2 = com.createEmployee("Andreas", "password", "Project Department");
		em3 = com.createEmployee("Allan", "password", "Project Department");
		em4 = com.createEmployee("Adam", "password", "Project Department");
		
		d1 = new GregorianCalendar();
		d2 = new GregorianCalendar();
		d3 = new GregorianCalendar();
		d4 = new GregorianCalendar();
		d5 = new GregorianCalendar();
		d6 = new GregorianCalendar();
		d7 = new GregorianCalendar();
		d8 = new GregorianCalendar();
		
		com.executiveLogin(ex.getPassword());
		p1 = com.createProject("p1");

		com.employeeLogin(em.getID(),em.getPassword());
		
	}
	
	@Test
	public void testAvaiableEmployeesOnePerson() throws Exception {
		
		ex.assignProjectLeader(em, p1);
		assertEquals(p1.getProjectLeader(), em);
		
		d1.set(2000, 3, 1);
		d2.set(2000, 4, 1);
		d3.set(2000, 5, 1);
		d4.set(2000, 6, 1);
		
		em.createActivity(p1, "activity1", d1, d2);
		em.createActivity(p1, "activity2", d3, d4);
		em.assignEmployeeActivity(em2, p1.getSpecificActivityByName("activity1"));
		em.assignEmployeeActivity(em2, p1.getSpecificActivityByName("activity2"));
		
		d5.set(2000, 1, 1);
		d6.set(2000, 2, 1);
		
		assertTrue(com.getAvailableEmployees(d5, d6).contains(em2));
		
		
	}
	
	@Test
	public void testAvailableEmployeesThreePersons() throws Exception {
		ex.assignProjectLeader(em, p1);
		assertEquals(p1.getProjectLeader(), em);
		
		d1.set(2000, 3, 1);
		d2.set(2000, 4, 1);
		d3.set(2000, 5, 1);
		d4.set(2000, 6, 1);
		em.createActivity(p1, "activity1", d1, d2);
		em.createActivity(p1, "activity2", d3, d4);
		em.assignEmployeeActivity(em2, p1.getSpecificActivityByName("activity1"));
		em.assignEmployeeActivity(em3, p1.getSpecificActivityByName("activity2"));
		em.assignEmployeeActivity(em4, p1.getSpecificActivityByName("activity2"));
		
		d5.set(2000, 1, 1);
		d6.set(2000, 2, 1);
		
		assertTrue(com.getAvailableEmployees(d5, d6).contains(em2));
		assertTrue(com.getAvailableEmployees(d5, d7).contains(em2));
		assertTrue(com.getAvailableEmployees(d5, d6).contains(em4));
	}
	
	@Test
	public void testAvailableEmployeesOverlap() throws Exception {
		ex.assignProjectLeader(em, p1);
		assertEquals(p1.getProjectLeader(), em);
		
		d1.set(2000, 3, 1);
		d2.set(2000, 4, 1);
		d3.set(2000, 5, 1);
		d4.set(2000, 6, 1);
		
		//em.createActivity(p1, "activity1", d1, d2);
		em.createActivity(p1, "activity2", d3, d4);
		em.assignEmployeeActivity(em2, p1.getSpecificActivityByName("activity1"));
		assertTrue(em2.getActivities().contains(p1.getSpecificActivityByName("activity1")));
//		em.assignEmployeeActivity(em2, p1.getSpecificActivityByName("activity2"));
//		assertTrue(em2.getActivities().contains(p1.getSpecificActivityByName("activity2")));
//		
		d5.set(2000, 5, 5);
		d6.set(2000, 5, 20);
		
		assertFalse(com.getAvailableEmployees(d5, d6).contains(em2));
		
	}

}
