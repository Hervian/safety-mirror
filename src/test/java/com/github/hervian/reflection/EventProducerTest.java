package com.github.hervian.reflection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventProducerTest {

    private static Delegate.With0Params<String> trimDelegate = new Delegate.With0Params<>();
    public static Event.With0Params<String> trimEvent= new Event.With0Params<>(trimDelegate);

    //If Lombok ever creates a meta annotation, this project could create an Event annotation that unfolded to: '@lombok.experimental.Delegate(excludes = Delegate.IDelegator.class)'
    @lombok.experimental.Delegate(excludes = Delegate.IDelegator.class)
    private Delegate.With0Params<String> privateDelegateWithLombokGeneratedAddAndRemoveMethod = new Delegate.With0Params("  hello world  "::trim); //*private* Delegate: Only this class can invoke the Delegate

    public static DelegateInvocationResult<String> requestInvokationOfTrimEvent(){
        return trimDelegate.invokeAndAggregateExceptions();
    }

    @Test
    public void testProduceEvent(){
        Delegate.With0Params<String> localTrimDelegate = new Delegate.With0Params<>();
        Event.With0Params<String> localTrimEvent= new Event.With0Params<>(localTrimDelegate);
        localTrimEvent.add("  hello world    "::trim);
        DelegateInvocationResult<String> res = localTrimDelegate.invokeAndAggregateExceptions();
        assertEquals("hello world", res.get(0).getResult());

        assertEquals("hello world", privateDelegateWithLombokGeneratedAddAndRemoveMethod.invokeAndAggregateExceptions().get(0).getResult());
    }

    @Test
    public void testEventWith0Params(){
        Delegate.With0Params<String> delegate = new Delegate.With0Params<>();
        Event.With0Params<String> event = new Event.With0Params<>(delegate);

        //Add function to the event
        Fun.With0Params<String> fun = new DelegateWith0ParamsTest()::stringMethod;
        event.add(fun);
        DelegateInvocationResult<String> res = delegate.invokeAndAggregateExceptions();

        assertEquals("stringMethod", res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith0ParamsAndVoid(){
        Delegate.With0ParamsAndVoid delegate = new Delegate.With0ParamsAndVoid();
        Event.With0ParamsAndVoid event = new Event.With0ParamsAndVoid(delegate);

        //Add function to the event
        Fun.With0ParamsAndVoid fun = new DelegateWith0ParamsTest()::voidMethod;
        event.add(fun);
        DelegateInvocationResult<Void> res = delegate.invokeAndAggregateExceptions();

        assertEquals(null, res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith1Param(){
        Delegate.With1Param<String, String> delegate = new Delegate.With1Param<>();
        Event.With1Param<String, String> event = new Event.With1Param<>(delegate);

        //Add function to the event
        Fun.With1Param<String, String> fun = new DelegateWith1ParamTest()::stringMethod;
        event.add(fun);
        DelegateInvocationResult<String> res = delegate.invokeAndAggregateExceptions("hello world");

        assertEquals("hello world", res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith1ParamAndVoid(){
        Delegate.With1ParamAndVoid<String> delegate = new Delegate.With1ParamAndVoid<>();
        Event.With1ParamAndVoid<String> event = new Event.With1ParamAndVoid(delegate);

        //Add function to the event
        Fun.With1ParamAndVoid<String> fun = new DelegateWith1ParamTest()::voidMethod;
        event.add(fun);
        DelegateInvocationResult<Void> res = delegate.invokeAndAggregateExceptions();

        assertEquals(null, res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith2Params(){
        Delegate.With2Params<String, String, String> delegate = new Delegate.With2Params<>();
        Event.With2Params<String, String, String> event = new Event.With2Params<>(delegate);

        //Add function to the event
        Fun.With2Params<String, String, String> fun = new DelegateWith2ParamsTest()::stringMethod;
        event.add(fun);
        DelegateInvocationResult<String> res = delegate.invokeAndAggregateExceptions("hello world", "arg");

        assertEquals("hello world", res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith2ParamsAndVoid(){
        Delegate.With2ParamsAndVoid<String, String> delegate = new Delegate.With2ParamsAndVoid<>();
        Event.With2ParamsAndVoid<String, String> event = new Event.With2ParamsAndVoid(delegate);

        //Add function to the event
        Fun.With2ParamsAndVoid<String, String> fun = new DelegateWith2ParamsTest()::voidMethod;
        event.add(fun);
        DelegateInvocationResult<Void> res = delegate.invokeAndAggregateExceptions();

        assertEquals(null, res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith3Params(){
        Delegate.With3Params<String, String, String, String> delegate = new Delegate.With3Params<>();
        Event.With3Params<String, String, String, String> event = new Event.With3Params<>(delegate);

        //Add function to the event
        Fun.With3Params<String, String, String, String> fun = new DelegateWith3ParamsTest()::stringMethod;
        event.add(fun);
        DelegateInvocationResult<String> res = delegate.invokeAndAggregateExceptions("hello world", "arg", "arg");

        assertEquals("hello world", res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith3ParamsAndVoid(){
        Delegate.With3ParamsAndVoid<String, String, String> delegate = new Delegate.With3ParamsAndVoid<>();
        Event.With3ParamsAndVoid<String, String, String> event = new Event.With3ParamsAndVoid(delegate);

        //Add function to the event
        Fun.With3ParamsAndVoid<String, String, String> fun = new DelegateWith3ParamsTest()::voidMethod;
        event.add(fun);
        DelegateInvocationResult<Void> res = delegate.invokeAndAggregateExceptions();

        assertEquals(null, res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith4Params(){
        Delegate.With4Params<String, String, String, String, String> delegate = new Delegate.With4Params<>();
        Event.With4Params<String, String, String, String, String> event = new Event.With4Params<>(delegate);

        //Add function to the event
        Fun.With4Params<String, String, String, String, String> fun = new DelegateWith4ParamsTest()::stringMethod;
        event.add(fun);
        DelegateInvocationResult<String> res = delegate.invokeAndAggregateExceptions("hello world", "arg", "arg", "arg");

        assertEquals("hello world", res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith4ParamsAndVoid(){
        Delegate.With4ParamsAndVoid<String, String, String, String> delegate = new Delegate.With4ParamsAndVoid<>();
        Event.With4ParamsAndVoid<String, String, String, String> event = new Event.With4ParamsAndVoid(delegate);

        //Add function to the event
        Fun.With4ParamsAndVoid<String, String, String, String> fun = new DelegateWith4ParamsTest()::voidMethod;
        event.add(fun);
        DelegateInvocationResult<Void> res = delegate.invokeAndAggregateExceptions();

        assertEquals(null, res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith5Params(){
        Delegate.With5Params<String, String, String, String, String, String> delegate = new Delegate.With5Params<>();
        Event.With5Params<String, String, String, String, String, String> event = new Event.With5Params<>(delegate);

        //Add function to the event
        Fun.With5Params<String, String, String, String, String, String> fun = new DelegateWith5ParamsTest()::stringMethod;
        event.add(fun);
        DelegateInvocationResult<String> res = delegate.invokeAndAggregateExceptions("hello world", "arg", "arg", "arg", "arg");

        assertEquals("hello world", res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith5ParamsAndVoid(){
        Delegate.With5ParamsAndVoid<String, String, String, String, String> delegate = new Delegate.With5ParamsAndVoid<>();
        Event.With5ParamsAndVoid<String, String, String, String, String> event = new Event.With5ParamsAndVoid(delegate);

        //Add function to the event
        Fun.With5ParamsAndVoid<String, String, String, String, String> fun = new DelegateWith5ParamsTest()::voidMethod;
        event.add(fun);
        DelegateInvocationResult<Void> res = delegate.invokeAndAggregateExceptions();

        assertEquals(null, res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith6Params(){
        Delegate.With6Params<String, String, String, String, String, String, String> delegate = new Delegate.With6Params<>();
        Event.With6Params<String, String, String, String, String, String, String> event = new Event.With6Params<>(delegate);

        //Add function to the event
        Fun.With6Params<String, String, String, String, String, String, String> fun = new DelegateWith6ParamsTest()::stringMethod;
        event.add(fun);
        DelegateInvocationResult<String> res = delegate.invokeAndAggregateExceptions("hello world", "arg", "arg", "arg", "arg", "arg");

        assertEquals("hello world", res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith6ParamsAndVoid(){
        Delegate.With6ParamsAndVoid<String, String, String, String, String, String> delegate = new Delegate.With6ParamsAndVoid<>();
        Event.With6ParamsAndVoid<String, String, String, String, String, String> event = new Event.With6ParamsAndVoid(delegate);

        //Add function to the event
        Fun.With6ParamsAndVoid<String, String, String, String, String, String> fun = new DelegateWith6ParamsTest()::voidMethod;
        event.add(fun);
        DelegateInvocationResult<Void> res = delegate.invokeAndAggregateExceptions();

        assertEquals(null, res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith7Params(){
        Delegate.With7Params<String, String, String, String, String, String, String, String> delegate = new Delegate.With7Params<>();
        Event.With7Params<String, String, String, String, String, String, String, String> event = new Event.With7Params<>(delegate);

        //Add function to the event
        Fun.With7Params<String, String, String, String, String, String, String, String> fun = new DelegateWith7ParamsTest()::stringMethod;
        event.add(fun);
        DelegateInvocationResult<String> res = delegate.invokeAndAggregateExceptions("hello world", "arg", "arg", "arg", "arg", "arg", "arg");

        assertEquals("hello world", res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith7ParamsAndVoid(){
        Delegate.With7ParamsAndVoid<String, String, String, String, String, String, String> delegate = new Delegate.With7ParamsAndVoid<>();
        Event.With7ParamsAndVoid<String, String, String, String, String, String, String> event = new Event.With7ParamsAndVoid(delegate);

        //Add function to the event
        Fun.With7ParamsAndVoid<String, String, String, String, String, String, String> fun = new DelegateWith7ParamsTest()::voidMethod;
        event.add(fun);
        DelegateInvocationResult<Void> res = delegate.invokeAndAggregateExceptions();

        assertEquals(null, res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith8Params(){
        Delegate.With8Params<String, String, String, String, String, String, String, String, String> delegate = new Delegate.With8Params<>();
        Event.With8Params<String, String, String, String, String, String, String, String, String> event = new Event.With8Params<>(delegate);

        //Add function to the event
        Fun.With8Params<String, String, String, String, String, String, String, String, String> fun = new DelegateWith8ParamsTest()::stringMethod;
        event.add(fun);
        DelegateInvocationResult<String> res = delegate.invokeAndAggregateExceptions("hello world", "arg", "arg", "arg", "arg", "arg", "arg", "arg");

        assertEquals("hello world", res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith8ParamsAndVoid(){
        Delegate.With8ParamsAndVoid<String, String, String, String, String, String, String, String> delegate = new Delegate.With8ParamsAndVoid<>();
        Event.With8ParamsAndVoid<String, String, String, String, String, String, String, String> event = new Event.With8ParamsAndVoid(delegate);

        //Add function to the event
        Fun.With8ParamsAndVoid<String, String, String, String, String, String, String, String> fun = new DelegateWith8ParamsTest()::voidMethod;
        event.add(fun);
        DelegateInvocationResult<Void> res = delegate.invokeAndAggregateExceptions();

        assertEquals(null, res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith9Params(){
        Delegate.With9Params<String, String, String, String, String, String, String, String, String, String> delegate = new Delegate.With9Params<>();
        Event.With9Params<String, String, String, String, String, String, String, String, String, String> event = new Event.With9Params<>(delegate);

        //Add function to the event
        Fun.With9Params<String, String, String, String, String, String, String, String, String, String> fun = new DelegateWith9ParamsTest()::stringMethod;
        event.add(fun);
        DelegateInvocationResult<String> res = delegate.invokeAndAggregateExceptions("hello world", "arg", "arg", "arg", "arg", "arg", "arg", "arg", "arg");

        assertEquals("hello world", res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

    @Test
    public void testEventWith9ParamsAndVoid(){
        Delegate.With9ParamsAndVoid<String, String, String, String, String, String, String, String, String> delegate = new Delegate.With9ParamsAndVoid<>();
        Event.With9ParamsAndVoid<String, String, String, String, String, String, String, String, String> event = new Event.With9ParamsAndVoid(delegate);

        //Add function to the event
        Fun.With9ParamsAndVoid<String, String, String, String, String, String, String, String, String> fun = new DelegateWith9ParamsTest()::voidMethod;
        event.add(fun);
        DelegateInvocationResult<Void> res = delegate.invokeAndAggregateExceptions();

        assertEquals(null, res.get(0).getResult());

        //Remove function from the event
        event.remove(fun);
        res = delegate.invokeAndAggregateExceptions();

        assertTrue(res.getFunctionInvocationResults().isEmpty());
    }

}
