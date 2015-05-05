package cmdinterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.GregorianCalendar;
import java.util.List;

import softwarehuset.Address;
import softwarehuset.Company;
import softwarehuset.Employee;
import softwarehuset.Executive;
import softwarehuset.OperationNotAllowedException;
import softwarehuset.Project;

/**
 * @author Per Lange Laursen - s144456
 *
 */
public class WRTcmdinterface {

	/**
	 * @param args
	 * @throws IOException
	 * @throws OperationNotAllowedException
	 */

	private static Address address = new Address("Kongens Lyngby", "Anker Engelunds Vej");
	private static Company company = new Company("Softwarehuset A/S", address);
	private static Executive executive = new Executive("ex01", "Executive", company, "password");
	private static Employee e, e2;
	private static Project p, p2;
	static BufferedReader input = new BufferedReader(new InputStreamReader(
			System.in));

	public static void main(String[] args) throws IOException, OperationNotAllowedException {
		//Setup
		company.executiveLogin("password");
		e = company.createEmployee("PRLR", "password", "Software");
		e2 = company.createEmployee("AKMU", "password", "Software");
		p = company.createProject("Project 3");
		p2 = company.createProject("Project 4");
		p.assignProjectLeader(e);
		company.employeeLogout();
		company.employeeLogin("PRLR", "password");
		e.assignEmployeeProject(e2, p);
		company.employeeLogout();
		
		// Print initial UI
		new WRTcmdinterface().initialScreen();
	}

	private void executiveScreen() throws IOException,OperationNotAllowedException {
		System.out.println("[Executive logged in]");
		System.out.println("Executive options");
		System.out.println("- Add Employee (Company Database)");
		System.out.println("- Create Project");
		System.out.println("- Assign Project Leader");
		System.out.println("- Log out");
		System.out.println();
		String inputString = input.readLine();
		String[] commands = inputString.split(" ");
		if (commands[0].equals("Add") && commands[1].equals("Employee")) {
			addEmployee();
		}

		if (commands[0].equals("Create") && commands[1].equals("Project")) {
			createProject();
		}

		if (commands[0].equals("Assign") && commands[1].equals("Project")
				&& commands[2].equals("Leader")) {
			assignProjectLeader();
		}

		if (commands[0].equals("Log") && commands[1].equals("out")) {
			initialScreen();
		}

	}

	private void assignProjectLeader() throws IOException,  OperationNotAllowedException {
		Employee projectLeader = findEmployee();
		Project currentProject = findProject();

		executive.assignProjectLeader(projectLeader, currentProject);
		System.out.println(projectLeader.getID()+ " is now project leader for project "	+ currentProject.getID());
		System.out.println();
		executiveScreen();

	}

	private void createProject() throws IOException, OperationNotAllowedException {
		String projectName = "";
		while (projectName.equals("")) {
			System.out.print("Enter project name: ");
			projectName = input.readLine();

			if (projectName.equals("")) {
				System.out.println("Missing information: Name");
				System.out.println();
			}
		}

		GregorianCalendar start = checkStartDate();
		GregorianCalendar end = checkEndDate();
		
		try {
			Project p = company.createProject(projectName, start, end);
			System.out.println("Project created: " + p.getID());
			System.out.println();
			executiveScreen();
		} catch (Exception e) {
			System.out.println("End date cannot be before start date");
			System.out.println("Project could not be created");
			System.out.println();
			createProject();
		}
	}

	private void addEmployee() throws IOException, OperationNotAllowedException {
		boolean isRunning = true;
		while (isRunning) {
			try {
				System.out.print("Enter Employee ID: ");
				String id = input.readLine();
				System.out.print("Enter Deparment: ");
				String department = input.readLine();
				company.createEmployee(id, "password", department);
				System.out.println("An employee with the ID \"" + id
						+ "\" was created");
				System.out.println("Default password for " + id
						+ " is \"password\"");
				System.out.println();
				isRunning = false;
			} catch (Exception e) {
				System.out.println("The ID must be 4 letters long");
				System.out.println();
			}
		}
		executiveScreen();
	}

	private void initialScreen() throws IOException, OperationNotAllowedException {
		System.out.println("Work Registration Tool 0.1");
		System.out.println("Softwarehuset A/S 2015");
		System.out
				.println("To login type \"Login\" with the username and a password (Regular user)");
		System.out
				.println("To login as executive type \"Login\" with just a password");
		System.out.println("To exit the system. Type \"Exit\"");
		System.out.println();
		String inputString = input.readLine();
		String[] commands = inputString.split(" ");

		if (commands[0].equals("Login") && commands.length == 3) {
			company.employeeLogin(commands[1], commands[2]);
			if (company.getLoggedInEmployee() != null) {
				employeeScreen();
			}
			System.out.println("Incorrect ID or password");
			System.out.println();
			initialScreen();

		}
		if (commands[0].equals("Login") && commands.length == 2) {
			company.executiveLogin(commands[1]);
			if (company.executiveIsLoggedIn()) {
				executiveScreen();
			}
			System.out.println("Incorrect password");
			System.out.println();
			initialScreen();
		}
		if (commands[0].equals("Exit")) {
			System.exit(0);
		}
	}

	private void employeeScreen() throws IOException, OperationNotAllowedException {
		System.out.println("[User: " + company.getLoggedInEmployee().getID()+
				" " + company.getLoggedInEmployee().getDepartment() + "]");
		System.out.println("Employee options");
		System.out.println("- Ask colleague for assistance");
		System.out.println("- Remove assisting colleague");
		System.out.println("- Register spent time");
		System.out.println("- Register vacation, sick-days and course attendance");
		System.out.println("- See registered spent time");
		System.out.println();
		System.out.println("Project Leader options");
		System.out.println("- Assign employee to project");
		System.out.println("- Assign employee to activity");
		System.out.println("- Create an activity");
		System.out.println("- Get Project Statistics");
		System.out.println("- Relieve employee from project");
		System.out.println("- See available employees");
		System.out.println("- Create reports on project meetings");
		System.out.println("- View report from project meeting");
		System.out.println();
		System.out.println("Log out");

		String userChoise = input.readLine();
		if (userChoise.equals("Register spent time")) {
			registerSpentTime();
		} else if (userChoise.equals("Ask colleague for assistance")) {
			askColleagueForAssistance();
		} else if (userChoise.equals("Remove assisting colleague")) {
			removeAssistingColleague();
		} else if (userChoise
				.equals("Register vacation, sick-days and course attendance")) {
			registerVSC();
		} else if (userChoise.equals("See registered spent time")) {
			registeredSpentTime();
		} else if (userChoise.equals("Assign employee to project")) {
			assignEmployeeProject();
		} else if (userChoise.equals("Assign employee to activity")) {
			assignEmployeeActivity();
		} else if (userChoise.equals("Create an activity")) {
			createActivity();
		} else if (userChoise.equals("Get Project Statistics")) {
			getStatistics();
		} else if (userChoise.equals("Relieve employee from project")) {
			relieveEmployeeProject();
		} else if (userChoise.equals("See available employees")) {
			seeAvailableEmployees();
		} else if (userChoise.equals("Create reports on project meetings")) {
			reportsOnProjectMeetings();
		} else if (userChoise.equals("View report from project meeting")) {
			viewReport();
		} else if (userChoise.equals("Log out")) {
			company.employeeLogout();
			initialScreen();
		} else {
			System.out.println("Incorrect command. Try Again.\n");
			employeeScreen();
		}
	}

	private void viewReport() throws IOException, OperationNotAllowedException {
		System.out.print("Enter Project Name: ");
		String project = input.readLine();

		System.out.print("Enter Report Name: ");
		String report = input.readLine();

		System.out.println(company.getSpecificProject(project)
				.getSpecificReportByName(report).getContent());

		employeeScreen();
	}

	private void reportsOnProjectMeetings() throws IOException,
			OperationNotAllowedException {
		System.out.print("Enter Project Name: ");
		String project = input.readLine();

		System.out.print("Enter Report Name: ");
		String report = input.readLine();

		System.out.print("Enter Report Date: ");
		int date = Integer.parseInt(input.readLine());

		System.out.print("Enter Report Month: ");
		int month = Integer.parseInt(input.readLine());

		System.out.print("Enter Report Year: ");
		int year = Integer.parseInt(input.readLine());

		company.getLoggedInEmployee().writeReport(
				company.getSpecificProject(project), report, year, month, date);
		System.out.println("Report created");

		System.out.println("Enter Report Content:");
		String content = input.readLine();

		company.getLoggedInEmployee().editReport(
				company.getSpecificProject(project).getSpecificReportByName(
						report), content);

		employeeScreen();
	}

	private void seeAvailableEmployees() throws IOException,
			OperationNotAllowedException {
		System.out.print("Enter Period Start Date: ");
		int startDate = Integer.parseInt(input.readLine());

		System.out.print("Enter Period Start Month: ");
		int startMonth = Integer.parseInt(input.readLine());

		System.out.print("Enter Period Start Year: ");
		int startYear = Integer.parseInt(input.readLine());

		GregorianCalendar start = new GregorianCalendar(startYear, startMonth,
				startDate);

		System.out.print("Enter Period End Date: ");
		int endDate = Integer.parseInt(input.readLine());

		System.out.print("Enter Period End Month: ");
		int endMonth = Integer.parseInt(input.readLine());

		System.out.print("Enter Period End Year: ");
		int endYear = Integer.parseInt(input.readLine());

		GregorianCalendar end = new GregorianCalendar(endYear, endMonth,
				endDate);

		List<Employee> employees = company.getAvailableEmployees(start, end);

		System.out.println("Available Employees with period:" + startDate + "/"
				+ startMonth + "/" + startYear + " " + endDate + "/" + endMonth
				+ "/" + endYear);
		for (Employee e : employees) {
			System.out.println("ID: " + e.getID() + " Department: "
					+ e.getDepartment());
		}

		employeeScreen();
	}

	private void registeredSpentTime() throws IOException, OperationNotAllowedException {
		System.out.print("Enter Activity ID: ");
		String activity = input.readLine();

		try{
			int time = company.getLoggedInEmployee().getSpentTime(activity);
			System.out.println(activity + ": "+ company.getLoggedInEmployee().getSpentTime(activity)+" hours(s)");
			System.out.println();
		} catch (Exception e){
			System.out.println(""+e.getMessage());
			System.out.println();
			registeredSpentTime();
		}
		employeeScreen();
	}

	private void removeAssistingColleague() throws IOException,	OperationNotAllowedException {
		System.out.print("Enter Project Name: ");
		String project = input.readLine();

		System.out.print("Enter Activity Name: ");
		String activity = input.readLine();

		System.out.print("Enter Employee ID: ");
		String id = input.readLine();
		Employee em = company.getEmployee(id);

		if (em != null) {
			company.getLoggedInEmployee().removeAssistingEmployee(em,
					company.getSpecificProject(project).getActivity(activity));
			System.out.println("Assisting Employee Removed");
		}

		employeeScreen();
	}

	private void askColleagueForAssistance() throws IOException,
			OperationNotAllowedException {
		System.out.print("Enter Project Name: ");
		String project = input.readLine();

		System.out.print("Enter Activity Name: ");
		String activity = input.readLine();

		System.out.print("Enter Employee ID: ");
		String id = input.readLine();
		Employee em = company.getEmployee(id);

		if (em != null) {
			company.getLoggedInEmployee().requestAssistance(em,
					company.getSpecificProject(project).getActivity(activity));
			System.out.println("Employee added to assist.");
		}

		employeeScreen();
	}

	private void registerVSC() throws IOException, OperationNotAllowedException {
		System.out.println("Avaliable types of Activity:");
		System.out.println("- Vacation");
		System.out.println("- Sick");
		System.out.println("- Course");
		System.out.println();
		System.out.print("Enter type of Activity: ");

		String type = input.readLine();

		System.out.print("Enter Start Date: ");
		int startDate = Integer.parseInt(input.readLine());

		System.out.print("Enter Start Month: ");
		int startMonth = Integer.parseInt(input.readLine());

		System.out.print("Enter Start Year: ");
		int startYear = Integer.parseInt(input.readLine());

		System.out.print("Enter End Date: ");
		int endDate = Integer.parseInt(input.readLine());

		System.out.print("Enter End Month: ");
		int endMonth = Integer.parseInt(input.readLine());

		System.out.print("Enter End Year: ");
		int endYear = Integer.parseInt(input.readLine());

		if (type.equals("Vacation") || type.equals("vacation")) {
			company.getLoggedInEmployee().registerVacationTime(startYear,
					startMonth, startDate, endYear, endMonth, endDate);
		}

		if (type.equals("Sick") || type.equals("sick")) {
			company.getLoggedInEmployee().registerSickTime(startYear,
					startMonth, startDate, endYear, endMonth, endDate);
		}

		if (type.equals("Course") || type.equals("course")) {
			company.getLoggedInEmployee().registerCourseTime(startYear,
					startMonth, startDate, endYear, endMonth, endDate);
		}

		employeeScreen();
	}

	private void registerSpentTime() throws IOException, OperationNotAllowedException {
		System.out.print("Enter Activity ID: ");
		String activity = input.readLine();
		
		System.out.print("Enter the number of total hours: ");
		String timeInput = input.readLine();
		while(!timeInput.matches("[0-9]+")){
			System.out.println("Spent time must be a number");
			System.out.print("Enter the number of total hours: ");
			timeInput = input.readLine();
		}
		int time = Integer.parseInt(timeInput);

		try {
			company.getLoggedInEmployee().registerSpentTime(activity, time);
			System.out.println("The spent time on the activity \"" + activity + "\" has been set to " + time);
			System.out.println();
		} catch (Exception e) {
			System.out.println(""+e.getMessage());
			registerSpentTime();
		}
		employeeScreen();
	}

	private void relieveEmployeeProject() throws IOException,
			OperationNotAllowedException {
		System.out.print("Enter Project Name: ");
		String project = input.readLine();

		System.out.print("Enter Employee ID: ");
		String id = input.readLine();
		Employee em = company.getEmployee(id);

		if (em != null) {
			company.getLoggedInEmployee().relieveEmployeeProject(em,
					company.getSpecificProject(project));
			System.out.println("Employee relieved form project");
		}
		employeeScreen();
	}

	private void getStatistics() throws OperationNotAllowedException,
			IOException {
		System.out.print("Enter Project Name: ");
		String project = input.readLine();

		List<String> statistics = company.getLoggedInEmployee()
				.getStatisticsProject(company.getSpecificProject(project));

		for (String s : statistics) {
			System.out.println(s);
		}
		employeeScreen();
	}

	private void createActivity() throws NumberFormatException, IOException, OperationNotAllowedException {
		Project project = findProject();

		System.out.print("Enter Activity ID: ");
		String activity = input.readLine();

		GregorianCalendar start = checkStartDate();
		GregorianCalendar end = checkEndDate();
		try{
			company.getLoggedInEmployee().createActivity(project, activity, start, end);
			System.out.println("The activity "+project.getID()+"-"+activity+" has been created");
			System.out.println();
			employeeScreen();
		} catch (Exception e){
			System.out.println(""+e.getMessage());
			System.out.println();
			createActivity();
		}
	}

	private void assignEmployeeActivity() throws IOException, OperationNotAllowedException {
		System.out.print("Enter Employee ID: ");
		String id = input.readLine();

		System.out.print("Enter Activity ID: ");
		String activity = input.readLine();

		try{
			company.getLoggedInEmployee().assignEmployeeActivity(id, activity);
			System.out.println(id+" has been assigned to the activity "+activity);
			System.out.println();
			employeeScreen();
		} catch (Exception e){
			System.out.println(""+e.getMessage());
			System.out.println();
			assignEmployeeActivity();
		}
	}

	private void assignEmployeeProject() throws OperationNotAllowedException,IOException {
		Employee employee = findEmployee();
		Project project = findProject();
		
		try{
			company.getLoggedInEmployee().assignEmployeeProject(employee, project);
			System.out.println(employee.getID()+" has been assigned to project "+project.getID());
			System.out.println();
			employeeScreen();
		} catch (Exception e){
			System.out.println(""+e.getMessage());
			System.out.println();
		}
	}

	private Employee findEmployee() throws IOException,
			OperationNotAllowedException {
		System.out.print("Enter Employee ID: ");
		String id = input.readLine();
		Employee projectLeader = company.getEmployee(id);
		if (projectLeader == null) {
			System.out.println("An employee with the given ID could not be found");
			System.out.println();
			assignProjectLeader();
		}
		return projectLeader;
	}

	private Project findProject() throws IOException {
		Project project;
		System.out.print("Enter Project ID: ");
		String projectInput = input.readLine();
		if (projectInput.matches("[0-9]+")) {
			int ID = Integer.parseInt(projectInput);
			project = company.getSpecificProject(ID);
		} else {
			project = company.getSpecificProject(projectInput);
		}
		if (project == null) {
			System.out.println("A project with the given ID could not be found");
			System.out.println();
			findProject();
		}
		return project;
	}
	
	private GregorianCalendar checkEndDate() throws IOException {
		int endDate, endMonth, endYear;
		while (true) {
			System.out.print("Enter End Date: ");
			String date = input.readLine();
			while (!date.matches("[0-9]+")) {
				System.out.println("Date must be a number");
				System.out.print("Enter End Date: ");
				date = input.readLine();
			}
			endDate = Integer.parseInt(date);

			System.out.print("Enter End Month: ");
			String month = input.readLine();
			while (!month.matches("[0-9]+")) {
				System.out.println("Month must be a number");
				System.out.print("Enter End Month: ");
				month = input.readLine();
			}
			endMonth = Integer.parseInt(month);

			System.out.print("Enter End Year: ");
			String year = input.readLine();
			while (!year.matches("[0-9]+")) {
				System.out.println("Year must be a number");
				System.out.print("Enter End Year: ");
				year = input.readLine();
			}
			endYear = Integer.parseInt(year);

			try {
				company.checkForInvalidDate(endYear, endMonth - 1, endDate);
				System.out.println();
				break;
			} catch (Exception e) {
				System.out.println("Invalid information");
				System.out.println();
			}
		}
		GregorianCalendar end = new GregorianCalendar(endYear, endMonth - 1, endDate, 0, 0, 0);
		return end;
	}

	private GregorianCalendar checkStartDate() throws IOException {
		int startDate, startMonth, startYear;
		while (true) {
			System.out.print("Enter Start Date: ");
			String date = input.readLine();
			while (!date.matches("[0-9]+")) {
				System.out.println("Date must be a number");
				System.out.print("Enter Start Date: ");
				date = input.readLine();
			}
			startDate = Integer.parseInt(date);

			System.out.print("Enter Start Month: ");
			String month = input.readLine();
			while (!month.matches("[0-9]+")) {
				System.out.println("Month must be a number");
				System.out.print("Enter Start Month: ");
				month = input.readLine();
			}
			startMonth = Integer.parseInt(month);

			System.out.print("Enter Start Year: ");
			String year = input.readLine();
			while (!year.matches("[0-9]+")) {
				System.out.println("Year must be a number");
				System.out.print("Enter Start Year: ");
				year = input.readLine();
			}
			startYear = Integer.parseInt(year);

			try {
				company.checkForInvalidDate(startYear, startMonth - 1,startDate);
				System.out.println();
				break;
			} catch (Exception e) {
				System.out.println("Invalid information");
				System.out.println();
			}
		}
		GregorianCalendar start = new GregorianCalendar(startYear, startMonth - 1, startDate, 0, 0, 0);
		return start;
	}
}
