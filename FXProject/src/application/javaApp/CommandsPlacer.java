package application.javaApp;

import java.util.ArrayList;

/**
 * Agent Class in Command Pattern
 */

public class CommandsPlacer {

    private ArrayList commandsQueue = new ArrayList();

    public CommandsPlacer() {
    }

    void placeOrder(GeneralCommand command, String[] in_arr) {
        //commandsQueue.add(command);
        command.execute(in_arr);
    }
}
