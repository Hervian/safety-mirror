package com.github.hervian.reflection;

import org.junit.Test;

import java.io.BufferedReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FunToMethodTest {

    @Test
    public void testCreateMethod() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        assertNotNull(Fun.toMethod(String::isEmpty));            // final class
        assertNotNull(Fun.toMethod(BufferedReader::readLine));   //throws checked exception
        assertNotNull(Fun.toMethod(Thread::isAlive));            //final method

        /**
         * Below assert will fail if the MethodRef.With0Params.Void is NOT generic, hence the dummy value. Apparently the compiler gets confused and cannot choose the proper one (which is MethodRef.with2Params.Void). Very strange...
         */
        assertNotNull(Fun.<String, Class<?>[]>toMethod(getClass()::getDeclaredMethod)); // to get vararg method you must specify parameters in generics
        assertNotNull(Fun.<String>toMethod(Class::forName)); 	   // to get overlaoded method you must specify parameters in generics
        assertNotNull(Fun.toMethod(this::toString));             //Works with inherited methods
             
        assertNotNull(Fun.toMethod(FunToMethodTest::testCreateMethod));
        assertNotNull(Fun.toMethod(new HashMap<>()::entrySet));
        assertNotNull(Fun.toMethod(getClass()::getDeclaredMethods));

        assertNotNull(Fun.toMethod(NestedClass::noOp));
        Fun.toMethod(NestedClass::noOp).invoke(new NestedClass());
        assertNotNull(Fun.toMethod(NestedClass::noOpStatic));
        Fun.toMethod(NestedClass::noOpStatic).invoke(null);

        assertEquals(17d, Fun.toMethod(NestedClass::get).invoke(new NestedClass()));
        assertEquals(17d, Fun.toMethod(NestedClass::getStatic).invoke(null));

        Method print = Fun.toMethod(NestedClass::print);
        assertNotNull(print);
        print.invoke(new NestedClass(), 1234);
        Method printStatic = Fun.toMethod(NestedClass::printStatic);
        printStatic.invoke(null, 1234);

        assertEquals("str_suffix", Fun.toMethod(NestedClass::modify).invoke(new NestedClass(), "str"));
        assertEquals("str_suffix", Fun.toMethod(NestedClass::modifyStatic).invoke(null, "str"));

        assertEquals("str1str2", Fun.toMethod(NestedClass::concatenate).invoke(new NestedClass(), "str1", "str2"));
        assertEquals("str1str2", Fun.toMethod(NestedClass::concatenateStatic).invoke(null, "str1", "str2"));
    }

    @Test
    public void testReturnArray(){
        String[] stringArray = new String[1];
        stringArray[0] = "hello world";
        Fun.With1Param<String[], String[]> fun = this::returnStringArray;
        String[] res = fun.invokeWithSneakyThrows(stringArray);
        assertEquals("hello world", res[0]);
    }

    public String[] returnStringArray(String[] stringArray){
        return stringArray;
    }
    
    @Test
    public void testGetName() {
        assertEquals("concatenate", Fun.getName(NestedClass::concatenate));
        assertEquals("isEmpty", Fun.getName(String::isEmpty));
        assertEquals("readLine", Fun.getName(BufferedReader::readLine));
        assertEquals("isAlive", Fun.getName(Thread::isAlive));
        assertEquals("getDeclaredMethod", Fun.<String, Class[]>getName(HashMap.class::getDeclaredMethod));
        assertEquals("getDeclaredMethod", Fun.<String, Class[]>getName(getClass()::getDeclaredMethod));
        assertEquals("getDeclaredField", Fun.getName(getClass()::getDeclaredField));
        assertEquals("forName", Fun.<String>getName(Class::forName));
        assertEquals("toString", Fun.getName(this::toString));
    }
    
    public static class NestedClass {
        public void noOp() {
            System.out.println("noOp");
        }
        public static void noOpStatic() {
            System.out.println("noOp");
        }

        public Double get() {
            return 17d;
        }
        public static Double getStatic() {
            return 17d;
        }

        public void print(int i) {
            System.out.println(i);
        }
        public static void printStatic(int i) {
            System.out.println(i);
        }

        public String modify(String str1) {
            return str1 + "_suffix";
        }
        public static String modifyStatic(String str1) {
            return str1 + "_suffix";
        }

        public String concatenate(String str1, String str2) {
            return str1 + str2;
        }
        public static String concatenateStatic(String str1, String str2) {
            return str1 + str2;
        }
    }

    //public Delegate2<String, String> myDelegate = Delegates.create();
    public Fun.With2Params<String, String, String> defaultDelegate = NestedClass::concatenateStatic;

    private String caller() throws InvocationTargetException, IllegalAccessException {
        return add(NestedClass::concatenateStatic);
    }

    private String add(Fun.With2Params<String, String, String> delegate) throws InvocationTargetException, IllegalAccessException {
        String str1 = null;
        String str2 = null;
        return (String) delegate.toMethod().invoke(null, str1, str2);
    }
    

}
