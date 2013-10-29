package main;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 *
 * @author Japhez
 */
public class FilterTextField extends JTextField {

    //The JList that displays the data
    private JList connectedList;
    //The model storing the visible data
    private DefaultListModel model;
    //The arraylist that holds the sum of the data
    private ArrayList data;

    public FilterTextField() {
        super();
        //JList will be added later
        registerListener();
    }

    public FilterTextField(JList connectedList) {
        super();
        this.connectedList = connectedList;
        registerListener();
    }

    /**
     * Register the listener that updates the visible elements of the list.
     */
    private void registerListener() {
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                updateVisibleElements(evt);
            }
        });
    }

    private void updateVisibleElements(java.awt.event.KeyEvent evt) {
        //Get list, filter list, update visible list
        connectedList.getModel()
        String searchQuery = locationFilterTextField.getText();
        ArrayList<Entry> results = pocket.searchPasswords(searchQuery);
        generateListFromArrayList(results);
    }

    /**
     * Fills the model for the list with the contains of the passed ArrayList.
     *
     * @param entries an ArrayList of entries
     */
    private void generateListFromArrayList(ArrayList<Entry> entries) {
        listModel.clear();
        for (Entry e : entries) {
            listModel.add(0, e);
        }
        resultList.clearSelection();
    }

    public JList getConnectedList() {
        return connectedList;
    }

    public void setConnectedList(JList connectedList) {
        this.connectedList = connectedList;
    }
}
