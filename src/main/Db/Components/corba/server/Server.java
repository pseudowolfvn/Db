package Db.Components.corba.server;

import Db.Components.corba.DatabaseManager;
import Db.Components.corba.DatabaseManagerHelper;
import Db.Components.corba.interfaces.DatabaseManagerImpl;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class Server {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            org.omg.CORBA.Object objRef =
                    orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            String name = "databaseManager_CORBA";
            NameComponent path[] = ncRef.to_name(name);

            DatabaseManagerImpl databaseManager = new DatabaseManagerImpl(rootpoa, ncRef);
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(databaseManager);
            DatabaseManager href = DatabaseManagerHelper.narrow(ref);

            ncRef.rebind(path, href);
            System.out.println("Server started.");
            orb.run();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
