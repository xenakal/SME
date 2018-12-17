package com.smart_house;

import java.util.*;
import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class SmartHome {


    private static SmartHome sh = new SmartHome();

    private String name;
    private Map<String, Rooms> roomsMap;
    private Map<String,AbsSensor> sensorMap;  //utiliser qu'en local


    private SmartHome(){}

    public static SmartHome getSmartHome() {
        return sh;
    }
    
    public void smartHomeInit(String filename){
    //public SmartHome(String filename){
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
                    AbsSensor s = Factory.getInstance().makeSensor((String) sens.get("type"),(String) sens.get("name"));
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
                    r.addDevice( Factory.getInstance().makeActuator(type,name));
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
                ManagerFeature manager =  location.getManagerOfType(Enum.convertToManager(type));
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

    public void addRoom(String name, Rooms room){ // TODO: Rooms contient déjà un nom, du coup pas besoin de le remettre en argument ici
        roomsMap.put(name, room);
        System.out.println("#Room "+name+" added to the rooms of the house ");
    }

}


