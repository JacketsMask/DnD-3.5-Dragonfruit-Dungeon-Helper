package character.classes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains meta data about a class, such a known spells and the class level.
 *
 * @author Japhez
 */
public class ClassMetaData implements Serializable {

    private int classLevel;
    //Class level: spell name
    private HashMap<Integer, ArrayList<String>> knownSpells;

    public ClassMetaData() {
    }

    public int getClassLevel() {
        return classLevel;
    }

    public HashMap<Integer, ArrayList<String>> getKnownSpells() {
        return knownSpells;
    }

    public void setClassLevel(int classLevel) {
        this.classLevel = classLevel;
    }
}
