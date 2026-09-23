package com.university.edtech.repository;

import com.university.edtech.model.RecommendationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationLogRepository extends JpaRepository<RecommendationLog, Long> {

    @Query("SELECT rl FROM RecommendationLog rl JOIN FETCH rl.recommendedCourse " +
            "WHERE rl.student.id = :studentId ORDER BY rl.confidenceScore DESC")
    List<RecommendationLog> findTopRecommendationsByStudentId(@Param("studentId") Long studentId);
}