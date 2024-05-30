package ru.nsu.kozoliy.model.object;

import lombok.Data;
import ru.nsu.kozoliy.model.configs.Student;
import ru.nsu.kozoliy.model.tasks.TaskConfig;

import java.util.HashMap;
import java.util.Map;

@Data
public class StudentInfo {
    Student student;
    Map<String, String> branchRename = new HashMap<>();
    Map<String, String> folderRename = new HashMap<>();
    Map<String, Double> extraScore = new HashMap<>();
    String branchPattern;
    String folderPattern;

    public StudentInfo(Student student) {
        this.student = student;
    }

    public StudentInfo(Student studentConfig, TaskConfig taskConfig) {
        this(studentConfig);
        branchPattern = taskConfig.getBranchPattern();
        folderPattern = taskConfig.getFolderPattern();
        taskConfig.getTasks().forEach(task -> {
            branchRename.put(task.id(), null);
            folderRename.put(task.id(), null);
            extraScore.put(task.id(), 0.0);
        });
    }
}