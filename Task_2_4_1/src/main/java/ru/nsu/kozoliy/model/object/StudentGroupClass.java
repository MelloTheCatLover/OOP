package ru.nsu.kozoliy.model.object;


import lombok.Data;
import groovy.lang.Closure;
import ru.nsu.kozoliy.dslFilesPackage.DslDelegate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class StudentGroupClass {
    String groupName;

    StudentClass studentClass;

    public void student(Closure<?> closure) {
        studentClass = new StudentClass();
        DslDelegate.groovyDelegate(studentClass, closure);
    }

}