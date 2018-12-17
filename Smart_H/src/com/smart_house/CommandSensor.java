package com.smart_house;

public class CommandSensor implements GeneralCommand{

    private SmartHome sh;

    public CommandSensor(){
        sh = SmartHome.getSmartHome();
    }

    @Override
    public void execute(String[] in_arr) {
        try {
            switch (in_arr[2]) { // second argument is manager type (light, radiator)
                case "setCode":
                    SensorAlarmBox alarm = (SensorAlarmBox) sh.getSensorMap().get(in_arr[1]);
                    int oldCode = Integer.parseInt(in_arr[3]);
                    int newCode = Integer.parseInt(in_arr[4]);
                    if(! alarm.setNewCode(oldCode, newCode)){
                        System.out.println("Invalid code, the code is still unchanged ");
                    }
                    break;
                default:
                    usage();
                    break;

            }
        }
        catch (Exception e){
            System.out.println("wrong parametrisation command");
            usage();
        }
    }

    public void usage(){
        System.out.println("Usage PARAM_SENSOR: param_sensor sensorName command_type value1 value2");
        System.out.println("The command_type are : setCode oldCode newCode");

    }
}

