package Db.Components;

import java.io.*;
import java.util.*;


public class Database implements Serializable {
    private String name, path;
    private Set<String> tableNames;
    private List<Table> dataBase;

    public Database(String path, String name) {
        this.path = path;
        this.name = name;
        this.tableNames = new HashSet<>();
        this.dataBase = new ArrayList<>();
    }

    public boolean addTable(String name) {
        if (tableNames.contains(name))
            return false;
        dataBase.add(new Table(name));
        tableNames.add(name);
        return true;
    }

    public List<String> getTableNames() {
        List<String> names = new ArrayList<>();
        names.addAll(tableNames);
        return names;
    }

    public String getName() {
        return name;
    }

    public Table getTable(String name) {
        if (!tableNames.contains(name))
            return null;
        for (int i = 0; i < dataBase.size(); ++i)
            if (Objects.equals(dataBase.get(i).getName(), name))
                return dataBase.get(i);
        return null;
    }

    public void save() throws IOException {
        FileOutputStream fileOut =
                new FileOutputStream(path + "/" + name + ".db");
        ObjectOutputStream objOut =
                new ObjectOutputStream(fileOut);
        objOut.writeObject(this);
        objOut.close();
        fileOut.close();
    }

    public static Database load(String path, String name) throws IOException, ClassNotFoundException {
        FileInputStream fileIn =
                new FileInputStream(path + "/" + name + ".db");
        ObjectInputStream objIn =
                new ObjectInputStream(fileIn);
        Database db = (Database) objIn.readObject();
        objIn.close();
        fileIn.close();
        return db;
    }
}
