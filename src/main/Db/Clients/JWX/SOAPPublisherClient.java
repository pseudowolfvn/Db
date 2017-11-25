package Db.Clients.JWX;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import Db.Components.Database;
import Db.Components.WebService.server.IDBJWX;

public class SOAPPublisherClient {
    public static void main(String[] args) throws MalformedURLException {
        URL wsdlURL = new URL("http://localhost:1234/ws/db?wsdl");

        //creating QName using targetNamespace and name
        QName qname = new QName("http://server.WebService.Components.Db/", "IDBJWXImplService");

        Service service = Service.create(wsdlURL, qname);

        //We need to pass interface and model beans to client
        IDBJWX ps = service.getPort(IDBJWX.class);

        Database db1 = new Database("/home/charmer/dev/projects/KNU/IT/Db/out/db", "JWXTest.db"); db1.addTable("test");

        Database db2 = new Database("/home/charmer/dev/projects/KNU/IT/Db/out/db", "JWXTest1.db"); db2.addTable("test1");

        System.out.println("Add DB Status="+ps.addDatabase(db1));
        System.out.println("Add DB Status="+ps.addDatabase(db2));


        System.out.println(ps.getDatabase("JWXTest.db"));

        System.out.println("Delete DB Status="+ps.deleteDatabase("JWXTest1.db"));

    }
}
