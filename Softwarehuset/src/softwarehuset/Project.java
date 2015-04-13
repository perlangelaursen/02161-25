package softwarehuset;

import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;
import java.util.Date;

public class Project {
	private String name;
	private GregorianCalendar start, end;
	private Employee projectLeader;
	private List<Employee> assignedEmployees = new ArrayList<Employee>();
	private List<Activity> activities = new ArrayList<Activity>();
	
	public Project(String name) {
		this.name = name;
	}
	
	public Project(String name, GregorianCalendar start, GregorianCalendar end) {
		this(name);
		this.start = start;
		this.end = end;
	}
	
	public String getName() {
		return name;
	}
	
	public void assignProjectLeader(Employee e) {
		this.projectLeader = e;
	}
	
	public void addEmployeeToProject(Employee e) {
		assignedEmployees.add(e);
	}
	
	public Employee getProjectleader() {
		return projectLeader;
	}

	public void createActivity(String activityName, GregorianCalendar start,
			GregorianCalendar end) {
		activities.add(new Activity(activityName, start, end));
	}
	
	public void addEmployeeToActivity(Employee e, Activity a) {
		for(Activity i : activities) {
			if(i.getName() == a.getName()) {
				i.addEmployeeToActivity(e);
			}
		}
	}
	
	public List<Activity> getActivities() {
		return activities;
	}
	
	public Activity getSpecificActivity(int i) {
		return activities.get(i);
	}

	public List<Employee> getEmployees() {
		return assignedEmployees;
	}

	public void relieveEmployee(Employee e) {
		assignedEmployees.remove(e);
	}

	public Employee getEmployee(String id) {
		// TODO Auto-generated method stub
		for(Employee e : assignedEmployees) {
			if(e.getName() == id) {
				return e;
			}
		}
		return null;
	}
}
