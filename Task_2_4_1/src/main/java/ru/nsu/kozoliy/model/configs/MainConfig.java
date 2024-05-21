package ru.nsu.kozoliy.model.configs;

import groovy.lang.Closure;
import lombok.Data;
import ru.nsu.kozoliy.dslFilesPackage.DslDelegate;

@Data
public class MainConfig {
    Git git = new Git();
    Evaluation evaluation = new Evaluation();

    public void git(Closure<?> closure) {
        DslDelegate.groovyDelegate(git, closure);
    }

    public void evaluation(Closure<?> closure) {
        DslDelegate.groovyDelegate(evaluation, closure);
    }

}