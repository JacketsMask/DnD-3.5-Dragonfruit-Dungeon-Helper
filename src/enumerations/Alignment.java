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
     * Override for toString that returns the enumeration with the first letter
     * capitalized, and the rest lower case.
     * http://javahowto.blogspot.com/2006/10/custom-string-values-for-enum.html?showComment=1240529040000#c6158717620626337359
     *
     * @return the enumeration with the first letter capitalized, and the rest
     * lower case.
     */
    @Override
    public String toString() {
        String output = name().toString();
        output = output.charAt(0) + output.substring(1).toLowerCase();
        return output;
    }
}
