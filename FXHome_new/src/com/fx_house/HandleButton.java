package com.fx_house;

import com.smart_house.SensorButton;
import com.smart_house.SensorMotion;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class HandleButton implements EventHandler<ActionEvent>{

    private SensorButton sensor;
    private int ispressed = 0;

    public HandleButton(SensorButton sensor) {
        this.sensor = sensor;
    }


    public void handle(ActionEvent event) {
        ispressed = 1 - 0*ispressed;
        sensor.detect(ispressed);
    }
}
