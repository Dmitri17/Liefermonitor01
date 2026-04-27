/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.DBObjekte;

import java.io.Serializable;

/**
 *
 * @author lepeschko
 */
public class Artikel implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id = 0; // ID записи в базе данных
    
    // Строчные поля
    private String material_EN = "";
    private String material_DE = "";
    private String dimention = "";
    private String supplier = "";
    private String colli = "";
    private String packListe = "";
    private String order_Number = "";
    private float menge_gelief_an_BS = 0;
    private String anfordrung = "";
    private String reloaded_in = "";
    private String container_Number = "";
    private String booking_Number = "";
    private String add_Info = "";
    private String good_Receipt = "";
    private String amount_of_Containers = "";
    private String weight = "";
    private String banfNrArt = ""; // BANF Nr. im Artikel
    private String shipment = "";
    private String date_of_Arrival = "";
    private String invoice_Proforma = "";
    private float sumMenge = 0;  // суммарное количество товара в заказе (Anforderung)
    private float menge_Baustelle_gel = 0;// gelieferte Menge fuer Baustelle
    private String einheit = "";
       private String lagerOrt = "";
          private String lagerMenge = "";
          private float preisEuro = 0;
              private String info01 = "";
                private String info02 = "";// Hier wird die Packliste (PL) gespeichert
                   private String info03 = ""; //Projektname mit ##Projektname##
    
    /**
     * Конструктор по умолчанию
     */
    public Artikel() {
    }
    public Artikel genEinzelLieferMenge( Artikel art, float einzMng){
        Artikel einzArtikel = new Artikel();
        
        einzArtikel.material_DE = art.material_DE;
        einzArtikel.material_EN = art.material_EN;
        einzArtikel.dimention = art.dimention;
        einzArtikel.supplier = art.supplier;
        einzArtikel.colli = art.colli;
        einzArtikel.packListe = art.packListe;
        einzArtikel.order_Number = art.order_Number;
        einzArtikel.anfordrung = art.anfordrung;
        einzArtikel.reloaded_in = art.reloaded_in;
        einzArtikel.container_Number = art.container_Number;
        einzArtikel.booking_Number = art.booking_Number;
        einzArtikel.add_Info = art.add_Info;
        einzArtikel.good_Receipt = art.good_Receipt;
        einzArtikel.amount_of_Containers = art.amount_of_Containers;
        einzArtikel.weight = art.weight;
        einzArtikel.banfNrArt = art.banfNrArt;
        einzArtikel.shipment = art.shipment;
        einzArtikel.date_of_Arrival = art.date_of_Arrival;
        einzArtikel.invoice_Proforma = art.invoice_Proforma;
        einzArtikel.sumMenge = art.sumMenge;
        einzArtikel.menge_Baustelle_gel = einzMng;
        einzArtikel.einheit = art.einheit;
          einzArtikel.lagerOrt = art.lagerOrt;
            einzArtikel.lagerMenge = art.lagerMenge;
            einzArtikel.preisEuro = art.preisEuro;
              einzArtikel.info01 = art.info01;
                einzArtikel.info02 = art.info02;
                  einzArtikel.info03 = art.info03;
        
        return einzArtikel;
    }
    
    /**
     * Конструктор с основными параметрами
     */
    public Artikel(String material_EN, String material_DE, String supplier) {
        this.material_EN = material_EN != null ? material_EN : "";
        this.material_DE = material_DE != null ? material_DE : "";
        this.supplier = supplier != null ? supplier : "";
    }
    
    // Геттеры и сеттеры для ID
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    // Геттеры
    public String getMaterial_EN() {
        return material_EN;
    }
    
    public String getMaterial_DE() {
        return material_DE;
    }
    
    public String getDimention() {
        return dimention;
    }
    
    public String getSupplier() {
        return supplier;
    }
    
    public String getColli() {
        return colli;
    }
     public String getPackListe() {
        return packListe;
    }
    
    public String getOrder_Number() {
        return order_Number;
    }

    public float getMenge_gelief_an_BS() {
        return menge_gelief_an_BS;
    }
    
    public String getAnfordrung() {
        return anfordrung;
    }
    
    public String getReloaded_in() {
        return reloaded_in;
    }
    
    public String getContainer_Number() {
        return container_Number;
    }
    
    public String getBooking_Number() {
        return booking_Number;
    }
    
    public String getAdd_Info() {
        return add_Info;
    }
    
    public String getGood_Receipt() {
        return good_Receipt;
    }
    
    public String getAmount_of_Containers() {
        return amount_of_Containers;
    }
    
    public String getWeight() {
        return weight;
    }
    
    public String getBanfNrArt() {
        return banfNrArt;
    }
    
    public String getShipment() {
        return shipment;
    }
    
    public String getDate_of_Arrival() {
        return date_of_Arrival;
    }
    
    public String getInvoice_Proforma() {
        return invoice_Proforma;
    }
     public String getLagerOrt() {
        return lagerOrt;
    }
      public String getLagerMenge() {
        return lagerMenge;
    }
       public String getInfo01() {
        return info01;
    }
        public String getInfo02() {
        return info02;
    }
     public String getInfo03() {
        return info03;
    }
       public float getPreisEuro() {
        return preisEuro;
    }
    // Сеттеры
    public void setMaterial_EN(String material_EN) {
        this.material_EN = material_EN != null ? material_EN : "";
    }
    
    public void setMaterial_DE(String material_DE) {
        this.material_DE = material_DE != null ? material_DE : "";
    }
    
    public void setDimention(String dimention) {
        this.dimention = dimention != null ? dimention : "";
    }
    
    public void setSupplier(String supplier) {
        this.supplier = supplier != null ? supplier : "";
    }
    
    public void setColli(String colli) {
        this.colli = colli != null ? colli : "";
    }
     public void setPackListe(String packListe) {
        this.packListe = packListe != null ? packListe : "";
    }
    public void setOrder_Number(String order_Number) {
        this.order_Number = order_Number != null ? order_Number : "";
    }

    public void setMenge_gelief_an_BS(float menge_gelief_an_BS) {
        this.menge_gelief_an_BS = menge_gelief_an_BS;
    }
    
    public void setAnfordrung(String anfordrung) {
        this.anfordrung = anfordrung != null ? anfordrung : "";
    }
    
    public void setReloaded_in(String reloaded_in) {
        this.reloaded_in = reloaded_in != null ? reloaded_in : "";
    }
    
    public void setContainer_Number(String container_Number) {
        this.container_Number = container_Number != null ? container_Number : "";
    }
    
    public void setBooking_Number(String booking_Number) {
        this.booking_Number = booking_Number != null ? booking_Number : "";
    }
    
    public void setAdd_Info(String add_Info) {
        this.add_Info = add_Info != null ? add_Info : "";
    }
    
    public void setGood_Receipt(String good_Receipt) {
        this.good_Receipt = good_Receipt != null ? good_Receipt : "";
    }
    
    public void setAmount_of_Containers(String amount_of_Containers) {
        this.amount_of_Containers = amount_of_Containers != null ? amount_of_Containers : "";
    }
    
    public void setWeight(String weight) {
        this.weight = weight != null ? weight : "";
    }
    
    public void setBanfNrArt(String banfNrArt) {
        this.banfNrArt = banfNrArt != null ? banfNrArt : "";
    }
    
    public void setShipment(String shipment) {
        this.shipment = shipment != null ? shipment : "";
    }
    
    public void setDate_of_Arrival(String date_of_Arrival) {
        this.date_of_Arrival = date_of_Arrival != null ? date_of_Arrival : "";
    }
    
    public void setInvoice_Proforma(String invoice_Proforma) {
        this.invoice_Proforma = invoice_Proforma != null ? invoice_Proforma : "";
    }
    
    public float getMenge() {
        return sumMenge;
    }
    
    public void setMenge(float menge) {
        this.sumMenge = menge;
    }
    
    public float getMenge_Baustelle_gel() {
        return menge_Baustelle_gel;
    }
    
    public void setMenge_Baustelle_gel(float menge_Baustelle_gel) {
        this.menge_Baustelle_gel = menge_Baustelle_gel;
    }
    
    public String getEinheit() {
        return einheit;
    }
    
    public void setEinheit(String einheit) {
        this.einheit = einheit != null ? einheit : "";
    }

public void setLagerOrt(String lagerOrt) {
        this.lagerOrt = lagerOrt != null ? lagerOrt : "";
    }
public void setLagerMenge(String lagerMenge) {
        this.lagerMenge = lagerMenge != null ? lagerMenge : "";
    }

public void setInfo01(String info01) {
        this.info01 = info01 != null ? info01 : "";
    }
public void setInfo02(String info02) {
        this.info02 = info02 != null ? info02 : "";
    }
public void setInfo03(String info03) {
        this.info03 = info03 != null ? info03 : "";
    }
public void setPreisEuro(float preis) {
        this.preisEuro = preis;
    }
}
 