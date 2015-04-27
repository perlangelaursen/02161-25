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
import softwarehuset.Project;

public class testAvailableEmployees {
	private Address add;
	private Company com;
	private Executive ex;
	private GregorianCalendar d1, d2, d3, d4, d5, d6;
	private Employee em, em2;
	private Project p1;
	
	@Before
	public void setUp() throws Exception {
		add = new Address("roskildevej 2", "roskilde");
		com = new Company("SoftwareHuset", add);
		ex = new Executive("name","Department1", com, "password");
		em = new Employee("Anders", "password", com, "Project Department");
		em2 = new Employee("Andreas", "password", com, "Project Department");
		p1 = new Project("p1");
		
		d1 = new GregorianCalendar();
		d2 = new GregorianCalendar();
		d3 = new GregorianCalendar();
		d4 = new GregorianCalendar();
		d5 = new GregorianCalendar();
		d6 = new GregorianCalendar();
	}
	
	@Test
	public void testAvaiableEmployees() throws Exception {
		assertFalse(com.executiveIsLoggedIn());
		com.executiveLogin(ex.getPassword());
		assertTrue(com.executiveIsLoggedIn());
		ex.assignProjectLeader(em, p1);
		assertEquals(p1.getProjectLeader(), em);
		
		d1.set(2000, 3, 1);
		d2.set(2000, 4, 1);
		d3.set(2000, 5, 1);
		d4.set(2000, 6, 1);
		
		em.createActivity(p1, "activiy1", d1, d2);
		em.createActivity(p1, "activity2", d3, d4);
		
		em.assignEmployeeActivity(em2, act1);
		em.assignEmployeeActivity(em2, act2);
		
		d5.set(2000, 1, 1);
		d6.set(2000, 2, 1);
		
		assertEquals(com.getAvailableEmployees(d5, d6).get(0),em2);
		
		
		
	}
	
	@Test
	public void testAvaiableEmployeesNoProject() throws Exception {
		
	}
	
	@Test
	public void testAvaiableEmployeesWithProject() throws Exception {
		
	}

}
