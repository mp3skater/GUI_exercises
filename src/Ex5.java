import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ex5 extends JFrame {
    private JTextField principalField;
    private JTextField interestField;
    private JTextField yearsField;
    private JLabel resultLabel;

    public Ex5() {
        // Initialize input fields
        principalField = new JTextField(10);
        interestField = new JTextField(10);
        yearsField = new JTextField(10);
        resultLabel = new JLabel("Monthly Payment: ");

        // Create Calculate button
        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculatePayment();
            }
        });

        // Layout setup
        setLayout(new GridLayout(5, 2));
        add(new JLabel("Principal:"));
        add(principalField);
        add(new JLabel("Interest Rate (%):"));
        add(interestField);
        add(new JLabel("Years:"));
        add(yearsField);
        add(calculateButton);
        add(resultLabel);

        // Frame settings
        setTitle("Loan Calculator");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void calculatePayment() {
        try {
            // Get values from text fields
            double principal = Double.parseDouble(principalField.getText());
            double interestRate = Double.parseDouble(interestField.getText()) / 100 / 12;
            int years = Integer.parseInt(yearsField.getText()) * 12;

            // Calculate monthly payment
            double monthlyPayment = (principal * interestRate) / (1 - Math.pow(1 + interestRate, -years));
            resultLabel.setText("Monthly Payment: " + String.format("%.2f", monthlyPayment));

        } catch (NumberFormatException ex) {
            // Show error if input is invalid
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter numbers only.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Ex5();
    }
}
