package xmlObject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Acceptation {
	private String id;
	private int soumission;
	private String name;
	
	public String getId() {
		return id;
	}
	
	public int getSoumission() {
		return soumission;
	}
	
	public String getName() {
		return name;
	}

	@XmlAttribute(name = "id")
	public void setId(String id) {
		this.id = id;
	}

	@XmlAttribute(name = "soumission")
	public void setSoumission(int soumission) {
		this.soumission = soumission;
	}

	@XmlValue
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() 
	{
		return "Acceptations{" + "id='" + this.id + "', soumissions=" + this.soumission + ", name='" + this.name + "'}";
	}
}
