package com.github.hervian.reflection;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.github.hervian.reflection.util.SignatureUtil;


public class Types {
    
    private static interface SuperConsumer extends Serializable { }
    
    public @FunctionalInterface static interface Supplier<T>  extends SuperConsumer { void getDeclaredMethod() throws Exception; }
    public @FunctionalInterface static interface Consumer<T>  extends SuperConsumer { void getDeclaredMethod(T t) throws Exception; }
    public @FunctionalInterface static interface BiConsumer<T1, T2> extends SuperConsumer { void getDeclaredMethod(T1 t1, T2 t2)  throws Exception; }
    public @FunctionalInterface static interface TriConsumer<T1, T2, T3> extends SuperConsumer { void getDeclaredMethod(T1 t1, T2 t2, T3 t3)  throws Exception; }
    public @FunctionalInterface static interface QuadConsumer<T1, T2, T3, T4> extends SuperConsumer { void getDeclaredMethod(T1 t1, T2 t2, T3 t3, T4 t4)  throws Exception; }
    public @FunctionalInterface static interface PentaConsumer<T1, T2, T3, T4, T5> extends SuperConsumer { void getDeclaredMethod(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5)  throws Exception; }
    public @FunctionalInterface static interface SextConsumer<T1, T2, T3, T4, T5, T6> extends SuperConsumer { void getDeclaredMethod(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6)  throws Exception; }
    
    
    public static <T1> Method getDeclaredMethod(Supplier<T1> consumer) { return getDeclaredMethodFromSuperConsumer(consumer); }
    public static <T1> Method getDeclaredMethod(Consumer<T1> consumer) { return getDeclaredMethodFromSuperConsumer(consumer); }
    public static <T1, T2> Method getDeclaredMethod(BiConsumer<T1, T2> consumer) { return getDeclaredMethodFromSuperConsumer(consumer); }
    public static <T1, T2, T3> Method getDeclaredMethod(TriConsumer<T1, T2, T3> consumer) { return getDeclaredMethodFromSuperConsumer(consumer); }
    public static <T1, T2, T3, T4> Method getDeclaredMethod(QuadConsumer<T1, T2, T3, T4> consumer) { return getDeclaredMethodFromSuperConsumer(consumer); }
    public static <T1, T2, T3, T4, T5> Method getDeclaredMethod(PentaConsumer<T1, T2, T3, T4, T5> consumer) { return getDeclaredMethodFromSuperConsumer(consumer); }
    public static <T1, T2, T3, T4, T5, T6> Method getDeclaredMethod(SextConsumer<T1, T2, T3, T4, T5, T6> consumer) { return getDeclaredMethodFromSuperConsumer(consumer); }

    
//    public static <T1> Property getProperty(Consumer<T1> consumer) {
//        Method method = getDeclaredMethodFromSuperConsumer(consumer); 
//        Field
//    }
    
    
    /**
     * Thanks to Holger for this StackOverflow answer: https://stackoverflow.com/a/21879031/6095334
     */
    private static Method getDeclaredMethodFromSuperConsumer(SuperConsumer lambda) {
        for (Class<?> cl = lambda.getClass(); cl != null; cl = cl.getSuperclass()) {
            try {
                Method m = cl.getDeclaredMethod("writeReplace");
                m.setAccessible(true);
                Object replacement = m.invoke(lambda);
                if (!(replacement instanceof SerializedLambda))
                    break;// custom interface implementation
                SerializedLambda l = (SerializedLambda) replacement;
                String className = SignatureUtil.compactClassName(l.getImplClass(), false);
                return Class.forName(className).getDeclaredMethod(l.getImplMethodName(), getParameters(l.getImplMethodSignature()));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                break;
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    private static Class<?>[] getParameters(String signature) throws ClassNotFoundException {
        String[] parameters = signature.substring(1, signature.lastIndexOf(')')).replaceAll("/", ".").split(";");
        String[] params = SignatureUtil.methodSignatureArgumentTypes(signature, false);
        
        Class<?>[] paramTypes = new Class[params.length];
        for (int i=0; i<params.length; i++) {
            //Arrays must somewhat surprising be in something resembling the JVM format (fx: [Ljava.lang.String) whereas regular classes must be in the format of class.getName (fx java.lang.String)
            paramTypes[i] = Class.forName(params[i].contains("[") ? parameters[i] + ";" : params[i]);
        }
        return paramTypes;
    }
    
    /**
     * Copy pasted from Gunnor Mörling's blog: http://in.relation.to/2016/04/14/emulating-property-literals-with-java-8-method-references/
     */
    private static String getPropertyName(Method method) {
        final boolean hasGetterSignature = method.getParameterTypes().length == 0 && method.getReturnType() != null;

        String name = method.getName();
        String propName = null;

        if ( hasGetterSignature ) {
            if ( name.startsWith( "get" ) && hasGetterSignature ) {
                propName = name.substring( 3, 4 ).toLowerCase() + name.substring( 4 );
            }
            else if ( name.startsWith( "is" ) && hasGetterSignature ) {
                propName = name.substring( 2, 3 ).toLowerCase() + name.substring( 3 );
            }
        }
        else {
            throw new RuntimeException( "Only property getter methods are expected to be passed" );
        }

        return propName;
    }
    
    
    
}
