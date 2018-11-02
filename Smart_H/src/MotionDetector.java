import java.util.*;

public class MotionDetector implements Runnable{

    //private Light connected_light;
    private String name;
    private volatile boolean l_switch; // false -> light off, true -> light on
    private int movement; // if movement is detected (0==false)
    private long startTime;
    private boolean ischangedvalue;  //inidique si la donnee a changee


    public List<FeatureManager> obsList = new LinkedList<FeatureManager>();

    public MotionDetector(String name){
        this.name = name;
        //this.connected_light = light;
        l_switch = false;
        movement = 0;
        //connected_light.on = false;
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
        //connected_light.turn_off();
    }

    public void detect(int value){
        if (value != movement){
            startTime = System.currentTimeMillis();
            ischangedvalue = !ischangedvalue;  //pour mettre ischangedValue a false si double changement
            movement = value;
            advertise();
        }


    }

    public void run(){
        while(l_switch){
            if(ischangedvalue){
                if(movement == 1){
                    this.advertise();
                }else if(System.currentTimeMillis() - startTime >= 10000){ //mvt == 0 & delais ecoule
                    System.out.println("timeout");
                    advertise();
                }
            }
            //sleep
        }
        //System.out.println("après while");
        //System.out.println("movement =" + movement);
        detect(0);//pas de capteur pas de mouvement
        advertise();
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
            o.react(this.makeinfo());
        }
    }

    public Info makeinfo(){
        return new Info("motion",  movement);
    }

}

