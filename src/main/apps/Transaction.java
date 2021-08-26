package main.apps;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import main.controllers.TransactionController;

public class Transaction extends VBox {
	private Node view;
	public TransactionController controller = new TransactionController();
	
	public String transId;
	public ArrayList<MenuItem> transItems = new ArrayList<MenuItem>();
	public Boolean isReview;
	public String empId;
	
	public Transaction(String transId, String empId, Boolean isReview) {
		this.isReview = isReview;
		this.transId = transId;
		this.empId = empId;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/views/Transaction.fxml"));
        fxmlLoader.setController(controller);
        try {
            view = (Node) fxmlLoader.load();
            controller.initialize(this);

        } catch (IOException ex) {
        }
        getChildren().add(view);
	}	
	
	public void getCurrentState() {
		System.out.println("Current state of transaction: ");
		for (int i=0; i<this.transItems.size(); i++) {
			System.out.println(this.transItems.get(i).itemId +"," + this.transItems.get(i).name + "," + this.transItems.get(i).price + "," + this.transItems.get(i).quantity);
		}
	}
}
