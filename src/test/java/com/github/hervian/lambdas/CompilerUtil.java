package com.github.hervian.lambdas;

import org.joor.Reflect;
import org.joor.ReflectException;

import static org.joor.Reflect.*;

public class CompilerUtil {

    /**
     * NB: The Method Reference must be to a PUBLIC method. This may be a limitation with the chosen library JOOR.
     * Alternatively one could skip this wrapper project and call the Java Compiler API directly.
     */
    static <T> T compile(Class<T> classToExtend, String functionField) throws ReflectException {
        String name = classToExtend.getName() + "CanCompileTest";
        String sourceCode = "package com.github.hervian.lambdas;\n" +
                "class "+ classToExtend.getSimpleName() + "CanCompileTest extends "+classToExtend.getName()+" {\n" +
                "    " + functionField + ";\n" +
                "}\n";
        return Reflect.compile(name, sourceCode).create().get();
    }

}
