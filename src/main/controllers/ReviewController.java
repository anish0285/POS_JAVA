package main.controllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import main.apps.MenuItem;
import main.apps.Transaction;
import main.services.DbBuildConnection;

public class ReviewController {
	@FXML private Pane activeTrans;
	@FXML private TextField numField;
	
	public MainController main;
	public ArrayList<Transaction> oldTransactions;
	
	public DbBuildConnection dbConn = new DbBuildConnection();
	public Integer activeTransNum;
	
	public void initialize(MainController main) {
		this.main = main;
		this.oldTransactions = dbConn.getOldTransactions(main);
		activeTransNum = this.oldTransactions.size() - 1;
		this.updateGui();
	}
	
	public void backTrans() {
		if(activeTransNum > 0) {
			this.activeTransNum -= 1;
		}
		this.updateGui();
	}
	
	public void nextTrans() {
		if(activeTransNum < this.oldTransactions.size()-1) {
			this.activeTransNum += 1;
		}
		this.updateGui();
	}
	
	public void updateGui() {
		this.activeTrans.getChildren().clear();
		this.activeTrans.getChildren().add(this.oldTransactions.get(activeTransNum));
		Integer intforNumField = activeTransNum + 1;
		numField.setText(intforNumField.toString());
	}
	
	public void refund() {
		this.main.lastAction.setText("Refund Started");
		this.main.changeTab("home");
		Transaction currentTrans = this.oldTransactions.get(activeTransNum);
		this.main.home.controller.transaction.controller.setOrderType(currentTrans.controller.getOrderType());
		for (MenuItem item: currentTrans.transItems) {
			this.main.home.controller.transaction.controller.addRemoveItem(item, "refund");
		}
	}
	
}
