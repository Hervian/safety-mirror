package com.github.hervian.reflection;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

public class TypesTest {

    @Test
    public void testTypes() {
        assertNotNull(Types.getDeclaredMethod(Integer::compareTo));
        assertNotNull(Types.getDeclaredMethod(TypesTest::testTypes));
        assertNotNull(Types.getDeclaredMethod(String::isEmpty));            // final class
        assertNotNull(Types.getDeclaredMethod(BufferedReader::readLine));   //throws checked exception
        assertNotNull(Types.getDeclaredMethod(Thread::isAlive));            //final method
        assertNotNull(Types.<String, Class[]> getDeclaredMethod(getClass()::getDeclaredMethod)); // to get vararg method you must specify parameters in generics
        assertNotNull(Types.<String>getDeclaredMethod(Class::forName)); // to get overlaoded method you must specify parameters in generics
        assertNotNull(Types.<Set>getDeclaredMethod(new HashMap()::entrySet));
        assertNotNull(Types.<Method[]>getDeclaredMethod(getClass()::getDeclaredMethods));
    }

}
