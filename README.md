# safety-mirror
Type safe Java Reflection

## Usage
Simply provide a [method reference](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html) to one of the overloaded methods in the Types class. This will provide you with a `java.lang.reflect.Method`.

    
    Method m1 = Types.createMethod(Thread::isAlive)  // Get final method
    Method m2 = Types.createMethod(String::isEmpty); // Get method from final class
    Method m3 = Types.createMethod(BufferedReader::readLine); // Get method that throws checked exception
    Method m4 = Types.<String, Class[]>createMethod(getClass()::getDeclaredMethod); //to get vararg method you must specify parameters in generics
    Method m5 = Types.<String>createMethod(Class::forName); // to get overloaded method you must specify parameters in generics
    Method m6 = Types.createMethod(this::toString); //Works with inherited methods

Notice that you have to provide the method parameters in generics under certain circumstances (When the method is overloaded, or if the method has a varargs parameter).    
    
Note that the project requires Java 8 or above.

## Dependency Management

Since this project is not in any Maven repo you must use [JitPack](https://jitpack.io/) to add the dependency.

## Related projects
See [lambda-factory](https://github.com/Hervian/lambda-factory), which makes your Method invocations as fast as direct method invocation.
