package Db.Types;

import java.util.ArrayList;

class DbTypeTest {
    public static void factoryTest() {
        try {
            Char ch = (Char) DbTypesFactory.dbTypeFromString("Char", "a");
            System.out.println(ch);

            Int i = (Int) DbTypesFactory.dbTypeFromString("Int", "42");
            System.out.println(i);

            IntIntv iIntv = (IntIntv) DbTypesFactory.dbTypeFromString("IntIntv", "0..10");
            System.out.println(iIntv);

            RealIntv rIntv = (RealIntv) DbTypesFactory.dbTypeFromString("RealIntv", "0.42..3.1415");
            System.out.println(rIntv);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void typesTest() {
        ArrayList<DbType> headers = new ArrayList<>();
        headers.add(new Int(42));
        headers.add(new Char('a'));
        System.out.println(headers.get(1).get());
    }

    public static void main(String[] args) {
        factoryTest();
    }
}