import java.util.ArrayList;
import java.util.List;


public class Thermostat_Manager implements FeatureManager{

    private String name;
    private int tolerance;  // tolerance on required temperature
    private int required_temperature;
    private List<Radiator> radiators ;
    private  List<Rooms> rooms;

    public Thermostat_Manager(int required_temperature, int tolerance) {
        this.required_temperature = required_temperature;
        this.tolerance = tolerance;
        radiators = new ArrayList<Radiator>();
        rooms = new ArrayList<Rooms>();
    }


    public void add(Radiator l){
        if (!radiators.contains(l)){
            radiators.add(l);
        }
    }


    public void add(Rooms r){
        if (!rooms.contains(r)){
            rooms.add(r);
        }
    }


    public void all_off(){
        for(Radiator rad : radiators){
            rad.turn_off();
        }
    }

    @Override
    public String ToString() {
        return null;
    }

    public void remove(Light l){
        radiators.remove(l);
    }

    public void remove(Rooms r){
        rooms.remove(r);
    }


    public List<Radiator> getRadiator() {
        List<Radiator> list = new ArrayList<Radiator>();
        for(Rooms r : rooms){
            for (Actuator rad : r.getActuatorofType(Enum.Actuator.radiator)){
                if (!list.contains((Radiator) rad)){
                    list.add((Radiator) rad);
                }
            }
        }
        for (Radiator rad : radiators){
            if (!list.contains(rad)){
                list.add(rad);
            }
        }
        return list;
    }

    @Override
    public void react(Info info) {
        switch (info.getName()) {
            case "temperature" :
                    for (Radiator rad : this.getRadiator()) {   //remplacer par getLights ou pas car perte de rapidité
                        setTemperature(rad,info.getValue());
                    }
                break;
            default: break;
        }
    }

    private void setTemperature(Radiator rad, int value){

        if(value < required_temperature - tolerance){
            if(!rad.getState())
                rad.turn_on();
            rad.increase_heat();
        }
        else if(value > required_temperature + tolerance){
            if(!rad.getState())
                rad.turn_on();
            rad.decrease_heat();
        }
        else{
            System.out.println("already at good temperature");
        }
    }
}
