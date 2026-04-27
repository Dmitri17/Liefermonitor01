package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.Artikel;
import de.zmiter.liefermonitor01.DBObjekte.Lieferung;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Lokaler Cache für Offline-Nutzung: speichert und lädt Listen von Artikeln und Lieferungen,
 * wenn keine Verbindung zur Datenbank besteht.
 */
public class OfflineCache {

    private static final String CACHE_DIR_NAME = ".Liefermonitor01";
    private static final String CACHE_SUBDIR = "cache";
    private static final String FILE_ARTIKEL = "artikelliste.cache";
    private static final String FILE_LIEFERUNGEN = "lieferungen.cache";
    private static final String FILE_ARTIKEL_TIME = "artikelliste.time";
    private static final String FILE_LIEFERUNGEN_TIME = "lieferungen.time";

    private static Path getCacheDir() {
        String userHome = System.getProperty("user.home");
        if (userHome == null) userHome = ".";
        Path dir = Paths.get(userHome, CACHE_DIR_NAME, CACHE_SUBDIR);
        try {
            Files.createDirectories(dir);
        } catch (IOException e) {
            System.err.println("OfflineCache: Konnte Cache-Verzeichnis nicht anlegen: " + e.getMessage());
        }
        return dir;
    }

    /** Speichert die Artikel-Liste und den Zeitstempel. */
    public static void saveArtikelliste(List<Artikel> list) {
        if (list == null) return;
        Path dir = getCacheDir();
        Path file = dir.resolve(FILE_ARTIKEL);
        Path timeFile = dir.resolve(FILE_ARTIKEL_TIME);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            oos.writeObject(new ArrayList<>(list));
            oos.flush();
        } catch (IOException e) {
            System.err.println("OfflineCache: Fehler beim Speichern der Artikelliste: " + e.getMessage());
            return;
        }
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(timeFile.toFile()))) {
            dos.writeLong(System.currentTimeMillis());
        } catch (IOException e) {
            // Zeitstempel optional
        }
    }

    /** Lädt die gecachte Artikel-Liste; null wenn kein Cache vorhanden oder Fehler. */
    @SuppressWarnings("unchecked")
    public static ArrayList<Artikel> loadArtikelliste() {
        Path file = getCacheDir().resolve(FILE_ARTIKEL);
        if (!Files.isRegularFile(file)) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) return (ArrayList<Artikel>) obj;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("OfflineCache: Fehler beim Laden der Artikelliste: " + e.getMessage());
        }
        return null;
    }

    /** Zeitstempel des letzten Artikellisten-Caches (Millisekunden) oder 0. */
    public static long getLastSyncTimeArtikelliste() {
        Path timeFile = getCacheDir().resolve(FILE_ARTIKEL_TIME);
        if (!Files.isRegularFile(timeFile)) return 0;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(timeFile.toFile()))) {
            return dis.readLong();
        } catch (IOException e) {
            return 0;
        }
    }

    /** Speichert die Lieferungen-Liste und den Zeitstempel. */
    public static void saveLieferungen(List<Lieferung> list) {
        if (list == null) return;
        Path dir = getCacheDir();
        Path file = dir.resolve(FILE_LIEFERUNGEN);
        Path timeFile = dir.resolve(FILE_LIEFERUNGEN_TIME);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file.toFile()))) {
            oos.writeObject(new ArrayList<>(list));
            oos.flush();
        } catch (IOException e) {
            System.err.println("OfflineCache: Fehler beim Speichern der Lieferungen: " + e.getMessage());
            return;
        }
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(timeFile.toFile()))) {
            dos.writeLong(System.currentTimeMillis());
        } catch (IOException e) { }
    }

    /** Lädt die gecachte Lieferungen-Liste; null wenn kein Cache vorhanden oder Fehler. */
    @SuppressWarnings("unchecked")
    public static ArrayList<Lieferung> loadLieferungen() {
        Path file = getCacheDir().resolve(FILE_LIEFERUNGEN);
        if (!Files.isRegularFile(file)) return null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file.toFile()))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList) return (ArrayList<Lieferung>) obj;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("OfflineCache: Fehler beim Laden der Lieferungen: " + e.getMessage());
        }
        return null;
    }

    /** Zeitstempel des letzten Lieferungen-Caches (Millisekunden) oder 0. */
    public static long getLastSyncTimeLieferungen() {
        Path timeFile = getCacheDir().resolve(FILE_LIEFERUNGEN_TIME);
        if (!Files.isRegularFile(timeFile)) return 0;
        try (DataInputStream dis = new DataInputStream(new FileInputStream(timeFile.toFile()))) {
            return dis.readLong();
        } catch (IOException e) {
            return 0;
        }
    }

    /** Formatiert Zeitstempel für Anzeige (z. B. "12.03.2025 14:30"). */
    public static String formatSyncTime(long millis) {
        if (millis <= 0) return "–";
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
        return sdf.format(new java.util.Date(millis));
    }
}
