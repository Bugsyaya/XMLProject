package Parser;

import java.util.ArrayList;

import javax.xml.xpath.XPathExpressionException;

import xmlObject.Article;
import xmlObject.Conference;

public class Main {

	public static void main(String[] args) {
		
		String fileName = "xmlFile/TALN-RECITAL-BIB.xml";

		System.out.println("Début des tests");
		System.out.println("===============");
		
		
		System.out.println();
		System.out.println("Test du parser Sax");
		System.out.println("=======================");
		
		
		System.out.println();
		System.out.println("Test du parser DomXPath");
		System.out.println("=======================");
		DomXPath parser = new DomXPath(fileName);
		System.out.println("L'analyse du document peut prendre quelques secondes...");
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
