package com.github.hervian.reflection;

import org.joor.Reflect;
import org.joor.ReflectException;

public class CompilerUtil {

    private static final String packageString = Delegate.class.getPackage().getName();

    /**
     * NB: The Method Reference must be to a PUBLIC method. This may be a limitation with the chosen library JOOR.
     * Alternatively one could skip this wrapper project and call the Java Compiler API directly.
     */
    static <T> T compile(Class<T> classToExtend, String functionField) throws ReflectException {
        String name = classToExtend.getName() + "CanCompileTest";
        String sourceCode = "package " + packageString + ";\n" +
                "class "+ classToExtend.getSimpleName() + "CanCompileTest extends "+classToExtend.getName()+" {\n" +
                "    " + functionField + ";\n" +
                "}\n";
        return Reflect.compile(name, sourceCode).create().get();
    }

}
