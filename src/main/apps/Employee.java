package main.apps;

public class Employee {
	private String name;
	private String employeeId;
	private String position;
	
	public Employee(String employeeId, String name, String position) {
		this.employeeId = employeeId;
		this.name = name;
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	
	

}
