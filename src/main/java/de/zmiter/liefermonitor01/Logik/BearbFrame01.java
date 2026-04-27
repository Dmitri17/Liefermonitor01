/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.Artikel;
import de.zmiter.liefermonitor01.DBObjekte.Lieferung;
import de.zmiter.liefermonitor01.DBObjekte.PersonUser;
import java.util.ArrayList;


/**
 *
 * @author lepeschko
 */
public class BearbFrame01 extends BearbForm  {
    ArrayList<Artikel> artikelListe = new ArrayList<>();
   
    
    public BearbFrame01(DBManager dbManager, Lieferung lief, PersonUser person) {
        super(dbManager, lief, person);
        this.persistBeschreibungFromTextArea = false;
        artikelListe = readArtikelinDB(super.lieferung.getAnfordNummer());
        jTextArea1.setText(formatArtikelListAsText(artikelListe));
      
        this.setTitle("Thyssen Schachtbau GmbH");
    }

    
    /**
     * Загружает из БД список статей (Artikel) по номеру заявки (Anforderung).
     * @param anford номер заявки (AnfordNummer)
     * @return список статей для данной заявки
     */
    ArrayList<Artikel> readArtikelinDB(String anford) {
        if (anford == null || anford.trim().isEmpty()) {
            return new ArrayList<>();
        }
        return dbManager.getArtikelByAnford(anford);
    }

    /**
     * Форматирует список артикулов в текст для отображения в jTextArea1.
     * @param list список статей из lager
     * @return многострочный текст — список артикулов (Nr., Material DE/EN, Menge, Einheit)
     */
    private String formatArtikelListAsText(ArrayList<Artikel> list) {
        if (list == null || list.isEmpty()) {
            return "(Keine Artikel für diese Anforderung in der Lager-DB)"; 
        }
        StringBuilder sb = new StringBuilder();
        int nr = 1;
        for (Artikel a : list) {
            String de = a.getMaterial_DE() != null ? a.getMaterial_DE() : "";
            String en = a.getMaterial_EN() != null ? a.getMaterial_EN() : "";
            String mat = de.isEmpty() && en.isEmpty() ? "—" : (de + (de.isEmpty() || en.isEmpty() ? "" : " / ") + en);
            String menge = a.getMenge_Baustelle_gel() != 0 ? String.valueOf(a.getMenge_Baustelle_gel()) : (a.getMenge() != 0 ? String.valueOf(a.getMenge()) : "");
            String einheit = a.getEinheit() != null && !a.getEinheit().isEmpty() ? a.getEinheit() : "";
            sb.append(nr).append(". ").append(mat);
            if (!menge.isEmpty()) {
                sb.append("  —  ").append(menge);
                if (!einheit.isEmpty()) sb.append(" ").append(einheit);
            }
            sb.append("\n");
            nr++;
        }
        return sb.toString().trim();
    }

    /**
     * После применения полей формы к Lieferung синхронизирует значения формы
     * с каждым Artikel в artikelListe и сохраняет изменения в БД (lager).
     */
    @Override
    protected void afterFormAppliedToLieferung() {
        if (this.artikelListe == null || this.artikelListe.isEmpty()) {
            return;
        }
        String supplier = lieferant_tFKommentar01 != null ? lieferant_tFKommentar01.getText() : "";
        String colli = jTextField14 != null ? jTextField14.getText() : "";
        String anfordrung =tFAgreementNR != null ? tFAgreementNR.getText() : "";
        String container_Number = jTextField16 != null ? jTextField16.getText() : "";
        String booking_Number = jTextField4 != null ? jTextField4.getText() : "";
        String add_Info = jTextField9 != null ? jTextField9.getText() : "";
        String weightStr = formatFormattedFieldValue(jFormattedTextField15);
        String banfNrArt =  tFAnforderungNR != null ? tFAnforderungNR.getText() : "";
        String date_of_Arrival = getWareneingangTSFaktFieldText();
         String packListe = getPackListe();

        for (Artikel a : this.artikelListe) {
            a.setSupplier(supplier);
            a.setColli(colli);
            a.setAnfordrung(anfordrung);
            a.setContainer_Number(container_Number);
            a.setBooking_Number(booking_Number);
            a.setAdd_Info(add_Info);
            a.setWeight(weightStr);
            a.setBanfNrArt(banfNrArt);
            a.setDate_of_Arrival(date_of_Arrival);
            a.setPackListe(packListe);
            a.setInfo02(packListe);  // в БД колонка info02 хранит значение из jTextField15 (PL)
        }
        dbManager.updateArtikelList(this.artikelListe);
    }

    /** Преобразует значение JFormattedTextField (Number/Date) в строку для Artikel. */
    private String formatFormattedFieldValue(javax.swing.JFormattedTextField field) {
        if (field == null) return "";
        Object value = field.getValue();
        if (value == null) return field.getText() != null ? field.getText() : "";
        return value.toString();
    }
}
