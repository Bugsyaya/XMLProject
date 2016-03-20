package application;

import java.util.ArrayList;

import javax.xml.xpath.XPathExpressionException;

import Parser.DomXPath;
import Parser.HtmlGenerate;
import xmlObject.Article;
import xmlObject.Conference;

public class Launcher_DomXPath implements Launcher {
	
	public void run() {

		System.out.println();
		System.out.println("Test du parser DomXPath");
		System.out.println("=======================");
		DomXPath parser = new DomXPath(HtmlGenerate.XML_FILE_NAME);
		System.out.print("L'analyse du document peut prendre quelques secondes...");
		ArrayList<Conference> conferences = new ArrayList<>();
		try {
			conferences = parser.buildConferences();
//			for (Conference c: conferences) {
//				System.out.println("Conférence : " + c.getEdition().getTitre());
//				for (Article a: c.getArticles()) {
//					System.out.println(" . " + a.getTitre());
//				}
//			}
		} catch(XPathExpressionException e) {
			System.out.println("Problème dans l'expression XPath : ");
			System.out.println(e.getMessage());
		}
		
	    if (conferences != null) {
	        System.out.println(conferences.size() + " conférences ont été récupérées du fichier XML\n");
	        HtmlGenerate.generateHomePage(conferences);
	    }
		
	}

}
