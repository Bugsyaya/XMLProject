package xmlObject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "type")
public class Type 
{
	private String id;
	private String name;
	
	public String getId() 
	{
		return id;
	}
	
	public String getName() 
	{
		return name;
	}
	
	@XmlAttribute(name = "id")
	public void setId(String id) 
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
		return "Type{id='" + this.id + "', name='" + this.name + "'}";
	}
	
	public String to_html() {
		String html = "";
		return html;
	}
}
