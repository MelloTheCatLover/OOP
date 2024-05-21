package ru.nsu.kozoliy.model.object;


import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class StudentGroupClass {
    String groupName;
    List<StudentClass> students = new ArrayList<>();

    public StudentGroupClass(String groupName, Collection<StudentClass> students) {
        this.groupName = groupName;
        this.students.addAll(students);
    }

}