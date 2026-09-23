package com.university.edtech.repository;

import com.university.edtech.model.StudentCompetency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentCompetencyRepository extends JpaRepository<StudentCompetency, Long> {
    @Query("SELECT sc FROM StudentCompetency sc JOIN FETCH sc.competency " +
            "WHERE sc.student.id = :studentId ORDER BY sc.score DESC")
    List<StudentCompetency> findTopCompetenciesByStudentId(@Param("studentId") Long studentId);
}
