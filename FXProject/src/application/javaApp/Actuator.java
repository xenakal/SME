package application.javaApp;

import application.ActuatorAgent;

public interface Actuator{

    public Enum.Actuator getType();
    public boolean isActive();
    public void active();
    public void deactive();
    public String getName();
    //public void informFX(ActuatorAgent act); 
}
