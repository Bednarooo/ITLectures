package com.example.itlectures.service.impl;

import com.example.itlectures.exceptions.*;
import com.example.itlectures.model.Lecture;
import com.example.itlectures.model.User;
import com.example.itlectures.repository.api.LectureRepository;
import com.example.itlectures.service.api.LectureService;
import com.example.itlectures.service.api.UserService;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import com.example.itlectures.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LectureServiceImpl implements LectureService {
  private final LectureRepository lectureRepository;
  private final UserService userService;

  public LectureServiceImpl(@Autowired LectureRepository lectureRepository,
                            @Autowired UserService userService) {
    this.lectureRepository = lectureRepository;
    this.userService = userService;
  }

  @Override
  public Optional<Lecture> findById(Long id) {
    return lectureRepository.findById(id);
  }

  @Override
  public List<Lecture> findAll() {
    return lectureRepository.findAll();
  }

  @Override
  public List<Lecture> findAllByUserLogin(String login) throws UserNotFoundException {
    if (userService.findByLogin(login).isPresent()) {
      return lectureRepository.findAllByUsersLogin(login);
    } else {
      throw new UserNotFoundException();
    }
  }

  @Override
  public Lecture createReservation(Long lectureId, String login, String email) throws LectureNotFoundException,
      LectureIsFullException, LoginAlreadyUsedException, UserAlreadyAssignedToLectureAtTheSameTimeException,
      FileNotFoundException {
    Lecture lecture = findById(lectureId).orElseThrow(LectureNotFoundException::new);
    Set<User> assignedUsers = lecture.getUsers();

    if (assignedUsers.size() == 5) {
      throw new LectureIsFullException();
    }
    User user;

    Optional<User> optionalUser = userService.findByLogin(login);
    if (optionalUser.isPresent()) {
      user = optionalUser.get();
      if (!Objects.equals(user.getEmail(), email)) {
        throw new LoginAlreadyUsedException();
      } else {
        List<Lecture> lecturesWithSameStartTime = lectureRepository.findAllByStartTime(lecture.getStartTime());
        for (Lecture parallelLecture : lecturesWithSameStartTime) {
          if (parallelLecture.getUsers().contains(user)) {
            throw new UserAlreadyAssignedToLectureAtTheSameTimeException();
          }
        }
        lecture.getUsers().add(user);
      }
    } else {
      user = User.builder()
          .login(login)
          .email(email)
          .build();

      lecture.getUsers().add(user);
    }
    Lecture savedLecture = lectureRepository.save(lecture);
    FileUtils.saveNotificationToFile("powiadomienia.txt", savedLecture, user);
    return savedLecture;
  }

  @Override
  public Lecture cancelReservation(Long lectureId, String login) throws UserNotFoundException, LectureNotFoundException, UserNotAssignedToLectureException {
    Lecture lecture = findById(lectureId).orElseThrow(LectureNotFoundException::new);
    User user = userService.findByLogin(login).orElseThrow(UserNotFoundException::new);
    List<Lecture> userLectures = findAllByUserLogin(login);
    if (userLectures.contains(lecture)) {
      for (Lecture userLecture : userLectures) {
        if (userLecture.getId().equals(lectureId)) {
          userLecture.getUsers().remove(user);
          return lectureRepository.save(userLecture);
        }
      }
    }
    throw new UserNotAssignedToLectureException();
  }


}
