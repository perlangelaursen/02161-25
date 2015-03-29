package softwarehuset;

import java.util.GregorianCalendar;

public class Employee {
	private String id;
	private String department;
	private boolean isProjectLeader = false;
	private boolean isActivityLeader = false;
	
	public Employee(String id, String department) {
		this.id = id;
		this.department = department;
	}
	
	public void setProjectLeaderStatus(boolean status) {
		this.isProjectLeader = status;
	}
	
	public void setActivityLeaderStatus(boolean status) {
		this.isActivityLeader = status;
	}
	
	public String assignEmployeeProject(Employee e, Project p) {
		if(isProjectLeader) {
			p.addEmployeeToProject(e);
			return "Employee assigned";
		} else {
			return "Not Project Leader";
		}
	}
	
	public void createAcivity(Project specificProject, String activityName,
			GregorianCalendar start, GregorianCalendar end) throws OperationNotAllowedException {
		if(isProjectLeader) {
			specificProject.createActivity(activityName, start, end);
		} else {
			throw new OperationNotAllowedException("Create activity is not allowed if not project leader.", 
					"Create activity");
		}
	}

	public String assignEmployeeActivity(Employee e, Activity a) {
		if(isActivityLeader) {
			a.addEmployeeToActivity(e); 
			return "Employee assigned";
		} else {
			return "Not Activity Leader";
		}
	}
	
	public void assignActivityLeader(Employee e, Activity a) {
		if(isProjectLeader) {
			a.setActivityLeader(e); 
			e.setActivityLeaderStatus(true);
		}
	}
}
