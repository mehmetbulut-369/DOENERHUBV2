package de.hnu.doenerhub.app;

import de.hnu.doenerhub.model.Brot;
import de.hnu.doenerhub.model.DoenerOrder;
import de.hnu.doenerhub.model.Fleisch;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.text.ParseException;

/**
 * Verantwortlichkeit: Diese Klasse bildet die View (Oberfläche) und den Controller.
 * Sie verwaltet die Benutzerinteraktionen und leitet Daten an die Logikschicht weiter.
 */
public class DoenerHubGUI {

    // Kapselung: Alle GUI-Komponenten sind privat, damit sie nicht von außen manipuliert werden können.
    private JPanel rootPanel;
    private JComboBox fleischComboBox;
    private JButton addOrderButton;
    private JButton gesamtpreisAnzeigenButton;
    private JSpinner anzahlTextField;
    private JTable orderTable;
    private JComboBox filterFleischComboBox;
    private JButton resetFilterButton;
    private JCheckBox glutenfreiCheckBox;
    private JRadioButton fladenBrotRadioButton;
    private JRadioButton yufkaRadioButton;
    private JButton bestellenButton;

    // Beziehung: Die GUI nutzt eine Instanz der App-Klasse (Assoziation),
    // um die Geschäftslogik von der Darstellung zu trennen.
    private final DoenerHubApp app = new DoenerHubApp();

    //Nutzung eines eigenen TableModels zur besseren Kontrolle der Datenanzeige.
    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"Fleisch", "Brot", "Anzahl", "Glutenfrei", "Gesamtpreis (€)", "__OBJ"},
            0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Verhindert direktes Editieren in der Tabelle für Datenkonsistenz.
        }
    };

    private final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);

    /**
     * Konstruktor: Initialisiert die Anwendung und bereitet die GUI vor.
     */
    public DoenerHubGUI() {
        app.initObjekte(); // Laden der Start-Objekte

        initInputs();
        initTable();
        initFilter();

        refreshTable();
        wireActions();
    }

    /**
     * Bereitet die Eingabekomponenten vor (z.B. Enums in ComboBoxen laden).
     */
    private void initInputs() {
        fleischComboBox.setModel(new DefaultComboBoxModel<>(Fleisch.values()));
        anzahlTextField.setModel(new SpinnerNumberModel(1, 1, 100, 1));

        ButtonGroup brotGroup = new ButtonGroup();
        brotGroup.add(fladenBrotRadioButton);
        brotGroup.add(yufkaRadioButton);
        fladenBrotRadioButton.setSelected(true);
    }

    /**
     * Konfiguriert die JTable und verknüpft sie mit dem TableModel.
     */
    private void initTable() {
        orderTable.setModel(tableModel);
        orderTable.setRowSorter(sorter);
        orderTable.setFillsViewportHeight(true);

        // Versteckte Spalte für das technische DoenerOrder-Objekt (für einfaches Löschen).
        int hiddenCol = orderTable.getColumnModel().getColumnCount() - 1;
        orderTable.getColumnModel().getColumn(hiddenCol).setMinWidth(0);
        orderTable.getColumnModel().getColumn(hiddenCol).setMaxWidth(0);
        orderTable.getColumnModel().getColumn(hiddenCol).setPreferredWidth(0);
    }

    /**
     * Initialisiert die Filter-Logik für die Tabelle.
     */
    private void initFilter() {
        DefaultComboBoxModel<Object> filterModel = new DefaultComboBoxModel<>();
        filterModel.addElement("Alle");
        for (Fleisch f : Fleisch.values()) {
            filterModel.addElement(f);
        }
        filterFleischComboBox.setModel(filterModel);

        // Listener für Echtzeit-Filterung
        filterFleischComboBox.addActionListener(e -> applyFleischFilter());

        // Kontextbasierter Reset-Button (Löschen einzelner Zeilen oder Filter zurücksetzen).
        resetFilterButton.addActionListener(e -> {
            Object sel = filterFleischComboBox.getSelectedItem();
            if (sel != null && !"Alle".equals(sel)) {
                filterFleischComboBox.setSelectedIndex(0);
                sorter.setRowFilter(null);
                return;
            }

            int viewRow = orderTable.getSelectedRow();
            if (viewRow >= 0) {
                // Konvertierung von View-Index zu Model-Index bei aktivem Filter/Sortierung.
                int modelRow = orderTable.convertRowIndexToModel(viewRow);
                DoenerOrder toRemove = (DoenerOrder) tableModel.getValueAt(modelRow, 5);

                // Sicherheitsabfrage vor dem Löschen (User Experience).
                int confirm = JOptionPane.showConfirmDialog(rootPanel,
                        "Ausgewählte Bestellung wirklich löschen?",
                        "Bestellung löschen",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    app.removeOrder(toRemove);
                    refreshTable();
                }
                return;
            }

            // Fallback: Wenn nichts ausgewählt ist, wird das Löschen der gesamten Liste angeboten.
            int confirmAll = JOptionPane.showConfirmDialog(rootPanel,
                    "Möchtest du alle Bestellungen löschen?",
                    "Alle löschen",
                    JOptionPane.YES_NO_OPTION);

            if (confirmAll == JOptionPane.YES_OPTION) {
                app.clearOrders();
                refreshTable();
            }
        });
    }

    /**
     * Filtert die Tabellenanzeige basierend auf der Fleischauswahl.
     */
    private void applyFleischFilter() {
        Object selected = filterFleischComboBox.getSelectedItem();
        if (selected == null || "Alle".equals(selected)) {
            sorter.setRowFilter(null);
            return;
        }

        String text = selected.toString();
        // Regex-Filter für exakte Übereinstimmung in der ersten Spalte (Fleisch).
        sorter.setRowFilter(RowFilter.regexFilter("^" + java.util.regex.Pattern.quote(text) + "$", 0));
    }

    /**
     * Bestimmt die gewählte Brot-Enum-Konstante basierend auf RadioButtons und CheckBox.
     */
    private Brot getSelectedBrot() {
        boolean gf = glutenfreiCheckBox.isSelected();
        if (fladenBrotRadioButton.isSelected()) {
            return gf ? Brot.FLADEN_BROT_GLUTENFREI : Brot.FLADEN_BROT;
        }
        if (yufkaRadioButton.isSelected()) {
            return gf ? Brot.YUFKA_GLUTENFREI : Brot.YUFKA;
        }
        throw new IllegalArgumentException("Bitte Brot auswählen.");
    }

    /**
     * Zentraler Ort für das Event-Handling (Button-Klicks).
     * Hier wird die Verarbeitungslogik gesteuert.
     */
    private void wireActions() {
        addOrderButton.addActionListener(e -> {
            // Exception Handling: Validierung der Benutzereingaben.
            try {
                Fleisch fleisch = (Fleisch) fleischComboBox.getSelectedItem();
                Brot brot = getSelectedBrot();

                // Übernahme des Werts aus dem JSpinner mit Parse-Check.
                anzahlTextField.commitEdit();
                int anzahl = (Integer) anzahlTextField.getValue();

                if (anzahl <= 0) throw new IllegalArgumentException("Anzahl muss > 0 sein.");

                // Erzeugung des Objekts und Speicherung in der Liste.
                DoenerOrder order = new DoenerOrder(fleisch, brot, anzahl, glutenfreiCheckBox.isSelected());
                app.addOrder(order);

                // UI-Reset nach Erfolg.
                anzahlTextField.setValue(1);
                refreshTable();

            } catch (IllegalArgumentException | ParseException ex) {
                // Fehlerbehandlung: Feedback an den Nutzer via Dialog.
                JOptionPane.showMessageDialog(rootPanel, ex.getMessage(), "Eingabefehler", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(rootPanel, "Fehler: " + ex.getMessage(), "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 3-Stufen-Validierung (UX): Schritt 2 - Preisprüfung vor Bestellung.
        gesamtpreisAnzeigenButton.addActionListener(e -> {
            if (app.getOrders().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Der Warenkorb ist leer.");
                return;
            }

            // Nutzung von Streams zur Summenbildung
            int sumCent = app.getOrders().stream().mapToInt(DoenerOrder::berechneGesamtpreisInCent).sum();
            JOptionPane.showMessageDialog(rootPanel, String.format("Gesamtpreis: %.2f €", sumCent / 100.0));
        });

        // 3-Stufen-Validierung (UX): Schritt 3 - Finale Bestellung.
        bestellenButton.addActionListener(e -> {
            if (app.getOrders().isEmpty()) {
                JOptionPane.showMessageDialog(rootPanel, "Bitte füge zuerst eine Bestellung hinzu.");
                return;
            }
            JOptionPane.showMessageDialog(rootPanel, "Guten Appetit! Bestellung wird vorbereitet.");
        });
    }

    /**
     * Synchronisiert die Tabelle mit der Liste der Bestellungen.
     * Nutzt eine Schleife über die Liste.
     */
    private void refreshTable() {
        tableModel.setRowCount(0); // Tabelle leeren vor Neuaufbau.
        for (DoenerOrder o : app.getOrders()) {
            double preisEuro = o.berechneGesamtpreisInCent() / 100.0;
            tableModel.addRow(new Object[]{
                    o.getFleisch(),
                    o.getBrot(),
                    o.getAnzahl(),
                    o.isGlutenfrei(),
                    String.format("%.2f", preisEuro),
                    o // Versteckte Referenz auf das Objekt selbst.
            });
        }
        applyFleischFilter(); // Filterzustand beibehalten.
    }

    public JPanel getRootPanel() { return rootPanel; }

    public static void start() {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("DönerHub - Bestelleffizienz");
            DoenerHubGUI gui = new DoenerHubGUI();
            frame.setContentPane(gui.getRootPanel());
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}