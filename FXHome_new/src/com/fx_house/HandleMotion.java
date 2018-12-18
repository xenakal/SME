package com.fx_house;

import com.smart_house.SensorMotion;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class HandleMotion implements EventHandler<ActionEvent>{

	private SensorMotion sensor; 
	
	public HandleMotion(SensorMotion sensor) {
		this.sensor = sensor; 
	}
	
	
	public void handle(ActionEvent event) {
		System.out.println("LIGHT !!!");
		sensor.detect_switch();
	}


	
}
