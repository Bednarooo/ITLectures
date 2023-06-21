package com.example.itlectures.exceptions;

public class UserNotAssignedToLectureException extends Exception {
  public UserNotAssignedToLectureException() {
    super("User is not assigned to this lecture");
  }
}
