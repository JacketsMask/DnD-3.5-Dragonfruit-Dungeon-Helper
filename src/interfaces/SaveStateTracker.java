package interfaces;

/**
 *
 * @author bcmchs
 */
public abstract class SaveStateTracker {

    protected boolean stateChanged;

    public SaveStateTracker() {
        stateChanged = false;
    }

    public boolean stateChanged() {
        return stateChanged;
    }

    public void stateSaved() {
        stateChanged = false;
    }
}
