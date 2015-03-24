package softwarehuset;

import java.util.ArrayList;
import java.util.List;

public class Project {
	private String name;
	private int estimatedTime;
	private String dueDate;
	private Employee projectLeader;
	private List<Employee> assignedEmployees = new ArrayList<Employee>();
	
	public Project(String string, int i, String string2) {
		// TODO Auto-generated constructor stub
		this.name = string;
		this.estimatedTime = i;
		this.dueDate = string2;
	}
	
	public void assignProjectLeader(Employee e) {
		this.projectLeader = e;
	}
	
	public void addEmployeeToProject(Employee e) {
		assignedEmployees.add(e);
	}

}
