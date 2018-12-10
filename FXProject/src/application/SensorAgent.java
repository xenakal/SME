package application;

import com.smart_house.AbsSensor;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class SensorAgent extends StackPane{
	
	private String name; 
	Rectangle r; 
	
	public SensorAgent(AbsSensor sensor) {
		
		this.name = sensor.getName();  
		
		r = new Rectangle(); 
		r.setWidth(100);
		r.setHeight(100);
		r.setArcWidth(20);
		r.setArcHeight(20);
		r.setFill(Color.GRAY.deriveColor(0, 1.2, 1, 0.6));
        r.setStroke(Color.GRAY);
        
        Button button = new Button(); 
        button.setText("Sensor");
        button.setOnAction(new HandleLight());
        
		Text text = new Text(sensor.getType().name());
        StackPane stack = new StackPane();
		
        this.getChildren().addAll(r, text, button);
     }   
	
	public void setState(String color) {
		switch (color){
			case "yellow":
				r.setFill(Color.YELLOW);
				break; 
			case "black": 
				r.setFill(Color.GRAY);
				break; 
			default: 
				System.out.println("Color can be only yellow or black :/ ");
		}
	}

}
