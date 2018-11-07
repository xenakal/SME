import java.util.ArrayList;
import java.util.List;

public class Thermostat implements FeatureManager{


    private List<Radiator> radiators ;
    private  List<Rooms> rooms;

    public Thermostat() {
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


    @Override
    public void react(Info info) {
        switch (info.getName()) {
            case "temperature" :
                    for (Radiator rad : radiators) {   //remplacer par getLights ou pas car perte de rapidité
                        rad.setTemp(info.getValue());
                    }
                break;
            default: break;
        }
    }
}
