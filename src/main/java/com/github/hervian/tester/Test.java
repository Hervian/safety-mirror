package com.github.hervian.tester;

import com.github.hervian.lambdas.Delegate;
import com.github.hervian.lambdas.Event;

public class Test {

    //@Event
    @lombok.experimental.Delegate(excludes = Delegate.IDelegator.class)
    Delegate.With0Params<String> delegate = new Delegate.With0Params(); //*private* Delegate: Only this class can invoke the Delegate



    public Event.With0Params<String> event = new Event.With0Params(delegate); // *public* Event: Subscriber classes can add and remove Events but they can't invoke or reassign the underlying Delegate.

    private void foo(){
        /*Delegate.With0Params.Void delegateQuestion = Delegate.with0Params(Test::foo);
        event.*/
    }
}
