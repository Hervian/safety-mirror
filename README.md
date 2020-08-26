# safety-mirror
Fun with Delegates, Events and type safe Java Reflection

## Usage

### Fun and friends: No more functional interfaces
With the Fun interface and its sub-interfaces you save yourself the hassle of creating functional interfaces 
for your fields and method signatures.  
The functions return value and its parameters, if any, are defined via generic types.  
That is:
* With functional interfaces you would define the functional interface and use the functional interface as a type in your method signature or field.
* With the Fun interfaces the type is already created and ready for use.

It's best explained with a few examples:  

 1. `Fun.With0Params<String> myFunctionField = "   hello world   "::trim;`
 1. `void equals(Fun.With2Params<Boolean, Object, Object> myEqualsFunction){myEqualsFunction.invoke(obj1, obj2)}`
 1. `Fun.With1ParamAndVoid<String> print = System.out::println;` //to match lower case void return value you must use the *AndVoid subclass (since Void is NOT a boxed void)

The Fun interface has a number of sub-interfaces called `With0Params`, `With1Param`, `With2Params`, ..., `With9Params`.
Further, if you need to match methods whose return type is lower case void you must use a speciel *AndVoid variant, i.e. `With0ParamsAndVoid`, `With1ParamAndVoid` etc.  

Each of the With* classes are generic. You must specify A) the return type and B) the type of the parameters.  
As seen by the examples above Fun.With2Params needs 3 generic types: The first one is the return type, the second and
third ones are the method parameters.

**Notice** that currently Java Generics does not accept primitive types, but due to autoboxing you can match
methods with primitive arguments by specifying their boxed counterpart. (This may change once [Project Valhalla](https://en.wikipedia.org/wiki/Project_Valhalla_(Java_language)) is completed)  

**Notice** that Void is not a boxed void - for this reason you must use a speciel *Void variant of above sub-interfaces
to match methods whose return type is void.  


### Delegates in Java!
The Delegate class's API is exposed as a number of static nested classes, one for each number of parameters.
Their behavior mimic that of C#'s Multicast Delegates, in that you can add, remove and invoke.

*Example:*
 ```
     public static void main(String[] args){
        Delegate.With1Param<String, String> greetingsDelegate = new Delegate.With1Param<>();
        greetingsDelegate.add(str -> "Hello " + str);
        greetingsDelegate.add(str -> "Goodbye " + str);

        DelegateInvocationResult<String> invocationResult = greetingsDelegate.invokeAndAggregateExceptions("Sir");

        invocationResult.getFunctionInvocationResults().forEach(funInvRes -> System.out.println(funInvRes.getResult()));
        //prints: "Hello sir" and "Goodbye Sir"
     }
```

Key points:
 1. Choose the Delegate that matches the number of parameters in the method you target.
    * Use `Delegate.With0Params` if the method has no parameters (remember to specify the return type within the generics `<...>`).
    * Use `Delegate.With1Param` if the method has 1 parameter (and remember to specify the return type and parameter type within the generics `<...>`)
    * Use `Delegate.With2Params` if the method has 2 parameters (and remember ...)
    * etc (up until `Delegate.With9Params`)
 1. Use the special *Void types when you target methods, whose return type is lower case void (Upper case Void return type is rare).
    * Use `Delegate.With0ParamsAndVoid` if the method has no parameters and returns void.
    * Use `Delegate.With1ParamAndVoid` if the method has 1 parameter and returns void (and remember to specify the parameter type within the generics `<...>`)
    * Use `Delegate.With2ParamsAndVoid` if the method has 2 parameters (and remember ...)
    * etc (up until `Delegate.With9ParamsAndVoid`)
  1. Create your delegate with the new keyword.
  1. Invoke your delegate using one of the following invocation methods:
     * `invoke(...)`: invokes all functions added to the delegate and throws any exception that might occur.
     That is, not all the delegate's functions are guaranteed to be invoked.
     * `invokeAndAggregateExceptions`: Similar to above but exceptions are aggregated. 
     Use this invocation method if you want to make sure that all the functions are executed
     or if you don't want to handle any exceptions. But remember to inspect the result for any
     exceptions (`boolean oneOrMoreExceptionsThrown = myDelegateInvocationResultInstance.isOneOrMoreExceptionsThrown()`).
    
Functions added to a delegate can be removed again, but be aware that a reference to the function is needed.

Examples: 
This will work:
```
    Fun.With0Params<String> myFunction = "   hello world  "::trim;
    Delegate.With0Params<String> myDelegate = new Delegate.With0Params(myFunction);
    myDelegate.remove(myFunction);
```
 This won't work:
``` 
    Delegate.With0Params<String> myDelegate = new Delegate.With0Params("   hello world  "::trim);
    myDelegate.remove("   hello world  "::trim);
```
### Event

Delegates are often used together with Events, where an Event is just the public facing object
that allows users to add functions to your event. Under the hood the Event simply adds the functions to the underlying 
delegate. The idea being, that the power of invoking the delegate (and removing functions) are not exposed to the public.

A typical use case is a GUI framework, where the GUI component classes define events, which
the controllers can then subscribe to. For example, a subscriber may want to run an input validation function
each time input is entered into a text box.

Example:

```
    //Create a private Delegate. Make sure it is private so only *you* can invoke it.
    private static Delegate.With0Params<String> trimDelegate = new Delegate.With0Params<>();

    //Create a public Event using the delegate you just created.
    public static Event.With0Params<String> trimEvent= new Event.With0Params<>(trimDelegate);
```
 

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
