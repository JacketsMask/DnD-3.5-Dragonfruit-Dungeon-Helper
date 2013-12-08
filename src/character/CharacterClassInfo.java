package character;

import character.classes.CharacterClass;
import character.classes.ClassMetaData;
import character.classes.ClassSpellList;
import character.classes.MutableCharacterClass;
import enumerations.AbilityScore;
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
    private transient Player player;
    private transient ArrayList<CharacterClass> classList;
    //class name:data
    private HashMap<String, ClassMetaData> classData;

    public CharacterClassInfo(Player player) {
        this.player = player;
        classList = new ArrayList<>();
        classData = new HashMap<>();
        super.stateChanged();
    }

    /**
     * @param cc the character class
     * @return the spell casting level (maximum level of spell that can be cast)
     * for the current level of the passed character class.
     */
    public int getSpellCasterLevel(CharacterClass cc) {
        return classData.get(cc.getName()).getSpellCasterLevel();
    }

    /**
     * Calculates and returns the maximum prepared spells for the passed level 
     * of spell for the passed class at its current level.
     * @param cc the class
     * @param spellLevel the level of spell
     * @return the maximum number of spells that are preparable
     */
    public int getMaxPreparedSpells(CharacterClass cc, int spellLevel) {
        int classLevel = classData.get(cc.getName()).getClassLevel();
        int total = 0;
        //Find out base value
        total += cc.getSpellsPerDay().getSpellsPerDay(classLevel, spellLevel);
        //Find out bonus value
        AbilityScore as = cc.getPrimaryAttribute();
        int modifier = player.getAbilityScore().getAbilityScoreModifier(as);
        int bonus = (int) Math.ceil(((double) 1 + modifier - spellLevel) / 4);
        //Make sure there's a base value, the spell level isn't zero, and the bonus is greater than 0
        if (total != 0 && spellLevel != 0 && bonus > 0) {
            total += bonus;
        }
        return total;
    }

    /**
     * Increase BAB (implicit).
     * Increase saves (implicit).
     * Gain ability to prepare new spells possibly (implicit).
     * Gain access to new spells. (Automatically learn available spells if innate)
     * //TODO: Gain any Ability available at the current level.
     * 
     * @param cc 
     */
    public void levelUp(CharacterClass cc) {
        System.out.println(cc.getName() + " leveling up.");
        ClassMetaData data = classData.get(cc.getName());
        int prevLevel = data.getClassLevel();
        int nextLevel = prevLevel + 1;
        int prevCasterLevel = data.getSpellCasterLevel();
        data.setClassLevel(nextLevel);
        //If the caster is just being created, set their spell level to 0
        if (data.getSpellCasterLevel() == -1) {
            data.setSpellCasterLevel(0);
        } else {
            //Find out if the player can cast higher level spells
            int maxPreparedSpells = getMaxPreparedSpells(cc, prevCasterLevel + 1);
            //If they can prepare spells at that level, increase their caster level
            if (maxPreparedSpells > 0) {
                data.setSpellCasterLevel(prevCasterLevel + 1);
            }
        }
        //Innate casters learn all their spells that they have access to immediately
        //Make sure they actually gained a caster level to prevent loading in duplicates
        if (cc.getCasterType().equals(CasterType.INNATE) && data.getSpellCasterLevel() != prevCasterLevel) {
            updateInnateSpellList(cc);
        }
        super.stateChanged();
    }

    private void updateInnateSpellList(CharacterClass cc) {
        //Clear know data first
        ClassMetaData data = classData.get(cc.getName());
        data.getKnownSpells().clear();
        //Get the current caster level
        int spellCasterLevel = data.getSpellCasterLevel();
        //Incrementally add all know spells to list up to the caster level
        for (int i = 0; i <= spellCasterLevel; i++) {
            System.out.println("Granting spells at level: " + i);
            ArrayList<Spell> spellsAtLevel = cc.getSpellList().getSpellsAtLevel(i);
            //Add all accessible spells to known list
            for (Spell s : spellsAtLevel) {
                learnSpell(cc, s);
            }
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

    public HashMap<Integer, ArrayList<String>> getPreparedSpells(CharacterClass cc) {
        return classData.get(cc.getName()).getPreparedSpells();
    }

    public void prepareSpell(CharacterClass cc, Spell spell) {
        classData.get(cc.getName()).prepareSpell(spell);
        super.stateChanged();
    }

    public void unprepareSpell(CharacterClass cc, Spell spell) {
        classData.get(cc.getName()).unprepareSpell(spell);
        super.stateChanged();
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
        //Bring to level 1
        levelUp(cClass);
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

    public void setPlayer(Player player) {
        this.player = player;
    }
}
