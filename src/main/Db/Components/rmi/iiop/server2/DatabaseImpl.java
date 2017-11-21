package Db.Components.rmi.iiop.server2;

import Db.Components.Database;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import java.io.IOException;
import java.rmi.RemoteException;

public class DatabaseImpl extends PortableRemoteObject
        implements Db.Components.rmi.iiop.interfaces.Database {
    Context context;
    Database db;

    public DatabaseImpl(Context _context, Database _db) throws RemoteException {
        super();
        this.context = _context;
        this.db = _db;
    }

    @Override
    public boolean addTable(String name) {
        return db.addTable(name);
    }

    @Override
    public String[] getTableNames() {
        return db.getTableNames().toArray(new String[0]);
    }

    @Override
    public String getName() {
        return db.getName();
    }

    @Override
    public String getTable(String name) {
        String tableCorbaName = "";
        try {
            TableImpl table = new TableImpl(db.getTable(name));

            tableCorbaName = table.getName() + "_CORBA";
            context.rebind(tableCorbaName, table);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return tableCorbaName;
    }

    @Override
    public void renameTable(String oldName, String newName) {
        db.renameTable(oldName, newName);
    }

    @Override
    public void deleteTable(String name) {
        db.deleteTable(name);
    }

    @Override
    public void save() {
        try {
            db.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
