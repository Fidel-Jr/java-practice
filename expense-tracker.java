import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class ExpenseTracker extends JFrame implements ActionListener {

    JTextField txtReceipt, txtStore, txtTotal, txtTax, txtFinal;
    JButton btnRecord, btnClear;

    JTable table;
    DefaultTableModel model;

    public ExpenseTracker() {

        setTitle("Expense Tracker");
        setSize(700,550);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel title = new JLabel("Expense Tracker");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(260,20,200,30);
        add(title);

        JLabel lblReceipt = new JLabel("Receipt Number:");
        lblReceipt.setBounds(50,70,120,25);
        add(lblReceipt);

        txtReceipt = new JTextField();
        txtReceipt.setBounds(180,70,150,25);
        add(txtReceipt);

        JLabel lblStore = new JLabel("Store Name:");
        lblStore.setBounds(50,105,120,25);
        add(lblStore);

        txtStore = new JTextField();
        txtStore.setBounds(180,105,150,25);
        add(txtStore);

        JLabel lblTotal = new JLabel("Total Cost:");
        lblTotal.setBounds(50,140,120,25);
        add(lblTotal);

        txtTotal = new JTextField();
        txtTotal.setBounds(180,140,150,25);
        add(txtTotal);

        JLabel lblTax = new JLabel("Tax (12%):");
        lblTax.setBounds(50,175,120,25);
        add(lblTax);

        txtTax = new JTextField();
        txtTax.setBounds(180,175,150,25);
        txtTax.setEditable(false);
        add(txtTax);

        JLabel lblFinal = new JLabel("Final Amount:");
        lblFinal.setBounds(50,210,120,25);
        add(lblFinal);

        txtFinal = new JTextField();
        txtFinal.setBounds(180,210,150,25);
        txtFinal.setEditable(false);
        add(txtFinal);

        btnRecord = new JButton("Record");
        btnRecord.setBounds(80,260,100,30);
        btnRecord.addActionListener(this);
        add(btnRecord);

        btnClear = new JButton("Clear");
        btnClear.setBounds(200,260,100,30);
        btnClear.addActionListener(this);
        add(btnClear);

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{
                "Receipt No", "Store", "Total", "Tax", "Final"
        });

        table = new JTable(model);
        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(350,70,300,350);
        add(pane);

        loadDataFromFile();

        setVisible(true);
    }

    public void loadDataFromFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));

            String line;
            String receipt = "", store = "", total = "", tax = "", finalAmt = "";

            while ((line = reader.readLine()) != null) {

                if (line.startsWith("Receipt No:"))
                    receipt = line.substring(12);

                else if (line.startsWith("Store Name:"))
                    store = line.substring(12);

                else if (line.startsWith("Total Cost:"))
                    total = line.substring(12);

                else if (line.startsWith("Tax:"))
                    tax = line.substring(5);

                else if (line.startsWith("Final Amount:"))
                    finalAmt = line.substring(14);

                else if (line.startsWith("----------------")) {
                    model.addRow(new Object[]{
                            receipt, store, total, tax, finalAmt
                    });
                }
            }

            reader.close();

        } catch (IOException e) {
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnRecord) {
            try {
                double totalCost = Double.parseDouble(txtTotal.getText());
                double tax = totalCost * 0.12;
                double finalAmount = totalCost + tax;

                txtTax.setText(String.format("%.2f", tax));
                txtFinal.setText(String.format("%.2f", finalAmount));

                FileWriter writer = new FileWriter("data.txt", true);
                writer.write("Receipt No: " + txtReceipt.getText() + "\n");
                writer.write("Store Name: " + txtStore.getText() + "\n");
                writer.write("Total Cost: " + totalCost + "\n");
                writer.write("Tax: " + tax + "\n");
                writer.write("Final Amount: " + finalAmount + "\n");
                writer.write("-----------------------------\n");
                writer.close();

                model.addRow(new Object[]{
                        txtReceipt.getText(),
                        txtStore.getText(),
                        totalCost,
                        tax,
                        finalAmount
                });

                JOptionPane.showMessageDialog(this, "Data Saved!");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter valid total cost!");
            }
        }

        if (e.getSource() == btnClear) {
            txtReceipt.setText("");
            txtStore.setText("");
            txtTotal.setText("");
            txtTax.setText("");
            txtFinal.setText("");
        }
    }

    public static void main(String[] args) {
        new ExpenseTracker();
    }
}
