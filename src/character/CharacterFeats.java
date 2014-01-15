package character;

import character.feats.Feat;
import file.manipulation.FileManipulator;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import savestate.StateSender;

/**
 * Stores feats that a character owns.
 *
 * @author Japhez
 */
public class CharacterFeats extends StateSender implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<String> featsNames;
    private transient ArrayList<Feat> feats;

    public CharacterFeats() {
        featsNames = new ArrayList<>();
    }
    
    public ArrayList<Feat> getFeats() {
        return feats;
    }

    /**
     * Attempts to read in feats known by this character from files.
     */
    public void loadFeats() {
        ArrayList<Feat> results = new ArrayList<>();
        for (String s : featsNames) {
            Feat feat = FileManipulator.readFeat(s);
            if (feat != null) {
                results.add(feat);
            } else {
                JOptionPane.showMessageDialog(null, "Unable to load feat \"" + s + "\".");
            }
        }
        feats = results;
    }

    public void addFeat(Feat feat) {
        featsNames.add(feat.getName());
        feats.add(feat);
        super.stateChanged();
    }

    public void removeFeat(Feat feat) {
        featsNames.remove(feat.getName());
        feats.add(feat);
        super.stateChanged();
    }
}
