package softwarehuset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Activity {
	private String activityName, type;
	private GregorianCalendar start, end;
	private Project project;
	private HashMap<Employee, Integer> employees = new HashMap<>();
	private List<Employee> assignedEmployees = new ArrayList<Employee>();

	public Activity(GregorianCalendar start, GregorianCalendar end, String type) {
		this.start = start;
		this.end = end;
		this.type = type;
	}
	
	public Activity(String activityName, GregorianCalendar start, GregorianCalendar end, Project project) {
		this(start, end, "Work");
		this.activityName = project.getName()+"-"+activityName;
		this.project = project;
	}

	public void addEmployeeToActivity(Employee e) {
		if (project.getEmployees().contains(e)) {
			employees.put(e, 0);
			assignedEmployees.add(e);
		}
	}

	public String getName() {
		return activityName;
	}

	public GregorianCalendar getStart() {
		return start;
	}

	public GregorianCalendar getEnd() {
		return end;
	}

	public List<Employee> getEmployees() {
		return assignedEmployees;
	}
	
	public Project getProject() {
		return project;
	}

	public int getSpentTime(Employee e) {
		return employees.get(e);
	}
	public int getAllSpentTime() {
		int sum = 0;
		for(Entry<Employee, Integer> e: employees.entrySet()){
			sum=+getSpentTime((Employee) e);
		}
		return sum;
	}
	public void setTime(Employee e, int time) {
		employees.put(e, time);
	}

	public boolean isOverlapping(Activity activity) {
		return (activity.getStart().after(start) && activity.getEnd().before(end)||
				activity.getStart().before(start) && activity.getEnd().after(end)||
				activity.getStart().before(start) && activity.getEnd().after(start)||
				activity.getStart().before(end) && activity.getEnd().after(end)||
				activity.getStart().getTime().equals(start.getTime()));
	}

	public int getTimeSpan() {
		double minutes = ((end.getTime().getTime() - start.getTime().getTime()) / (1000 * 60))+24*60; //include last day
		double hours = (int)Math.round(minutes/60);
		return (int) hours; 
	}

	public String getType() {
		return type;
	}

	public void setEnd(GregorianCalendar newDate) {
		end = newDate;		
	}

	public void setStart(GregorianCalendar newDate) {
		start = newDate;		
	}
}
