package com.github.hervian.lambdas;

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
public class DelegateWith9ParamsTest extends AbstractDelegateTest {

    @Mock
    private DelegateWith9ParamsTest tester;

    public void voidMethod(String str1, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        System.out.println(str1.length());
    }

    public String stringMethod(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9) {
        return str;
    }

    public int intMethod(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) {
        return i - 1;
    }

    public Integer integerMethod(Integer i, Integer i2, Integer i3, Integer i4, Integer i5, Integer i6, Integer i7, Integer i8, Integer i9) {
        return Integer.valueOf(8);
    }

    public double doubleMethod(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        return d;
    }

    public DelegateWith9ParamsTest returnDelegate(DelegateWith9ParamsTest delegate, DelegateWith9ParamsTest delegate2, DelegateWith9ParamsTest delegate3, DelegateWith9ParamsTest delegate4, DelegateWith9ParamsTest delegate5, DelegateWith9ParamsTest delegate6, DelegateWith9ParamsTest delegate7, DelegateWith9ParamsTest delegate8, DelegateWith9ParamsTest delegate9) {
        return delegate;
    }

    public Object objectMethod(Object o, Object o2, Object o3, Object o4, Object o5, Object o6, Object o7, Object o8, Object o9) {
        return o;
    }

    @Override
    protected DelegateType getDelegateType() {
        return DelegateType.With9Params;
    }


    @Test
    public void testVoidMethod() throws Exception {
        assertNotNull(tester);
        Delegate.With9ParamsAndVoid<String, String, String, String, String, String, String, String, String> delegate = new Delegate.With9ParamsAndVoid<>(tester::voidMethod);
        DelegateInvocationResult<Void> res = delegate.invoke("hello world", "arg", "arg", "arg", "arg", "arg", "arg", "arg", "arg");
        assertNotNull(res);
        verify(tester, times(1)).voidMethod(any(), any(), any(), any(), any(), any(), any(), any(), any());

        //Any return type will match the *AndVoid method / the void return type:
        Delegate.With9ParamsAndVoid delegateInt = new Delegate.With9ParamsAndVoid<>(this::intMethod);
        Delegate.With9ParamsAndVoid delegateObject = new Delegate.With9ParamsAndVoid<>(this::returnDelegate);
    }

    @Test
    public void testStringMethod() throws Exception {
        Delegate.With9Params<String, String, String, String, String, String, String, String, String, String> delegate = new Delegate.With9Params<>(this::stringMethod);

        DelegateInvocationResult<String> res = delegate.invoke("hello world!", "arg", "arg", "arg", "arg", "arg", "arg", "arg", "arg", "arg");
        assertEquals("hello world!", res.get(0).getResult());

        String delegateString = "Delegate.With9Params<String, String, String, String, String, String, String, String, String, String>";
        compile(delegateString, "this::stringMethod", "this::voidMethod");
        compile(delegateString, "this::stringMethod", "this::returnDelegate");
    }

    @Test
    public void testIntMethod() throws Exception {
        Delegate.With9Params<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> delegate = new Delegate.With9Params<>(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke(7, 7, 7, 7, 7, 7, 7, 7, 7);
        assertEquals((Integer) 6, res.get(0).getResult());

        String delegateString = "Delegate.With9Params<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>";
        compile(delegateString, "this::intMethod", "this::voidMethod");
        compile(delegateString, "this::intMethod", "this::returnDelegate");
        compile(delegateString, "this::intMethod", "this::doubleMethod");
    }

    @Test
    public void testIntegerMethod() throws Exception {
        Delegate.With9Params<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer> delegate = new Delegate.With9Params<>(this::integerMethod);
        delegate.add(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke(8, 8, 8, 8, 8, 8, 8, 8, 8);
        assertEquals((Integer) 8, res.get(0).getResult());
        assertEquals((Integer) 7, res.get(1).getResult());

        String delegateString = "Delegate.With9Params<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>";
        compile(delegateString, "this::integerMethod", "this::voidMethod");
        compile(delegateString, "this::integerMethod", "this::returnDelegate");
        compile(delegateString, "this::integerMethod", "this::doubleMethod");
    }

    @Test
    public void testDoubleMethod() throws Exception {
        Delegate.With9Params<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double> delegate = new Delegate.With9Params<>(this::doubleMethod);

        DelegateInvocationResult<Double> res = delegate.invoke(9d, 9d, 9d, 9d, 9d, 9d, 9d, 9d, 9d);
        assertEquals((Double) 9d, res.get(0).getResult());

        String delegateString = "Delegate.With9Params<Double, Double, Double, Double, Double, Double, Double, Double, Double, Double>";
        compile(delegateString, "this::doubleMethod", "this::voidMethod");
        compile(delegateString, "this::doubleMethod", "this::returnDelegate");
        compile(delegateString, "this::doubleMethod", "this::integerMethod");
        compile(delegateString, "this::doubleMethod", "this::intMethod");
    }

    @Test
    public void testObjectMethod() throws Exception {
        Delegate.With9Params<Object, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest> delegate = new Delegate.With9Params<>(this::objectMethod);
        delegate.add(this::returnDelegate);
        DelegateInvocationResult<Object> res2 = delegate.invoke(this, this, this, this, this, this, this, this, this);
        assertTrue(AbstractDelegateTest.class.isAssignableFrom(res2.get(0).getResult().getClass()));
        assertEquals(DelegateWith9ParamsTest.class, res2.get(1).getResult().getClass());

        String delegateString = "Delegate.With9Params<Object, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest, DelegateWith9ParamsTest>";
        compile(delegateString, "this::returnDelegate", "this::voidMethod");
        compile(delegateString, "this::returnDelegate", "this::integerMethod");
        compile(delegateString, "this::returnDelegate", "this::stringMethod");


    }

}