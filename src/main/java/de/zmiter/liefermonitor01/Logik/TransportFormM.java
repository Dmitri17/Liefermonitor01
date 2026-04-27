/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.Artikel;
import de.zmiter.liefermonitor01.DBObjekte.Lieferung;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

/**
 *
 * @author lepeschko
 */
public class TransportFormM extends javax.swing.JFrame{
    private final ArrayList<Lieferung> bestellLieferungen = new ArrayList<>();
    private boolean suppressComboLoad = false;
    
    public TransportFormM(){
        startComponents();
        this.setTitle("Thyssen Schachtbau GmbH (Logistik)");
        bindHandlers();
        loadBestellNummernIntoCombo();
    }
    public void startComponents(){
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextFieldSearchBestellung = new javax.swing.JTextField();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>());
        jComboBox2.setBorder(javax.swing.BorderFactory.createTitledBorder("Bestellnummer (SAP)"));

        jTextFieldSearchBestellung.setBorder(javax.swing.BorderFactory.createTitledBorder("Suche Bestellnummer"));

        jTextField1.setText("jTextField1");
        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder("Lieferant"));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldSearchBestellung)
                    .addComponent(jComboBox2, 0, 466, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jTextFieldSearchBestellung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jList1.setBorder(javax.swing.BorderFactory.createTitledBorder("Artikelliste der Bestellung"));
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1015, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                // Alter Header (nicht löschen, nur auskommentiert):
                // "Container Nr.", "Booking Nr.", "add.Info.", "good receipt", "amount of containers", "weight", "Agreement", "shipment", "Date of arrival", "invoice (Proforma)", "dimention", "Colli"
                "Raus?", "Bestellnummer", "Materialbezeichnuung", "ANF / Agreem./ RNG", "WE", "Kolli-No.:", "gross weight", "Mesurements", "Cont. Stau", "Loading date", "Invoice", "Value in Euro", "Container No.", "Load Ref.", "Bemerkungen", "xxx", "Prio", "RAL booking", "Closing Aarhus", "ETA Nanortalik", "MRN", "AV", "f23", "f24", "f25", "f26", "f27", "f28", "f29", "f30"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable1.putClientProperty("JTable.autoStartsEdit", Boolean.TRUE);
        jTable1.setSurrendersFocusOnKeystroke(true);
        jTable1.setRowHeight(30);
        javax.swing.DefaultCellEditor singleClickEditor = new javax.swing.DefaultCellEditor(new javax.swing.JTextField());
        singleClickEditor.setClickCountToStart(1);
        jTable1.setDefaultEditor(Object.class, singleClickEditor);
        configureTableCellNavigation();
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 8));
        javax.swing.JButton jButtonSaveAsNew = new javax.swing.JButton("In gewählte Lieferung speichern");
        jButtonSaveAsNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveTableRowAsNewTransportRecordAction();
            }
        });
        jPanel4.add(jButtonSaveAsNew);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu1.setText("File");

        jMenuItem1.setText("Änderungen speichern");
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("jMenuItem2");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem3.setText("Add in Excel Document");
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Alle Bestellungen anzeigen");
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Tabelenansicht");

        jMenuItem5.setText("Tabelle öfnen");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openTransportTableViewAction();
            }
        });
        jMenu3.add(jMenuItem5);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("DB-Optionen");

        jMenuItem6.setText("Create transport table");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createTransportTableAction();
            }
        });
        jMenu4.add(jMenuItem6);

        jMenuItem7.setText("Search transport records");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTransportRecordsAction();
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuItem8.setText("Add transport record");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTransportRecordAction();
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuItem9.setText("Update transport record");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTransportRecordAction();
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuItem10.setText("Delete transport record");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteTransportRecordAction();
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuItem11.setText("Import / Add from Excel...");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                importTransportFromExcelAction();
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuItem12.setText("Replace from Excel...");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceTransportFromExcelAction();
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }
    
    private void loadBestellNummernIntoCombo() {
        final javax.swing.JDialog loadingDialog = createDbLoadingDialog("Lese Bestellnummern aus der Datenbank...");
        javax.swing.SwingWorker<DefaultComboBoxModel<Lieferung>, Void> worker = new javax.swing.SwingWorker<DefaultComboBoxModel<Lieferung>, Void>() {
            @Override
            protected DefaultComboBoxModel<Lieferung> doInBackground() {
                DBManager dbManager = new DBManager();
                try {
                    ArrayList<String> bestellNummern = dbManager.getDistinctBestellNummern();
                    bestellLieferungen.clear();
                    DefaultComboBoxModel<Lieferung> model = new DefaultComboBoxModel<>();
                    for (String nummer : bestellNummern) {
                        Lieferung lieferung = dbManager.getLieferungByBestellnummerForUi(nummer);
                        if (lieferung == null) {
                            continue;
                        }
                        bestellLieferungen.add(lieferung);
                        model.addElement(lieferung);
                    }
                    return model;
                } finally {
                    dbManager.closeConnection();
                }
            }

            @Override
            protected void done() {
                loadingDialog.dispose();
                try {
                    DefaultComboBoxModel<Lieferung> model = get();
                    applyComboRenderer();
                    jComboBox2.setModel(model);
                    applyBestellungFilter();
                } catch (Exception e) {
                    System.err.println("Fehler beim Laden der Bestellnummern: " + e.getMessage());
                } finally {
                    suppressComboLoad = false;
                }
                loadArtikelForSelectedBestellnummer();
            }
        };
        worker.execute();
        loadingDialog.setVisible(true);
    }

    private void bindHandlers() {
        jComboBox2.addActionListener(evt -> loadArtikelForSelectedBestellnummer());
        jTextFieldSearchBestellung.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                applyBestellungFilter();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                applyBestellungFilter();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                applyBestellungFilter();
            }
        });
    }

    private void applyComboRenderer() {
        jComboBox2.setRenderer(new javax.swing.DefaultListCellRenderer() {
            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                java.awt.Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Lieferung) {
                    Lieferung l = (Lieferung) value;
                    String nr = l.getBestellNummer() != null ? l.getBestellNummer().trim() : "";
                    String txt = nr.isEmpty() ? "(ohne Bestellnummer)" : nr;
                    setText(txt);
                }
                return c;
            }
        });
    }

    private void applyBestellungFilter() {
        if (bestellLieferungen == null) {
            return;
        }
        String filter = jTextFieldSearchBestellung.getText();
        String f = filter == null ? "" : filter.trim().toLowerCase();

        Lieferung selectedBefore = null;
        Object current = jComboBox2.getSelectedItem();
        if (current instanceof Lieferung) {
            selectedBefore = (Lieferung) current;
        }

        suppressComboLoad = true;
        DefaultComboBoxModel<Lieferung> filteredModel = new DefaultComboBoxModel<>();
        for (Lieferung l : bestellLieferungen) {
            if (l == null) {
                continue;
            }
            String bestellNr = l.getBestellNummer() != null ? l.getBestellNummer() : "";
            String anfordNr = l.getAnfordNummer() != null ? l.getAnfordNummer() : "";
            String lieferant = l.getLieferantName() != null ? l.getLieferantName() : "";
            String haystack = (bestellNr + " " + anfordNr + " " + lieferant).toLowerCase();
            if (f.isEmpty() || haystack.contains(f)) {
                filteredModel.addElement(l);
            }
        }
        jComboBox2.setModel(filteredModel);
        applyComboRenderer();

        if (selectedBefore != null) {
            for (int i = 0; i < filteredModel.getSize(); i++) {
                Lieferung l = filteredModel.getElementAt(i);
                if (l != null && l.getId() == selectedBefore.getId()) {
                    jComboBox2.setSelectedIndex(i);
                    suppressComboLoad = false;
                    loadArtikelForSelectedBestellnummer();
                    return;
                }
            }
        }
        suppressComboLoad = false;
        if (filteredModel.getSize() > 0) {
            jComboBox2.setSelectedIndex(0);
            loadArtikelForSelectedBestellnummer();
        } else {
            jList1.setModel(new DefaultListModel<>());
            jTextField1.setText("");
        }
    }

    private void loadArtikelForSelectedBestellnummer() {
        if (suppressComboLoad) {
            return;
        }
        Object selected = jComboBox2.getSelectedItem();
        if (!(selected instanceof Lieferung)) {
            jList1.setModel(new DefaultListModel<>());
            jTextField1.setText("");
            return;
        }
        final Lieferung lieferung = (Lieferung) selected;
        final javax.swing.JDialog loadingDialog = createDbLoadingDialog("Lese Artikelliste aus der Datenbank...");

        javax.swing.SwingWorker<DefaultListModel<String>, Void> worker = new javax.swing.SwingWorker<DefaultListModel<String>, Void>() {
            @Override
            protected DefaultListModel<String> doInBackground() {
                DBManager dbManager = new DBManager();
                try {
                    DefaultListModel<String> listModel = new DefaultListModel<>();
                    String anford = lieferung.getAnfordNummer() != null ? lieferung.getAnfordNummer().trim() : "";
                    if (anford.isEmpty()) {
                        return listModel;
                    }
                    ArrayList<Artikel> artikelList = dbManager.getArtikelByAnford(anford);
                    for (Artikel artikel : artikelList) {
                        if (artikel == null) {
                            continue;
                        }
                        String material = artikel.getMaterial_DE() != null && !artikel.getMaterial_DE().trim().isEmpty()
                                ? artikel.getMaterial_DE().trim()
                                : (artikel.getMaterial_EN() != null ? artikel.getMaterial_EN().trim() : "");
                        String zeile = material + " | " + artikel.getMenge() + " " + (artikel.getEinheit() != null ? artikel.getEinheit() : "");
                        listModel.addElement(zeile);
                    }
                    return listModel;
                } finally {
                    dbManager.closeConnection();
                }
            }

            @Override
            protected void done() {
                loadingDialog.dispose();
                try {
                    jTextField1.setText(lieferung.getLieferantName() != null ? lieferung.getLieferantName() : "");
                    jList1.setModel(get());
                } catch (Exception e) {
                    System.err.println("Fehler beim Laden der Artikelliste: " + e.getMessage());
                }
            }
        };
        worker.execute();
        loadingDialog.setVisible(true);
    }

    private javax.swing.JDialog createDbLoadingDialog(String message) {
        // Modaler Dialog blockiert das Anzeigen/Interagieren mit dem Fenster,
        // bis die Daten vollständig geladen sind.
        javax.swing.JDialog dialog = new javax.swing.JDialog(this, "Bitte warten", true);
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setLayout(new java.awt.BorderLayout(10, 10));
        dialog.add(new javax.swing.JLabel(message), java.awt.BorderLayout.NORTH);
        javax.swing.JProgressBar progressBar = new javax.swing.JProgressBar();
        progressBar.setIndeterminate(true);
        dialog.add(progressBar, java.awt.BorderLayout.CENTER);
        dialog.setSize(420, 110);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);
        return dialog;
    }

    private void createTransportTableAction() {
        DBManager dbManager = new DBManager();
        try {
            boolean ok = dbManager.createTransportTable();
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    ok ? "Tabelle 'transport' ist bereit." : "Tabelle 'transport' konnte nicht erstellt werden.",
                    "DB-Optionen",
                    ok ? javax.swing.JOptionPane.INFORMATION_MESSAGE : javax.swing.JOptionPane.ERROR_MESSAGE
            );
        } finally {
            dbManager.closeConnection();
        }
    }

    private void searchTransportRecordsAction() {
        String text = javax.swing.JOptionPane.showInputDialog(
                this,
                "Suchtext für transport (leer = alle Datensätze):",
                ""
        );
        if (text == null) {
            return;
        }
        DBManager dbManager = new DBManager();
        try {
            java.util.ArrayList<String[]> rows = dbManager.searchTransportRecords(text);
            StringBuilder sb = new StringBuilder();
            sb.append("Gefundene Datensätze: ").append(rows.size()).append("\n\n");
            for (int i = 0; i < rows.size(); i++) {
                String[] row = rows.get(i);
                sb.append("ID=").append(row[0]).append(" | ");
                for (int f = 1; f <= 30; f++) {
                    String v = row[f] != null ? row[f] : "";
                    if (v.length() > 30) {
                        v = v.substring(0, 30) + "...";
                    }
                    sb.append("feld").append(String.format("%02d", f)).append("=").append(v);
                    if (f < 30) {
                        sb.append(" ; ");
                    }
                }
                sb.append("\n");
                if (i >= 19) {
                    sb.append("... weitere Datensätze gekürzt ...");
                    break;
                }
            }
            javax.swing.JTextArea area = new javax.swing.JTextArea(sb.toString());
            area.setEditable(false);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(area);
            scroll.setPreferredSize(new java.awt.Dimension(900, 450));
            javax.swing.JOptionPane.showMessageDialog(this, scroll, "Search transport", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } finally {
            dbManager.closeConnection();
        }
    }

    private void addTransportRecordAction() {
        String input = javax.swing.JOptionPane.showInputDialog(
                this,
                "30 Werte eingeben, getrennt durch ';' (Reihenfolge: feld01..feld30):",
                ""
        );
        if (input == null) {
            return;
        }
        String[] values = parseTransportValues(input);
        if (values == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Ungültige Eingabe. Bitte genau 30 Werte mit ';' trennen.",
                    "Eingabefehler",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        DBManager dbManager = new DBManager();
        try {
            int newId = dbManager.addTransportRecord(values);
            boolean ok = newId > 0;
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    ok ? "Datensatz erfolgreich hinzugefügt. ID=" + newId : "Datensatz konnte nicht hinzugefügt werden.",
                    "DB-Optionen",
                    ok ? javax.swing.JOptionPane.INFORMATION_MESSAGE : javax.swing.JOptionPane.ERROR_MESSAGE
            );
        } finally {
            dbManager.closeConnection();
        }
    }

    private void updateTransportRecordAction() {
        String idText = javax.swing.JOptionPane.showInputDialog(this, "ID des Datensatzes:", "");
        if (idText == null) {
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idText.trim());
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "ID ist ungültig.", "Eingabefehler", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        String input = javax.swing.JOptionPane.showInputDialog(
                this,
                "Neue 30 Werte eingeben, getrennt durch ';' (feld01..feld30):",
                ""
        );
        if (input == null) {
            return;
        }
        String[] values = parseTransportValues(input);
        if (values == null) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Ungültige Eingabe. Bitte genau 30 Werte mit ';' trennen.",
                    "Eingabefehler",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        DBManager dbManager = new DBManager();
        try {
            boolean ok = dbManager.updateTransportRecord(id, values);
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    ok ? "Datensatz erfolgreich aktualisiert." : "Datensatz konnte nicht aktualisiert werden.",
                    "DB-Optionen",
                    ok ? javax.swing.JOptionPane.INFORMATION_MESSAGE : javax.swing.JOptionPane.ERROR_MESSAGE
            );
        } finally {
            dbManager.closeConnection();
        }
    }

    private void deleteTransportRecordAction() {
        String idText = javax.swing.JOptionPane.showInputDialog(this, "ID des zu löschenden Datensatzes:", "");
        if (idText == null) {
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idText.trim());
        } catch (Exception ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "ID ist ungültig.", "Eingabefehler", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Datensatz mit ID " + id + " wirklich löschen?",
                "Löschen bestätigen",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        DBManager dbManager = new DBManager();
        try {
            boolean ok = dbManager.deleteTransportRecord(id);
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    ok ? "Datensatz erfolgreich gelöscht." : "Datensatz konnte nicht gelöscht werden.",
                    "DB-Optionen",
                    ok ? javax.swing.JOptionPane.INFORMATION_MESSAGE : javax.swing.JOptionPane.ERROR_MESSAGE
            );
        } finally {
            dbManager.closeConnection();
        }
    }

    private void importTransportFromExcelAction() {
        String filePath = chooseExcelFileForTransportImport();
        if (filePath == null) {
            return;
        }
        runTransportImport(filePath);
    }

    private String chooseExcelFileForTransportImport() {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Excel-Datei für transport auswählen");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Excel Dateien (*.xlsx, *.xls)", "xlsx", "xls"));
        int result = fileChooser.showOpenDialog(this);
        if (result != javax.swing.JFileChooser.APPROVE_OPTION) {
            return null;
        }
        return fileChooser.getSelectedFile().getAbsolutePath();
    }

    private void runTransportImport(String filePath) {
        DBManager dbManager = new DBManager();
        try {
            String report = dbManager.importTransportFromExcel(filePath);
            javax.swing.JTextArea area = new javax.swing.JTextArea(report);
            area.setEditable(false);
            area.setLineWrap(true);
            area.setWrapStyleWord(true);
            area.setCaretPosition(0);
            javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(area);
            scroll.setPreferredSize(new java.awt.Dimension(700, 220));
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    scroll,
                    "Import / Add transport aus Excel",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
        } finally {
            dbManager.closeConnection();
        }
    }

    private void replaceTransportFromExcelAction() {
        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Tabelle 'transport' wird vollständig geleert und danach neu aus Excel geladen.\nFortfahren?",
                "Replace transport from Excel",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        String filePath = chooseExcelFileForTransportImport();
        if (filePath == null) {
            return;
        }

        DBManager dbManager = new DBManager();
        try {
            boolean ok = dbManager.truncateTable("transport");
            if (!ok) {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Tabelle 'transport' konnte nicht geleert werden.",
                        "Replace transport from Excel",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                );
                return;
            }
        } finally {
            dbManager.closeConnection();
        }

        runTransportImport(filePath);
    }

    private void saveTableRowAsNewTransportRecordAction() {
        if (jTable1.isEditing()) {
            jTable1.getCellEditor().stopCellEditing();
        }

        Object selected = jComboBox2.getSelectedItem();
        if (!(selected instanceof Lieferung)) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Bitte zuerst eine Bestellung (Lieferung) im oberen Auswahlfeld wählen.",
                    "Speichern",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        Lieferung selectedLieferung = (Lieferung) selected;
        int selectedLieferungId = selectedLieferung.getId();
        if (selectedLieferungId <= 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Die gewählte Lieferung hat keine gültige ID.",
                    "Speichern",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int row = jTable1.getSelectedRow();
        if (row < 0) {
            row = findFirstNonEmptyTableRow();
        }
        if (row < 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Keine Datenzeile gefunden. Bitte zuerst Werte in die Tabelle eingeben.",
                    "Speichern",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (jTable1.getColumnCount() <= 7) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Spalten 'Kolli-No.:', 'gross weight' oder 'Mesurements' wurden in der Tabelle nicht gefunden.",
                    "Speichern",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        Object kolliCell = jTable1.getValueAt(row, 5); // "Kolli-No.:"
        String kolliValue = kolliCell == null ? "" : kolliCell.toString().trim();
        Object weCell = jTable1.getValueAt(row, 4); // "WE"
        String weValue = weCell == null ? "" : weCell.toString().trim();
        Object containerNoCell = jTable1.getValueAt(row, 12); // "Container No."
        String containerNoValue = containerNoCell == null ? "" : containerNoCell.toString().trim();
        Object bemerkungenCell = jTable1.getValueAt(row, 14); // "Bemerkungen"
        String bemerkungenValue = bemerkungenCell == null ? "" : bemerkungenCell.toString().trim();
        Object invoiceCell = jTable1.getValueAt(row, 10); // "Invoice"
        String invoiceValue = invoiceCell == null ? "" : invoiceCell.toString().trim();
        Object grossWeightCell = jTable1.getValueAt(row, 6); // "gross weight"
        String grossWeightValue = grossWeightCell == null ? "" : grossWeightCell.toString().trim();
        Object measurementsCell = jTable1.getValueAt(row, 7); // "Mesurements"
        String measurementsValue = measurementsCell == null ? "" : measurementsCell.toString().trim();

        DBManager dbManager = new DBManager();
        try {
            ArrayList<Lieferung> allLieferungen = dbManager.getAllLieferungen();
            Lieferung fullLieferung = null;
            for (Lieferung l : allLieferungen) {
                if (l != null && l.getId() == selectedLieferungId) {
                    fullLieferung = l;
                    break;
                }
            }
            if (fullLieferung == null) {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Die gewählte Lieferung wurde in der Datenbank nicht gefunden.",
                        "Speichern",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            fullLieferung.setKolli(kolliValue);
            if (!weValue.isEmpty()) {
                fullLieferung.setWarenEingangTS(weValue);
            }
            if (!containerNoValue.isEmpty()) {
                fullLieferung.setContainerNr(containerNoValue);
            }
            if (!bemerkungenValue.isEmpty()) {
                fullLieferung.setKommentarBS(bemerkungenValue);
            }
            if (!grossWeightValue.isEmpty()) {
                try {
                    float gewicht = Float.parseFloat(grossWeightValue.replace(",", "."));
                    fullLieferung.setGewicht(gewicht);
                } catch (NumberFormatException ex) {
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Ungültiger Wert in 'gross weight': " + grossWeightValue + "\n"
                                    + "Das Feld 'gewicht' wird nicht geändert.",
                            "Hinweis",
                            javax.swing.JOptionPane.WARNING_MESSAGE
                    );
                }
            }
            if (!measurementsValue.isEmpty()) {
                String parseError = applyMeasurementsToLieferung(measurementsValue, fullLieferung);
                if (parseError != null) {
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            parseError + "\nDas Feld 'Mesurements' wird nicht übernommen.",
                            "Hinweis",
                            javax.swing.JOptionPane.WARNING_MESSAGE
                    );
                }
            }
            boolean ok = dbManager.updateTabLieferungen(fullLieferung);
            if (ok) {
                int updatedArtikelCount = updateRelatedLagerEntriesFromTransport(
                        dbManager,
                        fullLieferung,
                        kolliValue,
                        weValue,
                        containerNoValue,
                        bemerkungenValue,
                        invoiceValue,
                        grossWeightValue,
                        measurementsValue
                );
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Gespeichert: Kolli-No. wurde in Lieferung übernommen.\n"
                                + "Lieferung-ID: " + selectedLieferungId
                                + "\nKolli: " + kolliValue
                                + (!weValue.isEmpty() ? "\nWE: " + weValue : "")
                                + (!containerNoValue.isEmpty() ? "\nContainer No.: " + containerNoValue : "")
                                + (!bemerkungenValue.isEmpty() ? "\nBemerkungen: " + bemerkungenValue : "")
                                + (!invoiceValue.isEmpty() ? "\nInvoice: " + invoiceValue : "")
                                + (!grossWeightValue.isEmpty() ? "\nGross weight: " + grossWeightValue : "")
                                + (!measurementsValue.isEmpty() ? "\nMesurements: " + measurementsValue : "")
                                + "\nLager-Updates: " + updatedArtikelCount,
                        "Speichern",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Änderung konnte nicht gespeichert werden.",
                        "Fehler",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                );
            }
        } finally {
            dbManager.closeConnection();
        }
    }

    private int updateRelatedLagerEntriesFromTransport(
            DBManager dbManager,
            Lieferung lieferung,
            String kolliValue,
            String weValue,
            String containerNoValue,
            String bemerkungenValue,
            String invoiceValue,
            String grossWeightValue,
            String measurementsValue
    ) {
        if (lieferung == null) {
            return 0;
        }
        String anford = lieferung.getAnfordNummer() != null ? lieferung.getAnfordNummer().trim() : "";
        if (anford.isEmpty()) {
            return 0;
        }

        ArrayList<Artikel> artikelList = dbManager.getArtikelByAnford(anford);
        if (artikelList == null || artikelList.isEmpty()) {
            return 0;
        }

        int updatedCount = 0;
        for (Artikel artikel : artikelList) {
            if (artikel == null) {
                continue;
            }
            if (!kolliValue.isEmpty()) {
                artikel.setColli(kolliValue);
            }
            if (!weValue.isEmpty()) {
                artikel.setGood_Receipt(weValue);
            }
            if (!containerNoValue.isEmpty()) {
                artikel.setContainer_Number(containerNoValue);
            }
            if (!bemerkungenValue.isEmpty()) {
                artikel.setAdd_Info(bemerkungenValue);
            }
            if (!invoiceValue.isEmpty()) {
                artikel.setInvoice_Proforma(invoiceValue);
            }
            if (!grossWeightValue.isEmpty()) {
                artikel.setWeight(grossWeightValue);
            }
            if (!measurementsValue.isEmpty()) {
                artikel.setDimention(measurementsValue);
            }
            if (artikel.getId() > 0 && dbManager.updateArtikel(artikel)) {
                updatedCount++;
            }
        }

        return updatedCount;
    }

    /**
     * Parst Mesurements-Text im Format L x B x H (z.B. 125x85x97) und schreibt
     * die Float-Werte in Lieferung.laenge/breite/hoehe.
     *
     * @return null bei Erfolg, sonst Fehlermeldung
     */
    private String applyMeasurementsToLieferung(String rawMeasurements, Lieferung lieferung) {
        if (rawMeasurements == null || rawMeasurements.trim().isEmpty()) {
            return "Mesurements ist leer.";
        }
        String normalized = rawMeasurements.trim();
        normalized = normalized.replace('×', 'x').replace('X', 'x');

        java.util.regex.Matcher matcher = java.util.regex.Pattern
                .compile("[-+]?\\d+(?:[\\.,]\\d+)?")
                .matcher(normalized);

        java.util.ArrayList<Float> nums = new java.util.ArrayList<>();
        while (matcher.find()) {
            String token = matcher.group().replace(",", ".");
            try {
                nums.add(Float.parseFloat(token));
            } catch (NumberFormatException ignore) {
            }
            if (nums.size() >= 3) {
                break;
            }
        }

        if (nums.size() < 3) {
            return "Mesurements konnte nicht als LxBxH gelesen werden: " + rawMeasurements;
        }

        lieferung.setLaenge(nums.get(0));
        lieferung.setBreite(nums.get(1));
        lieferung.setHoehe(nums.get(2));
        return null;
    }

    private int findFirstNonEmptyTableRow() {
        for (int row = 0; row < jTable1.getRowCount(); row++) {
            for (int col = 0; col < jTable1.getColumnCount(); col++) {
                Object cell = jTable1.getValueAt(row, col);
                if (cell != null && !cell.toString().trim().isEmpty()) {
                    return row;
                }
            }
        }
        return -1;
    }

    private void configureTableCellNavigation() {
        javax.swing.InputMap inputMap = jTable1.getInputMap(javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        javax.swing.ActionMap actionMap = jTable1.getActionMap();

        inputMap.put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0), "moveCellNext");
        inputMap.put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB, 0), "moveCellNext");
        inputMap.put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB, java.awt.event.InputEvent.SHIFT_DOWN_MASK), "moveCellPrev");
        inputMap.put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_RIGHT, 0), "moveCellNext");
        inputMap.put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_LEFT, 0), "moveCellPrev");

        actionMap.put("moveCellNext", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                moveTableSelectionBy(1);
            }
        });
        actionMap.put("moveCellPrev", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                moveTableSelectionBy(-1);
            }
        });
    }

    private void moveTableSelectionBy(int delta) {
        int rows = jTable1.getRowCount();
        int cols = jTable1.getColumnCount();
        if (rows <= 0 || cols <= 0) {
            return;
        }

        if (jTable1.isEditing()) {
            jTable1.getCellEditor().stopCellEditing();
        }

        int row = jTable1.getSelectedRow();
        int col = jTable1.getSelectedColumn();
        if (row < 0 || col < 0) {
            row = 0;
            col = 0;
        } else {
            col += delta;
            if (col >= cols) {
                col = 0;
                row = Math.min(row + 1, rows - 1);
            } else if (col < 0) {
                col = cols - 1;
                row = Math.max(row - 1, 0);
            }
        }

        jTable1.changeSelection(row, col, false, false);
        jTable1.editCellAt(row, col);
        java.awt.Component editor = jTable1.getEditorComponent();
        if (editor != null) {
            editor.requestFocusInWindow();
        }
    }

    private void openTransportTableViewAction() {
        TransportTableFrame tableFrame = new TransportTableFrame();
        tableFrame.setLocationRelativeTo(this);
        tableFrame.setVisible(true);
    }

    private String[] parseTransportValues(String input) {
        if (input == null) {
            return null;
        }
        String[] raw = input.split(";", -1);
        if (raw.length != 30) {
            return null;
        }
        String[] values = new String[30];
        for (int i = 0; i < raw.length; i++) {
            values[i] = raw[i].trim();
        }
        return values;
    }
    
      public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TransportFormModel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransportFormModel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransportFormModel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransportFormModel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransportFormM().setVisible(true);
            }
        });
    }
    
     private javax.swing.JComboBox<Lieferung> jComboBox2;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldSearchBestellung;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration                    
}
