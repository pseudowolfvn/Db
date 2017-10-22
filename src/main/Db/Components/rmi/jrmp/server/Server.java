package Db.Components.rmi.jrmp.server;

import Db.Components.rmi.jrmp.interfaces.DatabaseManager;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;

public class Server {
    public static void main(String[] args) {
        try {
            String rmiRegistryIp = "127.0.0.1";
            String rmiRegistryPort = "1099";

            DatabaseManager databaseManager
                    = (DatabaseManager) UnicastRemoteObject.exportObject(new DatabaseManagerImpl(), 0);
            Naming.rebind("rmi://" + rmiRegistryIp + ":" + rmiRegistryPort + "/databaseManager", databaseManager);
            System.out.println("Server started.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
