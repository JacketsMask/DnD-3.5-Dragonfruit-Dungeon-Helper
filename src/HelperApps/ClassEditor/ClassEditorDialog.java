package HelperApps.ClassEditor;

import character.classes.ClassSpellsPerDay;
import character.classes.CharacterClass;
import character.classes.MutableCharacterClass;
import character.classes.StartingGold;
import character.proficiencies.ArmorProficiency;
import character.proficiencies.WeaponProficiency;
import enumerations.Alignment;
import enumerations.CasterType;
import enumerations.Skill;
import character.classes.CharacterClassLevelData;
import enumerations.AbilityScore;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.table.TableColumnModel;
import main.JListHelper;
import main.TableColumnAdjuster;

/**
 *
 * @author Japhez
 */
public class ClassEditorDialog extends javax.swing.JDialog {

    private MutableCharacterClass newClass;
    private ClassEditorSpellsPerDayPanel spellsPerDayPanel;
    private ClassEditorSpellPanel spellPanel;
    private boolean classDone;

    /**
     * Creates new form ClassBuilder
     */
    public ClassEditorDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initSkillLists();
        initProficiencyLists();
        initPrimaryAttributes();
        new TableColumnAdjuster(classTable).adjustColumns();
        classDone = false;
    }

    /**
     * Load in an existing class to edit.
     *
     * @param parent
     * @param model
     * @param initialClass
     */
    public ClassEditorDialog(java.awt.Frame parent, boolean model, MutableCharacterClass initialClass) {
        super(parent, model);
        initComponents();
        newClass = initialClass;
        loadInInitialData();
        initPrimaryAttributes();
        classDone = false;
    }

    private void initPrimaryAttributes() {
        for (AbilityScore as : AbilityScore.getAbilityScores()) {
            primaryAttributeComboBox.addItem(as);
        }
    }

    public boolean isClassDone() {
        return classDone;
    }

    private void loadInInitialData() {
        //Name
        nameTextField.setText(newClass.getName());
        //Hit die
        hitDieTextField.setText("" + newClass.getHitDie());
        loadStartingGold();
        loadCasterData();
        loadAlignments();
        loadClassTable();
        loadSkills();
        loadProficiencies();
    }

    private void loadProficiencies() {
        //Armor proficiencies
        DefaultListModel knownArmor = new DefaultListModel();
        DefaultListModel unknownArmor = new DefaultListModel();
        ArrayList<ArmorProficiency> remainingArmor = ArmorProficiency.getProficiencies();
        ArmorProficiency[] armorProficiencies = newClass.getArmorProficiencies();
        //For each proficiency retrieved from the player
        for (ArmorProficiency a : armorProficiencies) {
            //Add to known list
            knownArmor.addElement(a);
            //Remove from list that will be the unknown list
            remainingArmor.remove(a);
        }
        //Add remaining proficiencies to unknown list
        for (ArmorProficiency a : remainingArmor) {
            unknownArmor.addElement(a);
        }
        knownArmorProficiencyList.setModel(knownArmor);
        unknownArmorProficiencyList.setModel(unknownArmor);
        JListHelper.registerLinkedJListListener(knownArmorProficiencyList, unknownArmorProficiencyList);
        //Weapon proficiencies
        DefaultListModel knownWeapons = new DefaultListModel();
        DefaultListModel unknownWeapons = new DefaultListModel();
        knownWeaponProficiencyList.setModel(knownWeapons);
        unknownWeaponProficiencyList.setModel(unknownWeapons);
        JListHelper.registerLinkedJListListener(knownWeaponProficiencyList, unknownWeaponProficiencyList);
        ArrayList<WeaponProficiency> remainingWeapons = WeaponProficiency.getAllProficiencies();
        WeaponProficiency[] weaponProficiencies = newClass.getWeaponProficiencies();
        for (WeaponProficiency w : weaponProficiencies) {
            knownWeapons.addElement(w);
            remainingWeapons.remove(w);
        }
        for (WeaponProficiency w : remainingWeapons) {
            unknownWeapons.addElement(w);
        }
        //Check to see if checkboxes should be set
        boolean simpleWeaponsIncluded = true;
        ArrayList<WeaponProficiency> simpleWeapons = WeaponProficiency.getSimpleWeapons();
        for (int i = 0; i < simpleWeapons.size(); i++) {
            if (!knownWeapons.contains(simpleWeapons.get(i))) {
                simpleWeaponsIncluded = false;
            }
        }
        if (simpleWeaponsIncluded) {
            simpleWeaponsCheckBox.setSelected(true);
        }
        //Martial weapons
        boolean martialWeaponsIncluded = true;
        ArrayList<WeaponProficiency> martialWeapons = WeaponProficiency.getMartialWeapons();
        for (int i = 0; i < martialWeapons.size(); i++) {
            if (!knownWeapons.contains(martialWeapons.get(i))) {
                martialWeaponsIncluded = false;
            }
        }
        if (martialWeaponsIncluded) {
            martialWeaponsCheckBox.setSelected(true);
        }
    }

    private void loadSkills() {
        //Get skill ranks per level
        skillInitialModifierTextField.setText("" + newClass.getInitialSkillRankModifier());
        skillModifierTextField.setText("" + newClass.getSkillRankModifier());
        ArrayList<Skill> remaining = Skill.getAllSkillsInArrayList();
        //Class skills
        DefaultListModel model = new DefaultListModel();
        for (Skill s : newClass.getClassSkills()) {
            model.addElement(s);
            remaining.remove(s);
        }
        classSkillsList.setModel(model);
        //Cross-class skills
        DefaultListModel model2 = new DefaultListModel();
        for (Skill s : remaining) {
            model2.addElement(s);
        }
        crossClassSkillsList.setModel(model2);
        JListHelper.registerLinkedJListListener(classSkillsList, crossClassSkillsList);
    }

    private void loadClassTable() {
        HashMap<Integer, CharacterClassLevelData> map = newClass.getLevelDataMap();
        TableColumnModel columnModel = classTable.getColumnModel();
        int babColumn = columnModel.getColumnIndex("Base Attack Bonus");
        int fortSaveColumn = columnModel.getColumnIndex("Fortitude Save");
        int reflexSaveColumn = columnModel.getColumnIndex("Reflex Save");
        int willSaveColumn = columnModel.getColumnIndex("Will Save");
        int notesColumn = columnModel.getColumnIndex("Notes");
        int rowCount = classTable.getRowCount();
        //Get rest of info from each column at the same row
        for (int i = 0; i < rowCount; i++) {
            CharacterClassLevelData data = map.get(i + 1);
            //Set BAB
            int baseAttackBonus = data.getBaseAttackBonus(); //FIXME: Null pointer
            classTable.setValueAt(baseAttackBonus, i, babColumn);
            //Set saves
            classTable.setValueAt(data.getFortSave(), i, fortSaveColumn);
            classTable.setValueAt(data.getRefSave(), i, reflexSaveColumn);
            classTable.setValueAt(data.getWillSave(), i, willSaveColumn);
            //Set level notes
            classTable.setValueAt(data.getLevelNotes(), i, notesColumn);
        }
    }

    private void loadStartingGold() {
        //Starting gold
        StartingGold startingGold = newClass.getStartingGold();
        startingGoldNumDieTextField.setText("" + startingGold.getNumDice());
        startingGoldSidesTextField.setText("" + startingGold.getNumSides());
        startingGoldMultiplierTextField.setText("" + startingGold.getMultiplier());
    }

    private void loadCasterData() {
        //Casting info
        if (newClass.getCasterType() == CasterType.LEARNED) {
            castsSpellsCheckBox.setSelected(true);
            learnedSpellcasterRadioButton.setSelected(true);
        } else if (newClass.getCasterType() == CasterType.INNATE) {
            castsSpellsCheckBox.setSelected(true);
            innateSpellcasterRadioButton.setSelected(true);
        } else if (newClass.getCasterType() == CasterType.NON_CASTER) {
            castsSpellsCheckBox.setSelected(false);
        }
    }

    private void loadAlignments() {
        //Possible alignments
        ArrayList<Alignment> alignmentLimitations = newClass.getAlignmentLimitations();
        if (!alignmentLimitations.contains(Alignment.LAWFUL_GOOD)) {
            lawfulGoodCheckBox.setSelected(true);
        } else {
            lawfulGoodCheckBox.setSelected(false);
        }
        if (!alignmentLimitations.contains(Alignment.NEUTRAL_GOOD)) {
            neutralGoodCheckBox.setSelected(true);
        } else {
            neutralGoodCheckBox.setSelected(false);
        }
        if (!alignmentLimitations.contains(Alignment.CHAOTIC_GOOD)) {
            chaoticGoodCheckBox.setSelected(true);
        } else {
            chaoticGoodCheckBox.setSelected(false);
        }
        if (!alignmentLimitations.contains(Alignment.LAWFUL_NEUTRAL)) {
            lawfulNeutralCheckBox.setSelected(true);
        } else {
            lawfulNeutralCheckBox.setSelected(false);
        }
        if (!alignmentLimitations.contains(Alignment.TRUE_NEUTRAL)) {
            trueNeutralCheckBox.setSelected(true);
        } else {
            trueNeutralCheckBox.setSelected(false);
        }
        if (!alignmentLimitations.contains(Alignment.CHAOTIC_NEUTRAL)) {
            chaoticNeutralCheckBox.setSelected(true);
        } else {
            chaoticNeutralCheckBox.setSelected(false);
        }
        if (!alignmentLimitations.contains(Alignment.LAWFUL_EVIL)) {
            lawfulEvilCheckBox.setSelected(true);
        } else {
            lawfulEvilCheckBox.setSelected(false);
        }
        if (!alignmentLimitations.contains(Alignment.NEUTRAL_EVIL)) {
            neutralEvilCheckBox.setSelected(true);
        } else {
            neutralEvilCheckBox.setSelected(false);
        }
        if (!alignmentLimitations.contains(Alignment.CHAOTIC_EVIL)) {
            chaoticEvilCheckBox.setSelected(true);
        } else {
            chaoticEvilCheckBox.setSelected(false);
        }
    }

    public CharacterClass getNewClass() {
        return newClass;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        casterTypeButtonGroup = new javax.swing.ButtonGroup();
        mainTabbedPane = new javax.swing.JTabbedPane();
        basicInfoPanel = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        hitDieTextField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        startingGoldNumDieTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        startingGoldSidesTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        startingGoldMultiplierTextField = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lawfulGoodCheckBox = new javax.swing.JCheckBox();
        chaoticNeutralCheckBox = new javax.swing.JCheckBox();
        chaoticGoodCheckBox = new javax.swing.JCheckBox();
        lawfulNeutralCheckBox = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        chaoticEvilCheckBox = new javax.swing.JCheckBox();
        neutralEvilCheckBox = new javax.swing.JCheckBox();
        trueNeutralCheckBox = new javax.swing.JCheckBox();
        neutralGoodCheckBox = new javax.swing.JCheckBox();
        lawfulEvilCheckBox = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        castsSpellsCheckBox = new javax.swing.JCheckBox();
        usesAbilitiesCheckBox = new javax.swing.JCheckBox();
        learnedSpellcasterRadioButton = new javax.swing.JRadioButton();
        innateSpellcasterRadioButton = new javax.swing.JRadioButton();
        getsDomainSpellCheckBox = new javax.swing.JCheckBox();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        primaryAttributeComboBox = new javax.swing.JComboBox();
        tablePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        classTable = new javax.swing.JTable();
        skills = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        crossClassSkillsList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        classSkillsList = new javax.swing.JList();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        skillInitialModifierTextField = new javax.swing.JTextField();
        skillModifierTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        knownArmorProficiencyList = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        unknownArmorProficiencyList = new javax.swing.JList();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        unknownWeaponProficiencyList = new javax.swing.JList();
        jScrollPane6 = new javax.swing.JScrollPane();
        knownWeaponProficiencyList = new javax.swing.JList();
        simpleWeaponsCheckBox = new javax.swing.JCheckBox();
        martialWeaponsCheckBox = new javax.swing.JCheckBox();
        jPanel9 = new javax.swing.JPanel();
        finishClassButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Character Class Builder");
        setLocationByPlatform(true);

        hitDieTextField.setText("8");

        jLabel1.setText("Class Name:");

        jLabel5.setText("Hitdie: 1D");

        nameTextField.setText("Potato Defender");

        jLabel3.setText("Starting gold:");

        startingGoldNumDieTextField.setText("5");

        jLabel4.setText("d");

        startingGoldSidesTextField.setText("4");

        jLabel6.setText("x");

        startingGoldMultiplierTextField.setText("10");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(startingGoldNumDieTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(startingGoldSidesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(startingGoldMultiplierTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hitDieTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(hitDieTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startingGoldNumDieTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(startingGoldSidesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(startingGoldMultiplierTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lawfulGoodCheckBox.setText("Lawful Good");

        chaoticNeutralCheckBox.setText("Chaotic Neutral");

        chaoticGoodCheckBox.setText("Chaotic Good");

        lawfulNeutralCheckBox.setText("Lawful Neutral");

        jLabel2.setText("Possible alignments:");

        chaoticEvilCheckBox.setText("Chaotic Evil");

        neutralEvilCheckBox.setText("Neutral Evil");

        trueNeutralCheckBox.setText("True Neutral");

        neutralGoodCheckBox.setText("Neutral Good");

        lawfulEvilCheckBox.setText("Lawful Evil");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lawfulGoodCheckBox)
                    .addComponent(jLabel2)
                    .addComponent(chaoticEvilCheckBox)
                    .addComponent(neutralEvilCheckBox)
                    .addComponent(lawfulEvilCheckBox)
                    .addComponent(trueNeutralCheckBox)
                    .addComponent(neutralGoodCheckBox)
                    .addComponent(lawfulNeutralCheckBox)
                    .addComponent(chaoticGoodCheckBox)
                    .addComponent(chaoticNeutralCheckBox))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lawfulGoodCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(neutralGoodCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chaoticGoodCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lawfulNeutralCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(trueNeutralCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chaoticNeutralCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lawfulEvilCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(neutralEvilCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chaoticEvilCheckBox)
                .addContainerGap(87, Short.MAX_VALUE))
        );

        jLabel13.setText("Casting information:");

        castsSpellsCheckBox.setText("Casts Spells");
        castsSpellsCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                castsSpellsCheckBoxItemStateChanged(evt);
            }
        });

        usesAbilitiesCheckBox.setText("Uses Abilities");

        casterTypeButtonGroup.add(learnedSpellcasterRadioButton);
        learnedSpellcasterRadioButton.setText("Learned: Learns spells individually up to current spell level");
        learnedSpellcasterRadioButton.setEnabled(false);

        casterTypeButtonGroup.add(innateSpellcasterRadioButton);
        innateSpellcasterRadioButton.setText("Innate: Knows all spells up to current spell level");
        innateSpellcasterRadioButton.setEnabled(false);

        getsDomainSpellCheckBox.setText("Gets domain spell");
        getsDomainSpellCheckBox.setEnabled(false);

        jLabel18.setText("Primary attribute (determines bonus spells):");

        jLabel19.setText("Ability information:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel19)
                    .addComponent(castsSpellsCheckBox)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(innateSpellcasterRadioButton)
                            .addComponent(learnedSpellcasterRadioButton)
                            .addComponent(getsDomainSpellCheckBox)
                            .addComponent(jLabel18)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(primaryAttributeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(usesAbilitiesCheckBox))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(castsSpellsCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(learnedSpellcasterRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(innateSpellcasterRadioButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(getsDomainSpellCheckBox)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(primaryAttributeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(usesAbilitiesCheckBox)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout basicInfoPanelLayout = new javax.swing.GroupLayout(basicInfoPanel);
        basicInfoPanel.setLayout(basicInfoPanelLayout);
        basicInfoPanelLayout.setHorizontalGroup(
            basicInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(basicInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(225, Short.MAX_VALUE))
        );
        basicInfoPanelLayout.setVerticalGroup(
            basicInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, basicInfoPanelLayout.createSequentialGroup()
                .addGroup(basicInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(basicInfoPanelLayout.createSequentialGroup()
                        .addGap(0, 50, Short.MAX_VALUE)
                        .addGroup(basicInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        mainTabbedPane.addTab("Basic class info", basicInfoPanel);

        classTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                { new Integer(1),  new Integer(0),  new Integer(2),  new Integer(0),  new Integer(2), " 	Turn or Rebuke Undead"},
                { new Integer(2),  new Integer(1),  new Integer(3),  new Integer(0),  new Integer(3), null},
                { new Integer(3),  new Integer(2),  new Integer(3),  new Integer(1),  new Integer(3), null},
                { new Integer(4),  new Integer(3),  new Integer(4),  new Integer(1),  new Integer(4), null},
                { new Integer(5),  new Integer(3),  new Integer(4),  new Integer(1),  new Integer(4), null},
                { new Integer(6),  new Integer(4),  new Integer(5),  new Integer(2),  new Integer(5), null},
                { new Integer(7),  new Integer(5),  new Integer(5),  new Integer(2),  new Integer(5), null},
                { new Integer(8),  new Integer(6),  new Integer(6),  new Integer(2),  new Integer(6), null},
                { new Integer(9),  new Integer(6),  new Integer(6),  new Integer(3),  new Integer(6), null},
                { new Integer(10),  new Integer(7),  new Integer(7),  new Integer(3),  new Integer(7), null},
                { new Integer(11),  new Integer(8),  new Integer(7),  new Integer(3),  new Integer(7), null},
                { new Integer(12),  new Integer(9),  new Integer(8),  new Integer(4),  new Integer(8), null},
                { new Integer(13),  new Integer(9),  new Integer(8),  new Integer(4),  new Integer(8), null},
                { new Integer(14),  new Integer(10),  new Integer(9),  new Integer(4),  new Integer(9), null},
                { new Integer(15),  new Integer(11),  new Integer(9),  new Integer(5),  new Integer(9), null},
                { new Integer(16),  new Integer(12),  new Integer(10),  new Integer(5),  new Integer(10), null},
                { new Integer(17),  new Integer(12),  new Integer(10),  new Integer(5),  new Integer(10), null},
                { new Integer(18),  new Integer(13),  new Integer(11),  new Integer(6),  new Integer(11), null},
                { new Integer(19),  new Integer(14),  new Integer(11),  new Integer(6),  new Integer(11), null},
                { new Integer(20),  new Integer(15),  new Integer(12),  new Integer(6),  new Integer(12), null}
            },
            new String [] {
                "Level", "Base Attack Bonus", "Fortitude Save", "Reflex Save", "Will Save", "Notes"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        classTable.setCellSelectionEnabled(true);
        classTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(classTable);
        classTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout tablePanelLayout = new javax.swing.GroupLayout(tablePanel);
        tablePanel.setLayout(tablePanelLayout);
        tablePanelLayout.setHorizontalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 871, Short.MAX_VALUE)
                .addContainerGap())
        );
        tablePanelLayout.setVerticalGroup(
            tablePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tablePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainTabbedPane.addTab("Table", tablePanel);

        crossClassSkillsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(crossClassSkillsList);

        classSkillsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(classSkillsList);

        jLabel7.setText("Class skills:");

        jLabel8.setText("Cross-class skills:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2)))
                .addContainerGap())
        );

        jLabel12.setText("+ intelligence modifier");

        skillInitialModifierTextField.setText("4");

        skillModifierTextField.setText("2");

        jLabel11.setText("Ranks gained after first level:");

        jLabel10.setText("x intelligence modifier");

        jLabel9.setText("Skill ranks at first level:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(skillModifierTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12))
                    .addComponent(jLabel9)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(skillInitialModifierTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10))
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(skillInitialModifierTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(skillModifierTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(276, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout skillsLayout = new javax.swing.GroupLayout(skills);
        skills.setLayout(skillsLayout);
        skillsLayout.setHorizontalGroup(
            skillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, skillsLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(433, Short.MAX_VALUE))
        );
        skillsLayout.setVerticalGroup(
            skillsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainTabbedPane.addTab("Skills", skills);

        knownArmorProficiencyList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(knownArmorProficiencyList);

        unknownArmorProficiencyList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(unknownArmorProficiencyList);

        jLabel15.setText("Known armor proficiencies:");

        jLabel14.setText("Unknown armor proficiencies:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5)
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );

        jLabel16.setText("Known weapon proficiencies:");

        jLabel17.setText("Unknown weapon proficiencies:");

        unknownWeaponProficiencyList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(unknownWeaponProficiencyList);

        knownWeaponProficiencyList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(knownWeaponProficiencyList);

        simpleWeaponsCheckBox.setText("Simple weapons");
        simpleWeaponsCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                simpleWeaponsCheckBoxItemStateChanged(evt);
            }
        });

        martialWeaponsCheckBox.setText("Martial weapons");
        martialWeaponsCheckBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                martialWeaponsCheckBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(simpleWeaponsCheckBox)
                    .addComponent(martialWeaponsCheckBox)
                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(simpleWeaponsCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(martialWeaponsCheckBox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(211, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        mainTabbedPane.addTab("Proficiencies", jPanel6);

        finishClassButton.setText("FINISH HIM");
        finishClassButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finishClassButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(282, 282, 282)
                .addComponent(finishClassButton)
                .addContainerGap(520, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(161, Short.MAX_VALUE)
                .addComponent(finishClassButton)
                .addGap(218, 218, 218))
        );

        mainTabbedPane.addTab("Finishing", jPanel9);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainTabbedPane)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainTabbedPane)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void finishClassButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finishClassButtonActionPerformed
        System.out.println("Creating new class...");
        //Basic class info
        newClass = new MutableCharacterClass(nameTextField.getText());
        //Hit die
        newClass.setHitDie(Integer.parseInt(hitDieTextField.getText()));
        //Starting gold
        int numDie = Integer.parseInt(startingGoldNumDieTextField.getText());
        int numSides = Integer.parseInt(startingGoldSidesTextField.getText());
        int multiplier = Integer.parseInt(startingGoldMultiplierTextField.getText());
        newClass.setStartingGold(new StartingGold(numDie, numSides, multiplier));
        //Restricted alignments
        ArrayList<Alignment> alignments = new ArrayList();
        if (!lawfulGoodCheckBox.isSelected()) {
            alignments.add(Alignment.LAWFUL_GOOD);
        }
        if (!neutralGoodCheckBox.isSelected()) {
            alignments.add(Alignment.NEUTRAL_GOOD);
        }
        if (!chaoticGoodCheckBox.isSelected()) {
            alignments.add(Alignment.CHAOTIC_GOOD);
        }
        if (!lawfulNeutralCheckBox.isSelected()) {
            alignments.add(Alignment.LAWFUL_NEUTRAL);
        }
        if (!trueNeutralCheckBox.isSelected()) {
            alignments.add(Alignment.TRUE_NEUTRAL);
        }
        if (!chaoticNeutralCheckBox.isSelected()) {
            alignments.add(Alignment.CHAOTIC_NEUTRAL);
        }
        if (!lawfulEvilCheckBox.isSelected()) {
            alignments.add(Alignment.LAWFUL_EVIL);
        }
        if (!neutralEvilCheckBox.isSelected()) {
            alignments.add(Alignment.NEUTRAL_EVIL);
        }
        if (!chaoticEvilCheckBox.isSelected()) {
            alignments.add(Alignment.CHAOTIC_EVIL);
        }
        newClass.setRestrictedAlignments(alignments);
        //Casting information
        if (castsSpellsCheckBox.isSelected()) {
            //Initialize fields for new spell data
            newClass.initSpellCasterFields();
            if (learnedSpellcasterRadioButton.isSelected()) {
                newClass.setCasterType(CasterType.LEARNED);
            } else if (innateSpellcasterRadioButton.isSelected()) {
                newClass.setCasterType(CasterType.INNATE);
            }
            //Set primary attribute
            newClass.setPrimaryAttribute((AbilityScore) primaryAttributeComboBox.getSelectedItem());
            //Get spells per level
            ClassSpellsPerDay classSpellsPerDay = new ClassSpellsPerDay();
            JTable spellsPerDayTable = spellsPerDayPanel.getSpellsPerDayTable();
            for (int columnIndex = 1; columnIndex <= 10; columnIndex++) {
                for (int rowIndex = 0; rowIndex < 20; rowIndex++) {
                    //Class level column doesn't change
                    int classLevel = (int) spellsPerDayTable.getValueAt(rowIndex, 0);
                    int spellLevel = columnIndex - 1;
                    Object valueAt = spellsPerDayTable.getValueAt(rowIndex, columnIndex);
                    int value = 0;
                    //Make sure it isn't null
                    if (valueAt != null) {
                        value = (int) valueAt;
                    }
                    classSpellsPerDay.setSpellsPerDay(classLevel, spellLevel, value);
                    System.out.println("Spells at level: " + classLevel + " - spell level: " + spellLevel + " = " + value);
                }
            }
            newClass.setSpellsPerDay(classSpellsPerDay);
            //Initialize spell info for class
            newClass.setSpellList(spellPanel.getSpellList());

        } else {
            newClass.setCasterType(CasterType.NON_CASTER);
        }
        //Ability information
        if (usesAbilitiesCheckBox.isSelected()) {
            newClass.setUsesAbilities(true);
        } else {
            newClass.setUsesAbilities(false);
        }

        //Table
        HashMap<Integer, CharacterClassLevelData> map = new HashMap<>();
        TableColumnModel columnModel = classTable.getColumnModel();
        int levelColumn = columnModel.getColumnIndex("Level");
        int babColumn = columnModel.getColumnIndex("Base Attack Bonus");
        int fortSaveColumn = columnModel.getColumnIndex("Fortitude Save");
        int reflexSaveColumn = columnModel.getColumnIndex("Reflex Save");
        int willSaveColumn = columnModel.getColumnIndex("Will Save");
        int notesColumn = columnModel.getColumnIndex("Notes");
        int rowCount = classTable.getRowCount();
        //Get rest of info from each column at the same row
        for (int i = 0; i < rowCount; i++) {
            int localLevel = (int) classTable.getValueAt(i, levelColumn);
            int bab = (int) classTable.getValueAt(i, babColumn);
            int fortSave = (int) classTable.getValueAt(i, fortSaveColumn);
            int reflexSave = (int) classTable.getValueAt(i, reflexSaveColumn);
            int willSave = (int) classTable.getValueAt(i, willSaveColumn);
            String notes = (String) classTable.getValueAt(i, notesColumn);
            CharacterClassLevelData data = new CharacterClassLevelData(localLevel, bab, fortSave, reflexSave, willSave, notes);
            //Add it all to the map at this level
            map.put(localLevel, data);
        }
        newClass.setLevelDataMap(map);
        //Skill ranks
        int initialSkillModifier = Integer.parseInt(skillInitialModifierTextField.getText());
        int skillModifier = Integer.parseInt(skillModifierTextField.getText());
        newClass.setInitialSkillRankModifier(initialSkillModifier);
        newClass.setSkillRankModifier(skillModifier);
        //Class skills
        ListModel model = classSkillsList.getModel();
        Skill[] skillArray = new Skill[model.getSize()];
        for (int i = 0; i < model.getSize(); i++) {
            skillArray[i] = (Skill) model.getElementAt(i);
        }
        newClass.setClassSkills(skillArray);
        //Proficiencies - Armor
        DefaultListModel armorProficiencyModel = (DefaultListModel) knownArmorProficiencyList.getModel();
        ArmorProficiency[] armorProficiencies = new ArmorProficiency[armorProficiencyModel.getSize()];
        for (int i = 0; i < armorProficiencyModel.getSize(); i++) {
            armorProficiencies[i] = (ArmorProficiency) armorProficiencyModel.getElementAt(i);
        }
        newClass.setArmorProficiencies(armorProficiencies);
        //Proficiencies - Weapon
        DefaultListModel weaponProficiencyModel = (DefaultListModel) knownWeaponProficiencyList.getModel();
        WeaponProficiency[] weaponProficiencies = new WeaponProficiency[weaponProficiencyModel.getSize()];
        for (int i = 0; i < weaponProficiencyModel.getSize(); i++) {
            weaponProficiencies[i] = (WeaponProficiency) weaponProficiencyModel.getElementAt(i);
        }
        newClass.setWeaponProficiencies(weaponProficiencies);
        //Class is done
        classDone = true;
        this.setVisible(false);
    }//GEN-LAST:event_finishClassButtonActionPerformed

    private void castsSpellsCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_castsSpellsCheckBoxItemStateChanged
        if (castsSpellsCheckBox.isSelected()) {
            learnedSpellcasterRadioButton.setEnabled(true);
            casterTypeButtonGroup.setSelected(innateSpellcasterRadioButton.getModel(), true);
            innateSpellcasterRadioButton.setEnabled(true);
            getsDomainSpellCheckBox.setEnabled(true);
            //Allow selection of primary attribute
            primaryAttributeComboBox.setEnabled(true);
            //Add spells per day tab
            //Initialize if necessary
            if (spellsPerDayPanel == null) {
                spellsPerDayPanel = new ClassEditorSpellsPerDayPanel();
                JTable spellsPerDayTable = spellsPerDayPanel.getSpellsPerDayTable();
                new TableColumnAdjuster(spellsPerDayTable).adjustColumns();
            }
            mainTabbedPane.add(spellsPerDayPanel, 3);
            //Add spells tab
            //Initialize if necessary
            if (spellPanel == null) {
                spellPanel = new ClassEditorSpellPanel(newClass);
            }
            mainTabbedPane.add(spellPanel, 4);

        } else {
            learnedSpellcasterRadioButton.setEnabled(false);
            innateSpellcasterRadioButton.setEnabled(false);
            getsDomainSpellCheckBox.setEnabled(false);
            getsDomainSpellCheckBox.setSelected(false);
            //Disallow selection of primary attribute
            primaryAttributeComboBox.setEnabled(false);
            casterTypeButtonGroup.clearSelection();
            //Remove spells per day tab
            mainTabbedPane.remove(3);
            //Remove spell panel
            mainTabbedPane.remove(3);
        }
    }//GEN-LAST:event_castsSpellsCheckBoxItemStateChanged

    private void simpleWeaponsCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_simpleWeaponsCheckBoxItemStateChanged
        //Selected, add simple weapon proficiencies to known list and remove them from unknown list
        ArrayList<WeaponProficiency> simpleWeapons = WeaponProficiency.getSimpleWeapons();
        DefaultListModel unknown = (DefaultListModel) unknownWeaponProficiencyList.getModel();
        DefaultListModel known = (DefaultListModel) knownWeaponProficiencyList.getModel();
        if (simpleWeaponsCheckBox.isSelected()) {
            for (WeaponProficiency wp : simpleWeapons) {
                unknown.removeElement(wp);
                known.addElement(wp);
            }
        } else {
            for (WeaponProficiency wp : simpleWeapons) {
                known.removeElement(wp);
                unknown.addElement(wp);
            }
        }
    }//GEN-LAST:event_simpleWeaponsCheckBoxItemStateChanged

    private void martialWeaponsCheckBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_martialWeaponsCheckBoxItemStateChanged
        //Selected, add martial weapon proficiencies to known list and remove them from unknown list
        ArrayList<WeaponProficiency> martialWeapons = WeaponProficiency.getMartialWeapons();
        DefaultListModel unknown = (DefaultListModel) unknownWeaponProficiencyList.getModel();
        DefaultListModel known = (DefaultListModel) knownWeaponProficiencyList.getModel();
        if (martialWeaponsCheckBox.isSelected()) {
            for (WeaponProficiency wp : martialWeapons) {
                unknown.removeElement(wp);
                known.addElement(wp);
            }
        } else {
            for (WeaponProficiency wp : martialWeapons) {
                known.removeElement(wp);
                unknown.addElement(wp);
            }
        }
    }//GEN-LAST:event_martialWeaponsCheckBoxItemStateChanged

    private void initProficiencyLists() {
        //Armor
        ArrayList<ArmorProficiency> armorProficiencies = ArmorProficiency.getProficiencies();
        DefaultListModel unknownArmorProficiencyModel = new DefaultListModel();
        DefaultListModel knownArmorProficiencyModel = new DefaultListModel();
        for (ArmorProficiency ap : armorProficiencies) {
            unknownArmorProficiencyModel.addElement(ap);
        }
        unknownArmorProficiencyList.setModel(unknownArmorProficiencyModel);
        knownArmorProficiencyList.setModel(knownArmorProficiencyModel);
        JListHelper.registerLinkedJListListener(unknownArmorProficiencyList, knownArmorProficiencyList);
        //Weapons
        ArrayList<WeaponProficiency> allWeaponProficiencies = WeaponProficiency.getAllProficiencies();
        DefaultListModel unknownWeaponProficiencyModel = new DefaultListModel();
        DefaultListModel knownWeaponProficiencyModel = new DefaultListModel();
        for (WeaponProficiency wp : allWeaponProficiencies) {
            unknownWeaponProficiencyModel.addElement(wp);
        }
        unknownWeaponProficiencyList.setModel(unknownWeaponProficiencyModel);
        knownWeaponProficiencyList.setModel(knownWeaponProficiencyModel);
        JListHelper.registerLinkedJListListener(unknownWeaponProficiencyList, knownWeaponProficiencyList);
    }

    private void initSkillLists() {
        Skill[] allSkills = Skill.getAllSkills();
        //Model for cross-class list
        final DefaultListModel crossClassModel = new DefaultListModel();
        //Model for class list
        final DefaultListModel classModel = new DefaultListModel();
        for (Skill s : allSkills) {
            crossClassModel.addElement(s);
        }
        //Set the model for the cross class list
        crossClassSkillsList.setModel(crossClassModel);
        //Set the model for the class list
        classSkillsList.setModel(classModel);
        JListHelper.registerLinkedJListListener(classSkillsList, crossClassSkillsList);
    }

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClassEditorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClassEditorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClassEditorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClassEditorDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ClassEditorDialog dialog = new ClassEditorDialog(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel basicInfoPanel;
    private javax.swing.ButtonGroup casterTypeButtonGroup;
    private javax.swing.JCheckBox castsSpellsCheckBox;
    private javax.swing.JCheckBox chaoticEvilCheckBox;
    private javax.swing.JCheckBox chaoticGoodCheckBox;
    private javax.swing.JCheckBox chaoticNeutralCheckBox;
    private javax.swing.JList classSkillsList;
    private javax.swing.JTable classTable;
    private javax.swing.JList crossClassSkillsList;
    private javax.swing.JButton finishClassButton;
    private javax.swing.JCheckBox getsDomainSpellCheckBox;
    private javax.swing.JTextField hitDieTextField;
    private javax.swing.JRadioButton innateSpellcasterRadioButton;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JList knownArmorProficiencyList;
    private javax.swing.JList knownWeaponProficiencyList;
    private javax.swing.JCheckBox lawfulEvilCheckBox;
    private javax.swing.JCheckBox lawfulGoodCheckBox;
    private javax.swing.JCheckBox lawfulNeutralCheckBox;
    private javax.swing.JRadioButton learnedSpellcasterRadioButton;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JCheckBox martialWeaponsCheckBox;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JCheckBox neutralEvilCheckBox;
    private javax.swing.JCheckBox neutralGoodCheckBox;
    private javax.swing.JComboBox primaryAttributeComboBox;
    private javax.swing.JCheckBox simpleWeaponsCheckBox;
    private javax.swing.JTextField skillInitialModifierTextField;
    private javax.swing.JTextField skillModifierTextField;
    private javax.swing.JPanel skills;
    private javax.swing.JTextField startingGoldMultiplierTextField;
    private javax.swing.JTextField startingGoldNumDieTextField;
    private javax.swing.JTextField startingGoldSidesTextField;
    private javax.swing.JPanel tablePanel;
    private javax.swing.JCheckBox trueNeutralCheckBox;
    private javax.swing.JList unknownArmorProficiencyList;
    private javax.swing.JList unknownWeaponProficiencyList;
    private javax.swing.JCheckBox usesAbilitiesCheckBox;
    // End of variables declaration//GEN-END:variables
}
