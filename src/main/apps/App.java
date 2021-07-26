package main.apps;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.controllers.LoginController;
import main.controllers.MainController;

public class App extends Application {
  Scene scene = new Scene(new BorderPane());
  public static void main(String[] args) { 
	  launch(args); 
  }
  @Override 
  public void start(Stage stage) throws IOException {
    
    showLoginScreen();
    stage.setTitle("Tasty Indian Bistro - POS");
    stage.setResizable(false);
    stage.setScene(scene);
    stage.show();
  }
  
  public void showLoginScreen() {
	    try {
	      FXMLLoader loader = new FXMLLoader(
	        getClass().getResource("/resources/views/Login.fxml")
	      );
	      scene.setRoot((Parent) loader.load());
	      LoginController controller = loader.<LoginController>getController();
	      controller.initialize(this);
	    } catch (IOException ex) {
	      System.out.println(ex);
	    }
  }
  
  public void showMainScreen(String[] empData) {
	  try {
	      FXMLLoader loader = new FXMLLoader(
	        getClass().getResource("/resources/views/Main.fxml")
	      );
		  scene.setRoot((Parent) loader.load());
	      MainController controller = loader.<MainController>getController();
	      controller.initialize(this, empData);
	    } catch (IOException ex) {
	      System.out.println(ex);
	    }
  }
  
}
