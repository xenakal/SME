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
        Rooms room = sh.getRoomsMap().get(in_arr[1]); // second argument is the room
        Enum.Actuator actuator = Enum.Actuator.valueOf(in_arr[2]); // third argument is the actuator
        String attribute = in_arr[3]; // fourth argument is the attribute (ex. tolerance, temperature, light_state)
        switch (actuator) {
            case light:
                ManagerLight light_manager = (ManagerLight) room.getManagerOfType(Enum.getCorrespondingManager(actuator));
                switch (attribute) {
                    case "light_state":
                        light_manager.getStates();
                        break;
                    default:
                        System.out.println("wrong attribute");
                        break;
                }
                break;
            case radiator:
                ManagerThermo thermo_manager = (ManagerThermo) room.getManagerOfType(Enum.getCorrespondingManager(actuator));
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
    }

}
