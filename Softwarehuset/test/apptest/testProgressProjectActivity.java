package apptest;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.Before;
import org.junit.Test;

import softwarehuset.*;

	//Mathias
public class testProgressProjectActivity {
	Employee projectLeader;
	Employee test1;
	Company company;
	Project p1, p2;
	
	@Before
	public void setUp() throws OperationNotAllowedException {
		// Create company and executive
		Address address = new Address("City", "Street");
		company = new Company("Company", address);
		Executive executive = new Executive("Name", "Department1", company, "password");
		company.setExecutive(executive);

		// Log in as executive
		company.executiveLogin("password");
		
		//Set date	
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.FEBRUARY, 23);
		
		//Create projects
		p1 = company.createProject("Project01", start, end);
		p2 = company.createProject("Project02", start, end);
		
		//Create employee and assign as project leader
		projectLeader = company.createEmployee("HALO", "password", "RandD");
		executive.assignProjectLeader(projectLeader,company.getSpecificProject("Project01"));
		
		test1 = company.createEmployee("MUHA", "password", "RandD");
		company.employeeLogin(projectLeader.getID(), "password");
		
		
		company.getSpecificProject("Project01").createActivity("Activity01", start, end, company.getSpecificProject("Project01"));
		projectLeader.assignEmployeeProject(projectLeader, company.getSpecificProject("Project01"));

		projectLeader.assignEmployeeActivity(projectLeader, p1.getActivity(p1.getID()+"-Activity01"));
		projectLeader.registerSpentTime(p1.getActivity(p1.getID()+"-Activity01"), 100);
		}
	
	
	@Test
	public void testProgressActivity() throws OperationNotAllowedException {
		
		assertEquals(100, projectLeader.viewProgress("Project01", p1.getID()+"-Activity01"));
	}
	@Test
	public void testProgressProject() throws OperationNotAllowedException {
		assertEquals(100, projectLeader.viewProgress("Project01"));
	}
	
	@Test
	public void testProgressNotLoggedIn() throws OperationNotAllowedException {
		company.employeeLogout();
		
		try{
			projectLeader.viewProgress("Project01", "Activity01");
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e){
			assertEquals("Operation is not allowed if not project leader",e.getMessage());
			assertEquals("Project leader operation",e.getOperation());
		}
		try{
			projectLeader.viewProgress("Project01");
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e){
			assertEquals("Operation is not allowed if not project leader",e.getMessage());
			assertEquals("Project leader operation",e.getOperation());
		}
	}
	
	@Test
	public void testProgressNotProjectLeader() throws OperationNotAllowedException {
		Employee em = company.createEmployee("JLBD", "password", "Department");
		company.employeeLogin("JLBD", "password");
		
		try{
			em.viewProgress("Project01", "Activity01");
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e){
			assertEquals("Operation is not allowed if not project leader",e.getMessage());
			assertEquals("Project leader operation",e.getOperation());
		}
		
		try{
			em.viewProgress("Project01");
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e){
			assertEquals("Operation is not allowed if not project leader",e.getMessage());
			assertEquals("Project leader operation",e.getOperation());
		}
	}
}

