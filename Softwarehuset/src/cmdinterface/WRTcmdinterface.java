/**
 * 
 */
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
 * @author Gruppe 25
 *
 */
public class WRTcmdinterface {

	/**
	 * @param args
	 * @throws IOException 
	 */
	Address address = new Address("Kongens Lyngby", "Anker Engelunds Vej");
	Company company = new Company("Softwarehuset A/S", address);
	Executive executive = new Executive("ex01", "Executive", company, "password");
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws IOException, OperationNotAllowedException {
		//Print initial UI
		new WRTcmdinterface().initialScreen();
	}
	
	private void executiveScreen() throws IOException, OperationNotAllowedException {
		// TODO Auto-generated method stub
		System.out.println("Executive logged in");
		System.out.println("Executive options");
		System.out.println("- Add Employee (Company Database)");
		System.out.println("- Create Project");
		System.out.println("- Assign Project Leader");
		System.out.println("- Log out");
		System.out.println();
		String inputString = input.readLine();
		String[] commands = inputString.split(" ");
		if(commands[0].equals("Add") && commands[1].equals("Employee")) {
			addEmployee();
		}
		
		if(commands[0].equals("Create") && commands[1].equals("Project")) {
			createProject();
		}
		
		if(commands[0].equals("Assign") && commands[1].equals("Project") 
				&& commands[2].equals("Leader")) {
			assignProjectLeader();
		}
		
		if(commands[0].equals("Log") && commands[1].equals("out")) {
			initialScreen();
		}
		
	}
	
	private void assignProjectLeader() throws IOException, OperationNotAllowedException {
		// TODO Auto-generated method stub
		System.out.print("Enter Employee ID: ");
		String id = input.readLine();
		Employee projectLeader = company.getEmployee(id);
		
		System.out.print("Enter Project ID: ");
		String project = input.readLine();
		Project currentProject = company.getSpecificProject(project);
		
		if(projectLeader != null && currentProject != null) {
			executive.assignProjectLeader(projectLeader, currentProject);
			System.out.println("Assignment Complete");
		}
		
		executiveScreen();
		
	}

	private void createProject() throws IOException, OperationNotAllowedException {
		// TODO Auto-generated method stub
		System.out.print("Enter project name: ");
		String projectName = input.readLine();
		
		System.out.print("Enter Start Date: ");
		int startDate = Integer.parseInt(input.readLine());
		
		System.out.print("Enter Start Month: ");
		int startMonth = Integer.parseInt(input.readLine());
		
		System.out.print("Enter Start Year: ");
		int startYear = Integer.parseInt(input.readLine());
		
		GregorianCalendar start = new GregorianCalendar(startYear, startMonth, startDate);
		
		System.out.print("Enter End Date: ");
		int endDate = Integer.parseInt(input.readLine());
		
		System.out.print("Enter End Month: ");
		int endMonth = Integer.parseInt(input.readLine());
		
		System.out.print("Enter End Year: ");
		int endYear = Integer.parseInt(input.readLine());
		
		GregorianCalendar end = new GregorianCalendar(endYear, endMonth, endDate);
		
		company.createProject(projectName, start, end);
		System.out.println("Project created");
		executiveScreen();
	}

	private void addEmployee() throws IOException, OperationNotAllowedException {
		// TODO Auto-generated method stub
		System.out.print("Enter Employee ID: ");
		String id = input.readLine();
		
		System.out.print("Enter Deparment: ");
		String department = input.readLine();
		
		System.out.println("Default password for " + id + " is password");
		company.createEmployee(id, "password", department);
		System.out.println();
		
		executiveScreen();
	}

	private void initialScreen() throws IOException, OperationNotAllowedException {
		System.out.println("Work Registration Tool 0.1");
		System.out.println("Softwarehuset A/S 2015");
		System.out.println("To login type \"Login\" with the username and a password (Regular user)");
		System.out.println("To login as executive type \"Login\" with just a password");
		System.out.println("To exit the system. Type \"Exit\"");
		System.out.println();
		String inputString = input.readLine();
		String[] commands = inputString.split(" ");
		if(commands[0].equals("Login") && commands.length == 3) {
			company.employeeLogin(commands[1], commands[2]);
			employeeScreen();
		}
		if(commands[0].equals("Login") && commands.length == 2) {
			company.executiveLogin(commands[1]);
			executiveScreen();
			
		}
		if(commands[0].equals("Exit")) {
			System.exit(0);
		}
	}

	private void employeeScreen() throws IOException, OperationNotAllowedException {
		// TODO Auto-generated method stub
		System.out.println("User: " + company.getLoggedInEmployee().getID() 
				+ company.getLoggedInEmployee().getDepartment());
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
		System.out.println("- Create An Activity");
		System.out.println("- Get Project Statistics");
		System.out.println("- Relieve Employee from project");
		System.out.println("- See available employees");
		System.out.println("- Reports on project meetings");
		System.out.println();
		System.out.println("Log out");
		
		String userChoise = input.readLine();
		if(userChoise.equals("Register spent time")) {
			registerSpentTime();
		}
		if(userChoise.equals("Ask colleague for assistance")) {
			askColleagueForAssistance();
		}
		if(userChoise.equals("Remove assisting colleague")) {
			removeAssistingColleague();
		}
		if(userChoise.equals("Register vacation, sick-days and course attendance")) {
			registerVSC();
		}
		if(userChoise.equals("See registered spent time")) {
			
		}
		if(userChoise.equals("Assign employee to project")) {
			assignEmployeeProject();
		}
		if(userChoise.equals("Assign employee to activity")) {
			assignEmployeeActivity();
		}
		if(userChoise.equals("Create An Activity")) {
			createActivity();
		}
		if(userChoise.equals("Get Project Statistics")) {
			getStatistics();
		}
		if(userChoise.equals("Relieve Employee from project")) {
			relieveEmployeeProject();
		}
		if(userChoise.equals("See available employees")) {
			
		}
		if(userChoise.equals("Reports on project meetings")) {
			
		}
		if(userChoise.equals("Log out")) {
			company.employeeLogout();
			initialScreen();
		}
	}

	private void removeAssistingColleague() throws IOException, OperationNotAllowedException {
		// TODO Auto-generated method stub
		System.out.print("Enter Project ID: ");
		String project = input.readLine();
		
		System.out.print("Enter Activity ID: ");
		String activity = input.readLine();
		
		System.out.print("Enter Employee ID: ");
		String id = input.readLine();
		Employee em = company.getEmployee(id);
		
		if(em != null) {
			company.getLoggedInEmployee().removeSpecificAssistingEmployee(em, company.getSpecificProject(project)
					.getSpecificActivityByName(activity));
			System.out.println("Employee added to assist.");
		}
		
		employeeScreen();
	}

	private void askColleagueForAssistance() throws IOException, OperationNotAllowedException {
		System.out.print("Enter Project ID: ");
		String project = input.readLine();
		
		System.out.print("Enter Activity ID: ");
		String activity = input.readLine();
		
		System.out.print("Enter Employee ID: ");
		String id = input.readLine();
		Employee em = company.getEmployee(id);
		
		if(em != null) {
			company.getLoggedInEmployee().needForAssistanceWithActivity(em, company.getSpecificProject(project)
					.getSpecificActivityByName(activity));
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
		
		if(type.equals("Vacation")) {
			company.getLoggedInEmployee().registerVacationTime(startYear, startMonth, startDate, endYear, endMonth, endDate);
		}
		
		if(type.equals("Sick")) {
			company.getLoggedInEmployee().registerSickTime(startYear, startMonth, startDate, endYear, endMonth, endDate);
		}
		
		if(type.equals("Course")) {
			company.getLoggedInEmployee().registerCourseTime(startYear, startMonth, startDate, endYear, endMonth, endDate);
		}
		
		employeeScreen();
	}

	private void registerSpentTime() throws IOException, OperationNotAllowedException {
		System.out.print("Enter Project ID: ");
		String project = input.readLine();
		
		System.out.print("Enter Activity ID: ");
		String activity = input.readLine();
		
		System.out.print("Enter the number of total hours: ");
		int time = Integer.parseInt(input.readLine());
		
		company.getLoggedInEmployee().registerSpentTime(company.getSpecificProject(project)
				.getSpecificActivityByName(activity), time);
		
		employeeScreen();
	}

	private void relieveEmployeeProject() throws IOException, OperationNotAllowedException {
		System.out.print("Enter Project ID: ");
		String project = input.readLine();
		
		System.out.print("Enter Employee ID: ");
		String id = input.readLine();
		Employee em = company.getEmployee(id);
		
		if(em != null) {
			company.getLoggedInEmployee().relieveEmployeeProject(em, company.getSpecificProject(project));
			System.out.println("Employee relieved form project");
		}
		employeeScreen();
	}

	private void getStatistics() throws OperationNotAllowedException, IOException {
		System.out.print("Enter Project ID: ");
		String project = input.readLine();
		
		List<String> statistics = company.getLoggedInEmployee().
				getStatisticsProject(company.getSpecificProject(project));
		
		for(String s : statistics) {
			System.out.println(s);
		}
		employeeScreen();
	}

	private void createActivity() throws NumberFormatException, IOException, OperationNotAllowedException {
		System.out.print("Enter Project ID: ");
		String project = input.readLine();
		
		System.out.print("Enter Activity ID: ");
		String activity = input.readLine();
		
		System.out.print("Enter Start Date: ");
		int startDate = Integer.parseInt(input.readLine());
		
		System.out.print("Enter Start Month: ");
		int startMonth = Integer.parseInt(input.readLine());
		
		System.out.print("Enter Start Year: ");
		int startYear = Integer.parseInt(input.readLine());
		
		GregorianCalendar start = new GregorianCalendar(startYear, startMonth, startDate);
		
		System.out.print("Enter End Date: ");
		int endDate = Integer.parseInt(input.readLine());
		
		System.out.print("Enter End Month: ");
		int endMonth = Integer.parseInt(input.readLine());
		
		System.out.print("Enter End Year: ");
		int endYear = Integer.parseInt(input.readLine());
		
		GregorianCalendar end = new GregorianCalendar(endYear, endMonth, endDate);
		
		company.getLoggedInEmployee().createActivity(
				company.getSpecificProject(project), activity, start, end);
		
		employeeScreen();
		
	}

	private void assignEmployeeActivity() throws IOException, OperationNotAllowedException {
		System.out.print("Enter Employee ID: ");
		String id = input.readLine();
		Employee em = company.getEmployee(id);
		
		System.out.print("Enter Project ID: ");
		String project = input.readLine();
		
		System.out.print("Enter Activity ID: ");
		String activity = input.readLine();
		
		if(em != null) {
			company.getLoggedInEmployee().assignEmployeeActivity(em, 
					company.getSpecificProject(project).getSpecificActivityByName(activity));
			System.out.println("Employee Assigned");
		}
		employeeScreen();
				
	}

	private void assignEmployeeProject() throws OperationNotAllowedException, IOException {
		System.out.print("Enter Employee ID: ");
		String id = input.readLine();
		Employee em = company.getEmployee(id);
		
		System.out.print("Enter Project ID: ");
		String project = input.readLine();
		if(em != null) {
			company.getLoggedInEmployee().assignEmployeeProject(em, company.getSpecificProject(project));
			System.out.println("Employee Assigned");
		}
		employeeScreen();
	}

}
