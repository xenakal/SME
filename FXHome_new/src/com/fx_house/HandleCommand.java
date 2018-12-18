package com.fx_house;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HandleCommand implements EventHandler<ActionEvent> {

    ClientAgent command_placer;
    TextField command;
    SmartHomeFX shfx;

    public HandleCommand(TextField text, SmartHomeFX shfx){
        this.command = text;
        this.command_placer = new ClientAgent();
        this.shfx = shfx;
    }


    @Override
    public void handle(ActionEvent event) {
        this.command_placer.execute(this.command);
        shfx.draw_house();
    }
}
