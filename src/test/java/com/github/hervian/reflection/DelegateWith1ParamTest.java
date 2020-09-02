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
public class DelegateWith1ParamTest extends AbstractDelegateTest {

    @Mock
    private DelegateWith1ParamTest tester;

    public void voidMethod(String str){
        System.out.println(str.length());
    }
    public String stringMethod(String str){return str;}
    public int intMethod(int i){return i-1;}
    public Integer integerMethod(Integer i){return Integer.valueOf(8);}
    public double doubleMethod(double d){return d;}
    public DelegateWith1ParamTest returnDelegate(DelegateWith1ParamTest delegate){return delegate;}
    public Object objectMethod(Object o){return o;}
    public byte byteMethod(byte b){return Integer.valueOf(7).byteValue();}
    public short shortMethod(short s){return s;}
    public char charMethod(char c){return c;}
    public boolean booleanMethod(boolean b){return b;}
    public float floatMethod(float f){return f;}
    public long longMethod(long l){return l;}
    public Integer[] integerArrayMethod(Integer[] arr){return arr;}

    @Override
    protected DelegateType getDelegateType() {
        return DelegateType.With1Param;
    }


    @Test
    public void testVoidMethod() throws Exception {
        Delegate.With1ParamAndVoid<String> delegate = new Delegate.With1ParamAndVoid<>(tester::voidMethod);
        DelegateInvocationResult<Void> res = delegate.invoke("hello world");
        assertNotNull(res);
        verify(tester, times(1)).voidMethod(any());

        //Any return type will match the *AndVoid method / the void return type:
        Delegate.With1ParamAndVoid delegateInt = new Delegate.With1ParamAndVoid<>(this::intMethod);
        Delegate.With1ParamAndVoid delegateObject = new Delegate.With1ParamAndVoid<>(this::returnDelegate);
    }

    @Test
    public void testStringMethod() throws Exception {
        Delegate.With1Param<String, String> delegate = new Delegate.With1Param<>(this::stringMethod);

        DelegateInvocationResult<String> res = delegate.invoke("hello world!");
        assertEquals("hello world!", res.get(0).getResult());

        String delegateString = "Delegate.With1Param<String, String>";
        compile(delegateString, "this::stringMethod", "this::voidMethod");
        compile(delegateString, "this::stringMethod", "this::returnDelegate");
    }

    @Test
    public void testIntMethod() throws Exception {
        Delegate.With1Param<Integer, Integer> delegate = new Delegate.With1Param<>(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke(7);
        assertEquals((Integer)6, res.get(0).getResult());

        String delegateString = "Delegate.With1Param<Integer, Integer>";
        compile(delegateString, "this::intMethod", "this::voidMethod");
        compile(delegateString, "this::intMethod", "this::returnDelegate");
        compile(delegateString, "this::intMethod", "this::doubleMethod");
    }

    @Test
    public void testIntegerMethod() throws Exception {
        Delegate.With1Param<Integer, Integer> delegate = new Delegate.With1Param<>(this::integerMethod);
        delegate.add(this::intMethod);

        DelegateInvocationResult<Integer> res = delegate.invoke(8);
        assertEquals((Integer)8, res.get(0).getResult());
        assertEquals((Integer)7, res.get(1).getResult());

        String delegateString = "Delegate.With1Param<Integer, Integer>";
        compile(delegateString, "this::integerMethod", "this::voidMethod");
        compile(delegateString, "this::integerMethod", "this::returnDelegate");
        compile(delegateString, "this::integerMethod", "this::doubleMethod");
    }

    @Test
    public void testDoubleMethod() throws Exception {
        Delegate.With1Param<Double, Double> delegate = new Delegate.With1Param<>(this::doubleMethod);

        DelegateInvocationResult<Double> res = delegate.invoke(9d);
        assertEquals((Double)9d, res.get(0).getResult());

        String delegateString = "Delegate.With1Param<Double, Double>";
        compile(delegateString, "this::doubleMethod", "this::voidMethod");
        compile(delegateString, "this::doubleMethod", "this::returnDelegate");
        compile(delegateString, "this::doubleMethod", "this::integerMethod");
        compile(delegateString, "this::doubleMethod", "this::intMethod");
    }

    @Test
    public void testObjectMethod() throws Exception {
        Delegate.With1Param<Object, DelegateWith1ParamTest> delegate = new Delegate.With1Param<>(this::returnDelegate);

        DelegateInvocationResult<Object> res = delegate.invoke(this);
        assertEquals(DelegateWith1ParamTest.class, res.get(0).getResult().getClass());

        String delegateString = "Delegate.With1Param<Object, DelegateWith1ParamTest>";
        compile(delegateString, "this::returnDelegate", "this::voidMethod");
        compile(delegateString, "this::returnDelegate", "this::integerMethod");
        compile(delegateString, "this::returnDelegate", "this::stringMethod");

        Delegate.With1Param<Object, DelegateWith1ParamTest> delegate2 = new Delegate.With1Param<>(this::objectMethod);
        delegate2.add(this::returnDelegate);
        DelegateInvocationResult<Object> res2 = delegate2.invoke(this);
        assertTrue(AbstractDelegateTest.class.isAssignableFrom(res2.get(0).getResult().getClass()));
        assertEquals(DelegateWith1ParamTest.class, res2.get(1).getResult().getClass());
    }

    @Test
    public void testByteMethod() throws Exception {
        Delegate.With1ParamAndVoid<Byte> delegate = new Delegate.With1ParamAndVoid<>(this::byteMethod);
        delegate.invoke(Integer.valueOf(7).byteValue());
    }

    @Test
    public void testShortMethod() throws Exception {
        Delegate.With1ParamAndVoid<Short> delegate = new Delegate.With1ParamAndVoid<>(this::shortMethod);
        delegate.invoke((short)7);
    }

    @Test
    public void testCharMethod() throws Exception {
        Delegate.With1ParamAndVoid<Character> delegate = new Delegate.With1ParamAndVoid<>(this::charMethod);
        delegate.invoke('c');
    }

    @Test
    public void testBooleanMethod() throws Exception {
        Delegate.With1ParamAndVoid<Boolean> delegate = new Delegate.With1ParamAndVoid<>(this::booleanMethod);
        delegate.invoke(true);
    }

    @Test
    public void testFloatMethod() throws Exception {
        Delegate.With1ParamAndVoid<Float> delegate = new Delegate.With1ParamAndVoid<>(this::floatMethod);
        delegate.invoke(7f);
    }

    @Test
    public void testLongMethod() throws Exception {
        Delegate.With1ParamAndVoid<Long> delegate = new Delegate.With1ParamAndVoid<>(this::longMethod);
        delegate.invoke(7l);
    }
}
