package character;

import character.classes.CharacterClass;
import character.classes.ClassMetaData;
import character.classes.MutableCharacterClass;
import file.manipulation.FileManipulator;
import main.SaveStateTracker;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Holds information regarding character classes. This exists because of the
 * desire for multi-class support down the line.
 *
 * @author Jacob Dorman
 */
public class CharacterClassInfo extends SaveStateTracker implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient ArrayList<CharacterClass> classList;
    //class name:data
    private HashMap<String, ClassMetaData> classData;

    public CharacterClassInfo() {
        super();
        classList = new ArrayList<>();
        classData = new HashMap<>();
    }

    public void loadClassData() {
        classList = new ArrayList<>();
        Set<String> keySet = classData.keySet();
        for (String s : keySet) {
            //Attempt to load in class
            try {
                MutableCharacterClass readClass = FileManipulator.readClass(s);
                classList.add(readClass);
            } catch (Exception e) {
                System.out.println("Unable to load class: " + s);
            }
        }
        
    }

    /**
     * Adds the passed spell to the learned spell list of the passed class.
     *
     * @param characterClass
     * @param spell
     */
    public void learnSpell(CharacterClass characterClass, Spell spell) {
        classData.get(characterClass.getName()).learnSpell(spell);
    }

    /**
     * Unlearns the passed spells from the passed class.
     *
     * @param characterClass
     * @param spell
     */
    public void unlearnSpell(CharacterClass characterClass, Spell spell) {
        classData.get(characterClass.getName()).unlearnSpell(spell);
    }

    /**
     * @return the first class of this character if it is defined, otherwise
     * null
     */
    public CharacterClass getInitialClass() {
        if (classList.isEmpty()) {
            return null;
        }
        return classList.get(0);
    }

    /**
     * @return an ArrayList of all CharacterClasses of this character
     */
    public ArrayList<CharacterClass> getCharacterClasses() {
        return classList;
    }

    /**
     * Removes all existing class data and sets the passed class as the new
     * initial class.
     *
     * @param cClass the new initial class
     */
    public void setClass(CharacterClass cClass) {
        classList = new ArrayList<>();
        classList.add(cClass);
        super.stateChanged = true;
    }

    /**
     * Adds the passed class to the list of classes. This represents the
     * multi-classing process.
     *
     * @param cClass the class to add
     */
    public void addClass(CharacterClass cClass) {
        classList.add(cClass);
        super.stateChanged = true;
    }
}
