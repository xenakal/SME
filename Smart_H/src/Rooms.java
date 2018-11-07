import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rooms {

    private String name ="no  name";
    //Sensor
    private List<MotionDetector> motionDetectors;
    private List<Light> light;
    private List<CoffeeMachine> coffeeMachines;
    private List<Radiator> radiators;
    private List<Thermostat> thermostats;

    //Actuator
    private Map<Enum.Actuator,List> actuatorMap; //TODO regrouper les actuators
    //private List<Light> light;
    //private List<CoffeeMachine> coffeeMachines;

    //Manager
    private Map<Enum.Actuator,FeatureManager> managerMap;


    public Rooms(){
        this.motionDetectors = new ArrayList<MotionDetector>();
        this.light = new ArrayList<Light>();
        this.thermostats = new ArrayList<Thermostat>();
        this.radiators = new ArrayList<Radiator>();
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

    public void addDevice(Radiator r) {radiators.add(r);}

    public void addDevice(CoffeeMachine cm){
        if (!actuatorMap.containsKey(Enum.Actuator.coffee)){
            actuatorMap.put(Enum.Actuator.coffee,new ArrayList<CoffeeMachine>());
        }
        this.getCoffeeMachines().add(cm);
    }

    public List<MotionDetector> getMotionDetectors() {
        return motionDetectors;
    }
    public List<Thermostat> getThermostats() {return thermostats;}
    public List<Light> getLight() {
        return actuatorMap.get(Enum.Actuator.light);
    }
    public List<Radiator> getRadiator() {return radiators;}
    public List<CoffeeMachine> getCoffeeMachines() {
        return actuatorMap.get(Enum.Actuator.coffee);
    }

    public void makeManagerForUsedDevices(){
        for (Enum.Actuator act: Enum.Actuator.values()) {
            if(actuatorMap.containsKey(act)){
                FeatureManager manager;
                //TODO improve FeatureManager
                switch (act){
                    case light: manager= new Lightmanager(); break;
                    case coffee: manager =new CoffeeManager(); break;
                    default:System.out.println("Error in makeManagerForUsedDevices : invalid actuator type");manager =new CoffeeManager(); //coffee
                }
                manager.add(this);
                managerMap.put(act,manager);
            }
        }
    }

    public FeatureManager getManager(Enum.Actuator act){
        return managerMap.get(act);
    }

    public String toString(){
        String str =  "Room :"+ name ;
        str = str + " \n sensors : \n";
        for (AbsSensor s: motionDetectors) {
            str = str + s.toString() + "\n";
        }
        str = str + " \n actuator : \n";
        for (Object act : actuatorMap.values()) {
            str = str + act.toString() + "\n";
        }
        str = str + " \n manager : \n";
        for (FeatureManager m : managerMap.values()) {
            str = str + m.ToString() + "\n";
        }
        return str + "\n \n";
    }
}
