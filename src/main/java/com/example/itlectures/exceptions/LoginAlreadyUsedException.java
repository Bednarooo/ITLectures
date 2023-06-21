package com.example.itlectures.exceptions;

public class LoginAlreadyUsedException extends Exception {
  public LoginAlreadyUsedException() {
    super("Login already used");
  }
}
