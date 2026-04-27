/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.Lieferung;
import de.zmiter.liefermonitor01.DBObjekte.PersonUser;
import de.zmiter.liefermonitor01.Logik.DBManager;
import de.zmiter.liefermonitor01.Logik.LagerFenster;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Locale;
/**
 *
 * @author lepeschko
 */
public class LieferListFensterM extends javax.swing.JFrame{
    
      PersonUser person;
    DBManager dbManager;
    ArrayList<Lieferung> lieferList;
    ArrayList<String> projects;
    boolean filterKeinBanfNur = false;
    String titel ="Thyssen Schachtbau GmbH - List der Lieferungen"; 
    public LieferListFensterM(PersonUser person) {
      //  initComponents();
    this.person = person;
    
     System.out.println("!!!!!!!!!!!!in LieferListe  !!!!!!!!!!!!   Person: " + person.getName()+ "     "+   person.getZugangsGruppe());
       
         dbManager = new DBManager();
          this.setTitle(titel);  
          startComponents();
        
      
        this.setSize(1100, 700);
        
    }
    
     public void startComponents(){
        
     jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBoxNoBanf = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
         jMenuItem7 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();

           java.util.ArrayList<de.zmiter.liefermonitor01.DBObjekte.Lieferung> lieferungenList = new java.util.ArrayList<>();
           projects = new ArrayList<>();
        try {
            if (dbManager.isConnectionValid()) {
                lieferungenList = dbManager.getAllLieferungen();
                if (lieferungenList != null && !lieferungenList.isEmpty()) {
                    OfflineCache.saveLieferungen(lieferungenList);
                }
            } else {
                System.err.println("ERROR: connection failed");
            }
        } catch (Exception e) {
            System.err.println("ERROR by receiving Info from DB: " + e.getMessage());
            e.printStackTrace();
        }
        if (lieferungenList == null || lieferungenList.isEmpty()) {
            java.util.ArrayList<Lieferung> cached = OfflineCache.loadLieferungen();
            if (cached != null && !cached.isEmpty()) {
                lieferungenList = cached;
                long t = OfflineCache.getLastSyncTimeLieferungen();
                this.setTitle(titel + " — Offline (Stand: " + OfflineCache.formatSyncTime(t) + ")");
            }
        }
        for(Lieferung lif: lieferungenList){
            String proName = lif.getProjectName();
            for(String str: projects){
                if(!projects.contains(str)){
                    projects.add(str);
                }
            }
        }
       
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setFocusCycleRoot(true);
        jPanel1.setFocusTraversalPolicyProvider(true);
        jPanel1.setLayout(new java.awt.GridLayout(1, 4));

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setFocusCycleRoot(true);
        jPanel4.setFocusTraversalPolicyProvider(true);
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 5));

        jLabel1.setBackground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("           ");
        jLabel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, null, java.awt.Color.red, null, null));
        jLabel1.setOpaque(true);
        jPanel4.add(jLabel1);

        jCheckBox2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox2.setForeground(java.awt.Color.RED);
        jCheckBox2.setText("Status");
        jCheckBox2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyAllFilters();
            }
        });
        jPanel4.add(jCheckBox2);

        jCheckBox3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox3.setForeground(java.awt.Color.YELLOW.darker());
        jCheckBox3.setText("Status");
        jCheckBox3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyAllFilters();
            }
        });
        jPanel4.add(jCheckBox3);

        jButtonRefresh = new javax.swing.JButton();
        jButtonRefresh.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonRefresh.setText("Aktualisieren");
        jButtonRefresh.setMinimumSize(new java.awt.Dimension(100, 28));
        jButtonRefresh.setPreferredSize(new java.awt.Dimension(120, 28));
        jButtonRefresh.setToolTipText("Liste aus der Datenbank neu laden (z.B. nach Änderungen in BearbForm)");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshDataFromDb();
            }
        });
        jPanel4.add(jButtonRefresh);

        jCheckBoxNoBanf.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBoxNoBanf.setText("kein BANF Nr.");
        jCheckBoxNoBanf.setToolTipText("Zeigt nur Lieferungen ohne gueltige BANF Nr.");
        jCheckBoxNoBanf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterKeinBanfNur = jCheckBoxNoBanf.isSelected();
                applyAllFilters();
            }
        });
        jPanel4.add(jCheckBoxNoBanf);

        jPanel1.add(jPanel4);

        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setFocusCycleRoot(true);
        jPanel5.setFocusTraversalPolicyProvider(true);
        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jCheckBox1.setText("Activ");
        jCheckBox1.setActionCommand("");
        
        // Добавляем обработчик для фильтрации активных заказов (фильтры складываются)
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyAllFilters();
            }
        });
        
        jPanel5.add(jCheckBox1);
        
     
// логика для создания списка проектов из базы данных
        // Извлекаем уникальные проекты из списка Lieferungen
        java.util.Set<String> uniqueProjects = new java.util.HashSet<>();
        for (de.zmiter.liefermonitor01.DBObjekte.Lieferung lieferung : lieferungenList) {
            String projectName = lieferung.getProjectName();
            if (projectName != null && !projectName.trim().isEmpty() && !projectName.equals(" ")) {
                uniqueProjects.add(projectName.trim());
            }
        }
        
         projects.add("Alle Projekte");
        projects.addAll(uniqueProjects);
        
        // Заполняем выпадающий список
        String[] projectArray = projects.toArray(new String[0]);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(projectArray));
        jComboBox1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Baustelle", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        
        // Добавляем обработчик событий для фильтрации по проекту
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        
        jPanel5.add(jComboBox1);

        jPanel1.add(jPanel5);

        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel6.setAutoscrolls(true);
        jPanel6.setFocusCycleRoot(true);
        jPanel6.setFocusTraversalPolicyProvider(true);
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Suchtext", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel6.add(jTextField1);

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setText("Suchen");
        jButton4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton4MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton4MousePressed(evt);
            }
        });
        jPanel6.add(jButton4);

        jPanel1.add(jPanel6);

        jPanel2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setAutoscrolls(true);
        jPanel3.setFocusCycleRoot(true);

         jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

         
        
        // Устанавливаем lieferList для использования в updateLieferungenPanel()
        lieferList = lieferungenList;
        
        // Обновляем панель с начальными данными
        updateLieferungenPanel();
        jScrollPane1.setViewportView(jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenuBar1.setBackground(new java.awt.Color(204, 255, 204));
        jMenuBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jMenuBar1.setForeground(new java.awt.Color(0, 102, 0));

        jMenu1.setText("Anforderung");

        jMenuItem1.setText("Neue Anforderung erstellen");
        jMenuItem1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem1MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem1MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        
          jMenuItem8.setText("Export in Excel Format");
        jMenuItem8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem8MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem8MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem8);
        
        jMenuItem9.setText("Export in HTML Format");
        jMenuItem9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem9MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem9MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem9MousePressed(evt);
            }
        });
        jMenu1.add(jMenuItem9);

        jMenuBar1.add(jMenu1);

        
          jMenu4.setText("Logistik");

        jMenuItem10.setText("Lager");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLagerActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuItem11.setText("Transport");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        javax.swing.JMenuItem jMenuItemGantt = new javax.swing.JMenuItem();
        jMenuItemGantt.setText("Gantt Übersicht");
        jMenuItemGantt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ArrayList<Lieferung> list = (lieferList != null) ? new ArrayList<>(lieferList) : null;
                LieferGanttFenster gantt = new LieferGanttFenster(person, list);
                gantt.setLocation(100, 50);
                //gantt.setLocationRelativeTo(LieferListFensterM.this);
                gantt.setVisible(true);
            }
        });
        jMenu4.add(jMenuItemGantt);

        jMenuBar1.add(jMenu4);
        jMenu2.setText("DB-Optionen");

        jMenuItem2.setText("Benutzer verwalten");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("create new Table in DB");
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem3MousePressed(evt);
            }
        });
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem5.setText("Datenbank verwalten");
        jMenuItem5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem5MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem6.setText("Neue Lieferung eintragen");
        jMenuItem6.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
            public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
                jMenuItem6MenuKeyPressed(evt);
            }
            public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
            }
            public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
            }
        });
        jMenuItem6.addMenuDragMouseListener(new javax.swing.event.MenuDragMouseListener() {
            public void menuDragMouseDragged(javax.swing.event.MenuDragMouseEvent evt) {
            }
            public void menuDragMouseEntered(javax.swing.event.MenuDragMouseEvent evt) {
                jMenuItem6MenuDragMouseEntered(evt);
            }
            public void menuDragMouseExited(javax.swing.event.MenuDragMouseEvent evt) {
                jMenuItem6MenuDragMouseExited(evt);
            }
            public void menuDragMouseReleased(javax.swing.event.MenuDragMouseEvent evt) {
            }
        });
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);
        
        jMenuItem7.setText("Excel-Datei importieren");
        jMenuItem7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem7MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem7MousePressed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        javax.swing.JMenuItem jMenuItemIntranetHtml = new javax.swing.JMenuItem();
        jMenuItemIntranetHtml.setText("Lieferungen aus TS-Intranet-HTML ergänzen");
        jMenuItemIntranetHtml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enrichLieferungenFromIntranetHtmlActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemIntranetHtml);

        javax.swing.JMenuItem jMenuItemNormalizeProjektNummer = new javax.swing.JMenuItem();
        jMenuItemNormalizeProjektNummer.setText("Projektnummern bereinigen (nur Ziffern lassen)");
        jMenuItemNormalizeProjektNummer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeProjektNummernActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemNormalizeProjektNummer);

        javax.swing.JMenuItem jMenuItemPostImportSync = new javax.swing.JMenuItem();
        jMenuItemPostImportSync.setText("Post-Import Sync: lager aus Lieferung ergänzen");
        jMenuItemPostImportSync.setToolTipText("Aktualisiert alle Artikelliste in neuimportierten Bestellungen/Lieferungen (nach Import von Excel ausführen!)");
        jMenuItemPostImportSync.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postImportSyncLagerActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemPostImportSync);
        applyDbOptionsAccessControl();

        jMenuBar1.add(jMenu2);

        javax.swing.JMenu jMenuTabellenansicht = new javax.swing.JMenu();
        jMenuTabellenansicht.setText("Tabellenansicht");
        javax.swing.JMenuItem jMenuItemAlsTabelle = new javax.swing.JMenuItem();
        jMenuItemAlsTabelle.setText("Als Tabelle zeigen");
        jMenuItemAlsTabelle.setEnabled(hasAdminAccess());
        if (!hasAdminAccess()) {
            jMenuItemAlsTabelle.setToolTipText("Nur Admin darf die Tabellenansicht öffnen");
        }
        jMenuItemAlsTabelle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showLieferungenTableActionPerformed(evt);
            }
        });
        jMenuTabellenansicht.add(jMenuItemAlsTabelle);
        jMenuBar1.add(jMenuTabellenansicht);

        jMenu3.setText("Hilfe?");

        jMenuItem4.setText("Problem melden");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showProblemMeldenDialog();
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

      

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1022, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        
          jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    applyAllFilters();
                }
            }
        });
        
    }
     
     
     
     
     
    private void jMenuItem1MouseEntered(java.awt.event.MouseEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void jMenuItem1MouseExited(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jMenuItem1MousePressed(java.awt.event.MouseEvent evt) {                                        
      /* Lieferung lieferung1 = new Lieferung("neues Artikel", 0);
         lieferung1.setNeu(true);
        BearbForm bearbeitung = new BearbForm(dbManager, lieferung1 , person);
        // BearbFormCursor bearbeitungC = new BearbFormCursor();
       
       bearbeitung.setSize(800, 600);
        bearbeitung.setLocation(150, 200);*/ 
       // bearbeitung.setVisible(true);
       NeuAnforderung neuAnforderung = new NeuAnforderung(person);
       neuAnforderung.setVisible(true);
       
       
    }                                       

    private void jButton4MouseEntered(java.awt.event.MouseEvent evt) {                                      
   


// TODO add your handling code here:
    }                                     

    private void jButton4MouseExited(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
    }                                    

    private void jButton4MousePressed(java.awt.event.MouseEvent evt) {                                      
        applyAllFilters();
    }                                     

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {
        if (hasAdminAccess()) {
            PersonForm personForm = new PersonForm(dbManager, person);
            personForm.setVisible(true);
        } else {
            showAdminAccessDenied();
        }
    }

    private void jMenuItem3MousePressed(java.awt.event.MouseEvent evt) {                                        
       if (!hasAdminAccess()) {
           showAdminAccessDenied();
           return;
       }
       DBManager dbManager = new DBManager();
      // dbManager.createArtikelTable();
       
       //dbManager.createTable(jTextField1.getText());
    }                                       

    private void jMenuItem5MousePressed(java.awt.event.MouseEvent evt) {                                        
     /*     DBManager dbManager = new DBManager();
       dbManager.deletTab(jTextField1.getText());*/
     if (hasAdminAccess()) {
          DBEditor dbEditor = new DBEditor();
          dbEditor.setVisible(true);
     } else {
          showAdminAccessDenied();
     }
     
       
    }                                       

    private void jMenuItem6MenuDragMouseEntered(javax.swing.event.MenuDragMouseEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void jMenuItem6MenuDragMouseExited(javax.swing.event.MenuDragMouseEvent evt) {                                               
        // TODO add your handling code here:
    }                                              

    private void jMenuItem6MenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {                                          
     
       
    }                                         

    private void jMenuItem7MouseEntered(java.awt.event.MouseEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void jMenuItem7MouseExited(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jMenuItem7MousePressed(java.awt.event.MouseEvent evt) {                                        
        if (!hasAdminAccess()) {
            showAdminAccessDenied();
            return;
        }
     
        String path = dbManager.selectExcelFile();
        if (path == null || path.trim().isEmpty()) {
            return;
        }

        String preview = dbManager.previewProcessExcelFile(path, 20);
        javax.swing.JTextArea textArea = new javax.swing.JTextArea(preview);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0);
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(textArea);
        scroll.setPreferredSize(new java.awt.Dimension(760, 420));
        javax.swing.JOptionPane.showMessageDialog(
                this,
                scroll,
                "Excel Import - Dry-Run",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Dry-Run wurde angezeigt.\nExcel-Import jetzt ausführen?",
                "Excel Import",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );
        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }
        final javax.swing.JDialog progressDialog = createExcelImportProgressDialog();
        javax.swing.SwingWorker<String, Void> worker = new javax.swing.SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() {
                return dbManager.processExcelFileWithReport(path);
            }

            @Override
            protected void done() {
                progressDialog.dispose();
                try {
                    String importReport = get();
                    javax.swing.JTextArea resultArea = new javax.swing.JTextArea(importReport);
                    resultArea.setEditable(false);
                    resultArea.setLineWrap(true);
                    resultArea.setWrapStyleWord(true);
                    resultArea.setCaretPosition(0);
                    javax.swing.JScrollPane resultScroll = new javax.swing.JScrollPane(resultArea);
                    resultScroll.setPreferredSize(new java.awt.Dimension(760, 420));
                    javax.swing.JOptionPane.showMessageDialog(
                            LieferListFensterM.this,
                            resultScroll,
                            "Excel Import - Ergebnis",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE
                    );
                    refreshDataFromDb();
                } catch (Exception e) {
                    System.err.println("Fehler beim Excel-Import: " + e.getMessage());
                    e.printStackTrace();
                    javax.swing.JOptionPane.showMessageDialog(
                            LieferListFensterM.this,
                            "Fehler beim Excel-Import:\n" + e.getMessage(),
                            "Fehler",
                            javax.swing.JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        };
        worker.execute();
        progressDialog.setVisible(true);
        
    }                                       

    private javax.swing.JDialog createExcelImportProgressDialog() {
        javax.swing.JDialog dialog = new javax.swing.JDialog(this, "Excel-Import", false);
        dialog.setDefaultCloseOperation(javax.swing.JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setLayout(new java.awt.BorderLayout(10, 10));
        dialog.add(new javax.swing.JLabel("Importiere Excel-Daten. Bitte warten..."), java.awt.BorderLayout.NORTH);
        javax.swing.JProgressBar progressBar = new javax.swing.JProgressBar();
        progressBar.setIndeterminate(true);
        dialog.add(progressBar, java.awt.BorderLayout.CENTER);
        dialog.setSize(380, 110);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);
        return dialog;
    }

    private void enrichLieferungenFromIntranetHtmlActionPerformed(java.awt.event.ActionEvent evt) {
        if (!hasAdminAccess()) {
            showAdminAccessDenied();
            return;
        }
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Intranet HTML-Datei wählen");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "HTML Dateien (*.htm, *.html)", "htm", "html"));
        int result = fileChooser.showOpenDialog(this);
        if (result != javax.swing.JFileChooser.APPROVE_OPTION) {
            return;
        }
        String htmlPath = fileChooser.getSelectedFile().getAbsolutePath();

        String preview = dbManager.previewEnrichLieferungenFromIntranetHtml(htmlPath, 20);
        javax.swing.JTextArea textArea = new javax.swing.JTextArea(preview);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0);
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(textArea);
        scroll.setPreferredSize(new java.awt.Dimension(760, 420));
        javax.swing.JOptionPane.showMessageDialog(
                this,
                scroll,
                "Intranet HTML Dry-Run",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Dry-Run wurde angezeigt.\nIntranet-HTML Import jetzt ausführen?",
                "Intranet HTML Import",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );
        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        String importReport = dbManager.enrichLieferungenFromIntranetHtmlWithReport(htmlPath);
        javax.swing.JTextArea resultArea = new javax.swing.JTextArea(importReport);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        resultArea.setCaretPosition(0);
        javax.swing.JScrollPane resultScroll = new javax.swing.JScrollPane(resultArea);
        resultScroll.setPreferredSize(new java.awt.Dimension(760, 420));
        javax.swing.JOptionPane.showMessageDialog(
                this,
                resultScroll,
                "Intranet HTML Import - Ergebnis",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
        refreshDataFromDb();
    }

    private void normalizeProjektNummernActionPerformed(java.awt.event.ActionEvent evt) {
        if (!hasAdminAccess()) {
            showAdminAccessDenied();
            return;
        }
        String preview = dbManager.previewNormalizeProjectNumbersOnlyDigits(20);
        javax.swing.JTextArea textArea = new javax.swing.JTextArea(preview);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0);
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(textArea);
        scroll.setPreferredSize(new java.awt.Dimension(760, 420));
        javax.swing.JOptionPane.showMessageDialog(
                this,
                scroll,
                "Projektnummern bereinigen - Dry-Run",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Dry-Run wurde angezeigt.\nBereinigung jetzt ausführen?",
                "Projektnummern bereinigen",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );
        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        int updated = dbManager.normalizeProjectNumbersOnlyDigits();
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Bereinigung abgeschlossen. Aktualisierte Lieferungen: " + updated,
                "Projektnummern bereinigen",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
        refreshDataFromDb();
    }

    private void postImportSyncLagerActionPerformed(java.awt.event.ActionEvent evt) {
        if (!hasAdminAccess()) {
            showAdminAccessDenied();
            return;
        }

        String preview = dbManager.previewPostImportSyncLagerFromLieferung();
        javax.swing.JTextArea textArea = new javax.swing.JTextArea(preview);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0);
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(textArea);
        scroll.setPreferredSize(new java.awt.Dimension(760, 420));
        javax.swing.JOptionPane.showMessageDialog(
                this,
                scroll,
                "Post-Import Sync (Dry-Run)",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "Dry-Run wurde angezeigt.\nPost-Import Sync jetzt ausführen?",
                "Post-Import Sync",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.QUESTION_MESSAGE
        );
        if (confirm != javax.swing.JOptionPane.YES_OPTION) {
            return;
        }

        int updated = dbManager.postImportSyncLagerFromLieferung();
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Post-Import Sync abgeschlossen. Aktualisierte Lager-Zeilen: " + updated,
                "Post-Import Sync",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
        refreshDataFromDb();
    }

    private void jMenuItem8MouseEntered(java.awt.event.MouseEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {
        if (!hasAdminAccess()) {
            showAdminAccessDenied();
            return;
        }
        NeuAnforderung neuAnforderung = new NeuAnforderung(person);
        neuAnforderung.setVisible(true);
    }

    private boolean hasAdminAccess() {
        return person != null
                && person.getZugangsGruppe() != null
                && person.getZugangsGruppe().equalsIgnoreCase("admin");
    }

    private void applyDbOptionsAccessControl() {
        boolean adminAccess = hasAdminAccess();
        jMenu2.setEnabled(adminAccess);
        jMenu2.setToolTipText(adminAccess ? null : "Nur Admin darf DB-Optionen verwenden");
        for (java.awt.Component component : jMenu2.getMenuComponents()) {
            if (component instanceof javax.swing.JMenuItem menuItem) {
                menuItem.setEnabled(adminAccess);
                menuItem.setToolTipText(adminAccess ? null : "Nur Admin");
            }
        }
    }

    private void showAdminAccessDenied() {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Zugriff verweigert. Nur Admin darf diese Aktion ausführen.",
                "Keine Berechtigung",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
    }

    private void jMenuItem8MouseExited(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jMenuItem8MousePressed(java.awt.event.MouseEvent evt) {                                        
         System.out.println("Menu 8 pressed!");
       dbManager.exportListToXls(lieferList);
       
    }                                       

    private void jMenuItem9MouseEntered(java.awt.event.MouseEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void jMenuItem9MouseExited(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jMenuItem9MousePressed(java.awt.event.MouseEvent evt) {                                        
        try {
            // Генерируем HTML страницу
            String htmlContent = generateHTMLPage();
            
            // Создаем временный файл
            java.io.File tempFile = java.io.File.createTempFile("Lieferungen_Export_", ".html");
            tempFile.deleteOnExit(); // Файл будет удален при выходе из программы
            
            // Сохраняем HTML во временный файл
            try (java.io.PrintWriter writer = new java.io.PrintWriter(tempFile, "UTF-8")) {
                writer.print(htmlContent);
            }
            
            // Открываем HTML файл в браузере по умолчанию
            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
                if (desktop.isSupported(java.awt.Desktop.Action.BROWSE)) {
                    desktop.open(tempFile);
                   // System.out.println("HTML Seite wurde im Browser geöffnet: " + tempFile.getAbsolutePath());
                } else {
                    javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Browser-Aktion wird nicht unterstützt.",
                        "Fehler",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Desktop-Aktionen werden nicht unterstützt.",
                    "Fehler",
                    javax.swing.JOptionPane.ERROR_MESSAGE
                );
            }
            
        } catch (Exception e) {
          //  System.err.println("Fehler beim Öffnen der HTML Seite: " + e.getMessage());
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Fehler beim Öffnen der HTML Seite:\n" + e.getMessage(),
                "Fehler",
                javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }                                       

    /**
     * Метод для обновления списка Lieferungen на панели
     */
    public void refreshLieferungenList() {
        // Очищаем панель от существующих кнопок Lieferung
        java.awt.Component[] components = jPanel2.getComponents();
        for (java.awt.Component component : components) {
            if (component instanceof de.zmiter.liefermonitor01.DBObjekte.Lieferung) {
                jPanel2.remove(component);
            }
        }
        
        // Создаем новый экземпляр DBManager
       // DBManager dbManager = new DBManager();
        
        // Получаем обновленный список Lieferungen aus DB oder aus Offline-Cache
        java.util.ArrayList<de.zmiter.liefermonitor01.DBObjekte.Lieferung> lieferungenList = new java.util.ArrayList<>();
        boolean fromCache = false;
        try {
            if (dbManager.isConnectionValid()) {
                lieferungenList = dbManager.getAllLieferungen();
                if (lieferungenList != null && !lieferungenList.isEmpty()) {
                    OfflineCache.saveLieferungen(lieferungenList);
                    System.out.println("Reloaded: gekommen sind " + lieferungenList.size() + " Lieferungen aus der DB");
                }
            } else {
                System.err.println("ERROR: keine Verbindung mit der DB");
            }
        } catch (Exception e) {
            System.err.println("ERROR beim Reloading der Dateien aus DB: " + e.getMessage());
            e.printStackTrace();
        }
        if (lieferungenList == null || lieferungenList.isEmpty()) {
            java.util.ArrayList<Lieferung> cached = OfflineCache.loadLieferungen();
            if (cached != null && !cached.isEmpty()) {
                lieferungenList = cached;
                lieferList = lieferungenList;
                fromCache = true;
                long t = OfflineCache.getLastSyncTimeLieferungen();
                setTitle(titel + " — Offline (Stand: " + OfflineCache.formatSyncTime(t) + ")");
                javax.swing.JOptionPane.showMessageDialog(this,
                    "Keine Verbindung zur Datenbank. Anzeige aus lokalem Cache (Stand: " + OfflineCache.formatSyncTime(t) + ").",
                    "Offline-Modus", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            lieferList = lieferungenList;
        }
        
        // Добавляем обновленные экземпляры Lieferung на панель
        for (de.zmiter.liefermonitor01.DBObjekte.Lieferung lieferung : lieferungenList) {
            // Настраиваем внешний вид кнопки Lieferung
            lieferung.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 60));
            lieferung.setPreferredSize(new java.awt.Dimension(400, 60));
            lieferung.setMinimumSize(new java.awt.Dimension(200, 40));
            lieferung.setText(buildLieferungButtonText(lieferung));
            
            // Добавляем обработчик событий для кнопки Lieferung
            lieferung.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // Открываем форму редактирования для выбранной Lieferung
                   
                   // System.out.println("Open a Lieferung with a Button!!! " + lieferung.getBeschreibung()); 
                    BearbForm bearbeitung = new BearbForm(dbManager, lieferung, person);
                   // BearbFormCursor bearbeitungC = new BearbFormCursor();
                    bearbeitung.setLocation(50, 50);
                   // bearbeitung.setVisible(true);
                   // bearbeitungC.setVisible(true);
                }
            });
            
            // Добавляем отступ между кнопками
            jPanel2.add(javax.swing.Box.createVerticalStrut(5));
            
            // Добавляем кнопку Lieferung на панель
            jPanel2.add(lieferung);
        }
        
        // Добавляем дополнительный отступ в конце
        jPanel2.add(javax.swing.Box.createVerticalStrut(10));
        
        // Перерисовываем панель
        jPanel2.revalidate();
        jPanel2.repaint();
    }
    
    /**
     * @param args the command line arguments
     */
  

    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBoxNoBanf;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
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
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration                   
    
    /**
     * Обновляет панель jPanel3 с текущим списком Lieferungen
     */
    Color checkStatus(Lieferung lieferung){ 
        Color statusColor = Color.GRAY;
       // System.out.println("WUNSCHDATUM in CHECKSTATUS: "+ lieferung.getWunschLieferDatum());
      //   System.out.println("ANFORDDATUM  in CHECKSTATUS: "+ lieferung.getAnforderungDatum());
      
        if(lieferung.getStatusBestellung().equalsIgnoreCase("Red")){
            statusColor = Color.RED;
        }else if(lieferung.getStatusBestellung().equalsIgnoreCase("Yellow")){
            statusColor = Color.yellow;
        }
       // System.out.println("COLOR STATUS   :-- " + lieferung.getStatusBestellung());
        
        return statusColor;
    }
    
    private void updateLieferungenPanel() {
        // Очищаем панель
        jPanel3.removeAll();
        
        // Добавляем все Lieferungen из текущего списка
        for(Lieferung l: lieferList){
            // Настраиваем внешний вид кнопки Lieferung
            l.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 60));
            l.setPreferredSize(new java.awt.Dimension(400, 60));
            l.setMinimumSize(new java.awt.Dimension(200, 40));
           // l.setBackground(checkStatus(l));
           l.setBackground(l.getColorDirekt());  // обращаемся за цветом в метод класса Lieferung
            l.setText(buildLieferungButtonText(l));
           
            l.setNeu(false);
            
            // Добавляем обработчик событий для кнопки Lieferung
            l.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    // Открываем форму редактирования для выбранной Lieferung
                   /* BearbForm bearbeitung = new BearbForm(dbManager, l, person);
                    bearbeitung.setLocation(150, 200);
                    bearbeitung.setVisible(true);*/
                     BearbFrame01 bearbeitung01 = new BearbFrame01(dbManager, l, person);
                    bearbeitung01.setLocation(400, 10);
                    bearbeitung01.setVisible(true);
                }
            });
            
            jPanel3.add(l);
        }
        
        // Обновляем отображение
        jPanel3.revalidate();
        jPanel3.repaint();
        jScrollPane1.revalidate();
        jScrollPane1.repaint();
    }

    private String buildLieferungButtonText(Lieferung l) {
        String anforderung = trimOrDash(l.getAnfordNummer());
        String banf = trimOrDash(l.getAgrNummer());
        String bestell = trimOrDash(l.getBestellNummer());
        String bestelltAm = trimOrDash(l.getBestelltAm());
        String lieferant = trimOrDash(l.getLieferantName());
        String beschreibung = trimOrEmpty(l.getBeschreibung());
        if (beschreibung.length() > 90) {
            beschreibung = beschreibung.substring(0, 90);
        }
        return " Anf.: " + anforderung
                + " | BANF: " + banf
                + " | Bestell: " + bestell
                + " | bestellt am: " + bestelltAm
                + " | Lieferant: " + lieferant
                + " (" + beschreibung + ")";
    }

    private String trimOrDash(String value) {
        String v = trimOrEmpty(value);
        return v.isEmpty() ? "-" : v;
    }

    private String trimOrEmpty(String value) {
        return value == null ? "" : value.trim();
    }

    private boolean hasValidBanfNummer(String agrNummer) {
        String v = trimOrEmpty(agrNummer);
        if (v.isEmpty()) {
            return false;
        }
        if ("__||".equals(v) || "-".equals(v) || "NA".equalsIgnoreCase(v) || "N/A".equalsIgnoreCase(v)) {
            return false;
        }
        return v.matches(".*\\d.*");
    }
    
    /**
     * Обработчик события выбора проекта в выпадающем списке (фильтры складываются)
     */
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        applyAllFilters();
    }
    
    /**
     * Применяет все фильтры вместе (складывает условия): Suchtext, Activ, Baustelle, Status Red.
     * Сначала берётся база по Suchtext, затем по Activ, по проекту, при включённом jCheckBox2 — только getStatusBestellung() "Red" (berechnet aus Datum).
     */
    private void applyAllFilters() {
        String searchText = jTextField1.getText().trim();
        
        // 1) База: по Suchtext или все Lieferungen
        ArrayList<Lieferung> list;
        if (!searchText.isEmpty()) {
            list = dbManager.seachInTabLief(searchText);
        } else {
            list = dbManager.getAllLieferungen();
        }
        
        // 2) Фильтр Activ: только активные
        if (jCheckBox1.isSelected()) {
            ArrayList<Lieferung> activeList = new ArrayList<>();
            for (Lieferung lieferung : list) {
                if (lieferung.isAktiv()) {
                    activeList.add(lieferung);
                }
            }
            list = activeList;
        }
        
        // 3) Фильтр Baustelle: только выбранный проект
        String selectedProject = (String) jComboBox1.getSelectedItem();
        if (selectedProject != null && !selectedProject.isEmpty() && !"Alle Projekte".equals(selectedProject)) {
            ArrayList<Lieferung> projectList = new ArrayList<>();
            for (Lieferung lieferung : list) {
                if (selectedProject.equals(lieferung.getProjectName())) {
                    projectList.add(lieferung);
                }
            }
            list = projectList;
        }
        
        // 4) Фильтр Status: Red (jCheckBox2) und/oder Yellow (jCheckBox3), außer Lieferung mit gesetztem Wareneingang
        if (jCheckBox2.isSelected() || jCheckBox3.isSelected()) {
            ArrayList<Lieferung> statusList = new ArrayList<>();
            boolean filterRed = jCheckBox2.isSelected();
            boolean filterYellow = jCheckBox3.isSelected();
            for (Lieferung lieferung : list) {
                String status = lieferung.getStatusBestellung();
                String wareneingangBS = lieferung.getWunschterminWareneingang();
                boolean hasWareneingang = (wareneingangBS != null && !wareneingangBS.trim().isEmpty());
                if (hasWareneingang) continue;
                if (status == null) continue;
                String s = status.trim();
                if (filterRed && "Red".equalsIgnoreCase(s)) statusList.add(lieferung);
                else if (filterYellow && "Yellow".equalsIgnoreCase(s)) statusList.add(lieferung);
            }
            list = statusList;
        }

        // 5) Filter: nur Lieferungen ohne gueltige BANF Nr.
        if (filterKeinBanfNur) {
            ArrayList<Lieferung> noBanfList = new ArrayList<>();
            for (Lieferung lieferung : list) {
                if (!hasValidBanfNummer(lieferung.getAgrNummer())) {
                    noBanfList.add(lieferung);
                }
            }
            list = noBanfList;
        }
        
        lieferList = list;
        updateLieferungenPanel();
    }
    
    /**
     * Lädt die Lieferungen-Liste neu aus der Datenbank und aktualisiert die Anzeige.
     * Berücksichtigt die aktuellen Filter (Suchtext, Activ, Baustelle, Status Red).
     */
    private void refreshDataFromDb() {
        applyAllFilters();
    }

    /**
     * Метод для фильтрации и отображения только активных Lieferungen (aktiv = true).
     * @deprecated Используйте {@link #applyAllFilters()} для складывания фильтров.
     */
    public void filterAndShowActiveLieferungen() {
        applyAllFilters();
    }
    
    /**
     * Метод для отображения диалогового окна отправки сообщения о проблеме
     */
    public void showProblemMeldenDialog() {
        // Создаем диалоговое окно
        javax.swing.JDialog dialog = new javax.swing.JDialog(this, "Problem melden", true);
        dialog.setLayout(new java.awt.BorderLayout(10, 10));
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(this);
        
        // Создаем панель с инструкциями
        javax.swing.JPanel topPanel = new javax.swing.JPanel(new java.awt.BorderLayout());
        topPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        javax.swing.JLabel instructionLabel = new javax.swing.JLabel(
            "<html><b>Bitte beschreiben Sie Ihr Problem:</b><br>" +
            "Ihre Nachricht wird an den Support gesendet.</html>"
        );
        instructionLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        topPanel.add(instructionLabel, java.awt.BorderLayout.NORTH);
        
        // Добавляем поля для имени пользователя, email и пароля
        javax.swing.JPanel userInfoPanel = new javax.swing.JPanel(new java.awt.GridLayout(3, 2, 5, 5));
        userInfoPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        javax.swing.JLabel nameLabel = new javax.swing.JLabel("Ihr Name:");
        javax.swing.JTextField nameField = new javax.swing.JTextField(person != null ? person.getName() : "");
        
        javax.swing.JLabel emailLabel = new javax.swing.JLabel("Ihre E-Mail:");
        javax.swing.JTextField emailField = new javax.swing.JTextField("");
        
        javax.swing.JLabel passwordLabel = new javax.swing.JLabel("E-Mail Passwort:");
        javax.swing.JPasswordField passwordField = new javax.swing.JPasswordField("");
        
        userInfoPanel.add(nameLabel);
        userInfoPanel.add(nameField);
        userInfoPanel.add(emailLabel);
        userInfoPanel.add(emailField);
        userInfoPanel.add(passwordLabel);
        userInfoPanel.add(passwordField);
        
        topPanel.add(userInfoPanel, java.awt.BorderLayout.CENTER);
        
        // Создаем текстовое поле для сообщения
        javax.swing.JTextArea messageArea = new javax.swing.JTextArea();
        messageArea.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(java.awt.Color.GRAY),
            javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
        
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(messageArea);
        scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 10, 10));
        
        // Создаем панель с кнопками
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        buttonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        javax.swing.JButton sendButton = new javax.swing.JButton("Senden");
        sendButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
        sendButton.setBackground(new java.awt.Color(100, 200, 100));
        
        javax.swing.JButton cancelButton = new javax.swing.JButton("Abbrechen");
        cancelButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
        
        // Обработчик кнопки "Senden"
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String message = messageArea.getText().trim();
                String userName = nameField.getText().trim();
                String userEmail = emailField.getText().trim();
                String userPassword = new String(passwordField.getPassword());
                
                // Валидация полей
                if (message.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(
                        dialog,
                        "Bitte geben Sie eine Nachricht ein.",
                        "Warnung",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                
                if (userEmail.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(
                        dialog,
                        "Bitte geben Sie Ihre E-Mail ein.",
                        "Warnung",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                
                if (userPassword.isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(
                        dialog,
                        "Bitte geben Sie Ihr E-Mail Passwort ein.",
                        "Warnung",
                        javax.swing.JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                
                // Отправляем сообщение
                boolean success = sendProblemEmail(userName, userEmail, userPassword, message);
                
                if (success) {
                    javax.swing.JOptionPane.showMessageDialog(
                        dialog,
                        "Ihre Nachricht wurde erfolgreich gesendet.\nVielen Dank!",
                        "Erfolg",
                        javax.swing.JOptionPane.INFORMATION_MESSAGE
                    );
                    dialog.dispose();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(
                        dialog,
                        "Fehler beim Senden der Nachricht.\nBitte versuchen Sie es später erneut.",
                        "Fehler",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
        
        // Обработчик кнопки "Abbrechen"
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dialog.dispose();
            }
        });
        
        buttonPanel.add(sendButton);
        buttonPanel.add(cancelButton);
        
        // Добавляем компоненты в диалог
        dialog.add(topPanel, java.awt.BorderLayout.NORTH);
        dialog.add(scrollPane, java.awt.BorderLayout.CENTER);
        dialog.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        // Показываем диалог
        dialog.setVisible(true);
    }
    
    /**
     * Метод для отправки email с сообщением о проблеме
     * @param userName имя пользователя
     * @param userEmail email пользователя (из формы)
     * @param userPassword пароль email (из формы)
     * @param message текст сообщения
     * @return true если отправка успешна
     */
    private boolean sendProblemEmail(String userName, String userEmail, String userPassword, String message) {
        // Email адрес получателя
        final String RECIPIENT_EMAIL = "lepeschko.dmitri@ts-gruppe.com";
        
        // SMTP настройки - уточните в IT отделе для вашего почтового сервера
        final String SMTP_HOST = "smtp.gmail.com";  // или smtp.office365.com для Outlook
        final String SMTP_PORT = "587";
        
        // Используем email и пароль из формы
        final String SENDER_EMAIL = userEmail;
        final String SENDER_PASSWORD = userPassword;
        
        try {
            // Настройка свойств для SMTP
            java.util.Properties properties = new java.util.Properties();
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.host", SMTP_HOST);
            properties.put("mail.smtp.port", SMTP_PORT);
            
            // Создание сессии с аутентификацией
            javax.mail.Session session = javax.mail.Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new javax.mail.PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
                    }
                });
            
            // Создание сообщения
            javax.mail.Message emailMessage = new javax.mail.internet.MimeMessage(session);
            emailMessage.setFrom(new javax.mail.internet.InternetAddress(SENDER_EMAIL));
            emailMessage.setRecipients(
                javax.mail.Message.RecipientType.TO,
                javax.mail.internet.InternetAddress.parse(RECIPIENT_EMAIL)
            );
            emailMessage.setSubject("Problem Meldung - Liefermonitor");
            
            // Формирование текста сообщения
            String emailBody = "Problem Meldung von Liefermonitor\n\n" +
                              "Von: " + userName + "\n" +
                              "E-Mail: " + userEmail + "\n" +
                              "Datum: " + new java.util.Date() + "\n\n" +
                              "Nachricht:\n" + message;
            
            emailMessage.setText(emailBody);
            
            // Отправка сообщения
            javax.mail.Transport.send(emailMessage);
            
            System.out.println("Problem-Email erfolgreich gesendet an: " + RECIPIENT_EMAIL);
            return true;
            
        } catch (javax.mail.MessagingException e) {
            System.err.println("Fehler beim Senden der Email: " + e.getMessage());
            e.printStackTrace();
            
            // Альтернативный вариант - сохранение в файл
            return saveProblemToFile(userName, userEmail, message);
        }
    }
    
    /**
     * Обработчик события нажатия кнопки Lager
     */
    private void showLieferungenTableActionPerformed(java.awt.event.ActionEvent evt) {
        if (!hasAdminAccess()) {
            showAdminAccessDenied();
            return;
        }
        java.util.ArrayList<Lieferung> source = (lieferList != null) ? lieferList : new java.util.ArrayList<>();
        if (source.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Keine Lieferungen geladen.",
                    "Tabellenansicht",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }
        LieferungTableView tableView = new LieferungTableView(this, source);
        tableView.setVisible(true);
    }

    private void jButtonLagerActionPerformed(java.awt.event.ActionEvent evt) {
       // LagerFenster lagerFenster = new LagerFenster(person);
      //  lagerFenster.setLocationRelativeTo(this);
      //  lagerFenster.setVisible(true);
        ArtikelListe artList = new ArtikelListe();
        artList.person = person;
        artList.setLocationRelativeTo(this);
        artList.setVisible(true);
    }

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {
        TransportFormM transportForm = new TransportFormM();
        transportForm.setLocationRelativeTo(this);
        transportForm.setVisible(true);
    }
    
    /**
     * Создает HTML страницу, соответствующую по содержанию и структуре LieferListFenster
     * @return строка с HTML кодом
     */
    public String generateHTMLPage() {
        StringBuilder html = new StringBuilder();
        
        // HTML заголовок
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"de\">\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>").append(titel).append("</title>\n");
        html.append("    <style>\n");
        html.append("        body {\n");
        html.append("            font-family: 'Segoe UI', Arial, sans-serif;\n");
        html.append("            margin: 20px;\n");
        html.append("            background-color: #f5f5f5;\n");
        html.append("        }\n");
        html.append("        .header {\n");
        html.append("            background-color: #ccffcc;\n");
        html.append("            padding: 15px;\n");
        html.append("            border: 2px solid #999;\n");
        html.append("            border-radius: 5px;\n");
        html.append("            margin-bottom: 20px;\n");
        html.append("        }\n");
        html.append("        h1 {\n");
        html.append("            margin: 0;\n");
        html.append("            color: #006600;\n");
        html.append("            font-size: 24px;\n");
        html.append("        }\n");
        html.append("        .info {\n");
        html.append("            margin-top: 10px;\n");
        html.append("            font-size: 14px;\n");
        html.append("            color: #333;\n");
        html.append("        }\n");
        html.append("        table {\n");
        html.append("            width: 100%;\n");
        html.append("            border-collapse: collapse;\n");
        html.append("            background-color: white;\n");
        html.append("            box-shadow: 0 2px 4px rgba(0,0,0,0.1);\n");
        html.append("        }\n");
        html.append("        th {\n");
        html.append("            background-color: #ccffcc;\n");
        html.append("            padding: 12px;\n");
        html.append("            text-align: left;\n");
        html.append("            border: 1px solid #999;\n");
        html.append("            font-weight: bold;\n");
        html.append("        }\n");
        html.append("        td {\n");
        html.append("            padding: 10px;\n");
        html.append("            border: 1px solid #ddd;\n");
        html.append("        }\n");
        html.append("        tr:hover {\n");
        html.append("            background-color: #f0f0f0;\n");
        html.append("        }\n");
        html.append("        .status-red {\n");
        html.append("            background-color: #f23d3d;\n");
        html.append("            color: white;\n");
        html.append("        }\n");
        html.append("        .status-yellow {\n");
        html.append("            background-color: #f0f6a3;\n");
        html.append("        }\n");
        html.append("        .status-green {\n");
        html.append("            background-color: #92ffaf;\n");
        html.append("        }\n");
        html.append("        .status-grey {\n");
        html.append("            background-color: #a5a598;\n");
        html.append("            color: white;\n");
        html.append("        }\n");
        html.append("        .description {\n");
        html.append("            max-width: 400px;\n");
        html.append("            overflow: hidden;\n");
        html.append("            text-overflow: ellipsis;\n");
        html.append("        }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        
        // Заголовок страницы
        html.append("    <div class=\"header\">\n");
        html.append("        <h1>").append(titel).append("</h1>\n");
        html.append("        <div class=\"info\">\n");
        html.append("            <strong>Erstellt am:</strong> ").append(new java.util.Date()).append("<br>\n");
        if (person != null) {
            html.append("            <strong>Benutzer:</strong> ").append(escapeHtml(person.getName())).append("<br>\n");
        }
        html.append("            <strong>Anzahl Lieferungen:</strong> ").append(lieferList != null ? lieferList.size() : 0).append("<br>\n");
        
        // Добавляем информацию о примененных фильтрах
        boolean hasFilters = false;
        StringBuilder filtersInfo = new StringBuilder();
        filtersInfo.append("            <div style=\"margin-top: 10px; padding: 10px; background-color: #e8f5e9; border-radius: 3px;\">\n");
        filtersInfo.append("                <strong>Angewendete Filter:</strong><br>\n");
        
        try {
            if (jCheckBox1 != null && jCheckBox1.isSelected()) {
                filtersInfo.append("                • <strong>Active Bestellungen:</strong> Aktiv<br>\n");
                hasFilters = true;
            }
            if (jComboBox1 != null && jComboBox1.getSelectedItem() != null) {
                String selectedProject = (String) jComboBox1.getSelectedItem();
                if (selectedProject != null && !selectedProject.equals("Alle Projekte")) {
                    filtersInfo.append("                • <strong>Projekt:</strong> ").append(escapeHtml(selectedProject)).append("<br>\n");
                    hasFilters = true;
                }
            }
            if (jTextField1 != null && !jTextField1.getText().trim().isEmpty()) {
                filtersInfo.append("                • <strong>Suchtext:</strong> ").append(escapeHtml(jTextField1.getText().trim())).append("<br>\n");
                hasFilters = true;
            }
        } catch (Exception e) {
            // Если компоненты еще не инициализированы, просто пропускаем информацию о фильтрах
            System.out.println("Hinweis: Filter-Informationen konnten nicht abgerufen werden: " + e.getMessage());
        }
        
        if (!hasFilters) {
            filtersInfo.append("                Keine Filter angewendet<br>\n");
        }
        filtersInfo.append("            </div>\n");
        html.append(filtersInfo.toString());
        html.append("        </div>\n");
        html.append("    </div>\n");
        
        // Таблица с данными
        html.append("    <table>\n");
        html.append("        <thead>\n");
        html.append("            <tr>\n");
        html.append("                <th>Status</th>\n");
        html.append("                <th>Anforderung Nr.</th>\n");
        html.append("                <th>Beschreibung</th>\n");
        html.append("                <th>Projekt</th>\n");
        html.append("                <th>Menge</th>\n");
        html.append("                <th>Anforderung Datum</th>\n");
        html.append("                <th>Wunsch Lieferdatum</th>\n");
        html.append("                <th>Person</th>\n");
        html.append("                <th>Aktiv</th>\n");
        html.append("                <th>Bestellnummer</th>\n");
        html.append("                <th>Lieferant</th>\n");
        html.append("            </tr>\n");
        html.append("        </thead>\n");
        html.append("        <tbody>\n");
        
        // Добавляем строки для каждой Lieferung
        if (lieferList != null && !lieferList.isEmpty()) {
            for (Lieferung lieferung : lieferList) {
                String statusClass = getStatusClass(lieferung);
                String beschreibung = lieferung.getBeschreibung();
                if (beschreibung != null && beschreibung.length() > 90) {
                    beschreibung = beschreibung.substring(0, 90) + "...";
                }
                
                html.append("            <tr class=\"").append(statusClass).append("\">\n");
                html.append("                <td>").append(escapeHtml(lieferung.getStatusBestellung())).append("</td>\n");
                html.append("                <td>").append(escapeHtml(lieferung.getAnfordNummer())).append("</td>\n");
                html.append("                <td class=\"description\">").append(escapeHtml(beschreibung != null ? beschreibung : "")).append("</td>\n");
                html.append("                <td>").append(escapeHtml(lieferung.getProjectName() != null ? lieferung.getProjectName() : "")).append("</td>\n");
                html.append("                <td>").append(lieferung.getMenge()).append(" ").append(escapeHtml(lieferung.getEinhMenge() != null ? lieferung.getEinhMenge() : "")).append("</td>\n");
                html.append("                <td>").append(escapeHtml(lieferung.getAnforderungDatum() != null ? lieferung.getAnforderungDatum() : "")).append("</td>\n");
                html.append("                <td>").append(escapeHtml(lieferung.getWunschLieferDatum() != null ? lieferung.getWunschLieferDatum() : "")).append("</td>\n");
                html.append("                <td>").append(escapeHtml(lieferung.getAnfordPerson() != null ? lieferung.getAnfordPerson() : "")).append("</td>\n");
                html.append("                <td>").append(lieferung.isAktiv() ? "Ja" : "Nein").append("</td>\n");
                html.append("                <td>").append(escapeHtml(lieferung.getBestellNummer() != null ? lieferung.getBestellNummer() : "")).append("</td>\n");
                html.append("                <td>").append(escapeHtml(lieferung.getLieferantName() != null ? lieferung.getLieferantName() : "")).append("</td>\n");
                html.append("            </tr>\n");
            }
        } else {
            html.append("            <tr>\n");
            html.append("                <td colspan=\"11\" style=\"text-align: center; padding: 20px;\">Keine Lieferungen gefunden</td>\n");
            html.append("            </tr>\n");
        }
        
        html.append("        </tbody>\n");
        html.append("    </table>\n");
        html.append("</body>\n");
        html.append("</html>\n");
        
        return html.toString();
    }
    
    /**
     * Получает CSS класс для статуса Lieferung
     */
    private String getStatusClass(Lieferung lieferung) {
        String status = lieferung.getStatusBestellung();
        if (status == null) {
            return "status-grey";
        }
        switch (status.toLowerCase()) {
            case "red":
                return "status-red";
            case "yellow":
                return "status-yellow";
            case "green":
                return "status-green";
            default:
                return "status-grey";
        }
    }
    
    /**
     * Экранирует HTML специальные символы
     */
    private String escapeHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }
    
    /**
     * Сохраняет HTML страницу в файл
     * @param filePath путь к файлу для сохранения
     * @return true если сохранение успешно, false в случае ошибки
     */
    public boolean saveHTMLPageToFile(String filePath) {
        try {
            String htmlContent = generateHTMLPage();
            java.io.File file = new java.io.File(filePath);
            
            // Создаем директорию, если она не существует
            file.getParentFile().mkdirs();
            
            try (java.io.PrintWriter writer = new java.io.PrintWriter(file, "UTF-8")) {
                writer.print(htmlContent);
            }
            
            System.out.println("HTML Seite erfolgreich gespeichert: " + filePath);
            return true;
            
        } catch (java.io.IOException e) {
            System.err.println("Fehler beim Speichern der HTML Seite: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Сохраняет HTML страницу в файл с автоматически сгенерированным именем
     * @return путь к сохраненному файлу или null в случае ошибки
     */
    public String saveHTMLPageToFileAuto() {
        try {
            java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
            String timestamp = dateFormat.format(new java.util.Date());
            String fileName = "Lieferungen_Export_" + timestamp + ".html";
            String filePath = "exports/" + fileName;
            
            if (saveHTMLPageToFile(filePath)) {
                return filePath;
            }
            return null;
            
        } catch (Exception e) {
            System.err.println("Fehler beim automatischen Speichern der HTML Seite: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Альтернативный метод - сохранение сообщения в файл, если email не работает
     */
    private boolean saveProblemToFile(String userName, String userEmail, String message) {
        try {
            String filename = "problem_reports/problem_" + System.currentTimeMillis() + ".txt";
            java.io.File file = new java.io.File(filename);
            file.getParentFile().mkdirs();
            
            try (java.io.PrintWriter writer = new java.io.PrintWriter(file)) {
                writer.println("Problem Meldung - Liefermonitor");
                writer.println("================================");
                writer.println("Datum: " + new java.util.Date());
                writer.println("Von: " + userName);
                writer.println("E-Mail: " + userEmail);
                writer.println("\nNachricht:");
                writer.println(message);
            }
            
            System.out.println("Problem-Bericht gespeichert in: " + filename);
            return true;
            
        } catch (java.io.IOException e) {
            System.err.println("Fehler beim Speichern der Problem-Meldung: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
