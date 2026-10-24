package com.pranil.gymmanagement.gym_man.service.impl;

import com.pranil.gymmanagement.gym_man.dto.TrainerRequestDto;
import com.pranil.gymmanagement.gym_man.dto.TrainerResponseDto;
import com.pranil.gymmanagement.gym_man.entity.Trainer;
import com.pranil.gymmanagement.gym_man.enums.TrainerStatus;
import com.pranil.gymmanagement.gym_man.exception.DuplicateResourceException;
import com.pranil.gymmanagement.gym_man.exception.ResourceNotFoundException;
import com.pranil.gymmanagement.gym_man.repository.TrainerRepository;
import com.pranil.gymmanagement.gym_man.service.interfaces.TrainerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    private void validateDuplicateData(TrainerRequestDto requestDto) {
        if (trainerRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new DuplicateResourceException("Phone number already exists.");
        }
        if (trainerRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }
    }

    private String generateTrainerCode() {
        Optional<Trainer> lastTrainer = trainerRepository.findTopByOrderByTrainerIdDesc();
        if (lastTrainer.isEmpty()) {
            return "TRN0001";
        }
        Long nextId = lastTrainer.get().getTrainerId() + 1;
        return String.format("TRN%04d", nextId);
    }

    @Override
    @Transactional
    public TrainerResponseDto addTrainer(TrainerRequestDto requestDto) {
        validateDuplicateData(requestDto);

        Trainer trainer = new Trainer();
        trainer.setFirstName(requestDto.getFirstName());
        trainer.setLastName(requestDto.getLastName());
        trainer.setGender(requestDto.getGender());
        trainer.setDateOfBirth(requestDto.getDateOfBirth());
        trainer.setPhoneNumber(requestDto.getPhoneNumber());
        trainer.setEmail(requestDto.getEmail());
        trainer.setLocality(requestDto.getLocality());
        trainer.setPinCode(requestDto.getPinCode());
        trainer.setProfilePhotoUrl(requestDto.getProfilePhotoUrl());
        trainer.setSpecialization(requestDto.getSpecialization());
        trainer.setExperience(requestDto.getExperience());
        trainer.setSalary(requestDto.getSalary());

        // Business Logic defaults
        trainer.setJoiningDate(LocalDate.now());
        trainer.setTrainerStatus(TrainerStatus.ACTIVE);
        trainer.setTrainerCode(generateTrainerCode());

        Trainer savedTrainer = trainerRepository.save(trainer);
        return mapToResponseDto(savedTrainer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainerResponseDto> getAllTrainers() {
        // Hiding INACTIVE trainers to act as our soft delete based on your Enum
        List<Trainer> trainers = trainerRepository.findAllByTrainerStatusNot(TrainerStatus.INACTIVE);
        List<TrainerResponseDto> response = new ArrayList<>();
        for (Trainer trainer : trainers) {
            response.add(mapToResponseDto(trainer));
        }
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public TrainerResponseDto getTrainerById(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + trainerId));

        if (trainer.getTrainerStatus() == TrainerStatus.INACTIVE) {
            throw new ResourceNotFoundException("Trainer not found with id: " + trainerId);
        }
        return mapToResponseDto(trainer);
    }

    @Override
    @Transactional
    public TrainerResponseDto updateTrainer(Long trainerId, TrainerRequestDto requestDto) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + trainerId));

        if (trainer.getTrainerStatus() == TrainerStatus.INACTIVE) {
            throw new ResourceNotFoundException("Trainer not found with id: " + trainerId);
        }

        // Validate duplicates only if fields changed
        if (!trainer.getPhoneNumber().equals(requestDto.getPhoneNumber()) &&
                trainerRepository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new DuplicateResourceException("Phone number already exists.");
        }
        if (!trainer.getEmail().equals(requestDto.getEmail()) &&
                trainerRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateResourceException("Email already exists.");
        }

        trainer.setFirstName(requestDto.getFirstName());
        trainer.setLastName(requestDto.getLastName());
        trainer.setGender(requestDto.getGender());
        trainer.setDateOfBirth(requestDto.getDateOfBirth());
        trainer.setPhoneNumber(requestDto.getPhoneNumber());
        trainer.setEmail(requestDto.getEmail());
        trainer.setLocality(requestDto.getLocality());
        trainer.setPinCode(requestDto.getPinCode());
        trainer.setProfilePhotoUrl(requestDto.getProfilePhotoUrl());
        trainer.setSpecialization(requestDto.getSpecialization());
        trainer.setExperience(requestDto.getExperience());
        trainer.setSalary(requestDto.getSalary());

        Trainer updatedTrainer = trainerRepository.save(trainer);
        return mapToResponseDto(updatedTrainer);
    }

    @Override
    @Transactional
    public void deleteTrainer(Long trainerId) {
        Trainer trainer = trainerRepository.findById(trainerId)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer not found with id: " + trainerId));

        trainer.setTrainerStatus(TrainerStatus.INACTIVE);
        trainerRepository.save(trainer);
    }

    private TrainerResponseDto mapToResponseDto(Trainer trainer) {
        TrainerResponseDto dto = new TrainerResponseDto();
        dto.setTrainerId(trainer.getTrainerId());
        dto.setTrainerCode(trainer.getTrainerCode());
        dto.setFirstName(trainer.getFirstName());
        dto.setLastName(trainer.getLastName());
        dto.setGender(trainer.getGender());
        dto.setDateOfBirth(trainer.getDateOfBirth());
        dto.setPhoneNumber(trainer.getPhoneNumber());
        dto.setEmail(trainer.getEmail());
        dto.setLocality(trainer.getLocality());
        dto.setPinCode(trainer.getPinCode());
        dto.setProfilePhotoUrl(trainer.getProfilePhotoUrl());
        dto.setSpecialization(trainer.getSpecialization());
        dto.setExperience(trainer.getExperience());
        dto.setSalary(trainer.getSalary());
        dto.setJoiningDate(trainer.getJoiningDate());
        dto.setTrainerStatus(trainer.getTrainerStatus());
        return dto;
    }
}
