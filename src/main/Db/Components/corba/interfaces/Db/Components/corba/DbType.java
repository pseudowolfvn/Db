package Db.Components.corba;


/**
* Db/Components/corba/DbType.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./DatabaseManager.idl
* 1 ноября 2017 г. 9:39:50 EET
*/

public final class DbType implements org.omg.CORBA.portable.IDLEntity
{
  public String type = null;
  public String value = null;

  public DbType ()
  {
  } // ctor

  public DbType (String _type, String _value)
  {
    type = _type;
    value = _value;
  } // ctor

} // class DbType
