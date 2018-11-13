import java.util.LinkedList;
import java.util.List;

abstract class AbsSensor extends Object{

    protected String name;
    protected List<FeatureManager> obsList ;


    protected AbsSensor(String name){
        this.name = name;
        obsList = new LinkedList<FeatureManager>();

    }

    public String getName() {
        return name;
    }
    abstract Enum.Sensor getType();

    public void setName(String name) {
        this.name = name;
    }

    abstract void reset();
    abstract void detect(int value);
    abstract Info makeinfo();


    public String toString(){
        String str = this.name + " is connected to \n";
        for (FeatureManager m: obsList) {
            str = str + m.ToString() + "\n";
        }
        return str;
    }



    public void sensor_on(){
        System.out.println("sensor is on");
    }


    public void sensor_off(){
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
        for (FeatureManager o: obsList
        ) {
            o.react(this.makeinfo());
        }
    }
}
