package com.university.edtech.controller;

import com.university.edtech.dto.EnrollmentRequestDto;
import com.university.edtech.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<Void> enrollStudent(@RequestBody EnrollmentRequestDto dto) {
        enrollmentService.enrollStudent(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // Returns 201 Created
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> dropEnrollment(@PathVariable Long id) {
        enrollmentService.dropEnrollment(id);
        return ResponseEntity.noContent().build(); // Returns 204 No Content
    }
}