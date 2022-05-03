package com.code.review.CodeReview.repository;

import com.code.review.CodeReview.model.Client;
import com.code.review.CodeReview.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query("select u from Client u where u.trainingType = :trainingType")
    Client findByTrainingType(String trainingType);
}
