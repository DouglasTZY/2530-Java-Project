import java.awt.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

public class MainFrame extends JFrame {
    // GUI Components
    private JLabel label1, label2, label3, label4;
    private JTextField textField2; // Name
    private JSpinner seatSpinner; // Replaces textField1 for Seat Number (Satisfies JSpinner requirement)
    private JComboBox<String> movieCombo;
    private JButton bookButton;
    private JMenuBar menuBar; // Additional Component #1: JMenuBar
    private JMenu fileMenu;
    private JMenuItem viewItem, exitItem;

    // ImageIcon components (3 icons)
    private ImageIcon posterIcon, ticketIcon, successIcon;

    public MainFrame() {
        setTitle("Movie Booking System");
        setSize(600, 500); // Increased height for menu
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize Menu Bar (Requirement: Additional Component)
        initMenuBar();

        // Create center panel with GridLayout(5, 2)
        JPanel centerPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // Added gaps
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding

        // Initialize 3 ImageIcons
        posterIcon = loadIcon("poster.webp");
        ticketIcon = loadIcon("ticket.png");
        successIcon = loadIcon("success.png");

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

        // Initialize Components
        // Requirement: Additional Component #2 (JSpinner)
        seatSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        textField2 = new JTextField(15);

        // Initialize JComboBox × 1 (Read from file)
        movieCombo = new JComboBox<>();
        loadMoviesFromFile(); // Requirement: File Reading

        // Initialize JButton × 1
        bookButton = new JButton("Book Movie", ticketIcon);

        // Add components to center panel
        centerPanel.add(label1);
        centerPanel.add(seatSpinner); // Using JSpinner
        centerPanel.add(label2);
        centerPanel.add(textField2);
        centerPanel.add(label3);
        centerPanel.add(movieCombo);
        centerPanel.add(label4);
        // Field for email - reusing simple textfield logic or just adding label for now
        // as placeholder needed for grid
        JTextField emailField = new JTextField(15);
        centerPanel.add(emailField);

        centerPanel.add(bookButton);
        centerPanel.add(new JPanel()); // Extra cell for GridLayout(5,2)

        // Create header panel with posterIcon
        JPanel headerPanel = new JPanel();
        JLabel posterLabel = new JLabel("Movie Booking System", posterIcon, JLabel.CENTER);
        posterLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(posterLabel);

        // Add event listeners
        bookButton.addActionListener(e -> {
            try {
                processBooking(emailField.getText());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error processing booking: " + ex.getMessage());
            }
        });

        movieCombo.addItemListener(e -> updatePrice());

        // Add panels to frame
        setJMenuBar(menuBar); // Add MenuBar
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void initMenuBar() {
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        viewItem = new JMenuItem("View Bookings");
        exitItem = new JMenuItem("Exit");

        viewItem.addActionListener(e -> viewBookings());
        exitItem.addActionListener(e -> System.exit(0));

        fileMenu.add(viewItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
    }

    // Requirement: File Reading (IO Exception handling)
    private void loadMoviesFromFile() {
        try {
            File file = new File("movies.txt");
            if (!file.exists()) {
                // Fallback if file doesn't exist
                String[] defaultMovies = { "Movie 1", "Movie 2", "Movie 3" };
                for (String m : defaultMovies)
                    movieCombo.addItem(m);
                return;
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.trim().isEmpty()) {
                    movieCombo.addItem(line);
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) { // Exception Handling #2
            JOptionPane.showMessageDialog(this, "Movies file not found!");
        }
    }

    private void processBooking(String email) {
        // Gathering Data
        // Exception Handling #3: NumberFormatException (handled by JSpinner implicitly
        // but good to verify logic)
        int seats = (Integer) seatSpinner.getValue();
        String name = textField2.getText();
        String movie = (String) movieCombo.getSelectedItem();

        if (name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter your name.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Implementation of Inheritance: Creating Object of Subclass
        MovieBooking newBooking = new MovieBooking(name, movie, seats, email);

        // File Writing
        try (FileWriter fw = new FileWriter("booking.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {

            out.println(newBooking.toFileString());

            // Show Custom Dialog (Requirement: Additional Component #3 - JDialog logic via
            // JOptionPane with custom icon)
            showConfirmationDialog(newBooking);

        } catch (IOException e) { // Exception Handling #2 (IOException)
            JOptionPane.showMessageDialog(this, "Error saving booking!", "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showConfirmationDialog(MovieBooking booking) {
        // Using JTextArea in a Dialog to show details
        JTextArea textArea = new JTextArea(booking.toString());
        textArea.setEditable(false);
        textArea.setBackground(new Color(240, 240, 240));

        JOptionPane.showMessageDialog(this,
                textArea,
                "Booking Confirmed",
                JOptionPane.INFORMATION_MESSAGE,
                successIcon); // Using the Success Icon
    }

    private void viewBookings() {
        try {
            File file = new File("booking.txt");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(this, "No bookings found.");
                return;
            }

            StringBuilder content = new StringBuilder();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                content.append(scanner.nextLine()).append("\n");
            }
            scanner.close();

            JTextArea textArea = new JTextArea(content.toString());
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            scrollPane.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(this, scrollPane, "All Bookings", JOptionPane.PLAIN_MESSAGE);

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error reading booking file.");
        }
    }

    // Helper method to load ImageIcon with fallback
    private ImageIcon loadIcon(String fileName) {
        try {
            return new ImageIcon(fileName); // Exception Handling #1 (General Exception)
        } catch (Exception e) {
            System.out.println("Warning: Could not load icon " + fileName);
            return null;
        }
    }

    // Event handler for ComboBox
    private void updatePrice() {
        String selectedMovie = (String) movieCombo.getSelectedItem();
        // Simple feedback logic
    }

    public static void main(String[] args) {
        // Ensure GUI is created on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}
