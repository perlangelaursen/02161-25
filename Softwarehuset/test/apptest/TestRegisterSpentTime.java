//Test by Van Anh Thi Trinh - s144449

package apptest;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import softwarehuset.*;
import static org.junit.Assert.*;

public class TestRegisterSpentTime {
	Company company;
	Employee projectLeader, employee;
	Project project;
	Activity activity;
	
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
	}

	/**
	 * Tests the scenario where an employee successfully registers his spent
	 * time on an activity
	 * <ol>
	 * <li>The employee is logged in
	 * <li>The employee registers his spent time on a chosen activity that he is assigned to
	 * </ol>
	 * 
	 */
	@Test
	public void testRegisterSpentTime() throws OperationNotAllowedException {
		company.employeeLogin("Employee1", "empassword1");
		// Add employee to project and activity
		projectLeader.assignEmployeeProject(employee, project);
		projectLeader.assignEmployeeActivity(employee, activity);

		// Register spent time
		company.employeeLogin("Employee2", "empassword2");
		employee.registerSpentTime(activity, 100);

		// See spent time
		assertEquals(100, activity.getSpentTime(employee));
	}
	
	/**
	 * Tests the scenario where an employee registers negative time for an activity
	 * <ol>
	 * <li>The employee is logged in
	 * <li>The employee registers negative time on a chosen activity that he is assigned to
	 * <li>An exception is thrown
	 * </ol>
	 * 
	 */
	@Test
	public void testRegisterNegativeTime() throws OperationNotAllowedException {
		company.employeeLogin("Employee1", "empassword1");
		// Add employee to project and activity
		projectLeader.assignEmployeeProject(employee, project);
		projectLeader.assignEmployeeActivity(employee, activity);

		// Try to register spent time
		company.employeeLogin("Employee2", "empassword2");
		try {
			employee.registerSpentTime(activity, -1);
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Invalid time", e.getMessage());
			assertEquals("Register spent time", e.getOperation());
		}

		// See spent time
		assertEquals(0, activity.getSpentTime(employee));
	}

	/**
	 * Tests the scenario where an employee tries to register time without being signed in
	 * <ol>
	 * <li>The employee is not logged in
	 * <li>The employee tries to register spent time on a chosen activity
	 * <li>An exception is thrown
	 * </ol>
	 * 
	 */
	@Test
	public void testRegisterWithoutLoggingIn() throws Exception {
		try {
			employee.registerSpentTime(activity, 100);
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Employee is not logged in", e.getMessage());
			assertEquals("Register spent time", e.getOperation());
		}
	}

	/**
	 * Tests the scenario where an employee tries to register time for an activity that he is not assigned to
	 * <ol>
	 * <li>The employee is logged in
	 * <li>The employee registers his spent time on a chosen activity that he is not assigned to
	 * <li>An exception is thrown
	 * </ol>
	 * 
	 */
	@Test
	public void testRegisterTimeforWrongActivity() throws Exception {
		company.employeeLogin("Employee2", "empassword2");
		
		try {
			employee.registerSpentTime(activity, 100);
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Employee is not assigned to the chosen activity", e.getMessage());
			assertEquals("Register spent time", e.getOperation());
		}
	}
}