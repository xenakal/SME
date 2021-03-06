package com.fx_house;

import com.smart_house.AbsSensor;
import com.smart_house.SensorThermo;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ThermoSensorAgent extends SensorAgent{

    private String name;
    private Rectangle r;
    private Button button;
    private TextField textIns;
    private VBox vbox;
    SensorThermo sensor;
    private Integer sens_required_temp;


    public ThermoSensorAgent(AbsSensor sensor) {

        this.setHgap(3);
        this.setVgap(3);
        this.setPadding(new Insets(0, 10, 0, 10));
        this.name = sensor.getName();
        this.sensor = (SensorThermo) sensor;
        this.sensor.active();
        this.sens_required_temp = this.sensor.getManagerRequiredTemp();

        this.textIns = new TextField();
        textIns.setPromptText("Insert temperature !"); //to set the hint text

        this.vbox = new VBox();
        vbox.setSpacing(10.0);

        this.button = new Button();
        button.setText("Thermometer: goal = " + this.sens_required_temp + " °C");
        button.setOnAction(new HandleThermo(this.sensor, this.textIns)); // add the text inside the text field


        vbox.getChildren().addAll(textIns,button);

        this.r = new Rectangle();
        r.setWidth(210);
        r.setHeight(60);
        r.setArcWidth(20);
        r.setArcHeight(20);
        r.setFill(Color.GREEN);
        r.setStroke(Color.GRAY);


        //this.add(r,0,0,1,2);
        this.add(r,0,0);
        this.add(vbox,0,0);
    }


}
