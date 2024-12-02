import javax.swing.*;
import java.awt.*;

public class Ex2 extends JFrame {
    private JButton regularButton;
    private JToggleButton toggleButton;

    public Ex2() {
        // Create a regular JButton
        regularButton = new JButton("Regular Button");
        regularButton.addActionListener(e -> {
            if(toggleButton.isSelected()) {
                JOptionPane.showMessageDialog(null, "Regular button clicked!");
            }
        });

        // Create a JToggleButton that can stay pressed
        toggleButton = new JToggleButton("Toggle Button");
        toggleButton.addActionListener(e -> {
            if (toggleButton.isSelected()) {
                JOptionPane.showMessageDialog(null, "Toggle button is ON");
            } else {
                JOptionPane.showMessageDialog(null, "Toggle button is OFF");
            }
        });

        // Set layout and add buttons
        setLayout(new FlowLayout());
        add(regularButton);
        add(toggleButton);

        // Frame settings
        setTitle("Button Example");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Ex2();
    }
}
