package Db.Components;

import Db.Exceptions.IllegalConversionException;
import Db.Exceptions.UnsupportedTypeException;
import Db.Types.DbType;
import Db.Types.DbTypesFactory;

import java.io.*;
import java.util.*;

public class Table implements Serializable{
    private String name;

    private Set<String> columnNames;

    private List<ColumnHeader> columns;
    private List<Row> rows;

    public Table(String name) {
        this.name = name;
        columnNames = new HashSet<>();
        columns = new ArrayList<>();
        rows = new ArrayList<>();
    }

    public List<Row> getRows() {
        return rows;
    }

    public List<ColumnHeader> getColumns() { return columns; }

    public List<List<DbType>> getData() {
        List<List<DbType>> data = new ArrayList<>();
        for (Row row: rows)
            data.add(row.getData());
        return data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public Row insertRow(String ... data) throws IllegalConversionException, UnsupportedTypeException {
        if (data.length != columns.size())
            return null;
        Row row = new Row();
        for (int i = 0; i < data.length; ++i) {
            DbType value = DbTypesFactory
                        .dbTypeFromString(columns.get(i).getType(), data[i]);
            row.insertData(value);
        }
        rows.add(row);
        return row;
    }

    public boolean addColumn(String name, String type) {
        if (columnNames.contains(name))
            return false;
        columns.add(new ColumnHeader(name, type));
        columnNames.add(name);
        for (int i = 0; i < rows.size(); ++i) {
            try {
                rows.get(i).insertData(DbTypesFactory
                        .dbTypeFromString(type, " "));
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        return true;
    }

    public void deleteRow(int rowInd) {
        rows.remove(rowInd);
    }

    public void deleteColumn(int colInd) {
        columnNames.remove(columns.get(colInd).getName());
        for (int i = 0; i < rows.size(); ++i) {
            rows.get(i).deleteData(colInd);
        }
        columns.remove(colInd);
    }

    public void deleteColumn(String name) {
        for (int i = 0; i < columns.size(); ++i)
            if (Objects.equals(columns.get(i).getName(), name))
            {
                this.deleteColumn(i);
                return;
            }
    }

    public ColumnHeader getColumn(String name) {
        for (int i = 0; i < columns.size(); ++i)
            if (Objects.equals(columns.get(i).getName(), name))
                return columns.get(i);
        return null;
    }

    public List<List<String>> getStringView() {
        List<List<String>> view = new LinkedList<>();
        view.add(new ArrayList<>());
        for (int i = 0; i < columns.size(); ++i) {
            view.get(0).add(columns.get(i).toString());
        }
        for (int i = 0; i < rows.size(); ++i) {
            view.add(rows.get(i).getStringView());
        }
        return view;
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

    public static Table load(String path) throws IOException, ClassNotFoundException {
        FileInputStream fileIn =
                new FileInputStream(path);
        ObjectInputStream objIn =
                new ObjectInputStream(fileIn);
        Table table = (Table) objIn.readObject();
        objIn.close();
        fileIn.close();
        return table;
    }

    public void printToStdout() {
        List<List<String>> view = this.getStringView();
        for (List<String> row : view) {
            for (String data : row) {
                System.out.print(data + " ");
            }
            System.out.println();
        }
    }
}
