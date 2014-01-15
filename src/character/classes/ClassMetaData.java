package character.classes;

import character.Spell;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains meta data about a class, such as its name, known spells and the
 * class level.
 * 
 * Note that this class doesn't need to extend StateSender because
 * the state is actually tracked in the above container.
 *
 * @author Japhez
 */
public class ClassMetaData implements Serializable {

    //The name of the class
    private String className;
    //The current level of advancement within the class
    private int classLevel;
    //Spell level: spell name
    private HashMap<Integer, ArrayList<String>> knownSpells;
    private HashMap<Integer, ArrayList<String>> preparedSpells;
    //Spell caster level
    private int spellCasterLevel;

    public ClassMetaData(String className) {
        this.classLevel = 0;
        this.className = className;
        knownSpells = new HashMap<>();
        preparedSpells = new HashMap<>();
        for (int i = 0; i <= 9; i++) {
            knownSpells.put(i, new ArrayList<String>());
            preparedSpells.put(i, new ArrayList<String>());
        }
        //At level 0, there is no access to spells
        spellCasterLevel = -1;
    }

    public int getSpellCasterLevel() {
        return spellCasterLevel;
    }

    public void setSpellCasterLevel(int spellCasterLevel) {
        this.spellCasterLevel = spellCasterLevel;
    }

    public HashMap<Integer, ArrayList<String>> getPreparedSpells() {
        return preparedSpells;
    }

    public void prepareSpell(Spell spell) {
        preparedSpells.get(spell.getLevel()).add(spell.getName());
    }

    public void unprepareSpell(Spell spell) {
        preparedSpells.get(spell.getLevel()).remove(spell.getName());
    }

    public void learnSpell(Spell spell) {
        if (knownSpells.get(spell.getLevel())== null) {
            System.out.println("nullllll");
            knownSpells.put(spell.getLevel(), new ArrayList<String>());
        }
        knownSpells.get(spell.getLevel()).add(spell.getName());
    }

    public void unlearnSpell(Spell spell) {
        knownSpells.get(spell.getLevel()).remove(spell.getName());
    }

    public String getClassName() {
        return className;
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
