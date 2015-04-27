package softwarehuset;

import java.util.ArrayList;
import java.util.List;
import java.util.GregorianCalendar;

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
	
	public Employee getProjectLeader() {
		return projectLeader;
	}

	public Activity createActivity(String activityName, GregorianCalendar start, GregorianCalendar end, Project project) {
		Activity a = new Activity(activityName, start, end, project);
		activities.add(a);
		return a;
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
		for(Employee e : assignedEmployees) {
			if(e.getID().equals(id)) {
				return e;
			}
		}
		return null;
	}
	public int getSpentTime() {
		int sum = 0;
		for(Activity i : activities) {
			sum=+i.getAllSpentTime();
		}
		return sum;
	}
}
