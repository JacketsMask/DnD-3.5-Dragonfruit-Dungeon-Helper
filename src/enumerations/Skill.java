package enumerations;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * An enumeration of all skill types included in D&D 3.5. Keep in mind that
 * there can be multiples of knowledge or profession type skills. TODO: Find a
 * way to differentiate.
 *
 * @author Jacob Dorman
 */
public enum Skill {

    APPRAISE, AUTOHYPNOSIS, BALANCE, BLUFF, CLIMB, CONCENTRATION, CONTROL_SHAPE,
    CRAFT, DECIPHER_SCRIPT, DIPLOMACY, DISABLE_DEVICE, DISGUISE, ESCAPE_ARTIST,
    FORGERY, GATHER_INFORMATION, HANDLE_ANIMAL, HEAL, HIDE, INTIMIDATE, JUMP,
    KNOWLEDGE, LISTEN, MOVE_SILENTLY, OPEN_LOCK, PERFORM, PSICRAFT, PROFESSION,
    RIDE, SEARCH, SENSE_MOTIVE, SLEIGHT_OF_HAND, SPEAK_LANGUAGE, SPELLCRAFT,
    SPOT, SURVIVAL, SWIM, TUMBLE, USE_MAGIC_DEVICE, USE_PSIONIC_DEVICE, USE_ROPE;

    /**
     * @return an array of all skills
     */
    public static Skill[] getAllSkills() {
        return new Skill[]{APPRAISE, AUTOHYPNOSIS, BALANCE, BLUFF, CLIMB, CONCENTRATION, CONTROL_SHAPE,
            CRAFT, DECIPHER_SCRIPT, DIPLOMACY, DISABLE_DEVICE, DISGUISE, ESCAPE_ARTIST,
            FORGERY, GATHER_INFORMATION, HANDLE_ANIMAL, HEAL, HIDE, INTIMIDATE, JUMP,
            KNOWLEDGE, LISTEN, MOVE_SILENTLY, OPEN_LOCK, PERFORM, PSICRAFT, PROFESSION,
            RIDE, SEARCH, SENSE_MOTIVE, SLEIGHT_OF_HAND, SPEAK_LANGUAGE, SPELLCRAFT,
            SPOT, SURVIVAL, SWIM, TUMBLE, USE_MAGIC_DEVICE, USE_PSIONIC_DEVICE, USE_ROPE};
    }
    
    public static ArrayList<Skill> getAllSkillsInArrayList() {
        ArrayList<Skill> arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(getAllSkills()));
        return arrayList;
     }

    /**
     * @return an array of strength based skills
     */
    public static Skill[] getStrSkills() {
        return new Skill[]{
            CLIMB, JUMP, SWIM
        };
    }

    /**
     * @return an array of dexterity based skills
     */
    public static Skill[] getDexSkills() {
        return new Skill[]{
            BALANCE, ESCAPE_ARTIST, HIDE, MOVE_SILENTLY, OPEN_LOCK,
            RIDE, SLEIGHT_OF_HAND, TUMBLE, USE_ROPE
        };
    }

    /**
     * @return an array of constitution based skills
     */
    public static Skill[] getConSkills() {
        return new Skill[]{
            CONCENTRATION
        };
    }

    /**
     * @return an array of intelligence based skills
     */
    public static Skill[] getIntSkills() {
        return new Skill[]{
            APPRAISE, CRAFT, DECIPHER_SCRIPT, DISABLE_DEVICE, FORGERY,
            KNOWLEDGE, PSICRAFT, SEARCH, SPELLCRAFT
        };
    }

    /**
     * @return an array of wisdom based skills
     */
    public static Skill[] getWisSkills() {
        return new Skill[]{
            AUTOHYPNOSIS, CONTROL_SHAPE, HEAL, LISTEN, PROFESSION,
            SENSE_MOTIVE, SPOT, SURVIVAL
        };
    }

    /**
     * @return an array of charisma based skills
     */
    public static Skill[] getChaSkills() {
        return new Skill[]{
            BLUFF, DIPLOMACY, DISGUISE, GATHER_INFORMATION,
            HANDLE_ANIMAL, INTIMIDATE, PERFORM, USE_MAGIC_DEVICE,
            USE_PSIONIC_DEVICE
        };
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
