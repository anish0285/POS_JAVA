package main.apps;

import main.controllers.HomeController;
import main.controllers.MainController;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class Home extends VBox{
	private Node view;
	public HomeController controller = new HomeController();
	
	
	public Home(MainController main){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/views/Home.fxml"));
        fxmlLoader.setController(controller);
        try {
            view = (Node) fxmlLoader.load();
            controller.initialize(main);

        } catch (IOException ex) {
        	System.out.println(ex);
        }
        getChildren().add(view);
	}
}
