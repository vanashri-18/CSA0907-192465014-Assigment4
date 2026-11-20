import java.sql.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RegistrationService {

    private String password;

    public RegistrationService(String password) {
        this.password = password;
    }

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/campus_event_registration";
        return DriverManager.getConnection(url, "root", password);
    }

    public void registerStudent(Registration r) throws SQLException {
        String sql = "INSERT INTO registrations " +
            "(student_name, register_number, email, event_name) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, r.getStudentName());
            ps.setString(2, r.getRegisterNumber());
            ps.setString(3, r.getEmail());
            ps.setString(4, r.getEventName());

            ps.executeUpdate();
        }
    }

    public int getTotalCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM registrations";

        try (Connection con = getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            rs.next();
            return rs.getInt(1);
        }
    }

    public List<Registration> getRegistrations(int page, int pageSize)
            throws SQLException {

        List<Registration> list = new ArrayList<>();

        String sql = "SELECT student_name, register_number, email, event_name " +
                     "FROM registrations ORDER BY id LIMIT ? OFFSET ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, pageSize);
            ps.setInt(2, page * pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Registration r = new Registration(
                        rs.getString("student_name"),
                        rs.getString("register_number"),
                        rs.getString("email"),
                        rs.getString("event_name")
                    );

                    list.add(r);
                }
            }
        }

        return list;
    }
}