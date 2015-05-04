package softwarehuset;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

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
		checkIfLoggedInProjectLeader(p);
		p.addEmployeeToProject(e);
	}
	

	public void createActivity(Project project, String activityName, GregorianCalendar start, GregorianCalendar end) throws OperationNotAllowedException {
		checkIfLoggedInProjectLeader(project);
		if(!end.after(start)){
			throw new OperationNotAllowedException("Incorrect order of dates.",	"Create activity");
		}
		project.createActivity(activityName, start, end, project);
	}

	public void assignEmployeeActivity(Employee e, Activity a) throws OperationNotAllowedException {
		checkIfLoggedInProjectLeader(a.getProject());
		a.addEmployeeToActivity(e);
		e.addActivity(a);
	}
	
	private void addActivity(Activity a) {
		activities.put(a, 0);
	}

	public void relieveEmployeeProject(Employee e, Project project) throws OperationNotAllowedException {
		checkIfLoggedInProjectLeader(project);
		project.relieveEmployee(e);
	}

	public String getID() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public void registerSpentTime(Activity activity, int time) throws OperationNotAllowedException {
		if(company.getLoggedInEmployee() != this){
			throw new OperationNotAllowedException("Employee is not logged in", "Register spent time");
		}
		if(time<0){
			throw new OperationNotAllowedException("Invalid time", "Register spent time");
		}
		if(!activities.containsKey(activity)){
			throw new OperationNotAllowedException("Employee is not assigned to the chosen activity", "Register spent time");
		}
			activities.put(activity, time);
			activity.setTime(this, time);
	}

	public String getDepartment() {
		return department;
	}
	
	public Object viewProgress(String p, String a) throws OperationNotAllowedException {
		Project project = company.getSpecificProject(p);
		Activity activity = company.getSpecificProject(p).getActivity(a);
		checkIfLoggedInProjectLeader(project);
		return activity.getAllSpentTime();
	}
	
	public int viewProgress(String p) throws OperationNotAllowedException {
		Project project = company.getSpecificProject(p);
		checkIfLoggedInProjectLeader(project);
		return project.getSpentTime();
	}



	public List<String> getStatisticsProject(Project project) throws OperationNotAllowedException {
		checkIfLoggedInProjectLeader(project);
		
		List<String> statistics = new ArrayList<String>();
		project.getProjectDetails(statistics);
		return statistics;
	}
	public void writeReport(Project project, String name, int year, int month, int date) throws OperationNotAllowedException{
		checkForInvalidDate(year, month-1, date);
		GregorianCalendar day = new GregorianCalendar();
		day.set(year, month, date, 0, 0, 0);
		
		checkIfLoggedInProjectLeader(project);
		Report report = new Report(project, name, day);
		project.addReport(report);
	}
	
	public void registerVacationTime(int year, int month, int date, int year2, int month2, int date2) throws OperationNotAllowedException {
		checkForInvalidDate(year, month-1, date);
		checkForInvalidDate(year2, month2-1, date2);
		Activity vacation = createPersonalActivity(year, month-1, date, year2, month2-1, date2, "Vacation");
		
		if(!vacation.getStart().after(company.getCurrentTime())){
			throw new OperationNotAllowedException("Cannot register vacation in the past", "Register other time");
		}

        updateCalendar(vacation);
		calendar.put(vacation, "Vacation");
	}

	public void registerSickTime(int year, int month, int date, int year2, int month2, int date2) throws OperationNotAllowedException {
		checkForInvalidDate(year, month-1, date);
		checkForInvalidDate(year2, month2-1, date2);
		Activity sick = createPersonalActivity(year, month-1, date, year2, month2-1, date2, "Sick");
		
		if(sick.getEnd().after(company.getCurrentTime())){
			throw new OperationNotAllowedException("Cannot register sick days in the future", "Register other time");
		}
		
        updateCalendar(sick);
		calendar.put(sick, "Sick");
	}
	
	public void registerCourseTime(int year, int month, int date, int year2, int month2, int date2) throws OperationNotAllowedException {
		checkForInvalidDate(year, month-1, date);
		checkForInvalidDate(year2, month2-1, date2);
		Activity course = createPersonalActivity(year, month-1, date, year2, month2-1, date2, "Course");
		
		if(!course.getStart().after(company.getCurrentTime())){
			throw new OperationNotAllowedException("Cannot register course attendance in the past", "Register other time");
		}
		
		updateCalendar(course);
		calendar.put(course, "Course");
	}

	private void updateCalendar(Activity a) {
		ArrayList<Activity> removes = new ArrayList<>();
		for(Activity activity: calendar.keySet()){
			if(a.isOverlapping(activity)){
            	removes.add(activity);
            }
		}
		for(Activity activity: removes){
			calendar.remove(activity);
			updateOldPlans(a, activity);
			}
	}

	public Activity createPersonalActivity(int year, int month, int date, int year2, int month2, int date2, String type)throws OperationNotAllowedException {
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

	private void checkForInvalidDate(int year, int month, int date) throws OperationNotAllowedException {
		//Find max year
		GregorianCalendar newYear = new GregorianCalendar();
		newYear.setTime(company.getCurrentTime().getTime());
		newYear.add(Calendar.YEAR, 5);
		int maxYear = newYear.get(Calendar.YEAR);
		
		//Check if valid date
		if(year<1980 || year > maxYear || month< 0 || month > 11){
			throw new OperationNotAllowedException("Invalid time input", "Choose date");
		}
		
		//Check if date exists in the chosen month
		GregorianCalendar cal = new GregorianCalendar(year, month, 1,0,0,0);
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		if (date < 1 || date > daysInMonth){
			throw new OperationNotAllowedException("Invalid time input", "Choose date");
		}
	}

	public void updateOldPlans(Activity newActivity, Activity oldActivity) {
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
						
			calendar.put(oldActivityNewFirstPart, oldActivityNewFirstPart.getType());
			calendar.put(oldActivityNewSecondPart, oldActivityNewSecondPart.getType());
			
			//Remove first part of old activity
		} else if (oldActivity.getStart().before(start) && !oldActivity.getEnd().before(start)){
			Activity oldActivityNew = new Activity(oldActivity.getStart(), newEnd, oldActivity.getType());
			calendar.put(oldActivityNew, oldActivityNew.getType());
			
			//Remove last part of old activity
		} else if (!oldActivity.getStart().after(end) && oldActivity.getEnd().after(end)){
			Activity oldActivityNew = new Activity(newStart, oldActivity.getEnd(), oldActivity.getType());
			calendar.put(oldActivityNew, oldActivityNew.getType());
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

	public boolean isAvailable(GregorianCalendar start, GregorianCalendar end) {
		Activity act = new Activity(start, end, "work");
		for (Activity a : activities.keySet()) {
			if (a.isOverlapping(act)) {
				return false; 
			}
		}
		return true;
	}

	public void needForAssistanceWithActivity(Employee selected, Activity specificActivity) throws OperationNotAllowedException {
		if(company.getLoggedInEmployee() == this) {
			specificActivity.assignAssistingEmployee(selected);
		} else {
			throw new OperationNotAllowedException("User not logged in", "Need For Assistance");
		}
		
	}

	public void removeSpecificAssistingEmployee(Employee selected, Activity a)
	throws OperationNotAllowedException {
		if(company.getLoggedInEmployee() == this) {
			a.removeAssistingEmployee(selected);
		} else {
			throw new OperationNotAllowedException("User not logged in", "Remove Assisting Employee from activity");
		}
	}

	public int getSpentTime(String activityName) throws OperationNotAllowedException {
		int time = -1;
		for(Activity a: activities.keySet()){
			if (a.getName().equals(activityName)){
				time = activities.get(a);
			}
		}
		if(time == -1){
			throw new OperationNotAllowedException("Employee is not assigned to the activity", "See registered spent time");
		}
		return time;
		}

	public void editReport(Report report, String content) throws OperationNotAllowedException {
		if(report==null){
			throw new OperationNotAllowedException("Report does not exist", "Edit report");
		}
		checkIfLoggedInProjectLeader(report.getProject());
		report.setContent(content);
	}

	private void checkIfLoggedInProjectLeader(Project project) throws OperationNotAllowedException {
		if(project.getProjectLeader() != this){
			throw new OperationNotAllowedException("Operation is not allowed if not project leader", "Project leader operation");
		}
		if(company.getLoggedInEmployee() != this){
			throw new OperationNotAllowedException("Operation is not allowed if not project leader", "Project leader operation");
		}
	}
	
	public Set<Activity> getActivities() {
		return activities.keySet();
	}

	public void editActivityStart(String activity, int year, int month, int date) throws OperationNotAllowedException {
		Activity a = getActivity(activity);
		//checkIfLoggedInProjectLeader(a.getProject());
		
		checkForInvalidDate(year, month-1, date);
		GregorianCalendar start = new GregorianCalendar();
		start.set(year, month-1, date, 0, 0, 0);
		a.setStart(start);
	}

	public void editActivityEnd(String activity, int year, int month, int date) throws OperationNotAllowedException {
		Activity a = getActivity(activity);
		checkIfLoggedInProjectLeader(a.getProject());
		
		checkForInvalidDate(year, month-1, date);
		GregorianCalendar end = new GregorianCalendar();
		end.set(year, month-1, date, 0, 0, 0);
		a.setEnd(end);
	}

	private Activity getActivity(String activity) throws OperationNotAllowedException {
		Activity a = null;
		for (Project p: company.getProjects()){
			a = p.getActivity(activity);
			break;
		}

		if (a == null){
			throw new OperationNotAllowedException("Activity does not exist", "Edit activity");
		}
		return a;
	}
}
