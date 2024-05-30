package ru.nsu.kozoliy.model.configs;

import groovy.lang.Closure;
import lombok.Data;
import ru.nsu.kozoliy.dslFilesPackage.DslDelegate;
import ru.nsu.kozoliy.model.object.Achievement;

import java.util.List;

@Data
public class MainConfig {

    private List<Achievement> enabledAchievements;
    Git git = new Git();
    Evaluation evaluation = new Evaluation();

    public void git(Closure<?> closure) {
        DslDelegate.groovyDelegate(git, closure);
    }

    public void evaluation(Closure<?> closure) {
        DslDelegate.groovyDelegate(evaluation, closure);
    }

    public List<Achievement> getEnabledAchievements() {
        return enabledAchievements;
    }

    public void setEnabledAchievements(List<Achievement> enabledAchievements) {
        this.enabledAchievements = enabledAchievements;
    }

    @Override
    public String toString() {
        return "MainConfig{" +
                "enabledAchievements=" + enabledAchievements +
                '}';
    }



}

