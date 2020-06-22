package com.github.hervian.lambdas;

import org.junit.Test;
import static org.junit.Assert.*;

public class EventSubscriberTest {

    @Test
    public void testSubscribeToEvents(){
        Fun.With0Params<String> returnStringFunction = this::returnSomeString;

        EventProducerTest.trimEvent.add(returnStringFunction);
        DelegateInvocationResult<String> res = EventProducerTest.requestInvokationOfTrimEvent();
        assertEquals("some string", res.get(0).getResult());

        EventProducerTest.trimEvent.remove(returnStringFunction);
        res = EventProducerTest.requestInvokationOfTrimEvent();
        assertTrue(res.getFunctionInvocationResults().isEmpty());

        EventProducerTest.trimEvent.add(this::returnSomeString);
        res = EventProducerTest.requestInvokationOfTrimEvent();
        assertEquals("some string", res.get(0).getResult());

        EventProducerTest.trimEvent.remove(this::returnSomeString);
        res = EventProducerTest.requestInvokationOfTrimEvent();
        assertFalse(res.getFunctionInvocationResults().isEmpty()); //Notice that the event/lambda was not removed - the lambdas are not equal. This is just how it is. You must Fun(ction) field and add that in order to be able to remove it later on.
    }

    public String returnSomeString(){
        return "some string";
    }

}
