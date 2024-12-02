import java.text.DecimalFormat;

public class Ex9 {
    private boolean nachschuessig;
    private double barwert;
    private double jahreszinssatz;
    private int laufzeitInJahren;
    private int ratenProJahr;
    private double rate;

    public void setNachschuessig(boolean nachschuessig) {
        this.nachschuessig = nachschuessig;
    }

    public boolean isNachschuessig() {
        return nachschuessig;
    }

    public void setBarwert(String sbarwert) throws RatenRechnerException {
        try {
            double value = Double.parseDouble(sbarwert);
            if (value <= 0) throw new RatenRechnerException("Kein gültiger Gleitkommawert.");
            this.barwert = value;
        } catch (NumberFormatException e) {
            throw new RatenRechnerException("Kein gültiger Gleitkommawert.");
        }
    }

    public String getBarwert() throws RatenRechnerException {
        if (rate == 0 || ratenProJahr == 0 || laufzeitInJahren == 0 || jahreszinssatz == 0) {
            throw new RatenRechnerException("Jahreszinssatz, Laufzeit, Raten pro Jahr oder Rate nicht gesetzt.");
        }
        double result = calculateBarwert();
        return formatDouble(result);
    }

    public void setJahreszinssatz(String szinssatz) throws RatenRechnerException {
        try {
            double value = Double.parseDouble(szinssatz);
            if (value < 0) throw new RatenRechnerException("Kein gültiger Zinssatz.");
            this.jahreszinssatz = value / 100;
        } catch (NumberFormatException e) {
            throw new RatenRechnerException("Kein gültiger Zinssatz.");
        }
    }

    public String getJahreszinssatz() {
        return formatDouble(jahreszinssatz * 100);
    }

    public void setLaufzeitInJahren(String slaufzeit) throws RatenRechnerException {
        try {
            int value = Integer.parseInt(slaufzeit);
            if (value <= 0) throw new RatenRechnerException("Laufzeit muss positiv sein.");
            this.laufzeitInJahren = value;
        } catch (NumberFormatException e) {
            throw new RatenRechnerException("Kein gültiger Laufzeitwert.");
        }
    }

    public String getLaufzeitInJahren() {
        return String.valueOf(laufzeitInJahren);
    }

    public void setRatenProJahr(String sraten) throws RatenRechnerException {
        switch (sraten) {
            case "1 Rate":
                this.ratenProJahr = 1;
                break;
            case "4 Raten":
                this.ratenProJahr = 4;
                break;
            case "6 Raten":
                this.ratenProJahr = 6;
                break;
            case "12 Raten":
                this.ratenProJahr = 12;
                break;
            default:
                throw new RatenRechnerException("Ungültige Ratenanzahl.");
        }
    }

    public String getRatenProJahr() {
        return String.valueOf(ratenProJahr);
    }

    public void setRate(String srate) throws RatenRechnerException {
        try {
            double value = Double.parseDouble(srate);
            if (value <= 0) throw new RatenRechnerException("Kein gültiger Ratenbetrag.");
            this.rate = value;
        } catch (NumberFormatException e) {
            throw new RatenRechnerException("Kein gültiger Ratenbetrag.");
        }
    }

    public String getRate() {
        return formatDouble(rate);
    }

    public String getTilgungsplan() throws RatenRechnerException {
        if (rate == 0 || barwert == 0 || ratenProJahr == 0 || laufzeitInJahren == 0 || jahreszinssatz == 0) {
            throw new RatenRechnerException("Führen Sie zuerst die Ratenberechnung durch.");
        }

        StringBuilder plan = new StringBuilder("<html><body><table border='1'>");
        plan.append("<tr><th>Rate</th><th>Zinsanteil</th><th>Tilgungsanteil</th><th>Restschuld</th></tr>");

        double restschuld = barwert;
        double zinssatzProPeriode = jahreszinssatz / ratenProJahr;
        int gesamtRaten = ratenProJahr * laufzeitInJahren;

        for (int i = 1; i <= gesamtRaten; i++) {
            double zinsanteil = restschuld * zinssatzProPeriode;
            double tilgungsanteil = rate - zinsanteil;
            restschuld -= tilgungsanteil;

            plan.append(String.format("<tr><td>%d</td><td>%.2f</td><td>%.2f</td><td>%.2f</td></tr>",
                    i, zinsanteil, tilgungsanteil, Math.max(restschuld, 0)));
        }

        plan.append("</table></body></html>");
        return plan.toString();
    }

    private double calculateBarwert() {
        double zinssatzProPeriode = jahreszinssatz / ratenProJahr;
        int gesamtRaten = ratenProJahr * laufzeitInJahren;
        return rate * (1 - Math.pow(1 + zinssatzProPeriode, -gesamtRaten)) / zinssatzProPeriode;
    }

    private String formatDouble(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(value);
    }
}
