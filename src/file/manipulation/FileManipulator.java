package file.manipulation;

import abstracts.Ability;
import character.Player;
import character.CharacterAbilityScore;
import character.CharacterBasicInfo;
import character.CharacterClassInfo;
import character.CharacterDefense;
import character.Spell;
import character.classes.CharacterClass;
import character.inventory.CharacterInventory;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Allows retrieval and storage of data in the standard hierarchy of data files.
 * Any interaction with files should be done through this class's static
 * methods.
 *
 * @author Jacob Dorman
 */
public class FileManipulator {

    private static final String ABILITY_PATH = "data" + File.separator + "abilities" + File.separator;
    private static final String CHARACTER_PATH = "data" + File.separator + "characters" + File.separator;
    private static final String CLASS_PATH = "data" + File.separator + "classes" + File.separator;
    private static final String FEAT_PATH = "data" + File.separator + "feats" + File.separator;
    private static final String ITEM_PATH = "data" + File.separator + "items" + File.separator;
    private static final String SPELL_PATH = "data" + File.separator + "spells" + File.separator;

    /**
     * Verifies that the file hierarchy is valid and existing. Attempts to
     * create the necessary folders if they don't currently exist.
     */
    public static void verifiyFileHierarchy() {
        new File("data" + File.separator + "abilities" + File.separator).mkdirs();
        new File("data" + File.separator + "characters" + File.separator).mkdirs();
        new File("data" + File.separator + "classes" + File.separator).mkdirs();
        new File("data" + File.separator + "feats" + File.separator).mkdirs();
        new File("data" + File.separator + "items" + File.separator).mkdirs();
        new File("data" + File.separator + "spells" + File.separator).mkdirs();
    }

    public static void writeAbility(Ability ability) {
        writeObject(ability, ABILITY_PATH, ability.getName() + ".ability");
    }

    public static void writeAbilityBundle(Ability[] abilities, String bundleName) {
        writeObject(abilities, ABILITY_PATH, bundleName + ".abilitybundle");
    }

    public static void writeClass(CharacterClass cClass) {
        writeObject(cClass, CLASS_PATH, cClass.getName() + ".class");
    }

    public static void writeClassBundle(CharacterClass[] classes, String bundleName) {
        writeObject(classes, CLASS_PATH, bundleName + ".classbundle");
    }

    /**
     * Writes the passed character's information to their character information
     * folder.
     *
     * @param character
     */
    public static void writeCharacterToFile(Player character) {
        //Create a new path for the character if it doesn't already exist
        String path = CHARACTER_PATH + File.separator + character.getCharacterInfo().getName() + File.separator;
        //Make sure the path exists
        File file = new File(path);
        file.mkdirs();
        //Write all applicable information to the file
        writeObject(character.getAbilityScore(), path, "ability score");
        writeObject(character.getClassInfo(), path, "classes");
        writeObject(character.getDefense(), path, "defense");
        writeObject(character.getCharacterInfo(), path, "character");
        writeObject(character.getInventory(), path, "inventory");
    }

    /**
     * Looks at folders in the character directory, and returns a string array
     * containing the character names.
     *
     * @return a string array of saved character names
     */
    public static String[] getSavedCharacters() {
        File[] listFiles = new File(CHARACTER_PATH).listFiles();
        String[] characters = new String[listFiles.length];
        for (int i = 0; i < listFiles.length; i++) {
            System.out.println(listFiles[i].getName());
            characters[i] = listFiles[i].getName();
        }
        System.out.println("Found " + characters.length + " characters.");
        return characters;
    }

    /**
     * Attempts to gather character data with the passed name, and then returns
     * it.
     */
    public static Player readCharacterFromFile(String characterName) {
        String path = (CHARACTER_PATH + File.separator + characterName + File.separator);
        Player character = new Player();
        character.setAbilityScore((CharacterAbilityScore) readObject(path, "ability score"));
        character.setCharacterInfo((CharacterBasicInfo) readObject(path, "character"));
        character.setClassInfo((CharacterClassInfo) readObject(path, "classes"));
        character.setDefense((CharacterDefense) readObject(path, "defense"));
        character.setInventory((CharacterInventory) readObject(path, "inventory"));
        return character;
    }

    /**
     * Allows a user to select a spell from the spell directory, and then
     * returns the selected spell.
     *
     * @return the selected spell, or null if no spell is selected
     */
    public static Spell userSelectSpell() {
        //Allow the user to choose the spell file.
        File file = chooseFile(SPELL_PATH, "spell");
        if (file == null) {
            return null;
        }
        //Attempt to deserialize the spell to return it.
        return (Spell) readObject(SPELL_PATH, file.getName());
    }

    /**
     * Writes the passed spell to the preset spell directory.
     *
     * @param spell
     */
    public static void writeSpell(Spell spell) {
        writeObject(spell, SPELL_PATH, spell.getName() + ".spell");
    }

    /**
     * Write the passed spell bundle with the passed name to the spells folder.
     *
     * @param spells
     * @param bundleName
     */
    public static void writeSpellBundle(Spell[] spells, String bundleName) {
        writeObject(spells, SPELL_PATH, bundleName + ".spellbundle");
    }

    /**
     * Writes the passed object to the passed filename on the passed path.
     *
     * @param objectToWrite
     * @param path
     * @param fileName
     */
    private static void writeObject(Object objectToWrite, String path, String fileName) {
        try {
            ObjectOutputStream oStream;
            FileOutputStream fileOut;
            fileOut = new FileOutputStream(path + fileName);
            oStream = new ObjectOutputStream(fileOut);
            oStream.writeObject(objectToWrite);
            oStream.close();
        } catch (IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Attempts to read in the object from the passed path and file name, and
     * then returns that object.
     *
     * @param path
     * @param fileName
     * @return the read in object
     */
    private static Object readObject(String path, String fileName) {

        try {
            Object result;
            try (ObjectInputStream iStream = new ObjectInputStream(new FileInputStream(path + fileName))) {
                result = iStream.readObject();
                return result;
            }

        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Allows the user to choose a file (limited by the passed extensions), and
     * returns that file. If the user cancels file selection, returns null.
     *
     * @param path
     * @param extensions
     * @return the file, or null if canceled
     */
    private static File chooseFile(String path, String... extensions) {
        JFileChooser fileChooser = new JFileChooser(path);
        //Limit the selection to the passed extensions
        fileChooser.setFileFilter(new FileNameExtensionFilter("", extensions));
        fileChooser.setAcceptAllFileFilterUsed(false);
        //Get the result of the user's selection
        int showOpenDialog = fileChooser.showOpenDialog(null);
        //If the user selected a spell, continue.  Otherwise return null.
        if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        getSavedCharacters();
    }
}