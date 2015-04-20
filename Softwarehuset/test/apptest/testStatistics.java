package apptest;

import static org.junit.Assert.*;

import java.util.List;
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

public class testStatistics {
	Employee projectLeader;
	Employee test1, test2;
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
		
		projectLeader = new Employee("Test", "password", company, "RandD");
		
		executive.assignProjectLeader(projectLeader,company.getSpecificProject("Project01"));
		test1 = new Employee("Test2", "password", company, "RandD");
		test2 = new Employee("Test3", "password", company, "RandD");
		
		company.employeeLogin(projectLeader.getID(), "password");
		
		projectLeader.assignEmployeeProject(test1, company.getSpecificProject("Project01"));
		projectLeader.assignEmployeeProject(test2, company.getSpecificProject("Project01"));
		
		projectLeader.createActivity(company.getSpecificProject("Project01"), "AO1", start, end);
		projectLeader.createActivity(company.getSpecificProject("Project01"), "AO2", start, end);
		
		projectLeader.assignActivityLeader(projectLeader, company.getSpecificProject("Project01").getSpecificActivity(0));
		projectLeader.assignActivityLeader(projectLeader, company.getSpecificProject("Project01").getSpecificActivity(1));
		
		projectLeader.assignEmployeeActivity(test1, company.getSpecificProject("Project01").getSpecificActivity(0));
		projectLeader.assignEmployeeActivity(test2, company.getSpecificProject("Project01").getSpecificActivity(1));
	}
	
	@Test
	public void testgetStatisticsPL() throws OperationNotAllowedException {
		List<String> statistics = projectLeader.getStatisticsProject(company.getSpecificProject("Project01"));
		assertEquals("Project Name: " + company.getSpecificProject("Project01").getName(), statistics.get(0));
		assertEquals("No. of employees assigned: " + company.getSpecificProject("Project01").getEmployees().size(), statistics.get(1));
		assertEquals("ID: " + test1.getID() + "Department: " + test1.getDepartment(), statistics.get(2));
		assertEquals("ID: " + test2.getID() + "Department: " + test2.getDepartment(), statistics.get(3));
		assertEquals("No. of activities: " + company.getSpecificProject("Project01").getActivities().size(), statistics.get(4));
		assertEquals("Activity name: " + company.getSpecificProject("Project01").getSpecificActivity(0).getName() +
				"No. of employees: " + company.getSpecificProject("Project01").getSpecificActivity(0).getEmployees().size(),
				statistics.get(5));
		assertEquals("Activity Leader: " + company.getSpecificProject("Project01").getSpecificActivity(0).getContactPerson().getID()
				+ company.getSpecificProject("Project01").getSpecificActivity(0).getContactPerson().getDepartment(),
				statistics.get(6));
		assertEquals("ID: " + test1.getID() + "Department: " + test1.getDepartment(), statistics.get(7));
		assertEquals("Activity name: " + company.getSpecificProject("Project01").getSpecificActivity(1).getName() +
				"No. of employees: " + company.getSpecificProject("Project01").getSpecificActivity(1).getEmployees().size(),
				statistics.get(8));
		assertEquals("Contact Person: " + company.getSpecificProject("Project01").getSpecificActivity(1).getContactPerson().getID()
				+ company.getSpecificProject("Project01").getSpecificActivity(1).getContactPerson().getDepartment(),
				statistics.get(9));
		assertEquals("ID: " + test2.getID() + "Department: " + test2.getDepartment(), statistics.get(10));
	}
	
	@Test
	public void testgetStatisticsNotPL() throws OperationNotAllowedException {
		Employee test3 = new Employee("Test4", "password", company, "RandD");
		company.employeeLogin(test3.getID(), "password");
		try {
			test3.getStatisticsProject(company.getSpecificProject("Project01"));
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Get statistics is not allowed if not project leader.",e.getMessage());
			assertEquals("Get statistics",e.getOperation());
		}
	}

}
