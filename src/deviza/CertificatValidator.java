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
 * A tan�s�tv�ny �rv�nyes�t�s�nek letilt�sa HTTPS-kapcsolatban
 * Alap�rtelmez�s szerint a HTTPS URL el�r�se az URL oszt�ly haszn�lat�val kiv�telt eredm�nyez,
 * ha a kiszolg�l� tan�s�tv�nyl�nca nem ellen�rizhet�, m�g nem telep�tett�k a megb�zhat�s�gi t�rol�ba.
 * Ha tesztel�si c�lb�l szeretn� letiltani a tan�s�tv�nyok �rv�nyes�t�s�t,
 * fel�l kell �rnia az alap�rtelmezett megb�zhat�s�gi kezel�t egy olyannal, amely minden tan�s�tv�nyban megb�zik.
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
