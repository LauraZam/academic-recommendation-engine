package com.university.edtech.dto;

public record StudentRequestDto(
        String studentNumber,
        String firstName,
        String lastName,
        String email,
        Integer academicYear
) {}
