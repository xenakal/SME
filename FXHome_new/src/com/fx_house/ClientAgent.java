package com.fx_house;
import com.smart_house.*;
import javafx.application.Platform;
import javafx.scene.control.TextField;

import javax.xml.soap.Text;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class ClientAgent {

    /**
     * How to use: Type a sequence of words with the following pattern
     *   - first word "get" --> second word should be the Room --> third the actuator type --> fourth the attribute (tolerance, temperature, light_state)
     *   - first word "detect" --> second word should be the Room --> third the type of command (ex. motion) --> fourth the value
     *   - first word "param" --> second word should be the type of manager --> third the type of command (ex. SetTemperature) --> fourth the Room -> fifth the value
     *   - first word "config" --> second word should be the option "room", "sensor" or "actuator" --> third the Room where to add the actuator/sesor if applicable
     */

    private GetCommand gc;
    private DetectCommand dc;
    private ParamCommand pc;
    private ConfigCommand cc;
    private FeatureModelCommand fmc;
    private CommandsPlacer commands_handler;

    public ClientAgent(){

        // Concrete Commands
        this.gc = new GetCommand();
        this.dc = new DetectCommand();
        this.pc = new ParamCommand();
        this.cc = new ConfigCommand();
        this.fmc = new FeatureModelCommand();

        // Agent
        this.commands_handler = new CommandsPlacer();
    }

    /*
    public void execute(TextField command){
        String in = command.getText();
        System.out.println(in);
        String[] in_arr = in.split(" "); // first word should be type of command
        for (String str : in_arr){
            System.out.println(str);
        }
    }
    */

    public void execute(TextField command){

        SmartHome sh = SmartHome.getSmartHome();

        try{

                String in = command.getText();
                String[] in_arr = in.split(" "); // first word should be type of command
                String command_type = in_arr[0];
                switch (command_type) {
                    case "usage":
                        System.out.println("Usage PRINT: print_house");
                        gc.usage();
                        dc.usage();
                        pc.usage();
                        cc.usage();
                        fmc.usage();
                        break;
                    case "print_house":
                        System.out.println(sh.toString());
                        break;
                    case "get":
                        commands_handler.placeOrder(gc, in_arr);
                        break;
                    case "detect": // second word is Room, third is Type (ex. motion or temperature) fourth is value
                        commands_handler.placeOrder(dc, in_arr);
                        break;
                    case "param":
                        commands_handler.placeOrder(pc, in_arr);
                        break;
                    case "config":
                        commands_handler.placeOrder(cc, in_arr);
                        break;
                    case "feature_model":
                        commands_handler.placeOrder(fmc, in_arr);
                        break;
                    case "exit":
                        Platform.exit();
                        break;
                    default:
                        System.out.println("wrong type of command, please try again");
                        System.out.println();
                        System.out.println("The command read is:");
                        System.out.println(in);
                        break;
                }
        }
        catch (Exception e){
            System.out.println("Some unexpected Exception occured :/ ");
        }

    }

}
