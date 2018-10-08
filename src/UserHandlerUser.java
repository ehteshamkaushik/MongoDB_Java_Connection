

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserHandlerUser extends DefaultHandler {
    int noOfRows=0;
    int Id, Reputation, Age, Views, Upvotes, DownVotes;
    String DisplayName, EmailHash, WebsiteUrl, Location, AboutMe;
    Date date, LastAccessDate;
    DBCollection coll=null;


    public UserHandlerUser(DBCollection c)
    {
        coll=c;
    }

    @Override
    public void startElement(String uri,
                             String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("row")) {

            Id = Integer.parseInt(attributes.getValue("Id"));
            Reputation = Integer.parseInt(attributes.getValue("Reputation"));
            DisplayName = attributes.getValue("DisplayName");
            EmailHash = attributes.getValue("EmailHash");
            WebsiteUrl = attributes.getValue("WebsiteUrl");
            Location = attributes.getValue("Location");
            try {
                Age = Integer.parseInt(attributes.getValue("Age"));
            }catch (Exception e)
            {
                Age = -1;
            }
            Views = Integer.parseInt(attributes.getValue("Views"));
            Upvotes = Integer.parseInt(attributes.getValue("UpVotes"));
            DownVotes = Integer.parseInt(attributes.getValue("DownVotes"));
            AboutMe = attributes.getValue("AboutMe");
            String d[] = attributes.getValue("CreationDate").split("T");
            String newdate = d[0] + " " + d[1];

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            try {
                date = df.parse(newdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            d = attributes.getValue("LastAccessDate").split("T");
            newdate = d[0] + " " + d[1];

            df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            try {
                LastAccessDate = df.parse(newdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            try {
                BasicDBObject doc = new BasicDBObject("Id", Id).
                        append("Reputation", Reputation).
                        append("CreationDate", date).
                        append("DisplayName", DisplayName).
                        append("EmailHash", EmailHash).
                        append("LastAccessDate", LastAccessDate).
                        append("WebsiteUrl", WebsiteUrl).
                        append("Location", Location).
                        append("Age", Age).
                        append("AboutMe", AboutMe).
                        append("Views", Views).
                        append("UpVotes", Upvotes).
                        append("DownVotes", DownVotes)
                        ;
                coll.insert(doc);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            noOfRows++;
            System.out.println(noOfRows);
        }
    }

    @Override
    public void endDocument()
    {
        System.out.println(noOfRows);
    }
}
