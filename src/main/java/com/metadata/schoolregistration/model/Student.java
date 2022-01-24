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
public class Student extends RepresentationModel<Student> {
   private UUID id;
   private User user;
   private Set<Course> courses;
}
