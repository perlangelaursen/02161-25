package softwarehuset;

public class Executive {
	private Company company;
	private String name, department, password;

	public Executive(String name, String department, Company company, String password) {
		this.name = name;
		this.department = department;
		this.company = company;
		company.setExecutive(this);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
}
