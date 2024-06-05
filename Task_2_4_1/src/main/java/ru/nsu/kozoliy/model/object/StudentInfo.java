package ru.nsu.kozoliy.model.object;

import lombok.Data;
import ru.nsu.kozoliy.model.configs.Student;
import ru.nsu.kozoliy.model.tasks.TaskConfig;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Data
public class StudentInfo {
    Student studentConfig;
    Map<String, String> branchRename = new HashMap<>();
    Map<String, String> folderRename = new HashMap<>();
    Map<String, Double> extraScore = new HashMap<>();
    String branchPattern;
    String folderPattern;

    Set<LessonClass> studentAttendance = new LinkedHashSet<>();

    public StudentInfo(Student studentConfig) {
        this.studentConfig = studentConfig;
    }

    public StudentInfo(Student studentConfig, TaskConfig taskConfig) {
        this(studentConfig);
        branchPattern = taskConfig.getBranchPattern();
        folderPattern = taskConfig.getFolderPattern();
        taskConfig.getTasks().forEach(task -> {
            extraScore.put(task.id(), 0.0);
        });
    }

}