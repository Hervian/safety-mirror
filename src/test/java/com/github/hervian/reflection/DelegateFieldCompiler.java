package com.github.hervian.reflection;

import org.joor.ReflectException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public interface DelegateFieldCompiler {

    default <T> T compile(Class<T> clazz, String delegateField_shouldCompile,String delegateField_shouldNotCompile){
        T compiledClass = CompilerUtil.compile(clazz, delegateField_shouldCompile);

        try {
            CompilerUtil.compile(clazz, delegateField_shouldNotCompile);
            fail("Following expr unexpectedly compiled: " + delegateField_shouldNotCompile);
        } catch (ReflectException e){
            assertTrue(true);
        }
        return compiledClass;
    }

}
