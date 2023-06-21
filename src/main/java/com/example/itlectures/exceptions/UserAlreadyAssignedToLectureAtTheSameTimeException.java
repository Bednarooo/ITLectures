package com.example.itlectures.exceptions;

public class UserAlreadyAssignedToLectureAtTheSameTimeException extends Exception {
  public UserAlreadyAssignedToLectureAtTheSameTimeException() {
    super("User is already assigned to lecture at this time");
  }
}
