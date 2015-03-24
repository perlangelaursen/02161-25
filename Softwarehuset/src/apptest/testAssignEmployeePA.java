package apptest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import softwarehuset.Employee;
import softwarehuset.Project;

public class testAssignEmployeePA {
	Project project1;
	Employee projectLeader;
	Employee test1;
	
	@Before
	public void setUp() {
		project1 = new Project("TestProject", 7, "MIA");
		
		projectLeader = new Employee("Test", "RandD");
		projectLeader.setProjectLeaderStatus(true);
		
		project1.assignProjectLeader(projectLeader);
		test1 = new Employee("Test2", "RandD");
	}
	
	@Test
	public void testAssignEmployeeProject() {
		String ret = projectLeader.assignEmployee(test1, project1);
		assertEquals("Employee assigned",ret);
	}
	
	@Test
	public void testAssignEmployeeActivity() {
		
	}
	
	@Test
	public void testEmployeeNotAvailable() {
		
	}
}
