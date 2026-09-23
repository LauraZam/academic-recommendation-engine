package com.university.edtech.repository;

import com.university.edtech.model.Competency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompetencyRepository extends JpaRepository<Competency, Long> {

    Optional<Competency> findByName(String name);

    List<Competency> findByCategory(String category);
}