package character.races;

import java.io.Serializable;

/**
 *
 * @author Japhez
 */
public class CustomRace extends Race implements Serializable {

    private static final long serialVersionUID = 1L;

    public CustomRace(String name) {
        super();
        this.name = name;
    }
}
