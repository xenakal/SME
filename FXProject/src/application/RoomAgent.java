package application;

import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;

public class RoomAgent extends FlowPane{
	
	public RoomAgent(){
		
		FlowPane cell = new FlowPane();
		cell.setPadding(new Insets(10,10,10,10)); // add padding around the cells 
		cell.setMinSize(10,10);
		cell.setVgap(5); // gaps between the cells 
		cell.setHgap(5); 
		cell.setStyle("-fx-background-color:DAE6F3;");
		
	}
	
}
