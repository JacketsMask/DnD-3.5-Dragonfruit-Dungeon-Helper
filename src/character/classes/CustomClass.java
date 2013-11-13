package character.classes;

import java.io.Serializable;

/**
 * A custom class. The information here is provided by and customized by the
 * user. This custom character class allows for sharing and customization
 * between users.
 *
 * @author Jacob Dorman
 */
public class CustomClass extends CharacterClass implements Serializable
{

    /**
     * Creates a new custom class with the passed name.
     * @param className 
     */
    public CustomClass(String className) {
        super();
        super.name = className;
    }
}
