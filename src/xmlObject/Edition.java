package xmlObject;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "edition")
@XmlType(propOrder =
		{
			"acronyme",
			"titre",
			"ville",
			"pays",
			"dateStart",
			"dateEnd",
			"president",
			"typeArticles",
			"statistique",
			"siteWeb",
			"meilleurArticle"
		})
public class Edition 
{
	private String acronyme;
	private String titre;
	private String ville;
	private String pays;
	private Date dateStart;
	private Date dateEnd;
	private ArrayList<String> presidents;
	private ArrayList<Type> typeArticles;
	private ArrayList<Acceptance> acceptances;
	private String siteWeb;
	private ArrayList<String> bestArticle;
	
	public Edition() 
	{
		this.presidents = new ArrayList<>();
		this.typeArticles = new ArrayList<>();
		this.acceptances = new ArrayList<>();
		this.bestArticle = new ArrayList<>();
	}

	public String getAcronyme() 
	{
		return acronyme;
	}

	public String getTitre() 
	{
		return titre;
	}

	public String getVille() 
	{
		return ville;
	}

	public String getPays() 
	{
		return pays;
	}

	public Date getDateStart() 
	{
		return dateStart;
	}

	public Date getDateEnd() 
	{
		return dateEnd;
	}

	public ArrayList<String> getPresidents() 
	{
		return presidents;
	}

	public ArrayList<Type> getTypeArticles() 
	{
		return typeArticles;
	}

	public ArrayList<Acceptance> getAcceptances() 
	{
		return acceptances;
	}

	public String getSiteWeb() 
	{
		return siteWeb;
	}

	public ArrayList<String> getBestArticle() 
	{
		return bestArticle;
	}

	public void setAcronyme(String acronyme) 
	{
		this.acronyme = acronyme;
	}

	public void setTitre(String titre) 
	{
		this.titre = titre;
	}

	public void setVille(String ville) 
	{
		this.ville = ville;
	}

	public void setPays(String pays) 
	{
		this.pays = pays;
	}

	public void setDateStart(Date dateStart) 
	{
		this.dateStart = dateStart;
	}

	public void setDateEnd(Date dateEnd) 
	{
		this.dateEnd = dateEnd;
	}

	@XmlElementWrapper(name = "presidents")
	@XmlElement(name = "nom")
	public void setPresidents(ArrayList<String> presidents) 
	{
		this.presidents = presidents;
	}

	@XmlElementWrapper(name = "typeArticles")
	@XmlElement(name = "type")
	public void setTypeArticles(ArrayList<Type> typeArticles) 
	{
		this.typeArticles = typeArticles;
	}

	@XmlElementWrapper(name = "statistiques")
	@XmlElement(name = "acceptations")
	public void setAcceptances(ArrayList<Acceptance> acceptances) 
	{
		this.acceptances = acceptances;
	}

	public void setSiteWeb(String siteWeb) 
	{
		this.siteWeb = siteWeb;
	}

	@XmlElementWrapper(name = "meilleurArticle")
	@XmlElement(name = "articleId")
	public void setBestArticle(ArrayList<String> bestArticle) 
	{
		this.bestArticle = bestArticle;
	}
	
	public String toString()
	{
		return "Edition{acronyme='" + this.acronyme + "', titre='" + this.titre + "', ville='" + this.ville + "', pays='" + this.pays + "', dateDebut=" + this.dateStart + ", dateFin=" + this.dateEnd + ", presidents=" + this.presidents + ", typeArticles=" + this.typeArticles + ", statistiques=" + this.acceptances + ", siteWeb='" + this.siteWeb + "', meilleurArticle=" + this.bestArticle + "}"; 
	}
	
	public String to_html() {
		String html = "";
		return html;
	}
}
