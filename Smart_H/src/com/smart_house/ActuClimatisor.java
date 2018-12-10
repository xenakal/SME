package com.smart_house;

public class ActuClimatisor implements Actuator{

    String name;
    private boolean on = false;
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
        if(on){climatisorOn();}
    }
    public void deactive(){
        isActive = false;
        System.out.println("# "+name + " deactivate");
        if(on){climatisorOff();}
    }

    public ActuClimatisor(String name){this.name = name;}

    @Override
    public Enum.Actuator getType() {
        return Enum.Actuator.climatisor;
    }


    public void turn_on(){
        if(!on){
            on = true;
            climatisorOn();
        }
    }

    public void turn_off(){
        if(on){
            on = false;
            climatisorOff();
        }
    }
    private void climatisorOn(){
        System.out.println("# " + name + ": ActuClimatisor on !");
        System.out.println("# climatisor "+name+" decreases heat");
    }

    private void climatisorOff(){
        System.out.println("# " + name + ": ActuClimatisor off !");
    }


    public boolean getState(){
        return on;
    }

    public String toString(){
        return "# "+ "climatisor : " +name  + (isActive()?" is active ": " is not active ");
    }


}
