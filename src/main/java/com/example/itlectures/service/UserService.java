package com.example.itlectures.service;

import com.example.itlectures.exceptions.UserNotFoundException;
import com.example.itlectures.model.User;
import com.example.itlectures.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;

  public Optional<User> findByLogin(String login) {
    return userRepository.findByLogin(login);
  }
}
