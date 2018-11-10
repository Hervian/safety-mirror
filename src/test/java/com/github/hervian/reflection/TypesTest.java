package com.github.hervian.reflection;

import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;

import org.junit.Test;

public class TypesTest {

    @Test
    public void testTypes() {
        assertNotNull(Types.getDeclaredMethod(Integer::compareTo));
        assertNotNull(Types.getDeclaredMethod(TypesTest::testTypes));
        assertNotNull(Types.getDeclaredMethod(String::isEmpty));
        assertNotNull(Types.getDeclaredMethod(BufferedReader::readLine));
//        assertNotNull(Types.getDeclaredMethod(String::));
//        assertNotNull(Types.getDeclaredMethod(Class::getDeclaredMethod));
//        assertNotNull(Types.getDeclaredMethod(Class::getDeclaredMethods));
//        assertNotNull(Types.getDeclaredMethod(Class::forName));
    }
    
}
