import java.util.LinkedList;
import java.util.List;

abstract class AbsSensor implements Runnable{


    protected boolean l_switch ; // false -> light off, true -> light on
    protected List<FeatureManager> obsList ;
    protected boolean ischangedvalue ;

    protected AbsSensor(){
        l_switch = false;
        obsList = new LinkedList<FeatureManager>();
        ischangedvalue = false;

    }

    //abstract void run();
    abstract void reset();
    abstract void detect(int value);
    abstract Info makeinfo();

    public void run(){
        System.out.println("Abs loop");
        while(this.l_switch){
            if(this.ischangedvalue){

                this.advertise();
            }
            //sleep
        }
        this.reset();
        this.advertise();
    }

    public void sensor_on(){
        System.out.println("sensor is on");
        l_switch = true;
        Thread thread = new Thread(this);
        thread.start(); // commence la boucle qui va continuellement checker s'il y a des signaux
    }


    public void sensor_off(){
        System.out.println("sensor is off");
        this.l_switch = false;
    }

    public void attach(FeatureManager obs){
        if (!this.obsList.contains(obs)){
            this.obsList.add(obs);
        }
    }

    public void detach(FeatureManager obs){
        obsList.remove(obs);
    }
    public void advertise(){
        System.out.println("advertise");
        this.ischangedvalue = false;
        for (FeatureManager o: obsList
        ) {
            o.react(this.makeinfo());
        }
    }
}
