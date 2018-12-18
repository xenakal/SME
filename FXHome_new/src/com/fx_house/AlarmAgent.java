package com.fx_house;

import com.smart_house.ActuAlarm;
import com.smart_house.Actuator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class AlarmAgent extends ActuatorAgent {

    private String name;
    Rectangle r;
    private ActuAlarm act;

    public AlarmAgent(Actuator act) {
        //System.out.println("LightAgent created");
        this.name = act.getName();
        this.act = (ActuAlarm) act;
        act.makeFX(this);

        r = new Rectangle();
        r.setWidth(100);
        r.setHeight(100);
        r.setArcWidth(20);
        r.setArcHeight(20);
        r.setFill(Color.GRAY.deriveColor(0, 1.2, 1, 0.6));
        r.setStroke(Color.GRAY);

        Text text = new Text(act.getType().name());

        this.getChildren().addAll(r, text);
    }

    public void setBackColor() {
        this.setBackColor("off");
    }
    public void setBackColor(String state) {
        switch (state){
            case "off":r.setFill(Color.DARKGRAY); break;
            case "on": r.setFill(Color.LIGHTGRAY); break;
            case "bip" : r.setFill(Color.RED); break;
        }
    }

    @Override
    public String getName() {
        return this.name;
    }



}
