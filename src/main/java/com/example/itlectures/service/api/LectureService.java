package com.example.itlectures.service.api;

import com.example.itlectures.exceptions.*;
import com.example.itlectures.model.Lecture;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface LectureService {
  Optional<Lecture> findById(Long id);
  List<Lecture> findAll();
  List<Lecture> findAllByUserLogin(String login) throws UserNotFoundException;
  Lecture createReservation(Long lectureId, String login, String email) throws LectureNotFoundException,
      LectureIsFullException, LoginAlreadyUsedException, UserAlreadyAssignedToLectureAtTheSameTimeException,
      FileNotFoundException;

  Lecture cancelReservation(Long lectureId, String login) throws UserNotFoundException, LectureNotFoundException, UserNotAssignedToLectureException;
}
