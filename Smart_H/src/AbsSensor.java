import java.util.LinkedList;
import java.util.List;

abstract class AbsSensor{

    protected String name;
    protected boolean l_switch ; // false -> light off, true -> light on
    protected List<FeatureManager> obsList ;
    protected boolean ischangedvalue ;


    protected AbsSensor(String name){
        this.name = name;
        l_switch = false;
        obsList = new LinkedList<FeatureManager>();
        ischangedvalue = false;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    abstract void reset();
    abstract void detect(int value);
    abstract Info makeinfo();
    abstract String ToString();



    public void sensor_on(){
        System.out.println("sensor is on");
        l_switch = true;
    }


    public void sensor_off(){
        this.l_switch = false;
        System.out.println("sensor is off");
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
