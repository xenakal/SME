import java.util.*;
import java.util.concurrent.TimeUnit;

public class Sensor extends Thread {

    private Light connected_light;
    private volatile boolean l_switch; // false -> light off, true -> light on
    private int movement; // if movement is detected (0==false)
    private long startTime;


    public List<Light> obsList = new LinkedList<Light>();    //TODO FEATUREMANAGER

    public Sensor(Light light){
        this.connected_light = light;
        l_switch = false;
        movement = 0;
    }

    public void sensor_on(){
        System.out.println("sensor is on");
        l_switch = true;
        start();
    }

    public void sensor_off(){
        System.out.println("sensor is off");
        l_switch = false;
    }

    public void detect(int value){
        if(value==1)
            movement = 1; // true
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
                if (movement==1) {
                    light_on();
                }
            }
            else{ // light on
                if(System.currentTimeMillis() - startTime >= 10000) { // bcp de temps sans mouvement
                    System.out.println("timeout");
                    light_off();
                }
                if (movement==0) {
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
            //o.react(info);
            o.turn_on();
        }
    }


}
