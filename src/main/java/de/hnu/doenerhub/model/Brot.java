package de.hnu.doenerhub.model;

    // Enum für Brotvarianten, um fehlerhafte Eingaben durch vordefinierte Optionen zu verhindern.
    //Verknüpfung von Brotname mit dem jeweiligen Aufpreis.
public enum Brot {
    FLADEN_BROT("Fladen Brot", 0),
    FLADEN_BROT_GLUTENFREI("Fladen Brot (glutenfrei)", 100),
    YUFKA("Yufka", 50),
    YUFKA_GLUTENFREI("Yufka (glutenfrei)", 150);

    // Anzeige-Text für das GUI (saubere Trennung: Code-Name vs. Anzeige)
    private final String label;

    // Aufpreis in Cent (keine Rundungsfehler)
    private final int aufpreisInCent;

    // Enum-Konstruktor: Jeder Enum-Wert erhält Anzeige-Text + Aufpreis
    Brot(String label, int aufpreisInCent) {
        this.label = label;
        this.aufpreisInCent = aufpreisInCent;
    }
    //Rückgabe des Aufpreises für die Preisberechnung in der Order-Klasse.
    public int getAufpreisInCent() {
        return aufpreisInCent;
    }

    // Swing (z.B. JComboBox) nutzt toString() automatisch für die Anzeige
    @Override
    public String toString() {
        return label;
    }
}