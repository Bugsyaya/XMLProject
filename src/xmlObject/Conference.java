package xmlObject;


import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "conference")
@XmlType(propOrder=
		{
			"edition",
			"articles"
		})
public class Conference implements Comparable<Conference>
{
	private ArrayList<Article> articles;
	
	private Edition edition;
	
	public ArrayList<Article> getArticles() 
	{
		return articles;
	}
	
	public Edition getEdition() 
	{
		return edition;
	}

	@XmlElementWrapper(name = "articles")
	@XmlElement(name = "article")
	public void setArticles(ArrayList<Article> articles)
	{
		this.articles = articles;
	}

	public void setEdition(Edition edition) 
	{
		this.edition = edition;
	}

	public Article getArticleById(String id)
	{
		for (Article article : articles)
			if ((article.getId()).equals(id))
				return article;
		
		return null;
	}

	public ArrayList<Article> getArticleByType(String type) 
	{
		ArrayList<Article> listArticle = new ArrayList<>();
		
		for (Article article : articles)
			if (type.equalsIgnoreCase((article.getType())))
				listArticle.add(article);
		
		return listArticle;
	}
	
	@Override
	public int compareTo(Conference conference) {
		return getEdition().getDateEnd().compareTo(conference.getEdition().getDateEnd());
	}
	
}
