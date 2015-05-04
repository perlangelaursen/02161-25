package softwarehuset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.GregorianCalendar;

public class Project {
	private String name;
	private int ID;
	private GregorianCalendar start, end;
	private Employee projectLeader;
	private List<Employee> assignedEmployees = new ArrayList<Employee>();
	private List<Activity> activities = new ArrayList<Activity>();
	private List<Report> reports = new ArrayList<Report>();
	private Company com;
	
	public Project(String name, Company com) {
		this.name = name;
		this.com = com;
		setID();
	}
	
	public Project(String name, GregorianCalendar start, GregorianCalendar end, Company com) {
		this(name, com);
		this.start = start;
		this.end = end;
	}
	
	public String getName() {
		return name;
	}
	
	private void setID() {
		int year = com.getCurrentTime().get(Calendar.YEAR) % 100 * 10000;
		this.ID = year + com.getProjectCounter();
	}
	
	public int getID() {
		return ID;
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
	
	public List<Activity> getActivities() {
		return activities;
	}
	
	public Activity getSpecificActivity(int i) throws OperationNotAllowedException {
		if(i>=activities.size()){
			throw new OperationNotAllowedException("Activity does not exist", "Get activity");
		}
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
			if(a.getName().equals(activityName)) return a;
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
	public Report getSpecificReport(int i) throws OperationNotAllowedException{
		if(i>=reports.size()){
			throw new OperationNotAllowedException("Report does not exist", "Get report");
		}
		return reports.get(i);
	}
	public Report getSpecificReportByName(String name) {
		for(Report r : reports) {
			if(r.getName().equals(name)) return r;
		}
		return null;
	}
}
