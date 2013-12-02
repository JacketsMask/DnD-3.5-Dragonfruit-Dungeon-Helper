package main;

/**
 * This class holds data about a mutable state.
 *
 * This class should be extended by a data-storing class if GUI elements rely on
 * the data in this class, or if the class needs to be checked by the
 * SerializationThread.
 *
 * This class hooks into the DataRetrievalManager class to allow GUI elements to
 * quickly update their data when necessary.
 *
 * @author Japhez
 */
public abstract class SaveStateSender {

    private boolean stateChanged;

    public SaveStateSender() {
        stateChanged = false;
    }

    public void stateChanged() {
        stateChanged = true;
        //Signal that this sender's data has changed
        DataRetrievalManager.dataChanged(this);
    }

    public boolean isStateChanged() {
        return stateChanged;
    }

    public void stateSaved() {
        stateChanged = false;
    }
}
