package xmlObject;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "article")
@XmlType(propOrder=
		{
			"auteurs",
			"affiliations",
			"titre",
			"type",
			"page",
			"resume",
			"mot_cle",
			"abstract_libelle",
			"keywords"
		})
public class Article implements Comparable<Article>
{
	
	private ArrayList<Affiliate> affiliations;
	private ArrayList<Author> auteurs;

	private String abstrace_libelle;
	private String id;
	private String keyword;
	private String mot_cle;
	private String page;
	private String resume;
	private String session;
	private String titre;
	private String type;

	public Article()
	{
		this.auteurs = new ArrayList<>();
		this.affiliations = new ArrayList<>();
	}
	
	@Override
	public int compareTo(Article article) 
	{
		int result = 0;
		
		if (auteurs.size() > 0 && article.getAuteurs().size() > 0)
			result = auteurs.get(0).getNom().compareTo(article.getAuteurs().get(0).getNom());

		return result;
	}

	public String getAbstrace_libelle() 
	{
		return abstrace_libelle;
	}

	public Affiliate getAffiliationById(int id)
	{
		for (Affiliate affiliation : affiliations)
			if (id == affiliation.getId())
				return affiliation;
		
		return null;
	}

	public ArrayList<Affiliate> getAffiliations() 
	{
		return affiliations;
	}

	public ArrayList<Author> getAuteurs() 
	{
		return auteurs;
	}

	public String getId() 
	{
		return id;
	}

	public String getKeyword() 
	{
		return keyword;
	}

	public String getMot_cle() 
	{
		return mot_cle;
	}

	public String getPage() 
	{
		return page;
	}

	public String getResume() 
	{
		return resume;
	}

	public String getSession() 
	{
		return session;
	}

	public String getTitre() 
	{
		return titre;
	}

	public String getType() 
	{
		return type;
	}

	@XmlElement(name = "abstract")
	public void setAbstrace_libelle(String abstrace_libelle) 
	{
		this.abstrace_libelle = abstrace_libelle;
	}

	@XmlElementWrapper(name = "affiliations")
	public void setAffiliations(ArrayList<Affiliate> affiliations) 
	{
		this.affiliations = affiliations;
	}

	@XmlElementWrapper(name = "auteurs")
	@XmlElement(name = "auteur")
	public void setAuteurs(ArrayList<Author> auteurs) 
	{
		this.auteurs = auteurs;
	}

	@XmlAttribute(name = "id")
	public void setId(String id) 
	{
		this.id = id;
	}

	public void setKeyword(String keyword) 
	{
		this.keyword = keyword;
	}

	public void setMot_cle(String mot_cle) 
	{
		this.mot_cle = mot_cle;
	}

	public void setPage(String page) 
	{
		this.page = page;
	}

	public void setResume(String resume) 
	{
		this.resume = resume;
	}

	@XmlAttribute(name = "session")
	public void setSession(String session) 
	{
		this.session = session;
	}

	public void setTitre(String titre) 
	{
		this.titre = titre;
	}
	
	public void setType(String type) 
	{
		this.type = type;
	}
	
	public String toString()
	{
		return "Article{id=" + this.id + ", session='" + this.session + "', auteurs=" + this.auteurs + ", affiliations=" + this.affiliations + ", titre='" + this.titre + "', type='" + this.type + "', pages='" + this.page + "', resume='" + this.resume + "', mots_cles='" + this.mot_cle + "', abstract_libelle='" + this.abstrace_libelle + "', keywords='" + this.keyword + "'}";
	}
	
	public String to_html() {
		String html = "";
		return html;
	}
}