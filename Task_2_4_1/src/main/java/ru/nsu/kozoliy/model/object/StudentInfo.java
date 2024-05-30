package ru.nsu.kozoliy.model.object;

import ru.nsu.kozoliy.model.object.Achievement;
import ru.nsu.kozoliy.model.configs.Student;
import ru.nsu.kozoliy.model.tasks.TaskConfig;

import java.util.List;

public class StudentInfo {
    private final Student studentConfig;
    private final TaskConfig taskConfig;
    private final List<Achievement> achievements;

    public StudentInfo(Student studentConfig, TaskConfig taskConfig, List<Achievement> achievements) {
        this.studentConfig = studentConfig;
        this.taskConfig = taskConfig;
        this.achievements = achievements;
    }

    public Student getStudentConfig() {
        return studentConfig;
    }

    public TaskConfig getTaskConfig() {
        return taskConfig;
    }

    public List<Achievement> getAchievements() {
        return achievements;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "studentConfig=" + studentConfig +
                ", taskConfig=" + taskConfig +
                ", achievements=" + achievements +
                '}';
    }
}
