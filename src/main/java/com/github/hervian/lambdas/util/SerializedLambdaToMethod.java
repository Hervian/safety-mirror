package com.github.hervian.lambdas.util;

import com.github.hervian.lambdas.Fun;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Copyright 2016 Anders Granau Høfft
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * END OF NOTICE
 *
 * @author Anders Granau Høfft
 *
 * Thanks to Holger for this StackOverflow answer: https://stackoverflow.com/a/21879031/6095334
 */
public class SerializedLambdaToMethod {

    private SerializedLambdaToMethod(){}

    public static Method createMethodFromSuperConsumer(Fun lambda) {
        SerializedLambda serializedLambda = getSerializedLambda(lambda);
        return getMethod(serializedLambda);
    }

     private static Method getMethod(SerializedLambda serializedLambda) {
        if (serializedLambda==null) {
            return null;
        } else {
            String className = SignatureUtil.compactClassName(serializedLambda.getImplClass(), false);
            try {
                return Class.forName(className).getDeclaredMethod(serializedLambda.getImplMethodName(), getParameters(serializedLambda.getImplMethodSignature()));
            } catch (NoSuchMethodException | SecurityException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static SerializedLambda getSerializedLambda(Fun lambda) {
        SerializedLambda serializedLambda = null;
        for (Class<?> cl = lambda.getClass(); cl != null; cl = cl.getSuperclass()) {
            try {
                Method m = cl.getDeclaredMethod("writeReplace");
                m.setAccessible(true);
                Object replacement = m.invoke(lambda);
                if (!(replacement instanceof SerializedLambda))
                    break;// custom interface implementation
                serializedLambda = (SerializedLambda) replacement;
                break;
            } catch (NoSuchMethodException e) {
            } catch (IllegalAccessException | InvocationTargetException | SecurityException e) {
                throw new RuntimeException(e);
            }
        }
        return serializedLambda;
    }

    public static String createMethodNameFromSuperConsumer(Fun lambda) {
        SerializedLambda serializedLambda = getSerializedLambda(lambda);
        return serializedLambda.getImplMethodName();
    }

    private static Class<?>[] getParameters(String signature) throws ClassNotFoundException {
        String[] parameters = signature.substring(1, signature.lastIndexOf(')')).replaceAll("/", ".").split(";");
        String[] params = SignatureUtil.methodSignatureArgumentTypes(signature, false);

        Class<?>[] paramTypes = new Class[params.length];
        for (int i=0; i<params.length; i++) {
            paramTypes[i] = isPrimitive(params[i])
                    ? getPrimitiveClass(params[i])
                    : Class.forName(params[i].contains("[") ? parameters[i] + ";" : params[i]); //Arrays must somewhat surprising be in something resembling the JVM format (fx: [Ljava.lang.String) whereas regular classes must be in the format of class.getName (fx java.lang.String)
        }
        return paramTypes;
    }

    private static boolean isPrimitive(String param) {
        switch (param){
            case "byte" : return true;
            case "short" : return true;
            case "int" : return true;
            case "long" : return true;
            case "double" : return true;
            case "float" : return true;
            case "boolean" : return true;
            case "char" : return true;
            default: return false;
        }
    }

    private static Class<?> getPrimitiveClass(String param) {
        switch (param){
            case "byte" : return byte.class;
            case "short" : return short.class;
            case "int" : return int.class;
            case "long" : return long.class;
            case "double" : return double.class;
            case "float" : return float.class;
            case "boolean" : return boolean.class;
            case "char" : return char.class;
            default: throw new UnsupportedOperationException("Unmapped switch case. Have a new primitive data type been added to the Java language?");
        }
    }

}
