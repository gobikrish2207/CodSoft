import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementSystemGUI extends JFrame {
    private List<Student> students = new ArrayList<>();

    private JTextField nameField, rollNumberField, gradeField;
    private JTextArea displayArea;

    public StudentManagementSystemGUI() {
        setTitle("Student Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel rollNumberLabel = new JLabel("Roll Number:");
        rollNumberField = new JTextField();
        JLabel gradeLabel = new JLabel("Grade:");
        gradeField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(rollNumberLabel);
        inputPanel.add(rollNumberField);
        inputPanel.add(gradeLabel);
        inputPanel.add(gradeField);

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        JButton removeButton = new JButton("Remove Student");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeStudent();
            }
        });

        JButton searchButton = new JButton("Search Student");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchStudent();
            }
        });

        inputPanel.add(addButton);
        inputPanel.add(removeButton);
        inputPanel.add(searchButton);

        displayArea = new JTextArea();
        displayArea.setEditable(false);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        add(mainPanel);
    }

    private void addStudent() {
        String name = nameField.getText();
        String rollNumberStr = rollNumberField.getText();
        String gradeStr = gradeField.getText();

        if (name.isEmpty() || rollNumberStr.isEmpty() || gradeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.");
            return;
        }

        try {
            int rollNumber = Integer.parseInt(rollNumberStr);
            double grade = Double.parseDouble(gradeStr);

            Student student = new Student(name, rollNumber, grade);
            students.add(student);

            displayStudents();
            clearFields();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid roll number or grade format.");
        }
    }

    private void removeStudent() {
        String rollNumberStr = rollNumberField.getText();

        if (rollNumberStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a roll number to remove.");
            return;
        }

        try {
            int rollNumber = Integer.parseInt(rollNumberStr);
            boolean removed = students.removeIf(student -> student.getRollNumber() == rollNumber);

            if (removed) {
                displayStudents();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid roll number format.");
        }
    }

    private void searchStudent() {
        String rollNumberStr = rollNumberField.getText();

        if (rollNumberStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a roll number to search.");
            return;
        }

        try {
            int rollNumber = Integer.parseInt(rollNumberStr);
            Student foundStudent = null;

            for (Student student : students) {
                if (student.getRollNumber() == rollNumber) {
                    foundStudent = student;
                    break;
                }
            }

            if (foundStudent != null) {
                displayArea.setText("Name: " + foundStudent.getName() + "\nRoll Number: " +
                        foundStudent.getRollNumber() + "\nGrade: " + foundStudent.getGrade());
            } else {
                displayArea.setText("Student not found.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid roll number format.");
        }
    }

    private void displayStudents() {
        displayArea.setText("");
        for (Student student : students) {
            displayArea.append("Name: " + student.getName() + "\nRoll Number: " +
                    student.getRollNumber() + "\nGrade: " + student.getGrade() + "\n\n");
        }
    }

    private void clearFields() {
        nameField.setText("");
        rollNumberField.setText("");
        gradeField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StudentManagementSystemGUI app = new StudentManagementSystemGUI();
                app.setVisible(true);
            }
        });
    }
}

class Student {
    private String name;
    private int rollNumber;
    private double grade;

    public Student(String name, int rollNumber, double grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public double getGrade() {
        return grade;
    }
}
