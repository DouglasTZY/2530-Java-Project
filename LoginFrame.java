// Douglas Tan Tze Yu 1221206532 
// Joel Nithyananthan Paramananthan 1211205630
// RS4 
// Java Project - Movie Booking System 

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.File;
import java.util.Scanner;

public class LoginFrame extends JFrame {

    private JTextField inputUser;
    private JPasswordField inputPass;
    private JButton btnLogin, btnGoRegister;

    public LoginFrame() {
        setTitle("Sign In - Movie Booking");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel topPnl = new JPanel();
        topPnl.setBackground(AppStyle.MAIN_COLOR);
        JLabel headLbl = new JLabel("Member Login");
        AppStyle.styleHeader(headLbl);
        topPnl.add(headLbl);
        add(topPnl, BorderLayout.NORTH);

        JPanel midPnl = new JPanel(new GridLayout(3, 2, 10, 20));
        midPnl.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        midPnl.setBackground(AppStyle.BG_COLOR);

        JLabel l1 = new JLabel("Username:");
        JLabel l2 = new JLabel("Password:");
        AppStyle.applyLabelStyle(l1);
        AppStyle.applyLabelStyle(l2);

        inputUser = new JTextField();
        AppStyle.styleTextField(inputUser);
        inputPass = new JPasswordField();
        AppStyle.styleTextField(inputPass);

        midPnl.add(l1);
        midPnl.add(inputUser);
        midPnl.add(l2);
        midPnl.add(inputPass);

        btnGoRegister = new JButton("Register");
        AppStyle.styleButton(btnGoRegister);
        btnGoRegister.setBackground(new Color(39, 174, 96));

        btnLogin = new JButton("Login");
        AppStyle.styleButton(btnLogin);

        midPnl.add(btnGoRegister);
        midPnl.add(btnLogin);

        add(midPnl, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> executeLogin());

        btnGoRegister.addActionListener(e -> {
            new RegisterFrame();
            dispose();
        });

        setVisible(true);
    }

    private void executeLogin() {
        String uText = inputUser.getText().trim();
        String pText = new String(inputPass.getPassword());

        try {
            User validUser = verifyCredentials(uText, pText);
            if (validUser != null) {
                JOptionPane.showMessageDialog(this, "Success! Welcome back " + validUser.getUsername());
                new MainFrame(validUser);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "System error: " + ex.getMessage());
        }
    }

    private User verifyCredentials(String username, String password) throws Exception {
        File userFile = new File("users.txt");
        if (!userFile.exists())
            return null;

        Scanner s = new Scanner(userFile);
        while (s.hasNextLine()) {
            String row = s.nextLine();
            String[] data = row.split("\\|");
            if (data.length >= 3) {
                if (data[0].equalsIgnoreCase(username) && data[1].equals(password)) {
                    s.close();
                    return new User(data[0], data[1], data[2]);
                }
            }
        }
        s.close();
        return null;
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
