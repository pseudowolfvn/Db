package Db.Components;

import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {
    @Test
    public void featuresTest() {
        String path = "/home/pseudowolf/dev/Projects/KNU/IT/Db/out/db";
        String name = "junit_test_db";
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
        DatabaseManager.deleteDatabase(renamed);
        assertEquals(DatabaseManager.isExists(renamed), false);
        DatabaseManager.deleteDatabase(name);
        assertEquals(DatabaseManager.isExists(name), false);
    }
}