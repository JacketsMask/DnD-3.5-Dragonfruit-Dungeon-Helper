package gui.spell;

import character.Spell;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * An object of this panel should be generated for every level of spell that a 
 * class can cast.
 * @author Japhez
 */
public class SpellByLevelPanel extends javax.swing.JPanel {

    private ArrayList<Spell> spellArray;
    private DefaultListModel model;

    /** Creates new form SpellByLevelPanel */
    public SpellByLevelPanel(int level, ArrayList<Spell> spellArray) {
        initComponents();
        spellsLevelLabel.setText("Level " + Spell.getSymbol(level) + " spells:");
        model = new DefaultListModel();
        spellList.setModel(model);
        this.spellArray = spellArray;
        initSpellList();
    }

    /**
     * Load spell information from the passed Array, clearing existing data.
     * @param spellArray 
     */
    public void loadSpellData(ArrayList<Spell> spellArray) {
        model.clear();
        this.spellArray = spellArray;
        for (Spell s : spellArray) {
            model.addElement(s);
        }
    }

    public Spell getSelectedSpell() {
        return (Spell) spellList.getSelectedValue();
    }

    private void initSpellList() {
        for (Spell s : spellArray) {
            model.addElement(s);
        }
    }

    public JList getSpellList() {
        return spellList;
    }

    public void reloadSpells() {
        model.clear();
        initSpellList();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        spellList = new javax.swing.JList();
        spellsLevelLabel = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        spellDescriptionTextArea = new javax.swing.JTextArea();
        uselessLabel780 = new javax.swing.JLabel();

        spellList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        spellList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        spellList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                spellListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(spellList);

        spellsLevelLabel.setText("[level # spells]");

        spellDescriptionTextArea.setEditable(false);
        spellDescriptionTextArea.setColumns(20);
        spellDescriptionTextArea.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        spellDescriptionTextArea.setLineWrap(true);
        spellDescriptionTextArea.setRows(5);
        spellDescriptionTextArea.setWrapStyleWord(true);
        spellDescriptionTextArea.setAutoscrolls(false);
        spellDescriptionTextArea.setBorder(null);
        jScrollPane5.setViewportView(spellDescriptionTextArea);

        uselessLabel780.setText("Selected spell info:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spellsLevelLabel)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(uselessLabel780)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spellsLevelLabel)
                    .addComponent(uselessLabel780))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void spellListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_spellListValueChanged
        if (!spellList.isSelectionEmpty()) {
            Spell spell = (Spell) spellList.getSelectedValue();
            spellDescriptionTextArea.setText(spell.getDescription());
        } else {
            spellDescriptionTextArea.setText("");
        }
    }//GEN-LAST:event_spellListValueChanged
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTextArea spellDescriptionTextArea;
    private javax.swing.JList spellList;
    private javax.swing.JLabel spellsLevelLabel;
    private javax.swing.JLabel uselessLabel780;
    // End of variables declaration//GEN-END:variables
}
