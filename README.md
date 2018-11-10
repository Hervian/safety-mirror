# safety-mirror
Type safe Java Reflection

## Usage
Simply provide a [method reference](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html) to one of the overloaded methods in the Types class. This will provide you with a `java.lang.reflect.Method`.

    ```java
    public void testTypes() {
        Method method1 = Types.getDeclaredMethod(Thread::isAlive)
        Method method2 = Types.getDeclaredMethod(String::isEmpty); //Retrieving method from final class
        Method method3 = Types.getDeclaredMethod(BufferedReader::readLine); //Retrieving method that throws a checked exception
    }
    ```
    
Note that the project requires Java 8 or above.
