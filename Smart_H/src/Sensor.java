import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Sensor extends Thread {

    private Light connected_light;
    private volatile boolean l_switch; // false -> light off, true -> light on
    private int movement; // if movement is detected (0==false)
    private long startTime;


    public List<FeatureManager> obsList = new LinkedList<FeatureManager>();

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
                    //this.advertise(); //TODO decomment this line
                    light_on(); //TODO remove this line
                }
            }
            else{ // light on
                if(System.currentTimeMillis() - startTime >= 10000) { // bcp de temps sans mouvement
                    System.out.println("timeout");
                    //this.advertise();
                    light_off(); //TODO remove this line
                }
                if (movement==0) {
                    //this.advertise();
                    light_off(); //TODO remove this line
                }
            }
        }
    }

    public void attach(FeatureManager obs){
        if (!obsList.contains(obs)){
            obsList.add(obs);
        }
    }

    public void detach(FeatureManager obs){
        obsList.remove(obs);
    }

    public void advertise(){
        for (FeatureManager o: obsList
             ) {
            o.react(new Info("motion",  movement));
        }
    }


}
