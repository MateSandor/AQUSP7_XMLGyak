package SaxAQUSP71019;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class SaxAQUSP7 {
	
	public static void main(String[] args) {
        try {
            /*Dokumentumolvas� l�trehoz�sa, a gy�r objektumot a SAXParserFactory oszt�ly newInstance met�dusa 
        	seg�ts�g�vel k�sz�tj�k el. */
        	
        	SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            
            //p�ld�nyos�tja a SAX �rtelmez�t, a visszakapott gy�r �ll�tja el� a SAX elemz�t. 
        	SAXParser saxParser = saxParserFactory.newSAXParser();
            
            //saj�t esem�nykezel� objektum l�trehoz�sa
            SaxHandler handler = new SaxHandler();
            
            //dokumentum �rtelmez�si folyamat�nak elind�t�sa, 
            //A parse met�dus els� param�tere a beolvasand� XML f�jl neve, a m�sodik param�tere a kezel�objektum
            saxParser.parse(new File("SM_kurzusfelvetel.xml"), handler);
        
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}

//Tartalomkezel� keret l�trehoz�sa, valamint az esem�ny- �s hibakezel� met�dusok defini�l�sa

//a DefaultHandler-b�l val� lesz�rmaztat�ssal �s a megfelel� met�dusainak defini�l�s�val t�rt�nik. 

class SaxHandler extends DefaultHandler {

	private int indent = 0;

private String formatAttributes(Attributes attributes) {
    int attrLength = attributes.getLength();
    if (attrLength == 0) {
        return "";
    }
    StringBuilder sb = new StringBuilder(", {");
    for (int i = 0; i < attrLength; i++) {
        sb.append(attributes.getLocalName(i) + "=" + attributes.getValue(i));
        if (i < attrLength - 1) {
            sb.append(", ");
        }
    }
    sb.append("}");
    return sb.toString();
}

private void indent() {
    for (int i = 0; i < indent; i++) {
        System.out.print("  ");
    }
}

//Esem�ny kezel� met�dusok l�trehoz�sa, startElement met�dus.

//elem kezdete �s v�ge
@Override
public void startElement(String uri, String localName, String qName, Attributes attributes) {
    indent++;   // beh�z mindent
    indent();   //start h�zza be
    System.out.println(qName + formatAttributes(attributes) + " start");
}

 //endElement met�dust �jraimplement�ljuk.
@Override
public void endElement(String uri, String localName, String qName) {
    indent();    //end h�zza be
    indent--;    // beh�z�s cs�kkent�se
    System.out.println(qName + " end");
}

//Sz�vegelem feldolgoz�sa, characters met�dust �jraimplement�ljuk.
@Override
public void characters(char ch[], int start, int length) {  //sz�vegtartalmat egy t�mbbe t�roljuk le
    String chars = new String(ch, start, length).trim();
    if (!chars.isEmpty()) {
        indent++;        // beh�z minden
        indent();        // karakter beh�z�sa
        indent--;        // beh�z�s cs�kkent�se
        System.out.println(chars);
    	}
	}
}
