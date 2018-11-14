import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;

public class Rooms {

    private String name = "no  name";
    //Sensor
    private Map<Enum.Sensor, List<AbsSensor>> sensorMap;

    //Actuator
    private Map<Enum.Actuator, List<Actuator>> actuatorMap; //TODO regrouper les actuators


    //Manager
    private Map<Enum.Actuator, ManagerFeature> managerMap;


    public Rooms() {
        this.sensorMap = new HashMap<Enum.Sensor, List<AbsSensor>>();
        this.actuatorMap = new HashMap<Enum.Actuator, List<Actuator>>();
        this.managerMap = new HashMap<Enum.Actuator, ManagerFeature>();
    }

    public Rooms(String name) {
        this.name = name;
        this.sensorMap = new HashMap<Enum.Sensor, List<AbsSensor>>();
        this.actuatorMap = new HashMap<Enum.Actuator, List<Actuator>>();
        this.managerMap = new HashMap<Enum.Actuator, ManagerFeature>();
    }


    public void addSensor(AbsSensor s) {
        if (!sensorMap.containsKey(s.getType())) {
            sensorMap.put(s.getType(), new ArrayList<AbsSensor>());
        }
        this.getSensorOfType(s.getType()).add(s);
    }

    public void addSensor(String type, String name) {
        AbsSensor s = Factory.makeSensor(type, name);
        this.addSensor(s);
    }


    public void addDevice(Actuator a) {
        if (!actuatorMap.containsKey(a.getType())) {
            actuatorMap.put(a.getType(), new ArrayList<Actuator>());
        }
        this.getActuatorofType(a.getType()).add(a);
    }

    public void addDevice(String type, String name) {
        Actuator a = Factory.makeActuator(type, name);
        this.addDevice(a);
    }



    public List<Actuator> getActuatorofType(Enum.Actuator type) {
        return actuatorMap.get(type);
    }

    public List<AbsSensor> getSensorOfType(Enum.Sensor type) {
        return sensorMap.get(type);
    }


    public void makeManagerForUsedDevices(){
        for (Enum.Actuator act: Enum.Actuator.values()) {
            if(actuatorMap.containsKey(act)){
                ManagerFeature manager = Factory.makeManager(act);
                manager.add(this);
                managerMap.put(act,manager);
            }
        }
    }

    public ManagerFeature getManager(Enum.Actuator act){
        return managerMap.get(act);
    }

    public String toString(){
        String str =  "Room :"+ name ;
        str = str + " \n sensors : \n";
        for (List<AbsSensor> l: sensorMap.values()) {
            for (AbsSensor s: l) {
                str = str + s.toString() + "\n";
            }
        }
        str = str + " \n actuator : \n";
        for (List<Actuator> l: actuatorMap.values()) {
            for (Actuator act : l) {
                str = str + act.toString() + "\n";
            }
        }

        str = str + " \n manager : \n";
        for (ManagerFeature m : managerMap.values()) {
            str = str + m.ToString() + "\n";
        }
        return str + "\n \n";
    }
}

/*
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rooms {

    private String name ="no  name";
    //Sensor
    private List<SensorMotion> motionDetectors;
    private List<ActuLight> light;
    private List<ActuCoffeeMachine> coffeeMachines;
    private List<ActuRadiator> radiators;
    private List<SensorThermo> thermometers;

    //Actuator
    private Map<Enum.Actuator,List> actuatorMap; //TODO regrouper les actuators
    //private List<ActuLight> light;
    //private List<ActuCoffeeMachine> coffeeMachines;

    //Manager
    private Map<Enum.Actuator,ManagerFeature> managerMap;


    public Rooms(){
        this.motionDetectors = new ArrayList<SensorMotion>();
        this.light = new ArrayList<ActuLight>();
        this.thermometers = new ArrayList<SensorThermo>();
        this.radiators = new ArrayList<ActuRadiator>();
        this.actuatorMap = new HashMap<Enum.Actuator,List>();
        this.managerMap = new HashMap<Enum.Actuator,ManagerFeature>();
    }

    public Rooms(String name){
        this.name = name;
        this.motionDetectors = new ArrayList<SensorMotion>();
        this.actuatorMap = new HashMap<Enum.Actuator,List>();
        this.managerMap = new HashMap<Enum.Actuator,ManagerFeature>();
    }

    public void addSensor(SensorMotion md ){
        motionDetectors.add(md);
    }
    public void addSensor(SensorThermo th) {thermometers.add(th);}

    public void addDevice(ActuLight l){
        if (!actuatorMap.containsKey(Enum.Actuator.light)){
            actuatorMap.put(Enum.Actuator.light,new ArrayList<ActuLight>());
        }
        this.getLight().add(l);
    }


    public void addDevice(ActuRadiator r) {
        if (!actuatorMap.containsKey(Enum.Actuator.radiator)){
            actuatorMap.put(Enum.Actuator.radiator,new ArrayList<ActuRadiator>());
        }
        this.getRadiator().add(r);
    }

    public void addDevice(ActuCoffeeMachine cm){
        if (!actuatorMap.containsKey(Enum.Actuator.coffee)){
            actuatorMap.put(Enum.Actuator.coffee,new ArrayList<ActuCoffeeMachine>());
        }
        this.getCoffeeMachines().add(cm);
    }

    public List<SensorMotion> getMotionDetectors() {
        return motionDetectors;
    }
    public List<SensorThermo> getThermometer() {return thermometers;}
    public List<ActuLight> getLight() {
        return actuatorMap.get(Enum.Actuator.light);
    }
    public List<ActuRadiator> getRadiator() {return actuatorMap.get(Enum.Actuator.radiator);}
    public List<ActuCoffeeMachine> getCoffeeMachines() {
        return actuatorMap.get(Enum.Actuator.coffee);
    }

    public void makeManagerForUsedDevices(){
        for (Enum.Actuator act: Enum.Actuator.values()) {
            if(actuatorMap.containsKey(act)){
                ManagerFeature manager;
                //TODO improve ManagerFeature
                switch (act){
                    case light: manager= new ManagerLight(); break;
                    case coffee: manager =new ManagerCoffee(); break;
                    default:System.out.println("Error in makeManagerForUsedDevices : invalid actuator type");manager =new ManagerCoffee(); //coffee
                }
                manager.add(this);
                managerMap.put(act,manager);
            }
        }
    }

    public ManagerFeature getManager(Enum.Actuator act){
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
        for (ManagerFeature m : managerMap.values()) {
            str = str + m.ToString() + "\n";
        }
        return str + "\n \n";
    }
}
*/