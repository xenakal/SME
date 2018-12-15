package application;

import com.smart_house.AbsSensor;
import com.smart_house.ActuClimatisor;
import com.smart_house.ActuCoffeeMachine;
import com.smart_house.ActuLight;
import com.smart_house.ActuRadiator;
import com.smart_house.Actuator;
import com.smart_house.SensorMotion;
import com.smart_house.SensorThermo;

import javafx.scene.paint.Color;

/*
 * Factory for making ActuatorAgents and SensorAgents to fill the different rooms of the house 
 * */
public class AgentFactory {

    private static  AgentFactory agentFactory = new AgentFactory();

	public static AgentFactory getInstance(){
        return agentFactory;
    } 
	
	public ActuatorAgent makeActuatorAgent(Actuator act){
		System.out.println("in FACTORY");
        switch (act.getType()){
            case light : return new LightAgent(act);
            //case coffee : return new LightAgent(act);
            case radiator : return new RadiatorAgent(act);
            //case climatisor : return new LightAgent(act);
            //case alarm : 
            default: System.out.println("Type error in Actuator Factory");	return null;
        }
    }
	
	
}
