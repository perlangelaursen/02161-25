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

public class TestCreateActivity {

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
		
		projectLeader = new Employee("Test", "password", company, "RanD");
		
		executive.assignProjectLeader(projectLeader,company.getSpecificProject("Project01"));
		test1 = new Employee("Test2", "password", company, "RanD");
	}
	
	@Test
	public void testCreateActivity01() throws OperationNotAllowedException {
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.JANUARY, 25);
		
		assertEquals(0, company.getSpecificProject("Project01").getActivities().size());
		
		projectLeader.createAcivity(company.getSpecificProject("Project01"), "TestActivity", start, end);
		
		assertEquals(1, company.getSpecificProject("Project01").getActivities().size());
	}

	@Test
	public void testCreateActivity02() throws OperationNotAllowedException {
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.JANUARY, 25);
		
		assertEquals(0, company.getSpecificProject("Project01").getActivities().size());
		
		Employee test2 = new Employee("Test2", "password", company, "RanD");
		
		try {
			test2.createAcivity(company.getSpecificProject("Project01"), "TestActivity", start, end);
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Create activity is not allowed if not project leader.",e.getMessage());
			assertEquals("Create activity",e.getOperation());
		}
	}
}
