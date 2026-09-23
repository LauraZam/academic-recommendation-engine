package com.university.edtech.service;

import com.university.edtech.dto.EnrollmentRequestDto;
import com.university.edtech.model.Course;
import com.university.edtech.model.Enrollment;
import com.university.edtech.model.Student;
import com.university.edtech.repository.CourseRepository;
import com.university.edtech.repository.EnrollmentRepository;
import com.university.edtech.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Transactional
    public void enrollStudent(EnrollmentRequestDto dto) {
        Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        Course course = courseRepository.findById(dto.courseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        // CORE BUSINESS RULE: A student cannot enroll in the same course twice in the same semester.
        boolean alreadyEnrolled = student.getEnrollments().stream()
                .anyMatch(e -> e.getCourse().getId().equals(course.getId())
                        && e.getSemester().equalsIgnoreCase(dto.semester()));

        if (alreadyEnrolled) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Business Rule Violation: Student is already enrolled in this course for the " + dto.semester() + " semester.");
        }

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .semester(dto.semester())
                .build();

        enrollmentRepository.save(enrollment);
    }

    @Transactional
    public void dropEnrollment(Long enrollmentId) {
        if (!enrollmentRepository.existsById(enrollmentId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Enrollment not found");
        }
        enrollmentRepository.deleteById(enrollmentId);
    }
}
