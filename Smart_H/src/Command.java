import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.List;


public class Command { // BROKER CLASS IN COMMAND PATTERN

    /*
    * How to use: Type a sequence of words with the following pattern
    *   - first word "detect" --> second word should be the Room --> third the type of command (ex. motion) --> fourth the value
    *   - first word "param" --> second word should be the type of actuator --> third the type of command (ex. SetTemperature) --> fourth the Room -> fifth the value
    *   - first word "config" --> second word should be the Room, sensor or actuator to add --> third the Room where to add the actuator/sesor if applicable
    */
    public static void start(){

        Smart_Home sh = Smart_Home.getSmartHome();


        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean end = false;
            while(!end){
                System.out.println("Tell us what is happening in the house");
                String in = br.readLine();
                String[] in_arr = in.split(" "); // first word should be type of command
                String command_type = in_arr[0];
                switch (command_type) {
                    case "detect": // second word is Room, third is Type (ex. motion or temperature) fourth is value
                        handle_detect(sh, in_arr);
                        break;
                    case "param":
                        handle_param(sh, in_arr);
                        break;
                    case "config":
                        handle_config(sh, in_arr);
                        break;
                    case "exit":
                        end = true;
                        break;
                    default:
                        System.out.println("wrong type of command, please try again");
                        System.out.println();
                        System.out.println("The command read is:");
                        System.out.println(in);
                        break;

                }
            }
        }
        catch (Exception e){
            System.out.println("Some unexpected Exception occured :/ ");
        }

    }

    private static void handle_detect(Smart_Home sh, String[] in_arr){
        Rooms room_detect = sh.getRoomsMap().get(in_arr[1]);
        if(room_detect==null)
            System.out.println("there is no such room !");
        try {
            Enum.Sensor sensor_type = Enum.Sensor.valueOf(in_arr[2]);
            List<AbsSensor> sensors = room_detect.getSensorOfType(sensor_type);
            if (sensors == null)
                System.out.println("there is no such room !");
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
                System.out.println("invalid sensor type");
            }
        }
    }

    private static void handle_param(Smart_Home sh, String[] in_arr){

        Rooms room = sh.getRoomsMap().get(in_arr[3]); // fourth argument is Room name

        switch (Enum.Actuator.valueOf(in_arr[1])) { // second argument is manager type (light, radiator)
            case light:
                ManagerLight light_manager = (ManagerLight) room.getManager(Enum.Actuator.light);
                // TODO: add light manager new method
                break;
            case radiator:
                ManagerThermo thermo_manager = (ManagerThermo) room.getManager(Enum.Actuator.radiator);
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

    private static void handle_config(Smart_Home sh, String[] in_arr){

    }


}
