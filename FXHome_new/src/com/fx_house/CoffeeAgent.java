package com.fx_house;

import com.fx_house.ActuatorAgent;
import com.smart_house.ActuCoffeeMachine;
import com.smart_house.Actuator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class CoffeeAgent extends ActuatorAgent {

    private String name;
    Rectangle r;
    private ActuCoffeeMachine act;

    public CoffeeAgent(Actuator act) {
        //System.out.println("LightAgent created");
        this.name = act.getName();
        this.act = (ActuCoffeeMachine) act;
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
        this.setBackColor(false);
    }
    public void setBackColor(Boolean on) {
        if(on)
            r.setFill(Color.BURLYWOOD);
        else
            r.setFill(Color.GRAY);

    }

    @Override
    public String getName() {
        return this.name;
    }
}

