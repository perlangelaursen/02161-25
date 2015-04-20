//Test by Van Anh Thi Trinh - s144449
package apptest;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;

import softwarehuset.*;
import static org.junit.Assert.*;

public class TestCreateProject {
	/**
	 * Tests the scenario where the executive successfully creates a project
	 * with start and end date and a project with only a name
	 * <ol>
	 * <li>The executive is logged in
	 * <li>The executive creates two projects
	 * </ol>
	 * @throws OperationNotAllowedException 
	 */
	@Test
	public void testNewProjectSuccess() throws OperationNotAllowedException {
		// Create company and executive
		Address address = new Address("City", "Street");
		Company company = new Company("Company", address);
		Executive executive = new Executive("Name", "Department1", company, "password");

		// Log in as executive
		assertFalse(company.executiveIsLoggedIn());
		company.executiveLogin("password");
		assertTrue(company.executiveIsLoggedIn());

		// Create a project
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.FEBRUARY, 23);
		company.createProject("Project01", start, end);
		company.createProject("Project02");

		// See projects
		List<Project> projects = (List<Project>) company.getProjects();
		int numberOfProjects = projects.size();
		assertEquals(2, numberOfProjects);
	}

	/**
	 * Tests the scenario where the executive is not logged in and tries to
	 * create projects
	 * <ol>
	 * <li>The executive is not logged in
	 * <li>The executive tries to create projects
	 * <li>An exception is thrown
	 * </ol>
	 */
	@Test
	public void testNewProjectLoggedOut() throws Exception {
		// Create company and executive
		Address address = new Address("City", "Street");
		Company company = new Company("Company", address);
		Executive ex = new Executive("Name", "Department1", company, "password");

		// Executive is not logged in
		company.executiveLogin("wrongPassword");
		assertFalse(company.executiveIsLoggedIn());

		// Try to create project with given dates		
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.FEBRUARY, 23);
		
		try {
			company.createProject("Project01", start, end);
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Create project operation is not allowed if not executive.",e.getMessage());
			assertEquals("Create project",e.getOperation());
		}
		
		//Try to create project without given dates
		try {
			company.createProject("Project02");
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Create project operation is not allowed if not executive.",e.getMessage());
			assertEquals("Create project",e.getOperation());
		}
		
		// See projects
		List<Project> projects = (List<Project>) company.getProjects();
		int numberOfProjects = projects.size();
		assertEquals(0, numberOfProjects);
	}
	
	/**
	 * Tests the scenario where the end date comes before the start date 
	 * create a project
	 * <ol>
	 * <li>The executive is logged in
	 * <li>The executive tries to create a project with wrong date order
	 * <li>An exception is thrown
	 * </ol>
	 */
	@Test
	public void testNewProjectDateOrder() throws Exception {
		// Create company and executive
		Address address = new Address("City", "Street");
		Company company = new Company("Company", address);
		Executive executive = new Executive("Name", "Department1", company, "password");

		// Log in as executive
		company.executiveLogin("password");

		// Create a project
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.FEBRUARY, 23);
		end.set(2015, Calendar.JANUARY, 23);

		// Try to create a project
		try {
			company.createProject("Project01", start, end);
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("The end date is set before the start date",e.getMessage());
			assertEquals("Create project",e.getOperation());
		}

		// See projects
		List<Project> projects = (List<Project>) company.getProjects();
		int numberOfProjects = projects.size();
		assertEquals(0, numberOfProjects);
	}
}
