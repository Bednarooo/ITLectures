package com.example.itlectures.controller;

import com.example.itlectures.dto.CancelReservationDto;
import com.example.itlectures.dto.CreateReservationDto;
import com.example.itlectures.dto.InterestOfLectureDto;
import com.example.itlectures.exceptions.*;
import com.example.itlectures.model.Lecture;
import com.example.itlectures.service.api.LectureService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lecture")
public class LectureController {
  private final LectureService lectureService;
  public LectureController(@Autowired LectureService lectureService) {
    this.lectureService = lectureService;
  }

  @GetMapping("/all")
  public ResponseEntity<List<Lecture>> getAllTutorials() {
    return new ResponseEntity<>(lectureService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/all/login/{login}")
  public ResponseEntity<List<Lecture>> getAllTutorialsByLogin(@PathVariable("login") String login) throws UserNotFoundException {
    return new ResponseEntity<>(lectureService.findAllByUserLogin(login), HttpStatus.OK);
  }

  @PostMapping("/reserve")
  public ResponseEntity<Lecture> createReservation(@RequestBody @Valid CreateReservationDto createReservationDto)
      throws FileNotFoundException, LoginAlreadyUsedException, LectureIsFullException, LectureNotFoundException,
      UserAlreadyAssignedToLectureAtTheSameTimeException {
    return new ResponseEntity<>(lectureService.createReservation(createReservationDto.getLectureId(),
          createReservationDto.getLogin(), createReservationDto.getEmail()), HttpStatus.CREATED);
  }

  @DeleteMapping("/cancel")
  public ResponseEntity<Lecture> cancelReservation(@RequestBody @Valid CancelReservationDto cancelReservationDto)
      throws UserNotFoundException, UserNotAssignedToLectureException, LectureNotFoundException {
    return new ResponseEntity<>(lectureService.cancelReservation(cancelReservationDto.getLectureId(),
        cancelReservationDto.getLogin()), HttpStatus.OK);
  }

  @GetMapping("/all/interest")
  public ResponseEntity<List<InterestOfLectureDto>> getInterestOfEachLecture() {
    return new ResponseEntity<>(lectureService.getInterestOfEachLecture(),
        HttpStatus.OK);
  }

  @GetMapping("all/interest/startTime")
  public ResponseEntity<List<InterestOfLectureDto>> getInterestOfEachLectureAtTheSameTime(@RequestParam("startTime")
                                                                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                                                          LocalDateTime startTime) throws LecturesNotFoundAtSpecifiedTimeException {
      return new ResponseEntity<>(lectureService.getInterestOfEachLectureAtTheSameTime(startTime),
          HttpStatus.OK);
  }

  @ExceptionHandler({UserNotFoundException.class, FileNotFoundException.class, LectureNotFoundException.class,
      LecturesNotFoundAtSpecifiedTimeException.class})
  public ResponseEntity<Map<String, String>> handleNotFoundException(Exception ex) {
    Map<String, String> map = new HashMap<>();
    map.put("error: ", ex.getMessage());
    return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler({LectureIsFullException.class, UserAlreadyAssignedToLectureAtTheSameTimeException.class,
      UserNotAssignedToLectureException.class})
  public ResponseEntity<Map<String, String>> handleBadRequestException(Exception ex) {
    Map<String, String> map = new HashMap<>();
    map.put("error: ", ex.getMessage());
    return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({LoginAlreadyUsedException.class})
  public ResponseEntity<Map<String, String>> handleConflictException(Exception ex) {
    Map<String, String> map = new HashMap<>();
    map.put("error: ", ex.getMessage());
    return new ResponseEntity<>(map, HttpStatus.CONFLICT);
  }
}
