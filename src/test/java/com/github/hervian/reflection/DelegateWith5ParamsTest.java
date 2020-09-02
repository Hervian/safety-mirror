package com.github.hervian.reflection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DelegateWith5ParamsTest extends AbstractDelegateTest {

    @Mock
    private DelegateWith5ParamsTest tester;

    public void voidMethod(String str1, String str2, String str3, String str4, String str5) {
        System.out.println(str1.length());
    }

    public String stringMethod(String str, String str2, String str3, String str4, String str5) {
        return str;
    }

    public int intMethod(int i, int i2, int i3, int i4, int i5) {
        return i - 1;
    }

    public Integer integerMethod(Integer i, Integer i2, Integer i3, Integer i4, Integer i5) {
        return Integer.valueOf(8);
    }

    public double doubleMethod(double d, double d2, double d3, double d4, double d5) {
        return d;
    }

    public DelegateWith5ParamsTest returnDelegate(DelegateWith5ParamsTest delegate, DelegateWith5ParamsTest delegate2, DelegateWith5ParamsTest delegate3, DelegateWith5ParamsTest delegate4, DelegateWith5ParamsTest delegate5) {
        return delegate;
    }

    public Object objectMethod(Object o, Object o2, Object o3, Object o4, Object o5) {
        return o;
    }

    @Override
    protected DelegateType getDelegateType() {
        return DelegateType.With5Params;
    }


    @Test
    public void testVoidMethod() throws Exception {
        assertNotNull(tester);
        Delegate.With5ParamsAndVoid<String, String, String, String, String> delegate = new Delegate.With5ParamsAndVoid<>(tester::voidMethod);
        DelegateInvocationResult<Void> res = delegate.invoke("hello world", "arg2", "arg3", "arg4", "arg5");
        assertNotNull(res);
        verify(tester, times(1)).voidMethod(any(), any(), any(), any(), any());

        //Any return type will match the *AndVoid method / the void return type:
        Delegate.With5ParamsAndVoid delegateInt = new Delegate.With5ParamsAndVoid<>(this::intMethod);
        Delegate.With5ParamsAndVoid delegateObject = new Delegate.With5ParamsAndVoid<>(this::returnDelegate);
    }

    @Test
    public void testStringMethod() throws Exception {
        Delegate.With5Params<String, String, String, String, String, String> delegate = new Delegate.With5Params<>(this::stringMethod);

        DelegateInvocationResult<String> res = delegate.invoke("hello world!", "arg2", "arg3", "arg4", "arg5");
        assertEquals("hello world!", res.get(0).getResult());

        String delegateString = "Delegate.With5Params<String, String, String, String, String, String>";
        compile(delegateString, "this::stringMethod", "this::voidMethod");
        compile(delegateString, "this::stringMethod", "this::returnDelegate");
    }

    @Test
    public void testIntMethod() throws Exception {
        Delegate.With5Params<Integer, Integer, Integer, Integer, Integer, Integer> delegate = new Delegate.With5Params<>(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke(7, 7, 7, 7, 7);
        assertEquals((Integer) 6, res.get(0).getResult());

        String delegateString = "Delegate.With5Params<Integer, Integer, Integer, Integer, Integer, Integer>";
        compile(delegateString, "this::intMethod", "this::voidMethod");
        compile(delegateString, "this::intMethod", "this::returnDelegate");
        compile(delegateString, "this::intMethod", "this::doubleMethod");
    }

    @Test
    public void testIntegerMethod() throws Exception {
        Delegate.With5Params<Integer, Integer, Integer, Integer, Integer, Integer> delegate = new Delegate.With5Params<>(this::integerMethod);
        delegate.add(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke(8, 8, 8, 8, 8);
        assertEquals((Integer) 8, res.get(0).getResult());
        assertEquals((Integer) 7, res.get(1).getResult());

        String delegateString = "Delegate.With5Params<Integer, Integer, Integer, Integer, Integer, Integer>";
        compile(delegateString, "this::integerMethod", "this::voidMethod");
        compile(delegateString, "this::integerMethod", "this::returnDelegate");
        compile(delegateString, "this::integerMethod", "this::doubleMethod");
    }

    @Test
    public void testDoubleMethod() throws Exception {
        Delegate.With5Params<Double, Double, Double, Double, Double, Double> delegate = new Delegate.With5Params<>(this::doubleMethod);

        DelegateInvocationResult<Double> res = delegate.invoke(9d, 9d, 9d, 9d, 9d);
        assertEquals((Double) 9d, res.get(0).getResult());

        String delegateString = "Delegate.With5Params<Double, Double, Double, Double, Double, Double>";
        compile(delegateString, "this::doubleMethod", "this::voidMethod");
        compile(delegateString, "this::doubleMethod", "this::returnDelegate");
        compile(delegateString, "this::doubleMethod", "this::integerMethod");
        compile(delegateString, "this::doubleMethod", "this::intMethod");
    }

    @Test
    public void testObjectMethod() throws Exception {
        Delegate.With5Params<Object, DelegateWith5ParamsTest, DelegateWith5ParamsTest, DelegateWith5ParamsTest, DelegateWith5ParamsTest, DelegateWith5ParamsTest> delegate = new Delegate.With5Params<>(this::objectMethod);
        delegate.add(this::returnDelegate);
        DelegateInvocationResult<Object> res2 = delegate.invoke(this, this, this, this, this);
        assertTrue(AbstractDelegateTest.class.isAssignableFrom(res2.get(0).getResult().getClass()));
        assertEquals(DelegateWith5ParamsTest.class, res2.get(1).getResult().getClass());

        String delegateString = "Delegate.With5Params<Object, DelegateWith5ParamsTest, DelegateWith5ParamsTest, DelegateWith5ParamsTest, DelegateWith5ParamsTest, DelegateWith5ParamsTest>";
        compile(delegateString, "this::returnDelegate", "this::voidMethod");
        compile(delegateString, "this::returnDelegate", "this::integerMethod");
        compile(delegateString, "this::returnDelegate", "this::stringMethod");


    }

}