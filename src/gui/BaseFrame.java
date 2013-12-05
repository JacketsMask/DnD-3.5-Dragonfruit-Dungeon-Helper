package gui;

import character.CharacterClassInfo;
import gui.classes.AbilityPanel;
import gui.skills.SkillsPanel;
import gui.proficiency.ProficiencyPanel;
import gui.basicinfo.BasicInfoPanel;
import gui.inventory.InventoryPanel;
import gui.abilityscore.AbilityScorePanel;
import gui.defense.DefensePanel;
import character.Player;
import character.classes.CharacterClass;
import enumerations.CasterType;
import gui.chat.ChatPanel;
import gui.classes.GeneralClassPanel;
import gui.inventory.GeneralInventoryPanel;
import gui.inventory.WalletPanel;
import gui.spell.SpellPanel;
import savestate.SaveStateReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTabbedPane;

/**
 *
 * @author Jacob Dorman
 */
public final class BaseFrame extends javax.swing.JFrame {

    private Player player;

    public BaseFrame(Player player) throws IOException {
        this.player = player;
        initComponents();
        addPanels();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        characterInfoTabbedPane = new javax.swing.JTabbedPane();
        chatTabbedPane = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("D&D 3.5 Helper");
        setResizable(false);

        characterInfoTabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        characterInfoTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                baseTabbedPaneStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(characterInfoTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                    .addComponent(chatTabbedPane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(characterInfoTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(chatTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Called when a tab from the main JTabbedPane is selected.
     *
     * For panels that implement CharacterInfoRetrieval, update the displayed
     * data whenever the panel is displayed.
     *
     * @param evt
     */
    private void baseTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_baseTabbedPaneStateChanged
        JTabbedPane source = (JTabbedPane) evt.getSource();
        if (source.getSelectedComponent() instanceof SaveStateReader) {
            SaveStateReader retriever = (SaveStateReader) source.getSelectedComponent();
            retriever.loadInfo();
        } //Check to see if there's a tabbed Pane within a tabbed pane
        //TODO: Consider making this a while to jump through any TabbedPanes, rather than just 2 deep
        else if (source.getSelectedComponent() instanceof JTabbedPane) {
            //Get the inner tab
            JTabbedPane innerTab = (JTabbedPane) source.getSelectedComponent();
            //If the inner tab's selected component is a SaveStateReader
            if (innerTab.getSelectedComponent() instanceof SaveStateReader) {
                SaveStateReader retriever = (SaveStateReader) innerTab.getSelectedComponent();
                retriever.loadInfo();
            }
        }
    }//GEN-LAST:event_baseTabbedPaneStateChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane characterInfoTabbedPane;
    private javax.swing.JTabbedPane chatTabbedPane;
    // End of variables declaration//GEN-END:variables

    /**
     * Adds the child panels into the window tabbed pane, and then adds the chat
     * panel below.
     */
    private void addPanels() throws IOException, IOException {
        characterInfoTabbedPane.addTab("General", new BasicInfoPanel(player));
        characterInfoTabbedPane.addTab("Ability Score", new AbilityScorePanel(player));
        CharacterClassInfo classInfo = player.getClassInfo();
        ArrayList<CharacterClass> characterClasses = classInfo.getCharacterClasses();
        for (CharacterClass cc : characterClasses) {
            JTabbedPane classTabs = new JTabbedPane();
            characterInfoTabbedPane.addTab(cc.getName(), classTabs);
            //Add general info panel
            classTabs.addTab("General", new GeneralClassPanel(player, cc));
            if (!cc.getCasterType().equals(CasterType.NON_CASTER)) {
                classTabs.addTab("Spells", new SpellPanel(player, cc));
            }
            if (cc.isAbilityUser()) {
                classTabs.addTab("Abilities", new AbilityPanel(player));
            }
        }
        characterInfoTabbedPane.addTab("Skills", new SkillsPanel(player));
        characterInfoTabbedPane.addTab("Attack", new AttackPanel(player));
        characterInfoTabbedPane.addTab("Defense", new DefensePanel(player));
        characterInfoTabbedPane.addTab("Proficiencies", new ProficiencyPanel(player));
        //Inventory
        JTabbedPane inventoryTabbedPane = new JTabbedPane();
        inventoryTabbedPane.addTab("General", new GeneralInventoryPanel(player));
        inventoryTabbedPane.add("Items", new InventoryPanel(player));
        inventoryTabbedPane.add("Coin Pouch", new WalletPanel(player));
        inventoryTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                baseTabbedPaneStateChanged(evt);
            }
        });
        characterInfoTabbedPane.add("Inventory", inventoryTabbedPane);
        chatTabbedPane.add("All", new ChatPanel(player));
        pack();
    }

    public Player getPlayer() {
        return player;
    }
}
