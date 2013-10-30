package main;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;

/**
 * A list model that updates items visible in the list to the content
 * dynamically typed into a related JTextField, irrespective of character case.
 *
 * For simplicity's sake currently the only way to modify the full element list
 * is to retrieve the data ArrayList with getData() and then work on that
 * ArrayList directly. This is to prevent confusion with the inherited methods
 * that modify the Vector data structure inherited from DefaultListModel, which
 * is actually used to display elements.
 *
 * Don't bother modifying the Vector structure. It is volatile and is
 * regenerated whenever the list is filtered, anyways.
 *
 * Elements added to the list won't be shown until the filter is refreshed,
 * though this process can be forced with the clearFilter() method.
 *
 * @author Jacob Dorman
 */
public class FilteredListModel<G> extends DefaultListModel {

    //Holds all list data, whether it be visible or not
    private ArrayList<G> data;
    //The text field that acts as a filter to the visible list data
    private JTextField filterTextField;

    /**
     * Creates a new FilteredListModel, and assigns the passed JTextField as the
     * filter for this list.
     *
     * @param filterTextField the filter of the list
     */
    public FilteredListModel(final JTextField filterTextField) {
        this.data = new ArrayList<>();
        this.filterTextField = filterTextField;
        //Add listener to JTextField to update filter
        filterTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                updateVisibleListFromFilter();
            }
        });
    }

    /**
     * Searches the stored data for the passed string, and returns an ArrayList
     * containing all resulting matches.
     *
     * @param searchTerm the term to search with
     * @return an ArrayList containing all matches
     */
    private void updateVisibleListFromFilter() {
        //Get the search query from the JTextField
        String searchQuery = filterTextField.getText();
        //Create an ArrayList to store query results
        ArrayList<G> results = new ArrayList<>();
        //Make the search term lower case for easier searching
        searchQuery = searchQuery.toLowerCase();
        //Search through the list, looking for things that contain the search term
        for (G nextElement : data) {
            String stringOfElement = nextElement.toString();
            //Check to see if the search term is included in the list
            if (stringOfElement.toLowerCase().contains(searchQuery)) {
                //Add the password/source to the result list
                results.add(nextElement);
            }
        }
        //Clear the visible data
        super.clear();
        //Add each result to the super (visible) vector, respecting original order
        for (G e : results) {
            super.add(0, e);
        }
    }

    /**
     * @return the ArrayList containing all this lists elements
     */
    public ArrayList<G> getData() {
        return data;
    }

    /**
     * If you really want to completely change the data stored for the list,
     * pass an ArrayList to this.
     *
     * @param data the new ArrayList to be used as list elements
     */
    public void setData(ArrayList<G> data) {
        this.data = data;
        //Clears the filtering text field
        filterTextField.setText("");
        clearFilter();
    }

    /**
     * Resets the filter JTextField's text to an empty string, and then
     * refreshes visible list.
     */
    public void clearFilter() {
        filterTextField.setText("");
        //Clear the visible data
        super.clear();
        //Add each entry from the data to the visible Vector
        for (G e : data) {
            super.add(0, e);
        }
    }
}
