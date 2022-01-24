package com.metadata.schoolregistration.service.impl;

import com.metadata.schoolregistration.entity.CourseEntity;
import com.metadata.schoolregistration.entity.RoleEnum;
import com.metadata.schoolregistration.entity.StudentEntity;
import com.metadata.schoolregistration.entity.UserEntity;
import com.metadata.schoolregistration.exception.MaxNumberOfCoursesAllowedException;
import com.metadata.schoolregistration.exception.MaxNumberOfStudentsAllowedPerCourseException;
import com.metadata.schoolregistration.exception.StudentAlreadyEnrolledException;
import com.metadata.schoolregistration.exception.StudentNotEnrolledException;
import com.metadata.schoolregistration.exception.StudentNotFoundException;
import com.metadata.schoolregistration.model.User;
import com.metadata.schoolregistration.repository.CourseRepository;
import com.metadata.schoolregistration.repository.StudentRepository;
import com.metadata.schoolregistration.service.CourseService;
import com.metadata.schoolregistration.service.StudentService;
import com.metadata.schoolregistration.service.UserService;
import java.util.HashSet;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl implements StudentService {

  private final int MAX_NUMBER_COURSES_ALLOWED;
  private final int MAX_NUMBER_STUDENTS_ALLOWED;
  private final StudentRepository studentRepository;
  private final CourseRepository courseRepository;
  private final CourseService courseService;
  private final UserService userService;

  public StudentServiceImpl(StudentRepository studentRepository, CourseService courseService,
      @Value("${max.number.courses.allowed}") int maxNumberOfCoursesAllowed,
      @Value("${max.number.students.allowed}") int maxNumberOfStudentsAllowed,
      UserService userService,
      CourseRepository courseRepository) {
    this.studentRepository = studentRepository;
    this.courseService = courseService;
    this.MAX_NUMBER_COURSES_ALLOWED = maxNumberOfCoursesAllowed;
    this.MAX_NUMBER_STUDENTS_ALLOWED = maxNumberOfStudentsAllowed;
    this.userService = userService;
    this.courseRepository = courseRepository;
  }

  @Override
  @Transactional
  public StudentEntity enroll(UUID userId, UUID courseId) {
    StudentEntity studentEntity = find(userId);

    if (studentEntity.getCourses().size() >= MAX_NUMBER_COURSES_ALLOWED) {
      throw new MaxNumberOfCoursesAllowedException(String.format("Student %s has max allowed number of enrolled courses", userId));
    }

    CourseEntity courseEntity = courseService.find(courseId);

    if (courseEntity.getStudents().size() >= MAX_NUMBER_STUDENTS_ALLOWED) {
      throw new MaxNumberOfStudentsAllowedPerCourseException(
          String.format("Course %s has the max number allow of students enrolled", courseId));
    }

    if (studentEntity.getCourses().contains(courseEntity)) {
      throw new StudentAlreadyEnrolledException(
          String.format("Student %s-%s is already enrolled in course %s", studentEntity.getUser().getFirstName(),
              studentEntity.getUser().getLastName(), courseEntity.getName()));
    }
    studentEntity.addCourse(courseEntity);

    studentEntity = studentRepository.save(studentEntity);

    return studentEntity;
  }

  @Override
  @Transactional
  public StudentEntity unroll(UUID userId, UUID courseId) {

    StudentEntity studentEntity = find(userId);
    CourseEntity courseEntity = courseService.find(courseId);

    if (!studentEntity.getCourses().contains(courseEntity)) {
      throw new StudentNotEnrolledException(
          String.format("Student %s-%s is not enrolled in course %s", studentEntity.getUser().getFirstName(),
              studentEntity.getUser().getLastName(), courseEntity.getName()));
    }

    studentEntity.removeCourse(courseEntity);

    studentEntity = studentRepository.save(studentEntity);

    return studentEntity;
  }

  @Override
  public Page<StudentEntity> getStudentsEnrolled(UUID courseId, Pageable pageable) {
    return studentRepository.findAllByCourses(courseId, pageable);
  }

  @Override
  public Page<StudentEntity> getStudentsWithoutCourses(Pageable pageable) {
    return studentRepository.findAllByCoursesIsNull(pageable);
  }

  @Override
  @Transactional
  public StudentEntity create(String username, String firstName, String lastName, String email) {

    User user = User.builder()
        .username(username)
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .password(createRandomPassword())
        .role(RoleEnum.USER)
        .build();

    UserEntity userEntity = userService.signUp(user);

    StudentEntity studentEntity = StudentEntity.builder()
        .user(userEntity)
        .courses(new HashSet<>())
        .build();

    studentEntity = studentRepository.save(studentEntity);

    return studentEntity;
  }

  @Override
  @Transactional
  public StudentEntity update(UUID id, String firstName, String lastName, String email) {

    StudentEntity studentEntity = find(id);
    UserEntity userEntity = studentEntity.getUser();
    userEntity.setFirstName(firstName);
    userEntity.setLastName(lastName);
    userEntity.setEmail(email);
    studentEntity.setUser(userEntity);

    studentEntity = studentRepository.save(studentEntity);
    return studentEntity;
  }

  @Override
  @Transactional
  public StudentEntity delete(UUID id) {
    StudentEntity studentEntity = find(id);
    studentRepository.delete(studentEntity);
    return studentEntity;
  }

  @Override
  public StudentEntity find(UUID id) {
    return studentRepository.findById(id)
        .orElseThrow(() -> new StudentNotFoundException(String.format("Student with id %s not found", id)));
  }

  @Override
  public Page<StudentEntity> findAll(Pageable pageable) {
    return studentRepository.findAll(pageable);
  }


  private String createRandomPassword() {
    char[] letters = new char[8];
    int letterValue = 0;

    for (int times = 0; times < letters.length; times++) {
      letterValue = (int) (33 + Math.round(Math.random() * 93));
      letters[times] = (char) letterValue;
    }

    return new String(letters);

  }
}
