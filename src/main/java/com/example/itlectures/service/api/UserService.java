package com.example.itlectures.service.api;

import com.example.itlectures.model.User;
import java.util.Optional;

public interface UserService {
  Optional<User> findByLogin(String login);
}
