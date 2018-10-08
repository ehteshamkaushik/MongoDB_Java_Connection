

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserHandler extends DefaultHandler {
    int noOfRows=0;
    int id, postid, score, userid;
    String text;
    Date time;
    DBCollection coll=null;

    public UserHandler(DBCollection c)
    {
        coll=c;
    }

    @Override
    public void startElement(String uri,
                             String localName, String qName, Attributes attributes)
            throws SAXException {
        if (qName.equalsIgnoreCase("row")) {

            id = Integer.parseInt(attributes.getValue("Id"));
            postid = Integer.parseInt(attributes.getValue("PostId"));
            score = Integer.parseInt(attributes.getValue("Score"));
            try {
                userid = Integer.parseInt(attributes.getValue("UserId"));
            }
            catch (Exception e)
            {
                userid = -1;
            }
            text = attributes.getValue("Text");
            String d[] = attributes.getValue("CreationDate").split("T");
            String newdate = d[0] + " " + d[1];

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            try {
                time = df.parse(newdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                BasicDBObject doc = new BasicDBObject("Id", id).
                        append("PostId", postid).
                        append("Score", score).
                        append("UserId", userid).
                        append("Text", text).
                        append("CreationDate", time);
                coll.insert(doc);
            }catch (Exception e)
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
