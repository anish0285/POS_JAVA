package main.apps;

public class ReservationTable {
	
	private String tableNo;
	private String capacity;
	private String status;
	
	public ReservationTable(String tableNo, String capacity, String status) {
		this.tableNo = tableNo;
		this.capacity = capacity;
		this.status = status;
	}
	
	public String getTableNo() {
		return tableNo;
	}
	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
