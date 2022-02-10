/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deviza;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * xml feldolgoz�s 2 r�szb�l �ll. xml let�lt�se + xml elemz�se
 * <p>
     * 1.Let�lt�s: A http k�r�sre adott v�laszt egy stream-be fogadjuk, ami Documnetum forr�sa lesz.
     * N�vt�r-feldolgoz�s enged�lyezve van. (Alap�ertelmezetten false.)
     * <p>
     * 2.Elemz�s: Az XPath seg�ts�g�vel navig�lhat az XML-dokumentum elemei �s attrib�tumai k�z�tt.
     * De a n�vteret le kell kezelni NamespaceContext obj. megval�s�t�s�val.
 */
public class XmlFeldolgozas {

    /**
     *
     * Feldolgoz�s sor�n l�trej�v� kulcs-�rt�k ((currency-rate) p�rokkal dolgozik. (Map obj.)
     */
    public Map<String, Float> my_map = new LinkedHashMap<>();

    /**
     * privat elj�r�sok h�v�sa: getDocument() + xmlElemzes()
     */
    public XmlFeldolgozas() {

        try {

            Document document = getDocument();
            xmlElemzes(document);

        } catch (Exception ex) {
            Logger.getLogger(XmlFeldolgozas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private Document getDocument() {
        try {
            String url = "https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = null;

            new CertificatValidator();

            try {
                HttpsURLConnection con = (HttpsURLConnection) new URL(url).openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-Type", "application/xml");
                con.setRequestProperty("Accept", "application/xml");

                doc = builder.parse(con.getInputStream());
                System.out.println("Let�lt�s siker�lt:" + url);

            } catch (IOException | SAXException ex) {
                Logger.getLogger(XmlFeldolgozas.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
                System.exit(-1);
            }

            return doc;
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XmlFeldolgozas.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    private void xmlElemzes(Document document) {

        String v_szoveg = "//gesmes:Envelope/alap:Cube/alap:Cube/alap:Cube";

        // Create XPathFactory object
        XPathFactory xpathFactory = XPathFactory.newInstance();

        XPath xpath = xpathFactory.newXPath();
        xpath.setNamespaceContext(new XmlNamespace_Api(document));

        try {

            // XPathExpression expr = xpath.compile("//*[local-name()='Cube']/@currency"); //ok
            XPathExpression expr = xpath.compile(v_szoveg);
            Object result = expr.evaluate(document, XPathConstants.NODESET);

            NodeList nodes = (NodeList) result;

            for (int i = 0; i < nodes.getLength(); i++) {
                Element e = (Element) nodes.item(i);
                my_map.put(e.getAttribute("currency"), Float.parseFloat(e.getAttribute("rate")));
            }

        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

    }

}
