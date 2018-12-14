package com.smart_house;

import java.util.ArrayList;
import java.util.List;

public class ManagerSecurity  implements ManagerFeature {
    private List<ActuAlarm> alarms;
    private  List<Rooms> rooms;
    private boolean isActive = false;

    public ManagerSecurity() {
        alarms = new ArrayList<ActuAlarm>();
        rooms = new ArrayList<Rooms>();
    }

    public boolean isActive() {
        return isActive;
    }
    public void active(){
        isActive = true;
        System.out.println("security manager activate");
    }
    public void deactive(){
        isActive = false;
        System.out.println("security manager deactivate");
    }


    public void add(ActuAlarm o){
        if (!alarms.contains(o)){
            alarms.add(o);
            update();
        }
    }

    public void add(Rooms r){
        if (!rooms.contains(r)){
            rooms.add(r);
            update();
        }
    }

    public void remove(ActuAlarm l){
        alarms.remove(l);
        update();
    }

    public void remove(Rooms r){
        rooms.remove(r);
        update();
    }

    public void update(){
        upDateAlarm(alarms);
    }


    public List<ActuAlarm> getAlarms() {
        List<ActuAlarm> list = new ArrayList<ActuAlarm>();
        upDateAlarm(list);
        return list;
    }

    public void upDateAlarm(List<ActuAlarm> list ) {
        for(Rooms r : rooms){
            for (Actuator a : r.getActuatorOfType(Enum.Actuator.alarm)){
                if (!list.contains((ActuAlarm) a)){
                    list.add((ActuAlarm) a);
                }
            }
        }
        for (ActuAlarm a : alarms){
            if (!list.contains(a)){
                list.add(a);
            }
        }
    }

    @Override
    public void react(Info info) {
        if(this.isActive()) {
            switch (info.getName()) {
                case "motion":
                    if (info.getValue() == 1) {  //true = 1
                        for (ActuAlarm a : alarms) {
                            a.Bip();
                            a.callThePolice();
                        }

                    }
                    break;
                default:
                    break;
            }
        }
    }

    public String ToString(){
        String str = (isActive()?"Active ": "Not active ") + "SecurityManager : " ;
        for (ActuAlarm c: alarms) {
            str = str + c.toString();
        }
        return str;
    }
}
