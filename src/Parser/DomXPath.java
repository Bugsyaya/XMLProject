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
	
	// Document à parser
	private Document xmlDocument = null;
	
	// Parser utilisé
	XPath xPath = XPathFactory.newInstance().newXPath();

	/**
	 * Constructeur.
	 * C'est le constructeur qui se charge de parser initialement
	 * le fichier .xml et de le charger en mémoire, dans l'état
	 * de l'objet.
	 * Code tiré du cours.
	 * @param fileName
	 */
	public DomXPath(String fileName) {
		
		
		// Parsing et chargement du doc. en mémoire.
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
	 * Principale fonction. Son rôle est de
	 * construire les modèles objet à partir
	 * du document parsé.
	 * @return
	 * @throws XPathExpressionException
	 */
	public ArrayList<Conference> buildConferences() throws XPathExpressionException {

		// Liste de Conferences (objets) à renvoyer.
		ArrayList<Conference> conferences = new ArrayList<Conference>();
		
		Element root = xmlDocument.getDocumentElement();
		
		// Pour chaque conférence dans le document:
		NodeList conf_nodes = (NodeList)xPath.evaluate(
			"//conference",
			xmlDocument.getDocumentElement(),
			XPathConstants.NODESET
		);
		for (int i = 0; i < conf_nodes.getLength(); ++i) {
			Node conf = conf_nodes.item(i);
			dump_node(conf);
		
			// On trouve un noeud 'édition'
			
			// Une liste d'articles
			
			// Pour chaque article : 
				
				// On récupère ses informations
				
				// On finit de builder la conférence
				// et on l'affecte à la liste des conférences.
			
		}  // fin boucle conférences
		
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
