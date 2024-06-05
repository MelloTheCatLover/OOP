package ru.nsu.kozoliy.model.configs;

import groovy.lang.Closure;
import lombok.Data;
import ru.nsu.kozoliy.dslFilesPackage.DslDelegate;

@Data
public class MainConfig {
    GitConf gitConf = new GitConf();
    Evaluation evaluation = new Evaluation();

    public void git(Closure<?> closure) {
        DslDelegate.groovyDelegate(gitConf, closure);
    }

    public void evaluation(Closure<?> closure) {
        DslDelegate.groovyDelegate(evaluation, closure);
    }

}