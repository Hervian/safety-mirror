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
public class DelegateWith2ParamsTest  extends AbstractDelegateTest {

    @Mock
    private DelegateWith2ParamsTest tester;

    public void voidMethod(String str1, String str2){
        System.out.println(str1.length());
    }
    public String stringMethod(String str, String str2){return str;}
    public int intMethod(int i, int i2){return i-1;}
    public Integer integerMethod(Integer i, Integer i2){return Integer.valueOf(8);}
    public double doubleMethod(double d, double d2){return d;}
    public DelegateWith2ParamsTest returnDelegate(DelegateWith2ParamsTest delegate, DelegateWith2ParamsTest delegate2){return delegate;}
    public Object objectMethod(Object o, Object o2){return o;}

    @Override
    protected DelegateType getDelegateType() {
        return DelegateType.With2Params;
    }


    @Test
    public void testVoidMethod() throws Exception {
        assertNotNull(tester);
        Delegate.With2ParamsAndVoid<String, String> delegate = new Delegate.With2ParamsAndVoid<>(tester::voidMethod);
        DelegateInvocationResult<Void> res = delegate.invoke("hello world", "arg2");
        assertNotNull(res);
        verify(tester, times(1)).voidMethod(any(), any());

        //Any return type will match the *AndVoid method / the void return type:
        Delegate.With2ParamsAndVoid delegateInt = new Delegate.With2ParamsAndVoid<>(this::intMethod);
        Delegate.With2ParamsAndVoid delegateObject = new Delegate.With2ParamsAndVoid<>(this::returnDelegate);
    }

    @Test
    public void testStringMethod() throws Exception {
        Delegate.With2Params<String, String, String> delegate = new Delegate.With2Params<>(this::stringMethod);

        DelegateInvocationResult<String> res = delegate.invoke("hello world!", "arg2");
        assertEquals("hello world!", res.get(0).getResult());

        String delegateString = "Delegate.With2Params<String, String, String>";
        compile(delegateString, "this::stringMethod", "this::voidMethod");
        compile(delegateString, "this::stringMethod", "this::returnDelegate");
    }

    @Test
    public void testIntMethod() throws Exception {
        Delegate.With2Params<Integer, Integer, Integer> delegate = new Delegate.With2Params<>(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke(7, 7);
        assertEquals((Integer)6, res.get(0).getResult());

        String delegateString = "Delegate.With2Params<Integer, Integer, Integer>";
        compile(delegateString, "this::intMethod", "this::voidMethod");
        compile(delegateString, "this::intMethod", "this::returnDelegate");
        compile(delegateString, "this::intMethod", "this::doubleMethod");
    }

    @Test
    public void testIntegerMethod() throws Exception {
        Delegate.With2Params<Integer, Integer, Integer> delegate = new Delegate.With2Params<>(this::integerMethod);
        delegate.add(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke(8, 8);
        assertEquals((Integer)8, res.get(0).getResult());
        assertEquals((Integer)7, res.get(1).getResult());

        String delegateString = "Delegate.With2Params<Integer, Integer, Integer>";
        compile(delegateString, "this::integerMethod", "this::voidMethod");
        compile(delegateString, "this::integerMethod", "this::returnDelegate");
        compile(delegateString, "this::integerMethod", "this::doubleMethod");
    }

    @Test
    public void testDoubleMethod() throws Exception {
        Delegate.With2Params<Double, Double, Double> delegate = new Delegate.With2Params<>(this::doubleMethod);

        DelegateInvocationResult<Double> res = delegate.invoke(9d, 9d);
        assertEquals((Double)9d, res.get(0).getResult());

        String delegateString = "Delegate.With2Params<Double, Double, Double>";
        compile(delegateString, "this::doubleMethod", "this::voidMethod");
        compile(delegateString, "this::doubleMethod", "this::returnDelegate");
        compile(delegateString, "this::doubleMethod", "this::integerMethod");
        compile(delegateString, "this::doubleMethod", "this::intMethod");
    }

    @Test
    public void testObjectMethod() throws Exception {
        Delegate.With2Params<Object, DelegateWith2ParamsTest, DelegateWith2ParamsTest> delegate = new Delegate.With2Params<>(this::objectMethod);
        delegate.add(this::returnDelegate);
        DelegateInvocationResult<Object> res2 = delegate.invoke(this, this);
        assertTrue(AbstractDelegateTest.class.isAssignableFrom(res2.get(0).getResult().getClass()));
        assertEquals(DelegateWith2ParamsTest.class, res2.get(1).getResult().getClass());

        String delegateString = "Delegate.With2Params<Object, DelegateWith2ParamsTest, DelegateWith2ParamsTest>";
        compile(delegateString, "this::returnDelegate", "this::voidMethod");
        compile(delegateString, "this::returnDelegate", "this::integerMethod");
        compile(delegateString, "this::returnDelegate", "this::stringMethod");


    }

}
