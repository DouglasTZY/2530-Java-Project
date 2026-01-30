// Group 1 
// Movie Booking System 
// Douglas Tan Tze Yu 1221206532 
// Joel Nithyananthan Paramananthan 1211205630

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AppStyle {

    // Define main colors
    public static final Color MAIN_COLOR = new Color(41, 128, 185);
    public static final Color SECOND_COLOR = new Color(52, 152, 219);
    public static final Color BG_COLOR = new Color(236, 240, 241);
    public static final Color TEXT_COLOR = new Color(44, 62, 80);
    public static final Color WHITE = Color.WHITE;

    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    public static final Font LABEL_FONT = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    // Style the title labels
    public static void styleHeader(JLabel label) {
        label.setFont(TITLE_FONT);
        label.setForeground(WHITE);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public static void applyLabelStyle(JLabel label) {
        label.setFont(LABEL_FONT);
        label.setForeground(TEXT_COLOR);
    }

    // Style buttons and add hover effect
    public static void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(MAIN_COLOR);
        btn.setForeground(WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(SECOND_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(MAIN_COLOR);
            }
        });
    }

    public static void styleTextField(JTextField tf) {
        tf.setFont(INPUT_FONT);
        tf.setBackground(WHITE);
        tf.setForeground(TEXT_COLOR);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(189, 195, 199)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    public static void styleComboBox(JComboBox<String> box) {
        box.setFont(INPUT_FONT);
        box.setBackground(WHITE);
        box.setForeground(TEXT_COLOR);
    }
}
