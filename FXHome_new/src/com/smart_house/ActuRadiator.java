package com.smart_house;

import com.fx_house.ActuatorAgent;
import com.fx_house.LightAgent;
import com.fx_house.RadiatorAgent;

public class ActuRadiator implements Actuator {

    String name;
    private boolean on = false;
    private boolean isActive = false;
    private boolean fx = false;
    private RadiatorAgent fxrad;


    public void makeFX(ActuatorAgent fxlight){
        this.fx = true;
        this.fxrad = (RadiatorAgent) fxlight;
    }


    public boolean isActive() {
        return isActive;
    }
    public void active(){
        isActive = true;
        System.out.println("# "+name + " activate");
        if(on){radiatorOn();}
    }
    public void deactive(){
        isActive = false;
        System.out.println("# "+name + " deactivate");
        if(on){radiatorOff();}
    }

    public ActuRadiator(String name){this.name = name;}

    @Override
    public Enum.Actuator getType() {
        return Enum.Actuator.radiator;
    }


    public void turn_on(){
        if(!on){
            on = true;
            radiatorOn();
        }
    }

    public void turn_off(){
        if(on){
            on = false;
            radiatorOff();
        }
    }
    private void radiatorOn(){
        System.out.println("# " + name + ": ActuRadiator on !");
        System.out.println("# radiator "+name+" increases heat");
        if(fx)
            this.fxrad.setBackColor();
    }

    private void radiatorOff(){
        System.out.println("# " + name + ": ActuRadiator off !");
        if(fx)
            this.fxrad.setBackColor();
    }


    public boolean getState(){
        return on;
    }

    public String toString(){
        return "# "+ "radiator : " +name  + (isActive()?" is active": " is not active ");
    }

    public String getName() { return this.name; }

}
