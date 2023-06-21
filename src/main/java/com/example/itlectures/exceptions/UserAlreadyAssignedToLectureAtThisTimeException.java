package com.example.itlectures.exceptions;

public class UserAlreadyAssignedToLectureAtThisTimeException extends Exception {
  public UserAlreadyAssignedToLectureAtThisTimeException() {
    super("User is already assigned to lecture at this time");
  }
}
