# safety-mirror
Type safe Java Reflection

## Usage
Simply provide a [method reference](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html) to one of the overloaded methods in the Types class. This will provide you with a `java.lang.reflect.Method`.

    
    Method m1 = Types.getDeclaredMethod(Thread::isAlive)  // Get final method
    Method m2 = Types.getDeclaredMethod(String::isEmpty); // Get method from final class
    Method m3 = Types.getDeclaredMethod(BufferedReader::readLine); // Get method that throws checked exception
    Method m4 = Types.<String, Class[]> getDeclaredMethod(getClass()::getDeclaredMethod); //to get vararg method you must specify parameters in generics
    Method m5 = Types.<String>getDeclaredMethod(Class::forName); // to get overloaded method you must specify parameters in generics
    Method m6 = Types.<Set>getDeclaredMethod(new HashMap()::entrySet);
    Method m7 = Types.<Method[]>getDeclaredMethod(getClass()::getDeclaredMethods);

Notice that you have to provide the method parameters in generics under certain circumstances (When the method is overloaded, or if the method has a varargs parameter).    
    
Note that the project requires Java 8 or above.
