# 🥙 DönerHub V2 – Prototyp eines kundenorientierten Bestellmanagementsystems

## 1. Projektbeschreibung
DönerHub V2 ist ein funktionaler Prototyp eines digitalen Bestellmanagement-Systems, der im Rahmen des ersten Semesters für das Fach Programmiertechnik entwickelt wurde. Das Projekt simuliert die Betriebsabläufe eines Döner-Imbisses und dient als "Skeleton-Prototyp". Es demonstriert, wie Java Swing und objektorientierte Prinzipien genutzt werden, um eine solide, skalierbare Basis für zukünftige UI/UX-Erweiterungen in der Gastronomie zu schaffen.

## 2. Technische Entwicklung & Design-Konzepte

In diesem Abschnitt werden die zentralen Architektur-Entscheidungen und UX-Strategien detailliert, die die Robustheit des Systems gewährleisten.

### A. Architektur und Technologie-Stack
Das System wurde auf einem modernen technischen Fundament errichtet:
* **Kerntechnologie:** Entwicklung unter **Java 17**.
* **GUI-Framework:** Umsetzung mittels **Java Swing** unter Verwendung des IntelliJ UI-Designers für eine saubere Trennung von Layout und Logik.
* **Build-Management:** Einsatz von **Maven** zur strukturierten Verwaltung von Projektabhängigkeiten.
* **Qualitätssicherung:** Implementierung automatisierter Modultests mit **JUnit 5**, um die funktionale Korrektheit der Preislogik mathematisch zu garantieren.

### B. Datenmodellierung und Geschäftslogik
Ein Fokus lag auf der Datensicherheit und kaufmännischen Präzision:
* **Typsicherheit durch Enums:** Die Nutzung von Enums (`Fleisch`, `Brot`) garantiert, dass nur vordefinierte, valide Zustände verarbeitet werden ve merkezi fiyat tanımlamasına olanak sağlar.
* **Präzise Preiskalkulation (Cent-Logik):** Um Rundungsfehler (typisch bei `double`) zu vermeiden, erfolgt die gesamte interne Berechnung auf **Cent-Basis** (`int`). Dies sichert die kaufmännische Genauigkeit.
* **Smart Data Display:** Durch ein `DefaultTableModel` und den `TableRowSorter` wird eine dynamische Tabellenansicht realisiert, die Echtzeit-Filterung und Sortierung nach Attributen ermöglicht.

### C. UX-Psychologie und Sicherheit (Nudging)
Das Interface schützt den Nutzer aktiv vor Fehlbedienungen:
* **3-Stufen-Validierungs-Flow:** Anstatt einer direkten Bestellung folgt der Prozess einer logischen Sequenz: **Hinzufügen -> Preisprüfung (Gesamtpreis anzeigen) -> Bestellen**. Dieses "Nudging"-Konzept gibt dem Nutzer volle Kostenkontrolle und verhindert versehentliche Käufe.
* **Kontextsensitive Reset-Funktion:** Der Reset-Button agiert intelligent: Er löscht markierte Einzelbestellungen oder bietet bei leerer Auswahl – nach einer expliziten Sicherheitsabfrage (`ConfirmDialog`) – die Leerung des gesamten Warenkorbs an.
* **Schutz vor Leerbestellungen:** Ein "Leerkauf" ist technisch ausgeschlossen. Die Anwendung prüft aktiv, ob Objekte im Warenkorb existieren, bevor eine Bestellung finalisiert werden kann.
* **Robustes Exception-Handling:** Die GUI ist durch `try-catch`-Blöcke gegen fehlerhafte Eingabeformate (z. B. Text in Mengenfeldern) abgesichert, inklusive aussagekräftiger Fehlermeldungen.

## 3. Quellenverzeichnis und KI-Erklärung
Zur Sicherstellung der Code-Qualität und zur Optimierung der Dokumentation wurden folgende Werkzeuge eingesetzt:
* **IDE:** IntelliJ IDEA 
* **KI-Unterstützung:** **Gemini 3.0 Flash** und **ChatGPT 5.0 (Auto)** wurden punktuell zur Code-Verifikation, Fehlersuche und Strukturierung der Dokumentation herangezogen.
* **Methodik:** Der Einsatz erfolgte als **Lern- und Sparringspartner**, wobei die konzeptionelle Planung, die finale Implementierung sowie die logische Kontrolle stets eigenständig durch die Autoren erfolgte
## 4. Anleitung zum Starten
1. Stellen Sie sicher, dass Java 17 installiert ist.
2. Starten Sie die Anwendung über die `main`-Methode in der Klasse `de.hnu.doenerhub.app.Main`.
3. Nutzen Sie die vordefinierten Test-Bestellungen oder fügen Sie neue über das Formular hinzu.

## 5. Autoren
* **Mehmet Bulut** 
* **Thu Luong** 