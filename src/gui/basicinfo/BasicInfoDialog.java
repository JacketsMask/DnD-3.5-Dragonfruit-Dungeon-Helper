package gui.basicinfo;

import character.races.CustomRace;
import character.races.Race;
import character.CharacterBasicInfo;
import character.ComponentLockableIntegerVerifier;
import character.Player;
import character.classes.CharacterClass;
import character.classes.CustomClass;
import enumerations.Alignment;
import enumerations.Gender;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

/**
 *
 * @author Japhez
 */
public class BasicInfoDialog extends javax.swing.JDialog {

    private BasicInfoPanel parent;
    private Player player;
    private ArrayList<String> deactivatedOrders;
    private ArrayList<String> deactivatedAlignments;

    /**
     * Creates new form GeneralInformationDialog
     */
    public BasicInfoDialog(BasicInfoPanel parent, boolean modal, Player player) {
        deactivatedOrders = new ArrayList<>();
        deactivatedAlignments = new ArrayList<>();
        this.parent = parent;
        this.player = player;
        initComponents();
        preFillCharacterInformation();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dialogCharacterCommitButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        dialogAgeTextField = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dialogHeightFtTextField = new javax.swing.JTextField();
        dialogWeightTextField = new javax.swing.JTextField();
        dialogEyesTextField = new javax.swing.JTextField();
        dialogHairTextField = new javax.swing.JTextField();
        dialogSkinTextField = new javax.swing.JTextField();
        dialogHeightInTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        dialogRaceComboBox = new javax.swing.JComboBox();
        dialogRaceTextField = new javax.swing.JTextField();
        dialogClassComboBox = new javax.swing.JComboBox();
        dialogClassTextField = new javax.swing.JTextField();
        dialogNameTextField = new javax.swing.JTextField();
        dialogSizeComboBox = new javax.swing.JComboBox();
        dialogDeityTextField = new javax.swing.JTextField();
        dialogOrderComboBox = new javax.swing.JComboBox();
        dialogAlignmentComboBox = new javax.swing.JComboBox();
        dialogGenderComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        dialogCharacterCommitButton.setText("Save Changes");
        dialogCharacterCommitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dialogCharacterCommitButtonActionPerformed(evt);
            }
        });

        dialogAgeTextField.setText("[age]");
        dialogAgeTextField.setInputVerifier(new ComponentLockableIntegerVerifier(1,600, dialogAgeTextField, dialogCharacterCommitButton));

        jLabel43.setText("Age");

        jLabel1.setText("Height (ft):");

        jLabel2.setText("Weight (lbs):");

        jLabel3.setText("Eyes:");

        jLabel4.setText("Hair:");

        jLabel5.setText("Skin:");

        dialogHeightFtTextField.setText("[height]");
        dialogHeightFtTextField.setInputVerifier(new ComponentLockableIntegerVerifier(0,500,dialogHeightFtTextField,dialogCharacterCommitButton));

        dialogWeightTextField.setText("[weight]");
        dialogWeightTextField.setInputVerifier(new ComponentLockableIntegerVerifier(1,500,dialogWeightTextField,dialogCharacterCommitButton));

        dialogEyesTextField.setText("[eyes]");

        dialogHairTextField.setText("[hair]");

        dialogSkinTextField.setText("[skin]");

        dialogHeightInTextField.setText("[height]");
        dialogHeightInTextField.setInputVerifier(new ComponentLockableIntegerVerifier(0,11,dialogHeightInTextField,dialogCharacterCommitButton));

        jLabel6.setText("Height (in):");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel43)
                        .addGap(52, 52, 52)
                        .addComponent(dialogAgeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(48, 48, 48)
                        .addComponent(dialogSkinTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(48, 48, 48)
                        .addComponent(dialogHairTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel6)
                            .addComponent(jLabel1))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dialogHeightFtTextField)
                            .addComponent(dialogHeightInTextField)
                            .addComponent(dialogWeightTextField)
                            .addComponent(dialogEyesTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(dialogAgeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(dialogHeightFtTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(dialogHeightInTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(dialogWeightTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dialogEyesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(dialogHairTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(dialogSkinTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jLabel28.setText("Race");

        jLabel29.setText("Size");

        jLabel27.setText("Starting class");

        jLabel23.setText("Gender");

        jLabel26.setText("Name");
        jLabel26.setToolTipText("The name of your hero.");

        jLabel24.setText("Alignment");

        jLabel30.setText("Patron Deity");

        dialogRaceComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Human", "Other" }));
        dialogRaceComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dialogRaceComboBoxItemStateChanged(evt);
            }
        });

        dialogRaceTextField.setText("[race]");

        dialogClassComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Other" }));
        dialogClassComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                dialogClassComboBoxItemStateChanged(evt);
            }
        });

        dialogClassTextField.setText("[class]");

        dialogNameTextField.setText("[character name]");

        dialogSizeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Fine", "Diminutive", "Tiny", "Small", "Medium", "Large", "Huge", "Gargantuan", "Colossal" }));

        dialogDeityTextField.setText("[deity]");

        dialogOrderComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Lawful", "Neutral", "Chaotic" }));

        dialogAlignmentComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Good", "Neutral", "Evil" }));

        dialogGenderComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Male", "Female" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel27))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dialogRaceComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dialogClassComboBox, 0, 84, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dialogRaceTextField)
                            .addComponent(dialogClassTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel30)
                                .addGap(5, 5, 5)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(dialogOrderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(dialogAlignmentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(dialogDeityTextField)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel29))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dialogSizeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dialogGenderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addGap(19, 19, 19)
                        .addComponent(dialogNameTextField))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(dialogNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(dialogClassComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dialogClassTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(dialogRaceComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dialogRaceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(dialogSizeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(dialogGenderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(dialogOrderComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dialogAlignmentComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(dialogDeityTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(138, 138, 138)
                        .addComponent(dialogCharacterCommitButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(144, 144, 144))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dialogCharacterCommitButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dialogCharacterCommitButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_dialogCharacterCommitButtonActionPerformed
    {//GEN-HEADEREND:event_dialogCharacterCommitButtonActionPerformed
        CharacterClass classInfo = player.getClassInfo().getInitialClass();
        CharacterBasicInfo characterInfo = player.getBasicInfo();
        //If names differ, change name
        if (!characterInfo.getName().equals(dialogNameTextField.getText())) {
            characterInfo.setName(dialogNameTextField.getText());
        }
        //If class differs, change class
        String selectedClass;
        //Check to see if this is an unsupported class
        if (dialogClassComboBox.getSelectedItem().equals("Other")) {
            selectedClass = dialogClassTextField.getText();

        } else {   //Class is supported, get fixed result
            selectedClass = (String) dialogClassComboBox.getSelectedItem();
        }
        //Check to see if the class isn't the same as the player's existing class
        if (classInfo != null && !classInfo.getName().equals(selectedClass)) {
            CustomClass customClass = new CustomClass(selectedClass);
            //Set the character's class to the new custom class
            player.getClassInfo().setClass(customClass);
        }
        //Check to see if this is a supported or unsupported race
        String selectedRace;
        if (dialogRaceComboBox.getSelectedItem().equals("Other")) {
            selectedRace = dialogRaceTextField.getText();

        } else {   //Race is supported, get fixed result
            selectedRace = (String) dialogRaceComboBox.getSelectedItem();
        }
        Race race = player.getBasicInfo().getRace();
        //Check to see if the race isn't the same as the player's existing class
        if (race != null && !race.getName().equals(selectedRace)) {
            CustomRace customRace = new CustomRace(selectedRace);
            //Set the character's class to the new custom class
            player.getBasicInfo().setRace(customRace);
        }
        //Check to see if gender is different
        if (!characterInfo.getGender().toString().equals(dialogGenderComboBox.getSelectedItem().toString())) {
            Gender gender = characterInfo.getGender();
            if (gender.equals(Gender.MALE)) {
                characterInfo.setGender(Gender.FEMALE);
            } else {
                characterInfo.setGender(Gender.MALE);
            }
        }
        //TODO: Check alignment
        //Check to see if deity has changed
        if (!characterInfo.getDeity().equals(dialogDeityTextField.getText())) {
            characterInfo.setDeity(dialogDeityTextField.getText());
        }
        //Check to see if age is different
        if (!dialogAgeTextField.getText().equals("") && !(characterInfo.getAge() == Integer.parseInt(dialogAgeTextField.getText()))) {
            characterInfo.setAge(Integer.parseInt(dialogAgeTextField.getText()));
        }
        //Check to see if height (ft) is different
        if (!(characterInfo.getHeightFeet() == Integer.parseInt(dialogHeightFtTextField.getText()))) {
            characterInfo.setHeightFeet(Integer.parseInt(dialogHeightFtTextField.getText()));
        }
        //Check to see if height (in) is different
        if (!(characterInfo.getHeightInches() == Integer.parseInt(dialogHeightInTextField.getText()))) {
            characterInfo.setHeightInches(Integer.parseInt(dialogHeightInTextField.getText()));
        }
        //Check to see if weight is different
        if (!(characterInfo.getWeight() == Integer.parseInt(dialogWeightTextField.getText()))) {
            characterInfo.setWeight(Integer.parseInt(dialogWeightTextField.getText()));
        }
        //Check to see if eyes are different
        if (!(characterInfo.getEyeColor().equals(dialogEyesTextField.getText()))) {
            characterInfo.setEyeColor(dialogEyesTextField.getText());
        }
        //Check to see if hair is different
        if (!(characterInfo.getHairColor().equals(dialogHairTextField.getText()))) {
            characterInfo.setHairColor(dialogHairTextField.getText());
        }
        //Check to see if skin is different
        if (!(characterInfo.getSkinColor().equals(dialogSkinTextField.getText()))) {
            characterInfo.setSkinColor(dialogSkinTextField.getText());
        }
        parent.loadInfo();
        this.setVisible(false);
    }//GEN-LAST:event_dialogCharacterCommitButtonActionPerformed

    private void dialogRaceComboBoxItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_dialogRaceComboBoxItemStateChanged
    {//GEN-HEADEREND:event_dialogRaceComboBoxItemStateChanged
        //Ignore deselection events
        if (evt.getStateChange() == ItemEvent.DESELECTED) {
            return;
        }
        String selectedItem = (String) dialogRaceComboBox.getSelectedItem();
        if (selectedItem.equals("Other")) {
            dialogRaceTextField.setVisible(true);
            dialogSizeComboBox.setEnabled(true);
            this.getContentPane().validate();
        } else {
            dialogRaceTextField.setVisible(false);
            dialogSizeComboBox.setSelectedItem(player.getBasicInfo().getSize().toString());
            dialogSizeComboBox.setEnabled(false);
            this.setVisible(true);
        }
    }//GEN-LAST:event_dialogRaceComboBoxItemStateChanged

    private void dialogClassComboBoxItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_dialogClassComboBoxItemStateChanged
    {//GEN-HEADEREND:event_dialogClassComboBoxItemStateChanged
        //Ignore deselection events
        if (evt.getStateChange() == ItemEvent.DESELECTED) {
            return;
        }
        updateCharacterOptionsBasedOffClass();
    }//GEN-LAST:event_dialogClassComboBoxItemStateChanged

    /**
     * Fills in character information for the dialog window as read from the
     * player file.
     */
    private void preFillCharacterInformation() {
        CharacterBasicInfo info = player.getBasicInfo();
        //Fill in information about the player's name
        if (info.getName() != null) {
            dialogNameTextField.setText(info.getName());
        }
        //Fill in information about the character's class
        CharacterClass characterClass = player.getClassInfo().getInitialClass();
        if (characterClass == null) {
            dialogClassComboBox.setSelectedItem("Other");
            player.getClassInfo().setClass(new CustomClass("Commoner"));
        } else {
            //The character's class is something else
            dialogClassComboBox.setSelectedItem("Other");
            dialogClassTextField.setVisible(true);
            dialogClassTextField.setText(characterClass.getName());
        }
        //Fill in information about the character's race, and size if race
        //determines that
        boolean raceKnown = false;
        Race race = info.getRace();
        if (race != null) {
            if (race instanceof Race.Human) {
                dialogRaceComboBox.setSelectedItem("Human");
                raceKnown = true;
            } else {
                dialogRaceComboBox.setSelectedItem("Other");
                dialogRaceTextField.setText(race.getName());
            }
            //If the player is a known race, disable unapplicable options
            if (raceKnown) {
                dialogRaceTextField.setVisible(false);
                dialogSizeComboBox.setSelectedItem(info.getSize().toString());
                dialogSizeComboBox.setEnabled(false);
                updateCharacterOptionsBasedOffClass();
            }
        }
        //Get the player's size if their race isn't known
        if (!raceKnown && info.getSize() != null) {
            dialogSizeComboBox.setSelectedItem(info.getSize().toString());
        }
        Gender gender = info.getGender();
        if (gender != null) {
            if (gender.equals(Gender.MALE)) {
                dialogGenderComboBox.setSelectedItem("Male");
            } else {
                dialogGenderComboBox.setSelectedItem("Female");
            }
        }
        //TODO: Handle alignment

        if (info.getDeity() != null) {
            dialogDeityTextField.setText(info.getDeity().toString());
        }
        //None of these need if statements, because int values are always initialized
        dialogAgeTextField.setText("" + info.getAge());
        dialogHeightFtTextField.setText("" + info.getHeightFeet());
        dialogHeightInTextField.setText("" + info.getHeightInches());
        dialogWeightTextField.setText("" + info.getWeight());
        if (info.getEyeColor() != null) {
            dialogEyesTextField.setText(info.getEyeColor());
        }
        if (info.getHairColor() != null) {
            dialogHairTextField.setText(info.getHairColor());
        }
        if (info.getSkinColor() != null) {
            dialogSkinTextField.setText(info.getSkinColor());
        }
    }

    /**
     * Updates available order and alignment based off of race.
     */
    private void updateCharacterOptionsBasedOffClass() {
        CharacterClass initialClass = player.getClassInfo().getInitialClass();
        String selectedItem = (String) dialogClassComboBox.getSelectedItem();
        if (selectedItem.equals("Other")) {
            //Restore deactivate order options
            for (String string : deactivatedOrders) {
                dialogOrderComboBox.addItem(string);
                System.out.println("Trying to add " + string + " to the options.");
            }
            //Restore deactivated alignment options
            for (String string : deactivatedAlignments) {
                dialogAlignmentComboBox.addItem(string);
                System.out.println("Trying to add " + string + " to the options.");
            }
            //Make text field visible
            dialogClassTextField.setText("" + player.getClassInfo().getInitialClass());
            dialogClassTextField.setVisible(true);
            invalidate();
        } else {
            //TODO: Get list of limited order and alignment from the race class
            //Hide the custom class text field
            dialogClassTextField.setVisible(false);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField dialogAgeTextField;
    private javax.swing.JComboBox dialogAlignmentComboBox;
    private javax.swing.JButton dialogCharacterCommitButton;
    private javax.swing.JComboBox dialogClassComboBox;
    private javax.swing.JTextField dialogClassTextField;
    private javax.swing.JTextField dialogDeityTextField;
    private javax.swing.JTextField dialogEyesTextField;
    private javax.swing.JComboBox dialogGenderComboBox;
    private javax.swing.JTextField dialogHairTextField;
    private javax.swing.JTextField dialogHeightFtTextField;
    private javax.swing.JTextField dialogHeightInTextField;
    private javax.swing.JTextField dialogNameTextField;
    private javax.swing.JComboBox dialogOrderComboBox;
    private javax.swing.JComboBox dialogRaceComboBox;
    private javax.swing.JTextField dialogRaceTextField;
    private javax.swing.JComboBox dialogSizeComboBox;
    private javax.swing.JTextField dialogSkinTextField;
    private javax.swing.JTextField dialogWeightTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
