package com.example.itlectures.controller;

import com.example.itlectures.dto.UpdateEmailDto;
import com.example.itlectures.exceptions.LectureNotFoundException;
import com.example.itlectures.exceptions.LecturesNotFoundAtSpecifiedTimeException;
import com.example.itlectures.exceptions.UserNotFoundException;
import com.example.itlectures.model.User;
import com.example.itlectures.service.api.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
  private final UserService userService;

  public UserController(@Autowired UserService userService) {
    this.userService = userService;
  }

  @PutMapping("/updateEmail")
  public ResponseEntity<User> updateEmail(@RequestBody @Valid UpdateEmailDto updateEmailDto) throws UserNotFoundException {
    return new ResponseEntity<>(userService.updateEmail(updateEmailDto.getLogin(),
        updateEmailDto.getNewEmail()), HttpStatus.OK);
  }

  @GetMapping("/all")
  public ResponseEntity<List<User>> findAll() {
    return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
  }

  @ExceptionHandler({UserNotFoundException.class})
  public ResponseEntity<Map<String, String>> handleNotFoundException(Exception ex) {
    Map<String, String> map = new HashMap<>();
    map.put("error: ", ex.getMessage());
    return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
  }
}
