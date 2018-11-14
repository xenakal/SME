import java.util.ArrayList;
import java.util.List;

/**
 * Manage the activation/disactivation of lights  according to presence of people
 */

public class ManagerLight implements ManagerFeature {
    private List<ActuLight> lights ;
    private  List<Rooms> rooms;

    public ManagerLight() {
        lights = new ArrayList<ActuLight>();
        rooms = new ArrayList<Rooms>();
    }

    public void add(ActuLight l){
        if (!lights.contains(l)){
            lights.add(l);
        }
    }


    public void add(Rooms r){
        if (!rooms.contains(r)){
            rooms.add(r);
        }
    }

    public void remove(ActuLight l){
        lights.remove(l);
    }

    public void remove(Rooms r){
        rooms.remove(r);
    }

    public List<ActuLight> getLights() {
        List<ActuLight> list = new ArrayList<ActuLight>();
        for(Rooms r : rooms){
            for (Actuator l : r.getActuatorofType(Enum.Actuator.light)){
                if (!list.contains((ActuLight) l)){
                    list.add((ActuLight) l);
                }
            }
        }
        for (ActuLight l : lights){
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
                    for (ActuLight li : this.getLights()) {   //remplacer par getLights ou pas car perte de rapidité
                        li.turn_on();
                    }

                }else{
                    for (ActuLight li : this.getLights()) {
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
        for (ActuLight l: getLights()) {
            str = str + l.toString();
        }
        return str;
    }
}
