package main.controllers;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import main.apps.App;
import main.apps.Home;
import main.apps.MenuItem;
import main.apps.Review;
import main.apps.Transaction;
import main.services.DbBuildConnection;

public class MainController {
	  @FXML private Text empId;
	  @FXML private Text empName;
	  @FXML private Text empPosition;
	  @FXML public Text lastAction;
	  @FXML private GridPane itemsGridPane;
	  @FXML private Button homeButton;
	  @FXML private Button reviewButton;
	  @FXML private Button reservationButton;
	  @FXML private Button reportButton;
	  @FXML private Button functionButton;
	  @FXML private Pane activePane;
	  
	  public ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();	 
	  public String activeTab = "home";
	  
	  public Home home;
	  public Review review;
	  
	  App app;
	  DbBuildConnection dbConn = new DbBuildConnection();
	  
	  public void initialize(App app, String[] empData) {
		  this.app = app;
		  empId.setText(empData[0]);
		  empName.setText(empData[1]);
		  empPosition.setText(empData[2]);
		  this.menuItems = dbConn.getSaleItems(this);
		  addItemsToGridPane();
		  updateGui();
	  }
	  
	  public void userLogout() {
		  app.showLoginScreen();
	  }
	  
	  public void addItemsToGridPane() {
		  int numItems = this.menuItems.size();
		  int itemNum = 0;
		  for (int row = 0; row <= numItems/5; row++) {
			    for(int col = 0; col < 5; col++) {
			    	if(itemNum < numItems) {
			    	   itemsGridPane.add(this.menuItems.get(itemNum), col, row);
			    	}
			    	itemNum = itemNum + 1;
			  }
		  }
	  }
	  
	  public void changeTab(String tabName) {
		  this.activeTab = tabName;
		  this.updateGui();
	  }
	  
	  public void homeClick(ActionEvent e) {
		  this.changeTab("home");
		  this.lastAction.setText("Open home tab");
	  }
	  
	  public void reviewClick() {
		  this.changeTab("review");
		  this.lastAction.setText("Open review tab");
	  }
	  
	  public void reservationClick() {
		  this.changeTab("reservation");
		  this.lastAction.setText("Open reservation tab");
	  }
	  
	  public void reportClick() {
		  this.changeTab("report");
		  this.lastAction.setText("Open report tab");
	  }
	  
	  public void functionClick() {
		  if(this.activeTab == "home") {
			  this.home.controller.tender();
		  }
	  }
	  
	  public void updateGui() {
		  String nonActiveStyle = "-fx-background-color: #dbe9ee;";
		  String activeStyle = "-fx-background-color: #0b7fab; -fx-text-fill: white";
		  this.homeButton.setStyle(nonActiveStyle);
		  this.reviewButton.setStyle(nonActiveStyle);
		  this.reservationButton.setStyle(nonActiveStyle);
		  this.reportButton.setStyle(nonActiveStyle);
		  this.functionButton.setVisible(true);
		  this.activePane.getChildren().clear();
		  if(this.activeTab == "home") {
			  this.homeButton.setStyle(activeStyle);
			  this.functionButton.setText("Tender");
			  this.home = new Home(this);
			  this.activePane.getChildren().add(this.home);
		  }
		  else if(this.activeTab == "review") {
			  this.reviewButton.setStyle(activeStyle);
			  this.functionButton.setText("Refund");
			  this.review = new Review(this);
			  this.activePane.getChildren().add(this.review);
		  }
		  else if(this.activeTab == "reservation") {
			  this.reservationButton.setStyle(activeStyle);
			  this.functionButton.setText("Reserve");
		  }
		  else if(this.activeTab == "report") {
			  this.reportButton.setStyle(activeStyle);
			  this.functionButton.setVisible(false);
		  }
	  }
}
