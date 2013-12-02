/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.spell;

import character.Spell;
import character.classes.CharacterClass;
import character.classes.ClassSpellList;
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jacob
 */
public class LearnSpellDialog extends javax.swing.JDialog {

    private HashMap<Integer, SpellByLevelPanel> spellPanels;
    private CharacterClass cc;
    private Spell spell;

    /** Creates new form LearnSpellDialog */
    public LearnSpellDialog(CharacterClass cc) {
        this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        this.cc = cc;
        this.spellPanels = new HashMap<>();
        initComponents();
        initSpellLists();
    }

    /**
     * Attempts to load in spell data from the class and create a tab for each
     * level of spells.
     */
    private void initSpellLists() {
        ClassSpellList spellList = cc.getSpellList();
        //Create a tab each level's spells for this class
        for (int i = 0; i <= 20; i++) {
            ArrayList<Spell> spellsAtLevel = spellList.getSpellsAtLevel(i);
            SpellByLevelPanel panel = new SpellByLevelPanel(i, spellsAtLevel);
            spellsByLevelTabbedPane.addTab("" + i, panel);
            spellPanels.put(i, panel);
        }
    }

    public Spell getSpell() {
        return spell;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        learnSpellButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        spellsByLevelTabbedPane = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        learnSpellButton.setText("Learn Spell");
        learnSpellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                learnSpellButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spellsByLevelTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(learnSpellButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spellsByLevelTabbedPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(learnSpellButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelButton)
                        .addGap(0, 288, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void learnSpellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_learnSpellButtonActionPerformed
        //Get currently active tab
        SpellByLevelPanel panel = (SpellByLevelPanel) spellsByLevelTabbedPane.getSelectedComponent();
        spell = panel.getSelectedSpell();
        this.setVisible(false);
    }//GEN-LAST:event_learnSpellButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton learnSpellButton;
    private javax.swing.JTabbedPane spellsByLevelTabbedPane;
    // End of variables declaration//GEN-END:variables
}
