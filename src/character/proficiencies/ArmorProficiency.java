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

    /**
     * A better toString method that returns a good looking string instead of a
     * enum looking one.
     * @return a better looking string
     */
    @Override
    public String toString() {
        String name = name();
        String newName = "";
        boolean nextIsCapitized = true;
        for (int i = 0; i < name.length(); i++) {
            if (nextIsCapitized) {
                newName += name.substring(i, i + 1).toUpperCase();
                nextIsCapitized = false;
            } else {
                String nextChar = name.substring(i, i + 1);
                if (nextChar.equals("_")) {
                    newName += " ";
                    nextIsCapitized = true;
                } else {
                    newName += name.substring(i, i + 1).toLowerCase();
                }
            }
        }
        return newName;
    }
}
