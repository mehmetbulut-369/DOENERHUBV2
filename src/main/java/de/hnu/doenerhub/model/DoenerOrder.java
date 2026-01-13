package de.hnu.doenerhub.model;

    /**
     * Model-Klasse für eine einzelne Bestellung.
     * Diese Klasse fungiert als "Bauplan" (Template) für jedes Döner-Objekt im System.
     */
public class DoenerOrder {
    //Private Attribute sorgen für Kapselung; Daten können nicht von außen manipuliert werden.
    private final Fleisch fleisch;
    private final Brot brot;
    private final int anzahl;
    private final boolean glutenfrei;


    /**
         * Konstruktor zur Initialisierung einer Bestellung.
         * Enthält Validierungslogik (Sanity Checks), um die Erzeugung ungültiger Objekte zu verhindern.
         * Dadurch sind die Objekte unveränderlich (immutable) und sicherer.
         */

    public DoenerOrder(Fleisch fleisch, Brot brot, int anzahl, boolean glutenfrei) {
        // Sicherstellung, dass keine Null-Referenzen oder unlogische Mengen gespeichert werden.
        if (fleisch == null) {
            throw new IllegalArgumentException("Fleisch darf nicht null sein.");
        }
        if (brot == null) {
            throw new IllegalArgumentException("Brot darf nicht null sein.");
        }
        if (anzahl <= 0) {
            throw new IllegalArgumentException("Anzahl muss > 0 sein.");
        }

        this.fleisch = fleisch;
        this.brot = brot;
        this.anzahl = anzahl;
        this.glutenfrei = glutenfrei;
    }

    // Getter-Methoden: Erlauben kontrollierten Lesezugriff auf die privaten Attribute.
    public Fleisch getFleisch() {
        return fleisch;
    }

    public Brot getBrot() {
        return brot;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public boolean isGlutenfrei() {
        return glutenfrei;
    }

    /**
     * Berechnet den Gesamtpreis der Bestellung basierend auf den Attributwerten.
     * Nutzt die Switch-Case-Logik für eine klare Entscheidungsstruktur.
     * @return Gesamtpreis in Cent (Vermeidung von Rundungsfehlern bei Fließkommazahlen).
     */
    public int berechneGesamtpreisInCent() {
        int basispreis;

        // Bestimmung des Basispreises basierend auf der Fleischwahl (Polymorphie-Alternative).
        switch (fleisch) {
            case HAEHNCHEN -> basispreis = 650; // 6,50 €
            case LAMM -> basispreis = 750;     // 7,50 €
            case KALB -> basispreis = 720;     // 7,20 €
            default -> throw new IllegalStateException("Unerwartetes Fleisch: " + fleisch);
        }

        // Berechnung der Brot-Aufpreise (Zusatzkostenlogik).
        int brotAufpreis;
        switch (brot) {
            case FLADEN_BROT -> brotAufpreis = 0;
            case FLADEN_BROT_GLUTENFREI -> brotAufpreis = 100; // +1,00 €
            case YUFKA -> brotAufpreis = 50;                   // +0,50 €
            case YUFKA_GLUTENFREI -> brotAufpreis = 150;       // +1,50 €
            default -> throw new IllegalStateException("Unerwartetes Brot: " + brot);
        }

        // Berücksichtigung globaler Optionen (Glutenfrei-Zuschlag).
        int glutenfreiAufpreis = glutenfrei ? 50 : 0;

        int preisProStueck = basispreis + brotAufpreis + glutenfreiAufpreis;
        return preisProStueck * anzahl;
    }

    @Override
    public String toString() {
        // Rückgabe einer menschenlesbaren Beschreibung des Objekts für Debugging und Logs.
        return "DoenerOrder{" +
                "fleisch=" + fleisch +
                ", brot=" + brot +
                ", anzahl=" + anzahl +
                ", glutenfrei=" + glutenfrei +
                ", gesamtpreis=" + (berechneGesamtpreisInCent() / 100.0) + "€" +
                '}';
    }
}
