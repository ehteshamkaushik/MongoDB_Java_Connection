import com.mongodb.BasicDBObject;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Set;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.io.File;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class JavaMongoUser {

    public static final String dbName="myMongoDB";
    public static final String colName="Users_table";
    private static long starttime = System.currentTimeMillis();
    public static void main(String[] args) {
        try {

            MongoClient mongoClient = new MongoClient("localhost");


            DB db = mongoClient.getDB(dbName);
            db.createCollection(colName,null);
            DBCollection coll = db.getCollection(colName);

            File file = new File("E:\\Users.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            UserHandlerUser userhandler = new UserHandlerUser(coll);
            saxParser.parse(file, userhandler);

            mongoClient.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        long endtime = System.currentTimeMillis();
        long duration = endtime-starttime;
        System.out.println("Time taken : " + duration + " millisec");
    }
}

            