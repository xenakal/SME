import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rooms {

    private String name ="no  name";
    //Sensor
    private List<MotionDetector> motionDetectors;

    //Actuator
    private Map<Enum.Actuator,List> actuatorMap; //TODO regrouper les actuators
    //private List<Light> light;
    //private List<CoffeeMachine> coffeeMachines;

    //Manager
    private Map<Enum.Actuator,FeatureManager> managerMap;


    /*public Rooms(MotionDetector[] sensor, Light[] light){
        this.light_sensor = sensor;
        this.living_light = light;

    }*/

    public Rooms(){
        this.motionDetectors = new ArrayList<MotionDetector>();
        this.motionDetectors = new ArrayList<MotionDetector>();
        this.actuatorMap = new HashMap<Enum.Actuator,List>();
        this.managerMap = new HashMap<Enum.Actuator,FeatureManager>();
    }

    public Rooms(String name){
        this.name = name;
        this.motionDetectors = new ArrayList<MotionDetector>();
        this.actuatorMap = new HashMap<Enum.Actuator,List>();
        this.managerMap = new HashMap<Enum.Actuator,FeatureManager>();
    }

    public void addSensor(MotionDetector  md ){
        motionDetectors.add(md);
    }

    public void addDevice(Light l){
        if (!actuatorMap.containsKey(Enum.Actuator.light)){
            actuatorMap.put(Enum.Actuator.light,new ArrayList<Light>());
        }
        this.getLight().add(l);
    }

    public void addDevice(CoffeeMachine cm){
        if (!actuatorMap.containsKey(Enum.Actuator.coffee)){
            actuatorMap.put(Enum.Actuator.coffee,new ArrayList<CoffeeMachine>());
        }
        this.getCoffeeMachines().add(cm);
    }

    public List<MotionDetector> getMotionDetectors() {
        return motionDetectors;
    }

    public List<Light> getLight() {
        return actuatorMap.get(Enum.Actuator.light);
    }

    public List<CoffeeMachine> getCoffeeMachines() {
        return actuatorMap.get(Enum.Actuator.coffee);
    }

    public void makeManagerForUsedDevices(){
        for (Enum.Actuator act: Enum.Actuator.values()) {
            if(actuatorMap.containsKey(act)){
                FeatureManager manager;
                //TODO improve FeatureManager
                switch (act){
                    case light: manager= new Lightmanager();
                    default:manager =new CoffeeManager(); //coffee
                }
                manager.add(this);
                managerMap.put(act,manager);
            }
        }
    }
}
