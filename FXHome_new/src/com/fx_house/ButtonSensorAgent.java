package com.fx_house;

import com.smart_house.AbsSensor;
import com.smart_house.SensorButton;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ButtonSensorAgent extends SensorAgent{

    private String name;
    private Rectangle r;
    private SensorButton sensor;

    public ButtonSensorAgent(AbsSensor sensor) {


        this.name = sensor.getName();
        this.sensor = (SensorButton) sensor;
        this.sensor.active();

        r = new Rectangle();
        r.setWidth(120);
        r.setHeight(50);
        r.setArcWidth(20);
        r.setArcHeight(20);
        r.setFill(Color.PURPLE);
        r.setStroke(Color.GRAY);

        Button button = new Button();
        button.setText("Button Sensor");
        button.setOnAction(new HandleButton(this.sensor));


        this.getChildren().addAll(r, button);
        //this.getChildren().addAll(button);
    }

    public void setState(String color) {
        switch (color){
            case "brown":
                r.setFill(Color.BROWN);
                break;
            case "black":
                r.setFill(Color.GRAY);
                break;
            default:
                System.out.println("Color can be only brown or black :/ ");
        }
    }


}
