package main;

import character.CharacterBasicInfo;
import interfaces.SaveStateReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Data is stored within classes than extend SaveStateTracker, and GUI
 * components (readers) depend on the data stored within the extended classes.
 *
 * This class
 *
 * @author Japhez
 */
public abstract class DataRetrievalManager {

    //Master list
    private static HashMap<SaveStateSender, ArrayList<SaveStateReader>> masterList = new HashMap<>();
    //Current list
    private static HashMap<SaveStateSender, ArrayList<SaveStateReader>> currentList = new HashMap<>();

    /**
     * Updates the current list of readers that need to retrieve the updated
     * data from the sender, removing the passed reader from the list.
     *
     * @param sender
     * @param reader
     */
    public static void dataRead(SaveStateSender sender, SaveStateReader reader) {
        currentList.get(sender).remove(reader);
    }

    /**
     * Called when data is changed, this resets the "read" status for any
     * readers that use the passed SaveStateTracker.
     *
     * @param sender
     */
    public static void dataChanged(SaveStateSender sender) {
        //Add to list if not already there
        if (masterList.get(sender) == null) {
            masterList.put(sender, new ArrayList<SaveStateReader>());
            currentList.put(sender, new ArrayList<SaveStateReader>());
        }
        //Clear current list
        currentList.get(sender).clear();
        //Add each reader from the master list to current list
        ArrayList<SaveStateReader> get = masterList.get(sender);
        for (SaveStateReader infoReader : get) {
            currentList.get(sender).add(infoReader);
        }
    }

    /**
     * @param source
     * @param reader
     * @return true if the passed reader needs to update their data from the
     * passed source.
     */
    public static boolean isDataChanged(SaveStateSender source, SaveStateReader reader) {
        return (currentList.get(source).contains(reader));
    }

    /**
     * Links the passed SaveStateReader to the passed SaveStateTracker.
     * After linking, the reader can use the isDataChanged() method to determine
     * whether they need to retrieve new data or not.
     *
     * @param reader
     * @param source
     */
    public static void linkReader(SaveStateReader reader, SaveStateSender source) {
        if (masterList.get(source) == null) {
            masterList.put(source, new ArrayList<SaveStateReader>());
            currentList.put(source, new ArrayList<SaveStateReader>());
        }
        masterList.get(source).add(reader);
        currentList.get(source).add(reader);
    }

    public static void main(String[] args) {
        final CharacterBasicInfo characterBasicInfo = new CharacterBasicInfo();
        SaveStateReader reader = new SaveStateReader() {
            @Override
            public void loadInfo() {
                if (DataRetrievalManager.isDataChanged(characterBasicInfo, this)) {
                    System.out.println("Info test load complete.");
                    //Set data as read
                    DataRetrievalManager.dataRead(characterBasicInfo, this);
                }
            }
        };
        DataRetrievalManager.dataChanged(characterBasicInfo);
        DataRetrievalManager.linkReader(reader, characterBasicInfo);
        System.out.println("Calling load method...");
        reader.loadInfo();
        System.out.println("Calling load method...");
        reader.loadInfo();
        characterBasicInfo.setAge(5);
        System.out.println("Age changed.");
        System.out.println("Calling load method...");
        reader.loadInfo();
        System.out.println("Calling load method...");
        reader.loadInfo();
    }
}
