public class Registration {
    private int id;
    private String studentName;
    private String registerNumber;
    private String email;
    private String eventName;

    public Registration(String studentName, String registerNumber,
                        String email, String eventName) {
        this.studentName = studentName;
        this.registerNumber = registerNumber;
        this.email = email;
        this.eventName = eventName;
    }

    public int getId() {
        return id;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getEventName() {
        return eventName;
    }
}