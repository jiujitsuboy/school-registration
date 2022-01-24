package com.metadata.schoolregistration.model;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course extends RepresentationModel<Course> {
  private UUID id;
  private String name;
  private Set<Student> students;
}
