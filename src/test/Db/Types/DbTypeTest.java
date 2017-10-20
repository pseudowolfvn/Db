package Db.Types;

import Db.Exceptions.IllegalConversionException;
import Db.Exceptions.UnsupportedTypeException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DbTypeTest {
    @Test
    public void factoryTest() {
        try {
            Char ch = (Char) DbTypesFactory.dbTypeFromString("Char", "a");
            assertEquals((char)ch.get(), 'a');

            Int i = (Int) DbTypesFactory.dbTypeFromString("Int", "42");
            assertEquals((int)i.get(), 42);

            IntIntv iIntv = (IntIntv) DbTypesFactory.dbTypeFromString("IntIntv", "0..10");
            assertEquals((int)iIntv.get().getLeft(), 0);
            assertEquals((int)iIntv.get().getRight(), 10);

            RealIntv rIntv = (RealIntv) DbTypesFactory.dbTypeFromString("RealIntv", "0.42..3.1415");
            assertEquals((double)rIntv.get().getLeft(), 0.42);
            assertEquals((double)rIntv.get().getRight(), 3.1415);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void typesSafety() {
        assertThrows(IllegalConversionException.class, ()->{
            Int i = (Int) DbTypesFactory.dbTypeFromString("Int", "bad_int");
        });
        assertThrows(UnsupportedTypeException.class, ()->{
            Object obj = (Object) DbTypesFactory.dbTypeFromString("Double", "0.42");
        });
    }
}