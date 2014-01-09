package character;

import character.feats.Feat;
import java.util.ArrayList;

/**
 * Stores feats that a character owns.
 *
 * @author Japhez
 */
public class CharacterFeats {

    private ArrayList<Feat> feats;

    public CharacterFeats() {
        feats = new ArrayList<>();
    }

    public void addFeat(Feat feat) {
        feats.add(feat);
    }

    public void removeFeat(Feat feat) {
        feats.remove(feat);
    }
}
