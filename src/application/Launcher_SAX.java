package application;

import java.util.ArrayList;

import Parser.HtmlGenerate;
import Parser.Sax;
import xmlObject.Conference;

public class Launcher_SAX implements Launcher {

	public void run() {
	
		System.out.println();
		System.out.println("Test du parser Sax");
		System.out.println("=======================");
		final ArrayList<Conference> conferencesSax = new Sax(HtmlGenerate.XML_FILE_NAME).parserXml();
	
		System.out.println(HtmlGenerate.XML_FILE_NAME);
		
	    if (conferencesSax != null) {
	        System.out.println(conferencesSax.size() + " conférences ont été récupérées du fichier XML\n");
	        HtmlGenerate.generateHomePage(conferencesSax);
	    }
	    
	}
}
