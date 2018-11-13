import java.util.ArrayList;

public class factory {

    public static Actuator makeActuator(String type, String name){
        switch (type){
            case "light" : return new Light(name);
            case "coffee" : return new CoffeeMachine(name);
            default: System.out.print("Type error in Actuator factory, given type : "+type);return new Light(name);
            //TODO launch error
        }
    }

    public static AbsSensor makeSensor(String type, String name){
        switch (type){
            case "motion" : return new MotionDetector(name);
            default:
                System.out.println("Error in json parse");
                return new MotionDetector(name);
                //TODO launch error
        }
    }
    

    public static FeatureManager makeManager(Enum.Actuator act){
        FeatureManager manager;
        //TODO improve FeatureManager
        switch (act){
            case light: manager= new Lightmanager(); break;
            case coffee: manager =new CoffeeManager(); break;
            default:System.out.println("Error in makeManagerForUsedDevices : invalid actuator type");manager =new CoffeeManager(); //coffee
                //TODO launch error
        }
        return manager;
    }
}
