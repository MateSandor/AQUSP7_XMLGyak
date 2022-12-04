package hu.domparse.aqusp7;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomReadAQUSP7 {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		//Forrás file 
		File file = new File("XMLAQUSP7.xml");		
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		
		Document doc = dBuilder.parse(file);
		
		doc.getDocumentElement().normalize();
		
		//Gyökérelem
		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
		//Gyerekelemek lementése
		NodeList nList = (NodeList) doc.getDocumentElement();
		
		for(int i = 0; i < nList.getLength(); i++)
        {
        	Node node = nList.item(i);
        	if(!(node.getNodeName().equals("#comment")||node.getNodeName().equals("#text")))
        	{
	            System.out.println("\nJelenlegi elem: " + node.getNodeName());
	
	            if(node.getNodeType() == Node.ELEMENT_NODE)
	            {
	            	Element elem = (Element) node;
	
	            	NodeList nList2 = elem.getChildNodes();
	
	            	for(int j = 0; j < nList2.getLength(); j++)
	                {
	            		Node node2 = nList2.item(j);
	
						if(node2.getNodeType() == Node.ELEMENT_NODE)
	                    {
							System.out.println(node2.getNodeName() + " : " + node2.getTextContent());
							
						}
					}
	            }
        	}
        }
	
			
			
		}
	}

