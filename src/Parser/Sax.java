package Parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import xmlObject.Acceptance;
import xmlObject.Affiliate;
import xmlObject.Article;
import xmlObject.Author;
import xmlObject.Conference;
import xmlObject.Type;

public class Sax extends DefaultHandler implements ParserInterface
{
	private ArrayList<Conference> conferences;
	private String nameFile;
	private String value;
	private Conference conference;
	private Type type;
	private Acceptance acceptance;
	private Article article;
	private ArrayList<Author> authors;
	private Author author;
	private ArrayList<Integer> affiliatesId;
	private Affiliate affiliate;
	private boolean isEdition;
	private boolean isPresident;
	private boolean isTypeA;
	private boolean isStat;
	private boolean isBestA;
	private boolean isListArticles;
	private boolean isArticle;
	private boolean isListAuthors;
	private boolean isAuthor;
	private boolean isAffiliate;
	
	private SimpleDateFormat dateFormat;
	
	public Sax(String nameFile) 
	{
		this.conferences = new ArrayList<>();
		this.isEdition = false;
		this.isPresident = false;
		this.isTypeA = false;
		this.isStat = false;
		this.isBestA = false;
		this.isListArticles = false;
		this.isArticle = false;
		this.isAuthor = false;
		this.isListAuthors = false;
		this.isAffiliate = false;
		this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		this.value = "";
		
		this.nameFile = nameFile;
	}
	
	public ArrayList<Conference> parserXml() 
	{
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		try 
		{
			SAXParser saxParser = saxFactory.newSAXParser();
			saxParser.parse(nameFile, this);
		} 
		catch (Exception e) 
		{
			System.out.println("[Error] " + e.getMessage());
		}

		return conferences;
	}
	
	public void elementStart(String nameE, Attributes attributes)
	{
		if (nameE.equals("conference"))
			conference = new Conference();
		
		if (nameE.equals("type") 
				&& isTypeA)
		{
			type = new Type();
			type.setId(attributes.getValue("id"));
		}
		
		if (nameE.equals("acceptions")
				&& isStat) 
		{
			acceptance = new Acceptance();
			acceptance.setId(attributes.getValue("id"));
			acceptance.setSoumission(Integer.parseInt(attributes.getValue("soumissions")));
		}
		
		if (nameE.equals("article")
				&& isListArticles) 
		{
			article = new Article();
			article.setId(attributes.getValue("id"));
			article.setSession(attributes.getValue("session"));
		}
		
		if (nameE.equals("auteurs")
				&& isArticle) 
		{
			authors = new ArrayList<>();
		}
		
		if (nameE.equals("auteur")
				&& isListAuthors) 
		{
			author = new Author();
			affiliatesId = new ArrayList<>();
		}
		
		if (nameE.equals("affiliation")
				&& isAffiliate) 
		{
			affiliate = new Affiliate();
			affiliate.setId(Integer.parseInt(attributes.getValue("affiliationId")));
		}
		
		if (nameE.equals("edition")) 
		{
			isEdition = true;
		}
		
		if (nameE.equals("presidents")) 
		{
			isPresident = true;
		}
		
		if (nameE.equals("typeArticles")) 
		{
			isListArticles = true;
		}
		
		if (nameE.equals("statistiques")) 
		{
			isStat = true;
		}
		
		if (nameE.equals("meilleurArticle")) 
		{
			isBestA = true;
		}
		
		if (nameE.equals("articles")) 
		{
			isListArticles = true;
		}
		
		if (nameE.equals("article")) 
		{
			isArticle = true;
		}
		
		if (nameE.equals("auteur")) 
		{
			isAuthor = true;
		}
		
		if (nameE.equals("auteurs")) 
		{
			isListAuthors = true;
		}
		
		if (nameE.equals("affiliations")) 
		{
			isAffiliate = true;
		}
	}
	
	public void endElement(String elementName) throws SAXException 
	{
        if (elementName.equals("conference")) 
        {
            conferences.add(conference);
        }

        if (elementName.equalsIgnoreCase("edition")) 
        {
            isEdition = false;
        }

        if (elementName.equalsIgnoreCase("presidents")) 
        {
            isPresident = false;
        }

        if (elementName.equalsIgnoreCase("typeArticles")) {
        	isTypeA = false;
        }

        if (elementName.equalsIgnoreCase("statistiques")) 
        {
            isStat = false;
        }

        if (elementName.equalsIgnoreCase("meilleurArticle")) 
        {
            isBestA = false;
        }

        if (elementName.equalsIgnoreCase("articles")) 
        {
            isListArticles = false;
        }

        if (elementName.equalsIgnoreCase("article")) 
        {
            isArticle = false;
        }

        if (elementName.equalsIgnoreCase("auteurs")) 
        {
            isListAuthors = false;
        }

        if (elementName.equalsIgnoreCase("auteur")) 
        {
            isAuthor = false;
        }

        if (elementName.equalsIgnoreCase("affiliations")) 
        {
            isAffiliate = false;
        }

        if (isEdition) 
        {
            if (elementName.equalsIgnoreCase("acronyme"))
                conference.getEdition().setAcronyme(value);

            if (elementName.equalsIgnoreCase("titre"))
                conference.getEdition().setTitre(value);

            if (elementName.equalsIgnoreCase("ville"))
                conference.getEdition().setVille(value);

            if (elementName.equalsIgnoreCase("pays"))
                conference.getEdition().setPays(value);

            if (elementName.equalsIgnoreCase("dateDebut")) 
            {
                try 
                {
                    conference.getEdition().setDateStart(dateFormat.parse(value));
                } 
                catch (ParseException e) 
                {
                    System.err.println("date parsing error");
                }
            }

            if (elementName.equalsIgnoreCase("dateFin")) 
            {
                try 
                {
                    conference.getEdition().setDateEnd(dateFormat.parse(value));
                } 
                catch (ParseException e) 
                {
                    System.err.println("date parsing error");
                }
            }

            if (elementName.equalsIgnoreCase("nom") && isPresident)
                conference.getEdition().getPresidents().add(value);

            if (elementName.equalsIgnoreCase("type") && isTypeA) 
            {
                type.setName(value);
                conference.getEdition().getTypeArticles().add(type);
            }

            if (elementName.equalsIgnoreCase("acceptations") 
            		&& isStat)
            {
                acceptance.setName(value);
                conference.getEdition().getAcceptances().add(acceptance);
            }

            if (elementName.equalsIgnoreCase("siteWeb")) 
                conference.getEdition().setSiteWeb(value);

            if (elementName.equalsIgnoreCase("articleId") 
            		&& isBestA)
                conference.getEdition().getBestArticle().add(value);
        }

        if (isListArticles) 
        {
            if (elementName.equalsIgnoreCase("article"))
                conference.getArticles().add(article);

            if (isArticle) 
            {
                if (isListAuthors) 
                {
                    if (elementName.equalsIgnoreCase("auteur")) 
                    {
                        author.setAffiliationId(affiliatesId);
                        authors.add(author);
                    }

                    if (isAuthor) 
                    {
                        if (elementName.equalsIgnoreCase("nom"))
                            author.setNom(value);

                        if (elementName.equalsIgnoreCase("email"))
                            author.setEmail(value);

                        if (elementName.equalsIgnoreCase("affiliationId")) 
                        {
                            try 
                            {
                                affiliatesId.add(Integer.parseInt(value));
                            } 
                            catch (NumberFormatException e) 
                            {
                                affiliatesId.add(0);
                            }
                        }
                    }
                }

                if (elementName.equalsIgnoreCase("auteurs"))
                	article.setAuteurs(authors);

                if (elementName.equalsIgnoreCase("affiliation") 
                		&& isAffiliate) 
                {
                    affiliate.setName(value);
                    article.getAffiliations().add(affiliate);
                }

                if (elementName.equalsIgnoreCase("titre"))
                	article.setTitre(value);

                if (elementName.equalsIgnoreCase("type"))
                	article.setType(value);
                	
                if (elementName.equalsIgnoreCase("pages"))
                	article.setPage(value);
                
                if (elementName.equalsIgnoreCase("resume"))
                	article.setResume(value);
                
                if (elementName.equalsIgnoreCase("mots_cles"))
                	article.setMot_cle(value);

                if (elementName.equalsIgnoreCase("abstract"))
                	article.setAbstrace_libelle(value);
                
                if (elementName.equalsIgnoreCase("keywords"))
                    article.setKeyword(value);
            }
        }
    }
}
