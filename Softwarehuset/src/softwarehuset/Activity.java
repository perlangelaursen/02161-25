package softwarehuset;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

public class Activity {
	private String activityName;
	private GregorianCalendar start, end;
	private Employee activityLeader;
	private Project project;
	private HashMap<Employee, Integer> employees = new HashMap<>();

	public Activity(String activityName, GregorianCalendar start,
			GregorianCalendar end, Project project) {
		this.activityName = activityName;
		this.start = start;
		this.end = end;
		this.project = project;
	}

	public void setActivityLeader(Employee e) {
		this.activityLeader = e;
	}

	public void addEmployeeToActivity(Employee e) {
		if (project.getEmployees().contains(e)) {
			employees.put(e, 0);
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

	public Project getProject() {
		return project;
	}

	public int getSpentTime(Employee e) {
		return employees.get(e);
	}

	public void setTime(Employee e, int time) {
		employees.put(e, time);
	}
}
