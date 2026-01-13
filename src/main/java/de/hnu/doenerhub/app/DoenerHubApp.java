package de.hnu.doenerhub.app;

import de.hnu.doenerhub.model.Brot;
import de.hnu.doenerhub.model.DoenerOrder;
import de.hnu.doenerhub.model.Fleisch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Diese Klasse fungiert als zentraler Verwalter für die Bestellungen.
 * Sie trennt die Datenhaltung von der grafischen Oberfläche (GUI).
 */
public class DoenerHubApp {

    // Eine dynamische Liste zur Speicherung aller Bestell-Objekte während der Programmlaufzeit.
    private final List<DoenerOrder> orders = new ArrayList<>();

    /**
     * Erzeugt beim Start der Anwendung vordefinierte Test-Bestellungen.
     * Dies dient zur Demonstration der Tabellenfunktionen und zur initialen Befüllung.
     */
    public void initObjekte() {
        orders.add(new DoenerOrder(Fleisch.HAEHNCHEN, Brot.FLADEN_BROT, 1, false));
        orders.add(new DoenerOrder(Fleisch.LAMM, Brot.YUFKA, 2, true));
        orders.add(new DoenerOrder(Fleisch.KALB, Brot.FLADEN_BROT_GLUTENFREI, 1, false));
    }

    /**
     * Fügt der Liste eine neue Bestellung hinzu.
     * Eine Prüfung auf Null-Referenzen verhindert Fehler in der Datenstruktur.
     */
    public void addOrder(DoenerOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Order darf nicht null sein.");
        }
        orders.add(order);
    }

    /**
     * Entfernt eine spezifische Bestellung aus der Liste.
     */
    public boolean removeOrder(DoenerOrder order) {
        return orders.remove(order);
    }

    /**
     * Leert den gesamten Warenkorb.
     */
    public void clearOrders() {
        orders.clear();
    }

    /**
     * Gibt die aktuelle Liste der Bestellungen zurück.
     * Durch 'unmodifiableList' wird verhindert, dass die Liste von außen
     * ohne Nutzung der add/remove-Methoden manipuliert werden kann.
     */
    public List<DoenerOrder> getOrders() {
        return Collections.unmodifiableList(orders);
    }
}