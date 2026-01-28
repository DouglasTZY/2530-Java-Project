// Douglas Tan Tze Yu 1221206532 
// Joel Nithyananthan Paramananthan 1211205630
// RS4 
// Java Project - Movie Booking System 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

// Main window for our booking application
public class MainFrame extends JFrame {

    private User currentUser; // The user logged in

    // Components
    private JLabel lblSeat, lblName, lblMovie, lblEmail;
    private JTextField txtName, txtEmail;
    private JSpinner spinSeat; // For selecting seat number (1-100)
    private JComboBox<String> comboMovies;
    private JButton btnBook;

    // Menu components
    private JMenuBar menuBar;
    private JMenu menuFile, menuAccount;
    private JMenuItem itemBookings, itemExit, itemLogout;

    // Images
    private ImageIcon iconPoster, iconTicket, iconSuccess;

    // Modified Constructor to accept User
    public MainFrame(User user) {
        this.currentUser = user;

        // Basic Frame settings
        setTitle("Movie Booking System - " + user.getUsername());
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center it

        // 1. Setup the Menu Bar
        setupMenu();
        setJMenuBar(menuBar);

        // 2. Load Images (Scaled)
        iconPoster = loadImage("poster.png", 100, 150);
        iconTicket = loadImage("ticket.png", 30, 30);
        iconSuccess = loadImage("success.png", 40, 40);

        // 3. Create Top Header Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(UIHelper.COLOR_PRIMARY); // Blue Background
        JLabel lblHeader = new JLabel("Welcome, " + user.getUsername(), iconPoster, JLabel.CENTER);
        UIHelper.styleHeader(lblHeader); // White text, big font
        topPanel.add(lblHeader);
        add(topPanel, BorderLayout.NORTH);

        // 4. Create Center Panel for Form
        // Grid layout with 5 rows, 2 columns, and gaps
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(UIHelper.COLOR_BG); // Soft Grey Background
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40)); // More padding

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
        UIHelper.styleTextField(txtName);
        txtName.setText(user.getUsername()); // Pre-fill name

        txtEmail = new JTextField();
        UIHelper.styleTextField(txtEmail);
        txtEmail.setText(user.getEmail()); // Pre-fill email
        txtEmail.setEditable(false); // ID cannot be changed

        // Movie dropdown - load from file
        comboMovies = new JComboBox<>();
        UIHelper.styleComboBox(comboMovies);
        readMovies();

        // Book Button
        btnBook = new JButton("Book Ticket", iconTicket);
        UIHelper.styleButton(btnBook);

        // Add everything to the grid
        formPanel.add(lblName);
        formPanel.add(txtName);

        formPanel.add(lblMovie);
        formPanel.add(comboMovies);

        formPanel.add(lblSeat);
        formPanel.add(spinSeat);

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
        menuAccount = new JMenu("Account");

        itemBookings = new JMenuItem("My Booking History");
        itemLogout = new JMenuItem("Logout");
        itemExit = new JMenuItem("Exit");

        // Action to view bookings (New Window)
        itemBookings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookingHistoryFrame(currentUser);
            }
        });

        // Action to logout
        itemLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showConfirmDialog(MainFrame.this, "Are you sure you want to logout?", "Logout",
                        JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    new LoginFrame();
                    dispose();
                }
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

        menuAccount.add(itemLogout);

        menuBar.add(menuFile);
        menuBar.add(menuAccount);
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
                JOptionPane.showMessageDialog(this, "Please enter passenger name!");
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

            JOptionPane.showMessageDialog(this, area, "Booking Success!", JOptionPane.INFORMATION_MESSAGE, iconSuccess);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving to file: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Something went wrong: " + e.getMessage());
        }
    }

    // Helper to load image safely and scale it
    private ImageIcon loadImage(String path, int width, int height) {
        try {
            ImageIcon originalIcon = new ImageIcon(path);
            Image originalImage = originalIcon.getImage();
            Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.out.println("Image not found: " + path);
            return null;
        }
    }
}
