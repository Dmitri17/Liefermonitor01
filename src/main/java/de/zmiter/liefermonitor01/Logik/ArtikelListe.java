/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.Logik;

import javax.swing.JFrame;
import de.zmiter.liefermonitor01.DBObjekte.Artikel;
import de.zmiter.liefermonitor01.DBObjekte.PersonUser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 *
 * @author lepeschko
 */
public class ArtikelListe extends JFrame {
    private static ArtikelListe lastInstance;
    private static final int TABLE_ROW_HEIGHT_NORMAL = 30;
    private static final int TABLE_ROW_HEIGHT_EDIT = 42;
    PersonUser person;
    public ArtikelListe(){
        startComponents();
        this.setTitle("Thyssen Schachtbau GmbH" );
        lastInstance = this;
    }
    
   public void startComponents(){
       
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jComboBoxAgreement = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jComboBoxInfo03 = new javax.swing.JComboBox<>();
        jComboBoxSupplier = new javax.swing.JComboBox<>();
        jButtonVerbrauch = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
        jRadioButton7 = new javax.swing.JRadioButton();
        jRadioButton8 = new javax.swing.JRadioButton();
        jRadioButton9 = new javax.swing.JRadioButton();
        jRadioButton10 = new javax.swing.JRadioButton();
        jRadioButton11 = new javax.swing.JRadioButton();
        jRadioButton12 = new javax.swing.JRadioButton();
        jRadioButton13 = new javax.swing.JRadioButton();
        jRadioButton14 = new javax.swing.JRadioButton();
        jRadioButton15 = new javax.swing.JRadioButton();
        jRadioButton16 = new javax.swing.JRadioButton();
        jRadioButton17 = new javax.swing.JRadioButton();
        jRadioButton18 = new javax.swing.JRadioButton();
        jRadioButton19 = new javax.swing.JRadioButton();
        jRadioButton20 = new javax.swing.JRadioButton();
        jRadioButton21 = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                confirmSaveBeforeClose();
            }
            @Override
            public void windowOpened(WindowEvent e) {
                loadInfo03Combo();
                loadSupplierCombo();
            }
            @Override
            public void windowClosed(WindowEvent e) {
                if (lastInstance == ArtikelListe.this) {
                    lastInstance = null;
                }
            }
        });

        jPanel1.setAutoscrolls(true);

        jTextField1.setText("");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Suchen");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBoxAgreement.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alle BANF Nr." }));
        jComboBoxAgreement.setToolTipText("Nach BANF Nr. (banfNrArt) filtern");
        jComboBoxAgreement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAgreementActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxAgreement, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jComboBoxAgreement))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton2.setText("Änderungen speichern");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBoxInfo03.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alle Projekte" }));
        jComboBoxInfo03.setToolTipText("Projekt auswählen (Die Liste wird dementsprechend gefiltert.)");
        jComboBoxInfo03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxInfo03ActionPerformed(evt);
            }
        });

        jComboBoxSupplier.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Alle Lieferanten" }));
        jComboBoxSupplier.setToolTipText("Lieferant auswählen (Filter aus gesamter Artikelliste)");
        jComboBoxSupplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxSupplierActionPerformed(evt);
            }
        });

        jButtonVerbrauch.setText("Verbrauchsbericht");
        jButtonVerbrauch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVerbrauchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonVerbrauch, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxInfo03, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonVerbrauch, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxInfo03, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxSupplier, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel5.setAutoscrolls(true);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Felderauswahl (Artikel)"));
        // Устанавливаем минимальную и предпочтительную ширину для панели, чтобы названия радиокнопок были видны полностью
        // Самые длинные названия: "amount_of_Containers", "container_Number", "date_of_Arrival", "invoice_Proforma"
        jPanel6.setMinimumSize(new java.awt.Dimension(220, 0));
        jPanel6.setPreferredSize(new java.awt.Dimension(300, 0));

        // Устанавливаем названия полей для радиокнопок
        jRadioButton1.setText("dimention");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton2.setText("Lieferant");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton3.setText("Kolli");
        jRadioButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton4.setText("geliefert an BS");
        jRadioButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton5.setText("reloaded_in");
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton6.setText("Container_Nummer"); // "container Number"
        jRadioButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton7.setText("Bestellnummer");  // "booking Number"
        jRadioButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton8.setText("Kommentar (Einkauf)"); // "add_Info"
        jRadioButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton9.setText("good_Receipt");
        jRadioButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton10.setText("amount_of_Containers");
        jRadioButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton11.setText("weight");
        jRadioButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton12.setText("BANF Nr.");  // "banf"  раньше называлось agreement
        jRadioButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton13.setText("shipment");
        jRadioButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton14.setText("Wareneingang TS erfolgt am (Einkauf)"); //"date_of_Arrival"
        jRadioButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton15.setText("invoice_Proforma");
        jRadioButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton16.setText("lagerOrt");
        jRadioButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton17.setText("Menge am Lager");
        jRadioButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton18.setText("verbrauchte Menge");
        jRadioButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton19.setText("Packliste");
        jRadioButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton20.setText("Projektname/ Zusatzliche Information");// "info03"
        jRadioButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        jRadioButton21.setText("Preis (Euro)");
        jRadioButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateTableColumns();
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton21)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Artikel"));
        // Убираем ограничение максимального размера, чтобы панель могла расширяться
        jPanel7.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // Инициализируем таблицу с базовыми столбцами
        String[] columnNames = {"Material_DE", "Material_EN", "Agreement/Anforderung", "Bestellte Menge", "Gelieferte Menge MH", "Einheit"};
        javax.swing.table.DefaultTableModel tableModel = new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            columnNames
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // Таблица редактируемая
            }
        };
        jTable1.setModel(tableModel);
        enableSingleClickEditingForTable();
        jTable1.setAutoCreateRowSorter(true); // Sortierung per Klick auf Spaltenkopf
        addDifferenzUpdateListener();
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setFillsViewportHeight(true);
        // Mehrfachauswahl fuer schnellere Stapelbearbeitung (Ctrl/Shift-Klick)
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // Устанавливаем минимальную ширину для столбцов
        for (int i = 0; i < columnNames.length; i++) {
            jTable1.getColumnModel().getColumn(i).setMinWidth(100);
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
        
        // Рендерер: Zeilen mit Bestellte Menge > Gelieferte Menge leicht rot hinterlegen
        jTable1.setDefaultRenderer(Object.class, createMengeRowColorRenderer());
        
        // Добавляем обработчик для отображения tooltip при наведении мыши
        jTable1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTable1MouseMoved(evt);
            }
        });
        
        // Добавляем обработчик правой кнопки мыши для удаления записи
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (evt.isPopupTrigger() || evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                    jTable1MouseRightClicked(evt);
                }
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (evt.isPopupTrigger()) {
                    jTable1MouseRightClicked(evt);
                }
            }
        });
        
        // Настраиваем прокрутку
        jScrollPane1.setViewportView(jTable1);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(0, 300));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE, Short.MAX_VALUE)
                .addContainerGap())
        );

        // Убираем ограничения размера для jPanel5, чтобы он мог расширяться
        jPanel5.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        
        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        // Убираем ограничения размера для jPanel1, чтобы он мог расширяться
        jPanel1.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenu1.setText("File");

        jMenuItem1.setText("jMenuItem1");
       // jMenu1.add(jMenuItem1);

        jMenuItem2.setText("jMenuItem2");
      //  jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem3.setText("jMenuItem3");
      //  jMenu2.add(jMenuItem3);

        jMenuItem4.setText("jMenuItem4");
      //  jMenu2.add(jMenuItem4);

        jMenuItem6.setText("Prefix exp. fuer Artikel ohne Lieferung");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6MarkExpActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Neu");
        jMenuItem5.setText("Neuen Artikel anlegen (nach Vorlage)");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5NeuArtikelActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);
        jMenuBar1.add(jMenu3);

        javax.swing.JMenu jMenuExport = new javax.swing.JMenu();
        javax.swing.JMenuItem jMenuItemExportCurrentTable = new javax.swing.JMenuItem();
        jMenuExport.setText("Export");
        jMenuItemExportCurrentTable.setText("Export aktuelle Tabelle in Excel");
        jMenuItemExportCurrentTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportCurrentTableToExcelActionPerformed(evt);
            }
        });
        jMenuExport.add(jMenuItemExportCurrentTable);
        jMenuBar1.add(jMenuExport);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        // Устанавливаем разумный начальный размер окна
        setSize(1500, 700);
        // Устанавливаем минимальный размер окна
        setMinimumSize(new java.awt.Dimension(900, 500));
        // Убеждаемся, что окно может расширяться
        setResizable(true);
    }// </editor-fold>                        

    /**
     * Метод для поиска в таблице artikel базы данных всех записей 
     * в соответствии с текстом из jTextField1
     * Если поле jTextField1 пустое, загружается весь список артикулов
     * @return ArrayList с экземплярами класса Artikel
     */
    private ArrayList<Artikel> searchArtikelInDB() {
        ArrayList<Artikel> artikelList = new ArrayList<>();
        String searchText = jTextField1 != null ? jTextField1.getText() : null;
        if (searchText != null) searchText = searchText.trim();

        try {
            DBManager dbManager = new DBManager();
            if (dbManager.isConnectionValid()) {
                int synced = PendingSync.replayPendingArtikelliste(dbManager);
                if (synced > 0) {
                    javax.swing.JOptionPane.showMessageDialog(this,
                        synced + " zuvor offline gespeicherte Änderungen wurden in die Datenbank übernommen.",
                        "Synchronisation", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
                if (searchText == null || searchText.isEmpty()) {
                    artikelList = dbManager.getAllArtikel();
                    if (artikelList != null && !artikelList.isEmpty()) {
                        OfflineCache.saveArtikelliste(artikelList);
                    }
                } else {
                    artikelList = dbManager.searchArtikel(searchText);
                }
                currentArtikelList = artikelList != null ? artikelList : new ArrayList<>();
                loadInfo03ComboFromDB(dbManager);
                loadAgreementComboFromArtikelList(currentArtikelList);
                dbManager.closeConnection();
                fillTableWithArtikel(applyPostFilters(currentArtikelList));
                showSearchResultMessage(currentArtikelList, searchText, false);
                return currentArtikelList;
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Suchen in DB: " + e.getMessage());
            e.printStackTrace();
        }

        // Offline: aus lokalem Cache laden und ggf. filtern
        ArrayList<Artikel> cached = OfflineCache.loadArtikelliste();
        if (cached != null && !cached.isEmpty()) {
            if (searchText != null && !searchText.isEmpty()) {
                String q = searchText.toLowerCase();
                artikelList = new ArrayList<>();
                for (Artikel a : cached) {
                    if (matchesSearch(a, q)) artikelList.add(a);
                }
            } else {
                artikelList = new ArrayList<>(cached);
            }
            currentArtikelList = artikelList;
            loadInfo03ComboFromArtikelList(cached);
            loadAgreementComboFromArtikelList(currentArtikelList);
            fillTableWithArtikel(applyPostFilters(currentArtikelList));
            long t = OfflineCache.getLastSyncTimeArtikelliste();
            setTitle("Thyssen Schachtbau GmbH — Offline (Stand: " + OfflineCache.formatSyncTime(t) + ")");
            showSearchResultMessage(artikelList, searchText, true);
            return artikelList;
        }

        javax.swing.JOptionPane.showMessageDialog(this,
            "Keine Verbindung zur Datenbank und kein lokaler Cache vorhanden.",
            "Offline", javax.swing.JOptionPane.WARNING_MESSAGE);
        return new ArrayList<>();
    }

    private boolean matchesSearch(Artikel a, String query) {
        if (query == null || query.isEmpty()) return true;
        return (a.getMaterial_DE() != null && a.getMaterial_DE().toLowerCase().contains(query))
            || (a.getMaterial_EN() != null && a.getMaterial_EN().toLowerCase().contains(query))
            || (a.getAnfordrung() != null && a.getAnfordrung().toLowerCase().contains(query))
            || (a.getInfo03() != null && a.getInfo03().toLowerCase().contains(query));
    }

    private void showSearchResultMessage(ArrayList<Artikel> list, String searchText, boolean offline) {
        String prefix = offline ? "Offline. " : "";
        if (list == null) list = new ArrayList<>();
        if (list.isEmpty()) {
            if (searchText == null || searchText.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, prefix + "Es gibt keine Artikel im Cache.", "Suchergebnis", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, prefix + "Für die Suche \"" + searchText + "\" wurde nichts gefunden.", "Suchergebnis", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            if (searchText == null || searchText.isEmpty()) {
                javax.swing.JOptionPane.showMessageDialog(this, prefix + "Geladen: " + list.size() + " Artikel (Stand: " + (offline ? OfflineCache.formatSyncTime(OfflineCache.getLastSyncTimeArtikelliste()) : "DB") + ").", "Suchergebnis", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, prefix + "Gefunden: " + list.size() + " Artikel.", "Suchergebnis", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * Загружает список уникальных info03 из БД и заполняет jComboBoxInfo03 (вызывается при открытии окна).
     */
    private void loadInfo03Combo() {
        try {
            DBManager dbManager = new DBManager();
            loadInfo03ComboFromDB(dbManager);
            dbManager.closeConnection();
        } catch (Exception e) {
            System.err.println("Fehler beim Laden der info03-Liste: " + e.getMessage());
        }
    }

    /**
     * Заполняет jComboBoxInfo03 значениями "Alle" + уникальные info03 из переданного DBManager.
     */
    private void loadInfo03ComboFromDB(DBManager dbManager) {
        if (dbManager == null || jComboBoxInfo03 == null) return;
        updatingInfo03Combo = true;
        try {
            java.util.ArrayList<String> distinct = dbManager.getDistinctInfo03();
            String[] items = new String[distinct.size() + 1];
            items[0] = "Alle Projekte";
            for (int i = 0; i < distinct.size(); i++) {
                items[i + 1] = distinct.get(i);
            }
            jComboBoxInfo03.setModel(new DefaultComboBoxModel<>(items));
        } finally {
            updatingInfo03Combo = false;
        }
    }

    /** Füllt jComboBoxInfo03 mit "Alle" + distinct info03 aus einer Artikel-Liste (z. B. aus Cache). */
    private void loadInfo03ComboFromArtikelList(ArrayList<Artikel> list) {
        if (jComboBoxInfo03 == null || list == null) return;
        updatingInfo03Combo = true;
        try {
            java.util.Set<String> set = new java.util.TreeSet<>();
            for (Artikel a : list) {
                String v = a.getInfo03();
                if (v != null && !v.trim().isEmpty()) set.add(v.trim());
            }
            String[] items = new String[set.size() + 1];
            items[0] = "Alle Projekte";
            int i = 1;
            for (String s : set) items[i++] = s;
            jComboBoxInfo03.setModel(new DefaultComboBoxModel<>(items));
        } finally {
            updatingInfo03Combo = false;
        }
    }

    /**
     * Обработчик выбора значения в jComboBoxInfo03: фильтрация артикулов по info03 из БД.
     */
    private void jComboBoxInfo03ActionPerformed(java.awt.event.ActionEvent evt) {
        if (updatingInfo03Combo || jComboBoxInfo03 == null || evt.getSource() != jComboBoxInfo03) return;
        Object sel = jComboBoxInfo03.getSelectedItem();
        if (sel == null) return;
        String value = sel.toString().trim();
        if ("Alle Projekte".equals(value)) {
            searchArtikelInDB();
            return;
        }
        try {
            DBManager dbManager = new DBManager();
            if (dbManager.isConnectionValid()) {
                ArrayList<Artikel> list = dbManager.getArtikelByInfo03(value);
                dbManager.closeConnection();
                currentArtikelList = list;
                loadAgreementComboFromArtikelList(currentArtikelList);
                fillTableWithArtikel(applyPostFilters(currentArtikelList));
                return;
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Filtern nach info03: " + e.getMessage());
        }
        // Offline: aus Cache filtern
        ArrayList<Artikel> cached = OfflineCache.loadArtikelliste();
        if (cached != null) {
            ArrayList<Artikel> list = new ArrayList<>();
            for (Artikel a : cached) {
                String v = a.getInfo03();
                if (v != null && v.trim().equalsIgnoreCase(value)) list.add(a);
            }
            currentArtikelList = list;
            loadAgreementComboFromArtikelList(currentArtikelList);
            fillTableWithArtikel(applyPostFilters(currentArtikelList));
            return;
        }
        javax.swing.JOptionPane.showMessageDialog(this,
                "Keine Verbindung zur Datenbank und kein Cache für Filter verfügbar.",
                "Fehler",
                javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private void jComboBoxAgreementActionPerformed(java.awt.event.ActionEvent evt) {
        if (updatingAgreementCombo || jComboBoxAgreement == null || evt.getSource() != jComboBoxAgreement) return;
        fillTableWithArtikel(applyPostFilters(currentArtikelList));
    }

    private void jComboBoxSupplierActionPerformed(java.awt.event.ActionEvent evt) {
        if (updatingSupplierCombo || jComboBoxSupplier == null || evt.getSource() != jComboBoxSupplier) return;
        fillTableWithArtikel(applyPostFilters(currentArtikelList));
    }

    /**
     * Открывает форму VerbrauchsBericht для оформления выдачи материалов со склада.
     */
    private void jButtonVerbrauchActionPerformed(java.awt.event.ActionEvent evt) {
        VerbrauchsBericht vb = new VerbrauchsBericht(person);
        vb.setLocationRelativeTo(this);
        vb.setVisible(true);
    }

    /**
     * Устанавливает текст поиска (номер заявки/Agreement) и сразу выполняет поиск артикулов.
     * Вызывается при открытии окна из формы BearbForm по кнопке "zu Artikelliste".
     * @param searchText номер заявки (Agreement/Anford Nr) или null/пустая строка для загрузки всех артикулов
     */
    public void setSearchTextAndPerformSearch(String searchText) {
        if (jTextField1 != null) {
            jTextField1.setText(searchText != null ? searchText : "");
            searchArtikelInDB();
        }
    }
    
    /**
     * Метод для заполнения таблицы данными из ArrayList<Artikel>
     * Всегда отображает базовые столбцы: material_DE, material_EN, anfordrung, Bestellte Menge, Gelieferte Menge in MH, einheit
     * Добавляет дополнительные столбцы в зависимости от выбранных радиокнопок
     * @param artikelList список экземпляров Artikel для отображения
     */
    private void fillTableWithArtikel(ArrayList<Artikel> artikelList) {
        // Создаем список названий столбцов
        java.util.List<String> columnNames = new java.util.ArrayList<>();
        
        // Базовые столбцы (всегда присутствуют)
        columnNames.add("Material_DE");
        columnNames.add("Material_EN");
        columnNames.add("Agreement/Anforderung");
        columnNames.add("Bestellte Menge");
        columnNames.add("Gelieferte Menge in MH");
        columnNames.add("Einheit");
        
        // Добавляем дополнительные столбцы в зависимости от выбранных радиокнопок
        if (jRadioButton1.isSelected()) columnNames.add("dimention");
        if (jRadioButton2.isSelected()) columnNames.add("Lieferant");// "supplier"
        if (jRadioButton3.isSelected()) columnNames.add("Kolli");//"colli"
        if (jRadioButton4.isSelected()) columnNames.add("geliefert an BS");
        if (jRadioButton5.isSelected()) columnNames.add("reloaded_in");
        if (jRadioButton6.isSelected()) columnNames.add("Container_Nummer");
        if (jRadioButton7.isSelected()) columnNames.add("Bestellnummer");// "booking_Number"
        if (jRadioButton8.isSelected()) columnNames.add("Kommentar (Einkauf)"); //"add_Info"
        if (jRadioButton9.isSelected()) columnNames.add("good_Receipt");
        if (jRadioButton10.isSelected()) columnNames.add("amount_of_Containers");
        if (jRadioButton11.isSelected()) columnNames.add("weight");
        if (jRadioButton12.isSelected()) columnNames.add("BANF Nr."); //"banf" раньше назывался agreement
        if (jRadioButton13.isSelected()) columnNames.add("shipment");
        if (jRadioButton14.isSelected()) columnNames.add("Wareneingang am"); //"date_of_Arrival"
        if (jRadioButton15.isSelected()) columnNames.add("invoice_Proforma");
        if (jRadioButton16.isSelected()) columnNames.add("lagerOrt");
        if (jRadioButton17.isSelected()) columnNames.add("Menge am Lager");
        if (jRadioButton18.isSelected()) columnNames.add("verbrauchte Menge");
        if (jRadioButton19.isSelected()) columnNames.add("Packliste");
        if (jRadioButton20.isSelected()) columnNames.add("Projektname/ Zusatzinfo");
        if (jRadioButton21.isSelected()) columnNames.add("Preis (Euro)");
        
        // Создаем новую модель таблицы с нужными столбцами
        javax.swing.table.DefaultTableModel tableModel = new javax.swing.table.DefaultTableModel(
            new Object[][] {},
            columnNames.toArray(new String[0])
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // Таблица редактируемая
            }
        };
        
        // Устанавливаем новую модель
        jTable1.setModel(tableModel);
        enableSingleClickEditingForTable();
        jTable1.setAutoCreateRowSorter(true); // Sortierung per Klick auf Spaltenkopf
        addDifferenzUpdateListener();
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTable1.setFillsViewportHeight(true);
        
        // Устанавливаем минимальную и предпочтительную ширину для всех столбцов
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setMinWidth(100);
            jTable1.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
        
        // Очищаем карту соответствия строк и ID
        rowToArtikelIdMap.clear();
        
        // Заполняем таблицу данными
        int rowIndex = 0;
        for (Artikel artikel : artikelList) {
            java.util.List<Object> rowData = new java.util.ArrayList<>();
            
            // Базовые поля (всегда добавляем все 6 полей)
            rowData.add(artikel.getMaterial_DE() != null ? artikel.getMaterial_DE() : "");
            rowData.add(artikel.getMaterial_EN() != null ? artikel.getMaterial_EN() : "");
            rowData.add(artikel.getAnfordrung() != null ? artikel.getAnfordrung() : "");
            rowData.add(artikel.getMenge()); // Bestellte Menge
            rowData.add(artikel.getMenge_Baustelle_gel()); // Gelieferte Menge in MH
            rowData.add(artikel.getEinheit() != null ? artikel.getEinheit() : "");
            
            // Дополнительные поля (добавляем только если соответствующая радиокнопка выбрана)
            if (jRadioButton1.isSelected()) rowData.add(artikel.getDimention() != null ? artikel.getDimention() : "");
            if (jRadioButton2.isSelected()) rowData.add(artikel.getSupplier() != null ? artikel.getSupplier() : "");
            if (jRadioButton3.isSelected()) rowData.add(artikel.getColli() != null ? artikel.getColli() : "");
            if (jRadioButton4.isSelected()) rowData.add(artikel.getMenge_gelief_an_BS());
            if (jRadioButton5.isSelected()) rowData.add(artikel.getReloaded_in() != null ? artikel.getReloaded_in() : "");
            if (jRadioButton6.isSelected()) rowData.add(artikel.getContainer_Number() != null ? artikel.getContainer_Number() : "");
            if (jRadioButton7.isSelected()) rowData.add(artikel.getBooking_Number() != null ? artikel.getBooking_Number() : "");
            if (jRadioButton8.isSelected()) rowData.add(artikel.getAdd_Info() != null ? artikel.getAdd_Info() : "");
            if (jRadioButton9.isSelected()) rowData.add(artikel.getGood_Receipt() != null ? artikel.getGood_Receipt() : "");
            if (jRadioButton10.isSelected()) rowData.add(artikel.getAmount_of_Containers() != null ? artikel.getAmount_of_Containers() : "");
            if (jRadioButton11.isSelected()) rowData.add(artikel.getWeight() != null ? artikel.getWeight() : "");
            if (jRadioButton12.isSelected()) rowData.add(artikel.getBanfNrArt() != null ? artikel.getBanfNrArt() : "");
            if (jRadioButton13.isSelected()) rowData.add(artikel.getShipment() != null ? artikel.getShipment() : "");
            if (jRadioButton14.isSelected()) rowData.add(artikel.getDate_of_Arrival() != null ? artikel.getDate_of_Arrival() : "");
            if (jRadioButton15.isSelected()) rowData.add(artikel.getInvoice_Proforma() != null ? artikel.getInvoice_Proforma() : "");
            if (jRadioButton16.isSelected()) rowData.add(artikel.getLagerOrt() != null ? artikel.getLagerOrt() : "");
            // "Menge am Lager" immer als Differenz anzeigen: geliefert an BS − Verbrauchte Menge
            if (jRadioButton17.isSelected()) rowData.add(artikel.getMenge_gelief_an_BS() - parseFloatFromCell(artikel.getInfo01()));
            if (jRadioButton18.isSelected()) rowData.add(artikel.getInfo01() != null ? artikel.getInfo01() : "");
            if (jRadioButton19.isSelected()) rowData.add(artikel.getPackListe() != null ? artikel.getPackListe() : ""); // раньше это было инфо 02
            if (jRadioButton20.isSelected()) rowData.add(artikel.getInfo03() != null ? artikel.getInfo03() : "");
            if (jRadioButton21.isSelected()) rowData.add(artikel.getPreisEuro());
            
            // Проверяем, что количество столбцов совпадает с количеством данных
            if (rowData.size() != tableModel.getColumnCount()) {
                System.err.println("Fehler: Dateimenge (" + rowData.size() + ") entspricht nicht der Spaltenzahl (" + tableModel.getColumnCount() + ")");
            }
            
            tableModel.addRow(rowData.toArray());
            
            // Сохраняем соответствие между строкой таблицы и ID объекта Artikel
            rowToArtikelIdMap.put(rowIndex, artikel.getId());
            rowIndex++;
        }
        
        // Принудительно обновляем отображение таблицы
        jTable1.revalidate();
        jTable1.repaint();
        
        // Обновляем прокрутку
        jScrollPane1.revalidate();
        jScrollPane1.repaint();
        
        // Принудительно обновляем заголовок таблицы
        if (jTable1.getTableHeader() != null) {
            jTable1.getTableHeader().revalidate();
            jTable1.getTableHeader().repaint();
        }
        
        // Обновляем родительскую панель
        jPanel7.revalidate();
        jPanel7.repaint();
    }
    
    /**
     * Berechnet für jede Zeile der Tabelle die Differenz:
     * geliefert an BS − verbrauchte Menge.
     * Wenn die Spalte "verbrauchte Menge" nicht vorhanden ist (Radio nicht gewählt), wird sie als 0 angenommen.
     * @return Liste der Differenzen pro Zeile (Index = Tabellenzeile); leere Liste wenn Tabelle leer oder Spalte "geliefert an BS" fehlt
     */
    public ArrayList<Float> getDifferenzGeliefertMinusVerbraucht() {
        ArrayList<Float> differenzen = new ArrayList<>();
        javax.swing.table.TableModel model = jTable1.getModel();
        int rows = model.getRowCount();
        if (rows == 0) {
            return differenzen;
        }
        int colGeliefert = -1;
        int colVerbraucht = -1;
        for (int c = 0; c < model.getColumnCount(); c++) {
            String name = model.getColumnName(c);
            if ("geliefert an BS".equals(name)) {
                colGeliefert = c;
            } else if ("verbrauchte Menge".equals(name)) {
                colVerbraucht = c;
            }
        }
        if (colGeliefert < 0) {
            return differenzen;
        }
        for (int r = 0; r < rows; r++) {
            float geliefert = parseFloatFromCell(model.getValueAt(r, colGeliefert));
            float verbraucht = (colVerbraucht >= 0) ? parseFloatFromCell(model.getValueAt(r, colVerbraucht)) : 0f;
            differenzen.add(geliefert - verbraucht);
        }
        return differenzen;
    }
    
    /**
     * Renderer: markiert die zweite Zelle je Mengen-Paar:
     * Bestellte Menge -> Gelieferte Menge in MH -> geliefert an BS -> Menge am Lager.
     * Rot: linker Wert > 0 und rechter Wert == 0.
     * Grün: linker Wert == rechter Wert.
     */
    private static DefaultTableCellRenderer createMengeRowColorRenderer() {
        return new DefaultTableCellRenderer() {
            private  final Color LIGHT_RED = new Color(255, 220, 220);
            private  final Color LIGHT_GREEN = new Color(220, 245, 220);
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                javax.swing.table.TableModel model = table.getModel();
                int colBestellte = -1;
                int colGelieferte = -1;
                int colGeliefertAnBS = -1;
                int colMengeAmLager = -1;
                for (int i = 0; i < model.getColumnCount(); i++) {
                    String name = model.getColumnName(i);
                    if ("Bestellte Menge".equals(name)) colBestellte = i;
                    if ("Gelieferte Menge in MH".equals(name) || "Gelieferte Menge MH".equals(name)) colGelieferte = i;
                    if ("geliefert an BS".equals(name)) colGeliefertAnBS = i;
                    if ("Menge am Lager".equals(name)) colMengeAmLager = i;
                }
                int colorMark = 0;
                if (row >= 0 && row < model.getRowCount()) {
                    if (colBestellte >= 0 && colGelieferte >= 0 && column == colGelieferte) {
                        float left = parseFloatFromCell(model.getValueAt(row, colBestellte));
                        float right = parseFloatFromCell(model.getValueAt(row, colGelieferte));
                        colorMark = getPairColorMark(left, right);
                    } else if (colGelieferte >= 0 && colGeliefertAnBS >= 0 && column == colGeliefertAnBS) {
                        float left = parseFloatFromCell(model.getValueAt(row, colGelieferte));
                        float right = parseFloatFromCell(model.getValueAt(row, colGeliefertAnBS));
                        colorMark = getPairColorMark(left, right);
                    } else if (colGeliefertAnBS >= 0 && colMengeAmLager >= 0 && column == colMengeAmLager) {
                        float left = parseFloatFromCell(model.getValueAt(row, colGeliefertAnBS));
                        float right = parseFloatFromCell(model.getValueAt(row, colMengeAmLager));
                        colorMark = getPairColorMark(left, right);
                    }
                }
                if (colorMark > 0) {
                    setBackground(isSelected ? table.getSelectionBackground() : LIGHT_GREEN);
                } else if (colorMark < 0) {
                    setBackground(isSelected ? table.getSelectionBackground() : LIGHT_RED);
                } else {
                    setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                }
                setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
                return c;
            }
        };
    }

    /**
     * Regeln fuer die Faerbung der zweiten Zelle im Vergleichspaar.
     * @return -1 (rot), 1 (gruen), 0 (default)
     */
    private static int getPairColorMark(float left, float right) {
        if (right < left || Float.compare(right, 0f) == 0) {
            return -1;
        }
        if (Float.compare(left, right) == 0 && Float.compare(right, 0f) != 0) {
            return 1;
        }
        return 0;
    }
    
    /**
     * Hilfsmethode: Wert aus Tabellenzelle als float lesen (0 bei null oder ungültig).
     */
    private static float parseFloatFromCell(Object value) {
        if (value == null) {
            return 0f;
        }
        String s = value.toString().trim();
        if (s.isEmpty()) {
            return 0f;
        }
        try {
            return Float.parseFloat(s.replace(',', '.'));
        } catch (NumberFormatException e) {
            return 0f;
        }
    }
    
    /**
     * Hängt an die aktuelle Tabellenmodell einen Listener, der bei Änderung von
     * "geliefert an BS" oder "verbrauchte Menge" die Differenz berechnet und in "Menge am Lager" speichert.
     */
    private void addDifferenzUpdateListener() {
        javax.swing.table.TableModel model = jTable1.getModel();
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() != TableModelEvent.UPDATE) {
                    return;
                }
                int col = e.getColumn();
                int colGeliefert = -1;
                int colVerbraucht = -1;
                int colLagerbestand = -1;
                for (int c = 0; c < model.getColumnCount(); c++) {
                    String name = model.getColumnName(c);
                    if ("geliefert an BS".equals(name)) colGeliefert = c;
                    if ("verbrauchte Menge".equals(name)) colVerbraucht = c;
                    if ("Menge am Lager".equals(name)) colLagerbestand = c;
                }
                boolean shouldRecalc = (col == TableModelEvent.ALL_COLUMNS)
                    || (col == colGeliefert) || (col == colVerbraucht);
                if (!shouldRecalc) {
                    return;
                }
                ArrayList<Float> differenzen = getDifferenzGeliefertMinusVerbraucht();
                if (colLagerbestand >= 0 && differenzen.size() == model.getRowCount()) {
                    for (int r = 0; r < differenzen.size(); r++) {
                        model.setValueAt(differenzen.get(r), r, colLagerbestand);
                    }
                }
            }
        });
        // Markierung: Tabelle wurde geändert (für Dialog beim Schließen)
        model.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                    tableDataModified = true;
                }
            }
        });
    }
    
    /**
     * Zeigt beim Schließen des Fensters einen Dialog, wenn es ungespeicherte Änderungen gibt.
     * Optionen: Speichern / Nicht speichern / Abbrechen.
     */
    private void confirmSaveBeforeClose() {
        // Редактирование ячейки завершить до диалога, иначе при потере фокуса правки могут не попасть в модель
        if (jTable1.isEditing() && jTable1.getCellEditor() != null) {
            jTable1.getCellEditor().stopCellEditing();
        }
        // Фокус на окно — редактор ячейки закоммитит значение при потере фокуса
        if (jTable1.hasFocus()) {
            requestFocusInWindow();
        }
        // Отложить диалог на следующий цикл EDT, чтобы setValueAt из редактора успел обновить модель
        SwingUtilities.invokeLater(() -> {
            if (!tableDataModified) {
                dispose();
                return;
            }
            String[] options = { "Speichern", "Nicht speichern", "Abbrechen" };
            int choice = javax.swing.JOptionPane.showOptionDialog(this,
                "Es gibt ungespeicherte Änderungen. Möchten Sie die Änderungen speichern?",
                "Änderungen speichern?",
                javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
            if (choice == 2) {
                return; // Abbrechen – Fenster nicht schließen
            }
            if (choice == 0) {
                saveChangesToDB();
                tableDataModified = false;
            } else {
                tableDataModified = false;
            }
            dispose();
        });
    }

    /**
     * Обновляет список артикулов согласно текущим фильтрам
     * (поисковая строка и выбранное значение info03 в комбобоксе).
     * Используется внешними формами (например, VerbrauchsBericht) для актуализации данных.
     */
    public void refreshWithCurrentFilters() {
        String searchText = jTextField1 != null ? jTextField1.getText() : null;
        String info03Filter = null;
        if (jComboBoxInfo03 != null && jComboBoxInfo03.getSelectedItem() != null) {
            String sel = jComboBoxInfo03.getSelectedItem().toString().trim();
            if (!"Alle".equals(sel)) {
                info03Filter = sel;
            }
        }
        String agreementFilter = getSelectedAgreementFilter();
        String supplierFilter = getSelectedSupplierFilter();

        ArrayList<Artikel> artikelList = new ArrayList<>();
        DBManager dbManager = null;
        try {
            dbManager = new DBManager();
            if (searchText == null || searchText.trim().isEmpty()) {
                if (info03Filter == null) {
                    artikelList = dbManager.getAllArtikel();
                } else {
                    artikelList = dbManager.getArtikelByInfo03(info03Filter);
                }
            } else {
                // сначала поиск по тексту, затем дополнительная фильтрация по info03 (если задана)
                ArrayList<Artikel> tmp = dbManager.searchArtikel(searchText.trim());
                if (info03Filter == null) {
                    artikelList = tmp;
                } else {
                    for (Artikel a : tmp) {
                        String v = a.getInfo03();
                        if (v != null && v.trim().equalsIgnoreCase(info03Filter)) {
                            artikelList.add(a);
                        }
                    }
                }
            }
            currentArtikelList = artikelList;
            loadAgreementComboFromArtikelList(currentArtikelList);
            fillTableWithArtikel(applyPostFilters(currentArtikelList, agreementFilter, supplierFilter));
        } catch (Exception ex) {
            System.err.println("Fehler beim Aktualisieren der ArtikelListe aus VerbrauchsBericht: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (dbManager != null) {
                dbManager.closeConnection();
            }
        }
    }

    private void loadAgreementComboFromArtikelList(ArrayList<Artikel> list) {
        if (jComboBoxAgreement == null || list == null) return;
        String selected = getSelectedAgreementFilter();
        updatingAgreementCombo = true;
        try {
            java.util.Set<String> set = new java.util.TreeSet<>();
            for (Artikel a : list) {
                String v = a.getBanfNrArt();
                if (v != null && !v.trim().isEmpty()) set.add(v.trim());
            }
            String[] items = new String[set.size() + 1];
            items[0] = "Alle BANF";
            int i = 1;
            for (String s : set) items[i++] = s;
            jComboBoxAgreement.setModel(new DefaultComboBoxModel<>(items));
            if (selected != null) {
                jComboBoxAgreement.setSelectedItem(selected);
            }
            if (jComboBoxAgreement.getSelectedIndex() < 0) {
                jComboBoxAgreement.setSelectedIndex(0);
            }
        } finally {
            updatingAgreementCombo = false;
        }
    }

    private ArrayList<Artikel> applyAgreementFilter(ArrayList<Artikel> source) {
        return applyAgreementFilter(source, getSelectedAgreementFilter());
    }

    private ArrayList<Artikel> applyAgreementFilter(ArrayList<Artikel> source, String agreementFilter) {
        ArrayList<Artikel> result = new ArrayList<>();
        if (source == null || source.isEmpty()) return result;
        if (agreementFilter == null || agreementFilter.isEmpty()) {
            result.addAll(source);
            return result;
        }
        for (Artikel a : source) {
            String v = a.getBanfNrArt();
            if (v != null && v.trim().equalsIgnoreCase(agreementFilter)) {
                result.add(a);
            }
        }
        return result;
    }

    private ArrayList<Artikel> applyPostFilters(ArrayList<Artikel> source) {
        return applyPostFilters(source, getSelectedAgreementFilter(), getSelectedSupplierFilter());
    }

    private ArrayList<Artikel> applyPostFilters(ArrayList<Artikel> source, String agreementFilter, String supplierFilter) {
        ArrayList<Artikel> byAgreement = applyAgreementFilter(source, agreementFilter);
        if (supplierFilter == null || supplierFilter.isEmpty()) {
            return byAgreement;
        }
        ArrayList<Artikel> result = new ArrayList<>();
        for (Artikel a : byAgreement) {
            String supplier = a.getSupplier();
            if (supplier != null && supplier.trim().equalsIgnoreCase(supplierFilter)) {
                result.add(a);
            }
        }
        return result;
    }

    private String getSelectedAgreementFilter() {
        if (jComboBoxAgreement == null || jComboBoxAgreement.getSelectedItem() == null) {
            return null;
        }
        String sel = jComboBoxAgreement.getSelectedItem().toString().trim();
        return "Alle BANF".equals(sel) ? null : sel;
    }

    private void loadSupplierCombo() {
        if (jComboBoxSupplier == null) return;
        updatingSupplierCombo = true;
        try {
            java.util.Set<String> supplierSet = new java.util.TreeSet<>();
            DBManager dbManager = null;
            try {
                dbManager = new DBManager();
                ArrayList<Artikel> allArtikel = dbManager.getAllArtikel();
                for (Artikel a : allArtikel) {
                    String supplier = a.getSupplier();
                    if (supplier != null && !supplier.trim().isEmpty()) {
                        supplierSet.add(supplier.trim());
                    }
                }
            } catch (Exception e) {
                System.err.println("Fehler beim Laden der Lieferantenliste: " + e.getMessage());
                ArrayList<Artikel> cached = OfflineCache.loadArtikelliste();
                if (cached != null) {
                    for (Artikel a : cached) {
                        String supplier = a.getSupplier();
                        if (supplier != null && !supplier.trim().isEmpty()) {
                            supplierSet.add(supplier.trim());
                        }
                    }
                }
            } finally {
                if (dbManager != null) dbManager.closeConnection();
            }
            String[] items = new String[supplierSet.size() + 1];
            items[0] = "Alle Lieferanten";
            int i = 1;
            for (String s : supplierSet) items[i++] = s;
            jComboBoxSupplier.setModel(new DefaultComboBoxModel<>(items));
        } finally {
            updatingSupplierCombo = false;
        }
    }

    private String getSelectedSupplierFilter() {
        if (jComboBoxSupplier == null || jComboBoxSupplier.getSelectedItem() == null) {
            return null;
        }
        String sel = jComboBoxSupplier.getSelectedItem().toString().trim();
        return "Alle Lieferanten".equals(sel) ? null : sel;
    }

    /**
     * Возвращает последний открытый экземпляр ArtikelListe или null, если окно закрыто.
     */
    public static ArtikelListe getLastInstance() {
        return lastInstance;
    }
    
    /**
     * Метод для обновления столбцов таблицы при изменении выбора радиокнопок
     */
    private void updateTableColumns() {
        // Перерисовываем таблицу с новыми столбцами
        // Если есть данные, заполняем их, иначе просто обновляем структуру столбцов
        if (!currentArtikelList.isEmpty()) {
            fillTableWithArtikel(currentArtikelList);
        } else {
            // Если данных нет, создаем пустую таблицу с правильными столбцами
            java.util.List<String> columnNames = new java.util.ArrayList<>();
            
            // Базовые столбцы (всегда присутствуют)
            columnNames.add("material_DE");
            columnNames.add("material_EN");
            columnNames.add("anfordrung");
            columnNames.add("Bestellte Menge");
            columnNames.add("Gelieferte Menge in MH");
            columnNames.add("einheit");
            
            // Добавляем дополнительные столбцы
            if (jRadioButton1.isSelected()) columnNames.add("dimention");
            if (jRadioButton2.isSelected()) columnNames.add("supplier");
            if (jRadioButton3.isSelected()) columnNames.add("colli");
            if (jRadioButton4.isSelected()) columnNames.add("geliefert an BS");
            if (jRadioButton5.isSelected()) columnNames.add("reloaded_in");
            if (jRadioButton6.isSelected()) columnNames.add("container_Number");
            if (jRadioButton7.isSelected()) columnNames.add("booking_Number");
            if (jRadioButton8.isSelected()) columnNames.add("add_Info");
            if (jRadioButton9.isSelected()) columnNames.add("good_Receipt");
            if (jRadioButton10.isSelected()) columnNames.add("amount_of_Containers");
            if (jRadioButton11.isSelected()) columnNames.add("weight");
            if (jRadioButton12.isSelected()) columnNames.add("BANF Nr.");
            if (jRadioButton13.isSelected()) columnNames.add("shipment");
            if (jRadioButton14.isSelected()) columnNames.add("date_of_Arrival");
            if (jRadioButton15.isSelected()) columnNames.add("invoice_Proforma");
            if (jRadioButton16.isSelected()) columnNames.add("lagerOrt");
            if (jRadioButton17.isSelected()) columnNames.add("Menge am Lager");
            if (jRadioButton18.isSelected()) columnNames.add("verbrauchte Menge");
            if (jRadioButton19.isSelected()) columnNames.add("Packliste");
            if (jRadioButton20.isSelected()) columnNames.add("Projektname/ Zusatzinfo");
            if (jRadioButton21.isSelected()) columnNames.add("Preis (Euro)");
            
            // Очищаем карту соответствия строк и ID (таблица пустая)
            rowToArtikelIdMap.clear();
            
            // Создаем новую модель таблицы
            javax.swing.table.DefaultTableModel tableModel = new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                columnNames.toArray(new String[0])
            ) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return true; // Таблица редактируемая
                }
            };
            
            // Устанавливаем новую модель
            jTable1.setModel(tableModel);
            enableSingleClickEditingForTable();
            jTable1.setAutoCreateRowSorter(true); // Sortierung per Klick auf Spaltenkopf
            addDifferenzUpdateListener();
            jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            jTable1.setFillsViewportHeight(true);
            
            // Устанавливаем минимальную и предпочтительную ширину для всех столбцов
            for (int i = 0; i < tableModel.getColumnCount(); i++) {
                jTable1.getColumnModel().getColumn(i).setMinWidth(100);
                jTable1.getColumnModel().getColumn(i).setPreferredWidth(150);
            }
            
            // Принудительно обновляем отображение
            jTable1.revalidate();
            jTable1.repaint();
            if (jTable1.getTableHeader() != null) {
                jTable1.getTableHeader().repaint();
            }
        }
    }

    private void enableSingleClickEditingForTable() {
        jTable1.setRowHeight(TABLE_ROW_HEIGHT_NORMAL);
        jTable1.putClientProperty("JTable.autoStartsEdit", Boolean.TRUE);
        jTable1.setSurrendersFocusOnKeystroke(true);
        javax.swing.JTextField editorField = new javax.swing.JTextField();
        // Schrift im Editor explizit wie in der Tabelle halten (nicht kleiner).
        editorField.setFont(jTable1.getFont());
        javax.swing.DefaultCellEditor singleClickEditor = new javax.swing.DefaultCellEditor(editorField) {
            @Override
            public java.awt.Component getTableCellEditorComponent(
                    javax.swing.JTable table,
                    Object value,
                    boolean isSelected,
                    int row,
                    int column) {
                java.awt.Component c = super.getTableCellEditorComponent(table, value, isSelected, row, column);
                if (c instanceof javax.swing.JTextField) {
                    javax.swing.JTextField tf = (javax.swing.JTextField) c;
                    tf.setFont(table.getFont());
                    String text = tf.getText() != null ? tf.getText().trim() : "";
                    // Für schnellere Eingabe: technische Defaultwerte direkt leeren.
                    if ("0.0".equals(text)) {
                        tf.setText("");
                    } else {
                        tf.selectAll();
                    }
                }
                return c;
            }
        };
        singleClickEditor.setClickCountToStart(1);
        jTable1.setDefaultEditor(Object.class, singleClickEditor);
        configureTableNavigationKeys();
        configureEditableRowHeightBehavior();
    }

    private void configureEditableRowHeightBehavior() {
        // Listener nur einmal pro JTable registrieren.
        if (Boolean.TRUE.equals(jTable1.getClientProperty("rowHeightBehaviorInstalled"))) {
            return;
        }
        jTable1.putClientProperty("rowHeightBehaviorInstalled", Boolean.TRUE);

        jTable1.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) {
                return;
            }
            updateTableRowHeightsForEditing();
        });
        jTable1.addPropertyChangeListener("tableCellEditor", evt -> updateTableRowHeightsForEditing());
    }

    private void updateTableRowHeightsForEditing() {
        int selectedRow = jTable1.getSelectedRow();
        int editingRow = jTable1.getEditingRow();
        for (int r = 0; r < jTable1.getRowCount(); r++) {
            if (r == selectedRow || r == editingRow) {
                jTable1.setRowHeight(r, TABLE_ROW_HEIGHT_EDIT);
            } else {
                jTable1.setRowHeight(r, TABLE_ROW_HEIGHT_NORMAL);
            }
        }
    }

    private void configureTableNavigationKeys() {
        javax.swing.InputMap inputMap = jTable1.getInputMap(javax.swing.JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        javax.swing.ActionMap actionMap = jTable1.getActionMap();

        inputMap.put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB, 0), "moveHorizontalNext");
        inputMap.put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_TAB, java.awt.event.InputEvent.SHIFT_DOWN_MASK), "moveHorizontalPrev");
        inputMap.put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, 0), "moveVerticalNext");
        inputMap.put(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.SHIFT_DOWN_MASK), "moveVerticalPrev");

        actionMap.put("moveHorizontalNext", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                moveTableSelection(0, 1);
            }
        });
        actionMap.put("moveHorizontalPrev", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                moveTableSelection(0, -1);
            }
        });
        actionMap.put("moveVerticalNext", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                moveTableSelection(1, 0);
            }
        });
        actionMap.put("moveVerticalPrev", new javax.swing.AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                moveTableSelection(-1, 0);
            }
        });
    }

    private void moveTableSelection(int rowDelta, int colDelta) {
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
            row = Math.max(0, Math.min(rows - 1, row + rowDelta));
            col = Math.max(0, Math.min(cols - 1, col + colDelta));
        }

        jTable1.changeSelection(row, col, false, false);
        jTable1.editCellAt(row, col);
        java.awt.Component editor = jTable1.getEditorComponent();
        if (editor != null) {
            editor.requestFocusInWindow();
        }
    }
    
    /**
     * Обработчик клика правой кнопкой мыши по таблице для удаления записи
     */
    private void jTable1MouseRightClicked(java.awt.event.MouseEvent evt) {
        javax.swing.JTable table = (javax.swing.JTable) evt.getSource();
        java.awt.Point point = evt.getPoint();
        int row = table.rowAtPoint(point);

        int[] selectedViewRows = table.getSelectedRows();

        // Важно: при множественном выборе не сбрасываем selection на одну строку.
        // Если ничего не выбрано (или выбрана одна строка), допускаем выбор по месту клика.
        if ((selectedViewRows == null || selectedViewRows.length <= 1)
                && row >= 0 && row < table.getRowCount()
                && !table.isRowSelected(row)) {
            table.setRowSelectionInterval(row, row);
            selectedViewRows = table.getSelectedRows();
        }
        java.util.ArrayList<Integer> selectedIds = new java.util.ArrayList<>();
        java.util.ArrayList<String> selectedNames = new java.util.ArrayList<>();
        for (int viewRow : selectedViewRows) {
            int modelRow = table.convertRowIndexToModel(viewRow);
            Integer artikelId = rowToArtikelIdMap.get(modelRow);
            if (artikelId == null || artikelId <= 0) {
                continue;
            }
            selectedIds.add(artikelId);
            for (Artikel art : currentArtikelList) {
                if (art.getId() == artikelId) {
                    String name = art.getMaterial_DE() != null ? art.getMaterial_DE().trim() : "";
                    if (!name.isEmpty()) {
                        selectedNames.add(name);
                    }
                    break;
                }
            }
        }

        if (selectedIds.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Fehler: die ID  fürd Löschen wurde nicht gefunden", 
                "Fehler", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Формируем сообщение для диалога (для одного/нескольких выделенных артикулов)
        String message;
        if (selectedIds.size() == 1 && !selectedNames.isEmpty()) {
            message = "Wollen Sie tatsächlich die Position aus der DB entfernen?:\n"
                    + selectedNames.get(0) + "\n\nDas kann man nicht umkehren!";
        } else if (!selectedNames.isEmpty()) {
            StringBuilder names = new StringBuilder();
            int previewCount = Math.min(5, selectedNames.size());
            for (int i = 0; i < previewCount; i++) {
                names.append(" - ").append(selectedNames.get(i)).append("\n");
            }
            if (selectedNames.size() > previewCount) {
                names.append(" ...\n");
            }
            message = "Wollen Sie tatsächlich " + selectedIds.size()
                    + " Positionen aus der DB entfernen?\n\n"
                    + names
                    + "\nDas kann man nicht umkehren!";
        } else {
            message = "Wollen Sie tatsächlich " + selectedIds.size()
                    + " Positionen aus der DB entfernen?\n\nDas kann man nicht umkehren!";
        }
        
        // Показываем диалог подтверждения
        int result = javax.swing.JOptionPane.showConfirmDialog(
            this,
            message,
            "Löschbestätigung",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.WARNING_MESSAGE
        );
        
        // Если пользователь подтвердил удаление
        if (result == javax.swing.JOptionPane.YES_OPTION) {
            deleteArtikelFromDB(selectedIds);
        }
    }
    
    /**
     * Удаляет артикул из базы данных по ID
     * @param artikelId ID артикула для удаления
     */
    private void deleteArtikelFromDB(java.util.List<Integer> artikelIds) {
        DBManager dbManager = null;
        
        try {
            dbManager = new DBManager();
            
            int deletedCount = dbManager.deleteArtikelList(artikelIds);
            if (deletedCount > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Artikel aus der DB gelöscht: " + deletedCount, 
                    "Erfolg", 
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
                // Обновляем таблицу - перезагружаем данные
                searchArtikelInDB();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, 
                    "Fehler beim Löschen aus der DB oder keine gültigen IDs ausgewählt", 
                    "Fehler", 
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            System.err.println("Fehler beim löschen der Position aus der DB: " + e.getMessage());
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Fehler beim Löschen aus der DBх:\n" + e.getMessage(), 
                "Fehler", 
                javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
            if (dbManager != null) {
                try {
                    dbManager.closeConnection();
                } catch (Exception e) {
                    System.err.println("Fehler bei der Schlissung der Verbindung: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Возвращает английское название артикула для строки таблицы (для отображения в ToolTip).
     * Использует Material_EN из Artikel; если его нет — возвращает немецкое название с пометкой.
     */
    private String getEnglishTooltipForRow(int row) {
        if (row < 0) {
            return null;
        }
        Artikel art = null;
        Integer artikelId = rowToArtikelIdMap.get(row);
        if (artikelId != null) {
            for (Artikel a : currentArtikelList) {
                if (a.getId() == artikelId) {
                    art = a;
                    break;
                }
            }
        }
        if (art == null && row < currentArtikelList.size()) {
            art = currentArtikelList.get(row);
        }
        if (art == null) {
            return null;
        }
        String en = art.getMaterial_EN();
        if (en != null && !en.trim().isEmpty()) {
            return en;
        }
        String de = art.getMaterial_DE();
        if (de != null && !de.trim().isEmpty()) {
            return de + " (keine engl. Bezeichnung)";
        }
        return null;
    }

    /**
     * Обработчик движения мыши над таблицей: ToolTip показывает английское название артикула (Material_EN).
     */
    private void jTable1MouseMoved(java.awt.event.MouseEvent evt) {
        javax.swing.JTable table = (javax.swing.JTable) evt.getSource();
        java.awt.Point point = evt.getPoint();
        int row = table.rowAtPoint(point);
        
        if (row >= 0 && row < table.getRowCount()) {
            String tooltipText = getEnglishTooltipForRow(row);
            table.setToolTipText(tooltipText);
        } else {
            table.setToolTipText(null);
        }
    }
    
    /**
     * Обработчик события для кнопки jButton1
     */
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        searchArtikelInDB();
    }
    
    /**
     * Обработчик события для поля jTextField1 (нажатие Enter)
     */
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        searchArtikelInDB();
    }
    
    /**
     * Обработчик события для кнопки jButton2 (Save)
     */
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        saveChangesToDB();
    }
    
    /**
     * Neu → Neuen Artikel anlegen: erstellt einen neuen Artikel mit material_DE = "Ergänzung--"
     * und anfordrung (Agreement/Anford Nr.) wie in der ausgewählten Zeile; fügt ihn der aktuellen Liste hinzu.
     */
    private void jMenuItem5NeuArtikelActionPerformed(java.awt.event.ActionEvent evt) {
        int row = jTable1.getSelectedRow();
        if (row < 0 || currentArtikelList.isEmpty() || row >= currentArtikelList.size()) {
            javax.swing.JOptionPane.showMessageDialog(this,    
                "Bitte wählen Sie eine Zeile in der Tabelle aus.",
                "Keine Zeile ausgewählt",
                javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        Artikel template = currentArtikelList.get(row);
        Artikel newArt = new Artikel();
        newArt.setMaterial_DE("Ergänzung--");
        newArt.setAnfordrung(template.getAnfordrung() != null ? template.getAnfordrung() : "");
        currentArtikelList.add(newArt);
        fillTableWithArtikel(currentArtikelList);
        tableDataModified = true;
    }

    private void jMenuItem6MarkExpActionPerformed(java.awt.event.ActionEvent evt) {
        DBManager dbManager = new DBManager();
        int updated = dbManager.markArtikelOhneLieferungMitExpPrefix();
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Fertig. Markierte Artikel: " + updated,
                "Analyse Artikel/Lieferung",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
        searchArtikelInDB();
    }

    private void exportCurrentTableToExcelActionPerformed(java.awt.event.ActionEvent evt) {
        if (jTable1 == null || jTable1.getColumnCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Keine Tabelle zum Exportieren vorhanden.",
                    "Export",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if (jTable1.getRowCount() == 0) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Die aktuelle Tabelle ist leer. Es gibt nichts zu exportieren.",
                    "Export",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Aktuelle Tabelle als Excel speichern");
        fileChooser.setFileFilter(new FileNameExtensionFilter(
                "Excel 97-2003 files (*.xls)", "xls"
        ));
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        fileChooser.setSelectedFile(new java.io.File("artikel_tabelle_export_" + timestamp + ".xls"));

        int result = fileChooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
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
            Sheet sheet = workbook.createSheet("Artikel");

            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            CellStyle numberStyle = workbook.createCellStyle();
            numberStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

            Row headerRow = sheet.createRow(0);
            for (int col = 0; col < jTable1.getColumnCount(); col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(jTable1.getColumnName(col));
                cell.setCellStyle(headerStyle);
            }

            for (int row = 0; row < jTable1.getRowCount(); row++) {
                Row excelRow = sheet.createRow(row + 1);
                for (int col = 0; col < jTable1.getColumnCount(); col++) {
                    Cell cell = excelRow.createCell(col);
                    Object value = jTable1.getValueAt(row, col);
                    if (value instanceof Number) {
                        cell.setCellValue(((Number) value).doubleValue());
                        cell.setCellStyle(numberStyle);
                    } else if (value instanceof Boolean) {
                        cell.setCellValue((Boolean) value);
                    } else {
                        cell.setCellValue(value != null ? value.toString() : "");
                    }
                }
            }

            for (int col = 0; col < jTable1.getColumnCount(); col++) {
                sheet.autoSizeColumn(col);
            }

            fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Export erfolgreich abgeschlossen:\n" + filePath,
                    "Export",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception e) {
            System.err.println("Fehler beim Export der aktuellen Tabelle nach Excel: " + e.getMessage());
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Fehler beim Export nach Excel:\n" + e.getMessage(),
                    "Export",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        } finally {
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
            } catch (Exception e) {
                System.err.println("Fehler beim Schließen des Export-Streams: " + e.getMessage());
            }
            try {
                if (workbook != null) {
                    workbook.close();
                }
            } catch (Exception e) {
                System.err.println("Fehler beim Schließen der Excel-Datei: " + e.getMessage());
            }
        }
    }
    
    /**
     * Überträgt die Tabellenwerte in die Objekte currentArtikelList (für Anzeige und für Offline-Pending).
     */
    private void applyTableValuesToArtikelList(javax.swing.table.TableModel tableModel, int rowCount, int columnCount, java.util.List<String> columnNames) {
        for (int row = 0; row < rowCount; row++) {
            Integer artikelId = rowToArtikelIdMap.get(row);
            Artikel artikel = findArtikelByTableRow(row, artikelId);
            if (artikel == null) {
                continue;
            }
            for (int col = 0; col < columnCount; col++) {
                String columnName = columnNames.get(col);
                Object cellValue = tableModel.getValueAt(row, col);
                String value = (cellValue != null) ? cellValue.toString() : "";
                try {
                    switch (columnName) {
                        case "material_DE": case "Material_DE": artikel.setMaterial_DE(value); break;
                        case "material_EN": case "Material_EN": artikel.setMaterial_EN(value); break;
                        case "anfordrung": case "Agreement/Anforderung": artikel.setAnfordrung(value); break;
                        case "Bestellte Menge": try { artikel.setMenge(Float.parseFloat(value)); } catch (NumberFormatException e) { artikel.setMenge(0); } break;
                        case "Gelieferte Menge in MH": try { artikel.setMenge_Baustelle_gel(Float.parseFloat(value)); } catch (NumberFormatException e) { artikel.setMenge_Baustelle_gel(0); } break;
                        case "einheit": case "Einheit": artikel.setEinheit(value); break;
                        case "dimention": artikel.setDimention(value); break;
                        case "supplier": artikel.setSupplier(value); break;
                        case "colli": artikel.setColli(value); break;
                        case "Order_Number":
                            artikel.setOrder_Number(value);
                            break;
                        case "geliefert an BS":
                            try {
                                artikel.setMenge_gelief_an_BS(Float.parseFloat(value));
                            } catch (NumberFormatException e) {
                                artikel.setMenge_gelief_an_BS(0);
                            }
                            break;
                        case "reloaded_in": artikel.setReloaded_in(value); break;
                        case "container_Number": artikel.setContainer_Number(value); break;
                        case "booking_Number": artikel.setBooking_Number(value); break;
                        case "add_Info": artikel.setAdd_Info(value); break;
                        case "good_Receipt": artikel.setGood_Receipt(value); break;
                        case "amount_of_Containers": artikel.setAmount_of_Containers(value); break;
                        case "weight": artikel.setWeight(value); break;
                        case "agreement":
                        case "BANF Nr.": artikel.setBanfNrArt(value); break;
                        case "shipment": artikel.setShipment(value); break;
                        case "date_of_Arrival": artikel.setDate_of_Arrival(value); break;
                        case "invoice_Proforma": artikel.setInvoice_Proforma(value); break;
                        case "lagerOrt": artikel.setLagerOrt(value); break;
                        case "Menge am Lager": artikel.setLagerMenge(value); break;
                        case "verbrauchte Menge": artikel.setInfo01(value); break;
                        case "Packliste":
                            artikel.setPackListe(value);
                            artikel.setInfo02(value); // Legacy-Feld für Kompatibilität
                            break;
                        case "info02":
                            artikel.setPackListe(value);
                            artikel.setInfo02(value);
                            break;
                        case "Projektname/ Zusatzinfo":
                            artikel.setInfo03(value);
                            break;
                        case "info03": artikel.setInfo03(value); break;
                        case "Preis (Euro)": try { artikel.setPreisEuro(Float.parseFloat(value)); } catch (NumberFormatException e) { artikel.setPreisEuro(0); } break;
                    }
                } catch (Exception e) { }
            }
        }
    }

    private Artikel findArtikelByTableRow(int row, Integer artikelId) {
        if (artikelId != null && artikelId > 0) {
            for (Artikel artikel : currentArtikelList) {
                if (artikel != null && artikel.getId() == artikelId) {
                    return artikel;
                }
            }
        }
        if (row >= 0 && row < currentArtikelList.size()) {
            Artikel fallback = currentArtikelList.get(row);
            if (fallback != null) {
                return fallback;
            }
        }
        return null;
    }

    /**
     * Метод для сохранения изменений из таблицы в базу данных
     */
    private void saveChangesToDB() {
        if (currentArtikelList.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Keine Information zum  Speichern", 
                "Information", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Завершаем редактирование текущей ячейки, чтобы значение из редактора попало в модель
        if (jTable1.isEditing() && jTable1.getCellEditor() != null) {
            jTable1.getCellEditor().stopCellEditing();
        }
        
        javax.swing.table.DefaultTableModel tableModel = (javax.swing.table.DefaultTableModel) jTable1.getModel();
        int rowCount = tableModel.getRowCount();
        
        int columnCount = tableModel.getColumnCount();
        
        if (rowCount == 0) {
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Tabelle ist leer", 
                "Information", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        java.util.List<String> columnNames = new java.util.ArrayList<>();
        for (int col = 0; col < columnCount; col++) {
            columnNames.add(tableModel.getColumnName(col));
        }
        
        // Zuerst Tabellenwerte in currentArtikelList übernehmen (für Offline-Pending)
        applyTableValuesToArtikelList(tableModel, rowCount, columnCount, columnNames);

        final JDialog progressDialog = createSaveProgressDialog();
        SwingWorker<SaveChangesResult, Void> worker = new SwingWorker<SaveChangesResult, Void>() {
            @Override
            protected SaveChangesResult doInBackground() {
                return performSaveChanges(rowCount);
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                try {
                    SaveChangesResult result = get();
                    handleSaveResult(result);
                } catch (Exception e) {
                    System.err.println("Fehler beim Speichern der Änderungen in DB: " + e.getMessage());
                    e.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(ArtikelListe.this,
                        "Fehler beim Speichern der Änderungen in DB:\n" + e.getMessage(),
                        "Fehler",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        worker.execute();
        progressDialog.setVisible(true);
    }

    private JDialog createSaveProgressDialog() {
        JDialog dialog = new JDialog(this, "Speichern", false);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.add(new JLabel("Speichere Änderungen in lager. Bitte warten..."), BorderLayout.NORTH);
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        dialog.add(progressBar, BorderLayout.CENTER);
        dialog.setSize(360, 110);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);
        return dialog;
    }

    private SaveChangesResult performSaveChanges(int rowCount) {
        SaveChangesResult result = new SaveChangesResult();
        DBManager dbManager = null;
        try {
            dbManager = new DBManager();
            if (!dbManager.isConnectionValid()) {
                dbManager.closeConnection();
                PendingSync.savePendingArtikelliste(currentArtikelList);
                result.offlineSaved = true;
                return result;
            }

            for (int row = 0; row < rowCount; row++) {
                Integer artikelId = rowToArtikelIdMap.get(row);
                Artikel artikel = findArtikelByTableRow(row, artikelId);
                if (artikel == null) {
                    result.errorCount++;
                    continue;
                }

                if (artikel.getId() <= 0) {
                    int newId = dbManager.addArtikelAndReturnId(artikel);
                    if (newId > 0) {
                        artikel.setId(newId);
                        rowToArtikelIdMap.put(row, newId);
                        result.successCount++;
                    } else {
                        result.errorCount++;
                        System.err.println("Fehler beim Einfügen des neuen Artikels für Zeile " + row);
                    }
                } else {
                    if (dbManager.updateArtikel(artikel)) {
                        result.successCount++;
                    } else {
                        result.errorCount++;
                        System.err.println("Ошибка при сохранении объекта Artikel mit ID " + artikel.getId());
                    }
                }
            }
        } catch (Exception e) {
            try {
                PendingSync.savePendingArtikelliste(currentArtikelList);
                result.offlineSaved = true;
            } catch (Exception ignored) {
            }
            result.exception = e;
        } finally {
            if (dbManager != null) {
                try {
                    dbManager.closeConnection();
                } catch (Exception e) {
                    System.err.println("Ошибка при закрытии соединения: " + e.getMessage());
                }
            }
        }
        return result;
    }

    private void handleSaveResult(SaveChangesResult result) {
        if (result == null) {
            return;
        }
        if (result.exception != null && !result.offlineSaved) {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Fehler beim Speichern der Änderungen in DB:\n" + result.exception.getMessage(),
                "Fehler",
                javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (result.offlineSaved) {
            tableDataModified = false;
            javax.swing.JOptionPane.showMessageDialog(this,
                "Keine Verbindung zur Datenbank. Änderungen wurden für die spätere Synchronisation gespeichert und werden beim nächsten Verbindungsaufbau übernommen.",
                "Offline gespeichert",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        if (result.errorCount == 0) {
            tableDataModified = false;
            javax.swing.JOptionPane.showMessageDialog(this,
                "Erfolgreich gespeichert : " + result.successCount,
                "Erfolg",
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this,
                "Gespeichert: " + result.successCount + " Aufzeichnungen\nFehler: " + result.errorCount,
                "Speicherergebnis",
                javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    private static class SaveChangesResult {
        int successCount = 0;
        int errorCount = 0;
        boolean offlineSaved = false;
        Exception exception;
    }
    
    /**
     * @param args the command line arguments
     */
   /**/
    
    // Variables declaration - do not modify
    private boolean tableDataModified = false; // Ungespeicherte Änderungen in der Tabelle
    private boolean updatingInfo03Combo = false; // блокировка рекурсии при обновлении списка info03
    private boolean updatingAgreementCombo = false; // блокировка рекурсии при обновлении списка agreement
    private boolean updatingSupplierCombo = false; // блокировка рекурсии при обновлении списка supplier
    private ArrayList<Artikel> currentArtikelList = new ArrayList<>(); // Текущий список найденных артикулов
    private Map<Integer, Integer> rowToArtikelIdMap = new HashMap<>(); // Соответствие между строками таблицы и ID объектов Artikel
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonVerbrauch;
    private JComboBox<String> jComboBoxAgreement;
    private JComboBox<String> jComboBoxInfo03;
    private JComboBox<String> jComboBoxSupplier;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
    private javax.swing.JRadioButton jRadioButton7;
    private javax.swing.JRadioButton jRadioButton8;
    private javax.swing.JRadioButton jRadioButton9;
    private javax.swing.JRadioButton jRadioButton10;
    private javax.swing.JRadioButton jRadioButton11;
    private javax.swing.JRadioButton jRadioButton12;
    private javax.swing.JRadioButton jRadioButton13;
    private javax.swing.JRadioButton jRadioButton14;
    private javax.swing.JRadioButton jRadioButton15;
    private javax.swing.JRadioButton jRadioButton16;
    private javax.swing.JRadioButton jRadioButton17;
    private javax.swing.JRadioButton jRadioButton18;
    private javax.swing.JRadioButton jRadioButton19;
    private javax.swing.JRadioButton jRadioButton20;
    private javax.swing.JRadioButton jRadioButton21;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1; 
    
}
