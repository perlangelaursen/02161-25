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
		
		projectLeader = new Employee("Test", "RandD");
		
		executive.assignProjectLeader(projectLeader,company.getSpecificProject("Project01"));
		test1 = new Employee("Test2", "RandD");
		test2 = new Employee("Test3", "RandD");
		
		projectLeader.createAcivity(company.getSpecificProject("Project01"), "AO1", start, end);
		projectLeader.createAcivity(company.getSpecificProject("Project01"), "AO2", start, end);
		
		projectLeader.assignActivityLeader(projectLeader, company.getSpecificProject("Project01").getSpecificActivity(0));
		projectLeader.assignActivityLeader(projectLeader, company.getSpecificProject("Project01").getSpecificActivity(1));
		
		projectLeader.assignEmployeeActivity(test1, company.getSpecificProject("Project01").getSpecificActivity(0));
		projectLeader.assignEmployeeActivity(test1, company.getSpecificProject("Project01").getSpecificActivity(1));
	}
	
	@Test
	public void testgetStatisticsPL() throws OperationNotAllowedException {
		projectLeader.getStatisticsProject(company.getSpecificProject("Project01"));
	}
	
	@Test
	public void testgetStatisticsNotPL() {
		
	}

}
