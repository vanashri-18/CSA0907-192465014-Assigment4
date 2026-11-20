
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CampusEventRegistration extends JFrame {

    private JTextField nameField, registerField, emailField, eventField;
    private JButton registerButton, previousButton, nextButton;
    private JLabel pageLabel;
    private JTable table;
    private DefaultTableModel model;

    private RegistrationService service;
    private int currentPage = 0;
    private final int PAGE_SIZE = 5;
    private int totalPages = 0;

    public CampusEventRegistration(String password) {
        service = new RegistrationService(password);

        setTitle("Campus Event Registration System");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel heading = new JLabel(
            "CAMPUS EVENT REGISTRATION",
            SwingConstants.CENTER
        );
        heading.setFont(new Font("Arial", Font.BOLD, 22));
        mainPanel.add(heading, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 8, 8));
        nameField = new JTextField();
        registerField = new JTextField();
        emailField = new JTextField();
        eventField = new JTextField();

        formPanel.add(new JLabel("Student Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Register Number:"));
        formPanel.add(registerField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Event Name:"));
        formPanel.add(eventField);

        registerButton = new JButton("Register Student");
        formPanel.add(registerButton);

        JButton clearButton = new JButton("Clear");
        formPanel.add(clearButton);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.CENTER);

        model = new DefaultTableModel(
            new String[]{"Student Name", "Register Number", "Email", "Event"},
            0
        );

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel navigationPanel = new JPanel();
        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");
        pageLabel = new JLabel("Page 1");

        navigationPanel.add(previousButton);
        navigationPanel.add(pageLabel);
        navigationPanel.add(nextButton);

        bottomPanel.add(navigationPanel, BorderLayout.SOUTH);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        registerButton.addActionListener(e -> registerStudent());

        clearButton.addActionListener(e -> {
            nameField.setText("");
            registerField.setText("");
            emailField.setText("");
            eventField.setText("");
        });

        previousButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                loadRegistrations();
            }
        });

        nextButton.addActionListener(e -> {
            if (currentPage + 1 < totalPages) {
                currentPage++;
                loadRegistrations();
            }
        });

        loadRegistrations();
    }

    private void registerStudent() {
        String name = nameField.getText().trim();
        String regNo = registerField.getText().trim();
        String email = emailField.getText().trim();
        String event = eventField.getText().trim();

        if (name.isEmpty() || regNo.isEmpty()
                || email.isEmpty() || event.isEmpty()) {
            JOptionPane.showMessageDialog(
                this, "Please fill all fields."
            );
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            JOptionPane.showMessageDialog(
                this, "Please enter a valid email address."
            );
            return;
        }

        Registration registration =
            new Registration(name, regNo, email, event);

        registerButton.setEnabled(false);

        new SwingWorker<Void, Void>() {
            private Exception error;

            protected Void doInBackground() {
                try {
                    service.registerStudent(registration);
                } catch (Exception ex) {
                    error = ex;
                }
                return null;
            }

            protected void done() {
                registerButton.setEnabled(true);

                if (error != null) {
                    if (error instanceof SQLException
                            && ((SQLException) error).getErrorCode() == 1062) {
                        JOptionPane.showMessageDialog(
                            CampusEventRegistration.this,
                            "This student is already registered for this event."
                        );
                    } else {
                        JOptionPane.showMessageDialog(
                            CampusEventRegistration.this,
                            "Registration failed: " + error.getMessage()
                        );
                    }
                } else {
                    JOptionPane.showMessageDialog(
                        CampusEventRegistration.this,
                        "Registration successful!"
                    );

                    nameField.setText("");
                    registerField.setText("");
                    emailField.setText("");
                    eventField.setText("");

                    currentPage = 0;
                    loadRegistrations();
                }
            }
        }.execute();
    }

    private void loadRegistrations() {
        previousButton.setEnabled(false);
        nextButton.setEnabled(false);

        new SwingWorker<List<Registration>, Void>() {

            private int count;
            private Exception error;

            protected List<Registration> doInBackground() {
                try {
                    count = service.getTotalCount();
                    return service.getRegistrations(currentPage, PAGE_SIZE);
                } catch (Exception ex) {
                    error = ex;
                    return null;
                }
            }

            protected void done() {
                try {
                    if (error != null) {
                        JOptionPane.showMessageDialog(
                            CampusEventRegistration.this,
                            "Unable to load records: " + error.getMessage()
                        );
                        return;
                    }

                    List<Registration> records = get();

                    model.setRowCount(0);

                    for (Registration r : records) {
                        model.addRow(new Object[]{
                            r.getStudentName(),
                            r.getRegisterNumber(),
                            r.getEmail(),
                            r.getEventName()
                        });
                    }

                    totalPages = Math.max(
                        1, (int) Math.ceil(count / (double) PAGE_SIZE)
                    );

                    pageLabel.setText(
                        "Page " + (currentPage + 1) + " of " + totalPages
                    );

                    previousButton.setEnabled(currentPage > 0);
                    nextButton.setEnabled(currentPage + 1 < totalPages);

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        CampusEventRegistration.this,
                        "Error: " + ex.getMessage()
                    );
                }
            }
        }.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPasswordField passwordField = new JPasswordField();

            int choice = JOptionPane.showConfirmDialog(
                null,
                passwordField,
                "Enter MySQL Root Password",
                JOptionPane.OK_CANCEL_OPTION
            );

            if (choice == JOptionPane.OK_OPTION) {
                String password =
                    new String(passwordField.getPassword());

                CampusEventRegistration app =
                    new CampusEventRegistration(password);

                app.setVisible(true);
            }
        });
    }
}
