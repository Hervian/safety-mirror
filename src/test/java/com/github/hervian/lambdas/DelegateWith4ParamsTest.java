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
public class DelegateWith4ParamsTest extends AbstractDelegateTest {

    @Mock
    private DelegateWith4ParamsTest tester;

    public void voidMethod(String str1, String str2, String str3, String str4) {
        System.out.println(str1.length());
    }

    public String stringMethod(String str, String str2, String str3, String str4) {
        return str;
    }

    public int intMethod(int i, int i2, int i3, int i4) {
        return i - 1;
    }

    public Integer integerMethod(Integer i, Integer i2, Integer i3, Integer i4) {
        return Integer.valueOf(8);
    }

    public double doubleMethod(double d, double d2, double d3, double d4) {
        return d;
    }

    public DelegateWith4ParamsTest returnDelegate(DelegateWith4ParamsTest delegate, DelegateWith4ParamsTest delegate2, DelegateWith4ParamsTest delegate3, DelegateWith4ParamsTest delegate4) {
        return delegate;
    }

    public Object objectMethod(Object o, Object o2, Object o3, Object o4) {
        return o;
    }

    @Override
    protected DelegateType getDelegateType() {
        return DelegateType.With4Params;
    }


    @Test
    public void testVoidMethod() throws Exception {
        assertNotNull(tester);
        Delegate.With4ParamsAndVoid<String, String, String, String> delegate = new Delegate.With4ParamsAndVoid<>(tester::voidMethod);
        DelegateInvocationResult<Void> res = delegate.invoke("hello world", "arg2", "arg3", "arg4");
        assertNotNull(res);
        verify(tester, times(1)).voidMethod(any(), any(), any(), any());

        //Any return type will match the *AndVoid method / the void return type:
        Delegate.With4ParamsAndVoid delegateInt = new Delegate.With4ParamsAndVoid<>(this::intMethod);
        Delegate.With4ParamsAndVoid delegateObject = new Delegate.With4ParamsAndVoid<>(this::returnDelegate);
    }

    @Test
    public void testStringMethod() throws Exception {
        Delegate.With4Params<String, String, String, String, String> delegate = new Delegate.With4Params<>(this::stringMethod);

        DelegateInvocationResult<String> res = delegate.invoke("hello world!", "arg2", "arg3", "arg4");
        assertEquals("hello world!", res.get(0).getResult());

        String delegateString = "Delegate.With4Params<String, String, String, String, String>";
        compile(delegateString, "this::stringMethod", "this::voidMethod");
        compile(delegateString, "this::stringMethod", "this::returnDelegate");
    }

    @Test
    public void testIntMethod() throws Exception {
        Delegate.With4Params<Integer, Integer, Integer, Integer, Integer> delegate = new Delegate.With4Params<>(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke(7, 7, 7, 7);
        assertEquals((Integer) 6, res.get(0).getResult());

        String delegateString = "Delegate.With4Params<Integer, Integer, Integer, Integer, Integer>";
        compile(delegateString, "this::intMethod", "this::voidMethod");
        compile(delegateString, "this::intMethod", "this::returnDelegate");
        compile(delegateString, "this::intMethod", "this::doubleMethod");
    }

    @Test
    public void testIntegerMethod() throws Exception {
        Delegate.With4Params<Integer, Integer, Integer, Integer, Integer> delegate = new Delegate.With4Params<>(this::integerMethod);
        delegate.add(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke(8, 8, 8, 8);
        assertEquals((Integer) 8, res.get(0).getResult());
        assertEquals((Integer) 7, res.get(1).getResult());

        String delegateString = "Delegate.With4Params<Integer, Integer, Integer, Integer, Integer>";
        compile(delegateString, "this::integerMethod", "this::voidMethod");
        compile(delegateString, "this::integerMethod", "this::returnDelegate");
        compile(delegateString, "this::integerMethod", "this::doubleMethod");
    }

    @Test
    public void testDoubleMethod() throws Exception {
        Delegate.With4Params<Double, Double, Double, Double, Double> delegate = new Delegate.With4Params<>(this::doubleMethod);

        DelegateInvocationResult<Double> res = delegate.invoke(9d, 9d, 9d, 9d);
        assertEquals((Double) 9d, res.get(0).getResult());

        String delegateString = "Delegate.With4Params<Double, Double, Double, Double, Double>";
        compile(delegateString, "this::doubleMethod", "this::voidMethod");
        compile(delegateString, "this::doubleMethod", "this::returnDelegate");
        compile(delegateString, "this::doubleMethod", "this::integerMethod");
        compile(delegateString, "this::doubleMethod", "this::intMethod");
    }

    @Test
    public void testObjectMethod() throws Exception {
        Delegate.With4Params<Object, DelegateWith4ParamsTest, DelegateWith4ParamsTest, DelegateWith4ParamsTest, DelegateWith4ParamsTest> delegate = new Delegate.With4Params<>(this::objectMethod);
        delegate.add(this::returnDelegate);
        DelegateInvocationResult<Object> res2 = delegate.invoke(this, this, this, this);
        assertTrue(AbstractDelegateTest.class.isAssignableFrom(res2.get(0).getResult().getClass()));
        assertEquals(DelegateWith4ParamsTest.class, res2.get(1).getResult().getClass());

        String delegateString = "Delegate.With4Params<Object, DelegateWith4ParamsTest, DelegateWith4ParamsTest, DelegateWith4ParamsTest, DelegateWith4ParamsTest>";
        compile(delegateString, "this::returnDelegate", "this::voidMethod");
        compile(delegateString, "this::returnDelegate", "this::integerMethod");
        compile(delegateString, "this::returnDelegate", "this::stringMethod");


    }

}