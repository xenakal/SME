import java.util.*;
import java.util.concurrent.TimeUnit;

public class Sensor extends Thread {

    private Light connected_light;
    private boolean l_switch; // false -> light off, true -> light on
    private boolean movement; // if movement is detected
    private long startTime;


    public List<Light> obsList = new LinkedList<Light>();    //TODO FEATUREMANAGER

    public Sensor(Light light){
        this.connected_light = light;
        l_switch = false;
        movement = false;
    }

    public void sensor_on(){
        System.out.println("sensor is on");
        l_switch = true;
        start();
    }

    public void sensor_off(){
        System.out.println("sensor is off");
        l_switch = false;
        System.out.println(l_switch);
    }

    public void detect(int value){
        if(value==1)
            movement = true;
    }

    private void light_on(){
        startTime = System.currentTimeMillis();
        connected_light.turn_on();
    }

    private void light_off(){
        connected_light.turn_off();
    }

    public void run(){
        while(l_switch){
            if(!connected_light.on){ // light off
                if (movement) {
                    light_on();
                }
            }
            else{ // light on
                if(System.currentTimeMillis() - startTime >= 10000) { // bcp de temps sans mouvement
                    System.out.println("timeout");
                    light_off();
                }
                if (!movement) {
                    light_off();
                }
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
            o.react(new Info("motion",  movement));
            o.turn_on();
        }
    }


}
