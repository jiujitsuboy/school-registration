package com.metadata.schoolregistration.controller;

import com.metadata.schoolregistration.exception.CourseNotFoundException;
import com.metadata.schoolregistration.exception.GenericAlreadyExistsException;
import com.metadata.schoolregistration.exception.InvalidRefreshTokenException;
import com.metadata.schoolregistration.exception.MaxNumberOfCoursesAllowedException;
import com.metadata.schoolregistration.exception.MaxNumberOfStudentsAllowedPerCourseException;
import com.metadata.schoolregistration.exception.StudentAlreadyEnrolledException;
import com.metadata.schoolregistration.exception.StudentNotEnrolledException;
import com.metadata.schoolregistration.exception.StudentNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

  @ExceptionHandler(GenericAlreadyExistsException.class)
  protected ResponseEntity<Object> genericAlreadyExistsException(
      GenericAlreadyExistsException ex, WebRequest request) {

      Map<String, Object> body = new LinkedHashMap<>();
      body.put("timestamp", LocalDateTime.now());
      body.put("message", ex.getMessage());

      return new ResponseEntity<>(body, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(InvalidRefreshTokenException.class)
  protected ResponseEntity<Object> invalidRefreshTokenException(
      InvalidRefreshTokenException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CourseNotFoundException.class)
  protected ResponseEntity<Object> courseNotFoundException(
      CourseNotFoundException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(StudentNotFoundException.class)
  protected ResponseEntity<Object> studentNotFoundException(
      StudentNotFoundException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(InsufficientAuthenticationException.class)
  protected ResponseEntity<Object> insufficientAuthenticationException(
      InsufficientAuthenticationException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  protected ResponseEntity<Object> usernameNotFoundException(
      UsernameNotFoundException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(StudentAlreadyEnrolledException.class)
  protected ResponseEntity<Object> studentAlreadyEnrolledException(
      StudentAlreadyEnrolledException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(StudentNotEnrolledException.class)
  protected ResponseEntity<Object> studentNotEnrolledException(
      StudentNotEnrolledException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MaxNumberOfCoursesAllowedException.class)
  protected ResponseEntity<Object> maxNumberOfCoursesAllowedException(
      MaxNumberOfCoursesAllowedException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MaxNumberOfStudentsAllowedPerCourseException.class)
  protected ResponseEntity<Object> maxNumberOfStudentsAllowedPerCourseException(
      MaxNumberOfStudentsAllowedPerCourseException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Error.class)
  protected ResponseEntity<Object> error(
      Error ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", ex.getClass().getName());

    return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

