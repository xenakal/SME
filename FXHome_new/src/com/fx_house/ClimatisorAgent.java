package com.fx_house;

import com.smart_house.ActuClimatisor;
import com.smart_house.Actuator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ClimatisorAgent extends ActuatorAgent{

    private String name;
    Rectangle r;
    private ActuClimatisor act;

    public ClimatisorAgent(Actuator act) {
        this.name = act.getName();
        this.act = (ActuClimatisor) act;
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
        if(act.getState())
            r.setFill(Color.CADETBLUE);
        else
            r.setFill(Color.GRAY);
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

}
