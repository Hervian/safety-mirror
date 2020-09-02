package com.github.hervian.reflection;

/**
 * <pre>
 *  Copyright 2016 Anders Granau Høfft
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  END OF NOTICE
 *
 *
 * This class is a wrapper around a Delegator. It limits the number of public methods.
 * The idea is to create a C#-like 'event', where subscribers can only add and remove
 * events but where the publisher (i.e. the class declaring the event) have full
 * control of the underlying delegate. In other words: only the publisher can invoke
 * the delegate's method references (a.k.a. publish the event).
 * <br>Examples:
 *
 * Example 1:
 *    Let's create a private Delegate but expose the add() and remove() method
 *    by creating a public Event field, that wraps the Delegator:
 *
 *       private Delegator.With0Params&lt;String&gt; delegator = Delegator.with0Params();
 *       public Event.With0Params&lt;String&gt; event = Event.with0Params(delegator);
 *
 * Example 2:
 *    Another way to expose the add and remove method only is by using
 *    Project Lombok's Delegate annotation, as such:
 *
 *      {@literal @}lombok.experimental.Delegate(excludes = Delegator.IDelegator.class)
 *       private Delegator.With0Params&lt;String&gt; delegator = Delegator.with0Params();
 *
 *    With this approach we do not need the Event field, since Lombok generates
 *    a public add() and remove() method directly in the declaring class.
 *    Of course, this approach requires that your project uses Lombok.
 *    As it stands, the Event approach is more easily remembered, however,
 *    if Lombok at some point creates the possibility of meta-annotations,
 *    this project will create an Event annnotation that would simplify above.
 *    Until that happens, using the Event class is the preferred way to create an'event'.
 * </pre>
 * @param <METHOD_REF>
 * @author Anders Granau Høfft
 */
public abstract class Event<RETURN, METHOD_REF extends Fun<RETURN>> {

    private Delegate<RETURN, METHOD_REF> delegate;

    private Event(Delegate<RETURN, METHOD_REF> delegate){
        this.delegate = delegate;
    }

    public void add(METHOD_REF... methodRefVarArg){
        delegate.add(methodRefVarArg);
    }

    public boolean remove(METHOD_REF... methodRefVarArg){
        return delegate.remove(methodRefVarArg);
    }


    //*********************************  STATIC NESTED CLASSES FROM HERE ONWARDS ***************************************/

    public static class With0Params<RETURN> extends Event<RETURN, Fun.With0Params<RETURN>> {
        public With0Params(Delegate.With0Params<RETURN> delegator) {
            super(delegator);
        }
    }

    public static class With0ParamsAndVoid extends Event<Void, Fun.With0ParamsAndVoid<?>> {
        public With0ParamsAndVoid(Delegate.With0ParamsAndVoid delegator) {
            super(delegator);
        }
    }

    public static class With1Param<RETURN, PARAM1> extends Event<RETURN, Fun.With1Param<RETURN, PARAM1>> {
        public With1Param(Delegate.With1Param<RETURN, PARAM1> delegator) {
            super(delegator);
        }
    }

    public static class With1ParamAndVoid<PARAM1> extends Event<Void, Fun.With1ParamAndVoid<PARAM1>> {
        public With1ParamAndVoid(Delegate.With1ParamAndVoid<PARAM1> delegator) {
            super(delegator);
        }
    }

    public static class With2Params<RETURN, PARAM1, PARAM2> extends Event<RETURN, Fun.With2Params<RETURN, PARAM1, PARAM2>> {
        public With2Params(Delegate.With2Params<RETURN, PARAM1, PARAM2> delegator) {
            super(delegator);
        }
    }

    public static class With2ParamsAndVoid<PARAM1, PARAM2> extends Event<Void, Fun.With2ParamsAndVoid<PARAM1, PARAM2>> {
        public With2ParamsAndVoid(Delegate.With2ParamsAndVoid<PARAM1, PARAM2> delegator) {
            super(delegator);
        }
    }

    public static class With3Params<RETURN, PARAM1, PARAM2, PARAM3> extends Event<RETURN, Fun.With3Params<RETURN, PARAM1, PARAM2, PARAM3>> {
        public With3Params(Delegate.With3Params<RETURN, PARAM1, PARAM2, PARAM3> delegator) {
            super(delegator);
        }
    }

    public static class With3ParamsAndVoid<PARAM1, PARAM2, PARAM3> extends Event<Void, Fun.With3ParamsAndVoid<PARAM1, PARAM2, PARAM3>> {
        public With3ParamsAndVoid(Delegate.With3ParamsAndVoid<PARAM1, PARAM2, PARAM3> delegator) {
            super(delegator);
        }
    }

    public static class With4Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4> extends Event<RETURN, Fun.With4Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4>> {
        public With4Params(Delegate.With4Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4> delegator) {
            super(delegator);
        }
    }

    public static class With4ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4> extends Event<Void, Fun.With4ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4>> {
        public With4ParamsAndVoid(Delegate.With4ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4> delegator) {
            super(delegator);
        }
    }

    public static class With5Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5> extends Event<RETURN, Fun.With5Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5>> {
        public With5Params(Delegate.With5Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5> delegator) {
            super(delegator);
        }
    }

    public static class With5ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5> extends Event<Void, Fun.With5ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5>> {
        public With5ParamsAndVoid(Delegate.With5ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5> delegator) {
            super(delegator);
        }
    }

    public static class With6Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6> extends Event<RETURN, Fun.With6Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6>> {
        public With6Params(Delegate.With6Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6> delegator) {
            super(delegator);
        }
    }

    public static class With6ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6> extends Event<Void, Fun.With6ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6>> {
        public With6ParamsAndVoid(Delegate.With6ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6> delegator) {
            super(delegator);
        }
    }

    public static class With7Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7> extends Event<RETURN, Fun.With7Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7>> {
        public With7Params(Delegate.With7Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7> delegator) {
            super(delegator);
        }
    }

    public static class With7ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7> extends Event<Void, Fun.With7ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7>> {
        public With7ParamsAndVoid(Delegate.With7ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7> delegator) {
            super(delegator);
        }
    }

    public static class With8Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8> extends Event<RETURN, Fun.With8Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8>> {
        public With8Params(Delegate.With8Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8> delegator) {
            super(delegator);
        }
    }

    public static class With8ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8> extends Event<Void, Fun.With8ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8>> {
        public With8ParamsAndVoid(Delegate.With8ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8> delegator) {
            super(delegator);
        }
    }

    public static class With9Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9> extends Event<RETURN, Fun.With9Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9>> {
        public With9Params(Delegate.With9Params<RETURN, PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9> delegator) {
            super(delegator);
        }
    }

    public static class With9ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9> extends Event<Void, Fun.With9ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9>> {
        public With9ParamsAndVoid(Delegate.With9ParamsAndVoid<PARAM1, PARAM2, PARAM3, PARAM4, PARAM5, PARAM6, PARAM7, PARAM8, PARAM9> delegator) {
            super(delegator);
        }
    }

}
