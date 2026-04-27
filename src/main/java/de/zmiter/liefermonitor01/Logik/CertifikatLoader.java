/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.zmiter.liefermonitor01.Logik;

/**
 *
 * @author lepeschko
 */

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
/**
 *
 * @author lepeschko
 */
public class CertifikatLoader {
    
     public  void load() {
        try {
            // Загрузите сертификат из файла
            FileInputStream fis = new FileInputStream("cer.cer");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate cert = (X509Certificate) cf.generateCertificate(fis);

            // Создайте KeyStore и добавьте сертификат
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(null, null);
            ks.setCertificateEntry("mycert", cert);

            // Используйте KeyStore в вашем соединении JDBC
            System.setProperty("javax.net.ssl.trustStore", "mykeystore.jks");
            System.setProperty("javax.net.ssl.trustStorePassword", "mypassword");

            // Ваш код для соединения с базой данных
            // ...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
