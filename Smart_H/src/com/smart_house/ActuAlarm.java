package com.smart_house;

public class ActuAlarm implements Actuator{

    String name;
    private boolean isActive = false;

    public boolean isActive() {
        return isActive;
    }
    public void active(){
        isActive = true;
        System.out.println("# "+name + " activate");
        //if(on){lightOn();}
    }
    public void deactive(){
        isActive = false;
        System.out.println("# "+name + " deactivate");
        //if(on){lightOff();}
    }


    public ActuAlarm(String name) {
        this.name = name;
    }

    @Override
    public Enum.Actuator getType() {
        return Enum.Actuator.alarm;
    }


    public void Bip(){
            System.out.println("Beeeeeep");
    }


    public void callThePolice(){

    }

    public String toString(){
        return "alarm : " +name + "  ";
    }

    //TODO more complete
    //TODO Code, ...

}
