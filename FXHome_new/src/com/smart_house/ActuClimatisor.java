package com.smart_house;

import com.fx_house.ActuatorAgent;
import com.fx_house.ClimatisorAgent;
import com.fx_house.LightAgent;

public class ActuClimatisor implements Actuator{

    String name;
    private boolean on = false;
    private boolean isActive = false;
    private boolean fx = false;
    private ClimatisorAgent fxagent;


    public void makeFX(ActuatorAgent fxlight){
        this.fx = true;
        this.fxagent = (ClimatisorAgent) fxlight;
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
        if(fx)
            this.fxagent.setBackColor();
    }

    private void climatisorOff(){
        System.out.println("# " + name + ": ActuClimatisor off !");
        if(fx)
            this.fxagent.setBackColor();
    }


    public boolean getState(){
        return on;
    }

    public String toString(){
        return "# "+ "climatisor : " +name  + (isActive()?" is active ": " is not active ");
    }
	@Override
	public String getName() {
		return this.name;
	}


}
