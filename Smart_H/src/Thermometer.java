import java.util.LinkedList;
import java.util.List;

public class Thermometer extends AbsSensor{

    private int recorded_temp = 25;
    public List<FeatureManager> obsList = new LinkedList<>();

    @Override
    Enum.Sensor getType() {
        return Enum.Sensor.temperature;
    }

    protected Thermometer(String name) {
        super(name);
     }

    public void sensor_on(){
        System.out.println("Thermometer is on");
    }
    public void sensor_off(){
        System.out.println("Thermometer is off");
    }

    void reset() {}

    void detect(int value) {
        System.out.println("# Température détectée: "+value);
        recorded_temp = value;
        this.advertise();
    }

    public void advertise(){
        for (FeatureManager o: obsList
                ) {
            o.react(this.makeinfo());
        }
    }

    public void detach(FeatureManager obs){
        obsList.remove(obs);
    }


    public void attach(FeatureManager obs){
        if (!obsList.contains(obs)){
            obsList.add(obs);
        }
    }


    public Info makeinfo(){
        return new Info("temperature",  recorded_temp);
    }
}
