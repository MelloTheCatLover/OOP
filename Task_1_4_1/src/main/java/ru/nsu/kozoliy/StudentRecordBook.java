package ru.nsu.kozoliy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentRecordBook {
    private final Map<String, List<Integer>> grades;
    private final Map<String, Double> semesterAverages;

    public StudentRecordBook() {
        this.grades = new HashMap<>();
        this.semesterAverages = new HashMap<>();
    }

    public void addGrade(String subject, int grade, String semester) {
        if (!grades.containsKey(semester)) {
            grades.put(semester, new ArrayList<>());
        }
        grades.get(semester).add(grade);

        // Recalculate semester average
        double semesterAverage = calculateAverage(grades.get(semester));
        semesterAverages.put(semester, semesterAverage);
    }

    public double getAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }

        int totalGrades = 0;
        int totalSubjects = 0;

        for (List<Integer> semesterGrades : grades.values()) {
            for (int grade : semesterGrades) {
                totalGrades += grade;
                totalSubjects++;
            }
        }

        return (double) totalGrades / totalSubjects;
    }

    public boolean hasExcellentDiploma() {
        if (grades.isEmpty()) {
            return false;
        }

        for (List<Integer> semesterGrades : grades.values()) {
            int lastGrade = semesterGrades.get(semesterGrades.size() - 1);
            if (lastGrade != 5) {
                return false;
            }
        }

        return true;
    }

    public boolean qualifiesForIncreasedScholarship() {
        // Assuming increased scholarship criteria are met
        // You can add more sophisticated logic based on your requirements
        return true;
    }

    public double getSemesterAverage(String semester) {
        return semesterAverages.getOrDefault(semester, 0.0);
    }

    private double calculateAverage(List<Integer> grades) {
        if (grades.isEmpty()) {
            return 0.0;
        }

        int totalGrades = 0;
        for (int grade : grades) {
            totalGrades += grade;
        }

        return (double) totalGrades / grades.size();
    }

    public static void main(String[] args) {
        StudentRecordBook studentRecordBook = new StudentRecordBook();

        // Add grades for various subjects in different semesters
        studentRecordBook.addGrade("Math", 4, "B1S1");
        studentRecordBook.addGrade("Physics", 5, "B1S1");
        studentRecordBook.addGrade("History", 5, "B1S2");

        studentRecordBook.addGrade("Math", 4, "B2S1");
        studentRecordBook.addGrade("Physics", 5, "B2S1");
        studentRecordBook.addGrade("History", 5, "B2S2");

        // Calculate and print average grade
        System.out.println("Average Grade: " + studentRecordBook.getAverageGrade());

        // Check for an excellent diploma
        System.out.println("Excellent Diploma: " + studentRecordBook.hasExcellentDiploma());

        // Check for increased scholarship
        System.out.println("Increased Scholarship: " + studentRecordBook.qualifiesForIncreasedScholarship());

        // Print semester averages
        System.out.println("Semester Average (B1S1): " + studentRecordBook.getSemesterAverage("B1S1"));
        System.out.println("Semester Average (B2S2): " + studentRecordBook.getSemesterAverage("B2S2"));
    }
}