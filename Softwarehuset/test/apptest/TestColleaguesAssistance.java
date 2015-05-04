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

public class TestColleaguesAssistance {

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
		projectLeader = company.createEmployee("ABCS", "password", "RandD");
		
		executive.assignProjectLeader(projectLeader,company.getSpecificProject("Project01"));
	}
	
	@Test
	public void testAskColleague() throws OperationNotAllowedException {
		company.employeeLogin(projectLeader.getID(), "password");
		Employee asker = company.createEmployee("HABC", "password", "RandD");
		Employee selected = company.createEmployee("SJKO", "password", "RandD");
		
		projectLeader.assignEmployeeProject(asker, company.getSpecificProject("Project01"));
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.JANUARY, 24);
		projectLeader.createActivity(company.getSpecificProject("Project01"), "A", start, end);
		projectLeader.assignEmployeeActivity(asker, company.getSpecificProject("Project01").getSpecificActivity(0));
		
		company.employeeLogin(asker.getID(), "password");
		asker.needForAssistanceWithActivity(selected, company.getSpecificProject("Project01").getSpecificActivity(0));
		assertEquals(1, company.getSpecificProject("Project01").getSpecificActivity(0).getAssistingEmployees().size());
		assertEquals(selected.getID(), company.getSpecificProject("Project01").getSpecificActivity(0).getSpecificAssitingEmployee(selected).getID());
		
	}
	
	@Test
	public void testEmployeeNotLoggedIn() throws OperationNotAllowedException {
		company.employeeLogin(projectLeader.getID(), "password");
		Employee asker = company.createEmployee("HABC", "password", "RandD");
		Employee selected = company.createEmployee("SJKO", "password", "RandD");
		
		projectLeader.assignEmployeeProject(asker, company.getSpecificProject("Project01"));
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.JANUARY, 24);
		projectLeader.createActivity(company.getSpecificProject("Project01"), "A", start, end);
		projectLeader.assignEmployeeActivity(asker, company.getSpecificProject("Project01").getSpecificActivity(0));
		
		try {
			asker.needForAssistanceWithActivity(selected, company.getSpecificProject("Project01").getSpecificActivity(0));
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("User not logged in",e.getMessage());
			assertEquals("Need For Assistance",e.getOperation());
		}
		
	}
	
	@Test
	public void testRemoveSpecificAssistingEmployee() throws OperationNotAllowedException {
		company.employeeLogin(projectLeader.getID(), "password");
		Employee asker = company.createEmployee("HABC", "password", "RandD");
		Employee selected = company.createEmployee("SJKO", "password", "RandD");
		
		projectLeader.assignEmployeeProject(asker, company.getSpecificProject("Project01"));
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.JANUARY, 24);
		projectLeader.createActivity(company.getSpecificProject("Project01"), "A", start, end);
		projectLeader.assignEmployeeActivity(asker, company.getSpecificProject("Project01").getSpecificActivity(0));
		
		company.employeeLogin(asker.getID(), "password");
		asker.needForAssistanceWithActivity(selected, company.getSpecificProject("Project01").getSpecificActivity(0));
		assertEquals(1, company.getSpecificProject("Project01").getSpecificActivity(0).getAssistingEmployees().size());
		assertEquals(selected.getID(), company.getSpecificProject("Project01").getSpecificActivity(0).getSpecificAssitingEmployee(selected).getID());
		
		company.employeeLogin(asker.getID(), "password");
		asker.removeSpecificAssistingEmployee(selected, company.getSpecificProject("Project01").getSpecificActivity(0));
		
		assertEquals(0, company.getSpecificProject("Project01").getSpecificActivity(0).getAssistingEmployees().size());
		
	}
	
	@Test
	public void testEmployeeNotLoggedInRemoveSpecificAssistingEmployee() throws OperationNotAllowedException {
		company.employeeLogin(projectLeader.getID(), "password");
		Employee asker = company.createEmployee("HABC", "password", "RandD");
		Employee selected = company.createEmployee("SJKO", "password", "RandD");
		
		projectLeader.assignEmployeeProject(asker, company.getSpecificProject("Project01"));
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.JANUARY, 24);
		projectLeader.createActivity(company.getSpecificProject("Project01"), "A", start, end);
		projectLeader.assignEmployeeActivity(asker, company.getSpecificProject("Project01").getSpecificActivity(0));
		
		try {
			asker.removeSpecificAssistingEmployee(selected, company.getSpecificProject("Project01").getSpecificActivity(0));
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("User not logged in",e.getMessage());
			assertEquals("Remove Assisting Employee from activity",e.getOperation());
		}
		
	}
}
