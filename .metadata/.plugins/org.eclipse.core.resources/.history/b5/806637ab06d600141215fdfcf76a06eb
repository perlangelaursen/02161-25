package softwarehuset;

import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;

public class Project {
	private String name;
	private GregorianCalendar start, end;
	private Employee projectLeader;
	private List<Employee> assignedEmployees = new ArrayList<Employee>();
	
	public Project(String name) {
		this.name = name;
	}
	
	public Project(String name, GregorianCalendar start, GregorianCalendar end) {
		this(name);
		this.start = start;
		this.end = end;
	}
	
	public void assignProjectLeader(Employee e) {
		this.projectLeader = e;
	}
	
	public void addEmployeeToProject(Employee e) {
		assignedEmployees.add(e);
	}

}
