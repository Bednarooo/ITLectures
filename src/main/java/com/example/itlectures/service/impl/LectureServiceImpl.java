package com.example.itlectures.service.impl;

import com.example.itlectures.exceptions.UserNotFoundException;
import com.example.itlectures.model.Lecture;
import com.example.itlectures.repository.api.LectureRepository;
import com.example.itlectures.service.api.LectureService;
import com.example.itlectures.service.api.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureServiceImpl implements LectureService {
  @Autowired
  private LectureRepository lectureRepository;
  @Autowired
  private UserService userService;

  public Optional<Lecture> findById(Long id) {
    return lectureRepository.findById(id);
  }

  public List<Lecture> findAll() {
    return lectureRepository.findAll();
  }

  public List<Lecture> findAllByUserLogin(String login) throws UserNotFoundException {
    if (userService.findByLogin(login).isPresent()) {
      return lectureRepository.findAllByUsersLogin(login);
    } else {
      throw new UserNotFoundException();
    }
  }
}
