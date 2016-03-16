package Parser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class JaxDate extends XmlAdapter<String, Date>
{
	final DateFormat formatDate = new SimpleDateFormat("dd/MM/yy");
	
	public Date unmarshal(String date) throws Exception 
	{
		return formatDate.parse(date);
	}

	@Override
	public String marshal(Date date) throws Exception 
	{
		return formatDate.format(date);
	}

}
