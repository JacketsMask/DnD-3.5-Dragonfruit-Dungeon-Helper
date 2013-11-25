package character;

import character.classes.CharacterClass;
import main.SaveStateTracker;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Holds information regarding character classes. This exists because of the
 * desire for multi-class support down the line.
 *
 * @author Jacob Dorman
 */
public class CharacterClassInfo extends SaveStateTracker implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<CharacterClass> list;
    private HashMap<CharacterClass, ArrayList<Spell>> knownSpells;

    public CharacterClassInfo() {
        super();
        list = new ArrayList<>();
        knownSpells = new HashMap<>();
    }
    
    /**
     * Adds the passed spell to the learned spell list of the passed class.
     * @param characterClass
     * @param spell 
     */
    public void learnSpell(CharacterClass characterClass, Spell spell) {
        //Add value to ArrayList of spells for the passed class,
        //if there is no ArrayList set up yet, set it up
        if (knownSpells.get(characterClass) != null) {
            knownSpells.get(characterClass).add(spell);
        } else {
            ArrayList<Spell> classSpells = new ArrayList<>();
            classSpells.add(spell);
            knownSpells.put(characterClass, classSpells);
        }
    }
    
    /**
     * Unlearns the passed spells from the passed class.
     * @param characterClass
     * @param spell 
     */
    public void unlearnSpell(CharacterClass characterClass, Spell spell) {
        knownSpells.get(characterClass).remove(spell);
    }

    /**
     * @return the first class of this character if it is defined, otherwise
     * null
     */
    public CharacterClass getInitialClass() {
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * @return an ArrayList of all CharacterClasses of this character
     */
    public ArrayList<CharacterClass> getCharacterClasses() {
        return list;
    }

    /**
     * Removes all existing class data and sets the passed class as the new
     * initial class.
     *
     * @param cClass the new initial class
     */
    public void setClass(CharacterClass cClass) {
        list = new ArrayList<>();
        list.add(cClass);
        super.stateChanged = true;
    }

    /**
     * Adds the passed class to the list of classes. This represents the
     * multi-classing process.
     *
     * @param cClass the class to add
     */
    public void addClass(CharacterClass cClass) {
        list.add(cClass);
        super.stateChanged = true;
    }
}
