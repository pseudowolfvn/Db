package Db.Components.rmi.iiop.server2;

import Db.Components.rmi.iiop.interfaces.DatabaseManager;

import javax.naming.Context;
import javax.naming.InitialContext;

public class ServerIIOPtoCorba {
    public static void main(String[] args) {
        try {
            Context namingContext = new InitialContext();
            DatabaseManager databaseManager = new DatabaseManagerImpl(namingContext);
            namingContext.rebind("databaseManager_CORBA", databaseManager);

            System.out.println("CORBA compatible RMI over IIOP server started.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
