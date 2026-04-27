/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.Logik;

/**
 *
 * @author lepeschko
 */
public class DBEditor extends javax.swing.JFrame{
    
    private final DBManager dbManager;
    
     public DBEditor() {
        dbManager = new DBManager();
        startComponents();
        this.setTitle("Datenbank Editorpanelle");
       
        loadMainDatabaseTablesInfo();
    }
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void startComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();

         setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(204, 255, 204));
        jPanel2.setAutoscrolls(true);

        jTextField1.setText("jTextField1");

        jButton1.setText("DB-Tabelle löschen");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField1)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel3.setAutoscrolls(true);

        jTextArea1.setBackground(new java.awt.Color(255, 255, 204));
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setEditable(false);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 204));

        jMenu1.setText("File");

        jMenuItem1.setText("neue Tabelle erstellen");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Spalte hinzufügen");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
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
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
    }                                    

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {                                      
       dbManager.deletTab(jTextField1.getText());
       loadMainDatabaseTablesInfo();
       // TODO add your handling code here:
    }                                     

    private void loadMainDatabaseTablesInfo() {
        jTextArea1.setText(dbManager.getMainDatabaseTablesInfo());
        jTextArea1.setCaretPosition(0);
    }
    
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {
        javax.swing.JTextField tableNameField = new javax.swing.JTextField(20);
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1, 0, 5));
        panel.add(new javax.swing.JLabel("Name der neuen Tabelle:"));
        panel.add(tableNameField);
        
        int result = javax.swing.JOptionPane.showConfirmDialog(
                this,
                panel,
                "Neue Tabelle erstellen",
                javax.swing.JOptionPane.OK_CANCEL_OPTION,
                javax.swing.JOptionPane.PLAIN_MESSAGE
        );
        
        if (result != javax.swing.JOptionPane.OK_OPTION) {
            return;
        }
        
        String tableName = tableNameField.getText() != null ? tableNameField.getText().trim() : "";
        if (tableName.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Bitte geben Sie einen Tabellennamen ein.",
                    "Hinweis",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        if (dbManager.createCustomTable(tableName)) {
            jTextField1.setText(tableName);
            loadMainDatabaseTablesInfo();
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Tabelle '" + tableName + "' wurde erstellt.",
                    "Information",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Tabelle '" + tableName + "' konnte nicht erstellt werden.",
                    "Fehler",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        java.util.ArrayList<String> tableNames = dbManager.getMainDatabaseTableNames();
        if (tableNames.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Es wurden keine Tabellen in der Hauptdatenbank gefunden.",
                    "Hinweis",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        javax.swing.JComboBox<String> tableBox = new javax.swing.JComboBox<>(tableNames.toArray(new String[0]));
        String currentTableName = jTextField1.getText() != null ? jTextField1.getText().trim() : "";
        if (!currentTableName.isEmpty()) {
            tableBox.setSelectedItem(currentTableName);
        }
        javax.swing.JTextField columnNameField = new javax.swing.JTextField(20);
        javax.swing.JComboBox<String> typeBox = new javax.swing.JComboBox<>(new String[] {
            "NVARCHAR", "VARCHAR", "INT", "BIGINT", "BIT", "DATE", "DATETIME", "FLOAT", "CHAR", "NCHAR"
        });
        javax.swing.JTextField sizeField = new javax.swing.JTextField("255", 10);
        
        javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(0, 1, 0, 5));
        panel.add(new javax.swing.JLabel("Tabelle:"));
        panel.add(tableBox);
        panel.add(new javax.swing.JLabel("Spaltenname:"));
        panel.add(columnNameField);
        panel.add(new javax.swing.JLabel("Datentyp:"));
        panel.add(typeBox);
        panel.add(new javax.swing.JLabel("Größe (nur für Texttypen):"));
        panel.add(sizeField);
        
        int result = javax.swing.JOptionPane.showConfirmDialog(
                this,
                panel,
                "Neue Spalte hinzufügen",
                javax.swing.JOptionPane.OK_CANCEL_OPTION,
                javax.swing.JOptionPane.PLAIN_MESSAGE
        );
        
        if (result != javax.swing.JOptionPane.OK_OPTION) {
            return;
        }
        
        String tableName = tableBox.getSelectedItem() != null ? tableBox.getSelectedItem().toString().trim() : "";
        String columnName = columnNameField.getText() != null ? columnNameField.getText().trim() : "";
        String dataType = typeBox.getSelectedItem() != null ? typeBox.getSelectedItem().toString() : "";
        Integer columnSize = null;
        
        if (tableName.isEmpty() || columnName.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Bitte geben Sie Tabellenname und Spaltenname ein.",
                    "Hinweis",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        
        String sizeText = sizeField.getText() != null ? sizeField.getText().trim() : "";
        if (!sizeText.isEmpty()) {
            try {
                columnSize = Integer.valueOf(sizeText);
            } catch (NumberFormatException ex) {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Die Spaltengröße muss eine ganze Zahl sein.",
                        "Hinweis",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                );
                return;
            }
        }
        
        if (dbManager.addColumnToTable(tableName, columnName, dataType, columnSize)) {
            jTextField1.setText(tableName);
            loadMainDatabaseTablesInfo();
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Spalte '" + columnName + "' wurde zu Tabelle '" + tableName + "' hinzugefügt.",
                    "Information",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Die Spalte konnte nicht hinzugefügt werden.",
                    "Fehler",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

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
            java.util.logging.Logger.getLogger(DBEditorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DBEditorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DBEditorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DBEditorForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DBEditorForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration                
}
