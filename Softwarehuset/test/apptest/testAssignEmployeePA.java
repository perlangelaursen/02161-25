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
import softwarehuset.Project;

public class testAssignEmployeePA {
	Project project1;
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
		
		executive.assignProjectLeader(projectLeader,company.getSpecificProject(0));
		test1 = new Employee("Test2", "RandD");
	}
	
	@Test
	public void testAssignEmployeeProject() {
		String ret = projectLeader.assignEmployee(test1, company.getSpecificProject(0));
		assertEquals("Employee assigned",ret);
	}
	
	@Test
	public void testAssignEmployeeActivity() {
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.JANUARY, 25);
	}
	
	@Test
	public void testExecutiveNotLogin() {
		
	}
	
	@Test
	public void testEmployeeNotAvailable() {
		
	}
}
