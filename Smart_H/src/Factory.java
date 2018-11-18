public class Factory {

    public static Actuator makeActuator(String type, String name){
        switch (type){
            case "light" : return new ActuLight(name);
            case "coffee" : return new ActuCoffeeMachine(name);
            case "radiator" : return new ActuRadiator(name);
            default: System.out.print("Type error in Actuator Factory, given type : "+type);return new ActuLight(name);
            //TODO launch error
        }
    }

    public static AbsSensor makeSensor(String type, String name){
        switch (type){
            case "motion" : return new SensorMotion(name);

            case "thermo" : return new SensorThermo(name);

            default:
                System.out.println("Error in json parse");
                return new SensorMotion(name);
                //TODO launch error
        }
    }


    public static ManagerFeature makeManager(Enum.Actuator act){
        ManagerFeature manager;
        // light,coffee,radiator,alarm
        //TODO improve ManagerFeature
        switch (act){
            case light: manager= new ManagerLight(); break;
            case radiator: manager = new ManagerThermo(20,0); break;
            case coffee: manager =new ManagerCoffee(); break;
            default:System.out.println("Error in makeManagerForUsedDevices : invalid actuator type");manager =new ManagerCoffee(); //coffee
                //TODO launch error
        }
        return manager;
    }
}
