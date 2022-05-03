package com.code.review.CodeReview.repository;

import com.code.review.CodeReview.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Integer> {
    @Query("select u from Coach u where u.subjectId = :subjectId")
    Coach findBySubjectId(String subjectId);
}
