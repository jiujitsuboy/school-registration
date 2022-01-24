package com.metadata.schoolregistration.repository;

import com.metadata.schoolregistration.entity.StudentEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

  Optional<StudentEntity> findByUserUsernameOrUserEmail(String username, String email);
  @Query(value = "select se from StudentEntity se join se.courses co where co.id = :courseId")
  Page<StudentEntity> findAllByCourses(UUID courseId, Pageable pageable);
  Page<StudentEntity> findAllByCoursesIsNull(Pageable pageable);
}
