package main.apps;

import java.io.IOException;
import java.io.InputStream;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import main.controllers.MainController;
import main.controllers.MenuItemController;

public class MenuItem extends BorderPane{
	private Node view;
	public MenuItemController controller = new MenuItemController();
	
	public String itemId;
	public String name;
	public double price;
	public InputStream image;
	public int quantity;
	public double total;
	public MainController main;
	
	public MenuItem(String itemId, String name, double price, int quantity) {
		this.itemId = itemId;
        this.name =name;
        this.price =price;
        this.quantity = quantity;
	}
	
	public MenuItem(MainController main, String itemId, String name, double price, int quantity, InputStream image) {
		this.main = main;
        this.itemId = itemId;
        this.name =name;
        this.price =price;
        this.image = image;
        this.quantity = quantity;
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/resources/views/MenuItem.fxml"));
        fxmlLoader.setController(controller);
        try {
            view = (Node) fxmlLoader.load();
            controller.initialize(this);

        } catch (IOException ex) {
        }
        getChildren().add(view);
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public double getTotal() {
		this.total = this.price * this.quantity;
		this.total = Math.round(this.total * 100.0) / 100.0;
		return total;
	}
	
	
	
	
}
