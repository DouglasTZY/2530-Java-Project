import javax.swing.*;
import java.awt.*;

// Helper class to make our UI look consistent
public class UIHelper {

    // Make the label look like a title (Blue and Bold)
    public static void makeTitle(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setForeground(Color.BLUE);
    }

    // Make buttons look standard
    public static void styleButton(JButton btn) {
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setForeground(Color.BLACK);
    }

    // Style text fields
    public static void styleTextField(JTextField tf) {
        tf.setFont(new Font("Arial", Font.PLAIN, 12));
        tf.setBackground(Color.WHITE);
        tf.setForeground(Color.BLACK);
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Helper class to make our UI look consistent and PREMIUM
public class UIHelper {

    // Modern Color Palette
    public static final Color COLOR_PRIMARY = new Color(41, 128, 185);    // Nice Blue
    public static final Color COLOR_SECONDARY = new Color(52, 152, 219);  // Lighter Blue
    public static final Color COLOR_BG = new Color(236, 240, 241);        // Soft Grey
    public static final Color COLOR_TEXT = new Color(44, 62, 80);         // Dark Grey
    public static final Color COLOR_WHITE = Color.WHITE;

    // Fonts
    public static final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font FONT_LABEL = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 14);

    // Style the Header Label
    public static void styleHeader(JLabel label) {
        label.setFont(FONT_TITLE);
        label.setForeground(COLOR_WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    // Style the Section/Input Labels
    public static void makeTitle(JLabel label) {
        label.setFont(FONT_LABEL);
        label.setForeground(COLOR_TEXT);
    }

    // Style Buttons with Hover Effect
    public static void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(COLOR_PRIMARY);
        btn.setForeground(COLOR_WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add Hover Effect
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(COLOR_SECONDARY); // Lighter blue on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(COLOR_PRIMARY); // Back to normal
            }
        });
    }

    // Style Text Fields
    public static void styleTextField(JTextField tf) {
        tf.setFont(FONT_INPUT);
        tf.setBackground(COLOR_WHITE);
        tf.setForeground(COLOR_TEXT);
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199)),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }
    
    // Style ComboBox
    public static void styleComboBox(JComboBox<String> box) {
        box.setFont(FONT_INPUT);
        box.setBackground(COLOR_WHITE);
        box.setForeground(COLOR_TEXT);
    }
}







