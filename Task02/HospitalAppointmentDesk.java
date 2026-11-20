import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class HospitalAppointmentDesk extends JFrame {

    JTextField patientId = new JTextField();

    String[] departments = {
        "Cardiology", "Neurology", "Orthopaedics"
    };

    JComboBox<String> department =
        new JComboBox<>(departments);

    JComboBox<String> doctor = new JComboBox<>();

    JComboBox<String> timeSlot =
        new JComboBox<>(new String[]{
            "10:00 AM", "11:00 AM", "02:00 PM"
        });

    JButton book = new JButton("Book Appointment");
    JButton clear = new JButton("Clear");

    Map<String, java.util.List<String>> doctors =
        new HashMap<>();

    public HospitalAppointmentDesk() {

        setTitle("Hospital Appointment Desk");
        setLayout(new GridLayout(6, 2, 10, 10));

        doctors.put("Cardiology",
            Arrays.asList("Dr. Arun", "Dr. Priya"));

        doctors.put("Neurology",
            Arrays.asList("Dr. Kumar", "Dr. Meena"));

        doctors.put("Orthopaedics",
            Arrays.asList("Dr. Ravi", "Dr. Divya"));

        add(new JLabel("Patient ID:"));
        add(patientId);

        add(new JLabel("Department:"));
        add(department);

        add(new JLabel("Doctor:"));
        add(doctor);

        add(new JLabel("Time Slot:"));
        add(timeSlot);

        add(book);
        add(clear);

        updateDoctors();

        department.addActionListener(e -> updateDoctors());

        book.addActionListener(e -> {
            if (patientId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                    this, "Please enter Patient ID!");
            } else {
                JOptionPane.showMessageDialog(
                    this, "Appointment Booked Successfully!");
            }
        });

        clear.addActionListener(e -> {
            patientId.setText("");
            department.setSelectedIndex(0);
            timeSlot.setSelectedIndex(0);
            updateDoctors();
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(
                    HospitalAppointmentDesk.this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION
                );

                if (choice == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        setSize(450, 300);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    void updateDoctors() {
        doctor.removeAllItems();

        String selected =
            (String) department.getSelectedItem();

        if (selected != null) {
            for (String name : doctors.get(selected)) {
                doctor.addItem(name);
            }
        }
    }

    public static void main(String[] args) {
        new HospitalAppointmentDesk();
    }
}