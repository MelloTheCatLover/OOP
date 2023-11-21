package ru.nsu.kozoliy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The StudentRecordBook class represents an electronic record book for a student,
 * storing grades for various subjects across different semesters.
 * It provides functionalities such as adding grades, calculating average grades,
 * and checking eligibility for a red certificate with distinction.
 */
public class StudentRecordBook {
    private final Map<Semester, Map<String, List<Integer>>> grades;
    private final Map<String, Map<Semester, Double>> averageGradeBySubject;
    private final Map<Semester, Map<String, Double>> finalGradesBySemester;

    /**
     * Class constructor.
     *
     */
    public StudentRecordBook() {
        this.grades = new HashMap<>();
        this.averageGradeBySubject = new HashMap<>();
        this.finalGradesBySemester = new HashMap<>();
    }

    private int qualifyingExam = 0;

    /**
     * Sets the qualifying exam grade for the student.
     *
     * @param grade The grade for the qualifying exam.
     */
    public void setQualifyingExam(int grade) {
        this.qualifyingExam = grade;
    }

    /**
     * Gets the qualifying exam grade for the student.
     *
     * @return The grade for the qualifying exam.
     */
    public int getQualifyingExam() {
        return  this.qualifyingExam;
    }

    /**
     * Adds a grade for a subject in a specific semester.
     *
     * @param subject  The name of the subject.
     * @param grade    The grade for the subject.
     * @param semester The semester in which the grade is added.
     */
    public void addGrade(String subject, int grade, Semester semester) {
        grades.computeIfAbsent(semester, k -> new HashMap<>())
                .computeIfAbsent(subject, k -> new ArrayList<>())
                .add(grade);

        calculateAverageGrades();
    }


    /**
     * Calculate average grades for each subject and semester, stores values in hash tables.
     *
     */
    private void calculateAverageGrades() {
        averageGradeBySubject.clear();
        finalGradesBySemester.clear();

        for (Map.Entry<Semester, Map<String, List<Integer>>> semesterEntry : grades.entrySet()) {
            Semester semester = semesterEntry.getKey();
            Map<String, List<Integer>> gradesBySubject = semesterEntry.getValue();

            for (Map.Entry<String, List<Integer>> subjectEntry : gradesBySubject.entrySet()) {
                String subject = subjectEntry.getKey();
                List<Integer> subjectGrades = subjectEntry.getValue();

                double averageGrade = subjectGrades.stream()
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0.0);

                averageGradeBySubject
                        .computeIfAbsent(subject, k -> new HashMap<>())
                        .put(semester, averageGrade);

                finalGradesBySemester
                        .computeIfAbsent(semester, k -> new HashMap<>())
                        .put(subject, averageGrade);
            }
        }
    }

    /**
     * Checks if the student is eligible for an increased scholarship in a specific semester.
     *
     * @param semester The semester for which eligibility is checked.
     * @return True if the student is eligible, false otherwise.
     */
    public boolean increasedScholarship(Semester semester) {
        if (grades.containsKey(semester)) {
            Map<String, List<Integer>> semesterGrades = grades.get(semester);

            for (List<Integer> subjectGrades : semesterGrades.values()) {
                for (Integer grade : subjectGrades) {
                    if (grade < 4) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Prints the average grade for a specific subject.
     *
     * @param subject The name of the subject.
     */
    public Map<Semester, Double> getSubjectAverageGrade(String subject) {
        return averageGradeBySubject.getOrDefault(subject, Collections.emptyMap());
    }

    /**
     * Calculates the final grade for a specific semester.
     *
     * @param semester The semester for which the final grade is calculated.
     */
    public List<Pair<String, Long>> getFinalGradesBySemester(Semester semester) {
        List<Pair<String, Long>> result = new ArrayList<>();

        if (finalGradesBySemester.containsKey(semester)) {
            finalGradesBySemester.get(semester).forEach((subject, averageGrade)
                    -> result.add(new Pair<>(subject, Math.round(averageGrade))));
        }

        return result;
    }


    /**
     * Enum for Semester representation with format: /B(CourseNum)S(SemesterNum)\.
     *
     */
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
        M2S2
    }



    /**
     * Calculates the overall average grade for specific semester.
     *
     * @return The semester final grade.
     */
    public double semesterFinalGrade(Semester semester) {
        if (finalGradesBySemester.containsKey(semester)) {
            Map<String, Double> subjectAverageGrades = finalGradesBySemester.get(semester);
            double totalGrade = subjectAverageGrades.values()
                    .stream().mapToDouble(Double::doubleValue).sum();
            return totalGrade / subjectAverageGrades.size();
        } else {
            return 0;
        }
    }

    /**
     * Calculates the overall average grade across all subjects and semesters.
     *
     * @return The overall average grade.
     */
    public double overallAverageGrade() {
        double totalGrade = averageGradeBySubject.values().stream()
                .flatMap(subjectGrades -> subjectGrades.values().stream())
                .mapToDouble(Double::doubleValue)
                .sum();
        long totalSubjects = averageGradeBySubject.values().stream()
                .mapToLong(Map::size)
                .sum();

        return totalGrade / totalSubjects;
    }

    /**
     * Checks the eligibility status for a red certificate with distinction.
     *
     * @return The status of the red diploma eligibility along with reasons if not eligible.
     */
    public RedCertificateStatus checkRedCertificateStatus() {
        // Check if 75% of all grades are excellent
        long totalExcellentGrades = averageGradeBySubject.values().stream()
                .flatMap(subjectGrades -> subjectGrades.values().stream())
                .filter(grade -> grade == 5.0)
                .count();
        long totalGrades = averageGradeBySubject.values().stream()
                .mapToLong(subjectGrades -> subjectGrades.values().size())
                .sum();

        // Check if all grades are good for every semester
        boolean excellentOrGoodGradesForAllSemesters = grades.keySet().stream()
                .allMatch(this::increasedScholarship);

        // Check if the qualifying exam is 5
        boolean qualifyingExamIsExcellent = qualifyingExam == 5;

        if (totalExcellentGrades / (double) totalGrades >= 0.75
                && excellentOrGoodGradesForAllSemesters
                && qualifyingExamIsExcellent) {
            return RedCertificateStatus.resCertificate;
        } else {
            List<RedDiplomaReason> reasons = new ArrayList<>();

            if (totalExcellentGrades / (double) totalGrades < 0.75) {
                reasons.add(RedDiplomaReason.excellentGradesPercentBelow75);
            }

            if (!excellentOrGoodGradesForAllSemesters) {
                reasons.add(RedDiplomaReason.notAllFinalGradesGoodOrExcellent);
            }

            if (qualifyingExam != 5) {
                reasons.add(RedDiplomaReason.qualifyingExamNotExcellent);
            }

            return RedCertificateStatus.notEligible(reasons);
        }
    }


    /**
     * Enum for representation student's red certificate status.
     * 2 statuses: resCertificate, notEligible.
     * Has 3 displayable reasons.
     *
     */
    public enum RedCertificateStatus {
        resCertificate,
        notEligible;

        private List<RedDiplomaReason> reasons;

        public boolean isEligible() {
            return this == resCertificate;
        }

        public List<RedDiplomaReason> getReasons() {
            return reasons;
        }

        public static RedCertificateStatus notEligible(List<RedDiplomaReason> reasons) {
            notEligible.reasons = reasons;
            return notEligible;
        }
    }

    /**
     * Enum for requirements for red certificate.
     *
     */
    public enum RedDiplomaReason {
        excellentGradesPercentBelow75,
        notAllFinalGradesGoodOrExcellent,
        qualifyingExamNotExcellent
    }

    /**
     * Starting point.
     *
     */
    @ExcludeFromJacocoGeneratedTestReport
    public static void main(String[] args) {
        StudentRecordBook studentRecordBook = new StudentRecordBook();

        // Adding grades
        studentRecordBook.addGrade("Operating Systems", 5, Semester.B1S1);
        studentRecordBook.addGrade("Operating Systems", 5, Semester.B1S1);
        studentRecordBook.addGrade("Operating Systems", 5, Semester.B1S1);
        studentRecordBook.addGrade("Probability Theory", 5, Semester.B1S1);
        studentRecordBook.addGrade("Probability Theory", 5, Semester.B1S2);
        studentRecordBook.addGrade("Probability Theory", 5, Semester.B2S1);
        studentRecordBook.addGrade("Databases", 3, Semester.B1S1);
        studentRecordBook.addGrade("Databases", 3, Semester.B1S1);
        studentRecordBook.addGrade("Databases", 4, Semester.B1S1);




        double semesterAverageGrade = studentRecordBook.semesterFinalGrade(Semester.B1S1);
        System.out.println("Average grade for Semester B1S1: " + semesterAverageGrade);

        List<Pair<String, Long>> ss = studentRecordBook.getFinalGradesBySemester(Semester.B1S1);

        System.out.println("GRADES(B1S1):\n" + ss);

        // Printing all grades for a specific semester
        if (studentRecordBook.increasedScholarship(Semester.B1S1)) {
            System.out.println("TRUE\n");
        } else {
            System.out.println("FALSE\n");
        }

        System.out.println("QualifyingExam: " + studentRecordBook.getQualifyingExam());

        double overallAverageGrade = studentRecordBook.overallAverageGrade();
        System.out.println("=======Overall average grade=======\n" + overallAverageGrade);


        studentRecordBook.setQualifyingExam(3);

        RedCertificateStatus redDiplomaStatus = studentRecordBook.checkRedCertificateStatus();

        if (redDiplomaStatus.isEligible()) {
            System.out.println("Studet good!");
        } else {
            System.out.println("Student not Good, here is why: \n Reasons:");
            for (int i = 0; i < redDiplomaStatus.reasons.size(); i++) {
                System.out.println(redDiplomaStatus.reasons.get(i));
            }
        }



    }
}
