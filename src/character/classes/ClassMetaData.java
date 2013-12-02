package character.classes;

import character.Spell;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Contains meta data about a class, such as its name, known spells and the
 * class level.
 * 
 * Note that this class doesn't need to extend SaveStateTracker because
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

    public ClassMetaData(String className) {
        this.classLevel = 1;
        this.className = className;
        knownSpells = new HashMap<>();
        for (int i = 0; i <= 9; i++) {
            knownSpells.put(i, new ArrayList<String>());
        }
    }

    public void learnSpell(Spell spell) {
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
