package Db.Components;

import Db.Exceptions.IllegalConversionException;
import Db.Exceptions.UnsupportedTypeException;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableTest {
    public static void tableTest() {
        Table table = new Table("Calendar");
        table.addColumn("Day", "Int");
        table.addColumn("Month", "Int");
        table.addColumn("Year", "Int");
        try {
            table.insertRow("28", "09", "2017");
            table.insertRow("30","01","2016");
            table.insertRow("03","10","2015");
            table.printToStdout();
            table.deleteColumn(0);
            table.deleteRow(1);
            table.deleteColumn("Month");
            table.deleteRow(1);
            table.printToStdout();
            table.save("/home/pseudowolf/dev/Projects/KNU/IT/Db/out/db/Calendar.dat");
        } catch (IllegalConversionException | UnsupportedTypeException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadTest() {
        try {
            Table table = Table.load("/home/pseudowolf/dev/Projects/KNU/IT/Db/out/db/Calendar.dat");
            table.printToStdout();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        loadTest();
    }
}