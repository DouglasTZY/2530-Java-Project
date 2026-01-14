import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

// Main window for our booking application
public class MainFrame extends JFrame {

    // Components
    private JLabel lblSeat, lblName, lblMovie, lblEmail;
    private JTextField txtName, txtEmail;
    private JSpinner spinSeat; // For selecting seat number (1-100)
    private JComboBox<String> comboMovies;
    private JButton btnBook;

    // Menu components
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem itemBookings, itemExit;

    // Images
    private ImageIcon iconPoster, iconTicket, iconSuccess;

    public MainFrame() {
        // Basic Frame settings
        setTitle("Movie Booking System");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 1. Setup the Menu Bar
        setupMenu();
        setJMenuBar(menuBar);

        // 2. Load Images
        iconPoster = loadImage("poster.png");
        iconTicket = loadImage("ticket.png");
        iconSuccess = loadImage("success.png");

        // 3. Create Top Header Panel
        JPanel topPanel = new JPanel();
        JLabel lblHeader = new JLabel("Movie Booking System", iconPoster, JLabel.CENTER);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
        topPanel.add(lblHeader);
        add(topPanel, BorderLayout.NORTH);

        // 4. Create Center Panel for Form
        // Grid layout with 5 rows, 2 columns, and gaps
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding

        // initialize labels
        lblSeat = new JLabel("Seat Number:");
        lblName = new JLabel("Passenger Name:");
        lblMovie = new JLabel("Select Movie:");
        lblEmail = new JLabel("Email Address:");

        // Style labels using our helper class
        UIHelper.makeTitle(lblSeat);
        UIHelper.makeTitle(lblName);
        UIHelper.makeTitle(lblMovie);
        UIHelper.makeTitle(lblEmail);

        // Initialize inputs
        spinSeat = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // min 1, max 100
        txtName = new JTextField();
        txtEmail = new JTextField();

        // Movie dropdown - load from file
        comboMovies = new JComboBox<>();
        readMovies();

        // Book Button
        btnBook = new JButton("Book Ticket", iconTicket);
        UIHelper.styleButton(btnBook);

        // Add everything to the grid
        formPanel.add(lblSeat);
        formPanel.add(spinSeat);

        formPanel.add(lblName);
        formPanel.add(txtName);

        formPanel.add(lblMovie);
        formPanel.add(comboMovies);

        formPanel.add(lblEmail);
        formPanel.add(txtEmail);

        formPanel.add(btnBook);
        formPanel.add(new JPanel()); // Empty spacer

        add(formPanel, BorderLayout.CENTER);

        // 5. Events / Actions

        // Button Click Action
        btnBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBooking();
            }
        });

        // Focus Listener for Name field (Auto-capitalize)
        txtName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // Do nothing
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = txtName.getText();
                if (text != null && text.length() > 0) {
                    txtName.setText(text.toUpperCase());
                }
            }
        });

        // Show window
        setVisible(true);
    }

    // Initialize the menu bar
    private void setupMenu() {
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");

        itemBookings = new JMenuItem("View Bookings");
        itemExit = new JMenuItem("Exit");

        // Action to view bookings
        itemBookings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookingHistory();
            }
        });

        // Action to close app
        itemExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menuFile.add(itemBookings);
        menuFile.addSeparator(); // Line separator
        menuFile.add(itemExit);
        menuBar.add(menuFile);
    }

    // Read movies from text file
    private void readMovies() {
        try {
            File f = new File("movies.txt");
            if (f.exists()) {
                Scanner sc = new Scanner(f);
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (line.length() > 0) {
                        comboMovies.addItem(line);
                    }
                }
                sc.close();
            } else {
                comboMovies.addItem("Default Movie 1");
                comboMovies.addItem("Default Movie 2");
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Cannot find movies.txt!");
        }
    }

    // Save booking to file
    private void saveBooking() {
        try {
            // Get values
            String name = txtName.getText();
            String email = txtEmail.getText();
            String movie = (String) comboMovies.getSelectedItem();
            int seat = (Integer) spinSeat.getValue();

            // Validation
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(this, "Please enter your name!");
                return;
            }

            // Create our Object (Inheritance)
            MovieBooking myBooking = new MovieBooking(name, movie, seat, email);

            // Write to file (Append mode is true)
            FileWriter fw = new FileWriter("booking.txt", true);
            PrintWriter pw = new PrintWriter(fw);

            pw.println(myBooking.getDataForFile());

            pw.close();
            fw.close(); // Close file to save it

            // Show success message dialog
            JTextArea area = new JTextArea(myBooking.toString());
            area.setEditable(false);
            area.setBackground(new Color(230, 230, 230));

            JOptionPane.showMessageDialog(this, area, "Success!", JOptionPane.INFORMATION_MESSAGE, iconSuccess);

            // Clear fields
            txtName.setText("");
            txtEmail.setText("");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving to file: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Something went wrong: " + e.getMessage());
        }
    }

    // Read and show booking history
    private void showBookingHistory() {
        try {
            File f = new File("booking.txt");
            if (!f.exists()) {
                JOptionPane.showMessageDialog(this, "No bookings yet!");
                return;
            }

            String allText = "";
            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                allText += sc.nextLine() + "\n"; // Simple string concatenation
            }
            sc.close();

            JTextArea historyArea = new JTextArea(allText);
            JScrollPane scroll = new JScrollPane(historyArea);
            scroll.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(this, scroll, "Booking History", JOptionPane.PLAIN_MESSAGE);

        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Error reading history.");
        }
    }

    // Helper to load image safely
    private ImageIcon loadImage(String path) {
        try {
            return new ImageIcon(path);
        } catch (Exception e) {
            System.out.println("Image not found: " + path);
            return null;
        }
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}
