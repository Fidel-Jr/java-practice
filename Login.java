package Colinares;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Login extends JFrame {

    JTextField txtUser;
    JPasswordField txtPass;

    public Login() {

        setTitle("Login");
        setSize(300, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel u = new JLabel("Username");
        u.setBounds(20, 20, 100, 20);

        JLabel p = new JLabel("Password");
        p.setBounds(20, 60, 100, 20);

        txtUser = new JTextField();
        txtUser.setBounds(120, 20, 120, 20);

        txtPass = new JPasswordField();
        txtPass.setBounds(120, 60, 120, 20);

        JButton login = new JButton("Login");
        JButton reg = new JButton("Register");
        JButton exit = new JButton("Exit");

        login.setBounds(20, 100, 70, 25);
        reg.setBounds(100, 100, 90, 25);
        exit.setBounds(200, 100, 70, 25);

        add(u); add(p);
        add(txtUser); add(txtPass);
        add(login); add(reg); add(exit);

        login.addActionListener(e -> loginUser());
        reg.addActionListener(e -> {
            new Register();
            dispose();
        });

        exit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }

    void loginUser() {

        try {

            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            boolean found = false;

            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                if (data[0].equals(user) && data[1].equals(pass)) {
                    found = true;
                    break;
                }
            }

            br.close();

            if (found) {
                new AccountPage();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Login");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "File not found");
        }
    }

    public static void main(String[] args) {
        new Login();
    }
}
