package apptest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import softwarehuset.Employee;

public class testAssignEmployeePA {
	@Before
	public void setUp() {
		Employee projectLeader = new Employee("Test", "RandD");
		projectLeader.setProjectLeaderStatus(true);
		Employee test1 = new Employee("Test2", "RandD");
	}
	
	@Test
	public void testAssignEmployeeProject() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testAssignEmployeeActivity() {
		
	}
	
	@Test
	public void testEmployeeNotAvailable() {
		
	}
}
