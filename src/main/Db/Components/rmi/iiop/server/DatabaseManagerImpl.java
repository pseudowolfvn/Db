package Db.Components.rmi.iiop.server;

import Db.Components.Database;
import Db.Components.rmi.jrmp.interfaces.DatabaseManager;

import javax.rmi.PortableRemoteObject;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.List;

public class DatabaseManagerImpl extends PortableRemoteObject implements DatabaseManager {
    /**
     * Initializes the object by calling <code>exportObject(this)</code>.
     *
     * @throws RemoteException if export fails.
     */
    protected DatabaseManagerImpl() throws RemoteException {
    }

    public Database createDatabase(String name) {
        Database db = null;
        try {
            db = Db.Components.DatabaseManager.createDatabase(name);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return db;
    }

    @Override
    public List<String> getDatabaseNames() {
        return Db.Components.DatabaseManager.getDatabaseNames();
    }

    @Override
    public Database getDatabase(String name) {
        return Db.Components.DatabaseManager.getDatabase(name);
    }

    @Override
    public boolean isExists(String name) {
        return Db.Components.DatabaseManager.isExists(name);
    }

    @Override
    public void deleteDatabase(String name) {
        Db.Components.DatabaseManager.deleteDatabase(name);
    }

    @Override
    public void renameDatabase(String oldName, String newName) {
        try {
            Db.Components.DatabaseManager.renameDatabase(oldName, newName);
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
