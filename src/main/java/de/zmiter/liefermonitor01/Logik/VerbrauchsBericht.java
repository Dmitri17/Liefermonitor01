/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.Artikel;
import de.zmiter.liefermonitor01.DBObjekte.Lieferung;
import de.zmiter.liefermonitor01.DBObjekte.PersonUser;
import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author lepeschko
 */
public class VerbrauchsBericht extends javax.swing.JFrame{
    
    private static final String AUTO_SAVE_DIRECTORY = "C:\\Users\\lepeschko\\OneDrive - ts-gruppe.com\\Dokumente\\Lager_in_der_App";
    private static final DateTimeFormatter SAVE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    private ArrayList<Artikel> searchResultList = new ArrayList<>();
    private ArrayList<String> verbrauchList = new ArrayList<>();
    private ArrayList<Artikel> geaenderteArtikel = new ArrayList<>();
    private final PersonUser personUser;
    DBManager dbManager;
    
    public VerbrauchsBericht(){
        this(null);
    }

    public VerbrauchsBericht(PersonUser personUser){
        this.personUser = personUser;
        startComponents();
        this.setTitle("Verbrauchsbericht. Übergabe Materialien vom Lager.");
    }
    
    @SuppressWarnings("unchecked")
     private void startComponents() {

        jMenu1 = new javax.swing.JMenu();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextFieldUserName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setAutoscrolls(true);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>());
        jComboBox1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Projekt", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        // Projekte-Liste laden wie in LieferListFensterM (aus allen Lieferungen)
        loadProjectsForCombo();

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "WBS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jTextField1.setText("");
        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Suchen/seach", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                performArtikelSearch();
            }
        });

        jLabel1.setText("");
        jLabel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("");
        jLabel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel3.setText("");
        jLabel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        try{
            jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/TS-Kugel.png"))); // NOI18N
        
        }catch(Exception ex){
         ex.printStackTrace();
            System.out.println("Problemme mit dem Laden von Bild...");
     }
        jLabel4.setText("TS");
        jLabel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, 287, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jComboBox2, 0, 287, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jComboBox2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>());
        jComboBox3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Material", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jComboBox3.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Artikel) {
                    Artikel a = (Artikel) value;
                    String de = a.getMaterial_DE() != null ? a.getMaterial_DE() : "";
                    String en = a.getMaterial_EN() != null ? a.getMaterial_EN() : "";
                    setText(de + " / " + en);
                } else if (value == null) {
                    setText("");
                }
                return this;
            }
        });
        jComboBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateEinheitFromSelection();
            }
        });

        jFormattedTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Menge", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCurrentSelectionToVerbrauchList();
            }
        });

        jTextField2.setText("");
        jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einheit", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 255, 204));
        jButton1.setText("Eintragen");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCurrentSelectionToVerbrauchList();
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jFormattedTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jTextArea1.setBackground(new java.awt.Color(250, 255, 246));
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setBackground(new java.awt.Color(255, 204, 204));
        jButton2.setText("in DB eintragen");
        jButton2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveVerbrauchToDatabaseAndExcel();
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 204, 255));
        jButton3.setText("als Excel Datei speichern");
        jButton3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.setVisible(false);

        jTextFieldUserName.setText(personUser != null && personUser.getName() != null ? personUser.getName() : "");
        jTextFieldUserName.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Benutzername", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N

        String currentUser = personUser != null && personUser.getName() != null ? personUser.getName().trim() : "";
        jLabel5.setText("Benutzer: " + currentUser);
        jLabel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1047, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                    .addComponent(jTextFieldUserName)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)))
                .addContainerGap())
        );

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
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu2.setText("File");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Edit");
        jMenuBar1.add(jMenu3);

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
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(VerbrauchsBerichtForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VerbrauchsBerichtForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VerbrauchsBerichtForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VerbrauchsBerichtForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VerbrauchsBerichtForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<Artikel> jComboBox3;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextFieldUserName;
    
    /**
     * Выполняет поиск по таблице lager (Artikel) по тексту из jTextField1
     * и заполняет результатами выпадающий список jComboBox3.
     * В списке отображаются поля material_DE и material_EN.
     */
    private void performArtikelSearch() {
        String text = jTextField1.getText();
        ArrayList<Artikel> list = new ArrayList<>();
        DBManager dbManager = null;
        try {
            dbManager = new DBManager();
            if (text == null || text.trim().isEmpty()) {
                list = dbManager.getAllArtikel();
            } else {
                list = dbManager.searchArtikel(text.trim());
            }
        } catch (Exception ex) {
            System.err.println("Fehler beim Suchen der Artikel im VerbrauchsBericht: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (dbManager != null) {
                dbManager.closeConnection();
            }
        }
        
        searchResultList = list;
        DefaultComboBoxModel<Artikel> model = new DefaultComboBoxModel<>();
        for (Artikel a : list) {
            model.addElement(a);
        }
        jComboBox3.setModel(model);
        if (model.getSize() > 0) {
            jComboBox3.setSelectedIndex(0);
        }
    }

    /**
     * Обновляет поле jTextField2 (Einheit) на основе выбранного в jComboBox3 Artikels.
     */
    private void updateEinheitFromSelection() {
        Object sel = jComboBox3.getSelectedItem();
        if (sel instanceof Artikel) {
            Artikel a = (Artikel) sel;
            String einheit = a.getEinheit();
            jTextField2.setText(einheit != null ? einheit : "");
        } else {
            jTextField2.setText("");
        }
    }

    /**
     * Загружает список проектов (Baustellen) в jComboBox1 так же, как при открытии LieferListFensterM:
     * собирает уникальные projectName (abteilung) из всех Lieferungen.
     */
    private void loadProjectsForCombo() {
        java.util.Set<String> uniqueProjects = new java.util.HashSet<>();
        DBManager dbManager = null;
        try {
            dbManager = new DBManager();
            java.util.ArrayList<Lieferung> lieferungen = dbManager.getAllLieferungen();
            for (Lieferung lieferung : lieferungen) {
                String projectName = lieferung.getProjectName();
                if (projectName != null && !projectName.trim().isEmpty() && !projectName.equals(" ")) {
                    uniqueProjects.add(projectName.trim());
                }
            }
        } catch (Exception ex) {
            System.err.println("Fehler beim Laden der Projekte für VerbrauchsBericht: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (dbManager != null) {
                dbManager.closeConnection();
            }
        }

        java.util.List<String> projects = new java.util.ArrayList<>();
        projects.add("Alle Projekte");
        projects.addAll(uniqueProjects);

        String[] projectArray = projects.toArray(new String[0]);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(projectArray));
    }

    /**
     * Добавляет текущий выбор (Artikel + Menge + Einheit) в список verbrauchList
     * и записывает строку в jTextArea1.
     */
    private void addCurrentSelectionToVerbrauchList() {
        Object sel = jComboBox3.getSelectedItem();
        if (!(sel instanceof Artikel)) {
            return;
        }
        Artikel a = (Artikel) sel;
        String menge = jFormattedTextField1.getText() != null ? jFormattedTextField1.getText().trim() : "";
        // Проверка: Menge не должна быть 0
        if (menge.isEmpty() || menge.equals("0") || menge.equals("0,0") || menge.equals("0.0")) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Bitte geben Sie eine von 0 verschiedene Menge ein.",
                    "Ungültige Menge",
                    javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }
        String einheit = jTextField2.getText() != null ? jTextField2.getText().trim() : "";
        String de = a.getMaterial_DE() != null ? a.getMaterial_DE() : "";
        String en = a.getMaterial_EN() != null ? a.getMaterial_EN() : "";

        StringBuilder sb = new StringBuilder();
        if (!menge.isEmpty()) {
            sb.append(menge);
            if (!einheit.isEmpty()) {
                sb.append(" ").append(einheit);
            }
            sb.append(" - ");
        }
        sb.append(de);
        if (!de.isEmpty() || !en.isEmpty()) {
            sb.append(" / ");
        }
        sb.append(en);

        String line = sb.toString();
        verbrauchList.add(line);

        String existing = jTextArea1.getText();
        if (existing == null || existing.isEmpty()) {
            jTextArea1.setText(line);
        } else {
            jTextArea1.setText(existing + System.lineSeparator() + line);
        }

        // Menge nur im Speicher vom Lagerbestand abziehen, ohne sofortige DB-Aktualisierung
       String aktuellVerbrMengeObj = "";
        if (!menge.isEmpty()) {
            try {
                String info01 = a.getInfo01();
                if (info01 == null || info01.trim().isEmpty()) {
                    info01 = "0";
                    a.setInfo01(info01);
                }
                aktuellVerbrMengeObj = info01.trim();
                float verbrauchMenge = Float.parseFloat(menge.replace(",", "."));
            
              Float currMen = Float.parseFloat(aktuellVerbrMengeObj.replace(",", "."));
                float currentMenge = currMen != null ? currMen : 0f;
                float newMenge = currentMenge + verbrauchMenge;
                a.setInfo01(""+newMenge);

                if (!geaenderteArtikel.contains(a)) {
                    geaenderteArtikel.add(a);
                }
            } catch (NumberFormatException nfe) {
                System.err.println("Ungültiger Zahlenwert für Menge: " + menge);
            }
        }
 }
    /**
     * Сохраняет все накопленные изменения количества (geaenderteArtikel)
     * в таблицу lager с помощью DBManager.updateArtikel(...) при нажатии jButton2.
     */
     private void saveVerbrauchToDatabaseAndExcel() {
        if (geaenderteArtikel.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Es gibt keine geänderten Artikel zum Speichern.",
                    "Keine Daten",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        DBManager dbManager = null;
        try {
            dbManager = new DBManager();
            for (Artikel a : geaenderteArtikel) {
                try {
                    dbManager.updateArtikel(a);
                    System.out.println("Artikel is UPDATED!!!!!!!!!!!!!!!!!!!!!!_________" + a.getMaterial_DE());
                } catch (Exception ex) {
                    System.err.println("Fehler beim Aktualisieren des Artikels (ID " + a.getId() + "): " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Die geänderten Artikel wurden erfolgreich in der DB gespeichert.",
                    "Speichern erfolgreich",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE);

            saveFormDataToExcel();
            geaenderteArtikel.clear();

            // Wenn die ArtikelListe geöffnet ist, aktualisieren wir sie mit den aktuellen Filtern
            ArtikelListe liste = ArtikelListe.getLastInstance();
            if (liste != null) {
                liste.refreshWithCurrentFilters();
            }
        } catch (Exception exOuter) {
            System.err.println("Fehler beim Speichern der Änderungen in der DB: " + exOuter.getMessage());
            exOuter.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this,
                    "Fehler beim Speichern der Änderungen in der DB:\n" + exOuter.getMessage(),
                    "Fehler",
                    javax.swing.JOptionPane.ERROR_MESSAGE);
        } finally {
            if (dbManager != null) {
                dbManager.closeConnection();
            }
        }
    }
   
    private void saveFormDataToExcel() {
        boolean hasFormData = (jTextArea1.getText() != null && !jTextArea1.getText().trim().isEmpty())
                || !verbrauchList.isEmpty();
        if (!hasFormData) {
            JOptionPane.showMessageDialog(this,
                    "Keine Daten zum Speichern vorhanden.",
                    "Hinweis",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        String timestamp = now.format(SAVE_TIME_FORMAT);
        String userName = personUser != null && personUser.getName() != null && !personUser.getName().trim().isEmpty()
                ? personUser.getName().trim()
                : "unknown";
        String defaultName = "Verbrauchsbericht_" + now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".xls";

        File autoDirectory = new File(AUTO_SAVE_DIRECTORY);
        File autoTarget = new File(autoDirectory, defaultName);
        String autoSaveError = null;
        try {
            if (!autoDirectory.exists() && !autoDirectory.mkdirs()) {
                throw new IllegalStateException("Ordner konnte nicht erstellt werden: " + autoDirectory.getAbsolutePath());
            }
            writeVerbrauchReportExcel(autoTarget, timestamp, userName);
        } catch (Exception ex) {
            autoSaveError = ex.getMessage();
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Verbrauchsbericht als Excel speichern");
        fileChooser.setSelectedFile(new File(defaultName));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Excel 97-2003 (*.xls)", "xls"));

        int choice = fileChooser.showSaveDialog(this);
        if (choice != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File targetFile = fileChooser.getSelectedFile();
        if (!targetFile.getName().toLowerCase().endsWith(".xls")) {
            targetFile = new File(targetFile.getAbsolutePath() + ".xls");
        }

        try {
            writeVerbrauchReportExcel(targetFile, timestamp, userName);
            StringBuilder message = new StringBuilder();
            message.append("Excel-Datei wurde gespeichert:\n").append(targetFile.getAbsolutePath());
            message.append("\n\nAutomatisches Speichern nach:\n").append(autoTarget.getAbsolutePath());
            if (autoSaveError == null) {
                message.append("\nStatus: erfolgreich");
            } else {
                message.append("\nStatus: fehlgeschlagen - ").append(autoSaveError);
            }
            JOptionPane.showMessageDialog(this, message.toString(), "Speichern erfolgreich", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Fehler beim Speichern der Excel-Datei:\n" + ex.getMessage(),
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void writeVerbrauchReportExcel(File targetFile, String timestamp, String userName) throws Exception {
        try (Workbook workbook = new HSSFWorkbook();
             FileOutputStream out = new FileOutputStream(targetFile)) {

            Sheet sheet = workbook.createSheet("Verbrauch");
            int rowIndex = 0;

            rowIndex = createKeyValueRow(sheet, rowIndex, "Projekt", selectedText(jComboBox1));
            rowIndex = createKeyValueRow(sheet, rowIndex, "WBS", selectedText(jComboBox2));
            rowIndex = createKeyValueRow(sheet, rowIndex, "Suchtext", safeText(jTextField1.getText()));
            rowIndex = createKeyValueRow(sheet, rowIndex, "Material (aktuell)", selectedArtikelText());
            rowIndex = createKeyValueRow(sheet, rowIndex, "Menge (aktuell)", safeText(jFormattedTextField1.getText()));
            rowIndex = createKeyValueRow(sheet, rowIndex, "Einheit (aktuell)", safeText(jTextField2.getText()));
            rowIndex = createKeyValueRow(sheet, rowIndex, "Gespeichert am", timestamp);
            rowIndex = createKeyValueRow(sheet, rowIndex, "Benutzer", userName);

            rowIndex++;
            Row header = sheet.createRow(rowIndex++);
            header.createCell(0).setCellValue("Positionen");
            header.createCell(1).setCellValue("Eintrag");

            ArrayList<String> lines = new ArrayList<>();
            if (!verbrauchList.isEmpty()) {
                lines.addAll(verbrauchList);
            } else {
                String[] textLines = safeText(jTextArea1.getText()).split("\\R");
                for (String line : textLines) {
                    if (!line.trim().isEmpty()) {
                        lines.add(line);
                    }
                }
            }

            int pos = 1;
            for (String line : lines) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(pos++);
                row.createCell(1).setCellValue(line);
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            workbook.write(out);
        }
    }

    private int createKeyValueRow(Sheet sheet, int rowIndex, String key, String value) {
        Row row = sheet.createRow(rowIndex);
        Cell keyCell = row.createCell(0);
        keyCell.setCellValue(key);
        Cell valueCell = row.createCell(1);
        valueCell.setCellValue(value);
        return rowIndex + 1;
    }

    private String selectedText(javax.swing.JComboBox<?> comboBox) {
        Object item = comboBox.getSelectedItem();
        return item == null ? "" : item.toString();
    }

    private String selectedArtikelText() {
        Object selected = jComboBox3.getSelectedItem();
        if (selected instanceof Artikel artikel) {
            String de = artikel.getMaterial_DE() == null ? "" : artikel.getMaterial_DE();
            String en = artikel.getMaterial_EN() == null ? "" : artikel.getMaterial_EN();
            if (!de.isEmpty() || !en.isEmpty()) {
                return (de + " / " + en).trim();
            }
        }
        return "";
    }

    private String safeText(String text) {
        return text == null ? "" : text.trim();
    }

}
