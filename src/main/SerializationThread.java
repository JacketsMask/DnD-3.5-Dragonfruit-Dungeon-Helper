package main;

import character.Player;
import character.Spell;
import character.classes.CharacterClass;
import character.classes.ClassSpellList;
import file.manipulation.FileManipulator;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A thread that periodically saves character data to the local disk.
 *
 * @author Japhez
 */
public class SerializationThread implements Runnable {

    private Player player;
    private final int CHECK_DELAY = 1000;
//    private final int CHANGE_DELAY = 5000;
    private final int CHANGE_DELAY = 1;
    private int timesSkippedBeforeSaving;
    private final int MAX_SKIPS = 3;

    public SerializationThread(Player player) {
        this.player = player;
        timesSkippedBeforeSaving = 0;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            try {
                //Check to see if the state has changed
                if (player.stateChanged()) {
                    do {
                        player.stateSaved();
                        System.out.println("SerializationThread: Player state change detected");
                        Thread.sleep(CHANGE_DELAY);
                        timesSkippedBeforeSaving++;
                    } while (player.stateChanged() || timesSkippedBeforeSaving >= MAX_SKIPS);
                    System.out.println("SerializationThread: Player state saved.");
                    FileManipulator.writeCharacterToFile(player);
                    player.stateSaved();
                    timesSkippedBeforeSaving = 0;
                }
                Thread.sleep(CHECK_DELAY);
            } catch (NullPointerException ex) {
                return;
            } catch (InterruptedException ex) {
                Logger.getLogger(SerializationThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) {
        CharacterClass[] readClasses = FileManipulator.readClasses();
        ClassSpellList spellList = readClasses[0].getSpellList();
        ArrayList<Spell> spellsAtLevel = spellList.getSpellsAtLevel(0);
        for (Spell s : spellsAtLevel) {
            System.out.println(s);
        }
    }
}
