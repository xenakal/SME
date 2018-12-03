public class Enum {
    public enum Actuator{
        light,coffee,radiator,alarm
    }

    //transforme un string en un element de l'enumeration
    public static Actuator convertToActu(String type){
        switch (type) {
            case "light" : return Actuator.light;
            case "coffee": return Actuator.coffee;
            case "radiator": return Actuator.radiator;
            case "alarm": return Actuator.alarm;
            default: System.out.println("Error Enum : invalid Actuator type : " +type);return Actuator.light;
        }
    }
    /*public enum Manager{
        lightManager,coffeeManager
    }*/
    public enum Sensor{
        motion,temperature
    }

    public static Sensor convertToSensor(String type){
        switch (type) {
            case "motion" : return Sensor.motion;
            case "temperature": return Sensor.temperature;
            case "thermo": return Sensor.temperature;
            default: System.out.println("Error Enum: invalid Sensor type : " +type); return Sensor.motion;
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
