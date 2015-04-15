package softwarehuset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Employee {
	private Company company;
	private String id, password;
	private String department;
	private HashMap<Activity, Integer> activities = new HashMap<>();
	private HashMap<Activity, String> calendar = new HashMap<>();
	
	public Employee(String id, String password, Company company, String department) {
		this.id = id;
		this.password = password;
		this.company = company;
		this.department = department;
	}
	
	public void assignEmployeeProject(Employee e, Project p) throws OperationNotAllowedException {
		if(company.getLoggedInEmployee() == this && p.getProjectLeader() == this) {
			p.addEmployeeToProject(e);
		} else {
			throw new OperationNotAllowedException("Assign Employee is not allowed if not project leader.", "Assign Employee");
		}
	}
	
	public void createAcivity(Project specificProject, String activityName,	GregorianCalendar start, GregorianCalendar end) throws OperationNotAllowedException {
		if(company.getLoggedInEmployee() == this && id.equals(specificProject.getProjectLeader().getID())) {
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
		if(company.getLoggedInEmployee() == this && a.getProject().getProjectLeader() == this) {
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
		if(company.getLoggedInEmployee() == this && a.getProject().getProjectLeader() == this) {
			a.setActivityLeader(e); 
		} else {
			throw new OperationNotAllowedException("Assign ActivityLeader is not allowed if not project leader.", 
					"Assign ActivityLeader");
		}
	}

	public void relieveEmployeeProject(Employee e, Project specificProject) throws OperationNotAllowedException {
		if(company.getLoggedInEmployee() == this && specificProject.getProjectLeader() == this) {
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
		if (company.getLoggedInEmployee() == this){
			if(activities.containsKey(activity)){
				if(time>=0){
					activities.put(activity, time);
					activity.setTime(this, time);
				} else {
					throw new OperationNotAllowedException("Invalid time", "Register spent time");
				}
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

	public void registerVacationTime(int year, int month, int date, int year2, int month2, int date2) throws OperationNotAllowedException {
		checkForInvalidDate(year, month-1, date, year2, month2-1, date2);
		Activity vacation = createPersonalActivity(year, month-1, date, year2, month2-1, date2, "Vacation");
		
		if(!vacation.getStart().after(company.getCurrentTime())){
			throw new OperationNotAllowedException("Cannot register vacation in the past", "Register other time");
		}

        updateCalendar(vacation);
		calendar.put(vacation, "Vacation");
	}

	public void registerSickTime(int year, int month, int date, int year2, int month2, int date2) throws OperationNotAllowedException {
		checkForInvalidDate(year, month-1, date, year2, month2-1, date2);
		Activity sick = createPersonalActivity(year, month-1, date, year2, month2-1, date2, "Sick");
		
		if(sick.getEnd().after(company.getCurrentTime())){
			throw new OperationNotAllowedException("Cannot register sick days in the future", "Register other time");
		}
		
        updateCalendar(sick);
		calendar.put(sick, "Sick");
	}
	
	public void registerCourseTime(int year, int month, int date, int year2, int month2, int date2) throws OperationNotAllowedException {
		checkForInvalidDate(year, month-1, date, year2, month2-1, date2);
		Activity course = createPersonalActivity(year, month-1, date, year2, month2-1, date2, "Course");
		
		if(!course.getStart().after(company.getCurrentTime())){
			throw new OperationNotAllowedException("Cannot register course attendance in the past", "Register other time");
		}
		
		updateCalendar(course);
		calendar.put(course, "Course");
	}

	private void updateCalendar(Activity sick) {
		ArrayList<Activity> removes = new ArrayList<>();
		for(Activity activity: calendar.keySet()){
			if(sick.isOverlapping(activity)){
            	removes.add(activity);
            }
		}
		for(Activity activity: removes){
			updateOldPlans(sick, activity);
			calendar.remove(activity);
		}
	}

	private Activity createPersonalActivity(int year, int month, int date,	int year2, int month2, int date2, String type)throws OperationNotAllowedException {
		GregorianCalendar start = new GregorianCalendar();
		GregorianCalendar end = new GregorianCalendar();
		start.set(year, month, date, 0,0,0);
		end.set(year2, month2, date2, 0,0,0);
		Activity activity = new Activity(start, end, type);
		
		if (start.after(end)){
			throw new OperationNotAllowedException("End date cannot be before start date", "Register other time");
		}
		return activity;
	}

	private void checkForInvalidDate(int year, int month, int date, int year2, int month2, int date2) throws OperationNotAllowedException {
		GregorianCalendar newYear = new GregorianCalendar();
		newYear.setTime(company.getCurrentTime().getTime());
		newYear.add(Calendar.YEAR, 5);
		int maxYear = newYear.get(Calendar.YEAR);
		if(year<1980 || year > maxYear || month< 0 || month > 11
				|| year2<1980 || year2 > maxYear || month2< 0 || month2 > 11){
			throw new OperationNotAllowedException("Invalid time input", "Register other time");
		}
		GregorianCalendar cal = new GregorianCalendar(year, month, 1,0,0,0);
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (date < 1 || date > daysInMonth || date2 < 1 || date2 > daysInMonth){
			throw new OperationNotAllowedException("Invalid time input", "Register other time");
		}
	}

	private void updateOldPlans(Activity newActivity, Activity oldActivity) {
		GregorianCalendar newStart = new GregorianCalendar();
		GregorianCalendar newEnd = new GregorianCalendar();
		GregorianCalendar start = newActivity.getStart();
		GregorianCalendar end = newActivity.getEnd();
		newEnd.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DAY_OF_MONTH)-1,0,0,0);
		newStart.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH)+1,0,0,0);
		
		//Divide old activity into two parts with new activity between
		if (oldActivity.getStart().before(start) && oldActivity.getEnd().after(end)){
			Activity oldActivityNewFirstPart = new Activity(oldActivity.getStart(), newEnd, oldActivity.getType());
			Activity oldActivityNewSecondPart = new Activity(newStart, oldActivity.getEnd(), oldActivity.getType());
						
			calendar.put(oldActivityNewFirstPart, oldActivity.getType());
			calendar.put(oldActivityNewSecondPart, oldActivity.getType());
			
			//Remove first part of old activity
		} else if (oldActivity.getStart().before(start) && oldActivity.getEnd().after(start)){
			Activity oldActivityNew = new Activity(oldActivity.getStart(), newEnd, oldActivity.getType());
			calendar.put(oldActivityNew, oldActivity.getType());
			
			//Remove last part of old activity
		} else if (oldActivity.getStart().before(end) && oldActivity.getEnd().after(end)){
			Activity oldActivityNew = new Activity(newStart, oldActivity.getEnd(), oldActivity.getType());
			calendar.put(oldActivityNew, oldActivity.getType());
		}
	}
	
	public int getOtherTime(String type) {
		int time = 0;
		for(Activity activity: calendar.keySet()){
			if (activity.getType().equals(type)){
				time += activity.getTimeSpan();
			}
		}
		return time;
	}
}
