package Parser;

import javax.print.attribute.Attribute;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
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

	/**
	 * Constructeur.
	 * C'est le constructeur qui se charge de parser initialement
	 * le fichier .xml et de le charger en mémoire, dans l'état
	 * de l'objet.
	 * Code tiré du cours.
	 * @param fileName
	 */
	public DomXPath(String fileName) {
		
		XPath xPath = XPathFactory.newInstance().newXPath();
		
		
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
	
	public ArrayList<Conference> buildConferences() {

		// Liste de Conferences (objets) à renvoyer.
		ArrayList<Conference> conferences = new ArrayList<Conference>();
		
		Element root = xmlDocument.getDocumentElement();
		
		// Pour chaque conférence dans le document:
		
			// On trouve un noeud 'édition'
			
			// Une liste d'articles
			
			// Pour chaque article : 
				
				// On récupère ses informations
				
				// On finit de builder la conférence
				// et on l'affecte à la liste des conférences.
		
		return conferences;
		
	}

}
