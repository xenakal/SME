package com.fx_house;

import com.smart_house.SensorThermo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class HandleThermo implements EventHandler<ActionEvent>{

    private SensorThermo sensor;
    private TextField textField;

    public HandleThermo(SensorThermo sensor, TextField textField) {
        this.sensor = sensor;
        this.textField = textField;
    }

    public void handle(ActionEvent event) {
        try {
            int value = Integer.parseInt(this.textField.getText());
            sensor.detect_temperature(value);
        }
        catch (Exception e){
            System.out.println("Temperature detected must be an integer !");
        }
    }



}
