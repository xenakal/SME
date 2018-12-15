package application;

import java.util.List;

import com.smart_house.AbsSensor;
import com.smart_house.Enum;
import com.smart_house.Rooms;
import com.smart_house.SensorMotion;
import com.smart_house.SmartHome;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class HandleLight implements EventHandler<ActionEvent>{

	private SensorMotion sensor; 
	
	public HandleLight(SensorMotion sensor) {
		this.sensor = sensor; 
	}
	
	
	public void handle(ActionEvent event) {
		System.out.println("PUSHED !!!"); 
		sensor.detect_switch(); 
	}


	
}
