package xmlObject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "affiliation")
public class Affiliate 
{
	private int id;
	private String name;
	
	
	public int getId() 
	{
		return id;
	}
	
	public String getName() 
	{
		return name;
	}
	
	@XmlAttribute(name = "affiliationId")
	public void setId(int id) 
	{
		this.id = id;
	}
	
	@XmlValue
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String toString()
	{
		return "Affiliation{affiliationId='" + this.id + "', name='" + this.name +"'}";
	}
}
