import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Manage the activation/disactivation of lights  according to presence of people
 */

public class Lightmanager implements FeatureManager{
    private List<Light> lights ;
    private  List<Rooms> rooms;

    public Lightmanager() {
        lights = new ArrayList<Light>();
        rooms = new ArrayList<Rooms>();
    }

    public void add(Light l){
        if (!lights.contains(l)){
            lights.add(l);
        }
    }


    public void add(Rooms r){
        if (!rooms.contains(r)){
            rooms.add(r);
        }
    }

    public void remove(Light l){
        lights.remove(l);
    }

    public void remove(Rooms r){
        rooms.remove(r);
    }

    public List<Light> getLights() {
        List<Light> list = new ArrayList<Light>();
        for(Rooms r : rooms){
            for (Actuator l : r.getActuatorofType(Enum.Actuator.coffee)){
                if (!list.contains((Light) l)){
                    list.add((Light) l);
                }
            }
        }
        for (Light l : lights){
            if (!list.contains(l)){
                list.add(l);
            }
        }
        return list;
    }


    @Override
    public void react(Info info) {
        switch (info.getName()) {
            case "motion" :
                if (info.getValue() == 1) {  //true = 1
                    for (Light li : this.getLights()) {   //remplacer par getLights ou pas car perte de rapidité
                        li.turn_on();
                    }

                }else{
                    for (Light li : this.getLights()) {
                        if(li.on)
                            li.turn_off();
                    }
                }
                break;
            default: break;
        }
    }


    public String ToString(){
        String str =  "LightManager : " ;
        for (Light l: getLights()) {
            str = str + l.toString();
        }
        return str;
    }
}
