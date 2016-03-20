package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.xpath.XPathExpressionException;

import Parser.DomXPath;
import Parser.Sax;
import xmlObject.Article;
import xmlObject.Conference;
import Parser.HtmlGenerate;


public class Main {

	public static void main(String[] args) {
		
		Launcher launcher = new Launcher_SAX();

		System.out.println("Quelle implémentation pour générer le html ?");
		System.out.println("  (d) : DomXPath");
		System.out.println("  (s) : SAX (par défaut)");
		System.out.println("  (q) : quitter");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String selectedOption = ""; 
        try {
            selectedOption = br.readLine();
            switch (selectedOption) {
                case "d":
                    launcher = new Launcher_DomXPath();
                    break;
                case "s":
                	launcher = new Launcher_SAX();
                    break;
                case "q":
                    System.exit(0);;
                    break;
                default:
                	main(new String[] {});
            }
        } catch (IOException ioe) {
        	main(new String[] {});
        }
		
		
		launcher.run();
		

		

		
	}

}
