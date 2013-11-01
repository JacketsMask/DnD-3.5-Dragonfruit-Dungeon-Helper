package main;

import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

/**
 * A table model that updates items visible in the table to the content
 * dynamically typed into a related JTextField, irrespective of character case.
 *
 * @author Jacob Dorman
 */
public final class FilteredTableModel extends AbstractTableModel {

    /**
     * Each column is an ArrayList that holds data for that column.
     * G value = masterData.get(0).get(1);
     *     ^                    ^      ^
     *   value                column  row
     * 
     */
    private ArrayList<ArrayList<Object>> masterData;
    private ArrayList<ArrayList<Object>> visibleData;
    private int numCols;
    private int numTotalRows;
    private int numVisibleRows;
    private String[] columnNames;
    //The text field that acts as a filter to the visible list data
    private JTextField filterTextField;

    /**
     * Creates a new FilteredListModel, and assigns the passed JTextField as the
     * filter for this list.
     * @param filterTextField the filter of the list
     * @param numCols the number of columns
     * @param columnNames a string array of the names of the columns
     */
    public FilteredTableModel(final JTextField filterTextField, int numCols, String[] columnNames) {
        this.numCols = numCols;
        this.numTotalRows = 0;
        this.numVisibleRows = 0;
        this.masterData = new ArrayList<>();
        this.visibleData = new ArrayList<>();
        //For each column
        for (int i = 0; i < numCols; i++) {
            //Add a new array list to hold data
            masterData.add(i, new ArrayList<>());
            visibleData.add(i, new ArrayList<>());
        }
        this.columnNames = columnNames;
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
     * Adds the passed data as a new row in the table.
     * @param rowData 
     */
    public void addRow(Object[] rowData) {
        //Make sure there's enough data for each column
        int dataCount = 0;
        if (rowData.length == numCols) {
            //For each column
            for (int i = 0; i < numCols; i++) {
                masterData.get(i).add(rowData[dataCount++]);
            }
            numTotalRows++;
        } else {
            throw new InvalidRowDataException("Expected " + numCols + " columns of data, " + rowData.length + " given.");
        }
    }

    /**
     * Removes the row at the passed index.
     * @param rowIndex 
     */
    public void removeRow(int rowIndex) {
        //Make sure the row is in bounds
        if (!(rowIndex < 0 || rowIndex > (numTotalRows - 1))) {
            for (ArrayList array : masterData) {
                array.remove(rowIndex);
            }
            numTotalRows--;
        }
    }

    /**
     * Searches the stored data for the passed string, and returns an ArrayList
     * containing all resulting matching rows.
     *
     * @param searchTerm the term to search with
     * @return an ArrayList containing all matches
     */
    private void updateVisibleListFromFilter() {
        //Get the search query from the JTextField
        String searchQuery = filterTextField.getText();
        //Create an ArrayList to store the indexes of matching rows
        ArrayList<Integer> results = new ArrayList<>();
        //Make the search term lower case for easier searching
        searchQuery = searchQuery.toLowerCase();
        //Search through the first column, looking for things that contain the search term
        ArrayList<Object> firstColumn = masterData.get(0);
        //Search every row of the first column
        for (int j = 0; j < numTotalRows; j++) {
            Object row = firstColumn.get(j);
            //Check to see is row contains the search query
            if (row.toString().toLowerCase().contains(searchQuery)) {
                results.add(j);
            }
        }
        //Now we know the indexes of matching rows, we need to add these rows to
        //the visible table
        //Clear the visible data rows
        clearVisibleTable();
        //Add each result to the visible ArrayList, respecting original order
        //For each matching row
        for (Integer row : results) {
            for (int column = 0; column < numCols; column++) {
                //Get matching record
                Object record = masterData.get(column).get(row);
                //Add it to visible column
                visibleData.get(column).add(record);
            }
            numVisibleRows++;
        }
        fireTableDataChanged();
    }

    private void clearVisibleTable() {
        for (ArrayList<Object> a : visibleData) {
            a.clear();
            numVisibleRows = 0;
        }
    }

    /**
     * Resets the filter JTextField's text to an empty string, and then
     * refreshes visible list.
     */
    public void clearFilter() {
        filterTextField.setText("");
        clearVisibleTable();
    }

    @Override
    public int getRowCount() {
        if (numVisibleRows == 0) {
            return numTotalRows;
        } else {
            return numVisibleRows;
        }
    }

    @Override
    public int getColumnCount() {
        return numCols;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArrayList<ArrayList<Object>> visibleTable;
        if (numVisibleRows == 0) {
            visibleTable = masterData;
        } else {
            visibleTable = visibleData;
        }
        //Make sure there's data at that location, if not then return null
        if (visibleTable.size() < columnIndex || visibleTable.get(columnIndex).size() < rowIndex) {
            return null;
        }
        return visibleTable.get(columnIndex).get(rowIndex);
    }

    /**
     * Sets the value in the passed row and column to the passed value.
     * @param value
     * @param rowIndex
     * @param columnIndex 
     */
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        masterData.get(columnIndex).set(rowIndex, value);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    /**
     * An exception that should be thrown when an invalid amount of data is
     * attempted to be added to a table.
     */
    private static class InvalidRowDataException extends RuntimeException {

        public InvalidRowDataException(String message) {
            super(message);
        }
    }
}