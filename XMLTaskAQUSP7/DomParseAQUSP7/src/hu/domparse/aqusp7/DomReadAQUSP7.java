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
		
		
		for (int i = 0; i < nList.getLength(); i++) {
			Node node = nList.item(i);
			
			//Ha Cim
			if(node.getNodeName() == "Cim"){
				if(!node.getNodeName().equals("#text")) {
					System.out.println("\n");
					System.out.println("Current element: " + node.getNodeName());
				}
				//Cím adatainak kiírása
				if(node.getNodeType()==Node.ELEMENT_NODE) {
					Element elem = (Element) node;
	
					String cid = elem.getAttribute("Cid");
	
					Node cim_node = elem.getElementsByTagName("iranyitoszam").item(0);
					String iranyitoszam = cim_node.getTextContent();
	
					Node cim_node2 = elem.getElementsByTagName("varos").item(0);
					String varos = cim_node2.getTextContent();
	
					Node cim_node3 = elem.getElementsByTagName("utca").item(0);
					String utca = cim_node3.getTextContent();
					
					Node cim_node4 = elem.getElementsByTagName("hazszam").item(0);
					String hazszam = cim_node4.getTextContent();
	
					System.out.printf("Cid: %s%n", cid);
					System.out.printf("Iranyitoszam: %s%n", iranyitoszam);
					System.out.printf("Varos: %s%n", varos);
					System.out.printf("Utca: %s%n", utca);
					System.out.printf("Hazszam: %s%n", hazszam);
				}
			}
			
			
		}
	}

}