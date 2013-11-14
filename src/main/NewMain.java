/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import character.Player;
import file.manipulation.FileManipulator;
import gui.BaseFrame;
import gui.character.selection.CharacterSelectionDialog;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 *
 * @author Japhez
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // Set look and feel by system.
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BaseFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    //Verify data hierarchy
                    FileManipulator.verifiyFileHierarchy();
                    //Select player file
                    CharacterSelectionDialog selection = new CharacterSelectionDialog(null, true);
                    selection.setVisible(true);
                    Player player = selection.getPlayer();
                    //Start the serialization thread for this player
                    new Thread(new SerializationThread(player)).start();
                    //Create the main frame and sub-panels
                    BaseFrame gui = new BaseFrame(player);
                    //Set the location of the new window to be determined by the OS
                    gui.setLocationByPlatform(true);
                    //Make the window visible
                    gui.setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

}
