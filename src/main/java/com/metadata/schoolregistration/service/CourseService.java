package com.metadata.schoolregistration.service;

import com.metadata.schoolregistration.entity.CourseEntity;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService{

  CourseEntity create(String name);
  CourseEntity update(UUID id, String name);
  CourseEntity delete(UUID id);
  CourseEntity find(UUID id);
  Page<CourseEntity> findAll(Pageable pageable);
  Page<CourseEntity> getStudentCourses(UUID studentId,Pageable pageable);
  Page<CourseEntity> getCoursesWithNoStudents(Pageable pageable);
}
