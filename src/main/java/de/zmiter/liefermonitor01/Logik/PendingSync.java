package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.Artikel;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Speichert Änderungen (z. B. Artikel-Liste), die offline erstellt wurden,
 * und wendet sie bei der nächsten Verbindung zur Datenbank an.
 */
public class PendingSync {

    private static final String CACHE_DIR_NAME = ".Liefermonitor01";
    private static final String CACHE_SUBDIR = "cache";
    private static final String FILE_PENDING_ARTIKEL = "pending_artikel.cache";

    private static Path getCacheDir() {
        String userHome = System.getProperty("user.home");
        if (userHome == null) userHome = ".";
        Path dir = Paths.get(userHome, CACHE_DIR_NAME, CACHE_SUBDIR);
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            System.err.println("PendingSync: Konnte Verzeichnis nicht anlegen: " + e.getMessage());
        }
        return dir;
    }

    /** Speichert eine Liste von Artikeln zur späteren Synchronisation (offline gespeichert). */
    public static void savePendingArtikelliste(List<Artikel> list) {
        if (list == null) return;
        Path file = getCacheDir().resolve(FILE_PENDING_ARTIKEL);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            oos.writeObject(new ArrayList<>(list));
            oos.flush();
        } catch (IOException e) {
            System.err.println("PendingSync: Fehler beim Speichern der ausstehenden Artikel: " + e.getMessage());
        }
    }

    /** Gibt true zurück, wenn ausstehende Artikel-Änderungen vorhanden sind. */
    public static boolean hasPendingArtikelliste() {
        return Files.isRegularFile(getCacheDir().resolve(FILE_PENDING_ARTIKEL));
    }

    /**
     * Wendet ausstehende Artikel-Änderungen auf die DB an und löscht die Pending-Datei.
     * @param dbManager verbundener DBManager
     * @return Anzahl erfolgreich synchronisierter Artikel, -1 bei Fehler
     */
    @SuppressWarnings("unchecked")
    public static int replayPendingArtikelliste(DBManager dbManager) {
        if (dbManager == null || !dbManager.isConnectionValid()) return -1;
        Path file = getCacheDir().resolve(FILE_PENDING_ARTIKEL);
        if (!Files.isRegularFile(file)) return 0;
        List<Artikel> list;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            Object obj = ois.readObject();
            if (!(obj instanceof ArrayList)) return -1;
            list = (ArrayList<Artikel>) obj;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("PendingSync: Fehler beim Lesen der ausstehenden Artikel: " + e.getMessage());
            return -1;
        }
        int ok = 0;
        for (Artikel a : list) {
            try {
                if (a.getId() > 0) {
                    if (dbManager.updateArtikel(a)) ok++;
                } else {
                    if (dbManager.addArtikelAndReturnId(a) > 0) ok++;
                }
            } catch (Exception e) {
                System.err.println("PendingSync: Fehler bei Artikel ID " + a.getId() + ": " + e.getMessage());
            }
        }
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) { }
        return ok;
    }
}
