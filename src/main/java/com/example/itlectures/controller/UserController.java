package com.example.itlectures.controller;

import com.example.itlectures.dto.UpdateEmailDto;
import com.example.itlectures.exceptions.UserNotFoundException;
import com.example.itlectures.model.User;
import com.example.itlectures.service.api.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
  private final UserService userService;

  public UserController(@Autowired UserService userService) {
    this.userService = userService;
  }

  @PutMapping("/updateEmail")
  public ResponseEntity<User> updateEmail(@RequestBody @Valid UpdateEmailDto updateEmailDto) {
    try {
      return new ResponseEntity<>(userService.updateEmail(updateEmailDto.getLogin(),
          updateEmailDto.getNewEmail()), HttpStatus.OK);
    } catch (UserNotFoundException nfe) {
      return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/all")
  public ResponseEntity<List<User>> findAll() {
    return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
  }
}
