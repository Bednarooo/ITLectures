package com.example.itlectures.exceptions;

public class LectureIsFullException extends Exception {
  public LectureIsFullException() {
    super("Lecture is full");
  }
}
