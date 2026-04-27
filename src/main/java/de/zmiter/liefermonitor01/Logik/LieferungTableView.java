package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.Lieferung;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LieferungTableView extends JFrame {

    private final ArrayList<Lieferung> lieferListRef;
    private final JTable table;
    private final DefaultTableModel model;
    private final int idColumnIndex;

    public LieferungTableView(JFrame parentFrame, ArrayList<Lieferung> lieferListRef) {
        this.lieferListRef = lieferListRef != null ? lieferListRef : new ArrayList<>();
        setTitle("Lieferungen als Tabelle");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(1400, 700);
        setLocationRelativeTo(parentFrame);

        Field[] allFields = Lieferung.class.getDeclaredFields();
        ArrayList<Field> fields = new ArrayList<>();
        for (Field field : allFields) {
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                fields.add(field);
            }
        }

        String[] columnNames = new String[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            columnNames[i] = fields.get(i).getName();
        }

        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }
        };

        int idCol = -1;
        for (int i = 0; i < fields.size(); i++) {
            if ("id".equals(fields.get(i).getName())) {
                idCol = i;
                break;
            }
        }
        idColumnIndex = idCol;

        for (Lieferung lieferung : this.lieferListRef) {
            Object[] row = new Object[fields.size()];
            for (int i = 0; i < fields.size(); i++) {
                try {
                    Object value = fields.get(i).get(lieferung);
                    if (value instanceof List) {
                        row[i] = "size=" + ((List<?>) value).size();
                    } else {
                        row[i] = value;
                    }
                } catch (IllegalAccessException e) {
                    row[i] = "";
                }
            }
            model.addRow(row);
        }

        table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(true);
        table.setCellSelectionEnabled(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.putClientProperty("JTable.autoStartsEdit", Boolean.TRUE);
        table.setSurrendersFocusOnKeystroke(true);
        JTextField editorField = new JTextField();
        DefaultCellEditor singleClickEditor = new DefaultCellEditor(editorField);
        singleClickEditor.setClickCountToStart(1);
        table.setDefaultEditor(Object.class, singleClickEditor);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        configureKeyboardNavigation();
        configureDeleteByRightClick();

        JScrollPane scrollPane = new JScrollPane(table);
        JPanel filterPanel = buildFilterPanel();

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(filterPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setJMenuBar(buildMenuBar());
    }

    private JMenuBar buildMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu exportMenu = new JMenu("Export");
        JMenuItem exportExcelItem = new JMenuItem("Export as Excel-file");
        exportExcelItem.addActionListener(e -> exportTableAsExcel());

        exportMenu.add(exportExcelItem);
        menuBar.add(exportMenu);
        return menuBar;
    }

    private void exportTableAsExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Export as Excel-file");
        fileChooser.setSelectedFile(new File("Lieferungen.xlsx"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel files (*.xlsx)", "xlsx"));

        int result = fileChooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = fileChooser.getSelectedFile();
        String filePath = selectedFile.getAbsolutePath();
        if (!filePath.toLowerCase().endsWith(".xlsx")) {
            selectedFile = new File(filePath + ".xlsx");
        }

        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream outputStream = new FileOutputStream(selectedFile)) {

            var sheet = workbook.createSheet("Lieferungen");

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < table.getColumnCount(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(table.getColumnName(col));
            }

            for (int viewRow = 0; viewRow < table.getRowCount(); viewRow++) {
                Row row = sheet.createRow(viewRow + 1);
                for (int col = 0; col < table.getColumnCount(); col++) {
                    Cell cell = row.createCell(col);
                    Object value = table.getValueAt(viewRow, col);
                    cell.setCellValue(value == null ? "" : String.valueOf(value));
                }
            }

            for (int col = 0; col < table.getColumnCount(); col++) {
                sheet.autoSizeColumn(col);
            }

            workbook.write(outputStream);
            JOptionPane.showMessageDialog(
                    this,
                    "Excel-Datei erfolgreich gespeichert:\n" + selectedFile.getAbsolutePath(),
                    "Export",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Fehler beim Export: " + ex.getMessage(),
                    "Export",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private JPanel buildFilterPanel() {
        JPanel filterPanel = new JPanel(new BorderLayout(8, 0));
        JLabel filterLabel = new JLabel("Filter:");
        JTextField filterField = new JTextField();
        filterField.setToolTipText("Filtert alle Spalten der Tabelle");
        filterPanel.add(filterLabel, BorderLayout.WEST);
        filterPanel.add(filterField, BorderLayout.CENTER);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        filterField.getDocument().addDocumentListener(new DocumentListener() {
            private void applyFilter() {
                String text = filterField.getText();
                if (text == null || text.trim().isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + Pattern.quote(text.trim())));
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                applyFilter();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                applyFilter();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                applyFilter();
            }
        });
        return filterPanel;
    }

    private void configureKeyboardNavigation() {
        javax.swing.Action moveHorizontal = new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int rows = table.getRowCount();
                int cols = table.getColumnCount();
                if (rows <= 0 || cols <= 0) {
                    return;
                }
                if (table.isEditing() && table.getCellEditor() != null) {
                    table.getCellEditor().stopCellEditing();
                }
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                if (row < 0 || col < 0) {
                    row = 0;
                    col = 0;
                } else {
                    col++;
                    if (col >= cols) {
                        col = 0;
                        row = Math.min(row + 1, rows - 1);
                    }
                }
                table.changeSelection(row, col, false, false);
                table.editCellAt(row, col);
                Component editor = table.getEditorComponent();
                if (editor != null) {
                    editor.requestFocusInWindow();
                }
            }
        };

        javax.swing.Action moveVertical = new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int rows = table.getRowCount();
                int cols = table.getColumnCount();
                if (rows <= 0 || cols <= 0) {
                    return;
                }
                if (table.isEditing() && table.getCellEditor() != null) {
                    table.getCellEditor().stopCellEditing();
                }
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                if (row < 0 || col < 0) {
                    row = 0;
                    col = 0;
                } else {
                    row++;
                    if (row >= rows) {
                        row = rows - 1;
                    }
                }
                table.changeSelection(row, col, false, false);
                table.editCellAt(row, col);
                Component editor = table.getEditorComponent();
                if (editor != null) {
                    editor.requestFocusInWindow();
                }
            }
        };

        InputMap inputMap = table.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        javax.swing.ActionMap actionMap = table.getActionMap();
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0), "editMoveHorizontal");
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "editMoveVertical");
        actionMap.put("editMoveHorizontal", moveHorizontal);
        actionMap.put("editMoveVertical", moveVertical);
    }

    private void configureDeleteByRightClick() {
        final int idCol = idColumnIndex;
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!javax.swing.SwingUtilities.isRightMouseButton(e)) {
                    return;
                }
                int viewRow = table.rowAtPoint(e.getPoint());
                int viewCol = table.columnAtPoint(e.getPoint());
                if (viewRow < 0 || viewCol < 0 || idCol < 0) {
                    return;
                }

                int[] selectedViewRows = table.getSelectedRows();
                if (selectedViewRows == null || selectedViewRows.length == 0) {
                    JOptionPane.showMessageDialog(
                            LieferungTableView.this,
                            "Bitte zuerst eine oder mehrere Zeilen markieren.",
                            "Löschen",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }
                int confirm = JOptionPane.showConfirmDialog(
                        LieferungTableView.this,
                        "Ausgewählte Lieferungen wirklich löschen? Anzahl: " + selectedViewRows.length,
                        "Löschen bestätigen",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
                ArrayList<Integer> selectedModelRows = new ArrayList<>();
                ArrayList<Integer> selectedIds = new ArrayList<>();
                for (int selectedViewRow : selectedViewRows) {
                    int modelRow = table.convertRowIndexToModel(selectedViewRow);
                    if (!selectedModelRows.contains(modelRow)) {
                        Object idObj = model.getValueAt(modelRow, idCol);
                        if (idObj == null) {
                            continue;
                        }
                        try {
                            int id = Integer.parseInt(idObj.toString().trim());
                            selectedModelRows.add(modelRow);
                            selectedIds.add(id);
                        } catch (NumberFormatException ex) {
                            // Ungültige Zeilen überspringen
                        }
                    }
                }
                if (selectedIds.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            LieferungTableView.this,
                            "Keine gültigen Lieferung-IDs in der Auswahl gefunden.",
                            "Löschen",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                DBManager localDb = new DBManager();
                ArrayList<Integer> deletedIds = new ArrayList<>();
                ArrayList<Integer> failedIds = new ArrayList<>();
                try {
                    for (Integer id : selectedIds) {
                        boolean deleted = localDb.deletFromTab(id);
                        if (deleted) {
                            deletedIds.add(id);
                        } else {
                            failedIds.add(id);
                        }
                    }
                } finally {
                    localDb.closeConnection();
                }
                if (deletedIds.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            LieferungTableView.this,
                            "Keine Lieferung konnte gelöscht werden.",
                            "Löschen",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }
                if (lieferListRef != null) {
                    lieferListRef.removeIf(l -> l != null && deletedIds.contains(l.getId()));
                }
                selectedModelRows.sort(Collections.reverseOrder());
                for (Integer modelRow : selectedModelRows) {
                    Object idObj = model.getValueAt(modelRow, idCol);
                    if (idObj == null) {
                        continue;
                    }
                    try {
                        int id = Integer.parseInt(idObj.toString().trim());
                        if (deletedIds.contains(id)) {
                            model.removeRow(modelRow);
                        }
                    } catch (NumberFormatException ex) {
                        // Ignorieren
                    }
                }
                String message = "Gelöscht: " + deletedIds.size();
                if (!failedIds.isEmpty()) {
                    message += "\nNicht gelöscht: " + failedIds.size() + " (IDs: " + failedIds + ")";
                }
                JOptionPane.showMessageDialog(
                        LieferungTableView.this,
                        message,
                        "Löschen",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }
}
