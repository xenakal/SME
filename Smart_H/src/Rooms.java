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
        AbsSensor s = Factory.getInstance().makeSensor(type, name);
        this.addSensor(s);
    }


    public void addDevice(Actuator a) {
        if (!actuatorMap.containsKey(a.getType())) { // si un manager pour ce type d'actuateur n'existe pas
            actuatorMap.put(a.getType(), new ArrayList<Actuator>());
            ManagerFeature m = this.getManager(a.getType());
            if (m != null) {
                m.update();
            }
        }
        this.getActuatorofType(a.getType()).add(a);
    }

    public void addDevice(String type, String name) {
        Actuator a = Factory.getInstance().makeActuator(type, name);
        this.addDevice(a);
    }



    public List<Actuator> getActuatorofType(Enum.Actuator type) {
        return actuatorMap.get(type);
    }

    public List<AbsSensor> getSensorOfType(Enum.Sensor type) {
        return sensorMap.get(type);
    }

    public ManagerFeature getManager(Enum.Actuator act){
        return managerMap.get(act);
    }

    public void makeManagerForUsedDevices(){
        for (Enum.Actuator act: Enum.Actuator.values()) {
            if(actuatorMap.containsKey(act)){
                ManagerFeature manager = Factory.getInstance().makeManager(act);
                manager.add(this);
                managerMap.put(act,manager);
            }
        }
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

