package character.proficiencies;

import java.util.ArrayList;

/**
 * Contains an enumeration of all of the weapon proficiencies available for 3.5.
 *
 * @author Japhez
 */
public enum WeaponProficiency {

    //Simple
    CLUB, DAGGER, DART, GAUNTLET, HEAVY_CROSSBOW, HEAVY_MACE, JAVELIN,
    LIGHT_CROSSBOW, LIGHT_MACE, LONGSPEAR, MORNINGSTAR, PUNCHING_DAGGER,
    QUARTERSTAFF, SHORTSPEAR, SICKLE, SLING, SPEAR, SPIKED_GAUNTLET,
    UNARMED_STRIKE,
    //Martial
    ARMOR_SPIKES, BATTLEAXE, COMPOSITE_LONGBOW, COMPOSITE_SHORTBOW, FALCHION,
    FLAIL, GLAIVE, GREATAXE, GREATCLUB, GREATSWORD, GUISARME, HALBERD, HANDAXE,
    HEAVY_FLAIL, HEAVY_PICK, HEAVY_SHIELD, KUKRI, LANCE, LIGHT_HAMMER,
    LIGHT_PICK, LIGHT_SHIELD, LONGBOW, LONGSWORD, RANSEUR, RAPIER, SAP,
    SCIMITAR, SCYTHE, SHORT_SWORD, SHORTBOW, THROWING_AXE, TRIDENT, WARHAMMER,
    //Exotic
    BASTARD_SWORD, BOLAS, DIRE_FLAIL, DWARVEN_URGROSH, DWARVEN_WARAXE,
    GNOME_HOOKED_HAMMER, HAND_CROSSBOW, KAMA, NET, NET_OF_SNARING, NUNCHAKU,
    ORC_DOUBLE_AXE, REPEATING_HEAVY_CROSSBOW, REPEATING_LIGHT_CROSSBOW, SAI,
    SHURIKEN, SIANGHAM, SPIKED_CHAIN, TWO_BLADED_SWORD, WHIP;

    /**
     * @return all exotic weapon proficiencies in an ArrayList
     */
    public static ArrayList<WeaponProficiency> getExoticWeapons() {
        ArrayList<WeaponProficiency> list = new ArrayList<>();
        list.add(BASTARD_SWORD);
        list.add(BOLAS);
        list.add(DIRE_FLAIL);
        list.add(DWARVEN_URGROSH);
        list.add(DWARVEN_WARAXE);
        list.add(GNOME_HOOKED_HAMMER);
        list.add(HAND_CROSSBOW);
        list.add(KAMA);
        list.add(NET);
        list.add(NET_OF_SNARING);
        list.add(NUNCHAKU);
        list.add(ORC_DOUBLE_AXE);
        list.add(REPEATING_HEAVY_CROSSBOW);
        list.add(REPEATING_LIGHT_CROSSBOW);
        list.add(SAI);
        list.add(SHURIKEN);
        list.add(SIANGHAM);
        list.add(SPIKED_CHAIN);
        list.add(TWO_BLADED_SWORD);
        list.add(WHIP);
        return list;
    }

    /**
     * @return all martial weapon proficiencies in an ArrayList
     */
    public static ArrayList<WeaponProficiency> getMartialWeapons() {
        ArrayList<WeaponProficiency> list = new ArrayList<>();
        list.add(ARMOR_SPIKES);
        list.add(BATTLEAXE);
        list.add(COMPOSITE_LONGBOW);
        list.add(COMPOSITE_SHORTBOW);
        list.add(FALCHION);
        list.add(FLAIL);
        list.add(GLAIVE);
        list.add(GREATAXE);
        list.add(GREATCLUB);
        list.add(GREATSWORD);
        list.add(GUISARME);
        list.add(HALBERD);
        list.add(HANDAXE);
        list.add(HEAVY_FLAIL);
        list.add(HEAVY_PICK);
        list.add(HEAVY_SHIELD);
        list.add(KUKRI);
        list.add(LANCE);
        list.add(LIGHT_HAMMER);
        list.add(LIGHT_PICK);
        list.add(LIGHT_SHIELD);
        list.add(LONGBOW);
        list.add(LONGSWORD);
        list.add(RANSEUR);
        list.add(RAPIER);
        list.add(SAP);
        list.add(SCIMITAR);
        list.add(SCYTHE);
        list.add(SHORT_SWORD);
        list.add(SHORTBOW);
        list.add(THROWING_AXE);
        list.add(TRIDENT);
        list.add(WARHAMMER);
        return list;
    }

    /**
     * @return all simple weapon proficiencies in an ArrayList
     */
    public static ArrayList<WeaponProficiency> getSimpleWeapons() {
        ArrayList<WeaponProficiency> list = new ArrayList<>();
        list.add(CLUB);
        list.add(DAGGER);
        list.add(DART);
        list.add(GAUNTLET);
        list.add(HEAVY_CROSSBOW);
        list.add(HEAVY_MACE);
        list.add(JAVELIN);
        list.add(LIGHT_CROSSBOW);
        list.add(LIGHT_MACE);
        list.add(LONGSPEAR);
        list.add(MORNINGSTAR);
        list.add(PUNCHING_DAGGER);
        list.add(QUARTERSTAFF);
        list.add(SHORTSPEAR);
        list.add(SICKLE);
        list.add(SLING);
        list.add(SPEAR);
        list.add(SPIKED_GAUNTLET);
        list.add(UNARMED_STRIKE);
        return list;
    }

    /**
     * A better toString method that returns a good looking string instead of a
     * enum looking one.
     * @return a better looking string
     */
    @Override
    public String toString() {
        String name = name();
        String newName = "";
        boolean nextIsCapitized = true;
        for (int i = 0; i < name.length(); i++) {
            if (nextIsCapitized) {
                newName += name.substring(i, i + 1).toUpperCase();
                nextIsCapitized = false;
            } else {
                String nextChar = name.substring(i, i + 1);
                if (nextChar.equals("_")) {
                    newName += " ";
                    nextIsCapitized = true;
                } else {
                    newName += name.substring(i, i + 1).toLowerCase();
                }
            }
        }
        return newName;
    }
}
