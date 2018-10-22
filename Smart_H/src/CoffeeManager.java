import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.List;



/**
 * Manage the coffe fabrication  according to presence of people
 */
public class CoffeeManager implements FeatureManager{

    private List<CoffeeMachine> machines ;
    private  List<Rooms> rooms;

    public CoffeeManager() {
        machines = new ArrayList<CoffeeMachine>();
        rooms = new ArrayList<Rooms>();
    }

    public void add(CoffeeMachine o){
        if (!machines.contains(o)){
            machines.add(o);
        }
    }

    public void add(Rooms r){
        if (!rooms.contains(r)){
            rooms.add(r);
        }
    }

    public void remove(Light l){
            machines.remove(l);
        }

    public void remove(Rooms r){
        rooms.remove(r);
    }


        public List<CoffeeMachine> getCoffeMachines() {
            List<CoffeeMachine> list = new ArrayList<CoffeeMachine>();
            for(Rooms r : rooms){
                for (CoffeeMachine cm : r.getCoffeeMachines()){
                    if (!list.contains(cm)){
                        list.add(cm);
                    }
                }
            }
            for (CoffeeMachine cm : machines){
                if (!list.contains(cm)){
                    list.add(cm);
                }
            }
            return list;
        }

        //TODO ajouter dependance par rapport à l'heure
        @Override
        public void react(Info info) {
            switch (info.name) {
                case "motion" :
                    if (info.value == 1) {  //true = 1
                        for (CoffeeMachine cm : getCoffeMachines()) {
                            cm.makeCoffee();
                        }

                    }
                    break;
                default: break;
            }
        }

}
