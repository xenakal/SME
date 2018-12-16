package application.javaApp;

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
    private Map<Enum.Manager, ManagerFeature> managerMap;


    public Rooms() {
        this.sensorMap = new HashMap<Enum.Sensor, List<AbsSensor>>();
        this.actuatorMap = new HashMap<Enum.Actuator, List<Actuator>>();
        this.managerMap = new HashMap<Enum.Manager, ManagerFeature>();
    }

    public Rooms(String name) {
        this.name = name;
        this.sensorMap = new HashMap<Enum.Sensor, List<AbsSensor>>();
        this.actuatorMap = new HashMap<Enum.Actuator, List<Actuator>>();
        this.managerMap = new HashMap<Enum.Manager, ManagerFeature>();
    }


    public void addSensor(AbsSensor s) {
        if (!sensorMap.containsKey(s.getType())) {
            sensorMap.put(s.getType(), new ArrayList<AbsSensor>());
        }
        this.getSensorOfType(s.getType()).add(s);
    }

    public void removeSensor(AbsSensor s) {
        if (sensorMap.containsKey(s.getType())) {
            this.getSensorOfType(s.getType()).remove(s);
            if(sensorMap.get(s.getType()).isEmpty()){
                sensorMap.remove(s.getType());
            }
        }

    }

    public void addSensor(String type, String name) {
        AbsSensor s = Factory.getInstance().makeSensor(type, name);
        this.addSensor(s);
    }

    public void removeSensor(String type, String name) {
        AbsSensor s = Factory.getInstance().makeSensor(type, name);
        this.removeSensor(s);
    }


    public void addDevice(Actuator a) {
        if (!actuatorMap.containsKey(a.getType())) {
            actuatorMap.put(a.getType(), new ArrayList<Actuator>());
            Enum.Manager managerType = Enum.getCorrespondingManager(a.getType());
            ManagerFeature m = this.getManagerOfType(managerType);
            if (m == null) {
                m = Factory.getInstance().makeManager(managerType);
                m.add(this);
                managerMap.put(managerType, m);
            }else{
                m.update();
            }
        }
        this.getActuatorOfType(a.getType()).add(a);
    }

    public void removeDevice(Actuator a) {
        if (actuatorMap.containsKey(a.getType())) {
            this.getActuatorOfType(a.getType()).remove(a);
            if(actuatorMap.get(a.getType()).isEmpty()){
                actuatorMap.remove(a.getType());
                Enum.Manager managerType = Enum.getCorrespondingManager(a.getType());
                ManagerFeature m = this.getManagerOfType(managerType);
                if(m != null){
                    managerMap.remove(managerType);
                }
            }
        }
    }

    public void addDevice(String type, String name) {
        Actuator a = Factory.getInstance().makeActuator(type, name);
        this.addDevice(a);
    }

    public void removeDevice(String type, String name) {
        Actuator a = Factory.getInstance().makeActuator(type, name);
        this.removeDevice(a);
    }



    public List<Actuator> getActuatorOfType(Enum.Actuator type) {
        List<Actuator> ret = actuatorMap.get(type);
        if(ret == null) ret =  new ArrayList<Actuator>();
        return ret;
    }

    public List<AbsSensor> getSensorOfType(Enum.Sensor type) {
        List<AbsSensor> ret = sensorMap.get(type);
        if(ret == null) ret =  new ArrayList<AbsSensor>();
        return ret;
    }
    
    public Map<Enum.Sensor, List<AbsSensor>> getSensorMap(){
    	return this.sensorMap; 
    }

    public ManagerFeature getManagerOfType(Enum.Manager man){
        return managerMap.get(man);
    }

    public void makeManagerForUsedDevices(){
        for (Enum.Actuator act : Enum.Actuator.values()) {
            if(actuatorMap.containsKey(act)){
                Enum.Manager man = Enum.getCorrespondingManager(act);
                if(!managerMap.containsKey(man)) {
                    ManagerFeature manager = Factory.getInstance().makeManager(man);
                    manager.add(this);
                    managerMap.put(man, manager);
                }
            }
        }
    }

    public Map<Enum.Actuator, List<Actuator>> getActuatorMap(){
        return actuatorMap;
    }
    
    public String getName() {
    	return this.name; 
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

