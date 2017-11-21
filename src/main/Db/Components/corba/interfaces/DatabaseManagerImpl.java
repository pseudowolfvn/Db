package Db.Components.corba.interfaces;

import Db.Components.DatabaseManager;
import Db.Components.corba.Database;
import Db.Components.corba.DatabaseHelper;
import Db.Components.corba.DatabaseManagerPOA;
import org.omg.CosNaming.NamingContextExt;
import org.omg.PortableServer.POA;

import java.io.IOException;

public class DatabaseManagerImpl extends DatabaseManagerPOA {
    NamingContextExt context;
    POA poa;

    public DatabaseManagerImpl(POA _poa, NamingContextExt _context) {
        this.poa = _poa;
        this.context = _context;
    }

    @Override
    public String createDatabase(String name) {
        String dbCorbaName = "";
        try {
            Db.Components.Database db = DatabaseManager.createDatabase(name);
            DatabaseImpl database = new DatabaseImpl(poa, context, db);

            org.omg.CORBA.Object ref = poa.servant_to_reference(database);
            Database href = DatabaseHelper.narrow(ref);
            dbCorbaName = db.getName() + "_CORBA";
            context.rebind(context.to_name(dbCorbaName), href);
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
            DatabaseImpl database = new DatabaseImpl(poa, context, db);

            org.omg.CORBA.Object ref = poa.servant_to_reference(database);
            Database href = DatabaseHelper.narrow(ref);
            dbCorbaName = db.getName() + "_CORBA";
            context.rebind(context.to_name(dbCorbaName), href);
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
