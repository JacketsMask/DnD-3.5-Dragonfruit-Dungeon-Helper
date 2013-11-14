 package main;

import character.IntegerVerifier;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * Verifies that a JTextField is an integer and that it falls within the set
 * upper and lower bounds, inclusive. Also locks a passed in JComponent object
 * if the values of the JTextField are invalid.
 *
 * @author Jacob Dorman
 */
public class ComponentLockableIntegerVerifier extends IntegerVerifier {

    /**
     * Creates a new IntegerVerifier with the passed lower and upper bounds,
     * inclusive, then sets up action listeners to lock and unlock the lockable
     * JComponent object.
     *
     * @param lowerBound
     * @param upperBound
     */
    public ComponentLockableIntegerVerifier(int lowerBound, int upperBound, final JTextField inputComponent, final JComponent lockableComponent) {
        super(lowerBound, upperBound);

        //Listener for key release, used to lock the lockable component
        inputComponent.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (!isValid(inputComponent)) {
                    lockableComponent.setEnabled(false);
                    inputComponent.setBackground(Color.red);
                    inputComponent.setForeground(Color.white);

                } else {
                    lockableComponent.setEnabled(true);
                    inputComponent.setBackground(Color.white);
                    inputComponent.setForeground(Color.black);
                }
            }
        });
        //Listener for focus lost, at which point verify will have been called
        //and the JTextField will be valid
        inputComponent.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!lockableComponent.isEnabled()) {
                    lockableComponent.setEnabled(true);
                    inputComponent.setBackground(Color.white);
                    inputComponent.setForeground(Color.black);
                }
            }
        });
    }

    /**
     * @param input the JTextField this verifier belongs to
     * @return true if the current value is within bounds.
     */
    public boolean isValid(JTextField input) {
        JTextField textField = (JTextField) input;
        String text = textField.getText();
        try {
            int parseInt = Integer.parseInt(text);
            if (parseInt >= lowerBound && parseInt <= upperBound) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
