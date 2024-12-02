public class EuroUmrechner {
    public double umrechnen(double betrag, int index) {
        return switch(index) {
            case 1 : yield WAEHRUNG.OESTERREICHERISCHE_SCHILLING.getRate()*betrag;
            case 2 : yield WAEHRUNG.BELGISCHE_FRANC.getRate()*betrag;
            case 3 : yield WAEHRUNG.DEUTSCHE_MARK.getRate()*betrag;
            case 4 : yield WAEHRUNG.SPANISCHE_PESETEN.getRate()*betrag;
            case 5 : yield WAEHRUNG.FRANZOESISCHE_FRANC.getRate()*betrag;
            case 6 : yield WAEHRUNG.IRISCHE_PFUND.getRate()*betrag;
            case 7 : yield WAEHRUNG.ITALIENISCHE_LIRE.getRate()*betrag;
            case 8 : yield WAEHRUNG.LUXENBURGISCHE_FRANC.getRate()*betrag;
            case 9 : yield WAEHRUNG.NIEDERLAENDISCHE_GULDEN.getRate()*betrag;
            case 10: yield WAEHRUNG.PORTUGIESISCHE_ESCUDOS.getRate()*betrag;
            case 11: yield WAEHRUNG.FINNMARK.getRate()*betrag;
            case 12: yield WAEHRUNG.GRIECHISCHE_DRACHMEN.getRate()*betrag;
            default:
                throw new IllegalStateException("Unexpected value: " + index);
        };
    }
}
