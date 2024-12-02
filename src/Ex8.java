import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Ex8 extends JFrame {

    private JTextField[] jTextFields;

    private final EuroUmrechner euroUmrechner; // Die gegebene Umrechnungsklasse

    public Ex8(EuroUmrechner euroUmrechner) {
        this.euroUmrechner = euroUmrechner;

        // Initialisierung
        initUI();
        initListeners();

        // JFrame-Einstellungen
        setTitle("Euro Umrechner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initUI() {
        // Labels und Textfelder initialisieren
        String[] waehrungen = {"EUR", "OES", "BEF", "DEM", "SPP", "FRF", "IRP", "ITL", "LUF", "NIG", "POE", "FIM", "GRD"};
        JLabel[] jLabels = new JLabel[waehrungen.length];
        jTextFields = new JTextField[waehrungen.length];

        // Layout setzen
        setLayout(new GridLayout(waehrungen.length, 2, 5, 5));

        for (int i = 0; i < waehrungen.length; i++) {
            jLabels[i] = new JLabel(waehrungen[i] + ":");
            jTextFields[i] = new JTextField();
            add(jLabels[i]);
            add(jTextFields[i]);
        }
    }

    private void initListeners() {
        // Listener hinzufügen
        MeinTastaturAbhoerer tastaturAbhoerer = new MeinTastaturAbhoerer();
        for (JTextField textField : jTextFields) {
            textField.addKeyListener(tastaturAbhoerer);
        }
    }

    private class MeinTastaturAbhoerer extends KeyAdapter {
        @Override
        public void keyReleased(KeyEvent e) {
            JTextField sourceField = (JTextField) e.getSource();
            String input = sourceField.getText();

            // return if not standard text field
            if(sourceField != jTextFields[0]) return;

            try {
                double betrag = Double.parseDouble(input);

                // Umrechnung durchführen und Felder aktualisieren
                for (int i = 1; i <= jTextFields.length; i++) {
                    if (!jTextFields[i].equals(sourceField)) {
                        double umgerechnet = euroUmrechner.umrechnen(betrag, i);
                        jTextFields[i].setText(String.format("%.2f", umgerechnet));
                    }
                }
            } catch (NumberFormatException ex) {
                // Eingabe ist ungültig → Keine Berechnung durchführen
                for (JTextField textField : jTextFields) {
                    if (!textField.equals(sourceField)) {
                        textField.setText("");
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        // Beispiel-Instanz von EuroUmrechner erstellen (Anpassung notwendig)
        EuroUmrechner euroUmrechner = new EuroUmrechner();
        new Ex8(euroUmrechner);
    }
}
