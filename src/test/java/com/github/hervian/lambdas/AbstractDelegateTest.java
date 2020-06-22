package com.github.hervian.lambdas;

import static org.junit.Assert.assertNotNull;

public abstract class AbstractDelegateTest implements DelegateFieldCompiler {

    enum DelegateType {
        With0Params(Fun.With0Params.class),
        With0ParamsAndVoid(Fun.With0ParamsAndVoid.class),
        With1Param(Fun.With1Param.class),
        With1ParamAndVoid(Fun.With1ParamAndVoid.class),
        With2Params(Fun.With2Params.class),
        With2ParamsAndVoid(Fun.With2ParamsAndVoid.class),
        With3Params(Fun.With3Params.class),
        With3ParamsAndVoid(Fun.With3ParamsAndVoid.class),
        With4Params(Fun.With4Params.class),
        With4ParamsAndVoid(Fun.With4ParamsAndVoid.class),
        With5Params(Fun.With5Params.class),
        With5ParamsAndVoid(Fun.With5ParamsAndVoid.class),
        With6Params(Fun.With6Params.class),
        With6ParamsAndVoid(Fun.With6ParamsAndVoid.class),
        With7Params(Fun.With7Params.class),
        With7ParamsAndVoid(Fun.With7ParamsAndVoid.class),
        With8Params(Fun.With8Params.class),
        With8ParamsAndVoid(Fun.With8ParamsAndVoid.class),
        With9Params(Fun.With9Params.class),
        With9ParamsAndVoid(Fun.With9ParamsAndVoid.class);

        private String subclass;

        private <T extends Fun> DelegateType(Class<T> fun){
            subclass = fun.getSimpleName();
        }
    }

    protected abstract DelegateType getDelegateType();

    void compile(String delegateType, String methodRefThatShouldCompile, String methodRefThatShouldNotCompile){
        String type = getDelegateType().subclass.contains("Void") ? getDelegateType().subclass : getDelegateType().subclass + "<>";
        String initializer = "new Delegate." + type + "(%s);";
        String delegateFieldTemplate = delegateType + " delegate = " + initializer;
        Object instanceOfCompiledClass = compile(getClass(),
                String.format(delegateFieldTemplate, methodRefThatShouldCompile),
                String.format(delegateFieldTemplate, methodRefThatShouldNotCompile)
        );

        assertNotNull(instanceOfCompiledClass);
    }

}
