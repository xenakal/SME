package com.fx_house;

import com.smart_house.SensorAlarmBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;

public class HandleAlarmBox implements EventHandler<ActionEvent> {

    private SensorAlarmBox sensor;
    private TextField textField;

    public HandleAlarmBox(SensorAlarmBox sensor, TextField textField) {
        this.sensor = sensor;
        this.textField = textField;
    }

    public void handle(ActionEvent event) {
        try {
            int value = Integer.parseInt(this.textField.getText());
            sensor.detect(value);
        }
        catch (Exception e){
            System.out.println("Code must be an integer of 4 digits !");
        }
    }
}
