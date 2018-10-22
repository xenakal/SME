import java.util.ArrayList;
import java.util.List;

public class Rooms {


    private List<MotionDetector> motionDetectors;
    private List<Light> light;
    private List<CoffeeMachine> coffeeMachines;

    /*public Rooms(MotionDetector[] sensor, Light[] light){
        this.light_sensor = sensor;
        this.living_light = light;

    }*/

    public Rooms(){
        this.motionDetectors = new ArrayList<MotionDetector>();
        this.light = new ArrayList<Light>();
        this.coffeeMachines = new ArrayList<CoffeeMachine>();

    }

    public void addSensor(MotionDetector  md ){
        motionDetectors.add(md);
    }

    public void addDevice(Light l){
        light.add(l);
    }

    public void addDevice(CoffeeMachine cm){
        coffeeMachines.add(cm);
    }

    public List<MotionDetector> getMotionDetectors() {
        return motionDetectors;
    }

    public List<Light> getLight() {
        return light;
    }

    public List<CoffeeMachine> getCoffeeMachines() {
        return coffeeMachines;
    }
}
