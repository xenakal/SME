package com.smart_house;

import com.fx_house.ActuatorAgent;
import com.fx_house.LightAgent;

public class ActuLight extends Object implements Actuator {

    String name;
    private boolean on = false;
    private boolean isActive = false;
    private boolean fx = false;
    private LightAgent fxlight;


    public void makeFX(ActuatorAgent fxlight){
        this.fx = true;
        this.fxlight = (LightAgent) fxlight;
    }

    public boolean isActive() {
        return isActive;
    }

    public void active(){
        isActive = true;
        System.out.println("# "+name + " activate");
        if(on){lightOn();}
    }

    public void deactive(){
        isActive = false;
        System.out.println("# "+name + " deactivate");
        if(on){lightOff();}
    }

    public ActuLight(String name) {
        this.name = name;
    }

    @Override
    public Enum.Actuator getType() {
        return Enum.Actuator.light;
    }

    public boolean getState(){
        return on;
    }

    public void turn_on(){
        if(!on){
            on = true;
           lightOn();
        }
    }

    public void turn_off(){
        if(on){
            on = false;
            lightOff();
        }
    }

    private void lightOn(){

        System.out.println("# "+name + "ActuLight on !");
        this.fxlight.setBackColor();
    }

    private void lightOff(){
        System.out.println("# "+name + "ActuLight off !");
        this.fxlight.setBackColor();
    }

    public String toString(){
        return "# "+"light : " +name + (isActive()?" is active ": " is not active ");
    }
    
    public String getName() { return this.name; }
	
}
