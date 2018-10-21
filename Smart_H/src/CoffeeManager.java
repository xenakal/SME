import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.List;



/**
 * Manage the coffe fabrication  according to presence of people
 */
public class CoffeeManager implements FeatureManager{

        private List<CoffeeMachine> machines ;

        public CoffeeManager() {
            machines = new ArrayList<CoffeeMachine>();
        }

        public void add(CoffeeMachine o){
            if (!machines.contains(o)){
                machines.add(o);
            }
        }


        public void remove(Light l){
            machines.remove(l);
        }


        public List<CoffeeMachine> getCoffeMachines() {
            return machines;
        }

        //TODO ajouter dependance par rapport à l'heure
        @Override
        public void react(Info info) {
            switch (info.name) {
                case "motion" :
                    if (info.value == 1) {  //true = 1
                        for (CoffeeMachine cm : machines) {
                            cm.makeCoffee();
                        }

                    }
                    break;
                default: break;
            }
        }

}
