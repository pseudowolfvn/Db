package Db.Components.corba.interfaces;

import Db.Components.Table;
import Db.Components.corba.ColumnHeader;
import Db.Components.corba.DbType;
import Db.Components.corba.TablePOA;
import Db.Exceptions.IllegalConversionException;
import Db.Exceptions.UnsupportedTypeException;

import java.util.ArrayList;
import java.util.List;

public class TableImpl extends TablePOA {
    Table table;

    TableImpl(Table _table) {
        this.table = _table;
    }

    private DbType[] RowToCorba(Db.Components.Row row) {
        List<Db.Types.DbType> data = row.getData();
        DbType[] corbaRow = new DbType[data.size()];
        for (int i = 0; i < data.size(); ++i)
            corbaRow[i] = new DbType(
                    data.get(i).getClass().getName(), data.get(i).toString());
        return corbaRow;
    }

    @Override
    public DbType[][] getRows() {
        List<Db.Components.Row> rows = table.getRows();
        DbType[][] corbaRows = new DbType[rows.size()][table.getColumns().size()];
        for (int i = 0; i < rows.size(); ++i) {
            corbaRows[i] = RowToCorba(rows.get(i));
        }
        return corbaRows;
    }

    @Override
    public ColumnHeader[] getColumns() {
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
    public String getName() {
        return table.getName();
    }

    @Override
    public void setName(String name) {
        table.setName(name);
    }

    @Override
    public DbType[] insertRow(String[] data) {
        Db.Components.Row row = null;
        try {
            row = table.insertRow(data);
        } catch (IllegalConversionException | UnsupportedTypeException e) {
            System.out.println(e.getMessage());
        }
        return RowToCorba(row);
    }

    @Override
    public boolean addColumn(String name, String type) {
        return table.addColumn(name, type);
    }

    @Override
    public ColumnHeader getColumn(String name) {
        Db.Components.ColumnHeader column = table.getColumn(name);
        ColumnHeader corbaColumn = new ColumnHeader(
                column.getType(), column.getName(), column.toString());
        return corbaColumn;
    }

    @Override
    public void setData(String value, String columnHeader, int rowIndex) {
        List<Db.Components.ColumnHeader> columns = table.getColumns();
        int columnIndex = -1;
        for (int i = 0; i < columns.size(); ++i) {
            if (columns.get(i).getName().equals(columnHeader))
                columnIndex = i;
        }
        table.getRows().get(rowIndex).getData().get(columnIndex).fromString(value);
    }
}
