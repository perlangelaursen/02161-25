package softwarehuset;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Company {
	private String name;
	private Address address;
	private Executive executive;
	private boolean executiveLoggedIn;
	private Employee loggedInEmployee;
	private List<Project> projects = new ArrayList<>();
	private List<Employee> employees = new ArrayList<>();
	//private DateServer dateServer;

	public Company(String name, Address address) {
		this.name = name;
		this.address = address;
		//dateServer = new DateServer();
	}

	public void setExecutive(Executive executive) {
		this.executive = executive;
	}

	public boolean executiveIsLoggedIn() {
		return executiveLoggedIn;
	}

	public void executiveLogin(String password) {
		if (password == executive.getPassword()) {
			executiveLoggedIn = true;
			executive.setLoginStatus(executiveLoggedIn);
		}
	}

	public Project createProject(String name) throws OperationNotAllowedException {
		if (!executiveIsLoggedIn()) {
			throw new OperationNotAllowedException("Create project operation is not allowed if not executive.", "Create project");
		}
		Project p = new Project(name);
		projects.add(p);
		return p;
	}
	
	public void createProject(String name, GregorianCalendar start, GregorianCalendar end) throws OperationNotAllowedException {
		if (!executiveIsLoggedIn()) {
			throw new OperationNotAllowedException("Create project operation is not allowed if not executive.", "Create project");
		}
		if (start.after(end)){
			throw new OperationNotAllowedException("The end date is set before the start date", "Create project");
		}
		Project p = new Project(name, start, end);
		projects.add(p);
		
	}
	public Employee createEmployee(String id, String password, String department) {
		Employee e = new Employee(id, password, this, department);
		employees.add(e);
		return e;
	}
	public List<Project> getProjects() {
		return projects;
	}
	
	public Project getSpecificProject(String name){
		for (Project p : projects) {
			if (p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}

	public void employeeLogin(String id, String password) {
		for(Employee e: employees){
			if (e.getID().equals(id)){
				if(e.getPassword().equals(password)){
					executiveLoggedIn = false;
					loggedInEmployee = e;
					break;
				}
			}
		}
	}

	public Employee getLoggedInEmployee() {
		return loggedInEmployee;
	}

//	public Calendar getCurrentTime() {
//		return dateServer.getCurrentDate();
//	}
//	
//	public void setDateServer(DateServer dateServer) {
//		this.dateServer = dateServer;
//	}
}
