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

public class TestPlanActivity {
	/**
	 * Tests the scenario where the project leader creates a activity
	 * with start and end date.
	 * <ol>
	 * <li>Project Leader creates a activity with two different dates or same date
	 * <li>Project Leader creates a activity with an end date before start date.
	 * </ol>
	 * @throws OperationNotAllowedException 
	 */
	public Company company;
	public Employee projectLeader;
	
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
		projectLeader = company.createEmployee("Test", "password", "RandD");
		
		executive.assignProjectLeader(projectLeader,company.getSpecificProject("Project01"));
	}
	
	@Test
	public void testPlanActivityRightDates() throws OperationNotAllowedException {
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.JANUARY, 25);
		
		company.employeeLogin(projectLeader.getID(), "password");
		projectLeader.createActivity(company.getSpecificProject("Project01"), "TestActivity", start, end);
		
		assertEquals(1, company.getSpecificProject("Project01").getActivities().size());
		assertEquals("TestActivity", company.getSpecificProject("Project01").getSpecificActivity(0).getName());
		assertEquals(start, company.getSpecificProject("Project01").getSpecificActivity(0).getStart());
		assertEquals(end, company.getSpecificProject("Project01").getSpecificActivity(0).getEnd());
	}
	
	@Test
	public void testPlanActivityWrongDates() throws OperationNotAllowedException {
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.JANUARY, 22);
		
		company.employeeLogin(projectLeader.getID(), "password");
		try {
			projectLeader.createActivity(company.getSpecificProject("Project01"), "TestActivity", start, end);
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Incorrect order of dates.",e.getMessage());
			assertEquals("Create activity",e.getOperation());
		}
		
		assertEquals(0, company.getSpecificProject("Project01").getActivities().size());
	}
	
	@Test
	public void testNotProjectLeader() throws OperationNotAllowedException {
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.JANUARY, 25);
		
		Employee test2 = new Employee("Test2", "password", company, "RandD");
		try {
			test2.createActivity(company.getSpecificProject("Project01"), "TestActivity", start, end);
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			// TODO: handle exception
			assertEquals("Project leader must be logged in to create an activity",e.getMessage());
			assertEquals("Create activity",e.getOperation());
		}
	}
}
