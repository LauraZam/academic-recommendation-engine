package com.university.edtech.service;

import com.university.edtech.dto.CourseRequestDto;
import com.university.edtech.dto.CourseResponseDto;
import com.university.edtech.model.Course;
import com.university.edtech.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final com.university.edtech.repository.EnrollmentRepository enrollmentRepository; // Add this line

    @Transactional
    public CourseResponseDto createCourse(CourseRequestDto dto) {
        Course course = Course.builder()
                .courseCode(dto.courseCode())
                .title(dto.title())
                .description(dto.description())
                .credits(dto.credits())
                .build();

        Course savedCourse = courseRepository.save(course);
        return mapToResponseDto(savedCourse);
    }

    @Transactional(readOnly = true)
    public CourseResponseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));
        return mapToResponseDto(course);
    }

    @Transactional
    public CourseResponseDto updateCourse(Long id, CourseRequestDto dto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));

        course.setCourseCode(dto.courseCode());
        course.setTitle(dto.title());
        course.setDescription(dto.description());
        course.setCredits(dto.credits());

        Course updatedCourse = courseRepository.save(course);
        return mapToResponseDto(updatedCourse);
    }

    @Transactional
    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
        courseRepository.deleteById(id);
    }

    private CourseResponseDto mapToResponseDto(Course course) {
        return new CourseResponseDto(
                course.getId(),
                course.getCourseCode(),
                course.getTitle(),
                course.getCredits()
        );
    }

    @Transactional(readOnly = true)
    public long getCourseEnrollmentCount(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found");
        }
        return enrollmentRepository.countByCourseId(courseId);
    }

    @Transactional(readOnly = true)
    public java.util.List<CourseResponseDto> searchCourses(String keyword) {
        return courseRepository.findByTitleContainingIgnoreCase(keyword).stream()
                .map(this::mapToResponseDto)
                .toList();
    }
}
