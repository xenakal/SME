import java.util.LinkedList;
import java.util.List;

abstract class AbsSensor {


    private boolean l_switch = false; // false -> light off, true -> light on
    private List<Light> obsList = new LinkedList<Light>();    //TODO FEATUREMANAGER


    abstract void run();
    abstract void detect(int value);


    public void sensor_on(){
        System.out.println("sensor is on");
        l_switch = true;
    }
    public void sensor_off(){
        System.out.println("sensor is off");
        l_switch = false;
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
        for (Light o: obsList) {
            //o.react(info);
            o.turn_on();
        }
    }
}
