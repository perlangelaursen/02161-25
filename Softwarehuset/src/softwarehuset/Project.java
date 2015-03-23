package softwarehuset;

import java.util.GregorianCalendar;

public class Project {
	private String name;
	private GregorianCalendar start, end;
	
	public Project(String name) {
		this.name = name;
	}
	
	public Project(String name, GregorianCalendar start, GregorianCalendar end) {
		this(name);
		this.start = start;
		this.end = end;
	}



}
