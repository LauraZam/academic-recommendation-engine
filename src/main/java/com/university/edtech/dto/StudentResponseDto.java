package com.university.edtech.dto;

public record StudentResponseDto(
        Long id,
        String studentNumber,
        String firstName,
        String lastName,
        String email,
        Integer academicYear
) {}
