import java.util.LinkedList;
import java.util.List;

/**
 * Manage the activation/disactivation of lights  according to presence of people
 */

public class Lightmanager {//implements FeatureManager{
    private List<Light> lights ;

    public Lightmanager() {
        lights = new LinkedList<Light>();
    }

    public void addLight(Light l){
        if (!lights.contains(l)){
            lights.add(l);
        }
    }

    public void removeLight(Light l){
        lights.remove(l);
    }

    public List<Light> getLights() {
        return lights;
    }


    //@Override
    public void react(Info[] infoTab) {
        for (Info info: infoTab
             ) {
            switch (info.name) {
                case "motion" :
                    if (info.value == 1) {  //true = 1
                        for (Light li : lights) {
                            li.turn_on();
                        }
                    }else{
                        for (Light li : lights) {
                            li.turn_off();
                        }
                    }
                    break;
                    default: break;
            }

        }
    }
}
