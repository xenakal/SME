package com.smart_house;

public class Enum {
    public enum Actuator{
        light,coffee,radiator,alarm,climatisor
    }

    //transforme un string en un element de l'enumeration
    public static Actuator convertToActu(String type){
        switch (type) {
            case "light" : return Actuator.light;
            case "coffee": return Actuator.coffee;
            case "radiator": return Actuator.radiator;
            case "alarm": return Actuator.alarm;
            case "climatisor": return Actuator.climatisor;
            default: System.out.println("Error Enum : invalid Actuator type : " +type);return null;
        }
    }
    public enum Manager{
        lightManager,coffeeManager, temperatureManager, securityManager
    }
    public static Manager convertToManager(String type){
        switch (type) {
            case "lightControl" : return Manager.lightManager;
            case "smartCoffee": return Manager.coffeeManager;
            case "temperatureControl": return Manager.temperatureManager;
            //case "securityControl": return Manager.securityManager;
            case "light" : return Manager.lightManager;
            case "coffee": return Manager.coffeeManager;
            case "radiator": return Manager.temperatureManager;
            case "climatisor" : return Manager.temperatureManager;
            case "alarm": return Manager.securityManager;
            default: System.out.println("Error Enum : invalid Actuator type : " +type); return null;
        }
    }

    public static Manager getCorrespondingManager(Actuator type){
        switch (type) {
            case light: return Manager.lightManager;
            case coffee: return Manager.coffeeManager;
            case radiator: return Manager.temperatureManager;
            case climatisor: return Manager.temperatureManager;
            case alarm: return Manager.securityManager;
            default: System.out.println("Error Enum : invalid Actuator type : " +type); return null;
        }
    }

    public enum Sensor{
        motion,temperature
    }

    public static Sensor convertToSensor(String type){
        switch (type) {
            case "motion" : return Sensor.motion;
            case "temperature": return Sensor.temperature;
            case "thermo": return Sensor.temperature;
            default: System.out.println("Error Enum: invalid Sensor type : " +type); return null;
        }
    }

    public enum ActivationCondition{
        presence
    }

    public static ActivationCondition convertToCondition(String type){
        switch (type) {
            case "presence" : return ActivationCondition.presence;
            default: System.out.println("Error Enum: invalid ActivationCondition type"); return ActivationCondition.presence;
        }
    }
}
