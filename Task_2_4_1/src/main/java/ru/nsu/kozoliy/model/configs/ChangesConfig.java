package ru.nsu.kozoliy.model.configs;

import groovy.lang.Closure;
import lombok.Data;
import ru.nsu.kozoliy.dslFilesPackage.DslDelegate;
import ru.nsu.kozoliy.model.object.ChangesUser;
import ru.nsu.kozoliy.model.object.StudentInfo;

import java.util.Map;

@Data
public class ChangesConfig {
    private Map<String, StudentInfo> informationMap;

    public ChangesConfig(Map<String, StudentInfo> informationMap) {
        this.informationMap = informationMap;

    }

    public void forStudent(String gitName, Closure<?> closure) {
        if (!informationMap.containsKey(gitName)) {
            System.err.println("There is no " + gitName);
            throw new RuntimeException("There is no user with gitName: " + gitName);
        }
        StudentInfo information = informationMap.get(gitName);
        ChangesUser personFix = new ChangesUser(information);
        DslDelegate.groovyDelegate(personFix, closure);
    }

}