import java.util.*;

public class Sensor {

    private Light connected_light;
    private boolean l_switch = false; // false -> light off, true -> light on
    private boolean movement = false; // if movement is detected
    public List<Light> obsList = new LinkedList<Light>();    //TODO FEATUREMANAGER
    public Sensor(Light light){
        this.connected_light = light;
    }

    public void sensor_on(){
        System.out.println("sensor is on");
        l_switch = true;
    }
    public void sensor_off(){
        System.out.println("sensor is off");
        l_switch = false;
    }

    public void detect_movement(){
        movement = true;
    }

    private void light_on(){
        connected_light.turn_on();
    }

    private void light_off(){
        connected_light.turn_off();
    }

    public void run(){
        while(l_switch){
            if(!connected_light.on) { // light off
                if (movement) {
                    light_on();
                }
            }
            else{ // light on
                if (!movement)
                    light_off();
            }

        }
    }

    public void attach(Light obs){
        if (!obsList.contains(obs)){
            obsList.add(obs);
        }
    }

    public void detach(Light obs){
        obsList.remove(obs);
    }

    public void advertise(){
        for (Light o: obsList
             ) {
            //o.react(info);
            o.turn_on();
        }
    }


}
