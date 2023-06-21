package com.example.itlectures.controller;

import com.example.itlectures.dto.ReserveLectureSlotDto;
import com.example.itlectures.exceptions.*;
import com.example.itlectures.model.Lecture;
import com.example.itlectures.service.api.LectureService;
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
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/reserve")
  public ResponseEntity<Lecture> reserveLectureSlot(@RequestBody ReserveLectureSlotDto reserveLectureSlotDto) {
    try {
      return new ResponseEntity<>(lectureService.reserveLectureSlot(reserveLectureSlotDto.getLectureId(),
          reserveLectureSlotDto.getLogin(), reserveLectureSlotDto.getEmail()), HttpStatus.OK);
    } catch (LectureNotFoundException lnfe) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    } catch (LectureIsFullException | LoginAlreadyUsedException
             | UserAlreadyAssignedToLectureAtThisTimeException life) {
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    } catch (FileNotFoundException fnfe) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
