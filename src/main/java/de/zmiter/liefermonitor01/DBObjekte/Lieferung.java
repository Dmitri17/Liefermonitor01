/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.DBObjekte;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JButton;

/**
 *
 * @author lepeschko
 */
public class Lieferung extends JButton{
    private int id = 0; // ID записи в базе данных
    boolean neu;//jCheckBox2
    boolean aktiv; // jCheckBox3
   
    // Feld 1- Planung
   String projectName = "";
  
    boolean unterschriftMH = false;// jCheckBox4
    boolean unterschriftKunde = false;// jCheckBox5
    boolean anEinkaufWeiterGeleitetMH = false; // jCheckBox6
    boolean invest_Leasing = false;// jCheckBox7
      String de_eu = " ";  //jComboBox1
      float menge = 0;//jFTF3
        String einhMenge = " ";//  jComboBox5
    String wunschLieferDatum = "00.00.00";  //jFTF2
    String beschreibung = "";  //jTA1  резервное поле, можно использовать для других целей
      String banfNr = " ";  // tFAgreement
         String anforderungDatum = ""; //jFTF1
    String anfordNummer  = " "; //tFAnforderungNR
          
   float summeMat_Leist = 0;// jFTF16
    String anfordPerson = " ";
  
    
    // Feld 2 Einkauf
    float preisEinkauf = 0;  //jFTF17
    String statusEinkauf = "";
    String lieferantName = ""; // tFKommentar01
    String bestelltAm = "";  //jFTF5
    String bestellNummer = ""; // jTF4
     String wareneingangTS_plan = "";  //jFTF6
    String wareneingangTS_Fakt = "";  //jFTF7
     String warenEingangTS = ""; //jTF2
  
     String auslieferungNachPlan = "";   //jFTF4
    String zahlungsbedingungen = "";  //jtf8
   String kommentEinkauf = "";   //jTF9
    
   
   
    // Feld 3 Baustelle 
     String statusAuslieferungBS = "";
    String lieferungAnBSerfolgtAm = "";  // jFTF8
    String wareneingang_erfolgtAm = "";  //jFTF9
     String kommentarBS = "";      //jTF5
   
     float gewicht = 0;    //jFTF15
    String einhMasse = "";  //jComboBox9
      String einheitLBH = "";  //jComboBox10
    float laenge = 0;   //jFTF18
    float breite = 0;  //jFTF20
    float hoehe = 0;   //jFTF19
    
     String kolli = "";   //jTF14
    String packListe = ""; //jTF15
    String containerNr = "";   // jTF16
   
  
  //Feld 4 Controlling
  
  
    String rgNummeranAG = "";  // jTF17
    String rechnungDatum = "";   // jFTF10
  boolean istWeitergerechnetAnAG = false;   // jCheckBox1
    float summeWeitergerechnetanAG_cents = 0;    //jFTF12
    float summe_nicht_weitergerechnet = 0;   //jFTF13
    float kundenInRechnungStellen = 0;  // jFTF14
    
  String info01 = "";  // Status- Bestellung
  String info02 = "";// reserviert für weitere Parameter (Packliste)
  String info03 = "";
  String info04 = "";
  String info05 = "";
  
 public ArrayList<Artikel> artikelListe = new ArrayList<>();
    
    
    
    
    
    
    public Lieferung ( String beschreibung, float menge){
       
        this.beschreibung = beschreibung;
        this.menge = menge;
      
        this.setText(" Anforderung: " + anfordNummer + "   ("  + beschreibung + ")");
    }
     
    public Lieferung (){
      this.neu = false;
        this.setText(" Anforderung: " + anfordNummer + "   ("  + beschreibung + ")");
    }
    
   
    public Lieferung(String beschreibung, float menge, String einhMenge, String de_eu, 
                     String anfordPerson, String agrNummer, String anfordNummer,
                     String anforderungDatum,
                     String wunschLieferDatum, String datumBS, String lieferantName, 
                     String bestellNummer, String bestelltAm, String wareneingangTS_plan, 
                     String wareneingangTS_Fakt, String auslieferungNachPlan, 
                     String zahlungsbedingungen, String kommentEinkauf, 
                     String wunschterminWareneingang, String wareneingang_Fakt, 
                     String kommentarBS, float gewicht, String einhMasse, 
                     float laenge, float breite, float hoehe, String einheitLBH, 
                     String kolli, String packListe, String containerNr, 
                     String rgNummeranAG, String rechnungDatum, 
                     boolean neu, boolean aktiv, boolean unterschriftMH, 
                     boolean unterschriftKunde, boolean anEinkaufWeiterGeleitetMH, 
                     boolean invest_Leasing, float summeMat_Leist_cents, 
                     float preisEinkauf) {
        
        // Основные поля
        this.beschreibung = beschreibung != null ? beschreibung : "";
        this.menge = menge;
        this.einhMenge = einhMenge != null ? einhMenge : " ";
        this.de_eu = de_eu != null ? de_eu : " ";
        this.anfordPerson = anfordPerson != null ? anfordPerson : " ";
        
        // Номера и даты
        this.banfNr = agrNummer != null ? agrNummer : " ";
        this.anfordNummer = anfordNummer != null ? anfordNummer : " ";
        this.anforderungDatum = anforderungDatum != null ? anforderungDatum : "";
        this.wunschLieferDatum = wunschLieferDatum != null ? wunschLieferDatum : "";
        this.rechnungDatum = datumBS != null ? datumBS : "";
        
        // Поля закупки
        this.lieferantName = lieferantName != null ? lieferantName : "";
        this.bestellNummer = bestellNummer != null ? bestellNummer : "";
        this.bestelltAm = bestelltAm != null ? bestelltAm : "";
        this.wareneingangTS_plan = wareneingangTS_plan != null ? wareneingangTS_plan : "";
        this.wareneingangTS_Fakt = wareneingangTS_Fakt != null ? wareneingangTS_Fakt : "";
        this.auslieferungNachPlan = auslieferungNachPlan != null ? auslieferungNachPlan : "";
        this.zahlungsbedingungen = zahlungsbedingungen != null ? zahlungsbedingungen : "";
        this.kommentEinkauf = kommentEinkauf != null ? kommentEinkauf : "";
        
        // Поля стройки
        this.lieferungAnBSerfolgtAm = wunschterminWareneingang != null ? wunschterminWareneingang : "";
        this.wareneingang_erfolgtAm = wareneingang_Fakt != null ? wareneingang_Fakt : "";
        this.kommentarBS = kommentarBS != null ? kommentarBS : "";
        this.gewicht = gewicht;
        this.einhMasse = einhMasse != null ? einhMasse : "";
        this.laenge = laenge;
        this.breite = breite;
        this.hoehe = hoehe;
        this.einheitLBH = einheitLBH != null ? einheitLBH : "";
        this.kolli = kolli != null ? kolli : "";
        this.packListe = packListe != null ? packListe : "";
        this.containerNr = containerNr != null ? containerNr : "";
        this.rgNummeranAG = rgNummeranAG != null ? rgNummeranAG : "";
        
        // Булевы поля
        this.neu = neu;
        this.aktiv = aktiv;
        this.unterschriftMH = unterschriftMH;
        this.unterschriftKunde = unterschriftKunde;
        this.anEinkaufWeiterGeleitetMH = anEinkaufWeiterGeleitetMH;
        this.invest_Leasing = invest_Leasing;
        
        // Числовые поля
        this.summeMat_Leist = summeMat_Leist_cents;
        this.preisEinkauf = preisEinkauf;
        
        // Устанавливаем текст кнопки
        this.setText(" Anforderung: " + anfordNummer + "   ("  + beschreibung + ")");
    }
    
  
    public Lieferung(String beschreibung, float menge, String anfordPerson, 
                     String agrNummer, String anfordNummer, boolean neu, boolean aktiv) {
        
        // Основные поля
        this.beschreibung = beschreibung != null ? beschreibung : "";
        this.menge = menge;
        this.anfordPerson = anfordPerson != null ? anfordPerson : " ";
        this.banfNr = agrNummer != null ? agrNummer : " ";
        this.anfordNummer = anfordNummer != null ? anfordNummer : " ";
        
        // Булевы поля
        this.neu = neu;
        this.aktiv = aktiv;
        
        // Устанавливаем текст кнопки
        this.setText(" Anforderung: " + anfordNummer + "   ("  + beschreibung + ")");
    }
    
    /**
     * Статический метод для создания объекта Lieferung из данных формы BearbForm
     * @param formData объект с данными формы
     * @return новый объект Lieferung
     */
    public static Lieferung createFromFormData(FormData formData) {
        return new Lieferung(
            formData.beschreibung,
            formData.menge,
            formData.einhMenge,
            formData.de_eu,
            formData.anfordPerson,
            formData.agrNummer,
            formData.anfordNummer,
            formData.anforderungDatum,
            formData.wunschLieferDatum,
            formData.datumBS,
            formData.lieferantName,
            formData.bestellNummer,
            formData.bestelltAm,
            formData.wareneingangTS_plan,
            formData.wareneingangTS_Fakt,
            formData.auslieferungNachPlan,
            formData.zahlungsbedingungen,
            formData.kommentEinkauf,
            formData.wunschterminWareneingang,
            formData.wareneingang_Fakt,
            formData.kommentarBS,
            formData.gewicht,
            formData.einhMasse,
            formData.laenge,
            formData.breite,
            formData.hoehe,
            formData.einheitLBH,
            formData.kolli,
            formData.packListe,
            formData.containerNr,
            formData.rgNummeranAG,
            formData.rechnungDatum,
            formData.neu,
            formData.aktiv,
            formData.unterschriftMH,
            formData.unterschriftKunde,
            formData.anEinkaufWeiterGeleitetMH,
            formData.invest_Leasing,
            formData.summeMat_Leist_cents,
            formData.preisEinkauf
        );
    }

   
    
    /**
     * Внутренний класс для передачи данных формы
     */
    public static class FormData {
        public String beschreibung;
        public float menge;
        public String einhMenge;
        public String de_eu;
        public String anfordPerson;
        public String agrNummer;
        public String anfordNummer;
        public String anforderungDatum;
        public String wunschLieferDatum;
        public String datumBS;
        public String lieferantName;
        public String bestellNummer;
        public String bestelltAm;
        public String wareneingangTS_plan;
        public String wareneingangTS_Fakt;
        public String auslieferungNachPlan;
        public String zahlungsbedingungen;
        public String kommentEinkauf;
        public String wunschterminWareneingang;
        public String wareneingang_Fakt;
        public String kommentarBS;
        public float gewicht;
        public String einhMasse;
        public float laenge;
        public float breite;
        public float hoehe;
        public String einheitLBH;
        public String kolli;
        public String packListe;
        public String containerNr;
        public String rgNummeranAG;
        public String rechnungDatum;
        public boolean neu;
        public boolean aktiv;
        public boolean unterschriftMH;
        public boolean unterschriftKunde;
        public boolean anEinkaufWeiterGeleitetMH;
        public boolean invest_Leasing;
        public float summeMat_Leist_cents;
        public float preisEinkauf;
    }
   /* */
    public String getBeschreibung(){
        return beschreibung;
    }
    
    // Геттеры для всех полей
    public boolean isNeu() { return neu; }
    public boolean isAktiv() { return aktiv; }
    public String getAgrNummer() { return banfNr; }
    public String getAnfordNummer() { return anfordNummer; }
    public float getMenge() { return menge; }
    public String getEinhMenge() { return einhMenge; }
 
    public String getDe_eu() { return de_eu; }
    public String getProjectName() { return projectName; }
    public String getAnforderungDatum() { return anforderungDatum; }
    public String getWunschLieferDatum() { return wunschLieferDatum; }
    public String getAnfordPerson() { return anfordPerson; }
    public boolean isUnterschriftMH() { return unterschriftMH; }
    public boolean isUnterschriftKunde() { return unterschriftKunde; }
    public boolean isAnEinkaufWeiterGeleitetMH() { return anEinkaufWeiterGeleitetMH; }
    public boolean isInvest_Leasing() { return invest_Leasing; }
    public float getSummeMat_Leist_cents() { return summeMat_Leist; }
  
    public float getPreisEinkauf() { return preisEinkauf; }
   public String getLieferantName() { return lieferantName; }
    public String getBestelltAm() { return bestelltAm; }
    public String getBestellNummer() { return bestellNummer; }
    public String getStatusEinkauf() { return statusEinkauf; }
    public String getWarenEingangTS() { return warenEingangTS; }
    public String getWareneingangTS_plan() { return wareneingangTS_plan; }
  
    public String getWareneingangTS_Fakt() { return wareneingangTS_Fakt; }
    public String getAuslieferungNachPlan() { return auslieferungNachPlan; }
   public String getZahlungsbedingungen() { return zahlungsbedingungen; }
    public String getKommentEinkauf() { return kommentEinkauf; }
    public String getWunschterminWareneingang() { return lieferungAnBSerfolgtAm; }
    public String getStatusAuslieferungBS() { return statusAuslieferungBS; }
    public String getWareneingang_Fakt() { return wareneingang_erfolgtAm; }
    public String getKommentarBS() { return kommentarBS; }
    public float getGewicht() { return gewicht; }
    public String getEinhMasse() { return einhMasse; }
    public float getLaenge() { return laenge; }
    public float getBreite() { return breite; }
    public float getHoehe() { return hoehe; }
   
    public String getEinheitLBH() { return einheitLBH; }
    public String getKolli() { return kolli; }
    public String getPackListe() { return packListe; }
    public String getContainerNr() { return containerNr; }
    public String getRgNummeranAG() { return rgNummeranAG; }
    public String getDatumBS() { return rechnungDatum; }
    public boolean getIstWeitergerechnetAnAG(){  return istWeitergerechnetAnAG;    } // neues Feld boolean!
    public float getSummeWeitergerechnetanAG_cents() { return  summeWeitergerechnetanAG_cents; }
    public float getSumme_nach_Weiterrechnung() { return  summe_nicht_weitergerechnet; }
    public float getKundenInRechnungStellen() { return  kundenInRechnungStellen; }
    public String getInfo01() { return info01; }
    public String getInfo02() { return info02; }
    public String getInfo03() { return info03; }
    public String getInfo04() { return info04; }
    public String getInfo05() { return info05; }
   
    // Геттер и сеттер для ID
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    // Сеттеры для всех полей
    public void setNeu(boolean neu) { this.neu = neu; }
    public void setAktiv(boolean aktiv) { this.aktiv = aktiv; }
    public void setAgrNummer(String agrNummer) { this.banfNr = agrNummer; }
    public void setAnfordNummer(String anfordNummer) { this.anfordNummer = anfordNummer; }
    public void setMenge(float menge) { this.menge = menge; }
    public void setEinhMenge(String einhMenge) { this.einhMenge = einhMenge; }
  
    public void setBeschreibung(String beschreibung) { this.beschreibung = beschreibung; }
    public void setDe_eu(String de_eu) { this.de_eu = de_eu; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public void setAnforderungDatum(String anforderungDatum) { this.anforderungDatum = anforderungDatum; }
    public void setWunschLieferDatum(String wunschLieferDatum) { this.wunschLieferDatum = wunschLieferDatum; }
    public void setAnfordPerson(String anfordPerson) { this.anfordPerson = anfordPerson; }
    public void setUnterschriftMH(boolean unterschriftMH) { this.unterschriftMH = unterschriftMH; }
    public void setUnterschriftKunde(boolean unterschriftKunde) { this.unterschriftKunde = unterschriftKunde; }
    public void setAnEinkaufWeiterGeleitetMH(boolean anEinkaufWeiterGeleitetMH) { this.anEinkaufWeiterGeleitetMH = anEinkaufWeiterGeleitetMH; }
    public void setInvest_Leasing(boolean invest_Leasing) { this.invest_Leasing = invest_Leasing; }
    public void setSummeMat_Leist_cents(float summeMat_Leist_cents) { this.summeMat_Leist = summeMat_Leist_cents; }
 
    public void setPreisEinkauf(float preisEinkauf) { this.preisEinkauf = preisEinkauf; }
    public void setLieferantName(String lieferantName) { this.lieferantName = lieferantName; }
    public void setBestelltAm(String bestelltAm) { this.bestelltAm = bestelltAm; }
    public void setBestellNummer(String bestellNummer) { this.bestellNummer = bestellNummer; }
    public void setStatusEinkauf(String statusEinkauf) { this.statusEinkauf = statusEinkauf; }
    public void setWarenEingangTS(String warenEingangTS) { this.warenEingangTS = warenEingangTS; }
    public void setWareneingangTS_plan(String wareneingangTS_plan) { this.wareneingangTS_plan = wareneingangTS_plan; }
  
    public void setWareneingangTS_Fakt(String wareneingangTS_Fakt) { this.wareneingangTS_Fakt = wareneingangTS_Fakt; }
    public void setAuslieferungNachPlan(String auslieferungNachPlan) { this.auslieferungNachPlan = auslieferungNachPlan; }
    public void setZahlungsbedingungen(String zahlungsbedingungen) { this.zahlungsbedingungen = zahlungsbedingungen; }
    public void setKommentEinkauf(String kommentEinkauf) { this.kommentEinkauf = kommentEinkauf; }
    public void setWunschterminWareneingang(String wunschterminWareneingang) { this.lieferungAnBSerfolgtAm = wunschterminWareneingang; }
    public void setStatusAuslieferungBS(String statusAuslieferungBS) { this.statusAuslieferungBS = statusAuslieferungBS; }
    public void setWareneingang_Fakt(String wareneingang_Fakt) { this.wareneingang_erfolgtAm = wareneingang_Fakt; }
    public void setKommentarBS(String kommentarBS) { this.kommentarBS = kommentarBS; }
    public void setGewicht(float gewicht) { this.gewicht = gewicht; }
    public void setEinhMasse(String einhMasse) { this.einhMasse = einhMasse; }
    public void setLaenge(float laenge) { this.laenge = laenge; }
    public void setBreite(float breite) { this.breite = breite; }
    public void setHoehe(float hoehe) { this.hoehe = hoehe; }
   public void setEinheitLBH(String einheitLBH) { this.einheitLBH = einheitLBH; }
    public void setKolli(String kolli) { this.kolli = kolli; }
    public void setPackListe(String packListe) { this.packListe = packListe; }
    public void setContainerNr(String containerNr) { this.containerNr = containerNr; }
    public void setRgNummeranAG(String rgNummeranAG) { this.rgNummeranAG = rgNummeranAG; }
    public void setDatumBS(String datumBS) { this.rechnungDatum = datumBS; }
    public void setIstWeitergerechnetAnAG(boolean weitergerechnetAnAG){   this.istWeitergerechnetAnAG = weitergerechnetAnAG;    }
   public void setSummeWeitergerechnetanAG_cents(float summeWeitergerechnetanAG_cents) { this.summeWeitergerechnetanAG_cents = summeWeitergerechnetanAG_cents; }
    public void setSumme_nach_Weiterrechnung(float summe_nach_Weiterrechnung) { this.summe_nicht_weitergerechnet = summe_nach_Weiterrechnung; }
    public void setKundenInRechnungStellen(float kundenInRechnungStellen) { this.kundenInRechnungStellen = kundenInRechnungStellen; }
   
    public void setInfo01(String info01) { this.info01 = info01; }
    public void setInfo02(String info02) { this.info02 = info02; }
    public void setInfo03(String info03) { this.info03 = info03; }
    public void setInfo04(String info04) { this.info04 = info04; }
    public void setInfo05(String info05) { this.info05 = info05; }
  
public String getStatusBestellung(){
    String status = "Unknown";
    String wunschlieferdatum = this.getWunschLieferDatum();
    String anforderungdatum = this.getAnforderungDatum();
    String wareneingangBS = this.getWunschterminWareneingang();
    // Проверяем, что обе даты не пустые
    if (wunschlieferdatum == null || wunschlieferdatum.trim().isEmpty() || 
        anforderungdatum == null || anforderungdatum.trim().isEmpty()) {
        String beschreibung = this.getBeschreibung();
        String beschreibungPreview = (beschreibung != null && beschreibung.length() > 20) 
            ? beschreibung.substring(0, 20) 
            : (beschreibung != null ? beschreibung : "");
       // System.out.println("Wunschlieferdatum is EMPTY!!!  " + this.getWunschLieferDatum() + " " + beschreibungPreview);
       return "Grey";
    }
    
    try {
        // Парсим даты из формата "dd.MM.yy"
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd.MM.yy");
        java.util.Date wunschDate = formatter.parse(wunschlieferdatum);
        java.util.Date anforderungDate = formatter.parse(anforderungdatum);
        
        // Вычисляем разность в миллисекундах
        long timeDiff = wunschDate.getTime() - anforderungDate.getTime();
        
        // Конвертируем в дни
        long daysDiff = timeDiff / (1000 * 60 * 60 * 24);
       // System.out.println("DAYS Difference: " + daysDiff);
        // Определяем статус на основе разности в днях
        if (daysDiff >= 90) {
            status = "Green";
        } else if (daysDiff < 70) {
            status = "Red";
        } else {
            status = "Yellow";
        }
      
        
    } catch (java.text.ParseException e) {
        // Если не удалось распарсить даты, возвращаем Unknown
      //  System.err.println("Fehller beim Parsen des Datums: " + e.getMessage());
       // status = "Grey";
    }
    
    return status;
}
 

public Color getColorDirekt(){
  String a = this.getWunschLieferDatum();
   String b = this.getAnforderungDatum();
    String wareneingangBS = this.getWunschterminWareneingang();
    
    Color color ;
   
      if (wareneingangBS != null && !wareneingangBS.trim().isEmpty()) {
             color = new Color(166, 245, 184);
              // System.out.println("Status Wareneingang in IF  &     : " + wareneingangBS + "#######################################");
    return color;
               } 

    if (a == null || a.trim().isEmpty() || b == null || b.trim().isEmpty()) {
        return new Color(204, 205, 192);
    }
    
    
    
    try {
        // Парсим даты из формата "dd.MM.yy"
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd.MM.yy");
        formatter.setLenient(false);
        java.util.Date wunschDate = formatter.parse(a);
        java.util.Date anforderungDate = formatter.parse(b);
        
    
        // Вычисляем разность в миллисекундах
        long timeDiff = wunschDate.getTime() - anforderungDate.getTime();
        
        // Конвертируем в дни и устанавливаем цвета для кнопок с заявками
        long daysDiff = timeDiff / (1000 * 60 * 60 * 24);
       // System.out.println("DAYS Difference: " + daysDiff);
        // Определяем статус на основе разности в днях
        
        if (daysDiff >= 90) {
            color = new Color(199, 242, 209);
            setStatusEinkauf("Green");
            //GREEN
        } else if (daysDiff < 70) {
            color = new Color(245, 192, 185);
            setStatusEinkauf("Red");
           // System.out.println("STATUS :        " +  getStatusEinkauf());
             // RED
        } else {
            color = new Color(240, 239, 165); //YELLOW
            setStatusEinkauf("Yellow");
            
        }
         
           
        
    } catch (Exception e) {
        // Если не удалось распарсить даты, возвращаем Unknown
     //  System.err.println("Fehller beim Parsen des Datums: " + e.getMessage());
       color = new Color(204, 205, 192);
      //  System.out.println("COLOR 4");  //WHITE
    }
    
    return color;
}

}

