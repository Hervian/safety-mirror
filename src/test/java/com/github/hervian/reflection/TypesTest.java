package com.github.hervian.reflection;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.util.HashMap;

import org.junit.Test;

public class TypesTest {

    @Test
    public void testTypes() {
        assertNotNull(Types.createMethod(String::isEmpty));            // final class
        assertNotNull(Types.createMethod(BufferedReader::readLine));   //throws checked exception
        assertNotNull(Types.createMethod(Thread::isAlive));            //final method
        assertNotNull(Types.<String, Class[]> createMethod(getClass()::getDeclaredMethod)); // to get vararg method you must specify parameters in generics
        assertNotNull(Types.<String>createMethod(Class::forName)); // to get overlaoded method you must specify parameters in generics
        assertNotNull(Types.createMethod(this::toString)); //Works with inherited methods
        
        assertNotNull(Types.createMethod(Integer::compareTo));
        assertNotNull(Types.createMethod(TypesTest::testTypes));
        assertNotNull(Types.createMethod(new HashMap()::entrySet));
        assertNotNull(Types.createMethod(getClass()::getDeclaredMethods));
    }
    
    

}
