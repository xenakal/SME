import java.util.ArrayList;
import java.util.List;


public class ManagerThermo implements ManagerFeature {

    private String name;
    private int tolerance;  // tolerance on required temperature
    private int required_temperature;


    public int getTolerance() {
        return tolerance;
    }

    public void setTolerance(int tolerance) {
        this.tolerance = tolerance;
    }

    public int getRequired_temperature() {
        return required_temperature;
    }

    public void setRequired_temperature(int required_temperature) {
        this.required_temperature = required_temperature;
    }

    private List<ActuRadiator> radiators ;
    private  List<Rooms> rooms;

    public ManagerThermo(int required_temperature, int tolerance) {
        this.required_temperature = required_temperature;
        this.tolerance = tolerance;
        radiators = new ArrayList<ActuRadiator>();
        rooms = new ArrayList<Rooms>();
    }


    public void add(ActuRadiator l){
        if (!radiators.contains(l)){
            radiators.add(l);
            update();
        }
    }


    public void add(Rooms r){
        if (!rooms.contains(r)){
            rooms.add(r);
            update();
        }
    }

    public void remove(ActuLight l){
        radiators.remove(l);
        update();
    }

    public void remove(Rooms r){
        rooms.remove(r);
        update();
    }

    @Override
    public void update() {
        radiators = getRadiator();
    }

    public List<ActuRadiator> getRadiator() {
        List<ActuRadiator> list = new ArrayList<ActuRadiator>();
        upDateRadiator(list);
        return list;
    }

    public void upDateRadiator(List<ActuRadiator> list) {
        for(Rooms r : rooms){
            for (Actuator rad : r.getActuatorofType(Enum.Actuator.radiator)){
                if (!list.contains((ActuRadiator) rad)){
                    list.add((ActuRadiator) rad);
                }
            }
        }
        for (ActuRadiator rad : radiators){
            if (!list.contains(rad)){
                list.add(rad);
            }
        }
    }



    @Override
    public void react(Info info) {
        switch (info.getName()) {
            case "temperature" :
                    for (ActuRadiator rad : radiators) {   //remplacer par getLights ou pas car perte de rapidité
                        applyTemperature(rad,info.getValue());
                    }
                break;
            default: break;
        }
    }

    private void applyTemperature(ActuRadiator rad, int value){

        if(value < required_temperature - tolerance){
            if(!rad.getState())
                rad.turn_on();
        }
        else if(value > required_temperature + tolerance){
            if(!rad.getState())
                rad.turn_off();
        }
        else{
            System.out.println("already at good temperature");
        }
    }

    public void all_off(){
        for(ActuRadiator rad : radiators){
            rad.turn_off();
        }
    }



    @Override
    public String ToString() {
        String str =  "ThermoManager : " ;
        for (ActuRadiator r: radiators) {
            str = str + r.toString();
        }
        return str;
    }
}
