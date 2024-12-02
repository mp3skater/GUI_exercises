import javax.swing.*;

public class Ex6 extends JFrame {
    public Ex6() {
        // Frame setup
        setTitle("Centered Window");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center window on screen
        setLocationRelativeTo(null);

        setVisible(true);
    }

    public static void main(String[] args) {
        new Ex6();
    }
}
