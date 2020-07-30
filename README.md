# safety-mirror
Fun with Delegates, Events and type safe Java Reflection

## Usage

### Fun and friends: No more functional interfaces
With the Fun interface and its sub-interfaces you save yourself the hassle of creating functional interfaces 
for your fields and method signatures.  
The functions return value and its parameters, if any, are defined via generic types.  

It's best explained with a few examples:  

 1. `Fun.With0Params<String> myFunctionField = "   hello world   "::trim;`
 1. `void equals(Fun.With2Params<Boolean, Object, Object> myEqualsFunction){myEqualsFunction.invoke(obj1, obj2)}`
 1. `Fun.With1ParamAndVoid<String> print = System.out::println;` //to match lower case void return value you must use the *AndVoid subclass (since Void is NOT a boxed void)

The Fun interface has a number of sub-interfaces called `With0Params`, `With1Param`, `With2Params`, ..., `With9Params`.
Further, if you need to match methods whose return type is lower case void you must use a speciel *AndVoid variant, i.e. `With0ParamsAndVoid`, `With1ParamAndVoid` etc.  

Each of the With* classes are generic. You must specify A) the return type and B) the type of the parameters.  
As seen by the examples above Fun.With2Params needs 3 generic types: The first one is the return type, the second and
third ones are the method parameters.

Notice that currently Java Generics does not accept primitive types, but due to autoboxing you can match
methods with primitive arguments by specifying their boxed counterpart.
Notice that Void is not a boxed void - for this reason you must use a speciel *Void variant of above sub-interfaces
to match methods whose return type is void.  


### Delegates in Java!
The Delegate class's API is exposed as a number of static nested classes, one for each number of parameters.
Their behavior mimic that of C#'s Multicast Delegates, in that you can add, remove and invoke.

Examples:

 1. `Delegate.With0Params<String> myDelegate = new Delegate.With0Params<>();`
    * `myDelegate.add(someClass::someMethodThatReturnsAStringAndTakesNoParams`
 1. ``
 
### Event

Examples:

 1. ``
 

### Type safe reflection
Simply provide a [method reference](https://docs.oracle.com/javase/tutorial/java/javaOO/methodreferences.html) 
to one of the overloaded toMethod methods in the Fun class. This will provide you with a `java.lang.reflect.Method`.

    
    Method m1 = Fun.toMethod(Thread::isAlive)  // Get final method
    Method m2 = Fun.toMethod(String::isEmpty); // Get method from final class
    Method m3 = Fun.toMethod(BufferedReader::readLine); // Get method that throws checked exception
    Method m4 = Fun.<String, Class[]>toMethod(getClass()::getDeclaredMethod); //to get vararg method you must specify parameters in generics
    Method m5 = Fun.<String>toMethod(Class::forName); // to get overloaded method you must specify parameters in generics
    Method m6 = Fun.toMethod(this::toString); //Works with inherited methods

Notice that you have to provide the method parameters in generics under certain circumstances 
(When the method is overloaded, or if the method has a varargs parameter).    
    
Note that the library requires Java 8 or above.

## Dependency Management

Since this project is not in any Maven repo you must use [JitPack](https://jitpack.io/) to add the dependency.

## Related projects
See [lambda-factory](https://github.com/Hervian/lambda-factory), which makes your Method invocations as fast as direct method invocation.
