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

public class TestMakeReport14 {
	Employee projectLeader;
	Employee employee;
	Company company;
	@Before
	public void setup() throws OperationNotAllowedException {
		// Create company and executive
		Address address = new Address("City", "Street");
		company = new Company("Company", address);
		Executive executive = new Executive("Name", "Department1", company, "password");
		company.setExecutive(executive);
		// Log in as executive
		company.executiveLogin("password");	
		
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
		employee = company.createEmployee("Tess", "password", "RandD");
		company.employeeLogin(projectLeader.getID(), "password");
		company.getSpecificProject("Project01").createActivity("Activity01", start, end, company.getSpecificProject("Project01"));
		projectLeader.assignEmployeeProject(projectLeader, company.getSpecificProject("Project01"));
		projectLeader.assignEmployeeActivity(projectLeader, company.getSpecificProject("Project01").getSpecificActivity(0));
		projectLeader.registerSpentTime(company.getSpecificProject("Project01").getSpecificActivity(0), 100);
		projectLeader.writeReport(company.getSpecificProject("Project01"), "Changes to Project", new GregorianCalendar(2015, Calendar.JANUARY, 23));
	}
	@Test
	public void testWriteReport() throws OperationNotAllowedException {
		assertEquals("Changes to Project", company.getSpecificProject("Project01").getSpecificReportByName("Changes to Project").getName());
		assertEquals("Changes to Project", company.getSpecificProject("Project01").getSpecificReport(0).getName());
	}
	@Test
	public void testReadReport() throws OperationNotAllowedException {
		projectLeader.writeReport(company.getSpecificProject("Project01"), "Changes to Project", new GregorianCalendar(2015, Calendar.JANUARY, 23));
		assertEquals(company.getSpecificProject("Project01").getSpecificReport(0).getName(), "Changes to Project");
	}
	@Test
	public void testWriteReportNotProjectLeader(){
		try{
			projectLeader.writeReport(company.getSpecificProject("Project02"), "Changes to Project", new GregorianCalendar(2015, Calendar.JANUARY, 23));
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e) {
			assertEquals("Unable to write report, not assigned project leader", e.getMessage());
			assertEquals("Write report", e.getOperation());
		}
	}
	@Test
	public void testWriteReportNonexistantProject(){
		try{
			projectLeader.writeReport(company.getSpecificProject("Project03"), "Changes to Project", new GregorianCalendar(2015, Calendar.JANUARY, 23));
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e) {
			assertEquals("Unable to write report, project does not exist", e.getMessage());
			assertEquals("Write report", e.getOperation());
		}
	}
	@Test
	public void testWriteReportInvalidDate(){
		//Ikkeeksisterende dato opfanges ikke
		try{
			projectLeader.writeReport(company.getSpecificProject("Project01"), "Changes to Project", new GregorianCalendar(2015, Calendar.JANUARY, 35));
			fail("OperationNotAllowedException expected"); //TODO: Fix bug
		} catch(OperationNotAllowedException e) {
			assertEquals("Unable to write report, invalid date", e.getMessage());
			assertEquals("Write Report", e.getOperation());
		}
	}
	@Test
	public void testReadReportNonexistantReport(){
		try{
			//Undgå at null opfanges som gyldigt input
			projectLeader.writeReport(company.getSpecificProject("Project01"), "Changes to Project", new GregorianCalendar(2015, Calendar.JANUARY, 23));
			company.getSpecificProject("Project01").getSpecificReportByName("Wrong name"); //TODO: Fix bug
			fail("OperationNotAllowedException expected");
		} catch (OperationNotAllowedException e) {
			assertEquals("Unable to read report, report does not exist", e.getMessage());
			assertEquals("Read report", e.getOperation());
		}
	}
	@Test
	public void testReadReportNotProjectLeader(){
		try{
			projectLeader.writeReport(company.getSpecificProject("Project01"), "Changes to Project", new GregorianCalendar(2015, Calendar.JANUARY, 23));
			employee.getSpecificReportByName(company.getSpecificProject("Project01"), "Changes to Project");
		} catch (OperationNotAllowedException e) {
			assertEquals("Unable to read report, not assigned project leader", e.getMessage());
			assertEquals("Read report", e.getOperation());
		}
	}
	@Test
	public void testEditReport() throws OperationNotAllowedException{
		projectLeader.editReport(company.getSpecificProject("Project01").getSpecificReportByName("Changes to Project"), "New content");
	}
	@Test
	public void testEditReportNotProjectLeader(){
		try{
			projectLeader.writeReport(company.getSpecificProject("Project01"), "Changes to Project", new GregorianCalendar(2015, Calendar.JANUARY, 23));
			employee.editReport(company.getSpecificProject("Project01").getSpecificReportByName("Changes to Project"), "New content");
		} catch (OperationNotAllowedException e) {
			assertEquals("Unable to edit report, not assigned project leader", e.getMessage());
			assertEquals("Edit report", e.getOperation());
		}
	}
	@Test
	public void testEditReportNonexistantReport(){
		try{
			projectLeader.writeReport(company.getSpecificProject("Project01"), "Changes to Project", new GregorianCalendar(2015, Calendar.JANUARY, 23));
			projectLeader.editReport(company.getSpecificProject("Project01").getSpecificReportByName("Wrong Report"), "New content");
		} catch (OperationNotAllowedException e) {
			assertEquals("Unable to edit report, report does not exist", e.getMessage());
			assertEquals("Edit report", e.getOperation());
		}
	}
	@Test
	public void testEditReportNonexistantProject(){
		try{
			projectLeader.writeReport(company.getSpecificProject("Project01"), "Changes to Project", new GregorianCalendar(2015, Calendar.JANUARY, 23));
			projectLeader.editReport(company.getSpecificProject("Project02").getSpecificReport(0), "New content");
		} catch (OperationNotAllowedException e) {
			assertEquals("Report does not exist", e.getMessage());
			assertEquals("Get report", e.getOperation());
		}
	}
}
