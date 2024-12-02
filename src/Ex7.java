import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;

public class Ex7 extends JFrame {
    private JTextField addressField;
    private JEditorPane editorPane;

    public Ex7() {
        // Setup GUI components
        addressField = new JTextField("http://", 30);
        JButton loadButton = new JButton("Load");
        editorPane = new JEditorPane();
        editorPane.setEditable(false); // Prevent editing

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(editorPane);

        // Set layout and add components
        setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        topPanel.add(addressField);
        topPanel.add(loadButton);
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Add action listener to load page
        loadButton.addActionListener(e -> loadPage());

        // Follow links clicked within the editor pane
        editorPane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    addressField.setText(e.getURL().toString());
                    loadPage();
                }
            }
        });

        // Frame settings
        setTitle("MeinWeb Browser");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loadPage() {
        try {
            System.out.println("trying to open page");
            editorPane.setPage(addressField.getText()); // Load page from address field
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Invalid URL", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Ex7();
    }
}
