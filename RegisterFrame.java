import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.Scanner;

public class RegisterFrame extends JFrame {

    private JTextField txtUser, txtEmail;
    private JPasswordField txtPass, txtConfirm;
    private JButton btnRegister, btnBack;

    public RegisterFrame() {
        setTitle("Create Account");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null); // Center window

        // Title Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(UIHelper.COLOR_PRIMARY);
        JLabel lblTitle = new JLabel("Register New User");
        UIHelper.styleHeader(lblTitle);
        topPanel.add(lblTitle);
        add(topPanel, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        formPanel.setBackground(UIHelper.COLOR_BG);

        // Components
        JLabel l1 = new JLabel("Username:");
        JLabel l2 = new JLabel("Email:");
        JLabel l3 = new JLabel("Password:");
        JLabel l4 = new JLabel("Confirm Password:");

        UIHelper.makeTitle(l1);
        UIHelper.makeTitle(l2);
        UIHelper.makeTitle(l3);
        UIHelper.makeTitle(l4);

        txtUser = new JTextField();
        txtEmail = new JTextField();
        txtPass = new JPasswordField();
        txtConfirm = new JPasswordField();

        UIHelper.styleTextField(txtUser);
        UIHelper.styleTextField(txtEmail);
        UIHelper.styleTextField(txtPass);
        UIHelper.styleTextField(txtConfirm);

        btnRegister = new JButton("Sign Up");
        UIHelper.styleButton(btnRegister);

        btnBack = new JButton("Back to Login");
        UIHelper.styleButton(btnBack);

        // Add to panel
        formPanel.add(l1);
        formPanel.add(txtUser);
        formPanel.add(l2);
        formPanel.add(txtEmail);
        formPanel.add(l3);
        formPanel.add(txtPass);
        formPanel.add(l4);
        formPanel.add(txtConfirm);
        formPanel.add(btnBack);
        formPanel.add(btnRegister);

        add(formPanel, BorderLayout.CENTER);

        // Events
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doRegister();
            }
        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame(); // Go back
                dispose();
            }
        });

        setVisible(true);
    }

    private void doRegister() {
        String user = txtUser.getText().trim();
        String mail = txtEmail.getText().trim();
        String p1 = new String(txtPass.getPassword());
        String p2 = new String(txtConfirm.getPassword());

        // Basic Validation
        if (user.isEmpty() || mail.isEmpty() || p1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        if (!p1.equals(p2)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!");
            return;
        }

        // Check availability
        if (checkUserExists(user)) {
            JOptionPane.showMessageDialog(this, "Username already taken!");
            return;
        }

        // Save
        try {
            // Append to file
            FileWriter fw = new FileWriter("users.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            // Format: user|pass|email
            pw.println(user + "|" + p1 + "|" + mail);

            pw.close();
            bw.close();
            fw.close();

            JOptionPane.showMessageDialog(this, "Account Created! Please Login.");
            new LoginFrame();
            dispose();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving user: " + e.getMessage());
        }
    }

    private boolean checkUserExists(String username) {
        try {
            File f = new File("users.txt");
            if (!f.exists())
                return false;

            Scanner sc = new Scanner(f);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                String[] parts = line.split("\\|");
                if (parts.length >= 1) {
                    if (parts[0].equalsIgnoreCase(username)) {
                        sc.close();
                        return true;
                    }
                }
            }
            sc.close();
        } catch (Exception e) {
            // ignore
        }
        return false;
    }
}
