package character.inventory;

import abstracts.Size;

/**
 * A class with static methods to determine load thresholds and things.
 * @author Jacob
 */
public class CarryingCapacity {

    /**
     * @param strength
     * @return the current load thresholds given the passed strength
     */
    public static LoadThresholds getLoadThresholds(int strength, Size size, boolean quadruped) {
        int light;
        int medium;
        int heavy;
        if (strength < 11) {
            heavy = (strength * 10);
        } else if (strength == 17) {
            heavy = 260;
        } else if (strength == 18) {
            heavy = 300;
        } else if (strength == 22) {
            heavy = 520;
        } else if (strength == 23) {
            heavy = 600;
        } else if (strength == 24) {
            heavy = 700;
        } else if (strength == 27) {
            heavy = 1040;
        } else if (strength == 28) {
            heavy = 1200;
        } else if (strength == 29) {
            heavy = 1400;
        } else if (strength > 29) {
            //Get 1's digit
            int mod = strength % 10;
            //Get cooresponding value on the table
            int newStrength = mod + 20;
            //Find difference, divide by 10
            int multiplier = (strength - newStrength) / 10;
            //Get heavy load data for the above value
            //Use medium size here because the size multiplier gets used later
            heavy = getLoadThresholds(newStrength, new Size.Medium(), false).getHeavy();
            //Multiply by 4 times the multiplier determined above
            heavy = heavy * (4 * multiplier);
        } else {
            //Heavy load
            heavy = (int) (Math.round(20 * Math.pow(Math.pow(2, 0.2), strength - 10)) * 5);
        }
        if (quadruped) {
            heavy = (int) (heavy * size.getQuadCarryingCapacityModifer());
        } else {
            heavy = (int) (heavy * size.getCarryingCapacityMultiplier());
        }//Medium load
        medium = heavy * 2 / 3;
        //Light load
        light = heavy * 1 / 3;
        return new LoadThresholds(light, medium, heavy);
    }

    /**
     * Gets the current Load value given the passed strength and weight values.
     * @param strength
     * @param weight
     * @return the current Load
     */
    public static Load getLoad(int strength, Size size, boolean quadruped, int weight) {
        LoadThresholds loadThresholds = getLoadThresholds(strength, size, quadruped);
        if (weight <= loadThresholds.getLight()) {
            return Load.LIGHT;
        } else if (weight <= loadThresholds.getMedium()) {
            return Load.MEDIUM;
        } else if (weight <= loadThresholds.getMedium()) {
            return Load.HEAVY;
        } else {
            return Load.OVERBURDENED;
        }
    }

    public enum Load {

        LIGHT, MEDIUM, HEAVY, OVERBURDENED;

        /**
         * Override for toString that returns the enumeration with the first letter
         * capitalized, and the rest lower case.
         * http://javahowto.blogspot.com/2006/10/custom-string-values-for-enum.html?showComment=1240529040000#c6158717620626337359
         *
         * @return the enumeration with the first letter capitalized, and the rest
         * lower case.
         */
        @Override
        public String toString() {
            String output = name().toString();
            output = output.charAt(0) + output.substring(1).toLowerCase();
            return output;
        }
    }

    public static class LoadThresholds {

        private int light;
        private int medium;
        private int heavy;

        public LoadThresholds(int light, int medium, int heavy) {
            this.light = light;
            this.medium = medium;
            this.heavy = heavy;
        }

        public int getLight() {
            return light;
        }

        public int getMedium() {
            return medium;
        }

        public int getHeavy() {
            return heavy;
        }
    }
}
