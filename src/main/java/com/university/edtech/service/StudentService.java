package com.university.edtech.service;

import com.university.edtech.dto.CourseResponseDto;
import com.university.edtech.dto.StudentRequestDto;
import com.university.edtech.dto.StudentResponseDto;
import com.university.edtech.model.Student;
import com.university.edtech.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public StudentResponseDto createStudent(StudentRequestDto dto) {
        Student student = Student.builder()
                .studentNumber(dto.studentNumber())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .email(dto.email())
                .academicYear(dto.academicYear())
                .build();

        Student savedStudent = studentRepository.save(student);
        return mapToResponseDto(savedStudent);
    }

    @Transactional(readOnly = true)
    public StudentResponseDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        return mapToResponseDto(student);
    }

    @Transactional
    public StudentResponseDto updateStudent(Long id, StudentRequestDto dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        student.setStudentNumber(dto.studentNumber());
        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setEmail(dto.email());
        student.setAcademicYear(dto.academicYear());

        Student updatedStudent = studentRepository.save(student);
        return mapToResponseDto(updatedStudent);
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found");
        }
        studentRepository.deleteById(id);
    }

    // Helper method to keep entity-to-DTO conversion clean
    private StudentResponseDto mapToResponseDto(Student student) {
        return new StudentResponseDto(
                student.getId(),
                student.getStudentNumber(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getAcademicYear()
        );
    }

    @Transactional(readOnly = true)
    public List<CourseResponseDto> getStudentSchedule(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));

        return student.getEnrollments().stream()
                .map(enrollment -> {
                    var course = enrollment.getCourse();
                    return new CourseResponseDto(
                            course.getId(),
                            course.getCourseCode(),
                            course.getTitle(),
                            course.getCredits()
                    );
                })
                .toList();
    }
}