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
        label1 = new JLabel("Seat Number:");
        label2 = new JLabel("Passenger Name:");
        label3 = new JLabel("Select Movie:");
        label4 = new JLabel("Email:");
        
        // Apply UIHelper styling to labels
        UIHelper.setTitle(label1);
        UIHelper.setTitle(label2);
        UIHelper.setTitle(label3);
        UIHelper.setTitle(label4);

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
        // Exception 1: NumberFormatException
        try {
            int seats = Integer.parseInt(textField1.getText());
            if (seats <= 0) {
                JOptionPane.showMessageDialog(this, "Please enter a valid seat number", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Exception 2: IOException - File Writing
            try {
                String passengerName = textField2.getText();
                String selectedMovie = (String) movieCombo.getSelectedItem();
                
                java.io.FileWriter fw = new java.io.FileWriter("booking.txt", true);
                fw.write("Seat: " + seats + ", Name: " + passengerName + ", Movie: " + selectedMovie + "\n");
                fw.close();
                
                JOptionPane.showMessageDialog(this, "Movie booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (java.io.IOException ex) {
                JOptionPane.showMessageDialog(this, "Error writing to file: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid seat number! Please enter a valid integer.", "Number Format Error", JOptionPane.ERROR_MESSAGE);
            textField1.setText("");
        }
        
        // Exception 3: ArrayIndexOutOfBoundsException (Demonstration)
        try {
            validateMovieSelection();
        } catch (ArrayIndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(this, "Invalid movie selection!", "Selection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Helper method to demonstrate ArrayIndexOutOfBoundsException handling
    private void validateMovieSelection() throws ArrayIndexOutOfBoundsException {
        String[] movies = {"Movie 1", "Movie 2", "Movie 3"};
        int selectedIndex = movieCombo.getSelectedIndex();
        
        if (selectedIndex >= movies.length) {
            throw new ArrayIndexOutOfBoundsException("Selected movie index is out of bounds");
        }
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
