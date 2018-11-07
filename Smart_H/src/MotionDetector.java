import java.util.*;

public class MotionDetector extends AbsSensor{

    private int movement; // if movement is detected (0==false)

    public List<FeatureManager> obsList = new LinkedList<FeatureManager>();

    public MotionDetector(String name){
        super(name);
        //this.name = name;
        movement = 0;
    }

    public void reset() {
        //TODO
    }

    public void sensor_on(){
        System.out.println("sensor is on");
    }

    public void sensor_off(){
        movement = 0;
        System.out.println("sensor is off");
        this.advertise();
    }

    public void detect(int value){
        if(value!=movement) {
            movement = value;
            this.advertise();
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
            o.react(this.makeinfo());
        }
    }

    public Info makeinfo(){
        return new Info("motion",  movement);
    }

    @Override
    public String toString(){
        return "      Motion detector : " + super.toString();
    }

}

