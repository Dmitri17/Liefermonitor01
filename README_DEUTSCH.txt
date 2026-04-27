Anleitung zur Verwendung der BAT-Dateien für Liefermonitor01
============================================================

Die folgenden BAT-Dateien wurden für die einfache Projektverwaltung erstellt:

1. run.bat - Hauptstartdatei
   - Überprüft Java-Installation
   - Überprüft JAR-Datei
   - Startet Anwendung mit detaillierter Diagnose
   - Zeigt verständliche Fehlermeldungen

2. start.bat - Schnellstart
   - Einfacher Start ohne Überprüfungen
   - Für erfahrene Benutzer

3. build.bat - Projekt erstellen
   - Überprüft Maven-Installation
   - Führt mvn clean package aus
   - Zeigt Build-Ergebnis

4. rebuild-and-run.bat - Vollständiger Zyklus
   - Erstellt zuerst das Projekt
   - Startet dann die Anwendung

5. start-silent.bat - Start ohne Konsole
   - Minimales schwarzes Fenster
   - Leitet Ausgabe zu null um

6. start-hidden.vbs - Vollständig versteckter Start
   - Keine Konsolenfenster
   - Startet nur GUI-Anwendung

7. start-minimal.bat - Minimales Fenster
   - Kleines grünes Fenster
   - Zeigt nur Titel

8. start-silent.ps1 - PowerShell-Skript
   - Vollständig versteckter Start
   - Erfordert PowerShell

VERWENDUNG:
===========

Für den ersten Start:
1. Doppelklicken Sie auf rebuild-and-run.bat
2. Warten Sie auf den Abschluss der Erstellung
3. Die Anwendung startet automatisch

Für normalen Start (nach der Erstellung):
1. Doppelklicken Sie auf run.bat

Für schnellen Start:
1. Doppelklicken Sie auf start.bat

Für Start OHNE schwarzes Fenster:
1. start-hidden.vbs - vollständig versteckter Start (empfohlen)
2. start-silent.bat - minimales Fenster
3. start-minimal.bat - kleines grünes Fenster
4. start-silent.ps1 - PowerShell-Skript

ANFORDERUNGEN:
==============
- Java 17 oder höher
- Maven (für die Erstellung)
- Zugang zur SQL Server-Datenbank

PROBLEME:
=========
Bei Fehlern:
1. Überprüfen Sie Java-Installation: java -version
2. Überprüfen Sie Maven-Installation: mvn -version
3. Überprüfen Sie Datenbankverbindung
4. Führen Sie run.bat für detaillierte Diagnose aus

DATENBANKVERBINDUNG:
===================
- Server: 172.21.3.104:1433
- Datenbank: deliveryapp
- Benutzername: lepeschko2
- Passwort: dmitri1969!

HINWEISE:
=========
- Verwenden Sie start-hidden.vbs für den saubersten Start
- Bei Problemen mit der Datenbankverbindung prüfen Sie die Netzwerkverbindung
- Für Debugging verwenden Sie run.bat
- Alle JAR-Dateien befinden sich im target-Ordner

SUPPORT:
========
Bei Problemen melden Sie sich über das Menü "Hilfe?" -> "Problem melden"
oder kontaktieren Sie: lepeschko.dmitri@ts-gruppe.com
