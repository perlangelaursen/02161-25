package softwarehuset;

public class Executive {

	private String name, department, password;
	private Company com;
	private Project p1;
	
	public Executive(String name, String department, Company com, String password) {
		this.name = name;
		this.department = department;
		this.com = com;
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}

	public void setProjectLeader(Employee em, Project p1) throws NotLoggedInException {
		p1.setProjectleader(em);
		
	}


}
