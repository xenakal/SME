package com.fx_house;

import com.smart_house.ActuRadiator;
import com.smart_house.Actuator;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class RadiatorAgent extends ActuatorAgent{
	
	private String name; 
	Rectangle r; 
	private ActuRadiator act; 
	
	public RadiatorAgent(Actuator act) {
		this.name = act.getName();  
		this.act = (ActuRadiator) act;
		act.makeFX(this);

		r = new Rectangle(); 
		r.setWidth(100);
		r.setHeight(100);
		r.setArcWidth(20);
		r.setArcHeight(20);
		r.setFill(Color.GRAY.deriveColor(0, 1.2, 1, 0.6));
        r.setStroke(Color.GRAY);
        
		Text text = new Text(act.getType().name());

        this.getChildren().addAll(r, text);
        setBackColor();
	}

	public void setBackColor() {
		if(act.getState())
			r.setFill(Color.DARKRED);
		else
			r.setFill(Color.GRAY);
			
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
