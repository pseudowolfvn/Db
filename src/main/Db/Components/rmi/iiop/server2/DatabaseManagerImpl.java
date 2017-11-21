package Db.Components.rmi.iiop.server2;

import Db.Components.DatabaseManager;
import Db.Components.rmi.iiop.interfaces.Database;

import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import java.io.IOException;
import java.rmi.RemoteException;

public class DatabaseManagerImpl extends PortableRemoteObject
        implements Db.Components.rmi.iiop.interfaces.DatabaseManager {
    Context context;

    public DatabaseManagerImpl(Context _context) throws RemoteException {
        super();
        this.context = _context;
    }

    @Override
    public String createDatabase(String name) {
        String dbCorbaName = "";
        try {
            Db.Components.Database db = DatabaseManager.createDatabase(name);
            Database database = new DatabaseImpl(context, db);

            dbCorbaName = db.getName() + "_CORBA";
            context.rebind(dbCorbaName, database);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dbCorbaName;
    }

    @Override
    public String[] getDatabaseNames() {
        return DatabaseManager.getDatabaseNames().toArray(new String[0]);
    }

    @Override
    public String getDatabase(String name) {
        String dbCorbaName = "";
        try {
            Db.Components.Database db = DatabaseManager.getDatabase(name);
            Database database = new DatabaseImpl(context, db);

            dbCorbaName = db.getName() + "_CORBA";
            context.rebind(dbCorbaName, database);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dbCorbaName;
    }

    @Override
    public boolean isExists(String name) {
        return DatabaseManager.isExists(name);
    }

    @Override
    public void deleteDatabase(String name) {
        DatabaseManager.deleteDatabase(name);
    }

    @Override
    public void renameDatabase(String oldName, String newName) {
        try {
            DatabaseManager.renameDatabase(oldName, newName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
