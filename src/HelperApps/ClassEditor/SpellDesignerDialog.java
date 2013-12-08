package HelperApps.ClassEditor;

import character.IntegerVerifier;
import character.Spell;
import character.Spell.School;
import file.manipulation.FileManipulator;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * A JDialog for customizing the information for a spell.
 * @author Japhez
 */
public class SpellDesignerDialog extends javax.swing.JDialog {

    private Spell newSpell;

    public SpellDesignerDialog(java.awt.Frame parent, boolean modal, Spell existingSpell) {
        super(parent, modal);
        initComponents();
        initSchoolComboBox();
        //If an existing spell is passed in, fill with that data
        if (existingSpell != null) {
            loadSpellInformation(existingSpell);
        }
    }

    /**
     * @return the spell that has been created or modified
     */
    public Spell getSpell() {
        return newSpell;
    }

    /**
     * Loads the information from the passed spell.
     *
     * @param spell the spell to load information from
     */
    private void loadSpellInformation(Spell spell) {
        nameTextField.setText(spell.getName());
        levelTextField.setText("" + spell.getLevel());
        schoolComboBox.setSelectedItem(spell.getSchool().toString());
        verbalCheckBox.setSelected(spell.isVerbal());
        xpCheckBox.setSelected(spell.getExperienceCost() != 0);
        xpTextField.setText("" + spell.getExperienceCost());
        somaticCheckBox.setSelected(spell.isSomatic());
        gpCheckBox.setSelected(spell.getGoldCost() != 0);
        gpTextField.setText("" + spell.getGoldCost());
        focusCheckBox.setSelected(spell.isFocus());
        descriptionTextArea.setText(spell.getDescription());
        castingTimeTextField.setText(spell.getCastingTime());
        targetTextField.setText(spell.getTarget());
        durationTextField.setText(spell.getDuration());
        savingThrowTextField.setText(spell.getSavingThrow());
        resistanceTextField.setText(spell.getSpellResistance());
    }

    /**
     * Fill the school combo box with all currently known schools.
     */
    private void initSchoolComboBox() {
        schoolComboBox.removeAllItems();
        School[] schools = Spell.School.getSchools();
        for (School s : schools) {
            schoolComboBox.addItem(s);
        }
    }

    /**
     * Resets all the input fields to allow for a new spell to be created.
     */
    private void resetInputFields() {
        nameTextField.setText("");
        levelTextField.setText("");
        referenceTextField.setText("");
        pageTextField.setText("");
        schoolComboBox.setSelectedIndex(0);
        verbalCheckBox.setSelected(false);
        somaticCheckBox.setSelected(false);
        focusCheckBox.setSelected(false);
        xpCheckBox.setSelected(false);
        gpCheckBox.setSelected(false);
        xpTextField.setText("");
        gpTextField.setText("");
        typeButtonGroup.clearSelection();
        castingTimeTextField.setText("");
        rangeTextField.setText("");
        targetTextField.setText("");
        effectTextField.setText("");
        durationTextField.setText("");
        savingThrowTextField.setText("");
        resistanceTextField.setText("");
        descriptionTextArea.setText("");
        numberOfDiceTextField.setText("0");
        numberOfSidesTextField.setText("0");
    }

    /**
     * Attempts to create a spell using the current values in the GUI.
     * Returns null if something went wrong.
     * 
     * @return a new spell or null
     */
    private Spell createSpell() {
        //Don't allow unnamed spells
        if (nameTextField.getText().equals("")) {
            return null;
        }
        String name = nameTextField.getText();
        Spell spell = new Spell(name);
        spell.setLevel(Integer.parseInt(levelTextField.getText()));
        spell.setReference(referenceTextField.getText());
        spell.setDescription(descriptionTextArea.getText());
        spell.setPage(Integer.parseInt(pageTextField.getText()));
        spell.setSchool((School) schoolComboBox.getSelectedItem());
        spell.setVerbal(verbalCheckBox.isSelected());
        spell.setSomatic(somaticCheckBox.isSelected());
        spell.setFocus(focusCheckBox.isSelected());
        if (xpCheckBox.isSelected()) {
            spell.setExperienceCost(Integer.parseInt(xpTextField.getText()));
        } else {
            spell.setExperienceCost(0);
        }
        if (gpCheckBox.isSelected()) {
            spell.setGoldCost((Integer.parseInt(gpTextField.getText())));
        } else {
            spell.setGoldCost(0);
        }

        spell.setCastingTime(castingTimeTextField.getText());
        spell.setRange(rangeTextField.getText());
        spell.setTarget(targetTextField.getText());
        spell.setEffect(effectTextField.getText());
        spell.setDuration(durationTextField.getText());
        spell.setSavingThrow(savingThrowTextField.getText());
        spell.setSpellResistance(resistanceTextField.getText());
        //Get dice roll information
        spell.setDiceNumber(Integer.parseInt(numberOfDiceTextField.getText()));
        spell.setDiceSides(Integer.parseInt(numberOfSidesTextField.getText()));
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

        typeButtonGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        schoolComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        verbalCheckBox = new javax.swing.JCheckBox();
        somaticCheckBox = new javax.swing.JCheckBox();
        focusCheckBox = new javax.swing.JCheckBox();
        xpCheckBox = new javax.swing.JCheckBox();
        xpTextField = new javax.swing.JTextField();
        gpCheckBox = new javax.swing.JCheckBox();
        gpTextField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        referenceTextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        pageTextField = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jLabel17 = new javax.swing.JLabel();
        levelTextField = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptionTextArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        castingTimeTextField = new javax.swing.JTextField();
        targetTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        durationTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        savingThrowTextField = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        resistanceTextField = new javax.swing.JTextField();
        rangeTextField = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        effectTextField = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        resetSpellInfoButton = new javax.swing.JButton();
        saveSpellButton = new javax.swing.JButton();
        importSpellButton = new javax.swing.JButton();
        exportSpellButton = new javax.swing.JToggleButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        numberOfDiceTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        numberOfSidesTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Spell creation");
        setLocationByPlatform(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Spell name:");

        jLabel2.setText("Spell school:");

        schoolComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Required components:");

        verbalCheckBox.setText("Verbal");

        somaticCheckBox.setText("Somatic");

        focusCheckBox.setText("Focus");

        xpCheckBox.setText("XP");
        xpCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xpCheckBoxActionPerformed(evt);
            }
        });

        xpTextField.setEnabled(false);
        xpTextField.setInputVerifier(new IntegerVerifier(0,100000));

        gpCheckBox.setText("GP");
        gpCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gpCheckBoxActionPerformed(evt);
            }
        });

        gpTextField.setEnabled(false);
        gpTextField.setInputVerifier(new IntegerVerifier(0,100000));

        jLabel15.setText("Reference:");

        jLabel16.setText("Page:");

        pageTextField.setColumns(5);
        pageTextField.setText("0");
        pageTextField.setInputVerifier(new IntegerVerifier(0,9999));

        typeButtonGroup.add(jRadioButton1);
        jRadioButton1.setText("Offensive");

        jLabel4.setText("Spell type:");

        typeButtonGroup.add(jRadioButton2);
        jRadioButton2.setText("Defensive");

        typeButtonGroup.add(jRadioButton3);
        jRadioButton3.setText("Utility");

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel17.setText("Spell level:");

        levelTextField.setColumns(5);
        levelTextField.setText("0");
        levelTextField.setInputVerifier(new IntegerVerifier(0,9));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(nameTextField))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel16)))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(referenceTextField)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton3)
                            .addComponent(jRadioButton2)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(verbalCheckBox)
                                .addGap(8, 8, 8)
                                .addComponent(xpCheckBox)
                                .addGap(4, 4, 4)
                                .addComponent(xpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(focusCheckBox)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(somaticCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gpCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(gpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jRadioButton1)
                            .addComponent(jLabel4)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addGap(23, 23, 23)
                                .addComponent(levelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(schoolComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 16, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(levelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(referenceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(pageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(schoolComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(verbalCheckBox)
                    .addComponent(xpCheckBox)
                    .addComponent(xpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(somaticCheckBox)
                    .addComponent(gpCheckBox)
                    .addComponent(gpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(focusCheckBox)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("Spell description:");

        descriptionTextArea.setColumns(20);
        descriptionTextArea.setLineWrap(true);
        descriptionTextArea.setRows(5);
        descriptionTextArea.setWrapStyleWord(true);
        jScrollPane2.setViewportView(descriptionTextArea);

        jLabel6.setText("Casting time:");

        jLabel7.setText("Target/Area:");

        jLabel8.setText("Duration:");

        jLabel9.setText("Saving throw:");

        jLabel10.setText("Spell resistance:");

        jLabel18.setText("Range:");

        jLabel19.setText("Effect:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(resistanceTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
                            .addComponent(savingThrowTextField)
                            .addComponent(durationTextField, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel19)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(effectTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(targetTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel18)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rangeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(33, 33, 33)
                            .addComponent(castingTimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(castingTimeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rangeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(targetTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(effectTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(durationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(savingThrowTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resistanceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel12.setText("<html>Fill in whatever information about the spell that you'd like.<br>  I recommend at least filling in the spell name, level, and description, but only the spell name and level are strictly necessary.</html>");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        resetSpellInfoButton.setText("Reset spell info");
        resetSpellInfoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetSpellInfoButtonActionPerformed(evt);
            }
        });

        saveSpellButton.setText("Save spell");
        saveSpellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveSpellButtonActionPerformed(evt);
            }
        });

        importSpellButton.setText("Import Spell");
        importSpellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importSpellButtonActionPerformed(evt);
            }
        });

        exportSpellButton.setText("Export Spell");
        exportSpellButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportSpellButtonActionPerformed(evt);
            }
        });

        jLabel14.setText("Number of sides:");

        jLabel13.setText("Number of dice:");

        numberOfDiceTextField.setText("0");
        numberOfDiceTextField.setInputVerifier(new IntegerVerifier(1,100));

        jLabel11.setText("Base roll without modifiers:");

        numberOfSidesTextField.setText("0");
        numberOfSidesTextField.setInputVerifier(new IntegerVerifier(1,100));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(resetSpellInfoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(saveSpellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(importSpellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(exportSpellButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(numberOfSidesTextField)
                            .addComponent(numberOfDiceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel13)
                    .addComponent(numberOfDiceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(numberOfSidesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveSpellButton)
                    .addComponent(importSpellButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetSpellInfoButton)
                    .addComponent(exportSpellButton))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void xpCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xpCheckBoxActionPerformed
        if (xpCheckBox.isSelected()) {
            xpTextField.setEnabled(true);
        } else {
            xpTextField.setEnabled(false);
        }
    }//GEN-LAST:event_xpCheckBoxActionPerformed

    private void gpCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gpCheckBoxActionPerformed
        if (gpCheckBox.isSelected()) {
            gpTextField.setEnabled(true);
        } else {
            gpTextField.setEnabled(false);
        }
    }//GEN-LAST:event_gpCheckBoxActionPerformed

    private void saveSpellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveSpellButtonActionPerformed
        this.newSpell = createSpell();
        this.setVisible(false);
    }//GEN-LAST:event_saveSpellButtonActionPerformed

    private void resetSpellInfoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetSpellInfoButtonActionPerformed
        resetInputFields();
    }//GEN-LAST:event_resetSpellInfoButtonActionPerformed

    private void importSpellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importSpellButtonActionPerformed
        Spell userSelectSpell = FileManipulator.userSelectSpell();
        if (userSelectSpell != null) {
            loadSpellInformation(userSelectSpell);
        }
    }//GEN-LAST:event_importSpellButtonActionPerformed

    private void exportSpellButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportSpellButtonActionPerformed
        Spell createSpell = createSpell();
        if (createSpell == null) {
            JOptionPane.showMessageDialog(this, "Invalid information.  Did you fill the bolded fields in?", "Export", JOptionPane.ERROR_MESSAGE);
        } else {
            FileManipulator.writeSpell(createSpell);
            JOptionPane.showMessageDialog(this, "Export successful!", "Export", JOptionPane.PLAIN_MESSAGE);
        }
    }//GEN-LAST:event_exportSpellButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SpellDesignerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SpellDesignerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SpellDesignerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SpellDesignerDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                SpellDesignerDialog dialog = new SpellDesignerDialog(new javax.swing.JFrame(), true, null);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField castingTimeTextField;
    private javax.swing.JTextArea descriptionTextArea;
    private javax.swing.JTextField durationTextField;
    private javax.swing.JTextField effectTextField;
    private javax.swing.JToggleButton exportSpellButton;
    private javax.swing.JCheckBox focusCheckBox;
    private javax.swing.JCheckBox gpCheckBox;
    private javax.swing.JTextField gpTextField;
    private javax.swing.JButton importSpellButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField levelTextField;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JTextField numberOfDiceTextField;
    private javax.swing.JTextField numberOfSidesTextField;
    private javax.swing.JTextField pageTextField;
    private javax.swing.JTextField rangeTextField;
    private javax.swing.JTextField referenceTextField;
    private javax.swing.JButton resetSpellInfoButton;
    private javax.swing.JTextField resistanceTextField;
    private javax.swing.JButton saveSpellButton;
    private javax.swing.JTextField savingThrowTextField;
    private javax.swing.JComboBox schoolComboBox;
    private javax.swing.JCheckBox somaticCheckBox;
    private javax.swing.JTextField targetTextField;
    private javax.swing.ButtonGroup typeButtonGroup;
    private javax.swing.JCheckBox verbalCheckBox;
    private javax.swing.JCheckBox xpCheckBox;
    private javax.swing.JTextField xpTextField;
    // End of variables declaration//GEN-END:variables
}
