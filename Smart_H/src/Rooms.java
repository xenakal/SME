import java.util.ArrayList;
import java.util.List;

public class Rooms {


    private List<MotionDetector> motionDetectors;
    private List<Light> light;
    private List<CoffeeMachine> coffeeMachines;
    private List<Radiator> radiators;
    private List<Thermostat> thermostats;

    /*public Rooms(MotionDetector[] sensor, Light[] light){
        this.light_sensor = sensor;
        this.living_light = light;

    }*/

    public Rooms(){
        this.motionDetectors = new ArrayList<MotionDetector>();
        this.light = new ArrayList<Light>();
        this.coffeeMachines = new ArrayList<CoffeeMachine>();
        this.thermostats = new ArrayList<Thermostat>();
        this.radiators = new ArrayList<Radiator>();

    }

    public void addSensor(MotionDetector  md ){
        motionDetectors.add(md);
    }

    // TODO: mettre les lights et radiators comme héritant d'une superclasse pour avoir qu'une addDevice
    public void addDevice(Light l){
        light.add(l);
    }

    public void addDevice(Radiator r) {radiators.add(r);}

    public void addDevice(CoffeeMachine cm){
        coffeeMachines.add(cm);
    }

    public List<MotionDetector> getMotionDetectors() {
        return motionDetectors;
    }
    public List<Thermostat> getThermostats() {return thermostats;}
    public List<Light> getLight() {
        return light;
    }
    public List<Radiator> getRadiator() {return radiators;}
    public List<CoffeeMachine> getCoffeeMachines() {
        return coffeeMachines;
    }
}
