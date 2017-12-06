package Db.Components;

import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {
    @Test
    public void createTest() {
        String path = "/home/charmer/dev/projects/KNU/IT/Db/out/db";
        String name = "junit_test1_db";
        if (DatabaseManager.isExists(name))
            DatabaseManager.deleteDatabase(name);
        Database db = new Database(path, name);
        try {
            db.save();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        assertEquals(DatabaseManager.isExists(name), true);
    }

    @Test
    public void renameTest() {
        String path = "/home/charmer/dev/projects/KNU/IT/Db/out/db";
        String name = "junit_test2_db";
        if (DatabaseManager.isExists(name))
            DatabaseManager.deleteDatabase(name);
        Database db = new Database(path, name);
        try {
            db.save();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        String renamed = name + "_renamed";
        try {
            DatabaseManager.renameDatabase(name, renamed);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        assertEquals(DatabaseManager.isExists(renamed), true);
    }

    @Test
    public void deleteTest() {
        String path = "/home/charmer/dev/projects/KNU/IT/Db/out/db";
        String name = "junit_test3_db";
        if (DatabaseManager.isExists(name))
            DatabaseManager.deleteDatabase(name);
        Database db = new Database(path, name);
        try {
            db.save();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        try {
            DatabaseManager.deleteDatabase(name);
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        assertEquals(DatabaseManager.isExists(name), false);
    }
}