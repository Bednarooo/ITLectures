package com.example.itlectures.exceptions;

public class LecturesNotFoundAtSpecifiedTimeException extends Exception {
  public LecturesNotFoundAtSpecifiedTimeException() {
    super("There are no lectures at specified time");
  }
}
