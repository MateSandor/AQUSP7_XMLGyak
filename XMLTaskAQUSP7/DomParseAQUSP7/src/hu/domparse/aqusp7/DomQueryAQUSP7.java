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

public class DomQueryAQUSP7
{
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
    {
		//Forr�s file 
		File file = new File("XMLAQUSP7.xml");

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		 
		Document doc = dBuilder.parse(file);
		doc.getDocumentElement().normalize();

        
         
        System.out.println("-----------------\n");
        System.out.println("Elso lekerdezes:");
        
        // C�mek kilist�z�sa
        NodeList nList1 = doc.getElementsByTagName("Cim");
         
        // V�gigfut az "Cim"-nek a gyerekelemein, kihagyva az "iranyitoszamot"-t
        for(int i = 0; i < nList1.getLength(); i++)
        {
        	Node node1 = nList1.item(i);
            System.out.println("\nJelenlegi elem: " + node1.getNodeName());

            if(node1.getNodeType() == Node.ELEMENT_NODE)
            {
            	Element elem = (Element) node1;

            	System.out.println("Cid: " + elem.getAttribute("Cid"));

            	NodeList nList11 = elem.getChildNodes();

            	for(int j = 0; j < nList11.getLength(); j++)
                {
            		Node node11 = nList11.item(j);

					if(node11.getNodeType() == Node.ELEMENT_NODE)
                    {
						if(!node11.getNodeName().equals("iranyitoszam"))
                        {
							System.out.println(node11.getNodeName() + " : " + node11.getTextContent());
						}
					}
				}
            }
		}
        
        
    //******************************************************************************************************************************
    System.out.println("\n-----------------\n");
    System.out.println("Masodik lekerdezes:\n");
    //Sz�molja meg a term�kek sz�m�t
    
    // Termekek kilist�z�sa
    NodeList nList2 = doc.getElementsByTagName("Termek");
    int termekDarabSzam = 0;
     
    // V�gigfut az "Cim"-nek a gyerekelemein, kihagyva az "iranyitoszamot"-t
    for(int i = 0; i < nList2.getLength(); i++)
    {
    	Node node2 = nList2.item(i);
        if(node2.getNodeType() == Node.ELEMENT_NODE)
        {
        	if(((Element) node2).getAttribute("Tid")!=null){
        		termekDarabSzam++;
        	}           	
        }
	}
    System.out.println("Termekek darabszama: " + termekDarabSzam);
    
  //******************************************************************************************************************************
    System.out.println("\n-----------------\n");
    System.out.println("Harmadik lekerdezes:\n");
    //Sz�molja ki a term�kek �rainak �tlag�t
    
    // Termekek kilist�z�sa
    NodeList nList3 = doc.getElementsByTagName("Termek");
    int termekSum = 0;
     
    // V�gigfut az "Term�kek"-en �s azon bel�l az "egysegar" akat szumm�zza egy v�ltoz�ba
    for(int i = 0; i < nList3.getLength(); i++)
    {
    	Node node3= nList3.item(i);
        if(node3.getNodeType() == Node.ELEMENT_NODE)
        {
        	NodeList nList33 = ((Element) node3).getChildNodes();
        	for (int j = 0; j < nList33.getLength(); j++) {
        		Node node33 = nList33.item(j);
        		if(node33.getNodeName().equals("egysegar"))
                {
					termekSum += Integer.parseInt(node33.getTextContent());
				}
			}
        	           	
        }
	}
System.out.println("Termekek �sszege: " + termekSum);

//******************************************************************************************************************************
System.out.println("\n-----------------\n");
System.out.println("Negyedik lekerdezes:\n");
//List�zza ki azokat azoknak a gy�rt�knak a nev�t, akiknek az �rtekel�se jobb mint 3

// Gyartok kilist�z�sa
NodeList nList4 = doc.getElementsByTagName("Gyarto"); 
// V�gigfut az "Gyarto"-kon �s kilist�zza azoknak a nev�t, akiknek az "elerhetosegek" gyermekelem�n nincs ertek
System.out.println("Gyartok 3 nal jobb ertekelessel: ");
for(int i = 0; i < nList4.getLength(); i++)
{
	Node node4= nList4.item(i);
    if(node4.getNodeType() == Node.ELEMENT_NODE)
    {
    	NodeList nList4Child = ((Element) node4).getChildNodes();
    	for (int j = 0; j < nList4Child.getLength(); j++) {
    		Node node4Child = nList4Child.item(j);
    		if(node4Child.getNodeName().equals("ertekeles"))
	            {
				if(Integer.parseInt(node4Child.getTextContent())>3) {
					for (int k = 0; k < nList4Child.getLength(); k++) {
						Node node4Chosen = nList4Child.item(k);
						if (node4Chosen.getNodeName().equals("gyartonev")) {
							System.out.println(node4Chosen.getTextContent());
						}
					}
				}
			}
			}
		}
    	           	
    }
//******************************************************************************************************************************
System.out.println("\n-----------------\n");
System.out.println("Otodik lekerdezes:\n");
//Sz�molja ki hogy a vev�k h�ny sz�zal�ka feln�tt

//Vevok kilist�z�sa
NodeList nList5 = doc.getElementsByTagName("Vevo"); 
//V�gigfut az "Vevo"-k�n mekeresi a feln�tteket �s elmenti a darabsz�mukat, majd sz�zal�kot sz�m�t
int felnottDarab = 0;
for(int i = 0; i < nList5.getLength(); i++)
{
	Node node5= nList5.item(i);
  if(node5.getNodeType() == Node.ELEMENT_NODE)
  {
  	NodeList nList5Child = ((Element) node5).getChildNodes();
  	for (int j = 0; j < nList5Child.getLength(); j++) {
  		Node node5Child = nList5Child.item(j);
  		if(node5Child.getNodeName().equals("felnott"))
	            {
				if (node5Child.getTextContent().equals("Igen")) {
					felnottDarab++;
				}
			}
		}
	}
  	           	
  }
//(double) parse �s form�z�s sz�ks�ges
System.out.println("A vevok "+String.format("%.2f",  ((double)felnottDarab / nList5.getLength() * 100))+"%-a felnott ");
    }

}