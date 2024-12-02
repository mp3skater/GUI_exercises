public class RatenRechner {
	private boolean nachschuessig;
	private double barwert;
	private double jahreszinssatz;
	private int laufzeitInJahren;
	private int ratenProJahr;
	private double rate;

	public void setNachschuessig(boolean nachschuessig) {
		this.nachschuessig = nachschuessig;
	}

	public void setBarwert(String sbarwert) throws RatenRechnerException {
		try {
			double value = Double.parseDouble(sbarwert);
			if (value <= 0) {
				//throw new RatenRechnerException("Kein gültiger Gleitkommawert: Barwert muss größer als 0 sein.");
			}
			this.barwert = value;
		} catch (NumberFormatException e) {
			throw new RatenRechnerException("Kein gültiger Gleitkommawert.");
		}
	}

	public double getBarwert() throws RatenRechnerException {
		final double n = laufzeitInJahren * ratenProJahr;
		final double q = 1. + (jahreszinssatz / ratenProJahr) / 100.;
		if (nachschuessig)
			barwert = rate * (Math.pow(q, n) - 1.) / (Math.pow(q, n) * (q - 1.));
		else
			barwert = rate * (Math.pow(q, n)- 1.) / (Math.pow(q, n - 1.) * (q - 1.));
		return barwert;
	}

	// Berechnung der Rate
	public double getRate() throws RatenRechnerException {
		final double n = laufzeitInJahren * ratenProJahr;
		final double q = 1. + (jahreszinssatz / ratenProJahr) / 100.;
		if (nachschuessig)
			rate = barwert * (Math.pow(q, n) * (q - 1.)) / (Math.pow(q, n) - 1.);
		else
			rate = barwert * (Math.pow(q, n - 1.) * (q - 1.)) / (Math.pow(q, n) - 1.);
		return rate;
	}

	// Berechnung der Laufzeit
	public int getLaufzeit() throws RatenRechnerException {
		final double q = 1. + (jahreszinssatz / ratenProJahr) / 100.;

		if (nachschuessig)
			laufzeitInJahren = (int) Math.ceil((-Math.log((rate - barwert * (q - 1.)) / rate) /
					Math.log(q)) / ratenProJahr);
		else
			laufzeitInJahren = (int) Math.ceil((1. - Math.log((q * rate - barwert * (q - 1.)) /
					rate) / Math.log(q)) / ratenProJahr);
		return laufzeitInJahren;
	}


	public void setJahreszinssatz(double jahreszinssatz) {
		this.jahreszinssatz = jahreszinssatz;
	}

	public void setLaufzeitInJahren(int laufzeitInJahren) {
		this.laufzeitInJahren = laufzeitInJahren;
	}

	public void setRatenProJahr(String ratenString) throws RatenRechnerException {
		switch (ratenString) {
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
				throw new RatenRechnerException("Ungültige Anzahl von Raten.");
		}
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String getTilgungsplan() throws RatenRechnerException {
		if (jahreszinssatz <= 0 || laufzeitInJahren <= 0 || ratenProJahr <= 0 || rate <= 0) {
			throw new RatenRechnerException("Führen Sie zuerst die Ratenberechnung durch.");
		}
		StringBuilder plan = new StringBuilder("<html><body><table border='1'><tr><th>Rate</th><th>Restschuld</th></tr>");
		double restschuld = barwert;
		double r = jahreszinssatz / (100 * ratenProJahr);
		int n = laufzeitInJahren * ratenProJahr;

		plan.append("<html><body><h1>TILGUNGSPLAN</h1>")
				.append("<table border='1'>")
				.append("<tr><td>Zahlungsart:</td><td>Nachschüssig</td></tr>")
				.append("<tr><td>Barwert:</td><td>").append(barwert).append("</td></tr>")
				.append("<tr><td>Jahreszinssatz:</td><td>").append(jahreszinssatz).append("%</td></tr>")
				.append("<tr><td>Laufzeit in Jahren:</td><td>").append(laufzeitInJahren).append("</td></tr>")
				.append("<tr><td>Rückzahlungsart:</td><td>").append(ratenProJahr).append(" Raten im Jahr</td></tr>")
				.append("<tr><td>Rate:</td><td>").append(String.format("%.2f", rate)).append("</td></tr>")
				.append("</table><br>")
				.append("<table border='1'>")
				.append("<tr><th>Periode</th><th>Rate</th><th>Restkapital</th><th>Zinsen</th></tr>");

		// Generate rows for each payment period
		for (int i = 1; i <= n; i++) {
			double zinsen = restschuld * r;
			double tilgung = rate - zinsen;
			restschuld -= tilgung;

			plan.append("<tr><td>").append(i).append("</td>")
					.append("<td>").append(String.format("%.2f", rate)).append("</td>")
					.append("<td>").append(String.format("%.2f", restschuld)).append("</td>")
					.append("<td>").append(String.format("%.2f", zinsen)).append("</td></tr>");
		}

		plan.append("</table></body></html>");
		return plan.toString();
	}
}
