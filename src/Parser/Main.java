package Parser;

import javax.xml.xpath.XPathExpressionException;

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
		try {
			parser.buildConferences();
		} catch(XPathExpressionException e) {
			System.out.println("Problème dans l'expression XPath : ");
			System.out.println(e.getMessage());
		}
		
	}

}
