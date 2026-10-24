package com.pranil.gymmanagement.gym_man.repository;

import com.pranil.gymmanagement.gym_man.entity.Trainer;
import com.pranil.gymmanagement.gym_man.enums.TrainerStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer,Long> {

    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);

    // For generating TRN0001 code
    Optional<Trainer> findTopByOrderByTrainerIdDesc();

    List<Trainer> findAllByTrainerStatusNot(TrainerStatus trainerStatus);
}
