package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import main.apps.MenuItem;

public class MenuItemController {
	 @FXML private Text itemName;
	  @FXML private Text itemPrice;
	  @FXML private ImageView itemImg;
	  
	  public MenuItem item;
	  
	public void initialize(MenuItem item) {
		this.item = item;
		itemName.setText(item.name);
		itemPrice.setText("$ " + item.price);
		itemImg.setImage(new Image(item.image));
	}
	
	public void itemClick(MouseEvent e) {
		if(this.item.main.activeTab != "home")
			this.item.main.changeTab("home");
		this.item.main.home.controller.itemClickHandler(item);
	}
}
