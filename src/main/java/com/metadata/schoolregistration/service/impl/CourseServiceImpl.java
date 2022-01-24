package com.metadata.schoolregistration.service.impl;

import com.metadata.schoolregistration.entity.CourseEntity;
import com.metadata.schoolregistration.exception.CourseNotFoundException;
import com.metadata.schoolregistration.exception.GenericAlreadyExistsException;
import com.metadata.schoolregistration.repository.CourseRepository;
import com.metadata.schoolregistration.service.CourseService;
import java.util.HashSet;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;

  public CourseServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Page<CourseEntity> getStudentCourses(UUID studentId,Pageable pageable) {
    return courseRepository.findCoursesByStudentId(studentId, pageable);
  }

  @Override
  public Page<CourseEntity> getCoursesWithNoStudents(Pageable pageable) {
    return courseRepository.findCourseEntitiesByStudentsNull(pageable);
  }

  @Override
  @Transactional
  public CourseEntity create(String name) {

    if(courseRepository.findByName(name).isPresent()){
      throw new GenericAlreadyExistsException(String.format("Course %s already exists.",name));
    }

    CourseEntity courseEntity = CourseEntity.builder()
        .name(name)
        .students(new HashSet<>())
        .build();
    courseEntity = courseRepository.save(courseEntity);
    return courseEntity;
  }

  @Override
  @Transactional
  public CourseEntity update(UUID id, String name) {
    CourseEntity courseEntity = find(id);
    courseEntity.setName(name);
    courseEntity = courseRepository.save(courseEntity);
    return courseEntity;
  }

  @Override
  @Transactional
  public CourseEntity delete(UUID id) {
    CourseEntity courseEntity = find(id);
    courseRepository.delete(courseEntity);
    return courseEntity;
  }

  @Override
  public CourseEntity find(UUID id) {
    return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(String.format("Course with id %s not found", id)));
  }

  @Override
  public Page<CourseEntity> findAll(Pageable pageable) {
    return courseRepository.findAll(pageable);
  }
}
