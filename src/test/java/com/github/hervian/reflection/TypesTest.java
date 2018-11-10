package com.github.hervian.reflection;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.util.HashMap;

import org.junit.Test;

public class TypesTest {

    @Test
    public void testTypes() {
        assertNotNull(Types.getDeclaredMethod(Integer::compareTo));
        assertNotNull(Types.getDeclaredMethod(TypesTest::testTypes));
        assertNotNull(Types.getDeclaredMethod(String::isEmpty));            // final class
        assertNotNull(Types.getDeclaredMethod(BufferedReader::readLine));   //throws checked exception
        assertNotNull(Types.getDeclaredMethod(Thread::isAlive));            //final method
//        assertNotNull(Types.getDeclaredMethod(String::));
//        assertNotNull(Types.getDeclaredMethod(Class::getDeclaredMethod));
//        assertNotNull(Types.getDeclaredMethod(Class::getDeclaredMethods));
//        assertNotNull(Types.getDeclaredMethod(Class::forName));
    }
    
}
