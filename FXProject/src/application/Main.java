package application;
	
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.smart_house.*;
import com.smart_house.Enum;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Main extends Application {
	
	Stage window; 
	SmartHome sh; 

	public static void main(String[] args) {
		System.out.println("okok");
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			window = primaryStage; 
			sh = SmartHome.getSmartHome();
	        sh.smartHomeInit("/home/xenakis/Documents/Cours/Master/Q7/SME/repo/Smart_H/src/com/smart_house/config.json5");
	        System.out.println(sh.toString());
			
	        /* prepare the scene graph with the required nodes */   
			
	        FlowPane layout = createHome(sh);
	        
			
			/* prepare the scene with the required dimensions and add the scene graph to it */
			
			Scene scene = new Scene(layout,1000,1000);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			/* prepare the stage and add the scene to the stage and display the contents of the stage */ 
			
			window.setScene(scene);
			window.setTitle("Lele's first try");
			window.show();
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*  
	 * creates the basic Pane that will contain all the Rooms
	 */
	private FlowPane createHome(SmartHome sh) {
		// root node 
        FlowPane layout = new FlowPane(); // root node 
        layout.setPadding(new Insets(10,10,10,10)); // add padding around the cells 
        layout.setMinSize(300,300); 
        layout.setVgap(30); // gaps between the cells 
        layout.setHgap(30); 
		
		// children 
		createCells(sh,layout);
		layout.setStyle("-fx-background-color: #9093ff;");
		
		return layout; 
	}
	
	/*  
	 * creates the layout containing the different cells for each room
	 */  
	private void createCells(SmartHome sh, FlowPane layout) {
		Map<String,Rooms> roomsMap = sh.getRoomsMap(); 
		Iterator<Map.Entry<String,Rooms>> it = roomsMap.entrySet().iterator(); 
	    while (it.hasNext()) { 
	        Map.Entry<String,Rooms> pair = (Map.Entry<String,Rooms>)it.next(); // use: pair.getKey() & pair.getValue() 
	        
	        // new cell for the room 
	        FlowPane roomCell = createSingleCellForRoom(pair.getValue(),pair.getKey()); // every room is a child of the main grid  and is a grid itself 
	        
			layout.getChildren().addAll(roomCell); 
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
	}
	
	// for a given room, create the corresponding cell
	// TODO: set correct size to make it square 
	// TODO: put the different actuators and other to match requirements	
	private FlowPane createSingleCellForRoom(Rooms room, String name) {
		// cell initialization 
		
		//RoomAgent roomCell = new RoomAgent(); // TODO: make this work  
		FlowPane roomCell = new FlowPane();
		roomCell.setPadding(new Insets(10,10,10,10)); // add padding around the cells 
		roomCell.setMinSize(10,10);
		roomCell.setVgap(5); // gaps between the cells 
		roomCell.setHgap(5); 
		roomCell.setStyle("-fx-background-color: DAE6F3;");
		
		
		
		// get the different components of the Room and add them to the cell 
		configureActuatorsForRoom(room, roomCell); 
		configureSensorsForRoom(room, roomCell); 
		
		
		return roomCell; 
	}
	
	/*
	 * Put every actuator of the room in the corresponding cell 
	 */
	public void configureActuatorsForRoom(Rooms room, FlowPane cell) {
		Map<Enum.Actuator, List<Actuator>> roomAM = room.getActuatorMap();    
		Iterator it = roomAM.entrySet().iterator(); 
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        // we want to add these actuators to the cell
	        for(Actuator act : (List<Actuator>) pair.getValue()) {
	        	addActuatorToCell(cell, act); 
	        }
	        
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	/*
	 * Put in the cell every sensor in the corresponding room 
	 */
	public void configureSensorsForRoom(Rooms room, FlowPane cell) {
		Map<Enum.Sensor, List<AbsSensor>> roomSM = room.getSensorMap(); 
		Iterator it = roomSM.entrySet().iterator(); 
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        // we want to add these sensors to the cell
	        for(AbsSensor sensor : (List<AbsSensor>) pair.getValue()) {
	        	addSensorToCell(cell, sensor); 
	        }
	        it.remove(); // avoids a ConcurrentModificationException
	    }
	} 
	
	/* 
	 * adds to cell the figure corresponding to the actuator act 
	 */
	public void addActuatorToCell(FlowPane cell, Actuator act) {
		// light,coffee,radiator,alarm
		ActuatorAgent actAgent = AgentFactory.getInstance().makeActuatorAgent(act); 
		System.out.println("okokok");
		if(actAgent != null)
			cell.getChildren().add(actAgent); 
		/*switch (act.getType()) {
			case light:
				ActuatorAgent light = new ActuatorAgent(act);
				cell.getChildren().add(light); 
				break; 
			case radiator: 
				ActuatorAgent rad = new ActuatorAgent(act);
				cell.getChildren().add(rad); 
				break; 
			case coffee:
				ActuatorAgent cof = new ActuatorAgent(act);
				cell.getChildren().add(cof); 
				break; 
			case alarm: 
				ActuatorAgent alarm = new ActuatorAgent(act);
				cell.getChildren().add(alarm); 
				break; 
			default:
				System.out.println("wrong type of actuator :/");
				break;
		*/ 
	}
	
	/* 
	 * adds to cell the figure corresponding to the actuator act 
	 */
	public void addSensorToCell(FlowPane cell, AbsSensor sensor) {
		switch (sensor.getType()) {
		case temperature:
			SensorAgent tempSensor = new SensorAgent(sensor);
			cell.getChildren().add(tempSensor); 
			break; 
		case motion: 
			SensorAgent motionSensor = new SensorAgent(sensor);
			cell.getChildren().add(motionSensor); 
			break; 
		default:
			System.out.println("wrong type of sensor :/");
			break; 
		}
	}
}