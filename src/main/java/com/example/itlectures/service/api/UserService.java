package com.example.itlectures.service.api;

import com.example.itlectures.exceptions.UserNotFoundException;
import com.example.itlectures.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
  Optional<User> findByLogin(String login);
  User updateEmail(String login, String newEmail) throws UserNotFoundException;
  List<User> findAll();
}
