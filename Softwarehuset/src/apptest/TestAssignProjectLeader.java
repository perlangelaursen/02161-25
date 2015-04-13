package apptest;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import softwarehuset.*;

public class TestAssignProjectLeader {

	@Test
	public void test1() throws Exception {
		Address add = new Address("roskildevej 2", 2000, "roskilde");
		Company com = new Company("SoftwareHuset", add);
		Executive ex = new Executive("name","Department1", com, "password");
		
		Date d1 = new Date();
		Date d2 = new Date();
		com.createProject("p1", d1, d2);
		Project p1 = new Project("p1", d1, d2);
		
		assertEquals(com.getProjects().size(),1);
		
		Employee em = new Employee("Anders", "Project Department");
		
		assertFalse(com.executiveIsLoggedIn());
		com.executiveLogin("password");
		assertTrue(com.executiveIsLoggedIn());
		
		ex.setProjectLeader(em, p1);
		
		assertEquals(p1.getProjectleader(), em);
		
	}
	
}
