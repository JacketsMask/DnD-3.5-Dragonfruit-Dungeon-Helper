package character.classes;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Holds spells per day data for a class.
 * @author Jacob
 */
public class ClassSpellsPerDay implements Serializable {

    //            Class level     Spell level  Spell count
    private HashMap<Integer, HashMap<Integer, Integer>> spellsPerDay;

    public ClassSpellsPerDay() {
        spellsPerDay = new HashMap<>();
        //Add maps for class levels
        for (int i = 1; i <= 20; i++) {
            spellsPerDay.put(i, new HashMap<Integer, Integer>());
        }
    }

    public void setSpellsPerDay(int classLevel, int spellLevel, int numSpells) {
        spellsPerDay.get(classLevel).put(spellLevel, numSpells);
    }

    public int getSpellsPerDay(int classLevel, int spellLevel) {
        return spellsPerDay.get(classLevel).get(spellLevel);
    }
}
