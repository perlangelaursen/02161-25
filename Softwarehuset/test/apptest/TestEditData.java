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

public class TestEditData {
	private Company company;
	private Employee projectLeader;
	private Employee test1;
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
		company.createProject("Project01", start, end);
		company.createProject("Project02", start, end);
		
		//Create employee and assign as project leader
		projectLeader = company.createEmployee("ABCD", "password", "RandD");
		executive.assignProjectLeader(projectLeader,company.getSpecificProject("Project01"));
		
		test1 = company.createEmployee("EFGH", "password", "RandD");
		company.employeeLogin(projectLeader.getID(), "password");
		
		company.getSpecificProject("Project01").createActivity("Activity01", start, end, company.getSpecificProject("Project01"));
		projectLeader.assignEmployeeProject(projectLeader, company.getSpecificProject("Project01"));
		projectLeader.assignEmployeeActivity(projectLeader, company.getSpecificProject("Project01").getSpecificActivity(0));
		
		}
	@Test
	public void testEditData() throws OperationNotAllowedException {
		projectLeader.editActivityStart(company.getSpecificProject("Project01").getSpecificActivityByName("Activity01"), new GregorianCalendar(2015, Calendar.JANUARY, 25));
		projectLeader.editActivityEnd(company.getSpecificProject("Project01").getSpecificActivityByName("Activity01"), new GregorianCalendar(2015, Calendar.JANUARY, 30));
		projectLeader.editActivityDescription(company.getSpecificProject("Project01").getSpecificActivityByName("Activity01"), "Activity description");
	}
	@Test
	public void testEditDateNotAssignedToProject() {
		try {
			test1.editActivityDescription(company.getSpecificProject("Project01").getSpecificActivityByName("Activity01"), "description");
		} catch (OperationNotAllowedException e) {
			assertEquals("Unable to edit activity description, not assigned to project", e.getMessage());
			assertEquals("Edit activity description", e.getOperation());
		}
	}
	@Test
	public void testEditDataNonexistantProject() {
		try {
			projectLeader.editActivityDescription(company.getSpecificProject("Project02").getSpecificActivityByName("Activity01"), "description");
		} catch (OperationNotAllowedException e) {
			assertEquals("Unable to change activity description, project does not exist", e.getMessage());
			assertEquals("Edit activity description", e.getOperation());
		}
	}
	@Test
	public void testEditDataNonexistantActivity() {
		try {
			projectLeader.editActivityDescription(company.getSpecificProject("Project01").getSpecificActivityByName("Activity02"), "description");
		} catch (OperationNotAllowedException e) {
			assertEquals("Unable to change activity description, activity does not exist", e.getMessage());
			assertEquals("Edit activity description", e.getOperation());
		}
	}
	@Test
	public void testEditDataInvalidDate() {
		try {
			projectLeader.editActivityStart(company.getSpecificProject("Project01").getSpecificActivityByName("Activity01"), new GregorianCalendar(2015, Calendar.JANUARY, 35));
		} catch (OperationNotAllowedException e) {
			assertEquals("Unable to change activity end date, invalid date", e.getMessage());
			assertEquals("Edit activity start date", e.getOperation());
		}
	}
	@Test
	public void testEditDataInvalidDescription() {
		try {
			test1.editActivityDescription(company.getSpecificProject("Project01").getSpecificActivityByName("Activity01"), "");
		} catch (OperationNotAllowedException e) {
			assertEquals("Unable to change activity description, description cannot be blank", e.getMessage());
			assertEquals("Edit activity description", e.getOperation());
		}
	}
}
