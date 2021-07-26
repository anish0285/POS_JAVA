package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import main.apps.Home;
import main.apps.MenuItem;
import main.apps.Transaction;
import main.services.DbBuildConnection;

public class HomeController {
	@FXML private Button addButton;
	@FXML private Button removeButton;
	@FXML private Button clearButton;
	@FXML private Pane transTable;
	
	public DbBuildConnection dbConn = new DbBuildConnection();
	public Transaction transaction;
	public String activeMode = "add";
	
	MainController main;
	
	public void initialize(MainController main) {
		this.main = main;
		this.transaction = new Transaction(this.dbConn.getNewTransId(), false);
		this.transTable.getChildren().add(this.transaction);
		this.updateGui();
	}
	
	public void itemClickHandler(MenuItem item) {
		this.transaction.controller.addRemoveItem(item, activeMode);
		if(activeMode == "add") this.main.lastAction.setText("Item Added");
		if(activeMode == "remove") this.main.lastAction.setText("Item Removed");
	}
	
	public void addItem() {
		this.activeMode = "add";
		this.updateGui();
	}
	
	public void removeItem() {
		this.activeMode = "remove";
		this.updateGui();
	}
	
	public void clearItems() {
		this.transaction.controller.clearTable();
		this.main.lastAction.setText("Transaction Cleared");
	}
	
	public void updateGui() {
		String nonActiveStyle = "-fx-background-color: #bcd8b7;";
		String activeStyle = "-fx-background-color: #32564a; -fx-text-fill: white";
		this.addButton.setStyle(nonActiveStyle);
		this.removeButton.setStyle(nonActiveStyle);
		this.clearButton.setStyle(nonActiveStyle);
		if(this.activeMode == "add") {
			this.addButton.setStyle(activeStyle);
		}
		else if(this.activeMode == "remove") {
			this.removeButton.setStyle(activeStyle);
		}
	}
	
	public void tender() {
		if(this.transaction.transItems.size() != 0) {
			dbConn.saveTransaction(this.transaction);
			this.initialize(this.main);
			this.main.lastAction.setText("Transaction Successful");
		}
	}
}
