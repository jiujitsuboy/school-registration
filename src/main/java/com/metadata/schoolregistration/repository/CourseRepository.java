package com.metadata.schoolregistration.repository;

import com.metadata.schoolregistration.entity.CourseEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {

  Optional<CourseEntity> findByName(String name);

  @Query(value = "select co from CourseEntity co join co.students st where st.id = :studentId")
  Page<CourseEntity> findCoursesByStudentId(UUID studentId, Pageable pageable);
  Page<CourseEntity> findCourseEntitiesByStudentsNull(Pageable pageable);
}
