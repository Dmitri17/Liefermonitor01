/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.Lieferung;
import de.zmiter.liefermonitor01.DBObjekte.Artikel;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.concurrent.CountDownLatch;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFShape;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author lepeschko
 */
public class DBManager {
    
    /** Fallback für erste Datenzeile (0-basiert), wenn Struktur nicht erkannt wird. */
    private static final int SAP_EXCEL_FIRST_DATA_ROW_INDEX = 1;
    private static final String SAP_ABRUFDOKU_HEADER = "Bestellentwicklung / Abrufdoku";
    
    Lieferung lif = new Lieferung();

    // URL для SQL Server базы данных
   
    // tsautodb01.ts-gruppe.com”
    private static Connection connection;
    
    // Для SQL Server нужны username и password
    String username = "lepeschko2";
    String password = "dmitri1969!";
    //  String persTab = "PersonUserFR";
      String persTab = "PersonUser";
   //  String tabLiefName = "lieferungenFR"; 
        String tabLiefName = "lieferung"; 
      
   // String lagerTab = "lagerFR";
      String lagerTab = "lager";
      String transportTab = "transport";
      private static final int TRANSPORT_FELD_COUNT = 30;

    /** Erstellt den Verbindungs-Dialog (Inhalt, noch nicht sichtbar). */
    private static JDialog createConnectionAttemptDialog() {
        JOptionPane pane = new JOptionPane(
            "DB-Info: Verbindungsversuch",
            JOptionPane.INFORMATION_MESSAGE,
            JOptionPane.DEFAULT_OPTION,
            null,
            new Object[0],
            null
        );
        pane.setOptions(new Object[0]);
        JDialog d = pane.createDialog((JFrame) null, "DB-Info");
        d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        d.setSize(360, 140);
        d.setLocationRelativeTo(null);
        return d;
    }

    public DBManager() {
        final Exception[] exHolder = new Exception[1];
        final boolean onEDT = SwingUtilities.isEventDispatchThread();

        if (onEDT) {
            // Auf EDT: Modal-Dialog anzeigen und in setVisible(true) blockieren.
            // So läuft der Event-Loop weiter und das Fenster wird korrekt gezeichnet.
            // Der Verbindungsthread schließt den Dialog nach Abschluss.
            final JDialog connDialog = createConnectionAttemptDialog();
            connDialog.setModal(true);
            Thread connectThread = new Thread(() -> {
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    connection = DriverManager.getConnection(DB_URL, username, password);
                    System.out.println("Connection with the SQL Server successfully made.");
                } catch (ClassNotFoundException e) {
                    exHolder[0] = e;
                    System.err.println("Error: SQL Server JDBC driver not found. Be sure, the dependency mssql-jdbc added at pom.xml");
                    e.printStackTrace();
                } catch (SQLException e) {
                    exHolder[0] = e;
                    System.err.println("Error by connecting DB: " + e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                    exHolder[0] = e;
                    System.err.println("Unexpected error: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    try {
                        SwingUtilities.invokeAndWait(() -> connDialog.dispose());
                    } catch (Exception ex) { }
                }
            });
            connectThread.start();
            connDialog.setVisible(true); // blockiert EDT bis Dialog geschlossen; Fenster wird gezeichnet
        } else {
            // Nicht auf EDT: Dialog anzeigen, Verbindung laufen lassen, dann Dialog schließen.
            final JDialog[] dialogHolder = new JDialog[1];
            try {
                SwingUtilities.invokeAndWait(() -> {
                    JDialog d = createConnectionAttemptDialog();
                    d.setModal(false);
                    d.setVisible(true);
                    dialogHolder[0] = d;
                });
            } catch (Exception ex) {
                dialogHolder[0] = null;
            }
            final JDialog connDialog = dialogHolder[0];
            final CountDownLatch latch = new CountDownLatch(1);
            Thread connectThread = new Thread(() -> {
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    connection = DriverManager.getConnection(DB_URL, username, password);
                    System.out.println("Connection with the SQL Server successfully made.");
                } catch (ClassNotFoundException e) {
                    exHolder[0] = e;
                    System.err.println("Error: SQL Server JDBC driver not found. Be sure, the dependency mssql-jdbc added at pom.xml");
                    e.printStackTrace();
                } catch (SQLException e) {
                    exHolder[0] = e;
                    System.err.println("Error by connecting DB: " + e.getMessage());
                    e.printStackTrace();
                } catch (Exception e) {
                    exHolder[0] = e;
                    System.err.println("Unexpected error: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
            connectThread.start();
            try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            if (connDialog != null) {
                try { SwingUtilities.invokeAndWait(() -> connDialog.dispose()); } catch (Exception ex) { }
            }
        }

        Exception e = exHolder[0];
        if (e instanceof ClassNotFoundException) {
            javax.swing.JOptionPane.showMessageDialog(null,
                "Fehler: Datenbanktreiber nicht gefunden.\nBitte prüfen Sie, ob alle Abhängigkeiten korrekt installiert sind.",
                "Fehler bei der Datenbankverbindung", javax.swing.JOptionPane.ERROR_MESSAGE);
        } else if (e instanceof SQLException) {
            javax.swing.JOptionPane.showMessageDialog(null,
                "Fehler bei der Datenbankverbindung:\n" + e.getMessage() +
                "\n\nBitte prüfen Sie die Verbindungseinstellungen und die Erreichbarkeit des Servers.",
                "Fehler bei der Datenbankverbindung", javax.swing.JOptionPane.ERROR_MESSAGE);
        } else if (e != null) {
            javax.swing.JOptionPane.showMessageDialog(null, "Unerwarteter Fehler: " + e.getMessage(), "Fehler", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        ensureGeliefertAnBsColumnInLager();
    }
    
    public void createTableLieferungen() {
        if (tabLiefName == null || tabLiefName.trim().isEmpty()) {
           // tabName = "lieferung";
          
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return;
        }
        
        // Сначала проверяем, существует ли таблица
        if (tableExists(tabLiefName)) {
            System.out.println("Table '" + tabLiefName + "' already exists");
            return;
        }
        
        String createTableSQL = "CREATE TABLE " + tabLiefName + " (" +
            "id INT IDENTITY(1,1) PRIMARY KEY," +
            "neu BIT DEFAULT 0," +
            "aktiv BIT DEFAULT 1," +
            "agrNummer NVARCHAR(255)," +
            "anfordNummer NVARCHAR(255)," +
            "menge NVARCHAR(50) DEFAULT '0'," +
            "einhMenge NVARCHAR(255)," +
            "beschreibung NVARCHAR(2000) DEFAULT '____'," +
            "de_eu NVARCHAR(255)," +
            "abteilung NVARCHAR(255)," +
            "anforderungDatum NVARCHAR(50)," +
            "wunschLieferDatum NVARCHAR(50)," +
            "anfordPerson NVARCHAR(255)," +  
            "unterschriftMH BIT DEFAULT 0," +
            "unterschriftKunde BIT DEFAULT 0," +
            "anEinkaufWeiterGeleitetMH BIT DEFAULT 0," +
            "invest_Leasing BIT DEFAULT 0," +
            "summeMat_Leist_cents NVARCHAR(50) DEFAULT '0'," +
            "preisEinkauf NVARCHAR(50) DEFAULT '0'," +
            "lieferantName NVARCHAR(255)," +
            "bestelltAm NVARCHAR(50)," +
            "bestellNummer NVARCHAR(255)," +
            "statusEinkauf NVARCHAR(255)," +
            "warenEingangTS NVARCHAR(255)," +
            "wareneingangTS_plan NVARCHAR(50)," +
            "wareneingangTS_Fakt NVARCHAR(50)," +
            "auslieferungNachPlan NVARCHAR(50)," +
            "zahlungsbedingungen NVARCHAR(1000)," +
            "kommentEinkauf NVARCHAR(1000)," +
            "terminWareneingangPlan NVARCHAR(50)," +
            "wareneingang_Fakt NVARCHAR(50)," +
            "kommentarBS NVARCHAR(1000)," +
            "statusAuslieferungBS NVARCHAR(255)," +
            "gewicht NVARCHAR(50) DEFAULT '0.0'," +
            "einhMasse NVARCHAR(255)," +
            "laenge NVARCHAR(50) DEFAULT '0.0'," +
            "breite NVARCHAR(50) DEFAULT '0.0'," +
            "hoehe NVARCHAR(50) DEFAULT '0.0'," +
            "einheitLBH NVARCHAR(255)," +
            "kolli NVARCHAR(255)," +
            "packListe NVARCHAR(255)," +
            "containerNr NVARCHAR(255)," +
            "rgNummeranAG NVARCHAR(255)," +
            "rechnungDatum NVARCHAR(50)," +
            "istWeitergerechnetAnAG BIT DEFAULT 0," +
            "summeWeitergerechnetanAG_cents NVARCHAR(50) DEFAULT '0'," +
            "summe_nach_Weiterrechnung NVARCHAR(50) DEFAULT '0'," +
            "kundenInRechnungStellen NVARCHAR(50) DEFAULT '0'," +
            "info01 NVARCHAR(1000)," +
            "info02 NVARCHAR(1000)," +
            "info03 NVARCHAR(1000)," +
            "info04 NVARCHAR(1000)," +
            "info05 NVARCHAR(1000)," +
            "created_at DATETIME2 DEFAULT GETDATE()," +
            "updated_at DATETIME2 DEFAULT GETDATE()" +
            ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Tabelle '" + tabLiefName + "' is created succesfully  ");
        } catch (SQLException e) {
            System.err.println("Error by creating a Table: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Создает таблицу PersonUser в базе данных
     */
    public void createPersonUserTable() {
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return;
        }
       // String persTab = "PersonUser";
        // String persTab = "PersonUserFR";
        String createTableSQL = "CREATE TABLE "+persTab+ " (" +//#####################################  FR!!!!!!!!!
            "id INT IDENTITY(1,1) PRIMARY KEY," +
            "name NVARCHAR(255) NOT NULL," +
            "login NVARCHAR(255) UNIQUE NOT NULL," +
            "password NVARCHAR(255) NOT NULL," +
            "abteilung NVARCHAR(255)," +
            "projekt NVARCHAR(255)," +
            "zugangsGruppe NVARCHAR(50) DEFAULT 'A'," +
            "created_at DATETIME2 DEFAULT GETDATE()," +
            "updated_at DATETIME2 DEFAULT GETDATE()" +
            ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table PersonUser erfolgreich erstellt");
        } catch (SQLException e) {
            System.err.println("Fehler beim Herstellen der  PersonUser Tabelle: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Prüft, ob bereits eine Lieferung mit derselben Anforderungsnummer
     * oder Bestellnummer existiert.
     * Hinweis: agrNummer wird bewusst NICHT für Duplikaterkennung verwendet.
     */
    private boolean lieferungExistsWithSameNummer(Lieferung lif) {
        if (!ensureConnection()) {
            return false;
        }
        String anfRaw = lif.getAnfordNummer();
        String bestellRaw = lif.getBestellNummer();
        String anf = anfRaw != null ? anfRaw.trim() : "";
        String bestell = bestellRaw != null ? bestellRaw.trim() : "";
        int hasAnf = anf.isEmpty() ? 0 : 1;
        int hasBestell = bestell.isEmpty() ? 0 : 1;
        if (hasAnf == 0 && hasBestell == 0) {
            return false;
        }
        String sql = "SELECT COUNT(*) FROM " + tabLiefName + " WHERE "
                + "((? = 1) AND LTRIM(RTRIM(ISNULL(anfordNummer, N''))) = ?) "
                + "OR ((? = 1) AND LTRIM(RTRIM(ISNULL(bestellNummer, N''))) = ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            int i = 1;
            ps.setInt(i++, hasAnf);
            ps.setString(i++, anf);
            ps.setInt(i++, hasBestell);
            ps.setString(i++, bestell);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler bei Duplikatpruefung Lieferung: " + e.getMessage());
        }
        return false;
    }
    
    boolean addInTabLif(Lieferung lif){
        boolean addOK = false;
        
        if (lif == null) {
            System.err.println("Error: object Lieferung can't be null");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return false;
        }
        
        if (lieferungExistsWithSameNummer(lif)) {
            System.err.println("Lieferung nicht eingetragen: Anforderungsnummer oder Bestellnummer existiert bereits "
                    + "(anfordNummer=" + lif.getAnfordNummer() + ", bestellNummer=" + lif.getBestellNummer() + ")");
            return false;
        }
       // String tabName = "lieferungenFR";
        
        String insertSQL = "INSERT INTO " + tabLiefName + " (" +
            "neu, aktiv, agrNummer, anfordNummer, menge, einhMenge, " +
            "beschreibung, de_eu, abteilung, anforderungDatum, wunschLieferDatum, " +
            "anfordPerson, unterschriftMH, unterschriftKunde, anEinkaufWeiterGeleitetMH, " +
            "invest_Leasing, summeMat_Leist_cents, preisEinkauf, lieferantName, bestelltAm, " +
            "bestellNummer, statusEinkauf, warenEingangTS, wareneingangTS_plan, wareneingangTS_Fakt, " +
            "auslieferungNachPlan, zahlungsbedingungen, kommentEinkauf, terminWareneingangPlan, " +
            "wareneingang_Fakt, kommentarBS, statusAuslieferungBS, gewicht, einhMasse, laenge, breite, hoehe, " +
            "einheitLBH, kolli, packListe, containerNr, rgNummeranAG, rechnungDatum, " +
            "istWeitergerechnetAnAG, summeWeitergerechnetanAG_cents, summe_nach_Weiterrechnung, kundenInRechnungStellen, " +
            "info01, info02, info03, info04, info05) " +
                
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            int paramIndex = 1;
            
            // Устанавливаем значения параметров
            pstmt.setBoolean(paramIndex++, lif.isNeu());
            pstmt.setBoolean(paramIndex++, lif.isAktiv());
            pstmt.setString(paramIndex++, lif.getAgrNummer());
            pstmt.setString(paramIndex++, lif.getAnfordNummer());
            pstmt.setString(paramIndex++, String.valueOf(lif.getMenge()));
            pstmt.setString(paramIndex++, lif.getEinhMenge());
            pstmt.setString(paramIndex++, lif.getBeschreibung());
            pstmt.setString(paramIndex++, lif.getDe_eu());
            pstmt.setString(paramIndex++, lif.getProjectName());
            pstmt.setString(paramIndex++, lif.getAnforderungDatum());
            pstmt.setString(paramIndex++, lif.getWunschLieferDatum());
            pstmt.setString(paramIndex++, lif.getAnfordPerson());
            pstmt.setBoolean(paramIndex++, lif.isUnterschriftMH());
            pstmt.setBoolean(paramIndex++, lif.isUnterschriftKunde());
            pstmt.setBoolean(paramIndex++, lif.isAnEinkaufWeiterGeleitetMH());
            pstmt.setBoolean(paramIndex++, lif.isInvest_Leasing());
            pstmt.setString(paramIndex++, String.valueOf(lif.getSummeMat_Leist_cents()));
            pstmt.setString(paramIndex++, String.valueOf(lif.getPreisEinkauf()));
            pstmt.setString(paramIndex++, lif.getLieferantName());
        
            pstmt.setString(paramIndex++, normalizeBestelltAmForDb(lif.getBestelltAm()));
            pstmt.setString(paramIndex++, lif.getBestellNummer());
            pstmt.setString(paramIndex++, lif.getStatusEinkauf());
            pstmt.setString(paramIndex++, lif.getWarenEingangTS());
            pstmt.setString(paramIndex++, lif.getWareneingangTS_plan());
            pstmt.setString(paramIndex++, lif.getWareneingangTS_Fakt());
            pstmt.setString(paramIndex++, lif.getAuslieferungNachPlan());
            pstmt.setString(paramIndex++, lif.getZahlungsbedingungen());
            pstmt.setString(paramIndex++, lif.getKommentEinkauf());
            pstmt.setString(paramIndex++, lif.getWunschterminWareneingang());
            pstmt.setString(paramIndex++, lif.getWareneingang_Fakt());
            pstmt.setString(paramIndex++, lif.getKommentarBS());
            pstmt.setString(paramIndex++, lif.getStatusAuslieferungBS());
            pstmt.setString(paramIndex++, String.valueOf(lif.getGewicht()));
            pstmt.setString(paramIndex++, lif.getEinhMasse());
            pstmt.setString(paramIndex++, String.valueOf(lif.getLaenge()));
            pstmt.setString(paramIndex++, String.valueOf(lif.getBreite()));
            pstmt.setString(paramIndex++, String.valueOf(lif.getHoehe()));
            pstmt.setString(paramIndex++, lif.getEinheitLBH());
            pstmt.setString(paramIndex++, lif.getKolli());
            pstmt.setString(paramIndex++, lif.getPackListe());
            pstmt.setString(paramIndex++, lif.getContainerNr());
            pstmt.setString(paramIndex++, lif.getRgNummeranAG());
            pstmt.setString(paramIndex++, lif.getDatumBS());
            pstmt.setBoolean(paramIndex++, lif.getIstWeitergerechnetAnAG());
            pstmt.setString(paramIndex++, String.valueOf(lif.getSummeWeitergerechnetanAG_cents()));
            pstmt.setString(paramIndex++, String.valueOf(lif.getSumme_nach_Weiterrechnung()));
            pstmt.setString(paramIndex++, String.valueOf(lif.getKundenInRechnungStellen()));
            pstmt.setString(paramIndex++, lif.getInfo01());
            pstmt.setString(paramIndex++, lif.getInfo02());
            pstmt.setString(paramIndex++, lif.getInfo03());
            pstmt.setString(paramIndex++, lif.getInfo04());
            pstmt.setString(paramIndex++, lif.getInfo05());
            
            // Выполняем запрос
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                int generatedId = 0;
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        generatedId = rs.getInt(1);
                    }
                }

                if (generatedId > 0) {
                    // Новый номер заявки = prefix (первые 5 символов login) + ID записи
                    String prefix = lif.getAnfordNummer() != null ? lif.getAnfordNummer().trim() : "";
                    if (prefix.length() > 5) {
                        prefix = prefix.substring(0, 5);
                    }
                    String generatedAnfordNummer = prefix + generatedId;
                    String updateNummerSql = "UPDATE " + tabLiefName + " SET anfordNummer = ? WHERE id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateNummerSql)) {
                        updateStmt.setString(1, generatedAnfordNummer);
                        updateStmt.setInt(2, generatedId);
                        updateStmt.executeUpdate();
                    }
                    lif.setId(generatedId);
                    lif.setAnfordNummer(generatedAnfordNummer);
                }

                addOK = true;
                System.out.println("Neuen lieferung ist erfolgreich in die Tabelle eingetragen");
            } else {
                System.err.println("ERROR: lieferung wurde nicht gespeichert");
            }
            
        } catch (SQLException e) {
            System.err.println("Fehler beim speichern in DB : " + e.getMessage());
            e.printStackTrace();
        }
        
        return addOK;
    }
    
    /**
     * Добавляет новую запись PersonUser в базу данных
     * @param personUser объект PersonUser для добавления
     * @return true если операция успешна
     */
    public boolean addPersonUser(de.zmiter.liefermonitor01.DBObjekte.PersonUser personUser) {
        boolean addOK = false;
        
        if (personUser == null) {
            System.err.println("ERROR:  PersonUser darf  nicht null sein");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return false;
        }
       // String persTab = "PersonUserFR";
        
        String insertSQL = "INSERT INTO "+ persTab + " (name, login, password, abteilung, projekt, zugangsGruppe) " +
                          "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, personUser.getName());
            pstmt.setString(2, personUser.getLogin());
            pstmt.setString(3, personUser.getPassword());
            pstmt.setString(4, personUser.getAbteilung());
            pstmt.setString(5, personUser.getProjekt());
            String zg = personUser.getZugangsGruppe();
            pstmt.setString(6, truncateZugangsGruppe(zg));
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                addOK = true;
                System.out.println("Benutzer " + personUser.getName() + " erfolgreich in die DB eingetragen");
            } else {
                System.err.println("Fehler: Benutzer ist nicht gespeichert");
            }
            
        } catch (SQLException e) {
            System.err.println("Fehler beim Eintragen in die DB : " + e.getMessage());
            e.printStackTrace();
        }
        
        return addOK;
    }
    
    /**
     * Обновляет запись PersonUser в базе данных по логину
     * @param personUser объект PersonUser с обновленными данными
     * @return true если операция успешна
     */
    public boolean updatePersonUser(de.zmiter.liefermonitor01.DBObjekte.PersonUser personUser) {
        boolean updateOK = false;
        
        if (personUser == null) {
            System.err.println("Fehler:  PersonUser darf nicht null sein");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return false;
        }
        
        String updateSQL = "UPDATE " + persTab + " SET " +
                          "name = ?, password = ?, abteilung = ?, projekt = ?, zugangsGruppe = ?, " +
                          "updated_at = GETDATE() " +
                          "WHERE login = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            pstmt.setString(1, personUser.getName());
            pstmt.setString(2, personUser.getPassword());
            pstmt.setString(3, personUser.getAbteilung());
            pstmt.setString(4, personUser.getProjekt());
            String zg = personUser.getZugangsGruppe();
            pstmt.setString(5, truncateZugangsGruppe(zg));
            pstmt.setString(6, personUser.getLogin());
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                updateOK = true;
                System.out.println("Benutzer " + personUser.getName() + " erfolgreich Updated in DB");
            } else {
                System.err.println("Fehler: Benutzer mit Login  " + personUser.getLogin() + " nicht gefunden");
            }
            
        } catch (SQLException e) {
            System.err.println("ERROR beim Updaten des Users in DB: " + e.getMessage());
            e.printStackTrace();
        }
        
        return updateOK;
    }
    
    /** Maximale Länge der Spalte zugangsGruppe (bestehende DB: NVARCHAR(10)). */
    private static final int ZUGANGSGRUPPE_MAX_LENGTH = 10;
    private static String truncateZugangsGruppe(String value) {
        if (value == null) return null;
        return value.length() <= ZUGANGSGRUPPE_MAX_LENGTH ? value : value.substring(0, ZUGANGSGRUPPE_MAX_LENGTH);
    }
    
    /**
     * Удаляет запись PersonUser из базы данных по логину
     * @param login логин пользователя для удаления
     * @return true если операция успешна
     */
    public boolean deletePersonUser(String login) {
        boolean deleteOK = false;
        
        if (login == null || login.trim().isEmpty()) {
            System.err.println("Fehler: Login darf nicht leer sein");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR:keine Verbindung mit DB");
            return false;
        }
        
        String deleteSQL = "DELETE FROM "+ persTab +" WHERE login = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setString(1, login);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                deleteOK = true;
                System.out.println("Nutzer mit Login " + login + " erfolgreich aus der DB entfernt");
            } else {
                System.err.println("Fehler: Nutzer mit Login " + login + " nicht gefundenн");
            }
            
        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen des Nutzes aus DB: " + e.getMessage());
            e.printStackTrace();
        }
        
        return deleteOK;
    }
    
    /**
     * Получает список всех PersonUser из базы данных
     * @return ArrayList<PersonUser> список всех пользователей
     */
    public ArrayList<de.zmiter.liefermonitor01.DBObjekte.PersonUser> getAllPersonUsers() {
        ArrayList<de.zmiter.liefermonitor01.DBObjekte.PersonUser> personUsers = new ArrayList<>();
        
        if (!ensureConnection()) {
            System.err.println("ERROR:keine Verbindung mit DB");
            return personUsers;
        }
        
        String selectSQL = "SELECT id, name, login, password, abteilung, projekt, zugangsGruppe, created_at, updated_at FROM "+ persTab +" ORDER BY name";
        
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                de.zmiter.liefermonitor01.DBObjekte.PersonUser personUser = new de.zmiter.liefermonitor01.DBObjekte.PersonUser(rs.getString("name"));
                personUser.setId(rs.getInt("id"));
                personUser.setLogin(rs.getString("login"));
                personUser.setPassword(rs.getString("password"));
                personUser.setAbteilung(rs.getString("abteilung"));
                personUser.setProjekt(rs.getString("projekt"));
                personUser.setZugangsGruppe(rs.getString("zugangsGruppe"));
                
                personUsers.add(personUser);
            }
            
           // System.out.println("Geladen " + personUsers.size() + " Nutzer aus DB");
            
        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen der Nutzerlist aus DB: " + e.getMessage());
            e.printStackTrace();
        }
        
        return personUsers;
    }
    
    /**
     * Получает PersonUser по логину
     * @param login логин пользователя
     * @return PersonUser объект пользователя или null если не найден
     */
    public de.zmiter.liefermonitor01.DBObjekte.PersonUser getPersonUserByLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            System.err.println("Fehler: Login darf nicht leer sein");
            return null;
        }
        
        if (!ensureConnection()) {
            System.err.println("Fehler: keine Verbindung mit DB");
            return null;
        }
        
        String selectSQL = "SELECT id, name, login, password, abteilung, projekt, zugangsGruppe, created_at, updated_at FROM "+ persTab + " WHERE login = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, login);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    de.zmiter.liefermonitor01.DBObjekte.PersonUser personUser = new de.zmiter.liefermonitor01.DBObjekte.PersonUser(rs.getString("name"));
                    personUser.setId(rs.getInt("id"));
                    personUser.setLogin(rs.getString("login"));
                    personUser.setPassword(rs.getString("password"));
                    personUser.setAbteilung(rs.getString("abteilung"));
                    personUser.setProjekt(rs.getString("projekt"));
                    personUser.setZugangsGruppe(rs.getString("zugangsGruppe"));
                    
                   // System.out.println("Пользователь " + personUser.getName() + " найден в базе данных");
                    return personUser;
                } else {
                    System.out.println("Nutzer mit Login " + login + " nicht gefunden");
                    return null;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen der Nutzer aus DB: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean deletFromTab(int id) {
        boolean deleteOK = false;
        
        if (id <= 0) {
            System.err.println("Fehler: ID soll grösser 0 sein");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return false;
        }
        
        String deleteSQL = "DELETE FROM "+ tabLiefName + " WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                deleteOK = true;
                System.out.println("Element mit ID " + id + " erfolgreich gelöscht");
            } else {
                System.err.println("Fehler: Element mit ID " + id + " wurde nicht gefunden");
            }
            
        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen von Element mit  ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        
        return deleteOK;
    }
    
    public boolean updateTabLieferungen(Lieferung lif){
        boolean updateOk = false;
        
        if (lif == null) {
            System.err.println("ERROR: Objekt Lieferung darf nicht  null sein");
            return false;
        }
        
        if (lif.getId() <= 0) {
            System.err.println("Error: ID muss groesser als  0 sein fuer Update ");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return false;
        }
        
        String updateSQL = "UPDATE "+ tabLiefName+" SET " +
            "neu = ?, aktiv = ?, agrNummer = ?, anfordNummer = ?, menge = ?, " +
            "einhMenge = ?, beschreibung = ?, de_eu = ?, abteilung = ?, " +
            "anforderungDatum = ?, wunschLieferDatum = ?, anfordPerson = ?, " +
            "unterschriftMH = ?, unterschriftKunde = ?, anEinkaufWeiterGeleitetMH = ?, " +
            "invest_Leasing = ?, summeMat_Leist_cents = ?, preisEinkauf = ?, " +
            "lieferantName = ?, bestelltAm = ?, bestellNummer = ?, statusEinkauf = ?, " +
            "warenEingangTS = ?, wareneingangTS_plan = ?, wareneingangTS_Fakt = ?, auslieferungNachPlan = ?, " +
            "zahlungsbedingungen = ?, kommentEinkauf = ?, terminWareneingangPlan = ?, wareneingang_Fakt = ?, " +
            "kommentarBS = ?, statusAuslieferungBS = ?, gewicht = ?, einhMasse = ?, laenge = ?, breite = ?, hoehe = ?, " +
            "einheitLBH = ?, kolli = ?, packListe = ?, containerNr = ?, rgNummeranAG = ?, " +
            "rechnungDatum = ?, istWeitergerechnetAnAG = ?, summeWeitergerechnetanAG_cents = ?, " +
            "summe_nach_Weiterrechnung = ?, kundenInRechnungStellen = ?, " +
            "info01 = ?, info02 = ?, info03 = ?, info04 = ?, info05 = ?, " +
            "updated_at = GETDATE() " +
            "WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            int paramIndex = 1;
            
            // Устанавливаем значения параметров (все поля кроме id)
            pstmt.setBoolean(paramIndex++, lif.isNeu());
            pstmt.setBoolean(paramIndex++, lif.isAktiv());
            pstmt.setString(paramIndex++, lif.getAgrNummer());
            pstmt.setString(paramIndex++, lif.getAnfordNummer());
            pstmt.setString(paramIndex++, String.valueOf(lif.getMenge()));
            pstmt.setString(paramIndex++, lif.getEinhMenge());
            pstmt.setString(paramIndex++, lif.getBeschreibung());
            pstmt.setString(paramIndex++, lif.getDe_eu());
            pstmt.setString(paramIndex++, lif.getProjectName());
            pstmt.setString(paramIndex++, lif.getAnforderungDatum());
            pstmt.setString(paramIndex++, lif.getWunschLieferDatum());
            pstmt.setString(paramIndex++, lif.getAnfordPerson());
            pstmt.setBoolean(paramIndex++, lif.isUnterschriftMH());
            pstmt.setBoolean(paramIndex++, lif.isUnterschriftKunde());
            pstmt.setBoolean(paramIndex++, lif.isAnEinkaufWeiterGeleitetMH());
            pstmt.setBoolean(paramIndex++, lif.isInvest_Leasing());
            pstmt.setString(paramIndex++, String.valueOf(lif.getSummeMat_Leist_cents()));
            pstmt.setString(paramIndex++, String.valueOf(lif.getPreisEinkauf()));
           
            pstmt.setString(paramIndex++, lif.getLieferantName());
            pstmt.setString(paramIndex++, normalizeBestelltAmForDb(lif.getBestelltAm()));
            pstmt.setString(paramIndex++, lif.getBestellNummer());
            pstmt.setString(paramIndex++, lif.getStatusEinkauf());
            pstmt.setString(paramIndex++, lif.getWarenEingangTS() );
            pstmt.setString(paramIndex++, lif.getWareneingangTS_plan());
            pstmt.setString(paramIndex++, lif.getWareneingangTS_Fakt());
            pstmt.setString(paramIndex++, lif.getAuslieferungNachPlan());
            pstmt.setString(paramIndex++, lif.getZahlungsbedingungen());
            pstmt.setString(paramIndex++, lif.getKommentEinkauf());
            pstmt.setString(paramIndex++, lif.getWunschterminWareneingang());
            pstmt.setString(paramIndex++, lif.getWareneingang_Fakt());
            pstmt.setString(paramIndex++, lif.getKommentarBS());
            pstmt.setString(paramIndex++, lif.getStatusAuslieferungBS());
            pstmt.setString(paramIndex++, String.valueOf(lif.getGewicht()));
            pstmt.setString(paramIndex++, lif.getEinhMasse());
            pstmt.setString(paramIndex++, String.valueOf(lif.getLaenge()));
            pstmt.setString(paramIndex++, String.valueOf(lif.getBreite()));
            pstmt.setString(paramIndex++, String.valueOf(lif.getHoehe()));
            pstmt.setString(paramIndex++, lif.getEinheitLBH());
            pstmt.setString(paramIndex++, lif.getKolli());
            pstmt.setString(paramIndex++, lif.getPackListe());
            pstmt.setString(paramIndex++, lif.getContainerNr());
            pstmt.setString(paramIndex++, lif.getRgNummeranAG());
            pstmt.setString(paramIndex++, lif.getDatumBS());
            pstmt.setBoolean(paramIndex++, lif.getIstWeitergerechnetAnAG());
            pstmt.setString(paramIndex++, String.valueOf(lif.getSummeWeitergerechnetanAG_cents()));
            pstmt.setString(paramIndex++, String.valueOf(lif.getSumme_nach_Weiterrechnung()));
            pstmt.setString(paramIndex++, String.valueOf(lif.getKundenInRechnungStellen()));
            pstmt.setString(paramIndex++, lif.getInfo01());
            pstmt.setString(paramIndex++, lif.getInfo02());
            pstmt.setString(paramIndex++, lif.getInfo03());
            pstmt.setString(paramIndex++, lif.getInfo04());
            pstmt.setString(paramIndex++, lif.getInfo05());
            
            // Устанавливаем ID для условия WHERE
            pstmt.setInt(paramIndex++, lif.getId());
            
            // Выполняем запрос
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                updateOk = true;
               // System.out.println("Element mit ID " + lif.getId() + " erfolgreich updated");
            } else {
                System.err.println("ERROR: Objekt mit ID " + lif.getId() + " wurde nicht gefunden und nicht Updated");
            }
            
        } catch (SQLException e) {
            System.err.println("ERROR beim Update des Objektes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return updateOk;
    }
    
    ArrayList<Lieferung> seachInTabLief(String param){
        ArrayList<Lieferung> lifList = new ArrayList<>();
        
        if (param == null || param.trim().isEmpty()) {
            System.err.println("ERROR: Suchparameter darf nicht leer sein");
            return lifList;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return lifList;
        }
        
        String searchSQL = "SELECT * FROM "+ tabLiefName+" WHERE " +
            "LOWER(agrNummer) LIKE LOWER(?) OR " +
            "LOWER(anfordNummer) LIKE LOWER(?) OR " +
            "LOWER(beschreibung) LIKE LOWER(?) OR " +
            "LOWER(de_eu) LIKE LOWER(?) OR " +
            "LOWER(abteilung) LIKE LOWER(?) OR " +
            "LOWER(anfordPerson) LIKE LOWER(?) OR " +
            "LOWER(lieferantName) LIKE LOWER(?) OR " +
            "LOWER(bestellNummer) LIKE LOWER(?) OR " +
            "LOWER(statusEinkauf) LIKE LOWER(?) OR " +
            "LOWER(zahlungsbedingungen) LIKE LOWER(?) OR " +
            "LOWER(kommentEinkauf) LIKE LOWER(?) OR " +
            "LOWER(kommentarBS) LIKE LOWER(?) OR " +
            "LOWER(statusAuslieferungBS) LIKE LOWER(?) OR " +
            "LOWER(einhMenge) LIKE LOWER(?) OR " +
            "LOWER(einhMasse) LIKE LOWER(?) OR " +
            "LOWER(einheitLBH) LIKE LOWER(?) OR " +
            "LOWER(kolli) LIKE LOWER(?) OR " +
            "LOWER(packListe) LIKE LOWER(?) OR " +
            "LOWER(containerNr) LIKE LOWER(?) OR " +
            "LOWER(rgNummeranAG) LIKE LOWER(?) OR " +
            "LOWER(info01) LIKE LOWER(?) OR " +
            "LOWER(info02) LIKE LOWER(?) OR " +
            "LOWER(info03) LIKE LOWER(?) OR " +
            "LOWER(info04) LIKE LOWER(?) OR " +
            "LOWER(info05) LIKE LOWER(?) OR " +
            "CAST(menge AS NVARCHAR) LIKE ? OR " +
            "CAST(summeMat_Leist_cents AS NVARCHAR) LIKE ? OR " +
            "CAST(preisEinkauf AS NVARCHAR) LIKE ? OR " +
            "CAST(gewicht AS NVARCHAR) LIKE ? OR " +
            "CAST(laenge AS NVARCHAR) LIKE ? OR " +
            "CAST(breite AS NVARCHAR) LIKE ? OR " +
            "CAST(hoehe AS NVARCHAR) LIKE ? OR " +
            "CAST(summeWeitergerechnetanAG_cents AS NVARCHAR) LIKE ? OR " +
            "CAST(summe_nach_Weiterrechnung AS NVARCHAR) LIKE ? OR " +
            "CAST(kundenInRechnungStellen AS NVARCHAR) LIKE ? " +
            "ORDER BY id DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(searchSQL)) {
            // Устанавливаем параметр поиска для всех полей
            String searchPattern = "%" + param.trim() + "%";
            
            // Устанавливаем параметры для текстовых полей
            for (int i = 1; i <= 25; i++) {
                pstmt.setString(i, searchPattern);
            }
            
            // Устанавливаем параметры для числовых полей
            for (int i = 26; i <= 35; i++) {
                pstmt.setString(i, searchPattern);
            }
            
            // Выполняем запрос
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String beschr =rs.getString("beschreibung");
                    String menStr = rs.getString("menge");
                    float men = Float.parseFloat(menStr != null ? menStr : "0");
                    Lieferung lieferung = new Lieferung(beschr, men );
                    
                    // Заполняем объект данными из ResultSet
                    lieferung.setId(rs.getInt("id"));
                    lieferung.setNeu(rs.getBoolean("neu"));
                    lieferung.setAktiv(rs.getBoolean("aktiv"));
                    lieferung.setAgrNummer(rs.getString("agrNummer"));
                    lieferung.setAnfordNummer(rs.getString("anfordNummer"));
                    lieferung.setMenge(men);
                    lieferung.setEinhMenge(rs.getString("einhMenge"));
                   
                    lieferung.setBeschreibung(beschr);
                    lieferung.setDe_eu(rs.getString("de_eu"));
                    lieferung.setProjectName(rs.getString("abteilung"));
                    
                    // Обработка дат как строк
                    lieferung.setAnforderungDatum(rs.getString("anforderungDatum"));
                    lieferung.setWunschLieferDatum(rs.getString("wunschLieferDatum"));
                    
                    lieferung.setAnfordPerson(rs.getString("anfordPerson"));
                    lieferung.setUnterschriftMH(rs.getBoolean("unterschriftMH"));
                    lieferung.setUnterschriftKunde(rs.getBoolean("unterschriftKunde"));
                    lieferung.setAnEinkaufWeiterGeleitetMH(rs.getBoolean("anEinkaufWeiterGeleitetMH"));
                    lieferung.setInvest_Leasing(rs.getBoolean("invest_Leasing"));
                    String summeStr = rs.getString("summeMat_Leist_cents");
                    lieferung.setSummeMat_Leist_cents(Float.parseFloat(summeStr != null ? summeStr : "0"));
                  
                    String preisStr = rs.getString("preisEinkauf");
                    lieferung.setPreisEinkauf(Float.parseFloat(preisStr != null ? preisStr : "0"));
                  
                    lieferung.setLieferantName(rs.getString("lieferantName"));
                    
                    lieferung.setBestelltAm(rs.getString("bestelltAm"));
                    
                    lieferung.setBestellNummer(rs.getString("bestellNummer"));
                    lieferung.setStatusEinkauf(rs.getString("statusEinkauf"));
                    
                   lieferung.setWarenEingangTS(rs.getString("warenEingangTS"));
                   
                    lieferung.setWareneingangTS_plan(rs.getString("wareneingangTS_plan"));
                    
                    lieferung.setWareneingangTS_Fakt(rs.getString("wareneingangTS_Fakt"));
                    
                    lieferung.setAuslieferungNachPlan(rs.getString("auslieferungNachPlan"));
                    
                    lieferung.setZahlungsbedingungen(rs.getString("zahlungsbedingungen"));
                    lieferung.setKommentEinkauf(rs.getString("kommentEinkauf"));
                    
                    lieferung.setWunschterminWareneingang(rs.getString("terminWareneingangPlan"));
                    
                    lieferung.setWareneingang_Fakt(rs.getString("wareneingang_Fakt"));
                    
                    lieferung.setKommentarBS(rs.getString("kommentarBS"));
                    lieferung.setStatusAuslieferungBS(rs.getString("statusAuslieferungBS"));
                    String gewichtStr = rs.getString("gewicht");
                    lieferung.setGewicht(Float.parseFloat(gewichtStr != null ? gewichtStr : "0"));
                    lieferung.setEinhMasse(rs.getString("einhMasse"));
                    String laengeStr = rs.getString("laenge");
                    lieferung.setLaenge(Float.parseFloat(laengeStr != null ? laengeStr : "0"));
                    String breiteStr = rs.getString("breite");
                    lieferung.setBreite(Float.parseFloat(breiteStr != null ? breiteStr : "0"));
                    String hoeheStr = rs.getString("hoehe");
                    lieferung.setHoehe(Float.parseFloat(hoeheStr != null ? hoeheStr : "0"));
                    lieferung.setEinheitLBH(rs.getString("einheitLBH"));
                    lieferung.setKolli(rs.getString("kolli"));
                    lieferung.setPackListe(rs.getString("packListe"));
                    lieferung.setContainerNr(rs.getString("containerNr"));
                    lieferung.setRgNummeranAG(rs.getString("rgNummeranAG"));
                    
                    lieferung.setDatumBS(rs.getString("rechnungDatum"));
                    
                    String summeWeiterStr = rs.getString("summeWeitergerechnetanAG_cents");
                    lieferung.setSummeWeitergerechnetanAG_cents(Float.parseFloat(summeWeiterStr != null ? summeWeiterStr : "0"));
                    String summeNachStr = rs.getString("summe_nach_Weiterrechnung");
                    lieferung.setSumme_nach_Weiterrechnung(Float.parseFloat(summeNachStr != null ? summeNachStr : "0"));
                    String kundenStr = rs.getString("kundenInRechnungStellen");
                    lieferung.setKundenInRechnungStellen(Float.parseFloat(kundenStr != null ? kundenStr : "0"));
                    
                    // Обработка информационных полей
                    lieferung.setInfo01(rs.getString("info01"));
                    lieferung.setInfo02(rs.getString("info02"));
                    lieferung.setInfo03(rs.getString("info03"));
                    lieferung.setInfo04(rs.getString("info04"));
                    lieferung.setInfo05(rs.getString("info05"));
                   
                    // Устанавливаем текст кнопки
                    lieferung.setText(lieferung.getBeschreibung());
                    
                    // Добавляем объект в список
                    lifList.add(lieferung);
                }
            }
            
            System.out.println("Wurde : " + lifList.size() + " Objekte gefunden");
            
        } catch (SQLException e) {
            System.err.println("Error beim Suchen nach Objekte: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lifList;
    }
    
    /**
     * Удаляет таблицу из базы данных
     * @param name имя таблицы для удаления
     * @return true если таблица успешно удалена, false в противном случае
     */
    public boolean deletTab(String name) {
        boolean deleteOK = false;
        
        if (name == null || name.trim().isEmpty()) {
            System.err.println("ERROR: Tabellenname darf nicht leer sein");
            return false;
        }
        
        // Проверяем соединение с базой данных
        if (!ensureConnection()) {
            System.err.println("ERROR: Verbindung mit DB ist  hergestellt nicht");
            return false;
        }
        
        String tableName = name.trim();
        
        // Проверяем существование таблицы перед удалением
        if (!tableExists(tableName)) {
            System.err.println("EROR: Tabelle '" + tableName + "' gibt e nicht");
            return false;
        }
        
        String dropTableSQL = "DROP TABLE IF EXISTS " + tableName;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(dropTableSQL);
            deleteOK = true;
            System.out.println("Tabelle '" + tableName + "' erfolgreich  gelöscht");
        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen der Tabelle '" + tableName + "': " + e.getMessage());
            e.printStackTrace();
        }
        
        return deleteOK;
    }
    
    /**
     * Erstellt eine neue Tabelle mit einer einfachen ID-Spalte.
     * @param name Tabellenname
     * @return true, wenn die Tabelle erfolgreich erstellt wurde
     */
    public boolean createCustomTable(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.err.println("ERROR: Tabellenname darf nicht leer sein");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR: Verbindung mit DB ist nicht hergestellt");
            return false;
        }
        
        String tableName = name.trim();
        if (!tableName.matches("[A-Za-z_][A-Za-z0-9_]*")) {
            System.err.println("ERROR: Ungültiger Tabellenname: " + tableName);
            return false;
        }
        
        if (tableExists(tableName)) {
            System.err.println("ERROR: Tabelle '" + tableName + "' existiert bereits");
            return false;
        }
        
        String createTableSQL = "CREATE TABLE " + tableName
                + " (id INT IDENTITY(1,1) PRIMARY KEY)";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Tabelle '" + tableName + "' wurde erfolgreich erstellt");
            return true;
        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen der Tabelle '" + tableName + "': " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Erstellt die Tabelle "transport" mit den Feldern feld01..feld30 (alle String/NVARCHAR).
     * @return true, wenn die Tabelle erstellt wurde oder bereits existiert
     */
    public boolean createTransportTable() {
        if (!ensureConnection()) {
            System.err.println("ERROR: Verbindung mit DB ist nicht hergestellt");
            return false;
        }

        if (tableExists(transportTab)) {
            System.out.println("Tabelle '" + transportTab + "' existiert bereits");
            return true;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE ").append(transportTab).append(" (")
          .append("id INT IDENTITY(1,1) PRIMARY KEY,");
        for (int i = 1; i <= TRANSPORT_FELD_COUNT; i++) {
            String feldName = String.format("feld%02d", i);
            sb.append(feldName).append(" NVARCHAR(255),");
        }
        sb.append("created_at DATETIME2 DEFAULT GETDATE(),")
          .append("updated_at DATETIME2 DEFAULT GETDATE())");

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sb.toString());
            System.out.println("Tabelle '" + transportTab + "' wurde erfolgreich erstellt");
            return true;
        } catch (SQLException e) {
            System.err.println("Fehler beim Erstellen der Tabelle '" + transportTab + "': " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Fügt einen Datensatz in die Tabelle "transport" ein.
     * values[0] -> feld01, values[1] -> feld02, ... values[29] -> feld30
     * @param values 30 String-Werte (null erlaubt)
     * @return generierte ID (>0) bei Erfolg, sonst -1
     */
    public int addTransportRecord(String[] values) {
        if (values == null || values.length != TRANSPORT_FELD_COUNT) {
            System.err.println("ERROR: Für transport sind genau 30 Felder erforderlich");
            return -1;
        }
        if (!ensureConnection()) {
            System.err.println("ERROR: Verbindung mit DB ist nicht hergestellt");
            return -1;
        }
        if (!tableExists(transportTab) && !createTransportTable()) {
            return -1;
        }

        StringBuilder cols = new StringBuilder();
        StringBuilder vals = new StringBuilder();
        for (int i = 1; i <= TRANSPORT_FELD_COUNT; i++) {
            if (i > 1) {
                cols.append(", ");
                vals.append(", ");
            }
            cols.append(String.format("feld%02d", i));
            vals.append("?");
        }
        String sql = "INSERT INTO " + transportTab + " (" + cols + ") VALUES (" + vals + ")";

        try (PreparedStatement pstmt = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < TRANSPORT_FELD_COUNT; i++) {
                pstmt.setString(i + 1, values[i]);
            }
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        System.out.println("Transport-Datensatz erfolgreich hinzugefügt, ID=" + id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Einfügen in Tabelle '" + transportTab + "': " + e.getMessage());
            e.printStackTrace();
        }

        return -1;
    }

    /**
     * Sucht Datensätze in "transport" nach Text über alle Felder feld01..feld30.
     * Ergebniszeile: index 0 = id, index 1..30 = feld01..feld30
     * @param searchText Suchtext; leer/null liefert alle Datensätze
     * @return Liste der gefundenen Datensätze
     */
    public ArrayList<String[]> searchTransportRecords(String searchText) {
        ArrayList<String[]> result = new ArrayList<>();
        if (!ensureConnection()) {
            System.err.println("ERROR: Verbindung mit DB ist nicht hergestellt");
            return result;
        }
        if (!tableExists(transportTab)) {
            System.err.println("ERROR: Tabelle '" + transportTab + "' existiert nicht");
            return result;
        }

        String normalized = searchText == null ? "" : searchText.trim();
        StringBuilder sql = new StringBuilder("SELECT id");
        for (int i = 1; i <= TRANSPORT_FELD_COUNT; i++) {
            sql.append(", ").append(String.format("feld%02d", i));
        }
        sql.append(" FROM ").append(transportTab);

        boolean withFilter = !normalized.isEmpty();
        if (withFilter) {
            sql.append(" WHERE ");
            for (int i = 1; i <= TRANSPORT_FELD_COUNT; i++) {
                if (i > 1) {
                    sql.append(" OR ");
                }
                sql.append("ISNULL(").append(String.format("feld%02d", i)).append(", N'') LIKE ?");
            }
        }
        sql.append(" ORDER BY id DESC");

        try (PreparedStatement pstmt = connection.prepareStatement(sql.toString())) {
            if (withFilter) {
                for (int i = 1; i <= TRANSPORT_FELD_COUNT; i++) {
                    pstmt.setString(i, "%" + normalized + "%");
                }
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String[] row = new String[TRANSPORT_FELD_COUNT + 1];
                    row[0] = String.valueOf(rs.getInt("id"));
                    for (int i = 1; i <= TRANSPORT_FELD_COUNT; i++) {
                        row[i] = rs.getString(String.format("feld%02d", i));
                    }
                    result.add(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler bei Suche in Tabelle '" + transportTab + "': " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Aktualisiert einen Datensatz in "transport" nach ID.
     * values[0] -> feld01, values[1] -> feld02, ... values[29] -> feld30
     * @param id ID des Datensatzes
     * @param values 30 String-Werte
     * @return true bei Erfolg
     */
    public boolean updateTransportRecord(int id, String[] values) {
        if (id <= 0) {
            System.err.println("ERROR: ID muss groesser als 0 sein");
            return false;
        }
        if (values == null || values.length != TRANSPORT_FELD_COUNT) {
            System.err.println("ERROR: Für transport sind genau 30 Felder erforderlich");
            return false;
        }
        if (!ensureConnection()) {
            System.err.println("ERROR: Verbindung mit DB ist nicht hergestellt");
            return false;
        }
        if (!tableExists(transportTab)) {
            System.err.println("ERROR: Tabelle '" + transportTab + "' existiert nicht");
            return false;
        }

        StringBuilder sql = new StringBuilder("UPDATE " + transportTab + " SET ");
        for (int i = 1; i <= TRANSPORT_FELD_COUNT; i++) {
            if (i > 1) {
                sql.append(", ");
            }
            sql.append(String.format("feld%02d", i)).append(" = ?");
        }
        sql.append(", updated_at = GETDATE() WHERE id = ?");

        try (PreparedStatement pstmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            for (int i = 0; i < TRANSPORT_FELD_COUNT; i++) {
                pstmt.setString(paramIndex++, values[i]);
            }
            pstmt.setInt(paramIndex, id);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Transport-Datensatz mit ID " + id + " erfolgreich aktualisiert");
                return true;
            }
            System.err.println("Datensatz mit ID " + id + " wurde nicht gefunden");
        } catch (SQLException e) {
            System.err.println("Fehler beim Aktualisieren von transport ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Liefert maximale Zeichenlängen für transport.feld01..feld30.
     * Key = Feldindex (1..30), Value = max. Länge in Zeichen.
     * Bei NVARCHAR(MAX) wird Integer.MAX_VALUE zurückgegeben.
     */
    public Map<Integer, Integer> getTransportFieldMaxLengths() {
        Map<Integer, Integer> maxLengths = new HashMap<>();
        if (!ensureConnection() || !tableExists(transportTab)) {
            return maxLengths;
        }

        String sql = "SELECT COLUMN_NAME, CHARACTER_MAXIMUM_LENGTH "
                + "FROM INFORMATION_SCHEMA.COLUMNS "
                + "WHERE TABLE_NAME = ? AND COLUMN_NAME LIKE 'feld__'";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, transportTab);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String columnName = rs.getString("COLUMN_NAME");
                    if (columnName == null || !columnName.matches("feld\\d{2}")) {
                        continue;
                    }
                    int index = Integer.parseInt(columnName.substring(4));
                    int rawLength = rs.getInt("CHARACTER_MAXIMUM_LENGTH");
                    if (rs.wasNull() || rawLength < 0) {
                        maxLengths.put(index, Integer.MAX_VALUE);
                    } else {
                        maxLengths.put(index, rawLength);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen der Spaltenlängen von transport: " + e.getMessage());
        }
        return maxLengths;
    }

    /**
     * Löscht einen Datensatz aus "transport" nach ID.
     * @param id ID des zu löschenden Datensatzes
     * @return true bei Erfolg
     */
    public boolean deleteTransportRecord(int id) {
        if (id <= 0) {
            System.err.println("ERROR: ID muss groesser als 0 sein");
            return false;
        }
        if (!ensureConnection()) {
            System.err.println("ERROR: Verbindung mit DB ist nicht hergestellt");
            return false;
        }
        if (!tableExists(transportTab)) {
            System.err.println("ERROR: Tabelle '" + transportTab + "' existiert nicht");
            return false;
        }

        String sql = "DELETE FROM " + transportTab + " WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Transport-Datensatz mit ID " + id + " erfolgreich gelöscht");
                return true;
            }
            System.err.println("Datensatz mit ID " + id + " wurde nicht gefunden");
        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen von transport ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Importiert Daten aus Excel in Tabelle "transport".
     * Regel:
     *  - Zeile 1 (Index 0): DB-Feldnamen (feld01..feld30)
     *  - Zeile 2 (Index 1): Spaltenüberschriften (werden ignoriert)
     *  - ab Zeile 3 (Index 2): Datenzeilen
     *
     * @param filePath Pfad zur Excel-Datei (.xlsx/.xls)
     * @return Importbericht
     */
    public String importTransportFromExcel(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return "Fehler: Dateipfad ist leer.";
        }
        if (!ensureConnection()) {
            return "Fehler: Keine Verbindung mit DB.";
        }
        if (!tableExists(transportTab) && !createTransportTable()) {
            return "Fehler: Tabelle 'transport' konnte nicht erstellt werden.";
        }

        Workbook workbook = null;
        FileInputStream inputStream = null;
        int imported = 0;
        int skipped = 0;
        int failed = 0;

        try {
            String lower = filePath.toLowerCase();
            if (lower.endsWith(".xlsx")) {
                inputStream = new FileInputStream(filePath);
                workbook = new XSSFWorkbook(inputStream);
            } else if (lower.endsWith(".xls")) {
                inputStream = new FileInputStream(filePath);
                workbook = new HSSFWorkbook(inputStream);
            } else {
                return "Fehler: Nicht unterstütztes Dateiformat. Bitte .xlsx oder .xls verwenden.";
            }

            Sheet sheet = workbook.getSheetAt(0);
            if (sheet == null) {
                return "Fehler: Keine Tabellenblätter in der Datei gefunden.";
            }

            java.util.Map<Integer, Integer> excelColToFeldIndex = new java.util.HashMap<>();
            String mappingMode = "column-header-row";
            int detectedHeaderRow = detectTransportHeaderRow(sheet, 12);

            // Primärer Modus: Zuordnung nach bekannten Spaltenüberschriften.
            if (detectedHeaderRow >= 0) {
                Row headerRow = sheet.getRow(detectedHeaderRow);
                short firstCell = headerRow.getFirstCellNum();
                short lastCell = headerRow.getLastCellNum();
                if (firstCell >= 0 && lastCell >= 0) {
                    for (int col = firstCell; col < lastCell; col++) {
                        String header = getCellValueAsString(headerRow.getCell(col));
                        int feldIndex = resolveTransportFeldIndexByHeader(header);
                        if (feldIndex >= 0) {
                            excelColToFeldIndex.put(col, feldIndex);
                        }
                    }
                }
            }

            // Fallback 1: alte feldXX-Zeile, falls vorhanden.
            if (excelColToFeldIndex.isEmpty()) {
                int detectedFieldRow = detectTransportFieldRow(sheet, 8);
                if (detectedFieldRow >= 0) {
                    Row dbFieldsRow = sheet.getRow(detectedFieldRow);
                    short firstCell = dbFieldsRow.getFirstCellNum();
                    short lastCell = dbFieldsRow.getLastCellNum();
                    if (firstCell >= 0 && lastCell >= 0) {
                        for (int col = firstCell; col < lastCell; col++) {
                            String fieldName = getCellValueAsString(dbFieldsRow.getCell(col));
                            int feldIndex = parseTransportFieldIndex(fieldName);
                            if (feldIndex >= 0) {
                                excelColToFeldIndex.put(col, feldIndex);
                            }
                        }
                    }
                    if (!excelColToFeldIndex.isEmpty()) {
                        mappingMode = "feld-row-fallback";
                        detectedHeaderRow = detectedFieldRow + 1; // Daten dann +1
                    }
                }
            }

            // Fallback 2: erste 30 Spalten positionsbasiert.
            if (excelColToFeldIndex.isEmpty()) {
                mappingMode = "fallback-first-30-columns";
                for (int i = 0; i < TRANSPORT_FELD_COUNT; i++) {
                    excelColToFeldIndex.put(i, i);
                }
            }

            int lastRowNum = sheet.getLastRowNum();
            int dataStartRow = (detectedHeaderRow >= 0) ? (detectedHeaderRow + 1) : 2;
            for (int rowIdx = dataStartRow; rowIdx <= lastRowNum; rowIdx++) {
                Row row = sheet.getRow(rowIdx);
                if (row == null) {
                    skipped++;
                    continue;
                }

                String[] values = new String[TRANSPORT_FELD_COUNT];
                for (java.util.Map.Entry<Integer, Integer> entry : excelColToFeldIndex.entrySet()) {
                    int excelCol = entry.getKey();
                    int feldIdx = entry.getValue();
                    values[feldIdx] = getCellValueAsString(row.getCell(excelCol));
                }

                if (!transportValuesHaveContent(values)) {
                    skipped++;
                    continue;
                }

                int newId = addTransportRecord(values);
                if (newId > 0) {
                    imported++;
                } else {
                    failed++;
                }
            }

            StringBuilder report = new StringBuilder();
            report.append("Import in Tabelle 'transport' abgeschlossen.\n");
            report.append("Datei: ").append(filePath).append("\n");
            report.append("Mapping-Modus: ").append(mappingMode).append("\n");
            report.append("Erkannte Headerzeile: ").append(detectedHeaderRow >= 0 ? (detectedHeaderRow + 1) : "nicht gefunden").append("\n");
            report.append("Datenstart (Zeile): ").append(dataStartRow + 1).append("\n");
            report.append("Erkannte Feldspalten: ").append(excelColToFeldIndex.size()).append("\n");
            report.append("Importiert: ").append(imported).append("\n");
            report.append("Übersprungen: ").append(skipped).append("\n");
            report.append("Fehler: ").append(failed);
            return report.toString();

        } catch (Exception e) {
            return "Fehler beim Import von Excel in 'transport': " + e.getMessage();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException ignored) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private int parseTransportFieldIndex(String fieldName) {
        if (fieldName == null) {
            return -1;
        }
        String normalized = fieldName.trim().toLowerCase();
        // Robust gegen Excel-Varianten wie "'feld01", "feld 01", "FELD01:" usw.
        normalized = normalized.replace('\u2019', '\'');
        normalized = normalized.replaceAll("[^a-z0-9]", "");
        java.util.regex.Matcher m = java.util.regex.Pattern.compile("feld(\\d{1,2})").matcher(normalized);
        if (!m.find()) {
            return -1;
        }
        int idx;
        try {
            idx = Integer.parseInt(m.group(1));
        } catch (NumberFormatException ex) {
            return -1;
        }
        if (idx < 1 || idx > TRANSPORT_FELD_COUNT) {
            return -1;
        }
        return idx - 1;
    }

    private int detectTransportFieldRow(Sheet sheet, int maxRowsToScan) {
        if (sheet == null) {
            return -1;
        }
        int lastRow = Math.min(sheet.getLastRowNum(), Math.max(0, maxRowsToScan - 1));
        int bestRow = -1;
        int bestCount = 0;
        for (int rowIdx = 0; rowIdx <= lastRow; rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            if (row == null) {
                continue;
            }
            short firstCell = row.getFirstCellNum();
            short lastCell = row.getLastCellNum();
            if (firstCell < 0 || lastCell < 0) {
                continue;
            }
            int count = 0;
            for (int col = firstCell; col < lastCell; col++) {
                String value = getCellValueAsString(row.getCell(col));
                if (parseTransportFieldIndex(value) >= 0) {
                    count++;
                }
            }
            if (count > bestCount) {
                bestCount = count;
                bestRow = rowIdx;
            }
        }
        return bestCount > 0 ? bestRow : -1;
    }

    private static final String[] TRANSPORT_HEADERS = new String[] {
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

    private int resolveTransportFeldIndexByHeader(String header) {
        String normalized = normalizeTransportHeader(header);
        if (normalized.isEmpty()) {
            return -1;
        }
        for (int i = 0; i < TRANSPORT_HEADERS.length; i++) {
            if (normalizeTransportHeader(TRANSPORT_HEADERS[i]).equals(normalized)) {
                return i;
            }
        }
        // Erlaubt auch direkte Angabe f23..f30 oder feld01..feld30.
        java.util.regex.Matcher mF = java.util.regex.Pattern.compile("^f(\\d{1,2})$").matcher(normalized);
        if (mF.find()) {
            int idx = Integer.parseInt(mF.group(1));
            if (idx >= 1 && idx <= TRANSPORT_FELD_COUNT) {
                return idx - 1;
            }
        }
        int byFeld = parseTransportFieldIndex(normalized);
        return byFeld;
    }

    private int detectTransportHeaderRow(Sheet sheet, int maxRowsToScan) {
        if (sheet == null) {
            return -1;
        }
        int lastRow = Math.min(sheet.getLastRowNum(), Math.max(0, maxRowsToScan - 1));
        int bestRow = -1;
        int bestScore = 0;
        for (int rowIdx = 0; rowIdx <= lastRow; rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            if (row == null) {
                continue;
            }
            short firstCell = row.getFirstCellNum();
            short lastCell = row.getLastCellNum();
            if (firstCell < 0 || lastCell < 0) {
                continue;
            }
            int score = 0;
            java.util.HashSet<Integer> matched = new java.util.HashSet<>();
            for (int col = firstCell; col < lastCell; col++) {
                String value = getCellValueAsString(row.getCell(col));
                int idx = resolveTransportFeldIndexByHeader(value);
                if (idx >= 0 && !matched.contains(idx)) {
                    score++;
                    matched.add(idx);
                }
            }
            if (score > bestScore) {
                bestScore = score;
                bestRow = rowIdx;
            }
        }
        // Минимум 6 совпадений считаем валидной строкой заголовков.
        return bestScore >= 6 ? bestRow : -1;
    }

    private String normalizeTransportHeader(String value) {
        if (value == null) {
            return "";
        }
        String v = value.trim().toLowerCase();
        v = v.replace("ä", "a").replace("ö", "o").replace("ü", "u").replace("ß", "ss");
        v = v.replace('\u2019', '\'');
        v = v.replaceAll("[^a-z0-9]", "");
        return v;
    }

    private boolean transportValuesHaveContent(String[] values) {
        if (values == null) {
            return false;
        }
        for (String value : values) {
            if (value != null && !value.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Fügt einer vorhandenen Tabelle eine neue Spalte hinzu.
     * @param tableName Name der Tabelle
     * @param columnName Name der neuen Spalte
     * @param dataType SQL-Datentyp
     * @param columnSize optionale Spaltengröße für Texttypen
     * @return true, wenn die Spalte erfolgreich hinzugefügt wurde
     */
    public boolean addColumnToTable(String tableName, String columnName, String dataType, Integer columnSize) {
        if (tableName == null || tableName.trim().isEmpty()
                || columnName == null || columnName.trim().isEmpty()
                || dataType == null || dataType.trim().isEmpty()) {
            System.err.println("ERROR: Tabellenname, Spaltenname und Datentyp sind erforderlich");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR: Verbindung mit DB ist nicht hergestellt");
            return false;
        }
        
        String cleanTableName = tableName.trim();
        String cleanColumnName = columnName.trim();
        String cleanDataType = dataType.trim().toUpperCase();
        
        if (!cleanTableName.matches("[A-Za-z_][A-Za-z0-9_]*")
                || !cleanColumnName.matches("[A-Za-z_][A-Za-z0-9_]*")) {
            System.err.println("ERROR: Ungültiger Tabellen- oder Spaltenname");
            return false;
        }
        
        if (!tableExists(cleanTableName)) {
            System.err.println("ERROR: Tabelle '" + cleanTableName + "' existiert nicht");
            return false;
        }
        
        String[] allowedTypes = {"INT", "BIGINT", "BIT", "DATE", "DATETIME", "FLOAT", "VARCHAR", "NVARCHAR", "CHAR", "NCHAR"};
        boolean allowed = false;
        for (String type : allowedTypes) {
            if (type.equals(cleanDataType)) {
                allowed = true;
                break;
            }
        }
        if (!allowed) {
            System.err.println("ERROR: Nicht unterstützter Datentyp '" + cleanDataType + "'");
            return false;
        }
        
        boolean needsSize = "VARCHAR".equals(cleanDataType)
                || "NVARCHAR".equals(cleanDataType)
                || "CHAR".equals(cleanDataType)
                || "NCHAR".equals(cleanDataType);
        
        StringBuilder columnDefinition = new StringBuilder(cleanDataType);
        if (needsSize) {
            int size = (columnSize != null && columnSize > 0) ? columnSize : 255;
            columnDefinition.append("(").append(size).append(")");
        }
        
        String sql = "ALTER TABLE " + cleanTableName
                + " ADD " + cleanColumnName + " " + columnDefinition.toString();
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Spalte '" + cleanColumnName + "' wurde zu Tabelle '" + cleanTableName + "' hinzugefügt");
            return true;
        } catch (SQLException e) {
            System.err.println("Fehler beim Hinzufügen der Spalte '" + cleanColumnName + "': " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Атрымлівае ўнікальныя нумары заказаў з табліцы lieferung
     * для запаўнення выпадаючых спісаў у формах.
     */
    public ArrayList<String> getDistinctBestellNummern() {
        ArrayList<String> list = new ArrayList<>();
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return list;
        }
        // При загрузке в форму удаляем технический суффикс "SAP",
        // чтобы в выпадающем списке не было дублей одного заказа.
        Set<String> normalized = new TreeSet<>();

        String sql = "SELECT DISTINCT LTRIM(RTRIM(bestellNummer)) AS bestellNummer "
                + "FROM " + tabLiefName + " "
                + "WHERE LTRIM(RTRIM(ISNULL(bestellNummer, ''))) <> '' "
                + "ORDER BY LTRIM(RTRIM(bestellNummer)) ASC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String v = rs.getString("bestellNummer");
                if (v != null && !v.trim().isEmpty()) {
                    String cleaned = normalizeBestellnummerForUi(v);
                    if (!cleaned.isEmpty()) {
                        normalized.add(cleaned);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Laden bestellNummer-Liste: " + e.getMessage());
            e.printStackTrace();
        }
        list.addAll(normalized);
        return list;
    }

    /**
     * Возвращает поставку по выбранному значению bestellNummer из UI
     * (с учетом возможного технического суффикса SAP в БД).
     */
    public Lieferung getLieferungByBestellnummerForUi(String bestellnummerUi) {
        if (bestellnummerUi == null || bestellnummerUi.trim().isEmpty()) {
            return null;
        }
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return null;
        }

        String sql = "SELECT TOP 1 id, anfordNummer, lieferantName, bestellNummer "
                + "FROM " + tabLiefName + " "
                + "WHERE LTRIM(RTRIM(CASE "
                + "WHEN UPPER(RIGHT(LTRIM(RTRIM(bestellNummer)), 5)) = '(SAP)' "
                + "THEN LEFT(LTRIM(RTRIM(bestellNummer)), LEN(LTRIM(RTRIM(bestellNummer))) - 5) "
                + "WHEN UPPER(RIGHT(LTRIM(RTRIM(bestellNummer)), 3)) = 'SAP' "
                + "THEN LEFT(LTRIM(RTRIM(bestellNummer)), LEN(LTRIM(RTRIM(bestellNummer))) - 3) "
                + "ELSE LTRIM(RTRIM(bestellNummer)) END)) = ? "
                + "ORDER BY updated_at DESC, id DESC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, bestellnummerUi.trim());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Lieferung lieferung = new Lieferung();
                    lieferung.setId(rs.getInt("id"));
                    lieferung.setAnfordNummer(rs.getString("anfordNummer"));
                    lieferung.setLieferantName(rs.getString("lieferantName"));
                    lieferung.setBestellNummer(rs.getString("bestellNummer"));
                    return lieferung;
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Laden Lieferung zu bestellNummer: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static String normalizeBestellnummerForUi(String raw) {
        if (raw == null) {
            return "";
        }
        String v = raw.trim();
        // Иногда после импорта в конце остается техническая метка SAP.
        v = v.replaceFirst("(?i)\\s*\\(?SAP\\)?\\s*$", "").trim();
        return v;
    }
    
      public ArrayList<Lieferung> getAllLieferungen() {
        ArrayList<Lieferung> lifList = new ArrayList<>();
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return lifList;
        }
        
        String selectSQL = "SELECT * FROM "+ tabLiefName+" ORDER BY id DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            
            while (rs.next()) {
                String beschreibung = rs.getString("beschreibung");
                String mengeStr = rs.getString("menge");
                float menge = Float.parseFloat(mengeStr != null ? mengeStr : "0");
                
                
                Lieferung lieferung = new Lieferung( beschreibung, menge );
                
                // Заполняем объект данными из ResultSet в соответствии со схемой createTable
                lieferung.setId(rs.getInt("id"));
                lieferung.setNeu(rs.getBoolean("neu"));
                lieferung.setAktiv(rs.getBoolean("aktiv"));
               
                lieferung.setUnterschriftMH(rs.getBoolean("unterschriftMH"));
                 lieferung.setUnterschriftKunde(rs.getBoolean("unterschriftKunde"));
                lieferung.setInvest_Leasing(rs.getBoolean("invest_Leasing"));
                 lieferung.setAnEinkaufWeiterGeleitetMH(rs.getBoolean("anEinkaufWeiterGeleitetMH"));
                lieferung.setDe_eu(rs.getString("de_eu"));
                   lieferung.setEinhMenge(rs.getString("einhMenge"));
               lieferung.setMenge(menge);
               // System.out.println("IS NEU??  :" + lieferung.isNeu() + rs.getBoolean("neu"));
               
                lieferung.setAgrNummer(rs.getString("agrNummer"));
                lieferung.setAnfordNummer(rs.getString("anfordNummer"));
              
                lieferung.setBeschreibung(beschreibung);
              
                lieferung.setProjectName(rs.getString("abteilung"));
                
                // Обработка дат
                lieferung.setAnforderungDatum(rs.getString("anforderungDatum"));
                
                lieferung.setWunschLieferDatum(rs.getString("wunschLieferDatum"));
                
                lieferung.setAnfordPerson(rs.getString("anfordPerson"));
               String summeStr = rs.getString("summeMat_Leist_cents");
               lieferung.setSummeMat_Leist_cents(Float.parseFloat(summeStr != null ? summeStr : "0"));
               
              //  System.out.println("Bestellt Am beim lesen aus DB : " + rs.getString("bestelltAm"));
              
                String preisStr = rs.getString("preisEinkauf");
                lieferung.setPreisEinkauf(Float.parseFloat(preisStr != null ? preisStr : "0"));
                lieferung.setLieferantName(rs.getString("lieferantName"));
                
                lieferung.setBestelltAm(rs.getString("bestelltAm"));
                 lieferung.setBestellNummer(rs.getString("bestellNummer"));
                lieferung.setStatusEinkauf(rs.getString("statusEinkauf"));
                
               
                lieferung.setWarenEingangTS(rs.getString("warenEingangTS"));
                
                lieferung.setWareneingangTS_plan(rs.getString("wareneingangTS_plan"));
                
                lieferung.setWareneingangTS_Fakt(rs.getString("wareneingangTS_Fakt"));
                
                lieferung.setAuslieferungNachPlan(rs.getString("auslieferungNachPlan"));
                
                lieferung.setZahlungsbedingungen(rs.getString("zahlungsbedingungen"));
                lieferung.setKommentEinkauf(rs.getString("kommentEinkauf"));
                
                lieferung.setWunschterminWareneingang(rs.getString("terminWareneingangPlan"));
                
                lieferung.setWareneingang_Fakt(rs.getString("wareneingang_Fakt"));
                
                lieferung.setKommentarBS(rs.getString("kommentarBS"));
                lieferung.setStatusAuslieferungBS(rs.getString("statusAuslieferungBS"));
                String gewichtStr = rs.getString("gewicht");
                lieferung.setGewicht(Float.parseFloat(gewichtStr != null ? gewichtStr : "0"));
                lieferung.setEinhMasse(rs.getString("einhMasse"));
                String laengeStr = rs.getString("laenge");
                lieferung.setLaenge(Float.parseFloat(laengeStr != null ? laengeStr : "0"));
                String breiteStr = rs.getString("breite");
                lieferung.setBreite(Float.parseFloat(breiteStr != null ? breiteStr : "0"));
                String hoeheStr = rs.getString("hoehe");
                lieferung.setHoehe(Float.parseFloat(hoeheStr != null ? hoeheStr : "0"));
                lieferung.setEinheitLBH(rs.getString("einheitLBH"));
                lieferung.setKolli(rs.getString("kolli"));
                lieferung.setPackListe(rs.getString("packListe"));
                lieferung.setContainerNr(rs.getString("containerNr"));
                lieferung.setRgNummeranAG(rs.getString("rgNummeranAG"));
                
                lieferung.setDatumBS(rs.getString("rechnungDatum"));
                
                lieferung.setIstWeitergerechnetAnAG(rs.getBoolean("istWeitergerechnetAnAG"));
                String summeWeiterStr = rs.getString("summeWeitergerechnetanAG_cents");
                lieferung.setSummeWeitergerechnetanAG_cents(Float.parseFloat(summeWeiterStr != null ? summeWeiterStr : "0"));
                String summeNachStr = rs.getString("summe_nach_Weiterrechnung");
                lieferung.setSumme_nach_Weiterrechnung(Float.parseFloat(summeNachStr != null ? summeNachStr : "0"));
                String kundenStr = rs.getString("kundenInRechnungStellen");
                lieferung.setKundenInRechnungStellen(Float.parseFloat(kundenStr != null ? kundenStr : "0"));
                
                // Обработка информационных полей
                lieferung.setInfo01(rs.getString("info01"));
                lieferung.setInfo02(rs.getString("info02"));
                lieferung.setInfo03(rs.getString("info03"));
                lieferung.setInfo04(rs.getString("info04"));
                lieferung.setInfo05(rs.getString("info05"));
                
                // Устанавливаем текст кнопки
                lieferung.setText(lieferung.getBeschreibung());
                
                // Добавляем объект в список
                lifList.add(lieferung);
            }
            
           // System.out.println( lifList.size()+"  Elementen aus DB " );
            
        } catch (SQLException e) {
            System.err.println("ERROR beim Lesen aller Elementen: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lifList;
    }
    
    
    
    /**
     * Проверяет существование таблицы в базе данных
     * @param tableName имя таблицы для проверки
     * @return true если таблица существует, false в противном случае
     */
   
    /**
     * Открывает диалог для выбора Excel файла
     * @return путь к выбранному файлу или null, если файл не выбран
     */
    public String selectExcelFile() {
        JFileChooser fileChooser = new JFileChooser();
        
        // Устанавливаем фильтр для Excel файлов
        FileNameExtensionFilter excelFilter = new FileNameExtensionFilter(
            "Excel files (*.xlsx, *.xls)", "xlsx", "xls"
        );
        fileChooser.setFileFilter(excelFilter);
        
        // Устанавливаем заголовок диалога
        fileChooser.setDialogTitle("Wählen Sie Excel Datei fürs Import");
        
        // Показываем диалог
        int result = fileChooser.showOpenDialog(null);
        
        // Проверяем, что пользователь выбрал файл
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        
        return null;
    }
    
    private static final class ExcelImportResult {
        int totalLieferungen;
        int addedLieferungen;
        int skippedExistingLieferungen;
        int failedLieferungen;
        int addedArtikel;
        int failedArtikel;
        ArrayList<String> examples = new ArrayList<>();
    }

    /**
     * Метод для обработки Excel файла
     * @param filePath путь к Excel файлу
     */
    public void processExcelFile(String filePath) {
        processExcelFileWithResult(filePath);
    }

    /**
     * Выполняет Excel-Import и возвращает текстовый отчет о фактических изменениях в БД.
     * @param filePath путь к Excel файлу
     * @return отчет для информационного окна
     */
    public String processExcelFileWithReport(String filePath) {
        ExcelImportResult result = processExcelFileWithResult(filePath);
        return buildExcelImportResultText(result, filePath);
    }

    private ExcelImportResult processExcelFileWithResult(String filePath) {
        ExcelImportResult result = new ExcelImportResult();
        if (filePath == null || filePath.trim().isEmpty()) {
            System.out.println("Filepath ist nicht gegeben");
            result.examples.add("Keine Datei ausgewaehlt.");
            return result;
        }

        System.out.println("Bearbeiten Excel Datei: " + filePath);
        ArrayList<Lieferung> listSAP = new ArrayList<>();
        FileInputStream inputStream = null;
        Workbook workbook = null;

        try {
            if (filePath.endsWith(".xlsx")) {
                inputStream = new FileInputStream(filePath);
                workbook = new XSSFWorkbook(inputStream);
            } else if (filePath.endsWith(".xls")) {
                inputStream = new FileInputStream(filePath);
                workbook = new HSSFWorkbook(inputStream);
            } else {
                System.out.println("Falsche Fileformat. Darf nur .xlsx oder .xls");
                result.examples.add("Falsches Dateiformat. Nur .xlsx oder .xls sind erlaubt.");
                return result;
            }

            Sheet sheet = workbook.getSheetAt(0);
            Lieferung currentLieferung = null;
            ArrayList<Artikel> artikelBatch = new ArrayList<>();
            String currentEinkaufBelegKey = "";
            int abrufdokuCol = findHeaderColumnIndex(sheet, SAP_ABRUFDOKU_HEADER, 20);
            Set<Integer> rowsWithAbrufdokuImage = collectPictureRowsForColumn(sheet, abrufdokuCol);

            int firstDataRow = detectSapDataStartRow(sheet);
            for (int rowIndex = firstDataRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }

                try {
                    String position = readSapPositionCell(row);

                    String proName = getCellValueAsString(row.getCell(3));
                    String spalteO = getCellValueAsString(row.getCell(14));
                    String spalteP = getCellValueAsString(row.getCell(15));
                    String[] datumUndRest = parseSapSpaltePDatumUndRest(spalteP);
                    // In eurer SAP-Datei liegt das Belegdatum im Regelfall in Spalte O.
                    String belegDatum = extractDateToken(spalteO);
                    if (belegDatum.isEmpty()) {
                        // Fallback: manche Exporte haben Datum in P
                        belegDatum = extractDateToken(spalteP);
                    }
                    if (belegDatum.isEmpty()) {
                        belegDatum = !datumUndRest[0].isEmpty() ? datumUndRest[0] : (spalteO != null ? spalteO.trim() : "");
                    }
                    String lieferant = !datumUndRest[1].isEmpty() ? datumUndRest[1] : (spalteP != null ? spalteP.trim() : "");
                    String kurztext = getCellValueAsString(row.getCell(17));
                    String lagerort = getCellValueAsString(row.getCell(23));
                    String spalteY = getCellValueAsString(row.getCell(24));
                    float[] mengeHolder = new float[1];
                    String[] einheitHolder = new String[1];
                    parseSapMengeUndEinheitAusSpalteY(spalteY, mengeHolder, einheitHolder);
                    float mengeSAP = mengeHolder[0];
                    String einheitSAP = einheitHolder[0];
                    if (mengeSAP == 0f && row.getCell(24) != null) {
                        mengeSAP = getCellValueAsFloat(row.getCell(24));
                    }
                    if ((einheitSAP == null || einheitSAP.isEmpty()) && row.getCell(25) != null) {
                        einheitSAP = getCellValueAsString(row.getCell(25));
                    }
                    String spalteAA = getCellValueAsString(row.getCell(26));
                    float preisSAP = parseSapPreisAusSpalteAA(spalteAA);
                    if (preisSAP == 0f && row.getCell(26) != null) {
                        preisSAP = getCellValueAsFloat(row.getCell(26));
                    }

                    if (position.regionMatches(true, 0, "Einkauf", 0, "Einkauf".length())) {
                        finishCurrentSapLieferung(listSAP, currentLieferung, artikelBatch);
                        currentLieferung = new Lieferung();
                        currentLieferung.setPreisEinkauf(0f);
                        currentLieferung.setSummeMat_Leist_cents(0f);
                        currentLieferung.setAktiv(true);
                        currentLieferung.setAnEinkaufWeiterGeleitetMH(true);
                        artikelBatch = new ArrayList<>();
                        currentEinkaufBelegKey = sapBelegNummerAusEinkaufsbelegZeile(position);
                      //  System.out.println(position + " Position in der Zeile!!");
                        if (!currentEinkaufBelegKey.isEmpty()) {
                            String tagged = currentEinkaufBelegKey;
                            currentLieferung.setAnfordNummer(tagged);
                          currentLieferung.setAgrNummer("__||");
                            currentLieferung.setBestellNummer(tagged);
                             currentLieferung.setProjectName(proName != null ? proName.trim() : "");
                   
                        }
                        } else {
                        if (currentLieferung == null || !lieferungHasNonEmptySapKey(currentLieferung)) {
                            continue;
                        }
                        if (position.isEmpty()) {
                            continue;
                        }
                        String posKey = sapArtikelPositionsSchluessel(position, currentEinkaufBelegKey);
                        if (posKey.isEmpty()) {
                            continue;
                        }
                        Artikel artikelSAP = new Artikel();
                        String safeProName = proName != null ? proName.trim() : "";
                        String safeLieferant = lieferant != null ? lieferant.trim() : "";
                        String safeKurztext = kurztext != null ? kurztext.trim() : "";
                        String safeLagerort = lagerort != null ? lagerort.trim() : "";
                        String safeEinheit = einheitSAP != null ? einheitSAP.trim() : "";
                        String bd = belegDatum != null ? belegDatum : "";
                        String bestellungMitDatum = buildBestellungMitDatum(posKey, bd);

                        artikelSAP.setBooking_Number(bestellungMitDatum);
                        artikelSAP.setSupplier(safeLieferant);
                        artikelSAP.setMaterial_DE(safeKurztext);
                        // Fallback: oft ist im SAP-Export nur eine Materialspalte vorhanden.
                        artikelSAP.setMaterial_EN(safeKurztext);
                        artikelSAP.setLagerOrt(safeLagerort);
                        artikelSAP.setMenge(mengeSAP);
                        boolean dokumentErkannt = rowsWithAbrufdokuImage.contains(rowIndex);
                        // Gelieferte Menge wird aus Bild-Hinweis in "Bestellentwicklung / Abrufdoku" abgeleitet.
                        artikelSAP.setMenge_Baustelle_gel(dokumentErkannt ? mengeSAP : 0f);
                        artikelSAP.setEinheit(safeEinheit);
                        artikelSAP.setPreisEuro(preisSAP);
                        // Summe je Artikel = Menge (Spalte Y) * Preis (Spalte AA)
                        float artikelSumme = mengeSAP * preisSAP;
                        float newSum = currentLieferung.getPreisEinkauf() + artikelSumme;
                        currentLieferung.setPreisEinkauf(newSum);
                        currentLieferung.setSummeMat_Leist_cents(newSum);
                        
                        artikelSAP.setAdd_Info(bd.isEmpty() ? "Belegdatum in SAP" : bd + " - Belegdatum in SAP");
                        artikelSAP.setOrder_Number(bestellungMitDatum);
                        // Чтобы в "verbrauchte Menge" не попадал номер заказа, не пишем его в info01.
                        artikelSAP.setInfo01("");
                        // Packliste/info02 wird beim Excel-Import bewusst nicht befüllt.
                        artikelSAP.setInfo03(safeProName);
                        // Für Lagerübersicht gleich befüllen, solange noch kein Wareneingang gebucht wurde.
                        artikelSAP.setLagerMenge(String.valueOf(mengeSAP));
                        if (dokumentErkannt) {
                            appendExample(result, "Dokument erkannt-> Gelieferte Menge gesetzt: "
                                    + posKey + " | " + safeKurztext);
                        }
                        artikelBatch.add(artikelSAP);

                        // Lieferant wie bei Datum aus den Artikelzeilen sammeln:
                        // eindeutige Werte, Reihenfolge beibehalten, Trennzeichen " - ".
                        currentLieferung.setLieferantName(
                                mergeUniqueLieferanten(currentLieferung.getLieferantName(), lieferant)
                        );

                        // Projektcode steht in einigen SAP-Dateien nur in Artikelzeilen.
                        currentLieferung.setProjectName(
                                mergeUniqueProjektCodes(currentLieferung.getProjectName(), proName)
                        );

                        // Belegdatum liegt in dieser Datei bei den Artikelzeilen unterhalb des Einkaufsbelegs.
                        // Für die Lieferung verwenden wir die spaeteste gefundene Datum.
                        String normalizedBd = normalizeBestelltAmForDb(bd);
                        String latest = pickLaterBestelltAm(currentLieferung.getBestelltAm(), normalizedBd);
                        if (latest != null && !latest.trim().isEmpty()) {
                            currentLieferung.setBestelltAm(latest);
                            currentLieferung.setBestellNummer(buildBestellungMitDatum(currentEinkaufBelegKey, latest));
                        }
                    }
                } catch (Exception e) {
                    System.err.println("ERROR beim bearbeiten der Zeile " + (rowIndex + 1) + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
            finishCurrentSapLieferung(listSAP, currentLieferung, artikelBatch);
            result.totalLieferungen = listSAP.size();

            persistSapLieferungenMitArtikeln(listSAP, result);

            System.out.println("Bearbeiten  Excel Datei ist beendet");
            System.out.println("In der List: " + listSAP.size() + "   -  Elements ");

        } catch (IOException e) {
            System.err.println("ERROR beim lesen Excel : " + e.getMessage());
            e.printStackTrace();
            result.examples.add("Fehler beim Lesen der Datei: " + safeTrim(e.getMessage()));
        } finally {
            try {
                if (workbook != null) {
                    workbook.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                System.err.println("ERROR beim schlissen der Datei: " + e.getMessage());
            }
        }
        return result;
    }

    /**
     * Dry-run Vorschau fuer Excel-Import (ohne DB-Update).
     * Zaehlt erkannte Einkaufsbelege und zeigt Beispiele.
     */
    public String previewProcessExcelFile(String filePath, int maxExamples) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return "Keine Datei ausgewaehlt.";
        }

        FileInputStream inputStream = null;
        Workbook workbook = null;
        try {
            if (filePath.endsWith(".xlsx")) {
                inputStream = new FileInputStream(filePath);
                workbook = new XSSFWorkbook(inputStream);
            } else if (filePath.endsWith(".xls")) {
                inputStream = new FileInputStream(filePath);
                workbook = new HSSFWorkbook(inputStream);
            } else {
                return "Falsches Dateiformat. Nur .xlsx oder .xls sind erlaubt.";
            }

            Sheet sheet = workbook.getSheetAt(0);
            int firstDataRow = detectSapDataStartRow(sheet);
            int totalRows = 0;
            int einkaufBlocks = 0;
            int shown = 0;
            int limit = Math.max(1, maxExamples);
            StringBuilder sb = new StringBuilder();
            sb.append("Excel-Import Dry-Run (ohne Speichern)\n");
            sb.append("Datei: ").append(filePath).append("\n\n");

            for (int rowIndex = firstDataRow; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) {
                    continue;
                }
                totalRows++;
                String position = readSapPositionCell(row);
                if (position == null) {
                    continue;
                }
                if (position.regionMatches(true, 0, "Einkauf", 0, "Einkauf".length())) {
                    einkaufBlocks++;
                    if (shown < limit) {
                        String beleg = sapBelegNummerAusEinkaufsbelegZeile(position);
                        String proName = getCellValueAsString(row.getCell(3));
                        sb.append("Block #").append(einkaufBlocks)
                          .append(" | Beleg=").append(beleg == null || beleg.isEmpty() ? "-" : beleg)
                          .append(" | Projekt=").append(proName == null || proName.trim().isEmpty() ? "-" : proName.trim())
                          .append("\n");
                        shown++;
                    }
                }
            }

            sb.append("\nZeilen geprueft: ").append(totalRows).append("\n");
            sb.append("Erkannte Einkaufsbloecke (voraussichtliche Lieferungen): ").append(einkaufBlocks).append("\n");
            sb.append("Beispiele angezeigt: ").append(Math.min(einkaufBlocks, limit))
              .append(" (Limit ").append(limit).append(")\n");
            if (einkaufBlocks > limit) {
                sb.append("... weitere ").append(einkaufBlocks - limit).append(" nicht angezeigt.\n");
            }
            return sb.toString();
        } catch (Exception e) {
            return "Fehler beim Excel-Dry-Run: " + e.getMessage();
        } finally {
            try {
                if (workbook != null) workbook.close();
                if (inputStream != null) inputStream.close();
            } catch (IOException ignore) { }
        }
    }

    /**
     * Ergänzt vorhandene Lieferungen aus einer Intranet-HTML-Tabelle (viewall):
     * Match über BestNr (Website) == bestellNummer (DB-Lieferung).
     *
     * Es wird ausschließlich BANF Nr. gesetzt:
     * - ID -> agrNummer (BANF Nr.)
     * und nur dann, wenn agrNummer in der Lieferung aktuell leer ist.
     *
     * @param htmlFilePath Pfad zur gespeicherten HTML-Datei
     * @return Anzahl aktualisierter Lieferungen
     */
    public int enrichLieferungenFromIntranetHtml(String htmlFilePath) {
        IntranetHtmlImportResult result = enrichLieferungenFromIntranetHtmlWithResult(htmlFilePath);
        return result.updatedCount;
    }

    public String enrichLieferungenFromIntranetHtmlWithReport(String htmlFilePath) {
        IntranetHtmlImportResult result = enrichLieferungenFromIntranetHtmlWithResult(htmlFilePath);
        return buildIntranetHtmlImportResultText(result, htmlFilePath);
    }

    private IntranetHtmlImportResult enrichLieferungenFromIntranetHtmlWithResult(String htmlFilePath) {
        IntranetHtmlImportResult result = new IntranetHtmlImportResult();
        ArrayList<Lieferung> lieferungen = getAllLieferungen();
        if (lieferungen == null || lieferungen.isEmpty()) {
            result.otherNotes.add("Keine Lieferungen in der Datenbank gefunden.");
            return result;
        }
        java.util.Map<String, Lieferung> byBestellnummer = new java.util.HashMap<>();
        for (Lieferung l : lieferungen) {
            if (l == null) continue;
            String key = normalizeCompareKey(l.getBestellNummer());
            if (!key.isEmpty() && !byBestellnummer.containsKey(key)) {
                byBestellnummer.put(key, l);
            }
        }
        if (byBestellnummer.isEmpty()) {
            result.otherNotes.add("Keine Lieferungen mit bestellNummer fuer den Abgleich vorhanden.");
            return result;
        }

        java.util.List<IntranetViewAllRow> rows = parseIntranetViewAllRows(htmlFilePath);
        result.totalRows = rows.size();
        for (IntranetViewAllRow row : rows) {
            if (row == null) continue;
            String bestNrKey = normalizeCompareKey(row.bestNr);
            if (bestNrKey.isEmpty()) {
                result.skippedWithoutBestNr++;
                continue;
            }

            Lieferung target = byBestellnummer.get(bestNrKey);
            if (target == null) {
                result.skippedNoMatchingLieferung++;
                continue;
            }
            result.matchedRows++;

            boolean changed = false;
            String targetKey = safeLieferungKey(target);

            if (isMissingAgrNummer(target.getAgrNummer()) && !isBlank(row.id)) {
                target.setAgrNummer(row.id.trim());
                changed = true;
            }

            if (!changed) {
                if (!isMissingAgrNummer(target.getAgrNummer())) {
                    result.skippedAlreadyHasBanf++;
                    appendListItem(result.alreadyPresentDetails,
                            "BANF bereits vorhanden: " + targetKey + ", BANF=" + nullToDash(target.getAgrNummer()));
                } else {
                    result.skippedWithoutHtmlId++;
                    appendListItem(result.missingDataDetails,
                            "Keine BANF-ID in HTML: " + targetKey + ", BestNr=" + nullToDash(row.bestNr));
                }
                continue;
            }

            if (updateLieferungAgrNummerById(target.getId(), target.getAgrNummer())) {
                result.updatedCount++;
                appendListItem(result.updatedDetails,
                        "Aktualisiert: " + targetKey + ", neue BANF=" + nullToDash(target.getAgrNummer()));
            } else {
                result.failedUpdates++;
                appendListItem(result.errorDetails,
                        "Fehler beim DB-Update: " + targetKey + ", BANF=" + nullToDash(target.getAgrNummer()));
            }
        }
        return result;
    }

    /**
     * Aktualisiert nur BANF Nr. (agrNummer) für eine vorhandene Lieferung.
     * Dadurch vermeiden wir Seiteneffekte eines Voll-Updates auf alle Felder.
     */
    private boolean updateLieferungAgrNummerById(int lieferungId, String agrNummer) {
        if (lieferungId <= 0) {
            return false;
        }
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return false;
        }

        String updateSql = "UPDATE " + tabLiefName + " SET agrNummer = ?, updated_at = GETDATE() WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(updateSql)) {
            ps.setString(1, agrNummer != null ? agrNummer.trim() : null);
            ps.setInt(2, lieferungId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Fehler beim Update BANF Nr (id=" + lieferungId + "): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Dry-run Vorschau (ohne DB-Update) für Intranet-HTML-Abgleich.
     * Zeigt bis zu {@code maxRows} passende Datensätze.
     */
    public String previewEnrichLieferungenFromIntranetHtml(String htmlFilePath, int maxRows) {
        ArrayList<Lieferung> lieferungen = getAllLieferungen();
        if (lieferungen == null || lieferungen.isEmpty()) {
            return "Keine Lieferungen in der Datenbank gefunden.";
        }
        java.util.Map<String, Lieferung> byBestellnummer = new java.util.HashMap<>();
        for (Lieferung l : lieferungen) {
            if (l == null) continue;
            String key = normalizeCompareKey(l.getBestellNummer());
            if (!key.isEmpty() && !byBestellnummer.containsKey(key)) {
                byBestellnummer.put(key, l);
            }
        }
        if (byBestellnummer.isEmpty()) {
            return "Keine Lieferungen mit bestellNummer für den Abgleich vorhanden.";
        }

        java.util.List<IntranetViewAllRow> rows = parseIntranetViewAllRows(htmlFilePath);
        if (rows.isEmpty()) {
            return "Keine Datensätze in der HTML-Tabelle gefunden.";
        }

        int matches = 0;
        int shown = 0;
        int limit = Math.max(1, maxRows);
        StringBuilder sb = new StringBuilder();
        sb.append("Dry-run Vorschau (ohne Speichern)\n");
        sb.append("Datei: ").append(htmlFilePath).append("\n\n");

        for (IntranetViewAllRow row : rows) {
            if (row == null) continue;
            String bestNrKey = normalizeCompareKey(row.bestNr);
            if (bestNrKey.isEmpty()) continue;
            Lieferung target = byBestellnummer.get(bestNrKey);
            if (target == null) continue;

            matches++;
            if (shown >= limit) continue;

            sb.append("Match #").append(matches).append(" | BestNr=").append(bestNrKey).append("\n");
            sb.append("  Lieferung: anfordNummer=").append(nullToDash(target.getAnfordNummer()))
              .append(", bestellNummer=").append(nullToDash(target.getBestellNummer())).append("\n");
            sb.append("  Website: ID=").append(nullToDash(row.id))
              .append(", Lieferant=").append(nullToDash(row.lieferant))
              .append(", Kostenstelle=").append(nullToDash(row.kostenstelle)).append("\n");

            java.util.ArrayList<String> willSet = new java.util.ArrayList<>();
            if (isMissingAgrNummer(target.getAgrNummer()) && !isBlank(row.id)) willSet.add("BANF Nr");
            sb.append("  Würde setzen: ").append(willSet.isEmpty() ? "-" : String.join(", ", willSet)).append("\n\n");
            shown++;
        }

        sb.append("Gesamt-Matches: ").append(matches).append("\n");
        sb.append("Angezeigt: ").append(Math.min(matches, limit)).append(" (Limit ").append(limit).append(")\n");
        if (matches > limit) {
            sb.append("... weitere ").append(matches - limit).append(" Matches nicht angezeigt.\n");
        }
        return sb.toString();
    }

    private static final class IntranetViewAllRow {
        String id;
        String bestNr;
        String lieferant;
        String kostenstelle;
        String angefordertAm;
        String liefertermin;
    }

    private static final class IntranetHtmlImportResult {
        int totalRows;
        int matchedRows;
        int updatedCount;
        int skippedAlreadyHasBanf;
        int skippedWithoutHtmlId;
        int skippedNoMatchingLieferung;
        int skippedWithoutBestNr;
        int failedUpdates;
        ArrayList<String> updatedDetails = new ArrayList<>();
        ArrayList<String> alreadyPresentDetails = new ArrayList<>();
        ArrayList<String> missingDataDetails = new ArrayList<>();
        ArrayList<String> errorDetails = new ArrayList<>();
        ArrayList<String> otherNotes = new ArrayList<>();
    }

    private void appendListItem(ArrayList<String> list, String line) {
        if (list == null || line == null) {
            return;
        }
        if (list.size() < 40) {
            list.add(line);
        }
    }

    private String buildIntranetHtmlImportResultText(IntranetHtmlImportResult result, String htmlFilePath) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Intranet-HTML Import: Ergebnis ===\n");
        sb.append("Datei: ").append(htmlFilePath == null ? "-" : htmlFilePath).append("\n\n");

        sb.append("1) Zusammenfassung\n");
        sb.append("   - Zeilen aus HTML gelesen: ").append(result.totalRows).append("\n");
        sb.append("   - Matches mit Lieferungen: ").append(result.matchedRows).append("\n");
        sb.append("   - Aktualisiert (BANF gesetzt): ").append(result.updatedCount).append("\n");
        sb.append("   - Uebersprungen (BANF bereits vorhanden): ").append(result.skippedAlreadyHasBanf).append("\n");
        sb.append("   - Uebersprungen (HTML ohne BANF-ID): ").append(result.skippedWithoutHtmlId).append("\n");
        sb.append("   - Uebersprungen (keine passende Lieferung): ").append(result.skippedNoMatchingLieferung).append("\n");
        sb.append("   - Uebersprungen (HTML ohne BestNr): ").append(result.skippedWithoutBestNr).append("\n");
        sb.append("   - Fehler bei DB-Updates: ").append(result.failedUpdates).append("\n");

        appendSectionWithItems(sb, "2) Aktualisiert", result.updatedDetails);
        appendSectionWithItems(sb, "3) Bereits vorhanden (keine Aenderung)", result.alreadyPresentDetails);
        appendSectionWithItems(sb, "4) Fehlende HTML-Daten", result.missingDataDetails);
        appendSectionWithItems(sb, "5) Fehler", result.errorDetails);
        appendSectionWithItems(sb, "6) Weitere Hinweise", result.otherNotes);

        return sb.toString();
    }

    private java.util.List<IntranetViewAllRow> parseIntranetViewAllRows(String htmlFilePath) {
        java.util.List<IntranetViewAllRow> out = new java.util.ArrayList<>();
        if (htmlFilePath == null || htmlFilePath.trim().isEmpty()) {
            return out;
        }
        try {
            String html = Files.readString(Paths.get(htmlFilePath), StandardCharsets.UTF_8);
            return parseIntranetViewAllRowsFromHtml(html);
        } catch (Exception e) {
            System.err.println("Fehler beim Lesen/Parsen der Intranet-HTML-Datei: " + e.getMessage());
            e.printStackTrace();
            return out;
        }
    }

    private java.util.List<IntranetViewAllRow> parseIntranetViewAllRowsFromHtml(String html) {
        java.util.List<IntranetViewAllRow> out = new java.util.ArrayList<>();
        if (html == null || html.trim().isEmpty()) {
            return out;
        }
        try {
            int tableIdIdx = html.indexOf("id=\"MainContent_GridView1\"");
            if (tableIdIdx < 0) {
                return out;
            }
            int tableStart = html.lastIndexOf("<table", tableIdIdx);
            int tableEnd = html.indexOf("</table>", tableIdIdx);
            if (tableStart < 0 || tableEnd < 0 || tableEnd <= tableStart) {
                return out;
            }
            String tableHtml = html.substring(tableStart, tableEnd);

            Pattern trPattern = Pattern.compile("<tr[^>]*>(.*?)</tr>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Pattern tdPattern = Pattern.compile("<td[^>]*>(.*?)</td>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
            Matcher trMatcher = trPattern.matcher(tableHtml);
            while (trMatcher.find()) {
                String tr = trMatcher.group(1);
                if (tr == null || tr.toLowerCase().contains("<th")) continue;
                java.util.ArrayList<String> cells = new java.util.ArrayList<>();
                Matcher tdMatcher = tdPattern.matcher(tr);
                while (tdMatcher.find()) cells.add(cleanHtmlCell(tdMatcher.group(1)));
                if (cells.size() < 11) continue;
                IntranetViewAllRow r = new IntranetViewAllRow();
                r.id = safeCell(cells, 1);
                r.bestNr = safeCell(cells, 2);
                r.lieferant = safeCell(cells, 6);
                r.kostenstelle = safeCell(cells, 8);
                r.angefordertAm = safeCell(cells, 9);
                r.liefertermin = safeCell(cells, 10);
                out.add(r);
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Parsen der Intranet-HTML-Tabelle: " + e.getMessage());
            e.printStackTrace();
        }
        return out;
    }

    private static String safeCell(java.util.List<String> cells, int idx) {
        if (cells == null || idx < 0 || idx >= cells.size()) {
            return "";
        }
        String v = cells.get(idx);
        return v != null ? v.trim() : "";
    }

    private static String cleanHtmlCell(String raw) {
        if (raw == null) return "";
        String x = raw.replaceAll("(?is)<[^>]*>", " ");
        x = x.replace("&nbsp;", " ")
             .replace("&#160;", " ")
             .replace("&amp;", "&")
             .replace("&quot;", "\"")
             .replace("&#39;", "'");
        x = x.replaceAll("\\s+", " ").trim();
        return x;
    }

    private static String normalizeCompareKey(String value) {
        if (value == null) return "";
        String v = value.trim();
        if (v.isEmpty()) return "";
        if ("NA".equalsIgnoreCase(v) || "-".equals(v)) return "";
        // Für Abgleich Website <-> DB nur den führenden numerischen Teil verwenden
        // (z.B. "4500129425 (_SAP_)" -> "4500129425").
        Matcher m = Pattern.compile("^(\\d+)").matcher(v);
        if (m.find()) {
            return m.group(1);
        }
        return v;
    }

    private static String nullToDash(String s) {
        if (s == null || s.trim().isEmpty()) return "-";
        return s.trim();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * In Altbeständen ist BANF Nr. teils mit Platzhaltern belegt.
     * Diese Werte behandeln wir beim Import wie "leer".
     */
    private static boolean isMissingAgrNummer(String s) {
        if (s == null) {
            return true;
        }
        String v = s.trim();
        if (v.isEmpty()) {
            return true;
        }
        return "__||".equals(v)
                || "-".equals(v)
                || "NA".equalsIgnoreCase(v)
                || "N/A".equalsIgnoreCase(v);
    }

    private static String normalizeIntranetDateToShort(String raw) {
        if (raw == null || raw.trim().isEmpty()) {
            return "";
        }
        String v = raw.trim();
        try {
            // Formate wie "30.03.2026 09:57:53" oder "01.04.2026"
            Matcher m = Pattern.compile("(\\d{1,2}\\.\\d{1,2}\\.\\d{4})").matcher(v);
            if (m.find()) {
                java.text.SimpleDateFormat in = new java.text.SimpleDateFormat("dd.MM.yyyy");
                in.setLenient(false);
                java.util.Date d = in.parse(m.group(1));
                java.text.SimpleDateFormat out = new java.text.SimpleDateFormat("dd.MM.yy");
                return out.format(d);
            }
            // Falls schon kurz:
            Matcher m2 = Pattern.compile("(\\d{1,2}\\.\\d{1,2}\\.\\d{2})").matcher(v);
            if (m2.find()) {
                return m2.group(1);
            }
        } catch (Exception ignore) {}
        return "";
    }

    /**
     * Bereinigt Projektnummern in Lieferungen (Spalte abteilung):
     * entfernt alle Nicht-Ziffern und speichert nur Zahlen.
     *
     * @return Anzahl aktualisierter Datensätze
     */
    public int normalizeProjectNumbersOnlyDigits() {
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return 0;
        }

        ArrayList<Lieferung> lieferungen = getAllLieferungen();
        if (lieferungen == null || lieferungen.isEmpty()) {
            return 0;
        }

        int updated = 0;
        String sql = "UPDATE " + tabLiefName + " SET abteilung = ?, updated_at = GETDATE() WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            for (Lieferung l : lieferungen) {
                if (l == null || l.getId() <= 0) {
                    continue;
                }
                String oldValue = l.getProjectName() != null ? l.getProjectName().trim() : "";
                String digitsOnly = oldValue.replaceAll("\\D+", "");
                if (!oldValue.equals(digitsOnly)) {
                    ps.setString(1, digitsOnly);
                    ps.setInt(2, l.getId());
                    updated += ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Bereinigen der Projektnummern: " + e.getMessage());
            e.printStackTrace();
        }
        return updated;
    }

    /**
     * Dry-run für Bereinigung der Projektnummern (abteilung):
     * zeigt nur, was geändert WÜRDE, ohne DB-Update.
     */
    public String previewNormalizeProjectNumbersOnlyDigits(int maxExamples) {
        if (!ensureConnection()) {
            return "Keine Verbindung zur Datenbank.";
        }

        ArrayList<Lieferung> lieferungen = getAllLieferungen();
        if (lieferungen == null || lieferungen.isEmpty()) {
            return "Keine Lieferungen in der Datenbank gefunden.";
        }

        int checked = 0;
        int wouldChange = 0;
        int shown = 0;
        int limit = Math.max(1, maxExamples);
        StringBuilder sb = new StringBuilder();
        sb.append("Dry-run Projektnummern-Bereinigung (ohne Speichern)\n\n");

        for (Lieferung l : lieferungen) {
            if (l == null) {
                continue;
            }
            checked++;
            String oldValue = l.getProjectName() != null ? l.getProjectName().trim() : "";
            String digitsOnly = oldValue.replaceAll("\\D+", "");
            if (!oldValue.equals(digitsOnly)) {
                wouldChange++;
                if (shown < limit) {
                    sb.append("ID=").append(l.getId())
                      .append(" | alt='").append(oldValue)
                      .append("' -> neu='").append(digitsOnly).append("'\n");
                    shown++;
                }
            }
        }

        sb.append("\nGeprueft: ").append(checked).append("\n");
        sb.append("Wuerde geaendert: ").append(wouldChange).append("\n");
        sb.append("Beispiele angezeigt: ").append(Math.min(wouldChange, limit))
          .append(" (Limit ").append(limit).append(")\n");
        if (wouldChange > limit) {
            sb.append("... weitere ").append(wouldChange - limit).append(" nicht angezeigt.\n");
        }
        return sb.toString();
    }

    /**
     * Findet automatisch die erste Datenzeile (Einkaufsbeleg oder Positionsnummer).
     */
    private static int detectSapDataStartRow(Sheet sheet) {
        if (sheet == null) {
            return SAP_EXCEL_FIRST_DATA_ROW_INDEX;
        }
        int max = Math.min(sheet.getLastRowNum(), 80);
        for (int i = 0; i <= max; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            String p = readSapPositionCell(row);
            if (p.isEmpty()) {
                continue;
            }
            String pl = p.toLowerCase();
            if (pl.startsWith("einkauf") || p.matches("\\d+")) {
                return i;
            }
        }
        return SAP_EXCEL_FIRST_DATA_ROW_INDEX;
    }

    /**
     * Sucht in den oberen Zeilen nach einer Spaltenueberschrift und liefert deren Spaltenindex.
     */
    private static int findHeaderColumnIndex(Sheet sheet, String headerText, int searchRows) {
        if (sheet == null || headerText == null || headerText.trim().isEmpty()) {
            return -1;
        }
        String wanted = normalizeHeaderText(headerText);
        int maxRows = Math.min(sheet.getLastRowNum(), Math.max(0, searchRows - 1));
        for (int rowIdx = 0; rowIdx <= maxRows; rowIdx++) {
            Row row = sheet.getRow(rowIdx);
            if (row == null) {
                continue;
            }
            short lastCellNum = row.getLastCellNum();
            if (lastCellNum < 0) {
                continue;
            }
            for (int colIdx = 0; colIdx < lastCellNum; colIdx++) {
                String cellText = normalizeHeaderText(getStringFromCellRaw(row.getCell(colIdx)));
                if (!cellText.isEmpty() && cellText.equals(wanted)) {
                    return colIdx;
                }
            }
        }
        return -1;
    }

    private static String normalizeHeaderText(String text) {
        if (text == null) {
            return "";
        }
        return text.replace('\n', ' ')
                .replace('\r', ' ')
                .replaceAll("\\s+", " ")
                .trim()
                .toLowerCase();
    }

    /**
     * Sammelt Zeilenindizes, deren Bilder in der gewuenschten Spalte verankert sind.
     */
    private static Set<Integer> collectPictureRowsForColumn(Sheet sheet, int targetCol) {
        Set<Integer> rows = new HashSet<>();
        if (sheet == null || targetCol < 0) {
            return rows;
        }

        if (sheet instanceof XSSFSheet) {
            XSSFDrawing drawing = ((XSSFSheet) sheet).getDrawingPatriarch();
            if (drawing != null) {
                for (XSSFShape shape : drawing.getShapes()) {
                    if (!(shape instanceof XSSFPicture)) {
                        continue;
                    }
                    XSSFClientAnchor anchor = ((XSSFPicture) shape).getClientAnchor();
                    collectAnchoredRows(rows, anchor.getRow1(), anchor.getRow2(), anchor.getCol1(), anchor.getCol2(), targetCol);
                }
            }
            return rows;
        }

        if (sheet instanceof HSSFSheet) {
            HSSFPatriarch drawing = ((HSSFSheet) sheet).getDrawingPatriarch();
            if (drawing != null) {
                for (HSSFShape shape : drawing.getChildren()) {
                    if (!(shape instanceof HSSFPicture)) {
                        continue;
                    }
                    HSSFClientAnchor anchor = (HSSFClientAnchor) shape.getAnchor();
                    if (anchor == null) {
                        continue;
                    }
                    collectAnchoredRows(rows, anchor.getRow1(), anchor.getRow2(), anchor.getCol1(), anchor.getCol2(), targetCol);
                }
            }
        }
        return rows;
    }

    private static void collectAnchoredRows(Set<Integer> rows, int row1, int row2, int col1, int col2, int targetCol) {
        int startCol = Math.min(col1, col2);
        int endCol = Math.max(col1, col2);
        if (targetCol < startCol || targetCol > endCol) {
            return;
        }
        int startRow = Math.min(row1, row2);
        int endRow = Math.max(row1, row2);
        for (int row = startRow; row <= endRow; row++) {
            rows.add(row);
        }
    }

    /**
     * In einigen SAP-Dateien steht "Position"/"Einkaufsbeleg" in Spalte A, in anderen in B.
     */
    private static String readSapPositionCell(Row row) {
        if (row == null) {
            return "";
        }
        String a = getStringFromCellRaw(row.getCell(0));
        if (!a.isEmpty()) {
            return a;
        }
        return getStringFromCellRaw(row.getCell(1));
    }

    private static String getStringFromCellRaw(Cell cell) {
        if (cell == null) {
            return "";
        }
        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue() != null ? cell.getStringCellValue().trim() : "";
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return "";
                    }
                    double n = cell.getNumericCellValue();
                    return (n == Math.rint(n)) ? String.valueOf((long) n) : String.valueOf(n);
                case FORMULA:
                    switch (cell.getCachedFormulaResultType()) {
                        case STRING:
                            return cell.getStringCellValue() != null ? cell.getStringCellValue().trim() : "";
                        case NUMERIC:
                            double fn = cell.getNumericCellValue();
                            return (fn == Math.rint(fn)) ? String.valueOf((long) fn) : String.valueOf(fn);
                        default:
                            return "";
                    }
                default:
                    return "";
            }
        } catch (Exception ex) {
            return "";
        }
    }

    /**
     * Spalte A Kopfzeile „Einkaufsbeleg …“: Belegnummer (z. B. 4120010804).
     */
    private static String sapBelegNummerAusEinkaufsbelegZeile(String position) {
        if (position == null) {
            return "";
        }
        String p = position.trim();
        Matcher m = Pattern.compile("(?i)Einkaufsbeleg\\s*(\\d+)").matcher(p);
        if (m.find()) {
            return m.group(1);
        }
        if (p.regionMatches(true, 0, "Einkauf", 0, "Einkauf".length()) && p.length() > 14) {
            return p.substring(14).trim();
        }
        return "";
    }

    /**
     * Spalte A Positionszeilen: kurze Nummer (10, 20, …) mit vorheriger Belegnummer, sonst Legacy (langer Text ab Index 14).
     */
    private static String sapArtikelPositionsSchluessel(String positionColA, String belegNummer) {
        if (positionColA == null) {
            return "";
        }
        String pos = positionColA.trim();
        if (pos.isEmpty()) {
            return "";
        }
        if (pos.regionMatches(true, 0, "Einkauf", 0, "Einkauf".length())) {
            return "";
        }
        if (pos.length() > 14) {
            return pos.substring(14).trim();
        }
        if (belegNummer != null && !belegNummer.isEmpty()) {
            return belegNummer + "_" + pos;
        }
        return pos;
    }

    /** Spalte P: „25.04.2023 169998 Lieferant …“ – Datum und Rest (Lieferant/Zusatz). */
    private static String[] parseSapSpaltePDatumUndRest(String spalteP) {
        String[] out = new String[]{"", ""};
        if (spalteP == null || spalteP.trim().isEmpty()) {
            return out;
        }
        String t = spalteP.trim();
        Matcher m = Pattern.compile("^(\\d{1,2}\\.\\d{1,2}\\.(\\d{4}|\\d{2}))\\s+(.*)$").matcher(t);
        if (m.find()) {
            out[0] = m.group(1);
            out[1] = m.group(3).trim();
        } else {
            out[1] = t;
        }
        return out;
    }

    /**
     * Sucht ein Datums-Token dd.MM.yy / dd.MM.yyyy irgendwo im Text.
     */
    private static String extractDateToken(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }
        Matcher m = Pattern.compile("(\\d{1,2}\\.\\d{1,2}\\.(?:\\d{2}|\\d{4}))").matcher(text);
        if (m.find()) {
            return m.group(1);
        }
        return "";
    }

    /** Spalte Y: „1,000 ST“ / „1.000 ST“ – Menge und Einheit. */
    private static void parseSapMengeUndEinheitAusSpalteY(String roh, float[] mengeOut, String[] einheitOut) {
        mengeOut[0] = 0f;
        einheitOut[0] = "";
        if (roh == null || roh.trim().isEmpty()) {
            return;
        }
        Matcher m = Pattern.compile("^([\\d\\.\\,]+)\\s*(.*)$").matcher(roh.trim());
        if (m.find()) {
            mengeOut[0] = parseFloatGermanNumberToken(m.group(1));
            einheitOut[0] = m.group(2).trim();
        }
    }

    /** Spalte AA: „48,21 EUR***“ / „14.500,00 EUR“. */
    private static float parseSapPreisAusSpalteAA(String roh) {
        if (roh == null) {
            return 0f;
        }
        String t = roh.trim().replaceAll("\\*+", "").trim();
        Matcher m = Pattern.compile("([\\d\\.\\,]+)").matcher(t);
        if (!m.find()) {
            return 0f;
        }
        return parseFloatGermanNumberToken(m.group(1));
    }

    /**
     * Ein Zahlen-Token im deutschen Excel-Stil (Tausenderpunkt, Dezimalkomma).
     */
    private static float parseFloatGermanNumberToken(String s) {
        if (s == null || s.isEmpty()) {
            return 0f;
        }
        String z = s.trim();
        try {
            if (z.contains(",") && z.contains(".")) {
                int liKomma = z.lastIndexOf(',');
                int liPunkt = z.lastIndexOf('.');
                if (liKomma > liPunkt) {
                    z = z.replace(".", "").replace(",", ".");
                } else {
                    z = z.replace(",", "");
                }
            } else if (z.contains(",")) {
                String nachKomma = z.substring(z.lastIndexOf(',') + 1);
                if (nachKomma.length() == 2 && nachKomma.chars().allMatch(Character::isDigit)) {
                    z = z.replace(",", ".");
                } else if (nachKomma.length() == 3 && nachKomma.chars().allMatch(Character::isDigit)) {
                    z = z.replace(",", "");
                } else {
                    z = z.replace(".", "").replace(",", ".");
                }
            } else if (z.contains(".")) {
                String nachPunkt = z.substring(z.lastIndexOf('.') + 1);
                if (nachPunkt.length() == 3 && nachPunkt.chars().allMatch(Character::isDigit)) {
                    z = z.replace(".", "");
                }
            }
            return Float.parseFloat(z);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    private static String buildBestellungMitDatum(String nummer, String datum) {
        String n = nummer != null ? nummer.trim() : "";
        String d = datum != null ? datum.trim() : "";
        if (n.isEmpty()) {
            return "";
        }
        if (d.isEmpty()) {
            return n;
        }
        return n + " (" + d + ")";
    }

    /**
     * Normalisiert bestelltAm für Speicherung in DB auf dd.MM.yy.
     * Wenn Parsing fehlschlägt, wird der Originalwert zurückgegeben.
     */
    private static String normalizeBestelltAmForDb(String rawDate) {
        if (rawDate == null) {
            return "";
        }
        String raw = rawDate.trim();
        if (raw.isEmpty()) {
            return "";
        }

        java.util.Date parsed = null;
        String[] patterns = new String[] {
            "dd.MM.yy",
            "dd.MM.yyyy",
            "yyyy-MM-dd",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss.S",
            "yyyy-MM-dd'T'HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss.SSS"
        };

        for (String p : patterns) {
            try {
                java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat(p);
                fmt.setLenient(false);
                parsed = fmt.parse(raw);
                if (parsed != null) {
                    break;
                }
            } catch (Exception ignore) {}
        }

        if (parsed == null) {
            return raw;
        }
        java.text.SimpleDateFormat out = new java.text.SimpleDateFormat("dd.MM.yy");
        return out.format(parsed);
    }

    /**
     * Waehlt aus zwei Datumswerten den spaeteren (Format dd.MM.yy).
     * Falls einer der Werte nicht geparst werden kann, wird der andere bevorzugt.
     */
    private static String pickLaterBestelltAm(String currentValue, String candidateValue) {
        String current = currentValue != null ? currentValue.trim() : "";
        String candidate = candidateValue != null ? candidateValue.trim() : "";
        if (current.isEmpty()) {
            return candidate;
        }
        if (candidate.isEmpty()) {
            return current;
        }
        try {
            java.text.SimpleDateFormat fmt = new java.text.SimpleDateFormat("dd.MM.yy");
            fmt.setLenient(false);
            java.util.Date dCurrent = fmt.parse(current);
            java.util.Date dCandidate = fmt.parse(candidate);
            if (dCurrent == null) {
                return candidate;
            }
            if (dCandidate == null) {
                return current;
            }
            return dCandidate.after(dCurrent) ? candidate : current;
        } catch (Exception ex) {
            return current;
        }
    }

    /**
     * Fuegt Lieferantenname in eine Liste mit Trennung " - " ein, ohne Duplikate.
     * Beispiel: "A - B" + "A" => "A - B",  "A - B" + "C" => "A - B - C"
     */
    private static String mergeUniqueLieferanten(String existing, String candidate) {
        String c = candidate != null ? candidate.trim() : "";
        if (c.isEmpty()) {
            return existing != null ? existing : "";
        }

        java.util.LinkedHashSet<String> set = new java.util.LinkedHashSet<>();
        if (existing != null && !existing.trim().isEmpty()) {
            String[] parts = existing.split("\\s-\\s");
            for (String p : parts) {
                String t = p != null ? p.trim() : "";
                if (!t.isEmpty()) {
                    set.add(t);
                }
            }
        }
        set.add(c);
        return String.join(" - ", set);
    }

    /**
     * Fuegt Projektcodes ohne Duplikate zusammen (Trennung " - ").
     */
    private static String mergeUniqueProjektCodes(String existing, String candidate) {
        String c = candidate != null ? candidate.trim() : "";
        if (c.isEmpty()) {
            return existing != null ? existing : "";
        }
        java.util.LinkedHashSet<String> set = new java.util.LinkedHashSet<>();
        if (existing != null && !existing.trim().isEmpty()) {
            String[] parts = existing.split("\\s-\\s");
            for (String p : parts) {
                String t = p != null ? p.trim() : "";
                if (!t.isEmpty()) {
                    set.add(t);
                }
            }
        }
        set.add(c);
        return String.join(" - ", set);
    }

    private static boolean lieferungHasNonEmptySapKey(Lieferung lif) {
        if (lif == null) {
            return false;
        }
        String a = lif.getAgrNummer();
        return a != null && !a.trim().isEmpty();
    }

    /**
     * Übernimmt die Artikel der aktuellen Kopfzeile und hängt die Lieferung an die Importliste.
     */
    private static void finishCurrentSapLieferung(ArrayList<Lieferung> listSAP, Lieferung current,
            ArrayList<Artikel> artikelBatch) {
        if (current == null || !lieferungHasNonEmptySapKey(current)) {
            return;
        }
        current.artikelListe.clear();
        if (artikelBatch != null && !artikelBatch.isEmpty()) {
            current.artikelListe.addAll(artikelBatch);
        }
        listSAP.add(current);
    }

    /**
     * Speichert jede Lieferung, setzt danach anfordrung an jedem Artikel und legt die Artikel in {@code lager} an.
     */
    private void persistSapLieferungenMitArtikeln(ArrayList<Lieferung> listSAP, ExcelImportResult result) {
        for (Lieferung l : listSAP) {
            if (l == null) {
                continue;
            }
            try {
                String key = safeLieferungKey(l);
                boolean success = this.addInTabLif(l);
                if (success) {
                    result.addedLieferungen++;
                    appendExample(result, "Neu angelegt: " + key);
                    String anford = l.getAnfordNummer();
                    System.out.println("ERFOLG: Anforderung " + anford + " wurde gespeichert");
                    if (l.artikelListe != null && !l.artikelListe.isEmpty()) {
                        for (Artikel a : l.artikelListe) {
                            if (a == null) {
                                continue;
                            }
                            a.setAnfordrung(anford != null ? anford : "");
                            String agr = l.getAgrNummer();
                            if (isMissingAgrNummer(a.getBanfNrArt()) && !isMissingAgrNummer(agr)) {
                                a.setBanfNrArt(agr.trim());
                            }
                            boolean artOk = this.addArtikel(a);
                            if (!artOk) {
                                result.failedArtikel++;
                                System.err.println("FEHLER: Artikel konnte nicht gespeichert werden (Anforderung " + anford + ")");
                            } else {
                                result.addedArtikel++;
                            }
                        }
                    }
                } else {
                    if (lieferungExistsWithSameNummer(l)) {
                        result.skippedExistingLieferungen++;
                        appendExample(result, "Bereits vorhanden (keine Aenderung): " + key);
                    } else {
                        result.failedLieferungen++;
                        appendExample(result, "Fehler beim Speichern: " + key);
                    }
                    System.err.println("FEHLER: Anforderung " + l.getAnfordNummer() + " konnte nicht gespeichert werden");
                }
            } catch (Exception e) {
                result.failedLieferungen++;
                appendExample(result, "Exception beim Speichern: " + safeLieferungKey(l) + " | " + safeTrim(e.getMessage()));
                System.err.println("FEHLER beim Speichern von Anforderung " + l.getAnfordNummer() + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void appendExample(ExcelImportResult result, String line) {
        if (result == null || line == null) {
            return;
        }
        if (result.examples.size() < 40) {
            result.examples.add(line);
        }
    }

    private String safeLieferungKey(Lieferung l) {
        if (l == null) {
            return "-";
        }
        String anford = safeTrim(l.getAnfordNummer());
        String bestell = safeTrim(l.getBestellNummer());
        if (!anford.isEmpty() || !bestell.isEmpty()) {
            return "Anf=" + (anford.isEmpty() ? "-" : anford) + ", Bestell=" + (bestell.isEmpty() ? "-" : bestell);
        }
        return safeTrim(l.getBeschreibung());
    }

    private String safeTrim(String value) {
        return value == null ? "" : value.trim();
    }

    private String buildExcelImportResultText(ExcelImportResult result, String filePath) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Excel-Import: Ergebnis ===\n");
        sb.append("Datei: ").append(filePath == null ? "-" : filePath).append("\n\n");

        sb.append("1) Zusammenfassung\n");
        sb.append("   - Lieferungen erkannt: ").append(result.totalLieferungen).append("\n");
        sb.append("   - Neu angelegt: ").append(result.addedLieferungen).append("\n");
        sb.append("   - Bereits vorhanden (ohne Aenderung): ").append(result.skippedExistingLieferungen).append("\n");
        sb.append("   - Fehler bei Lieferungen: ").append(result.failedLieferungen).append("\n");
        sb.append("   - Neu angelegte Artikel: ").append(result.addedArtikel).append("\n");
        sb.append("   - Fehler bei Artikeln: ").append(result.failedArtikel).append("\n");

        ArrayList<String> addedDetails = new ArrayList<>();
        ArrayList<String> skippedDetails = new ArrayList<>();
        ArrayList<String> errorDetails = new ArrayList<>();
        ArrayList<String> otherDetails = new ArrayList<>();
        if (result.examples != null) {
            for (String line : result.examples) {
                if (line == null) {
                    continue;
                }
                if (line.startsWith("Neu angelegt:")) {
                    addedDetails.add(line);
                } else if (line.startsWith("Bereits vorhanden")) {
                    skippedDetails.add(line);
                } else if (line.startsWith("Fehler") || line.startsWith("Exception")) {
                    errorDetails.add(line);
                } else {
                    otherDetails.add(line);
                }
            }
        }

        appendSectionWithItems(sb, "2) Neu angelegt", addedDetails);
        appendSectionWithItems(sb, "3) Bereits vorhanden (keine Aenderung)", skippedDetails);
        appendSectionWithItems(sb, "4) Fehler", errorDetails);
        appendSectionWithItems(sb, "5) Weitere Hinweise", otherDetails);
        return sb.toString();
    }

    private void appendSectionWithItems(StringBuilder sb, String title, ArrayList<String> items) {
        sb.append("\n").append(title).append("\n");
        if (items == null || items.isEmpty()) {
            sb.append("   - Keine Eintraege.\n");
            return;
        }
        int idx = 1;
        for (String item : items) {
            sb.append("   ").append(idx++).append(") ").append(item).append("\n");
        }
    }
    
    /**
     * Вспомогательный метод для получения строкового значения из ячейки
     */
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return formatExcelDate(cell.getDateCellValue());
                } else {
                    double nv = cell.getNumericCellValue();
                    if (nv == Math.rint(nv)) {
                        return String.valueOf((long) nv);
                    }
                    return String.valueOf(nv);
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    switch (cell.getCachedFormulaResultType()) {
                        case STRING:
                            return cell.getStringCellValue() != null ? cell.getStringCellValue() : "";
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell)) {
                                return formatExcelDate(cell.getDateCellValue());
                            }
                            double nv = cell.getNumericCellValue();
                            if (nv == Math.rint(nv)) {
                                return String.valueOf((long) nv);
                            }
                            return String.valueOf(nv);
                        case BOOLEAN:
                            return String.valueOf(cell.getBooleanCellValue());
                        default:
                            return "";
                    }
                } catch (Exception ex) {
                    return cell.getCellFormula();
                }
            default:
                return "";
        }
    }
    
    /**
     * Форматирует дату из Excel в формат "dd.MM.yy"
     */
    private String formatExcelDate(java.util.Date date) {
        if (date == null) return "";
        
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd.MM.yy");
        return formatter.format(date);
    }
    
    /**
     * Вспомогательный метод для получения числового значения из ячейки
     */
    private float getCellValueAsFloat(Cell cell) {
        if (cell == null) return 0;
        
        switch (cell.getCellType()) {
            case NUMERIC:
                return (float) cell.getNumericCellValue();
            case STRING:
                try {
                    return Float.parseFloat(cell.getStringCellValue().replace(",", "."));
                } catch (NumberFormatException e) {
                    return 0;
                }
            case FORMULA:
                try {
                    switch (cell.getCachedFormulaResultType()) {
                        case NUMERIC:
                            return (float) cell.getNumericCellValue();
                        case STRING:
                            try {
                                return Float.parseFloat(cell.getStringCellValue().replace(",", "."));
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        default:
                            return 0;
                    }
                } catch (Exception e) {
                    return 0;
                }
            default:
                return 0;
        }
    }
    
 
 public boolean tableExists(String tableName) {
        if (tableName == null || tableName.trim().isEmpty()) {
            return false;
        }
        
        if (!ensureConnection()) {
            return false;
        }
        
        try {
            // Проверяем существование таблицы в системной таблице
            String checkSQL = "SELECT COUNT(*) FROM information_schema.tables WHERE table_name = ?";
            
            try (PreparedStatement pstmt = connection.prepareStatement(checkSQL)) {
                pstmt.setString(1, tableName.trim());
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (SQLException e) {
            // Если information_schema недоступен, пробуем другой способ
            try {
                String checkSQL = "SELECT 1 FROM " + tableName.trim() + " LIMIT 1";
                try (Statement stmt = connection.createStatement()) {
                    stmt.executeQuery(checkSQL);
                    return true;
                }
            } catch (SQLException ex) {
                // Таблица не существует
                return false;
            }
        }
        
        return false;
    }
    
    /**
     * Получает список всех таблиц в базе данных
     * @return список имен таблиц
     */
    public ArrayList<String> getAllTables() {
        ArrayList<String> tables = new ArrayList<>();
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return tables;
        }
        
        try {
            String sql = "SELECT table_name FROM information_schema.tables WHERE table_type = 'BASE TABLE'";
            
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                while (rs.next()) {
                    tables.add(rs.getString("table_name"));
                }
            }
        } catch (SQLException e) {
            System.err.println("ERROR beim lesen der Liste der Tabellen: " + e.getMessage());
            e.printStackTrace();
        }
        
        return tables;
    }
    
    /**
     * Удаляет все данные из таблицы, но сохраняет структуру
     * @param tableName имя таблицы
     * @return true если данные успешно удалены, false в противном случае
     */
    public boolean truncateTable(String tableName) {
        boolean truncateOK = false;
        
        if (tableName == null || tableName.trim().isEmpty()) {
            System.err.println("Fehler: Name der Tabelle darf nicht leer sein");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR:keine Verbindung mit DB");
            return false;
        }
        
        if (!tableExists(tableName.trim())) {
            System.err.println("Fehler: Tabelle '" + tableName + "' existiert nicht");
            return false;
        }
        
        try (Statement stmt = connection.createStatement()) {
            // Используем TRUNCATE TABLE для быстрого удаления всех данных
            String truncateSQL = "TRUNCATE TABLE " + tableName.trim();
            stmt.execute(truncateSQL);
            truncateOK = true;
            System.out.println("Alle Elemente aus der Tabelle '" + tableName + "' wurden komplett gelöscht");
        } catch (SQLException e) {
            // Если TRUNCATE не поддерживается или есть ограничения, используем DELETE
            try (Statement stmt = connection.createStatement()) {
                String deleteSQL = "DELETE FROM " + tableName.trim();
                int rowsAffected = stmt.executeUpdate(deleteSQL);
                truncateOK = true;
                System.out.println("Wurde gelöscht " + rowsAffected + " Elementen aus der Tabelle- '" + tableName + "'");
            } catch (SQLException ex) {
                System.err.println("Fehler beim Löschen der Elementen aus der Tabelle '" + tableName + "': " + ex.getMessage());
                ex.printStackTrace();
            }
        }
        
        return truncateOK;
    }
    
    // Метод для получения всех записей из таблицы
  
    
    // Метод для фильтрации по статусу (активные/неактивные)
    public ArrayList<Lieferung> getLieferungenByStatus(boolean aktiv) {
        ArrayList<Lieferung> lifList = new ArrayList<>();
        
        String selectSQL = "SELECT * FROM "+tabLiefName + " WHERE aktiv = ? ORDER BY id DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setBoolean(1, aktiv);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String beschr = rs.getString("beschreibung");
                    String menStr = rs.getString("menge");
                    float men = Float.parseFloat(menStr != null ? menStr : "0");
                    Lieferung lieferung = new Lieferung(beschr, men);
                    
                    // Заполняем объект данными из ResultSet (аналогично getAllLieferungen)
                    lieferung.setId(rs.getInt("id"));
                    lieferung.setNeu(rs.getBoolean("neu"));
                    lieferung.setAktiv(rs.getBoolean("aktiv"));
                    lieferung.setAgrNummer(rs.getString("agrNummer"));
                    lieferung.setAnfordNummer(rs.getString("anfordNummer"));
                    lieferung.setMenge(men);
                    lieferung.setEinhMenge(rs.getString("einhMenge"));
                   lieferung.setBeschreibung(beschr);
                    lieferung.setDe_eu(rs.getString("de_eu"));
                    lieferung.setProjectName(rs.getString("abteilung"));
                    
                    // Обработка дат
                    lieferung.setAnforderungDatum(rs.getString("anforderungDatum"));
                    
                    lieferung.setWunschLieferDatum(rs.getString("wunschLieferDatum"));
                    
                    lieferung.setAnfordPerson(rs.getString("anfordPerson"));
                    lieferung.setUnterschriftMH(rs.getBoolean("unterschriftMH"));
                    lieferung.setUnterschriftKunde(rs.getBoolean("unterschriftKunde"));
                    lieferung.setAnEinkaufWeiterGeleitetMH(rs.getBoolean("anEinkaufWeiterGeleitetMH"));
                    lieferung.setInvest_Leasing(rs.getBoolean("invest_Leasing"));
                    String summeIntStr = rs.getString("summeMat_Leist_cents");
                    lieferung.setSummeMat_Leist_cents(Float.parseFloat(summeIntStr != null ? summeIntStr : "0"));
                    String preisIntStr = rs.getString("preisEinkauf");
                    lieferung.setPreisEinkauf(Float.parseFloat(preisIntStr != null ? preisIntStr : "0"));
                    lieferung.setLieferantName(rs.getString("lieferantName"));
                    
                    lieferung.setBestelltAm(rs.getString("bestelltAm"));
                    
                    lieferung.setBestellNummer(rs.getString("bestellNummer"));
                    lieferung.setStatusEinkauf(rs.getString("statusEinkauf"));
                    
                   
                    lieferung.setWarenEingangTS(rs.getString("warenEingangTS"));
                    
                    lieferung.setWareneingangTS_plan(rs.getString("wareneingangTS_plan"));
                    
                    lieferung.setWareneingangTS_Fakt(rs.getString("wareneingangTS_Fakt"));
                    
                    lieferung.setAuslieferungNachPlan(rs.getString("auslieferungNachPlan"));
                    
                    lieferung.setZahlungsbedingungen(rs.getString("zahlungsbedingungen"));
                    lieferung.setKommentEinkauf(rs.getString("kommentEinkauf"));
                    
                    lieferung.setWunschterminWareneingang(rs.getString("terminWareneingangPlan"));
                    
                    lieferung.setWareneingang_Fakt(rs.getString("wareneingang_Fakt"));
                    
                    lieferung.setKommentarBS(rs.getString("kommentarBS"));
                    lieferung.setStatusAuslieferungBS(rs.getString("statusAuslieferungBS"));
                    String gewichtStr = rs.getString("gewicht");
                    lieferung.setGewicht(Float.parseFloat(gewichtStr != null ? gewichtStr : "0"));
                    lieferung.setEinhMasse(rs.getString("einhMasse"));
                    String laengeStr = rs.getString("laenge");
                    lieferung.setLaenge(Float.parseFloat(laengeStr != null ? laengeStr : "0"));
                    String breiteStr = rs.getString("breite");
                    lieferung.setBreite(Float.parseFloat(breiteStr != null ? breiteStr : "0"));
                    String hoeheStr = rs.getString("hoehe");
                    lieferung.setHoehe(Float.parseFloat(hoeheStr != null ? hoeheStr : "0"));
                    lieferung.setEinheitLBH(rs.getString("einheitLBH"));
                    lieferung.setKolli(rs.getString("kolli"));
                    lieferung.setPackListe(rs.getString("packListe"));
                    lieferung.setContainerNr(rs.getString("containerNr"));
                    lieferung.setRgNummeranAG(rs.getString("rgNummeranAG"));
                    
                    lieferung.setDatumBS(rs.getString("rechnungDatum"));
                    
                    lieferung.setIstWeitergerechnetAnAG(rs.getBoolean("istWeitergerechnetAnAG"));
                    String summeWeiterStr = rs.getString("summeWeitergerechnetanAG_cents");
                    lieferung.setSummeWeitergerechnetanAG_cents(Float.parseFloat(summeWeiterStr != null ? summeWeiterStr : "0"));
                    String summeNachStr = rs.getString("summe_nach_Weiterrechnung");
                    lieferung.setSumme_nach_Weiterrechnung(Float.parseFloat(summeNachStr != null ? summeNachStr : "0"));
                    String kundenStr = rs.getString("kundenInRechnungStellen");
                    lieferung.setKundenInRechnungStellen(Float.parseFloat(kundenStr != null ? kundenStr : "0"));
                    
                    // Обработка информационных полей
                    lieferung.setInfo01(rs.getString("info01"));
                    lieferung.setInfo02(rs.getString("info02"));
                    lieferung.setInfo03(rs.getString("info03"));
                    lieferung.setInfo04(rs.getString("info04"));
                    lieferung.setInfo05(rs.getString("info05"));
                   
                    // Устанавливаем текст кнопки
                    lieferung.setText(lieferung.getBeschreibung());
                    
                    // Добавляем объект в список
                    lifList.add(lieferung);
                }
            }
            
            System.out.println("Gefundeen " + (aktiv ? "aktiven" : "nicht aktiven") + " Elementen: " + lifList.size());
            
        } catch (SQLException e) {
            System.err.println("Fehler beim Filtern nach Status: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lifList;
    }
    
    // Метод для фильтрации по диапазону дат
    public ArrayList<Lieferung> getLieferungenByDateRange(Date startDate, Date endDate) {
        ArrayList<Lieferung> lifList = new ArrayList<>();
        
        if (startDate == null || endDate == null) {
            System.err.println("Fehler (Zeile 1493): Datum darf nicht  null sein");
            return lifList;
        }
        
        String selectSQL = "SELECT * FROM "+ tabLiefName+" WHERE " +
            "(anforderungDatum BETWEEN ? AND ?) OR " +
            "(wunschLieferDatum BETWEEN ? AND ?) OR " +
            "(bestelltAm BETWEEN ? AND ?) OR " +
            "(warenEingangTS BETWEEN ? AND ?) OR " +
            "(wareneingangTS_plan BETWEEN ? AND ?) OR " +
            "(wareneingangTS_Fakt BETWEEN ? AND ?) OR " +
            "(auslieferungNachPlan BETWEEN ? AND ?) OR " +
            "(terminWareneingangPlan BETWEEN ? AND ?) OR " +
            "(wareneingang_Fakt BETWEEN ? AND ?) OR " +
            "(rechnungDatum BETWEEN ? AND ?) " +
            "ORDER BY id DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            // Устанавливаем параметры для всех дат
            for (int i = 1; i <= 20; i += 2) {
                pstmt.setTimestamp(i, new Timestamp(startDate.getTime()));
                pstmt.setTimestamp(i + 1, new Timestamp(endDate.getTime()));
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String beschr = rs.getString("beschreibung") ;
                    String menStr = rs.getString("menge");
                    float men = Float.parseFloat(menStr != null ? menStr : "0");
                    Lieferung lieferung = new Lieferung(beschr, men);
                    
                    // Заполняем объект данными из ResultSet (аналогично предыдущим методам)
                    lieferung.setId(rs.getInt("id"));
                    lieferung.setNeu(rs.getBoolean("neu"));
                    lieferung.setAktiv(rs.getBoolean("aktiv"));
                    lieferung.setAgrNummer(rs.getString("agrNummer"));
                    lieferung.setAnfordNummer(rs.getString("anfordNummer"));
                    lieferung.setMenge(men);
                    lieferung.setEinhMenge(rs.getString("einhMenge"));
                   lieferung.setBeschreibung(beschr);
                    lieferung.setDe_eu(rs.getString("de_eu"));
                    lieferung.setProjectName(rs.getString("abteilung"));
                    
                    // Обработка дат
                    lieferung.setAnforderungDatum(rs.getString("anforderungDatum"));
                    
                    lieferung.setWunschLieferDatum(rs.getString("wunschLieferDatum"));
                    
                    lieferung.setAnfordPerson(rs.getString("anfordPerson"));
                    lieferung.setUnterschriftMH(rs.getBoolean("unterschriftMH"));
                    lieferung.setUnterschriftKunde(rs.getBoolean("unterschriftKunde"));
                    lieferung.setAnEinkaufWeiterGeleitetMH(rs.getBoolean("anEinkaufWeiterGeleitetMH"));
                    lieferung.setInvest_Leasing(rs.getBoolean("invest_Leasing"));
                    String summeIntStr = rs.getString("summeMat_Leist_cents");
                    lieferung.setSummeMat_Leist_cents(Float.parseFloat(summeIntStr != null ? summeIntStr : "0"));
                    String preisIntStr = rs.getString("preisEinkauf");
                    lieferung.setPreisEinkauf(Float.parseFloat(preisIntStr != null ? preisIntStr : "0"));
                    lieferung.setLieferantName(rs.getString("lieferantName"));
                    
                    lieferung.setBestelltAm(rs.getString("bestelltAm"));
                    
                    lieferung.setBestellNummer(rs.getString("bestellNummer"));
                    lieferung.setStatusEinkauf(rs.getString("statusEinkauf"));
                    
                   
                    lieferung.setWarenEingangTS(rs.getString("warenEingangTS"));
                    
                    lieferung.setWareneingangTS_plan(rs.getString("wareneingangTS_plan"));
                    
                    lieferung.setWareneingangTS_Fakt(rs.getString("wareneingangTS_Fakt"));
                    
                    lieferung.setAuslieferungNachPlan(rs.getString("auslieferungNachPlan"));
                    
                    lieferung.setZahlungsbedingungen(rs.getString("zahlungsbedingungen"));
                    lieferung.setKommentEinkauf(rs.getString("kommentEinkauf"));
                    
                    lieferung.setWunschterminWareneingang(rs.getString("terminWareneingangPlan"));
                    
                    lieferung.setWareneingang_Fakt(rs.getString("wareneingang_Fakt"));
                    
                    lieferung.setKommentarBS(rs.getString("kommentarBS"));
                    lieferung.setStatusAuslieferungBS(rs.getString("statusAuslieferungBS"));
                    String gewichtStr = rs.getString("gewicht");
                    lieferung.setGewicht(Float.parseFloat(gewichtStr != null ? gewichtStr : "0"));
                    lieferung.setEinhMasse(rs.getString("einhMasse"));
                    String laengeStr = rs.getString("laenge");
                    lieferung.setLaenge(Float.parseFloat(laengeStr != null ? laengeStr : "0"));
                    String breiteStr = rs.getString("breite");
                    lieferung.setBreite(Float.parseFloat(breiteStr != null ? breiteStr : "0"));
                    String hoeheStr = rs.getString("hoehe");
                    lieferung.setHoehe(Float.parseFloat(hoeheStr != null ? hoeheStr : "0"));
                    lieferung.setEinheitLBH(rs.getString("einheitLBH"));
                    lieferung.setKolli(rs.getString("kolli"));
                    lieferung.setPackListe(rs.getString("packListe"));
                    lieferung.setContainerNr(rs.getString("containerNr"));
                    lieferung.setRgNummeranAG(rs.getString("rgNummeranAG"));
                    
                    lieferung.setDatumBS(rs.getString("rechnungDatum"));
                    
                    lieferung.setIstWeitergerechnetAnAG(rs.getBoolean("istWeitergerechnetAnAG"));
                    String summeWeiterStr = rs.getString("summeWeitergerechnetanAG_cents");
                    lieferung.setSummeWeitergerechnetanAG_cents(Float.parseFloat(summeWeiterStr != null ? summeWeiterStr : "0"));
                    String summeNachStr = rs.getString("summe_nach_Weiterrechnung");
                    lieferung.setSumme_nach_Weiterrechnung(Float.parseFloat(summeNachStr != null ? summeNachStr : "0"));
                    String kundenStr = rs.getString("kundenInRechnungStellen");
                    lieferung.setKundenInRechnungStellen(Float.parseFloat(kundenStr != null ? kundenStr : "0"));
                    
                    // Обработка информационных полей
                    lieferung.setInfo01(rs.getString("info01"));
                    lieferung.setInfo02(rs.getString("info02"));
                    lieferung.setInfo03(rs.getString("info03"));
                    lieferung.setInfo04(rs.getString("info04"));
                    lieferung.setInfo05(rs.getString("info05"));
                   
                    // Устанавливаем текст кнопки
                    lieferung.setText(lieferung.getBeschreibung());
                    
                    // Добавляем объект в список
                    lifList.add(lieferung);
                }
            }
            
           // System.out.println("Найдено записей в диапазоне дат: " + lifList.size());
            
        } catch (SQLException e) {
            System.err.println("Fehler (Zeile 1608) beim Filtern der Datums: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lifList;
    }
    
    // Метод для получения только новых записей
    public ArrayList<Lieferung> getNewLieferungen() {
        ArrayList<Lieferung> lifList = new ArrayList<>();
        
        String selectSQL = "SELECT * FROM "+ tabLiefName +" WHERE neu = true ORDER BY id DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            
            while (rs.next()) {
                String beschr = rs.getString("beschreibung")  ;
                String menStr = rs.getString("menge");
                float men = Float.parseFloat(menStr != null ? menStr : "0");
                
                
                Lieferung lieferung = new Lieferung(beschr, men);
                
                // Заполняем объект данными из ResultSet (аналогично предыдущим методам)
                lieferung.setId(rs.getInt("id"));
                lieferung.setNeu(rs.getBoolean("neu"));
                lieferung.setAktiv(rs.getBoolean("aktiv"));
                lieferung.setAgrNummer(rs.getString("agrNummer"));
                lieferung.setAnfordNummer(rs.getString("anfordNummer"));
                lieferung.setMenge(men);
                lieferung.setEinhMenge(rs.getString("einhMenge"));
               lieferung.setBeschreibung(beschr);
                lieferung.setDe_eu(rs.getString("de_eu"));
                lieferung.setProjectName(rs.getString("abteilung"));
                
                // Обработка дат
                lieferung.setAnforderungDatum(rs.getString("anforderungDatum"));
                
                lieferung.setWunschLieferDatum(rs.getString("wunschLieferDatum"));
                
                lieferung.setAnfordPerson(rs.getString("anfordPerson"));
                lieferung.setUnterschriftMH(rs.getBoolean("unterschriftMH"));
                lieferung.setUnterschriftKunde(rs.getBoolean("unterschriftKunde"));
                lieferung.setAnEinkaufWeiterGeleitetMH(rs.getBoolean("anEinkaufWeiterGeleitetMH"));
                lieferung.setInvest_Leasing(rs.getBoolean("invest_Leasing"));
                lieferung.setSummeMat_Leist_cents(rs.getInt("summeMat_Leist_cents"));
                lieferung.setPreisEinkauf(rs.getInt("preisEinkauf"));
                lieferung.setLieferantName(rs.getString("lieferantName"));
                
                lieferung.setBestelltAm(rs.getString("bestelltAm"));
                
                                    lieferung.setBestellNummer(rs.getString("bestellNummer"));
                    lieferung.setStatusEinkauf(rs.getString("statusEinkauf"));
                    
                   
                    lieferung.setWarenEingangTS(rs.getString("warenEingangTS"));
                    
                    lieferung.setWareneingangTS_plan(rs.getString("wareneingangTS_plan"));
                    
                    lieferung.setWareneingangTS_Fakt(rs.getString("wareneingangTS_Fakt"));
                    
                    lieferung.setAuslieferungNachPlan(rs.getString("auslieferungNachPlan"));
                    
                    lieferung.setZahlungsbedingungen(rs.getString("zahlungsbedingungen"));
                    lieferung.setKommentEinkauf(rs.getString("kommentEinkauf"));
                    
                    lieferung.setWunschterminWareneingang(rs.getString("terminWareneingangPlan"));
                    
                    lieferung.setWareneingang_Fakt(rs.getString("wareneingang_Fakt"));
                    
                    lieferung.setKommentarBS(rs.getString("kommentarBS"));
                    lieferung.setStatusAuslieferungBS(rs.getString("statusAuslieferungBS"));
                    String gewichtStr = rs.getString("gewicht");
                    lieferung.setGewicht(Float.parseFloat(gewichtStr != null ? gewichtStr : "0"));
                    lieferung.setEinhMasse(rs.getString("einhMasse"));
                    String laengeStr = rs.getString("laenge");
                    lieferung.setLaenge(Float.parseFloat(laengeStr != null ? laengeStr : "0"));
                    String breiteStr = rs.getString("breite");
                    lieferung.setBreite(Float.parseFloat(breiteStr != null ? breiteStr : "0"));
                    String hoeheStr = rs.getString("hoehe");
                    lieferung.setHoehe(Float.parseFloat(hoeheStr != null ? hoeheStr : "0"));
                    lieferung.setEinheitLBH(rs.getString("einheitLBH"));
                    lieferung.setKolli(rs.getString("kolli"));
                    lieferung.setPackListe(rs.getString("packListe"));
                    lieferung.setContainerNr(rs.getString("containerNr"));
                    lieferung.setRgNummeranAG(rs.getString("rgNummeranAG"));
                    
                    lieferung.setDatumBS(rs.getString("rechnungDatum"));
                    
                    lieferung.setIstWeitergerechnetAnAG(rs.getBoolean("istWeitergerechnetAnAG"));
                    String summeWeiterStr = rs.getString("summeWeitergerechnetanAG_cents");
                    lieferung.setSummeWeitergerechnetanAG_cents(Float.parseFloat(summeWeiterStr != null ? summeWeiterStr : "0"));
                    String summeNachStr = rs.getString("summe_nach_Weiterrechnung");
                    lieferung.setSumme_nach_Weiterrechnung(Float.parseFloat(summeNachStr != null ? summeNachStr : "0"));
                    String kundenStr = rs.getString("kundenInRechnungStellen");
                    lieferung.setKundenInRechnungStellen(Float.parseFloat(kundenStr != null ? kundenStr : "0"));
                    
                    // Обработка информационных полей
                    lieferung.setInfo01(rs.getString("info01"));
                    lieferung.setInfo02(rs.getString("info02"));
                    lieferung.setInfo03(rs.getString("info03"));
                    lieferung.setInfo04(rs.getString("info04"));
                    lieferung.setInfo05(rs.getString("info05"));
               
                // Устанавливаем текст кнопки
                lieferung.setText(lieferung.getBeschreibung());
                
                // Добавляем объект в список
                lifList.add(lieferung);
            }
            
           // System.out.println("Найдено новых записей: " + lifList.size());
            
        } catch (SQLException e) {
            System.err.println("Fehler ( Zeile 1722) при получении новых записей: " + e.getMessage());
            e.printStackTrace();
        }
        
        return lifList;
    }
    
    // Метод для получения общего количества записей
    public int getLieferungenCount() {
        int count = 0;
        
        String countSQL = "SELECT COUNT(*) FROM "+tabLiefName;
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(countSQL)) {
            
            if (rs.next()) {
                count = rs.getInt(1);
            }
            
           // System.out.println("Общее количество записей: " + count);
            
        } catch (SQLException e) {
            System.err.println("Ошибка при подсчете записей: " + e.getMessage());
            e.printStackTrace();
        }
        
        return count;
    }
    
    // Метод для получения количества активных записей
    public int getActiveLieferungenCount() {
        int count = 0;
        
        String countSQL = "SELECT COUNT(*) FROM "+ tabLiefName + " WHERE aktiv = true";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(countSQL)) {
            
            if (rs.next()) {
                count = rs.getInt(1);
            }
            
          //  System.out.println("Количество активных записей: " + count);
            
        } catch (SQLException e) {
            System.err.println("Fehler (Zeile 1769) при подсчете активных записей: " + e.getMessage());
            e.printStackTrace();
        }
        
        return count;
    }
    
    // Метод для экспорта данных в CSV файл
 
    
    // Вспомогательный метод для экранирования CSV значений
    private String escapeCSV(Object value) {
        if (value == null) {
            return "";
        }
        
        String strValue = value.toString();
        
        // Если значение содержит запятую, кавычку или перенос строки, заключаем в кавычки
        if (strValue.contains(",") || strValue.contains("\"") || strValue.contains("\n") || strValue.contains("\r")) {
            // Заменяем кавычки на двойные кавычки
            strValue = strValue.replace("\"", "\"\"");
            // Оборачиваем в кавычки
            strValue = "\"" + strValue + "\"";
        }
        
        return strValue;
    }
    
    // Вспомогательный метод для форматирования дат
    
    // Вспомогательный метод для форматирования строковых дат
    private String formatDateString(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return "";
        }
        
        try {
            // Если строка уже в правильном формате, возвращаем как есть
            if (dateString.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
                return dateString;
            }
            
            // Пытаемся распарсить различные форматы дат
            SimpleDateFormat[] formats = {
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
                new SimpleDateFormat("yyyy-MM-dd"),
                new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"),
                new SimpleDateFormat("dd.MM.yyyy"),
                new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"),
                new SimpleDateFormat("MM/dd/yyyy")
            };
            
            for (SimpleDateFormat format : formats) {
                try {
                    java.util.Date date = format.parse(dateString);
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                } catch (java.text.ParseException e) {
                    // Продолжаем с следующим форматом
                }
            }
            
            // Если не удалось распарсить, возвращаем исходную строку
            return dateString;
            
        } catch (Exception e) {
            System.err.println("Fehler (1835) при форматировании даты: " + dateString + " - " + e.getMessage());
            return dateString;
        }
    }
    
    // Метод для проверки валидности соединения
    public boolean isConnectionValid() {
        if (connection == null) {
            return false;
        }
        
        try {
            // Проверяем, что соединение не закрыто и валидно
            return !connection.isClosed() && connection.isValid(5); // timeout 5 секунд
        } catch (SQLException e) {
            System.err.println("Fehler (Zeile 1850) Ошибка при проверке соединения: " + e.getMessage());
            return false;
        }
    }

    /**
     * Prüft die Datenbankverbindung. Zeigt bei fehlender Verbindung einen Dialog
     * mit der Option, die Verbindung erneut zu versuchen.
     * @return true, wenn Verbindung besteht (oder erfolgreich wiederhergestellt), sonst false
     */
    public boolean ensureConnection() {
        if (isConnectionValid()) {
            return true;
        }
        
        // Automatische Wiederholungsversuche, bevor der Benutzer gefragt wird
        final int maxAttempts = 3;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            if (reconnect() && isConnectionValid()) {
                return true;
            }
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        String message = "Die Verbindung zur Datenbank konnte nicht hergestellt werden.\n\nVerbindung erneut versuchen und Anfrage ausführen?";
        int option = javax.swing.JOptionPane.showConfirmDialog(null,
                message,
                "Fehler bei der Datenbankverbindung",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE);
        if (option != javax.swing.JOptionPane.YES_OPTION) {
            return false;
        }
        if (reconnect() && isConnectionValid()) {
            return true;
        }
        String failMsg = "Die Verbindung zur Datenbank konnte nicht wiederhergestellt werden.\nBitte prüfen Sie die Einstellungen und die Erreichbarkeit des Servers.";
        javax.swing.JOptionPane.showMessageDialog(null, failMsg, "Fehler bei der Verbindung", javax.swing.JOptionPane.ERROR_MESSAGE);
        return false;
    }
    
    // Метод для переподключения к базе данных
    public boolean reconnect() {
        final boolean[] success = new boolean[1];
        final boolean onEDT = SwingUtilities.isEventDispatchThread();

        if (onEDT) {
            final JDialog connDialog = createConnectionAttemptDialog();
            connDialog.setModal(true);
            Thread connectThread = new Thread(() -> {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                    connection = DriverManager.getConnection(DB_URL, username, password);
                    System.out.println("Verbindung zur DB wurde hergestellt");
                    success[0] = true;
                } catch (SQLException e) {
                    System.err.println("Fehler Zeile 1869 Ошибка при переподключении к базе данных: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    try { SwingUtilities.invokeAndWait(() -> connDialog.dispose()); } catch (Exception ex) { }
                }
            });
            connectThread.start();
            connDialog.setVisible(true);
        } else {
            final JDialog[] dialogHolder = new JDialog[1];
            try {
                SwingUtilities.invokeAndWait(() -> {
                    JDialog d = createConnectionAttemptDialog();
                    d.setModal(false);
                    d.setVisible(true);
                    dialogHolder[0] = d;
                });
            } catch (Exception ex) {
                dialogHolder[0] = null;
            }
            final JDialog connDialog = dialogHolder[0];
            final CountDownLatch latch = new CountDownLatch(1);
            Thread connectThread = new Thread(() -> {
                try {
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }
                    connection = DriverManager.getConnection(DB_URL, username, password);
                    System.out.println("Verbindung zur DB wurde hergestellt");
                    success[0] = true;
                } catch (SQLException e) {
                    System.err.println("Fehler Zeile 1869 Ошибка при переподключении к базе данных: " + e.getMessage());
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
            connectThread.start();
            try { latch.await(); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            if (connDialog != null) {
                try { SwingUtilities.invokeAndWait(() -> connDialog.dispose()); } catch (Exception ex) { }
            }
        }
        return success[0];
    }
    
    // Метод для корректного закрытия соединения
    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    System.out.println("Verbindung zur DB wurde getrennt");
                }
            } catch (SQLException e) {
                System.err.println("Fehler Z. 1884 Ошибка при закрытии соединения: " + e.getMessage());
                e.printStackTrace();
            } finally {
                connection = null;
            }
        }
    }
    
    // Метод для получения информации о соединении
    public String getConnectionInfo() {
        if (connection == null) {
            return "Verbindung wurde nicht hergestellt";
        }
        
        try {
            StringBuilder info = new StringBuilder();
            info.append("URL: ").append(connection.getMetaData().getURL()).append("\n");
            info.append("База данных: ").append(connection.getCatalog()).append("\n");
            info.append("Пользователь: ").append(connection.getMetaData().getUserName()).append("\n");
            info.append("Тип базы данных: ").append(connection.getMetaData().getDatabaseProductName()).append("\n");
            info.append("Версия базы данных: ").append(connection.getMetaData().getDatabaseProductVersion()).append("\n");
            info.append("Авто-коммит: ").append(connection.getAutoCommit()).append("\n");
            info.append("Транзакции: ").append(connection.getMetaData().supportsTransactions() ? "Поддерживаются" : "Не поддерживаются").append("\n");
            info.append("Статус соединения: ").append(connection.isClosed() ? "Закрыто" : "Открыто");
            
            return info.toString();
            
        } catch (SQLException e) {
            return "Ошибка при получении информации о соединении: " + e.getMessage();
        }
    }
    
    /**
     * Liefert eine Textdarstellung aller Tabellen der Hauptdatenbank
     * inklusive Spaltenname, Datentyp und Spaltengröße.
     */
    public String getMainDatabaseTablesInfo() {
        if (!ensureConnection()) {
            return "Die Verbindung zur Hauptdatenbank konnte nicht hergestellt werden.";
        }

        StringBuilder info = new StringBuilder();

        try {
            java.sql.DatabaseMetaData metaData = connection.getMetaData();
            String catalog = connection.getCatalog();

            try (ResultSet tables = metaData.getTables(catalog, null, "%", new String[] { "TABLE" })) {
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    info.append("==================================================").append("\n");
                    info.append("Tabelle: ").append(tableName).append("\n");
                    info.append("--------------------------------------------------").append("\n");

                    try (ResultSet columns = metaData.getColumns(catalog, null, tableName, "%")) {
                        while (columns.next()) {
                            String columnName = columns.getString("COLUMN_NAME");
                            String typeName = columns.getString("TYPE_NAME");
                            int columnSize = columns.getInt("COLUMN_SIZE");

                            info.append("  ")
                                .append(columnName)
                                .append(" (")
                                .append(typeName);

                            if (columnSize > 0) {
                                info.append(", ").append(columnSize);
                            }

                            info.append(")").append("\n");
                        }
                    }

                    info.append("==================================================").append("\n\n");
                }
            }
        } catch (SQLException e) {
            return "Fehler beim Lesen der Tabellenstruktur: " + e.getMessage();
        }

        if (info.length() == 0) {
            return "In der Hauptdatenbank wurden keine Tabellen gefunden.";
        }

        return info.toString();
    }

    /**
     * Liefert die Namen aller Tabellen der Hauptdatenbank.
     */
    public java.util.ArrayList<String> getMainDatabaseTableNames() {
        java.util.ArrayList<String> tableNames = new java.util.ArrayList<>();

        if (!ensureConnection()) {
            return tableNames;
        }

        try {
            java.sql.DatabaseMetaData metaData = connection.getMetaData();
            String catalog = connection.getCatalog();

            try (ResultSet tables = metaData.getTables(catalog, null, "%", new String[] { "TABLE" })) {
                while (tables.next()) {
                    String tableName = tables.getString("TABLE_NAME");
                    if (tableName != null && !tableName.trim().isEmpty()) {
                        tableNames.add(tableName);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Lesen der Tabellennamen: " + e.getMessage());
        }

        return tableNames;
    }
    
    // Метод для установки авто-коммита
    public boolean setAutoCommit(boolean autoCommit) {
        if (!ensureConnection()) {
            System.err.println("Ошибка: соединение не установлено");
            return false;
        }
        
        try {
            connection.setAutoCommit(autoCommit);
            System.out.println("Авто-коммит установлен: " + autoCommit);
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка при установке авто-коммита: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Метод для коммита транзакции
    public boolean commit() {
        if (!ensureConnection()) {
            System.err.println("Ошибка: соединение не установлено");
            return false;
        }
        
        try {
            connection.commit();
            System.out.println("Транзакция зафиксирована");
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка при фиксации транзакции: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Метод для отката транзакции
    public boolean rollback() {
        if (!ensureConnection()) {
            System.err.println("Ошибка: соединение не установлено");
            return false;
        }
        
        try {
            connection.rollback();
            System.out.println("Транзакция откачена");
            return true;
        } catch (SQLException e) {
            System.err.println("Ошибка при откате транзакции: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Метод для проверки поддержки функций базы данных
    public boolean supportsFeature(String feature) {
        if (!ensureConnection()) {
            return false;
        }
        
        try {
            switch (feature.toLowerCase()) {
                case "transactions":
                    return connection.getMetaData().supportsTransactions();
                case "batch":
                    return connection.getMetaData().supportsBatchUpdates();
                case "savepoints":
                    return connection.getMetaData().supportsSavepoints();
                case "catalogs":
                    return connection.getMetaData().supportsCatalogsInTableDefinitions();
                case "schemas":
                    return connection.getMetaData().supportsSchemasInTableDefinitions();
                default:
                    return false;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при проверке поддержки функции '" + feature + "': " + e.getMessage());
            return false;
        }
    }
    
    // Метод для получения статистики соединения
    public String getConnectionStats() {
        if (!ensureConnection()) {
            return "Соединение не установлено";
        }
        
        try {
            StringBuilder stats = new StringBuilder();
            stats.append("=== Статистика соединения ===\n");
            stats.append("Время создания: ").append(System.currentTimeMillis()).append("\n");
            stats.append("Авто-коммит: ").append(connection.getAutoCommit()).append("\n");
            stats.append("Только для чтения: ").append(connection.isReadOnly()).append("\n");
            stats.append("Уровень изоляции: ").append(getIsolationLevelName(connection.getTransactionIsolation())).append("\n");
            stats.append("Соединение закрыто: ").append(connection.isClosed()).append("\n");
            stats.append("Соединение валидно: ").append(connection.isValid(5)).append("\n");
            
            return stats.toString();
            
        } catch (SQLException e) {
            return "Ошибка при получении статистики: " + e.getMessage();
        }
    }
    
    // Вспомогательный метод для получения названия уровня изоляции
    private String getIsolationLevelName(int level) {
        switch (level) {
            case java.sql.Connection.TRANSACTION_NONE:
                return "TRANSACTION_NONE";
            case java.sql.Connection.TRANSACTION_READ_UNCOMMITTED:
                return "TRANSACTION_READ_UNCOMMITTED";
            case java.sql.Connection.TRANSACTION_READ_COMMITTED:
                return "TRANSACTION_READ_COMMITTED";
            case java.sql.Connection.TRANSACTION_REPEATABLE_READ:
                return "TRANSACTION_REPEATABLE_READ";
            case java.sql.Connection.TRANSACTION_SERIALIZABLE:
                return "TRANSACTION_SERIALIZABLE";
            default:
                return "UNKNOWN (" + level + ")";
        }
    }
    
    // Метод для создания резервной копии таблицы
    public boolean createBackup(String backupFilePath) {
        if (backupFilePath == null || backupFilePath.trim().isEmpty()) {
            System.err.println("Ошибка: путь к файлу резервной копии не может быть пустым");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("Ошибка: соединение с базой данных не установлено");
            return false;
        }
        
        try {
            // Создаем резервную копию всей базы данных SQLite
            String backupSQL = "BACKUP TO ?";
            
            try (PreparedStatement pstmt = connection.prepareStatement(backupSQL)) {
                pstmt.setString(1, backupFilePath);
                pstmt.execute();
                
                System.out.println("Резервная копия успешно создана: " + backupFilePath);
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Ошибка при создании резервной копии: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Метод для создания резервной копии только таблицы lieferung
    public boolean createTableBackup(String backupFilePath) {
        if (backupFilePath == null || backupFilePath.trim().isEmpty()) {
            System.err.println("Ошибка: путь к файлу резервной копии не может быть пустым");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("Ошибка: соединение с базой данных не установлено");
            return false;
        }
        
        try {
            // Создаем новую базу данных для резервной копии
            String backupDBUrl = "jdbc:sqlite:" + backupFilePath;
            
            try (Connection backupConnection = DriverManager.getConnection(backupDBUrl)) {
                
                // Создаем таблицу в резервной базе
                String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tabLiefName + " (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "neu BOOLEAN DEFAULT FALSE," +
                    "aktiv BOOLEAN DEFAULT TRUE," +
                    "agrNummer TEXT," +
                    "anfordNummer TEXT," +
                    "menge NVARCHAR(50) DEFAULT '0'," +
                    "einhMenge TEXT," +
                    "beschreibung TEXT DEFAULT '____'," +
                    "de_eu TEXT," +
                    "abteilung TEXT," +
                    "anforderungDatum DATE," +
                    "wunschLieferDatum DATE," +
                    "anfordPerson TEXT," +
                    "unterschriftMH BOOLEAN DEFAULT FALSE," +
                    "unterschriftKunde BOOLEAN DEFAULT FALSE," +
                    "anEinkaufWeiterGeleitetMH BOOLEAN DEFAULT FALSE," +
                    "invest_Leasing BOOLEAN DEFAULT FALSE," +
                    "summeMat_Leist_cents INTEGER DEFAULT 0," +
                    "preisEinkauf INTEGER DEFAULT 0," +
                    "lieferantName TEXT," +
                    "bestelltAm DATE," +
                    "bestellNummer TEXT," +
                    "statusEinkauf TEXT," +
                    "warenEingangTS DATE," +
                    "wareneingangTS_plan DATE," +
                    "wareneingangTS_Fakt DATE," +
                    "auslieferungNachPlan DATE," +
                    "zahlungsbedingungen TEXT," +
                    "kommentEinkauf TEXT," +
                    "terminWareneingangPlan DATE," +
                    "wareneingang_Fakt DATE," +
                    "kommentarBS TEXT," +
                    "statusAuslieferungBS TEXT," +
                    "gewicht REAL DEFAULT 0.0," +
                    "einhMasse TEXT," +
                    "laenge REAL DEFAULT 0.0," +
                    "breite REAL DEFAULT 0.0," +
                    "hoehe REAL DEFAULT 0.0," +
                    "einheitLBH TEXT," +
                    "kolli TEXT," +
                    "packListe TEXT," +
                    "containerNr TEXT," +
                    "rgNummeranAG TEXT," +
                    "rechnungDatum DATE," +
                    "istWeitergerechnetAnAG BIT DEFAULT 0," +
                    "summeWeitergerechnetanAG_cents INTEGER DEFAULT 0," +
                    "summe_nach_Weiterrechnung INTEGER DEFAULT 0," +
                    "kundenInRechnungStellen INTEGER DEFAULT 0," +
                    "info01 TEXT," +
                    "info02 TEXT," +
                    "info03 TEXT," +
                    "info04 TEXT," +
                    "info05 TEXT," +
                    "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                    "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                    ")";
                
                try (Statement stmt = backupConnection.createStatement()) {
                    stmt.execute(createTableSQL);
                }
                
                // Копируем данные из основной таблицы
                String selectSQL = "SELECT * FROM lieferung";
                String insertSQL = "INSERT INTO lieferung (" +
                    "id, neu, aktiv, agrNummer, anfordNummer, menge, einhMenge, " +
                    "beschreibung, de_eu, abteilung, anforderungDatum, wunschLieferDatum, " +
                    "anfordPerson, unterschriftMH, unterschriftKunde, anEinkaufWeiterGeleitetMH, " +
                    "invest_Leasing, summeMat_Leist_cents, preisEinkauf, lieferantName, bestelltAm, " +
                    "bestellNummer, statusEinkauf, warenEingangTS, wareneingangTS_plan, wareneingangTS_Fakt, " +
                    "auslieferungNachPlan, zahlungsbedingungen, kommentEinkauf, terminWareneingangPlan, " +
                    "wareneingang_Fakt, kommentarBS, statusAuslieferungBS, gewicht, einhMasse, laenge, breite, hoehe, " +
                    "einheitLBH, kolli, packListe, containerNr, rgNummeranAG, rechnungDatum, " +
                    "istWeitergerechnetAnAG, summeWeitergerechnetanAG_cents, summe_nach_Weiterrechnung, kundenInRechnungStellen, " +
                    "info01, info02, info03, info04, info05, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                try (Statement selectStmt = connection.createStatement();
                     ResultSet rs = selectStmt.executeQuery(selectSQL);
                     PreparedStatement insertStmt = backupConnection.prepareStatement(insertSQL)) {
                    
                    int rowCount = 0;
                    while (rs.next()) {
                        int paramIndex = 1;
                        
                        insertStmt.setInt(paramIndex++, rs.getInt("id"));
                        insertStmt.setBoolean(paramIndex++, rs.getBoolean("neu"));
                        insertStmt.setBoolean(paramIndex++, rs.getBoolean("aktiv"));
                        insertStmt.setString(paramIndex++, rs.getString("agrNummer"));
                        insertStmt.setString(paramIndex++, rs.getString("anfordNummer"));
                        insertStmt.setDouble(paramIndex++, rs.getInt("menge"));
                        insertStmt.setString(paramIndex++, rs.getString("einhMenge"));
                        insertStmt.setString(paramIndex++, rs.getString("einheit"));
                        insertStmt.setString(paramIndex++, rs.getString("beschreibung"));
                        insertStmt.setString(paramIndex++, rs.getString("de_eu"));
                        insertStmt.setString(paramIndex++, rs.getString("abteilung"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("anforderungDatum"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("wunschLieferDatum"));
                        insertStmt.setString(paramIndex++, rs.getString("anfordPerson"));
                        insertStmt.setBoolean(paramIndex++, rs.getBoolean("unterschriftMH"));
                        insertStmt.setBoolean(paramIndex++, rs.getBoolean("unterschriftKunde"));
                        insertStmt.setBoolean(paramIndex++, rs.getBoolean("anEinkaufWeiterGeleitetMH"));
                        insertStmt.setBoolean(paramIndex++, rs.getBoolean("invest_Leasing"));
                        insertStmt.setInt(paramIndex++, rs.getInt("summeMat_Leist_cents"));
                        insertStmt.setString(paramIndex++, rs.getString("waehrungSummeMat"));
                        insertStmt.setInt(paramIndex++, rs.getInt("preisEinkauf"));
                        insertStmt.setString(paramIndex++, rs.getString("waehrungPreis"));
                        insertStmt.setString(paramIndex++, rs.getString("lieferantName"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("bestelltAm"));
                        insertStmt.setString(paramIndex++, rs.getString("bestellNummer"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("wareneingangTS_plan"));
                        insertStmt.setString(paramIndex++, rs.getString("statusAnlieferungAnTS"));
                        insertStmt.setString(paramIndex++, rs.getString("warenEingangTS"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("wareneingangTS_Fakt"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("auslieferungNachPlan"));
                        insertStmt.setString(paramIndex++, rs.getString("statusAuslieferungExtern"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("auslieferungExtern_Fakt"));
                        insertStmt.setString(paramIndex++, rs.getString("zahlungsbedingungen"));
                        insertStmt.setString(paramIndex++, rs.getString("kommentEinkauf"));
                        insertStmt.setString(paramIndex++, rs.getString("statusAuslieferungBS"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("lieferungBS_Fakt"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("wareneingang_Fakt"));
                        insertStmt.setString(paramIndex++, rs.getString("kommentarBS"));
                        insertStmt.setDouble(paramIndex++, rs.getDouble("gewicht"));
                        insertStmt.setString(paramIndex++, rs.getString("einhMasse"));
                        insertStmt.setDouble(paramIndex++, rs.getDouble("laenge"));
                        insertStmt.setDouble(paramIndex++, rs.getDouble("breite"));
                        insertStmt.setDouble(paramIndex++, rs.getDouble("hoehe"));
                        insertStmt.setString(paramIndex++, rs.getString("lxBxH"));
                        insertStmt.setString(paramIndex++, rs.getString("einheitLBH"));
                        insertStmt.setString(paramIndex++, rs.getString("kolli"));
                        insertStmt.setString(paramIndex++, rs.getString("packListe"));
                        insertStmt.setString(paramIndex++, rs.getString("containerNr"));
                        insertStmt.setString(paramIndex++, rs.getString("rgNummeranAG"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("datumBS"));
                        insertStmt.setString(paramIndex++, rs.getString("weitergerechnetAnAG"));
                        insertStmt.setString(paramIndex++, rs.getString("summeWeitergerechnetanAG_cents"));
                        insertStmt.setString(paramIndex++, rs.getString("summe_nach_Weiterrechnung"));
                        insertStmt.setString(paramIndex++, rs.getString("kundenInRechnungStellen"));
                        insertStmt.setString(paramIndex++, rs.getString("waehrungRechn"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("created_at"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("updated_at"));
                        
                        insertStmt.executeUpdate();
                        rowCount++;
                    }
                    
                    System.out.println("Резервная копия таблицы успешно создана: " + backupFilePath);
                    System.out.println("Скопировано записей: " + rowCount);
                    return true;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Ошибка при создании резервной копии таблицы: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Метод для восстановления таблицы из резервной копии
    public boolean restoreFromBackup(String backupFilePath, boolean overwriteExisting) {
        if (backupFilePath == null || backupFilePath.trim().isEmpty()) {
            System.err.println("Ошибка: путь к файлу резервной копии не может быть пустым");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("Ошибка: соединение с базой данных не установлено");
            return false;
        }
        
        try {
            // Проверяем существование файла резервной копии
            java.io.File backupFile = new java.io.File(backupFilePath);
            if (!backupFile.exists()) {
                System.err.println("Ошибка: файл резервной копии не найден: " + backupFilePath);
                return false;
            }
            
            // Подключаемся к резервной базе данных
            String backupDBUrl = "jdbc:sqlite:" + backupFilePath;
            
            try (Connection backupConnection = DriverManager.getConnection(backupDBUrl)) {
                
                // Проверяем, существует ли таблица в резервной копии
                try (Statement checkStmt = backupConnection.createStatement();
                     ResultSet rs = checkStmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" +tabLiefName+ "'")) {
                    
                    if (!rs.next()) {
                        System.err.println("Ошибка: таблица 'lieferung' не найдена в резервной копии");
                        return false;
                    }
                }
                
                // Если нужно перезаписать существующую таблицу
                if (overwriteExisting) {
                    try (Statement dropStmt = connection.createStatement()) {
                        dropStmt.execute("DROP TABLE IF EXISTS lieferung");
                        System.out.println("Существующая таблица удалена");
                    }
                }
                
                // Создаем таблицу в основной базе (если не существует)
                createTableLieferungen();
                
                // Копируем данные из резервной копии
                String selectSQL = "SELECT * FROM "+ tabLiefName;
                String insertSQL = "INSERT INTO " + tabLiefName+ " (" +
                    "id, neu, aktiv, agrNummer, anfordNummer, menge, einhMenge, einheit, " +
                    "beschreibung, de_eu, abteilung, anforderungDatum, wunschLieferDatum, " +
                    "anfordPerson, unterschriftMH, unterschriftKunde, anEinkaufWeiterGeleitetMH, " +
                    "invest_Leasing, summeMat_Leist_cents, waehrungSummeMat, preisEinkauf, " +
                    "waehrungPreis, lieferantName, bestelltAm, bestellNummer, wareneingangTS_plan, " +
                    "statusAnlieferungAnTS, warenEingangTS, wareneingangTS_Fakt, auslieferungNachPlan, " +
                    "statusAuslieferungExtern, auslieferungExtern_Fakt, zahlungsbedingungen, " +
                    "kommentEinkauf, statusAuslieferungBS, lieferungBS_Fakt, " +
                    "wareneingang_Fakt, kommentarBS, gewicht, einhMasse, laenge, breite, hoehe, " +
                    "lxBxH, einheitLBH, kolli, packListe, containerNr, rgNummeranAG, datumBS, " +
                    "weitergerechnetAnAG, summeWeitergerechnetanAG_cents, summe_nach_Weiterrechnung, " +
                    "kundenInRechnungStellen, waehrungRechn, created_at, updated_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                try (Statement selectStmt = backupConnection.createStatement();
                     ResultSet rs = selectStmt.executeQuery(selectSQL);
                     PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
                    
                    int rowCount = 0;
                    while (rs.next()) {
                        int paramIndex = 1;
                        
                        insertStmt.setInt(paramIndex++, rs.getInt("id"));
                        insertStmt.setBoolean(paramIndex++, rs.getBoolean("neu"));
                        insertStmt.setBoolean(paramIndex++, rs.getBoolean("aktiv"));
                        insertStmt.setString(paramIndex++, rs.getString("agrNummer"));
                        insertStmt.setString(paramIndex++, rs.getString("anfordNummer"));
                        insertStmt.setDouble(paramIndex++, rs.getInt("menge"));
                        insertStmt.setString(paramIndex++, rs.getString("einhMenge"));
                        insertStmt.setString(paramIndex++, rs.getString("einheit"));
                        insertStmt.setString(paramIndex++, rs.getString("beschreibung"));
                        insertStmt.setString(paramIndex++, rs.getString("de_eu"));
                        insertStmt.setString(paramIndex++, rs.getString("abteilung"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("anforderungDatum"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("wunschLieferDatum"));
                        insertStmt.setString(paramIndex++, rs.getString("anfordPerson"));
                        insertStmt.setBoolean(paramIndex++, rs.getBoolean("unterschriftMH"));
                        insertStmt.setBoolean(paramIndex++, rs.getBoolean("unterschriftKunde"));
                        insertStmt.setBoolean(paramIndex++, rs.getBoolean("anEinkaufWeiterGeleitetMH"));
                        insertStmt.setBoolean(paramIndex++, rs.getBoolean("invest_Leasing"));
                        insertStmt.setInt(paramIndex++, rs.getInt("summeMat_Leist_cents"));
                        insertStmt.setInt(paramIndex++, rs.getInt("preisEinkauf"));
                        insertStmt.setString(paramIndex++, rs.getString("lieferantName"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("bestelltAm"));
                        insertStmt.setString(paramIndex++, rs.getString("bestellNummer"));
                        insertStmt.setString(paramIndex++, rs.getString("statusEinkauf"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("warenEingangTS"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("wareneingangTS_plan"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("wareneingangTS_Fakt"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("auslieferungNachPlan"));
                        insertStmt.setString(paramIndex++, rs.getString("zahlungsbedingungen"));
                        insertStmt.setString(paramIndex++, rs.getString("kommentEinkauf"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("terminWareneingangPlan"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("wareneingang_Fakt"));
                        insertStmt.setString(paramIndex++, rs.getString("kommentarBS"));
                        insertStmt.setString(paramIndex++, rs.getString("statusAuslieferungBS"));
                        insertStmt.setDouble(paramIndex++, rs.getDouble("gewicht"));
                        insertStmt.setString(paramIndex++, rs.getString("einhMasse"));
                        insertStmt.setDouble(paramIndex++, rs.getDouble("laenge"));
                        insertStmt.setDouble(paramIndex++, rs.getDouble("breite"));
                        insertStmt.setDouble(paramIndex++, rs.getDouble("hoehe"));
                        insertStmt.setString(paramIndex++, rs.getString("einheitLBH"));
                        insertStmt.setString(paramIndex++, rs.getString("kolli"));
                        insertStmt.setString(paramIndex++, rs.getString("packListe"));
                        insertStmt.setString(paramIndex++, rs.getString("containerNr"));
                        insertStmt.setString(paramIndex++, rs.getString("rgNummeranAG"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("rechnungDatum"));
                        insertStmt.setString(paramIndex++, rs.getString("summeWeitergerechnetanAG_cents"));
                        insertStmt.setString(paramIndex++, rs.getString("summe_nach_Weiterrechnung"));
                        insertStmt.setString(paramIndex++, rs.getString("kundenInRechnungStellen"));
                        insertStmt.setString(paramIndex++, rs.getString("info01"));
                        insertStmt.setString(paramIndex++, rs.getString("info02"));
                        insertStmt.setString(paramIndex++, rs.getString("info03"));
                        insertStmt.setString(paramIndex++, rs.getString("info04"));
                        insertStmt.setString(paramIndex++, rs.getString("info05"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("created_at"));
                        insertStmt.setTimestamp(paramIndex++, rs.getTimestamp("updated_at"));
                        
                        insertStmt.executeUpdate();
                        rowCount++;
                    }
                    
                    System.out.println("Восстановление из резервной копии выполнено успешно");
                    System.out.println("Восстановлено записей: " + rowCount);
                    return true;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Ошибка при восстановлении из резервной копии: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    // Метод для создания резервной копии с метаданными
    public boolean createBackupWithMetadata(String backupFilePath, String description) {
        if (backupFilePath == null || backupFilePath.trim().isEmpty()) {
            System.err.println("Ошибка: путь к файлу резервной копии не может быть пустым");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("Ошибка: соединение с базой данных не установлено");
            return false;
        }
        
        try {
            // Создаем резервную копию
            boolean backupSuccess = createTableBackup(backupFilePath);
            
            if (backupSuccess) {
                // Создаем файл с метаданными
                String metadataFilePath = backupFilePath + ".meta";
                try (PrintWriter writer = new PrintWriter(new FileWriter(metadataFilePath))) {
                    writer.println("=== Метаданные резервной копии ===");
                    writer.println("Дата создания: " + new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
                    writer.println("Описание: " + (description != null ? description : "Без описания"));
                    writer.println("Количество записей: " + getLieferungenCount());
                    writer.println("Активных записей: " + getActiveLieferungenCount());
                    writer.println("Версия схемы: 1.0");
                    writer.println("Формат: SQLite");
                }
                
                System.out.println("Метаданные сохранены в: " + metadataFilePath);
                return true;
            }
            
            return false;
            
        } catch (IOException e) {
            System.err.println("Ошибка при создании метаданных: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Экспортирует список Lieferung в XLS файл с диалогом выбора места сохранения
     * @param lieferList список объектов Lieferung для экспорта
     * @return true если экспорт успешен, false в противном случае
     * 
     * Пример использования:
     * <pre>
     * ArrayList&lt;Lieferung&gt; lieferList = dbManager.getAllLieferungen();
     * boolean success = dbManager.exportListToXls(lieferList);
     * if (success) {
     *     System.out.println("Export erfolgreich!");
     * }
     * </pre>
     */
    public boolean exportListToXls(ArrayList<Lieferung> lieferList) {
        if (lieferList == null || lieferList.isEmpty()) {
            System.out.println("Liste ist leer, nichts zu exportieren");
            return false;
        }
        
        // Открываем диалог выбора файла для сохранения
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Speichern als Excel Datei");
        
        // Устанавливаем фильтр для XLS файлов
        FileNameExtensionFilter xlsFilter = new FileNameExtensionFilter(
            "Excel 97-2003 files (*.xls)", "xls"
        );
        fileChooser.setFileFilter(xlsFilter);
        
        // Устанавливаем имя файла по умолчанию
        fileChooser.setSelectedFile(new java.io.File("lieferungen_export.xls"));
        
        // Показываем диалог сохранения
        int result = fileChooser.showSaveDialog(null);
        
        if (result != JFileChooser.APPROVE_OPTION) {
            System.out.println("Export abgebrochen");
            return false;
        }
        
        String filePath = fileChooser.getSelectedFile().getAbsolutePath();
        
        // Добавляем расширение .xls если его нет
        if (!filePath.toLowerCase().endsWith(".xls")) {
            filePath += ".xls";
        }
        
        // Экспортируем в файл
        return exportListToXlsFile(lieferList, filePath);
    }
    
    /**
     * Экспортирует список Lieferung в XLS файл
     * @param lieferList список объектов Lieferung для экспорта
     * @param filePath путь для сохранения файла
     * @return true если экспорт успешен, false в противном случае
     */
    private boolean exportListToXlsFile(ArrayList<Lieferung> lieferList, String filePath) {
        Workbook workbook = null;
        FileOutputStream fileOut = null;
        
        try {
            // Создаем новую рабочую книгу (XLS формат)
            workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet("Lieferungen");
            
            // Создаем стиль для заголовков
            CellStyle headerStyle = workbook.createCellStyle();
            
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            
            // Создаем строку заголовков
            Row headerRow = sheet.createRow(0);
            String[] headers = {
                "ID", "Neu", "Aktiv", "AGR Nummer", "Anford Nummer", "Menge", "Einh Menge",
                "Beschreibung", "DE/EU", "Abteilung", "Anforderung Datum", "Wunsch Liefer Datum", 
                "Anford Person", "Unterschrift MH", "Unterschrift Kunde", "An Einkauf Weitergeleitet MH",
                "Invest/Leasing", "Summe Mat/Leist", "Preis Einkauf", "Lieferant Name", "Bestellt Am",
                "Bestell Nummer", "Status Einkauf", "Waren Eingang TS", "Wareneingang TS Plan", 
                "Wareneingang TS Fakt", "Auslieferung Nach Plan", "Zahlungsbedingungen", "Komment Einkauf",
                "Termin Wareneingang Plan", "Wareneingang Fakt", "Kommentar BS", "Status Auslieferung BS",
                "Gewicht", "Einh Masse", "Laenge", "Breite", "Hoehe", "Einheit LBH", "Kolli",
                "Pack Liste", "Container Nr", "RG Nummer an AG", "Rechnung Datum", 
                "Ist Weitergerechnet An AG", "Summe Weitergerechnet an AG", "Summe nach Weiterrechnung",
                "Kunden In Rechnung Stellen", "Info01", "Info02", "Info03", "Info04", "Info05"
            };
            
            // Заполняем заголовки
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
            
            // Заполняем данными из списка
            int rowNum = 1;
            for (Lieferung lief : lieferList) {
                Row row = sheet.createRow(rowNum++);
                int colNum = 0;
                
                // Заполняем ячейки данными
                row.createCell(colNum++).setCellValue(lief.getId());
                row.createCell(colNum++).setCellValue(lief.isNeu() ? "Ja" : "Nein");
                row.createCell(colNum++).setCellValue(lief.isAktiv() ? "Ja" : "Nein");
                row.createCell(colNum++).setCellValue(lief.getAgrNummer() != null ? lief.getAgrNummer() : "");
                row.createCell(colNum++).setCellValue(lief.getAnfordNummer() != null ? lief.getAnfordNummer() : "");
                row.createCell(colNum++).setCellValue(lief.getMenge());
                row.createCell(colNum++).setCellValue(lief.getEinhMenge() != null ? lief.getEinhMenge() : "");
                row.createCell(colNum++).setCellValue(lief.getBeschreibung() != null ? lief.getBeschreibung() : "");
                row.createCell(colNum++).setCellValue(lief.getDe_eu() != null ? lief.getDe_eu() : "");
                row.createCell(colNum++).setCellValue(lief.getProjectName() != null ? lief.getProjectName() : "");
                row.createCell(colNum++).setCellValue(lief.getAnforderungDatum() != null ? lief.getAnforderungDatum() : "");
                row.createCell(colNum++).setCellValue(lief.getWunschLieferDatum() != null ? lief.getWunschLieferDatum() : "");
                row.createCell(colNum++).setCellValue(lief.getAnfordPerson() != null ? lief.getAnfordPerson() : "");
                row.createCell(colNum++).setCellValue(lief.isUnterschriftMH() ? "Ja" : "Nein");
                row.createCell(colNum++).setCellValue(lief.isUnterschriftKunde() ? "Ja" : "Nein");
                row.createCell(colNum++).setCellValue(lief.isAnEinkaufWeiterGeleitetMH() ? "Ja" : "Nein");
                row.createCell(colNum++).setCellValue(lief.isInvest_Leasing() ? "Ja" : "Nein");
                row.createCell(colNum++).setCellValue(lief.getSummeMat_Leist_cents());
                row.createCell(colNum++).setCellValue(lief.getPreisEinkauf());
                row.createCell(colNum++).setCellValue(lief.getLieferantName() != null ? lief.getLieferantName() : "");
                row.createCell(colNum++).setCellValue(lief.getBestelltAm() != null ? lief.getBestelltAm() : "");
                row.createCell(colNum++).setCellValue(lief.getBestellNummer() != null ? lief.getBestellNummer() : "");
                row.createCell(colNum++).setCellValue(lief.getStatusEinkauf() != null ? lief.getStatusEinkauf() : "");
                row.createCell(colNum++).setCellValue(lief.getWarenEingangTS() != null ? lief.getWarenEingangTS() : "");
                row.createCell(colNum++).setCellValue(lief.getWareneingangTS_plan() != null ? lief.getWareneingangTS_plan() : "");
                row.createCell(colNum++).setCellValue(lief.getWareneingangTS_Fakt() != null ? lief.getWareneingangTS_Fakt() : "");
                row.createCell(colNum++).setCellValue(lief.getAuslieferungNachPlan() != null ? lief.getAuslieferungNachPlan() : "");
                row.createCell(colNum++).setCellValue(lief.getZahlungsbedingungen() != null ? lief.getZahlungsbedingungen() : "");
                row.createCell(colNum++).setCellValue(lief.getKommentEinkauf() != null ? lief.getKommentEinkauf() : "");
                row.createCell(colNum++).setCellValue(lief.getWareneingangTS_plan() != null ? lief.getWareneingangTS_plan() : "");
                row.createCell(colNum++).setCellValue(lief.getWareneingang_Fakt() != null ? lief.getWareneingang_Fakt() : "");
                row.createCell(colNum++).setCellValue(lief.getKommentarBS() != null ? lief.getKommentarBS() : "");
                row.createCell(colNum++).setCellValue(lief.getStatusAuslieferungBS() != null ? lief.getStatusAuslieferungBS() : "");
                row.createCell(colNum++).setCellValue(lief.getGewicht());
                row.createCell(colNum++).setCellValue(lief.getEinhMasse() != null ? lief.getEinhMasse() : "");
                row.createCell(colNum++).setCellValue(lief.getLaenge());
                row.createCell(colNum++).setCellValue(lief.getBreite());
                row.createCell(colNum++).setCellValue(lief.getHoehe());
                row.createCell(colNum++).setCellValue(lief.getEinheitLBH() != null ? lief.getEinheitLBH() : "");
                row.createCell(colNum++).setCellValue(lief.getKolli() != null ? lief.getKolli() : "");
                row.createCell(colNum++).setCellValue(lief.getPackListe() != null ? lief.getPackListe() : "");
                row.createCell(colNum++).setCellValue(lief.getContainerNr() != null ? lief.getContainerNr() : "");
                row.createCell(colNum++).setCellValue(lief.getRgNummeranAG() != null ? lief.getRgNummeranAG() : "");
                row.createCell(colNum++).setCellValue(lief.getDatumBS() != null ? lief.getDatumBS() : "");
                row.createCell(colNum++).setCellValue(lief.getIstWeitergerechnetAnAG() ? "Ja" : "Nein");
                row.createCell(colNum++).setCellValue(lief.getSummeWeitergerechnetanAG_cents());
                row.createCell(colNum++).setCellValue(lief.getSumme_nach_Weiterrechnung());
                row.createCell(colNum++).setCellValue(lief.getKundenInRechnungStellen());
                row.createCell(colNum++).setCellValue(lief.getInfo01() != null ? lief.getInfo01() : "");
                row.createCell(colNum++).setCellValue(lief.getInfo02() != null ? lief.getInfo02() : "");
                row.createCell(colNum++).setCellValue(lief.getInfo03() != null ? lief.getInfo03() : "");
                row.createCell(colNum++).setCellValue(lief.getInfo04() != null ? lief.getInfo04() : "");
                row.createCell(colNum++).setCellValue(lief.getInfo05() != null ? lief.getInfo05() : "");
            }
            
            // Автоматически подгоняем ширину колонок
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Сохраняем файл
            fileOut = new FileOutputStream(filePath);
            workbook.write(fileOut);
            
            System.out.println("Export erfolgreich: " + lieferList.size() + " Lieferungen nach " + filePath);
            return true;
            
        } catch (Exception e) {
            System.err.println("Fehler beim Export nach XLS: " + e.getMessage());
            e.printStackTrace();
            return false;
            
        } finally {
            // Закрываем ресурсы
            try {
                if (fileOut != null) {
                    fileOut.close();
                }
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                System.err.println("Fehler beim Schließen der Datei: " + e.getMessage());
            }
        }
    }
    
    // ==================== МЕТОДЫ ДЛЯ РАБОТЫ С ТАБЛИЦЕЙ ARTIKEL ====================
    
    /**
     * Создает таблицу artikel в базе данных
     */
    public void createArtikelTable() {
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return;
        }
        
        // Проверяем, существует ли таблица
        if (tableExists(lagerTab)) {
            System.out.println("Table 'lager' already exists");
            ensureGeliefertAnBsColumnInLager();
            return;
        }
        
        String createTableSQL = "CREATE TABLE "+ lagerTab+ " (" +
            "id INT IDENTITY(1,1) PRIMARY KEY," +
            "material_EN NVARCHAR(255)," +
            "material_DE NVARCHAR(255)," +
            "dimention NVARCHAR(255)," +
            "supplier NVARCHAR(255)," +
            "colli NVARCHAR(255)," +
            "order_Number NVARCHAR(255)," +
            "geliefert_an_bs NVARCHAR(255)," +
            "anfordrung NVARCHAR(255)," +
            "reloaded_in NVARCHAR(255)," +
            "container_Number NVARCHAR(255)," +
            "booking_Number NVARCHAR(255)," +
            "add_Info NVARCHAR(1000)," +
            "good_Receipt NVARCHAR(255)," +
            "amount_of_Containers NVARCHAR(255)," +
            "weight NVARCHAR(255)," +
            "agreement NVARCHAR(255)," +
            "shipment NVARCHAR(255)," +
            "date_of_Arrival NVARCHAR(50)," +
            "invoice_Proforma NVARCHAR(255)," +
            "menge NVARCHAR(255)," +
            "einzelMenge NVARCHAR(255)," +
            "einheit NVARCHAR(255)," +
            "lagerOrt NVARCHAR(255)," +
            "lagerMenge NVARCHAR(255)," +
            "info01 NVARCHAR(255)," +
            "info02 NVARCHAR(255)," +
            "info03 NVARCHAR(255)," +
            "preisEuro FLOAT," +
            "created_at DATETIME2 DEFAULT GETDATE()," +
            "updated_at DATETIME2 DEFAULT GETDATE()" +
            ")";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Tabelle " + lagerTab + "  is created successfully");
        } catch (SQLException e) {
            System.err.println("Error by creating lager Table: " + e.getMessage());
            e.printStackTrace();
        }
        ensureGeliefertAnBsColumnInLager();
    }

    private void ensureGeliefertAnBsColumnInLager() {
        if (connection == null) {
            return;
        }
        if (!tableExists(lagerTab)) {
            return;
        }
        // Мягкая миграция: добавляем колонку только если её ещё нет.
        String sql = "IF COL_LENGTH('" + lagerTab + "', 'geliefert_an_bs') IS NULL "
                + "ALTER TABLE " + lagerTab + " ADD geliefert_an_bs NVARCHAR(255)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Fehler beim Sicherstellen der Spalte geliefert_an_bs: " + e.getMessage());
        }
    }
    
    /**
     * Добавляет новый элемент Artikel в таблицу
     * @param artikel объект Artikel для добавления
     * @return true если добавление успешно, false в противном случае
     */
    public boolean addArtikel(Artikel artikel) {
        boolean addOK = false;
        
        if (artikel == null) {
            System.err.println("Error: object Artikel can't be null");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return false;
        }
        
        String insertSQL = "INSERT INTO " + lagerTab +" (" +
            "material_EN, material_DE, dimention, supplier, colli, " +
            "order_Number, geliefert_an_bs, anfordrung, reloaded_in, container_Number, booking_Number, " +
            "add_Info, good_Receipt, amount_of_Containers, weight, agreement, " +
            "shipment, date_of_Arrival, invoice_Proforma, menge, einzelMenge, einheit, " +
            "lagerOrt, lagerMenge, info01, info02, info03, preisEuro) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            int paramIndex = 1;
            
            // Устанавливаем значения параметров
            pstmt.setString(paramIndex++, artikel.getMaterial_EN());
            pstmt.setString(paramIndex++, artikel.getMaterial_DE());
            pstmt.setString(paramIndex++, artikel.getDimention());
            pstmt.setString(paramIndex++, artikel.getSupplier());
            pstmt.setString(paramIndex++, artikel.getColli());
            pstmt.setString(paramIndex++, artikel.getOrder_Number());
            pstmt.setString(paramIndex++, String.valueOf(artikel.getMenge_gelief_an_BS()));
            pstmt.setString(paramIndex++, artikel.getAnfordrung());
            pstmt.setString(paramIndex++, artikel.getReloaded_in());
            pstmt.setString(paramIndex++, artikel.getContainer_Number());
            pstmt.setString(paramIndex++, artikel.getBooking_Number());
            pstmt.setString(paramIndex++, artikel.getAdd_Info());
            pstmt.setString(paramIndex++, artikel.getGood_Receipt());
            pstmt.setString(paramIndex++, artikel.getAmount_of_Containers());
            pstmt.setString(paramIndex++, artikel.getWeight());
            pstmt.setString(paramIndex++, artikel.getBanfNrArt());
            pstmt.setString(paramIndex++, artikel.getShipment());
            pstmt.setString(paramIndex++, artikel.getDate_of_Arrival());
            pstmt.setString(paramIndex++, artikel.getInvoice_Proforma());
            pstmt.setString(paramIndex++, String.valueOf(artikel.getMenge()));
            pstmt.setString(paramIndex++, String.valueOf(artikel.getMenge_Baustelle_gel()));
            pstmt.setString(paramIndex++, artikel.getEinheit());
            pstmt.setString(paramIndex++, artikel.getLagerOrt());
            pstmt.setString(paramIndex++, artikel.getLagerMenge());
            pstmt.setString(paramIndex++, artikel.getInfo01());
            pstmt.setString(paramIndex++, artikel.getInfo02());
            pstmt.setString(paramIndex++, artikel.getInfo03());
             pstmt.setFloat(paramIndex++, artikel.getPreisEuro());
            // Выполняем запрос
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                addOK = true;
                System.out.println("Neuen Artikel ist erfolgreich in die Tabelle eingetragen");
            } else {
                System.err.println("ERROR: Artikel wurde nicht gespeichert");
            }
            
        } catch (SQLException e) {
            System.err.println("Fehler beim speichern von Artikel in DB: " + e.getMessage());
            e.printStackTrace();
        }
        
        return addOK;
    }
    
    /**
     * Fügt einen neuen Artikel in die Tabelle lager ein und gibt die von der DB vergebene ID zurück.
     * Für Artikel mit id <= 0 (z. B. "Neu" in ArtikelListe); die ID wird von IDENTITY erzeugt.
     * @param artikel der einzufügende Artikel (id wird ignoriert)
     * @return die neue ID (> 0) bei Erfolg, sonst 0
     */
    public int addArtikelAndReturnId(Artikel artikel) {
        if (artikel == null || !ensureConnection()) {
            return 0;
        }
        String insertSQL = "INSERT INTO " +lagerTab +" (" +
            "material_EN, material_DE, dimention, supplier, colli, " +
            "order_Number, geliefert_an_bs, anfordrung, reloaded_in, container_Number, booking_Number, " +
            "add_Info, good_Receipt, amount_of_Containers, weight, agreement, " +
            "shipment, date_of_Arrival, invoice_Proforma, menge, einzelMenge, einheit, " +
            "lagerOrt, lagerMenge, info01, info02, info03, preisEuro) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            int paramIndex = 1;
            pstmt.setString(paramIndex++, artikel.getMaterial_EN() != null ? artikel.getMaterial_EN() : "");
            pstmt.setString(paramIndex++, artikel.getMaterial_DE() != null ? artikel.getMaterial_DE() : "");
            pstmt.setString(paramIndex++, artikel.getDimention() != null ? artikel.getDimention() : "");
            pstmt.setString(paramIndex++, artikel.getSupplier() != null ? artikel.getSupplier() : "");
            pstmt.setString(paramIndex++, artikel.getColli() != null ? artikel.getColli() : "");
            pstmt.setString(paramIndex++, artikel.getOrder_Number() != null ? artikel.getOrder_Number() : "");
            pstmt.setString(paramIndex++, String.valueOf(artikel.getMenge_gelief_an_BS()));
            pstmt.setString(paramIndex++, artikel.getAnfordrung() != null ? artikel.getAnfordrung() : "");
            pstmt.setString(paramIndex++, artikel.getReloaded_in() != null ? artikel.getReloaded_in() : "");
            pstmt.setString(paramIndex++, artikel.getContainer_Number() != null ? artikel.getContainer_Number() : "");
            pstmt.setString(paramIndex++, artikel.getBooking_Number() != null ? artikel.getBooking_Number() : "");
            pstmt.setString(paramIndex++, artikel.getAdd_Info() != null ? artikel.getAdd_Info() : "");
            pstmt.setString(paramIndex++, artikel.getGood_Receipt() != null ? artikel.getGood_Receipt() : "");
            pstmt.setString(paramIndex++, artikel.getAmount_of_Containers() != null ? artikel.getAmount_of_Containers() : "");
            pstmt.setString(paramIndex++, artikel.getWeight() != null ? artikel.getWeight() : "");
            pstmt.setString(paramIndex++, artikel.getBanfNrArt() != null ? artikel.getBanfNrArt() : "");
            pstmt.setString(paramIndex++, artikel.getShipment() != null ? artikel.getShipment() : "");
            pstmt.setString(paramIndex++, artikel.getDate_of_Arrival() != null ? artikel.getDate_of_Arrival() : "");
            pstmt.setString(paramIndex++, artikel.getInvoice_Proforma() != null ? artikel.getInvoice_Proforma() : "");
            pstmt.setString(paramIndex++, String.valueOf(artikel.getMenge()));
            pstmt.setString(paramIndex++, String.valueOf(artikel.getMenge_Baustelle_gel()));
            pstmt.setString(paramIndex++, artikel.getEinheit() != null ? artikel.getEinheit() : "");
            pstmt.setString(paramIndex++, artikel.getLagerOrt() != null ? artikel.getLagerOrt() : "");
            pstmt.setString(paramIndex++, artikel.getLagerMenge() != null ? artikel.getLagerMenge() : "");
            pstmt.setString(paramIndex++, artikel.getInfo01() != null ? artikel.getInfo01() : "");
            pstmt.setString(paramIndex++, artikel.getInfo02() != null ? artikel.getInfo02() : "");
            pstmt.setString(paramIndex++, artikel.getInfo03() != null ? artikel.getInfo03() : "");
              pstmt.setFloat(paramIndex++, artikel.getPreisEuro());
            if (pstmt.executeUpdate() <= 0) return 0;
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int newId = rs.getInt(1);
                    if (newId > 0) return newId;
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Einfügen von Artikel (addArtikelAndReturnId): " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Markiert Artikel als "exp.", wenn es in Tabelle lieferung keinen Datensatz
     * mit passender Anforderungsnummer gibt.
     * Verglichen wird lager.anfordrung mit lieferung.anfordNummer (trimmed).
     * Bereits markierte Einträge (exp.*) bleiben unverändert.
     * @return Anzahl aktualisierter Artikel
     */
    public int markArtikelOhneLieferungMitExpPrefix() {
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return 0;
        }
        String sql = "UPDATE " + lagerTab + " SET anfordrung = ? + LTRIM(RTRIM(anfordrung)) "
                + "WHERE LTRIM(RTRIM(ISNULL(anfordrung, ''))) <> '' "
                + "AND LOWER(LTRIM(RTRIM(anfordrung))) NOT LIKE LOWER(? + '%') "
                + "AND NOT EXISTS ("
                + "  SELECT 1 FROM " + tabLiefName + " l "
                + "  WHERE LTRIM(RTRIM(ISNULL(l.anfordNummer, ''))) = LTRIM(RTRIM(ISNULL(" + lagerTab + ".anfordrung, '')))"
                + ")";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "exp.");
            ps.setString(2, "exp.");
            int updated = ps.executeUpdate();
            System.out.println("Artikel mit Prefix exp. markiert: " + updated);
            return updated;
        } catch (SQLException e) {
            System.err.println("Fehler beim Markieren der Artikel ohne Lieferung: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * Dry-run fuer Post-Import-Sync: zeigt, wie viele Lager-Artikel durch
     * vorhandene Lieferungen (Match ueber anfordrung/anfordNummer) ergaenzt werden koennen.
     */
    public String previewPostImportSyncLagerFromLieferung() {
        if (!ensureConnection()) {
            return "ERROR: keine Verbindung mit DB";
        }
        String agreementMissing =
                "(LTRIM(RTRIM(ISNULL(l.agreement, ''))) = '' OR UPPER(LTRIM(RTRIM(ISNULL(l.agreement, '')))) IN ('__||','-','NA','N/A'))";
        String changedCondition =
                "(" + agreementMissing + " AND LTRIM(RTRIM(ISNULL(lf.agrNummer, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.supplier, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.lieferantName, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.colli, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.kolli, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.container_Number, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.containerNr, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.date_of_Arrival, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.wareneingangTS_plan, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.good_Receipt, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.wareneingang_Fakt, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.order_Number, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.bestellNummer, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.info03, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.abteilung, ''))) <> '')";

        String countSql = "SELECT COUNT(1) "
                + "FROM " + lagerTab + " l "
                + "INNER JOIN " + tabLiefName + " lf "
                + "ON LTRIM(RTRIM(ISNULL(l.anfordrung, ''))) = LTRIM(RTRIM(ISNULL(lf.anfordNummer, ''))) "
                + "WHERE LTRIM(RTRIM(ISNULL(l.anfordrung, ''))) <> '' "
                + "AND " + changedCondition;
        String cleanupPacklisteCountSql = "SELECT COUNT(1) FROM " + lagerTab + " l "
                + "WHERE CHARINDEX('|', ISNULL(l.info02, '')) > 0";
        String cleanupVerbrauchCountSql = "SELECT COUNT(1) FROM " + lagerTab + " l "
                + "WHERE LTRIM(RTRIM(ISNULL(l.info01, ''))) <> '' "
                + "AND ("
                + "LTRIM(RTRIM(ISNULL(l.info01, ''))) = LTRIM(RTRIM(ISNULL(l.order_Number, ''))) "
                + "OR LTRIM(RTRIM(ISNULL(l.info01, ''))) = LTRIM(RTRIM(ISNULL(l.booking_Number, ''))) "
                + "OR LTRIM(RTRIM(ISNULL(l.info01, ''))) LIKE '[0-9][0-9][0-9][0-9][0-9][0-9]%'"
                + ")";
        String cleanupBestellnummerCountSql = "SELECT COUNT(1) FROM " + tabLiefName + " lf "
                + "WHERE LTRIM(RTRIM(ISNULL(lf.bestellNummer, ''))) <> '' "
                + "AND UPPER(LTRIM(RTRIM(ISNULL(lf.bestellNummer, '')))) LIKE '%SAP'";

        StringBuilder sb = new StringBuilder();
        sb.append("=== Post-Import Sync (Dry-Run) ===\n");
        sb.append("Quelle: lieferung  ->  Ziel: lager\n");
        sb.append("Join: lager.anfordrung == lieferung.anfordNummer (trimmed)\n\n");
        sb.append("Felder, die nur bei leerem Zielwert gefuellt werden:\n");
        sb.append(" - agreement <- agrNummer\n");
        sb.append(" - supplier <- lieferantName\n");
        sb.append(" - colli <- kolli\n");
        sb.append(" - container_Number <- containerNr\n");
        sb.append(" - date_of_Arrival <- wareneingangTS_plan\n");
        sb.append(" - good_Receipt <- wareneingang_Fakt\n");
        sb.append(" - order_Number <- bestellNummer\n");
        sb.append(" - info03 <- abteilung (Projektname)\n\n");
        sb.append("Zusaetzliche Korrekturen fuer alte Importfehler:\n");
        sb.append(" - Packliste (info02): Teil mit Preis nach '|' entfernen\n");
        sb.append(" - Verbrauchte Menge (info01): leeren, wenn dort eine Bestellnummer steht\n\n");
        sb.append(" - lieferung.bestellNummer: technischen Suffix SAP entfernen\n\n");
        try (PreparedStatement ps = connection.prepareStatement(countSql);
             ResultSet rs = ps.executeQuery()) {
            int count = 0;
            if (rs.next()) {
                count = rs.getInt(1);
            }
            sb.append("Potenziell aktualisierbare Lager-Zeilen: ").append(count).append("\n");
        } catch (SQLException e) {
            sb.append("Fehler im Dry-Run: ").append(e.getMessage()).append("\n");
            System.err.println("Fehler beim Dry-Run Post-Import-Sync: " + e.getMessage());
            e.printStackTrace();
        }
        try (PreparedStatement ps = connection.prepareStatement(cleanupPacklisteCountSql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                sb.append("Potenziell korrigierbare Packliste-Zeilen: ").append(rs.getInt(1)).append("\n");
            }
        } catch (SQLException e) {
            sb.append("Fehler beim Dry-Run Packliste-Korrektur: ").append(e.getMessage()).append("\n");
        }
        try (PreparedStatement ps = connection.prepareStatement(cleanupVerbrauchCountSql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                sb.append("Potenziell korrigierbare Verbrauchte-Menge-Zeilen: ").append(rs.getInt(1)).append("\n");
            }
        } catch (SQLException e) {
            sb.append("Fehler beim Dry-Run Verbrauchte-Menge-Korrektur: ").append(e.getMessage()).append("\n");
        }
        try (PreparedStatement ps = connection.prepareStatement(cleanupBestellnummerCountSql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                sb.append("Potenziell korrigierbare bestellNummer in lieferung: ").append(rs.getInt(1)).append("\n");
            }
        } catch (SQLException e) {
            sb.append("Fehler beim Dry-Run bestellNummer-Korrektur: ").append(e.getMessage()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Fuehrt einen Post-Import-Sync aus und fuellt leere Felder in lager aus lieferung.
     * Es werden nur leere Zielwerte in lager aktualisiert.
     * @return Anzahl aktualisierter Zeilen in lager
     */
    public int postImportSyncLagerFromLieferung() {
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return 0;
        }

        String agreementMissing =
                "(LTRIM(RTRIM(ISNULL(l.agreement, ''))) = '' OR UPPER(LTRIM(RTRIM(ISNULL(l.agreement, '')))) IN ('__||','-','NA','N/A'))";
        String changedCondition =
                "(" + agreementMissing + " AND LTRIM(RTRIM(ISNULL(lf.agrNummer, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.supplier, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.lieferantName, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.colli, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.kolli, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.container_Number, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.containerNr, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.date_of_Arrival, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.wareneingangTS_plan, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.good_Receipt, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.wareneingang_Fakt, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.order_Number, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.bestellNummer, ''))) <> '') OR "
                + "(LTRIM(RTRIM(ISNULL(l.info03, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.abteilung, ''))) <> '')";

        String updateSql = "UPDATE l SET "
                + "agreement = CASE WHEN " + agreementMissing + " AND LTRIM(RTRIM(ISNULL(lf.agrNummer, ''))) <> '' THEN lf.agrNummer ELSE l.agreement END, "
                + "supplier = CASE WHEN LTRIM(RTRIM(ISNULL(l.supplier, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.lieferantName, ''))) <> '' THEN lf.lieferantName ELSE l.supplier END, "
                + "colli = CASE WHEN LTRIM(RTRIM(ISNULL(l.colli, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.kolli, ''))) <> '' THEN lf.kolli ELSE l.colli END, "
                + "container_Number = CASE WHEN LTRIM(RTRIM(ISNULL(l.container_Number, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.containerNr, ''))) <> '' THEN lf.containerNr ELSE l.container_Number END, "
                + "date_of_Arrival = CASE WHEN LTRIM(RTRIM(ISNULL(l.date_of_Arrival, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.wareneingangTS_plan, ''))) <> '' THEN lf.wareneingangTS_plan ELSE l.date_of_Arrival END, "
                + "good_Receipt = CASE WHEN LTRIM(RTRIM(ISNULL(l.good_Receipt, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.wareneingang_Fakt, ''))) <> '' THEN lf.wareneingang_Fakt ELSE l.good_Receipt END, "
                + "order_Number = CASE WHEN LTRIM(RTRIM(ISNULL(l.order_Number, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.bestellNummer, ''))) <> '' THEN lf.bestellNummer ELSE l.order_Number END, "
                + "info03 = CASE WHEN LTRIM(RTRIM(ISNULL(l.info03, ''))) = '' AND LTRIM(RTRIM(ISNULL(lf.abteilung, ''))) <> '' THEN lf.abteilung ELSE l.info03 END, "
                + "updated_at = GETDATE() "
                + "FROM " + lagerTab + " l "
                + "INNER JOIN " + tabLiefName + " lf "
                + "ON LTRIM(RTRIM(ISNULL(l.anfordrung, ''))) = LTRIM(RTRIM(ISNULL(lf.anfordNummer, ''))) "
                + "WHERE LTRIM(RTRIM(ISNULL(l.anfordrung, ''))) <> '' "
                + "AND (" + changedCondition + ")";
        // Массовый ремонт: в старых импортах в Packliste попадала цена после знака '|'.
        String cleanupPacklisteSql = "UPDATE l SET "
                + "info02 = LTRIM(RTRIM(LEFT(info02, CHARINDEX('|', info02) - 1))), "
                + "updated_at = GETDATE() "
                + "FROM " + lagerTab + " l "
                + "WHERE CHARINDEX('|', ISNULL(l.info02, '')) > 0";
        // Массовый ремонт: в info01 (verbrauchte Menge) могли попадать номера заказов.
        String cleanupVerbrauchSql = "UPDATE l SET "
                + "info01 = '', updated_at = GETDATE() "
                + "FROM " + lagerTab + " l "
                + "WHERE LTRIM(RTRIM(ISNULL(l.info01, ''))) <> '' "
                + "AND ("
                + "LTRIM(RTRIM(ISNULL(l.info01, ''))) = LTRIM(RTRIM(ISNULL(l.order_Number, ''))) "
                + "OR LTRIM(RTRIM(ISNULL(l.info01, ''))) = LTRIM(RTRIM(ISNULL(l.booking_Number, ''))) "
                + "OR LTRIM(RTRIM(ISNULL(l.info01, ''))) LIKE '[0-9][0-9][0-9][0-9][0-9][0-9]%'"
                + ")";
        // Массовая очистка: убираем технический суффикс SAP у номеров заказов поставок.
        String cleanupBestellnummerSql = "UPDATE lf SET "
                + "bestellNummer = LTRIM(RTRIM(CASE "
                + "WHEN UPPER(RIGHT(LTRIM(RTRIM(bestellNummer)), 5)) = '(SAP)' "
                + "THEN LEFT(LTRIM(RTRIM(bestellNummer)), LEN(LTRIM(RTRIM(bestellNummer))) - 5) "
                + "WHEN UPPER(RIGHT(LTRIM(RTRIM(bestellNummer)), 3)) = 'SAP' "
                + "THEN LEFT(LTRIM(RTRIM(bestellNummer)), LEN(LTRIM(RTRIM(bestellNummer))) - 3) "
                + "ELSE LTRIM(RTRIM(bestellNummer)) END)), "
                + "updated_at = GETDATE() "
                + "FROM " + tabLiefName + " lf "
                + "WHERE LTRIM(RTRIM(ISNULL(lf.bestellNummer, ''))) <> '' "
                + "AND UPPER(LTRIM(RTRIM(ISNULL(lf.bestellNummer, '')))) LIKE '%SAP'";

        try (PreparedStatement psSync = connection.prepareStatement(updateSql);
             PreparedStatement psPack = connection.prepareStatement(cleanupPacklisteSql);
             PreparedStatement psVerbrauch = connection.prepareStatement(cleanupVerbrauchSql);
             PreparedStatement psBestell = connection.prepareStatement(cleanupBestellnummerSql)) {
            int updatedSync = psSync.executeUpdate();
            int updatedPack = psPack.executeUpdate();
            int updatedVerbrauch = psVerbrauch.executeUpdate();
            int updatedBestell = psBestell.executeUpdate();
            int totalUpdated = updatedSync + updatedPack + updatedVerbrauch + updatedBestell;
            System.out.println("Post-Import Sync lager <- lieferung, aktualisierte Zeilen: " + updatedSync);
            System.out.println("Korrektur Packliste (info02), aktualisierte Zeilen: " + updatedPack);
            System.out.println("Korrektur Verbrauchte Menge (info01), aktualisierte Zeilen: " + updatedVerbrauch);
            System.out.println("Korrektur bestellNummer (Suffix SAP), aktualisierte Zeilen: " + updatedBestell);
            return totalUpdated;
        } catch (SQLException e) {
            System.err.println("Fehler beim Post-Import-Sync lager <- lieferung: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
    
    /**
     * Получает все элементы Artikel из таблицы lager
     * @return список всех элементов Artikel
     */
    public ArrayList<Artikel> getAllArtikel() {
        ArrayList<Artikel> artikelList = new ArrayList<>();
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return artikelList;
        }
        
        String selectSQL = "SELECT * FROM " +lagerTab+" ORDER BY material_DE ASC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                Artikel artikel = new Artikel();
                
                // Заполняем объект данными из ResultSet
                artikel.setId(rs.getInt("id"));
                artikel.setMaterial_EN(rs.getString("material_EN"));
                artikel.setMaterial_DE(rs.getString("material_DE"));
                artikel.setDimention(rs.getString("dimention"));
                artikel.setSupplier(rs.getString("supplier"));
                artikel.setColli(rs.getString("colli"));
                artikel.setOrder_Number(rs.getString("order_Number"));
                try {
                    String geliefertAnBsStr = rs.getString("geliefert_an_bs");
                    if (geliefertAnBsStr != null && !geliefertAnBsStr.trim().isEmpty()) {
                        artikel.setMenge_gelief_an_BS(Float.parseFloat(geliefertAnBsStr));
                    }
                } catch (Exception ignored) { }
                artikel.setAnfordrung(rs.getString("anfordrung"));
                artikel.setReloaded_in(rs.getString("reloaded_in"));
                artikel.setContainer_Number(rs.getString("container_Number"));
                artikel.setBooking_Number(rs.getString("booking_Number"));
                artikel.setAdd_Info(rs.getString("add_Info"));
                artikel.setGood_Receipt(rs.getString("good_Receipt"));
                artikel.setAmount_of_Containers(rs.getString("amount_of_Containers"));
                artikel.setWeight(rs.getString("weight"));
                artikel.setBanfNrArt(rs.getString("agreement"));
                artikel.setShipment(rs.getString("shipment"));
                    artikel.setDate_of_Arrival(rs.getString("date_of_Arrival"));
                    artikel.setInvoice_Proforma(rs.getString("invoice_Proforma"));
                
                // Поля menge и einzelMenge
                if (rs.getObject("menge") != null) {
                    try {
                        String mengeStr = rs.getString("menge");
                        if (mengeStr != null && !mengeStr.trim().isEmpty()) {
                            artikel.setMenge(Float.parseFloat(mengeStr));
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Fehler beim Konvertieren von menge zu float: " + e.getMessage());
                        artikel.setMenge(0);
                    }
                }
                if (rs.getObject("einzelMenge") != null) {
                    try {
                        String einzelMengeStr = rs.getString("einzelMenge");
                        if (einzelMengeStr != null && !einzelMengeStr.trim().isEmpty()) {
                            artikel.setMenge_Baustelle_gel(Float.parseFloat(einzelMengeStr));
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Fehler beim Konvertieren von einzelMenge zu float: " + e.getMessage());
                        artikel.setMenge_Baustelle_gel(0);
                    }
                }
                artikel.setEinheit(rs.getString("einheit"));
                artikel.setLagerOrt(rs.getString("lagerOrt"));
                artikel.setLagerMenge(rs.getString("lagerMenge"));
                artikel.setInfo01(rs.getString("info01"));
                artikel.setInfo02(rs.getString("info02"));
                artikel.setInfo03(rs.getString("info03"));
                artikel.setPackListe(rs.getString("info02")); // packListe в БД хранится в info02
                artikel.setPreisEuro(rs.getFloat("preisEuro"));
                artikelList.add(artikel);
            }
            
        } catch (SQLException e) {
            System.err.println("Fehler beim Laden aller Artikel: " + e.getMessage());
            e.printStackTrace();
        }
        
        return artikelList;
    }
    
    /**
     * Получает все элементы Artikel из таблицы lager по номеру заявки (Anforderung).
     * @param anfordNummer номер заявки (anfordrung)
     * @return список элементов Artikel для данной заявки
     */
    public ArrayList<Artikel> getArtikelByAnford(String anfordNummer) {
        ArrayList<Artikel> artikelList = new ArrayList<>();
        if (anfordNummer == null || anfordNummer.trim().isEmpty()) {
            return artikelList;
        }
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return artikelList;
        }
        String selectSQL = "SELECT * FROM " +lagerTab + " WHERE LTRIM(RTRIM(ISNULL(anfordrung, ''))) = ? ORDER BY material_DE ASC";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, anfordNummer.trim());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Artikel artikel = new Artikel();
                    artikel.setId(rs.getInt("id"));
                    artikel.setMaterial_EN(rs.getString("material_EN"));
                    artikel.setMaterial_DE(rs.getString("material_DE"));
                    artikel.setDimention(rs.getString("dimention"));
                    artikel.setSupplier(rs.getString("supplier"));
                    artikel.setColli(rs.getString("colli"));
                    artikel.setOrder_Number(rs.getString("order_Number"));
                    try {
                        String geliefertAnBsStr = rs.getString("geliefert_an_bs");
                        if (geliefertAnBsStr != null && !geliefertAnBsStr.trim().isEmpty()) {
                            artikel.setMenge_gelief_an_BS(Float.parseFloat(geliefertAnBsStr));
                        }
                    } catch (Exception ignored) { }
                    artikel.setAnfordrung(rs.getString("anfordrung"));
                    artikel.setReloaded_in(rs.getString("reloaded_in"));
                    artikel.setContainer_Number(rs.getString("container_Number"));
                    artikel.setBooking_Number(rs.getString("booking_Number"));
                    artikel.setAdd_Info(rs.getString("add_Info"));
                    artikel.setGood_Receipt(rs.getString("good_Receipt"));
                    artikel.setAmount_of_Containers(rs.getString("amount_of_Containers"));
                    artikel.setWeight(rs.getString("weight"));
                    artikel.setBanfNrArt(rs.getString("agreement"));
                    artikel.setShipment(rs.getString("shipment"));
                    artikel.setDate_of_Arrival(rs.getString("date_of_Arrival"));
                    artikel.setInvoice_Proforma(rs.getString("invoice_Proforma"));
                    if (rs.getObject("menge") != null) {
                        try {
                            String mengeStr = rs.getString("menge");
                            if (mengeStr != null && !mengeStr.trim().isEmpty()) {
                                artikel.setMenge(Float.parseFloat(mengeStr));
                            }
                        } catch (NumberFormatException e) {
                            artikel.setMenge(0);
                        }
                    }
                    if (rs.getObject("einzelMenge") != null) {
                        try {
                            String einzelMengeStr = rs.getString("einzelMenge");
                            if (einzelMengeStr != null && !einzelMengeStr.trim().isEmpty()) {
                                artikel.setMenge_Baustelle_gel(Float.parseFloat(einzelMengeStr));
                            }
                        } catch (NumberFormatException e) {
                            artikel.setMenge_Baustelle_gel(0);
                        }
                    }
                    artikel.setEinheit(rs.getString("einheit"));
                    artikel.setLagerOrt(rs.getString("lagerOrt"));
                    artikel.setLagerMenge(rs.getString("lagerMenge"));
                    artikel.setInfo01(rs.getString("info01"));
                    artikel.setInfo02(rs.getString("info02"));
                    artikel.setInfo03(rs.getString("info03"));
                    artikel.setPackListe(rs.getString("info02")); // packListe в БД хранится в info02
                    artikel.setPreisEuro(rs.getFloat("preisEuro"));
                    artikelList.add(artikel);
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Laden der Artikel für Anforderung " + anfordNummer + ": " + e.getMessage());
            e.printStackTrace();
        }
        return artikelList;
    }
    
    /**
     * Возвращает список всех уникальных непустых значений info03 из таблицы lager.
     * @return список уникальных значений info03, отсортированный по возрастанию
     */
    public ArrayList<String> getDistinctInfo03() {
        ArrayList<String> list = new ArrayList<>();
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return list;
        }
        String sql = "SELECT DISTINCT LTRIM(RTRIM(info03)) AS info03 FROM " + lagerTab
            + " WHERE LTRIM(RTRIM(ISNULL(info03, ''))) <> '' ORDER BY LTRIM(RTRIM(info03)) ASC";
        try (PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String v = rs.getString("info03");
                if (v != null && !v.trim().isEmpty()) {
                    list.add(v.trim());
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler getDistinctInfo03: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }
    
    /**
     * Получает все элементы Artikel из таблицы lager по значению info03.
     * @param info03 значение поля info03 для фильтрации
     * @return список элементов Artikel с данным info03
     */
    public ArrayList<Artikel> getArtikelByInfo03(String info03) {
        ArrayList<Artikel> artikelList = new ArrayList<>();
        if (info03 == null) {
            return artikelList;
        }
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return artikelList;
        }
        String selectSQL = "SELECT * FROM " + lagerTab
            + " WHERE LTRIM(RTRIM(ISNULL(info03, ''))) = ? ORDER BY material_DE ASC";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, info03.trim());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Artikel artikel = new Artikel();
                    artikel.setId(rs.getInt("id"));
                    artikel.setMaterial_EN(rs.getString("material_EN"));
                    artikel.setMaterial_DE(rs.getString("material_DE"));
                    artikel.setDimention(rs.getString("dimention"));
                    artikel.setSupplier(rs.getString("supplier"));
                    artikel.setColli(rs.getString("colli"));
                    artikel.setOrder_Number(rs.getString("order_Number"));
                    try {
                        String geliefertAnBsStr = rs.getString("geliefert_an_bs");
                        if (geliefertAnBsStr != null && !geliefertAnBsStr.trim().isEmpty()) {
                            artikel.setMenge_gelief_an_BS(Float.parseFloat(geliefertAnBsStr));
                        }
                    } catch (Exception ignored) { }
                    artikel.setAnfordrung(rs.getString("anfordrung"));
                    artikel.setReloaded_in(rs.getString("reloaded_in"));
                    artikel.setContainer_Number(rs.getString("container_Number"));
                    artikel.setBooking_Number(rs.getString("booking_Number"));
                    artikel.setAdd_Info(rs.getString("add_Info"));
                    artikel.setGood_Receipt(rs.getString("good_Receipt"));
                    artikel.setAmount_of_Containers(rs.getString("amount_of_Containers"));
                    artikel.setWeight(rs.getString("weight"));
                    artikel.setBanfNrArt(rs.getString("agreement"));
                    artikel.setShipment(rs.getString("shipment"));
                    artikel.setDate_of_Arrival(rs.getString("date_of_Arrival"));
                    artikel.setInvoice_Proforma(rs.getString("invoice_Proforma"));
                    if (rs.getObject("menge") != null) {
                        try {
                            String mengeStr = rs.getString("menge");
                            if (mengeStr != null && !mengeStr.trim().isEmpty()) {
                                artikel.setMenge(Float.parseFloat(mengeStr));
                            }
                        } catch (NumberFormatException e) {
                            artikel.setMenge(0);
                        }
                    }
                    if (rs.getObject("einzelMenge") != null) {
                        try {
                            String einzelMengeStr = rs.getString("einzelMenge");
                            if (einzelMengeStr != null && !einzelMengeStr.trim().isEmpty()) {
                                artikel.setMenge_Baustelle_gel(Float.parseFloat(einzelMengeStr));
                            }
                        } catch (NumberFormatException e) {
                            artikel.setMenge_Baustelle_gel(0);
                        }
                    }
                    artikel.setEinheit(rs.getString("einheit"));
                    artikel.setLagerOrt(rs.getString("lagerOrt"));
                    artikel.setLagerMenge(rs.getString("lagerMenge"));
                    artikel.setInfo01(rs.getString("info01"));
                    artikel.setInfo02(rs.getString("info02"));
                    artikel.setInfo03(rs.getString("info03"));
                    artikel.setPackListe(rs.getString("info02"));
                    artikel.setPreisEuro(rs.getFloat("preisEuro"));
                    artikelList.add(artikel);
                }
            }
        } catch (SQLException e) {
            System.err.println("Fehler beim Laden der Artikel für info03 " + info03 + ": " + e.getMessage());
            e.printStackTrace();
        }
        return artikelList;
    }
    
    /**
     * Поиск элементов Artikel по всем полям
     * @param param поисковый параметр
     * @return список найденных элементов Artikel
     */
    public ArrayList<Artikel> searchArtikel(String param) {
        ArrayList<Artikel> artikelList = new ArrayList<>();
        
        if (param == null || param.trim().isEmpty()) {
            System.err.println("ERROR: Suchparameter darf nicht leer sein");
            return artikelList;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return artikelList;
        }
        
        String searchSQL = "SELECT * FROM " +lagerTab + " WHERE " +
            "LOWER(material_EN) LIKE LOWER(?) OR " +
            "LOWER(material_DE) LIKE LOWER(?) OR " +
            "LOWER(dimention) LIKE LOWER(?) OR " +
            "LOWER(supplier) LIKE LOWER(?) OR " +
            "LOWER(colli) LIKE LOWER(?) OR " +
            "LOWER(order_Number) LIKE LOWER(?) OR " +
            "LOWER(geliefert_an_bs) LIKE LOWER(?) OR " +
            "LOWER(anfordrung) LIKE LOWER(?) OR " +
            "LOWER(reloaded_in) LIKE LOWER(?) OR " +
            "LOWER(container_Number) LIKE LOWER(?) OR " +
            "LOWER(booking_Number) LIKE LOWER(?) OR " +
            "LOWER(add_Info) LIKE LOWER(?) OR " +
            "LOWER(good_Receipt) LIKE LOWER(?) OR " +
            "LOWER(amount_of_Containers) LIKE LOWER(?) OR " +
            "LOWER(weight) LIKE LOWER(?) OR " +
            "LOWER(agreement) LIKE LOWER(?) OR " +
            "LOWER(shipment) LIKE LOWER(?) OR " +
            "LOWER(date_of_Arrival) LIKE LOWER(?) OR " +
            "LOWER(invoice_Proforma) LIKE LOWER(?) OR " +
            "LOWER(einheit) LIKE LOWER(?) OR " +
            "LOWER(lagerOrt) LIKE LOWER(?) OR " +
            "LOWER(lagerMenge) LIKE LOWER(?) OR " +
            "LOWER(info01) LIKE LOWER(?) OR " +
            "LOWER(info02) LIKE LOWER(?) OR " +
            "LOWER(info03) LIKE LOWER(?) OR " +
            "CAST(preisEuro AS NVARCHAR) LIKE ? OR " +
            "CAST(menge AS NVARCHAR) LIKE ? OR " +
            "CAST(einzelMenge AS NVARCHAR) LIKE ? " +
            "ORDER BY id DESC";
        
        try (PreparedStatement pstmt = connection.prepareStatement(searchSQL)) {
            // Устанавливаем параметр поиска для всех полей
            String searchPattern = "%" + param.trim() + "%";
            for (int i = 1; i <= 28; i++) {
                pstmt.setString(i, searchPattern);
            }
            
            // Выполняем запрос
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Artikel artikel = new Artikel();
                    
                    // Заполняем объект данными из ResultSet
                    artikel.setId(rs.getInt("id"));
                    artikel.setMaterial_EN(rs.getString("material_EN"));
                    artikel.setMaterial_DE(rs.getString("material_DE"));
                    artikel.setDimention(rs.getString("dimention"));
                    artikel.setSupplier(rs.getString("supplier"));
                    artikel.setColli(rs.getString("colli"));
                    artikel.setOrder_Number(rs.getString("order_Number"));
                    try {
                        String geliefertAnBsStr = rs.getString("geliefert_an_bs");
                        if (geliefertAnBsStr != null && !geliefertAnBsStr.trim().isEmpty()) {
                            artikel.setMenge_gelief_an_BS(Float.parseFloat(geliefertAnBsStr));
                        }
                    } catch (Exception ignored) { }
                    artikel.setAnfordrung(rs.getString("anfordrung"));
                    artikel.setReloaded_in(rs.getString("reloaded_in"));
                    artikel.setContainer_Number(rs.getString("container_Number"));
                    artikel.setBooking_Number(rs.getString("booking_Number"));
                    artikel.setAdd_Info(rs.getString("add_Info"));
                    artikel.setGood_Receipt(rs.getString("good_Receipt"));
                    artikel.setAmount_of_Containers(rs.getString("amount_of_Containers"));
                    artikel.setWeight(rs.getString("weight"));
                    artikel.setBanfNrArt(rs.getString("agreement"));
                    artikel.setShipment(rs.getString("shipment"));
                    artikel.setDate_of_Arrival(rs.getString("date_of_Arrival"));
                    artikel.setInvoice_Proforma(rs.getString("invoice_Proforma"));
                    
                    // Новые поля
                    if (rs.getObject("menge") != null) {
                        try {
                            String mengeStr = rs.getString("menge");
                            if (mengeStr != null && !mengeStr.trim().isEmpty()) {
                                artikel.setMenge(Float.parseFloat(mengeStr));
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Fehler beim Konvertieren von menge zu float: " + e.getMessage());
                            artikel.setMenge(0);
                        }
                    }
                    if (rs.getObject("einzelMenge") != null) {
                        try {
                            String einzelMengeStr = rs.getString("einzelMenge");
                            if (einzelMengeStr != null && !einzelMengeStr.trim().isEmpty()) {
                                artikel.setMenge_Baustelle_gel(Float.parseFloat(einzelMengeStr));
                            }
                        } catch (NumberFormatException e) {
                            System.err.println("Fehler beim Konvertieren von einzelMenge zu float: " + e.getMessage());
                            artikel.setMenge_Baustelle_gel(0);
                        }
                    }
                    artikel.setEinheit(rs.getString("einheit"));
                    artikel.setLagerOrt(rs.getString("lagerOrt"));
                    artikel.setLagerMenge(rs.getString("lagerMenge"));
                    artikel.setInfo01(rs.getString("info01"));
                    artikel.setInfo02(rs.getString("info02"));
                    artikel.setInfo03(rs.getString("info03"));
                    artikel.setPackListe(rs.getString("info02")); // packListe в БД хранится в info02
                    artikel.setPreisEuro(rs.getFloat("preisEuro"));
                    artikelList.add(artikel);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Fehler beim Suchen von Artikel: " + e.getMessage());
            e.printStackTrace();
        }
        
        return artikelList;
    }
    
    /**
     * Обновляет элемент Artikel в таблице
     * @param artikel объект Artikel с обновленными данными
     * @return true если обновление успешно, false в противном случае
     */
    public boolean updateArtikel(Artikel artikel) {
        boolean updateOk = false;
        
        if (artikel == null) {
            System.err.println("ERROR: Objekt Artikel darf nicht null sein");
            return false;
        }
        
        if (artikel.getId() <= 0) {
            System.err.println("Error: ID muss groesser als 0 sein fuer Update");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return false;
        }
        
        String updateSQL = "UPDATE "+ lagerTab + " SET " +
            "material_EN = ?, material_DE = ?, dimention = ?, supplier = ?, colli = ?, " +
            "order_Number = ?, geliefert_an_bs = ?, anfordrung = ?, reloaded_in = ?, container_Number = ?, booking_Number = ?, " +
            "add_Info = ?, good_Receipt = ?, amount_of_Containers = ?, weight = ?, agreement = ?, " +
            "shipment = ?, date_of_Arrival = ?, invoice_Proforma = ?, menge = ?, einzelMenge = ?, einheit = ?, " +
            "lagerOrt = ?, lagerMenge = ?, info01 = ?, info02 = ?, info03 = ?, preisEuro = ?, " +
            "updated_at = GETDATE() " +
            "WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(updateSQL)) {
            int paramIndex = 1;
            
            // Устанавливаем значения параметров
            pstmt.setString(paramIndex++, artikel.getMaterial_EN());
            pstmt.setString(paramIndex++, artikel.getMaterial_DE());
            pstmt.setString(paramIndex++, artikel.getDimention());
            pstmt.setString(paramIndex++, artikel.getSupplier());
            pstmt.setString(paramIndex++, artikel.getColli());
            pstmt.setString(paramIndex++, artikel.getOrder_Number());
            pstmt.setString(paramIndex++, String.valueOf(artikel.getMenge_gelief_an_BS()));
            pstmt.setString(paramIndex++, artikel.getAnfordrung());
            pstmt.setString(paramIndex++, artikel.getReloaded_in());
            pstmt.setString(paramIndex++, artikel.getContainer_Number());
            pstmt.setString(paramIndex++, artikel.getBooking_Number());
            pstmt.setString(paramIndex++, artikel.getAdd_Info());
            pstmt.setString(paramIndex++, artikel.getGood_Receipt());
            pstmt.setString(paramIndex++, artikel.getAmount_of_Containers());
            pstmt.setString(paramIndex++, artikel.getWeight());
            pstmt.setString(paramIndex++, artikel.getBanfNrArt());
            pstmt.setString(paramIndex++, artikel.getShipment());
            pstmt.setString(paramIndex++, artikel.getDate_of_Arrival());
            pstmt.setString(paramIndex++, artikel.getInvoice_Proforma());
            pstmt.setString(paramIndex++, String.valueOf(artikel.getMenge()));
            pstmt.setString(paramIndex++, String.valueOf(artikel.getMenge_Baustelle_gel()));
            pstmt.setString(paramIndex++, artikel.getEinheit());
            pstmt.setString(paramIndex++, artikel.getLagerOrt());
            pstmt.setString(paramIndex++, artikel.getLagerMenge());
            pstmt.setString(paramIndex++, artikel.getInfo01());
            pstmt.setString(paramIndex++, artikel.getInfo02());
            pstmt.setString(paramIndex++, artikel.getInfo03());
            pstmt.setFloat(paramIndex++, artikel.getPreisEuro());
            pstmt.setInt(paramIndex++, artikel.getId());
            
            // Выполняем запрос
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                updateOk = true;
                System.out.println("Artikel mit ID " + artikel.getId() + " erfolgreich aktualisiert");
            } else {
                System.err.println("ERROR: Artikel mit ID " + artikel.getId() + " wurde nicht gefunden");
            }
            
        } catch (SQLException e) {
            System.err.println("Fehler beim Aktualisieren von Artikel: " + e.getMessage());
            e.printStackTrace();
        }
        
        return updateOk;
    }
    
    /**
     * Обновляет список элементов Artikel в таблице lager.
     * Для каждого элемента с id > 0 вызывается updateArtikel(artikel).
     * @param list список объектов Artikel с обновлёнными данными
     * @return количество успешно обновлённых записей
     */
    public boolean updateArtikelList(ArrayList<Artikel> list) {
        boolean updated = false;
        if (list == null || list.isEmpty()) {
            return false;
        }
       try{
            for (Artikel artikel : list) {
            if (artikel != null && artikel.getId() > 0 && updateArtikel(artikel)) {
               
            }
        }
            updated = true;
       }catch(Exception e){
           System.out.println("сохранение изменений артикула из списка не сделано!" );
       }
       
        return updated;
    }
    
    /**
     * Удаляет элемент Artikel из таблицы по ID
     * @param id идентификатор элемента для удаления
     * @return true если удаление успешно, false в противном случае
     */
    public boolean deleteArtikel(int id) {
        boolean deleteOK = false;
        
        if (id <= 0) {
            System.err.println("Fehler: ID soll grösser 0 sein");
            return false;
        }
        
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return false;
        }
        
        String deleteSQL = "DELETE FROM " + lagerTab +" WHERE id = ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                deleteOK = true;
                System.out.println("Artikel mit ID " + id + " erfolgreich gelöscht");
            } else {
                System.err.println("Fehler: Artikel mit ID " + id + " wurde nicht gefunden");
            }
            
        } catch (SQLException e) {
            System.err.println("Fehler beim Löschen von Artikel mit ID " + id + ": " + e.getMessage());
            e.printStackTrace();
        }
        
        return deleteOK;
    }

    /**
     * Удаляет несколько элементов Artikel из таблицы lager по списку ID.
     * @param ids список идентификаторов для удаления
     * @return количество успешно удаленных записей
     */
    public int deleteArtikelList(java.util.List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return 0;
        }
        if (!ensureConnection()) {
            System.err.println("ERROR: keine Verbindung mit DB");
            return 0;
        }

        String deleteSQL = "DELETE FROM " + lagerTab + " WHERE id = ?";
        int deleted = 0;
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            for (Integer id : ids) {
                if (id == null || id <= 0) {
                    continue;
                }
                pstmt.setInt(1, id);
                int affected = pstmt.executeUpdate();
                if (affected > 0) {
                    deleted += affected;
                }
            }
            System.out.println("Artikel erfolgreich geloescht (Mehrfach): " + deleted);
        } catch (SQLException e) {
            System.err.println("Fehler beim Mehrfach-Löschen von Artikeln: " + e.getMessage());
            e.printStackTrace();
        }
        return deleted;
    }
    
    
}
