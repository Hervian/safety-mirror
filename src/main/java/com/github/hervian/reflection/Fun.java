package com.github.hervian.reflection;

import com.github.hervian.reflection.util.SerializedLambdaToMethod;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * <pre>
 * Copyright 2016 Anders Granau Høfft
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * END OF NOTICE
 *
 *
 * Fun, short for Function, is a super interface that from a user perspective is used as part of a fluent API
 * in signatures that accepts Method References.
 * I.e. one writes 'Fun.' and then choose the appropriate nested class such as 'Fun.With2Params'.
 * (In most IDEs you can simply type Fun.W2 to get the autocomplete to suggest 'Fun.With2Params'.)
 *
 * Notice that you must supply the signatures return value and parameters in the generics,
 * fx: 'Fun.With2Params&lt;Double, Integer, String&gt; for methods that
 * given an Integer and a String (in that order) returns a Double.
 *
 * Note that since capital 'Void' is NOT a boxed lower case 'void' we need to give method references
 * with return type void special attention. In other words, current Java versions won't accept generics with
 * primitives, including void, but for all primitives except void, this is ok since fx 'Fun.With2Params&lt;Double&gt;
 * will match primitive signatures due to autoboxing. But for void as return type we must use a special subclass and
 * write code like fx: 'Fun.With2ParamsAndVoid&lt;Double&gt;
 * Note also, that this may (or may not) change once Project Valhalla is completed and specialized Generics
 * become part of the Java Language Specification. See https://en.wikipedia.org/wiki/Project_Valhalla_(Java_language).
 *
 * Besides the fluent API described above (i.e. the subinterfaces that allow you to pass around functions and
 * use them as types), the Fun interface contains a large number of overloaded methods.
 * The overloading is actually an implementation detail.
 * From a user perspective you should consider the Fun type to have the following API:
 *
 * <code>java.lang.Method method = Fun.toMethod(SomeClassOrObject::someMethod);</code>
 *
 * Please be aware that there are 2 situations where you must help the compiler choose the correct overloaded method.
 *
 * 1.
 * When targeting an overloaded  method you must help the compiler choose the correct overloaded toMethod method
 * by specifying the parameters and return value in generics. This is fx the case with this call:
 * <code>Fun.<String>toMethod(Class::forName)</code></ol>
 *
 * 2.
 * When targeting a method with varargs you must cast the Method Reference to help the compiler choose
 * the correct overloaded toMethod method, Fx:
 * <code>Method m = Fun.toMethod((Fun.With1ParamAndVoid<String[]>)new FunToMethodTest()::methodThatTakesAVarargParam);</code>
 *
 * </pre>
 * @author Anders Granau Høfft
 */
public interface Fun<RETURN> extends Serializable {

    default Method toMethod(){
        return SerializedLambdaToMethod.createMethodFromSuperConsumer(this);
    }

    /**
     * <pre>
     * Usage:
     * <code>java.lang.Method method = Fun.toMethod(SomeClassOrObject::someMethod);</code>
     *
     * Don't worry too much about the large number of overloaded toMethod methods. The overloading is an implementation
     * detail. That being said, pay attention to below corner cases.
     *
     * Please be aware that there are 2 situations where you must help the compiler choose the correct overloaded method.
     *
     * 1.
     * When targeting an overloaded  method you must help the compiler choose the correct overloaded toMethod method
     * by specifying the parameters and return value in generics. This is fx the case with this call:
     * <code>Fun.<String>toMethod(Class::forName)</code></ol>
     *
     * 2.
     * When targeting a method with varargs you must cast the Method Reference to help the compiler choose
     * the correct overloaded toMethod method, Fx:
     * <code>Method m = Fun.toMethod((Fun.With1ParamAndVoid<String[]>)new FunToMethodTest()::methodThatTakesAVarargParam);</code>
     *
     * </pre>
     * @see Fun
     */
    static <DUMMY> Method toMethod(Fun.With0ParamsAndVoid<DUMMY> methodRef) { return SerializedLambdaToMethod.createMethodFromSuperConsumer(methodRef); }

    /**
     * @see Fun#toMethod(With0ParamsAndVoid)
     */
    static <T1> Method toMethod(Fun.With1ParamAndVoid<T1> methodRef) { return SerializedLambdaToMethod.createMethodFromSuperConsumer(methodRef); }
    /**
     * @see Fun#toMethod(With0ParamsAndVoid)
     */
    static <T1, T2> Method toMethod(Fun.With2ParamsAndVoid<T1, T2> methodRef) { return SerializedLambdaToMethod.createMethodFromSuperConsumer(methodRef); }
    /**
     * @see Fun#toMethod(With0ParamsAndVoid)
     */
    static <T1, T2, T3> Method toMethod(Fun.With3ParamsAndVoid<T1, T2, T3> methodRef) { return SerializedLambdaToMethod.createMethodFromSuperConsumer(methodRef); }
    /**
     * @see Fun#toMethod(With0ParamsAndVoid)
     */
    static <T1, T2, T3, T4> Method toMethod(Fun.With4ParamsAndVoid<T1, T2, T3, T4> methodRef) { return SerializedLambdaToMethod.createMethodFromSuperConsumer(methodRef); }
    /**
     * @see Fun#toMethod(With0ParamsAndVoid)
     */
    static <T1, T2, T3, T4, T5> Method toMethod(Fun.With5ParamsAndVoid<T1, T2, T3, T4, T5> methodRef) { return SerializedLambdaToMethod.createMethodFromSuperConsumer(methodRef); }
    /**
     * @see Fun#toMethod(With0ParamsAndVoid)
     */
    static <T1, T2, T3, T4, T5, T6> Method toMethod(Fun.With6ParamsAndVoid<T1, T2, T3, T4, T5, T6> methodRef) { return SerializedLambdaToMethod.createMethodFromSuperConsumer(methodRef); }
    /**
     * @see Fun#toMethod(With0ParamsAndVoid)
     */
    static <T1, T2, T3, T4, T5, T6, T7> Method toMethod(Fun.With7ParamsAndVoid<T1, T2, T3, T4, T5, T6, T7> methodRef) { return SerializedLambdaToMethod.createMethodFromSuperConsumer(methodRef); }
    /**
     * @see Fun#toMethod(With0ParamsAndVoid)
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8> Method toMethod(Fun.With8ParamsAndVoid<T1, T2, T3, T4, T5, T6, T7, T8> methodRef) { return SerializedLambdaToMethod.createMethodFromSuperConsumer(methodRef); }
    /**
     * @see Fun#toMethod(With0ParamsAndVoid)
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> Method toMethod(Fun.With9ParamsAndVoid<T1, T2, T3, T4, T5, T6, T7, T8, T9> methodRef) { return SerializedLambdaToMethod.createMethodFromSuperConsumer(methodRef); }

    /**
     * Ues this method to turn a Method Reference in the form of a double colon expression into a String holding the method name. That is to get 'isEmpty' from String::isEmpty.
     * <br> <br>
     * NB: An output a la 'lambda$16' can occur for corner cases, see  Holger, on SO: "The limitations are that it will print not very useful method references for lambda expressions (references to the synthetic method containing the lambda code)" https://stackoverflow.com/a/21879031/6095334
     * TODO: In those cases where a string a la 'lambda$16' is returned, i.e. the method name could not be resolved, one could perhaps use Gunnar Morlings approach as a fallback? https://in.relation.to/2016/04/14/emulating-property-literals-with-java-8-method-references/
     *
     * @param consumer A Method Reference, typically in the form using the double colon syntax, fx String::isEmpty
     * @param <DUMMY> A dummy generic value created to satisfy the compiler, which will otherwise get confused and be unable to choose the correct overloaded getName method,
     * @return the method name
     */
    static <DUMMY> String getName(Fun.With0ParamsAndVoid<DUMMY> consumer) { return SerializedLambdaToMethod.createMethodNameFromSuperConsumer(consumer); }

    /**
     * @see Fun#getName(With0ParamsAndVoid)
     */
    static <T1> String getName(Fun.With1ParamAndVoid<T1> consumer) { return SerializedLambdaToMethod.createMethodNameFromSuperConsumer(consumer); }
    /**
     * @see Fun#getName(With0ParamsAndVoid)
     */
    static <T1, T2> String getName(Fun.With2ParamsAndVoid<T1, T2> consumer) { return SerializedLambdaToMethod.createMethodNameFromSuperConsumer(consumer); }
    /**
     * @see Fun#getName(With0ParamsAndVoid)
     */
    static <T1, T2, T3> String getName(Fun.With3ParamsAndVoid<T1, T2, T3> consumer) { return SerializedLambdaToMethod.createMethodNameFromSuperConsumer(consumer); }
    /**
     * @see Fun#getName(With0ParamsAndVoid)
     */
    static <T1, T2, T3, T4> String getName(Fun.With4ParamsAndVoid<T1, T2, T3, T4> consumer) { return SerializedLambdaToMethod.createMethodNameFromSuperConsumer(consumer); }
    /**
     * @see Fun#getName(With0ParamsAndVoid)
     */
    static <T1, T2, T3, T4, T5> String getName(Fun.With5ParamsAndVoid<T1, T2, T3, T4, T5> consumer) { return SerializedLambdaToMethod.createMethodNameFromSuperConsumer(consumer); }
    /**
     * @see Fun#getName(With0ParamsAndVoid)
     */
    static <T1, T2, T3, T4, T5, T6> String getName(Fun.With6ParamsAndVoid<T1, T2, T3, T4, T5, T6> consumer) { return SerializedLambdaToMethod.createMethodNameFromSuperConsumer(consumer); }
    /**
     * @see Fun#getName(With0ParamsAndVoid)
     */
    static <T1, T2, T3, T4, T5, T6, T7> String getName(Fun.With7ParamsAndVoid<T1, T2, T3, T4, T5, T6, T7> consumer) { return SerializedLambdaToMethod.createMethodNameFromSuperConsumer(consumer); }
    /**
     * @see Fun#getName(With0ParamsAndVoid)
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8> String getName(Fun.With8ParamsAndVoid<T1, T2, T3, T4, T5, T6, T7, T8> consumer) { return SerializedLambdaToMethod.createMethodNameFromSuperConsumer(consumer); }
    /**
     * @see Fun#getName(With0ParamsAndVoid)
     */
    static <T1, T2, T3, T4, T5, T6, T7, T8, T9> String getName(Fun.With9ParamsAndVoid<T1, T2, T3, T4, T5, T6, T7, T8, T9> consumer) { return SerializedLambdaToMethod.createMethodNameFromSuperConsumer(consumer); }


    //************************* NESTED STATIC INTERFACES FROM HERE ONWARDS. THE IDEA BEING TO CREATE A FLUENT API ****************************'

    @FunctionalInterface
    interface With0Params<RETURN> extends Fun<RETURN> {
        RETURN invoke() throws Exception;

        @SneakyThrows
        default RETURN invokeWithSneakyThrows(){
            return invoke();
        }
    }

    /**
     * Use the special *AndVoid subclasses for matching methods that return lower case void.
     * Do note, though, that the *AndVoid interfaces will match any return type.
     */
    @FunctionalInterface
    interface With0ParamsAndVoid<DUMMY> extends Fun<Void> { // A note on the DUMMY generic value: It was the only way to make the following jUnit assert pass: assertNotNull(Types2.<String, Class<?>[]>createMethod(getClass()::getDeclaredMethod)); See comment in DelegateTest
        void invoke() throws Exception;

        @SneakyThrows
        default void invokeWithSneakyThrows(){
            invoke();
        }
    }

    @FunctionalInterface
    interface With1Param<RETURN, PARAM1> extends Fun<RETURN> {
        RETURN invoke(@NonNull @NotNull PARAM1 param1) throws Exception;

        /**
         * @param param1
         * @return the return value/result of the invoked method
         * @throws NullPointerException if one or more of the arguments are null
         */
        @SneakyThrows
        default RETURN invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1){
            return invoke(param1);
        }
    }

    /**
     * @see With0ParamsAndVoid
     */
    @FunctionalInterface
    interface With1ParamAndVoid<PARAM1> extends Fun<Void> {
        void invoke(@NonNull @NotNull PARAM1 param1) throws Exception;

        /**
         * @param param1
         * @throws NullPointerException if one or more of the arguments are null
         */
        @SneakyThrows
        default void invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1){
            invoke(param1);
        }
    }

    @FunctionalInterface
    interface With2Params<RETURN, PARAM1, PARAM2> extends Fun<RETURN> {
        RETURN invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2) throws Exception;

        @SneakyThrows
        default RETURN invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2){
            return invoke(param1, param2);
        }
    }

    /**
     * @see With0ParamsAndVoid
     */
    @FunctionalInterface
    interface With2ParamsAndVoid<PARAM1, PARAM2> extends Fun<Void> {
        void invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2) throws Exception;

        /**
         * @param param1
         * @param param2
         * @throws NullPointerException if one or more of the arguments are null
         */
        @SneakyThrows
        default void invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2){
            invoke(param1, param2);
        }
    }

    @FunctionalInterface
    interface With3Params<RETURN, PARAM1, PARAM2, PARAM3> extends Fun<RETURN> {
        RETURN invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3) throws Exception;

        @SneakyThrows
        default RETURN invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3){
            return invoke(param1, param2, param3);
        }
    }

    /**
     * @see With0ParamsAndVoid
     */
    @FunctionalInterface
    interface With3ParamsAndVoid<PARAM1, PARAM2, PARAM3> extends Fun<Void> {
        void invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3) throws Exception;

        /**
         * @param param1
         * @param param2
         * @param param3
         * @throws NullPointerException if one or more of the arguments are null
         */
        @SneakyThrows
        default void invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3){
            invoke(param1, param2, param3);
        }
    }

    @FunctionalInterface
    interface With4Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4> extends Fun<RETURN> {
        RETURN invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4) throws Exception;

        @SneakyThrows
        default RETURN invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4){
            return invoke(param1, param2, param3, param4);
        }
    }

    /**
     * @see With0ParamsAndVoid
     */
    @FunctionalInterface
    interface With4ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4> extends Fun<Void> {
        void invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4) throws Exception;

        /**
         * @param param1
         * @param param2
         * @param param3
         * @param param4
         * @throws NullPointerException if one or more of the arguments are null
         */
        @SneakyThrows
        default void invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4){
            invoke(param1, param2, param3, param4);
        }
    }

    @FunctionalInterface
    interface With5Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5> extends Fun<RETURN> {
        RETURN invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5) throws Exception;

        @SneakyThrows
        default RETURN invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5){
            return invoke(param1, param2, param3, param4, param5);
        }
    }

    /**
     * @see With0ParamsAndVoid
     */
    @FunctionalInterface
    interface With5ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5> extends Fun<Void> {
        void invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5) throws Exception;

        /**
         * @param param1
         * @param param2
         * @param param3
         * @param param4
         * @param param5
         * @throws NullPointerException if one or more of the arguments are null
         */
        @SneakyThrows
        default void invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5){
            invoke(param1, param2, param3, param4, param5);
        }
    }

    @FunctionalInterface
    interface With6Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6> extends Fun<RETURN> {
        RETURN invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6) throws Exception;

        @SneakyThrows
        default RETURN invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6){
            return invoke(param1, param2, param3, param4, param5, param6);
        }
    }

    /**
     * @see With0ParamsAndVoid
     */
    @FunctionalInterface
    interface With6ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6> extends Fun<Void> {
        void invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6) throws Exception;

        /**
         * @param param1
         * @param param2
         * @param param3
         * @param param4
         * @param param5
         * @param param6
         * @throws NullPointerException if one or more of the arguments are null
         */
        @SneakyThrows
        default void invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6){
            invoke(param1, param2, param3, param4, param5, param6);
        }
    }

    @FunctionalInterface
    interface With7Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7> extends Fun<RETURN> {
        RETURN invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6, @NonNull @NotNull PARAM7 param7) throws Exception;

        @SneakyThrows
        default RETURN invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6, @NonNull @NotNull PARAM7 param7){
            return invoke(param1, param2, param3, param4, param5, param6, param7);
        }
    }

    /**
     * @see With0ParamsAndVoid
     */
    @FunctionalInterface
    interface With7ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7> extends Fun<Void> {
        void invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6, @NonNull @NotNull PARAM7 param7) throws Exception;

        /**
         * @param param1
         * @param param2
         * @param param3
         * @param param4
         * @param param5
         * @param param6
         * @param param7
         * @throws NullPointerException if one or more of the arguments are null
         */
        @SneakyThrows
        default void invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6, @NonNull @NotNull PARAM7 param7){
            invoke(param1, param2, param3, param4, param5, param6, param7);
        }
    }

    @FunctionalInterface
    interface With8Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8> extends Fun<RETURN> {
        RETURN invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6, @NonNull @NotNull PARAM7 param7, @NonNull @NotNull PARAM8 param8) throws Exception;

        @SneakyThrows
        default RETURN invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6, @NonNull @NotNull PARAM7 param7, @NonNull @NotNull PARAM8 param8){
            return invoke(param1, param2, param3, param4, param5, param6, param7, param8);
        }
    }

    /**
     * @see With0ParamsAndVoid
     */
    @FunctionalInterface
    interface With8ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8> extends Fun<Void> {
        void invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6, @NonNull @NotNull PARAM7 param7, @NonNull @NotNull PARAM8 param8) throws Exception;

        /**
         * @param param1
         * @param param2
         * @param param3
         * @param param4
         * @param param5
         * @param param6
         * @param param7
         * @param param8
         * @throws NullPointerException if one or more of the arguments are null
         */
        @SneakyThrows
        default void invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6, @NonNull @NotNull PARAM7 param7, @NonNull @NotNull PARAM8 param8){
            invoke(param1, param2, param3, param4, param5, param6, param7, param8);
        }
    }

    @FunctionalInterface
    interface With9Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9> extends Fun<RETURN> {
        RETURN invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6, @NonNull @NotNull PARAM7 param7, @NonNull @NotNull PARAM8 param8, @NonNull @NotNull PARAM9 param9) throws Exception;

        @SneakyThrows
        default RETURN invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6, @NonNull @NotNull PARAM7 param7, @NonNull @NotNull PARAM8 param8, @NonNull @NotNull PARAM9 param9){
            return invoke(param1, param2, param3, param4, param5, param6, param7, param8, param9);
        }
    }

    /**
     * @see With0ParamsAndVoid
     */
    @FunctionalInterface
    interface With9ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9> extends Fun<Void> {
        void invoke(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6, @NonNull @NotNull PARAM7 param7, @NonNull @NotNull PARAM8 param8, @NonNull @NotNull PARAM9 param9) throws Exception;

        /**
         * @param param1
         * @param param2
         * @param param3
         * @param param4
         * @param param5
         * @param param6
         * @param param7
         * @param param8
         * @param param9
         * @throws NullPointerException if one or more of the arguments are null
         */
        @SneakyThrows
        default void invokeWithSneakyThrows(@NonNull @NotNull PARAM1 param1, @NonNull @NotNull PARAM2 param2, @NonNull @NotNull PARAM3 param3, @NonNull @NotNull PARAM4 param4, @NonNull @NotNull PARAM5 param5, @NonNull @NotNull PARAM6 param6, @NonNull @NotNull PARAM7 param7, @NonNull @NotNull PARAM8 param8, @NonNull @NotNull PARAM9 param9){
            invoke(param1, param2, param3, param4, param5, param6, param7, param8, param9);
        }
    }

}
