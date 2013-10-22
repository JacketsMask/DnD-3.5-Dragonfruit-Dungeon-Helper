package gui.spellcrafter;

/**
 * Holds the spell availability information for a given class and the level at
 * which the spell can be used.
 *
 * @author Jacob Dorman
 */
public class ClassAvailability
{

    private String className;
    private int classLevel;

    public ClassAvailability(String className, int classLevel)
    {
        this.className = className;
        this.classLevel = classLevel;
    }

    public int getClassLevel()
    {
        return classLevel;
    }

    public String getClassName()
    {
        return className;
    }

    @Override
    public String toString()
    {
        return className + " [" + classLevel + "]";
    }
}
