package hu.domparse.aqusp7;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

public class DomModifyAQUSP7
{
    public static void main(String argv[])
    {
        try
        {
            File inputFile = new File("XMLAQUSP7.xml");

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.parse(inputFile);
            
            // ------------------------------------------------------------------------------------------------------------------------------

            //Attrib�tum �rt�keket v�ltoztat, felt�tellel for loopban az �sszes c�mre.
            
            NodeList cimList = doc.getElementsByTagName("Cim");
            for(int i = 0; i < cimList.getLength(); i++)
            {
                Node cim = doc.getElementsByTagName("Cim").item(i);
                NamedNodeMap attr = cim.getAttributes();
                Node nodeAttr = attr.getNamedItem("Cid");
                nodeAttr.setTextContent("lakcim" + (i+1));
                
                NodeList list = cim.getChildNodes();     
                
                // for loop ami a gyerekelemek sz�m�ig megy
                for(int temp = 0; temp < list.getLength(); temp++)
                {
                    Node node = list.item(temp);

                    if (node.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element eElement = (Element) node;

                        // A varos nev� gyerekelemn�l teljes�l
                        if ("varos".equals(eElement.getNodeName()))
                        {
                            // Ha a varos egyenl� ezzel
                            if ("Miskolc".equals(eElement.getTextContent()))
                            {
                                // V�ltoztassa meg erre
                                eElement.setTextContent("Szirma");
                            }
                        }
                    }
                }
            }
  
            // ------------------------------------------------------------------------------------------------------------------------------
            
            //Gyerekelem t�rl�s
            
            NodeList gyartoList = doc.getElementsByTagName("Gyarto");
            for(int i = 0; i < gyartoList.getLength(); i++)
            {
                // Kilist�zza a jelenlegi gyarto egyedet
                Node gyarto = doc.getElementsByTagName("Gyarto").item(i);
            
                // Lek�ri annak gyerekelemeit
                NodeList childNodes = gyarto.getChildNodes();

                // V�gigmegy a gyerekelemeken
                for (int count = 0; count < childNodes.getLength(); count++)
                {
                    Node node = childNodes.item(count);
                    // Ha a gyerekelem neve "ertekeles"
                    if("ertekeles".equals(node.getNodeName()))
                    {
                        // Akkor t�r�lje
                        gyarto.removeChild(node);
                    }
                }
            }
            
            // ------------------------------------------------------------------------------------------------------------------------------
            
            // A d�tumon bel�l m�dos�tja az �vbe �rtakat
            
            NodeList datumList = doc.getElementsByTagName("datum");
            for(int i = 0; i < datumList.getLength(); i++)
            {
                // Kilist�zza a datum egyedeket
                Node datum = doc.getElementsByTagName("datum").item(i);
            
                // Lek�ri annak gyerekelemeit
                NodeList childNodes = datum.getChildNodes();

                // for loop ami a gyerekelemek sz�m�ig megy
                for(int temp = 0; temp < childNodes.getLength(); temp++)
                {
                    Node node = childNodes.item(temp);

                    // Ellen�rz�s hogy a kapott egyed elem-e
                    if(node.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element eElement = (Element) node;

                        // Az ev nev� gyerekelemn�l teljes�l
                        if("ev".equals(eElement.getNodeName()))
                        {
                            // Ha az �v egyenl� ezzel
                            if ("2022".equals(eElement.getTextContent()))
                            {
                               // V�ltoztassa meg "now"-ra
                               eElement.setTextContent("now");
                            }
                        }
                    }
                }
            }
            // ------------------------------------------------------------------------------------------------------------------------------

            // �j gyerekelemet vesz fel a Tartalmazza egyedbe - "kupon", majd az FRid attrib�tum �rt�ke alapj�n �ll�t neki �rt�ket
            
            NodeList tartalmazzaList = doc.getElementsByTagName("Tartalmazza");
            for (int i = 0; i < tartalmazzaList.getLength(); i++)
            {
                Node tartalmazza = tartalmazzaList.item(i);

                // Lek�ri az "FRid" attrib�tum �rt�k�t �s elt�rolja az "id"-ben
                String id = tartalmazza.getAttributes().getNamedItem("FTaRid").getTextContent();

                // L�trehozza az �j "kupon" elemet
                Element kupon = doc.createElement("kupon");
                tartalmazza.appendChild(kupon);

                // Az "id" �rt�ke alapj�n ad �rt�ket az �j "kupon" elemnek
                if ("r1".equals(id))
                {
                    kupon.appendChild(doc.createTextNode("0"));
                }
                if ("r2".equals(id))
                {
                    kupon.appendChild(doc.createTextNode("30"));
                }
                if ("r3".equals(id))
                {
                    kupon.appendChild(doc.createTextNode("0"));
                }
            }
            // ------------------------------------------------------------------------------------------------------------------------------

            // Tratalom konzolra �r�sa:
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            System.out.println("-----M�dos�tott f�jl-----");
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
