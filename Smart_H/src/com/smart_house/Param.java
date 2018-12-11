package com.smart_house;

import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Param {
    private static Param instance = new Param(); //singelton
    private FeatureCompo mainFeature;

    public static Param getInstance(){
        return instance;
    }

    private Param(){
        this.mainFeature = new FeatureCompo(true, "main", null, null);
        FeatureCompo room = new FeatureCompo(false, "room", null, null);
        this.mainFeature.add(room);
        String[][] roomNameList = {{"kitchen","mandatory"},{"bedroom","mandatory"}};//{"living","mandatory"},{"bathroom",,"mandatory"},{"balcony","free"}
        for(String[] r : roomNameList){
            room.add(new RoomFeature(r[0],r[1]));
        }

        FeatureCompo sensor = new FeatureCompo(false, "sensor", null, null);
        this.mainFeature.add(sensor);
        String[][] sensorNameList = {{"motion","or"},{"thermo","or"},{"alarmBox","or"}};
        for(String[] sens : sensorNameList){
            sensor.add(new sensorFeature(sens[0],sens[1]));
        }

        FeatureCompo actuator = new FeatureCompo(false, "actuator", null, null);
        this.mainFeature.add(actuator);
        String[][] actuNameList = {{"light","or"},{"radiator","or"},{"climatisor","or"},{"coffee","or"},{"alarm","or"}};
        for(String[] actu : actuNameList){
            actuator.add(new actuatorFeature(actu[0],actu[1]));
        }

        FeatureCompo functonality = new FeatureCompo(false, "functionality", null, null);
        this.mainFeature.add(functonality);
        String[][] functNameList = {{"lightControl","or"},{"temperatureControl","or"},{"smartCoffee","or"},{"securityControl","or"}};
        for(String[] funct : functNameList){

            functonality.add(new functionalityFeature(funct[0],funct[1]));
        }

        //other constraints
        //temperaureControl => thermo & (radiator)
        Feature[][] conditions1 = {{sensor.getOneChild("thermo")},{actuator.getOneChild("radiator"),actuator.getOneChild("cimatisor")}};
        functonality.getOneChild("temperatureControl").setDependences(conditions1);
        //lightControl => motion & light
        Feature[][] conditions2 = {{sensor.getOneChild("motion")},{actuator.getOneChild("light")}};
        functonality.getOneChild("lightControl").setDependences(conditions2);
        //smartCoffee => coffee
        Feature[][] conditions3 = {{actuator.getOneChild("coffee")}};
        functonality.getOneChild("smartCoffee").setDependences(conditions3);
        //
        Feature[][] conditions4 = {{sensor.getOneChild("motion"),sensor.getOneChild("alarmBox")},{actuator.getOneChild("alarm")}};
        functonality.getOneChild("securityControl").setDependences(conditions2);
    }

    /**
     * Adapte la parametrisation au fichier de configuration
     * Ajoute les piece necessaire aux parametre et
     * Active les fonctionnalité indiquée dans le fichier de configuration
     */
    public void adaptToConfig(String configFileName){
        JSONParser parser = new JSONParser();
        try {
            //FileReader FR = new FileReader("/Users/DimiS/Documents/Maintenace & evolution M1Q1/SME/Smart_H/src/config.txt");
            Object obj = parser.parse(new FileReader(configFileName));
            JSONObject jsonObject = (JSONObject) obj;

            JSONObject config = (JSONObject) jsonObject.get("parametrisation");

            FeatureCompo roomParam = (FeatureCompo) mainFeature.getOneChild("room");
            JSONArray roomList = (JSONArray) jsonObject.get("rooms");
            Iterator roomIterator = roomList.iterator();
            while (roomIterator.hasNext()) {
                JSONObject rjs = (JSONObject) roomIterator.next();
                String roomName = (String) rjs.get("name");
                roomParam.add(new RoomFeature( roomName,"free"));
            }
            for(Feature r : roomParam.getChild()){
                r.active();
            }
            roomParam.active();

            JSONObject sensorConfig= (JSONObject) config.get("sensorParam");
            FeatureCompo sensorParam = (FeatureCompo) mainFeature.getOneChild("sensor");
            String[] sensorNameList = {"motion","thermo", "alarmBox"};
            for(String sens : sensorNameList){
                boolean param = (boolean) sensorConfig.get(sens);
                if(param)activeSensor(sens);//sensorParam.getOneChild(sens).active();
            }



            JSONObject actuConfig = (JSONObject) config.get("actuatorParam");
            FeatureCompo actuParam = (FeatureCompo) mainFeature.getOneChild("actuator");
            String[] actuNameList = {"light","coffee","radiator","climatisor","alarm"};
            for(String actu : actuNameList){
                boolean param = (boolean) actuConfig.get(actu);
                if(param)activeActuator(actu);//actuParam.getOneChild(actu).active();
            }

            JSONObject functConfig = (JSONObject) config.get("functionalityParam");
            FeatureCompo functParam = (FeatureCompo) mainFeature.getOneChild("functionality");
            String[] funNameList = {"lightControl","smartCoffee","temperatureControl","securityControl"};
            for(String f : funNameList){

                boolean param = (boolean) functConfig.get(f);
                if(param)activeFunctonality(f);//functParam.getOneChild(f).active();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isActiveSubFeature(String subFeature, String name){
        FeatureCompo subFeat = (FeatureCompo) mainFeature.getOneChild(subFeature);
        if(subFeat == null)return false; //error
        Feature feat = subFeat.getOneChild(name);
        if(feat == null)return false; //error
        return feat.isActive();
        //todo exception
        //todo more modulable
    }

    public boolean isActivailbleSubFeature(String subFeature, String name){
        FeatureCompo subFeat = (FeatureCompo) mainFeature.getOneChild(subFeature);
        if(subFeat == null)return false; //error
        Feature feat = subFeat.getOneChild(name);
        if(feat == null)return false; //error
        return feat.isActivable();
        //todo exception
        //todo more modulable
    }

    public void activeRoom(String roomName){
        //todo
    }
    public void deactiveRoom(String roomName){
        //todo
    }

    public void activeSensor(String sensorName){
        FeatureCompo sensorParamList = (FeatureCompo) mainFeature.getOneChild("sensor");
        sensorFeature s = (sensorFeature) sensorParamList.getOneChild(sensorName);
        if(s.isActive() || s.isActivable()){
            sensorParamList.activeChild(s);
        }else {
            System.out.println("Sensor" + sensorName+ " non-activable");
        }
    }
    public void deactiveSensor(String sensorName){
        FeatureCompo sensorParamList = (FeatureCompo) mainFeature.getOneChild("sensor");
        sensorFeature s = (sensorFeature) sensorParamList.getOneChild(sensorName);
        if(!s.isActive() || s.isDeactivable()){
            sensorParamList.deactivateChild(s);
        }else {
            System.out.println("Sensor" + sensorName+ " is not disactivable");
        }
    }

    public void activeActuator(String Name){
        FeatureCompo ParamList = (FeatureCompo) mainFeature.getOneChild("actuator");
        actuatorFeature a = (actuatorFeature) ParamList.getOneChild(Name);
        if(a.isActive() || a.isActivable()){
            ParamList.activeChild(a);
        }else {
            System.out.println("Actuator "+ Name+ "non-activable");
        }
    }
    public void deactiveActuator(String Name){
        FeatureCompo ParamList = (FeatureCompo) mainFeature.getOneChild("actuator");
        actuatorFeature a  = (actuatorFeature) ParamList.getOneChild(Name);
        if(!a.isActive() || a.isDeactivable()){
            ParamList.deactivateChild(a);
        }else {
            System.out.println("Actuator "+ Name + " is not disactivable");
        }
    }

    public void activeFunctonality(String Name){
        FeatureCompo ParamList = (FeatureCompo) mainFeature.getOneChild("functionality");
        functionalityFeature f = (functionalityFeature) ParamList.getOneChild(Name);
        if(f.isActive() || f.isActivable()){
            ParamList.activeChild(f);
        }else {
            System.out.println("Functionnality "+ Name + " is not activable");
        }
    }
    public void deactiveFunctonality(String Name){
        FeatureCompo ParamList = (FeatureCompo) mainFeature.getOneChild("functionality");
        functionalityFeature f = (functionalityFeature) ParamList.getOneChild(Name);
        if(!f.isActive() || f.isDeactivable()){
            ParamList.deactivateChild(f);
        }else {
            System.out.println("Functionnality "+ Name + " is not disactivable");
        }
    }

//###############################################################################################################################################
    public class RoomFeature extends Feature {

        public RoomFeature(String name, String parentDependence) {
            super(name, parentDependence);
        }

        @Override
            public boolean localCheck() {
            return true ;
                //return SmartHome.getSmartHome().getRoomsMap().containsKey(this.getName());
            }


        @Override
        protected void active() {
            super.active();
        }
        //todo activation of room
    }


//###############################################################################################################################################
    public class sensorFeature extends Feature {
        public sensorFeature(String name, String parentDependence) {
            super(name, parentDependence);
        }

        @Override
        public boolean localCheck() {
            for (Rooms r : SmartHome.getSmartHome().getRoomsMap().values()){
                Enum.Sensor type = Enum.convertToSensor(this.getName());
                 if(r.getSensorOfType(type) != null && !r.getSensorOfType(type).isEmpty()){return true;}
            }
            return false;
        }

        @Override
        protected void active() {
            super.active();
            for (Rooms r : SmartHome.getSmartHome().getRoomsMap().values()){
                Enum.Sensor type = Enum.convertToSensor(this.getName());
                if(r.getSensorOfType(type) != null) {
                    for(AbsSensor s :r.getSensorOfType(type)){
                        if (!s.isActive()){
                            s.active();
                        }
                    }
                }
            }

        }

        @Override
        protected void deactivate() {
            super.deactivate();
            for (Rooms r : SmartHome.getSmartHome().getRoomsMap().values()){
                Enum.Sensor type = Enum.convertToSensor(this.getName());
                if(r.getSensorOfType(type) != null) {
                    for (AbsSensor s : r.getSensorOfType(type)) {
                        if (s.isActive()) {
                            s.deactive();
                        }
                    }
                }
            }

        }
    }
//###############################################################################################################################################
    public class actuatorFeature extends Feature {
        public actuatorFeature(String name, String parentDependence) {
            super(name, parentDependence);
        }

        @Override
        public boolean localCheck() {
            for (Rooms r : SmartHome.getSmartHome().getRoomsMap().values()){
                Enum.Actuator type = Enum.convertToActu(this.getName());
                if(r.getActuatorOfType(type) != null && !r.getActuatorOfType(type).isEmpty()){
                    return true ;
                }
            }
            return false;
        }

        @Override
        protected void active() {
            super.active();
            for (Rooms r : SmartHome.getSmartHome().getRoomsMap().values()){
                Enum.Actuator type = Enum.convertToActu(this.getName());
                if(r.getActuatorOfType(type) != null) {
                    for(Actuator a :r.getActuatorOfType(type)) {
                        if (!a.isActive()) {
                            a.active();
                        }
                    }
                }
            }

        }

        @Override
        protected void deactivate() {
            super.deactivate();
            for (Rooms r : SmartHome.getSmartHome().getRoomsMap().values()){
                Enum.Actuator type = Enum.convertToActu(this.getName());
                if(r.getActuatorOfType(type) != null) {
                    for(Actuator a :r.getActuatorOfType(type)){
                        if (a.isActive()){
                            a.deactive();
                        }
                    }
                }
            }

        }
    }
//###############################################################################################################################################
    public class functionalityFeature extends Feature {
        public functionalityFeature(boolean isActivate, String name, Feature[][] dependences, String parentDependence) {
            super(isActivate, name, dependences, parentDependence);
        }

        public functionalityFeature(String name, String parentDependence) {
            super(name, parentDependence);
        }

        @Override
        public boolean localCheck() {
            for (Rooms r : SmartHome.getSmartHome().getRoomsMap().values()){
                Enum.Manager type = Enum.convertToManager(this.getName());
                if(r.getManagerOfType(type) != null){
                    return true ;
                }
            }
            return false;
        }

        @Override
        protected void active() {
            super.active();
            for (Rooms r : SmartHome.getSmartHome().getRoomsMap().values()){
                Enum.Manager type = Enum.convertToManager(this.getName());
                if(r.getManagerOfType(type) != null) {
                    if (!r.getManagerOfType(type).isActive()) {
                        r.getManagerOfType(type).active();
                    }
                }
            }
        }


        @Override
        protected void deactivate() {
            super.deactivate();
            for (Rooms r : SmartHome.getSmartHome().getRoomsMap().values()){
                Enum.Manager type = Enum.convertToManager(this.getName());
                if(r.getManagerOfType(type) != null) {
                    if (r.getManagerOfType(type).isActive()) {
                        r.getManagerOfType(type).deactive();
                    }
                }
            }

        }
    }
}
