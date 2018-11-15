
import java.util.*;
import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Smart_Home {


    private static Smart_Home sh = new Smart_Home();

    private String name;
    private Map<String, Rooms> roomsMap;
    private Map<String,AbsSensor> sensorMap;  //utiliser qu'en local


    private Smart_Home(){}

    public static Smart_Home getSmartHome() {
        return sh;
    }

    public void Smart_Home_Init(String filename){
    //public Smart_Home(String filename){
        roomsMap = new HashMap<String, Rooms>();
        sensorMap = new HashMap<String, AbsSensor>();

        JSONParser parser = new JSONParser();

        try {
            //FileReader FR = new FileReader("/Users/DimiS/Documents/Maintenace & evolution M1Q1/SME/Smart_H/src/config.txt");
            Object obj = parser.parse(new FileReader(filename));
            JSONObject jsonObject = (JSONObject) obj;

            name = (String) jsonObject.get("name");

            //lecture des pieces
            JSONArray roomList = (JSONArray) jsonObject.get("rooms");
            Iterator roomIterator = roomList.iterator();
            while (roomIterator.hasNext()) {
                JSONObject rjs = (JSONObject) roomIterator.next();
                String roomname = (String) rjs.get("name");
                Rooms r = new Rooms(roomname);
                roomsMap.put(roomname, r);

                //ajout des sensor
                JSONArray sensors = (JSONArray) rjs.get("sensor");
                Iterator sensorIterator = sensors.iterator();
                while (sensorIterator.hasNext()){
                    JSONObject sens = (JSONObject) sensorIterator.next();
                    AbsSensor s = Factory.makeSensor((String) sens.get("type"),(String) sens.get("name")); //TODO replace by AbsSensor s;
                    /*switch ((String) sens.get("type")){
                        case "motion" : s = new SensorMotion((String) sens.get("name")); break;
                        default:
                            System.out.println("Error in json parse");
                            s= new SensorMotion((String) sens.get("name"));
                    }*/
                    r.addSensor(s);
                    sensorMap.put((String) sens.get("name"),s);
                }


                //ajout des device
                JSONArray devices = (JSONArray) rjs.get("actuator");
                Iterator deviceIterator = devices.iterator();
                while (deviceIterator.hasNext()){
                    JSONObject dev = (JSONObject) deviceIterator.next();
                    String type = (String) dev.get("type");
                    String name = (String) dev.get("name");
                    r.addDevice( Factory.makeActuator(type,name));
                    //switch (type){
                    //    case "light" : r.addDevice(new ActuLight((String)dev.get("name") )); break;
                    //    case "coffee" : r.addDevice(new ActuCoffeeMachine((String) dev.get("name")));break;
                    //}
                }
                if((Boolean) jsonObject.get("makeManagerforeachroom")){
                    //cree un manager pour chaque type d'actuator
                    r.makeManagerForUsedDevices();
                }
            }
            //TODO ajouter la possibilté de créer un nouveau manager
            //TODO IMPROVE connecter les sensor et le manager ¿mettre les lien directement avec le sensor?
            JSONArray connections  = (JSONArray) jsonObject.get("connection");
            Iterator connectionIt = connections.iterator();
            while (connectionIt.hasNext()){
                JSONObject connect = (JSONObject) connectionIt.next();
                AbsSensor sens = sensorMap.get((String) connect.get("sensorname"));
                Rooms location = roomsMap.get((String) connect.get("roomactuator"));
                String type = (String) connect.get("actuatortype");
                ManagerFeature manager =  location.getManager(Enum.convertToActu(type));
                sens.attach(manager);  //TODO on pourrais permettre de lier un senor a plusieur manager directement
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String toString(){
        String str = "SmartHouse : "+name+"\n ";
        for (Rooms r: roomsMap.values()) {
            str = str + r.toString();
        }
        return str;
    }

    public String getName() {
        return name;
    }

    public Map<String, AbsSensor> getSensorMap() {
        return sensorMap;
    }

    public Map<String, Rooms> getRoomsMap() {
        return roomsMap;
    }

}


