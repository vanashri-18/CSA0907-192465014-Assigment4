import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class LibrarySearchScreen extends JFrame {

    JTextField searchField = new JTextField(20);

    JButton searchButton = new JButton("Search");
    JButton clearButton = new JButton("Clear");

    JLabel status = new JLabel("Enter a book title to search");

    DefaultTableModel model;

    String[][] books = {
        {"Java Programming", "James Gosling", "Available"},
        {"Python Basics", "Guido Rossum", "Available"},
        {"Computer Networks", "Andrew Tanenbaum", "Issued"},
        {"Cyber Security", "William Stallings", "Available"},
        {"Data Structures", "Mark Allen", "Issued"}
    };

    public LibrarySearchScreen() {

        setTitle("Campus Library Search Screen");
        setLayout(new BorderLayout(10, 10));

        JLabel heading = new JLabel(
            "CAMPUS LIBRARY SEARCH",
            JLabel.CENTER
        );

        heading.setFont(new Font("Arial", Font.BOLD, 22));

        JPanel header = new JPanel(new BorderLayout());
        header.add(heading, BorderLayout.CENTER);

        add(header, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);

        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(new JLabel("Book Title:"), gbc);

        gbc.gridx = 1;
        searchPanel.add(searchField, gbc);

        gbc.gridx = 2;
        searchPanel.add(searchButton, gbc);

        gbc.gridx = 3;
        searchPanel.add(clearButton, gbc);

        model = new DefaultTableModel(
            new String[]{"Book Title", "Author", "Status"}, 0
        );

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel centerPanel = new JPanel(new BorderLayout());

        centerPanel.add(searchPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        JPanel footer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footer.add(status);

        add(footer, BorderLayout.SOUTH);

        displayBooks();

        searchButton.addActionListener(e -> searchBooks());

        searchField.addActionListener(e -> searchBooks());

        clearButton.addActionListener(e -> {
            searchField.setText("");
            displayBooks();
            status.setText("Search cleared");
        });

        setSize(650, 400);
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void displayBooks() {
        model.setRowCount(0);

        for (String[] book : books) {
            model.addRow(book);
        }
    }

    void searchBooks() {
        String keyword = searchField.getText()
            .trim().toLowerCase();

        model.setRowCount(0);

        int count = 0;

        for (String[] book : books) {
            if (book[0].toLowerCase().contains(keyword)) {
                model.addRow(book);
                count++;
            }
        }

        status.setText(count + " book(s) found");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(
            () -> new LibrarySearchScreen()
        );
    }
}