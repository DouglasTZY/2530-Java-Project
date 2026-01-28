// Douglas Tan Tze Yu 1221206532 
// Joel Nithyananthan Paramananthan 1211205630
// RS4 
// Java Project - Movie Booking System 

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

public class LoginFrame extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private JButton btnLogin, btnRegister;

    public LoginFrame() {
        setTitle("Login - Movie Booking");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center on screen

        // Build UI

        // 1. Header (Logo area)
        JPanel topPanel = new JPanel();
        topPanel.setBackground(UIHelper.COLOR_PRIMARY);
        JLabel lblTitle = new JLabel("Member Login");
        UIHelper.styleHeader(lblTitle);
        topPanel.add(lblTitle);
        add(topPanel, BorderLayout.NORTH);

        // 2. Form
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        centerPanel.setBackground(UIHelper.COLOR_BG);

        JLabel l1 = new JLabel("Username:");
        JLabel l2 = new JLabel("Password:");
        UIHelper.makeTitle(l1);
        UIHelper.makeTitle(l2);

        txtUser = new JTextField();
        UIHelper.styleTextField(txtUser);
        txtPass = new JPasswordField();
        UIHelper.styleTextField(txtPass);

        centerPanel.add(l1);
        centerPanel.add(txtUser);
        centerPanel.add(l2);
        centerPanel.add(txtPass);

        // Buttons Panel (Nested for better layout)
        JPanel btnPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        btnPanel.setBackground(UIHelper.COLOR_BG); // match parent

        btnLogin = new JButton("Login");
        UIHelper.styleButton(btnLogin);

        btnRegister = new JButton("Register");
        UIHelper.styleButton(btnRegister);
        btnRegister.setBackground(new Color(39, 174, 96)); // Green for register

        centerPanel.add(btnRegister);
        centerPanel.add(btnLogin);

        add(centerPanel, BorderLayout.CENTER);

        // Events
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame();
                dispose(); // Close login window
            }
        });

        setVisible(true);
    }

    private void doLogin() {
        String u = txtUser.getText().trim();
        String p = new String(txtPass.getPassword());

        User foundUser = authenticate(u, p);
        if (foundUser != null) {
            // Success!
            JOptionPane.showMessageDialog(this, "Welcome back, " + foundUser.getUsername() + "!");
            // Open Main App
            new MainFrame(foundUser);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Username or Password!");
        }
    }

    private User authenticate(String username, String password) {
        try {
            File f = new File("users.txt");
            if (!f.exists())
                return null;

            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                // Format: user|pass|email
                String[] parts = line.split("\\|");
                if (parts.length >= 3) {
                    String fileUser = parts[0];
                    String filePass = parts[1];
                    String fileEmail = parts[2];

                    if (fileUser.equalsIgnoreCase(username) && filePass.equals(password)) {
                        sc.close();
                        return new User(fileUser, filePass, fileEmail);
                    }
                }
            }
            sc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Main method for starting the app (New Entry Point)
    public static void main(String[] args) {
        // Optional: Set simple Look and Feel
        try {
            // javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }

        new LoginFrame();
    }
}
