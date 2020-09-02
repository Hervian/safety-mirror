package com.github.hervian.reflection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DelegateInvocationTest {

    private String throwExceptionMethod(){throw new RuntimeException();}
    private String stringMethod(){return "hello world";}

    @Test
    public void testInvokeAndAggregate(){
        Delegate.With0Params<String> getStringDelegate = new Delegate.With0Params<>();
        getStringDelegate.add(this::throwExceptionMethod, this::stringMethod);

        DelegateInvocationResult<String> res = invokeAndAggregate(getStringDelegate);
        assertTrue(res.get(0).exceptionThrown());
        assertEquals("hello world", res.getFunctionInvocationResults().get(1).getResult());
    }

    @Test
    public void testInvoke(){
        Delegate.With0Params<String> getStringDelegate = new Delegate.With0Params<>();
        getStringDelegate.add(this::throwExceptionMethod, this::stringMethod);

        try {
            invoke(getStringDelegate);
            fail();
        } catch (Exception e){
            assertTrue(true);
        }
    }

    private DelegateInvocationResult<String> invokeAndAggregate(Delegate.With0Params<String> delegate_returnString){
        return delegate_returnString.invokeAndAggregateExceptions();
    }

    private DelegateInvocationResult<String> invoke(Delegate.With0Params<String> delegate_returnString) throws Exception {
        return delegate_returnString.invoke();
    }

}
