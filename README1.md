# Фрагментарна реалізація систем управління табличними базами даних

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

So we can use base set of functions for normal work with database: creating, deleting, modifying. The functions are according to [lab's statement](https://magistrs2016.github.io/).

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
![Screenshot of started server](https://drive.google.com/file/d/1PnubuL7HCh7fnckF3Vwhbjlb_GyM0xty/view?usp=sharing " ")

Then you can connect via client by starting jrmpGUI.

Server's console output:
![Screenshot of JRMI terminal]()

GUI:
![JRMI GUI]()

### RMI/IIOP

In distributed computing, General Inter-ORB Protocol (GIOP) is the message protocol by which object request brokers (ORBs) communicate in CORBA. Standards associated with the protocol are maintained by the Object Management Group (OMG).
`rest` tool exposes [REST API][rest-api-wiki] for database.

RMI-IIOP (read as "RMI over IIOP") denotes the Java Remote Method Invocation (RMI) interface over the Internet Inter-Orb Protocol (IIOP), which delivers Common Object Request Broker Architecture (CORBA) distributed computing capabilities to the Java platform. It was initially based on two specifications: the Java Language Mapping to OMG IDL, and CORBA/IIOP 2.3.1.

To use it, we need to start server. Use the same method, but for starting ORDB server.

Then we need to start IIOP GUI.

### RMI/ORDB + CORBA GUI

For this GUI we use the same server, like for IIOP client. More information about CORBA technology you can see [there](https://uk.wikipedia.org/wiki/CORBA):  

First we need to start the server:

Then we start GUI:

### JAVA FX

For all previous interfaces we used Java FX interfaces. JavaFX is a software platform for creating and delivering desktop applications, as well as rich internet applications (RIAs) that can run across a wide variety of devices. JavaFX is intended to replace Swing as the standard GUI library for Java SE, but both will be included for the foreseeable future. JavaFX has support for desktop computers and web browsers on Microsoft Windows, Linux, and macOS. You can read more about it [there](https://uk.wikipedia.org/wiki/JavaFX)

## WEB-services

### JWS

The Java API for XML Web Services (JAX-WS) is a Java programming language API for creating web services, particularly SOAP services. JAX-WS is one of the Java XML programming APIs. It is part of the Java EE platform. You can read more about JWS [there] (https://en.wikipedia.org/wiki/Java_API_for_XML_Web_Services)

Starting the server:

### ASP.NET

ASP.NET is an open-source server-side web application framework designed for web development to produce dynamic web pages. It was developed by Microsoft to allow programmers to build dynamic web sites, web applications and web services.

You can read more about it [there](https://en.wikipedia.org/wiki/ASP.NET)