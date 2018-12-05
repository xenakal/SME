import java.util.List;
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
        FeatureCompo room = new FeatureCompo(false, "Room", null, null);
        this.mainFeature.add(room);
        //room.add(new RoomFeature(false,"living",null, "mandatory"));
        room.add(new RoomFeature(false,"kitchen",null, "mandatory"));
        //room.add(new RoomFeature(false,"bathroom",null, "mandatory"));
        room.add(new RoomFeature(false,"bedroom",null, "mandatory"));
        //room.add(new RoomFeature(false,"balcony",null, "free"));
        //TODO reflection on the feature model for other rooms


        FeatureCompo sensor = new FeatureCompo(false, "Sensors", null, null);
        this.mainFeature.add(sensor);
        String[][] sensorNameList = {{"motion","or"},{"thermo","or"}};
        for(String[] sens : sensorNameList){
            sensor.add(new sensorFeature(sens[0],sens[1]));
        }

        FeatureCompo actuator = new FeatureCompo(false, "Actuators", null, null);
        this.mainFeature.add(actuator);
        String[][] actuNameList = {{"light","or"},{"radiator","or"},{"coffee","or"}};
        for(String[] actu : actuNameList){
            actuator.add(new actuatorFeature(actu[0],actu[1]));
        }

        FeatureCompo functonality = new FeatureCompo(false, "Functionality", null, null);
        this.mainFeature.add(functonality);
        String[][] functNameList = {{"lightControl","or"},{"temperatureControl","or"},{"smartCoffee","or"}};
        for(String[] funct : functNameList){

            functonality.add(new functionalityFeature(funct[0],funct[1]));
        }

        //other constraints
        //temperaureControl => thermo & (radiator| climatisor)
        //todo
        //lightControl => motion & light
        //todo
        //smartCoffee => coffee

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

            FeatureCompo roomParam = (FeatureCompo) mainFeature.getOneChild("Room");
            JSONArray roomList = (JSONArray) jsonObject.get("rooms");
            Iterator roomIterator = roomList.iterator();
            while (roomIterator.hasNext()) {
                JSONObject rjs = (JSONObject) roomIterator.next();
                String roomName = (String) rjs.get("name");
                roomParam.add(new RoomFeature( roomName,"free"));
            }
            for(Feature r : roomParam.getChild()){
                roomParam.activeChild(r);
            }

            JSONObject sensorConfig= (JSONObject) config.get("sensorParam");
            FeatureCompo sensorParam = (FeatureCompo) mainFeature.getOneChild("Sensors");
            boolean motionParam = (boolean) sensorConfig.get("motion");
            if(motionParam)sensorParam.getOneChild("motion").active();
            boolean thermoParam = (boolean) sensorConfig.get("thermo");
            if(thermoParam)sensorParam.getOneChild("thermo").active();


            JSONObject actuConfig = (JSONObject) config.get("actuatorParam");
            FeatureCompo actuParam = (FeatureCompo) mainFeature.getOneChild("Actuators");
            boolean lightParam = (boolean) actuConfig.get("light");
            if(lightParam)actuParam.getOneChild("light").active();
            boolean coffeeParam = (boolean) actuConfig.get("coffee");
            if(coffeeParam)actuParam.getOneChild("coffee").active();
            boolean radiatorParam = (boolean) actuConfig.get("radiator");
            if(radiatorParam)actuParam.getOneChild("radiator").active();

            JSONObject functConfig = (JSONObject) config.get("functionalityParam");
            FeatureCompo functParam = (FeatureCompo) mainFeature.getOneChild("Functionality");
            boolean lightManParam = (boolean) functConfig.get("lightControl");
            if(lightManParam) functParam.getOneChild("lightControl").active();
            boolean coffeeManParam = (boolean) functConfig.get("smartCoffee");
            if(coffeeManParam) functParam.getOneChild("smartCoffee").active();
            boolean thermoManParam = (boolean) functConfig.get("temperatureControl");
            if(thermoManParam) functParam.getOneChild("temperatureControl").active();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void activeRoom(String roomName){
        //todo
    }
    public void deactiveRoom(String roomName){
        //todo
    }
    public void activeSensor(String sensorName){
        Enum.Sensor name = Enum.convertToSensor(sensorName);
        FeatureCompo sensorParamList = (FeatureCompo) mainFeature.getOneChild("Sensors");
        sensorFeature s = (sensorFeature) sensorParamList.getOneChild(sensorName);
        if(s.isActivable()){
            sensorParamList.activeChild(s);
        }
    }
    public void deactiveSensor(String sensorName){
        Enum.Sensor name = Enum.convertToSensor(sensorName);
        FeatureCompo sensorParamList = (FeatureCompo) mainFeature.getOneChild("Sensors");
        sensorFeature s = (sensorFeature) sensorParamList.getOneChild(sensorName);
        if(s.isDeactivable()){
            sensorParamList.deactivateChild(s);
        }
    }

    public void activeActuator(String Name){
        Enum.Actuator name = Enum.convertToActu(Name);
        FeatureCompo ParamList = (FeatureCompo) mainFeature.getOneChild("Actuators");
        actuatorFeature a = (actuatorFeature) ParamList.getOneChild(Name);
        if(a.isActivable()){
            ParamList.activeChild(a);
        }
    }
    public void deactiveActuator(String Name){
        Enum.Actuator name = Enum.convertToActu(Name);
        FeatureCompo ParamList = (FeatureCompo) mainFeature.getOneChild("Actuators");
        actuatorFeature a  = (actuatorFeature) ParamList.getOneChild(Name);
        if(a.isDeactivable()){
            ParamList.deactivateChild(a);
        }
    }

    public void activeFunctonality(String Name){
        Enum.Manager name = Enum.convertToManager(Name);
        FeatureCompo ParamList = (FeatureCompo) mainFeature.getOneChild("Functionality");
        functionalityFeature f = (functionalityFeature) ParamList.getOneChild(Name);
        if(f.isActivable()){
            ParamList.activeChild(f);
        }
    }
    public void deactiveFunctonality(String Name){
        Enum.Manager name = Enum.convertToManager(Name);
        FeatureCompo ParamList = (FeatureCompo) mainFeature.getOneChild("Functionality");
        functionalityFeature f = (functionalityFeature) ParamList.getOneChild(Name);
        if(f.isDeactivable()){
            ParamList.deactivateChild(f);
        }
    }
    //todo  tester activation desactivation

//###############################################################################################################################################
    public class RoomFeature extends Feature {
        public RoomFeature(boolean isActivate, String name, Feature[] dependences, String parentDependence) {
            super(isActivate, name, dependences, parentDependence);
        }

    public RoomFeature(String name, String parentDependence) {
        super(name, parentDependence);
    }

    @Override
        public boolean localCheck() {
            return SmartHome.getSmartHome().getRoomsMap().containsKey(this.getName());
        }
    }
    //todo activation of room
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
        public functionalityFeature(boolean isActivate, String name, Feature[] dependences, String parentDependence) {
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
