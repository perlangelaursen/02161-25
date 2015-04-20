package softwarehuset;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Activity {
	private String activityName;
	private GregorianCalendar start, end;
	private Employee activityLeader;
	private List<Employee> assignedEmployees = new ArrayList<Employee>();
	
	public Activity(String activityName, GregorianCalendar start,
			GregorianCalendar end) {
		this.activityName = activityName;
		this.start = start;
		this.end = end;
	}
	
	public void setActivityLeader(Employee e) {
		this.activityLeader = e;
	}
	
	public void addEmployeeToActivity(Employee e) {
		assignedEmployees.add(e);
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
}
