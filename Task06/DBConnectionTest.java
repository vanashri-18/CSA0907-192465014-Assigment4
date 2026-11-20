import java.sql.Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JPasswordField;
import javax.swing.JOptionPane;

public class DBConnectionTest {
    public static void main(String[] args) {
        JPasswordField passwordField = new JPasswordField();

        int option = JOptionPane.showConfirmDialog(
            null,
            passwordField,
            "Enter MySQL Root Password",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (option != JOptionPane.OK_OPTION) {
            System.out.println("Connection cancelled.");
            return;
        }

        String password = new String(passwordField.getPassword());

        String url = "jdbc:mysql://localhost:3306/campus_event_registration";

        try {
            Connection con = DriverManager.getConnection(
                url, "root", password
            );

            System.out.println("MySQL Connected Successfully!");

            con.close();
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e.getMessage());
        }
    }
}