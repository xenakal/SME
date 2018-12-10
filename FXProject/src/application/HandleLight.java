package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

public class HandleLight implements EventHandler<ActionEvent>{

	/* TODO: ok now this is not what we want. Here we just added a button in every object, we have to remove it, make a button for every sensor and make it react 
	 * 		 in the way corresponding to the sensor. 
	 * 
	 */
	public void handle(ActionEvent event) {
		if(event.getSource() instanceof Button) {
			Button button = (Button) event.getSource();
			ActuatorAgent act = (ActuatorAgent) button.getParent(); 
			act.setBackColor("yellow");
		}
		else {
			System.out.println("nop");
		}
	}

	
}
