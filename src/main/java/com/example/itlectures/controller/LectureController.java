package com.example.itlectures.controller;

import com.example.itlectures.dto.CancelReservationDto;
import com.example.itlectures.dto.CreateReservationDto;
import com.example.itlectures.dto.InterestOfLectureDto;
import com.example.itlectures.exceptions.*;
import com.example.itlectures.model.Lecture;
import com.example.itlectures.service.api.LectureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/lecture")
public class LectureController {
  private final LectureService lectureService;
  public LectureController(@Autowired LectureService lectureService) {
    this.lectureService = lectureService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<Lecture>> getAllTutorials() {
    try {
      return new ResponseEntity<>(lectureService.findAll(), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/all/login/{login}")
  public ResponseEntity<List<Lecture>> getAllTutorialsByLogin(@PathVariable("login") String login) {
    try {
      return new ResponseEntity<>(lectureService.findAllByUserLogin(login), HttpStatus.OK);
    } catch (UserNotFoundException unfe) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/reserve")
  public ResponseEntity<Lecture> createReservation(@RequestBody @Valid CreateReservationDto createReservationDto) {
    try {
      return new ResponseEntity<>(lectureService.createReservation(createReservationDto.getLectureId(),
          createReservationDto.getLogin(), createReservationDto.getEmail()), HttpStatus.CREATED);
    } catch (LectureNotFoundException lnfe) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (LectureIsFullException | LoginAlreadyUsedException
             | UserAlreadyAssignedToLectureAtTheSameTimeException life) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    } catch (FileNotFoundException fnfe) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/cancel")
  public ResponseEntity<Lecture> cancelReservation(@RequestBody @Valid CancelReservationDto cancelReservationDto) {
    try {
      return new ResponseEntity<>(lectureService.cancelReservation(cancelReservationDto.getLectureId(),
          cancelReservationDto.getLogin()), HttpStatus.OK);
    } catch (LectureNotFoundException | UserNotFoundException nfe) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (UserNotAssignedToLectureException bre) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/all/interest")
  public ResponseEntity<List<InterestOfLectureDto>> getInterestOfEachLecture() {
    return new ResponseEntity<>(lectureService.getInterestOfEachLecture(),
        HttpStatus.OK);
  }
}
