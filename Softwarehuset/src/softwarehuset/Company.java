package softwarehuset;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Company {
	private String name;
	private Address address;
	private Executive executive;
	private boolean executiveLoggedIn;
	private List<Project> projects = new ArrayList<Project>();

	public Company(String name, Address address) {
		this.name = name;
		this.address = address;
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

	public void createProject(String name) throws OperationNotAllowedException {
		if (!executiveIsLoggedIn()) {
			throw new OperationNotAllowedException("Create project operation is not allowed if not executive.", "Create project");
		}
		Project p = new Project(name);
		projects.add(p);
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

	public ArrayList<Project> getProjects() {
		return projects;
	}
	
	public Project getSpecificProject(int i) {
		return projects.get(i);
	}

}
