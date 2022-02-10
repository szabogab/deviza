/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deviza;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * A tanúsítvány érvényesítésének letiltása HTTPS-kapcsolatban
 * Alapértelmezés szerint a HTTPS URL elérése az URL osztály használatával kivételt eredményez,
 * ha a kiszolgáló tanúsítványlánca nem ellenõrizhetõ, még nem telepítették a megbízhatósági tárolóba.
 * Ha tesztelési célból szeretné letiltani a tanúsítványok érvényesítését,
 * felül kell írnia az alapértelmezett megbízhatósági kezelõt egy olyannal, amely minden tanúsítványban megbízik.
 */
public class CertificatValidator {

    /**
     *
     */
    public CertificatValidator() {

// Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }
            };

// Install the all-trusting trust manager
            try {
                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            } catch (Exception e) {
            }
    }
    
    
    
}
