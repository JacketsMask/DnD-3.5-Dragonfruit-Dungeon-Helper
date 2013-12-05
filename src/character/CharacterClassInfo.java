package character;

import character.classes.CharacterClass;
import character.classes.ClassMetaData;
import character.classes.ClassSpellList;
import character.classes.MutableCharacterClass;
import enumerations.CasterType;
import file.manipulation.FileManipulator;
import savestate.SaveStateSender;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 * Holds information regarding character classes. This exists because of the
 * desire for multi-class support down the line.
 * 
 * This class also provides access to information that would require info from
 * the character class and from class meta-data (such as known spells).
 * 
 * Class meta-data should only be accessed through here, and not directly 
 * retrieved (don't give them direct access to the HashMap).
 *
 * @author Jacob Dorman
 */
public class CharacterClassInfo extends SaveStateSender implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient ArrayList<CharacterClass> classList;
    //class name:data
    private HashMap<String, ClassMetaData> classData;

    public CharacterClassInfo() {
        classList = new ArrayList<>();
        classData = new HashMap<>();
        super.stateChanged();

    }

    /**
     * TODO: Flesh out level-up process
     * Increase hitpoints.
     * Gain access to new spells. (Automatically learn available spells if Divine)
     * Gain new feat at every % 3 level.
     * Gain new AbilityScore at every % 4 level.
     * 
     * 
    Increase hit points (roll class hit die and add Con mod).
    Increase base attack bonus
    Increase saves
    Allocate skill points
    Add/advance class features

     * 
     * @param cc 
     */
    public void levelUp(CharacterClass cc) {
        int prevLevel = classData.get(cc.getName()).getClassLevel();
        classData.get(cc.getName()).setClassLevel(prevLevel + 1);
        //Increase hitpoints
        //Divine casters learn all their spells that they have access to immediately
        if (cc.getCasterType().equals(CasterType.DIVINE_CASTER)) {
            //Add all accessible spells to known list
        }
        super.stateChanged();
    }

    public void setClassLevel(CharacterClass cc, int classLevel) {
        classData.get(cc.getName()).setClassLevel(classLevel);
        System.out.println(cc.getName() + " now level " + classLevel);
        super.stateChanged();
    }

    public int getClassLevel(CharacterClass cc) {
        return classData.get(cc.getName()).getClassLevel();
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
                System.err.println("Unable to load class: " + s);
            }
        }
    }

    /**
     * Returns a HashMap with keys being spell levels, and the values being
     * ArrayLists of spells known at that level.
     * @param cc the character class
     * @return a HashMap of spell data
     */
    public HashMap<Integer, ArrayList<Spell>> getKnownSpells(CharacterClass cc) {
        ClassSpellList spellList = cc.getSpellList();
        HashMap<Integer, ArrayList<Spell>> spellMap = new HashMap<>();
        HashMap<Integer, ArrayList<String>> knownSpells = classData.get(cc.getName()).getKnownSpells();
        //For each spell level
        for (Integer spellLevel : knownSpells.keySet()) {
            //Create array to hold spells for this level
            ArrayList<Spell> spellsThisLevel = new ArrayList<>();
            //For each spell
            for (String spellName : knownSpells.get(spellLevel)) {
                //Get spell reference and add to the result
                Spell spell = spellList.getSpell(spellLevel, spellName);
                spellsThisLevel.add(spell);
            }
            spellMap.put(spellLevel, spellsThisLevel);
        }
        return spellMap;
    }

    /**
     * Adds the passed spell to the learned spell list of the passed class.
     *
     * @param characterClass
     * @param spell
     */
    public void learnSpell(CharacterClass characterClass, Spell spell) {
        classData.get(characterClass.getName()).learnSpell(spell);
        super.stateChanged();
    }

    /**
     * Unlearns the passed spells from the passed class.
     *
     * @param characterClass
     * @param spell
     */
    public void unlearnSpell(CharacterClass characterClass, Spell spell) {
        classData.get(characterClass.getName()).unlearnSpell(spell);
        super.stateChanged();

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
        //Add class data
        classList.add(cClass);
        //Add meta data
        classData.put(cClass.getName(), new ClassMetaData(cClass.getName()));
        super.stateChanged();
    }

    /**
     * Adds the passed class to the list of classes. This represents the
     * multi-classing process.
     *
     * @param cClass the class to add
     */
    public void addClass(CharacterClass cClass) {
        classList.add(cClass);
        super.stateChanged();
    }
}
