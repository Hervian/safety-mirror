# safety-mirror
Fun with Delegates, Events and type safe [Method](https://docs.oracle.com/javase/9/docs/api/java/lang/reflect/Method.html) creation.

## Releases
Available in [Maven Central](https://search.maven.org/search?q=g:%22com.github.hervian%22%3D%20AND%20a:%22safety-mirror%22%3D):
```
<dependency>
  <groupId>com.github.hervian</groupId>
  <artifactId>safety-mirror</artifactId>
  <version>3.0.0 (or see Maven Central or mvnrepository.com for newest version)</version>
</dependency>
```

Requires **Java 9** or above.
See requirements section if you need to use this library with *Java 8*.

## Usage
### Cheat sheet of features
* [Fun and friends](#fun-and-friends-no-more-functional-interfaces):  
        `Fun.With0Params<String> myFunctionField = "   hello world   "::trim;`
        `Fun.With2Params<Boolean, Object, Object> equals = Objects::equals;`  
        `Fun.With1ParamAndVoid<String> print = System.out::println;` 
        
        Method m1 = Fun.toMethod(String::isEmpty)
        Method m2 = Fun.<String>toMethod(Class::forName)); // to get overloaded method you must specify parameters in generics  
        
        assertEquals("isEmpty", Fun.getName(String::isEmpty)); //use Fun's static getName method to get the method name.
        The create Methods will not return the correct String.
* [Delegates in Java!](#delegates-in-java)  
        
        Delegate.With1Param<String, String> greetingsDelegate = new Delegate.With1Param<>();
        greetingsDelegate.add(str -> "Hello " + str);
        greetingsDelegate.add(str -> "Goodbye " + str);

        DelegateInvocationResult<String> invocationResult = greetingsDelegate.invokeAndAggregateExceptions("Sir");

        invocationResult.getFunctionInvocationResults().forEach(funInvRes -> System.out.println(funInvRes.getResult()));
        //prints: "Hello sir" and "Goodbye Sir"
* [Events](#events)  

        //Create a private Delegate. Make sure it is private so only *you* can invoke it.
        private static Delegate.With0Params<String> trimDelegate = new Delegate.With0Params<>();

        //Create a public Event using the delegate you just created.
        public static Event.With0Params<String> trimEvent= new Event.With0Params<>(trimDelegate);
* [Type safe method creation](#type-safe-method-creation)

        Method m1 = Fun.toMethod(Thread::isAlive)  // Get final method
        Method m2 = Fun.toMethod(String::isEmpty); // Get method from final class
        Method m3 = Fun.toMethod(BufferedReader::readLine); // Get method that throws checked exception
        Method m4 = Fun.<String, Class[]>toMethod(getClass()::getDeclaredMethod); //to get vararg method you must specify parameters in generics
        Method m5 = Fun.<String>toMethod(Class::forName); // to get overloaded method you must specify parameters in generics
        Method m6 = Fun.toMethod(this::toString); //Works with inherited methods

### Requirements
Requires **Java 9** or above.  
If you wish to use this project with *Java 8* you must clone the project and 1) change the pom.xml's properties section 
such that source and target is set to 1.8 (instead of 1.9) and 2) delete the  module-info.java file.
After this you should be able to build using JDK-8.

### Java 9+ setup guide...
This project is built with JDK9 and is modularized in that in contains a *module-info.java* file.
The module's name is `safety.mirror`.  
To make the library work, you must both add the *safety.mirror* module and allow it to perform reflection on your code.  
That is:  
1.  add the following line to your *module-info.java* file: `requires safety.mirror;`
1.  to allow the library to use reflection on your code you can do one of the following:
    1.  Declare your module as *open*, fx `open module my.project`, thereby granting all modules reflective access to
    all of your project's packages.
    1. open one or more specific packages to reflection (i.e. granting all modules reflective access to that 
    or those packages). This is done by adding a line a la `opens com.my.example.package;`. 
    Off course, if you're choosing this approach, you should open those packages where you
     use the safety-mirror library.
     
Example #1:  
```java
open module my.test {
    requires safety.mirror;
}
```

Example #2:  
```java
module my.test {
    requires safety.mirror;
    opens com.test.main;
}
```

## Details

### Fun and friends: no more functional interfaces
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
        Delegate.With1Param<String, String> greetingsDelegate = new Delegate.With1Param<>(); //NB: Please remember the diamond operator on the right hand side. Without it this line won't compile.
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
  1. As always with Java Generics you must remember to add the diamond `<>` operator to the right hand side in order for the 
     compiler to be able to correctly infer the generic type.
    
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
### Events

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
 

### Type safe method creation
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

### Known limitations and workarounds
The static overloaded Fun.toMethod methods does for some corner cases not compile when varargs is involved.
To solve this, one must help the compiler by casting:  
`Method m = Fun.toMethod((Fun.With1ParamAndVoid<String[]>)new FunToMethodTest()::methodThatTakesAVarargParam); //When varargs is involved you are unfortunately forced to cast to avoid an "Ambiguous method call" error.`  
Elaboration:  
Take for example the following method:
`public void foo(String... stringArray){...}`  
The following won't compile:  
`Method m = Fun.<String[]>toMethod(this::foo);`  
The reason is that the compiler can't find the correct overloaded method (With1ParamAndVoid<String[]>). This is the compiler error:  
`Ambiguous method call. Both toMethod(With0ParamsAndVoid<Object>) in Fun and toMethod(With1ParamAndVoid<Object>) in Fun match`

## Related projects
* [lambda-factory](https://github.com/Hervian/lambda-factory): a fast alternative to [Reflection](https://docs.oracle.com/javase/9/docs/api/java/lang/reflect/package-summary.html-based) based method invocation.
