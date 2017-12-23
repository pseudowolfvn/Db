# Realization of simple DataBase Manager.

This project a different possible ways to work with our custom dataBase. For example we can use desktop client with java FX interface, client-server app. etc.

## How it works

All the interactions with database is done via different managers. For example:

```java
public class DatabaseManager {
    public static Database createDatabase(String name) throws IOException {}
    public static List<String> getDatabaseNames() {}
    public static Database getDatabase(String name) {}
    public static boolean isExists(String name) {}
    public static void deleteDatabase(String name) {}
    public static void renameDatabase(String oldName, String newName) throws IOException {}

}
```

So we can use base set of functions for normal work with database: creating, deleting, modifying. The functions are according to [lab's statement](https://magistrs2016.github.io/).k

## Static version of database.

In static (`base`) version of database we realized Column, Row, Table, Header, Database. Each of it user can add and delete. Also possible to modify, save and load objects of external classes(`Table` and `database`). For example, functionality of simple class `Row`: 

```java
public class Row implements Serializable {
    private List<DbType> data;
    public Row() {
        data = new ArrayList<>();
    }

    public void insertData(DbType value) {
        data.add(value);
    }

    public void deleteData(int dataInd) {
        data.remove(dataInd);
    }

    public List<DbType> getData() {
        return data;
    }

    public List<String> getStringView() {
        List<String> view = new ArrayList<>();
        for (int i = 0; i < data.size(); ++i)
            view.add(data.get(i).toString());
        return view;
    }
}
```

### What kind of types we work with
+ Integer
+ Real
+ String
+ Integer Interval
+ Real Interval
+ Images

## Java Remote Method Invocation

`client` and `server` tools use [Java Remote Method Invocation](https://en.wikipedia.org/wiki/Java_remote_method_invocation) to communicate.

### RMI/JRMP

JRMP (Java Remote Method Protocol) is the Java technology-specific protocol for looking up and referencing remote objects. JRMP is a Java-specific, stream-based protocol for Java-to-Java remote calls, requiring both clients and server to use Java objects.

Firstly, start the server. Start the following command from directory, which contains binary files of project. In our case it is: <project directory name>/KNU/IT/Db/out/production/Db;

```bash
$ rmiregistry
...
```
Server starts:

![Screenshot of started server]( https://scontent-frx5-1.xx.fbcdn.net/v/t1.0-9/25550526_535437456819552_2081734138998665948_n.jpg?oh=e7f648d60044706dbba2b2bc4e936344&oe=5AD04A1F " ")

Then you can connect via client by starting jrmpGUI.

Server's console output:

![Screenshot of JRMI terminal](https://scontent-frx5-1.xx.fbcdn.net/v/t1.0-9/25591961_535437423486222_1117305717724966248_n.jpg?oh=680f596a1b8b5f85edaff1ac4c79fddd&oe=5AC8FE59 " ")

GUI:
![JRMI GUI](https://scontent-frx5-1.xx.fbcdn.net/v/t31.0-8/25542744_535437426819555_4489909562278937439_o.jpg?oh=22edb4e062d9c60a537b2ecfa494223b&oe=5AD14424 " ")

### RMI/IIOP

In distributed computing, General Inter-ORB Protocol (GIOP) is the message protocol by which object request brokers (ORBs) communicate in CORBA. Standards associated with the protocol are maintained by the Object Management Group (OMG).
`rest` tool exposes [REST API][rest-api-wiki] for database.

RMI-IIOP (read as "RMI over IIOP") denotes the Java Remote Method Invocation (RMI) interface over the Internet Inter-Orb Protocol (IIOP), which delivers Common Object Request Broker Architecture (CORBA) distributed computing capabilities to the Java platform. It was initially based on two specifications: the Java Language Mapping to OMG IDL, and CORBA/IIOP 2.3.1.

To use it, we need to start server. Use the same method, but for starting ORDB server.

Then we need to start IIOP GUI.

Code of IIOP server:
```java
public class ServerIIOP {
    public static void main(String[] args) {
        try {
            DatabaseManager databaseManager = new DatabaseManagerImpl();

            Context namingContext = new InitialContext();
            namingContext.rebind("databaseManager", databaseManager);

            System.out.println("Server RMI over IIOP started.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
```

### RMI/ORDB + CORBA GUI

For this GUI we use the same server, like for IIOP client. More information about CORBA technology you can see [there](https://uk.wikipedia.org/wiki/CORBA):  

Code of Corba server:

```java
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
```

### JAVA FX

For all previous interfaces we used Java FX interfaces. JavaFX is a software platform for creating and delivering desktop applications, as well as rich internet applications (RIAs) that can run across a wide variety of devices. JavaFX is intended to replace Swing as the standard GUI library for Java SE, but both will be included for the foreseeable future. JavaFX has support for desktop computers and web browsers on Microsoft Windows, Linux, and macOS. You can read more about it [there](https://uk.wikipedia.org/wiki/JavaFX)

The example of FX GUI in our app:

![Screnshot of simple GUI](https://scontent-frx5-1.xx.fbcdn.net/v/t1.0-9/25550500_535437403486224_8694161747756605296_n.jpg?oh=4d894c0b187f4e0671b08a7917aba44e&oe=5AFF30A5 " ")

New window, result of pusing "Add" button:

!![Screnshot of simple GUI](https://scontent-frx5-1.xx.fbcdn.net/v/t1.0-9/25552032_535437370152894_5033974826589984678_n.jpg?oh=861e951a21c4976566495f0356695866&oe=5AFCEEA8 " ")
## WEB-services

### JWS

The Java API for XML Web Services (JAX-WS) is a Java programming language API for creating web services, particularly SOAP services. JAX-WS is one of the Java XML programming APIs. It is part of the Java EE platform. You can read more about JWS [there] (https://en.wikipedia.org/wiki/Java_API_for_XML_Web_Services)

### ASP.NET

ASP.NET is an open-source server-side web application framework designed for web development to produce dynamic web pages. It was developed by Microsoft to allow programmers to build dynamic web sites, web applications and web services.

You can read more about it [there](https://en.wikipedia.org/wiki/ASP.NET)

### Realized functionality(v. 91 and v. 16):
+ Table multiplying
+ Search in data base

Example of searching data:
![Screenshot of searching data](https://scontent-frx5-1.xx.fbcdn.net/v/t31.0-8/25626469_535437323486232_8887947838359229176_o.jpg?oh=111f85396855061b322504a7614761e7&oe=5ABE4DE9 " ")

### ASP.NET Application + Azure

Microsoft Azure (formerly Windows Azure) is a cloud computing service created by Microsoft for building, testing, deploying, and managing applications and services through a global network of Microsoft-managed data centers. It provides software as a service (SaaS), platform as a service and infrastructure as a service and supports many different programming languages, tools and frameworks, including both Microsoft-specific and third-party software and systems. You can read more [trere](https://www.codeproject.com/Tips/1044950/How-to-Publish-ASP-NET-MVC-Web-Application-to-Azur)

Our ASP.NET Application deployed on Azure:
![Screenshot of IT/DB deployed application](https://scontent-frx5-1.xx.fbcdn.net/v/t31.0-8/25587768_535437320152899_5372729201315480526_o.jpg?oh=b1370eb173a0edd345b66aaa6c75e4e1&oe=5AD33955 " ")

## Unit tests using junit

JUnit is a unit testing framework for the Java programming language. JUnit has been important in the development of test-driven development, and is one of a family of unit testing frameworks which is collectively known as xUnit that originated with SUnit. More information [there](https://en.wikipedia.org/wiki/JUnit)

The simple code of test:
```java
    @Test
    public void createTest() {
        String path = "/home/charmer/dev/projects/KNU/IT/Db/out/db/";
        DatabaseManager.setPath(path);
        String name = "junit_test1_db";
        if (DatabaseManager.isExists(name))
            DatabaseManager.deleteDatabase(name);
        Database db = new Database(path, name);
        try {
            db.save();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        assertEquals(DatabaseManager.isExists(name), true);
    }
```
The output of tests work:

!![Screnshot of simple Tests](https://scontent-frx5-1.xx.fbcdn.net/v/t1.0-9/25550312_535437376819560_2602462261513052399_n.jpg?oh=e58844edb8ab8886b11d3321986033bb&oe=5ABB13B7 " ")
