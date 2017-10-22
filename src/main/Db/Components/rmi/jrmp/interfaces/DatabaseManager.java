package Db.Components.rmi.jrmp.interfaces;

import Db.Components.Database;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface DatabaseManager extends Remote {
    public Database createDatabase(String name) throws RemoteException;
    public List<String> getDatabaseNames() throws RemoteException;
    public Database getDatabase(String name) throws RemoteException;
    public boolean isExists(String name) throws RemoteException;
    public void deleteDatabase(String name) throws RemoteException;
    public void renameDatabase(String oldName, String newName) throws RemoteException;
}
