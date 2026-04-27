package de.zmiter.liefermonitor01.Logik;

import de.zmiter.liefermonitor01.DBObjekte.Lieferung;
import de.zmiter.liefermonitor01.DBObjekte.PersonUser;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LinearGradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

/**
 * Gantt-Uebersicht ueber Lieferungen (Start = AnforderungDatum, Ende = auswaehlbares Fakt-Datum).
 *
 * Basis-Implementierung без сторонних chart-библиотек:
 * - кастомная отрисовка в JPanel
 * - бары кликабельны (открывается BearbFrame01)
 */
public class LieferGanttFenster extends JFrame {

    public enum EndFieldMode {
        WUNSCH_LIEFER_DATUM("Anforderungsdatum -> Wunschliefertermin"),
        BESTELLT_AM("Anforderungsdatum -> vom EinkaufBestellt"),
        MIT_FELD6_ENDE_FELD2("WareneingangTS_plan -> Wunschliefertermin"),
        ALLE_LIEFERUNGEN("Alle Lieferungen anzeigen (auch ohne Datum)");

        private final String uiText;

        EndFieldMode(String uiText) {
            this.uiText = uiText;
        }

        @Override
        public String toString() {
            return uiText;
        }
    }

    private final PersonUser person;
    private final DBManager dbManager;

    private ArrayList<Lieferung> lieferList;
    private EndFieldMode endFieldMode = EndFieldMode.WUNSCH_LIEFER_DATUM;

    private final GanttChartPanel chartPanel;
    private final JComboBox<EndFieldMode> endFieldCombo;
    private final JLabel statusLabel = new JLabel(" ");

    public LieferGanttFenster(PersonUser person) {
        this(person, null);
    }

    public LieferGanttFenster(PersonUser person, ArrayList<Lieferung> lieferList) {
        super("Lieferungen Gantt Uebersicht");
        this.person = person;
        this.dbManager = new DBManager();
        this.lieferList = lieferList;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(1200, 700);
        this.setLocationRelativeTo(null);
        

        JPanel root = new JPanel(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        topPanel.setBackground(new Color(204, 255, 204));

        JButton btnReload = new JButton("Aktualisieren");
        btnReload.addActionListener(e -> reloadDataFromDbOrCache());

        endFieldCombo = new JComboBox<>(EndFieldMode.values());
        endFieldCombo.setSelectedItem(this.endFieldMode);
        endFieldCombo.addActionListener(e -> {
            EndFieldMode selected = (EndFieldMode) endFieldCombo.getSelectedItem();
            if (selected != null) {
                endFieldMode = selected;
                rerender();
            }
        });

        topPanel.add(new JLabel("Basis der Darstellung: "));
        topPanel.add(endFieldCombo);
        topPanel.add(btnReload);
        topPanel.add(statusLabel);
        topPanel.add(new JLabel("   Legende: "));
        topPanel.add(createLegendLabel("Rot (< 70 Tage)", new Color(245, 201, 186)));
        topPanel.add(createLegendLabel("Gelb (70-89 Tage)", new Color(247, 246, 137)));
        topPanel.add(createLegendLabel("Grün (>= 90 Tage)", new Color(160, 237, 109)));
       // topPanel.add(createLegendLabel("Grau (unvollständige Info über die Termine)", new Color(204, 205, 192)));

        chartPanel = new GanttChartPanel(
                new GanttChartPanel.BarColorResolver() {
                    @Override
                    public Color resolveBarColor(Lieferung l) {
                        return colorForStatus(l);
                    }
                },
                new Consumer<Lieferung>() {
                    @Override
                    public void accept(Lieferung l) {
                        if (l == null) return;
                        try {
                            BearbFrame01 form = new BearbFrame01(dbManager, l, LieferGanttFenster.this.person);
                            form.setLocation(400, 10);
                            form.setVisible(true);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(
                                    LieferGanttFenster.this,
                                    "Fehler beim Oeffnen der Bearb-Ansicht:\n" + ex.getMessage(),
                                    "Fehler",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                }
        );

        JScrollPane scrollPane = new JScrollPane(chartPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        root.add(topPanel, BorderLayout.NORTH);
        root.add(scrollPane, BorderLayout.CENTER);

        setContentPane(root);

        if (this.lieferList == null) {
            reloadDataFromDbOrCache();
        } else {
            rerender();
        }
    }

    private void reloadDataFromDbOrCache() {
        try {
            ArrayList<Lieferung> loaded = null;
            if (dbManager.isConnectionValid()) {
                loaded = dbManager.getAllLieferungen();
                if (loaded != null && !loaded.isEmpty()) {
                    OfflineCache.saveLieferungen(loaded);
                    statusLabel.setText(" - Daten aus DB geladen: " + loaded.size());
                }
            }
            if (loaded == null || loaded.isEmpty()) {
                ArrayList<Lieferung> cached = OfflineCache.loadLieferungen();
                if (cached != null && !cached.isEmpty()) {
                    loaded = cached;
                    long t = OfflineCache.getLastSyncTimeLieferungen();
                    statusLabel.setText(" - Offline (Stand: " + OfflineCache.formatSyncTime(t) + ")");
                }
            }

            if (loaded == null || loaded.isEmpty()) {
                loaded = new ArrayList<>();
                statusLabel.setText(" - Keine Daten verfügbar");
            }
            this.lieferList = loaded;
            rerender();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Fehler beim Laden der Lieferungen:\n" + ex.getMessage(),
                    "Fehler",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void rerender() {
        ArrayList<Lieferung> list = this.lieferList != null ? this.lieferList : new ArrayList<>();
        chartPanel.setData(list, endFieldMode);
    }

    private static Color colorForStatus(Lieferung l) {
        if (l == null) return new Color(204, 205, 192);
        // Wenn Wareneingang_Fakt (jFormattedTextField9 in BearbForm) gesetzt ist,
        // markieren wir den Balken als "abgeschlossen" in dunkelgruen.
        String wareneingangFakt = l.getWareneingang_Fakt();
        if (wareneingangFakt != null && !wareneingangFakt.trim().isEmpty()) {
            return new Color(94, 171, 43);
        }
        String status = l.getStatusBestellung();
        if (status == null) return new Color(204, 205, 192);

        if ("Red".equalsIgnoreCase(status)) return new Color(245, 201, 186);
        if ("Yellow".equalsIgnoreCase(status)) return new Color(244, 245, 186); // ähnlich zum bisherigen UI
        if ("Green".equalsIgnoreCase(status)) return new Color(160, 237, 109);
        return new Color(204, 205, 192);
    }

    private static String safeSubstring(String s, int maxLen) {
        if (s == null) return "";
        String t = s.trim();
        if (t.length() <= maxLen) return t;
        return t.substring(0, maxLen) + "...";
    }

    private static JLabel createLegendLabel(String text, Color bgColor) {
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setBackground(bgColor);
        label.setBorder(BorderFactory.createEmptyBorder(3, 6, 3, 6));
        return label;
    }

    private static LocalDate parseDate(String raw) {
        if (raw == null) return null;
        String s = raw.trim();
        if (s.isEmpty()) return null;
        if ("00.00.00".equals(s) || "-".equals(s)) return null;

        // Иногда встречаются timestamps/длинные строки: берем первую "датную" часть.
        if (s.length() > 10) {
            s = s.substring(0, 10).trim();
        }
        if (s.length() > 8 && s.contains(" ")) {
            s = s.substring(0, s.indexOf(' ')).trim();
        }

        try {
            if (s.length() == 8) { // dd.MM.yy
                int day = Integer.parseInt(s.substring(0, 2));
                int month = Integer.parseInt(s.substring(3, 5));
                int yy = Integer.parseInt(s.substring(6, 8));
                int fullYear = 2000 + yy;
                return LocalDate.of(fullYear, month, day);
            }
            if (s.length() == 10) { // dd.MM.yyyy
                int day = Integer.parseInt(s.substring(0, 2));
                int month = Integer.parseInt(s.substring(3, 5));
                int yyyy = Integer.parseInt(s.substring(6, 10));
                return LocalDate.of(yyyy, month, day);
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private static class GanttRow {
        final Lieferung lieferung;
        final LocalDate start;
        final LocalDate end;
        final boolean noDateInfo;

        GanttRow(Lieferung lieferung, LocalDate start, LocalDate end, boolean noDateInfo) {
            this.lieferung = lieferung;
            this.start = start;
            this.end = end;
            this.noDateInfo = noDateInfo;
        }
    }

    private static class GanttChartPanel extends JPanel {
        interface BarColorResolver {
            Color resolveBarColor(Lieferung l);
        }

        private final BarColorResolver barColorResolver;
        private final Consumer<Lieferung> onBarClick;

        private final List<Bar> bars = new ArrayList<>();

        private LocalDate minDate;
        private LocalDate maxDate;

        private int pxPerDay = 4;
        private int rowHeight = 26;
        private int headerHeight = 38;
        private int topPadding = 6;

        private int leftMargin = 260;
        private int rightMargin = 30;

        /** Unter dieser Balkenbreite (px) nur flache Fuellung — Gradient wirkt sonst unscharf. */
        private static final int BAR_GRADIENT_MIN_WIDTH = 12;

        private static class Bar {
            final Lieferung lieferung;
            final boolean noDateInfo;
            Rectangle rect;

            Bar(Lieferung lieferung, boolean noDateInfo) {
                this.lieferung = lieferung;
                this.noDateInfo = noDateInfo;
            }
        }

        GanttChartPanel(BarColorResolver barColorResolver, Consumer<Lieferung> onBarClick) {
            this.barColorResolver = Objects.requireNonNull(barColorResolver);
            this.onBarClick = onBarClick;
            setBackground(Color.WHITE);
            setDoubleBuffered(true);

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    for (Bar b : bars) {
                        if (b.rect != null && b.rect.contains(e.getPoint())) {
                            if (onBarClick != null) onBarClick.accept(b.lieferung);
                            return;
                        }
                        // Linke Beschriftungszone (Titel der Lieferung) ebenfalls klickbar machen
                        if (b.rect != null
                                && e.getX() >= 0
                                && e.getX() < leftMargin
                                && e.getY() >= b.rect.y
                                && e.getY() <= (b.rect.y + b.rect.height)) {
                            if (onBarClick != null) onBarClick.accept(b.lieferung);
                            return;
                        }
                    }
                }
            });
        }

        void setData(ArrayList<Lieferung> data, EndFieldMode endFieldMode) {
            bars.clear();
            minDate = null;
            maxDate = null;

            if (data == null) data = new ArrayList<>();

            // Предварительно преобразуем в строки, чтобы корректно вычислить min/max.
            List<GanttRow> rows = new ArrayList<>();
            LocalDate today = LocalDate.now();
            for (Lieferung l : data) {
                if (l == null) continue;
                LocalDate start = parseDate(l.getAnforderungDatum());
                boolean noDateInfo = false;
                if (start == null) {
                    if (endFieldMode == EndFieldMode.ALLE_LIEFERUNGEN) {
                        start = today;
                        noDateInfo = true;
                    } else {
                        continue;
                    }
                }

                // End ist abhängig von JComboBox:
                // - Wunschliefertermin (jFormattedTextField2)
                // - Bestellt am (jFormattedTextField5)
                // - Nur Datensaetze mit Feld6 (wareneingangTS_plan), Ende = Feld2
                LocalDate end;
                if (endFieldMode == EndFieldMode.BESTELLT_AM) {
                    end = parseDate(l.getBestelltAm());
                } else if (endFieldMode == EndFieldMode.MIT_FELD6_ENDE_FELD2) {
                    // In diesem Modus anzeigen: nur Lieferungen mit gesetztem Feld1 (AnforderungDatum),
                    // Start = Feld6 (wareneingangTS_plan), Ende = Feld2 (wunschLieferDatum)
                    String feld1 = l.getAnforderungDatum();
                    if (feld1 == null || feld1.trim().isEmpty()) {
                        continue;
                    }
                    LocalDate startFromFeld6 = parseDate(l.getWareneingangTS_plan());
                    if (startFromFeld6 == null) {
                        continue;
                    }
                    start = startFromFeld6;
                    end = parseDate(l.getWunschLieferDatum());
                } else if (endFieldMode == EndFieldMode.ALLE_LIEFERUNGEN) {
                    end = parseDate(l.getWunschLieferDatum());
                } else {
                    end = parseDate(l.getWunschLieferDatum());
                }
                if (end == null) {
                    // Если завершение не заполнено, показываем "на точке старта" (короткий бар).
                    end = start;
                    if (endFieldMode == EndFieldMode.ALLE_LIEFERUNGEN) {
                        noDateInfo = true;
                    }
                }
                if (end.isBefore(start)) {
                    // На случай неправильного ввода поменяем местами.
                    LocalDate tmp = start;
                    start = end;
                    end = tmp;
                }

                rows.add(new GanttRow(l, start, end, noDateInfo));
                minDate = (minDate == null) ? start : minDate.isAfter(start) ? start : minDate;
                maxDate = (maxDate == null) ? end : maxDate.isBefore(end) ? end : maxDate;
            }

            // Damit "heute" (rote Linie) immer sichtbar ist, muss диапазон
            // текущей даты включать min/max, навіть wenn die Daten das nicht tun.
            if (minDate != null && today.isBefore(minDate)) {
                minDate = today;
            }
            if (maxDate != null && today.isAfter(maxDate)) {
                maxDate = today;
            }

            if (rows.isEmpty() || minDate == null || maxDate == null) {
                setPreferredSize(new Dimension(700, headerHeight + topPadding + 10));
                revalidate();
                repaint();
                return;
            }

            // Сортируем для стабильной визуализации: по старте, затем по номеру.
            rows.sort(new Comparator<GanttRow>() {
                @Override
                public int compare(GanttRow o1, GanttRow o2) {
                    int c = o1.start.compareTo(o2.start);
                    if (c != 0) return c;
                    String a1 = o1.lieferung.getAnfordNummer();
                    String a2 = o2.lieferung.getAnfordNummer();
                    if (a1 == null) a1 = "";
                    if (a2 == null) a2 = "";
                    return a1.compareTo(a2);
                }
            });

            long daysRange = ChronoUnit.DAYS.between(minDate, maxDate);
            if (daysRange < 0) daysRange = 0;
            int daysCount = (int) Math.min(Integer.MAX_VALUE, daysRange + 1);

            int contentWidth = leftMargin + (daysCount * pxPerDay) + rightMargin;
            int contentHeight = headerHeight + topPadding + (rows.size() * rowHeight) + 20;
            setPreferredSize(new Dimension(Math.max(900, contentWidth), contentHeight));

            // Предвычисляем прямоугольники баров для hit-testing.
            bars.clear();
            for (int i = 0; i < rows.size(); i++) {
                GanttRow r = rows.get(i);
                long startOffsetDays = ChronoUnit.DAYS.between(minDate, r.start);
                long endOffsetDays = ChronoUnit.DAYS.between(minDate, r.end);

                int x = leftMargin + (int) (startOffsetDays * pxPerDay);
                int wDays = (int) (endOffsetDays - startOffsetDays + 1);
                int w = Math.max(pxPerDay, wDays * pxPerDay);

                int y = headerHeight + topPadding + (i * rowHeight) + 4;
                int h = rowHeight - 8;

                Bar bar = new Bar(r.lieferung, r.noDateInfo);
                bar.rect = new Rectangle(x, y, w, h);
                bars.add(bar);
            }

            revalidate();
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(
                        java.awt.RenderingHints.KEY_ANTIALIASING,
                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON
                );

                if (minDate == null || maxDate == null || bars.isEmpty()) {
                    g2.setFont(getFont().deriveFont(Font.PLAIN, 14f));
                    g2.setColor(Color.DARK_GRAY);
                    g2.drawString("Keine Daten für die Anzeige.", 20, 30);
                    return;
                }

                g2.setStroke(new BasicStroke(1f));

                // Hintergrundzonen:
                // - linke Textspalte etwas dunkler
                // - Diagrammbereich (Balken) heller
                g2.setColor(new Color(236, 236, 236));
                g2.fillRect(0, 0, leftMargin, getHeight());
                g2.setColor(new Color(248, 248, 248));
                g2.fillRect(leftMargin, 0, Math.max(0, getWidth() - leftMargin), getHeight());

                // Заголовок + сетка
                long daysRange = ChronoUnit.DAYS.between(minDate, maxDate);
                if (daysRange < 0) daysRange = 0;
                int daysCount = (int) Math.min(Integer.MAX_VALUE, daysRange + 1);

                // Горизонтальная ось (линия)
                int axisY = headerHeight - 10;
                g2.setColor(new Color(220, 220, 220));
                g2.drawLine(leftMargin, axisY, leftMargin + (daysCount * pxPerDay), axisY);

                // Текущая дата (красная вертикальная линия)
                LocalDate today = LocalDate.now();
                if ((today.isAfter(minDate) || today.isEqual(minDate)) && (today.isBefore(maxDate) || today.isEqual(maxDate))) {
                    long offsetDays = ChronoUnit.DAYS.between(minDate, today);
                    int xToday = leftMargin + (int) (offsetDays * pxPerDay);
                    g2.setColor(Color.RED);
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawLine(xToday, axisY, xToday, getHeight());
                    // label (короткий), чтобы видно где сегодня
                    g2.setStroke(new BasicStroke(1f));
                    String label = today.format(DateTimeFormatter.ofPattern("dd.MM"));
                    g2.setColor(Color.RED.darker());
                    g2.setFont(getFont().deriveFont(Font.BOLD, 11f));
                    g2.drawString(label, xToday + 3, axisY - 3);
                }

                // Тикеры каждые 1 месяц (по границам месяцев)
                DateTimeFormatter tickFmt = DateTimeFormatter.ofPattern("MM/yy");
                LocalDate tickDate = minDate.withDayOfMonth(1);
                if (tickDate.isBefore(minDate)) {
                    tickDate = tickDate.plusMonths(1);
                }
                while (!tickDate.isAfter(maxDate)) {
                    long offsetDays = ChronoUnit.DAYS.between(minDate, tickDate);
                    int x = leftMargin + (int) (offsetDays * pxPerDay);
                    g2.setColor(new Color(235, 235, 235));
                    g2.drawLine(x, axisY, x, getHeight());
                    g2.setColor(Color.GRAY);
                    String label = tickDate.format(tickFmt);
                    g2.drawString(label, x + 2, headerHeight - 4);
                    tickDate = tickDate.plusMonths(1);
                }

                // Рисуем бары (leicht erhaben: Gradient, Rundung, Lichtkante)
                for (Bar b : bars) {
                    if (b.rect == null) continue;

                    Color fill = barColorResolver.resolveBarColor(b.lieferung);
                    drawVolumetricBar(g2, b.rect, fill);

                    // Подпись (короткая) внутри бара, если есть место
                    if (b.rect.width >= 60) {
                        String text = safeSubstring(
                                (b.lieferung.getAnfordNummer() != null ? b.lieferung.getAnfordNummer() : "-"),
                                10
                        );
                        g2.setFont(getFont().deriveFont(Font.PLAIN, 11f));
                        FontMetrics barFm = g2.getFontMetrics();
                        int tx = b.rect.x + 4;
                        int ty = b.rect.y + (b.rect.height / 2) + (barFm.getAscent() / 2) - 2;
                        drawBarLabelWithShadow(g2, text, tx, ty);
                    }
                }

                // Рисуем слева названия строк (снизу вверх с тем же порядком, что bars)
                for (Bar b : bars) {
                    String label = buildRowLabel(b.lieferung);
                    if (b.noDateInfo) {
                        g2.setFont(getFont().deriveFont(Font.ITALIC, 12f));
                        g2.setColor(new Color(0, 70, 170));
                    } else {
                        g2.setFont(getFont().deriveFont(Font.PLAIN, 12f));
                        g2.setColor(Color.DARK_GRAY);
                    }
                    Rectangle r = b.rect;
                    if (r != null) {
                        FontMetrics labelFm = g2.getFontMetrics();
                        int baselineY = r.y + (r.height / 2) + (labelFm.getAscent() / 2) - 2;
                        g2.drawString(label, 10, baselineY);
                    } else {
                        g2.drawString(label, 10, headerHeight + topPadding);
                    }
                }
            } finally {
                g2.dispose();
            }
        }

        private String buildRowLabel(Lieferung l) {
            String anford = l.getAnfordNummer();
            if (anford == null || anford.trim().isEmpty()) anford = "-";
            String desc = safeSubstring(l.getBeschreibung(), 40);
            return anford + " " + desc;
        }

        private static void drawVolumetricBar(Graphics2D g2, Rectangle r, Color base) {
            int x = r.x;
            int y = r.y;
            int w = r.width;
            int h = r.height;
            if (w < 1 || h < 1) {
                return;
            }
            if (w < BAR_GRADIENT_MIN_WIDTH || h < 4) {
                g2.setColor(base);
                g2.fillRect(x, y, w, h);
                g2.setColor(base.darker());
                g2.drawRect(x, y, w, h);
                return;
            }
            int arc = Math.min(8, Math.min(w, h) / 2);
            Point2D pTop = new Point2D.Float(x, y);
            Point2D pBot = new Point2D.Float(x, y + h);
            Color cTop = blendColor(base, Color.WHITE, 0.24f);
            Color cBot = blendColor(base, Color.BLACK, 0.22f);
            LinearGradientPaint grad = new LinearGradientPaint(
                    pTop, pBot,
                    new float[] { 0f, 1f },
                    new Color[] { cTop, cBot }
            );
            Paint oldPaint = g2.getPaint();
            RoundRectangle2D.Float rr = new RoundRectangle2D.Float(x, y, w, h, arc, arc);
            g2.setPaint(grad);
            g2.fill(rr);
            g2.setPaint(oldPaint);

            g2.setStroke(new BasicStroke(1f));
            if (w > 6 && h > 3) {
                g2.setColor(new Color(255, 255, 255, 95));
                g2.drawLine(x + 2, y + 1, x + w - 3, y + 1);
            }
            g2.setColor(blendColor(base, Color.BLACK, 0.38f));
            g2.draw(rr);
        }

        private static void drawBarLabelWithShadow(Graphics2D g2, String text, int tx, int ty) {
            g2.setColor(new Color(0, 0, 0, 90));
            g2.drawString(text, tx + 1, ty + 1);
            g2.setColor(new Color(25, 25, 25));
            g2.drawString(text, tx, ty);
        }

        private static Color blendColor(Color a, Color b, float t) {
            float u = Math.max(0f, Math.min(1f, t));
            int r = Math.round(a.getRed() * (1f - u) + b.getRed() * u);
            int g = Math.round(a.getGreen() * (1f - u) + b.getGreen() * u);
            int bl = Math.round(a.getBlue() * (1f - u) + b.getBlue() * u);
            r = Math.min(255, Math.max(0, r));
            g = Math.min(255, Math.max(0, g));
            bl = Math.min(255, Math.max(0, bl));
            return new Color(r, g, bl);
        }
    }
}

