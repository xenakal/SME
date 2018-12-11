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

	/* TODO: now this is not what we want. Here we just added a button in every object, we have to remove it, make a button for every sensor and make it react 
	 * 		 in the way corresponding to the sensor. 
	 * 
	 */
	public void handle(ActionEvent event) {
		if(event.getSource() instanceof Button) {
			Button button = (Button) event.getSource();
			//ActuatorAgent act = (ActuatorAgent) button.getParent(); 
			SensorAgent act = (SensorAgent) button.getParent(); 
			SmartHome sh = SmartHome.getSmartHome(); 
			Rooms room_detect = sh.getRoomsMap().get("bedroom");
			
			if(room_detect != null) {
				System.out.println(room_detect.getName()); 
				Enum.Sensor sensor_type = Enum.Sensor.valueOf("motion");
				List<AbsSensor> sensors = room_detect.getSensorOfType(sensor_type);
				for(AbsSensor sensor : sensors) {
					SensorMotion sensor1 = (SensorMotion) sensor; 
					System.out.println(sensor1.getName());
					sensor1.detect(1);
				}
				
			}
			else {
				System.out.println("there is no such room");
			}
		}
		else {
			System.out.println("nop");
		}
	}

	
}
