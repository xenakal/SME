package com.smart_house;

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

        switch (Enum.Actuator.valueOf(in_arr[1])) { // second argument is manager type (light, radiator)
            case light:
                ManagerLight light_manager = (ManagerLight) room.getManagerOfType(Enum.Manager.lightManager);
                // TODO: add light manager new method
                break;
            case radiator:
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

            default:
                System.out.println("not a valid manager type");
                break;

        }

    }
}