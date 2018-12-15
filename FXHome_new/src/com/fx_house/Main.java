package com.fx_house;

import com.smart_house.*;
import com.smart_house.Enum;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Main extends Application {

    Stage window;
    SmartHome sh;

    public static void main(String[] args) {
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
            scene.getStylesheets().add(getClass().getResource("./application.css").toExternalForm());
			
			/* prepare the stage and add the scene to the stage and display the contents of the stage */

            window.setScene(scene);
            window.setTitle("Lele's second try");
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
        Map<String,Rooms> roomsMap = sh.getRoomsMap(); // get the rooms
        Iterator<Map.Entry<String,Rooms>> it = roomsMap.entrySet().iterator(); // iterate over the rooms
        while (it.hasNext()) {
            Map.Entry<String,Rooms> pair = it.next();
            // new cell for the room
            FlowPane roomCell = createSingleCellForRoom(pair.getValue(),pair.getKey()); // every room is a child of the main grid  and is a grid itself

            layout.getChildren().add(roomCell);
            //it.remove(); // avoids a ConcurrentModificationException
        }

    }

    // for a given room, create the corresponding cell
    // TODO: set correct size to make it square
    // TODO: put the different actuators and other to match requirements
    /* Creates a cell for a given room */
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
        Map<Enum.Actuator, List<Actuator>> roomAM = room.getActuatorMap(); // get the actuators
        Iterator it = roomAM.entrySet().iterator(); // iterate over them
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();

            for(Actuator act : (List<Actuator>) pair.getValue()) { // add each actuator to the cell
                addActuatorToCell(cell, act);
            }
        }
    }

    /*
     * adds to cell the figure corresponding to the actuator act
     */
    public void addActuatorToCell(FlowPane cell, Actuator act) {
        // light,coffee,radiator,alarm
        ActuatorAgent actAgent = AgentFactory.getInstance().makeActuatorAgent(act);
        if(actAgent != null)
            cell.getChildren().add(actAgent);
    }

    /*
     * Put in the cell every sensor in the corresponding room
     */
    public void configureSensorsForRoom(Rooms room, FlowPane cell) {
        Map<Enum.Sensor, List<AbsSensor>> roomSM = room.getSensorMap(); // get the sensors
        Iterator it = roomSM.entrySet().iterator(); // iterate over the sensors
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            // we want to add these sensors to the cell
            for(AbsSensor sensor : (List<AbsSensor>) pair.getValue()) {
                addSensorToCell(cell, sensor); // add them to the cell
            }
        }
    }

    /*
     * adds to cell the figure corresponding to the actuator act
     */
    public void addSensorToCell(FlowPane cell, AbsSensor sensor) {
        SensorAgent sensAgent = AgentFactory.getInstance().makeSensorAgent(sensor);
        if(sensAgent != null)
            cell.getChildren().add(sensAgent);
    }

}
