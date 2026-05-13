package Colinares;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Register extends JFrame {

    JTextField txtUser, txtFull;
    JPasswordField txtPass;

    public Register() {

        setTitle("Register");
        setSize(300, 220);
        setLayout(null);

        JLabel u = new JLabel("Username");
        u.setBounds(20, 20, 100, 20);

        JLabel p = new JLabel("Password");
        p.setBounds(20, 60, 100, 20);

        JLabel f = new JLabel("Full Name");
        f.setBounds(20, 100, 100, 20);

        txtUser = new JTextField();
        txtUser.setBounds(120, 20, 120, 20);

        txtPass = new JPasswordField();
        txtPass.setBounds(120, 60, 120, 20);

        txtFull = new JTextField();
        txtFull.setBounds(120, 100, 120, 20);

        JButton reg = new JButton("Register");
        JButton clear = new JButton("Clear");
        JButton back = new JButton("Back");

        reg.setBounds(20, 140, 90, 25);
        clear.setBounds(120, 140, 70, 25);
        back.setBounds(200, 140, 70, 25);

        add(u); add(p); add(f);
        add(txtUser); add(txtPass); add(txtFull);
        add(reg); add(clear); add(back);

        reg.addActionListener(e -> saveUser());

        clear.addActionListener(e -> {
            txtUser.setText("");
            txtPass.setText("");
            txtFull.setText("");
        });

        back.addActionListener(e -> {
            new Login();
            dispose();
        });

        setVisible(true);
    }

    void saveUser() {

        try {

            FileWriter fw = new FileWriter("users.txt", true);

            fw.write(
                txtUser.getText() + "," +
                new String(txtPass.getPassword()) + "," +
                txtFull.getText() + "\n"
            );

            fw.close();

            JOptionPane.showMessageDialog(this, "Registered!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving file");
        }
    }
}
