import java.awt.*;
import java.awt.event.*;

public class FeePaymentGUI extends Frame {

    TextField regNo = new TextField(15);
    TextField amount = new TextField(15);

    Button pay = new Button("Pay");
    Button clear = new Button("Clear");
    Button receipt = new Button("Receipt");

    Label status = new Label("Enter payment details");

    FeeService service = new FeeService();

    public FeePaymentGUI() {

        setTitle("Student Fee Payment");

        setLayout(new GridLayout(5, 2, 10, 10));

        add(new Label("Register Number:"));
        add(regNo);

        add(new Label("Amount:"));
        add(amount);

        add(pay);
        add(clear);

        add(receipt);
        add(status);

        pay.addActionListener(e -> {
            String result = service.payFee(
                regNo.getText(), amount.getText());

            status.setText(result);
        });

        clear.addActionListener(e -> {
            regNo.setText("");
            amount.setText("");
            status.setText("Fields Cleared");
        });

        receipt.addActionListener(e -> {
            String result = service.generateReceipt(
                regNo.getText(), amount.getText());

            status.setText(result);
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(450, 250);
        setVisible(true);
    }

    public static void main(String[] args) {
        new FeePaymentGUI();
    }
}