import java.util.LinkedList;
import java.util.List;

public class SensorThermo extends AbsSensor{

    private int recorded_temp = 25;

    @Override
    Enum.Sensor getType() {
        return Enum.Sensor.temperature;
    }

    protected SensorThermo(String name) {
        super(name);
     }

    public void sensor_on(){
        System.out.println("SensorThermo is on");
    }
    public void sensor_off(){
        System.out.println("SensorThermo is off");
    }

    void reset() {}

    void detect(int value) {
        //System.out.println("# Température détectée: "+value);
        recorded_temp = value;
        this.advertise();
    }



    public Info makeinfo(){
        return new Info("temperature",  recorded_temp);
    }
}
