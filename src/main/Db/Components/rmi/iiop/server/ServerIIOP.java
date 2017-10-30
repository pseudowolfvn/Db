package Db.Components.rmi.iiop.server;

import Db.Components.rmi.jrmp.interfaces.DatabaseManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

public class ServerIIOP {
    public static void main(String[] args) {
        try {
            DatabaseManager databaseManager = new DatabaseManagerImpl();

            Context namingContext = new InitialContext();
            namingContext.rebind("databaseManager", databaseManager);

            System.out.println("Server RMI over IIOP started.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
