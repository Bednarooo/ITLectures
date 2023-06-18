package com.example.itlectures.service.api;

import com.example.itlectures.exceptions.UserNotFoundException;
import com.example.itlectures.model.Lecture;
import java.util.List;
import java.util.Optional;

public interface LectureService {
  Optional<Lecture> findById(Long id);
  List<Lecture> findAll();
  List<Lecture> findAllByUserLogin(String login) throws UserNotFoundException;
}
