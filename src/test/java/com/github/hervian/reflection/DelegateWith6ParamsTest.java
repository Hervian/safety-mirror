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
public class DelegateWith6ParamsTest extends AbstractDelegateTest {

    @Mock
    private DelegateWith6ParamsTest tester;

    public void voidMethod(String str1, String str2, String str3, String str4, String str5, String str6) {
        System.out.println(str1.length());
    }

    public String stringMethod(String str, String str2, String str3, String str4, String str5, String str6) {
        return str;
    }

    public int intMethod(int i, int i2, int i3, int i4, int i5, int i6) {
        return i - 1;
    }

    public Integer integerMethod(Integer i, Integer i2, Integer i3, Integer i4, Integer i5, Integer i6) {
        return Integer.valueOf(8);
    }

    public double doubleMethod(double d, double d2, double d3, double d4, double d5, double d6) {
        return d;
    }

    public DelegateWith6ParamsTest returnDelegate(DelegateWith6ParamsTest delegate, DelegateWith6ParamsTest delegate2, DelegateWith6ParamsTest delegate3, DelegateWith6ParamsTest delegate4, DelegateWith6ParamsTest delegate5, DelegateWith6ParamsTest delegate6) {
        return delegate;
    }

    public Object objectMethod(Object o, Object o2, Object o3, Object o4, Object o5, Object o6) {
        return o;
    }

    @Override
    protected DelegateType getDelegateType() {
        return DelegateType.With6Params;
    }


    @Test
    public void testVoidMethod() throws Exception {
        assertNotNull(tester);
        Delegate.With6ParamsAndVoid<String, String, String, String, String, String> delegate = new Delegate.With6ParamsAndVoid<>(tester::voidMethod);
        DelegateInvocationResult<Void> res = delegate.invoke("hello world", "arg", "arg", "arg", "arg", "arg");
        assertNotNull(res);
        verify(tester, times(1)).voidMethod(any(), any(), any(), any(), any(), any());

        //Any return type will match the *AndVoid method / the void return type:
        Delegate.With6ParamsAndVoid delegateInt = new Delegate.With6ParamsAndVoid<>(this::intMethod);
        Delegate.With6ParamsAndVoid delegateObject = new Delegate.With6ParamsAndVoid<>(this::returnDelegate);
    }

    @Test
    public void testStringMethod() throws Exception {
        Delegate.With6Params<String, String, String, String, String, String, String> delegate = new Delegate.With6Params<>(this::stringMethod);

        DelegateInvocationResult<String> res = delegate.invoke("hello world!", "arg", "arg", "arg", "arg", "arg", "arg");
        assertEquals("hello world!", res.get(0).getResult());

        String delegateString = "Delegate.With6Params<String, String, String, String, String, String, String>";
        compile(delegateString, "this::stringMethod", "this::voidMethod");
        compile(delegateString, "this::stringMethod", "this::returnDelegate");
    }

    @Test
    public void testIntMethod() throws Exception {
        Delegate.With6Params<Integer, Integer, Integer, Integer, Integer, Integer, Integer> delegate = new Delegate.With6Params<>(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke(7, 7, 7, 7, 7, 7);
        assertEquals((Integer) 6, res.get(0).getResult());

        String delegateString = "Delegate.With6Params<Integer, Integer, Integer, Integer, Integer, Integer, Integer>";
        compile(delegateString, "this::intMethod", "this::voidMethod");
        compile(delegateString, "this::intMethod", "this::returnDelegate");
        compile(delegateString, "this::intMethod", "this::doubleMethod");
    }

    @Test
    public void testIntegerMethod() throws Exception {
        Delegate.With6Params<Integer, Integer, Integer, Integer, Integer, Integer, Integer> delegate = new Delegate.With6Params<>(this::integerMethod);
        delegate.add(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke(8, 8, 8, 8, 8, 8);
        assertEquals((Integer) 8, res.get(0).getResult());
        assertEquals((Integer) 7, res.get(1).getResult());

        String delegateString = "Delegate.With6Params<Integer, Integer, Integer, Integer, Integer, Integer, Integer>";
        compile(delegateString, "this::integerMethod", "this::voidMethod");
        compile(delegateString, "this::integerMethod", "this::returnDelegate");
        compile(delegateString, "this::integerMethod", "this::doubleMethod");
    }

    @Test
    public void testDoubleMethod() throws Exception {
        Delegate.With6Params<Double, Double, Double, Double, Double, Double, Double> delegate = new Delegate.With6Params<>(this::doubleMethod);

        DelegateInvocationResult<Double> res = delegate.invoke(9d, 9d, 9d, 9d, 9d, 9d);
        assertEquals((Double) 9d, res.get(0).getResult());

        String delegateString = "Delegate.With6Params<Double, Double, Double, Double, Double, Double, Double>";
        compile(delegateString, "this::doubleMethod", "this::voidMethod");
        compile(delegateString, "this::doubleMethod", "this::returnDelegate");
        compile(delegateString, "this::doubleMethod", "this::integerMethod");
        compile(delegateString, "this::doubleMethod", "this::intMethod");
    }

    @Test
    public void testObjectMethod() throws Exception {
        Delegate.With6Params<Object, DelegateWith6ParamsTest, DelegateWith6ParamsTest, DelegateWith6ParamsTest, DelegateWith6ParamsTest, DelegateWith6ParamsTest, DelegateWith6ParamsTest> delegate = new Delegate.With6Params<>(this::objectMethod);
        delegate.add(this::returnDelegate);
        DelegateInvocationResult<Object> res2 = delegate.invoke(this, this, this, this, this, this);
        assertTrue(AbstractDelegateTest.class.isAssignableFrom(res2.get(0).getResult().getClass()));
        assertEquals(DelegateWith6ParamsTest.class, res2.get(1).getResult().getClass());

        String delegateString = "Delegate.With6Params<Object, DelegateWith6ParamsTest, DelegateWith6ParamsTest, DelegateWith6ParamsTest, DelegateWith6ParamsTest, DelegateWith6ParamsTest, DelegateWith6ParamsTest>";
        compile(delegateString, "this::returnDelegate", "this::voidMethod");
        compile(delegateString, "this::returnDelegate", "this::integerMethod");
        compile(delegateString, "this::returnDelegate", "this::stringMethod");


    }

}