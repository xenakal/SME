package com.fx_house;

import com.smart_house.AbsSensor;
import com.smart_house.Actuator;

/*
 * Factory for making ActuatorAgents and SensorAgents to fill the different rooms of the house 
 * */
public class AgentFactory {

    private static  AgentFactory agentFactory = new AgentFactory();

	public static AgentFactory getInstance(){
        return agentFactory;
    } 
	
	public ActuatorAgent makeActuatorAgent(Actuator act){
		//System.out.println("in FACTORY");
        switch (act.getType()){
            case light : return new LightAgent(act);
            case coffee : return new CoffeeAgent(act);
            case radiator : return new RadiatorAgent(act);
            case climatisor : return new ClimatisorAgent(act);
            case alarm : return new AlarmAgent(act);
            default:
                System.out.println(act.getType());
                System.out.println("Type error in Actuator Factory");
                return null;
        }
    }

    public SensorAgent makeSensorAgent(AbsSensor sensor){

        switch (sensor.getType()) {
            case temperature:
                return new ThermoSensorAgent(sensor);
            case motion:
                return new MotionSensorAgent(sensor);
            case button:
                return new ButtonSensorAgent(sensor);
            case alarmBox:
                return new AlarmBoxSensorAgent(sensor);
            default:
                System.out.println("wrong type of sensor :/");
                return null;
        }

    }
	
	
}
