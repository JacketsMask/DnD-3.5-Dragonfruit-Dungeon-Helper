package savestate;

/**
 * This class holds meta-data about a mutable data set, allowing other objects
 * to retrieve updated information that depends on that data.
 *
 * This class should be extended by a data-storing class if another class relies
 * on StateReader elements rely on the data in this class.
 *
 * This class hooks into the DataRetrievalManager class to allow GUI elements to
 * quickly update their data when necessary.
 *
 * @author Japhez
 */
public abstract class StateSender {

    private boolean stateChanged;

    public StateSender() {
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
