package com.university.edtech.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "competencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Competency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}