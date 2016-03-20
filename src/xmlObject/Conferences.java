package xmlObject;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "conferences")
public class Conferences 
{
	ArrayList<Conference> conferences;

	public ArrayList<Conference> getConferences() 
	{
		return conferences;
	}

	@XmlElement(name = "conference")
	public void setConferences(ArrayList<Conference> conferences) 
	{
		this.conferences = conferences;
	}
	
	public String to_html() {
		String html = "";
		return html;
	}
}
