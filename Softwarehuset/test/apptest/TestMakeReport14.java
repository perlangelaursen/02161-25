package apptest;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestMakeReport14 {

	@Test
	public void testMakeReport() {
		assertEquals("Changes to Project", project.getSpecificReportByName("Changes to Project").getName());
		assertEquals("Changes to Project", project.getSpecificReport(0).getName());
		fail("Not yet implemented");
	}
	@Test
	public void testMakeReportNotProjectLeader(){
		
	}
	@Test
	public void testMakeReportNonexistantProject(){
		
	}
	@Test
	public void testMakeReportNonexistantReport(){
		
	}
	@Test
	public void testMakeReportInvalidDate(){
		
	}
}
