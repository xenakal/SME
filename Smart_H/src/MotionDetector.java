import java.util.*;

public class MotionDetector implements Runnable{

    private Light connected_light;
    private volatile boolean l_switch; // false -> light off, true -> light on
    private int movement; // if movement is detected (0==false)
    private long startTime;
    private boolean ischangedvalue;  //inidique si la donnee a changee


    public List<FeatureManager> obsList = new LinkedList<FeatureManager>();

    public MotionDetector(Light light){
        this.connected_light = light;
        l_switch = false;
        movement = 0;
        connected_light.on = false;
    }

    public void sensor_on(){
        System.out.println("sensor is on");
        l_switch = true;
        Thread thread = new Thread(this);
        thread.start(); // commence la boucle qui va continuellement checker s'il y a des signaux
    }

    public void sensor_off(){
        System.out.println("sensor is off");
        l_switch = false;
        connected_light.turn_off();
    }

    public void detect(int value){
        if (value != movement){
            ischangedvalue = true;
            movement = value;
        }


    }

    private void light_on(){
        connected_light.turn_on();
    }

    private void light_off(){
        connected_light.turn_off();
    }

    public void run(){
        while(l_switch){
            if(!connected_light.on){ // light off
                if (movement==1) {
                    startTime = System.currentTimeMillis();
                    this.advertise(); //TODO decomment this line
                    //light_on(); //TODO remove this line
                }
            }
            else{ // light on
                if(System.currentTimeMillis() - startTime >= 10000) { // bcp de temps sans mouvement
                    System.out.println("timeout");
                    movement=0; // Du coup en fait ca va continuer à être "on", jusqu'à ce que il n'y ai plus de mouvements pdt 10 sec
                    this.advertise();
                    //light_off(); //TODO remove this line
                }
            }
        }
    }

    public void run2(){
        while(l_switch){
            if(ischangedvalue){
                this.advertise();
            }
            //sleep

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
        ischangedvalue = false;
        for (FeatureManager o: obsList
             ) {
            o.react(new Info("motion",  movement));
        }
    }

    //private Info makeinfo


}
