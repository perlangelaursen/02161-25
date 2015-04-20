package softwarehuset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class Activity {
	private String activityName, type;
	private GregorianCalendar start, end;
	private Employee activityLeader;
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
		this.activityName = activityName;
		this.project = project;
	}

	public void setActivityLeader(Employee e) {
		this.activityLeader = e;
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

	public Employee getActivityLeader() {
		// TODO Auto-generated method stub
		return activityLeader;
	}
	
	public Project getProject() {
		return project;
	}

	public int getSpentTime(Employee e) {
		return employees.get(e);
	}

	public void setTime(Employee e, int time) {
		employees.put(e, time);
	}

	public boolean isOverlapping(Activity activity) {
		return (activity.getStart().after(start) && activity.getEnd().before(end)||
				activity.getStart().before(start) && activity.getEnd().after(end)||
				activity.getStart().before(start) && activity.getEnd().after(start)||
				activity.getStart().before(end) && activity.getEnd().after(end)||
				overLaps(activity));
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
	
	private boolean overLaps(Activity activity) {
		System.out.println("overlaps");
		return  activity.getStart().get(Calendar.YEAR) == start.get(Calendar.YEAR) &&
				activity.getStart().get(Calendar.DAY_OF_YEAR) == start.get(Calendar.DAY_OF_YEAR)&&
				activity.getEnd().get(Calendar.YEAR) == end.get(Calendar.YEAR) &&
				activity.getEnd().get(Calendar.DAY_OF_YEAR) == end.get(Calendar.DAY_OF_YEAR);
	}
}
