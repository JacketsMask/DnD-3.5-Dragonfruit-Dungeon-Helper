package character;

import character.classes.CharacterClass;
import character.classes.ClassCustom;
import interfaces.SaveStateTracker;
import java.util.ArrayList;

/**
 * Holds information regarding character classes. This exists because of the
 * desire for multi-class support down the line.
 *
 * @author Jacob Dorman
 */
public class CharacterClassInfo extends SaveStateTracker
{
    
    private ArrayList<CharacterClass> list;

    /**
     * Creates a object with a default class of Commoner.
     */
    public CharacterClassInfo()
    {
        super();
        list = new ArrayList<>();
        list.add(new ClassCustom("Commoner"));
    }

    /**
     * @return the first class of this character if it is defined, otherwise
     * null
     */
    public CharacterClass getInitialClass()
    {
        if (list.isEmpty())
        {
            return null;
        }
        return list.get(0);
    }

    /**
     * Removes all existing class data and sets the passed class as the new
     * initial class.
     *
     * @param cClass the new initial class
     */
    public void setClass(CharacterClass cClass)
    {
        list = new ArrayList<>();
        list.add(cClass);
        super.stateChanged = true;
    }

    /**
     * Adds the passed class to the list of classes. This represents the
     * multi-classing process.
     *
     * @param cClass the class to add
     */
    public void addClass(CharacterClass cClass)
    {
        list.add(cClass);
        super.stateChanged = true;
    }
}
