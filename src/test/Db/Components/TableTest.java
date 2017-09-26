package Db.Components;

import Db.Exceptions.IllegalConversionException;
import Db.Exceptions.UnsupportedTypeException;

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
            table.deleteColumn(0);
            table.deleteRow(1);
            table.deleteColumn("Month");
            table.deleteRow(1);
            List<List<String>> view = table.getStringView();
            for (List<String> row : view) {
                for (String data : row) {
                    System.out.print(data + " ");
                }
                System.out.println();
            }
        } catch (IllegalConversionException | UnsupportedTypeException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        tableTest();
    }
}