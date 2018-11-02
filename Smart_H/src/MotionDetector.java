import java.util.*;

public class MotionDetector{

    //private Light connected_light;
    private int movement; // if movement is detected (0==false)
    private long startTime;


    public List<FeatureManager> obsList = new LinkedList<FeatureManager>();

    public MotionDetector(){
        movement = 0;
    }

    public void sensor_on(){
        System.out.println("sensor is on");
    }

    public void sensor_off(){
        System.out.println("sensor is off");
    }

    public void detect(int value){
        // TODO: DÈS QU'ON DETECTE 0 ON FERME DIRECT LA LUMIÈRE (ON A DÉCIDÉ COMME CA) ET DU COUP ON ENLEVE COMPLETEMENT LE TEMPS
        // TODO: AJOUTER DES SENSOR ETC POUR VOIR SI C'EST ADAPTABLE (AJOUTER UN THERMOMETRE)
        // TODO: AJOUTER AUSSI DES FEATUREMANAGER DU COUP
        if (value != movement){
            movement = value;
            if(movement == 1){ // movement is sensed
                startTime = System.currentTimeMillis();
                this.advertise();
            }else if(System.currentTimeMillis() - startTime >= 10000){ //mvt == 0 & delais ecoule
                System.out.println("timeout");
                this.advertise();
            }
        }
        else if(value == 0) {
            if(System.currentTimeMillis() - startTime >= 10000){
                System.out.println("timeout");
                this.advertise();
            }

        }
        else if(value == 1) // movement detected but light already on
            startTime = System.currentTimeMillis(); // reset timeout
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

}

