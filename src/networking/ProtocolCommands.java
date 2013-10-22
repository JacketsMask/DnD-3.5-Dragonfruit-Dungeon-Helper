package networking;

import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * An enumeration of protocols used to communicate between client and server. In
 * this program, the server and clients always initiate communication with a
 * string to explain their intent.
 *
 * @author Jacob Dorman
 */
public enum ProtocolCommands {

    //Server usage GET_NAME
    GET_NAME,
    //Client usage SET_NAME [name]
    SET_NAME;

    /**
     * Returns true if the passed command is a built in protocol command sent by
     * the server and enumerated above.
     *
     * @param commandToCheck the command to check against the built in command
     * list
     * @return true if the command is built-in
     */
    public static boolean isServerCommand(String commandToCheck) {
        if (commandToCheck.contains(GET_NAME.toString())) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if the passed command is a built in protocol command sent by
     * the client and enumerated above.
     *
     * @param commandToCheck the command to check against the built in command
     * list
     * @return true if the command is built-in
     */
    public static boolean isClientCommand(String commandToCheck) {
        if (commandToCheck.contains(SET_NAME.toString())) {
            return true;
        }
        return false;
    }
}
