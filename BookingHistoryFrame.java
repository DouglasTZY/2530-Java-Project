// Group 1 
// Movie Booking System 
// Douglas Tan Tze Yu 1221206532 
// Joel Nithyananthan Paramananthan 1211205630

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.ArrayList;

public class BookingHistoryFrame extends JFrame {

    private User activeUser;
    private JTable bookingTable;
    private DefaultTableModel tableModel;
    private JButton btnExit, btnDelete;

    public BookingHistoryFrame(User u) {
        this.activeUser = u;

        setTitle("Purchase Records - " + u.getUsername());
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel topPnl = new JPanel();
        topPnl.setBackground(AppStyle.MAIN_COLOR);
        JLabel titleLbl = new JLabel("Your Booking History");
        AppStyle.styleHeader(titleLbl);
        topPnl.add(titleLbl);
        add(topPnl, BorderLayout.NORTH);

        String[] headers = { "Selected Date", "Movie Title", "Seat No", "Customer Name" };
        tableModel = new DefaultTableModel(headers, 0);
        bookingTable = new JTable(tableModel);
        bookingTable.setRowHeight(25);
        JScrollPane scroll = new JScrollPane(bookingTable);
        add(scroll, BorderLayout.CENTER);

        JPanel botPnl = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnDelete = new JButton("Delete Record");
        AppStyle.styleButton(btnDelete);
        btnDelete.setBackground(new java.awt.Color(231, 76, 60));

        btnExit = new JButton("Go Back");
        AppStyle.styleButton(btnExit);

        btnDelete.addActionListener(e -> removeSelectedRecord());
        btnExit.addActionListener(e -> dispose());

        botPnl.add(btnDelete);
        botPnl.add(btnExit);
        add(botPnl, BorderLayout.SOUTH);

        loadUserBookings();

        setVisible(true);
    }

    private void loadUserBookings() {
        try {
            File f = new File("booking.txt");
            if (!f.exists())
                return;

            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String rowText = s.nextLine();

                if (rowText.contains(activeUser.getEmail())) {
                    String[] rowData = rowText.split("\\|");
                    if (rowData.length >= 4) {
                        String dateStr = rowData[0].trim();
                        String titleStr = rowData[1].trim();
                        String seatStr = rowData[2].trim();

                        int bracket = dateStr.indexOf("]");
                        if (bracket != -1) {
                            String d = dateStr.substring(0, bracket + 1);
                            String n = dateStr.substring(bracket + 1).trim();
                            tableModel.addRow(new Object[] { d, titleStr, seatStr, n });
                        }
                    }
                }
            }
            s.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to load database records!");
        }
    }

    private void removeSelectedRecord() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a booking from the table first!");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this, "Do you really want to delete this record?",
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION)
            return;

        try {
            ArrayList<String> fileLines = new ArrayList<>();
            File bookingFile = new File("booking.txt");
            Scanner s = new Scanner(bookingFile);

            String d = (String) tableModel.getValueAt(selectedRow, 0);
            String t = (String) tableModel.getValueAt(selectedRow, 1);
            String sNo = (String) tableModel.getValueAt(selectedRow, 2);
            String uName = (String) tableModel.getValueAt(selectedRow, 3);

            String targetRow = d + " " + uName + " | " + t + " | " + sNo + " | " + activeUser.getEmail();

            while (s.hasNextLine()) {
                String currentLine = s.nextLine();
                if (!currentLine.trim().equals(targetRow.trim())) {
                    fileLines.add(currentLine);
                }
            }
            s.close();

            PrintWriter pw = new PrintWriter(bookingFile);
            for (String line : fileLines) {
                pw.println(line);
            }
            pw.close();

            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Booking record removed successfully.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error during deletion: " + ex.getMessage());
        }
    }
}
