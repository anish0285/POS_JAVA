package main.services;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

import main.apps.Employee;
import main.apps.MenuItem;
import main.apps.Transaction;
import main.controllers.MainController;

public class DbBuildConnection {
	
	Connection con;
	// JDBC driver name and database URL
	String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	String DB_URL = "jdbc:mysql://localhost:3306/pos_db?useSSL=false";
	// Database credentials
	String USER = "root";
	String PASS = "password";
	
	public DbBuildConnection() {
		try{  
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(DB_URL,USER,PASS); 
		}
		catch(Exception e){
			System.out.println(e);
		}  
	}
	
	// if authorized returns String array with employee data else returns null
	public Employee loginVerification(String employeeId, String pin) {
		String query = "SELECT * FROM employee WHERE employeeId=" + employeeId + " and pin="+pin;
		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query); 
	        if (rs.next()) {
	            return new Employee(rs.getString("EmployeeId"), rs.getString("Name"), rs.getString("Position"));
	        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}
	
	public ArrayList<MenuItem> getSaleItems(MainController main) {
		ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
		String query = "SELECT * FROM item";
		Statement st;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				MenuItem item = new MenuItem(main, rs.getString("ItemID"), rs.getString("Name"), rs.getDouble("Price"), 0, rs.getBlob("Image").getBinaryStream());
				menuItems.add(item);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return menuItems;
	}

	public String getNewTransId() {
		String query = "SELECT TransactionID FROM transaction ORDER BY TransactionID DESC LIMIT 1;";
		Statement st;
		int id = 9192;
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				id = rs.getInt("TransactionID") + 1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(id);
	}
	
	public void saveTransaction(Transaction transaction) {
		String query_one = String.format("INSERT INTO transaction VALUES ('%s' , '%s' , '%s')", transaction.transId, transaction.controller.getTime(), transaction.controller.getOrderType());
		String query_two = "INSERT INTO transaction_items VALUES ";
		for (MenuItem item : transaction.transItems) {
			query_two += String.format("('%s' , '%s' , '%s')", transaction.transId, item.itemId, String.valueOf(item.quantity));
			if(item != transaction.transItems.get(transaction.transItems.size()-1)) {
				query_two += " ,";
			}
		}
		Statement st;
		try {
			st = con.createStatement();
			st.execute(query_one);
			st.execute(query_two);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	public ArrayList<Transaction> getOldTransactions(MainController main){
		ArrayList<Transaction> transactions = new ArrayList<Transaction>();
		String query_one = "SELECT * FROM transaction";
		String query_two = "SELECT * FROM transaction_items inner join item ON transaction_items.ItemID = item.ItemID WHERE transaction_items.TransactionID = ";
		Statement st_one;
		Statement st_two;
		try {
			st_one = con.createStatement();
			st_two = con.createStatement();
			ResultSet rs_one = st_one.executeQuery(query_one);
			while(rs_one.next()) {
				String transId = rs_one.getString("TransactionID");
				String dateTime = rs_one.getString("Time");
				String orderType = rs_one.getString("OrderType");
				ArrayList<MenuItem> transItems = new ArrayList<MenuItem>();
				ResultSet rs_two = st_two.executeQuery(query_two + transId);
				while(rs_two.next()) {
					String itemId = rs_two.getString("ItemID");
					String itemName = rs_two.getString("Name");
					double itemPrice = rs_two.getDouble("Price");
					int itemQuantity = rs_two.getInt("Quantity");
					InputStream itemImage = rs_two.getBlob("Image").getBinaryStream();
					MenuItem item = new MenuItem(main, itemId, itemName, itemPrice, itemQuantity, itemImage);
					transItems.add(item);
				}
				Transaction transaction = new Transaction(transId, true);
				transaction.controller.setOrderType(orderType);
				transaction.controller.setTime(dateTime);
				transaction.transItems = transItems;
				transaction.controller.updateTable();
				transactions.add(transaction);
			}
			st_one.close();
			st_two.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transactions;
	}
}
