package character.proficiencies;

import java.util.ArrayList;

/**
 * An enumeration of the armor proficiencies.
 *
 * @author Jacob Dorman
 */
public enum ArmorProficiency {

    HEAVY, MEDIUM, LIGHT;

    /**
     * @return an ArrayList of all armor proficiencies
     */
    public static ArrayList<ArmorProficiency> getProficiencies() {
        ArrayList<ArmorProficiency> list = new ArrayList<>();
        list.add(HEAVY);
        list.add(MEDIUM);
        list.add(LIGHT);
        return list;
    }
}
