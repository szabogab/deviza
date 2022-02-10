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
* n�vt�r nem ad meg el�tagot a forr�sdokumentumban (azaz ez az alap�rtelmezett n�vt�r), mindenk�ppen t�rs�tania kell egy el�taggal.
* Az XPath olyan elemeket pr�b�l megc�mezni, amelyeknek alap�rtelmezett n�vtere van, nincs prefix taguk, akkor hib�ra fut.
* Teh�t ahhoz, hogy XPath kifejez�seket haszn�lhassunk egy (alap�rtelmezett) n�vt�rben defini�lt XML-tartalomban,
* meg kell adnunk egy n�vt�r-el�tag-lek�pez�st.
* Minden n�vt�r el�taghoz megadja az URI-t a javax.xml.namespace.NamespaceContext p�ld�ny haszn�lat�val.
 */

public class XmlNamespace_Api implements NamespaceContext {
    
    private Document sourceDocument;

    /**
     * Visszadja a prefix n�vter�t
     * @param sourceDocument Bemen� param�ter
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
