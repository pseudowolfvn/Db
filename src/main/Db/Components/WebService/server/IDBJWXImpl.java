package Db.Components.WebService.server;

import javax.jws.WebService;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import Db.Components.Database;
import Db.Components.WebService.server.IDBJWX;

@WebService(endpointInterface = "Db.Components.WebService.server.IDBJWX")
public class IDBJWXImpl implements IDBJWX {

    private static Map<String, Database> DBs = new HashMap<String, Database>();

    @Override
    public boolean addDatabase(Database db) {
        if(DBs.get(db.getName()) != null) return false;
        DBs.put(db.getName(), db);
        return true;
    }

    @Override
    public boolean deleteDatabase(String name) {
        if (DBs.get(name) == null) return false;
        DBs.remove(name);
        return true;
    }

    @Override
    public Database getDatabase(String name) {
        return DBs.get(name);
    }
}
