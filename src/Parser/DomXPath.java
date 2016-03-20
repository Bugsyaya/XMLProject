package Parser;

import javax.print.attribute.Attribute;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class DomXPath {
	
	// Document � parser
	private Document xmlDocument = null;
	
	// Parser utilis�
	XPath xPath = XPathFactory.newInstance().newXPath();
	
	// Juste pour les conversions de dates
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			xmlDocument = builder.parse(new File(fileName));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Principale fonction. Son r�le est de
	 * construire les mod�les objet � partir
	 * du document pars�.
	 * @return ArrayList<Conference>
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
		
			// On trouve le noeud '�dition'
			Node edition_node = (Node)xPath.evaluate(
				".//edition",
				conf,
				XPathConstants.NODE
			);
			
			// On extrait les informations dont on a besoin
			String acronyme = ((Node)xPath.evaluate(
				".//acronyme",
				edition_node,
				XPathConstants.NODE)).getTextContent();
			String titreConf = ((Node)xPath.evaluate(
				".//titre",
				edition_node,
				XPathConstants.NODE)).getTextContent();
			String ville = ((Node)xPath.evaluate(
				".//ville",
				edition_node,
				XPathConstants.NODE)).getTextContent();
			String pays = ((Node)xPath.evaluate(
				".//pays",
				edition_node,
				XPathConstants.NODE)).getTextContent();
			
			Date dateStart = null;
			Date dateEnd = null;
			try {
				dateStart = dateFormat.parse(
					((Node)xPath.evaluate(
						".//dateDebut",
						edition_node,
						XPathConstants.NODE)
					).getTextContent()
				);
				dateEnd = dateFormat.parse(
					((Node)xPath.evaluate(
						".//dateFin",
						edition_node,
						XPathConstants.NODE)
					).getTextContent()
				);
			} catch (DOMException e1) {
				System.out.println("Probl�me de conversion de date : " + e1.getMessage());
			} catch (ParseException e1) {
				System.out.println("Probl�me de format date : " + e1.getMessage());
			}
			
			ArrayList<String> presidents = new ArrayList<>();
			ArrayList<Type> typeArticles = new ArrayList<>();
			String siteWeb = ((Node)xPath.evaluate(
				".//siteWeb",
				edition_node,
				XPathConstants.NODE)).getTextContent();
			ArrayList<Acceptance> acceptances = new ArrayList<>();
			ArrayList<String> bestArticle = new ArrayList<>();
			
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
		
			// Construction de la liste d'articles
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
				String titreArt = ((Node)xPath.evaluate(
					".//titre | .//title",
					article_node,
					XPathConstants.NODE)).getTextContent();
				String type = ((Node)xPath.evaluate(
					".//type",
					article_node,
					XPathConstants.NODE)).getTextContent();
				String resume = ((Node)xPath.evaluate(
					".//resume",
					article_node,
					XPathConstants.NODE)).getTextContent();
				String abstract_libelle = ((Node)xPath.evaluate(
					".//abstract",
					article_node,
					XPathConstants.NODE)).getTextContent();
				
				/*
				// R�cup�ration des affiliations de l'article
				NodeList affiliation_nodes = (NodeList)xPath.evaluate(
					".//affiliations/affiliation", 
					article_node, 
					XPathConstants.NODESET
				);
				for (int k = 0; k < affiliation_nodes.getLength(); ++k) {
					Node affiliation_node = affiliation_nodes.item(i);
					int id = 0;
					try {
						id = Integer.valueOf(
							(String)xPath.evaluate(
								"./@id", 
								affiliation_node,
								XPathConstants.STRING
							)
						);
					} catch (NumberFormatException e1) {
						System.out.println(e1.getMessage());
					}
					String name = affiliation_node.getTextContent();
					
					Affiliate aff = new Affiliate();
					aff.setId(id);
					aff.setName(name);
					affiliations.add(aff);
				}
				
				
				// R�cup�ration des auteurs de l'article
				NodeList auteurs_nodes = (NodeList)xPath.evaluate(
					".//auteurs/auteur", 
					article_node, 
					XPathConstants.NODESET
				);
				for (int l = 0; l < affiliation_nodes.getLength(); ++l) {
					Node auteur_node = auteurs_nodes.item(i);

					String nom = ((Node)xPath.evaluate(
						".//nom", 
						auteur_node, 
						XPathConstants.NODE)
					).getTextContent();
					
					String email = ((Node)xPath.evaluate(
						".//email", 
						auteur_node, 
						XPathConstants.NODE)
					).getTextContent();
					
					int aff_id = Integer.valueOf(
						((Node)xPath.evaluate(
							".//affiliationId", 
							auteur_node, 
							XPathConstants.NODE)
						).getTextContent()
					);
					ArrayList<Integer> affiliation_ids = new ArrayList<>();
					affiliation_ids.add(aff_id);

					Author auth = new Author();
					auth.setNom(nom);
					auth.setEmail(email);
					auth.setAffiliationId(affiliation_ids);
					auteurs.add(auth);
				}
				*/
				
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
			
			conferences.add(c);
			System.out.print(".");
			
		}  // fin boucle conf�rences
		System.out.println();
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
