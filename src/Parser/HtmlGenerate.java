package Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import xmlObject.Acceptance;
import xmlObject.Affiliate;
import xmlObject.Article;
import xmlObject.Author;
import xmlObject.Conference;
import xmlObject.Type;

public class HtmlGenerate 
{
    public static final String RESSOURCES_PATH = "web/";
    public static final String HTML_PATH = "html/";
    public static final String CONFERENCES_PATH = "conferences/";
    public static final String TEMPLATES_PATH = RESSOURCES_PATH + HTML_PATH;

    public static final String XML_FILE_NAME = RESSOURCES_PATH + "TALN-RECITAL-BIB.xml";
    public static final String HTML_RESSOURSES_PATH = "TALN_RECITAL_fichiers/";

    public static final String HOME_PAGE_FILE_NAME = "TALN_RECITAL.html";
    public static final String HOME_PAGE_TEMPLATE_NAME = "homeTemplate.html";
    public static final String CONFERENCE_PAGE_TEMPLATE_NAME = "conferenceTemplate.html";
    public static final String CONFERENCE_ITEM_TEMPLATE_NAME = "confItemTemplate.html";

    private static final SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
    private static final SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd MMMMM yyyy");

    private static void saveInFile(final String html, final String filePath, final String fileName) 
    {
        File repCourant = new java.io.File(new java.io.File("").getAbsolutePath());

        String DIRECTORY = repCourant + "/" + filePath;

        try 
        {
            PrintWriter writer = new PrintWriter(DIRECTORY + fileName, "UTF-8");
            writer.write(html);
            writer.close();
            System.out.println("Fichier \"" + fileName + "\" correctement créé à cet endroit : \"" + DIRECTORY + "\"");
        } 
        catch (Exception e) 
        {
            System.err.println(e.getMessage());
        }
    }

    public static void generateHomePage(List<Conference> conferences) 
    {
        String htmlTemplateFile = TEMPLATES_PATH + HOME_PAGE_TEMPLATE_NAME;

        String htmlString = readTemplate(htmlTemplateFile);

        if (htmlString != null) 
        {
            htmlString = htmlString.replace("$ressourcesPath", HTML_RESSOURSES_PATH);
            htmlString = htmlString.replace("$conferences", getConferences(conferences));
        }

        saveInFile(htmlString, HTML_PATH, HOME_PAGE_FILE_NAME);
    }

    private static String generateConferencePage(Conference conference) 
    {
        String fileName = conference.getEdition().getAcronyme().replace("'", "_").toUpperCase() + ".html";

        String htmlTemplateFile = TEMPLATES_PATH + CONFERENCE_PAGE_TEMPLATE_NAME;

        String htmlString = readTemplate(htmlTemplateFile);

        if (htmlString != null) 
        {
            htmlString = htmlString.replace("$ressourcesPath", HTML_RESSOURSES_PATH);
            htmlString = htmlString.replace("$homePage", HOME_PAGE_FILE_NAME);
            htmlString = htmlString.replace("$acronyme", conference.getEdition().getAcronyme());
            htmlString = htmlString.replace("$titre", conference.getEdition().getTitre());
            htmlString = htmlString.replace("$presidents", getPresidentsFormat(conference.getEdition().getPresidents()));
            htmlString = htmlString.replace("$ville", conference.getEdition().getVille());
            htmlString = htmlString.replace("$pays", conference.getEdition().getPays());
            htmlString = htmlString.replace("$siteWeb", conference.getEdition().getSiteWeb());
            htmlString = htmlString.replace("$dateDebut", dayFormat.format(conference.getEdition().getDateStart()));
            htmlString = htmlString.replace("$dateFin", fullDateFormat.format(conference.getEdition().getDateEnd()));
            htmlString = htmlString.replace("$statistiques", getConferenceStats(conference.getEdition().getAcceptances()));
            htmlString = htmlString.replace("$bestArticles", getBestArticle(conference));
            htmlString = htmlString.replace("$articles", getArticles(conference));
        }

        saveInFile(htmlString, HTML_PATH + CONFERENCES_PATH, fileName);

        return CONFERENCES_PATH + fileName;
    }

    private static String getPresidentsFormat(final List<String> presidents) 
    {
        String formatedStr = "";

        for (int i = 0; i < presidents.size(); i++) 
        {
            if (i == 0)
                formatedStr += presidents.get(i);
            else if (i != (presidents.size() - 1))
                formatedStr += ", " + presidents.get(i);
            else
                formatedStr += " et " + presidents.get(i);
        }

        return formatedStr;
    }

    private static String getConferences(final List<Conference> conferences) 
    {
        String htmlTemplateFile = TEMPLATES_PATH + CONFERENCE_ITEM_TEMPLATE_NAME;

        String formatedStr = "";

        Collections.sort(conferences, Collections.reverseOrder());

        for (Conference conference : conferences) 
        {
            String conferencePageUrl = generateConferencePage(conference);

            String conferenceItemHtmlString = readTemplate(htmlTemplateFile);

            if (conferenceItemHtmlString != null) 
            {
                conferenceItemHtmlString = conferenceItemHtmlString.replace("$conferencePageUrl", conferencePageUrl);
                conferenceItemHtmlString = conferenceItemHtmlString.replace("$acronyme", conference.getEdition().getAcronyme());
                conferenceItemHtmlString = conferenceItemHtmlString.replace("$titre", conference.getEdition().getTitre());
                conferenceItemHtmlString = conferenceItemHtmlString.replace("$presidents", getPresidentsFormat(conference.getEdition().getPresidents()));
                conferenceItemHtmlString = conferenceItemHtmlString.replace("$ville", conference.getEdition().getVille());
                conferenceItemHtmlString = conferenceItemHtmlString.replace("$pays", conference.getEdition().getPays());
                conferenceItemHtmlString = conferenceItemHtmlString.replace("$dateDebut", dayFormat.format(conference.getEdition().getDateStart()));
                conferenceItemHtmlString = conferenceItemHtmlString.replace("$dateFin", fullDateFormat.format(conference.getEdition().getDateEnd()));
            }

            formatedStr += conferenceItemHtmlString;
        }

        return formatedStr;
    }

    private static String getConferenceStats(ArrayList<Acceptance> stats) 
    {
        String formatedStr = "";

        for (Acceptance a : stats) 
        {
            formatedStr += "<img src=\"../" + HTML_RESSOURSES_PATH + "/puce.gif\" alt=\"-\">" + a.getSoumission() + " soumissions d'articles " + a.getId() + "<br>";
            formatedStr += "<img src=\"../" + HTML_RESSOURSES_PATH + "/puce.gif\" alt=\"-\">" + a.getName() + " articles " + a.getId() + " acceptés<br>";
        }
    
        return formatedStr;
    }

    private static String getBestArticle(final Conference conference) 
    {
        String formatedStr = "";

        for (String articleId : conference.getEdition().getBestArticle()) 
        {
            Article article = conference.getArticleById(articleId);

            if (article != null)
                formatedStr += "<img src=\"../" + HTML_RESSOURSES_PATH + "/puce.gif\" alt=\"-\"> " + getAuteursFormat(article) + " : <i>" + article.getTitre() + "</i><br>";
        }

        return formatedStr;
    }

    private static String getAuteursFormat(final Article article) 
    {
        ArrayList<Author> auteurs = article.getAuteurs();

        String formatedStr = "";

        for (int i = 0; i < auteurs.size(); i++)
        {
            if (i == 0)
                formatedStr += auteurs.get(i).getNom();
            else if (i != (auteurs.size() - 1))
                formatedStr += ", " + auteurs.get(i).getNom();
            else
                formatedStr += " et " + auteurs.get(i).getNom();
        }

        Set<String> tmpAffiliationsId = new HashSet<String>();

        for (Author auteur : auteurs)
            for (int id : auteur.getAffiliationId())
                tmpAffiliationsId.add(String.valueOf(id));

        if (tmpAffiliationsId.size() != 0) 
        {
            ArrayList<String> affiliationsId = new ArrayList<String>(tmpAffiliationsId);

            formatedStr += " (";
            for (int i = 0; i < affiliationsId.size(); i++) 
            {
                Affiliate affiliation = article.getAffiliationById(Integer.parseInt(affiliationsId.get(i)));

                if (affiliation != null) 
                {
                    if (i == 0) 
                        formatedStr += affiliation.getName();
                    else
                        formatedStr += " - " + affiliation.getName();
                }
            }
            formatedStr += ")";
        }

        return formatedStr;
    }

    private static String getArticleFormat(ArrayList<Article> articles) 
    {
        Collections.sort(articles);

        String formatedStr = "";

        for (Article article : articles)
            formatedStr += "<img src=\"../" + HTML_RESSOURSES_PATH + "/puce.gif\" alt=\"-\">" + getAuteursFormat(article) + ", <b>" + article.getTitre() + "</b><br>";

        return formatedStr;
    }

    private static String getArticles(Conference conference) 
    {
        String formatedStr = "";

        for (Type type : conference.getEdition().getTypeArticles()) 
        {
            formatedStr += "<p>" +
                    "<strong>" + type.getName() + "</strong>\n" +
                    "<br><br>\n" +
                    getArticleFormat(conference.getArticleByType(type.getId())) +
                    "</p>";
        }

        return formatedStr;
    }

    private static String readTemplate(String templatePath) 
    {
        try 
        {
            return new Scanner(new File(templatePath)).useDelimiter("\\Z").next();
        } 
        catch (FileNotFoundException e) 
        {
            System.err.println("Template \"" + templatePath + "\" not found");
        }

        return null;
    }
}