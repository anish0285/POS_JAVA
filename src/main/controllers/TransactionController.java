package main.controllers;

import javafx.util.*;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import main.apps.MenuItem;
import main.apps.Transaction;

public class TransactionController {
	@FXML private Text transactionId;
	@FXML private Text dateTime;
	@FXML private Text subtotal;
	@FXML private Text taxes;
	@FXML private Text netAmount;
	@FXML private TableView<MenuItem> itemsTable;
	@FXML private TableColumn<MenuItem, String> nameColumn;
	@FXML private TableColumn<MenuItem, String> priceColumn;
	@FXML private TableColumn<MenuItem, String> quantityColumn;
	@FXML private TableColumn<MenuItem, String> totalColumn;
	@FXML private ToggleGroup orderType;
	
	Transaction transaction;
	
	public void initialize(Transaction transaction) {
		this.transaction = transaction;
		this.transactionId.setText(this.transaction.transId);
		this.orderType.getToggles().forEach(toggle -> {
		    Node node = (Node) toggle ;
		    node.setDisable(this.transaction.isReview);
		});
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
		this.updateTable();
		if(!this.transaction.isReview) initClock();
	}
	
	private void initClock() {
		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	        dateTime.setText(LocalDateTime.now().format(formatter));
	    }), new KeyFrame(Duration.seconds(1)));
	    clock.setCycleCount(Animation.INDEFINITE);
	    clock.play();
	}
	
	public void addRemoveItem(MenuItem item, String command) {
		if(!this.transaction.transItems.contains(item)) {
			this.transaction.transItems.add(item);
		}
		if(command == "add") {
			this.transaction.transItems.get(this.transaction.transItems.indexOf(item)).quantity += 1;
		}
		else if(command =="remove") {
			this.transaction.transItems.get(this.transaction.transItems.indexOf(item)).quantity -= 1;
		}
		else if(command == "refund") {
			this.transaction.transItems.get(this.transaction.transItems.indexOf(item)).quantity *= -1;
		}
		if (this.transaction.transItems.get(this.transaction.transItems.indexOf(item)).quantity == 0) {
			this.transaction.transItems.remove(this.transaction.transItems.indexOf(item));
		}
		this.updateTable();
	}
	
	public void clearTable() {
		this.transaction.transItems.clear();
		this.updateTable();
	}
	
	public String getTime() {
		return this.dateTime.getText();
	}
	
	public void setTime(String time) {
		this.dateTime.setText(time);
	}
	
	public String getOrderType() {
		String selectedToggle = "dinein";
		for (Toggle toggle : this.orderType.getToggles()) {
			Node node = (Node) toggle ;
		    if(toggle.isSelected()) {
		    	selectedToggle = node.getId();
		    	break;
		    }
		}
		return selectedToggle;
	}
	
	public void setOrderType(String orderType) {
		for(Toggle toggle : this.orderType.getToggles()) {
			Node node = (Node) toggle ;
		    if(orderType.equals(node.getId())) {
		    	this.orderType.selectToggle(toggle);
		    	break;
		    }
		}
	}
	
	public void updateTable() {
		this.itemsTable.getItems().clear();
		double subtotal = 0;
		for (int i=0; i<this.transaction.transItems.size(); i++) {
			MenuItem item = this.transaction.transItems.get(i);
			this.itemsTable.getItems().add(item);
			subtotal += item.getTotal();
		}
		subtotal = Math.round(subtotal * 100.0) / 100.0;
		double taxes = Math.round(subtotal * 0.05 * 100.0) / 100.0;
		double net = Math.round((subtotal + taxes) * 100.0) / 100.0;
		this.subtotal.setText("$ " + String.valueOf(subtotal));
		this.taxes.setText("$ " + String.valueOf(taxes));
		this.netAmount.setText("$ " + String.valueOf(net));
	}
}
