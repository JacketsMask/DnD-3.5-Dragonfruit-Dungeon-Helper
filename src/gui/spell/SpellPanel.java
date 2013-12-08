package gui.spell;

import character.IntegerVerifier;
import character.Player;
import character.Spell;
import character.classes.CharacterClass;
import enumerations.CasterType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import savestate.DataRetrievalManager;
import savestate.SaveStateReader;

/**
 * Displays spell information for this class.
 * @author Japhez
 */
public class SpellPanel extends javax.swing.JPanel implements SaveStateReader {

    private Player player;
    private CharacterClass cc;
    //Level, Panel
    private HashMap<Integer, SpellByLevelPanel> spellPanels;

    /**
     * Creates new form SpellPanel
     */
    public SpellPanel(Player player, CharacterClass cc) {
        this.player = player;
        this.cc = cc;
        spellPanels = new HashMap<>();
        initComponents();
        preparedSpellsList.setModel(new DefaultListModel());
        initSpellLists();
        //Innate casters don't learn spells, they are granted
        if (cc.getCasterType().equals(CasterType.INNATE)) {
            System.out.println("INNATE CASTER NO LEARN");
            learnSpellsButton.setVisible(false);
            unlearnSpellsButton.setVisible(false);
            this.validate();
        }
        DataRetrievalManager.linkReader(this, player.getClassInfo());
    }

    private void initPreparedSpellList() {
        DefaultListModel model = (DefaultListModel) preparedSpellsList.getModel();
        model.clear();
        //TODO: Implement this in class, who cares about Strings?
        HashMap<Integer, ArrayList<String>> preparedSpells = player.getClassInfo().getPreparedSpells(cc);
        Set<Integer> keySet = preparedSpells.keySet();
        for (Integer i : keySet) {
            ArrayList<String> spellNames = preparedSpells.get(i);
            for (String s : spellNames) {
                Spell spell = cc.getSpellList().getSpell(i, s);
                model.addElement(spell);
            }
        }
    }

    /**
     * Attempts to load in spell data from the class and create a tab for each
     * level of spells.
     */
    private void initSpellLists() {
        //Clear tabbed pane
        spellsByLevelTabbedPane.removeAll();
        HashMap<Integer, ArrayList<Spell>> knownSpells = player.getClassInfo().getKnownSpells(cc);
        //Find out what level spells can be cast at the current class level
        int spellCasterLevel = player.getClassInfo().getSpellCasterLevel(cc);
        //Create a tab each level's spells for this class
        for (int i = 0; i <= spellCasterLevel; i++) {
            ArrayList<Spell> spellsAtLevel = knownSpells.get(i);
            SpellByLevelPanel panel = new SpellByLevelPanel(i, spellsAtLevel);
            spellsByLevelTabbedPane.addTab("" + i, panel);
            spellPanels.put(i, panel);
        }
    }

    /**
     * Adds the passed spell to the appropriate spell list.
     * @param spell 
     */
    private void addSpellToList(Spell spell) {
        JList spellList = spellPanels.get(spell.getLevel()).getSpellList();
        DefaultListModel model = (DefaultListModel) spellList.getModel();
        model.addElement(spell);
    }

    /**
     * Removes the passed spell from the appropriate spell list.
     * @param spell 
     */
    private void removeFromSpellList(Spell spell) {
        JList spellList = spellPanels.get(spell.getLevel()).getSpellList();
        DefaultListModel model = (DefaultListModel) spellList.getModel();
        model.removeElement(spell);
    }

    /**
     * @param spell
     * @return true if the passed spell is already in a spell list
     */
    private boolean spellListed(Spell spell) {
        JList spellList = spellPanels.get(spell.getLevel()).getSpellList();
        DefaultListModel model = (DefaultListModel) spellList.getModel();
        for (Object s : model.toArray()) {
            if (s.equals(spell)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void loadInfo() {
        if (DataRetrievalManager.isDataChanged(player.getClassInfo(), this)) {
            initSpellLists();
            DataRetrievalManager.dataRead(player.getClassInfo(), this);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainTabbedPane = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        learnSpellsButton = new javax.swing.JButton();
        prepareSpellButton = new javax.swing.JButton();
        castSpellButton = new javax.swing.JButton();
        unlearnSpellsButton = new javax.swing.JButton();
        spellsByLevelTabbedPane = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        spellScrollPane1 = new javax.swing.JScrollPane();
        preparedSpellsList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        uselessLabel781 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        spellDescriptionTextArea = new javax.swing.JTextArea();

        setName("Class Spells"); // NOI18N

        learnSpellsButton.setText("Learn A Spell");
        learnSpellsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                learnSpellsButtonActionPerformed(evt);
            }
        });

        prepareSpellButton.setText("Prepare Spell");
        prepareSpellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prepareSpellButtonActionPerformed(evt);
            }
        });

        castSpellButton.setText("Cast Spell");
        castSpellButton.setEnabled(false);

        unlearnSpellsButton.setText("Unlearn Spell");
        unlearnSpellsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unlearnSpellsButtonActionPerformed(evt);
            }
        });

        spellsByLevelTabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(spellsByLevelTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(learnSpellsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(castSpellButton, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(unlearnSpellsButton, javax.swing.GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
                    .addComponent(prepareSpellButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spellsByLevelTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(castSpellButton)
                        .addGap(18, 18, 18)
                        .addComponent(prepareSpellButton)
                        .addGap(18, 18, 18)
                        .addComponent(learnSpellsButton)
                        .addGap(18, 18, 18)
                        .addComponent(unlearnSpellsButton)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        mainTabbedPane.addTab("Known Spells", jPanel1);

        preparedSpellsList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                preparedSpellsListValueChanged(evt);
            }
        });
        spellScrollPane1.setViewportView(preparedSpellsList);

        jLabel1.setText("Prepared spells:");

        jButton1.setText("Cast Spell");
        jButton1.setEnabled(false);

        jButton2.setText("Un-prepare Spell");

        uselessLabel781.setText("Selected spell info:");

        spellDescriptionTextArea.setEditable(false);
        spellDescriptionTextArea.setColumns(20);
        spellDescriptionTextArea.setLineWrap(true);
        spellDescriptionTextArea.setRows(5);
        spellDescriptionTextArea.setWrapStyleWord(true);
        spellDescriptionTextArea.setAutoscrolls(false);
        spellDescriptionTextArea.setBorder(null);
        jScrollPane6.setViewportView(spellDescriptionTextArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2))
                    .addComponent(spellScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uselessLabel781))
                .addContainerGap(98, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(spellScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(uselessLabel781)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        mainTabbedPane.addTab("Prepared Spells", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainTabbedPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void unlearnSpellsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unlearnSpellsButtonActionPerformed
        SpellByLevelPanel panel = (SpellByLevelPanel) spellsByLevelTabbedPane.getSelectedComponent();
        Spell spell = panel.getSelectedSpell();
        player.getClassInfo().unlearnSpell(cc, spell);
        removeFromSpellList(spell);
    }//GEN-LAST:event_unlearnSpellsButtonActionPerformed

    private void learnSpellsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_learnSpellsButtonActionPerformed
        //List class spells
        cc.getSpellList();
        LearnSpellDialog learnSpellDialog = new LearnSpellDialog(cc);
        learnSpellDialog.setVisible(true);
        Spell spell = learnSpellDialog.getSpell();
        //If the spell isn't null and it isn't already in the list
        if (spell != null && !spellListed(spell)) {
            //Learn the spell
            player.getClassInfo().learnSpell(cc, spell);
            //Add the spell to the appropriate list
            addSpellToList(spell);
        }
    }//GEN-LAST:event_learnSpellsButtonActionPerformed

    private void prepareSpellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prepareSpellButtonActionPerformed
        SpellByLevelPanel panel = (SpellByLevelPanel) spellsByLevelTabbedPane.getSelectedComponent();
        Spell spell = panel.getSelectedSpell();
        if (spell != null) {
            player.getClassInfo().prepareSpell(cc, spell);
            initPreparedSpellList();
        }
    }//GEN-LAST:event_prepareSpellButtonActionPerformed

    private void preparedSpellsListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_preparedSpellsListValueChanged
        if (!preparedSpellsList.isSelectionEmpty()) {
            Spell spell = (Spell) preparedSpellsList.getSelectedValue();
            spellDescriptionTextArea.setText(spell.getDescription());
        } else {
            spellDescriptionTextArea.setText("");
        }
    }//GEN-LAST:event_preparedSpellsListValueChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton castSpellButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JButton learnSpellsButton;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JButton prepareSpellButton;
    private javax.swing.JList preparedSpellsList;
    private javax.swing.JTextArea spellDescriptionTextArea;
    private javax.swing.JScrollPane spellScrollPane1;
    private javax.swing.JTabbedPane spellsByLevelTabbedPane;
    private javax.swing.JButton unlearnSpellsButton;
    private javax.swing.JLabel uselessLabel781;
    // End of variables declaration//GEN-END:variables
}
