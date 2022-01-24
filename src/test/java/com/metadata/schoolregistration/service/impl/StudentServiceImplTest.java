package com.metadata.schoolregistration.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.metadata.schoolregistration.TestConstants;
import com.metadata.schoolregistration.entity.CourseEntity;
import com.metadata.schoolregistration.entity.StudentEntity;
import com.metadata.schoolregistration.entity.UserEntity;
import com.metadata.schoolregistration.exception.MaxNumberOfCoursesAllowedException;
import com.metadata.schoolregistration.exception.MaxNumberOfStudentsAllowedPerCourseException;
import com.metadata.schoolregistration.exception.StudentAlreadyEnrolledException;
import com.metadata.schoolregistration.exception.StudentNotEnrolledException;
import com.metadata.schoolregistration.model.User;
import com.metadata.schoolregistration.repository.CourseRepository;
import com.metadata.schoolregistration.repository.StudentRepository;
import com.metadata.schoolregistration.service.CourseService;
import com.metadata.schoolregistration.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

  @Mock
  private StudentRepository studentRepository;
  @Mock
  private CourseRepository courseRepository;
  @Mock
  private UserService userService;
  @Mock
  private CourseService courseService;

  private final int MAX_NUMBER_COURSES_ALLOWED = 5;
  private final int MAX_NUMBER_STUDENTS_ALLOWED = 50;

  private StudentServiceImpl classUnderTest;

  @BeforeEach
  public void init() {
    classUnderTest = new StudentServiceImpl(studentRepository, courseService, MAX_NUMBER_COURSES_ALLOWED, MAX_NUMBER_STUDENTS_ALLOWED,
        userService, courseRepository);
  }

  @Test
  public void enroll() {

    UUID courseId = UUID.randomUUID();
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    String courseName = "math";

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);
    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);

    when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.of(studentEntity));
    when(courseService.find(any(UUID.class))).thenReturn(courseEntity);
    when(studentRepository.save(any(StudentEntity.class))).thenReturn(studentEntity);

    StudentEntity studentEntityResult = classUnderTest.enroll(studentId, courseId);

    assertNotNull(studentEntityResult);
    assertEquals(studentEntityResult.getCourses().size(), 1);

  }


  @Test
  public void enrollMaxNumberOfCoursesAllowedException() {

    UUID courseId = UUID.randomUUID();
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    String courseName = "math";

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);
    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);

    studentEntity.getCourses().add(courseEntity);

    IntStream.range(0, 4).forEach(value ->
        studentEntity.getCourses().add(TestConstants.getTestCourseEntity(UUID.randomUUID(), courseName + value)));

    when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.of(studentEntity));

    assertThrows(MaxNumberOfCoursesAllowedException.class, () -> classUnderTest.enroll(studentId, courseId));

  }

  @Test
  public void enrollMaxNumberOfStudentsAllowedPerCourseException() {
    UUID courseId = UUID.randomUUID();
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    String courseName = "math";

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);
    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);

    courseEntity.getStudents().add(studentEntity);

    IntStream.range(0, 49).forEach(value ->
        courseEntity.getStudents().add(TestConstants.getTestStudentEntityA(UUID.randomUUID(), UUID.randomUUID())));

    when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.of(studentEntity));
    when(courseService.find(any(UUID.class))).thenReturn(courseEntity);

    assertThrows(MaxNumberOfStudentsAllowedPerCourseException.class, () -> classUnderTest.enroll(studentId, courseId));
  }

  @Test
  public void enrollStudentAlreadyEnrolledException() {
    UUID courseId = UUID.randomUUID();
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    String courseName = "math";

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);
    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);

    studentEntity.getCourses().add(courseEntity);

    when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.of(studentEntity));
    when(courseService.find(any(UUID.class))).thenReturn(courseEntity);

    assertThrows(StudentAlreadyEnrolledException.class, () -> classUnderTest.enroll(studentId, courseId));
  }

  @Test
  public void unroll() {
    UUID courseId = UUID.randomUUID();
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    String courseName = "math";

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);
    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);

    studentEntity.getCourses().add(courseEntity);

    when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.of(studentEntity));
    when(courseService.find(any(UUID.class))).thenReturn(courseEntity);
    when(studentRepository.save(any(StudentEntity.class))).thenReturn(studentEntity);

    StudentEntity studentEntityResult = classUnderTest.unroll(studentId, courseId);

    assertNotNull(studentEntityResult);
    assertEquals(studentEntityResult.getCourses().size(), 0);
  }

  @Test
  public void unrollStudentNotEnrolledException() {
    UUID courseId = UUID.randomUUID();
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    String courseName = "math";

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);
    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);

    when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.of(studentEntity));
    when(courseService.find(any(UUID.class))).thenReturn(courseEntity);

    assertThrows(StudentNotEnrolledException.class, () -> classUnderTest.unroll(studentId, courseId));
  }

  @Test
  public void getStudentsEnrolled() {
    UUID courseId = UUID.randomUUID();
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    int numPage = 0;
    int size = 2;

    Page page = new PageImpl(List.of(TestConstants.getTestStudentEntityA(studentId, userId),
        TestConstants.getTestStudentEntityB(UUID.randomUUID(), UUID.randomUUID())));
    when(studentRepository.findAllByCourses(any(UUID.class), any(Pageable.class))).thenReturn(page);

    Page<StudentEntity> studentEntityPage = classUnderTest.getStudentsEnrolled(courseId, PageRequest.of(numPage, size));
    assertNotNull(studentEntityPage);
    assertEquals(studentEntityPage.getNumber(), numPage);
    assertEquals(studentEntityPage.getSize(), size);
  }

  @Test
  public void getStudentsWithoutCourses() {
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    int numPage = 0;
    int size = 2;

    Page page = new PageImpl(List.of(TestConstants.getTestStudentEntityA(studentId, userId),
        TestConstants.getTestStudentEntityB(UUID.randomUUID(), UUID.randomUUID())));
    when(studentRepository.findAllByCoursesIsNull(any(Pageable.class))).thenReturn(page);

    Page<StudentEntity> studentEntityPage = classUnderTest.getStudentsWithoutCourses(PageRequest.of(numPage, size));
    assertNotNull(studentEntityPage);
    assertEquals(studentEntityPage.getNumber(), numPage);
    assertEquals(studentEntityPage.getSize(), size);
  }

  @Test
  public void create() {

    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);
    UserEntity userEntity = TestConstants.getTestUserEntity(userId, TestConstants.USER_NAME_A, TestConstants.USER_PASSWORD_A,
        TestConstants.USER_FIRST_NAME_A, TestConstants.USER_LAST_NAME_A, TestConstants.USER_EMAIL_A);

    when(userService.signUp(any(User.class))).thenReturn(userEntity);
    when(studentRepository.save(any(StudentEntity.class))).thenReturn(studentEntity);

    StudentEntity studentEntityResult = classUnderTest.create(TestConstants.USER_NAME_A,
        TestConstants.USER_FIRST_NAME_A, TestConstants.USER_LAST_NAME_A, TestConstants.USER_EMAIL_A);

    assertNotNull(studentEntityResult);
    assertEquals(studentEntityResult.getUser().getUsername(), TestConstants.USER_NAME_A);
    assertEquals(studentEntityResult.getUser().getFirstName(), TestConstants.USER_FIRST_NAME_A);
    assertEquals(studentEntityResult.getUser().getLastName(), TestConstants.USER_LAST_NAME_A);
    assertEquals(studentEntityResult.getUser().getEmail(), TestConstants.USER_EMAIL_A);

  }

  @Test
  public void update() {

    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);

    when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.of(studentEntity));
    when(studentRepository.save(any(StudentEntity.class))).thenReturn(studentEntity);

    StudentEntity studentEntityResult = classUnderTest.update(studentId, TestConstants.USER_FIRST_NAME_B, TestConstants.USER_LAST_NAME_B,
        TestConstants.USER_EMAIL_B);

    assertNotNull(studentEntityResult);
    assertEquals(studentEntityResult.getUser().getUsername(), TestConstants.USER_NAME_A);
    assertEquals(studentEntityResult.getUser().getFirstName(), TestConstants.USER_FIRST_NAME_B);
    assertEquals(studentEntityResult.getUser().getLastName(), TestConstants.USER_LAST_NAME_B);
    assertEquals(studentEntityResult.getUser().getEmail(), TestConstants.USER_EMAIL_B);

  }

  @Test
  public void delete(){

    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);

    when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.of(studentEntity));
    doNothing().when(studentRepository).delete(any(StudentEntity.class));

    StudentEntity studentEntityResult = classUnderTest.delete(studentId);

    assertNotNull(studentEntityResult);
    assertEquals(studentEntityResult.getUser().getUsername(), TestConstants.USER_NAME_A);
    assertEquals(studentEntityResult.getUser().getFirstName(), TestConstants.USER_FIRST_NAME_A);
    assertEquals(studentEntityResult.getUser().getLastName(), TestConstants.USER_LAST_NAME_A);
    assertEquals(studentEntityResult.getUser().getEmail(), TestConstants.USER_EMAIL_A);

  }

  @Test
  public void find(){
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);
    when(studentRepository.findById(any(UUID.class))).thenReturn(Optional.of(studentEntity));

    StudentEntity studentEntityResult = classUnderTest.find(studentId);

    assertNotNull(studentEntityResult);
    assertEquals(studentEntityResult.getUser().getUsername(), TestConstants.USER_NAME_A);
    assertEquals(studentEntityResult.getUser().getFirstName(), TestConstants.USER_FIRST_NAME_A);
    assertEquals(studentEntityResult.getUser().getLastName(), TestConstants.USER_LAST_NAME_A);
    assertEquals(studentEntityResult.getUser().getEmail(), TestConstants.USER_EMAIL_A);
  }

  @Test
  public void findAll() {
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    int numPage = 0;
    int size = 2;

    Page page = new PageImpl(List.of(TestConstants.getTestStudentEntityA(studentId, userId),
        TestConstants.getTestStudentEntityB(UUID.randomUUID(), UUID.randomUUID())));
    when(studentRepository.findAll(any(Pageable.class))).thenReturn(page);

    Page<StudentEntity> studentEntityPage = classUnderTest.findAll(PageRequest.of(numPage, size));
    assertNotNull(studentEntityPage);
    assertEquals(studentEntityPage.getNumber(), numPage);
    assertEquals(studentEntityPage.getSize(), size);
  }
}