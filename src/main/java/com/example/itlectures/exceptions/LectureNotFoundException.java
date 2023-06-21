package com.example.itlectures.exceptions;

public class LectureNotFoundException extends Exception {
  public LectureNotFoundException() {
    super("Lecture not found");
  }
}
