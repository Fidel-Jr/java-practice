package Colinares;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.*;

public class AccountManagement extends JFrame {

    JTable table;
    DefaultTableModel model;

    public AccountManagement() {

        setTitle("Account Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // center screen

        setLayout(null);

        // ===== TABLE =====
        model = new DefaultTableModel();
        model.addColumn("Username");
        model.addColumn("Password");
        model.addColumn("Full Name");

        table = new JTable(model);
        table.setRowHeight(22);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 20, 540, 200);

        // ===== BUTTONS =====
        JButton load = new JButton("Load");
        JButton addBtn = new JButton("Add");
        JButton delete = new JButton("Delete");
        JButton update = new JButton("Update");
        JButton logout = new JButton("Logout");

        // button row layout (clean alignment)
        load.setBounds(20, 240, 100, 30);
        addBtn.setBounds(130, 240, 100, 30);
        delete.setBounds(240, 240, 100, 30);
        update.setBounds(350, 240, 100, 30);
        logout.setBounds(460, 300, 100, 30);

        // add components
        add(sp);
        add(load);
        add(addBtn);
        add(delete);
        add(update);
        add(logout);

        // ===== ACTIONS =====
        load.addActionListener(e -> loadFile());
        addBtn.addActionListener(e -> openAddFrame());
        delete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                model.removeRow(row);
                saveToFile();
            } else {
                JOptionPane.showMessageDialog(this, "Select a row first!");
            }
        });

        update.addActionListener(e -> openUpdateFrame());

        logout.addActionListener(e -> {
            new Login();
            dispose();
        });

        setVisible(true);
    }

    // ================= LOAD FILE =================
    void loadFile() {

        model.setRowCount(0);

        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {

            String line;

            while ((line = br.readLine()) != null) {
                model.addRow(line.split(","));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No file found");
        }
    }

    // ================= SAVE FILE =================
    void saveToFile() {

        try (FileWriter fw = new FileWriter("users.txt")) {

            for (int i = 0; i < model.getRowCount(); i++) {
                fw.write(
                        model.getValueAt(i, 0) + "," +
                        model.getValueAt(i, 1) + "," +
                        model.getValueAt(i, 2) + "\n"
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving file");
        }
    }

    // ================= ADD USER FRAME =================
    void openAddFrame() {

        JFrame f = new JFrame("Add User");
        f.setSize(300, 250);
        f.setLayout(null);
        f.setLocationRelativeTo(this);

        JLabel u1 = new JLabel("Username");
        JLabel p1 = new JLabel("Password");
        JLabel f1 = new JLabel("Full Name");

        JTextField tu = new JTextField();
        JTextField tp = new JTextField();
        JTextField tf = new JTextField();

        JButton save = new JButton("Save");

        u1.setBounds(20, 20, 100, 20);
        p1.setBounds(20, 60, 100, 20);
        f1.setBounds(20, 100, 100, 20);

        tu.setBounds(120, 20, 140, 20);
        tp.setBounds(120, 60, 140, 20);
        tf.setBounds(120, 100, 140, 20);

        save.setBounds(100, 150, 80, 30);

        f.add(u1);
        f.add(p1);
        f.add(f1);
        f.add(tu);
        f.add(tp);
        f.add(tf);
        f.add(save);

        save.addActionListener(e -> {

            String u = tu.getText();
            String p = tp.getText();
            String full = tf.getText();

            if (u.isEmpty() || p.isEmpty() || full.isEmpty()) {
                JOptionPane.showMessageDialog(f, "All fields required!");
                return;
            }

            try (FileWriter fw = new FileWriter("users.txt", true)) {

                fw.write(u + "," + p + "," + full + "\n");

                loadFile();

                JOptionPane.showMessageDialog(f, "User Added!");
                f.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(f, "Error saving file");
            }
        });

        f.setVisible(true);
    }

    // ================= UPDATE USER FRAME =================
    void openUpdateFrame() {

        int row = table.getSelectedRow();

        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Select a row first!");
            return;
        }

        JFrame f = new JFrame("Update User");
        f.setSize(300, 250);
        f.setLayout(null);
        f.setLocationRelativeTo(this);

        JLabel u1 = new JLabel("Username");
        JLabel p1 = new JLabel("Password");
        JLabel f1 = new JLabel("Full Name");

        JTextField tu = new JTextField((String) model.getValueAt(row, 0));
        JTextField tp = new JTextField((String) model.getValueAt(row, 1));
        JTextField tf = new JTextField((String) model.getValueAt(row, 2));

        JButton save = new JButton("Update");

        u1.setBounds(20, 20, 100, 20);
        p1.setBounds(20, 60, 100, 20);
        f1.setBounds(20, 100, 100, 20);

        tu.setBounds(120, 20, 140, 20);
        tp.setBounds(120, 60, 140, 20);
        tf.setBounds(120, 100, 140, 20);

        save.setBounds(90, 150, 100, 30);

        f.add(u1);
        f.add(p1);
        f.add(f1);
        f.add(tu);
        f.add(tp);
        f.add(tf);
        f.add(save);

        save.addActionListener(e -> {

            String u = tu.getText();
            String p = tp.getText();
            String full = tf.getText();

            if (u.isEmpty() || p.isEmpty() || full.isEmpty()) {
                JOptionPane.showMessageDialog(f, "All fields required!");
                return;
            }

            model.setValueAt(u, row, 0);
            model.setValueAt(p, row, 1);
            model.setValueAt(full, row, 2);

            saveToFile();

            JOptionPane.showMessageDialog(f, "User Updated!");
            f.dispose();
        });

        f.setVisible(true);
    }
}
