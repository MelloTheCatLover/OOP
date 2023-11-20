package ru.nsu.kozoliy;

import java.util.*;

public class StudentRecordBook {
    private final Map<String, Map<Semester, List<Integer>>> grades;
    private final Map<String, Map<Semester, Double>> averageGradesBySubject;
    private final Map<Semester, Map<String, Double>> averageGradesBySemester;

    public StudentRecordBook() {
        this.grades = new HashMap<>();
        this.averageGradesBySubject = new HashMap<>();
        this.averageGradesBySemester = new HashMap<>();
    }

    public void addGrade(String subject, int grade, Semester semester) {
        grades.computeIfAbsent(subject, k -> new HashMap<>())
                .computeIfAbsent(semester, k -> new ArrayList<>())
                .add(grade);

        calculateAverageGrades();
    }

    private void calculateAverageGrades() {
        averageGradesBySubject.clear();
        averageGradesBySemester.clear();

        for (Map.Entry<String, Map<Semester, List<Integer>>> entry : grades.entrySet()) {
            String subject = entry.getKey();
            Map<Semester, List<Integer>> gradesBySemester = entry.getValue();

            for (Map.Entry<Semester, List<Integer>> semesterEntry : gradesBySemester.entrySet()) {
                Semester semester = semesterEntry.getKey();
                List<Integer> subjectGrades = semesterEntry.getValue();

                double averageGrade = subjectGrades.stream()
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0.0);

                averageGradesBySubject
                        .computeIfAbsent(subject, k -> new HashMap<>())
                        .put(semester, averageGrade);

                averageGradesBySemester
                        .computeIfAbsent(semester, k -> new HashMap<>())
                        .put(subject, averageGrade);
            }
        }
    }

    public void printAverageGradesBySubject(String subject) {
        if (averageGradesBySubject.containsKey(subject)) {
            System.out.println("Average grades for subject '" + subject + "':");
            averageGradesBySubject.get(subject).forEach((semester, averageGrade) ->
                    System.out.println("Semester " + semester + ": " + averageGrade));
        } else {
            System.out.println("No grades found for subject '" + subject + "'.");
        }
    }

    public void printAverageGradesBySemester(Semester semester) {
        if (averageGradesBySemester.containsKey(semester)) {
            System.out.println("Average grades for semester " + semester + ":");
            averageGradesBySemester.get(semester).forEach((subject, averageGrade) ->
                    System.out.println(subject + ": " + averageGrade));
        } else {
            System.out.println("No grades found for semester " + semester + ".");
        }
    }

    public enum Semester {
        B1S1,
        B1S2,
        B2S1,
        B2S2,
        B3S1,
        B3S2,
        B4S1,
        B4S2,
        M1S1,
        M2S2;
    }

    public double calculateAverageGradeForSemester(Semester semester) {
        if (averageGradesBySemester.containsKey(semester)) {
            Map<String, Double> subjectAverageGrades = averageGradesBySemester.get(semester);
            double totalGrade = subjectAverageGrades.values().stream().mapToDouble(Double::doubleValue).sum();
            return totalGrade / subjectAverageGrades.size();
        } else {
            return 0.0;
        }
    }

    public double calculateOverallAverageGrade() {
        double totalGrade = averageGradesBySubject.values().stream()
                .flatMap(subjectGrades -> subjectGrades.values().stream())
                .mapToDouble(Double::doubleValue)
                .sum();
        long totalSubjects = averageGradesBySubject.values().stream()
                .mapToLong(Map::size)
                .sum();

        return totalGrade / totalSubjects;
    }

    public static void main(String[] args) {
        StudentRecordBook studentRecordBook = new StudentRecordBook();

        // Adding grades
        studentRecordBook.addGrade("Operating Systems", 5, Semester.B1S1);
        studentRecordBook.addGrade("Operating Systems", 4, Semester.B1S1);
        studentRecordBook.addGrade("Operating Systems", 3, Semester.B1S2);
        studentRecordBook.addGrade("Probability Theory", 4, Semester.B1S1);
        studentRecordBook.addGrade("Probability Theory", 5, Semester.B1S2);
        studentRecordBook.addGrade("Probability Theory", 3, Semester.B2S1);

        // Printing average grades by subject and semester
        studentRecordBook.printAverageGradesBySubject("Operating Systems");
        studentRecordBook.printAverageGradesBySemester(Semester.B1S1);

        double semesterAverageGrade = studentRecordBook.calculateAverageGradeForSemester(Semester.B2S1);
        System.out.println("Average grade for Semester B1S1: " + semesterAverageGrade);

        double overallAverageGrade = studentRecordBook.calculateOverallAverageGrade();
        System.out.println("=======Overall average grade=======\n" + overallAverageGrade);
    }
}