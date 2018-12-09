package application;
	
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.smart_house.*;
import com.smart_house.Enum;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class MainFX extends Application {
	
	Stage window; 
	SmartHome sh; 
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			window = primaryStage; 
			sh = SmartHome.getSmartHome();
	        sh.smartHomeInit("/home/xenakis/Documents/Cours/Master/Q7/SME/repo/Smart_H/src/com/smart_house/config.json5");
	        System.out.println(sh.toString());
			
	        /* prepare the scene graph with the required nodes */   
			
			// root node 
	        FlowPane layout = new FlowPane(); // root node 
	        layout.setPadding(new Insets(10,10,10,10)); // add padding around the cells 
	        layout.setMinSize(300,300); 
	        layout.setVgap(30); // gaps between the cells 
	        layout.setHgap(30); 
			
			// children 
			createCells(sh,layout);
			layout.setStyle("-fx-background-color: #9093ff;");
			
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
	
	// creates the layout containing the different cells for each room 
	private void createCells(SmartHome sh, FlowPane layout) {
		Map<String,Rooms> roomsMap = sh.getRoomsMap(); 
		Iterator<Map.Entry<String,Rooms>> it = roomsMap.entrySet().iterator(); 
	    while (it.hasNext()) { // TODO: put this in a method in SmartHome (when merge is done and copy of code is removed from FX project) 
	        Map.Entry<String,Rooms> pair = (Map.Entry<String,Rooms>)it.next(); // use: pair.getKey() & pair.getValue() 
	        
	        // new cell for the room 
	        FlowPane roomCell = createSingleCell(pair.getValue(),pair.getKey()); // to be added as grid child 
	        
			layout.getChildren().addAll(roomCell); 
	        it.remove(); // avoids a ConcurrentModificationException
	    }
		
	}
	
	// for a given room, create the corresponding cell
	// TODO: set correct size to make it square 
	// TODO: put the different actuators and other to match requirements	
	// TODO: dans la version à merger, faut ajouter la méthode getActuators rajoutée dans Rooms  
	private FlowPane createSingleCell(Rooms room, String name) {
		// cell initialization 
		FlowPane cell = new FlowPane();
		cell.setPadding(new Insets(10,10,10,10)); // add padding around the cells 
		cell.setMinSize(10,10);
		cell.setVgap(5); // gaps between the cells 
		cell.setHgap(5); 
		cell.setStyle("-fx-background-color: #FF5733;");
		
		// get the different components of the Room and add them to the cell 
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
		
		
		return cell; 
	}
	 
	// adds to cell the figure corresponding to the actuator act
	public void addActuatorToCell(FlowPane cell, Actuator act) {
		// light,coffee,radiator,alarm
		switch (act.getType()) {
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
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
