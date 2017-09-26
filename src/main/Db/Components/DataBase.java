package Db.Components;

import Db.Exceptions.IllegalConversionException;
import Db.Exceptions.UnsupportedTypeException;
import Db.Types.DbType;
import Db.Types.DbTypesFactory;

import java.io.*;
import java.util.*;


public class DataBase implements Serializable {
    private String name;
    private Set<String> tableNames;
    private List<Table> dataBase;

    public DataBase(String name) {
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

    public void save(String path) throws IOException {
        FileOutputStream fileOut =
                new FileOutputStream(path);
        ObjectOutputStream objOut =
                new ObjectOutputStream(fileOut);
        objOut.writeObject(this);
        objOut.close();
        fileOut.close();
    }

    public static DataBase load(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileIn =
                new FileInputStream(path);
        ObjectInputStream objIn =
                new ObjectInputStream(fileIn);
        DataBase db = (DataBase) objIn.readObject();
        objIn.close();
        fileIn.close();
        return db;
    }
}
