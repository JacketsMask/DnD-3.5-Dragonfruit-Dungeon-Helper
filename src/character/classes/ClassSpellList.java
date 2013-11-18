package character.classes;

import character.Spell;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import main.SaveStateTracker;

/**
 * This is used to store spell data for a class.
 * @author Japhez
 */
public class ClassSpellList extends SaveStateTracker implements Serializable {

    private static final long serialVersionUID = 1L;
    private HashMap<Integer, ArrayList<Spell>> spellMap;

    public ClassSpellList() {
        //Initialize map for each level 0-20
        spellMap = new HashMap<>();
        for (int i = 0; i <= 20; i++) {
            spellMap.put(i, new ArrayList<Spell>());
        }
    }

    /**
     * Add the passed spell to the list of spells for this class.
     * @param spell
     * @param level 
     */
    public void addSpell(Spell spell, int level) {
        System.out.println("Spell created: " + spell + ", level: " + spell.getLevel());
        spellMap.get(level).add(spell);
        super.stateChanged = true;
    }

    /**
     * Returns the ArrayList of spells available for the specified level.
     * @param level the level of spells to retrieve
     * @return the ArrayList of spells for the passed level
     */
    public ArrayList<Spell> getSpellsAtLevel(int level) {
        return spellMap.get(level);
    }
}
