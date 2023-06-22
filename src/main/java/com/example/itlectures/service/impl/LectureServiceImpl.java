package com.example.itlectures.service.impl;

import com.example.itlectures.dto.InterestOfLectureDto;
import com.example.itlectures.exceptions.*;
import com.example.itlectures.model.Lecture;
import com.example.itlectures.model.User;
import com.example.itlectures.repository.api.LectureRepository;
import com.example.itlectures.service.api.LectureService;
import com.example.itlectures.service.api.UserService;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.*;

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

  @Override
  public List<InterestOfLectureDto> getInterestOfEachLecture() {
    List<Lecture> lectures = findAll();
    return getInterestOfLectures(lectures);
  }

  @Override
  public List<InterestOfLectureDto> getInterestOfEachLectureAtTheSameTime(LocalDateTime lectureTime) throws LecturesNotFoundAtSpecifiedTimeException {
    List<Lecture> lecturesAtTheSameTime = lectureRepository.findAllByStartTime(lectureTime);
    if (lecturesAtTheSameTime.size() == 0) {
      throw new LecturesNotFoundAtSpecifiedTimeException();
    } else {
      return getInterestOfLectures(lecturesAtTheSameTime);
    }
  }

  private List<InterestOfLectureDto> getInterestOfLectures (List < Lecture > lectures) {
    List<InterestOfLectureDto> interestOfLectures = new ArrayList<>();
    for (Lecture lecture : lectures) {
      interestOfLectures.add(InterestOfLectureDto.builder()
          .title(lecture.getTitle())
          .interest(lecture.getUsers().size() / 5.0)
          .build());
    }
    interestOfLectures.sort((o1, o2) -> {
      if (Objects.equals(o1.getInterest(), o2.getInterest())) {
        return 0;
      }
      return o1.getInterest() > o2.getInterest() ? -1 : 1;
    });
    return interestOfLectures;
  }
}
