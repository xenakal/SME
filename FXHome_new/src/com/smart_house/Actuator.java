package com.smart_house;

import com.fx_house.ActuatorAgent;

public interface Actuator{
    public void makeFX(ActuatorAgent fxAgent);
    public Enum.Actuator getType();
    public boolean isActive();
    public void active();
    public void deactive();
    public String getName();

}