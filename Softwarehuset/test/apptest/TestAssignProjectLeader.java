package apptest;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import softwarehuset.*;

public class TestAssignProjectLeader {

	@Test
	public void test1() throws Exception {
		Address add = new Address("roskildevej 2", "roskilde");
		Company com = new Company("SoftwareHuset", add);
		Executive ex = new Executive("name","Department1", com, "password");
		
		GregorianCalendar d1 = new GregorianCalendar();
		GregorianCalendar d2 = new GregorianCalendar();
		
		assertFalse(com.executiveIsLoggedIn());
		com.executiveLogin("password");
		assertTrue(com.executiveIsLoggedIn());
		
		com.createProject("p1", d1, d2);
		Project p1 = com.getSpecificProject("p1");
		
		assertEquals(com.getProjects().size(),1);
		
		Employee em = new Employee("Anders", "Project Department");
		
		ex.assignProjectLeader(em, p1);
		
		assertEquals(p1.getProjectleader(), em);
		
	}
	
	@Test
	public void test2() throws Exception {
		
	}
	
	
}