import java.util.ArrayList;
import java.util.List;

public class Rooms {


    private List<MotionDetector> motionDetectors;
    private List<Light> light;

    /*public Rooms(MotionDetector[] sensor, Light[] light){
        this.light_sensor = sensor;
        this.living_light = light;

    }*/

    public Rooms(){
        this.motionDetectors = new ArrayList<MotionDetector>();
        this.light = new ArrayList<Light>();

    }

    public void addSensor(MotionDetector  md ){
        motionDetectors.add(md);
    }

    public void addDevice(Light l){
        light.add(l);
    }

    public List<MotionDetector> getMotionDetectors() {
        return motionDetectors;
    }

    public List<Light> getLight() {
        return light;
    }
}
