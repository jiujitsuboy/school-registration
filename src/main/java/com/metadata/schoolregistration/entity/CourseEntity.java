package com.metadata.schoolregistration.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.metadata.schoolregistration.model.Course;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name ="course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(type="uuid-char")
  @Column(name="ID", updatable = false, nullable = false)
  private UUID id;

  @Column(name="NAME", nullable = false)
  private String name;

  @JsonIgnore
  @ManyToMany(mappedBy = "courses",fetch = FetchType.EAGER)
  private Set<StudentEntity> students;

  public Course toModel(boolean includeStudents){
    Course course = Course.builder()
        .id(id)
        .name(name)
        .build();

    if(includeStudents) {
      course.setStudents(students.stream().map(studentEntity -> studentEntity.toModel(false)).collect(Collectors.toSet()));
    }
    return course;
  }
}
