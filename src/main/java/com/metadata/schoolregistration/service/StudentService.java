package com.metadata.schoolregistration.service;

import com.metadata.schoolregistration.entity.StudentEntity;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService{
    StudentEntity create(String username, String firstName, String lastName, String email);
    StudentEntity update(UUID id, String firstName, String lastName, String email);
    StudentEntity delete(UUID id);
    StudentEntity find(UUID id);
    Page<StudentEntity> findAll(Pageable pageable);
    StudentEntity enroll(UUID userId,UUID courseId);
    StudentEntity unroll(UUID userId, UUID courseId);
    Page<StudentEntity> getStudentsEnrolled(UUID courseId, Pageable pageable);
    Page<StudentEntity> getStudentsWithoutCourses(Pageable pageable);
}
