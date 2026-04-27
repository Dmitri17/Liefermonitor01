/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.Logik;

/**
 *
 * @author lepeschko
 */ 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;


    
   
public class CalendarDL extends JPanel {
    
SimpleDateFormat month = new SimpleDateFormat("MMMM");
SimpleDateFormat year = new SimpleDateFormat("yyyy");
SimpleDateFormat day = new SimpleDateFormat("d");
Date date = new Date();

public void setDate(Date date) {
this.date = date;
}

public void paintComponent(Graphics g) {
((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
g.fillRect(0, 0, getWidth(), getHeight());
super.paintComponent(g);

g.setFont(new Font("TimesRoman", Font.PLAIN, 17));
g.setColor(Color.black);
g.drawString(month.format(date).toUpperCase(), 34, 36);
g.setColor(Color.black);
g.drawString(year.format(date), 235, 36);

g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
Calendar today = Calendar.getInstance();
today.setTime(date);
Calendar cal = Calendar.getInstance();
cal.setTime(date);
cal.set(Calendar.DATE, 1);
cal.add(Calendar.DATE, - cal.get(Calendar.DAY_OF_WEEK) + 1);
for (int week = 0; week < 5; week++) {
    for (int d = 0; d < 7; d++) {
            g.drawString(day.format(cal.getTime()), d * 30 + 46 + 4,week * 29 + 81 + 20);
            cal.add(Calendar.DATE, +1);
        }
    }
}
public  void startCalendar() {
JFrame frame = new JFrame();
frame.getContentPane().setBackground(Color.white);
frame.setPreferredSize(new Dimension(300, 280));
CalendarDL ch = new CalendarDL();
ch.setDate(new Date());
frame.getContentPane().add(ch);
frame.setUndecorated(true);
frame.pack();
frame.setVisible(true);
}
}

