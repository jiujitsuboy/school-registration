package com.metadata.schoolregistration.exception;

public class InvalidRefreshTokenException extends RuntimeException {

  public InvalidRefreshTokenException(final String message) {
    super(message);
  }
}
