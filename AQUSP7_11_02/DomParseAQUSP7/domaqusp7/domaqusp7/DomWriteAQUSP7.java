package domaqusp7;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class DomWriteAQUSP7 {

	public static void main(String[] args) throws ParserConfigurationException, TransformerException {
		File xmlFile = new File("usersAQUSP7.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		
		Document doc = dBuilder.newDocument();
		
		Element root = doc.createElementNS("DOMAQUSP7","users");
		doc.appendChild(root);
		
		root.appendChild(createUser(doc, "1", "Pal", "Kiss", "Web developer"));
		root.appendChild(createUser(doc, "2", "Piroska", "Zold", "Java programozo"));
		root.appendChild(createUser(doc, "3", "Jane", "Doe", "Teacher"));

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transf = transformerFactory.newTransformer();
		
		transf.setOutputProperties(OutputKeys.ENCODING, "UTF-8");
		transf.setOutputProperties(OutputKeys.INDENT, "yes");
		transf.setOutputProperties(OutputKeys.ENCODING, "(http://xml.apache.org/xslt)indent-amount","2");
		
		DOMSource source = new DOMSource(doc);
		
		File myFile = new File("users2.xml");
				
				StreamResult console = new StreamResult(System.out);
		StreamResult file = new StreamResult(myFile);
		
		
				
				
		
		
		/*
		Document doc = dBuilder.parse(xmlFile);
		
		doc.getDocumentElement().normalize();
		
		System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
		
		NodeList nList = doc.getElementsByTagName("user");
		
		for (int i = 0; i < nList.getLength(); i++) {
			Node nNode = nList.item(i);
			
			System.out.println("\nCurrent Element: "+ nNode.getNodeName());
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) nNode;
				
				String uid= elem.getAttribute("id");
				
				Node node1 = elem.getElementsByTagName("firstname").item(0);
				String fname = node1.getTextContent();
				
				Node node2 = elem.getElementsByTagName("lastname").item(0);
				String lname = node2.getTextContent();
				
				Node node3 = elem.getElementsByTagName("profession").item(0);
				String pname = node3.getTextContent();
				
				System.out.println("User id: "+ uid);
				System.out.println("Firstname: "+ fname);
				System.out.println("Lastname: "+ lname);
				System.out.println("Profession: "+ pname);
			}
		}
			*/	

	}
	
	private static Node createUser(Document doc, String id, String firstName, String lastName, String profession) {
		Element user = doc.createElement("user");
		
		user.setAttribute("id",id);
		user.appendChild(createUserElement(doc, "firstname",firstName));
		user.appendChild(createUserElement(doc, "lastname",lastName));
		user.appendChild(createUserElement(doc, "profession",profession));
		
		return user;
	}

	private static Node createUserElement(Document doc, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

	
	
}
