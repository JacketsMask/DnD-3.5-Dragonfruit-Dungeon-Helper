/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package character;

import character.proficiencies.ArmorProficiency;
import character.proficiencies.WeaponProficiency;
import interfaces.SaveStateTracker;
import java.util.ArrayList;

/**
 *
 * @author Japhez
 */
public class CharacterProficiencies extends SaveStateTracker {

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
        super.stateChanged = true;
    }

    public void addWeaponProficiency(WeaponProficiency proficiency) {
        if (!weaponProficiencyList.contains(proficiency)) {
            weaponProficiencyList.add(proficiency);
        }
        super.stateChanged = true;
    }

    public void removeWeaponProficiency(WeaponProficiency proficiency) {
        weaponProficiencyList.remove(proficiency);
        super.stateChanged = true;
    }

    public void removeArmorProficiency(ArmorProficiency proficiency) {
        armorProficiencyList.remove(proficiency);
        super.stateChanged = true;
    }

    public boolean hasArmorProficiency(ArmorProficiency proficiency) {
        return (armorProficiencyList.contains(proficiency));
    }

    public boolean hasWeaponProficiency(WeaponProficiency proficiency) {
        return (weaponProficiencyList.contains(proficiency));
    }
}
