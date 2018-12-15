package application.javaApp;

/**
 * Interface for executing an operation. The Receiver is the
 *
 *
*/

public interface GeneralCommand {


    void execute(String[] in_arr);

    /**
     * Tell how use the command
     */
    void usage();

}
