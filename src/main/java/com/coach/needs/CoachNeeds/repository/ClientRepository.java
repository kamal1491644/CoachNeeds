package com.coach.needs.CoachNeeds.repository;

import com.coach.needs.CoachNeeds.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    @Query("select u from Client u where u.trainingType = :trainingType and u.coach.id=:coachId")
    List<Client> findByTrainingType(String trainingType, Integer coachId);
}
    