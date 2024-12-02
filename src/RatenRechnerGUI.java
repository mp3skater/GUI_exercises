import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.Objects;

public class RatenRechnerGUI {
    private RatenRechner rechner;

    public RatenRechnerGUI() {
        rechner = new RatenRechner();
        JFrame frame = new JFrame("RatenRechner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 500);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(6, 3));
        JTextField barwertField = new JTextField();
        JTextField jahreszinsField = new JTextField();
        JTextField laufzeitField = new JTextField();
        JComboBox<String> ratenCombo = new JComboBox<>(new String[]{"1 Rate", "4 Raten", "6 Raten", "12 Raten"});
        JTextField rateField = new JTextField();

        JButton calcBarwert = new JButton("Berechne Barwert");
        JButton calcLaufzeit = new JButton("Berechne Laufzeit");
        JButton calcRate = new JButton("Berechne Rate");

        JRadioButton nachschuessigButton = new JRadioButton("Nachschüssig", true);
        JRadioButton vorschuessigButton = new JRadioButton("Vorschüssig");
        ButtonGroup group = new ButtonGroup();
        group.add(nachschuessigButton);
        group.add(vorschuessigButton);

        inputPanel.add(new JLabel("Barwert:"));
        inputPanel.add(barwertField);
        inputPanel.add(calcBarwert);

        inputPanel.add(new JLabel("Jahreszinssatz (%):"));
        inputPanel.add(jahreszinsField);
        inputPanel.add(new JPanel());

        inputPanel.add(new JLabel("Laufzeit in Jahren:"));
        inputPanel.add(laufzeitField);
        inputPanel.add(calcLaufzeit);

        inputPanel.add(new JLabel("Raten pro Jahr:"));
        inputPanel.add(ratenCombo);
        inputPanel.add(new JPanel());

        inputPanel.add(new JLabel("Rate:"));
        inputPanel.add(rateField);
        inputPanel.add(calcRate);

        inputPanel.add(nachschuessigButton);
        inputPanel.add(vorschuessigButton);

        JButton calculateButton = new JButton("Zeige Tilgungsplan");
        JButton saveButton = new JButton("Speichern");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(calculateButton);
        buttonPanel.add(saveButton);

        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        calcBarwert.addActionListener(
                e -> {
                    try {
                        rechner.getBarwert();
                    } catch (RatenRechnerException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );
        
        try{
            rechner.setNachschuessig(true);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
        nachschuessigButton.addActionListener(
                e -> rechner.setNachschuessig(Objects.equals(nachschuessigButton.getText(), "Nachschüssig"))
        );

        try{
            rechner.setRatenProJahr("1 Rate");
        }
        catch (RuntimeException | RatenRechnerException e) {
            throw new RuntimeException(e);
        }
        ratenCombo.addActionListener(e -> {
            String selectedRaten = (String) ratenCombo.getSelectedItem();
            if (selectedRaten != null) {
                try {
                    rechner.setRatenProJahr(selectedRaten);
                } catch (RatenRechnerException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        barwertField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = barwertField.getText();
                try {
                    rechner.setBarwert(input);
                } catch (RatenRechnerException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        jahreszinsField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = jahreszinsField.getText();
                try {
                    rechner.setJahreszinssatz(Double.parseDouble(input));
                } catch (NumberFormatException ex) {
                    // Handle invalid input
                }
            }
        });

        laufzeitField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = laufzeitField.getText();
                try {
                    rechner.setLaufzeitInJahren(Integer.parseInt(input));
                } catch (NumberFormatException ex) {
                    // Handle invalid input
                }
            }
        });

        rateField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = rateField.getText();
                try {
                    rechner.setRate(Double.parseDouble(input));
                } catch (NumberFormatException ex) {
                    // Handle invalid input
                }
            }
        });


        calcBarwert.addActionListener(
                e -> {
                    try {
                        String value = String.valueOf(rechner.getBarwert());
                        barwertField.setText(value);
                        rechner.setBarwert(value);
                    } catch (RatenRechnerException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );

        calcRate.addActionListener(
                e -> {
                    try {
                        String value = String.valueOf(rechner.getRate());
                        rateField.setText(value);
                        rechner.setRate(Double.parseDouble(value));
                    } catch (RatenRechnerException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );

        calcLaufzeit.addActionListener(
                e -> {
                    try {
                        String value = String.valueOf(rechner.getLaufzeit());
                        laufzeitField.setText(value);
                        rechner.setLaufzeitInJahren(Integer.parseInt(value));
                    } catch (RatenRechnerException ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );

        calculateButton.addActionListener(e -> {
            try {
                rechner.setBarwert(barwertField.getText());
                rechner.setJahreszinssatz(Double.parseDouble(jahreszinsField.getText()));
                rechner.setLaufzeitInJahren(Integer.parseInt(laufzeitField.getText()));
                rechner.setRatenProJahr((String) ratenCombo.getSelectedItem());
                rechner.setRate(Double.parseDouble(rateField.getText()));
                String tilgungsplan = rechner.getTilgungsplan();

                JEditorPane editorPane = new JEditorPane();
                editorPane.setContentType("text/html");
                editorPane.setText(tilgungsplan);
                editorPane.setCaretPosition(0);
                editorPane.setEditable(false);

                JScrollPane scrollPane = new JScrollPane(editorPane);
                scrollPane.setPreferredSize(new Dimension(400, 300)); // Adjust dimensions as needed
                JOptionPane.showMessageDialog(frame, scrollPane, "Tilgungsplan", JOptionPane.PLAIN_MESSAGE);

            } catch (RatenRechnerException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Ungültige Eingabe.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        saveButton.addActionListener(e -> {
            try {
                JFileChooser fileChooser = new JFileChooser();
                int choice = fileChooser.showSaveDialog(frame);
                if (choice == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (file.exists()) {
                        int overwrite = JOptionPane.showConfirmDialog(frame, "Datei existiert bereits. Überschreiben?", "Warnung", JOptionPane.YES_NO_OPTION);
                        if (overwrite != JOptionPane.YES_OPTION) return;
                    }
                    try (FileWriter writer = new FileWriter(file)) {
                        writer.write(rechner.getTilgungsplan());
                        JOptionPane.showMessageDialog(frame, "Tilgungsplan gespeichert.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Fehler beim Speichern.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new RatenRechnerGUI();
    }
}
