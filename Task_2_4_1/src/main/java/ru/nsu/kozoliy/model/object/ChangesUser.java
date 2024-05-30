package ru.nsu.kozoliy.model.object;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class ChangesUser {
    StudentInfo studentInfo;

    public ChangesUser(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    public void changeBranch(String taskId, String newBranchName) {
        if (!studentInfo.branchRename.containsKey(taskId)) {
            System.err.println("No such taskId: " + taskId);
            System.out.println(studentInfo.branchRename);
            throw new RuntimeException("No such taskId: " + taskId);
        }
        studentInfo.branchRename.put(taskId, newBranchName);
    }

    public void changeFolder(String taskId, String newFolderName) {
        if (!studentInfo.folderRename.containsKey(taskId)) {
            System.err.println("No such taskId: " + taskId);
            throw new RuntimeException("No such taskId: " + taskId);
        }
        studentInfo.folderRename.put(taskId, newFolderName);
    }

    public void changeBranch(Map<String, String> changes) {
        changes.forEach(this::changeBranch);
    }

    public void changeFolder(Map<String, String> changes) {
        changes.forEach(this::changeFolder);
    }

    public void changeBranchPattern(String newPattern) {
        studentInfo.branchPattern = newPattern;
    }

    public void changeFolderPattern(String newPattern) {
        studentInfo.folderPattern = newPattern;
    }

    public void changeExtraScore(String taskId, BigDecimal score) {
        if (!studentInfo.extraScore.containsKey(taskId)) {
            System.out.println(studentInfo.extraScore);
            System.err.println("No such taskId: " + taskId);
            throw new RuntimeException("No such taskId: " + taskId);
        }
        studentInfo.extraScore.put(taskId, score.doubleValue());
    }

    public void changeExtraScore(Map<String, BigDecimal> changes) {
        changes.forEach(this::changeExtraScore);
    }
}