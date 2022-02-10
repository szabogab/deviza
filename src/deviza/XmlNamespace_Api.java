/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deviza;

import java.util.Iterator;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import org.w3c.dom.Document;

/**
 * 
* xmlns="http://www.ecb.int/vocabulary/2002-08-01/eurofxref"
* névtér nem ad meg elõtagot a forrásdokumentumban (azaz ez az alapértelmezett névtér), mindenképpen társítania kell egy elõtaggal.
* Az XPath olyan elemeket próbál megcímezni, amelyeknek alapértelmezett névtere van, nincs prefix taguk, akkor hibára fut.
* Tehát ahhoz, hogy XPath kifejezéseket használhassunk egy (alapértelmezett) névtérben definiált XML-tartalomban,
* meg kell adnunk egy névtér-elõtag-leképezést.
* Minden névtér elõtaghoz megadja az URI-t a javax.xml.namespace.NamespaceContext példány használatával.
 */

public class XmlNamespace_Api implements NamespaceContext {
    
    private Document sourceDocument;

    /**
     * Visszadja a prefix névterét
     * @param sourceDocument Bemenõ paraméter
     */
    public XmlNamespace_Api(Document sourceDocument) {
        this.sourceDocument = sourceDocument;
    }

    @Override
    public String getNamespaceURI(String prefix) {

                switch (prefix) {
                    case "gesmes":
                        //"http://www.gesmes.org/xml/2002-08-01";
                        return this.sourceDocument.lookupNamespaceURI(prefix);

                    case "alap":
                        //"http://www.ecb.int/vocabulary/2002-08-01/eurofxref";
                        return this.sourceDocument.lookupNamespaceURI(null);
                                
                    default:
                        return XMLConstants.NULL_NS_URI;
                }

    }

    @Override
    public String getPrefix(String namespaceURI) {
       return null;
    }

    @Override
    public Iterator getPrefixes(String namespaceURI) {
         return null;
    }
    
}
