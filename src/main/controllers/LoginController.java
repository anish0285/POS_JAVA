package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.apps.App;
import main.apps.Employee;
import main.services.DbBuildConnection;

/** Controls the login screen */
public class LoginController {
  @FXML private TextField employeeId;
  @FXML private PasswordField pin;
  @FXML private Text info;
  
  App app;
  
  public void initialize(App app) {
	  this.app = app;
  }
  
  // if user is authorized shows main screen else shows error text
  public void userLogin() {
	  info.setText("Verifying...");
	  DbBuildConnection dbConn = new DbBuildConnection();
	  Employee employee = dbConn.loginVerification(employeeId.getText(), pin.getText());
	  if(employee != null) {
		  app.showMainScreen(employee);
	  }
	  else {
		  info.setFill(Color.RED);
		  info.setText("Wrong Credentials!");
	  }
  }
  
  }
