// Group 1 
// Movie Booking System 
// Douglas Tan Tze Yu 1221206532 
// Joel Nithyananthan Paramananthan 1211205630

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

public class RegisterFrame extends JFrame {

    private JTextField boxUser, boxEmail;
    private JPasswordField boxPass, boxConfirm;
    private JButton btnSignUp, btnBack;

    public RegisterFrame() {
        setTitle("Join Movie System - Sign Up");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel topPnl = new JPanel();
        topPnl.setBackground(AppStyle.MAIN_COLOR);
        JLabel titleLabel = new JLabel("Create New Account");
        AppStyle.styleHeader(titleLabel);
        topPnl.add(titleLabel);
        add(topPnl, BorderLayout.NORTH);

        JPanel formPnl = new JPanel(new GridLayout(5, 2, 10, 10));
        formPnl.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPnl.setBackground(AppStyle.BG_COLOR);

        JLabel l1 = new JLabel("Username:");
        JLabel l2 = new JLabel("Email ID:");
        JLabel l3 = new JLabel("Password:");
        JLabel l4 = new JLabel("Confirm Password:");

        AppStyle.applyLabelStyle(l1);
        AppStyle.applyLabelStyle(l2);
        AppStyle.applyLabelStyle(l3);
        AppStyle.applyLabelStyle(l4);

        boxUser = new JTextField();
        boxEmail = new JTextField();
        boxPass = new JPasswordField();
        boxConfirm = new JPasswordField();

        AppStyle.styleTextField(boxUser);
        AppStyle.styleTextField(boxEmail);
        AppStyle.styleTextField(boxPass);
        AppStyle.styleTextField(boxConfirm);

        btnBack = new JButton("Cancel");
        AppStyle.styleButton(btnBack);

        btnSignUp = new JButton("Register Now");
        AppStyle.styleButton(btnSignUp);

        formPnl.add(l1);
        formPnl.add(boxUser);
        formPnl.add(l2);
        formPnl.add(boxEmail);
        formPnl.add(l3);
        formPnl.add(boxPass);
        formPnl.add(l4);
        formPnl.add(boxConfirm);
        formPnl.add(btnBack);
        formPnl.add(btnSignUp);

        add(formPnl, BorderLayout.CENTER);

        btnSignUp.addActionListener(e -> saveNewUser());

        btnBack.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }

    private void saveNewUser() {
        String u = boxUser.getText().trim();
        String e = boxEmail.getText().trim();
        String p1 = new String(boxPass.getPassword());
        String p2 = new String(boxConfirm.getPassword());

        if (u.isEmpty() || e.isEmpty() || p1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all blanks!");
            return;
        }

        if (!p1.equals(p2)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        if (checkExistingUser(u)) {
            JOptionPane.showMessageDialog(this, "This username is already taken!");
            return;
        }

        try {
            FileWriter fileWriter = new FileWriter("users.txt", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(u + "|" + p1 + "|" + e);
            printWriter.close();
            fileWriter.close();

            JOptionPane.showMessageDialog(this, "Registration successful! Please login.");
            new LoginFrame();
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database write error: " + ex.getMessage());
        }
    }

    private boolean checkExistingUser(String name) {
        try {
            File f = new File("users.txt");
            if (!f.exists())
                return false;
            Scanner s = new Scanner(f);
            while (s.hasNextLine()) {
                String row = s.nextLine();
                if (row.startsWith(name + "|")) {
                    s.close();
                    return true;
                }
            }
            s.close();
        } catch (Exception ex) {
        }
        return false;
    }
}
