package com.github.hervian.lambdas;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DelegateWith0ParamsTest extends AbstractDelegateTest {

    public void voidMethod(){}
    public String stringMethod(){return "stringMethod";}
    public int intMethod(){return 7;}
    public Integer integerMethod(){return Integer.valueOf(8);}
    public double doubleMethod(){return 9d;}
    public DelegateWith0ParamsTest returnDelegateWith0ParamsTest(){return this;}
    public Object objectMethod(){return new Object();}

    @Test
    public void testVoidMethod() throws Exception {
        Delegate.With0ParamsAndVoid delegate = new Delegate.With0ParamsAndVoid(this::voidMethod);
        DelegateInvocationResult<Void> res = delegate.invoke();

        //Any return type will match the *AndVoid method / the void return type:
        Delegate.With0ParamsAndVoid delegateInt = new Delegate.With0ParamsAndVoid(this::intMethod);
        Delegate.With0ParamsAndVoid delegateObject = new Delegate.With0ParamsAndVoid(this::returnDelegateWith0ParamsTest);
    }

    @Test
    public void testStringMethod() throws Exception {
        Delegate.With0Params<String> delegate = new Delegate.With0Params<>(this::stringMethod);
        delegate.add("  hello world  " ::trim);

        DelegateInvocationResult<String> res = delegate.invoke();
        assertEquals("stringMethod", res.get(0).getResult());
        assertEquals("hello world", res.get(1).getResult());

        compile("Delegate.With0Params<String>", "this::stringMethod", "this::voidMethod");
        compile("Delegate.With0Params<String>", "this::stringMethod", "this::returnDelegateWith0ParamsTest");
    }

    @Test
    public void testIntMethod() throws Exception {
        Delegate.With0Params<Integer> delegate = new Delegate.With0Params<>(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke();
        assertEquals((Integer)7, res.get(0).getResult());

        compile("Delegate.With0Params<Integer>", "this::intMethod", "this::voidMethod");
        compile("Delegate.With0Params<Integer>", "this::intMethod", "this::returnDelegateWith0ParamsTest");
        compile("Delegate.With0Params<Integer>", "this::intMethod", "this::doubleMethod");
    }

    @Test
    public void testIntegerMethod() throws Exception {
        Delegate.With0Params<Integer> delegate = new Delegate.With0Params<>(this::integerMethod);
        delegate.add(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke();
        assertEquals((Integer)8, res.get(0).getResult());
        assertEquals((Integer)7, res.get(1).getResult());

        compile("Delegate.With0Params<Integer>", "this::integerMethod", "this::voidMethod");
        compile("Delegate.With0Params<Integer>", "this::integerMethod", "this::returnDelegateWith0ParamsTest");
        compile("Delegate.With0Params<Integer>", "this::integerMethod", "this::doubleMethod");
    }

    @Test
    public void testObjectMethod() throws Exception {
        Delegate.With0Params<DelegateWith0ParamsTest> delegate = new Delegate.With0Params<>(this::returnDelegateWith0ParamsTest);

        DelegateInvocationResult<DelegateWith0ParamsTest> res = delegate.invoke();
        assertEquals(DelegateWith0ParamsTest.class, res.get(0).getResult().getClass());

        compile("Delegate.With0Params<DelegateWith0ParamsTest>", "this::returnDelegateWith0ParamsTest", "this::voidMethod");
        compile("Delegate.With0Params<DelegateWith0ParamsTest>", "this::returnDelegateWith0ParamsTest", "this::integerMethod");
        compile("Delegate.With0Params<DelegateWith0ParamsTest>", "this::returnDelegateWith0ParamsTest", "this::objectMethod");
        compile("Delegate.With0Params<DelegateWith0ParamsTest>", "this::returnDelegateWith0ParamsTest", "this::stringMethod");

        Delegate.With0Params<Object> delegate2 = new Delegate.With0Params<>(this::objectMethod);
        delegate2.add(this::returnDelegateWith0ParamsTest);
        DelegateInvocationResult<Object> res2 = delegate2.invoke();
        assertEquals(Object.class, res2.get(0).getResult().getClass());
        assertEquals(DelegateWith0ParamsTest.class, res2.get(1).getResult().getClass());
    }

    @Test
    public void testDoubleMethod() throws Exception {
        Delegate.With0Params<Double> delegate = new Delegate.With0Params<>(this::doubleMethod);

        DelegateInvocationResult<Double> res = delegate.invoke();
        assertEquals((Double)9d, res.get(0).getResult());

        compile("Delegate.With0Params<Double>", "this::doubleMethod", "this::voidMethod");
        compile("Delegate.With0Params<Double>", "this::doubleMethod", "this::returnDelegateWith0ParamsTest");
        compile("Delegate.With0Params<Double>", "this::doubleMethod", "this::integerMethod");
        compile("Delegate.With0Params<Double>", "this::doubleMethod", "this::intMethod");
    }

    @Test
    public void deleteme(){
        Fun.With0Params<String> myFunction = "   hello world  "::trim;
     Delegate.With0Params myDelegate = new Delegate.With0Params(myFunction);
     myDelegate.remove(myFunction);
    }

    @Override
    protected DelegateType getDelegateType() {
        return DelegateType.With0Params;
    }


}
