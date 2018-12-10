package com.smart_house;

public class ActuAlarm implements Actuator{

    String name;
    private boolean isActive = false;

    @Override
    public String getName() {
        return name;
    }

    public boolean isActive() {
        return isActive;
    }
    public void active(){
        isActive = true;
        System.out.println("# "+name + " activate");
    }
    public void deactive(){
        isActive = false;
        System.out.println("# "+name + " deactivate");
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
        System.out.println("Call the police");
    }

    public String toString(){
        return "alarm : " +name + "  ";
    }

    //TODO more complete
    //TODO Code, ...

}
