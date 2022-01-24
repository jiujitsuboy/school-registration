package com.metadata.schoolregistration.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.metadata.schoolregistration.TestConstants;
import com.metadata.schoolregistration.entity.CourseEntity;
import com.metadata.schoolregistration.exception.GenericAlreadyExistsException;
import com.metadata.schoolregistration.repository.CourseRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

  @Mock
  private CourseRepository courseRepository;
  @InjectMocks
  private CourseServiceImpl classUnderTest;

  @Test
  public void getStudentCourses() {
    UUID courseId = UUID.randomUUID();
    UUID studentId = UUID.randomUUID();
    String courseName = "math";
    int numPage = 0;
    int size = 2;

    Page page = new PageImpl(List.of(TestConstants.getTestCourseEntity(courseId, courseName + 1),
        TestConstants.getTestCourseEntity(UUID.randomUUID(), courseName + 2)));
    when(courseRepository.findCoursesByStudentId(any(UUID.class), any(Pageable.class))).thenReturn(page);

    Page<CourseEntity> courseEntityPage = classUnderTest.getStudentCourses(studentId, PageRequest.of(numPage, size));
    assertNotNull(courseEntityPage);
    assertEquals(courseEntityPage.getNumber(), numPage);
    assertEquals(courseEntityPage.getSize(), size);
  }

  @Test
  public void getCoursesWithNoStudents() {
    UUID courseId = UUID.randomUUID();
    String courseName = "math";
    int numPage = 0;
    int size = 2;

    Page page = new PageImpl(List.of(TestConstants.getTestCourseEntity(courseId, courseName + 1),
        TestConstants.getTestCourseEntity(UUID.randomUUID(), courseName + 2)));
    when(courseRepository.findCourseEntitiesByStudentsNull(any(Pageable.class))).thenReturn(page);

    Page<CourseEntity> courseEntityPage = classUnderTest.getCoursesWithNoStudents(PageRequest.of(numPage, size));
    assertNotNull(courseEntityPage);
    assertEquals(courseEntityPage.getNumber(), numPage);
    assertEquals(courseEntityPage.getSize(), size);
  }

  @Test
  public void create() {

    UUID courseId = UUID.randomUUID();
    String courseName = "math";

    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);
    when(courseRepository.findByName(anyString())).thenReturn(Optional.empty());
    when(courseRepository.save(any(CourseEntity.class))).thenReturn(courseEntity);

    CourseEntity courseEntityResult = classUnderTest.create(courseName);

    assertNotNull(courseEntityResult);
    assertEquals(courseEntityResult.getName(), courseName);

  }

  @Test
  public void createGenericAlreadyExistsException() {

    UUID courseId = UUID.randomUUID();
    String courseName = "math";

    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);
    when(courseRepository.findByName(anyString())).thenReturn(Optional.of(courseEntity));

    assertThrows(GenericAlreadyExistsException.class, () -> classUnderTest.create(courseName));

  }

  @Test
  public void update() {

    UUID courseId = UUID.randomUUID();
    String courseName = "math";
    String courseNameUpdated = "biology";

    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);
    when(courseRepository.findById(any(UUID.class))).thenReturn(Optional.of(courseEntity));
    when(courseRepository.save(any(CourseEntity.class))).thenReturn(courseEntity);

    CourseEntity courseEntityResult = classUnderTest.update(courseId, courseNameUpdated);

    assertNotNull(courseEntityResult);
    assertEquals(courseEntityResult.getName(), courseNameUpdated);

  }

  @Test
  public void delete() {

    UUID courseId = UUID.randomUUID();
    String courseName = "math";

    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);
    when(courseRepository.findById(any(UUID.class))).thenReturn(Optional.of(courseEntity));
    doNothing().when(courseRepository).delete(any(CourseEntity.class));

    CourseEntity courseEntityResult = classUnderTest.delete(courseId);

    assertNotNull(courseEntityResult);
    assertEquals(courseEntityResult.getName(), courseName);

  }

  @Test
  public void find() {
    UUID courseId = UUID.randomUUID();
    String courseName = "math";

    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);
    when(courseRepository.findById(any(UUID.class))).thenReturn(Optional.of(courseEntity));

    CourseEntity courseEntityResult = classUnderTest.find(courseId);
    assertNotNull(courseEntityResult);
    assertEquals(courseEntityResult.getName(), courseName);
  }

  @Test
  public void findAll() {
    UUID courseId = UUID.randomUUID();
    String courseName = "math";
    int numPage = 0;
    int size = 2;

    Page page = new PageImpl(List.of(TestConstants.getTestCourseEntity(courseId, courseName + 1),
        TestConstants.getTestCourseEntity(UUID.randomUUID(), courseName + 2)));
    when(courseRepository.findAll(any(Pageable.class))).thenReturn(page);

    Page<CourseEntity> courseEntityPage = classUnderTest.findAll(PageRequest.of(numPage, size));
    assertNotNull(courseEntityPage);
    assertEquals(courseEntityPage.getNumber(), numPage);
    assertEquals(courseEntityPage.getSize(), size);
  }
}