import java.util.ArrayList;
import java.util.List;



/**
 * Manage the coffe fabrication  according to presence of people
 */
public class ManagerCoffee implements ManagerFeature {

    private List<ActuCoffeeMachine> machines ;
    private  List<Rooms> rooms;

    public ManagerCoffee() {
        machines = new ArrayList<ActuCoffeeMachine>();
        rooms = new ArrayList<Rooms>();
    }

    public void add(ActuCoffeeMachine o){
        if (!machines.contains(o)){
            machines.add(o);
            update();
        }
    }

    public void add(Rooms r){
        if (!rooms.contains(r)){
            rooms.add(r);
            update();
        }
    }

    public void remove(ActuLight l){
            machines.remove(l);
            update();
        }

    public void remove(Rooms r){
        rooms.remove(r);
        update();
    }

    public void update(){
        machines = getCoffeMachines();
    }


    public List<ActuCoffeeMachine> getCoffeMachines() {
        List<ActuCoffeeMachine> list = new ArrayList<ActuCoffeeMachine>();
        for(Rooms r : rooms){
            for (Actuator cm : r.getActuatorofType(Enum.Actuator.coffee)){
                if (!list.contains((ActuCoffeeMachine) cm)){
                    list.add((ActuCoffeeMachine) cm);
                }
            }
        }
        for (ActuCoffeeMachine cm : machines){
            if (!list.contains(cm)){
                list.add(cm);
            }
        }
        return list;
    }

    //TODO ajouter dependance par rapport à l'heure
    @Override
    public void react(Info info) {
        switch (info.getName()) {
            case "motion" :
                if (info.getValue() == 1) {  //true = 1
                    for (ActuCoffeeMachine cm : machines) {
                        cm.makeCoffee();
                    }

                }
                break;
            default: break;
        }
    }

    public String ToString(){
        String str =  "CoffeManager : " ;
        for (ActuCoffeeMachine c: machines) {
            str = str + c.toString();
        }
        return str;
    }

}
