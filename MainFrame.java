// Douglas Tan Tze Yu 1221206532 
// Joel Nithyananthan Paramananthan 1211205630
// RS4 
// Java Project - Movie Booking System 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Calendar;
import java.text.SimpleDateFormat;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class MainFrame extends JFrame {

    private User loggedInUser;

    private JLabel lblSeat, lblName, lblMovie, lblEmail, lblDate;
    private JTextField txtNameField, txtEmailField;
    private JSpinner spinSeatField;
    private JComboBox<String> comboMovieField, comboDateField;
    private JButton btnConfirmBooking, btnViewHistory;

    private JMenuBar mainMenuBar;
    private JMenu optionMenu, accountMenu;
    private JMenuItem itemHistory, itemExit, itemLogOut;

    private ImageIcon posterIcon, ticketIcon, successIcon;

    public MainFrame(User u) {
        this.loggedInUser = u;

        setTitle("Movie Booking App - " + u.getUsername());
        setSize(650, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        initializeMenu();
        setJMenuBar(mainMenuBar);

        posterIcon = getResizedImage("poster.png", 100, 150);
        ticketIcon = getResizedImage("ticket.png", 30, 30);
        successIcon = getResizedImage("success.png", 40, 40);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(AppStyle.MAIN_COLOR);
        JLabel headerLabel = new JLabel("Book Your Tickets", posterIcon, JLabel.CENTER);
        AppStyle.styleHeader(headerLabel);
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(7, 2, 10, 15));
        centerPanel.setBackground(AppStyle.BG_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        lblName = new JLabel("Full Name:");
        lblMovie = new JLabel("Movie:");
        lblDate = new JLabel("Date:");
        lblSeat = new JLabel("Seat:");
        lblEmail = new JLabel("Email:");

        AppStyle.applyLabelStyle(lblName);
        AppStyle.applyLabelStyle(lblMovie);
        AppStyle.applyLabelStyle(lblDate);
        AppStyle.applyLabelStyle(lblSeat);
        AppStyle.applyLabelStyle(lblEmail);

        txtNameField = new JTextField(u.getUsername());
        AppStyle.styleTextField(txtNameField);

        txtEmailField = new JTextField(u.getEmail());
        AppStyle.styleTextField(txtEmailField);
        txtEmailField.setEditable(false);

        spinSeatField = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        comboMovieField = new JComboBox<>();
        AppStyle.styleComboBox(comboMovieField);
        loadMoviesList();

        comboDateField = new JComboBox<>();
        AppStyle.styleComboBox(comboDateField);
        setupDateOptions();

        btnConfirmBooking = new JButton("Book Seat Now", ticketIcon);
        AppStyle.styleButton(btnConfirmBooking);

        btnViewHistory = new JButton("View Previous Bookings");
        AppStyle.styleButton(btnViewHistory);
        btnViewHistory.setBackground(new Color(46, 204, 113));

        centerPanel.add(lblName);
        centerPanel.add(txtNameField);
        centerPanel.add(lblMovie);
        centerPanel.add(comboMovieField);
        centerPanel.add(lblDate);
        centerPanel.add(comboDateField);
        centerPanel.add(lblSeat);
        centerPanel.add(spinSeatField);
        centerPanel.add(lblEmail);
        centerPanel.add(txtEmailField);
        centerPanel.add(btnViewHistory);
        centerPanel.add(btnConfirmBooking);

        add(centerPanel, BorderLayout.CENTER);

        btnConfirmBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processAndSaveBooking();
            }
        });

        btnViewHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookingHistoryFrame(loggedInUser);
            }
        });

        txtNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                String val = txtNameField.getText();
                if (val != null && !val.isEmpty()) {
                    txtNameField.setText(val.toUpperCase());
                }
            }
        });

        setVisible(true);
    }

    private void initializeMenu() {
        mainMenuBar = new JMenuBar();
        optionMenu = new JMenu("System Options");
        accountMenu = new JMenu("User Account");

        itemHistory = new JMenuItem("My Purchase History");
        itemLogOut = new JMenuItem("Sign Out");
        itemExit = new JMenuItem("Exit Application");

        itemHistory.addActionListener(e -> new BookingHistoryFrame(loggedInUser));

        itemLogOut.addActionListener(e -> {
            int reply = JOptionPane.showConfirmDialog(this, "Are you sure you want to sign out?", "Confirm Log Out",
                    JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                new LoginFrame();
                dispose();
            }
        });

        itemExit.addActionListener(e -> System.exit(0));

        optionMenu.add(itemHistory);
        optionMenu.addSeparator();
        optionMenu.add(itemExit);
        accountMenu.add(itemLogOut);

        mainMenuBar.add(optionMenu);
        mainMenuBar.add(accountMenu);
    }

    private void setupDateOptions() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd (EEEE)");
        Calendar cal = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            comboDateField.addItem(sdf.format(cal.getTime()));
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }
    }

    private void loadMoviesList() {
        try {
            File movieFile = new File("movies.txt");
            if (movieFile.exists()) {
                Scanner s = new Scanner(movieFile);
                while (s.hasNextLine()) {
                    String row = s.nextLine().trim();
                    if (!row.isEmpty())
                        comboMovieField.addItem(row);
                }
                s.close();
            } else {
                comboMovieField.addItem("No Movies Found");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error reading movie source file.");
        }
    }

    private void processAndSaveBooking() {
        try {
            String nameVal = txtNameField.getText().trim();
            String movieVal = (String) comboMovieField.getSelectedItem();
            String dateVal = (String) comboDateField.getSelectedItem();
            int seatVal = (Integer) spinSeatField.getValue();
            String emailVal = txtEmailField.getText();

            if (nameVal.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a passenger name first!");
                return;
            }

            MovieBooking myTicket = new MovieBooking(nameVal, movieVal, seatVal, emailVal, dateVal);

            FileWriter fw = new FileWriter("booking.txt", true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(myTicket.getSaveLine());
            pw.close();
            fw.close();

            JTextArea summary = new JTextArea(myTicket.toString());
            summary.setEditable(false);
            summary.setBackground(new Color(240, 240, 240));
            JOptionPane.showMessageDialog(this, summary, "Ticket Booked!", JOptionPane.INFORMATION_MESSAGE,
                    successIcon);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Save error: " + ex.getMessage());
        }
    }

    private ImageIcon getResizedImage(String file, int w, int h) {
        try {
            ImageIcon icon = new ImageIcon(file);
            Image original = icon.getImage();
            Image scaled = original.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            return new ImageIcon(scaled);
        } catch (Exception e) {
            return null;
        }
    }
}
