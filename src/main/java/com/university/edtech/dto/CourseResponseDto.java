package com.university.edtech.dto;

public record CourseResponseDto(
        Long id,
        String courseCode,
        String title,
        Integer credits
) {}