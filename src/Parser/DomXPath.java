package Parser;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Node;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
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
	
	// Document à parser
	private Document xmlDocument = null;
	
	// Parser utilisé
	XPath xPath = XPathFactory.newInstance().newXPath();
	
	// Juste pour les conversions de dates
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			xmlDocument = builder.parse(new File(fileName));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Principale fonction. Son rôle est de
	 * construire les modèles objet à partir
	 * du document parsé.
	 * @return ArrayList<Conference>
	 * @throws XPathExpressionException
	 */
	public ArrayList<Conference> buildConferences() throws XPathExpressionException {

		// Liste de Conferences (objets) à renvoyer.
		ArrayList<Conference> conferences = new ArrayList<Conference>();
		
		// Pour chaque conférence dans le document:
		String expression = "/conferences/conference";	        
        NodeList conf_nodes = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
		for (int i = 0; i < conf_nodes.getLength(); ++i) {
			Node conf = conf_nodes.item(i);
		
			// On trouve le noeud 'édition'
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
				System.out.println("Problème de conversion de date : " + e1.getMessage());
			} catch (ParseException e1) {
				System.out.println("Problème de format date : " + e1.getMessage());
			}
			
			ArrayList<String> presidents = new ArrayList<>();
			ArrayList<Type> typeArticles = new ArrayList<>();
			String siteWeb = ((Node)xPath.evaluate(
				".//siteWeb",
				edition_node,
				XPathConstants.NODE)).getTextContent();
			ArrayList<Acceptance> acceptances = new ArrayList<>();
			ArrayList<String> bestArticle = new ArrayList<>();
			
			// Récupération des présidents
			NodeList president_nodes = (NodeList)xPath.evaluate(
				".//presidents/nom", 
				edition_node, 
				XPathConstants.NODESET
			);
			for(int p = 0; p < president_nodes.getLength(); ++p) {
				Node president_node = president_nodes.item(p);
				presidents.add(president_node.getTextContent());
			}
			
			// Récupération des types d'articles
			NodeList type_nodes = (NodeList)xPath.evaluate(
				".//typeArticles/type", 
				edition_node,
				XPathConstants.NODESET
			);
			for(int t = 0; t < type_nodes.getLength(); ++t) {
				Node type_node = type_nodes.item(t);
				String type_id = (String)xPath.evaluate(
					"./@id",
					type_node,
					XPathConstants.STRING
				);
				String type_name = type_node.getTextContent();
				Type type = new Type();
				type.setId(type_id);
				type.setName(type_name);
				typeArticles.add(type);
			}
			
			// Récupération des acceptances
			NodeList acceptance_nodes = (NodeList)xPath.evaluate(
				".//statistiques/acceptations", 
				edition_node,
				XPathConstants.NODESET
			);
			for(int a = 0; a < acceptance_nodes.getLength(); ++a) {
				Node acceptance_node = type_nodes.item(a);
				
				String acc_name = acceptance_node.getTextContent();
				String acc_id = (String)xPath.evaluate(
						"./@id",
						acceptance_node,
						XPathConstants.STRING
					);
				int soumission = ((Double)xPath.evaluate(
						"./@soumissions",
						acceptance_node,
						XPathConstants.NUMBER
					)
				).intValue();
				
				Acceptance acc = new Acceptance();
				acc.setId(acc_id);
				acc.setName(acc_name);
				acc.setSoumission(soumission);
				acceptances.add(acc);
			}
			
			// Récupération des best articles
			NodeList best_nodes = (NodeList)xPath.evaluate(
				"./meilleurArticle/articleId",
				edition_node,
				XPathConstants.NODESET
			);
			for(int b = 0; b < best_nodes.getLength(); ++b) {
				Node best_node = best_nodes.item(b);
				bestArticle.add(best_node.getTextContent());
			}
			
			// On construit le modèle objet qui sera
			// ajouté à l'objet conference
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
				
				// Récupérer ici les informations à l'aide 
				// du xpath approprié
				ArrayList<Author> auteurs = new ArrayList<>();
				ArrayList<Affiliate> affiliations = new ArrayList<>();
				String art_id = (String)xPath.evaluate(
						"./@id",
						article_node,
						XPathConstants.STRING
					);
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
				
				// Récupération des affiliations de l'article
				NodeList affiliation_nodes = (NodeList)xPath.evaluate(
					".//affiliations/affiliation", 
					article_node, 
					XPathConstants.NODESET
				);
				for (int k = 0; k < affiliation_nodes.getLength(); ++k) {
					Node affiliation_node = affiliation_nodes.item(k);
					int id = 0;  // TODO
					String name = affiliation_node.getTextContent();
					
					Affiliate aff = new Affiliate();
					aff.setId(id);
					aff.setName(name);
					affiliations.add(aff);
				}
				
				// Récupération des auteurs de l'article
				NodeList auteurs_nodes = (NodeList)xPath.evaluate(
					".//auteurs/auteur", 
					article_node, 
					XPathConstants.NODESET
				);
				for (int l = 0; l < auteurs_nodes.getLength(); ++l) {
					Node auteur_node = auteurs_nodes.item(l);

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
					
					int aff_id = 0;  // TODO
					ArrayList<Integer> affiliation_ids = new ArrayList<>();
					affiliation_ids.add(aff_id);

					Author auth = new Author();
					auth.setNom(nom);
					auth.setEmail(email);
					auth.setAffiliationId(affiliation_ids);
					auteurs.add(auth);
				}
				
				// On ajoute l'article à la liste d'articles 
				// de cette conférence
				Article a = new Article();
				a.setId(art_id);
				a.setAuteurs(auteurs);
				a.setAffiliations(affiliations);
				a.setTitre(titreArt);
				a.setType(type);
				a.setResume(resume);
				a.setAbstrace_libelle(abstract_libelle);
				
				articles.add(a);
			
			}  // fin boucle articles	
			
			// On finit de builder la conférence
			// et on l'affecte à la liste des conférences.
			Conference c = new Conference();
			c.setEdition(e);
			c.setArticles(articles);
			
			conferences.add(c);
			System.out.print(".");
			
		}  // fin boucle conférences
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
