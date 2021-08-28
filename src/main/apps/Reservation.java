package main.apps;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import main.controllers.MainController;
import main.controllers.ReservationController;

public class Reservation extends VBox{
	
	private Node view;
	public ReservationController controller = new ReservationController();
	
	
	public Reservation(MainController main){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/views/Reservation.fxml"));
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


