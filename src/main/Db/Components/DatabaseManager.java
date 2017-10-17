package Db.Components;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    static String path = "./out/db";
    public static List<String> getDatabaseNames() {
        List<String> names = new ArrayList<>();

        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String[] filenameTokens = file.getName().split("\\.(?=[^\\.]+$)");
                System.out.println(filenameTokens[0]);
                if (filenameTokens[1].equalsIgnoreCase("db"))
                    names.add(filenameTokens[0]);
            }
        }

        return names;
    }
    public static Database getDatabase(String name) {
        Database db = null;
        try{
            db = Database.load(path, name);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return db;
    }

    public static void deleteDatabase(String name) {
        List<String> names = new ArrayList<>();

        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String[] filenameTokens = file.getName().split("\\.(?=[^\\.]+$)");
                System.out.println(filenameTokens[0]);
                if (filenameTokens[1].equalsIgnoreCase("db")
                        && filenameTokens[0].equalsIgnoreCase(name))
                        file.delete();
            }
        }

    }
}
