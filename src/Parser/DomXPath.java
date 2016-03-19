package Parser;

import javax.print.attribute.Attribute;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import xmlObject.Conference;

import java.io.File;
import java.util.ArrayList;


public class DomXPath {
	
	// Document � parser
	private Document xmlDocument = null;
	
	// Parser utilis�
	XPath xPath = XPathFactory.newInstance().newXPath();

	/**
	 * Constructeur.
	 * C'est le constructeur qui se charge de parser initialement
	 * le fichier .xml et de le charger en m�moire, dans l'�tat
	 * de l'objet.
	 * Code tir� du cours.
	 * @param fileName
	 */
	public DomXPath(String fileName) {
		
		
		// Parsing et chargement du doc. en m�moire.
		try {
			// getting the default implementation of DOM builder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			// parsing the XML file
			xmlDocument = builder.parse(new File(fileName));

		} catch (Exception e) {
			// catching all exceptions
			System.out.println();
			System.out.println(e.toString());
		}
		
	}
	
	/**
	 * Principale fonction. Son r�le est de
	 * construire les mod�les objet � partir
	 * du document pars�.
	 * @return
	 * @throws XPathExpressionException
	 */
	public ArrayList<Conference> buildConferences() throws XPathExpressionException {

		// Liste de Conferences (objets) � renvoyer.
		ArrayList<Conference> conferences = new ArrayList<Conference>();
		
		Element root = xmlDocument.getDocumentElement();
		
		// Pour chaque conf�rence dans le document:
		NodeList conf_nodes = (NodeList)xPath.evaluate(
			"//conference",
			xmlDocument.getDocumentElement(),
			XPathConstants.NODESET
		);
		for (int i = 0; i < conf_nodes.getLength(); ++i) {
			Node conf = conf_nodes.item(i);
			dump_node(conf);
		
			// On trouve un noeud '�dition'
			
			// Une liste d'articles
			
			// Pour chaque article : 
				
				// On r�cup�re ses informations
				
				// On finit de builder la conf�rence
				// et on l'affecte � la liste des conf�rences.
			
		}  // fin boucle conf�rences
		
		return conferences;
		
	}

	/**
	 * Fonction helper pour visualiser le contenu d'un noeud.
	 */
	public void dump_node(Node n) {
		System.out.println("================");
		System.out.println("Nom : " + n.getNodeName());
		System.out.println("Texte : ");
		System.out.println(n.getTextContent());
		System.out.println("Attributes : ");
		System.out.println(n.getAttributes());
		System.out.println("Num. of children : " + n.getChildNodes().getLength());
		System.out.println();
	}
}
