package ru.nsu.kozoliy.model.tasks;


import groovy.lang.Closure;
import lombok.Data;
import ru.nsu.kozoliy.dslFilesPackage.DslDelegate;
import ru.nsu.kozoliy.model.configs.MainConfig;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskConfig {
    List<Task> tasks = new ArrayList<>();
    Double taskScore;
    String branchPattern = "task-$1-$2-$3";
    String folderPattern = "Task_$1_$2_$3";

    public TaskConfig(MainConfig generalConfig) {
        taskScore = generalConfig.getEvaluation().getTaskScore();
    }

    public void tasks(Closure<?> closure) {
        DslDelegate.groovyDelegate(this, closure);
    }

    public void createTask(String name, Integer fst, Integer snd,
                           Integer thd) {
        String branchName = branchPattern
                .replace("$1", fst.toString())
                .replace("$2", snd.toString())
                .replace("$3", thd.toString());
        String folderName = folderPattern
                .replace("$1", fst.toString())
                .replace("$2", snd.toString())
                .replace("$3", thd.toString());
        task(name, branchName, folderName);
    }

    public void createTask(String name, Integer fst, Integer snd,
                           Integer thd, Closure<?> closure) {
        String branchName = branchPattern
                .replace("$1", fst.toString())
                .replace("$2", snd.toString())
                .replace("$3", thd.toString());
        String folderName = folderPattern
                .replace("$1", fst.toString())
                .replace("$2", snd.toString())
                .replace("$3", thd.toString());
        task(name, branchName, folderName, closure);
    }

    public void task(String name, String folder, String branch) {
        Task task = new Task(name, folder, branch, taskScore);
        tasks.add(task);
    }

    public void task(String name, String folder,
                     String branch, Closure<?> closure) {
        Task task = new Task(name, folder, branch);
        DslDelegate.groovyDelegate(task, closure);
        tasks.add(task);
    }
}