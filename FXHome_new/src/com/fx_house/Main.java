package com.fx_house;

import com.smart_house.*;
import com.smart_house.Enum;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.control.TextField;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.Button;

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
            //System.out.println("Working Directory = " +
            //        System.getProperty("user.dir"));
            // /home/xenakis/Documents/Cours/Master/Q7/SME/repo/FXHome_new/src/com/smart_house
            sh.smartHomeInit("./src/com/smart_house/config.json5");
            System.out.println(sh.toString());
			
	        /* prepare the scene graph with the required nodes */
            VBox appComponents = new VBox();

            GridPane command_layout = createCommand(window.getMaxWidth());

            FlowPane house_layout = createHome(sh);

	        appComponents.getChildren().addAll(house_layout,command_layout);
			/* prepare the scene with the required dimensions and add the scene graph to it */

            Scene scene = new Scene(appComponents,1000,1000);
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
            VBox roomCell = createSingleCellForRoom(pair.getValue(),pair.getKey()); // every room is a child of the main grid  and is a grid itself

            layout.getChildren().add(roomCell);
            //it.remove(); // avoids a ConcurrentModificationException
        }
    }

    /* Creates a cell for a given room */
    private VBox createSingleCellForRoom(Rooms room, String name) {

        // cell initialization
        VBox roomTitled = new VBox();
        Text title = new Text(name);
        title.setFont(Font.font("Verdana", 25));

        roomTitled.setAlignment(Pos.CENTER_LEFT);

        // actual room
        FlowPane roomCell = new FlowPane();
        roomCell.setPadding(new Insets(10,10,10,10)); // add padding around the cells
        roomCell.setMinSize(10,10);
        roomCell.setVgap(5); // gaps between the cells
        roomCell.setHgap(5);
        roomCell.setStyle("-fx-background-color: DAE6F3;");

        // get the different components of the Room and add them to the cell
        configureActuatorsForRoom(room, roomCell);
        configureSensorsForRoom(room, roomCell);

        roomTitled.getChildren().addAll(title,roomCell);

        return roomTitled;
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

    private GridPane createCommand(double width){
        GridPane commandCell = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(90);
        commandCell.getColumnConstraints().add(column1);

        TextField command_text = new TextField();
        command_text.setPromptText("Insert your command here :)");  //to set the hint text
        command_text.setFont(Font.font("Verdana", FontWeight.BOLD, 30));

        Button validate_button = new Button("OK !");
        Font font = new Font(28.5); //Button font's size should increase to 40
        validate_button.setFont(font);
        validate_button.setOnAction(new HandleCommand(command_text));

        commandCell.add(command_text,0,0);
        commandCell.add(validate_button,1,0);
        return commandCell;
    }

}
