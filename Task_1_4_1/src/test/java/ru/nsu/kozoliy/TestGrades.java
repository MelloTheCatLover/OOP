package ru.nsu.kozoliy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class TestGrades {

    @Test
    void testAverageGradeWithNoGrades() {
        StudentRecordBook studentRecordBook = new StudentRecordBook();
        assertEquals(0.0, studentRecordBook.getAverageGrade(), 0.01);
    }

    @Test
    void testAverageGradeWithGrades() {
        StudentRecordBook studentRecordBook = new StudentRecordBook();
        studentRecordBook.addGrade("Math", 4, "B1S1");
        studentRecordBook.addGrade("Physics", 5, "B1S1");
        studentRecordBook.addGrade("History", 5, "B1S2");
        studentRecordBook.addGrade("English", 4, "B1S2");
        studentRecordBook.addGrade("Programming", 5, "B1S2");

        assertEquals(4.6, studentRecordBook.getAverageGrade(), 0.01);
    }

    @Test
    void testExcellentDiplomaWithNoGrades() {
        StudentRecordBook studentRecordBook = new StudentRecordBook();
        assertFalse(studentRecordBook.hasExcellentDiploma());
    }

    @Test
    void testExcellentDiplomaWithExcellentGrades() {
        StudentRecordBook studentRecordBook = new StudentRecordBook();
        studentRecordBook.addGrade("Math", 5, "B1S1");
        studentRecordBook.addGrade("Physics", 5, "B1S2");
        studentRecordBook.addGrade("History", 5, "B1S2");
        studentRecordBook.addGrade("English", 5, "B2S1");
        studentRecordBook.addGrade("Programming", 5, "B2S1");
        assertTrue(studentRecordBook.hasExcellentDiploma());
    }

    @Test
    void testExcellentDiplomaWithoutExcellentGrades() {
        StudentRecordBook studentRecordBook = new StudentRecordBook();
        studentRecordBook.addGrade("Math", 5, "B1S1");
        studentRecordBook.addGrade("Physics", 5, "B1S2");
        studentRecordBook.addGrade("History", 4, "B1S2");
        studentRecordBook.addGrade("English", 5, "B2S1");
        studentRecordBook.addGrade("Programming", 5, "B2S1");
        assertFalse(studentRecordBook.hasExcellentDiploma());
    }

    @Test
    void testIncreasedScholarshipAlwaysTrue() {
        StudentRecordBook studentRecordBook = new StudentRecordBook();
        assertTrue(studentRecordBook.qualifiesForIncreasedScholarship());
    }

    @Test
    void testSemesterAverageWithNoGrades() {
        StudentRecordBook studentRecordBook = new StudentRecordBook();
        assertEquals(0.0, studentRecordBook.getSemesterAverage("B1S1"), 0.01);
    }

    @Test
    void testSemesterAverageWithGrades() {
        StudentRecordBook studentRecordBook = new StudentRecordBook();
        studentRecordBook.addGrade("Math", 4, "B1S1");
        studentRecordBook.addGrade("Physics", 5, "B1S1");
        studentRecordBook.addGrade("History", 5, "B1S1");
        studentRecordBook.addGrade("English", 4, "B1S2");
        studentRecordBook.addGrade("Programming", 5, "B1S2");

        assertEquals(4.66, studentRecordBook.getSemesterAverage("B1S1"), 0.01);
    }

    @Test
    void testSemesterAverageWithNoGradesInSpecifiedSemester() {
        StudentRecordBook studentRecordBook = new StudentRecordBook();
        studentRecordBook.addGrade("Math", 4, "B1S1");
        studentRecordBook.addGrade("Physics", 5, "B1S2");
        assertEquals(0.0, studentRecordBook.getSemesterAverage("B1S3"), 0.01);
    }

    @Test
    void testSemesterAverageWithGradesInDifferentSemesters() {
        StudentRecordBook studentRecordBook = new StudentRecordBook();
        studentRecordBook.addGrade("Math", 4, "B1S1");
        studentRecordBook.addGrade("Physics", 5, "B1S2");
        assertEquals(4.0, studentRecordBook.getSemesterAverage("B1S1"), 0.01);
    }
}
