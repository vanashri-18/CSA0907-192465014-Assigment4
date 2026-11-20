import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CurrencyConverter extends JFrame implements ActionListener {

    JTextField amountField, rateField, resultField;
    JButton convertButton, clearButton, exitButton;

    CurrencyConverter() {
        setTitle("Currency Converter");
        setSize(400, 300);
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        add(new JLabel("Exchange Rate:"));
        rateField = new JTextField("83.00");
        add(rateField);

        add(new JLabel("Converted Amount:"));
        resultField = new JTextField();
        resultField.setEditable(false);
        add(resultField);

        convertButton = new JButton("Convert");
        clearButton = new JButton("Clear");
        exitButton = new JButton("Exit");

        add(convertButton);
        add(clearButton);
        add(exitButton);

        // Style 1: Implementing ActionListener
        convertButton.addActionListener(this);

        // Style 2: Anonymous inner class
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                amountField.setText("");
                rateField.setText("83.00");
                resultField.setText("");
            }
        });

        // Style 3: Lambda expression
        exitButton.addActionListener(e -> System.exit(0));

        // Style 4: Method reference
        amountField.addActionListener(this::convertAmount);
        rateField.addActionListener(this::convertAmount);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        convertAmount(e);
    }

    void convertAmount(ActionEvent e) {
        try {
            double amount = Double.parseDouble(amountField.getText());
            double rate = Double.parseDouble(rateField.getText());

            if (amount < 0 || rate <= 0) {
                JOptionPane.showMessageDialog(
                    this, "Enter valid positive values!");
                return;
            }

            double result = amount * rate;
            resultField.setText(String.format("%.2f", result));

            JOptionPane.showMessageDialog(
                this, "Conversion Successful!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(
                this, "Please enter valid numbers!");
        }
    }

    public static void main(String[] args) {
        new CurrencyConverter();
    }
}