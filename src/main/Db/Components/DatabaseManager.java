package Db.Components;

import java.io.File;
import java.io.IOException;
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
                if (filenameTokens.length > 1
                        && filenameTokens[1].equalsIgnoreCase("db"))
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
    }
}
