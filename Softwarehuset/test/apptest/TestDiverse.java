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
	private Employee em2, em3;
	
	@Before
	public void setUp() {
		Address add = new Address("Kagevej 2", "Hundige");
		company = new Company("Softwarehuset", add);
		Executive executive = new Executive("Name", "Department1", company, "password");
		
		// Log in as executive
		assertFalse(company.executiveIsLoggedIn());
		company.executiveLogin("password");
		assertTrue(company.executiveIsLoggedIn());
		
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
		
		em2 = company.createEmployee("BAMS", "password", "department");
		
		company.employeeLogin("BAMS", "password2");
		assertTrue(company.executiveIsLoggedIn());
		
	}
	
	@Test
	public void testGetEmployee() throws Exception {
		
		em3 = company.createEmployee("LAVT", "password", "department");
		
		assertEquals(company.getEmployee("LAVT"), em3);
		assertEquals(company.getEmployee("HAVD"), em2);
		assertNull(company.getEmployee("MIST"));
		
		
	}
	
}