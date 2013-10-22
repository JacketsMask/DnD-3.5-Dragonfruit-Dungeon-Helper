package main;

import character.Player;
import file.manipulation.FileManipulator;

/**
 * A thread that periodically saves character data to the local disk.
 *
 * @author Japhez
 */
public class SerializationThread implements Runnable {

    private Player player;
    private final int CHECK_DELAY = 1000;
    private final int CHANGE_DELAY = 5000;
    private int timesResetBeforeSave;

    public SerializationThread(Player player) {
        this.player = player;
        timesResetBeforeSave = 0;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                //Check to see if the state has changed
                if (player.stateChanged()) {
                    do {
                        System.out.println("SerializationThread: Player state change detected");
                        Thread.sleep(CHANGE_DELAY);
                        timesResetBeforeSave++;
                    } while (player.stateChanged() || timesResetBeforeSave > 3);
                    FileManipulator.writeCharacterToFile(player);
                    player.stateSaved();
                    timesResetBeforeSave = 0;
                }
                Thread.sleep(CHECK_DELAY);
            } catch (InterruptedException ex) {
                return;
            }
        }
    }
}
