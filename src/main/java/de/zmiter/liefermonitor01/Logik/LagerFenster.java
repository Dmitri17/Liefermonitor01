/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.PersonUser;
import javax.swing.JOptionPane;

/**
 *
 * @author lepeschko
 */
public class LagerFenster extends javax.swing.JFrame {

    PersonUser person;
    DBManager dbManager;
    String titel = "Thyssen Schachtbau GmbH - Lager";

    /**
     * Creates new form LagerFenster
     */
    public LagerFenster(PersonUser person) {
        this.person = person;
        dbManager = new DBManager();
        this.setTitle(titel);
        startComponents();
        this.setSize(800, 600);
    }

    @SuppressWarnings("unchecked")
    public void startComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuEdit = new javax.swing.JMenu();
        jMenuItemMarkExp = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Lager Fenster");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        jPanel1.add(jLabel1, java.awt.BorderLayout.CENTER);

        jMenuEdit.setText("Edit");
        jMenuItemMarkExp.setText("Prefix exp. fuer Artikel ohne Lieferung");
        jMenuItemMarkExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markArtikelOhneLieferungActionPerformed(evt);
            }
        });
        jMenuEdit.add(jMenuItemMarkExp);
        jMenuBar1.add(jMenuEdit);
        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );

        pack();
    }

    private void markArtikelOhneLieferungActionPerformed(java.awt.event.ActionEvent evt) {
        int updated = dbManager.markArtikelOhneLieferungMitExpPrefix();
        JOptionPane.showMessageDialog(
                this,
                "Fertig. Markierte Artikel: " + updated,
                "Analyse Artikel/Lieferung",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemMarkExp;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
