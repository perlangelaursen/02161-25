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
		projectLeader.assignEmployeeActivity(projectLeader, company.getSpecificProject("Project01").getSpecificActivity(0));
		projectLeader.registerSpentTime(company.getSpecificProject("Project01").getSpecificActivity(0), 100);
		}
	
	
	@Test
	public void testProgressActivity() throws OperationNotAllowedException {
		Activity a = p1.getActivities().get(0);
		projectLeader.viewProgress(p1, a);
		assertEquals(100, projectLeader.viewProgress(p1, a));
	}
	@Test
	public void testProgressProject() throws OperationNotAllowedException {
		assertEquals(100, projectLeader.viewProgress(p1));
	}
	
	@Test
	public void testProgressNotLoggedIn() throws OperationNotAllowedException {
		Activity a = p1.getActivities().get(0);
		company.employeeLogout();
		try{
			projectLeader.viewProgress(p1, a);
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e){
			assertEquals("View progress is not allowed if not logged in", e.getMessage());
			assertEquals("View progress", e.getOperation());
		}
		try{
			projectLeader.viewProgress(p1);
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e){
			assertEquals("View progress is not allowed if not logged in", e.getMessage());
			assertEquals("View progress", e.getOperation());
		}
	}
	
	@Test
	public void testProgressNotProjectLeader() throws OperationNotAllowedException {
		Activity a = p1.getActivities().get(0);
		Employee em = company.createEmployee("JLBD", "password", "Department");
		company.employeeLogin("JLBD", "password");
		try{
			em.viewProgress(p1, a);
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e){
			assertEquals("View progress is not allowed if not project leader", e.getMessage());
			assertEquals("View progress", e.getOperation());
		}
		
		try{
			em.viewProgress(p1);
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e){
			assertEquals("View progress is not allowed if not project leader", e.getMessage());
			assertEquals("View progress", e.getOperation());
		}
	}
}

