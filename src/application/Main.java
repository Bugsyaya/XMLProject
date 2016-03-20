package application;

import java.util.ArrayList;

import javax.xml.xpath.XPathExpressionException;

import Parser.DomXPath;
import Parser.Sax;
import xmlObject.Article;
import xmlObject.Conference;
import Parser.HtmlGenerate;


public class Main {

	public static void main(String[] args) {

		System.out.println("Début des tests");
		System.out.println("===============");
		
		
		System.out.println();
		System.out.println("Test du parser Sax");
		System.out.println("=======================");
		final ArrayList<Conference> conferencesSax = new Sax(HtmlGenerate.XML_FILE_NAME).parserXml();

        if (conferencesSax != null) {
            System.out.println(conferencesSax.size() + " conférences ont été récupérées du fichier XML\n");
            HtmlGenerate.generateHomePage(conferencesSax);
        }
		
		System.out.println();
		System.out.println("Test du parser DomXPath");
		System.out.println("=======================");
		DomXPath parser = new DomXPath(HtmlGenerate.XML_FILE_NAME);
		System.out.print("L'analyse du document peut prendre quelques secondes...");
		try {
			ArrayList<Conference> conferences = parser.buildConferences();
			for (Conference c: conferences) {
				System.out.println("Conférence : " + c.getEdition().getTitre());
				for (Article a: c.getArticles()) {
					System.out.println(" . " + a.getTitre());
				}
			}
		} catch(XPathExpressionException e) {
			System.out.println("Problème dans l'expression XPath : ");
			System.out.println(e.getMessage());
		}
		
	}

}
