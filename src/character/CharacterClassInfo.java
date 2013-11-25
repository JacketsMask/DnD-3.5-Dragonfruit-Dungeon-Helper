package character;

import character.classes.CharacterClass;
import character.classes.ClassMetaData;
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
    private transient ArrayList<CharacterClass> list;
    //class name:data
    private HashMap<String, ClassMetaData> classData;
    
    public CharacterClassInfo() {
        super();
        list = new ArrayList<>();
        classData = new HashMap<>();
    }

    /**
     * Adds the passed spell to the learned spell list of the passed class.
     *
     * @param characterClass
     * @param spell
     */
    public void learnSpell(CharacterClass characterClass, Spell spell) {
        HashMap<Integer, ArrayList<String>> knownSpells = classData.get(characterClass.getName()).getKnownSpells();
        int spellLevel = spell.getLevel();
        //Add value to ArrayList of spells for the passed class,
        knownSpells.get(spellLevel).add(spell.getName());
    }

    /**
     * Unlearns the passed spells from the passed class.
     *
     * @param characterClass
     * @param spell
     */
    public void unlearnSpell(CharacterClass characterClass, Spell spell) {
        HashMap<Integer, ArrayList<String>> knownSpells = classData.get(characterClass.getName()).getKnownSpells();
        knownSpells.get(spell.getLevel()).remove(spell.getName());
    }
    
    public int getFortSave() {
        
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
