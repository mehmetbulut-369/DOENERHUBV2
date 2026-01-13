package de.hnu.doenerhub.app;

/**
 * Die Main-Klasse dient als offizieller Einstiegspunkt (Entry Point) der Anwendung.
 * Sie hat die alleinige Verantwortung, den Startprozess der GUI einzuleiten.
 */
public class Main {
    /**
     * Die Hauptmethode startet die grafische Benutzeroberfläche.
     * Hier wird die Trennung zwischen Startvorgang und Anwendungslogik gewahrt.
     */
    public static void main(String[] args) {
        // Aufruf der statischen Methode start(), um das Hauptfenster zu initialisieren.
        DoenerHubGUI.start();
    }
}

