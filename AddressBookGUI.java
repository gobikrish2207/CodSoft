import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddressBookGUI extends JFrame {
    private List<Contact> contacts = new ArrayList<>();

    private JTextField nameField, phoneField, emailField;
    private JTextArea displayArea;

    public AddressBookGUI() {
        setTitle("Address Book");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone:");
        phoneField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);

        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });

        JButton removeButton = new JButton("Remove Contact");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeContact();
            }
        });

        JButton searchButton = new JButton("Search Contact");
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchContact();
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

    private void addContact() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.");
            return;
        }

        Contact contact = new Contact(name, phone, email);
        contacts.add(contact);

        displayContacts();
        clearFields();
    }

    private void removeContact() {
        String name = nameField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a name to remove.");
            return;
        }

        Contact toRemove = null;
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                toRemove = contact;
                break;
            }
        }

        if (toRemove != null) {
            contacts.remove(toRemove);
            displayContacts();
            clearFields();
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found.");
        }
    }

    private void searchContact() {
        String name = nameField.getText();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a name to search.");
            return;
        }

        Contact foundContact = null;
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                foundContact = contact;
                break;
            }
        }

        if (foundContact != null) {
            displayArea.setText("Name: " + foundContact.getName() + "\nPhone: " +
                    foundContact.getPhone() + "\nEmail: " + foundContact.getEmail());
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found.");
        }
    }

    private void displayContacts() {
        displayArea.setText("");
        for (Contact contact : contacts) {
            displayArea.append("Name: " + contact.getName() + "\nPhone: " +
                    contact.getPhone() + "\nEmail: " + contact.getEmail() + "\n\n");
        }
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                AddressBookGUI app = new AddressBookGUI();
                app.setVisible(true);
            }
        });
    }
}

class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
