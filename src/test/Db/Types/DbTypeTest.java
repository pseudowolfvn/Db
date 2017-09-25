package Db.Types;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DbTypeTest {
    public static void main(String[] args) {
        ArrayList<DbType> headers = new ArrayList<>();
        headers.add(new Int(42));
        headers.add(new Char('a'));
        System.out.println(headers.get(1).get());
    }
}