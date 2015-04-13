package softwarehuset;

import java.util.Date;

public class Project {

	private String name;
	private Date start, end;
	private Employee projectleader;
	private Company com;
	private Executive ex;
	
	public Project(String name, Date start, Date end) {
		this.name = name;
		this.start = start;
		this.end = end;
	}

	public void setProjectleader(Employee em) throws NotLoggedInException {
		com.executiveLogin(ex.getPassword());
		if (!com.executiveIsLoggedIn()) {
			throw new NotLoggedInException("Executive Not Logged In", "Login Fail");
		}
		projectleader = em;
		
	}
	
	public Employee getProjectleader() {
		return projectleader;
	}

}
