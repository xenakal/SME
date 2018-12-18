package com.smart_house;

import com.fx_house.ActuatorAgent;
import com.fx_house.AlarmAgent;
import com.fx_house.LightAgent;

public class ActuAlarm implements Actuator{

    String name;
    private boolean isActive = false;
    private boolean fx = false;
    private AlarmAgent fxagent;


    public void makeFX(ActuatorAgent fxlight){
        this.fx = true;
        this.fxagent = (AlarmAgent) fxlight;
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
        if(fx)
            this.fxagent.setBackColor("bip");
    }
    public  void alarmSet(){
        this.fxagent.setBackColor("on");
    }
    public  void alarmNotSet(){
        this.fxagent.setBackColor("off");
    }


    public void callThePolice(){
        System.out.println("Call the police");
        if(fx)
            this.fxagent.setBackColor("bip");
    }

    public String toString(){
        return "alarm : " +name + "  ";
    }

    public String getName() { return this.name; }
    //TODO more complete
    //TODO Code, ...

}
