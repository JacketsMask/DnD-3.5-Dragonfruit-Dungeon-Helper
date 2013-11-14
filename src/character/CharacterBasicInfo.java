package character;

import abstracts.Size;
import character.races.Race;
import enumerations.Alignment;
import enumerations.Gender;
import main.SaveStateTracker;
import java.io.Serializable;
import java.util.Random;

/**
 * Holds basic character information for an entity.
 *
 * @author Jacob Dorman
 */
public class CharacterBasicInfo extends SaveStateTracker implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String[] FIRST_NAMES = {
        "Xavier", "The", "Winged", "Cowardly", "Subpar"
    };
    private final String[] LAST_NAMES = {
        "Wrathlord", "Dudemaster", "Duck", "Mouseman", "Man"
    };
    private String name;
    private Gender gender;
    private String deity;
    private Alignment alignment;
    private Size size;
    private int age;
    private Race race;
    private int speed;
    private int weight;
    private int heightFeet;
    private int heightInches;
    private String eyeColor;
    private String hairColor;
    private String skinColor;

    public CharacterBasicInfo() {
        super();
        setDefaultValues();
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHeightFeet() {
        return heightFeet;
    }

    public void setHeightFeet(int heightFeet) {
        this.heightFeet = heightFeet;
        super.stateChanged = true;
    }

    public int getHeightInches() {
        return heightInches;
    }

    public void setHeightInches(int heightInches) {
        this.heightInches = heightInches;
        super.stateChanged = true;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
        super.stateChanged = true;
    }

    public void setWeight(int weight) {
        this.weight = weight;
        super.stateChanged = true;
    }

    public int getWeight() {
        return weight;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
        super.stateChanged = true;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
        super.stateChanged = true;
    }

    /**
     * Sets the race of this player. To be used at character creation.
     *
     * @param newRace the race of this player
     */
    public void setRace(Race newRace) {
        race = newRace;
        super.stateChanged = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        super.stateChanged = true;
    }

    public Race getRace() {
        return race;
    }

    public Size getSize() {
        return size;
    }

    public Gender getGender() {
        return gender;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public String getDeity() {
        return deity;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        super.stateChanged = true;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
        super.stateChanged = true;

    }

    public void setDeity(String deity) {
        this.deity = deity;
        super.stateChanged = true;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
        super.stateChanged = true;

    }

    public void setSize(Size size) {
        this.size = size;
        super.stateChanged = true;
    }

    /**
     * Sets the player information to the default values.
     */
    private void setDefaultValues() {
        Random rand = new Random();
        name = FIRST_NAMES[rand.nextInt(FIRST_NAMES.length)] + " " + LAST_NAMES[rand.nextInt(LAST_NAMES.length)];
        gender = Gender.MALE;
        race = new Race.Human();
        size = race.getSize();
        speed = race.getSpeed();
        deity = "Atheist";
        alignment = Alignment.TRUE_NEUTRAL;
        age = 21;
        heightFeet = 5;
        heightInches = 5;
        weight = 150;
        eyeColor = "Auburn";
        hairColor = "Brown";
        skinColor = "Tan";
        super.stateChanged = true;
    }
}
