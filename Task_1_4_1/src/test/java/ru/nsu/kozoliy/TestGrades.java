package ru.nsu.kozoliy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class TestGrades {
    private final StudentRecordBook studentRecordBook = new StudentRecordBook("Michail Kozoliy");

    @BeforeEach
    public void setup() {
        studentRecordBook.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);
        studentRecordBook.addGrade("History of Russia",
                4, StudentRecordBook.Semester.B1S1);
        studentRecordBook.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);
        studentRecordBook.addGrade("History of Russia",
                4, StudentRecordBook.Semester.B1S1);
        studentRecordBook.addGrade("History of Russia",
                3, StudentRecordBook.Semester.B1S1);
        studentRecordBook.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);

        studentRecordBook.addGrade("Imperative programming ",
                4, StudentRecordBook.Semester.B1S1);
        studentRecordBook.addGrade("Imperative programming ",
                2, StudentRecordBook.Semester.B1S1);
        studentRecordBook.addGrade("Imperative programming ",
                3, StudentRecordBook.Semester.B1S1);

        studentRecordBook.addGrade("Imperative programming ",
                3, StudentRecordBook.Semester.B1S2);
        studentRecordBook.addGrade("Imperative programming ",
                3, StudentRecordBook.Semester.B1S2);


        studentRecordBook.addGrade("Declarative programming ",
                4, StudentRecordBook.Semester.B1S1);
        studentRecordBook.addGrade("Declarative programming ",
                4, StudentRecordBook.Semester.B1S1);
        studentRecordBook.addGrade("Declarative programming ",
                4, StudentRecordBook.Semester.B1S1);

        studentRecordBook.addGrade("Declarative programming ",
                4, StudentRecordBook.Semester.B1S2);
        studentRecordBook.addGrade("Declarative programming ",
                4, StudentRecordBook.Semester.B1S2);

        studentRecordBook.addGrade("Operating Systems", 2, StudentRecordBook.Semester.B2S1);
        studentRecordBook.addGrade("Operating Systems", 2, StudentRecordBook.Semester.B2S1);
        studentRecordBook.addGrade("Operating Systems", 2, StudentRecordBook.Semester.B2S1);
        studentRecordBook.addGrade("Operating Systems", 2, StudentRecordBook.Semester.B2S1);
        studentRecordBook.addGrade("Operating Systems", 2, StudentRecordBook.Semester.B2S1);

        studentRecordBook.addGrade("Java", 4, StudentRecordBook.Semester.B2S1);
        studentRecordBook.addGrade("Java", 4, StudentRecordBook.Semester.B2S1);
        studentRecordBook.addGrade("Java", 4, StudentRecordBook.Semester.B2S1);
        studentRecordBook.addGrade("Java", 4, StudentRecordBook.Semester.B2S1);

        studentRecordBook.addGrade("Java", 5, StudentRecordBook.Semester.B2S2);
        studentRecordBook.addGrade("Java", 4, StudentRecordBook.Semester.B2S2);
        studentRecordBook.addGrade("Java", 5, StudentRecordBook.Semester.B2S2);
        studentRecordBook.addGrade("Java", 5, StudentRecordBook.Semester.B2S2);
        studentRecordBook.addGrade("Java", 5, StudentRecordBook.Semester.B2S2);

        studentRecordBook.addGrade("Models of Computing", 5, StudentRecordBook.Semester.B2S2);
        studentRecordBook.addGrade("Models of Computing", 3, StudentRecordBook.Semester.B2S2);

        studentRecordBook.addGrade("Machine learning methods",
                5, StudentRecordBook.Semester.B3S1);
        studentRecordBook.addGrade("Machine learning methods",
                4, StudentRecordBook.Semester.B3S1);

        studentRecordBook.addGrade("Cat science", 5, StudentRecordBook.Semester.B3S1);

        studentRecordBook.addGrade("Cat practice", 4, StudentRecordBook.Semester.B3S1);

        studentRecordBook.addGrade("Dog science", 4, StudentRecordBook.Semester.B3S1);

        studentRecordBook.addGrade("Dog practice", 4, StudentRecordBook.Semester.B3S1);

        studentRecordBook.addGrade("Machine learning methods",
                3, StudentRecordBook.Semester.B3S2);
        studentRecordBook.addGrade("Machine learning methods",
                3, StudentRecordBook.Semester.B3S2);

        studentRecordBook.addGrade("Data protection", 5, StudentRecordBook.Semester.B4S1);

        studentRecordBook.addGrade("Data protection", 5, StudentRecordBook.Semester.B4S2);
    }

    @Test
    void setAndGetQualifyingExam() {
        StudentRecordBook studentRecordBook = new StudentRecordBook();
        studentRecordBook.setQualifyingExam(5);
        assertEquals(5, studentRecordBook.getQualifyingExam());
    }

    @Test
    void setAndGetStudentName() {
        StudentRecordBook studentWithName = new StudentRecordBook();
        studentWithName.setStudentName("Test Name");

        assertEquals("Test Name", studentWithName.getStudentName());
    }
    @Test
    void addGrade() {
        studentRecordBook.addGrade("TEST SUBJECT", 5, StudentRecordBook.Semester.B1S1);
        studentRecordBook.addGrade("TEST SUBJECT", 5, StudentRecordBook.Semester.B1S1);

        assertEquals(4, studentRecordBook
                .getFinalGradesBySemester(StudentRecordBook.Semester.B1S1).size());
        assertEquals("TEST SUBJECT: 5", studentRecordBook
                .getFinalGradesBySemester(StudentRecordBook.Semester.B1S1).get(0).toString());
        assertEquals("[TEST SUBJECT: 5, History of Russia: 4"
                        + ", Declarative programming : 4, Imperative programming : 3]",
                studentRecordBook
                        .getFinalGradesBySemester(StudentRecordBook.Semester.B1S1).toString());
        assertEquals("[Declarative programming : 4, Imperative programming : 3]",
                studentRecordBook
                        .getFinalGradesBySemester(StudentRecordBook.Semester.B1S2).toString());
        assertEquals("[Java: 4, Operating Systems: 2]",
                studentRecordBook
                        .getFinalGradesBySemester(StudentRecordBook.Semester.B2S1).toString());
        assertEquals("[Java: 5, Models of Computing: 4]",
                studentRecordBook
                        .getFinalGradesBySemester(StudentRecordBook.Semester.B2S2).toString());
        assertEquals("[Cat science: 5, "
                + "Cat practice: 4, Dog practice: 4, Machine learning methods: 5, "
                + "Dog science: 4]", studentRecordBook
                .getFinalGradesBySemester(StudentRecordBook.Semester.B3S1)
                .toString());
        assertEquals("[Machine learning methods: 3]",
                studentRecordBook.getFinalGradesBySemester(StudentRecordBook.Semester.B3S2)
                        .toString());
        assertEquals("[Data protection: 5]", studentRecordBook
                .getFinalGradesBySemester(StudentRecordBook.Semester.B4S1).toString());

    }

    @Test
    void increasedScholarship() {
        assertFalse(studentRecordBook.increasedScholarship(StudentRecordBook.Semester.B1S1));

        StudentRecordBook goodStudent = new StudentRecordBook();
        goodStudent.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);
        goodStudent.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);
        goodStudent.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);
        goodStudent.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);
        goodStudent.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);
        goodStudent.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);

        goodStudent.addGrade("Imperative programming ",
                5, StudentRecordBook.Semester.B1S1);
        goodStudent.addGrade("Imperative programming ",
                5, StudentRecordBook.Semester.B1S1);
        goodStudent.addGrade("Imperative programming ",
                5, StudentRecordBook.Semester.B1S1);
        goodStudent.addGrade("Imperative programming ",
                5, StudentRecordBook.Semester.B1S1);
        goodStudent.addGrade("Imperative programming ",
                5, StudentRecordBook.Semester.B1S1);


        goodStudent.addGrade("Declarative programming ",
                5, StudentRecordBook.Semester.B1S1);
        goodStudent.addGrade("Declarative programming ",
                5, StudentRecordBook.Semester.B1S1);
        goodStudent.addGrade("Declarative programming ",
                5, StudentRecordBook.Semester.B1S1);
        goodStudent.addGrade("Declarative programming ",
                5, StudentRecordBook.Semester.B1S1);
        goodStudent.addGrade("Declarative programming ",
                5, StudentRecordBook.Semester.B1S1);

        goodStudent.addGrade("Operating Systems",
                5, StudentRecordBook.Semester.B2S1);
        goodStudent.addGrade("Operating Systems",
                5, StudentRecordBook.Semester.B2S1);
        goodStudent.addGrade("Operating Systems",
                5, StudentRecordBook.Semester.B2S1);
        goodStudent.addGrade("Operating Systems",
                5, StudentRecordBook.Semester.B2S1);
        goodStudent.addGrade("Operating Systems",
                5, StudentRecordBook.Semester.B2S1);

        assertTrue(goodStudent.increasedScholarship(StudentRecordBook.Semester.B1S1));
        assertTrue(goodStudent.increasedScholarship(StudentRecordBook.Semester.B2S1));
    }

    @Test
    void getAverageGradesBySubject() {

        Map<StudentRecordBook.Semester, Double> subjectAverageGrades
                = studentRecordBook.getSubjectAverageGrade("Java");


        assertTrue(subjectAverageGrades.containsKey(StudentRecordBook.Semester.B2S1));
        assertTrue(subjectAverageGrades.containsKey(StudentRecordBook.Semester.B2S2));
        assertEquals(4.0, subjectAverageGrades
                .get(StudentRecordBook.Semester.B2S1), 0.01);
        assertEquals(4.8, subjectAverageGrades
                .get(StudentRecordBook.Semester.B2S2), 0.01);
    }

    @Test
    void getFinalGradesBySemester() {
        assertEquals(3, studentRecordBook
                .getFinalGradesBySemester(StudentRecordBook.Semester.B1S1).size());
        assertEquals("History of Russia: 4", studentRecordBook
                .getFinalGradesBySemester(StudentRecordBook.Semester.B1S1)
                .get(0).toString());
        assertEquals("[History of Russia: 4,"
                + " Declarative programming : 4, "
                + "Imperative programming : 3]", studentRecordBook
                .getFinalGradesBySemester(StudentRecordBook.Semester.B1S1)
                .toString());
        assertEquals("[Declarative programming : 4, "
                + "Imperative programming : 3]", studentRecordBook
                .getFinalGradesBySemester(StudentRecordBook.Semester.B1S2)
                .toString());
        assertEquals("[Java: 4, Operating Systems: 2]",
                studentRecordBook
                        .getFinalGradesBySemester(StudentRecordBook.Semester.B2S1)
                        .toString());
        assertEquals("[Java: 5, Models of Computing: 4]",
                studentRecordBook
                        .getFinalGradesBySemester(StudentRecordBook.Semester.B2S2)
                        .toString());
        assertEquals("[Cat science: 5, Cat practice: 4,"
                        + " Dog practice: 4,"
                        + " Machine learning methods: 5, Dog science: 4]",
                studentRecordBook
                        .getFinalGradesBySemester(StudentRecordBook.Semester.B3S1)
                        .toString());
        assertEquals("[Machine learning methods: 3]",
                studentRecordBook
                        .getFinalGradesBySemester(StudentRecordBook.Semester.B3S2)
                        .toString());
        assertEquals("[Data protection: 5]",
                studentRecordBook
                        .getFinalGradesBySemester(StudentRecordBook.Semester.B4S1)
                        .toString());

    }

    @Test
    void semesterFinalGrade() {
        assertEquals(3.77,
                studentRecordBook.semesterFinalGrade(StudentRecordBook.Semester.B1S1),
                0.01);
    }

    @Test
    void overallAverageGrade() {
        assertEquals(3.97,
                studentRecordBook.overallAverageGrade(), 0.01);
    }

    @Test
    void checkRedCertificateStatus() {
        studentRecordBook.setQualifyingExam(4);

        StudentRecordBook.RedCertificateStatus redDiplomaStatus
                = studentRecordBook.checkRedCertificateStatus();

        assertEquals(StudentRecordBook
                .RedCertificateStatus.notEligible, studentRecordBook
                .checkRedCertificateStatus());

        assertEquals("[excellentGradesPercentBelow75,"
                        + " notAllFinalGradesGoodOrExcellent,"
                        + " qualifyingExamNotExcellent]",
                redDiplomaStatus.getReasons().toString());

        studentRecordBook.setQualifyingExam(5);
        redDiplomaStatus = studentRecordBook.checkRedCertificateStatus();

        assertEquals("[excellentGradesPercentBelow75,"
                        + " notAllFinalGradesGoodOrExcellent]",
                redDiplomaStatus.getReasons().toString());

        StudentRecordBook bestOfTheBest = new StudentRecordBook("Best Of the Best");
        bestOfTheBest.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);
        bestOfTheBest.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);
        bestOfTheBest.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);
        bestOfTheBest.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);
        bestOfTheBest.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);
        bestOfTheBest.addGrade("History of Russia",
                5, StudentRecordBook.Semester.B1S1);

        bestOfTheBest.addGrade("Imperative programming ",
                5, StudentRecordBook.Semester.B1S1);
        bestOfTheBest.addGrade("Imperative programming ",
                5, StudentRecordBook.Semester.B1S1);
        bestOfTheBest.addGrade("Imperative programming ",
                5, StudentRecordBook.Semester.B1S1);
        bestOfTheBest.addGrade("Imperative programming ",
                5, StudentRecordBook.Semester.B1S1);
        bestOfTheBest.addGrade("Imperative programming ",
                5, StudentRecordBook.Semester.B1S1);


        bestOfTheBest.addGrade("Declarative programming ",
                5, StudentRecordBook.Semester.B1S1);
        bestOfTheBest.addGrade("Declarative programming ",
                5, StudentRecordBook.Semester.B1S1);
        bestOfTheBest.addGrade("Declarative programming ",
                5, StudentRecordBook.Semester.B1S1);
        bestOfTheBest.addGrade("Declarative programming ",
                5, StudentRecordBook.Semester.B1S1);
        bestOfTheBest.addGrade("Declarative programming ",
                5, StudentRecordBook.Semester.B1S1);

        bestOfTheBest.addGrade("Operating Systems",
                5, StudentRecordBook.Semester.B2S1);
        bestOfTheBest.addGrade("Operating Systems",
                5, StudentRecordBook.Semester.B2S1);
        bestOfTheBest.addGrade("Operating Systems",
                5, StudentRecordBook.Semester.B2S1);
        bestOfTheBest.addGrade("Operating Systems",
                5, StudentRecordBook.Semester.B2S1);
        bestOfTheBest.addGrade("Operating Systems",
                5, StudentRecordBook.Semester.B2S1);
        bestOfTheBest.addGrade("Java",
                5, StudentRecordBook.Semester.B2S2);
        bestOfTheBest.addGrade("Java",
                5, StudentRecordBook.Semester.B2S2);
        bestOfTheBest.addGrade("Java",
                5, StudentRecordBook.Semester.B2S2);
        bestOfTheBest.addGrade("Java",
                5, StudentRecordBook.Semester.B2S2);
        bestOfTheBest.addGrade("Java",
                5, StudentRecordBook.Semester.B2S2);

        bestOfTheBest.addGrade("Models of Computing",
                5, StudentRecordBook.Semester.B2S2);
        bestOfTheBest.addGrade("Models of Computing",
                5, StudentRecordBook.Semester.B2S2);

        bestOfTheBest.addGrade("Machine learning methods",
                5, StudentRecordBook.Semester.B3S1);
        bestOfTheBest.addGrade("Machine learning methods",
                5, StudentRecordBook.Semester.B3S1);

        bestOfTheBest.addGrade("Cat science",
                5, StudentRecordBook.Semester.B3S1);

        bestOfTheBest.addGrade("Cat practice",
                5, StudentRecordBook.Semester.B3S1);

        bestOfTheBest.addGrade("Dog science",
                5, StudentRecordBook.Semester.B3S1);

        bestOfTheBest.addGrade("Dog practice",
                4, StudentRecordBook.Semester.B3S1);

        bestOfTheBest.addGrade("Machine learning methods",
                5, StudentRecordBook.Semester.B3S2);
        bestOfTheBest.addGrade("Machine learning methods",
                5, StudentRecordBook.Semester.B3S2);

        bestOfTheBest.addGrade("Data protection",
                5, StudentRecordBook.Semester.B4S1);

        bestOfTheBest.addGrade("Data protection",
                5, StudentRecordBook.Semester.B4S2);

        bestOfTheBest.setQualifyingExam(5);

        assertEquals(StudentRecordBook.RedCertificateStatus
                .resCertificate, bestOfTheBest.checkRedCertificateStatus());
        assertNull(bestOfTheBest.checkRedCertificateStatus().getReasons());
    }
}
