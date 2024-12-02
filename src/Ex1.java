import javax.swing.*;

public class Ex1 extends JFrame {
    private JTextField textField;
    private JButton button;

    public Ex1() {
        // Create text field and button
        textField = new JTextField(20);
        button = new JButton("Focus Text Field");

        // Set mnemonic to Alt+B to trigger button action
        button.setMnemonic('B');
        button.addActionListener(e -> {
            textField.requestFocusInWindow();
            System.out.println("button");
        });

        // Add components to the frame
        setLayout(new java.awt.FlowLayout());
        add(textField);
        add(button);

        // Frame settings
        setTitle("Mnemonic Example");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Ex1();
    }
}
