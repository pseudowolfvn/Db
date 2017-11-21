package Db.Components.corba.interfaces;

import Db.Components.Database;
import Db.Components.DatabaseManager;
import Db.Components.corba.DatabaseHelper;
import Db.Components.corba.DatabasePOA;
import Db.Components.corba.Table;
import Db.Components.corba.TableHelper;
import org.omg.CosNaming.NamingContextExt;
import org.omg.PortableServer.POA;

import java.io.IOException;

public class DatabaseImpl extends DatabasePOA {
    NamingContextExt context;
    POA poa;
    Database db;

    public DatabaseImpl(POA _poa, NamingContextExt _context, Database _db) {
        this.poa = _poa;
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

            org.omg.CORBA.Object ref = poa.servant_to_reference(table);
            Table href = TableHelper.narrow(ref);
            tableCorbaName = table.getName() + "_CORBA";
            context.rebind(context.to_name(tableCorbaName), href);
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
