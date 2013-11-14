package main;

import java.io.Serializable;

/**
 *
 * @author Japhez
 */
public abstract class SaveStateTracker implements Serializable {

    private static final long serialVersionUID = 1L;
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
