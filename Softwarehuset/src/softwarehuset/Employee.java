package softwarehuset;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

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
	
	public void assignEmployeeProject(Employee e, Project p) throws OperationNotAllowedException {
		if(isProjectLeader) {
			p.addEmployeeToProject(e);
		} else {
			throw new OperationNotAllowedException("Assign Employee is not allowed if not project leader.", 
					"Assign Employee");
		}
	}
	
	public void createAcivity(Project specificProject, String activityName,
			GregorianCalendar start, GregorianCalendar end) throws OperationNotAllowedException {
		if(isProjectLeader) {
			if(end.after(start) || end.equals(start)){
				specificProject.createActivity(activityName, start, end);
			} else {
				System.out.println("Date problems");
			}
		} else {
			throw new OperationNotAllowedException("Create activity is not allowed if not project leader.", 
					"Create activity");
		}
	}

	public void assignEmployeeActivity(Employee e, Activity a) throws OperationNotAllowedException {
		if(isActivityLeader) {
			a.addEmployeeToActivity(e); 
		} else {
			throw new OperationNotAllowedException("Assign Employee is not allowed if not activity leader.", 
					"Assign Employee");
		}
	}
	
	public void assignActivityLeader(Employee e, Activity a) throws OperationNotAllowedException {
		if(isProjectLeader) {
			a.setActivityLeader(e); 
			e.setActivityLeaderStatus(true);
		} else {
			throw new OperationNotAllowedException("Assign ActivityLeader is not allowed if not project leader.", 
					"Assign ActivityLeader");
		}
	}

	public void relieveEmployeeProject(Employee e, Project specificProject) throws OperationNotAllowedException {
		if(isProjectLeader) {
			specificProject.relieveEmployee(e);
		} else {
			throw new OperationNotAllowedException("Relieve Employee if not projectleader", 
					"Relieve Employee");
		}
	}

	public String getName() {
		return id;
	}

	public String getDepartment() {
		return department;
	}

	public void getStatisticsProject(Project specificProject) throws OperationNotAllowedException {
		// TODO Auto-generated method stub
		if(isProjectLeader) {
			List<String> statistics = new ArrayList<String>();
			statistics.add("Project Name: " + specificProject.getName());
			statistics.add("No. of employees assigned: " + specificProject.getEmployees().size());
			for(Employee e : specificProject.getEmployees()) {
				statistics.add("ID: " + e.getName() + "Department: " + e.getDepartment());
			}
			statistics.add("No. of activities: "+ specificProject.getActivities().size());
		} else {
			throw new OperationNotAllowedException("Get statistics is not allowed if not project leader.", 
					"Get statistics");
		}
		
	}
}
