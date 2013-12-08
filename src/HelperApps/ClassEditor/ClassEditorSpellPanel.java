package HelperApps.ClassEditor;

import gui.spell.*;
import character.Spell;
import character.classes.CharacterClass;
import character.classes.ClassSpellList;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Japhez
 */
public class ClassEditorSpellPanel extends javax.swing.JPanel {
    //Level, Panel

    private HashMap<Integer, SpellByLevelPanel> spellPanels;
    //Spell list to modify or build
    private ClassSpellList spellList;

    public ClassEditorSpellPanel(CharacterClass cc) {
        if (cc == null || cc.getSpellList() == null) {
            spellList = new ClassSpellList();
        } else {
            spellList = cc.getSpellList();
        }
        spellPanels = new HashMap<>();
        initComponents();
        initSpellLists();
    }

    public ClassSpellList getSpellList() {
        return spellList;
    }

    /**
     * Attempts to load in spell data from the class and create a tab for each
     * level of spells.
     */
    private void initSpellLists() {
        //Create a tab each level's spells for this class
        for (int i = 0; i <= 9; i++) {
            ArrayList<Spell> spellsAtLevel = spellList.getSpellsAtLevel(i);
            if (!spellsAtLevel.isEmpty()) {
                System.out.println("loaded: " + spellsAtLevel.get(0));
            }
            SpellByLevelPanel panel = new SpellByLevelPanel(i, spellsAtLevel);
            spellsByLevelTabbedPane.addTab("" + Spell.getSymbol(i), panel);
            spellPanels.put(i, panel);
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

        jPanel1 = new javax.swing.JPanel();
        spellsAddSpellButton = new javax.swing.JButton();
        spellsRemoveSpellbutton = new javax.swing.JButton();
        spellsByLevelTabbedPane = new javax.swing.JTabbedPane();
        modifySpellButton = new javax.swing.JButton();

        setName("Class Spells"); // NOI18N

        spellsAddSpellButton.setText("Add Spell");
        spellsAddSpellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spellsAddSpellButtonActionPerformed(evt);
            }
        });

        spellsRemoveSpellbutton.setText("Remove Spell");
        spellsRemoveSpellbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                spellsRemoveSpellbuttonActionPerformed(evt);
            }
        });

        spellsByLevelTabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        modifySpellButton.setText("Modify Spell");
        modifySpellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifySpellButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spellsByLevelTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(spellsAddSpellButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spellsRemoveSpellbutton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(modifySpellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spellsByLevelTabbedPane)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(spellsAddSpellButton)
                        .addGap(18, 18, 18)
                        .addComponent(modifySpellButton)
                        .addGap(18, 18, 18)
                        .addComponent(spellsRemoveSpellbutton)
                        .addGap(0, 269, Short.MAX_VALUE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void spellsAddSpellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spellsAddSpellButtonActionPerformed
        //Start creating a new spell
        SpellDesignerDialog spellDesignerDialog = new SpellDesignerDialog(null, true, null);
        spellDesignerDialog.setVisible(true);
        //Get the resulting spell
        Spell spell = spellDesignerDialog.getSpell();
        if (spell != null) {
            //Add the spell to the spell list
            spellList.addSpell(spell, spell.getLevel());
            //Reload displayed spell list
            spellPanels.get(spell.getLevel()).reloadSpells();
        }
    }//GEN-LAST:event_spellsAddSpellButtonActionPerformed

    private void spellsRemoveSpellbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_spellsRemoveSpellbuttonActionPerformed
        //Find active tab
        SpellByLevelPanel spellPanel = (SpellByLevelPanel) spellsByLevelTabbedPane.getSelectedComponent();
        if (spellPanel != null) {
            //Make sure a spell is selected
            Spell spell = spellPanel.getSelectedSpell();
            if (spell != null) {
                //Remove the old spell data, and the new spell data
                spellList.removeSpell(spell, spell.getLevel());
                spellPanels.get(spell.getLevel()).reloadSpells();
                //Clear list
                spellPanels.get(spell.getLevel()).getSpellList().clearSelection();
            } else {
                System.out.println("No spell selected.");
            }
        } else {
            System.out.println("No spell panel selected.");
        }
    }//GEN-LAST:event_spellsRemoveSpellbuttonActionPerformed

    private void modifySpellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifySpellButtonActionPerformed
        //Find active tab
        SpellByLevelPanel spellPanel = (SpellByLevelPanel) spellsByLevelTabbedPane.getSelectedComponent();
        if (spellPanel != null) {
            //Make sure a spell is selected
            Spell spell = spellPanel.getSelectedSpell();
            if (spell != null) {
                SpellDesignerDialog spellDesignerDialog = new SpellDesignerDialog(null, true, spell);
                spellDesignerDialog.setVisible(true);
                //Get updated spell info
                Spell newSpell = spellDesignerDialog.getSpell();
                if (newSpell != null) {
                    //Remove the old spell data, and the new spell data
                    spellList.removeSpell(spell, spell.getLevel());
                    spellList.addSpell(newSpell, newSpell.getLevel());
                    spellPanels.get(spell.getLevel()).reloadSpells();
                    //Clear list
                    spellPanels.get(spell.getLevel()).getSpellList().clearSelection();
                }
            } else {
                System.out.println("No spell selected.");
            }
        } else {
            System.out.println("No spell panel selected.");
        }
    }//GEN-LAST:event_modifySpellButtonActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton modifySpellButton;
    private javax.swing.JButton spellsAddSpellButton;
    private javax.swing.JTabbedPane spellsByLevelTabbedPane;
    private javax.swing.JButton spellsRemoveSpellbutton;
    // End of variables declaration//GEN-END:variables
}
