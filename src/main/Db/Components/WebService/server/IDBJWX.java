package Db.Components.WebService.server;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import Db.Components.Database;


@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface IDBJWX {

    @WebMethod
    public boolean addDatabase(Database db);

    @WebMethod
    public boolean deleteDatabase(String name);

    @WebMethod
    public Database getDatabase(String name);
}
