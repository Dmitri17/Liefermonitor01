/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.Artikel;
import de.zmiter.liefermonitor01.DBObjekte.Lieferung;
import de.zmiter.liefermonitor01.DBObjekte.PersonUser;
import java.awt.Color;
import java.awt.Desktop;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author lepeschko
 */
public class BearbForm extends javax.swing.JFrame  {

    /**
     * Creates new form BearbForm
     */
    String titel = "";
    String personName = "pers__";
      PersonUser person;
    String beschreibung = "";
    float menge = 1;
    Lieferung lieferung ;
    Lieferung originalLieferung; // Снимок исходных данных для сравнения
    protected boolean persistBeschreibungFromTextArea = true;
    String name = ""; // здесь мы будем представлять название поставки или товара
    DBManager dbManager;
    int sprache = 0;
     JButton jButtonLoc;
       JButton jButtonPro;
        String locationIST;
        String projektIST;
        LieferListFensterModel lifListe ;
    private javax.swing.JFrame textAreaEditorFrame; // Ссылка на окно редактора текста
    public BearbForm(DBManager dbManager, Lieferung lief, PersonUser person) {
       
        this.person = person;
       // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!   Person: " + person.getName()+ "     "+   person.getZugangsGruppe());
        this.dbManager = dbManager;
       this.lieferung = lief;
       // Создаем снимок исходных данных для сравнения
       this.originalLieferung = createCopyOfLieferung(lief);
      // titel = lief.getAnfordNummer()+ "  (--"  + lief.getProjectName() + "--)";
        locationIST = lieferung.getInfo01();
          projektIST = lieferung.getProjectName();
        
        startComponents();
       // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!   Person: " + person.getName()+ "     "+   person.getZugangsGruppe());
       
        name = lief.getAnfordNummer()+ "  ("  + lieferung.getProjectName() + ")";
        lifListe = new LieferListFensterModel(person);
       // Проверяем, что объект Lieferung не null
        if (lief == null) {
            this.name = "Fehler: Lieferung ist null";
            return;
        }
        
        this.name = "<" + (lief.getText() != null ? lief.getText() : "Neue Lieferung") + "> :Bearbeitungtafel";
       // lieferung.setNeu(true); 
       
        this.jCheckBox2.setSelected(lief.isNeu());
          this.jCheckBox3.setSelected(lief.isAktiv());
            this.jCheckBox4.setSelected(lief.isUnterschriftMH());
              this.jCheckBox5.setSelected(lief.isUnterschriftKunde());
                this.jCheckBox6.setSelected(lief.isAnEinkaufWeiterGeleitetMH());
                  this.jCheckBox7.setSelected(lief.isInvest_Leasing());
        if (lief.getDe_eu() != null) {
            this.jComboBox1.setSelectedItem(lief.getDe_eu());
        }
        this.jFormattedTextField3.setValue(lief.getMenge());
        if (lief.getEinhMenge() != null) {
            this.jComboBox5.setSelectedItem(lief.getEinhMenge());
        }
        try {
            String rawWunsch = lief.getWunschLieferDatum();
            if (rawWunsch != null) rawWunsch = rawWunsch.trim();
            if (rawWunsch == null || rawWunsch.isEmpty()) {
                this.jFormattedTextField2.setText("");
            } else {
                // Нормализуем к формату dd.MM.yy
                java.util.Date parsed = null;
                // 1) Попробуем dd.MM.yy
                try {
                    java.text.SimpleDateFormat sdfShort = new java.text.SimpleDateFormat("dd.MM.yy");
                    sdfShort.setLenient(false);
                    parsed = sdfShort.parse(rawWunsch);
                } catch (Exception ignore) {}

                // 2) Если не получилось — попробуем dd.MM.yyyy
                if (parsed == null) {
                    try {
                        java.text.SimpleDateFormat sdfFull = new java.text.SimpleDateFormat("dd.MM.yyyy");
                        sdfFull.setLenient(false);
                        parsed = sdfFull.parse(rawWunsch);
                    } catch (Exception ignore) {}
                }

                if (parsed != null) {
                    java.text.SimpleDateFormat out = new java.text.SimpleDateFormat("dd.MM.yy");
                    this.jFormattedTextField2.setText(out.format(parsed));
                } else {
                    // Если распарсить не удалось, показываем как есть
                    this.jFormattedTextField2.setText(rawWunsch);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            this.jFormattedTextField2.setText(lief.getWunschLieferDatum() != null ? lief.getWunschLieferDatum() : "");
        }
      this.setTitle(lief.getAnfordNummer());
        this.jTextArea1.setText(lief.getBeschreibung() != null ? lief.getBeschreibung() : "");
        this.jTextArea1.setEditable(false);
         //___________________________________________________________________
        this.tFAgreementNR.setText(lief.getAnfordNummer() != null ? lief.getAnfordNummer() : "");
           try{
              if (lief.getAnforderungDatum() != null && !lief.getAnforderungDatum().isEmpty()) {
                  this.jFormattedTextField1.setText(lief.getAnforderungDatum());
              } else {
                  this.jFormattedTextField1.setText("");
              }
        }catch(Exception e2){
            e2.printStackTrace();
        }
        
       this.tFAnforderungNR.setText(lief.getAgrNummer() != null ? lief.getAgrNummer() : "");
        this.jFormattedTextField16.setValue(lief.getSummeMat_Leist_cents());
        this.jTextField1.setText(lief.getAnfordPerson() != null ? lief.getAnfordPerson() : "");  // при обработке уже готовой заявки поле заполняем из DB,  а при создании новой заявки это поле заполняем через Person
       
        this.jFormattedTextField17.setValue(lief.getPreisEinkauf());
        
        this.lieferant_tFKommentar01.setText(lief.getLieferantName() != null ? lief.getLieferantName() : "");
      
        this.jFormattedTextField5.setText(normalizeDateToShortDisplay(lief.getBestelltAm()));
       
         
        this.jTextField4.setText(lief.getBestellNummer() != null ? lief.getBestellNummer() : "");
          
        
           this.jFormattedTextField6.setText(lief.getWareneingangTS_plan() != null ? lief.getWareneingangTS_plan() : "");
        
            try{
              if (lief.getWareneingangTS_Fakt() != null) {
                  this.jFormattedTextField7.setText(lief.getWareneingangTS_Fakt().toString());
              } else {
                  this.jFormattedTextField7.setText("");
              }
        }catch(Exception e5){
            e5.printStackTrace();
        }
            
             this.jTextField2.setText(lief.getWarenEingangTS() != null ? lief.getWarenEingangTS() : "");
           try{
              if (lief.getAuslieferungNachPlan() != null) {
                  this.jFormattedTextField4.setText(lief.getAuslieferungNachPlan().toString());
              } else {
                  this.jFormattedTextField4.setText("");
              }
        }catch(Exception e6){
            e6.printStackTrace();
        }
        
        this.jTextField8.setText(lief.getZahlungsbedingungen() != null ? lief.getZahlungsbedingungen() : "");
        this.jTextField9.setText(lief.getKommentEinkauf() != null ? lief.getKommentEinkauf() : "");
          //_________________________________________________________________
          
             this.jFormattedTextField8.setText(lief.getWunschterminWareneingang() != null ? lief.getWunschterminWareneingang() : "");
        
              this.jFormattedTextField9.setText(lief.getWareneingang_Fakt() != null ? lief.getWareneingang_Fakt() : "");
     
         // System.out.println("#######################__________"+ lief.getKommentarBS() + "               ??");
        this.jTextField5.setText(lief.getKommentarBS() != null ? lief.getKommentarBS() : "");
        this.jFormattedTextField15.setValue(lief.getGewicht());
        if (lief.getEinhMasse() != null) {
            this.jComboBox9.setSelectedItem(lief.getEinhMasse());
        }
        if (lief.getEinheitLBH() != null) {
            this.jComboBox10.setSelectedItem(lief.getEinheitLBH());
        }
        
        this.jFormattedTextField18.setValue(lief.getLaenge());
        this.jFormattedTextField20.setValue(lief.getBreite());
        this.jFormattedTextField19.setValue(lief.getHoehe());
        this.jTextField14.setText(lief.getKolli() != null ? lief.getKolli() : "");
        this.jTextField15.setText(lief.getPackListe() != null ? lief.getPackListe() : "");
        this.jTextField16.setText(lief.getContainerNr() != null ? lief.getContainerNr() : "");
        //_______________________________________________________________
        this.jTextField17.setText(lief.getRgNummeranAG() != null ? lief.getRgNummeranAG() : "");
           try{
              if (lief.getDatumBS() != null || !lief.getDatumBS().isEmpty()) {
                  this.jFormattedTextField10.setText(lief.getDatumBS());
              } else {
                  this.jFormattedTextField10.setText("");
              }
        
        }catch(Exception e9){
            e9.printStackTrace();
        }
        
      //  System.out.println("Neu selected --- "+ lief.isNeu());
        
        // Безопасно устанавливаем заголовок
        if (lieferung != null && lieferung.getBeschreibung() != null && !lieferung.getBeschreibung().isEmpty()) {
           // this.setTitle(titel);
           // this.setTitle(lieferung.getAnfordNummer()+ "  ("  + lieferung.getProjectName() + ")");
          //  System.out.println("PROJEKTNAME: #########################------------------------------  " + lief.getProjectName() +  ".............." + titel);
        } else {
            this.setTitle("Lieferung");
        }
       
        this.jFormattedTextField3.setValue(lief.getMenge());
        
        // Безопасно устанавливаем заголовок с описанием
        if (lief.getBeschreibung() != null && !lief.getBeschreibung().isEmpty()) {
            if (lief.getBeschreibung().length() > 30) {
                String kurzTitel = lief.getBeschreibung().substring(0, 29);
               this.setTitle("Anforderung: " + lief.getAnfordNummer() + "  ("  + lief.getProjectName() + ")");
            } else {
               this.setTitle("Anforderung:: " + lieferung.getAnfordNummer()+ "  ("  + lief.getProjectName() + ")" + "   " + lieferung.getBeschreibung());
            }
        } else {
            this.setTitle("ID in DB: " + lief.getId() + "   Neue Lieferung");
        }
      try{
           this.jCheckBox1.setSelected(lief.getIstWeitergerechnetAnAG());
      }catch(Exception eee){
       //   System.out.println("jCheckBox1 ist nicht Selekted!!!############");
      }
      if(lief.getSummeWeitergerechnetanAG_cents() != 0 ){
            this.jFormattedTextField12.setValue(lief.getSummeWeitergerechnetanAG_cents());
         
      }
           
       // System.out.println("Letzte Felder :  "+  lief.getSumme_nach_Weiterrechnung() + "    #################################### --    "+ lief.getKundenInRechnungStellen() );
         if(lief.getSumme_nach_Weiterrechnung() != 0 ){
            this.jFormattedTextField13.setValue(lief.getSumme_nach_Weiterrechnung());
         
      }
       if(lief.getKundenInRechnungStellen() != 0 ){
            this.jFormattedTextField14.setValue(lief.getKundenInRechnungStellen());
         
      }
       
      
       
      
    }
    public BearbForm( PersonUser person){
     Lieferung lief = new Lieferung("", 0);
     lief.setNeu(true);
      this.lieferung = lief;
      // Создаем снимок исходных данных для сравнения
      this.originalLieferung = createCopyOfLieferung(lief);
     this.person = person;
         dbManager =  new DBManager();
         // System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!   Person: " + person.getName()+ "     "+   person.getZugangsGruppe());
       // javax.swing.JButton jButtonZuArtikelliste = new javax.swing.JButton("zur Artikelliste");
          startComponents();
      //  System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!   Person: " + person.getName()+ "     "+   person.getZugangsGruppe());
       
        // Инициализируем поля для новой поставки
        this.name = "Neue Lieferung";
        this.jTextArea1.setText("Kurztext");
        this.jFormattedTextField3.setText("0");
        
        // Устанавливаем значения по умолчанию для чекбоксов
        this.jCheckBox2.setSelected(false); // neu
        this.jCheckBox3.setSelected(true);  // aktiv
        this.jCheckBox4.setSelected(false); // unterschriftMH
        this.jCheckBox5.setSelected(false); // unterschriftKunde
        this.jCheckBox6.setSelected(false); // anEinkaufWeiterGeleitetMH
        this.jCheckBox7.setSelected(false); // invest_Leasing
        
        // Очищаем поля дат
        this.jFormattedTextField1.setText("");  // datumBS
        this.jFormattedTextField2.setText("");  // wunschLieferDatum
        this.jFormattedTextField8.setText("");  // lieferungAnBS
        this.jFormattedTextField16.setText(""); // wareneingangTS_plan
        this.jFormattedTextField7.setText("");  // wareneingangTS_Fakt
        this.jFormattedTextField4.setText("");  // auslieferungNachPlan
        this.jFormattedTextField9.setText("");  // wunschterminWareneingang
        this.jFormattedTextField10.setText(""); // datumBS (второе поле)
        
        // Очищаем текстовые поля
        this.tFAgreementNR.setText("");
        this.tFAnforderungNR.setText("");
        this.jTextField1.setText(person != null ? person.getName() : "");
       // this.jFormattedTextField16.setText("0");
        this.jFormattedTextField17.setText("0");
        this.lieferant_tFKommentar01.setText("");
        this.jTextField4.setText("");
        this.jTextField15.setText("");
        this.jFormattedTextField15.setText("0");
        this.jFormattedTextField18.setText("0");
        this.jFormattedTextField20.setText("0");
        this.jFormattedTextField19.setText("0");
        this.jTextField14.setText("");
        this.jTextField15.setText("");
        this.jTextField16.setText("");
        this.jTextField17.setText("");
        this.jTextField8.setText("");
        this.jTextField9.setText("");
        this.jTextField2.setText("");
        this.jTextField5.setText("");
        
        // Устанавливаем значения по умолчанию для комбобоксов
        if (this.jComboBox1 != null) this.jComboBox1.setSelectedIndex(0);
        if (this.jComboBox5 != null) this.jComboBox5.setSelectedIndex(0);
        if (this.jComboBox9 != null) this.jComboBox9.setSelectedIndex(0);
        if (this.jComboBox10 != null) this.jComboBox10.setSelectedIndex(0);
        
        this.setTitle(" Lieferung");
       
    }

      
       
       /**
        * Метод для получения даты из строки в формате dd.MM.yy
        * @param strDate строка с датой в формате dd.MM.yy
        * @return Date объект или null если преобразование не удалось
        */
       Date getDateFromField(String strDate){  // метод для получения даты из поля формы
           if (strDate == null || strDate.trim().isEmpty()) {
               return null;
           }
           
           try {
               // Проверяем, что строка соответствует формату dd.MM.yy
               if (!isDateOKShortFormat(strDate)) {
                   System.err.println("Falsche Datumformat: " + strDate + ". Wird Format dd.mm.yy erwartet");
                   return null;
               }
               
               // Парсим дату из формата dd.MM.yy
               int day = Integer.parseInt(strDate.substring(0, 2));
               int month = Integer.parseInt(strDate.substring(3, 5));
               int year = Integer.parseInt(strDate.substring(6, 8));
               
               // Преобразуем двухзначный год в четырехзначный (предполагаем 20xx для годов 00-99)
               int fullYear = 2000 + year;
               
               // Создаем объект java.util.Date используя Calendar (более современный подход)
               java.util.Calendar calendar = java.util.Calendar.getInstance();
               calendar.set(fullYear, month - 1, day, 0, 0, 0);
               calendar.set(java.util.Calendar.MILLISECOND, 0);
               java.util.Date utilDate = calendar.getTime();
               
               // Преобразуем в java.sql.Date
               return new Date(utilDate.getTime());
               
           } catch (Exception e) {
               System.err.println("Fehler beim Parsing von Datum: " + strDate + " - " + e.getMessage());
               return null;
           }
       }
       
       /**
        * Метод для преобразования текста из поля jFormattedTextField2 (Wunschliefertermin) 
        * в формат Date для записи в базу данных
        * Поле содержит дату в формате "dd.MM.yyyy" (например: 25.12.2024)
        * @return Date объект или null если преобразование не удалось
        */
       public Date convertWunschLieferDatumFromField() {
           String dateText = jFormattedTextField2.getText();
           
           if (dateText == null || dateText.trim().isEmpty()) {
               System.out.println("Feld  Wunschliefertermin ist leer");
               return null;
           }
           
           try {
               // Проверяем формат даты dd.MM.yyyy
               if (!isDateOKFullFormat(dateText)) {
                   System.err.println("Falsche Datumformat: " + dateText + ". Wird Format  dd.MM.yy erwartet");
                   return null;
               }
               
               // Парсим дату из формата dd.MM.yyyy
               int day = Integer.parseInt(dateText.substring(0, 2));
               int month = Integer.parseInt(dateText.substring(3, 5));
               int year = Integer.parseInt(dateText.substring(6, 8));
               
               // Создаем объект java.util.Date используя Calendar (более современный подход)
               java.util.Calendar calendar = java.util.Calendar.getInstance();
               calendar.set(year, month - 1, day, 0, 0, 0);
               calendar.set(java.util.Calendar.MILLISECOND, 0);
               java.util.Date utilDate = calendar.getTime();
               
               // Преобразуем в java.sql.Date для записи в базу данных
               Date sqlDate = new Date(utilDate.getTime());
               
               System.out.println("Datum erfolgreich konvertiert: " + dateText + " -> " + sqlDate);
               return sqlDate;
               
           } catch (Exception e) {
               System.err.println("Fehler beim Konvertieren von Datum aus jFormattedTextField2: " + dateText + " - " + e.getMessage());
               e.printStackTrace();
               return null;
           }
       }
       
       /**
        * Проверяет корректность формата даты dd.MM.yyyy
        * @param dateStr строка с датой
        * @return true если формат корректный, false в противном случае
        */
       private boolean isDateOKFullFormat(String dateStr) {
           if (dateStr == null || dateStr.length() != 10) {
               return false;
           }
           
           try {
               // Проверяем, что все символы на правильных позициях
               if (dateStr.charAt(2) != '.' || dateStr.charAt(5) != '.') {
                   return false;
               }
               
               // Проверяем, что все остальные символы - цифры
               for (int i = 0; i < dateStr.length(); i++) {
                   if (i != 2 && i != 5 && !Character.isDigit(dateStr.charAt(i))) {
                       return false;
                   }
               }
               
               // Парсим компоненты даты
               int day = Integer.parseInt(dateStr.substring(0, 2));
               int month = Integer.parseInt(dateStr.substring(3, 5));
               int year = Integer.parseInt(dateStr.substring(6, 8));
               
               // Проверяем корректность значений
               if (day < 1 || day > 31 || month < 1 || month > 12 || year < 00 || year > 99) {
                   return false;
               }
               
               return true;
               
           } catch (NumberFormatException e) {
               return false;
           }
       }
       Integer getIntFromField(String str){     // метод для получения чисел из поля формы
           return  null;
       }
       
       /**
        * Метод для тестирования преобразования даты из jFormattedTextField2
        * Поле содержит дату в формате "dd.MM.yyyy" (например: 25.12.2024)
        * Можно вызвать для проверки корректности работы метода convertWunschLieferDatumFromField()
        */
       public void testWunschLieferDatumConversion() {
           System.out.println("=== Testen von Konventieren WunschlieferDatum ===");
           
           // Получаем текущий текст из поля
           String currentText = jFormattedTextField2.getText();
           System.out.println("Aktuel im Textfeld: '" + currentText + "'");
           
           // Преобразуем в Date
           Date convertedDate = convertWunschLieferDatumFromField();
           
           if (convertedDate != null) {
               System.out.println("Konventieren ist erfolgreich: " + convertedDate);
           } else {
               System.out.println("Konventieren ist fehlgeschlagen");
           }
           
           System.out.println("=== Ende des Testens ===");
       }
       
       // ===== МЕТОДЫ ПРЕОБРАЗОВАНИЯ ДАТ ДЛЯ ВСЕХ ПОЛЕЙ =====
       
       /**
        * Универсальный метод для преобразования даты из любого поля с датой
        * @param field поле с датой
        * @param fieldName название поля для логирования
        * @return Date объект или null если преобразование не удалось
        */
      private Date convertDateFromField(javax.swing.JFormattedTextField field, String fieldName) {
           String dateText = field.getText();
           
           if (dateText == null || dateText.trim().isEmpty()) {
               System.out.println("Feld " + fieldName + " ist leer");
               return null;
           }
           
           try {
               // Проверяем формат даты dd.MM.yy (короткий формат)
               if (!isDateOKShortFormat(dateText)) {
                   System.err.println("Falsche Datenformat   : " + dateText + " im Feld " + fieldName + ". Wird format dd.MM.yy erwartet");
                  
                   return null;
               }
               
               // Парсим дату из формата dd.MM.yy
               int day = Integer.parseInt(dateText.substring(0, 2));
               int month = Integer.parseInt(dateText.substring(3, 5));
               int year = Integer.parseInt(dateText.substring(6, 8));
               
               // Преобразуем двухзначный год в четырехзначный (предполагаем 20xx для годов 00-99)
               int fullYear = 2000 + year;
               
               // Создаем объект java.util.Date используя Calendar
               java.util.Calendar calendar = java.util.Calendar.getInstance();
               calendar.set(fullYear, month - 1, day, 0, 0, 0);
               calendar.set(java.util.Calendar.MILLISECOND, 0);
               java.util.Date utilDate = calendar.getTime();
               
               // Преобразуем в java.sql.Date для записи в базу данных
               Date sqlDate = new Date(utilDate.getTime());
               
               System.out.println("Erfolgreich Daum konvertiert aus " + fieldName + ": " + dateText + " -> " + sqlDate);
               return sqlDate;
               
           } catch (Exception e) {
               System.err.println("Fehler beim Konvertieren von Datum aus " + fieldName + ": " + dateText + " - " + e.getMessage());
               e.printStackTrace();
               return null;
           }
       }

      /**
       * Нормализует произвольную дату из DB/Excel к виду dd.MM.yy для отображения в форме.
       */
      private String normalizeDateToShortDisplay(String rawDate) {
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
        * Проверяет корректность формата даты dd.MM.yy (короткий формат)
        * @param dateStr строка с датой
        * @return true если формат корректный, false в противном случае
        */
       private boolean isDateOKShortFormat(String dateStr) {
           if (dateStr == null || dateStr.length() != 8) {
               return false;
           }
           
           try {
               // Проверяем, что все символы на правильных позициях
               if (dateStr.charAt(2) != '.' || dateStr.charAt(5) != '.') {
                   return false;
               }
               
               // Проверяем, что все остальные символы - цифры
               for (int i = 0; i < dateStr.length(); i++) {
                   if (i != 2 && i != 5 && !Character.isDigit(dateStr.charAt(i))) {
                       return false;
                   }
               }
               
               // Парсим компоненты даты
               int day = Integer.parseInt(dateStr.substring(0, 2));
               int month = Integer.parseInt(dateStr.substring(3, 5));
               int year = Integer.parseInt(dateStr.substring(6, 8));
               
               // Проверяем корректность значений
               if (day < 1 || day > 31 || month < 1 || month > 12 || year < 0 || year > 99) {
                   return false;
               }
               
               return true;
               
           } catch (NumberFormatException e) {
               return false;
           }
       }
       
       // ===== МЕТОДЫ ДЛЯ КОНКРЕТНЫХ ПОЛЕЙ =====
       
       /**
        * Преобразует дату из поля jFormattedTextField1 (Datum der Anforderung)
        * @return Date объект или null если преобразование не удалось
        */
       public Date convertAnforderungDatumFromField() {
           return convertDateFromField(jFormattedTextField1, "Datum der Anforderung");
       }
       
       /**
        * Преобразует дату из поля jFormattedTextField4 (Auslieferung nach Plan)
        * @return Date объект или null если преобразование не удалось
        */
       public Date convertAuslieferungNachPlanFromField() {
           return convertDateFromField(jFormattedTextField4, "Auslieferung nach Plan");
       }
       
       /**
        * Преобразует дату из поля jFormattedTextField5 (Bestellt am)
        * @return Date объект или null если преобразование не удалось
        */
       public Date convertBestelltAmFromField() {
           return convertDateFromField(jFormattedTextField5, "Bestellt am");
       }
       
       /**
        * Преобразует дату из поля jFormattedTextField6 (Wareneingang TS nach Plan)
        * @return Date объект или null если преобразование не удалось
        */
       public Date convertWareneingangTSPlanFromField() {
           return convertDateFromField(jFormattedTextField6, "Wareneingang TS nach Plan");
       }
       
       /**
        * Преобразует дату из поля jFormattedTextField7 (Wareneingang TS erfolgt am)
        * @return Date объект или null если преобразование не удалось
        */
       public Date convertWareneingangTSFaktFromField() {
           return convertDateFromField(jFormattedTextField7, "Wareneingang TS erfolgt am");
       }
       
       /**
        * Преобразует дату из поля jFormattedTextField8 (Lieferung an BS erfolgt am)
        * @return Date объект или null если преобразование не удалось
        */
       public Date convertLieferungAnBSFromField() {
           return convertDateFromField(jFormattedTextField8, "Lieferung an BS erfolgt am");
       }
       
       /**
        * Преобразует дату из поля jFormattedTextField9 (Wareneingang erfolgt am)
        * @return Date объект или null если преобразование не удалось
        */
       public Date convertWareneingangFaktFromField() {
           return convertDateFromField(jFormattedTextField9, "Wareneingang erfolgt am");
       }
       
       /**
        * Преобразует дату из поля jFormattedTextField10 (Datum)
        * @return Date объект или null если преобразование не удалось
        */
       public Date convertDatumFromField() {
           return convertDateFromField(jFormattedTextField10, "Datum");
       }
       
       /**
        * Метод для тестирования всех полей с датами
        */
       public void testAllDateFieldsConversion() {
           System.out.println("=== Testen aller Felder mit Daten ===");
           
           // Тестируем все поля с датами
           Date anforderungDatum = convertAnforderungDatumFromField();
           Date auslieferungNachPlan = convertAuslieferungNachPlanFromField();
           Date bestelltAm = convertBestelltAmFromField();
           Date wareneingangTSPlan = convertWareneingangTSPlanFromField();
           Date wareneingangTSFakt = convertWareneingangTSFaktFromField();
           Date lieferungAnBS = convertLieferungAnBSFromField();
           Date wareneingangFakt = convertWareneingangFaktFromField();
           Date datum = convertDatumFromField();
           Date wunschLieferDatum = convertWunschLieferDatumFromField();
           /*
           System.out.println("Ergebnisse des Konvertierens##### :");
           System.out.println("AnforderungDatum: " + (anforderungDatum != null ? anforderungDatum : "null"));
           System.out.println("AuslieferungNachPlan: " + (auslieferungNachPlan != null ? auslieferungNachPlan : "null"));
           System.out.println("BestelltAm: " + (bestelltAm != null ? bestelltAm : "null"));
           System.out.println("WareneingangTSPlan: " + (wareneingangTSPlan != null ? wareneingangTSPlan : "null"));
           System.out.println("WareneingangTSFakt: " + (wareneingangTSFakt != null ? wareneingangTSFakt : "null"));
           System.out.println("LieferungAnBS: " + (lieferungAnBS != null ? lieferungAnBS : "null"));
           System.out.println("WareneingangFakt: " + (wareneingangFakt != null ? wareneingangFakt : "null"));
           System.out.println("Datum: " + (datum != null ? datum : "null"));
           System.out.println("WunschLieferDatum: " + (wunschLieferDatum != null ? wunschLieferDatum : "null"));
           
           System.out.println("=== Ende des Testens  ======================");*/
       }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    public void startComponents(){
         jButtonLoc = new JButton("Lieferung  orten");
           jButtonPro = new JButton("Projektname / Info");
           jButtonZuArtikelliste = new javax.swing.JButton("zur Artikelliste");
     jPanel1 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
       
        jPanel31 = new javax.swing.JPanel();
        jPanel79 = new javax.swing.JPanel();
          jPanel80 = new javax.swing.JPanel(); 
          jPanel81 = new javax.swing.JPanel(); 
          jPanel4 = new javax.swing.JPanel(); 
          jPanel78 = new javax.swing.JPanel();
        jPanel73 = new javax.swing.JPanel();
        jPanel74 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
       
        jComboBox1 = new javax.swing.JComboBox<>(); 
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
       jFormattedTextField3 = new javax.swing.JFormattedTextField();
      //  System.out.println("##############  Result beim Start jFormTextFild3  "+ jFormattedTextField3.getValue()+ "  ---  "+ (String)jFormattedTextField3.getValue());
        jComboBox5 = new javax.swing.JComboBox<>();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
      jTextArea1 = new javax.swing.JTextArea();
           labelBestellung = new javax.swing.JLabel();
        statusBestellung = new javax.swing.JLabel();
         jLabel2 = new javax.swing.JLabel();
        statusEinkauf = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        statusBaustelle = new javax.swing.JLabel();
         jLabel4 = new javax.swing.JLabel();
        statusControlling = new javax.swing.JLabel();
        tFAgreementNR = new javax.swing.JTextField();
         jFormattedTextField17 = new javax.swing.JFormattedTextField();
         jFormattedTextField8 = new javax.swing.JFormattedTextField();
         jTextField17 = new javax.swing.JTextField();
      jFormattedTextField1 = new javax.swing.JFormattedTextField();
       lieferant_tFKommentar01 = new javax.swing.JTextField();
          jFormattedTextField9 = new javax.swing.JFormattedTextField();
         jFormattedTextField10 = new javax.swing.JFormattedTextField();
         tFAnforderungNR = new javax.swing.JTextField();
       jFormattedTextField5 = new javax.swing.JFormattedTextField();
        jFormattedTextField16 = new javax.swing.JFormattedTextField();
          jTextField4 = new javax.swing.JTextField();
        jFormattedTextField15 = new javax.swing.JFormattedTextField();
        jComboBox9 = new javax.swing.JComboBox<>();
          jFormattedTextField12 = new javax.swing.JFormattedTextField();
       jTextField1 = new javax.swing.JTextField();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
         jComboBox10 = new javax.swing.JComboBox<>();
        jFormattedTextField18 = new javax.swing.JFormattedTextField();
          jFormattedTextField13 = new javax.swing.JFormattedTextField();
       jFormattedTextField7 = new javax.swing.JFormattedTextField();
         jFormattedTextField20 = new javax.swing.JFormattedTextField();
       jFormattedTextField14 = new javax.swing.JFormattedTextField();
        jFormattedTextField19 = new javax.swing.JFormattedTextField();
         jFormattedTextField4 = new javax.swing.JFormattedTextField();
         jTextField2 = new javax.swing.JTextField();
         jTextField14 = new javax.swing.JTextField();
       jTextField8 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
         jTextField9 = new javax.swing.JTextField();
       jTextField5 = new javax.swing.JTextField();
         jCheckBox1 = new javax.swing.JCheckBox();
           jTextField16 = new javax.swing.JTextField();
      
         
         
       jPanel75 = new javax.swing.JPanel();
       jPanel76 = new javax.swing.JPanel();
       jPanel77 = new javax.swing.JPanel();
       jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
       jPanel22 = new javax.swing.JPanel();
       jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel51 = new javax.swing.JPanel();
         jPanel25 = new javax.swing.JPanel();
       jPanel3 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jPanel54 = new javax.swing.JPanel();
        jPanel55 = new javax.swing.JPanel();
       jPanel33 = new javax.swing.JPanel();
       jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
       jPanel14 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
      jPanel10 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
       jPanel36 = new javax.swing.JPanel();
       jPanel42 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
       jPanel37 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel61 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
       jPanel38 = new javax.swing.JPanel();
        jPanel62 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel63 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
       jPanel35 = new javax.swing.JPanel();
        jPanel64 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jPanel65 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jPanel66 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel71 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jPanel72 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
         jMenuItem4 = new javax.swing.JMenuItem();
          jMenu3 = new javax.swing.JMenu();
           jMenu4 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
         jMenuItem7 = new javax.swing.JMenuItem();
 
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle(titel );
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setFocusCycleRoot(false);
        setFocusTraversalPolicyProvider(true);
        setLocation(new java.awt.Point(50, 10));
        setMaximumSize(new java.awt.Dimension(1200, 800));
        setMinimumSize(new java.awt.Dimension(500, 400));
        setPreferredSize(new java.awt.Dimension(1000, 750));
        setSize(new java.awt.Dimension(1000, 750));
        setType(java.awt.Window.Type.NORMAL);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setMaximumSize(new java.awt.Dimension(900, 450));
        jPanel1.setMinimumSize(new java.awt.Dimension(500, 100));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 250));
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        jPanel30.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 102), new java.awt.Color(255, 255, 51), null, null));
        jPanel30.setLayout(new java.awt.GridLayout(2, 4, 1, 1));

        jCheckBox2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox2.setText("neu");
        jCheckBox2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox2);

        jCheckBox3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox3.setText("aktiv");
        jCheckBox3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox3);

        jCheckBox4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox4.setText("MH unterschrieben");
        jCheckBox4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox4);

        jCheckBox5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox5.setText("Unterschr. Kunde");
        jCheckBox5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox5);

        jCheckBox6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox6.setText("an Einkauf weitergeleitet");
        jCheckBox6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox6);

        jCheckBox7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox7.setText("Investition/Leasing");
       
        jPanel30.add(jCheckBox7);
       
        jButtonLoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonLocMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonLocMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonLocMousePressed(evt);
               
            }
        });
       
         jButtonPro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButtonProMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButtonProMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButtonProMousePressed(evt);
              
            }
        });
        jPanel30.add(jButtonPro);
        jPanel30.add(jButtonLoc);
        jPanel1.add(jPanel30);

        jPanel31.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel31.setMinimumSize(new java.awt.Dimension(109, 200));
        jPanel31.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel79.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DE", "EU", "Sonst", "unbekannt" }));
        jComboBox1.setToolTipText("");
        jComboBox1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Herkunft", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N

        javax.swing.GroupLayout jPanel79Layout = new javax.swing.GroupLayout(jPanel79);
        jPanel79.setLayout(jPanel79Layout);
        jPanel79Layout.setHorizontalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
            .addGroup(jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel79Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jComboBox1, 0, 298, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel79Layout.setVerticalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
            .addGroup(jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel79Layout.createSequentialGroup()
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel31.add(jPanel79);

        jPanel80.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel80.setLayout(new java.awt.BorderLayout());

        jFormattedTextField3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Menge", javax.swing.border.TitledBorder.CENTER, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField3.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jPanel80.add(jFormattedTextField3, java.awt.BorderLayout.CENTER);

        jComboBox5.setFont(new java.awt.Font("Segoe UI", 1, 10)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "St.", "Tonnen", "Kg.", "M", "M2", "M3",  "Satz" }));
        jComboBox5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einh.",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
        jPanel80.add(jComboBox5, java.awt.BorderLayout.EAST);

        jPanel31.add(jPanel80);

        jPanel81.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jFormattedTextField2.setBackground(new java.awt.Color(255, 204, 204));
        jFormattedTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wunschliefertermin", 
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField2.setFormatterFactory(
            new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd.MM.yy"))
            )
        );
        jFormattedTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField2ActionPerformed(evt);
            }
        });
        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel81Layout = new javax.swing.GroupLayout(jPanel81);
        jPanel81.setLayout(jPanel81Layout);
        jPanel81Layout.setHorizontalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
            .addGroup(jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
        );
        jPanel81Layout.setVerticalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
            .addGroup(jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel81Layout.createSequentialGroup()
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel31.add(jPanel81);

        jPanel1.add(jPanel31);

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setMaximumSize(new java.awt.Dimension(900, 250));
        jPanel4.setMinimumSize(new java.awt.Dimension(500, 60));
        jPanel4.setName(""); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(800, 70));
        jPanel4.setRequestFocusEnabled(false);

        jScrollPane1.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(500, 82));
        jScrollPane1.setName(""); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(238, 70));

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(50);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Beschreibung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextArea1.setMaximumSize(new java.awt.Dimension(2000, 2000));
        jTextArea1.setMinimumSize(new java.awt.Dimension(232, 50));
        jTextArea1.setName(""); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);
        
        // Добавляем обработчик двойного клика для открытия расширенного редактора
        jTextArea1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    openTextAreaEditor();
                }
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel78Layout = new javax.swing.GroupLayout(jPanel78);
        jPanel78.setLayout(jPanel78Layout);
        jPanel78Layout.setHorizontalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE) // было MAx_VALUE
        );
        jPanel78Layout.setVerticalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, 1) // было (0, 0, 3)
        );
        jPanel78.setVisible(false);

        jPanel73.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel73.setLayout(new java.awt.GridLayout(1, 4));

        jPanel74.setBackground(new java.awt.Color(204, 255, 204));
        jPanel74.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel74.setPreferredSize(new java.awt.Dimension(1000, 30));
        jPanel74.setLayout(new java.awt.BorderLayout());

        labelBestellung.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelBestellung.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBestellung.setText("BESTELLUNG");
        jPanel74.add(labelBestellung, java.awt.BorderLayout.CENTER);

        statusBestellung.setBackground(lieferung.getColorDirekt());
       // statusBestellung.setBackground(new java.awt.Color(255, 0, 0));
        statusBestellung.setForeground(new java.awt.Color(255, 0, 102));
        statusBestellung.setText("        ");
        statusBestellung.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        statusBestellung.setOpaque(true);
        jPanel74.add(statusBestellung, java.awt.BorderLayout.EAST);

        jPanel73.add(jPanel74);

        jPanel75.setBackground(new java.awt.Color(204, 204, 255));
        jPanel75.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel75.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("EINKAUF");
        jPanel75.add(jLabel2, java.awt.BorderLayout.CENTER);

        statusEinkauf.setText("        ");
        statusEinkauf.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel75.add(statusEinkauf, java.awt.BorderLayout.EAST);

        jPanel73.add(jPanel75);

        jPanel76.setBackground(new java.awt.Color(255, 153, 153));
        jPanel76.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel76.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("BAUSTELLE");
        jPanel76.add(jLabel3, java.awt.BorderLayout.CENTER);

        statusBaustelle.setText("        ");
        statusBaustelle.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel76.add(statusBaustelle, java.awt.BorderLayout.EAST);

        jPanel73.add(jPanel76);

        jPanel77.setBackground(new java.awt.Color(255, 255, 204));
        jPanel77.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel77.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("CONTROLLING");
        jPanel77.add(jLabel4, java.awt.BorderLayout.CENTER);

        statusControlling.setText("        ");
        statusControlling.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel77.add(statusControlling, java.awt.BorderLayout.EAST);

        jPanel73.add(jPanel77);

        jPanel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.setAutoscrolls(true);
        jPanel9.setMaximumSize(new java.awt.Dimension(1000, 600));
        jPanel9.setMinimumSize(new java.awt.Dimension(600, 100));
        jPanel9.setPreferredSize(new java.awt.Dimension(900, 600));
        jPanel9.setRequestFocusEnabled(false);
        jPanel9.setVerifyInputWhenFocusTarget(false);
        jPanel9.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane2.setFocusCycleRoot(true);
        jScrollPane2.setFocusTraversalPolicyProvider(true);
        jScrollPane2.setInheritsPopupMenu(true);
        jScrollPane2.setMaximumSize(new java.awt.Dimension(1200, 1000));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(600, 200));
        jScrollPane2.setName(""); // NOI18N
        jScrollPane2.setPreferredSize(new java.awt.Dimension(900, 900));
        jScrollPane2.setViewportView(jPanel2);

        jPanel2.setAutoscrolls(true);
        jPanel2.setFocusCycleRoot(true);
        jPanel2.setFocusTraversalPolicyProvider(true);
        jPanel2.setMaximumSize(new java.awt.Dimension(1200, 1000));
        jPanel2.setMinimumSize(new java.awt.Dimension(500, 700));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 900));
        jPanel2.setLayout(new java.awt.GridLayout(12, 1, 1, 1));

        jPanel27.setLayout(new java.awt.GridLayout(1, 4, 1, 1));

        jPanel28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel28.setLayout(new java.awt.BorderLayout());

        tFAgreementNR.setText("jTextField11");
        tFAgreementNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agreement/BS Anford. Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel28.add(tFAgreementNR, java.awt.BorderLayout.CENTER);

        jPanel27.add(jPanel28);

        jPanel29.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel29.setLayout(new javax.swing.BoxLayout(jPanel29, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preis", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField17.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField17MouseExited(evt);
            }
        });
        jFormattedTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField17KeyPressed(evt);
            }
        });
        jPanel29.add(jFormattedTextField17);

        jPanel27.add(jPanel29);

        jPanel50.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel50.setLayout(new javax.swing.BoxLayout(jPanel50, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lieferung an BS erfolgt am",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField8.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField8KeyPressed(evt);
            }
        });
        jPanel50.add(jFormattedTextField8);

        jPanel27.add(jPanel50);

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.LINE_AXIS));

        jTextField17.setText("jTextField17");
        jTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RG-Nummer an AG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel22.add(jTextField17);

        jPanel27.add(jPanel22);

        jPanel2.add(jPanel27);

        jPanel5.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datum der Anforderung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField1.setToolTipText("Datum in Format dd.mm.yy");
        jFormattedTextField1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel6.add(jFormattedTextField1);

        jPanel5.add(jPanel6);

        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        lieferant_tFKommentar01.setText("jTextField13");
        lieferant_tFKommentar01.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lieferant", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel7.add(lieferant_tFKommentar01);

        jPanel5.add(jPanel7);

        jPanel51.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel51.setLayout(new javax.swing.BoxLayout(jPanel51, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang erfolgt am", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField9.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField9KeyPressed(evt);
            }
        });
        jPanel51.add(jFormattedTextField9);

        jPanel5.add(jPanel51);

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel25.setLayout(new javax.swing.BoxLayout(jPanel25, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datum", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField10KeyPressed(evt);
            }
        });
        jPanel25.add(jFormattedTextField10);

        jPanel5.add(jPanel25);

        jPanel2.add(jPanel5);

        jPanel3.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel53.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel53.setLayout(new javax.swing.BoxLayout(jPanel53, javax.swing.BoxLayout.LINE_AXIS));

        tFAnforderungNR.setText("jTextField12");
        tFAnforderungNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BANF Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel53.add(tFAnforderungNR);
        
        // Добавляем обработчик двойного клика для tFAnforderungNR
        tFAnforderungNR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    openTextFieldEditor(tFAnforderungNR, "Anforderung Nr. - Erweiterte Bearbeitung", "Anforderung Nr.");
                }
            }
        });

        jPanel3.add(jPanel53);
//"Bestellt am (dd.mm.yy)"

        jPanel54.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null)); // NOI18N
        jPanel54.setLayout(new javax.swing.BoxLayout(jPanel54, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "(dd.mm.yy)Bestellt am ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField5.setFormatterFactory(
            new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd.MM.yy"))
            )
        );
        jFormattedTextField5.setAlignmentX(LEFT_ALIGNMENT);
        jFormattedTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField5KeyPressed(evt);
            }
        });
        jPanel54.add(jFormattedTextField5);
        
        
        
        
        
        
        

        jPanel3.add(jPanel54);

        jPanel55.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel55.setLayout(new javax.swing.BoxLayout(jPanel55, javax.swing.BoxLayout.LINE_AXIS));

        jTextField5.setText("jTextField5");
        jTextField5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kommentar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel55.add(jTextField5);

        jPanel3.add(jPanel55);

        jPanel33.setBackground(new java.awt.Color(255, 255, 204));
        jPanel33.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel33.setLayout(new javax.swing.BoxLayout(jPanel33, javax.swing.BoxLayout.LINE_AXIS));

        jCheckBox1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox1.setText("Weitergerechnet an AG");
        jPanel33.add(jCheckBox1);

        jPanel3.add(jPanel33);

        jPanel2.add(jPanel3);

        jPanel12.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Summe für Material und Leistungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField16.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField16MouseExited(evt);
            }
              public void mousePressed(java.awt.event.MouseEvent evt) {
                jFormattedTextField16MousePressed(evt);
            }

         private void jFormattedTextField16MousePressed(MouseEvent evt) {
           //  throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
         }
        });
        jFormattedTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField16KeyPressed(evt);
            }
        });
        jPanel13.add(jFormattedTextField16);

        jPanel12.add(jPanel13);

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jTextField4.setText("jTextField4");
        jTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellungsnummer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel14.add(jTextField4, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel14);

        jPanel56.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel56.setLayout(new javax.swing.BoxLayout(jPanel56, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Gewicht", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField15.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField15KeyPressed(evt);
            }
        });
        jPanel56.add(jFormattedTextField15);

        jComboBox9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tonnen", "Kg", "" }));
        jComboBox9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einh.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel56.add(jComboBox9);

        jPanel12.add(jPanel56);

        jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Angebotssumme weitergerechnet", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField12.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField12MouseExited(evt);
            }
        });
        jFormattedTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField12KeyPressed(evt);
            }
        });
        jPanel10.add(jFormattedTextField12);

        jPanel12.add(jPanel10);

        jPanel2.add(jPanel12);

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel15.setLayout(new java.awt.GridLayout(1, 3));

        jPanel16.setBackground(new java.awt.Color(204, 255, 204));
        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        jTextField1.setText("jTextField1");
        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellt von", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel16.add(jTextField1);
        
        // Добавляем обработчик двойного клика для jTextField1
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    openTextFieldEditor(jTextField1, "Bestellt von - Erweiterte Bearbeitung", "Bestellt von");
                }
            }
        });

        jPanel15.add(jPanel16);

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS nach Plan (dd.mm.yy)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField6.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField6KeyPressed(evt);
            }
        });
        jPanel17.add(jFormattedTextField6);

        jPanel15.add(jPanel17);

        jPanel36.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel36.setLayout(new javax.swing.BoxLayout(jPanel36, javax.swing.BoxLayout.LINE_AXIS));

        jComboBox10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "CM", "MM" }));
        jComboBox10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einh.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel36.add(jComboBox10);

        jFormattedTextField18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Länge", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField18.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jPanel36.add(jFormattedTextField18);

        jPanel15.add(jPanel36);

        jPanel42.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel42.setLayout(new javax.swing.BoxLayout(jPanel42, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Angebotssumme nicht weitergerechnet", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField13.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField13MouseExited(evt);
            }
        });
        jFormattedTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField13KeyPressed(evt);
            }
        });
        jPanel42.add(jFormattedTextField13);

        jPanel15.add(jPanel42);

        jPanel2.add(jPanel15);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel18.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel19.setBackground(new java.awt.Color(204, 255, 204));
        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, new java.awt.Color(0, 153, 102)));
        jPanel19.setLayout(new java.awt.BorderLayout());
       
        jButtonZuArtikelliste.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                de.zmiter.liefermonitor01.Logik.ArtikelListe artList = new de.zmiter.liefermonitor01.Logik.ArtikelListe();
                artList.person = person;
                String anfordNr = tFAgreementNR != null ? tFAgreementNR.getText() : "";
                artList.setSearchTextAndPerformSearch(anfordNr != null ? anfordNr.trim() : "");
                artList.setLocationRelativeTo(BearbForm.this);
                artList.setVisible(true);
            }
        });
        jPanel19.add(jButtonZuArtikelliste, java.awt.BorderLayout.CENTER);
        jPanel18.add(jPanel19);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS erfolgt am", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField7.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField7KeyPressed(evt);
            }
        });
        jPanel20.add(jFormattedTextField7);

        jPanel18.add(jPanel20);

        jPanel37.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel37.setLayout(new javax.swing.BoxLayout(jPanel37, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Breite", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField20.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jPanel37.add(jFormattedTextField20);

        jPanel18.add(jPanel37);

        jPanel46.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel46.setLayout(new javax.swing.BoxLayout(jPanel46, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kunden in Rechnung stellen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField14.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField14MouseExited(evt);
            }
        });
        jFormattedTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField14KeyPressed(evt);
            }
        });
        jPanel46.add(jFormattedTextField14);

        jPanel18.add(jPanel46);

        jPanel2.add(jPanel18);

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel21.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel61.setBackground(new java.awt.Color(204, 255, 204));
        jPanel61.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jPanel21.add(jPanel61);

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.LINE_AXIS));

        jTextField2.setText("jTextField2");
        jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel23.add(jTextField2);

        jPanel21.add(jPanel23);

        jPanel38.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel38.setLayout(new javax.swing.BoxLayout(jPanel38, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Höhe", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField19.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jPanel38.add(jFormattedTextField19);

        jPanel21.add(jPanel38);

        jPanel62.setBackground(new java.awt.Color(255, 255, 204));
        jPanel62.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jPanel21.add(jPanel62);

        jPanel2.add(jPanel21);

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel24.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel63.setBackground(new java.awt.Color(204, 255, 204));
        jPanel63.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jPanel24.add(jPanel63);

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel26.setLayout(new javax.swing.BoxLayout(jPanel26, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Auslieferung nach Plan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jPanel26.add(jFormattedTextField4);

        jPanel24.add(jPanel26);

        jPanel35.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel35.setLayout(new javax.swing.BoxLayout(jPanel35, javax.swing.BoxLayout.LINE_AXIS));

        jTextField14.setText("jTextField14");
        jTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kolli", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel35.add(jTextField14);

        jPanel24.add(jPanel35);

        jPanel64.setBackground(new java.awt.Color(255, 255, 204));
        jPanel64.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jPanel24.add(jPanel64);

        jPanel2.add(jPanel24);

        jPanel32.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel32.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel65.setBackground(new java.awt.Color(204, 255, 204));
        jPanel65.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jPanel32.add(jPanel65);

        jPanel34.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel34.setLayout(new javax.swing.BoxLayout(jPanel34, javax.swing.BoxLayout.LINE_AXIS));

        jTextField8.setText("jTextField8");
        jTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Zahlungsbedingungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel34.add(jTextField8);

        jPanel32.add(jPanel34);

        jPanel39.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel39.setLayout(new javax.swing.BoxLayout(jPanel39, javax.swing.BoxLayout.LINE_AXIS));

        jTextField15.setText("");
        jTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel39.add(jTextField15);
        
        // Добавляем обработчик двойного клика для jTextField15
        jTextField15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    openTextFieldEditor(jTextField15, "PL - Erweiterte Bearbeitung", "PL");
                }
            }
        });

        jPanel32.add(jPanel39);

        jPanel66.setBackground(new java.awt.Color(255, 255, 204));
        jPanel66.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jPanel32.add(jPanel66);

        jPanel2.add(jPanel32);

        jPanel45.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel45.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel71.setBackground(new java.awt.Color(204, 255, 204));
        jPanel71.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel71Layout = new javax.swing.GroupLayout(jPanel71);
        jPanel71.setLayout(jPanel71Layout);
        jPanel71Layout.setHorizontalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel71Layout.setVerticalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jPanel45.add(jPanel71);

        jPanel47.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel47.setLayout(new javax.swing.BoxLayout(jPanel47, javax.swing.BoxLayout.LINE_AXIS));

        jTextField9.setText("jTextField9");
        jTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kommentar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        
        // Добавляем обработчик двойного клика для jTextField9
        jTextField9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    openTextFieldEditor(jTextField9, "Kommentar - Erweiterte Bearbeitung", "Kommentar");
                }
            }
        });
        
         jTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                   openGoogleMapsFromTextField();
                }
            }
        });
        
        jPanel47.add(jTextField9);

        jPanel45.add(jPanel47);

        jPanel48.setBackground(new java.awt.Color(242, 242, 242));  //255, 153, 153
        jPanel48.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel48.setLayout(new javax.swing.BoxLayout(jPanel48, javax.swing.BoxLayout.LINE_AXIS));

        jTextField16.setText("jTextField16");
        jTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Container", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel48.add(jTextField16);

        jPanel45.add(jPanel48);

        jPanel72.setBackground(new java.awt.Color(255, 255, 204));
        jPanel72.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel72Layout = new javax.swing.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 45, Short.MAX_VALUE)
        );

        jPanel45.add(jPanel72);

        jPanel2.add(jPanel45);

        jScrollPane2.setViewportView(jPanel2);

        jPanel9.add(jScrollPane2, java.awt.BorderLayout.CENTER);

         jMenuBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jMenu1.setText("File");

        jMenuItem1.setText("Speichern in DB");
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

      
        jMenu2.setText("Edit");

        jMenuItem2.setText("Projektname");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        jMenuItem2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenuItem2KeyPressed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Neue Lieferung eintragen");
        jMenuItem3.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
            public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
                jMenuItem3MenuKeyPressed(evt);
            }
            public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
            }
            public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
            }
        });
        jMenuItem3.addMenuDragMouseListener(new javax.swing.event.MenuDragMouseListener() {
            public void menuDragMouseDragged(javax.swing.event.MenuDragMouseEvent evt) {
            }
            public void menuDragMouseEntered(javax.swing.event.MenuDragMouseEvent evt) {
                jMenuItem3MenuDragMouseEntered(evt);
            }
            public void menuDragMouseExited(javax.swing.event.MenuDragMouseEvent evt) {
                jMenuItem3MenuDragMouseExited(evt);
            }
            public void menuDragMouseReleased(javax.swing.event.MenuDragMouseEvent evt) {
            }
        });
        jMenu2.add(jMenuItem3);
       

          jMenu3.setText("Löschen");
          jMenuItem4.setText("aus DB entfernen");
        jMenuItem4.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem4MouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem4MouseExited(evt);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem4MousePressed(evt);
            }
        });
      
        jMenu3.add(jMenuItem4);
        
           jMenu4.setText("DE");
          jMenuItem5.setText("set English as a language");
        jMenuItem5.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem5MouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem5MouseExited(evt);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem5MousePressed(evt);
            }
        });
      
        jMenu4.add(jMenuItem5);
         jMenuItem6.setText("Выбрать русский язык");
        jMenuItem6.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem6MouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem6MouseExited(evt);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem6MousePressed(evt);
            }
        });
          jMenu4.add(jMenuItem6);
          
           jMenuItem7.setText("Deutsch als Sprache wählen");
        jMenuItem7.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem7MouseEntered(evt);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem7MouseExited(evt);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem7MousePressed(evt);
            }
        });
            jMenu4.add(jMenuItem7);
           jMenuBar1.add(jMenu1);
          jMenuBar1.add(jMenu2);
         jMenuBar1.add(jMenu3);
           jMenuBar1.add(jMenu4);
        

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 963, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 963, Short.MAX_VALUE)
            /**/
                    .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel73, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel78, 0, 0, 0)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        // Увеличиваем высоту всех текстовых полей на 30%
        java.awt.Dimension defaultSize = new java.awt.Dimension();
        java.awt.Dimension newSize = new java.awt.Dimension();
        
        // JTextField поля
        java.awt.Component[] textFields = {
            tFAgreementNR, jTextField17, lieferant_tFKommentar01, tFAnforderungNR, 
            jTextField4, jTextField1, jTextField2, jTextField14, jTextField8, 
            jTextField15, jTextField9, jTextField5, jTextField16
        };
        
        // JFormattedTextField поля
        java.awt.Component[] formattedTextFields = {
            jFormattedTextField3, jFormattedTextField2, jFormattedTextField17, 
            jFormattedTextField8, jFormattedTextField1, jFormattedTextField9, 
            jFormattedTextField10, jFormattedTextField5, jFormattedTextField16, 
            jFormattedTextField15, jFormattedTextField12, jFormattedTextField6, 
            jFormattedTextField18, jFormattedTextField13, jFormattedTextField7, 
            jFormattedTextField20, jFormattedTextField14, jFormattedTextField19, 
            jFormattedTextField4
        };
        
        // Устанавливаем увеличенную высоту для всех текстовых полей
        for (java.awt.Component field : textFields) {
            if (field != null) {
                defaultSize = field.getPreferredSize();
                newSize = new java.awt.Dimension(defaultSize.width, (int)(defaultSize.height * 1.5));
                field.setPreferredSize(newSize);
            }
        }
        
        for (java.awt.Component field : formattedTextFields) {
            if (field != null) {
                defaultSize = field.getPreferredSize();
                newSize = new java.awt.Dimension(defaultSize.width, (int)(defaultSize.height * 1.5));
                field.setPreferredSize(newSize);
            }
        }
        
        // Добавляем обработчики двойного клика для всех JTextField
        addTextFieldDoubleClickHandlers();

        pack();
    }// </editor-fold>                        

     private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jCheckBox4 = new javax.swing.JCheckBox();
        jCheckBox5 = new javax.swing.JCheckBox();
        jCheckBox6 = new javax.swing.JCheckBox();
        jCheckBox7 = new javax.swing.JCheckBox();
        jPanel31 = new javax.swing.JPanel();
        jPanel79 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel80 = new javax.swing.JPanel();
        jFormattedTextField3 = new javax.swing.JFormattedTextField();
        jComboBox5 = new javax.swing.JComboBox<>();
        jPanel81 = new javax.swing.JPanel();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel78 = new javax.swing.JPanel();
        jPanel73 = new javax.swing.JPanel();
        jPanel74 = new javax.swing.JPanel();
        labelBestellung = new javax.swing.JLabel();
        statusBestellung = new javax.swing.JLabel();
        jPanel75 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        statusEinkauf = new javax.swing.JLabel();
        jPanel76 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        statusBaustelle = new javax.swing.JLabel();
        jPanel77 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        statusControlling = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        tFAgreementNR = new javax.swing.JTextField();
        jPanel29 = new javax.swing.JPanel();
        jFormattedTextField17 = new javax.swing.JFormattedTextField();
        jPanel50 = new javax.swing.JPanel();
        jFormattedTextField8 = new javax.swing.JFormattedTextField();
        jPanel22 = new javax.swing.JPanel();
        jTextField17 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jPanel7 = new javax.swing.JPanel();
        lieferant_tFKommentar01 = new javax.swing.JTextField();
        jPanel51 = new javax.swing.JPanel();
        jFormattedTextField9 = new javax.swing.JFormattedTextField();
        jPanel25 = new javax.swing.JPanel();
        jFormattedTextField10 = new javax.swing.JFormattedTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        tFAnforderungNR = new javax.swing.JTextField();
        jPanel54 = new javax.swing.JPanel();
        jFormattedTextField5 = new javax.swing.JFormattedTextField();
        jPanel55 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jPanel33 = new javax.swing.JPanel();
        JFormattedTextField jFormattedTextField11 = new javax.swing.JFormattedTextField();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jFormattedTextField4 = new javax.swing.JFormattedTextField();
        jPanel14 = new javax.swing.JPanel();
        jTextField4 = new javax.swing.JTextField();
        jPanel56 = new javax.swing.JPanel();
        jFormattedTextField15 = new javax.swing.JFormattedTextField();
        jComboBox9 = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        jFormattedTextField12 = new javax.swing.JFormattedTextField();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jFormattedTextField16 = new javax.swing.JFormattedTextField();
        jPanel17 = new javax.swing.JPanel();
        jFormattedTextField6 = new javax.swing.JFormattedTextField();
        jPanel36 = new javax.swing.JPanel();
        jComboBox10 = new javax.swing.JComboBox<>();
        jFormattedTextField18 = new javax.swing.JFormattedTextField();
        jPanel42 = new javax.swing.JPanel();
        jFormattedTextField13 = new javax.swing.JFormattedTextField();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        JLabel jLabel1 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jFormattedTextField7 = new javax.swing.JFormattedTextField();
        jPanel37 = new javax.swing.JPanel();
        jFormattedTextField20 = new javax.swing.JFormattedTextField();
        jPanel46 = new javax.swing.JPanel();
        jFormattedTextField14 = new javax.swing.JFormattedTextField();
        jPanel21 = new javax.swing.JPanel();
        jPanel61 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jPanel38 = new javax.swing.JPanel();
        jFormattedTextField19 = new javax.swing.JFormattedTextField();
        jPanel62 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jPanel63 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        JTextField jTextField6 = new javax.swing.JTextField();
        jPanel35 = new javax.swing.JPanel();
        jTextField14 = new javax.swing.JTextField();
        jPanel64 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jPanel65 = new javax.swing.JPanel();
        jPanel34 = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        jPanel39 = new javax.swing.JPanel();
        jTextField15 = new javax.swing.JTextField();
        jPanel66 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel71 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jTextField9 = new javax.swing.JTextField();
        jPanel48 = new javax.swing.JPanel();
        jTextField16 = new javax.swing.JTextField();
        jPanel72 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
         jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(name);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setFocusCycleRoot(false);
        setFocusTraversalPolicyProvider(true);
        setLocation(new java.awt.Point(100, 100));
        setMaximumSize(new java.awt.Dimension(1200, 800));
        setMinimumSize(new java.awt.Dimension(500, 400));
        setPreferredSize(new java.awt.Dimension(1000, 550));
        setSize(new java.awt.Dimension(1000, 600));
        setType(java.awt.Window.Type.UTILITY);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setMaximumSize(new java.awt.Dimension(900, 100));
        jPanel1.setMinimumSize(new java.awt.Dimension(500, 50));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 150));
        jPanel1.setLayout(new java.awt.GridLayout(2, 1));

        jPanel30.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 102), new java.awt.Color(255, 255, 51), null, null));
        jPanel30.setLayout(new java.awt.GridLayout(2, 3, 1, 1));

        jCheckBox2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox2.setText("neu");
        jCheckBox2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox2);

        jCheckBox3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox3.setText("aktiv");
        jCheckBox3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox3);

        jCheckBox4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox4.setText("MH unterschrieben");
        jCheckBox4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox4);

        jCheckBox5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox5.setText("Unterschr. Kunde");
        jCheckBox5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox5);

        jCheckBox6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox6.setText("an Einkauf weitergeleitet");
        jCheckBox6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel30.add(jCheckBox6);

        jCheckBox7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jCheckBox7.setText("Investition/Leasing");
        jPanel30.add(jCheckBox7);

        jPanel1.add(jPanel30);

        jPanel31.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel31.setMinimumSize(new java.awt.Dimension(109, 160));
        jPanel31.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel79.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DE", "EU", "Sonst", "unbekannt" }));
        jComboBox1.setToolTipText("");
        jComboBox1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Herkunft", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        javax.swing.GroupLayout jPanel79Layout = new javax.swing.GroupLayout(jPanel79);
        jPanel79.setLayout(jPanel79Layout);
        jPanel79Layout.setHorizontalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
            .addGroup(jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel79Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jComboBox1, 0, 298, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel79Layout.setVerticalGroup(
            jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 47, Short.MAX_VALUE)
            .addGroup(jPanel79Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel79Layout.createSequentialGroup()
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel31.add(jPanel79);

        jPanel80.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel80.setLayout(new java.awt.BorderLayout());

        jFormattedTextField3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Menge", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        jFormattedTextField3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel80.add(jFormattedTextField3, java.awt.BorderLayout.CENTER);

        jComboBox5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tonnen", "Kg.", "M", "M2", "M3", "St.", "Satz" }));
        jComboBox5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einh.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel80.add(jComboBox5, java.awt.BorderLayout.EAST);

        jPanel31.add(jPanel80);

        jPanel81.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jFormattedTextField2.setBackground(new java.awt.Color(255, 204, 204));
        jFormattedTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wunschliefertermin", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField2.setFormatterFactory(
            new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd.MM.yy"))
            )
        );
        jFormattedTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFormattedTextField2ActionPerformed(evt);
            }
        });
        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel81Layout = new javax.swing.GroupLayout(jPanel81);
        jPanel81.setLayout(jPanel81Layout);
        jPanel81Layout.setHorizontalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
            .addGroup(jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jFormattedTextField2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE))
        );
        jPanel81Layout.setVerticalGroup(
            jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 49, Short.MAX_VALUE)
            .addGroup(jPanel81Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel81Layout.createSequentialGroup()
                    .addComponent(jFormattedTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel31.add(jPanel81);

        jPanel1.add(jPanel31);

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setMaximumSize(new java.awt.Dimension(900, 150));
        jPanel4.setMinimumSize(new java.awt.Dimension(500, 50));
        jPanel4.setName(""); // NOI18N
        jPanel4.setPreferredSize(new java.awt.Dimension(800, 70));
        jPanel4.setRequestFocusEnabled(false);

        jScrollPane1.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(500, 82));
        jScrollPane1.setName(""); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(238, 70));

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(100);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Beschreibung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextArea1.setMaximumSize(new java.awt.Dimension(2000, 2000));
        jTextArea1.setMinimumSize(new java.awt.Dimension(232, 50));
        jTextArea1.setName(""); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 945, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout jPanel78Layout = new javax.swing.GroupLayout(jPanel78);
        jPanel78.setLayout(jPanel78Layout);
        jPanel78Layout.setHorizontalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel78Layout.setVerticalGroup(
            jPanel78Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel78.setVisible(false);

        jPanel73.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel73.setLayout(new java.awt.GridLayout(1, 4));

        jPanel74.setBackground(new java.awt.Color(204, 255, 204));
        jPanel74.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel74.setPreferredSize(new java.awt.Dimension(1000, 30));
        jPanel74.setLayout(new java.awt.BorderLayout());

        labelBestellung.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        labelBestellung.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelBestellung.setText("BESTELLUNG");
        jPanel74.add(labelBestellung, java.awt.BorderLayout.CENTER);

        statusBestellung.setBackground(new java.awt.Color(255, 0, 0));
        statusBestellung.setForeground(new java.awt.Color(255, 0, 102));
        statusBestellung.setText("        ");
        statusBestellung.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        statusBestellung.setOpaque(true);
        jPanel74.add(statusBestellung, java.awt.BorderLayout.EAST);

        jPanel73.add(jPanel74);

        jPanel75.setBackground(new java.awt.Color(204, 204, 255));
        jPanel75.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel75.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("EINKAUF");
        jPanel75.add(jLabel2, java.awt.BorderLayout.CENTER);

        statusEinkauf.setText("        ");
        statusEinkauf.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel75.add(statusEinkauf, java.awt.BorderLayout.EAST);

        jPanel73.add(jPanel75);

        jPanel76.setBackground(new java.awt.Color(255, 153, 153));
        jPanel76.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel76.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("BAUSTELLE");
        jPanel76.add(jLabel3, java.awt.BorderLayout.CENTER);

        statusBaustelle.setText("        ");
        statusBaustelle.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel76.add(statusBaustelle, java.awt.BorderLayout.EAST);

        jPanel73.add(jPanel76);

        jPanel77.setBackground(new java.awt.Color(255, 255, 204));
        jPanel77.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel77.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("CONTROLLING");
        jPanel77.add(jLabel4, java.awt.BorderLayout.CENTER);

        statusControlling.setText("        ");
        statusControlling.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel77.add(statusControlling, java.awt.BorderLayout.EAST);

        jPanel73.add(jPanel77);

        jPanel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel9.setAutoscrolls(true);
        jPanel9.setMaximumSize(new java.awt.Dimension(1000, 700));
        jPanel9.setMinimumSize(new java.awt.Dimension(600, 100));
        jPanel9.setPreferredSize(new java.awt.Dimension(900, 400));
        jPanel9.setRequestFocusEnabled(false);
        jPanel9.setVerifyInputWhenFocusTarget(false);
        jPanel9.setLayout(new java.awt.BorderLayout(1, 1));

        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setViewportBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jScrollPane2.setMaximumSize(new java.awt.Dimension(1200, 800));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(600, 160));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(900, 600));
        jScrollPane2.setViewportView(jPanel2);

        jPanel2.setAutoscrolls(true);
        jPanel2.setFocusCycleRoot(true);
        jPanel2.setFocusTraversalPolicyProvider(true);
        jPanel2.setMaximumSize(new java.awt.Dimension(1200, 1000));
        jPanel2.setMinimumSize(new java.awt.Dimension(500, 300));
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(600, 600));
        jPanel2.setLayout(new java.awt.GridLayout(12, 1, 1, 1));

        jPanel27.setLayout(new java.awt.GridLayout(1, 4, 1, 1));

        jPanel28.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel28.setLayout(new java.awt.BorderLayout());

        tFAgreementNR.setText("jTextField11");
        tFAgreementNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agreement/ Bestellung Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel28.add(tFAgreementNR, java.awt.BorderLayout.CENTER);

        jPanel27.add(jPanel28);

        jPanel29.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel29.setLayout(new javax.swing.BoxLayout(jPanel29, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preis", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField17.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField17MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField17MouseExited(evt);
            }
        });
        jFormattedTextField17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField17KeyPressed(evt);
            }
        });
        jPanel29.add(jFormattedTextField17);

        jPanel27.add(jPanel29);

        jPanel50.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel50.setLayout(new javax.swing.BoxLayout(jPanel50, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lieferung an GR erfolgt am", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField8.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField8KeyPressed(evt);
            }
        });
        jPanel50.add(jFormattedTextField8);

        jPanel27.add(jPanel50);

        jPanel22.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.LINE_AXIS));

        jTextField17.setText("jTextField17");
        jTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RG-Nummer an AG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel22.add(jTextField17);

        jPanel27.add(jPanel22);

        jPanel2.add(jPanel27);

        jPanel5.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel6.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datum der Anforderung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField1.setToolTipText("Datum in Format dd.mm.yy");
        jFormattedTextField1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPanel6.add(jFormattedTextField1);

        jPanel5.add(jPanel6);

        jPanel7.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel7.setLayout(new javax.swing.BoxLayout(jPanel7, javax.swing.BoxLayout.LINE_AXIS));

        lieferant_tFKommentar01.setText("jTextField13");
        lieferant_tFKommentar01.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lieferant", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel7.add(lieferant_tFKommentar01);

        jPanel5.add(jPanel7);

        jPanel51.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel51.setLayout(new javax.swing.BoxLayout(jPanel51, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang erfolgt am", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField9.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField9KeyPressed(evt);
            }
        });
        jPanel51.add(jFormattedTextField9);

        jPanel5.add(jPanel51);

        jPanel25.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel25.setLayout(new javax.swing.BoxLayout(jPanel25, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datum", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField10.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField10KeyPressed(evt);
            }
        });
        jPanel25.add(jFormattedTextField10);

        jPanel5.add(jPanel25);

        jPanel2.add(jPanel5);

        jPanel3.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel53.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel53.setLayout(new javax.swing.BoxLayout(jPanel53, javax.swing.BoxLayout.LINE_AXIS));

        tFAnforderungNR.setText("jTextField12");
        tFAnforderungNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Anforderung Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel53.add(tFAnforderungNR);

        jPanel3.add(jPanel53);

        jPanel54.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellt am (dd.mm.yy)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel54.setLayout(new javax.swing.BoxLayout(jPanel54, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField5.setFormatterFactory(
            new javax.swing.text.DefaultFormatterFactory(
                new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd.MM.yy"))
            )
        );
        jFormattedTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField5KeyPressed(evt);
            }
        });
        jPanel54.add(jFormattedTextField5);

        jPanel3.add(jPanel54);

        jPanel55.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel55.setLayout(new javax.swing.BoxLayout(jPanel55, javax.swing.BoxLayout.LINE_AXIS));

        jTextField5.setText("jTextField5");
        jTextField5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kommentar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel55.add(jTextField5);

        jPanel3.add(jPanel55);

        jPanel33.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel33.setLayout(new javax.swing.BoxLayout(jPanel33, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Weitergerechnet an AG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField11.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter()));
        jFormattedTextField11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField11MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField11MouseExited(evt);
            }

            private void jFormattedTextField11MouseEntered(MouseEvent evt) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            private void jFormattedTextField11MouseExited(MouseEvent evt) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
        jFormattedTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField11KeyPressed(evt);
            }

            private void jFormattedTextField11KeyPressed(KeyEvent evt) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
        jPanel33.add(jFormattedTextField11);

        jPanel3.add(jPanel33);

        jPanel2.add(jPanel3);

        jPanel12.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel13.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellt am (dd.mm.yy)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField4.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField4KeyPressed(evt);
            }

            private void jFormattedTextField4KeyPressed(KeyEvent evt) {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
        jPanel13.add(jFormattedTextField4);

        jPanel12.add(jPanel13);

        jPanel14.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jTextField4.setText("jTextField4");
        jTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellungsnummer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel14.add(jTextField4, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel14);

        jPanel56.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel56.setLayout(new javax.swing.BoxLayout(jPanel56, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Gewicht", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField15.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField15KeyPressed(evt);
            }
        });
        jPanel56.add(jFormattedTextField15);

        jComboBox9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tonnen", "Kg", "" }));
        jComboBox9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einh.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel56.add(jComboBox9);

        jPanel12.add(jPanel56);

        jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Angebotssumme weitergerechnet", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField12.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField12MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField12MouseExited(evt);
            }
        });
        jFormattedTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField12KeyPressed(evt);
            }
        });
        jPanel10.add(jFormattedTextField12);

        jPanel12.add(jPanel10);

        jPanel2.add(jPanel12);

        jPanel15.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel15.setLayout(new java.awt.GridLayout(1, 3));

        jPanel16.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, null));
        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Summe für Material und Leistungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField16.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField16MouseExited(evt);
            }
             public void mousePressed(java.awt.event.MouseEvent evt) {
                jFormattedTextField16MousePressed(evt);
            }

            private void jFormattedTextField16MousePressed(MouseEvent evt) {
              //  throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });
        jFormattedTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField16KeyPressed(evt);
            }
        });
        jPanel16.add(jFormattedTextField16);

        jPanel15.add(jPanel16);

        jPanel17.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS nach Plan (dd.mm.yy)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField6.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField6KeyPressed(evt);
            }
        });
        jPanel17.add(jFormattedTextField6);

        jPanel15.add(jPanel17);

        jPanel36.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel36.setLayout(new javax.swing.BoxLayout(jPanel36, javax.swing.BoxLayout.LINE_AXIS));

        jComboBox10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "CM", "MM" }));
        jComboBox10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einh.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        jPanel36.add(jComboBox10);

        jFormattedTextField18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Länge", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField18.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jPanel36.add(jFormattedTextField18);

        jPanel15.add(jPanel36);

        jPanel42.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel42.setLayout(new javax.swing.BoxLayout(jPanel42, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Angebotssumme nicht weitergerechnet", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField13.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField13MouseExited(evt);
            }
        });
        jFormattedTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField13KeyPressed(evt);
            }
        });
        jPanel42.add(jFormattedTextField13);

        jPanel15.add(jPanel42);

        jPanel2.add(jPanel15);

        jPanel18.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel18.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel19.setBackground(new java.awt.Color(204, 255, 204));
        jPanel19.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(0, 153, 102), new java.awt.Color(0, 153, 102), null, new java.awt.Color(0, 153, 102)));
        jPanel19.setLayout(new java.awt.BorderLayout());
        jPanel19.add(jLabel1, java.awt.BorderLayout.CENTER);

        jPanel18.add(jPanel19);

        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS erfolgt am", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField7.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(java.text.DateFormat.getDateInstance(java.text.DateFormat.SHORT))));
        jFormattedTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField7KeyPressed(evt);
            }
        });
        jPanel20.add(jFormattedTextField7);

        jPanel18.add(jPanel20);

        jPanel37.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel37.setLayout(new javax.swing.BoxLayout(jPanel37, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Breite", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField20.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jPanel37.add(jFormattedTextField20);

        jPanel18.add(jPanel37);

        jPanel46.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 255, 0), new java.awt.Color(255, 255, 0), null, null));
        jPanel46.setLayout(new javax.swing.BoxLayout(jPanel46, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kunden in Rechnung stellen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField14.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jFormattedTextField14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jFormattedTextField14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jFormattedTextField14MouseExited(evt);
            }
        });
        jFormattedTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jFormattedTextField14KeyPressed(evt);
            }
        });
        jPanel46.add(jFormattedTextField14);

        jPanel18.add(jPanel46);

        jPanel2.add(jPanel18);

        jPanel21.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel21.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel61.setBackground(new java.awt.Color(204, 255, 204));
        jPanel61.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel61Layout = new javax.swing.GroupLayout(jPanel61);
        jPanel61.setLayout(jPanel61Layout);
        jPanel61Layout.setHorizontalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel61Layout.setVerticalGroup(
            jPanel61Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        jPanel21.add(jPanel61);

        jPanel23.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel23.setLayout(new javax.swing.BoxLayout(jPanel23, javax.swing.BoxLayout.LINE_AXIS));

        jTextField2.setText("jTextField2");
        jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel23.add(jTextField2);

        jPanel21.add(jPanel23);

        jPanel38.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel38.setLayout(new javax.swing.BoxLayout(jPanel38, javax.swing.BoxLayout.LINE_AXIS));

        jFormattedTextField19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Höhe", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField19.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0.00"))));
        jPanel38.add(jFormattedTextField19);

        jPanel21.add(jPanel38);

        jPanel62.setBackground(new java.awt.Color(255, 255, 204));
        jPanel62.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel62Layout = new javax.swing.GroupLayout(jPanel62);
        jPanel62.setLayout(jPanel62Layout);
        jPanel62Layout.setHorizontalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel62Layout.setVerticalGroup(
            jPanel62Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        jPanel21.add(jPanel62);

        jPanel2.add(jPanel21);

        jPanel24.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel24.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel63.setBackground(new java.awt.Color(204, 255, 204));
        jPanel63.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel63Layout = new javax.swing.GroupLayout(jPanel63);
        jPanel63.setLayout(jPanel63Layout);
        jPanel63Layout.setHorizontalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel63Layout.setVerticalGroup(
            jPanel63Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        jPanel24.add(jPanel63);

        jPanel26.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel26.setLayout(new javax.swing.BoxLayout(jPanel26, javax.swing.BoxLayout.LINE_AXIS));

        jTextField6.setText("jTextField6");
        jTextField6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Auslieferung nach Plan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel26.add(jTextField6);

        jPanel24.add(jPanel26);

        jPanel35.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel35.setLayout(new javax.swing.BoxLayout(jPanel35, javax.swing.BoxLayout.LINE_AXIS));

        jTextField14.setText("jTextField14");
        jTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kolli", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel35.add(jTextField14);

        jPanel24.add(jPanel35);

        jPanel64.setBackground(new java.awt.Color(255, 255, 204));
        jPanel64.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel64Layout = new javax.swing.GroupLayout(jPanel64);
        jPanel64.setLayout(jPanel64Layout);
        jPanel64Layout.setHorizontalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel64Layout.setVerticalGroup(
            jPanel64Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        jPanel24.add(jPanel64);

        jPanel2.add(jPanel24);

        jPanel32.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel32.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel65.setBackground(new java.awt.Color(204, 255, 204));
        jPanel65.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel65Layout = new javax.swing.GroupLayout(jPanel65);
        jPanel65.setLayout(jPanel65Layout);
        jPanel65Layout.setHorizontalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel65Layout.setVerticalGroup(
            jPanel65Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        jPanel32.add(jPanel65);

        jPanel34.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel34.setLayout(new javax.swing.BoxLayout(jPanel34, javax.swing.BoxLayout.LINE_AXIS));

        jTextField8.setText("jTextField8");
        jTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Zahlungsbedingungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel34.add(jTextField8);

        jPanel32.add(jPanel34);

        jPanel39.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 153, 153), new java.awt.Color(255, 153, 153), null, null));
        jPanel39.setLayout(new javax.swing.BoxLayout(jPanel39, javax.swing.BoxLayout.LINE_AXIS));

        jTextField15.setText("jTextField15");
        jTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel39.add(jTextField15);

        jPanel32.add(jPanel39);

        jPanel66.setBackground(new java.awt.Color(255, 255, 204));
        jPanel66.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel66Layout = new javax.swing.GroupLayout(jPanel66);
        jPanel66.setLayout(jPanel66Layout);
        jPanel66Layout.setHorizontalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel66Layout.setVerticalGroup(
            jPanel66Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        jPanel32.add(jPanel66);

        jPanel2.add(jPanel32);

        jPanel45.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel45.setLayout(new java.awt.GridLayout(1, 3, 1, 1));

        jPanel71.setBackground(new java.awt.Color(204, 255, 204));
        jPanel71.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel71Layout = new javax.swing.GroupLayout(jPanel71);
        jPanel71.setLayout(jPanel71Layout);
        jPanel71Layout.setHorizontalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel71Layout.setVerticalGroup(
            jPanel71Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        jPanel45.add(jPanel71);

        jPanel47.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(204, 204, 255), new java.awt.Color(204, 204, 255), null, null));
        jPanel47.setLayout(new javax.swing.BoxLayout(jPanel47, javax.swing.BoxLayout.LINE_AXIS));

        jTextField9.setText("jTextField9");
        jTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kommentar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel47.add(jTextField9);
  jTextField9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
          
                }
            }
        });
        jPanel45.add(jPanel47);

        jPanel48.setBackground(new java.awt.Color(255, 153, 153));
        jPanel48.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel48.setLayout(new javax.swing.BoxLayout(jPanel48, javax.swing.BoxLayout.LINE_AXIS));

        jTextField16.setText("jTextField16");
        jTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Container", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jPanel48.add(jTextField16);

        jPanel45.add(jPanel48);

        jPanel72.setBackground(new java.awt.Color(255, 255, 204));
        jPanel72.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel72Layout = new javax.swing.GroupLayout(jPanel72);
        jPanel72.setLayout(jPanel72Layout);
        jPanel72Layout.setHorizontalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        jPanel72Layout.setVerticalGroup(
            jPanel72Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 37, Short.MAX_VALUE)
        );

        jPanel45.add(jPanel72);

        jPanel2.add(jPanel45);

        jScrollPane2.setViewportView(jPanel2);

        jPanel9.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jMenuBar1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jMenu1.setText("File");

        jMenuItem1.setText("Speichern in DB");
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

      
        jMenu2.setText("Edit");

        jMenuItem2.setText("DB-Options");
        jMenuItem2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        jMenuItem2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenuItem2KeyPressed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Neue Lieferung eintragen");
        jMenuItem3.addMenuKeyListener(new javax.swing.event.MenuKeyListener() {
            public void menuKeyPressed(javax.swing.event.MenuKeyEvent evt) {
                jMenuItem3MenuKeyPressed(evt);
            }
            public void menuKeyReleased(javax.swing.event.MenuKeyEvent evt) {
            }
            public void menuKeyTyped(javax.swing.event.MenuKeyEvent evt) {
            }
        });
        jMenuItem3.addMenuDragMouseListener(new javax.swing.event.MenuDragMouseListener() {
            public void menuDragMouseDragged(javax.swing.event.MenuDragMouseEvent evt) {
            }
            public void menuDragMouseEntered(javax.swing.event.MenuDragMouseEvent evt) {
                jMenuItem3MenuDragMouseEntered(evt);
            }
            public void menuDragMouseExited(javax.swing.event.MenuDragMouseEvent evt) {
                jMenuItem3MenuDragMouseExited(evt);
            }
            public void menuDragMouseReleased(javax.swing.event.MenuDragMouseEvent evt) {
            }
        });
       // jMenu2.add(jMenuItem3);
       

          jMenu3.setText("Löschen");
          jMenuItem4.setText("aus DB entfernen");
        jMenuItem4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jMenuItem2MouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jMenuItem2MousePressed(evt);
            }
        });
        jMenuItem4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jMenuItem2KeyPressed(evt);
            }
        });
        jMenu3.add(jMenuItem4);
        
          
           jMenuBar1.add(jMenu1);
          jMenuBar1.add(jMenu2);
         jMenuBar1.add(jMenu3);
        

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 963, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 963, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel73, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel78, 0, 0, 0)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel73, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        
    
    
    
    
    private void jMenuItem1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1MouseEntered

    private void jMenuItem1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1MouseExited




    private void jMenuItem1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem1MousePressed
        // dbManager уже инициализирован в конструкторе 
      String gruppe =  person.getZugangsGruppe();
      
      
     
      
            lieferung.setNeu(jCheckBox2.isSelected());
            lieferung.setAktiv(jCheckBox3.isSelected());
            lieferung.setUnterschriftMH(jCheckBox4.isSelected());
            lieferung.setUnterschriftKunde(jCheckBox5.isSelected());
            lieferung.setAnEinkaufWeiterGeleitetMH(jCheckBox6.isSelected());
            lieferung.setInvest_Leasing(jCheckBox7.isSelected());
            lieferung.setDe_eu((String) jComboBox1.getSelectedItem());
          //   System.out.println("MENGE  vor Update: " + lieferung.getMenge());
          if(jFormattedTextField3.getValue() != null){
              try {
                  Object value = jFormattedTextField3.getValue();
                  if (value instanceof Number) {
                      lieferung.setMenge(((Number) value).floatValue());
                  } else {
                      lieferung.setMenge(Float.parseFloat(value.toString()));
                  }
                //  System.out.println("Menge gesetzt: " + lieferung.getMenge());
              } catch (Exception e) {
                  System.err.println("Fehler beim Parsen der Menge: " + e.getMessage());
                  lieferung.setMenge(0.0f);
              }
          } else {
              System.out.println("jFormattedTextField3.getValue() (Menge)  ist null");
              lieferung.setMenge(0.0f);
          }
            
               lieferung.setEinhMenge((String) jComboBox5.getSelectedItem());
            if(jFormattedTextField2.getText() != null || !jFormattedTextField2.getText().trim().isEmpty()){
                  lieferung.setWunschLieferDatum(jFormattedTextField2.getText());
              
            }else{
                lieferung.setWunschLieferDatum(null);
             
            }
          if (persistBeschreibungFromTextArea) {
              String beschreibungText = jTextArea1.getText() != null ? jTextArea1.getText().trim() : "";
              if (!beschreibungText.isEmpty()) {
                  lieferung.setBeschreibung(beschreibungText);
              } else {
                  lieferung.setBeschreibung(null);
              }
          }
          // Bereich der Änderung an ersten Spalte ######################################################################################
          // AnforderungDatum soll fuer alle Rollen gespeichert werden, wenn Feld geaendert ist.
          String anforderungDatumText = jFormattedTextField1.getText() != null ? jFormattedTextField1.getText().trim() : "";
          if (!anforderungDatumText.isEmpty()) {
              lieferung.setAnforderungDatum(anforderungDatumText);
          } else {
              lieferung.setAnforderungDatum(null);
          }
          
          String banfNrText = tFAnforderungNR.getText() != null ? tFAnforderungNR.getText().trim() : "";
          if (!banfNrText.isEmpty()) {
              lieferung.setAgrNummer(banfNrText);
          } else {
              lieferung.setAgrNummer(null);
          }

          if(gruppe.equalsIgnoreCase("Admin") || person.getZugangsGruppe().equalsIgnoreCase("Bestellung")){
                 System.out.println("PERSONINFO: " + person.getName() + "   "+ person.getZugangsGruppe() + "  Zugangsgruppe  " + gruppe);

            if(tFAgreementNR.getText() != null || !tFAgreementNR.getText().trim().isEmpty()){
                 lieferung.setAnfordNummer(tFAgreementNR.getText());
            }
                 if(jFormattedTextField16.getValue() != null){
                try {
                    Object value = jFormattedTextField16.getValue();
                    if (value instanceof Number) {
                        lieferung.setSummeMat_Leist_cents(((Number) value).floatValue());
                    } else {
                        lieferung.setSummeMat_Leist_cents(Float.parseFloat(value.toString()));
                    }
                  
                } catch (Exception e) {
                    System.err.println("Fehler beim Parsen der SummeMat_Leist_cents: " + e.getMessage());
                    lieferung.setSummeMat_Leist_cents(0.0f);
                }
            } else {
                lieferung.setSummeMat_Leist_cents(0.0f);
            }
             if(jTextField1.getText() != null || !jTextField1.getText().trim().isEmpty()){
                lieferung.setAnfordPerson(jTextField1.getText());
             }    
                 
                 
          } /*else {
              // Для пользователей без прав Admin/Bestellung устанавливаем имя текущего пользователя
              lieferung.setAnfordPerson(person.getName());
          }
        */
          //###########################   Einkauf  ######################################################################################################
          //________________________________________________________________________________________________________   
              if(gruppe.equalsIgnoreCase("Einkauf")|| gruppe.equalsIgnoreCase("Admin")){
           
              if(jFormattedTextField17.getValue() != null){
                  try {
                      Object value = jFormattedTextField17.getValue();
                      if (value instanceof Number) {
                          lieferung.setPreisEinkauf(((Number) value).floatValue());
                      } else {
                          lieferung.setPreisEinkauf(Float.parseFloat(value.toString()));
                      }
                     // System.out.println("PreisEinkauf gesetzt: " + lieferung.getPreisEinkauf());
                  } catch (Exception e) {
                      System.err.println("Fehler beim Setzen des PreisEinkauf: " + e.getMessage());
                      lieferung.setPreisEinkauf(0.0f);
                  }
              } else {
                  lieferung.setPreisEinkauf(0.0f);
              }
              
           if(lieferant_tFKommentar01.getText() != null || !lieferant_tFKommentar01.getText().trim().isEmpty()){
                lieferung.setLieferantName( lieferant_tFKommentar01.getText());
           }
           
           if(jFormattedTextField5.getText() != null && !jFormattedTextField5.getText().trim().isEmpty()){
                 lieferung.setBestelltAm(jFormattedTextField5.getText().trim());
          
           }
           if(jTextField4.getText() != null || !jTextField4.getText().trim().isEmpty()){
                lieferung.setBestellNummer(jTextField4.getText());
            
           }
           
           
            if(jFormattedTextField6.getText() != null || !jFormattedTextField6.getText().trim().isEmpty()){
                lieferung.setWareneingangTS_plan(jFormattedTextField6.getText());
             
             }
            
              if(jFormattedTextField7.getText() != null || !jFormattedTextField7.getText().trim().isEmpty()){
                    lieferung.setWareneingangTS_Fakt(jFormattedTextField7.getText());
           
              }
              if(jTextField2.getText() != null || !jTextField2.getText().trim().isEmpty()){
                    lieferung.setWarenEingangTS(jTextField2.getText());
         
              }
            if(jFormattedTextField4.getText() != null || !jFormattedTextField4.getText().trim().isEmpty()){
             lieferung.setAuslieferungNachPlan(jFormattedTextField4.getText());
               
            }
             if(jTextField8.getText() != null || !jTextField8.getText().trim().isEmpty()){
                   lieferung.setZahlungsbedingungen(jTextField8.getText());
             }
           
           if(jTextField9.getText() != null || !jTextField9.getText().trim().isEmpty()){
                 lieferung.setKommentEinkauf(jTextField9.getText());
           
           }
       }  
              
           //__________________________   Baustelle  _____________________________________________
          
            if(gruppe.equalsIgnoreCase("Baustelle")|| gruppe.equalsIgnoreCase("Admin")){
                  if(jFormattedTextField8.getText() != null ){
           // if(jFormattedTextField8.getText() != null && !jFormattedTextField8.getText().trim().isEmpty()){
               //  System.out.println("Lieferung an BS erfolgt am : " + jFormattedTextField8.getValue());
        // lieferung.setWunschterminWareneingang(jFormattedTextField8.getText() != null ? jFormattedTextField8.getText().trim() : "");
         lieferung.setWunschterminWareneingang(jFormattedTextField8.getText());
            }else{
              //  System.out.println("-----------------------");
            }
           if(jFormattedTextField9.getText() != null || !jFormattedTextField9.getText().trim().isEmpty()){
                 lieferung.setWareneingang_Fakt(jFormattedTextField9.getText());
           }
         if(jTextField5.getText() != null || !jTextField5.getText().trim().isEmpty()){
               lieferung.setKommentarBS(jTextField5.getText());
          
         }
           if(jFormattedTextField15.getValue() != null){
                  try {
                      Object value = jFormattedTextField15.getValue();
                      if (value instanceof Number) {
                          lieferung.setGewicht(((Number) value).floatValue());
                      } else {
                          lieferung.setGewicht(Float.parseFloat(value.toString()));
                      }
                    //  System.out.println("Gewicht gesetzt: " + lieferung.getGewicht());
                  } catch (Exception e) {
                      System.err.println("Fehler beim Setzen des Gewicht: " + e.getMessage());
                      lieferung.setGewicht(0.0f);
                  }
             } else {
                 lieferung.setGewicht(0.0f);
             }
             if(jComboBox9.getSelectedItem() != null){
                 lieferung.setEinhMasse((String) jComboBox9.getSelectedItem());
           
             }
             if(jComboBox10.getSelectedItem() != null){
                 lieferung.setEinheitLBH((String) jComboBox10.getSelectedItem());
              
             }
              if(jFormattedTextField18.getValue() != null){
                  try {
                      Object value = jFormattedTextField18.getValue();
                      if (value instanceof Number) {
                          lieferung.setLaenge(((Number) value).floatValue());
                      } else {
                          lieferung.setLaenge(Float.parseFloat(value.toString()));
                      }
                     // System.out.println("Laenge gesetzt: " + lieferung.getLaenge());
                  } catch (Exception e) {
                      System.err.println("Fehler beim Setzen der Laenge: " + e.getMessage());
                      lieferung.setLaenge(0.0f);
                  }
              } else {
                  lieferung.setLaenge(0.0f);
              }
                if(jFormattedTextField20.getValue() != null){
                      try {
                          Object value = jFormattedTextField20.getValue();
                          if (value instanceof Number) {
                              lieferung.setBreite(((Number) value).floatValue());
                          } else {
                              lieferung.setBreite(Float.parseFloat(value.toString()));
                          }
                         // System.out.println("Breite gesetzt: " + lieferung.getBreite());
                      } catch (Exception e) {
                          System.err.println("Fehler beim Setzen der Breite: " + e.getMessage());
                          lieferung.setBreite(0.0f);
                      }
                } else {
                    lieferung.setBreite(0.0f);
                }
           
                  if(jFormattedTextField19.getValue() != null){
                      try {
                          Object value = jFormattedTextField19.getValue();
                          if (value instanceof Number) {
                              lieferung.setHoehe(((Number) value).floatValue());
                          } else {
                              lieferung.setHoehe(Float.parseFloat(value.toString()));
                          }
                         // System.out.println("Hoehe gesetzt: " + lieferung.getHoehe());
                      } catch (Exception e) {
                          System.err.println("Fehler beim Setzen der Hoehe: " + e.getMessage());
                          lieferung.setHoehe(0.0f);
                      }
                  } else {
                      lieferung.setHoehe(0.0f);
                  }
                    // Kolli всегда сохраняем из поля (как и остальные текстовые поля)
                    lieferung.setKolli(jTextField14.getText() != null ? jTextField14.getText() : "");
                      if(jTextField15.getText() != null || !jTextField15.getText().trim().isEmpty()){
                        lieferung.setPackListe(jTextField15.getText());
             
                      }
                        if(jTextField16.getText() != null || !jTextField16.getText().trim().isEmpty()){
                          lieferung.setContainerNr(jTextField16.getText());
                
                        }
       }
            
             if(gruppe.equals("Baustelle") || gruppe.equals("Einkauf")|| gruppe.equals("Admin" )){
           if(jFormattedTextField15.getValue() != null){
                  try {
                      Object value = jFormattedTextField15.getValue();
                      if (value instanceof Number) {
                          lieferung.setGewicht(((Number) value).floatValue());
                      } else {
                          lieferung.setGewicht(Float.parseFloat(value.toString()));
                      }
                    //  System.out.println("Gewicht gesetzt: " + lieferung.getGewicht());
                  } catch (Exception e) {
                      System.err.println("Fehler beim Setzen des Gewicht: " + e.getMessage());
                      lieferung.setGewicht(0.0f);
                  }
             } else {
                 lieferung.setGewicht(0.0f);
             }
             if(jComboBox9.getSelectedItem() != null){
                 lieferung.setEinhMasse((String) jComboBox9.getSelectedItem());
           
             }
             if(jComboBox10.getSelectedItem() != null){
                 lieferung.setEinheitLBH((String) jComboBox10.getSelectedItem());
              
             }
              if(jFormattedTextField18.getValue() != null){
                  try {
                      Object value = jFormattedTextField18.getValue();
                      if (value instanceof Number) {
                          lieferung.setLaenge(((Number) value).floatValue());
                      } else {
                          lieferung.setLaenge(Float.parseFloat(value.toString()));
                      }
                     // System.out.println("Laenge gesetzt: " + lieferung.getLaenge());
                  } catch (Exception e) {
                      System.err.println("Fehler beim Setzen der Laenge: " + e.getMessage());
                      lieferung.setLaenge(0.0f);
                  }
              } else {
                  lieferung.setLaenge(0.0f);
              }
                if(jFormattedTextField20.getValue() != null){
                      try {
                          Object value = jFormattedTextField20.getValue();
                          if (value instanceof Number) {
                              lieferung.setBreite(((Number) value).floatValue());
                          } else {
                              lieferung.setBreite(Float.parseFloat(value.toString()));
                          }
                         // System.out.println("Breite gesetzt: " + lieferung.getBreite());
                      } catch (Exception e) {
                          System.err.println("Fehler beim Setzen der Breite: " + e.getMessage());
                          lieferung.setBreite(0.0f);
                      }
                } else {
                    lieferung.setBreite(0.0f);
                }
           
                  if(jFormattedTextField19.getValue() != null){
                      try {
                          Object value = jFormattedTextField19.getValue();
                          if (value instanceof Number) {
                              lieferung.setHoehe(((Number) value).floatValue());
                          } else {
                              lieferung.setHoehe(Float.parseFloat(value.toString()));
                          }
                         // System.out.println("Hoehe gesetzt: " + lieferung.getHoehe());
                      } catch (Exception e) {
                          System.err.println("Fehler beim Setzen der Hoehe: " + e.getMessage());
                          lieferung.setHoehe(0.0f);
                      }
                  } else {
                      lieferung.setHoehe(0.0f);
                  }
                    // Kolli всегда сохраняем из поля (как и остальные текстовые поля)
                    lieferung.setKolli(jTextField14.getText() != null ? jTextField14.getText() : "");
                      if(jTextField15.getText() != null || !jTextField15.getText().trim().isEmpty()){
                        lieferung.setPackListe(jTextField15.getText());
             
                      }
                        if(jTextField16.getText() != null || !jTextField16.getText().trim().isEmpty()){
                          lieferung.setContainerNr(jTextField16.getText());
                
                        }
       }
          
             
                        //____________________   Controlling  ________________________________
               
                        
                         if(gruppe.equalsIgnoreCase("Control")|| gruppe.equalsIgnoreCase("Admin")){
             if(jTextField17.getText() != null || !jTextField17.getText().trim().isEmpty()) {
                              lieferung.setRgNummeranAG(jTextField17.getText());
                
                       }
                       
                     
                  if(jFormattedTextField10.getText() != null || !jFormattedTextField10.getText().trim().isEmpty()){
                         lieferung.setDatumBS(jFormattedTextField10.getText());
                       }
                       
                  try{
                       lieferung.setIstWeitergerechnetAnAG(jCheckBox1.isSelected());
                  }catch(Exception e){
                    //  System.out.println("Weitergerechnt an AG <CheckBox> hat ein Problem!!!!!#####################");
                  }
           
            
              if(jFormattedTextField12.getValue() != null){
                  try {
                      Object value = jFormattedTextField12.getValue();
                      if (value instanceof Number) {
                          lieferung.setSummeWeitergerechnetanAG_cents(((Number) value).floatValue());
                      } else {
                          lieferung.setSummeWeitergerechnetanAG_cents(Float.parseFloat(value.toString()));
                      }
                    //  System.out.println("SummeWeitergerechnetanAG_cents gesetzt: " + lieferung.getSummeWeitergerechnetanAG_cents());
                  } catch (Exception e) {
                    //  System.err.println("Fehler beim Setzen der SummeWeitergerechnetanAG_cents: " + e.getMessage());
                      lieferung.setSummeWeitergerechnetanAG_cents(0.0f);
                  }
              } else {
                  lieferung.setSummeWeitergerechnetanAG_cents(0.0f);
              }
                if(jFormattedTextField13.getValue() != null ){
                    try {
                        Object value = jFormattedTextField13.getValue();
                        if (value instanceof Number) {
                            lieferung.setSumme_nach_Weiterrechnung(((Number) value).floatValue());
                        } else {
                            lieferung.setSumme_nach_Weiterrechnung(Float.parseFloat(value.toString()));
                        }
                      //  System.out.println("Summe_nach_Weiterrechnung gesetzt: " + lieferung.getSumme_nach_Weiterrechnung());
                    } catch (Exception e) {
                      //  System.err.println("Fehler beim Setzen der Summe_nach_Weiterrechnung: " + e.getMessage());
                        lieferung.setSumme_nach_Weiterrechnung(0.0f);
                    }
                } else {
                    lieferung.setSumme_nach_Weiterrechnung(0.0f);
                }
                 if(jFormattedTextField14.getValue() != null){
                     try {
                         Object value = jFormattedTextField14.getValue();
                         if (value instanceof Number) {
                             lieferung.setKundenInRechnungStellen(((Number) value).floatValue());
                         } else {
                             lieferung.setKundenInRechnungStellen(Float.parseFloat(value.toString()));
                         }
                       //  System.out.println("KundenInRechnungStellen gesetzt: " + lieferung.getKundenInRechnungStellen());
                     } catch (Exception e) {
                       //  System.err.println("Fehler beim Setzen der KundenInRechnungStellen: " + e.getMessage());
                         lieferung.setKundenInRechnungStellen(0.0f);
                     }
                 } else {
                     lieferung.setKundenInRechnungStellen(0.0f);
                 }
          
       }
                     
         // Проверяем изменения перед сохранением
         if (!hasChanges()) {
             System.out.println("Speichern vom Benutzer abgebrochen");
             return;
         }
         
         afterFormAppliedToLieferung();
         
         // Диагностика перед сохранением
        /*System.out.println("=== DIAGNOSTIK VOR SPEICHERN ===");
         System.out.println("Lieferung ID: " + lieferung.getId());
         System.out.println("Lieferung ist neu: " + lieferung.isNeu());
         System.out.println("Menge: " + lieferung.getMenge());
         System.out.println("Beschreibung: " + lieferung.getBeschreibung());
         System.out.println("AgrNummer: " + lieferung.getAgrNummer());
         System.out.println("AnfordNummer: " + lieferung.getAnfordNummer());
         System.out.println("================================");
         */ 
         // Флаги постобработки после успешного сохранения поставки.
         boolean wareneingangTsFaktWasSetNow = wasWareneingangTsFaktSetNow();
         boolean lieferungAnBsFilled = isLieferungAnBsFilled();
         try {
             boolean success;
             if(lieferung.isNeu()){
                 System.out.println("Versuche neue Lieferung zu speichern...");
                 success = dbManager.addInTabLif(lieferung);
                 if(success) {
                     System.out.println("Neue Lieferung ist erfolgreich gespeichert in DB");
                 } else {
                     System.err.println("Fehler beim Speichern der neuen Lieferung in DB");
                 }
             } else {
                 System.out.println("Versuche bestehende Lieferung zu aktualisieren...");
                 success = dbManager.updateTabLieferungen(lieferung);
                 if(success) {
                     System.out.println("Lieferung wurde erfolgreich geändert und gespeichert in DB");
                 } else {
                     System.err.println("Fehler beim Aktualisieren der Lieferung in DB");
                 }
             }

             if (success && wareneingangTsFaktWasSetNow) {
                 syncGelieferteMengeInMhWithMengeForAnforderung();
             }
             if (success && lieferungAnBsFilled) {
                 syncGeliefertAnBsWithMengeForAnforderung();
             }
         } catch (Exception e) {
             System.err.println("Fehler beim Speichern in DB: " + e.getMessage());
             e.printStackTrace();
         }
       
         
      
       
    }//GEN-LAST:event_jMenuItem1MousePressed

    private boolean wasWareneingangTsFaktSetNow() {
        String oldValue = originalLieferung != null && originalLieferung.getWareneingangTS_Fakt() != null
                ? originalLieferung.getWareneingangTS_Fakt().trim()
                : "";
        String newValue = jFormattedTextField7.getText() != null
                ? jFormattedTextField7.getText().trim()
                : "";
        return oldValue.isEmpty() && !newValue.isEmpty();
    }

    private void syncGelieferteMengeInMhWithMengeForAnforderung() {
        ArrayList<Artikel> artikelList = getArtikelForCurrentAnforderung();
        if (artikelList == null || artikelList.isEmpty()) return;

        // При подтверждении прихода в MH проставляем фактическую доставку = заказанному количеству.
        for (Artikel artikel : artikelList) {
            if (artikel != null) {
                artikel.setMenge_Baustelle_gel(artikel.getMenge());
            }
        }
        dbManager.updateArtikelList(artikelList);
    }

    private boolean isLieferungAnBsFilled() {
        String value = jFormattedTextField8.getText() != null
                ? jFormattedTextField8.getText().trim()
                : "";
        return !value.isEmpty();
    }

    private void syncGeliefertAnBsWithMengeForAnforderung() {
        ArrayList<Artikel> artikelList = getArtikelForCurrentAnforderung();
        if (artikelList == null || artikelList.isEmpty()) return;

        // При наличии даты доставки на стройку проставляем delivered-to-site = заказанному количеству.
        for (Artikel artikel : artikelList) {
            if (artikel != null) {
                artikel.setMenge_gelief_an_BS(artikel.getMenge());
            }
        }
        dbManager.updateArtikelList(artikelList);
    }

    private ArrayList<Artikel> getArtikelForCurrentAnforderung() {
        String anforderung = lieferung != null && lieferung.getAnfordNummer() != null
                ? lieferung.getAnfordNummer().trim()
                : "";
        if (anforderung.isEmpty()) {
            return null;
        }

        ArrayList<Artikel> artikelList = dbManager.getArtikelByAnford(anforderung);
        if (artikelList == null || artikelList.isEmpty()) {
            return null;
        }
        return artikelList;
    }

    private void jMenuItem2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2MouseEntered

    private void jMenuItem2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem2MouseExited

    private void jMenuItem2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuItem2MousePressed
      showProjectNameEditor(lieferung);
        

    }//GEN-LAST:event_jMenuItem2MousePressed

    private void jMenuItem2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenuItem2KeyPressed
      
    }//GEN-LAST:event_jMenuItem2KeyPressed

    private void jFormattedTextField8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField8KeyPressed

    private void jFormattedTextField9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField9KeyPressed

    private void jFormattedTextField5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField5KeyPressed

    private void jFormattedTextField6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField6KeyPressed

    private void jFormattedTextField15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField15KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField15KeyPressed

    private void jFormattedTextField7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField7KeyPressed

    private void jFormattedTextField12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField12KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField12KeyPressed

    private void jFormattedTextField14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField14KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField14KeyPressed

    private void jFormattedTextField13KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField13KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField13KeyPressed

    private void jFormattedTextField10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField10KeyPressed

    private void jFormattedTextField16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField16KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField16KeyPressed

    private void jFormattedTextField17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField17KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField17KeyPressed

    private void jFormattedTextField17MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField17MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField17MouseEntered

    private void jFormattedTextField17MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField17MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField17MouseExited

    private void jFormattedTextField16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField16MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField16MouseEntered

    private void jFormattedTextField16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField16MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField16MouseExited

    private void jFormattedTextField12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField12MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField12MouseEntered

    private void jFormattedTextField12MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField12MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField12MouseExited

    private void jFormattedTextField13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField13MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField13MouseEntered

    private void jFormattedTextField13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField13MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField13MouseExited

    private void jFormattedTextField14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField14MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField14MouseEntered

    private void jFormattedTextField14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jFormattedTextField14MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField14MouseExited

    private void jFormattedTextField2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField2KeyPressed
       
      
       
    }//GEN-LAST:event_jFormattedTextField2KeyPressed

    private void jFormattedTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFormattedTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jFormattedTextField2ActionPerformed

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
      
           
       
    }//GEN-LAST:event_formKeyPressed

    private void jMenuItem3MenuDragMouseEntered(javax.swing.event.MenuDragMouseEvent evt) {//GEN-FIRST:event_jMenuItem3MenuDragMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3MenuDragMouseEntered

    private void jMenuItem3MenuDragMouseExited(javax.swing.event.MenuDragMouseEvent evt) {//GEN-FIRST:event_jMenuItem3MenuDragMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3MenuDragMouseExited

    private void jMenuItem3MenuKeyPressed(javax.swing.event.MenuKeyEvent evt) {//GEN-FIRST:event_jMenuItem3MenuKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem3MenuKeyPressed

      private void jMenuItem4MouseEntered(java.awt.event.MouseEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void jMenuItem4MouseExited(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jMenuItem4MousePressed(java.awt.event.MouseEvent evt) {                                        
        this.dbManager.deletFromTab(lieferung.getId());
        System.out.println("Lieferung " + lieferung.getBeschreibung() + "   ist aus der Tabelle entfernt!!!" +   jMenuItem4.getText());
    }      
    
    
       private void jMenuItem5MouseEntered(java.awt.event.MouseEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void jMenuItem5MouseExited(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jMenuItem5MousePressed(java.awt.event.MouseEvent evt) { 
        
      if(sprache == 0){
          sprache++; 
          jMenu4.setText("EN");
          jMenuItem5.setText("DEUTSCH als Sprache wählen");
          jMenu3.setText("Delete");
         jMenuItem4.setText("delete from DB"); 
      }else{
          sprache = 0;
           jMenu4.setText("DE");
          jMenuItem5.setText("Choose ENGLISH as a language");
       jMenu3.setText("Löschen");
          jMenuItem4.setText("entfernen aus DB");
      }
       langChoose(sprache);
      
       
       
       
    }      
     private void jMenuItem6MouseEntered(java.awt.event.MouseEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void jMenuItem6MouseExited(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jMenuItem6MousePressed(java.awt.event.MouseEvent evt) { 
        
     sprache = 2;
        
          jMenu4.setText("RU");
          jMenuItem6.setText("Выбрать русский язык");
         
      
          
          
       
      
       langChoose(sprache);
      
       
       
       
    } 
    
      private void jMenuItem7MouseEntered(java.awt.event.MouseEvent evt) {                                        
        // TODO add your handling code here:
    }                                       

    private void jMenuItem7MouseExited(java.awt.event.MouseEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void jMenuItem7MousePressed(java.awt.event.MouseEvent evt) { 
        
     sprache = 0;
        
          jMenu4.setText("DE");
          jMenuItem6.setText("Deutsch als Sprache wählen");
         
      
          
          
       
      
       langChoose(sprache);
      
       
       
       
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
            java.util.logging.Logger.getLogger(BearbForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BearbForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BearbForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BearbForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               
              
            }
        });
        
        
      
        
    }
     
    public void langChoose(int a){
        if(a == 1){
            
          jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Description", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
      
        jComboBox1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Origin", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
  
        jCheckBox2.setText("new");
        jCheckBox3.setText("activ");
        jCheckBox4.setText("signed in MH");
        jCheckBox5.setText("Signing customer");
        jCheckBox6.setText("sent to procurement");
        jCheckBox7.setText("investment/ leasing");
       jFormattedTextField3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Quantity", javax.swing.border.TitledBorder.CENTER, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       
       jComboBox5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Units",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
      
       jFormattedTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preferred delivery date", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N jTextArea1 = new javax.swing.JTextArea();
           labelBestellung.setText("Order");
       // statusBestellung = new javax.swing.JLabel();
         jLabel2.setText("Procurement");
       // statusEinkauf = new javax.swing.JLabel();
        jLabel3.setText("Construction Site");
      //  statusBaustelle = new javax.swing.JLabel();
         jLabel4.setText("Controlling");
       // statusControlling = new javax.swing.JLabel();
       tFAgreementNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Request No.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Price", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Delivered to site on",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
      jTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Invoce-Nr to Customer", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
              javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
      jFormattedTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Date of Request", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
              javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        lieferant_tFKommentar01.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Supplier", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jFormattedTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Goods will come at", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                 javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jFormattedTextField10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                 javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        tFAnforderungNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BANF Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "(dd.mm.yy)ordered at ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sum for material and service", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Order Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                 javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Weight", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
          jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tones", "Lib", "Once", "??", "Kg", "gr", "M3", "Barels" }));
        jComboBox9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Units", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Order amount invoiced", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Ordered by", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Planed delivery date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "CM", "MM", "ft", "ya" }));
        jComboBox10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Units", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
       jFormattedTextField18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Length", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Order amount not invoiced", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Delivery received to TS at ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Width", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "has to be invoiced to the client", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jFormattedTextField19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hight", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                 javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Delivery as planed", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Gods are delivered on TS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                 javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Colli", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Payment conditions", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                 javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Comment", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Comment", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
            jCheckBox1.setText("Invoiced to client");
       jButtonZuArtikelliste.setText("to the list of materials");
       jButtonLoc.setText("locate the delivery");
       jButtonPro.setText("Project name/info");
           jTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Container", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                   javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       
      
        }else if(a== 0){
            
          jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Beschreibung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
      
        jComboBox1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Herkunft", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
  
        jCheckBox2.setText("neu");
        jCheckBox3.setText("activ");
        jCheckBox4.setText("Unterschr.MH");
        jCheckBox5.setText("Unterschr.Kunde");
        jCheckBox6.setText("an Einkauf weitergeleitet");
        jCheckBox7.setText("investition/ leasing");
       jFormattedTextField3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Menge", javax.swing.border.TitledBorder.CENTER, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       
       jComboBox5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einheiten",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
      
       jFormattedTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wunschliefertermin", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N jTextArea1 = new javax.swing.JTextArea();
           labelBestellung.setText("Bestellung");
       // statusBestellung = new javax.swing.JLabel();
         jLabel2.setText("Einkauf");
       // statusEinkauf = new javax.swing.JLabel();
        jLabel3.setText("Baustelle");
      //  statusBaustelle = new javax.swing.JLabel();
         jLabel4.setText("Controlling");
       // statusControlling = new javax.swing.JLabel();
       tFAgreementNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Agreement/ BS Anford Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preis", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lieferung an BS erfolgt am:",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
      jTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "RG Nummer an AG", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
      jFormattedTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datum der Anforderung", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        lieferant_tFKommentar01.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lieferant", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jFormattedTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang erfolgt am:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jFormattedTextField10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datum", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        tFAnforderungNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BANF Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellt am: ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Summe für Material und Leistungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellungsnummer Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Gewicht", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
          jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tonnen",  "??", "Kg", "gr", "M3" }));
        jComboBox9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einh.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Angebotssumme weitergerechnet", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Bestellt von", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS nach Plan:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "CM", "MM" }));
        jComboBox10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Einh.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
       jFormattedTextField18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Länge", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Angebotssumme nicht weitergerechnet", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS erfolgt am", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Breite", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kunden in Rechnung stellen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jFormattedTextField19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Höhe", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Auslieferung nach Plan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Wareneingang TS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Kolli", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Zahlungsbedingungen", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Komment", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Komment", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
            jCheckBox1.setText("Transfered to the customer");
       jButtonZuArtikelliste.setText("Zu der Artikelliste");
        jButtonLoc.setText("Lieferung orten");
       jButtonPro.setText("Projektname/info");
           jTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Container", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       
      
        }else if(a == 2){  // здесь будет вариант для русского языка
            
          jTextArea1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Описание", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
      
        jComboBox1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Происхождение", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
  
        jCheckBox2.setText("новая");
        jCheckBox3.setText("активная");
        jCheckBox4.setText("подпись МХ");
        jCheckBox5.setText("подпись заказчика");
        jCheckBox6.setText("отправлено в Снабжение");
        jCheckBox7.setText("инвестиция/ лизинг");
       jFormattedTextField3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "количество", javax.swing.border.TitledBorder.CENTER, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       
       jComboBox5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "единицы",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 10))); // NOI18N
      
       jFormattedTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "желательная дата доставки", 
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N jTextArea1 = new javax.swing.JTextArea();
           labelBestellung.setText("Заказ");
       // statusBestellung = new javax.swing.JLabel();
         jLabel2.setText("Снабжение");
       // statusEinkauf = new javax.swing.JLabel();
        jLabel3.setText("Стройка");
      //  statusBaustelle = new javax.swing.JLabel();
         jLabel4.setText("Контроллинг");
       // statusControlling = new javax.swing.JLabel();
       tFAgreementNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "номер заявки.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "сумма", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "когда доставлено на стройку",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
      jTextField17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Invoce-Nr для клиента", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
              javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
      jFormattedTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "дата заявки", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
              javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        lieferant_tFKommentar01.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "поставщик", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jFormattedTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "когда прибудут материалы", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                 javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jFormattedTextField10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "дата", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                 javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        tFAnforderungNR.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BANF Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "когда заказано(dd.mm.yy) ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "сумма за метериалы и услуги", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "заказ Nr.", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                 javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "вес", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
          jComboBox9.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Тонн", "Фунт", "Дюйм", "??", "Kg", "gr", "M3", "Барель" }));
        jComboBox9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "единицы", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "на заказ выставлен инвойс", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "кем заказано", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "запланированная дата доставки", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jComboBox10.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "CM", "MM", "ft", "ya" }));
        jComboBox10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "единицы", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
       jFormattedTextField18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "длина", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "инвойс не выставлен на сумму", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "когда поставка пришла в ТШ ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jFormattedTextField20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ширина", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "нужно выставить инвойс заказчику", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jFormattedTextField19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "высота", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                 javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       jFormattedTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "поставка запланирована", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
               javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "материалы доставлены в ТШ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                 javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "колли", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "условия оплаты", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
         jTextField15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "упак. лист", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                 javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "комментарий", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        jTextField5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "комментарий", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
            jCheckBox1.setText("инвойс выставлен заказчику");
      jButtonZuArtikelliste.setText("перейти к списку артикулов");
       jButtonLoc.setText("найти поставку на карте");
       jButtonPro.setText("название проекта/ информация");
           jTextField16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "контейнер", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                   javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
       
      
        }
        
    }
    
  public void backgroundColorSet(JComboBox box){
      if(box.getSelectedItem().equals("Rot")){
          box.setBackground(Color.red);
          box.setForeground(Color.WHITE);
      }else if(box.getSelectedItem().equals("Gelb")){
          box.setBackground(Color.yellow);
      }else{
          box.setBackground(Color.green);
      }
      

}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JCheckBox jCheckBox5;
    private javax.swing.JCheckBox jCheckBox6;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox10;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox9;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField10;
    private javax.swing.JFormattedTextField jFormattedTextField12;
    private javax.swing.JFormattedTextField jFormattedTextField13;
    private javax.swing.JFormattedTextField jFormattedTextField14;
    protected javax.swing.JFormattedTextField jFormattedTextField15;
    private javax.swing.JFormattedTextField jFormattedTextField16;
    private javax.swing.JFormattedTextField jFormattedTextField17;
    private javax.swing.JFormattedTextField jFormattedTextField18;
    private javax.swing.JFormattedTextField jFormattedTextField19;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JFormattedTextField jFormattedTextField20;
    private javax.swing.JFormattedTextField jFormattedTextField3;
    private javax.swing.JFormattedTextField jFormattedTextField4;
    private javax.swing.JFormattedTextField jFormattedTextField5;
    private javax.swing.JFormattedTextField jFormattedTextField6;
    private javax.swing.JFormattedTextField jFormattedTextField7;
    private javax.swing.JFormattedTextField jFormattedTextField8;
    protected javax.swing.JFormattedTextField jFormattedTextField9;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
    private javax.swing.JPanel jPanel80;
    private javax.swing.JPanel jPanel81;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    protected javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    protected javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField2;
    protected javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField8;
    protected javax.swing.JTextField jTextField9;
    private javax.swing.JLabel labelBestellung;
    private javax.swing.JLabel statusBaustelle;
    private javax.swing.JLabel statusBestellung;
    private javax.swing.JLabel statusControlling;
    private javax.swing.JLabel statusEinkauf;
    protected javax.swing.JTextField tFAgreementNR;
    protected javax.swing.JTextField tFAnforderungNR;
    private javax.swing.JTextField tFKommentar01;
    // End of variables declaration//GEN-END:variables
 javax.swing.JButton jButtonZuArtikelliste;
      private javax.swing.JMenu jMenu3;
      private javax.swing.JMenuItem jMenuItem4;
       private javax.swing.JMenu jMenu4;
      private javax.swing.JMenuItem jMenuItem5;
       private javax.swing.JMenuItem jMenuItem6;
        private javax.swing.JMenuItem jMenuItem7;
       
    JComboBox jComboBoxEinhMenge;
  public javax.swing.JTextField lieferant_tFKommentar01;

    /**
     * Вызывается после применения полей формы к Lieferung, перед сохранением в БД.
     * Подклассы (например BearbFrame01) могут переопределить этот метод,
     * чтобы синхронизировать поля формы со списком Artikel.
     */
    protected void afterFormAppliedToLieferung() {
    }

    /** Значение поля Wareneingang TS erfolgt am (jFormattedTextField7) для использования в подклассах. */
    protected String getWareneingangTSFaktFieldText() {
        return jFormattedTextField7 != null && jFormattedTextField7.getText() != null ? jFormattedTextField7.getText() : "";
    }
    String getPackListe(){
        return jTextField15 != null && jTextField15.getText() != null ? jTextField15.getText() : "";
    }

    // Вспомогательный метод для безопасного преобразования Object в double
    private double getDoubleValue(Object value) {
        if (value == null) {
            return 0.0;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        if (value instanceof String) {
            try {
                return Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return 0.0;
    }
    
    /**
     * Метод для отслеживания изменений в полях формы
     * Сравнивает текущие значения полей с исходными значениями из объекта Lieferung
     * @return true если есть изменения, false если изменений нет
     */
    public boolean hasChanges() {
        java.util.List<String> changes = new java.util.ArrayList<>();
        /*
        System.out.println("=== ОТЛАДКА hasChanges() ===");
        System.out.println("Исходное значение WunschLieferDatum: '" + originalLieferung.getWunschLieferDatum() + "'");
        System.out.println("Текущее значение jFormattedTextField2: '" + jFormattedTextField2.getText() + "'");
        System.out.println("Исходное значение Beschreibung: '" + originalLieferung.getBeschreibung() + "'");
        System.out.println("Текущее значение jTextArea1: '" + jTextArea1.getText() + "'");
        */
        // Проверяем текстовые поля
        if (persistBeschreibungFromTextArea) {
            if (!isEqual(originalLieferung.getBeschreibung(), jTextArea1.getText())) {
                changes.add("Beschreibung: '" + originalLieferung.getBeschreibung() + "' → '" + jTextArea1.getText() + "'");
               // System.out.println(" Feld Beschreibung: " + "Beschreibung: '" + originalLieferung.getBeschreibung() + "' → '" + jTextArea1.getText() + "'");
            }
        }
        
        if (!isEqual(originalLieferung.getAgrNummer(), tFAnforderungNR.getText())) {
            changes.add("AgrNummer: '" + originalLieferung.getAgrNummer() + "' → '" + tFAnforderungNR.getText() + "'");
           // System.out.println("Feld ArgNummer: " + originalLieferung.getAgrNummer() + "' → '" + tFAgreementNR.getText());
        }
        
        if (!isEqual(originalLieferung.getAnfordNummer(), tFAgreementNR.getText())) {
            changes.add("AnfordNummer: '" + originalLieferung.getAnfordNummer() + "' → '" + tFAgreementNR.getText() + "'");
          //  System.out.println("Feld AnfordNummer:  " +originalLieferung.getAnfordNummer() + "' → '" + tFAnforderungNR.getText());
        }
        
        if (!isEqual(originalLieferung.getAnforderungDatum(), jFormattedTextField1.getText())) {
            changes.add("AnforderungDatum: '" + originalLieferung.getAnforderungDatum() + "' → '" + jFormattedTextField1.getText() + "'");
          //  System.out.println("Feld AnfDatum: " + originalLieferung.getAnforderungDatum() + "' → '" + jFormattedTextField1.getText());
        }
        
        if (!isEqual(originalLieferung.getWunschLieferDatum(), jFormattedTextField2.getText())) {
            changes.add("WunschLieferDatum: '" + originalLieferung.getWunschLieferDatum() + "' → '" + jFormattedTextField2.getText() + "'");
          //  System.out.println("Обнаружено изменение в WunschLieferDatum: "+ originalLieferung.getWunschLieferDatum() + "' → '" + jFormattedTextField2.getText());
        } else {
            System.out.println("WunschLieferDatum не изменилось");
        }
        
        if (!isEqual(originalLieferung.getLieferantName(), lieferant_tFKommentar01.getText())) {
            changes.add("LieferantName: '" + originalLieferung.getLieferantName() + "' → '" + lieferant_tFKommentar01.getText() + "'");
            //System.out.println("Feld LieferantName: " + originalLieferung.getLieferantName() + "' → '" + lieferant_tFKommentar01.getText());
        }
        
        if (!isEqual(originalLieferung.getBestelltAm(), jFormattedTextField5.getText())) {
            changes.add("BestelltAm: '" + originalLieferung.getBestelltAm() + "' → '" + jFormattedTextField5.getText() + "'");
          //  System.out.println("Feld Bestellt am: " + originalLieferung.getBestelltAm() + "' → '" + jFormattedTextField5.getText());
        }
        
        if (!isEqual(originalLieferung.getBestellNummer(), jTextField4.getText())) {
            changes.add("BestellNummer: '" + originalLieferung.getBestellNummer() + "' → '" + jTextField4.getText() + "'");
           // System.out.println("Feld Bestellnummer: " + originalLieferung.getBestellNummer() + "' → '" + jTextField4.getText());
        }
        
        if (!isEqual(originalLieferung.getWareneingangTS_plan(), jFormattedTextField6.getText())) {
            changes.add("WareneingangTS_plan: '" + originalLieferung.getWareneingangTS_plan() + "' → '" + jFormattedTextField6.getText() + "'");
          //  System.out.println("Feld Wareneingang TS plan:" + originalLieferung.getWareneingangTS_plan() + "' → '" + jFormattedTextField6.getText());
        }
        
        if (!isEqual(originalLieferung.getWareneingangTS_Fakt(), jFormattedTextField7.getText())) {
            changes.add("WareneingangTS_Fakt: '" + originalLieferung.getWareneingangTS_Fakt() + "' → '" + jFormattedTextField7.getText() + "'");
           // System.out.println("Feld Wareneingang Fakt: " + originalLieferung.getWareneingangTS_Fakt() + "' → '" + jFormattedTextField7.getText() );
        }
         if (!isEqual(originalLieferung.getAnfordPerson(), jTextField1.getText())) {
            changes.add("Anford.Person:: '" + originalLieferung.getAnfordPerson() + "' → '" + jTextField2.getText() + "'");
          //  System.out.println("Feld Wareneingang TS:"  + originalLieferung.getWarenEingangTS() + "' → '" + jTextField2.getText());
        }
        
        if (!isEqual(originalLieferung.getWarenEingangTS(), jTextField2.getText())) {
            changes.add("WarenEingangTS: '" + originalLieferung.getWarenEingangTS() + "' → '" + jTextField2.getText() + "'");
          //  System.out.println("Feld Wareneingang TS:"  + originalLieferung.getWarenEingangTS() + "' → '" + jTextField2.getText());
        }
        
        if (!isEqual(originalLieferung.getAuslieferungNachPlan(), jFormattedTextField4.getText())) {
            changes.add("AuslieferungNachPlan: '" + originalLieferung.getAuslieferungNachPlan() + "' → '" + jFormattedTextField4.getText() + "'");
           // System.out.println("Auslieferung nach Plan: " + originalLieferung.getAuslieferungNachPlan() + "' → '" + jFormattedTextField4.getText());
        }
        
        if (!isEqual(originalLieferung.getZahlungsbedingungen(), jTextField8.getText())) {
            changes.add("Zahlungsbedingungen: '" + originalLieferung.getZahlungsbedingungen() + "' → '" + jTextField8.getText() + "'");
          //  System.out.println("Zahlungsbedingungen : "+ originalLieferung.getZahlungsbedingungen() + "' → '" + jTextField8.getText());
        }
        
        if (!isEqual(originalLieferung.getKommentEinkauf(), jTextField9.getText())) {
            changes.add("KommentEinkauf: '" + originalLieferung.getKommentEinkauf() + "' → '" + jTextField9.getText() + "'");
           // System.out.println("Komment Einkauf: "+ originalLieferung.getKommentEinkauf() + "' → '" + jTextField9.getText());
        }
        
        if (!isEqual(originalLieferung.getWunschterminWareneingang(), jFormattedTextField8.getText())) {
            changes.add("WunschterminWareneingang: '" + originalLieferung.getWunschterminWareneingang() + "' → '" + jFormattedTextField8.getText() + "'");
          //  System.out.println("WunschWareneingang: " + originalLieferung.getWunschterminWareneingang() + "' → '" + jFormattedTextField8.getText());
        }
        
        if (!isEqual(originalLieferung.getWareneingang_Fakt(), jFormattedTextField9.getText())) {
            changes.add("Wareneingang_Fakt: '" + originalLieferung.getWareneingang_Fakt() + "' → '" + jFormattedTextField9.getText() + "'");
        }
        
        if (!isEqual(originalLieferung.getKommentarBS(), jTextField5.getText())) {
            changes.add("KommentarBS: '" + originalLieferung.getKommentarBS() + "' → '" + jTextField5.getText() + "'");
            System.out.println("Kommentar BS: "+ originalLieferung.getKommentarBS() + "' → '" + jTextField5.getText());
        }
        
        if (!isEqual(originalLieferung.getEinhMasse(), (String) jComboBox9.getSelectedItem())) {
            changes.add("EinhMasse: '" + originalLieferung.getEinhMasse() + "' → '" + (String) jComboBox9.getSelectedItem() + "'");
            System.out.println("EinhMasse: "+ originalLieferung.getEinhMasse() + "' → '" + (String) jComboBox9.getSelectedItem() );
        }
        
        if (!isEqual(originalLieferung.getEinheitLBH(), (String) jComboBox10.getSelectedItem())) {
            changes.add("EinheitLBH: '" + originalLieferung.getEinheitLBH() + "' → '" + (String) jComboBox10.getSelectedItem() + "'");
            System.out.println("EinheitLBH: "+ originalLieferung.getEinheitLBH() + "' → '" + (String) jComboBox10.getSelectedItem());
        }
        
        if (!isEqual(originalLieferung.getKolli(), jTextField14.getText())) {
            changes.add("Kolli: '" + originalLieferung.getKolli() + "' → '" + jTextField14.getText() + "'");
            System.out.println("Kolli: "+ originalLieferung.getKolli() + "' → '" + jTextField14.getText());
        }
        
        if (!isEqual(originalLieferung.getPackListe(), jTextField15.getText())) {
            changes.add("PackListe: '" + originalLieferung.getPackListe() + "' → '" + jTextField15.getText() + "'");
            System.out.println("PL :" + originalLieferung.getPackListe() + "' → '" + jTextField15.getText());
        }
        
        if (!isEqual(originalLieferung.getContainerNr(), jTextField16.getText())) {
            changes.add("ContainerNr: '" + originalLieferung.getContainerNr() + "' → '" + jTextField16.getText() + "'");
            System.out.println("Container Nr: "+ originalLieferung.getContainerNr() + "' → '" + jTextField16.getText());
        }
        
        if (!isEqual(originalLieferung.getRgNummeranAG(), jTextField17.getText())) {
            changes.add("RgNummeranAG: '" + originalLieferung.getRgNummeranAG() + "' → '" + jTextField17.getText() + "'");
            System.out.println("RgNummer AG: " +originalLieferung.getRgNummeranAG() + "' → '" + jTextField17.getText());
        }
        
        if (!isEqual(originalLieferung.getDatumBS(), jFormattedTextField10.getText())) {
            changes.add("DatumBS: '" + originalLieferung.getDatumBS() + "' → '" + jFormattedTextField10.getText() + "'");
            System.out.println("DatumBS: "+ originalLieferung.getDatumBS() + "' → '" + jFormattedTextField10.getText());
        }
        
        // Проверяем числовые поля
        if (!isEqual(originalLieferung.getMenge(), getFloatValue(jFormattedTextField3.getValue()))) {
            changes.add("Menge: " + originalLieferung.getMenge() + " → " + getFloatValue(jFormattedTextField3.getValue()));
            System.out.println("Menge:  "+ originalLieferung.getMenge() + " → " + getFloatValue(jFormattedTextField3.getValue()));
        }
        
        if (!isEqual(originalLieferung.getSummeMat_Leist_cents(), getFloatValue(jFormattedTextField16.getValue()))) {
            changes.add("SummeMat_Leist_cents: " + originalLieferung.getSummeMat_Leist_cents() + " → " + getFloatValue(jFormattedTextField16.getValue()));
        }
        
        if (!isEqual(originalLieferung.getPreisEinkauf(), getFloatValue(jFormattedTextField17.getValue()))) {
            changes.add("PreisEinkauf: " + originalLieferung.getPreisEinkauf() + " → " + getFloatValue(jFormattedTextField17.getValue()));
        }
        
        if (!isEqual(originalLieferung.getGewicht(), getFloatValue(jFormattedTextField15.getValue()))) {
            changes.add("Gewicht: " + originalLieferung.getGewicht() + " → " + getFloatValue(jFormattedTextField15.getValue()));
        }
        
        if (!isEqual(originalLieferung.getLaenge(), getFloatValue(jFormattedTextField18.getValue()))) {
            changes.add("Laenge: " + originalLieferung.getLaenge() + " → " + getFloatValue(jFormattedTextField18.getValue()));
        }
        
        if (!isEqual(originalLieferung.getBreite(), getFloatValue(jFormattedTextField20.getValue()))) {
            changes.add("Breite: " + originalLieferung.getBreite() + " → " + getFloatValue(jFormattedTextField20.getValue()));
        }
        
        if (!isEqual(originalLieferung.getHoehe(), getFloatValue(jFormattedTextField19.getValue()))) {
            changes.add("Hoehe: " + originalLieferung.getHoehe() + " → " + getFloatValue(jFormattedTextField19.getValue()));
        }
        
        if (!isEqual(originalLieferung.getSummeWeitergerechnetanAG_cents(), getFloatValue(jFormattedTextField12.getValue()))) {
            changes.add("SummeWeitergerechnetanAG_cents: " + originalLieferung.getSummeWeitergerechnetanAG_cents() + " → " + getFloatValue(jFormattedTextField12.getValue()));
        }
        
        if (!isEqual(originalLieferung.getSumme_nach_Weiterrechnung(), getFloatValue(jFormattedTextField13.getValue()))) {
            changes.add("Summe_nach_Weiterrechnung: " + originalLieferung.getSumme_nach_Weiterrechnung() + " → " + getFloatValue(jFormattedTextField13.getValue()));
        }
        
        if (!isEqual(originalLieferung.getKundenInRechnungStellen(), getFloatValue(jFormattedTextField14.getValue()))) {
            changes.add("KundenInRechnungStellen: " + originalLieferung.getKundenInRechnungStellen() + " → " + getFloatValue(jFormattedTextField14.getValue()));
        }
        
        // Проверяем булевы поля
        if (originalLieferung.isNeu() != jCheckBox2.isSelected()) {
            changes.add("Neu: " + originalLieferung.isNeu() + " → " + jCheckBox2.isSelected());
        }
        
        if (originalLieferung.isAktiv() != jCheckBox3.isSelected()) {
            changes.add("Aktiv: " + originalLieferung.isAktiv() + " → " + jCheckBox3.isSelected());
        }
        
        if (originalLieferung.isUnterschriftMH() != jCheckBox4.isSelected()) {
            changes.add("UnterschriftMH: " + originalLieferung.isUnterschriftMH() + " → " + jCheckBox4.isSelected());
        }
        
        if (originalLieferung.isUnterschriftKunde() != jCheckBox5.isSelected()) {
            changes.add("UnterschriftKunde: " + originalLieferung.isUnterschriftKunde() + " → " + jCheckBox5.isSelected());
        }
        
        if (originalLieferung.isAnEinkaufWeiterGeleitetMH() != jCheckBox6.isSelected()) {
            changes.add("AnEinkaufWeiterGeleitetMH: " + originalLieferung.isAnEinkaufWeiterGeleitetMH() + " → " + jCheckBox6.isSelected());
        }
        
        if (originalLieferung.isInvest_Leasing() != jCheckBox7.isSelected()) {
            changes.add("Invest_Leasing: " + originalLieferung.isInvest_Leasing() + " → " + jCheckBox7.isSelected());
        }
        
        if (originalLieferung.getIstWeitergerechnetAnAG() != jCheckBox1.isSelected()) {
            changes.add("IstWeitergerechnetAnAG: " + originalLieferung.getIstWeitergerechnetAnAG() + " → " + jCheckBox1.isSelected());
        }
        
        // Проверяем ComboBox поля
        if (!isEqual(originalLieferung.getDe_eu(), (String) jComboBox1.getSelectedItem())) {
            changes.add("De_eu: '" + originalLieferung.getDe_eu() + "' → '" + (String) jComboBox1.getSelectedItem() + "'");
        }
        
        if (!isEqual(originalLieferung.getEinhMenge(), (String) jComboBox5.getSelectedItem())) {
            changes.add("EinhMenge: '" + originalLieferung.getEinhMenge() + "' → '" + (String) jComboBox5.getSelectedItem() + "'");
        }
        
       // System.out.println("Size of CHANGES:   " + changes.size());
        // Если есть изменения, показываем диалог подтверждения
       // System.out.println("Общее количество изменений: " + changes.size());
        for (String change : changes) {
           // System.out.println("Change: " + change);
        }
        
        if (!changes.isEmpty()) {
            return showConfirmationDialog(changes);
        }
        
        System.out.println("No changes found");
        return true; // Нет изменений, можно сохранять
    }
    
    /**
     * Вспомогательный метод для сравнения строк с учетом null значений
     */
    private boolean isEqual(String original, String current) {
        System.out.println("isEqual: original='" + original + "', current='" + current + "'");
        if (original == null && current == null) {
            System.out.println("isEqual: both null - true");
            return true;
        }
        if (original == null) {
            boolean result = current.trim().isEmpty();
            System.out.println("isEqual: original null, current empty=" + result);
            return result;
        }
        if (current == null) {
            boolean result = original.trim().isEmpty();
            System.out.println("isEqual: current null, original empty=" + result);
            return result;
        }
        boolean result = original.equals(current);
        System.out.println("isEqual: equals=" + result);
        return result;
    }
    
    /**
     * Вспомогательный метод для сравнения float значений
     */
    private boolean isEqual(float original, float current) {
        return Math.abs(original - current) < 0.001f; // Учитываем погрешность для float
    }
    
    /**
     * Вспомогательный метод для получения float значения из Object
     */
    private float getFloatValue(Object value) {
        if (value == null) return 0.0f;
        if (value instanceof Number) {
            return ((Number) value).floatValue();
        }
        try {
            return Float.parseFloat(value.toString());
        } catch (NumberFormatException e) {
            return 0.0f;
        }
    }
    
    /** Имя файла лога изменений в БД */
    private static final String DB_CHANGES_LOG_FILE = "I:\\Austausch Dmitri\\log_liefermonitor01\\db_changes_log.txt";

    /**
     * Показывает диалог подтверждения изменений
     * @param changes список изменений
     * @return true если пользователь подтвердил изменения, false если отменил
     */
    private boolean showConfirmationDialog(java.util.List<String> changes) {
        StringBuilder message = new StringBuilder();
        message.append("Änderungen im Fenster:\n\n");
        
        for (int i = 0; i < changes.size(); i++) {
            message.append((i + 1)).append(". ").append(changes.get(i)).append("\n");
        }
        
        message.append("\n Wollen Sie die Änderungen speichern?");
        
        int result = javax.swing.JOptionPane.showConfirmDialog(
            this,
            message.toString(),
            "Bestätigung der Änderungen ",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
        );
        
        if (result == javax.swing.JOptionPane.YES_OPTION) {
            appendChangesToLog(message.toString());
        }
        
        return result == javax.swing.JOptionPane.YES_OPTION;
    }

    /**
     * Добавляет запись об изменении в лог-файл: время, пользователь, текст сообщения.
     * Каждая новая запись дописывается в конец файла.
     */
    private void appendChangesToLog(String messageText) {
        try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(DB_CHANGES_LOG_FILE, true), StandardCharsets.UTF_8))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            pw.println("---------- " + timestamp + " ----------");
            if (person != null) {
                pw.println("Benutzer: " + person.getName());
                pw.println("Login: " + person.getLogin());
                pw.println("Zugangsgruppe: " + person.getZugangsGruppe());
            } else {
                pw.println("Benutzer: (nicht angegeben)");
            }
            pw.println("Änderungen:");
            pw.println(messageText.replace("\n Wollen Sie die Änderungen speichern?", ""));
            pw.println();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Log-Datei konnte nicht geschrieben werden: " + e.getMessage(),
                "Hinweis",
                JOptionPane.WARNING_MESSAGE);
        }
    }
   private boolean bestaetDialog(String s1, String s2, String feldName){
        StringBuilder message = new StringBuilder();
        message.append("Änderungen im  " + feldName + ":\n\n");
        if(!s1.equals(s2)){
            message.append(s1 + "  ->>  "+ s2);
        }
       
        
        message.append("\n Wollen Sie die Änderungen speichern?");
        
        int result = javax.swing.JOptionPane.showConfirmDialog(
            this,
            message.toString(),
            "Bestätigung der Änderungen ",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE
        );
        
        return result == javax.swing.JOptionPane.YES_OPTION;  
    }
    
    /**
     * Создает копию объекта Lieferung для сравнения изменений
     * @param original исходный объект Lieferung
     * @return копия объекта Lieferung
     */
    private Lieferung createCopyOfLieferung(Lieferung original) {
        if (original == null) {
            return new Lieferung("", 0);
        }
        
        // Создаем новый объект Lieferung с теми же данными
        Lieferung copy = new Lieferung();
        
        // Копируем все поля
        copy.setId(original.getId());
        copy.setNeu(original.isNeu());
        copy.setAktiv(original.isAktiv());
        copy.setAgrNummer(original.getAgrNummer());
        copy.setAnfordNummer(original.getAnfordNummer());
        copy.setMenge(original.getMenge());
        copy.setEinhMenge(original.getEinhMenge());
        copy.setBeschreibung(original.getBeschreibung());
        copy.setDe_eu(original.getDe_eu());
        copy.setProjectName(original.getProjectName());
        copy.setAnforderungDatum(original.getAnforderungDatum());
        copy.setWunschLieferDatum(original.getWunschLieferDatum());
        copy.setAnfordPerson(original.getAnfordPerson());
        copy.setUnterschriftMH(original.isUnterschriftMH());
        copy.setUnterschriftKunde(original.isUnterschriftKunde());
        copy.setAnEinkaufWeiterGeleitetMH(original.isAnEinkaufWeiterGeleitetMH());
        copy.setInvest_Leasing(original.isInvest_Leasing());
        copy.setSummeMat_Leist_cents(original.getSummeMat_Leist_cents());
        copy.setPreisEinkauf(original.getPreisEinkauf());
        copy.setLieferantName(original.getLieferantName());
        copy.setBestelltAm(original.getBestelltAm());
        copy.setBestellNummer(original.getBestellNummer());
        copy.setStatusEinkauf(original.getStatusEinkauf());
        copy.setWarenEingangTS(original.getWarenEingangTS());
        copy.setWareneingangTS_plan(original.getWareneingangTS_plan());
        copy.setWareneingangTS_Fakt(original.getWareneingangTS_Fakt());
        copy.setAuslieferungNachPlan(original.getAuslieferungNachPlan());
        copy.setZahlungsbedingungen(original.getZahlungsbedingungen());
        copy.setKommentEinkauf(original.getKommentEinkauf());
        copy.setWunschterminWareneingang(original.getWunschterminWareneingang());
        copy.setStatusAuslieferungBS(original.getStatusAuslieferungBS());
        copy.setWareneingang_Fakt(original.getWareneingang_Fakt());
        copy.setKommentarBS(original.getKommentarBS());
        copy.setGewicht(original.getGewicht());
        copy.setEinhMasse(original.getEinhMasse());
        copy.setLaenge(original.getLaenge());
        copy.setBreite(original.getBreite());
        copy.setHoehe(original.getHoehe());
        copy.setEinheitLBH(original.getEinheitLBH());
        copy.setKolli(original.getKolli());
        copy.setPackListe(original.getPackListe());
        copy.setContainerNr(original.getContainerNr());
        copy.setRgNummeranAG(original.getRgNummeranAG());
        copy.setDatumBS(original.getDatumBS());
        copy.setIstWeitergerechnetAnAG(original.getIstWeitergerechnetAnAG());
        copy.setSummeWeitergerechnetanAG_cents(original.getSummeWeitergerechnetanAG_cents());
        copy.setSumme_nach_Weiterrechnung(original.getSumme_nach_Weiterrechnung());
        copy.setKundenInRechnungStellen(original.getKundenInRechnungStellen());
        copy.setInfo01(original.getInfo01());
        copy.setInfo02(original.getInfo02());
        copy.setInfo03(original.getInfo03());
        copy.setInfo04(original.getInfo04());
        copy.setInfo05(original.getInfo05());
        
        return copy;
    }
    
    /**
     * Открывает диалоговое окно для редактирования ProjectName
     */
    public void showProjectNameEditor(Lieferung lieferung) {
        // Создаем диалоговое окно
        javax.swing.JDialog dialog = new javax.swing.JDialog(this, "Projektname bearbeiten", true);
        dialog.setLayout(new java.awt.BorderLayout(10, 10));
        dialog.setSize(400, 180);
        dialog.setLocationRelativeTo(this);
        
        // Создаем панель для содержимого с отступами
        javax.swing.JPanel contentPanel = new javax.swing.JPanel();
        contentPanel.setLayout(new java.awt.BorderLayout(10, 10));
        contentPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Создаем метку
        javax.swing.JLabel label = new javax.swing.JLabel("Projektname:");
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        contentPanel.add(label, java.awt.BorderLayout.NORTH);
        
        // Создаем текстовое поле с текущим значением ProjectName
        javax.swing.JTextField textField = new javax.swing.JTextField();
        textField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        String currentProjectName = lieferung.getProjectName();
        textField.setText(currentProjectName);
        textField.setToolTipText("Hier können Sie die Projektname ändern");
        contentPanel.add(textField, java.awt.BorderLayout.CENTER);
        
        // Создаем панель для кнопок
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        
        // Создаем кнопку подтверждения
        javax.swing.JButton confirmButton = new javax.swing.JButton("Speichern");
        confirmButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Сохраняем значение из текстового поля в объект Lieferung
                String newProjectName = textField.getText().trim();
                lieferung.setProjectName(newProjectName);
               bestaetDialog(lieferung.getProjectName(), newProjectName, "(Projektname)");
                System.out.println("Projekt Name gespeichert: " + newProjectName);
                dbManager.updateTabLieferungen(lieferung);
                dialog.dispose(); // Закрываем диалог
            }
        });
        buttonPanel.add(confirmButton);
        
        // Создаем кнопку отмены
        javax.swing.JButton cancelButton = new javax.swing.JButton("Abbrechen");
        cancelButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dialog.dispose(); // Закрываем диалог без сохранения
            }
        });
        buttonPanel.add(cancelButton);
        
        contentPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        // Добавляем панель содержимого в диалог
        dialog.add(contentPanel);
        
        // Устанавливаем кнопку Speichern как кнопку по умолчанию (Enter)
        dialog.getRootPane().setDefaultButton(confirmButton);
        
        // Добавляем обработчик Enter для текстового поля
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    confirmButton.doClick(); // Имитируем нажатие на кнопку Speichern
                }
            }
        });
        
        // Устанавливаем фокус на текстовое поле
        textField.requestFocusInWindow();
        
        // Показываем диалог
        dialog.setVisible(true);
    }
    
    /**
     * Öffnet Google Maps im Browser mit dem Link aus jTextField9
     */
    public void openGoogleMapsFromTextField() {
        try {
            // Link aus jTextField9 holen
            String mapsLink = jTextField9.getText();
            
            // Prüfen, ob das Feld nicht leer ist
            if (mapsLink == null || mapsLink.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Kein Google Maps Link vorhanden!", 
                    "Fehler", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Prüfen, ob Desktop unterstützt wird
            if (!Desktop.isDesktopSupported()) {
                JOptionPane.showMessageDialog(this, 
                    "Desktop wird auf diesem System nicht unterstützt!", 
                    "Fehler", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Desktop desktop = Desktop.getDesktop();
            
            // Prüfen, ob Browse-Aktion unterstützt wird
            if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                JOptionPane.showMessageDialog(this, 
                    "Browser kann nicht geöffnet werden!", 
                    "Fehler", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // URL öffnen
            URI uri = new URI(mapsLink.trim());
            desktop.browse(uri);
            
        } catch (java.net.URISyntaxException e) {
            JOptionPane.showMessageDialog(this, 
                "Ungültiger Link: " + e.getMessage(), 
                "Fehler", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Fehler beim Öffnen des Browsers: " + e.getMessage(), 
                "Fehler", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Unbekannter Fehler: " + e.getMessage(), 
                "Fehler", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
 public void openGoogleMapsFromDB(Lieferung lieferung) {
        try {
            // Link aus jTextField9 holen
            String mapsLink = lieferung.getInfo01();
            
            // Prüfen, ob das Feld nicht leer ist
            if (mapsLink == null || mapsLink.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Kein Google Maps Link vorhanden!", 
                    "Fehler", 
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Prüfen, ob Desktop unterstützt wird
            if (!Desktop.isDesktopSupported()) {
                JOptionPane.showMessageDialog(this, 
                    "Desktop wird auf diesem System nicht unterstützt!", 
                    "Fehler", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Desktop desktop = Desktop.getDesktop();
            
            // Prüfen, ob Browse-Aktion unterstützt wird
            if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                JOptionPane.showMessageDialog(this, 
                    "Browser kann nicht geöffnet werden!", 
                    "Fehler", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // URL öffnen
            URI uri = new URI(mapsLink.trim());
            desktop.browse(uri);
            
        } catch (java.net.URISyntaxException e) {
            JOptionPane.showMessageDialog(this, 
                "Ungültiger Link: " + e.getMessage(), 
                "Fehler", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (java.io.IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Fehler beim Öffnen des Browsers: " + e.getMessage(), 
                "Fehler", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Unbekannter Fehler: " + e.getMessage(), 
                "Fehler", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
 
   
    public void showLocationEditor(Lieferung lieferung) {
        // Создаем диалоговое окно
        javax.swing.JDialog dialog = new javax.swing.JDialog(this, "Verfolgung der Lieferung", true);
        dialog.setLayout(new java.awt.BorderLayout(10, 10));
        dialog.setSize(400, 180);
        dialog.setLocationRelativeTo(this);
        
        // Создаем панель для содержимого с отступами
        javax.swing.JPanel contentPanel = new javax.swing.JPanel();
        contentPanel.setLayout(new java.awt.BorderLayout(10, 10));
        contentPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Создаем метку
        javax.swing.JLabel label = new javax.swing.JLabel("Google Maps Location link:");
        label.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        contentPanel.add(label, java.awt.BorderLayout.NORTH);
        
        // Создаем текстовое поле с текущим значением ProjectName
        javax.swing.JTextField textField = new javax.swing.JTextField();
        textField.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        String currentLocation = lieferung.getInfo01();
        textField.setText(currentLocation);
        textField.setToolTipText("Hier können Sie die Position der Lieferung ändern");
        contentPanel.add(textField, java.awt.BorderLayout.CENTER);
        
        // Создаем панель для кнопок
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        
        // Создаем кнопку подтверждения
        javax.swing.JButton confirmButton = new javax.swing.JButton("Speichern");
        confirmButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14));
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                // Сохраняем значение из текстового поля в объект Lieferung
                String newLocation = textField.getText();
                lieferung.setInfo01(newLocation);
               bestaetDialog(locationIST, newLocation, "(Lagerort - Position)");
                System.out.println("Lagerort gespeichert: " + newLocation);
                  dbManager.updateTabLieferungen(lieferung);
                dialog.dispose(); // Закрываем диалог
            }
        });
        buttonPanel.add(confirmButton);
        
        // Создаем кнопку отмены
        javax.swing.JButton cancelButton = new javax.swing.JButton("Abbrechen");
        cancelButton.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dialog.dispose(); // Закрываем диалог без сохранения
            }
        });
        buttonPanel.add(cancelButton);
        
        contentPanel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        // Добавляем панель содержимого в диалог
        dialog.add(contentPanel);
        
        // Устанавливаем кнопку Speichern как кнопку по умолчанию (Enter)
        dialog.getRootPane().setDefaultButton(confirmButton);
        
        // Добавляем обработчик Enter для текстового поля
        textField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    confirmButton.doClick(); // Имитируем нажатие на кнопку Speichern
                }
            }
        });
        
        // Устанавливаем фокус на текстовое поле
        textField.requestFocusInWindow();
        
        // Показываем диалог
        dialog.setVisible(true);
    }
    
      private void jButtonProMouseEntered(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void jButtonProMouseExited(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
    }                                    

    private void jButtonProMousePressed(java.awt.event.MouseEvent evt) {                                      
       showProjectNameEditor(lieferung);
// TODO add your handling code here:
    }  
    
     private void jButtonLocMouseEntered(java.awt.event.MouseEvent evt) {                                      
        // TODO add your handling code here:
    }                                     

    private void jButtonLocMouseExited(java.awt.event.MouseEvent evt) {                                     
        // TODO add your handling code here:
    }                                    

    private void jButtonLocMousePressed(java.awt.event.MouseEvent evt) {                                      
       System.out.println("Loc gedruckt");
       openGoogleMapsFromDB(lieferung);
       showLocationEditor( lieferung);
// TODO add your handling code here:
    }     
    
    /**
     * Открывает новое окно с расширенным редактором текста для jTextArea1
     */
    private void openTextAreaEditor() {
        openTextAreaEditor(jTextArea1, "Beschreibung im Erweiterten Fenster", "Beschreibung");
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
        editorTextArea.setEditable(false);
        
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
        
        // Устанавливаем размер окна (около 100x100, но делаем его изменяемым)
        editorFrame.setSize(600, 400); // Разумный размер для редактирования текста
        editorFrame.setMinimumSize(new java.awt.Dimension(300, 200));
        editorFrame.setLocationRelativeTo(this); // Центрируем относительно родительского окна
        editorFrame.setVisible(true);
    }
    
    /**
     * Универсальный метод для открытия окна редактора текста для любого JTextField
     * @param textField JTextField для редактирования
     * @param frameTitle заголовок окна
     * @param borderTitle заголовок для границы текстового поля
     */
    private void openTextFieldEditor(javax.swing.JTextField textField, String frameTitle, String borderTitle) {
        // Проверяем, не открыто ли уже окно редактора для этого поля
        if (textAreaEditorFrame != null && textAreaEditorFrame.isDisplayable()) {
            textAreaEditorFrame.requestFocus();
            return;
        }
        
        // Создаем новое JFrame
        javax.swing.JFrame editorFrame = new javax.swing.JFrame(frameTitle);
        editorFrame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        textAreaEditorFrame = editorFrame; // Сохраняем ссылку
        
        // Создаем JTextArea для редактирования (используем JTextArea для многострочного редактирования)
        javax.swing.JTextArea editorTextArea = new javax.swing.JTextArea();
        editorTextArea.setLineWrap(true);
        editorTextArea.setWrapStyleWord(true);
        editorTextArea.setFont(textField.getFont());
        editorTextArea.setBorder(javax.swing.BorderFactory.createTitledBorder(null, borderTitle, 
            javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, 
            javax.swing.border.TitledBorder.DEFAULT_POSITION, 
            new java.awt.Font("Segoe UI", 1, 14)));
        
        // Копируем текст из исходного JTextField
        editorTextArea.setText(textField.getText());
        
        // Устанавливаем разумные размеры для редактирования
        editorTextArea.setRows(10);
        editorTextArea.setColumns(50);
        
        // Создаем JScrollPane для прокрутки
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(editorTextArea);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        // Сохраняем ссылку на исходное поле для сохранения текста
        final javax.swing.JTextField sourceTextField = textField;
        
        // Добавляем обработчик закрытия окна для сохранения текста
        editorFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                // Сохраняем текст обратно в исходное JTextField
                sourceTextField.setText(editorTextArea.getText());
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
        
        // Устанавливаем размер окна
        editorFrame.setSize(600, 400);
        editorFrame.setMinimumSize(new java.awt.Dimension(300, 200));
        editorFrame.setLocationRelativeTo(this); // Центрируем относительно родительского окна
        editorFrame.setVisible(true);
    }
    
    /**
     * Добавляет обработчики двойного клика для всех JTextField в методе startComponents()
     */
    private void addTextFieldDoubleClickHandlers() {
        // Добавляем обработчики для каждого поля напрямую
        if (tFAgreementNR != null) {
            tFAgreementNR.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        openTextFieldEditor(tFAgreementNR, "Agreement/ Bestellung Nr. - Erweiterte Bearbeitung", "Agreement/ Bestellung Nr.");
                    }
                }
            });
        }
        
        if (jTextField17 != null) {
            jTextField17.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        openTextFieldEditor(jTextField17, "RG-Nummer an AG - Erweiterte Bearbeitung", "RG-Nummer an AG");
                    }
                }
            });
        }
        
        if (lieferant_tFKommentar01 != null) {
            lieferant_tFKommentar01.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        openTextFieldEditor(lieferant_tFKommentar01, "Lieferant - Erweiterte Bearbeitung", "Lieferant");
                    }
                }
            });
        }
        
        if (tFAnforderungNR != null) {
            tFAnforderungNR.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        openTextFieldEditor(tFAnforderungNR, "Anforderung Nr. - Erweiterte Bearbeitung", "BANF Nr.");
                    }
                }
            });
        }
        
        if (jTextField4 != null) {
            jTextField4.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        openTextFieldEditor(jTextField4, "Bestellungsnummer - Erweiterte Bearbeitung", "Bestellungsnummer");
                    }
                }
            });
        }
        
        // Добавляем обработчики для полей, которые могут не иметь их
        if (jTextField1 != null) {
            jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        openTextFieldEditor(jTextField1, "Bestellt von - Erweiterte Bearbeitung", "Bestellt von");
                    }
                }
            });
        }
        
        if (jTextField5 != null) {
            jTextField5.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        openTextFieldEditor(jTextField5, "Kommentar - Erweiterte Bearbeitung", "Kommentar");
                    }
                }
            });
        }
        
        if (jTextField15 != null) {
            jTextField15.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        openTextFieldEditor(jTextField15, "PL - Erweiterte Bearbeitung", "PL");
                    }
                }
            });
        }
        
        if (jTextField2 != null) {
            jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        openTextFieldEditor(jTextField2, "Wareneingang TS - Erweiterte Bearbeitung", "Wareneingang TS");
                    }
                }
            });
        }
        
        if (jTextField14 != null) {
            jTextField14.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        openTextFieldEditor(jTextField14, "Kolli - Erweiterte Bearbeitung", "Kolli");
                    }
                }
            });
        }
        
        if (jTextField8 != null) {
            jTextField8.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        openTextFieldEditor(jTextField8, "Zahlungsbedingungen - Erweiterte Bearbeitung", "Zahlungsbedingungen");
                    }
                }
            });
        }
        
        // jTextField15 уже имеет обработчик
        
        if (jTextField16 != null) {
            jTextField16.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    if (evt.getClickCount() == 2) {
                        openTextFieldEditor(jTextField16, "Container - Erweiterte Bearbeitung", "Container");
                    }
                }
            });
        }
    }
    
}
