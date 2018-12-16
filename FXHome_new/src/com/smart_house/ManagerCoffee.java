package com.smart_house;

import java.util.ArrayList;
import java.util.List;


/**
 * Manage the coffe fabrication  according to presence of people
 */
public class ManagerCoffee implements ManagerFeature {

    private List<ActuCoffeeMachine> machines ;
    private  List<Rooms> rooms;
    private boolean isActive = false;

    public boolean isActive() {
        return isActive;
    }
    public void active(){
        isActive = true;
        System.out.println("coffee manager activate");
    }
    public void deactive(){
        isActive = false;
        System.out.println("coffee manager deactivate");
    }

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
        upDateCoffeMachines(machines);
    }


    public List<ActuCoffeeMachine> getCoffeMachines() {
        List<ActuCoffeeMachine> list = new ArrayList<ActuCoffeeMachine>();
        upDateCoffeMachines(list);
        return list;
    }

    public void upDateCoffeMachines(List<ActuCoffeeMachine> list ) {
        for(Rooms r : rooms){
            for (Actuator cm : r.getActuatorOfType(Enum.Actuator.coffee)){
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
    }

    //TODO ajouter dependance par rapport à l'heure
    @Override
    public void react(Info info) {
        if(this.isActive()) {
            switch (info.getName()) {
                case "motion":
                    if (info.getValue() == 1) {  //true = 1
                        for (ActuCoffeeMachine cm : machines) {
                            cm.makeCoffee();
                        }

                    }
                    break;
                default:
                    break;
            }
        }
    }

    public String ToString(){
        String str = (isActive()?"Active ": "Not active ") + "CoffeManager : " ;
        for (ActuCoffeeMachine c: machines) {
            str = str + c.toString();
        }
        return str;
    }

}
