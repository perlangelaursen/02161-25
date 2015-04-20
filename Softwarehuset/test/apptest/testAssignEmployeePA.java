package apptest;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import softwarehuset.Address;
import softwarehuset.Company;
import softwarehuset.Employee;
import softwarehuset.Executive;
import softwarehuset.OperationNotAllowedException;

/*
* Test created by Per Lange Laursen - s144486 DTU
*/

public class testAssignEmployeePA {
	/**
	 * Tests the scenario where a project or activity is created
	 * then a projectleader or activityleader is assigned.
	 * <ol>
	 * <li>Projectleader is assigned and a employee is added to a project
	 * <li>Activityleader is assigned and a employee is added to a activity
	 * </ol>
	 * @throws OperationNotAllowedException 
	 */
	
	Employee projectLeader;
	Employee test1;
	Company company;
	
	@Before
	public void setUp() throws OperationNotAllowedException {
		// Create company and executive
		Address address = new Address("City", "Street");
		company = new Company("Company", address);
		Executive executive = new Executive("Name", "Department1", company, "password");
		
		// Log in as executive
		assertFalse(company.executiveIsLoggedIn());
		company.executiveLogin("password");
		assertTrue(company.executiveIsLoggedIn());
		
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.FEBRUARY, 23);
		company.createProject("Project01", start, end);
		company.createProject("Project02");
		
		projectLeader = new Employee("Test", "RandD");
		
		executive.assignProjectLeader(projectLeader,company.getSpecificProject("Project01"));
		test1 = new Employee("Test2", "RandD");
	}
	
	@Test
	public void testAssignEmployeeProject() throws OperationNotAllowedException {
		projectLeader.assignEmployeeProject(test1, company.getSpecificProject("Project01"));
		assertEquals(company.getSpecificProject("Project01").getEmployee("Test2").getName(), test1.getName());
		assertEquals(company.getSpecificProject("Project01").getEmployee("Test2").getDepartment(), test1.getDepartment());
	}
	
	@Test
	public void testNotProjectLeader() throws OperationNotAllowedException {
		Employee test2 = new Employee("Test2", "RandD");
		try {
			test2.assignEmployeeProject(test1, company.getSpecificProject("Project01"));
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Assign Employee is not allowed if not project leader.",e.getMessage());
			assertEquals("Assign Employee",e.getOperation());
		}
	}
	
	@Test
	public void testAssignEmployeeActivity() throws OperationNotAllowedException {
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.JANUARY, 25);
		
		projectLeader.createAcivity(company.getSpecificProject("Project01"), "TestActivity", start, end);
		
		Employee activityLeader=new Employee("AL", "RandD");
		projectLeader.assignActivityLeader(activityLeader, company.getSpecificProject("Project01").getSpecificActivity(0));
		
		activityLeader.assignEmployeeActivity(test1, company.getSpecificProject("Project01").getSpecificActivity(0));
	}
	
	@Test
	public void testNotActivityLeader() throws OperationNotAllowedException {
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.JANUARY, 25);
		
		projectLeader.createAcivity(company.getSpecificProject("Project01"), "TestActivity", start, end);
		
		Employee test2 = new Employee("Test2", "RandD");
		try {
			test2.assignEmployeeActivity(test1, company.getSpecificProject("Project01").getSpecificActivity(0));
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Assign Employee is not allowed if not activity leader.",e.getMessage());
			assertEquals("Assign Employee",e.getOperation());
		}
	}
}
