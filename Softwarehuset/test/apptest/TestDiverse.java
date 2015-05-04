//Test by Anna Ølgaard Nieksen - s144437
package apptest;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import softwarehuset.*;
import static org.junit.Assert.*;

public class TestDiverse {
	
	private Company company;
	private Executive executive;
	private Employee em2, em3, pro;
	private Project p;
	private GregorianCalendar d1, d2, d3, d4;
	
	@Before
	public void setUp() throws Exception {
		Address add = new Address("Kagevej 2", "Hundige");
		company = new Company("Softwarehuset", add);
		executive = new Executive("Name", "Department1", company, "password");
		
		// Log in as executive
		assertFalse(company.executiveIsLoggedIn());
		company.executiveLogin("password");
		assertTrue(company.executiveIsLoggedIn());
		
		p = company.createProject("project1");
		pro = company.createEmployee("PROJ", "password", "department");
		executive.assignProjectLeader(pro, p);
		
		d1 = new GregorianCalendar();
		d2 = new GregorianCalendar();
		d3 = new GregorianCalendar();
		d4 = new GregorianCalendar();

		d1.set(2015, 1, 1);
		d2.set(2015, 2, 1);
		d3.set(2015, 3, 1);
		d4.set(2015, 4, 1);
	
		em2 = company.createEmployee("BAMS", "password", "department");
	}
	
	@Test
	public void testCreateEmployee() throws Exception {
		try {
			company.createEmployee("Anders", "password", "department");
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Employee ID must be the length of 4 letters",e.getMessage());
			assertEquals("Create employee",e.getOperation());
		}
		
		try {
			company.createEmployee("AND1", "password", "department");
			fail("OperationNotAllowedException exception should have been thrown");
		} catch (OperationNotAllowedException e) {
			assertEquals("Employee ID must not contain any numbers",e.getMessage());
			assertEquals("Create employee",e.getOperation());
		}		
	}
	
	@Test
	public void testEmployeeLogin() throws Exception {
		
		Employee em = new Employee("HAVD", "password", company, "department");
		
		company.employeeLogin("HAVD", "password");
		assertTrue(company.executiveIsLoggedIn());
		
		company.employeeLogin("BAMS", "password2");
		assertTrue(company.executiveIsLoggedIn());
		
	}
	
	@Test
	public void testGetEmployee() throws Exception {
		em3 = company.createEmployee("LAVT", "password", "department");
		
		assertEquals(company.getEmployee("LAVT"), em3);
		assertEquals(company.getEmployee("BAMS"), em2);
		assertNull(company.getEmployee("MIST"));
		
		
	}
	
	@Test
	public void testgetEmployee() throws Exception {
		company.employeeLogin("PROJ", "password");
		Employee em10 = company.createEmployee("ANDS", "password", "department");
		
		assertNull(p.getEmployee("ANDS"));
		
		pro.assignEmployeeProject(em10, p);
		assertNull(p.getEmployee("STEF"));
		
	}
	
}