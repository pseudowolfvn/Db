package Db.Components.corba;


/**
* Db/Components/corba/ListRowHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./DatabaseManager.idl
* 1 ноября 2017 г. 9:39:50 EET
*/

public final class ListRowHolder implements org.omg.CORBA.portable.Streamable
{
  public Db.Components.corba.DbType value[][] = null;

  public ListRowHolder ()
  {
  }

  public ListRowHolder (Db.Components.corba.DbType[][] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Db.Components.corba.ListRowHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Db.Components.corba.ListRowHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Db.Components.corba.ListRowHelper.type ();
  }

}
