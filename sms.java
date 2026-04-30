import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Sms extends JFrame {
    JTextField id, name, age, dob, nationality, contact, email, department, job;
    JComboBox<String> civil;
    JRadioButton male, female;
    JTable table;
    DefaultTableModel model;
    String file = "employees.txt";

    public Sms() {
        setTitle("Employee Management System");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(6, 4, 10, 10));

        id = new JTextField();
        name = new JTextField();
        age = new JTextField();
        dob = new JTextField();
        nationality = new JTextField();
        contact = new JTextField();
        email = new JTextField();
        department = new JTextField();
        job = new JTextField();

        civil = new JComboBox<>(new String[]{"Single", "Married", "Widowed", "Separated", "Divorced"});

        male = new JRadioButton("Male");
        female = new JRadioButton("Female");
        ButtonGroup bg = new ButtonGroup();
        bg.add(male);
        bg.add(female);

        form.add(new JLabel("Employee ID")); form.add(id);
        form.add(new JLabel("Age")); form.add(age);
        form.add(new JLabel("Gender"));
        JPanel gPanel = new JPanel();
        gPanel.add(male); gPanel.add(female);
        form.add(gPanel);

        form.add(new JLabel("Full Name")); form.add(name);
        form.add(new JLabel("Civil Status")); form.add(civil);
        form.add(new JLabel("Contact Number")); form.add(contact);
        form.add(new JLabel("Department")); form.add(department);

        form.add(new JLabel("Date of Birth")); form.add(dob);
        form.add(new JLabel("Nationality")); form.add(nationality);
        form.add(new JLabel("Email")); form.add(email);
        form.add(new JLabel("Job Title/Position")); form.add(job);

        JButton add = new JButton("Add Employee");

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(add, BorderLayout.SOUTH);

        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{
                "Employee ID","Full Name","Birth","Age","Civil Status","Nationality","Gender",
                "Contact","Email","Department","Job Title"
        },0);

        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        add.addActionListener(e -> addEmployee());

        loadData();

        setVisible(true);
    }

    void addEmployee() {
        String gender = male.isSelected() ? "Male" : "Female";

        String data = id.getText()+"#"+name.getText()+"#"+dob.getText()+"#"+age.getText()+"#"+
                civil.getSelectedItem()+"#"+nationality.getText()+"#"+gender+"#"+
                contact.getText()+"#"+email.getText()+"#"+department.getText()+"#"+job.getText();

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(data);
            bw.newLine();
            bw.close();
        } catch(Exception ex){}

        model.addRow(data.split("#"));
        clearFields();
    }

    void loadData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while((line = br.readLine()) != null) {
                model.addRow(line.split("#"));
            }
            br.close();
        } catch(Exception ex){}
    }

    void clearFields() {
        id.setText(""); name.setText(""); age.setText(""); dob.setText("");
        nationality.setText(""); contact.setText(""); email.setText("");
        department.setText(""); job.setText("");
    }

    public static void main(String[] args) {
        new Sms();
    }
}
