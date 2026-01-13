package de.hnu.doenerhub.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit-Tests für die Klasse DoenerOrder.
 * Diese Tests stellen sicher, dass die Preisberechnung und die Validierungslogik
 * mathematisch korrekt funktionieren.
 */
class DoenerOrderTest {

    /**
     * Testet die Basis-Preisberechnung für eine Standardbestellung.
     * Erwartet: 650 Cent (6,50 €).
     */
    @Test
    void berechneGesamtpreisInCent_haehnchen_fladenbrot_1_stueck_nicht_glutenfrei() {
        DoenerOrder order = new DoenerOrder(
                Fleisch.HAEHNCHEN,
                Brot.FLADEN_BROT,
                1,
                false
        );

        // Überprüfung, ob der berechnete Preis exakt dem Erwartungswert entspricht.
        assertEquals(650, order.berechneGesamtpreisInCent());
    }

    /**
     * Testet eine komplexere Bestellung mit Aufpreisen (Lamm, Yufka, Glutenfrei) und Menge 2.
     * Logik: (7,50 + 0,50 + 0,50) * 2 = 17,00 € -> 1700 Cent.
     */
    @Test
    void berechneGesamtpreisInCent_lamm_yufka_2_stueck_glutenfrei() {
        DoenerOrder order = new DoenerOrder(
                Fleisch.LAMM,
                Brot.YUFKA,
                2,
                true
        );

        // Sicherstellung, dass alle Aufpreise korrekt summiert und multipliziert werden.
        assertEquals(1700, order.berechneGesamtpreisInCent());
    }

    /**
     * Überprüft die Fehlerbehandlung im Konstruktor.
     * Es wird sichergestellt, dass bei einer ungültigen Anzahl (0)
     * wie gewünscht eine Exception ausgelöst wird.
     */
    @Test
    void konstruktor_wirft_exception_bei_ungueltiger_anzahl() {
        // AssertThrows prüft, ob die erwartete IllegalArgumentException tatsächlich geworfen wird.
        assertThrows(IllegalArgumentException.class, () ->
                new DoenerOrder(Fleisch.KALB, Brot.FLADEN_BROT, 0, false)
        );
    }
}