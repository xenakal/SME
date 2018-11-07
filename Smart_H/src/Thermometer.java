import java.util.LinkedList;
import java.util.List;

public class Thermometer extends AbsSensor{

    private int temperature;
    public List<FeatureManager> obsList = new LinkedList<>();

    void reset() {}

    void detect(int value) {
        if(value!=temperature){
            temperature = value;
            this.advertise();
        }
    }

    public void advertise(){
        for (FeatureManager o: obsList
                ) {
            o.react(this.makeinfo());
        }
    }

    public Info makeinfo(){
        return new Info("temperature",  temperature);
    }
}
