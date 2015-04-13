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

public class testRelieveEmployee {
	/**
         * Tests the scenario where the project leader want to relieve
         * an employee from a project
         * <ol>
         * <li>Project Leader relieves an employee
         * </ol>
         * @throws OperationNotAllowedException
         */


	public Company company;
	public Employee projectLeader, test1;
	
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
				
		executive.assignProjectLeader(projectLeader,company.getSpecificProject(0));
		test1 = new Employee("Test2", "RandD");
		projectLeader.assignEmployeeProject(test1, company.getSpecificProject(0));
	}
	
	@Test
	public void testRelieveEmployee01() throws OperationNotAllowedException {
		assertEquals(1, company.getSpecificProject(0).getEmployees().size());
		
		projectLeader.relieveEmployeeProject(test1, company.getSpecificProject(0));
		
		assertEquals(0, company.getSpecificProject(0).getEmployees().size());
	}
	
	@Test
	public void testNotProjectLeader() {
		assertEquals(1, company.getSpecificProject(0).getEmployees().size());
		Employee test2 = new Employee("Test2", "RandD");
		try {
			test2.relieveEmployeeProject(test1, company.getSpecificProject(0));
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Relieve Employee if not projectleader",e.getMessage());
			assertEquals("Not ProjectLeader",e.getOperation());
		}
	}

}
