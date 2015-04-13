package softwarehuset;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Company {

	private String name;
	private	Address add;
	private	Boolean loggedIn = false;
	private	List<Project> projects = new ArrayList<Project>();
	private	Executive ex;
	
	public Company(String name, Address add) {
		this.name = name;
		this.add = add;
	}

	public void createProject(String name, Date start, Date end) {
		Project project = new Project(name, start, end);
		projects.add(project);
		
	}

	public List getProjects() {
		return projects;
	}

	public boolean executiveIsLoggedIn() {
		return loggedIn;
	}

	public void executiveLogin(String password) {
		System.out.println();
		loggedIn = ex.getPassword().equals(password);
		
		/*if (ex.getPassword().equals(password)) {
			System.out.println("password");
			loggedIn = true;
		} else {
			System.out.println("no password");
		}*/
	}

}
