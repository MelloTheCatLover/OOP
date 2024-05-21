package ru.nsu.kozoliy.model.object;

import lombok.Data;

@Data
public class StudentClass {
    String name;
    String githubName;
    String defaultBranch;
    String repoName;
    private String repoUrl;

}
