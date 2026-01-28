import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class BookingHistoryFrame extends JFrame {

    private User currentUser;
    private JTable table;
    private DefaultTableModel model;
    private JButton btnClose;

    public BookingHistoryFrame(User user) {
        this.currentUser = user;

        setTitle("My Booking History - " + user.getUsername());
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close this, not app
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        // Header
        JPanel topPanel = new JPanel();
        topPanel.setBackground(UIHelper.COLOR_PRIMARY);
        JLabel lblHeader = new JLabel("Your Bookings");
        UIHelper.styleHeader(lblHeader);
        topPanel.add(lblHeader);
        add(topPanel, BorderLayout.NORTH);

        // Table
        String[] columns = { "Date", "Movie", "Seat", "Passenger" };
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom
        JPanel botPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnClose = new JButton("Close");
        UIHelper.styleButton(btnClose);

        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        botPanel.add(btnClose);
        add(botPanel, BorderLayout.SOUTH);

        // Load Data
        loadBookings();

        setVisible(true);
    }

    private void loadBookings() {
        try {
            File f = new File("booking.txt");
            if (!f.exists())
                return;

            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // Format: [2025-01-27 22:56] Douglas | Avengers | Seat: 5 | douglas@mail.com
                // We need to parse this messy string or just check if it contains the email

                // Simple Check: Does the line contain the user's email?
                if (line.contains(currentUser.getEmail())) {
                    // Try to make it look nice in table
                    // Split by "|"
                    String[] parts = line.split("\\|");
                    if (parts.length >= 4) {
                        // Part 0: [Date] Name
                        // Part 1: Movie
                        // Part 2: Seat: X
                        // Part 3: Email

                        String p0 = parts[0].trim();
                        String date = p0;
                        String passenger = "";

                        // Extract date vs name from "[Date] Name"
                        int closeBracket = p0.indexOf("]");
                        if (closeBracket != -1) {
                            date = p0.substring(0, closeBracket + 1);
                            passenger = p0.substring(closeBracket + 1).trim();
                        }

                        String movie = parts[1].trim();
                        String seat = parts[2].trim();

                        model.addRow(new Object[] { date, movie, seat, passenger });
                    }
                }
            }
            sc.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading history.");
        }
    }
}
