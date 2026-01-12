import javax.swing.*;
import java.awt.*;

/**
 * Helper Class for UI styling operations
 * Provides utility methods for formatting GUI components
 */
public class UIHelper {
    /**
     * Sets title styling for a JLabel
     * Applies Arial Bold font at size 20 with blue color
     * 
     * @param label the JLabel to style
     */
    public static void setTitle(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.BLUE);
    }
    
    /**
     * Sets standard button styling
     * 
     * @param button the JButton to style
     */
    public static void setButtonStyle(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setBackground(Color.LIGHT_GRAY);
        button.setForeground(Color.BLACK);
    }
    
    /**
     * Sets standard text field styling
     * 
     * @param field the JTextField to style
     */
    public static void setTextFieldStyle(JTextField field) {
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBackground(Color.WHITE);
        field.setForeground(Color.BLACK);
    }
}
