package apptest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import softwarehuset.*;

public class TestAddress {
	Company company;
	
	@Before
	public void setup() {
		Address address = new Address("City", "Street", 1);
		company = new Company("Company", address);
		Executive executive = new Executive("Name", "Department1", company, "password");
		company.setExecutive(executive);
		company.executiveLogin("password");
		
	}
	@Test
	public void testSetAddress() throws OperationNotAllowedException {
		company.setAddress("New city", "New street", 2);
		assertEquals(company.getAddress().getCity(), "New city");
		assertEquals(company.getAddress().getStreet(), "New street");
		assertEquals(company.getAddress().getStreetNumber(), 2);
	}
	@Test
	public void testSetAddressInvalidStreet() {
		try {
			company.setAddress("New city", "New street", -1);
		} catch (OperationNotAllowedException e){
			assertEquals("Unable to set address, invalid street number", e.getMessage());
			assertEquals("Set address", e.getOperation());
		}
	}
	@Test
	public void testSetAddressInvalidString() {
		try {
			company.setAddress("", "", 2);
		} catch (OperationNotAllowedException e){
			assertEquals("Unable to set address, invalid city/street name", e.getMessage());
			assertEquals("Set address", e.getOperation());
		}
	}
}
