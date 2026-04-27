IMPORT MAPPING: EXCEL -> DATABASE -> BearbForm
==============================================

Kontext
-------
Dieses Dokument beschreibt die aktuelle Zuordnung beim SAP-Excel-Import
in DBManager.processExcelFileWithResult(...).

Es gibt zwei Zieltabellen:
1) lieferung   (Kopf der Lieferung / Bestellung)
2) lager       (Artikelpositionen)

BearbForm arbeitet primaer mit "lieferung".


1) Mapping in Tabelle "lieferung" (mit Bezug zu BearbForm)
-----------------------------------------------------------

Excel Spalte A (Zeile "Einkaufsbeleg ...")
-> lieferung.anfordNummer
-> BearbForm: tFAgreementNR (Agreement/BS Anford. Nr.)

Excel Spalte A + ermitteltes Datum
-> lieferung.bestellNummer (Format: "Nummer (Datum)")
-> BearbForm: jTextField4 (Bestellungsnummer)

Excel Spalte D (index 3, Projekt)
-> lieferung.abteilung (projectName)
-> BearbForm: Projektname/Info (Projektbezug)

Excel Spalte O/P (Datumsanteil, normalisiert)
-> lieferung.bestelltAm
-> BearbForm: jFormattedTextField5 (Bestellt am)

Excel Spalte P (Lieferantentext, Teil nach Datum)
-> lieferung.lieferantName
-> BearbForm: lieferant_tFKommentar01 (Lieferant)

Excel Spalte Y (Menge) + Spalte AA (Preis)
-> berechnete Summen in lieferung.preisEinkauf und lieferung.summeMat_Leist_cents
-> BearbForm:
   - jFormattedTextField17 (Preis)
   - jFormattedTextField16 (Summe fuer Material/Leistung)

Hinweis zu BANF:
Beim SAP-Import wird fuer lieferung.agrNummer initial ein Platzhalter "__||"
gesetzt. BANF kann spaeter durch Sync/Anreicherung ersetzt werden.
-> BearbForm: tFAnforderungNR (BANF Nr.)


2) Mapping in Tabelle "lager" (Artikel) - kein direktes BearbForm-Feld
-----------------------------------------------------------------------

Excel Spalte A (Positionskontext + Beleg)
-> lager.booking_Number
-> lager.order_Number

Excel Spalte R (index 17, Materialtext)
-> lager.material_DE
-> lager.material_EN

Excel Spalte X (index 23)
-> lager.lagerOrt

Excel Spalte Y (index 24)
-> lager.menge
-> lager.einheit (mit Fallback aus Spalte Z / index 25)

Excel Spalte AA (index 26)
-> lager.preisEuro

Bild-Hinweis in SAP-Spalte "Bestellentwicklung / Abrufdoku"
-> lager.einzelMenge (Gelieferte Menge in MH)

Packliste / Verbrauchte Menge (nach aktuellen Fixes)
-> lager.info02 (Packliste): nur aus Spalte Y, ohne Preisanteil
-> lager.info01 (verbrauchte Menge): beim Import leer


3) Wichtige spaetere Nachbearbeitung (Post-Import Sync)
--------------------------------------------------------

Der Post-Import-Sync kann:
- Felder in lager aus lieferung nachziehen (bei leeren Zielwerten),
- historische Importfehler korrigieren:
  * Packliste: Teil nach '|' entfernen
  * Verbrauchte Menge: versehentliche Bestellnummern in info01 leeren
  * bestellNummer in lieferung: technischen Suffix "SAP"/"(SAP)" am Ende entfernen


4) Kurzfazit
------------

- BearbForm zeigt/editiert vor allem Daten aus "lieferung".
- Artikeldetails fuer "lager" sieht/editiert man in ArtikelListe.
- Import schreibt Kopf- und Positionsdaten getrennt; manche Felder werden
  bewusst spaeter durch Syncs verfeinert.

