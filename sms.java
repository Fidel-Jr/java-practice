import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;

public class SMS extends JFrame {

    JTextField id, name, age, dob, nationality, contact, email, department, job;
    JComboBox<String> civil;
    JRadioButton male, female;
    JTable table;
    DefaultTableModel model;
    String file = "employees.txt";

    public SMS() {
        setTitle("Employee Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ================= FORM PANEL (MATCH IMAGE STYLE) =================
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        id = new JTextField();
        name = new JTextField();
        age = new JTextField();
        dob = new JTextField();
        nationality = new JTextField();
        contact = new JTextField();
        email = new JTextField();
        department = new JTextField();
        job = new JTextField();

        civil = new JComboBox<>(new String[]{"Single","Married","Widowed","Separated","Divorced"});

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        ButtonGroup bg = new ButtonGroup();
        bg.add(male);
        bg.add(female);

        // LEFT COLUMN
        addField(form, gbc, 0, 0, "Employee ID", id);
        addField(form, gbc, 0, 1, "Gender", createGenderPanel());
        addField(form, gbc, 0, 2, "Civil Status", civil);
        addField(form, gbc, 0, 3, "Department", department);
        addField(form, gbc, 0, 4, "Nationality", nationality);

        // RIGHT COLUMN
        addField(form, gbc, 1, 0, "Age", age);
        addField(form, gbc, 1, 1, "Full Name", name);
        addField(form, gbc, 1, 2, "Contact Number", contact);
        addField(form, gbc, 1, 3, "Date of Birth", dob);
        addField(form, gbc, 1, 4, "Email", email);
        addField(form, gbc, 1, 5, "Job Title/Position", job);

        // ================= BUTTONS (REPOSITIONED AS REQUESTED) =================
        JButton add = new JButton("Add Employee");
        JButton update = new JButton("Update Employee");
        JButton delete = new JButton("Delete Employee");

        JPanel btnPanel = new JPanel(new GridLayout(3,1,5,5));
        btnPanel.add(add);       // under nationality
        btnPanel.add(update);    // under email
        btnPanel.add(delete);    // under job title

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        form.add(btnPanel, gbc);

        // ================= TABLE =================
        model = new DefaultTableModel(new String[]{
                "Employee ID","Full Name","Birth","Age","Civil Status",
                "Nationality","Gender","Contact","Email","Department","Job Title"
        },0);

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(form, BorderLayout.NORTH);

        // ================= ACTIONS =================
        add.addActionListener(e -> addEmployee());
        update.addActionListener(e -> updateEmployee());
        delete.addActionListener(e -> deleteEmployee());

        loadData();
        setVisible(true);
    }

    // helper for layout
    void addField(JPanel panel, GridBagConstraints gbc, int x, int y, String label, Component field) {
        gbc.gridx = x*2;
        gbc.gridy = y;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = x*2 + 1;
        panel.add(field, gbc);
    }

    JPanel createGenderPanel() {
        JPanel p = new JPanel();
        p.add(male);
        p.add(female);
        return p;
    }

    // ================= ADD =================
    void addEmployee() {
        String gender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "";

        String data = id.getText().trim() + "#" +
                name.getText().trim() + "#" +
                dob.getText().trim() + "#" +
                age.getText().trim() + "#" +
                civil.getSelectedItem() + "#" +
                nationality.getText().trim() + "#" +
                gender + "#" +
                contact.getText().trim() + "#" +
                email.getText().trim() + "#" +
                department.getText().trim() + "#" +
                job.getText().trim();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file,true))) {
            bw.write(data);
            bw.newLine();
        } catch (Exception ex) { ex.printStackTrace(); }

        model.addRow(data.split("#"));
        clearFields();
    }

    // ================= UPDATE =================
    void updateEmployee() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this,"Select a row to update.");
            return;
        }

        String gender = male.isSelected() ? "Male" : female.isSelected() ? "Female" : "";

        model.setValueAt(id.getText(), row, 0);
        model.setValueAt(name.getText(), row, 1);
        model.setValueAt(dob.getText(), row, 2);
        model.setValueAt(age.getText(), row, 3);
        model.setValueAt(civil.getSelectedItem(), row, 4);
        model.setValueAt(nationality.getText(), row, 5);
        model.setValueAt(gender, row, 6);
        model.setValueAt(contact.getText(), row, 7);
        model.setValueAt(email.getText(), row, 8);
        model.setValueAt(department.getText(), row, 9);
        model.setValueAt(job.getText(), row, 10);

        saveAllData();
    }

    // ================= DELETE =================
    void deleteEmployee() {
        int row = table.getSelectedRow();
        if (row == -1) return;

        model.removeRow(row);
        saveAllData();
    }

    // ================= SAVE =================
    void saveAllData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (int i=0;i<model.getRowCount();i++) {
                StringBuilder sb = new StringBuilder();
                for (int j=0;j<model.getColumnCount();j++) {
                    sb.append(model.getValueAt(i,j));
                    if (j<model.getColumnCount()-1) sb.append("#");
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch(Exception e){ e.printStackTrace(); }
    }

    // ================= LOAD =================
    void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                model.addRow(line.split("#"));
            }
        } catch(Exception e){}
    }

    void clearFields() {
        id.setText(""); name.setText(""); age.setText(""); dob.setText("");
        nationality.setText(""); contact.setText(""); email.setText("");
        department.setText(""); job.setText("");
    }

    public static void main(String[] args) {
        new SMS();
    }
}
