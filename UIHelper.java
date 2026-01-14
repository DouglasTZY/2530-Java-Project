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
