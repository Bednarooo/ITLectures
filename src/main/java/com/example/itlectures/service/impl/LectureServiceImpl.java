package com.example.itlectures.service.impl;

import com.example.itlectures.exceptions.*;
import com.example.itlectures.model.Lecture;
import com.example.itlectures.model.User;
import com.example.itlectures.repository.api.LectureRepository;
import com.example.itlectures.service.api.LectureService;
import com.example.itlectures.service.api.UserService;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
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

  public Lecture reserveLectureSlot(Long lectureId, String login, String email) throws LectureNotFoundException,
      LectureIsFullException, LoginAlreadyUsedException, UserAlreadyAssignedToLectureAtThisTimeException,
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
            throw new UserAlreadyAssignedToLectureAtThisTimeException();
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
    String startTimeMinutes = String.format("%02d", savedLecture.getStartTime().getMinute());

    LocalDateTime sendDateTime = LocalDateTime.now();
    String sendTimeMinutes = String.format("%02d", sendDateTime.getMinute());
    String notification =
        "Data wysłania: " + sendDateTime.getDayOfMonth()
        + "." + sendDateTime.getMonth() + "." + sendDateTime.getYear() + "r. "
        + sendDateTime.getHour() + ":" + sendTimeMinutes
        + "\nGratulacje " + user.getEmail() + "!"
        + "\nUdało Ci się zapisać na poniższy wykład:"
        + "\nTytuł: " + savedLecture.getTitle()
        + "\nData rozpoczęcia: " + savedLecture.getStartTime().getDayOfMonth()
        + "." + savedLecture.getStartTime().getMonth() + "." + savedLecture.getStartTime().getYear() + "r. "
        + savedLecture.getStartTime().getHour() + ":" + startTimeMinutes
        + "\nData zakończenia: " + savedLecture.getEndTime().getDayOfMonth()
        + "." + savedLecture.getEndTime().getMonth() + "." + savedLecture.getEndTime().getYear() + "r. "
        + savedLecture.getEndTime().getHour() + ":" + savedLecture.getEndTime().getMinute() + "\n\n";
    FileUtils.saveNotificationToFile("powiadomienia.txt", notification);
    return savedLecture;
  }
}
