package Db.Components.corba;


/**
* Db/Components/corba/ListColumnHeaderHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./DatabaseManager.idl
* 1 ноября 2017 г. 9:39:50 EET
*/

public final class ListColumnHeaderHolder implements org.omg.CORBA.portable.Streamable
{
  public Db.Components.corba.ColumnHeader value[] = null;

  public ListColumnHeaderHolder ()
  {
  }

  public ListColumnHeaderHolder (Db.Components.corba.ColumnHeader[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Db.Components.corba.ListColumnHeaderHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Db.Components.corba.ListColumnHeaderHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Db.Components.corba.ListColumnHeaderHelper.type ();
  }

}
