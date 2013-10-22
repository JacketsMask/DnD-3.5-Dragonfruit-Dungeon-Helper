package character.races;

/**
 * Represents a saving throw modifier for a specific type.
 * @author Jacob Dorman
 */
public class SavingThrowModifier
{

    private SavingThrowType type;
    private int value;

    public SavingThrowModifier(SavingThrowType type, int value)
    {
        this.type = type;
        this.value = value;
    }

    public SavingThrowType getType()
    {
        return type;
    }

    public int getValue()
    {
        return value;
    }
    
    public enum SavingThrowType {
        FORTITUDE, REFLEX, WILL
    }
}
