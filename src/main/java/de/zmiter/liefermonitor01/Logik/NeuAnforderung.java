/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.Artikel;
import de.zmiter.liefermonitor01.DBObjekte.Lieferung;
import de.zmiter.liefermonitor01.DBObjekte.PersonUser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author lepeschko
 */
public class NeuAnforderung extends javax.swing.JFrame {
    Lieferung lieferung;
    DBManager dbManager;
    PersonUser person;
    ArrayList<Artikel> artikelListe  = new ArrayList<>();;
    ArrayList<Artikel> lagerArtikelListe = new ArrayList<>(); // Список артикулов из таблицы lager для ComboBox5
    private javax.swing.JFrame textAreaEditorFrame; // Ссылка на окно редактора текста
    
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
     private javax.swing.JTextField jTextField7;
    
     public NeuAnforderung(PersonUser pers) {
      //   initComponents();
      dbManager = new DBManager();
      this.person = pers;
      this.setTitle("Thyssen Schachtbaz GmbH -  Nneue Anforderung");
      startComponents();
    }
    
   public void startComponents(){
        
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField6 = new javax.swing.JTextField();
        jComboBox5 = new javax.swing.JComboBox<>();
        jButton3 = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jPanel7 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
         jTextField7 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Suchen"));

        jTextField6.setText("");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox5.setMinimumSize(new java.awt.Dimension(50, 22));

        jButton3.setText("OK");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, 562, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
                    .addComponent(jComboBox5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField6))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTextField3.setText("0");
        jTextField3.setBorder(javax.swing.BorderFactory.createTitledBorder("Menge"));

        jTextField4.setText("");
        jTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder("Einheit"));

        jButton1.setBackground(new java.awt.Color(204, 255, 204));
        jButton1.setText("Eintragen/ Save");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 161, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                    .addComponent(jTextField4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder("Beschreibung der Ware/ Goods description"));

        jTextField1.setText("");
        jTextField1.setToolTipText("Deutsch");

        jTextField2.setText("");
        jTextField2.setToolTipText("English");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTextField1)
            .addComponent(jTextField2)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

       jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Bestellung"));

        jTextArea2.setColumns(20);
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

jTextField7.setText("(automatisch nach Speichern)");
jTextField7.setEditable(false);
        jTextField7.setBorder(javax.swing.BorderFactory.createTitledBorder("Agreement/Anford. Nr."));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addComponent(jTextField7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        // Добавляем обработчик двойного клика для jTextArea2
        jTextArea2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    openTextAreaEditor(jTextArea2, "Bestellung - Erweiterte Bearbeitung", "Bestellung");
                }
            }
        });

       

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Baustelle/Projekt/Abteilung"));

        jTextField5.setText("");
        jTextField5.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        // Список проектов из таблицы lieferung (поле abteilung)
        java.util.ArrayList<Lieferung> lieferungenList = new ArrayList<>();
        try {
            if (dbManager != null && dbManager.isConnectionValid()) {
                lieferungenList = dbManager.getAllLieferungen();
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Laden der Projekte: " + e.getMessage());
        }
        Set<String> uniqueProjects = new HashSet<>();
        for (Lieferung lif : lieferungenList) {
            String projectName = lif.getProjectName();
            if (projectName != null && !projectName.trim().isEmpty() && !projectName.equals(" ")) {
                uniqueProjects.add(projectName.trim());
            }
        }
        List<String> projectsSorted = new ArrayList<>(uniqueProjects);
        Collections.sort(projectsSorted);
        String[] projectArray = projectsSorted.toArray(new String[0]);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(projectArray));

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Object sel = jComboBox1.getSelectedItem();
                if (sel != null) {
                    jTextField5.setText(sel.toString());
                }
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Wunschliefertermin"));

        jComboBox2.setBorder(javax.swing.BorderFactory.createTitledBorder("Tag"));

        jComboBox3.setBorder(javax.swing.BorderFactory.createTitledBorder("Monat"));

        jComboBox4.setBorder(javax.swing.BorderFactory.createTitledBorder("Jahr"));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                    .addComponent(jComboBox3)
                    .addComponent(jComboBox4))
                .addContainerGap())
        );

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder("Kommentar"));
        jScrollPane1.setViewportView(jTextArea1);
        
        // Добавляем обработчик двойного клика для jTextArea1
        jTextArea1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    openTextAreaEditor(jTextArea1, "Kommentar - Erweiterte Bearbeitung", "Kommentar");
                }
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 204, 204));
        jButton2.setText("Anforderung speichern");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("File");

        jMenuItem1.setText("Tabelle \"Lager\" in DB erstellen");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createLagerTableWithPassword();
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("jMenuItem2");
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");

        jMenuItem3.setText("jMenuItem3");
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("jMenuItem4");
        jMenu2.add(jMenuItem4);

        jMenuBar1.add(jMenu2);

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
        
        // Инициализируем комбобоксы с датами
        initializeDateComboBoxes();
        
        // Загружаем артикулы из базы данных в jComboBox5
        loadArtikelToComboBox5();
        
        // Добавляем обработчики событий
        setupEventHandlers();
    }
    
    /**
     * Инициализация комбобоксов для выбора даты
     */
    private void initializeDateComboBoxes() {
        Calendar cal = Calendar.getInstance();
        
        // Инициализация ComboBox для месяцев (немецкие названия)
        String[] months = {"Januar", "Februar", "März", "April", "Mai", "Juni", 
                          "Juli", "August", "September", "Oktober", "November", "Dezember"};
        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(months));
        
        // Инициализация ComboBox для годов (2010-2060)
        String[] years = new String[51]; // 2010-2060 = 51 год
        for (int i = 0; i < 51; i++) {
            years[i] = String.valueOf(2010 + i);
        }
        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(years));
        
        // Устанавливаем текущий год
        int currentYear = cal.get(Calendar.YEAR);
        if (currentYear >= 2010 && currentYear <= 2060) {
            jComboBox4.setSelectedItem(String.valueOf(currentYear));
        } else if (currentYear < 2010) {
            jComboBox4.setSelectedIndex(0); // 2010
        } else {
            jComboBox4.setSelectedIndex(50); // 2060
        }
        
        // Устанавливаем текущий месяц
        jComboBox3.setSelectedIndex(cal.get(Calendar.MONTH));
        
        // Инициализируем дни месяца (после установки месяца и года)
        updateDaysInMonth();
        
        // Устанавливаем текущий день
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        if (jComboBox2.getItemCount() > 0) {
            int dayIndex = currentDay - 1;
            if (dayIndex >= 0 && dayIndex < jComboBox2.getItemCount()) {
                jComboBox2.setSelectedIndex(dayIndex);
            }
        }
        
        // Добавляем слушатели для обновления дней при изменении месяца или года
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateDaysInMonth();
            }
        });
        
        jComboBox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateDaysInMonth();
            }
        });
    }
    
    /**
     * Обновляет количество дней в ComboBox в зависимости от выбранного месяца и года
     */
    private void updateDaysInMonth() {
        if (jComboBox3.getSelectedIndex() < 0 || jComboBox4.getSelectedItem() == null) {
            return;
        }
        
        int selectedMonth = jComboBox3.getSelectedIndex() + 1; // 1-12
        String yearString = (String) jComboBox4.getSelectedItem();
        if (yearString == null || yearString.trim().isEmpty()) {
            return;
        }
        
        int selectedYear;
        try {
            selectedYear = Integer.parseInt(yearString);
        } catch (NumberFormatException e) {
            System.err.println("Fehler beim Parsen des Jahres: " + yearString);
            return;
        }
        
        // Определяем количество дней в месяце
        int daysInMonth;
        if (selectedMonth == 2) { // Февраль
            // Проверяем високосный год
            boolean isLeapYear = (selectedYear % 4 == 0 && selectedYear % 100 != 0) || (selectedYear % 400 == 0);
            daysInMonth = isLeapYear ? 29 : 28;
        } else if (selectedMonth == 4 || selectedMonth == 6 || selectedMonth == 9 || selectedMonth == 11) {
            // Апрель, Июнь, Сентябрь, Ноябрь - 30 дней
            daysInMonth = 30;
        } else {
            // Остальные месяцы - 31 день
            daysInMonth = 31;
        }
        
        // Сохраняем текущий выбранный день
        int currentSelectedDay = jComboBox2.getSelectedIndex() + 1;
        if (currentSelectedDay > daysInMonth) {
            currentSelectedDay = daysInMonth;
        }
        
        // Создаем массив дней
        String[] days = new String[daysInMonth];
        for (int i = 0; i < daysInMonth; i++) {
            days[i] = String.valueOf(i + 1);
        }
        
        // Устанавливаем новую модель
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(days));
        
        // Восстанавливаем выбранный день (если возможно)
        int dayIndex = currentSelectedDay - 1;
        if (dayIndex >= 0 && dayIndex < daysInMonth) {
            jComboBox2.setSelectedIndex(dayIndex);
        } else {
            jComboBox2.setSelectedIndex(daysInMonth - 1);
        }
    }
    
    /**
     * Настройка обработчиков событий
     */
    private void setupEventHandlers() {
        // Обработчик для кнопки jButton1 (Eintragen/ Save)
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTextToTextArea2();
            }
        });
        
        // Обработчик для кнопки jButton2 (Anforderung speichern)
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveLieferungToDatabase();
            }
        });
        
        // Обработчик для кнопки jButton3 (OK) - заполняет поля из выбранного артикула
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fillFieldsFromSelectedArtikel();
            }
        });
        
        // Обработчик для Enter в jComboBox5 - то же действие, что и кнопка jButton3 (OK)
        jComboBox5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    fillFieldsFromSelectedArtikel();
                    evt.consume();
                }
            }
        });
        
        // Обработчик для Enter и Tab в jTextField1
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    addTextToTextArea2();
                    evt.consume();
                } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_TAB) {
                    jTextField2.requestFocusInWindow();
                    evt.consume(); // Предотвращаем стандартное поведение Tab
                }
            }
        });
        
        // Обработчик для Enter и Tab в jTextField2
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    addTextToTextArea2();
                    evt.consume();
                } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_TAB) {
                    jTextField3.requestFocusInWindow();
                    evt.consume(); // Предотвращаем стандартное поведение Tab
                }
            }
        });
        
        // Обработчик для Enter и Tab в jTextField3
        jTextField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    addTextToTextArea2();
                    evt.consume();
                } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_TAB) {
                    jTextField4.requestFocusInWindow();
                    evt.consume(); // Предотвращаем стандартное поведение Tab
                }
            }
        });
        
        // Обработчик для Enter и Tab в jTextField4
        jTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    addTextToTextArea2();
                    evt.consume();
                } else if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_TAB) {
                    // После jTextField4 можно вернуться к jTextField1 или оставить стандартное поведение
                    jTextField1.requestFocusInWindow();
                    evt.consume(); // Предотвращаем стандартное поведение Tab
                }
            }
        });
        
        // Обработчик для Enter в jTextField6 (поле поиска) - обновляет список в jComboBox5
        jTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    String searchText = jTextField6.getText();
                    loadArtikelToComboBox5(searchText);
                    evt.consume();
                }
            }
        });
    }
    
    /**
     * Метод для объединения текста из полей и записи в jTextArea2
     * Формат: текст_из_jTextField1(текст_из_jTextField2) текст_из_jTextField3 текст_из_jTextField4
     */
   
    private void addTextToTextArea2() {
        // Проверка: если Bestellmenge = "0", уточняем у пользователя
        String mengeText = jTextField3.getText().trim();
        if ("0".equals(mengeText)) {
            int answer = javax.swing.JOptionPane.showConfirmDialog(this,
                    "Wollen Sie tatsächlich die Bestellmenge als \"0\" speichern?",
                    "Bestellmenge",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE);
            if (answer != javax.swing.JOptionPane.YES_OPTION) {
                jTextField3.requestFocusInWindow();
                jTextField3.selectAll();
                return;
            }
        }

        // Получаем тексты из полей
        Artikel art = new Artikel();
        art.setMaterial_DE(jTextField1.getText());
        art.setMaterial_EN(jTextField2.getText());
        art.setAnfordrung(jTextField7.getText()); 
        art.setAdd_Info(jTextArea1.getText());
        if(jTextField5.getText().isEmpty()){
            String pro = "##no Project## ";
            art.setInfo03("##"+ pro + "##");
        }else{
             art.setInfo03(jTextField5.getText()); 
        }
        
      

        art.setMenge(Float.parseFloat(jTextField3.getText()));
       
        art.setEinheit(jTextField4.getText());
        
         artikelListe.add(art);
        String text1 = jTextField1.getText().trim();
        String text2 = jTextField2.getText().trim();
        String text3 = jTextField3.getText().trim();
        String text4 = jTextField4.getText().trim();
        
        // Формируем строку: text1(text2) text3 text4
        StringBuilder result = new StringBuilder();
        
        // Добавляем текст из jTextField1
        if (!text1.isEmpty()) {
            result.append(text1);
        }
        
        // Добавляем текст из jTextField2 в скобках, если он не пустой
        if (!text2.isEmpty()) {
            result.append("(").append(text2).append(")");
        }
        
        // Добавляем пробел перед text3, если уже есть текст
        if (result.length() > 0 && !text3.isEmpty()) {
            result.append(" ");
        }
        
        // Добавляем текст из jTextField3
        if (!text3.isEmpty()) {
            result.append(text3);
        }
        
        // Добавляем пробел перед text4, если уже есть текст
        if (result.length() > 0 && !text4.isEmpty()) {
            result.append(" ");
        }
        
        // Добавляем текст из jTextField4
        if (!text4.isEmpty()) {
            result.append(text4);
        }
        
        // Если результирующая строка не пустая, добавляем её в jTextArea2
        if (result.length() > 0) {
            String currentText = jTextArea2.getText();
            if (currentText.isEmpty()) {
                jTextArea2.setText(result.toString());
            } else {
                jTextArea2.setText(currentText + "\n" + result.toString());
            }
        }
    }
    public Lieferung getLieferung()
    {
        float menge = 0;
        String mengeText = jTextField3.getText().trim();
        if(!mengeText.isEmpty()){
            try {
                menge = Float.parseFloat(mengeText);
            } catch (NumberFormatException e) {
                System.err.println("Fehler beim Parsen von menge: " + e.getMessage());
                menge = 0;
            }
        }
        
        lieferung = new Lieferung();
        lieferung.setNeu(true);
        lieferung.setAktiv(true);
        lieferung.setBeschreibung(jTextArea2.getText() != null ? jTextArea2.getText() : "");
        lieferung.setMenge(menge);
        lieferung.setEinhMenge(jTextField4.getText() != null ? jTextField4.getText() : "");
        // Prefix (erste 5 Zeichen von aktuellem Login) wird mit DB-ID kombiniert
        lieferung.setAnfordNummer(getCurrentLoginPrefix());
        lieferung.setAnfordPerson(person.getName());
        String projectNameText = jTextField5.getText();
        if(projectNameText != null && !projectNameText.trim().isEmpty()){
            lieferung.setProjectName(projectNameText);
        }
        
        // Формируем дату из комбобоксов
        Object day = jComboBox2.getSelectedItem();
        Object month = jComboBox3.getSelectedIndex();
        Object year = jComboBox4.getSelectedItem();
        String dayStr = (day != null) ? day.toString() : "";
        String monthStr = (month != null) ? month.toString() : "";
        String yearStr = (year != null) ? year.toString() : "";
        lieferung.setWunschLieferDatum(dayStr + "." + monthStr + "." + yearStr);
        
        lieferung.setKommentarBS(jTextArea1.getText() != null ? jTextArea1.getText() : "");

        return lieferung;
    }
    
    /**
     * Übersetzt deutschen Text ins Englische (MyMemory-API, funktioniert ohne API-Key).
     * Für Google Cloud Translation API müsste ein API-Key gesetzt werden.
     * @param germanText deutscher Text
     * @return englische Übersetzung oder null bei Fehler/leerem Eingabetext
     */
    private String translateGermanToEnglish(String germanText) {
        if (germanText == null || germanText.trim().isEmpty()) {
            return null;
        }
        try {
            String encoded = URLEncoder.encode(germanText.trim(), "UTF-8");
            String urlStr = "https://api.mymemory.translated.net/get?q=" + encoded + "&langpair=de%7Cen";
            URI uri = URI.create(urlStr);
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int code = conn.getResponseCode();
            if (code != 200) {
                return null;
            }
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            conn.disconnect();
            // Einfaches Auslesen: "translatedText":"...":
            String json = response.toString();
            int start = json.indexOf("\"translatedText\":\"");
            if (start < 0) return null;
            start += 18;
            int end = json.indexOf("\"", start);
            if (end < 0) return null;
            String translated = json.substring(start, end)
                .replace("\\u0026", "&")
                .replace("\\\"", "\"")
                .replace("\\n", "\n");
            return translated;
        } catch (Exception e) {
            System.err.println("Übersetzung fehlgeschlagen: " + e.getMessage());
            return null;
        }
        
        
        
    }

    /**
     * Übersetzt englischen Text ins Deutsche (MyMemory-API).
     * @param englishText englischer Text
     * @return deutsche Übersetzung oder null bei Fehler/leerem Eingabetext
     */
    private String translateEnglishToGerman(String englishText) {
        if (englishText == null || englishText.trim().isEmpty()) {
            return null;
        }
        try {
            String encoded = URLEncoder.encode(englishText.trim(), "UTF-8");
            String urlStr = "https://api.mymemory.translated.net/get?q=" + encoded + "&langpair=en%7Cde";
            URI uri = URI.create(urlStr);
            HttpURLConnection conn = (HttpURLConnection) uri.toURL().openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            int code = conn.getResponseCode();
            if (code != 200) {
                return null;
            }
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            conn.disconnect();
            String json = response.toString();
            int start = json.indexOf("\"translatedText\":\"");
            if (start < 0) return null;
            start += 18;
            int end = json.indexOf("\"", start);
            if (end < 0) return null;
            String translated = json.substring(start, end)
                .replace("\\u0026", "&")
                .replace("\\\"", "\"")
                .replace("\\n", "\n");
            return translated;
        } catch (Exception e) {
            System.err.println("Übersetzung EN→DE fehlgeschlagen: " + e.getMessage());
            return null;
        }
    }

    /**
     * Сохраняет Lieferung в базу данных
     */
    private void saveLieferungToDatabase() {
        try {
            // Получаем объект Lieferung aus der Form
            Lieferung lieferung = getLieferung();
            
            // Проверяем, что объект создан успешно
            if (lieferung == null) {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Fehler: Lieferung konnte nicht erstellt werden.",
                    "Fehler",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Сохраняем в базу данных
            boolean success = dbManager.addInTabLif(lieferung);
            
            if (success) {
                // Vor dem Speichern: fehlende Sprache übersetzen (DE→EN oder EN→DE)
                int savedArtikelCount = 0;
                int failedArtikelCount = 0;
                for (Artikel artikel : artikelListe) {
                    // Artikel an dieselbe Anforderungsnummer (DB-ID) anbinden
                    artikel.setAnfordrung(lieferung.getAnfordNummer());
                    String de = artikel.getMaterial_DE();
                    String en = artikel.getMaterial_EN();
                    boolean hasDe = de != null && !de.trim().isEmpty();
                    boolean hasEn = en != null && !en.trim().isEmpty();
                    if (hasDe) {
                        // Deutsch vorhanden → ins Englische übersetzen
                        String translatedEn = translateGermanToEnglish(de);
                        if (translatedEn != null && !translatedEn.isEmpty()) {
                            artikel.setMaterial_EN(translatedEn);
                        }
                    } else if (hasEn) {
                        // Nur Englisch vorhanden → ins Deutsche übersetzen
                        String translatedDe = translateEnglishToGerman(en);
                        if (translatedDe != null && !translatedDe.isEmpty()) {
                            artikel.setMaterial_DE(translatedDe);
                        }
                    }
                    artikel.setAdd_Info(jTextArea1.getText());
                    artikel.setInfo01("0.0");
                    if (dbManager.addArtikel(artikel)) {
                        savedArtikelCount++;
                    } else {
                        failedArtikelCount++;
                    }
                }
                
                // Формируем сообщение о результате
                StringBuilder resultMessage = new StringBuilder();
                resultMessage.append("Anforderung erfolgreich gespeichert!\n");
                
                if (!artikelListe.isEmpty()) {
                    resultMessage.append("Artikel gespeichert: ").append(savedArtikelCount);
                    if (failedArtikelCount > 0) {
                        resultMessage.append("\nFehler bei Artikel: ").append(failedArtikelCount);
                    }
                }
                
                javax.swing.JOptionPane.showMessageDialog(this,
                    resultMessage.toString(),
                    "Erfolg",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    
                // Очищаем форму после успешного сохранения
                clearForm();
            } else {
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Fehler beim Speichern der Anforderung.",
                    "Fehler",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Speichern: " + e.getMessage());
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                "Fehler beim Speichern: " + e.getMessage(),
                "Fehler",
                javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Создаёт таблицу "Lager" в базе данных после проверки пароля
     */
    private void createLagerTableWithPassword() {
        // Запрашиваем пароль у пользователя
        javax.swing.JPasswordField passwordField = new javax.swing.JPasswordField();
        int option = javax.swing.JOptionPane.showConfirmDialog(
            this,
            passwordField,
            "Bitte geben Sie das Passwort ein:",
            javax.swing.JOptionPane.OK_CANCEL_OPTION,
            javax.swing.JOptionPane.PLAIN_MESSAGE
        );
        
        if (option == javax.swing.JOptionPane.OK_OPTION) {
            String enteredPassword = new String(passwordField.getPassword());
            
            // Проверяем пароль (замените "admin123" на ваш пароль)
            final String ADMIN_PASSWORD = "adminDima";
            
            if (ADMIN_PASSWORD.equals(enteredPassword)) {
                // Пароль верный, создаём таблицу
                try {
                    dbManager.createArtikelTable();
                    javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Tabelle \"Lager\" wurde erfolgreich erstellt!",
                        "Erfolg",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (Exception e) {
                    javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Fehler beim Erstellen der Tabelle: " + e.getMessage(),
                        "Fehler",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                // Неверный пароль
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Falsches Passwort! Zugriff verweigert.",
                    "Fehler",
                    javax.swing.JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    /**
     * Очищает форму после успешного сохранения
     */
    private void clearForm() {
        jTextField1.setText("");
        jTextField2.setText("");
        jTextField3.setText("0");
        jTextField4.setText("");
        jTextField5.setText("");
        jTextField7.setText("");
        jTextArea1.setText("");
        jTextArea2.setText("");
        artikelListe.clear();
        // Комбобоксы можно оставить как есть или сбросить на значения по умолчанию
    }

    private String getCurrentLoginPrefix() {
        String pref =  "";
        String login = (person != null && person.getLogin() != null) ? person.getLogin().trim() : "";
        if (login.isEmpty()) {
            return "noname_";
        }
        pref = login.length() <= 5 ? login : login.substring(0, 5) +"_";
        return pref;
    }
    
    /**
     * Загружает все артикулы из таблицы lager в jComboBox5
     * Формат отображения: material_DE / material_EN
     * Дубликаты по комбинации material_DE + material_EN фильтруются
     * @param searchText текст для поиска (если пустой или null, показываются все артикулы)
     */
    private void loadArtikelToComboBox5(String searchText) {
        // Получаем все артикулы из базы данных
        ArrayList<Artikel> allArtikel = dbManager.getAllArtikel();
        
        // Фильтруем дубликаты по комбинации material_DE + material_EN
        ArrayList<Artikel> uniqueArtikel = new ArrayList<>();
        java.util.HashSet<String> uniqueKeys = new java.util.HashSet<>();
        
        for (Artikel art : allArtikel) {
            String materialDE = art.getMaterial_DE() != null ? art.getMaterial_DE() : "";
            String materialEN = art.getMaterial_EN() != null ? art.getMaterial_EN() : "";
            String key = materialDE + "|||" + materialEN; // Уникальный ключ
            
            if (!uniqueKeys.contains(key)) {
                uniqueKeys.add(key);
                uniqueArtikel.add(art);
            }
        }
        
        // Фильтруем по поисковому запросу, если он указан
        lagerArtikelListe = new ArrayList<>();
        String searchLower = (searchText != null && !searchText.trim().isEmpty()) 
            ? searchText.trim().toLowerCase() : null;
        
        for (Artikel art : uniqueArtikel) {
            // Если поисковый запрос пустой, добавляем все артикулы
            if (searchLower == null) {
                lagerArtikelListe.add(art);
            } else {
                // Иначе проверяем, соответствует ли артикул поисковому запросу
                String materialDE = art.getMaterial_DE() != null ? art.getMaterial_DE().toLowerCase() : "";
                String materialEN = art.getMaterial_EN() != null ? art.getMaterial_EN().toLowerCase() : "";
                
                // Проверяем, содержится ли поисковый текст в material_DE или material_EN
                if (materialDE.contains(searchLower) || materialEN.contains(searchLower)) {
                    lagerArtikelListe.add(art);
                }
            }
        }
        
        // Создаём массив строк для ComboBox
        String[] artikelStrings;
        
        if (lagerArtikelListe.isEmpty()) {
            artikelStrings = new String[]{"Keine Artikel vorhanden"};
        } else {
            artikelStrings = new String[lagerArtikelListe.size()];
            for (int i = 0; i < lagerArtikelListe.size(); i++) {
                Artikel art = lagerArtikelListe.get(i);
                String materialDE = art.getMaterial_DE() != null ? art.getMaterial_DE() : "";
                String materialEN = art.getMaterial_EN() != null ? art.getMaterial_EN() : "";
                artikelStrings[i] = materialDE + " / " + materialEN;
            }
        }
        
        // Устанавливаем модель для ComboBox
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(artikelStrings));
    }
    
    /**
     * Перегрузка метода для загрузки всех артикулов (без фильтрации)
     */
    private void loadArtikelToComboBox5() {
        loadArtikelToComboBox5(null);
    }
    
    /**
     * Заполняет поля jTextField1 и jTextField2 данными из выбранного артикула в jComboBox5
     */
    private void fillFieldsFromSelectedArtikel() {
        int selectedIndex = jComboBox5.getSelectedIndex();
        
        // Проверяем, что выбран допустимый индекс и список не пустой
        if (selectedIndex >= 0 && selectedIndex < lagerArtikelListe.size() && !lagerArtikelListe.isEmpty()) {
            Artikel selectedArtikel = lagerArtikelListe.get(selectedIndex);
            
            // Заполняем поля
            jTextField1.setText(selectedArtikel.getMaterial_DE() != null ? selectedArtikel.getMaterial_DE() : "");
            jTextField2.setText(selectedArtikel.getMaterial_EN() != null ? selectedArtikel.getMaterial_EN() : "");
        }
    }
    
    /**
     * Универсальный метод для открытия окна редактора текста для любого JTextArea
     * @param textArea JTextArea для редактирования
     * @param frameTitle заголовок окна
     * @param borderTitle заголовок для границы текстового поля
     */
    private void openTextAreaEditor(javax.swing.JTextArea textArea, String frameTitle, String borderTitle) {
        // Проверяем, не открыто ли уже окно редактора для этого поля
        if (textAreaEditorFrame != null && textAreaEditorFrame.isDisplayable()) {
            textAreaEditorFrame.requestFocus();
            return;
        }
        
        // Создаем новое JFrame
        javax.swing.JFrame editorFrame = new javax.swing.JFrame(frameTitle);
        editorFrame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        textAreaEditorFrame = editorFrame; // Сохраняем ссылку
        
        // Создаем JTextArea для редактирования
        javax.swing.JTextArea editorTextArea = new javax.swing.JTextArea();
        editorTextArea.setLineWrap(true);
        editorTextArea.setWrapStyleWord(true);
        editorTextArea.setFont(textArea.getFont());
        editorTextArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, borderTitle, 
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
            javax.swing.border.TitledBorder.DEFAULT_POSITION, 
            new java.awt.Font("Segoe UI", 1, 14)));
        
        // Копируем текст из исходного JTextArea
        editorTextArea.setText(textArea.getText());
        
        // Получаем размеры исходного JTextArea
        int originalRows = textArea.getRows();
        int originalColumns = textArea.getColumns();
        
        // Устанавливаем размеры: высота в 3 раза больше, ширина немного больше
        editorTextArea.setRows(originalRows * 3);
        editorTextArea.setColumns(Math.max(originalColumns, 60));
        
        // Создаем JScrollPane для прокрутки
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(editorTextArea);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Сохраняем ссылку на исходное поле для сохранения текста
        final javax.swing.JTextArea sourceTextArea = textArea;
        
        // Добавляем обработчик закрытия окна для сохранения текста
        editorFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Сохраняем текст обратно в исходное JTextArea
                sourceTextArea.setText(editorTextArea.getText());
                textAreaEditorFrame = null; // Очищаем ссылку при закрытии
            }
            
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                textAreaEditorFrame = null; // Очищаем ссылку при закрытии
            }
        });
        
        // Настраиваем layout
        editorFrame.getContentPane().setLayout(new java.awt.BorderLayout());
        editorFrame.getContentPane().add(scrollPane, java.awt.BorderLayout.CENTER);
        
        // Устанавливаем размер окна (около 600x400 для удобного редактирования)
        editorFrame.setSize(600, 400);
        editorFrame.setMinimumSize(new java.awt.Dimension(300, 200));
        editorFrame.setLocationRelativeTo(this); // Центрируем относительно родительского окна
        editorFrame.setVisible(true);
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
            java.util.logging.Logger.getLogger(NeuAnforderungModel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NeuAnforderungModel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NeuAnforderungModel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NeuAnforderungModel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              //  new NeuAnforderung(person).setVisible(true);
            }
        });
    }
}
