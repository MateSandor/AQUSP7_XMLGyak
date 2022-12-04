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

            //Attribútum értékeket változtat, feltétellel for loopban az összes címre.
            
            NodeList cimList = doc.getElementsByTagName("Cim");
            for(int i = 0; i < cimList.getLength(); i++)
            {
                Node cim = doc.getElementsByTagName("Cim").item(i);
                NamedNodeMap attr = cim.getAttributes();
                Node nodeAttr = attr.getNamedItem("Cid");
                nodeAttr.setTextContent("lakcim" + (i+1));
                
                NodeList list = cim.getChildNodes();     
                
                // for loop ami a gyerekelemek számáig megy
                for(int temp = 0; temp < list.getLength(); temp++)
                {
                    Node node = list.item(temp);

                    if (node.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element eElement = (Element) node;

                        // A varos nevû gyerekelemnél teljesül
                        if ("varos".equals(eElement.getNodeName()))
                        {
                            // Ha a varos egyenlõ ezzel
                            if ("Miskolc".equals(eElement.getTextContent()))
                            {
                                // Változtassa meg erre
                                eElement.setTextContent("Szirma");
                            }
                        }
                    }
                }
            }
  
            // ------------------------------------------------------------------------------------------------------------------------------
            
            //Gyerekelem törlés
            
            NodeList gyartoList = doc.getElementsByTagName("Gyarto");
            for(int i = 0; i < gyartoList.getLength(); i++)
            {
                // Kilistázza a jelenlegi gyarto egyedet
                Node gyarto = doc.getElementsByTagName("Gyarto").item(i);
            
                // Lekéri annak gyerekelemeit
                NodeList childNodes = gyarto.getChildNodes();

                // Végigmegy a gyerekelemeken
                for (int count = 0; count < childNodes.getLength(); count++)
                {
                    Node node = childNodes.item(count);
                    // Ha a gyerekelem neve "ertekeles"
                    if("ertekeles".equals(node.getNodeName()))
                    {
                        // Akkor törölje
                        gyarto.removeChild(node);
                    }
                }
            }
            
            // ------------------------------------------------------------------------------------------------------------------------------
            
            // A dátumon belül módosítja az évbe írtakat
            
            NodeList datumList = doc.getElementsByTagName("datum");
            for(int i = 0; i < datumList.getLength(); i++)
            {
                // Kilistázza a datum egyedeket
                Node datum = doc.getElementsByTagName("datum").item(i);
            
                // Lekéri annak gyerekelemeit
                NodeList childNodes = datum.getChildNodes();

                // for loop ami a gyerekelemek számáig megy
                for(int temp = 0; temp < childNodes.getLength(); temp++)
                {
                    Node node = childNodes.item(temp);

                    // Ellenõrzés hogy a kapott egyed elem-e
                    if(node.getNodeType() == Node.ELEMENT_NODE)
                    {
                        Element eElement = (Element) node;

                        // Az ev nevû gyerekelemnél teljesül
                        if("ev".equals(eElement.getNodeName()))
                        {
                            // Ha az év egyenlõ ezzel
                            if ("2022".equals(eElement.getTextContent()))
                            {
                               // Változtassa meg "now"-ra
                               eElement.setTextContent("now");
                            }
                        }
                    }
                }
            }
            // ------------------------------------------------------------------------------------------------------------------------------

            // Új gyerekelemet vesz fel a Tartalmazza egyedbe - "kupon", majd az FRid attribútum értéke alapján állít neki értéket
            
            NodeList tartalmazzaList = doc.getElementsByTagName("Tartalmazza");
            for (int i = 0; i < tartalmazzaList.getLength(); i++)
            {
                Node tartalmazza = tartalmazzaList.item(i);

                // Lekéri az "FRid" attribútum értékét és eltárolja az "id"-ben
                String id = tartalmazza.getAttributes().getNamedItem("FTaRid").getTextContent();

                // Létrehozza az új "kupon" elemet
                Element kupon = doc.createElement("kupon");
                tartalmazza.appendChild(kupon);

                // Az "id" értéke alapján ad értéket az új "kupon" elemnek
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

            // Tratalom konzolra írása:
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            DOMSource source = new DOMSource(doc);

            System.out.println("-----Módosított fájl-----");
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
