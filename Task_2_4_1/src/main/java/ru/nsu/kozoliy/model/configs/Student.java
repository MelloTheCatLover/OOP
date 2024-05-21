package ru.nsu.kozoliy.model.configs;

import lombok.Data;

@Data
public class Student {
    String fullName;
    String gitName;
    String defaultBranch;
    String repository;

    public Student() {

    }

    public Student(String gitName) {
        this.gitName = gitName;
    }

    public Student(String gitName, String fullName) {
        this.gitName = gitName;
        this.fullName = fullName;
    }

}