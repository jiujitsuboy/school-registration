package com.metadata.schoolregistration.exception;

public class StudentAlreadyEnrolledException extends RuntimeException {

  public StudentAlreadyEnrolledException(final String message) {
    super(message);
  }
}
