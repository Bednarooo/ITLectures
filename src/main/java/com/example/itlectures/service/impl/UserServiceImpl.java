package com.example.itlectures.service.impl;

import com.example.itlectures.model.User;
import com.example.itlectures.repository.api.UserRepository;
import com.example.itlectures.service.api.UserService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public UserServiceImpl(@Autowired UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Optional<User> findByLogin(String login) {
    return userRepository.findByLogin(login);
  }
}
