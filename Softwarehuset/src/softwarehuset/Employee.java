package softwarehuset;

public class Employee {
	private String id;
	private String department;
	private boolean isProjectLeader = false;
	
	public Employee(String id, String department) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.department = department;
	}
	
	public void setProjectLeaderStatus(boolean status) {
		this.isProjectLeader = status;
	}
	
	public String assignEmployee(Employee e, Project p) {
		if(isProjectLeader) {
			p.addEmployeeToProject(e);
			return "Employee assigned";
		} else {
			return "Not Project Leader";
		}
	}

}
