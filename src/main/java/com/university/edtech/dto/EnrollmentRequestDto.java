package com.university.edtech.dto;

public record EnrollmentRequestDto(
        Long studentId,
        Long courseId,
        String semester
) {}
