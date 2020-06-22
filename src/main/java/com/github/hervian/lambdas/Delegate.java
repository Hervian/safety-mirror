package com.github.hervian.lambdas;

import lombok.SneakyThrows;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
 * </pre>
 * @author Anders Granau Høfft
 */
public abstract class Delegate<RETURN, METHOD_REF extends Fun<RETURN>> { //TODO: Rename to Delegate (and fix the Lombok Delegate annotation examples accordingly by using FQCN for the Lombok anno to anoid name clash.

    private List<METHOD_REF> methodRefs = new LinkedList<>();
    public enum InvocationStrategy {
        THROW_EXCEPTIONS,
        CATCH_AND_AGGREGATE_EXCEPTIONS;
    }

    public Delegate(METHOD_REF[] methodRefs) { //TODO: How to handle null values in array - ignore, as now, or throw exception?
        add(methodRefs);
    }

    public List<METHOD_REF> getMethodRefs() {
        return Collections.unmodifiableList(methodRefs);
    }


    protected abstract RETURN invoke(METHOD_REF function, Object... args) throws Exception;

    protected DelegateInvocationResult<RETURN> invoke(Object... args) throws Exception {
        return invoke(InvocationStrategy.THROW_EXCEPTIONS, args);
    }

    @SneakyThrows //Invocation exceptions are caught in the invokeMethodReferences method. Adding SneakyThrows annotation to avoid compiler error - but no exceptions are expected here.
    protected DelegateInvocationResult<RETURN> invokeAndAggregateExceptions(Object... args) {
        return invoke(InvocationStrategy.CATCH_AND_AGGREGATE_EXCEPTIONS, args);
    }

    protected DelegateInvocationResult<RETURN> invoke(InvocationStrategy invocationStrategy, Object... args) throws Exception {
        return invokeMethodReferences(invocationStrategy, args);
    }


    //TODO: Synchronous vs Asynchronous raising? See C# guide: https://docs.microsoft.com/en-us/dotnet/csharp/programming-guide/events/
    private DelegateInvocationResult<RETURN> invokeMethodReferences(InvocationStrategy invocationStrategy, Object... args) throws Exception {
        DelegateInvocationResult.DelegateInvocationResultBuilder delegateInvocationResultBuilder = DelegateInvocationResult.builder();
        for (METHOD_REF m : getMethodRefs()) {
            FunctionInvocationResult.FunctionInvocationResultBuilder builder = FunctionInvocationResult.builder();
            builder.method(m.toMethod());
            try {
                Object result = invoke(m, args);
                builder.result(result);
            } catch (Exception e) {
                if (InvocationStrategy.THROW_EXCEPTIONS == invocationStrategy) {
                    throw e;
                } else {
                    builder.exception(e);
                }
            }
            delegateInvocationResultBuilder.functionInvocationResult(builder.build());
        }
        return delegateInvocationResultBuilder.build();
    }

    //TODO Make thread safe?
    /**
     * <pre>
     * Method to add a function to the delegate (i.e. to the delegate's underlying
     * list of functions).
     * Be aware that you need to add the function as a Fun reference variable
     * if you want to be able to remove i later on - that is, you need the reference.
     *
     * Examples:
     *
     * This will work:
     *    Fun.With0Params&lt;String&gt; myFunction = "   hello world  "::trim;
     *    Delegate.With0Params&lt;String&gt; myDelegate = new Delegate.With0Params(myFunction);
     *    myDelegate.remove(myFunction);
     *
     * This won't work:
     *    Delegate.With0Params&lt;String&gt; myDelegate = new Delegate.With0Params("   hello world  "::trim);
     *    myDelegate.remove("   hello world  "::trim);
     * </pre>
     * @param methodRefVarArg
     */
    @SafeVarargs
    public final void add(METHOD_REF... methodRefVarArg){
        if (methodRefVarArg!=null){
            for (METHOD_REF methodRef: methodRefVarArg){
                if (methodRef != null) {
                    methodRefs.add(methodRef);
                }
            }
        }
    }

    //TODO Make thread safe?
    /**
     * <pre>
     * Method to remove function from delegate (i.e. from the delegate's underlying
     * list of functions).
     * Be aware that you need to add the function as a Fun reference variable
     * in order to be able to remove it.
     *
     * Examples:
     *
     * This will work:
     *    Fun.With0Params&lt;String&gt; myFunction = "   hello world  "::trim;
     *    Delegate.With0Params&lt;String&gt; myDelegate = new Delegate.With0Params(myFunction);
     *    myDelegate.remove(myFunction);
     *
     * This won't work:
     *    Delegate.With0Params&lt;String&gt; myDelegate = new Delegate.With0Params("   hello world  "::trim);
     *    myDelegate.remove("   hello world  "::trim);
     * </pre>
     *
     * @param methodRefVarArg
     * @return
     */
    @SafeVarargs
    public final boolean remove(METHOD_REF... methodRefVarArg){
        boolean removed = false;
        if (methodRefVarArg!=null){
            for (METHOD_REF methodRef: methodRefVarArg){
                if (methodRef != null) {
                    removed = methodRefs.removeIf(e -> e.equals(methodRef));
                }
            }
        }
        return removed;
    }



    //*********************************  STATIC NESTED CLASSES FROM HERE ONWARDS ***************************************/

    public static class With0Params<RETURN> extends Delegate<RETURN, Fun.With0Params<RETURN>> {
        public @SafeVarargs With0Params(Fun.With0Params<RETURN>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected RETURN invoke(Fun.With0Params<RETURN> function, Object... args) throws Exception {
            return function.invoke();
        }
    }


    public static class With0ParamsAndVoid extends Delegate<Void, Fun.With0ParamsAndVoid<?>> {
        public With0ParamsAndVoid(Fun.With0ParamsAndVoid... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected Void invoke(Fun.With0ParamsAndVoid function, Object... args) throws Exception {
            function.invoke();
            return null;
        }
    }


    public static class With1Param<RETURN, PARAM1> extends Delegate<RETURN, Fun.With1Param<RETURN, PARAM1>> {
        public With1Param(Fun.With1Param<RETURN, PARAM1>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected RETURN invoke(Fun.With1Param<RETURN, PARAM1> function, Object... args) throws Exception {
            return function.invoke(args==null ? null : (PARAM1)args[0]);
        }
    }

    public static class With1ParamAndVoid<PARAM1> extends Delegate<Void, Fun.With1ParamAndVoid<PARAM1>> {
        public With1ParamAndVoid(Fun.With1ParamAndVoid<PARAM1>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected Void invoke(Fun.With1ParamAndVoid<PARAM1> function, Object... args) throws Exception {
            function.invoke(args==null ? null : (PARAM1)args[0]);
            return null;
        }
    }


    public static class With2Params<RETURN, PARAM1, PARAM2> extends Delegate<RETURN, Fun.With2Params<RETURN, PARAM1, PARAM2>> {
        public With2Params(Fun.With2Params<RETURN, PARAM1 ,PARAM2>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected RETURN invoke(Fun.With2Params<RETURN, PARAM1, PARAM2> function, Object... args) throws Exception {
            return function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1]);
        }
    }

    public static class With2ParamsAndVoid<PARAM1, PARAM2> extends Delegate<Void, Fun.With2ParamsAndVoid<PARAM1, PARAM2>> {
        public With2ParamsAndVoid(Fun.With2ParamsAndVoid<PARAM1 ,PARAM2>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected Void invoke(Fun.With2ParamsAndVoid<PARAM1, PARAM2> function, Object... args) throws Exception {
            function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1]);
            return null;
        }
    }


    public static class With3Params<RETURN, PARAM1, PARAM2, PARAM3> extends Delegate<RETURN, Fun.With3Params<RETURN, PARAM1, PARAM2, PARAM3>> {
        public With3Params(Fun.With3Params<RETURN, PARAM1 ,PARAM2, PARAM3>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected RETURN invoke(Fun.With3Params<RETURN, PARAM1, PARAM2, PARAM3> function, Object... args) throws Exception {
            return function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2]);
        }
    }

    public static class With3ParamsAndVoid<PARAM1, PARAM2, PARAM3> extends Delegate<Void, Fun.With3ParamsAndVoid<PARAM1, PARAM2, PARAM3>> {
        public With3ParamsAndVoid(Fun.With3ParamsAndVoid<PARAM1 ,PARAM2, PARAM3>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected Void invoke(Fun.With3ParamsAndVoid<PARAM1, PARAM2, PARAM3> function, Object... args) throws Exception {
            function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2]);
            return null;
        }
    }


    public static class With4Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4> extends Delegate<RETURN, Fun.With4Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4>> {
        public With4Params(Fun.With4Params<RETURN, PARAM1 ,PARAM2, PARAM3, PARAM4>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected RETURN invoke(Fun.With4Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4> function, Object... args) throws Exception {
            return function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2], (PARAM4)args[3]);
        }
    }

    public static class With4ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4> extends Delegate<Void, Fun.With4ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4>> {
        public With4ParamsAndVoid(Fun.With4ParamsAndVoid<PARAM1 ,PARAM2, PARAM3, PARAM4>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected Void invoke(Fun.With4ParamsAndVoid< PARAM1, PARAM2, PARAM3, PARAM4> function, Object... args) throws Exception {
            function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2], (PARAM4)args[3]);
            return null;
        }
    }


    public static class With5Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5> extends Delegate<RETURN, Fun.With5Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5>> {
        public With5Params(Fun.With5Params<RETURN, PARAM1 ,PARAM2, PARAM3, PARAM4, PARAM5>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected RETURN invoke(Fun.With5Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5> function, Object... args) throws Exception {
            return function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2], (PARAM4)args[3], (PARAM5)args[4]);
        }
    }

    public static class With5ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5> extends Delegate<Void, Fun.With5ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5>> {
        public With5ParamsAndVoid(Fun.With5ParamsAndVoid<PARAM1 ,PARAM2, PARAM3, PARAM4, PARAM5>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected Void invoke(Fun.With5ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5> function, Object... args) throws Exception {
            function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2], (PARAM4)args[3], (PARAM5)args[4]);
            return null;
        }
    }


    public static class With6Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6> extends Delegate<RETURN, Fun.With6Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6>> {
        public With6Params(Fun.With6Params<RETURN, PARAM1 ,PARAM2, PARAM3, PARAM4, PARAM5, PARAM6>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected RETURN invoke(Fun.With6Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6> function, Object... args) throws Exception {
            return function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2], (PARAM4)args[3], (PARAM5)args[4], (PARAM6)args[5]);
        }
    }

    public static class With6ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6> extends Delegate<Void, Fun.With6ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6>> {
        public With6ParamsAndVoid(Fun.With6ParamsAndVoid<PARAM1 ,PARAM2, PARAM3, PARAM4, PARAM5, PARAM6>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected Void invoke(Fun.With6ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6> function, Object... args) throws Exception {
            function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2], (PARAM4)args[3], (PARAM5)args[4], (PARAM6)args[5]);
            return null;
        }
    }


    public static class With7Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7> extends Delegate<RETURN, Fun.With7Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7>> {
        public With7Params(Fun.With7Params<RETURN, PARAM1 ,PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected RETURN invoke(Fun.With7Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7> function, Object... args) throws Exception {
            return function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2], (PARAM4)args[3], (PARAM5)args[4], (PARAM6)args[5], (PARAM7)args[6]);
        }
    }

    public static class With7ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7> extends Delegate<Void, Fun.With7ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7>> {
        public With7ParamsAndVoid(Fun.With7ParamsAndVoid<PARAM1 ,PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected Void invoke(Fun.With7ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7> function, Object... args) throws Exception {
            function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2], (PARAM4)args[3], (PARAM5)args[4], (PARAM6)args[5], (PARAM7)args[6]);
            return null;
        }
    }


    public static class With8Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8> extends Delegate<RETURN, Fun.With8Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8>> {
        public With8Params(Fun.With8Params<RETURN, PARAM1 ,PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected RETURN invoke(Fun.With8Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8> function, Object... args) throws Exception {
            return function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2], (PARAM4)args[3], (PARAM5)args[4], (PARAM6)args[5], (PARAM7)args[6], (PARAM8)args[7]);
        }
    }

    public static class With8ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8> extends Delegate<Void, Fun.With8ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8>> {
        public With8ParamsAndVoid(Fun.With8ParamsAndVoid<PARAM1 ,PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected Void invoke(Fun.With8ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8> function, Object... args) throws Exception {
            function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2], (PARAM4)args[3], (PARAM5)args[4], (PARAM6)args[5], (PARAM7)args[6], (PARAM8)args[7]);
            return null;
        }
    }


    public static class With9Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9> extends Delegate<RETURN, Fun.With9Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9>> {
        public With9Params(Fun.With9Params<RETURN, PARAM1 ,PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected RETURN invoke(Fun.With9Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9> function, Object... args) throws Exception {
            return function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2], (PARAM4)args[3], (PARAM5)args[4], (PARAM6)args[5], (PARAM7)args[6], (PARAM8)args[7], (PARAM9)args[8]);
        }
    }

    public static class With9ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9> extends Delegate<Void, Fun.With9ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9>> {
        public With9ParamsAndVoid(Fun.With9ParamsAndVoid<PARAM1 ,PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9>... methodRefs) {
            super(methodRefs);
        }

        @Override
        protected Void invoke(Fun.With9ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9> function, Object... args) throws Exception {
            function.invoke(args==null ? null : (PARAM1)args[0], (PARAM2)args[1], (PARAM3)args[2], (PARAM4)args[3], (PARAM5)args[4], (PARAM6)args[5], (PARAM7)args[6], (PARAM8)args[7], (PARAM9)args[8]);
            return null;
        }
    }


    /**
     * Interface class created to be used in Lombok's @lombok.experimental.Delegate annotation as an argument to the 'excludes' annotation type member.
     * The idea is that one can create a C# like 'event' by annotating a private Delegate field with @Delegate(excludes=Delegator.IDelegator.class)
     * Going forward we hope that Lombok will support creating meta annotations, in which case this project could create an @Event annotation that lombok would then view as @Delegate(excludes=Delegator.IDelegator.class).
     */
    public interface IDelegator {
        void invoke();
        void invokeWithSneakyThrows();
        List<?> getMethodRefs();
    }

}