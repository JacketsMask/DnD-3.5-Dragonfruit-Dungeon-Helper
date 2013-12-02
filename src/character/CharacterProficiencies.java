/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package character;

import character.classes.CharacterClass;
import character.proficiencies.ArmorProficiency;
import character.proficiencies.WeaponProficiency;
import main.SaveStateSender;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Japhez
 */
public class CharacterProficiencies extends SaveStateSender implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<ArmorProficiency> armorProficiencyList;
    private ArrayList<WeaponProficiency> weaponProficiencyList;

    public CharacterProficiencies() {
        armorProficiencyList = new ArrayList<>();
        weaponProficiencyList = new ArrayList<>();
    }

    public ArrayList<ArmorProficiency> getArmorProficiencyList() {
        return armorProficiencyList;
    }

    public ArrayList<WeaponProficiency> getWeaponProficiencyList() {
        return weaponProficiencyList;
    }

    public void addArmorProficiency(ArmorProficiency proficiency) {
        if (!armorProficiencyList.contains(proficiency)) {
            armorProficiencyList.add(proficiency);
        }
        super.stateChanged();
    }

    public void addWeaponProficiency(WeaponProficiency proficiency) {
        if (!weaponProficiencyList.contains(proficiency)) {
            weaponProficiencyList.add(proficiency);
        }
        super.stateChanged();
    }

    public void removeWeaponProficiency(WeaponProficiency proficiency) {
        weaponProficiencyList.remove(proficiency);
        super.stateChanged();
    }

    public void removeArmorProficiency(ArmorProficiency proficiency) {
        armorProficiencyList.remove(proficiency);
        super.stateChanged();
    }

    public boolean hasArmorProficiency(ArmorProficiency proficiency) {
        return (armorProficiencyList.contains(proficiency));
    }

    public boolean hasWeaponProficiency(WeaponProficiency proficiency) {
        return (weaponProficiencyList.contains(proficiency));
    }

    public void updateProficienciesFromClasses(ArrayList<CharacterClass> characterClasses) {
        for (CharacterClass cc : characterClasses) {
            for (ArmorProficiency ap : cc.getArmorProficiencies()) {
                if (!armorProficiencyList.contains(ap)) {
                    armorProficiencyList.add(ap);
                }
            }
            for (WeaponProficiency wp : cc.getWeaponProficiencies()) {
                if (!weaponProficiencyList.contains(wp)) {
                    weaponProficiencyList.add(wp);
                }
            }
        }
        System.out.println("Proficiencies updated from new class information.");
    }
}
