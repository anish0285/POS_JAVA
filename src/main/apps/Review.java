package main.apps;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import main.controllers.MainController;
import main.controllers.ReviewController;

public class Review extends VBox {
	private Node view;
	public ReviewController controller = new ReviewController();
	
	public Review(MainController main) {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/views/Review.fxml"));
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
