package de.hnu.doenerhub.model;

     /**
     * Enum für Fleischsorten zur Gewährleistung der Typsicherheit.
     * Verhindert, dass ungültige Fleischnamen im System verarbeitet werden.
     */
    public enum Fleisch {
    HAEHNCHEN("Hähnchen", 650),
    LAMM("Lamm", 750),
    KALB("Kalb", 720);
    // Jeder Enum-Wert ist ein konstantes Objekt mit festem Namen und Preis.

    // Anzeige-Text für das GUI
    private final String label;
    // Basispreis in Cent (keine Rundungsfehler)
    private final int basispreisInCent;

    /**
     * Dies ist der Constructor. Er wird intern aufgerufen, um jedem
     * Enum-Wert seine individuellen Eigenschaften (Name und Preis) zuzuweisen.
     */
    Fleisch(String label, int basispreisInCent) {
        this.label = label;
        this.basispreisInCent = basispreisInCent;
    }

    // Getter-Methode für den Preis in Cent (Vermeidung von Rundungsfehlern).
    public int getBasispreisInCent() {
        return basispreisInCent;
    }

    // Sorgt dafür, dass in der GUI (ComboBox) der lesbare Name angezeigt wird.
    @Override
    public String toString() {
        return label;
    }
}
