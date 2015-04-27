//Test by Van Anh Thi Trinh - s144449
package apptest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.*;
import org.junit.*;
import softwarehuset.*;
public class TestSeeRegisteredSpentTime {
	Company company;
	Employee projectLeader, employee;
	Project project;
	Activity activity, activity2, activity3;
	
	@Before
	public void setUp() throws OperationNotAllowedException {
		// Create company executive, project leader for a project and employee
		// assigned to the project
		Address address = new Address("City", "Street");
		company = new Company("Softwarehuset", address);
		Executive executive = new Executive("Executive", "Department1",	company, "password");
		projectLeader = company.createEmployee("Employee1", "empassword1", "Department1");
		employee = company.createEmployee("Employee2", "empassword2", "Department1");

		// Create project and assign project leader
		company.executiveLogin("password");
		project = company.createProject("Project01");
		project.assignProjectLeader(projectLeader);

		// Create activity
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(2015, Calendar.JANUARY, 23);
		end.set(2015, Calendar.FEBRUARY, 23);
		activity = project.createActivity("Designing", start, end,	project);
		activity2 = project.createActivity("Refactoring", start, end,	project);
		activity3 = project.createActivity("Programming", start, end,	project);
		
		company.employeeLogin("Employee1", "empassword1");
		
		// Add employee to project and activity
		projectLeader.assignEmployeeProject(employee, project);
		projectLeader.assignEmployeeActivity(employee, activity);
		projectLeader.assignEmployeeActivity(employee, activity2);
		projectLeader.assignEmployeeActivity(employee, activity3);

		// Register spent time
		company.employeeLogin("Employee2", "empassword2");
		employee.registerSpentTime(activity, 100);
		employee.registerSpentTime(activity2, 50);
		
		//Log out
		company.employeeLogout();
	}

	/**
	 * Tests the scenario where a logged in employee successfully sees spent time on an activity
	 * <ol>
	 * <li>The employee is logged in
	 * <li>The employee see registered time on two activities
	 * </ol>
	 * 
	 */
	@Test
	public void testSeeRegisteredSpentTime() throws OperationNotAllowedException {
		//Login
		company.employeeLogin("Employee2", "empassword2");

		// See spent time
		assertEquals(100, employee.getSpentTime("Project01-Designing"));
		assertEquals(50, employee.getSpentTime("Project01-Refactoring"));
		assertEquals(0, employee.getSpentTime("Project01-Programming"));
	}
	
	/**
	 * Tests the scenario where an employee tries to see spent time on an activity he is not assigned to
	 * <ol>
	 * <li>The employee is logged in
	 * <li>The employee tries to get spent time on an activity he is not assigned to
	 * <li>An exception is thrown
	 * </ol>
	 * 
	 */
	@Test
	public void testSeeRegisteredSpentTimeOnWrongActivity() throws OperationNotAllowedException {
		//Login
		company.employeeLogin("Employee2", "empassword2");

		try {
			assertEquals(100, employee.getSpentTime("Project05-Designing"));
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Employee is not assigned to the activity", e.getMessage());
			assertEquals("See registered spent time", e.getOperation());
		}
	}
}
