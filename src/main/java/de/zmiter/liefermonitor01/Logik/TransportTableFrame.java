package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.Artikel;
import de.zmiter.liefermonitor01.DBObjekte.Lieferung;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class TransportTableFrame extends JFrame {

    private static final int TRANSPORT_FELD_COUNT = 30;
    private static final String[] TRANSPORT_COLUMN_NAMES = new String[] {
        "Raus?",
        "Bestellnummer",
        "Materialbezeichnuung",
        "ANF / Agreem./ RNG",
        "WE",
        "Kolli-No.:",
        "gross weight",
        "Mesurements",
        "Cont. Stau",
        "Loading date",
        "Invoice",
        "Value in Euro",
        "Container No.",
        "Load Ref.",
        "Bemerkungen",
        "xxx",
        "Prio",
        "RAL booking",
        "Closing Aarhus",
        "ETA Nanortalik",
        "MRN",
        "AV",
        "f23",
        "f24",
        "f25",
        "f26",
        "f27",
        "f28",
        "f29",
        "f30"
    };

    private final JTextField filterField;
    private final JLabel statusLabel;
    private final JTable table;
    private final DefaultTableModel tableModel;
    private final Font baseTableFont;
    private final Font baseHeaderFont;
    private final int baseRowHeight;
    private final int baseHeaderHeight;
    private final Preferences colorPreferences;
    private final Map<String, Color> cellColorCache;
    private final JPopupMenu cellColorPopupMenu;
    private int popupTargetRow = -1;
    private int popupTargetColumn = -1;
    private static final Color LIGHT_BLUE = new Color(204, 229, 255);
    private static final Color LIGHT_GREEN = new Color(204, 255, 204);
    private static final Color LIGHT_YELLOW = new Color(255, 249, 196);
    private static final Color LIGHT_RED = new Color(255, 205, 210);
    private static final String COLOR_PREF_NODE = "transport_table_cell_colors";
    private static final int MIN_CELL_CHAR_WIDTH = 5;
    private static final int MAX_CELL_CHAR_WIDTH = 30;

    public TransportTableFrame() {
        setTitle("Transport - Tabellenansicht");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1350, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8, 8));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel filterLabel = new JLabel("Filter:");
        filterField = new JTextField();
        filterField.setPreferredSize(new Dimension(360, 28));
        JButton searchButton = new JButton("Suchen");
        JButton clearButton = new JButton("Zurücksetzen");
        JButton refreshButton = new JButton("Aktualisieren");

        topPanel.add(filterLabel);
        topPanel.add(filterField);
        topPanel.add(searchButton);
        topPanel.add(clearButton);
        topPanel.add(refreshButton);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(buildColumnNames(), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        colorPreferences = Preferences.userNodeForPackage(TransportTableFrame.class).node(COLOR_PREF_NODE);
        cellColorCache = new HashMap<>();
        loadSavedCellColors();
        cellColorPopupMenu = buildCellColorPopupMenu();
        table = new JTable(tableModel) {
            private final Color weRowHighlight = LIGHT_GREEN;

            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (isRowSelected(row)) {
                    return c;
                }

                Color savedCellColor = getSavedCellColor(row, column);
                if (savedCellColor != null) {
                    c.setBackground(savedCellColor);
                    return c;
                }

                int weColumnIndex = tableModel.findColumn("WE");
                if (weColumnIndex < 0) {
                    c.setBackground(getBackground());
                    return c;
                }

                Object weValue = tableModel.getValueAt(row, weColumnIndex);
                String weText = weValue == null ? "" : weValue.toString().trim().toUpperCase();
                if (weText.startsWith("WE")) {
                    c.setBackground(weRowHighlight);
                } else {
                    c.setBackground(getBackground());
                }
                return c;
            }
        };
        table.setDefaultRenderer(Object.class, new MultiLineCellRenderer());
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        configureWrappedColumns();
        baseTableFont = table.getFont();
        baseHeaderFont = table.getTableHeader().getFont();
        baseRowHeight = table.getRowHeight();
        baseHeaderHeight = table.getTableHeader().getPreferredSize().height;
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2 && javax.swing.SwingUtilities.isLeftMouseButton(evt)) {
                    openRowActionDialogForSelectedRow();
                }
            }

            @Override
            public void mousePressed(MouseEvent evt) {
                if (isRightClickTrigger(evt)) {
                    showCellColorPopupForPoint(evt);
                }
            }

            @Override
            public void mouseReleased(MouseEvent evt) {
                if (isRightClickTrigger(evt)) {
                    showCellColorPopupForPoint(evt);
                }
            }
        });
        table.getInputMap(javax.swing.JComponent.WHEN_FOCUSED)
                .put(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0), "deleteTransportRow");
        table.getActionMap().put("deleteTransportRow", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                deleteSelectedRowWithConfirm();
            }
        });
        add(new JScrollPane(table), BorderLayout.CENTER);

        statusLabel = new JLabel(" ");
        add(statusLabel, BorderLayout.SOUTH);
        configureMenuBar();

        searchButton.addActionListener(evt -> loadByFilter());
        clearButton.addActionListener(evt -> {
            filterField.setText("");
            loadByFilter();
        });
        refreshButton.addActionListener(evt -> loadByFilter());
        filterField.addActionListener(evt -> loadByFilter());

        loadByFilter();
    }

    private boolean isRightClickTrigger(MouseEvent evt) {
        return evt.isPopupTrigger();
    }

    private void showCellColorPopupForPoint(MouseEvent evt) {
        int viewRow = table.rowAtPoint(evt.getPoint());
        int viewColumn = table.columnAtPoint(evt.getPoint());
        if (viewRow < 0 || viewColumn < 0) {
            return;
        }
        popupTargetRow = viewRow;
        popupTargetColumn = viewColumn;
        table.changeSelection(viewRow, viewColumn, false, false);
        if (cellColorPopupMenu == null) {
            return;
        }
        cellColorPopupMenu.show(evt.getComponent(), evt.getX(), evt.getY());
    }

    private JPopupMenu buildCellColorPopupMenu() {
        JPopupMenu popup = new JPopupMenu("Zellenfarbe");
        popup.add(createColorMenuItem("Hellgrün", LIGHT_GREEN));
        popup.add(createColorMenuItem("Hellblau", LIGHT_BLUE));
        popup.add(createColorMenuItem("Hellgelb", LIGHT_YELLOW));
        popup.add(createColorMenuItem("Hellrot", LIGHT_RED));
        popup.addSeparator();

        JMenuItem defaultItem = new JMenuItem("Default");
        defaultItem.addActionListener(evt -> {
            if (!hasValidPopupTarget()) {
                return;
            }
            clearCellColor(popupTargetRow, popupTargetColumn);
            table.repaint();
        });
        popup.add(defaultItem);
        return popup;
    }

    private JMenuItem createColorMenuItem(String text, Color color) {
        JMenuItem item = new JMenuItem(text);
        item.setOpaque(true);
        item.setBackground(color);
        item.addActionListener(evt -> {
            if (!hasValidPopupTarget()) {
                return;
            }
            saveCellColor(popupTargetRow, popupTargetColumn, color);
            table.repaint();
        });
        return item;
    }

    private boolean hasValidPopupTarget() {
        if (popupTargetRow < 0 || popupTargetColumn < 0) {
            return false;
        }
        return popupTargetRow < table.getRowCount() && popupTargetColumn < table.getColumnCount();
    }

    private void loadSavedCellColors() {
        try {
            String[] keys = colorPreferences.keys();
            for (String key : keys) {
                String hex = colorPreferences.get(key, null);
                Color color = parseColor(hex);
                if (color != null) {
                    cellColorCache.put(key, color);
                }
            }
        } catch (BackingStoreException ignored) {
            cellColorCache.clear();
        }
    }

    private Color getSavedCellColor(int viewRow, int viewColumn) {
        String key = buildCellColorKey(viewRow, viewColumn);
        if (key == null) {
            return null;
        }
        return cellColorCache.get(key);
    }

    private void saveCellColor(int viewRow, int viewColumn, Color color) {
        String key = buildCellColorKey(viewRow, viewColumn);
        if (key == null || color == null) {
            return;
        }
        String hex = String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
        cellColorCache.put(key, color);
        colorPreferences.put(key, hex);
        try {
            colorPreferences.flush();
        } catch (BackingStoreException ignored) { }
    }

    private void clearCellColor(int viewRow, int viewColumn) {
        String key = buildCellColorKey(viewRow, viewColumn);
        if (key == null) {
            return;
        }
        cellColorCache.remove(key);
        colorPreferences.remove(key);
        try {
            colorPreferences.flush();
        } catch (BackingStoreException ignored) { }
    }

    private String buildCellColorKey(int viewRow, int viewColumn) {
        int modelRow = table.convertRowIndexToModel(viewRow);
        int modelColumn = table.convertColumnIndexToModel(viewColumn);
        Object idValue = tableModel.getValueAt(modelRow, 0);
        if (idValue == null) {
            return null;
        }
        String colName = tableModel.getColumnName(modelColumn);
        return idValue.toString().trim() + "|" + colName;
    }

    private Color parseColor(String hex) {
        if (hex == null || !hex.matches("^#[0-9A-Fa-f]{6}$")) {
            return null;
        }
        return new Color(
                Integer.parseInt(hex.substring(1, 3), 16),
                Integer.parseInt(hex.substring(3, 5), 16),
                Integer.parseInt(hex.substring(5, 7), 16)
        );
    }

    private void configureMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu editMenu = new JMenu("Edit");
        JMenuItem exportMenuItem = new JMenuItem("Export als Excel- dokument");
        exportMenuItem.addActionListener(evt -> exportTableToExcelAction());
        editMenu.add(exportMenuItem);
        JMenu viewMenu = new JMenu("Ansicht");
        int baseSize = baseTableFont != null ? baseTableFont.getSize() : 12;
        JMenuItem baseSizeItem = new JMenuItem("Aktuelle Schriftgröße (Basis): " + baseSize + " pt");
        baseSizeItem.setEnabled(false);
        JMenuItem increase30Item = new JMenuItem("Schriftgröße +30%");
        increase30Item.addActionListener(evt -> applyTableFontScale(1.3f));
        JMenuItem increase50Item = new JMenuItem("Schriftgröße +50%");
        increase50Item.addActionListener(evt -> applyTableFontScale(1.5f));
        viewMenu.add(baseSizeItem);
        viewMenu.add(increase30Item);
        viewMenu.add(increase50Item);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        setJMenuBar(menuBar);
    }

    private void applyTableFontScale(float scale) {
        if (scale <= 0f) {
            return;
        }
        int newFontSize = Math.max(10, Math.round(baseTableFont.getSize2D() * scale));
        int newHeaderFontSize = Math.max(10, Math.round(baseHeaderFont.getSize2D() * scale));
        table.setFont(baseTableFont.deriveFont((float) newFontSize));
        table.getTableHeader().setFont(baseHeaderFont.deriveFont((float) newHeaderFontSize));
        table.setRowHeight(Math.max(18, Math.round(baseRowHeight * scale)));
        configureWrappedColumns();
        recalculateAllRowHeights();
        table.getTableHeader().setPreferredSize(new Dimension(
                table.getTableHeader().getPreferredSize().width,
                Math.max(20, Math.round(baseHeaderHeight * scale))
        ));
        table.revalidate();
        table.repaint();
    }

    private String[] buildColumnNames() {
        String[] cols = new String[TRANSPORT_FELD_COUNT + 1];
        cols[0] = "id";
        System.arraycopy(TRANSPORT_COLUMN_NAMES, 0, cols, 1, TRANSPORT_FELD_COUNT);
        return cols;
    }

    private void loadByFilter() {
        String filterText = filterField.getText() == null ? "" : filterField.getText().trim();
        DBManager dbManager = new DBManager();
        try {
            ArrayList<String[]> rows = dbManager.searchTransportRecords(filterText);
            tableModel.setRowCount(0);
            for (String[] row : rows) {
                Object[] data = new Object[row.length];
                System.arraycopy(row, 0, data, 0, row.length);
                tableModel.addRow(data);
            }
            configureWrappedColumns();
            recalculateAllRowHeights();
            statusLabel.setText("Datensätze: " + rows.size() + " | Filter: " + (filterText.isEmpty() ? "(leer)" : filterText));
        } catch (Exception e) {
            statusLabel.setText("Fehler beim Laden: " + e.getMessage());
        } finally {
            dbManager.closeConnection();
        }
    }

    private void configureWrappedColumns() {
        int charPixelWidth = table.getFontMetrics(table.getFont()).charWidth('W');
        int minWidth = charPixelWidth * MIN_CELL_CHAR_WIDTH + 14;
        for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
            int maxTextLength = table.getColumnName(i) != null ? table.getColumnName(i).length() : 0;
            for (int row = 0; row < tableModel.getRowCount(); row++) {
                Object value = tableModel.getValueAt(row, i);
                if (value != null) {
                    maxTextLength = Math.max(maxTextLength, value.toString().length());
                }
            }
            int targetCharWidth = Math.min(MAX_CELL_CHAR_WIDTH, Math.max(MIN_CELL_CHAR_WIDTH, maxTextLength));
            int cellWidth = charPixelWidth * targetCharWidth + 14;
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setMinWidth(minWidth);
            column.setPreferredWidth(cellWidth);
            column.setMaxWidth(cellWidth);
        }
    }

    private void recalculateAllRowHeights() {
        int minimumRowHeight = table.getRowHeight();
        for (int row = 0; row < table.getRowCount(); row++) {
            int requiredHeight = minimumRowHeight;
            for (int col = 0; col < table.getColumnCount(); col++) {
                TableCellRenderer renderer = table.getCellRenderer(row, col);
                Component component = table.prepareRenderer(renderer, row, col);
                requiredHeight = Math.max(requiredHeight, component.getPreferredSize().height);
            }
            if (table.getRowHeight(row) != requiredHeight) {
                table.setRowHeight(row, requiredHeight);
            }
        }
    }

    private static class MultiLineCellRenderer extends JTextArea implements TableCellRenderer {

        private final DefaultTableCellRenderer fallbackRenderer = new DefaultTableCellRenderer();

        MultiLineCellRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
            setBorder(UIManager.getBorder("Table.cellBorder"));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            String text = value == null ? "" : String.valueOf(value);
            setText(text);
            setFont(table.getFont());

            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                Component fallback = fallbackRenderer.getTableCellRendererComponent(
                        table, value, false, hasFocus, row, column
                );
                setForeground(fallback.getForeground());
                setBackground(fallback.getBackground());
            }

            int columnWidth = table.getColumnModel().getColumn(column).getWidth();
            setSize(new Dimension(columnWidth, Short.MAX_VALUE));
            return this;
        }
    }

    private void openEditDialogForSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        Object idObj = tableModel.getValueAt(selectedRow, 0);
        if (idObj == null) {
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idObj.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ungültige ID der Zeile.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String originalBestellnummer = tableModel.getValueAt(selectedRow, 2) != null
                ? tableModel.getValueAt(selectedRow, 2).toString().trim()
                : "";

        JPanel panel = new JPanel();
        panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
        JTextField[] fields = new JTextField[TRANSPORT_FELD_COUNT];
        for (int i = 1; i <= TRANSPORT_FELD_COUNT; i++) {
            JPanel rowPanel = new JPanel(new BorderLayout(8, 0));
            JLabel label = new JLabel(TRANSPORT_COLUMN_NAMES[i - 1]);
            label.setPreferredSize(new Dimension(170, 24));
            label.setMinimumSize(new Dimension(140, 24));
            rowPanel.add(label, BorderLayout.WEST);

            String current = tableModel.getValueAt(selectedRow, i) != null
                    ? tableModel.getValueAt(selectedRow, i).toString()
                    : "";
            JTextField tf = new JTextField(current, 40);
            fields[i - 1] = tf;
            rowPanel.add(tf, BorderLayout.CENTER);
            rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
            panel.add(rowPanel);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(700, 520));

        int result = JOptionPane.showConfirmDialog(
                this,
                scrollPane,
                "Transport bearbeiten (ID=" + id + ")",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );
        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String[] values = new String[TRANSPORT_FELD_COUNT];
        for (int i = 0; i < TRANSPORT_FELD_COUNT; i++) {
            values[i] = fields[i].getText();
        }

        DBManager dbManager = new DBManager();
        try {
            String[] adjustedValues = adjustOverlongTransportValues(dbManager, values);
            if (adjustedValues == null) {
                return;
            }

            boolean ok = dbManager.updateTransportRecord(id, adjustedValues);
            if (ok) {
                String syncMessage = syncLieferungAfterTransportUpdate(dbManager, adjustedValues, originalBestellnummer);
                loadByFilter();
                JOptionPane.showMessageDialog(
                        this,
                        "Datensatz wurde aktualisiert." + (syncMessage.isEmpty() ? "" : "\n" + syncMessage),
                        "Erfolg",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(this, "Datensatz konnte nicht aktualisiert werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        } finally {
            dbManager.closeConnection();
        }
    }

    private String[] adjustOverlongTransportValues(DBManager dbManager, String[] values) {
        Map<Integer, Integer> maxLengths = dbManager.getTransportFieldMaxLengths();
        if (maxLengths == null || maxLengths.isEmpty()) {
            return values;
        }

        while (true) {
            LinkedHashMap<Integer, Integer> violations = new LinkedHashMap<>();
            for (int i = 0; i < TRANSPORT_FELD_COUNT; i++) {
                int fieldIndex = i + 1;
                int maxLength = maxLengths.getOrDefault(fieldIndex, Integer.MAX_VALUE);
                if (maxLength == Integer.MAX_VALUE) {
                    continue;
                }
                String current = values[i] == null ? "" : values[i];
                if (current.length() > maxLength) {
                    violations.put(fieldIndex, maxLength);
                }
            }

            if (violations.isEmpty()) {
                return values;
            }

            JPanel panel = new JPanel();
            panel.setLayout(new javax.swing.BoxLayout(panel, javax.swing.BoxLayout.Y_AXIS));
            panel.add(new JLabel("Einige Werte sind zu lang für die Datenbank. Bitte korrigieren:"));

            LinkedHashMap<Integer, JTextField> editors = new LinkedHashMap<>();
            for (Map.Entry<Integer, Integer> entry : violations.entrySet()) {
                int fieldIndex = entry.getKey();
                int maxLength = entry.getValue();

                JPanel rowPanel = new JPanel(new BorderLayout(8, 0));
                String labelText = TRANSPORT_COLUMN_NAMES[fieldIndex - 1] + " (max " + maxLength + ")";
                JLabel label = new JLabel(labelText);
                label.setPreferredSize(new Dimension(240, 24));
                rowPanel.add(label, BorderLayout.WEST);

                JTextField textField = new JTextField(values[fieldIndex - 1] == null ? "" : values[fieldIndex - 1], 45);
                rowPanel.add(textField, BorderLayout.CENTER);
                rowPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
                panel.add(rowPanel);
                editors.put(fieldIndex, textField);
            }

            JScrollPane scrollPane = new JScrollPane(panel);
            scrollPane.setPreferredSize(new Dimension(900, 320));
            int option = JOptionPane.showConfirmDialog(
                    this,
                    scrollPane,
                    "Werte zu lang - bitte anpassen",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (option != JOptionPane.OK_OPTION) {
                return null;
            }

            for (Map.Entry<Integer, JTextField> editorEntry : editors.entrySet()) {
                int fieldIndex = editorEntry.getKey();
                values[fieldIndex - 1] = editorEntry.getValue().getText();
            }
        }
    }

    private void openRowActionDialogForSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        String[] options = {"Bearbeiten", "Löschen", "Abbrechen"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Aktion für die ausgewählte Zeile wählen:",
                "Zeilenaktion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            openEditDialogForSelectedRow();
        } else if (choice == 1) {
            deleteSelectedRowWithConfirm();
        }
    }

    private void deleteSelectedRowWithConfirm() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bitte zuerst eine Zeile auswählen.",
                    "Löschen",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        Object idObj = tableModel.getValueAt(selectedRow, 0);
        if (idObj == null) {
            JOptionPane.showMessageDialog(this, "ID der ausgewählten Zeile ist leer.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idObj.toString());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ungültige ID der ausgewählten Zeile.", "Fehler", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Datensatz mit ID " + id + " wirklich löschen?",
                "Löschen bestätigen",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        DBManager dbManager = new DBManager();
        try {
            boolean ok = dbManager.deleteTransportRecord(id);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Datensatz wurde gelöscht.", "Erfolg", JOptionPane.INFORMATION_MESSAGE);
                loadByFilter();
            } else {
                JOptionPane.showMessageDialog(this, "Datensatz konnte nicht gelöscht werden.", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        } finally {
            dbManager.closeConnection();
        }
    }

    private void exportTableToExcelAction() {
        if (tableModel.getRowCount() == 0 || tableModel.getColumnCount() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Keine Daten in der Tabelle zum Exportieren.",
                    "Export",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Transport-Tabelle als Excel speichern");
        fileChooser.setSelectedFile(new java.io.File("transport_table_export.xls"));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Excel 97-2003 files (*.xls)", "xls"));

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".xls")) {
            filePath += ".xls";
        }

        Workbook workbook = null;
        FileOutputStream fileOut = null;
        try {
            workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Transport");

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(tableModel.getColumnName(col));
            }

            for (int row = 0; row < tableModel.getRowCount(); row++) {
                Row excelRow = sheet.createRow(row + 1);
                for (int col = 0; col < tableModel.getColumnCount(); col++) {
                    Cell cell = excelRow.createCell(col);
                    Object value = tableModel.getValueAt(row, col);
                    if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                    } else if (value instanceof Boolean) {
                        cell.setCellValue((Boolean) value);
                    } else {
                        cell.setCellValue(value != null ? value.toString() : "");
                    }
                }
            }

            for (int col = 0; col < tableModel.getColumnCount(); col++) {
                sheet.autoSizeColumn(col);
            }

            fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);

            JOptionPane.showMessageDialog(
                    this,
                    "Export erfolgreich abgeschlossen:\n" + filePath,
                    "Export",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Fehler beim Export nach Excel:\n" + e.getMessage(),
                    "Export",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (Exception ignored) { }
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (Exception ignored) { }
        }
    }

    private String syncLieferungAfterTransportUpdate(DBManager dbManager, String[] values, String fallbackBestellnummer) {
        if (values == null || values.length < TRANSPORT_FELD_COUNT) {
            return "";
        }

        String bestellnummer = values[1] != null ? values[1].trim() : "";
        String weValue = values[4] != null ? values[4].trim() : "";
        if (weValue.isEmpty()) {
            return "Hinweis: WE ist leer, Feld warenEingangTS wurde nicht geändert.";
        }
        if (bestellnummer.isEmpty()) {
            bestellnummer = fallbackBestellnummer != null ? fallbackBestellnummer.trim() : "";
            if (bestellnummer.isEmpty()) {
                return "Hinweis: Keine Bestellnummer gefunden, Lieferung wurde nicht synchronisiert.";
            }
        }

        Lieferung fullLieferung = findLieferungByBestellnummer(dbManager, bestellnummer);
        if (fullLieferung == null && fallbackBestellnummer != null && !fallbackBestellnummer.trim().isEmpty()
                && !fallbackBestellnummer.trim().equalsIgnoreCase(bestellnummer)) {
            fullLieferung = findLieferungByBestellnummer(dbManager, fallbackBestellnummer.trim());
        }
        if (fullLieferung == null) {
            return "Hinweis: Keine passende Lieferung zu Bestellnummer '" + bestellnummer + "' gefunden.";
        }

        String kolliValue = values[5] != null ? values[5].trim() : "";
        String grossWeightValue = values[6] != null ? values[6].trim() : "";
        String measurementsValue = values[7] != null ? values[7].trim() : "";
        String invoiceValue = values[10] != null ? values[10].trim() : "";
        String containerNoValue = values[12] != null ? values[12].trim() : "";
        String bemerkungenValue = values[14] != null ? values[14].trim() : "";

        String mergedBestellNummer = appendCommaSeparatedUnique(fullLieferung.getBestellNummer(), weValue);
        fullLieferung.setBestellNummer(mergedBestellNummer);
        fullLieferung.setKolli(kolliValue);
        fullLieferung.setWarenEingangTS(weValue);
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
            } catch (NumberFormatException ignore) { }
        }
        if (!measurementsValue.isEmpty()) {
            applyMeasurementsToLieferung(measurementsValue, fullLieferung);
        }

        boolean lieferungUpdated = dbManager.updateTabLieferungen(fullLieferung);
        if (!lieferungUpdated) {
            return "Hinweis: Lieferung wurde nicht aktualisiert.";
        }

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
        return "Lieferung synchronisiert (ID: " + fullLieferung.getId() + "), Lager-Updates: " + updatedArtikelCount;
    }

    private String appendCommaSeparatedUnique(String existingValue, String newValue) {
        String incoming = newValue == null ? "" : newValue.trim();
        if (incoming.isEmpty()) {
            return existingValue == null ? "" : existingValue.trim();
        }

        String existing = existingValue == null ? "" : existingValue.trim();
        if (existing.isEmpty()) {
            return incoming;
        }

        String[] parts = existing.split(",");
        for (String part : parts) {
            if (incoming.equalsIgnoreCase(part.trim())) {
                return existing;
            }
        }
        return existing + ", " + incoming;
    }

    private Lieferung findLieferungByBestellnummer(DBManager dbManager, String bestellnummer) {
        if (dbManager == null) {
            return null;
        }
        String wanted = normalizeBestellnummerForMatch(bestellnummer);
        if (wanted.isEmpty()) {
            return null;
        }

        // 1) Nutze die bestehende DB-Logik aus dem Import/UI-Pfad (mit SAP-Suffix-Normalisierung).
        Lieferung fromDbLookup = dbManager.getLieferungByBestellnummerForUi(wanted);
        if (fromDbLookup != null && fromDbLookup.getId() > 0) {
            return hydrateLieferungById(dbManager, fromDbLookup.getId());
        }

        // 2) Fallback: robuste Suche in bereits geladenen Lieferungen.
        ArrayList<Lieferung> allLieferungen = dbManager.getAllLieferungen();
        if (allLieferungen == null || allLieferungen.isEmpty()) {
            return null;
        }
        for (Lieferung lieferung : allLieferungen) {
            if (lieferung == null) {
                continue;
            }
            String candidate = lieferung.getBestellNummer() != null ? lieferung.getBestellNummer().trim() : "";
            if (matchesBestellnummer(candidate, wanted)) {
                return lieferung;
            }
        }
        return null;
    }

    private boolean matchesBestellnummer(String candidateRaw, String wantedNormalized) {
        if (wantedNormalized == null || wantedNormalized.isEmpty()) {
            return false;
        }
        String candidateNormalized = normalizeBestellnummerForMatch(candidateRaw);
        if (candidateNormalized.isEmpty()) {
            return false;
        }
        if (candidateNormalized.equalsIgnoreCase(wantedNormalized)) {
            return true;
        }
        // Häufige Fälle aus Importen: zusätzliche Teile/Suffixe im selben Feld.
        if (candidateNormalized.toUpperCase().contains(wantedNormalized.toUpperCase())) {
            return true;
        }
        String[] tokens = candidateNormalized.split(",");
        for (String token : tokens) {
            String t = normalizeBestellnummerForMatch(token);
            if (t.equalsIgnoreCase(wantedNormalized)) {
                return true;
            }
            if (t.toUpperCase().contains(wantedNormalized.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    private String normalizeBestellnummerForMatch(String raw) {
        if (raw == null) {
            return "";
        }
        String v = raw.trim();
        v = v.replaceFirst("(?i)\\s*\\(?SAP\\)?\\s*$", "").trim();
        return v;
    }

    private Lieferung hydrateLieferungById(DBManager dbManager, int id) {
        if (id <= 0) {
            return null;
        }
        ArrayList<Lieferung> allLieferungen = dbManager.getAllLieferungen();
        if (allLieferungen == null) {
            return null;
        }
        for (Lieferung l : allLieferungen) {
            if (l != null && l.getId() == id) {
                return l;
            }
        }
        return null;
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

    private String applyMeasurementsToLieferung(String rawMeasurements, Lieferung lieferung) {
        if (rawMeasurements == null || rawMeasurements.trim().isEmpty()) {
            return "Mesurements ist leer.";
        }
        String normalized = rawMeasurements.trim().replace('×', 'x').replace('X', 'x');
        java.util.regex.Matcher matcher = java.util.regex.Pattern
                .compile("[-+]?\\d+(?:[\\.,]\\d+)?")
                .matcher(normalized);
        java.util.ArrayList<Float> nums = new java.util.ArrayList<>();
        while (matcher.find()) {
            String token = matcher.group().replace(",", ".");
            try {
                nums.add(Float.parseFloat(token));
            } catch (NumberFormatException ignore) { }
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
}
