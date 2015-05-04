/**
 * 
 */
package cmdinterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
		company.createProject(projectName);
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
			
		}
		if(userChoise.equals("Ask colleague for assistance")) {
			
		}
		if(userChoise.equals("Register vacation, sick-days and course attendance")) {
			
		}
		if(userChoise.equals("See registered spent time")) {
			
		}
		if(userChoise.equals("Assign employee to project")) {
			
		}
		if(userChoise.equals("Assign employee to activity")) {
			
		}
		if(userChoise.equals("Create An Activity")) {
			
		}
		if(userChoise.equals("Get Project Statistics")) {
			
		}
		if(userChoise.equals("Relieve Employee from project")) {
			
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

}
