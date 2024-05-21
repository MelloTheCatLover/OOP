package ru.nsu.kozoliy.model.configs;

import lombok.Data;

@Data
public class Git {
    String repoLinkPrefix = "https://github.com/";
    String repoLinkPostfix = ".git";
    String defaultRepository;
    String docsBranch = "gh-pages";
    String defaultBranch = "main";
}