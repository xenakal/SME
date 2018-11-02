
import org.json.simple.*;
import org.json.simple.parser.ParseException;
import java.util.*;
import java.io.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sun.management.Sensor;

public class Smart_Home {

        private Map<String, Rooms> rooms;
        private Map<String,MotionDetector> sensorMap;  //TODO absSensor
/*

        //Creation de la maison
        //Initialise Controlled devices
        Light bedroom_light = new Light("bedroom");
        Light living_light = new Light("living_1");
        Light living_light2 = new Light("living_2"); //une 2e lampe
        Light kitchen_light = new Light("kitchen");
        CoffeeMachine coffee = new CoffeeMachine("What else ...");


        //init sensors
        MotionDetector bedroom_motion_detector = new MotionDetector();
        MotionDetector living_motion_detector = new MotionDetector();
        MotionDetector kitchen_motion_detector = new MotionDetector();



        //Creation des piece
        //bedroom
        Rooms bedroom = new Rooms();

        //living
        Rooms living = new Rooms();

        //kitchen
        Rooms kitchen = new Rooms();


        //init manger
        Lightmanager bedroom_lightmanager = new Lightmanager();
        Lightmanager living_lightmanager = new Lightmanager();
        Lightmanager open_kitchen_lightmanager = new Lightmanager();
        CoffeeManager coffeeManager = new CoffeeManager();*/

    public Smart_Home(String filename){
        rooms = new HashMap<String, Rooms>();
        sensorMap = new HashMap<String, MotionDetector>();//TODO Change to Abssensor

        JSONParser parser = new JSONParser();

        try {
            //FileReader FR = new FileReader("/Users/DimiS/Documents/Maintenace & evolution M1Q1/SME/Smart_H/src/config.txt");
            Object obj = parser.parse(new FileReader("/Users/DimiS/Documents/Maintenace & evolution M1Q1/SME/Smart_H/src/config.json5"));
            JSONObject jsonObject = (JSONObject) obj;

            String name = (String) jsonObject.get("name");
            System.out.println("Name: " + name);

            //lecture des pieces
            JSONArray roomList = (JSONArray) jsonObject.get("rooms");
            System.out.println("\nRoom List:");
            Iterator roomIterator = roomList.iterator();
            while (roomIterator.hasNext()) {
                JSONObject rjs = (JSONObject) roomIterator.next();
                String roomname = (String) rjs.get("name");
                Rooms r = new Rooms(roomname);
                rooms.put(roomname, r);

                //ajout des sensor
                JSONArray sensors = (JSONArray) rjs.get("sensor");
                Iterator sensorIterator = sensors.iterator();
                while (sensorIterator.hasNext()){
                    JSONObject sens = (JSONObject) sensorIterator.next();
                    MotionDetector s; //TODO replace by AbsSensor s;
                    switch ((String) sens.get("type")){
                        case "motion" : s = new MotionDetector((String) sens.get("name")); break;
                        default:
                            System.out.println("Error in json parse");
                            s= new MotionDetector((String) sens.get("name"));
                    }
                    r.addSensor(s);
                    sensorMap.put((String) sens.get("name"),s);
                }


                //ajout des device
                JSONArray devices = (JSONArray) rjs.get("actuator");
                Iterator deviceIterator = sensors.iterator();
                while (deviceIterator.hasNext()){
                    JSONObject dev = (JSONObject) deviceIterator.next();
                    switch ((String) dev.get("type")){
                        case "light" : r.addDevice(new Light((String) dev.get("name"))); break;
                        case "coffee" : r.addDevice(new CoffeeMachine((String) dev.get("name")));break;
                    }
                }
                if((Boolean) jsonObject.get("makeManagerforeachroom")){
                    //cree un manager pour chaque type d'actuator
                    r.makeManagerForUsedDevices();
                }

                //TODO connecter les sensor et le manager ¿mettre les lien directement avec le sensor?
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

/*
        //Lier les devises aux piece des piece
        //bedroom
        bedroom.addDevice(bedroom_light);
        //living
        living.addDevice(living_light);
        living.addDevice(living_light2);
        //kitchen
        kitchen.addDevice(kitchen_light);
        kitchen.addDevice(coffee);


        //init manager
        bedroom_lightmanager.add(bedroom_light);
        living_lightmanager.add(living);
        open_kitchen_lightmanager.add(living);        //allumerra la cuisine et le living
        open_kitchen_lightmanager.add(kitchen);      //allumerra la cuisine et le living
        //coffeeManager.add(coffee);


        //lier un sensor à une/des lightmanager
        bedroom_motion_detector.attach(bedroom_lightmanager);
        living_motion_detector.attach(living_lightmanager);
        kitchen_motion_detector.attach(coffeeManager);
        kitchen_motion_detector.attach(open_kitchen_lightmanager);
*/
    }
}


