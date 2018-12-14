import com.smart_house.SensorMotion;
import com.smart_house.SensorThermo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class HandleThermo implements EventHandler<ActionEvent>{

    private SensorThermo sensor;

    public HandleThermo(SensorThermo sensor) {
        this.sensor = sensor;
    }


    public void handle(ActionEvent event) {
        System.out.println("THERMO !!!");
        //sensor.detect_switch();
    }



}
