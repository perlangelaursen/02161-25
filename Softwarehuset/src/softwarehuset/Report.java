package softwarehuset;

import java.util.GregorianCalendar;

public class Report {
	private Project project;
	private GregorianCalendar date;
	private String name;
	private String content;
	
	public Report(Project project, String name, GregorianCalendar date) {
		this.project = project;
		this.name = name;
		this.date = date;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setContent(String content){
		this.content = content;
	}
	public void setDate(GregorianCalendar date){
		this.date = date;
	}
	public void setProject(Project project){
		this.project = project;
	}
	public String getContent() {
		return content;
	}
	public String getName() {
		return name;
	}
	public GregorianCalendar getDate(){
		return date;
	}
	public Project getProject(){
		return project;
	}
}
