package com.github.hervian.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.junit.Test;

public class TypesTest {

    @Test
    public void testCreateMethod() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        assertNotNull(Types.createMethod(String::isEmpty));            // final class
        assertNotNull(Types.createMethod(BufferedReader::readLine));   //throws checked exception
        assertNotNull(Types.createMethod(Thread::isAlive));            //final method
        assertNotNull(Types.<String, Class<?>[]>createMethod(getClass()::getDeclaredMethod)); // to get vararg method you must specify parameters in generics
        assertNotNull(Types.<String>createMethod(Class::forName)); 	   // to get overlaoded method you must specify parameters in generics
        assertNotNull(Types.createMethod(this::toString));             //Works with inherited methods
             
        assertNotNull(Types.createMethod(TypesTest::testCreateMethod));
        assertNotNull(Types.createMethod(new HashMap<>()::entrySet));
        assertNotNull(Types.createMethod(getClass()::getDeclaredMethods));
    }
    
    @Test
    public void testGetName() throws Throwable {
        assertEquals("isEmpty", Types.getName(String::isEmpty));
        assertEquals("readLine", Types.getName(BufferedReader::readLine));
        assertEquals("isAlive", Types.getName(Thread::isAlive));
        assertEquals("getDeclaredMethod", Types.<String, Class[]>getName(getClass()::getDeclaredMethod));  //lambda$16 -> Holger, on SO: "The limitations are that it will print not very useful method references for lambda expressions (references to the synthetic method containing the lambda code)" https://stackoverflow.com/a/21879031/6095334
        assertEquals("forName", Types.<String>getName(Class::forName));
        assertEquals("toString", Types.getName(this::toString));
    }
    
    

}
