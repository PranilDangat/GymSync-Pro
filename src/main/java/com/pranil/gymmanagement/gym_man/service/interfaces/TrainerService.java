package com.pranil.gymmanagement.gym_man.service.interfaces;

import com.pranil.gymmanagement.gym_man.dto.TrainerRequestDto;
import com.pranil.gymmanagement.gym_man.dto.TrainerResponseDto;

import java.util.List;

public interface TrainerService {

    TrainerResponseDto addTrainer(TrainerRequestDto requestDto);
    List<TrainerResponseDto> getAllTrainers();
    TrainerResponseDto getTrainerById(Long trainerId);
    TrainerResponseDto updateTrainer(Long trainerId, TrainerRequestDto requestDto);
    void deleteTrainer(Long trainerId);
}
