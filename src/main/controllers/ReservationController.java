package main.controllers;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import main.apps.ReservationTable;
import main.services.DbBuildConnection;

public class ReservationController {
	
	public MainController main;
	DbBuildConnection dbCon = new DbBuildConnection();
	ArrayList<ReservationTable> rTables;
	
	@FXML private TableView<ReservationTable> reservationsTable;
	@FXML private TableColumn<ReservationTable, String> tableColumn;
	@FXML private TableColumn<ReservationTable, String> capacityColumn;
	@FXML private TableColumn<ReservationTable, String> statusColumn;
	
	public void initialize(MainController main) {
		this.main = main;
		tableColumn.setCellValueFactory(new PropertyValueFactory<>("tableNo"));
		capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		rTables = dbCon.getReservationTables();
		this.updateGui();
		//System.out.println();
	}
	
	public void updateGui() {
		this.reservationsTable.getItems().clear();
		for (ReservationTable rTable: this.rTables) {
			this.reservationsTable.getItems().add(rTable);
		}
		
	}

	public void reserveToggle() {
		if (this.reservationsTable.getSelectionModel().getSelectedItem() != null) {
			dbCon.reservationToggle(this.reservationsTable.getSelectionModel().getSelectedItem(), main);
			this.initialize(main);
		}
		
		else {
			this.main.lastAction.setText("No Table Selected");
		}
		
	}



}
