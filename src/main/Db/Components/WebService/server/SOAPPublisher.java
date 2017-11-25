package Db.Components.WebService.server;

import javax.xml.ws.Endpoint;

public class SOAPPublisher {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:1234/ws/db", new IDBJWXImpl());
    }

}