package interfaces;

/**
 * This interface is for extended JPanels that need to retrieve character
 * information when they are selected. A StateChanged listener on the base
 * JTabbedPane will call this method when the panel gets focus.
 *
 * This will allow panels to easily reload data when they are selected.
 *
 * @author Japhez
 */
public interface CharacterInfoRetriever {

    public void loadInfo();
}
