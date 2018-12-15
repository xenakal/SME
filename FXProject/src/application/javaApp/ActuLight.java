package application.javaApp;

import application.ActuatorAgent;
import application.LightAgent;

public class ActuLight extends Object implements Actuator{

    String name;
    private boolean on = false;
    private boolean isActive = false;
    private LightAgent fxAgent; 
    private boolean fx = false; 
    
    // FOR FX PROJECT 
    public void informFX(ActuatorAgent fxAgent) {
    	this.fxAgent = (LightAgent) fxAgent; 
    	this.fx = true;  
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
        if(fx) {
        	fxAgent.setBackColor(); 
        }
    }
    private void lightOff(){
        System.out.println("# "+name + "ActuLight off !");
        if(fx) {
        	fxAgent.setBackColor(); 
        }
    }

    public String toString(){
        return "# "+"light : " +name + (isActive()?" is active ": " is not active ");
    }
    
    public String getName() { return this.name; }

	
}
