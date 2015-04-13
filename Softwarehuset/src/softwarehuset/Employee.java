package softwarehuset;

import java.util.GregorianCalendar;
import java.util.HashMap;

public class Employee {
	private Company company;
	private String id, password;
	private String department;
	private HashMap<Activity, Integer> activities = new HashMap<>();
	
	public Employee(String id, String password, Company company, String department) {
		this.id = id;
		this.password = password;
		this.company = company;
		this.department = department;
	}
	
	public void assignEmployeeProject(Employee e, Project p) throws OperationNotAllowedException {
		if(p.getProjectLeader() == this) {
			p.addEmployeeToProject(e);
		} else {
			throw new OperationNotAllowedException("Assign Employee is not allowed if not project leader.", "Assign Employee");
		}
	}
	
	public void createAcivity(Project specificProject, String activityName,	GregorianCalendar start, GregorianCalendar end) throws OperationNotAllowedException {
		if(id.equals(specificProject.getProjectLeader().getID())) {
			if(end.after(start) || end.equals(start)){
				specificProject.createActivity(activityName, start, end, specificProject);
			} else {
				System.out.println("Date problems");
			}
		} else {
			throw new OperationNotAllowedException("Create activity is not allowed if not project leader.", 
					"Create activity");
		}
	}

	public void assignEmployeeActivity(Employee e, Activity a) throws OperationNotAllowedException {
		if(a.getProject().getProjectLeader() == this) {
			a.addEmployeeToActivity(e);
			e.addActivity(a);
		} else {
			throw new OperationNotAllowedException("Assign Employee is not allowed if not activity leader.", 
					"Assign Employee");
		}
	}
	
	private void addActivity(Activity a) {
		activities.put(a, 0);
	}

	public void assignActivityLeader(Employee e, Activity a) throws OperationNotAllowedException {
		if(a.getProject().getProjectLeader() == this) {
			a.setActivityLeader(e); 
		} else {
			throw new OperationNotAllowedException("Assign ActivityLeader is not allowed if not project leader.", 
					"Assign ActivityLeader");
		}
	}

	public void relieveEmployeeProject(Employee e, Project specificProject) throws OperationNotAllowedException {
		if(specificProject.getProjectLeader() == this) {
			specificProject.relieveEmployee(e);
		} else {
			throw new OperationNotAllowedException("Relieve Employee if not projectleader", 
					"Relieve Employee");
		}
	}

	public String getID() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void registerSpentTime(Activity activity, int time) throws OperationNotAllowedException {
		if (company.getSignedInEmployee() == this){
			if(activities.containsKey(activity)){
				activities.put(activity, time);
				activity.setTime(this, time);
			} else {
				throw new OperationNotAllowedException("Employee is not assigned to the chosen activity", "Register spent time");
			}
		} else {
			throw new OperationNotAllowedException("Employee is not logged in", "Register spent time");
		}
	}

	public String getDepartment() {
		return department;
	}
}
