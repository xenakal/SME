package com.fx_house;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HandleCommand implements EventHandler<ActionEvent> {

    ClientAgent command_placer;
    TextField command;

    public HandleCommand(TextField text){
        this.command = text;
        this.command_placer = new ClientAgent();
    }


    @Override
    public void handle(ActionEvent event) {
        this.command_placer.execute(this.command);
    }
}
