package application.javaApp;

/**
 * Concrete Client class
 */

public class ParamCommand implements GeneralCommand{

    private SmartHome sh;

    public ParamCommand(){
        sh = SmartHome.getSmartHome();
    }

    @Override
    public void execute(String[] in_arr) {

        Rooms room = sh.getRoomsMap().get(in_arr[3]); // fourth argument is Room name

        switch (Enum.Manager.valueOf(in_arr[1])) { // second argument is manager type (light, radiator)
            case lightManager:
                ManagerLight light_manager = (ManagerLight) room.getManagerOfType(Enum.Manager.lightManager);
                // TODO: add light manager new method
                break;
            case temperatureManager:
                ManagerThermo thermo_manager = (ManagerThermo) room.getManagerOfType(Enum.Manager.temperatureManager);
                int value = Integer.parseInt(in_arr[4]);

                switch (in_arr[2]) { // third argument is specific command (setRequiredTemperature or setTolerance)
                    case "setRequiredTemperature":
                        thermo_manager.setRequired_temperature(value); // fifth argument is value
                        break;

                    case "setTolerance":
                        thermo_manager.setTolerance(value);
                        break;

                    default:
                        System.out.println("not a valid command for this type of manager");
                        break;
                }

                break;
            case securityManager:
                if(in_arr.length != 5){
                    System.out.print("Usage : param setCode room oldCode newCode");
                    break;
                }
                int oldCode = Integer.parseInt(in_arr[4]);
                int newCode = Integer.parseInt(in_arr[5]);
                switch (in_arr[2]) {
                    case "setCode":
                        SensorAlarmBox alarm = (SensorAlarmBox) room.getSensorOfType(Enum.Sensor.alarmBox).get(0);
                        if(! alarm.setNewCode(oldCode, newCode)){
                           System.out.println("Invalid code, the code is still unchanged ");
                        }
                        break;
                }

            default:
                System.out.println("not a valid manager type");
                break;

        }

    }

    public void usage(){
        System.out.println("Usage : param manager command_type room value");
        System.out.println("The command_type are :   lightManager : ");
        System.out.println("                   temperatureManager : setRequiredTemperature, setTolerance");
        System.out.println("                      securityManager : setCode");
    }
}
