import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

/* TODO:
    - Do the Command correctly
    - Add an actual exception and handle all exceptions
    - JavaFx
 */
public class Command { // BROKER CLASS IN COMMAND PATTERN

    /*
    * How to use: Type a sequence of words with the following pattern
    *   - first word "get" --> second word should be the Room --> third the actuator type --> fourth the attribute (tolerance, temperature, light_state)
    *   - first word "detect" --> second word should be the Room --> third the type of command (ex. motion) --> fourth the value
    *   - first word "param" --> second word should be the type of actuator --> third the type of command (ex. SetTemperature) --> fourth the Room -> fifth the value
    *   - first word "config" --> second word should be the option "room", "sensor" or "actuator" --> third the Room where to add the actuator/sesor if applicable
    */
    public static void start(){

        SmartHome sh = SmartHome.getSmartHome();


        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            boolean end = false;
            while(!end){
                System.out.println("Tell us what is happening in the house");
                String in = br.readLine();
                String[] in_arr = in.split(" "); // first word should be type of command
                String command_type = in_arr[0];
                switch (command_type) {
                    case "print_house":
                        System.out.println(sh.toString());
                        break;
                    case "get":
                        handle_get(sh, in_arr);
                        break;
                    case "detect": // second word is Room, third is Type (ex. motion or temperature) fourth is value
                        handle_detect(sh, in_arr);
                        break;
                    case "param":
                        handle_param(sh, in_arr);
                        break;
                    //case "config":
                    //    handle_config(sh, in_arr);
                    //    break;
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

    private static void handle_get(SmartHome sh, String[] in_arr){
        Rooms room = sh.getRoomsMap().get(in_arr[1]); // second argument is the room
        Enum.Actuator actuator = Enum.Actuator.valueOf(in_arr[2]); // third argument is the actuator
        String attribute = in_arr[3]; // fourth argument is the attribute (ex. tolerance, temperature, light_state)
        switch (actuator) {
            case light:
                ManagerLight light_manager = (ManagerLight) room.getManager(actuator);
                switch (attribute){
                    case "light_state":
                        light_manager.getStates();
                        break;
                    default:
                        System.out.println("wrong attribute");
                        break;
                }
                break;
            case radiator:
                ManagerThermo thermo_manager = (ManagerThermo) room.getManager(actuator);
                switch (attribute){
                    case "tolerance":
                        int tol = thermo_manager.getTolerance();
                        System.out.println("#Tolerance is "+tol);
                        break;
                    case "requiredTemperature":
                        int temp = thermo_manager.getRequired_temperature();
                        System.out.println("#Goal Temperature is "+temp);
                        break;
                    case "temperature":
                        int temp1 = thermo_manager.getTemperature(); // last recorded temperature
                        System.out.println("#Temperature is "+temp1);
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

    private static void handle_detect(SmartHome sh, String[] in_arr){
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
                System.out.println("Exception! invalid sensor type");
            }
        }
    }

    private static void handle_param(SmartHome sh, String[] in_arr){

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

    private static void handle_config(SmartHome sh, String[] in_arr){
        switch (in_arr[1]){ // second argument is the room or sensor or actuator option
            case "room": // ajouter une chambre à la maison
                String name = in_arr[2]; // third argument is name of the room to be added
                sh.addRoom(name, new Rooms(name));
                break;
            case "sensor": // ajouter un senseur à une chambre
                Rooms sroom = sh.getRoomsMap().get(in_arr[2]); // third argument is name of the room in which to add the sensor
                String type = in_arr[3]; // forth argument is the type of the sensor (thermo or motion)
                String sname = in_arr[4];
                AbsSensor sens = Factory.getInstance().makeSensor(type,sname);
                sroom.addSensor(sens);
                ManagerFeature manager =  sroom.getManager(Enum.convertToActu(type));
                // TODO: CORRIGER
                sens.attach(manager);
                break;
            case "actuator": // ajouter un actuateur à une chambre
                Rooms aroom = sh.getRoomsMap().get(in_arr[2]); // third argument is name of the room in which to add the actuator
                String atype = in_arr[3]; // forth argument is the type of the actuator
                aroom.addDevice(Factory.getInstance().makeActuator(atype,in_arr[4]));
                break;
            default:
                System.out.println("can't add a "+in_arr[1]+", sorry :/");
                break;
        }
    }


}
