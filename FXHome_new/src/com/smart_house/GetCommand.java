package com.smart_house;

/**
 * Concrete Client class
 */


public class GetCommand implements GeneralCommand{

    private SmartHome sh;

    public GetCommand(){
        sh = SmartHome.getSmartHome();
    }

    @Override
    public void execute(String[] in_arr) {
        try {
            Rooms room = sh.getRoomsMap().get(in_arr[1]); // second argument is the room
            Enum.Actuator actuator = Enum.convertToActu(in_arr[2]); // third argument is the actuator/manager
            Enum.Manager managerType;
            if (actuator != null) {
                managerType = Enum.getCorrespondingManager(actuator);
            }else {
                managerType = Enum.convertToManager(in_arr[2]);
            }
            String attribute = in_arr[3]; // fourth argument is the attribute (ex. tolerance, temperature, light_state)
            switch (managerType) {
                case lightManager:
                    ManagerLight light_manager = (ManagerLight) room.getManagerOfType(Enum.Manager.lightManager);
                    switch (attribute) {
                        case "light_state":
                            light_manager.getStates();
                            break;
                        default:
                            System.out.println("wrong attribute");
                            break;
                    }
                    break;
                case temperatureManager:
                    ManagerThermo thermo_manager = (ManagerThermo) room.getManagerOfType(Enum.Manager.temperatureManager);
                    switch (attribute) {
                        case "tolerance":
                            int tol = thermo_manager.getTolerance();
                            System.out.println("#Tolerance is " + tol);
                            break;
                        case "requiredTemperature":
                            int temp = thermo_manager.getRequired_temperature();
                            System.out.println("#Goal Temperature is " + temp);
                            break;
                        case "temperature":
                            int temp1 = thermo_manager.getTemperature(); // last recorded temperature
                            System.out.println("#Temperature is " + temp1);
                            break;
                        default:
                            System.out.println("wrong attribute");
                            break;
                    }
                    break;
                default:
                    System.out.println("not a valid actuator");
            }

        }catch (Exception e){
            System.out.println("Exception in getCommand :" + e.toString());
            usage();
        }
    }

    public void usage(){
        System.out.println("Usage : get room actuator_type attribute");
    }

}
