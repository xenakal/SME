import com.smart_house.SensorMotion;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class HandleLight implements EventHandler<ActionEvent>{

	private SensorMotion sensor; 
	
	public HandleLight(SensorMotion sensor) {
		this.sensor = sensor; 
	}
	
	
	public void handle(ActionEvent event) {
		System.out.println("LIGHT !!!");
		//sensor.detect_switch();
	}


	
}
