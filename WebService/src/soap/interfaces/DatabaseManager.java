package soap.interfaces;

import Db.Components.rmi.iiop.interfaces.ColumnHeader;
import Db.Components.rmi.iiop.interfaces.DbType;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.rmi.RemoteException;

@WebService
@SOAPBinding
public interface DatabaseManager
{
    @WebMethod
    boolean createDatabase (String dbName);
    @WebMethod
    String[] getDatabaseNames ();
    @WebMethod
    boolean isExists (String dbName);
    @WebMethod
    void deleteDatabase (String dbName);
    @WebMethod
    void renameDatabase (String oldDbName, String newDbName);
    @WebMethod
    boolean addTable (String dbName, String tableName);
    @WebMethod
    String[] getTableNames (String dbName);
    @WebMethod
    void renameTable (String dbName, String oldTableName, String newTableName);
    @WebMethod
    void deleteTable (String dbName, String tableName);
    @WebMethod
    DbType[][] getRows(String dbName, String tableName);
    @WebMethod
    ColumnHeader[] getColumns(String dbName, String tableName);
    @WebMethod
    DbType[] insertRow(String dbName, String tableName, String[] data);
    @WebMethod
    boolean addColumn(String dbName, String tableName, String columnName, String columnType);
    @WebMethod
    ColumnHeader getColumn(String dbName, String tableName, String columnName);
    @WebMethod
    void setRow(String dbName, String tableName, String value, String columnHeader, int rowIndex);
    @WebMethod
    boolean save();
}