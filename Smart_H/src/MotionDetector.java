import java.util.*;

public class MotionDetector implements Runnable{

    //private Light connected_light;
    private volatile boolean l_switch; // false -> light off, true -> light on
    private int movement; // if movement is detected (0==false)
    private long startTime;
    private boolean ischangedvalue;  //inidique si la donnee a changee


    public List<FeatureManager> obsList = new LinkedList<FeatureManager>();

    public MotionDetector(){
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
        }


    }

    public void run(){
        while(l_switch){
            if(ischangedvalue){
                if(movement == 1){
                    this.advertise();
                }else if(System.currentTimeMillis() - startTime >= 10000){ //mvt == 0 + delais ecoule
                    advertise();
                }
            }
            //sleep
        }
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


/*
import java.util.*;


public class MotionDetector extends AbsSensor{

    //private Light connected_light;
    //private volatile boolean l_switch; // false -> light off, true -> light on
    private int movement; // if movement is detected (0==false)
    private long startTime;
    //private boolean ischangedvalue;  //inidique si la donnee a changee


    public List<FeatureManager> obsList = new LinkedList<FeatureManager>();

    public MotionDetector(){
        super();
        //l_switch = false;
        //super.l_switch = false;
        //super.obsList = new LinkedList<FeatureManager>();
        //super.ischangedvalue = false;
        movement = 0;
                }


public void reset(){
        if( movement == 1){
        super.ischangedvalue = ! super.ischangedvalue;
        movement = 0;
        }

        }

public void detect(int value){
        //System.out.println("Before    detect " + value + " old mouvement " + movement + " chd " + super.ischangedvalue);
        if (value != movement){
        startTime = System.currentTimeMillis();
        super.ischangedvalue = !super.ischangedvalue;  //pour mettre ischangedValue a false si double changement
        movement = value;
        }
    //System.out.println("After    detect " + value + " mouvement " + movement + " chd " + super.ischangedvalue);


        }

@Override
public void run(){
        while(super.l_switch){
        //System.out.println("run");
        if(super.ischangedvalue){
            System.out.println("changed");
        if(this.movement == 1){
        super.advertise();
        }else if(System.currentTimeMillis() - this.startTime >= 10000){ //mvt == 0 + delais ecoule
        advertise();
        }
        }
        //sleep
        }
        reset();//pas de capteur pas de mouvement
        advertise();
        }



public Info makeinfo(){
        return new Info("motion",  movement);
        }


        }
*/