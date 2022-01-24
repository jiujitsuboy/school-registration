package com.metadata.schoolregistration.entity;

import com.metadata.schoolregistration.model.Student;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentEntity {

  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Type(type="uuid-char")
  @Column(name = "ID", updatable = false, nullable = false)
  private UUID id;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "course_student",
      joinColumns = @JoinColumn(name = "student_id"),
      inverseJoinColumns = @JoinColumn(name = "course_id")
  )
  private Set<CourseEntity> courses;

  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
  private UserEntity user;

  public void addCourse(CourseEntity courseEntity){
    courses.add(courseEntity);
    courseEntity.getStudents().add(this);
  }

  public void removeCourse(CourseEntity courseEntity){
    courses.remove(courseEntity);
    courseEntity.getStudents().remove(this);
  }

  public Student toModel(boolean includeCourses) {
    Student student = Student.builder()
        .id(id)
        .user(user.toModel())
        .build();

    if(includeCourses){
      student.setCourses(courses.stream().map(courseEntity -> courseEntity.toModel(false)).collect(Collectors.toSet()));
    }
    return student;
  }

}
