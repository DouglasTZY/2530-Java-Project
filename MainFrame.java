import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MainFrame extends JFrame {
    // GUI Components
    private JLabel label1, label2, label3, label4;
    private JTextField textField1, textField2;
    private JComboBox<String> movieCombo;
    private JButton bookButton;

    public MainFrame() {
        setTitle("Movie Booking System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create center panel with GridLayout(5, 2)
        JPanel centerPanel = new JPanel(new GridLayout(5, 2));

        // Initialize JLabel × 4
        label1 = new JLabel("Label 1");
        label2 = new JLabel("Label 2");
        label3 = new JLabel("Label 3");
        label4 = new JLabel("Label 4");

        // Initialize JTextField × 2
        textField1 = new JTextField(15);
        textField2 = new JTextField(15);

        // Initialize JComboBox × 1
        movieCombo = new JComboBox<>(new String[]{"Movie 1", "Movie 2", "Movie 3"});

        // Initialize JButton × 1
        bookButton = new JButton("Book Movie");

        // Add components to center panel
        centerPanel.add(label1);
        centerPanel.add(textField1);
        centerPanel.add(label2);
        centerPanel.add(textField2);
        centerPanel.add(label3);
        centerPanel.add(movieCombo);
        centerPanel.add(label4);
        centerPanel.add(bookButton);
        centerPanel.add(new JPanel()); // Extra cell for GridLayout(5,2)

        // Add event listeners
        bookButton.addActionListener(e -> bookMovie());
        movieCombo.addItemListener(e -> updatePrice());

        // Add center panel to frame
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Event handler for Book Button
    private void bookMovie() {
        JOptionPane.showMessageDialog(this, "Movie booked successfully!");
    }

    // Event handler for ComboBox
    private void updatePrice() {
        String selectedMovie = (String) movieCombo.getSelectedItem();
        JOptionPane.showMessageDialog(this, "Selected: " + selectedMovie + "\nPrice updated!");
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
