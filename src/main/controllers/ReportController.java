package main.controllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import main.apps.Employee;
import main.apps.MenuItem;
import main.apps.ReservationTable;
import main.services.DbBuildConnection;

public class ReportController {
	
	MainController main;
	ArrayList<Employee> employees;
	ArrayList<MenuItem> items;
	Employee chosenEmployee;
	DbBuildConnection dbConn = new DbBuildConnection();
	
	@FXML private ComboBox<Employee> empChoser;
	@FXML private TableView<MenuItem> itemsTable;
	@FXML private TableColumn<MenuItem, String> nameColumn;
	@FXML private TableColumn<MenuItem, String> priceColumn;
	@FXML private TableColumn<MenuItem, String> quantityColumn;
	@FXML private TableColumn<MenuItem, String> totalColumn;
	@FXML private Text totalSales;
	@FXML private VBox vbox;
	
	
	public void initialize(MainController main) {
		this.main = main;
		this.employees = dbConn.getStaff();
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
		empChoser.setConverter(new StringConverter<Employee>() {
		    @Override
		    public String toString(Employee object) {
		    	if (object == null) {
                    return "";
                } else {
                    return object.getName();
                }
		    }

		    @Override
		    public Employee fromString(String string) {
		        return null;
		    }
		});
		

		
		if(this.main.employee.getPosition().equals("Manager")) {
			for (Employee emp: this.employees)
				empChoser.getItems().add(emp);
		}
		
		else {
			empChoser.getItems().add(this.main.employee);
		}
		
		
		
		
	}
	
	public void onChoiceChange() {
		this.chosenEmployee = empChoser.getValue();
		if(this.chosenEmployee != null) {
			this.items = dbConn.getEmployeeSaleReport(this.chosenEmployee.getEmployeeId());
			this.updateGui();
		}
	}
	
	public void updateGui() {
		this.itemsTable.getItems().clear();
		double total = 0;
		for (MenuItem item: this.items) {
			this.itemsTable.getItems().add(item);
			total += item.getTotal();
		}
		this.main.lastAction.setText("Report Viewed");
		total = Math.round(total * 100.0) / 100.0;
		totalSales.setText("$ " + String.valueOf(total));
	}
	
	public void print() {
		PrinterJob pj = PrinterJob.createPrinterJob();
		
		if (this.chosenEmployee!=null) {
			
			if (pj != null && pj.showPrintDialog(vbox.getScene().getWindow())){
			    boolean success = pj.printPage(vbox);
			    if (success) {
			        pj.endJob();
			    }
			}
			this.main.lastAction.setText("Report Printed");
			
		}
		
		else {
			this.main.lastAction.setText("No Employee Selected");
		}
		
		
		    
	}
}
