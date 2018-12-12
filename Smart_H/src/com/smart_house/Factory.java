package com.smart_house;
import java.lang.*;

public class Factory{

    private static  Factory factory = new Factory();

    public static Factory getInstance(){
        return factory;
    }

    public Actuator makeActuator(String type, String name)throws ClassNotFoundException{
        switch (type){
            case "light" : return new ActuLight(name);
            case "coffee" : return new ActuCoffeeMachine(name);
            case "radiator" : return new ActuRadiator(name);
            case "climatisor" : return new ActuClimatisor(name);
            case "alarm" : return new ActuAlarm(name);
            default:
                throw new ClassNotFoundException("wrong sensor type" + type);
                //System.out.print("Type error in Actuator Factory, given type : "+type); return null;
        }
    }

    public AbsSensor makeSensor(String type, String name)throws ClassNotFoundException{
        // TODO: utiliser les types de ENUM
        switch (type){
            case "motion" : return new SensorMotion(name);

            case "thermo" : return new SensorThermo(name);

            case "alarmBox": return new SensorAlarmBox(name);

            default:
                throw new ClassNotFoundException("wrong sensor type" + type);
                //System.out.println("Error in json parse");
                //return null;
        }
    }


    public ManagerFeature makeManager(Enum.Manager m){
        ManagerFeature manager;
        switch (m){
            case lightManager: manager= new ManagerLight(); break;
            case temperatureManager: manager = new ManagerThermo(20,0); break;
            case coffeeManager: manager =new ManagerCoffee(); break;
            case securityManager: manager = new ManagerSecurity(); break;
            default:System.out.println("Error in makeManagerForUsedDevices : invalid actuator type"); manager = null;
                //TODO launch error
        }
        return manager;
    }
}
