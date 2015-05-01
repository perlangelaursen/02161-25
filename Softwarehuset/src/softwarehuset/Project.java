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
	private List<Report> reports = new ArrayList<Report>();
	
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
		for(Activity a : activities) {
			sum=+a.getAllSpentTime();
		}
		return sum;
	}

	public Activity getSpecificActivityByName(String activityName) throws OperationNotAllowedException {
		for(Activity a : activities){
			if(a.getName().equals(activityName));
			return a;
		}
		return null;
	}
	
	public Report getReport (int i){
		return reports.get(i);
	}
	
	public List<Report> getAllReports(){
		return reports;
	}
	public void addReport(Report report){
		reports.add(report);
	}

	public void getProjectDetails(List<String> statistics) {
		statistics.add("Project Name: " + name);
		statistics.add("Project Leader ID: " + projectLeader.getID() +
				" Department " + projectLeader.getDepartment());
		statistics.add("No. of employees assigned: " + assignedEmployees.size());
		assignedEmployeesInProject(statistics);
		activitiesInProject(statistics);
	}

	public void assignedEmployeesInProject(List<String> statistics) {
		for(Employee e : assignedEmployees) {
			statistics.add("ID: " + e.getID() + " Department: " + e.getDepartment());
		}
	}

	public void activitiesInProject(List<String> statistics) {
		statistics.add("No. of activities: "+ activities.size());
		for(Activity a : activities) {
			statistics.add("Activity name: " + a.getName() + 
					" No. of employees: " + a.getEmployees().size());
			a.assignedEmployeesInActivity(statistics);
		}
	}
}
