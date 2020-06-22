package com.github.hervian.lambdas;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

}
