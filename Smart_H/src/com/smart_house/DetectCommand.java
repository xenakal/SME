package com.smart_house;

import java.util.List;
/**
 * Concrete Client class
 */

public class DetectCommand implements GeneralCommand{

    private SmartHome sh;

    public DetectCommand(){
        sh = SmartHome.getSmartHome();
    }

    @Override
    public void execute(String[] in_arr) {
        try {
            Rooms room_detect = sh.getRoomsMap().get(in_arr[1]);
            if(room_detect==null)
                System.out.println("there is no such room !");
            try {
                Enum.Sensor sensor_type = Enum.convertToSensor(in_arr[2]);
                List<AbsSensor> sensors = room_detect.getSensorOfType(sensor_type);
                if (sensors == null)
                    System.out.println("there is no such sensor !");
                else {
                    try {
                        int detected_value = Integer.parseInt(in_arr[3]);
                        for (AbsSensor sensor : sensors) {
                            sensor.detect(detected_value);
                        }
                    } catch (Exception e) {
                        System.out.println("Value should be an integer, try again please!");
                    }
                }
            }
            catch (Exception e){
                if(e instanceof IllegalArgumentException){
                    System.out.println("Exception! invalid sensor type");
                }
            }
        }catch (Exception e){
            System.out.println("Exception in detectCommand :" + e.toString());
            usage();
        }

    }

    public void usage(){
        System.out.println("Usage DETECT : detect room sensor_type value");
    }
}
