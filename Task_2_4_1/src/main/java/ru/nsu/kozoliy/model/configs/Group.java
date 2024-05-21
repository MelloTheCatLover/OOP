package ru.nsu.kozoliy.model.configs;


import groovy.lang.Closure;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nsu.kozoliy.dslFilesPackage.DslDelegate;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Group {
    String groupName;
    String defaultBranch = "main";
    String defaultRepository = "oop";
    List<Student> studentConfigs = new ArrayList<>();

    public Group(MainConfig mainConfig) {
        defaultBranch = mainConfig.getGit().getDefaultBranch();
        defaultRepository = mainConfig.getGit().getDefaultRepository();
    }

    public void group(String groupName, Closure<?> closure) {
        this.groupName = groupName;
        DslDelegate.groovyDelegate(this, closure);
    }

    private Student getDefaultStudent() {
        Student studentConfig = new Student();
        studentConfig.defaultBranch = defaultBranch;
        studentConfig.repository = defaultRepository;
        return studentConfig;
    }

    public void student(Closure<?> closure) {
        Student studentConfig = getDefaultStudent();
        DslDelegate.groovyDelegate(studentConfig, closure);
        studentConfigs.add(studentConfig);
    }


    public void student(String gitName, Closure<?> closure) {
        Student studentConfig = getDefaultStudent();
        studentConfig.gitName = gitName;
        studentConfig.fullName = gitName;
        DslDelegate.groovyDelegate(studentConfig, closure);
        studentConfigs.add(studentConfig);
    }

    public void student(String gitName,
                        String fullName,
                        Closure<?> closure) {
        Student studentConfig = getDefaultStudent();
        studentConfig.gitName = gitName;
        studentConfig.fullName = fullName;
        DslDelegate.groovyDelegate(studentConfig, closure);
        studentConfigs.add(studentConfig);
    }


    public void student(String gitName) {
        Student studentConfig = getDefaultStudent();
        studentConfig.fullName = gitName;
        studentConfig.gitName = gitName;
        studentConfigs.add(studentConfig);
    }

    public void student(String gitName,
                        String fullName) {
        Student studentConfig = getDefaultStudent();
        studentConfig.gitName = gitName;
        studentConfig.fullName = fullName;
        studentConfigs.add(studentConfig);
    }


}