package Db.Components;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseManager {
    static String path = "./out/db";

    static HashMap<String, Database> session = new HashMap<>();

    public static void endSession() {
        for (Database db: session.values()) {
            try {
                db.save();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }


    public static void setPath(String path) {
        DatabaseManager.path = path;
    }

    public static Database createDatabase(String name) throws IOException {
        Database db = new Database(path, name);
        session.put(name, db);
        return db;
    }

    public static List<String> getDatabaseNames() {
        List<String> names = new ArrayList<>();

        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String[] filenameTokens = file.getName().split("\\.(?=[^\\.]+$)");
                System.out.println(filenameTokens[0]);
                if (filenameTokens.length > 1
                        && filenameTokens[1].equalsIgnoreCase("db") || true)
                    names.add(filenameTokens[0]);
            }
        }

        return names;
    }
    public static Database getDatabase(String name) {
        if (session.containsKey(name))
            return session.get(name);
        Database db = null;
        try{
            db = Database.load(path, name);
            session.put(name, db);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return db;
    }

    public static boolean isExists(String name) {
        List<String> names = new ArrayList<>();

        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String[] filenameTokens = file.getName().split("\\.(?=[^\\.]+$)");
                System.out.println(filenameTokens[0]);
                if (filenameTokens[1].equalsIgnoreCase("db")
                        && filenameTokens[0].equalsIgnoreCase(name))
                    return true;
            }
        }
        return false;
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

        if (session.containsKey(name))
            session.remove(name);
    }

    public static void renameDatabase(String oldName, String newName) throws IOException {
        List<String> names = new ArrayList<>();

        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                String[] filenameTokens = file.getName().split("\\.(?=[^\\.]+$)");
                System.out.println(filenameTokens[0]);
                if (filenameTokens[1].equalsIgnoreCase("db")
                        && filenameTokens[0].equalsIgnoreCase(oldName))
                    Files.move(file.toPath(), file.toPath().resolveSibling(newName + ".db"));
            }
        }

        if (session.containsKey(oldName)) {
            Database db = session.get(oldName);
            session.remove(oldName);
            session.put(newName, db);
        }
    }
}
