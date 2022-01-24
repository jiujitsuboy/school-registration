package com.metadata.schoolregistration.exception;

public class StudentNotEnrolledException extends RuntimeException {

  public StudentNotEnrolledException(final String message) {
    super(message);
  }
}
