package enumerations;

import java.util.ArrayList;

/**
 *
 * @author Jacob Dorman
 */
public enum Alignment {

    LAWFUL_GOOD, NEUTRAL_GOOD, CHAOTIC_GOOD, LAWFUL_NEUTRAL, TRUE_NEUTRAL, CHAOTIC_NEUTRAL, LAWFUL_EVIL, NEUTRAL_EVIL, CHAOTIC_EVIL;

    public static ArrayList<Alignment> getAllAlignments() {
        ArrayList<Alignment> arrayList = new ArrayList<>();
        arrayList.add(LAWFUL_GOOD);
        arrayList.add(NEUTRAL_GOOD);
        arrayList.add(CHAOTIC_GOOD);
        arrayList.add(LAWFUL_NEUTRAL);
        arrayList.add(TRUE_NEUTRAL);
        arrayList.add(CHAOTIC_NEUTRAL);
        arrayList.add(LAWFUL_EVIL);
        arrayList.add(NEUTRAL_EVIL);
        arrayList.add(CHAOTIC_EVIL);
        return arrayList;
    }

    /**
     * A better toString method that returns a good looking string instead of a
     * enum looking one.
     *
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
