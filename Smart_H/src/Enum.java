public class Enum {
    public enum Actuator{
        light,coffee
    }

    //transforme un string en un element de l'enumeration
    public static Actuator convertToActu(String type){
        switch (type) {
            case "light" : return Actuator.light;
            case "coffee": return Actuator.coffee;
            default: System.out.println("Error : invalid Actuator type");return Actuator.light;
        }
    }
    /*public enum Manager{
        lightManager,coffeeManager
    }*/
    public enum Sensor{
        motion
    }

    public static Sensor convertToSensor(String type){
        switch (type) {
            case "motion" : return Sensor.motion;
            default: System.out.println("Error : invalid Sensor type"); return Sensor.motion;
        }
    }

    public enum ActivationCondition{
        presence
    }

    public static ActivationCondition convertToCondition(String type){
        switch (type) {
            case "presence" : return ActivationCondition.presence;
            default: System.out.println("Error : invalid ActivationCondition type"); return ActivationCondition.presence;
        }
    }
}
