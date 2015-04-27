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
	//Mathias
public class testProgressProjectActivity {
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
		company.executiveLogin("password");
		
		//Set date
		
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.FEBRUARY, 23);
		//Create projects
		company.createProject("Project01", start, end);
		company.createProject("Project02", start, end);
		//Create employee and assign as project leader
		projectLeader = company.createEmployee("Test", "password", "RandD");
		executive.assignProjectLeader(projectLeader,company.getSpecificProject("Project01"));
		test1 = company.createEmployee("Test2", "password", "RandD");
		company.employeeLogin(projectLeader.getID(), "password");
		projectLeader.assignEmployeeProject(test1, company.getSpecificProject("Project01"));
		company.getSpecificProject("Project01").createActivity("Activity01", start, end, company.getSpecificProject("Project01"));
		projectLeader.registerSpentTime(company.getSpecificProject("Project01").getSpecificActivity(0), 100);
	}
	@Test
	public void testProgressActivity() throws OperationNotAllowedException {
		assertEquals(100, projectLeader.viewProgress(company.getSpecificProject("Project01"), company.getSpecificProject("Project01").getSpecificActivity(0)));
	}
	@Test
	public void testProgressProject() throws OperationNotAllowedException {
		assertEquals(100, projectLeader.viewProgress(company.getSpecificProject("Project01")));
	}
	
	@Test
	public void testProgressNonexistantActivity() throws OperationNotAllowedException {
		try{
			projectLeader.viewProgress(company.getSpecificProject("Project01"), company.getSpecificProject("Project01").getSpecificActivity(1));
			fail("OperationNotAllowedException expected");
	
		} catch (OperationNotAllowedException e){
			assertEquals("Unable to view progress, nonexistant activity", e.getMessage());
			assertEquals("Choose another activity", e.getOperation());
		}
	}
	@Test
	public void testProgressNonexistantProject() throws OperationNotAllowedException {
		try{
			projectLeader.viewProgress(company.getSpecificProject("ProjectXX"));
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e){
			assertEquals("Unable to view progress, nonexistant project", e.getMessage());
			assertEquals("Choose another project", e.getOperation());
		}
	}
	@Test
	public void testProgressProjectNotAssigned() throws OperationNotAllowedException {
		try{
			projectLeader.viewProgress(company.getSpecificProject("Project02"));
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e){
			assertEquals("Project Leader is not assigned to the chosen project", e.getMessage());
			assertEquals("Choose another project", e.getOperation());
		}
	}
	@Test
	public void testProgressActivityNotAssigned() throws OperationNotAllowedException {
		try{
			projectLeader.viewProgress(company.getSpecificProject("Project02"), company.getSpecificProject("Project02").getSpecificActivity(0));
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e){
			assertEquals("Project Leader is not assigned to the chosen project", e.getMessage());
			assertEquals("Choose another project", e.getOperation());
		}
	}
}
