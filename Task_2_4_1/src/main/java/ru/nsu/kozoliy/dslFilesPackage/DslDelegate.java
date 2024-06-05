package ru.nsu.kozoliy.dslFilesPackage;

import groovy.lang.Closure;

public class DslDelegate {
    public static void groovyDelegate(Object delegate, Closure<?> closure) {
        closure.setDelegate(delegate);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.call();
    }
}