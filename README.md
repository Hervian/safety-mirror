# safety-mirror
Type safe Java Reflection

## Usage
Simply provide a [method reference](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html) to one of the overloaded methods in the Types class. This will provide you with a `java.lang.reflect.Method`.

    
    public void testTypes() {
        Method method1 = Types.getDeclaredMethod(Thread::isAlive)  // Get final method
        Method method2 = Types.getDeclaredMethod(String::isEmpty); // Get method from final class
        Method method3 = Types.getDeclaredMethod(BufferedReader::readLine); // Get method that throws checked exception
    }
    
    
Note that the project requires Java 8 or above.
