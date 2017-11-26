
package soap.server;

import Db.Components.Database;
import Db.Components.Table;
import Db.Components.rmi.iiop.interfaces.ColumnHeader;
import Db.Components.rmi.iiop.interfaces.DbType;
import Db.Exceptions.IllegalConversionException;
import Db.Exceptions.UnsupportedTypeException;
import soap.interfaces.DatabaseManager;

import javax.jws.WebService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebService(endpointInterface="soap.interfaces.DatabaseManager"
, serviceName = "DatabaseManager")
public class DatabaseManagerImpl implements DatabaseManager {
    public DatabaseManagerImpl() {
        Db.Components.DatabaseManager.setPath("/var/lib/tomcat8/webapps/WebService_war/resources");
    }

    @Override
    public boolean createDatabase(String dbName) {
        try {
            Db.Components.DatabaseManager.createDatabase(dbName);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public String[] getDatabaseNames() {
        return Db.Components.DatabaseManager.getDatabaseNames().toArray(new String[0]);
    }

    @Override
    public boolean isExists(String dbName) {
        return Db.Components.DatabaseManager.isExists(dbName);
    }

    @Override
    public void deleteDatabase(String dbName) {
        Db.Components.DatabaseManager.deleteDatabase(dbName);
    }

    @Override
    public void renameDatabase(String oldDbName, String newDbName) {
        try {
            Db.Components.DatabaseManager.renameDatabase(oldDbName, newDbName);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean addTable(String dbName, String tableName) {
        Database db = Db.Components.DatabaseManager.getDatabase(dbName);
        return db.addTable(tableName);
    }

    @Override
    public String[] getTableNames(String dbName) {
        Database db = Db.Components.DatabaseManager.getDatabase(dbName);
        return db.getTableNames().toArray(new String[0]);
    }

    @Override
    public void renameTable(String dbName, String oldTableName, String newTableName) {
        Database db = Db.Components.DatabaseManager.getDatabase(dbName);
        db.renameTable(oldTableName, newTableName);
    }

    @Override
    public void deleteTable(String dbName, String tableName) {
        Database db = Db.Components.DatabaseManager.getDatabase(dbName);
        db.deleteTable(tableName);
    }

    private DbType[] RowToArray(Db.Components.Row row) {
        List<Db.Types.DbType> data = row.getData();
        DbType[] corbaRow = new DbType[data.size()];
        for (int i = 0; i < data.size(); ++i)
            corbaRow[i] = new DbType(
                    data.get(i).getClass().getName(), data.get(i).toString());
        return corbaRow;
    }

    @Override
    public DbType[][] getRows(String dbName, String tableName) {
        Database db = Db.Components.DatabaseManager.getDatabase(dbName);
        Table table = db.getTable(tableName);
        List<Db.Components.Row> rows = table.getRows();
        DbType[][] rowsArray = new DbType[rows.size()][table.getColumns().size()];
        for (int i = 0; i < rows.size(); ++i) {
            rowsArray[i] = RowToArray(rows.get(i));
        }
        return rowsArray;
    }

    @Override
    public ColumnHeader[] getColumns(String dbName, String tableName) {
        Database db = Db.Components.DatabaseManager.getDatabase(dbName);
        Table table = db.getTable(tableName);
        List<Db.Components.ColumnHeader> columns = table.getColumns();
        List<ColumnHeader> corbaColumns = new ArrayList<>();
        for (int i = 0; i < columns.size(); ++i) {
            String type = columns.get(i).getType();
            String name = columns.get(i).getName();
            String title = columns.get(i).toString();
            corbaColumns.add(new ColumnHeader(type, name, title));
        }
        return corbaColumns.toArray(new ColumnHeader[0]);
    }

    @Override
    public DbType[] insertRow(String dbName, String tableName, String[] data) {
        Database db = Db.Components.DatabaseManager.getDatabase(dbName);
        Table table = db.getTable(tableName);
        Db.Components.Row row = null;
        try {
            row = table.insertRow(data);
        } catch (IllegalConversionException | UnsupportedTypeException e) {
            System.out.println(e.getMessage());
        }
        return RowToArray(row);
    }

    @Override
    public boolean addColumn(String dbName, String tableName, String columnName, String columnType) {
        Database db = Db.Components.DatabaseManager.getDatabase(dbName);
        Table table = db.getTable(tableName);
        return table.addColumn(columnName, columnType);
    }

    @Override
    public ColumnHeader getColumn(String dbName, String tableName, String columnName) {
        Database db = Db.Components.DatabaseManager.getDatabase(dbName);
        Table table = db.getTable(tableName);
        Db.Components.ColumnHeader column = table.getColumn(columnName);
        ColumnHeader corbaColumn = new ColumnHeader(
                column.getType(), column.getName(), column.toString());
        return corbaColumn;
    }

    @Override
    public void setRow(String dbName, String tableName, String value, String columnHeader, int rowIndex) {
        Database db = Db.Components.DatabaseManager.getDatabase(dbName);
        Table table = db.getTable(tableName);
        List<Db.Components.ColumnHeader> columns = table.getColumns();
        int columnIndex = -1;
        for (int i = 0; i < columns.size(); ++i) {
            if (columns.get(i).getName().equals(columnHeader))
                columnIndex = i;
        }
        table.getRows().get(rowIndex).getData().get(columnIndex).fromString(value);
    }

    @Override
    public boolean save() {
        Db.Components.DatabaseManager.endSession();
        return true;
    }
}
