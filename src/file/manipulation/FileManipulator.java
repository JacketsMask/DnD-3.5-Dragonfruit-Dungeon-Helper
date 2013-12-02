package file.manipulation;

import abstracts.Ability;
import character.Player;
import character.CharacterAbilityScore;
import character.CharacterAttack;
import character.CharacterBasicInfo;
import character.CharacterClassInfo;
import character.CharacterDefense;
import character.CharacterHealth;
import character.CharacterProficiencies;
import character.CharacterSkills;
import character.Spell;
import character.classes.CharacterClass;
import character.classes.MutableCharacterClass;
import character.effects.EffectManager;
import character.inventory.CharacterInventory;
import character.inventory.Item;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
        System.out.println(cClass.getName() + " class data written to file.");
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
        String path = CHARACTER_PATH + File.separator + character.getBasicInfo().getName() + File.separator;
        //Make sure the path exists
        File file = new File(path);
        file.mkdirs();
        //Write all applicable information to the file
        writeObject(character.getBasicInfo(), path, "basic info");
        writeObject(character.getClassInfo(), path, "classes");
        writeObject(character.getHealth(), path, "health");
        writeObject(character.getAbilityScore(), path, "ability score");
        writeObject(character.getAttack(), path, "attack");
        writeObject(character.getDefense(), path, "defense");
        writeObject(character.getProficiencies(), path, "proficiencies");
        writeObject(character.getInventory(), path, "inventory");
        writeObject(character.getEffectManager(), path, "effects");
        writeObject(character.getSkills(), path, "skills");
    }

    private static String[] getFilesOnPath(String path) {
        File[] listFiles = new File(path).listFiles();
        if (listFiles == null) {
            return null;
        }
        String[] fileNames = new String[listFiles.length];
        for (int i = 0; i < listFiles.length; i++) {
            System.out.println(listFiles[i].getName());
            fileNames[i] = listFiles[i].getName();
        }
        return fileNames;
    }

    /**
     * Looks at folders in the class directory, and returns a string array
     * containing the class names.
     *
     * @return a string array of saved class names
     */
    public static String[] getSavedClasses() {
        return getFilesOnPath(CLASS_PATH);
    }

    /**
     * Looks at folders in the character directory, and returns a string array
     * containing the character names.
     *
     * @return a string array of saved character names
     */
    public static String[] getSavedCharacters() {
        return getFilesOnPath(CHARACTER_PATH);
    }

    public static void deleteCharacterFile(String characterName) {
        String path = CHARACTER_PATH + File.separator + characterName + File.separator;
        System.out.println("path: " + path);
        File[] listFiles = new File(path).listFiles();
        for (File f : listFiles) {
            System.out.println(f);
            f.delete();
        }
        boolean delete = new File(path).delete();
        if (delete) {
            System.out.println(characterName + " was deleted.");
        }
    }

    /**
     * Attempts to gather character data with the passed name, and then returns
     * it.
     * 
     * //TODO: Document this better, this step is huge.
     */
    public static Player readCharacterFromFile(String characterName) {
        String path = (CHARACTER_PATH + File.separator + characterName + File.separator);
        Player player = new Player();
        try {
            player.setBasicInfo((CharacterBasicInfo) readObject(path, "basic info"));
        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Basic info serialization outdated.  You'll have to re-enter a bit of data for your character. Sorry about that.", "Oops", JOptionPane.OK_OPTION);
//            character.setBasicInfo(new CharacterBasicInfo());
        }
        try {
            CharacterClassInfo classInfo = (CharacterClassInfo) readObject(path, "classes");
            classInfo.loadClassData();
            player.setClassInfo(classInfo);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            CharacterHealth health = (CharacterHealth) readObject(path, "health");
            player.setHealth(health);
            health.setPlayer(player);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            CharacterAbilityScore as = (CharacterAbilityScore) readObject(path, "ability score");
            player.setAbilityScore(as);
        } catch (ClassNotFoundException | IOException ex) {
            JOptionPane.showMessageDialog(null, "Ability score serialization outdated.  You'll have to re-enter a bit of data for your character. Sorry about that.", "Oops", JOptionPane.OK_OPTION);
        }
        try {
            CharacterAttack attack = (CharacterAttack) readObject(path, "attack");
            attack.setPlayer(player);
            player.setAttack(attack);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            CharacterDefense defense = (CharacterDefense) readObject(path, "defense");
            player.setDefense(defense);
            defense.setPlayer(player);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            player.setProficiencies((CharacterProficiencies) readObject(path, "proficiencies"));
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            CharacterInventory inventory = (CharacterInventory) readObject(path, "inventory");
            player.setInventory(inventory);
            inventory.setPlayer(player);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            EffectManager effects = (EffectManager) readObject(path, "effects");
            player.setEffectManager(effects);
            effects.setPlayer(player);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            CharacterSkills skills = (CharacterSkills) readObject(path, "skills");
            player.setSkills(skills);
            skills.setPlayer(player);
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return player;
    }

    /**
     * Allows a user to select a item from the item directory, and then
     * returns the selected item.
     *
     * @return the selected item, or null if no item is selected
     */
    public static Item userSelectItem() {
        //Allow the user to choose the item file.
        File file = chooseFile(ITEM_PATH, "item");
        if (file == null) {
            return null;
        }
        try {
            //Attempt to deserialize the item to return it.
            return (Item) readObject(ITEM_PATH, file.getName());
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
        try {
            //Attempt to deserialize the spell to return it.
            return (Spell) readObject(SPELL_PATH, file.getName());
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
     * Writes the passed item to the preset item directory.
     *
     * @param item
     */
    public static void writeItem(Item item) {
        writeObject(item, ITEM_PATH, item.getName() + ".item");
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
    private static Object readObject(String path, String fileName) throws ClassNotFoundException, IOException {

        try {
            Object result;
            try (ObjectInputStream iStream = new ObjectInputStream(new FileInputStream(path + fileName))) {
                result = iStream.readObject();
                return result;
            }

        } catch (ClassNotFoundException | IOException ex) {
            throw ex;
        }
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

    public static MutableCharacterClass[] readClasses() {
        File[] listFiles = new File(CLASS_PATH).listFiles();
        if (listFiles == null) {
            System.err.println("Can't find path to class files.");
        }
        MutableCharacterClass[] classes = new MutableCharacterClass[listFiles.length];
        for (int i = 0; i < classes.length; i++) {
            try {
                classes[i] = (MutableCharacterClass) readObject(CLASS_PATH, listFiles[i].getName());
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return classes;
    }

    public static MutableCharacterClass readClass(String className) {
        try {
            return (MutableCharacterClass) readObject(CLASS_PATH, className + ".class");
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(FileManipulator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}