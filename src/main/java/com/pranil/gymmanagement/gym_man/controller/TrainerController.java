package com.pranil.gymmanagement.gym_man.controller;

import com.pranil.gymmanagement.gym_man.dto.TrainerRequestDto;
import com.pranil.gymmanagement.gym_man.dto.TrainerResponseDto;
import com.pranil.gymmanagement.gym_man.service.interfaces.TrainerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public ResponseEntity<TrainerResponseDto> addTrainer(@Valid @RequestBody TrainerRequestDto requestDto) {
        TrainerResponseDto responseDto = trainerService.addTrainer(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<TrainerResponseDto>> getAllTrainers() {
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @GetMapping("/{trainerId}")
    public ResponseEntity<TrainerResponseDto> getTrainerById(@PathVariable Long trainerId) {
        return ResponseEntity.ok(trainerService.getTrainerById(trainerId));
    }

    @PutMapping("/{trainerId}")
    public ResponseEntity<TrainerResponseDto> updateTrainer(@PathVariable Long trainerId,
                                                            @Valid @RequestBody TrainerRequestDto requestDto) {
        return ResponseEntity.ok(trainerService.updateTrainer(trainerId, requestDto));
    }

    @DeleteMapping("/{trainerId}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable Long trainerId) {
        trainerService.deleteTrainer(trainerId);
        return ResponseEntity.noContent().build();
    }
}