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

import xmlObject.Acceptance;
import xmlObject.Affiliate;
import xmlObject.Article;
import xmlObject.Author;
import xmlObject.Conference;
import xmlObject.Edition;
import xmlObject.Type;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;


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
		String expression = "/conferences/conference";	        
        NodeList conf_nodes = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
		for (int i = 0; i < conf_nodes.getLength(); ++i) {
			Node conf = conf_nodes.item(i);
		
			// On trouve un noeud '�dition'
			Node edition_node = (Node)xPath.evaluate(
				".//edition",
				conf,
				XPathConstants.NODE
			);
			
			// R�cup�rer ici les informations � l'aide 
			// du xpath appropri�
			String titreConf = "";
			Date dateStart = new Date();
			Date dateEnd = new Date();
			String pays = "";
			String ville = "";
			ArrayList<String> presidents = new ArrayList<>();
			String siteWeb = "";
			String acronyme = "";
			ArrayList<String> bestArticle = new ArrayList<>();
			ArrayList<Acceptance> acceptances = new ArrayList<>();
			ArrayList<Type> typeArticles = new ArrayList<>();
			
			
			// On construit le mod�le objet qui sera
			// ajout� � l'objet conference
			Edition e = new Edition();
			e.setTitre(titreConf);
			e.setDateStart(dateStart);
			e.setDateEnd(dateEnd);
			e.setPays(pays);
			e.setVille(ville);
			e.setPresidents(presidents);
			e.setSiteWeb(siteWeb);
			e.setAcronyme(acronyme);
			e.setBestArticle(bestArticle);
			e.setAcceptances(acceptances);
			e.setTypeArticles(typeArticles);
			
			
	
			// Et une liste d'articles
			NodeList article_nodes = (NodeList)xPath.evaluate(
				".//article",
				conf,
				XPathConstants.NODESET
			);
			ArrayList<Article> articles = new ArrayList<Article>();
			
			// Pour chaque article :
			for (int j = 0; j < article_nodes.getLength(); ++j) {
				Node article_node = article_nodes.item(j);
				
				// R�cup�rer ici les informations � l'aide 
				// du xpath appropri�
				ArrayList<Author> auteurs = new ArrayList<>();
				ArrayList<Affiliate> affiliations = new ArrayList<>();
				String titreArt = "";
				String type = new String();
				String resume = "";
				String abstract_libelle = "";
				
				// On ajoute l'article � la liste d'articles 
				// de cette conf�rence
				Article a = new Article();
				a.setAuteurs(auteurs);
				a.setAffiliations(affiliations);
				a.setTitre(titreArt);
				a.setType(type);
				a.setResume(resume);
				a.setAbstrace_libelle(abstract_libelle);
				
				articles.add(a);
			
			}  // fin boucle articles	
			
			// On finit de builder la conf�rence
			// et on l'affecte � la liste des conf�rences.
			Conference c = new Conference();
			c.setEdition(e);
			c.setArticles(articles);
			
		}  // fin boucle conf�rences
		
		return conferences;
		
	}

	/**
	 * Fonction helper pour visualiser le contenu d'un noeud.
	 */
	public void dump_node(Node n) {
		System.out.println("================");
		System.out.println("Nom : " + n.getNodeName());
		//System.out.println("Texte : ");
		//System.out.println(n.getTextContent());
		System.out.println("Attributes : ");
		System.out.println(n.getAttributes());
		System.out.println("Num. of children : " + n.getChildNodes().getLength());
		System.out.println();
	}
}