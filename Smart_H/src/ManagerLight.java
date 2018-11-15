import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manage the activation/disactivation of lights  according to presence of people
 */

public class ManagerLight implements ManagerFeature {
    private List<ActuLight> lights ;  //toutes les lampes
    private  List<Rooms> rooms;
    private Info lastinfo;
    //mode d'allumage
    private Map<String,List<ActuLight>> modeMap;
    private String currentmode = "all";


    public ManagerLight() {
        lights = new ArrayList<ActuLight>();
        rooms = new ArrayList<Rooms>();
        modeMap = new HashMap<String,List<ActuLight>>();
        //Default mode : all
        modeMap.put("all", lights);
    }

    public void add(ActuLight l){
        if (!lights.contains(l)){
            lights.add(l);
            this.update();
        }
    }


    public void add(Rooms r){
        if (!rooms.contains(r)){
            rooms.add(r);
            this.update();
        }
    }

    public void remove(ActuLight l){
        lights.remove(l);
        this.update();
    }

    public void remove(Rooms r){
        if (!rooms.contains(r)) {
            rooms.remove(r);
            this.update();
        }
    }

    public void update(){
        //update "all"
        lights = getLights();
        //for all other mode suppress the removed lights
        for (List<ActuLight> lightList: modeMap.values()) {
            for (ActuLight li: lightList) {
                if (! lights.contains(li)){
                    System.out.println("Update : "+ li.toString() + "suppressed "); //TODO remove
                    lightList.remove(li);
                }
            }
        }

    }

    public void setMode(String name){
        if (modeMap.containsKey(name)){
            currentmode = name;
            react(lastinfo);
        }else{
            System.out.println("Invalid mode : this mode doesn't exist in this manager");
        }
    }
    public void addMode(String name){
        if (modeMap.containsKey(name)){
            System.out.println("mode name is alreaddy used for this manager");
        }else{
            modeMap.put(name, new ArrayList<ActuLight>());
        }
    }
    public void removeMode(String name){
        if (modeMap.containsKey(name)){
            modeMap.remove(name);
        }
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
        lastinfo = info;
        List<ActuLight> myLight = modeMap.get(currentmode);
        switch (info.getName()) {
            case "motion" :
                if (info.getValue() == 1) {  //true = 1
                    for (ActuLight li : myLight) {
                        li.turn_on();
                    }

                }else{
                    for (ActuLight li : myLight) {
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
