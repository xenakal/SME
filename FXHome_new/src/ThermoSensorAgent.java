import com.smart_house.AbsSensor;
import com.smart_house.SensorMotion;
import com.smart_house.SensorThermo;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ThermoSensorAgent extends SensorAgent{

    private String name;
    Rectangle r;
    SensorThermo sensor;

    public ThermoSensorAgent(AbsSensor sensor) {

        this.name = sensor.getName();
        this.sensor = (SensorThermo) sensor;
        r = new Rectangle();
        r.setWidth(100);
        r.setHeight(100);
        r.setArcWidth(20);
        r.setArcHeight(20);
        r.setFill(Color.GRAY.deriveColor(0, 1.2, 1, 0.6));
        r.setStroke(Color.GRAY);

        Button button = new Button();
        button.setText("Thermometer");
        button.setOnAction(new HandleThermo(this.sensor));

        Text text = new Text(sensor.getType().name());

        this.getChildren().addAll(r, text, button);
    }


}
