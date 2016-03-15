package xmlObject;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "auteur")
@XmlType(propOrder =
		{
			"nom",
			"email",
			"affiliationId"
		})
public class Auteur 
{
	private ArrayList<Integer> affiliationId;
	
	private String email;
	private String nom;
	
	public String getNom() 
	{
		return nom;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public ArrayList<Integer> getAffiliationId() 
	{
		return affiliationId;
	}
	
	public void setNom(String nom) 
	{
		this.nom = nom;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	@XmlList
	@XmlElement(name = "affiliationId")
	public void setAffiliationId(ArrayList<Integer> affiliationId) 
	{
		this.affiliationId = affiliationId;
	}
	
	public String toString()
	{
		return "Auteur{nom='" + this.nom + "', email='" + this.email + "', affiliations=" + this.affiliationId + "}";
	}
}
