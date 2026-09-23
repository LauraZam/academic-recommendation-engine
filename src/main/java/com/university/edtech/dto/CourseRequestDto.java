package com.university.edtech.dto;

public record CourseRequestDto(
        String courseCode,
        String title,
        String description,
        Integer credits
) {}