public enum WAEHRUNG {
    OESTERREICHERISCHE_SCHILLING(7.11/0.52),
    BELGISCHE_FRANC(20.83/0.52),
    DEUTSCHE_MARK(1.01/0.52),
    SPANISCHE_PESETEN(85.93/0.52),
    FRANZOESISCHE_FRANC(3.39/0.52),
    IRISCHE_PFUND(0.41/0.52),
    ITALIENISCHE_LIRE(1000/0.52),
    LUXENBURGISCHE_FRANC(20.86/0.52),
    NIEDERLAENDISCHE_GULDEN(1.14/0.52),
    PORTUGIESISCHE_ESCUDOS(103.54/0.52),
    FINNMARK(3.07/0.52),
    GRIECHISCHE_DRACHMEN(175.98/0.52);


    private final double rate;

    WAEHRUNG(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }
}

