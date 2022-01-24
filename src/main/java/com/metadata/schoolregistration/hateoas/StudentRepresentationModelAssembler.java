package com.metadata.schoolregistration.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.metadata.schoolregistration.controller.StudentController;
import com.metadata.schoolregistration.entity.StudentEntity;
import com.metadata.schoolregistration.model.Student;
import com.metadata.schoolregistration.model.User;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class StudentRepresentationModelAssembler extends RepresentationModelAssemblerSupport<StudentEntity, Student> {

  private final UserRepresentationModelAssembler userRepresentationModelAssembler;
  
  public StudentRepresentationModelAssembler(UserRepresentationModelAssembler userRepresentationModelAssembler) {
    super(StudentController.class, Student.class);
    this.userRepresentationModelAssembler = userRepresentationModelAssembler;
  }

  @Override
  public Student toModel(StudentEntity studentEntity) {
    User user = userRepresentationModelAssembler.toModel(studentEntity.getUser());

    Student student =  studentEntity.toModel(true);
    student.setUser(user);

    student.add(linkTo(methodOn(StudentController.class).findStudent(student.getId())).slash(student.getId()).withSelfRel());

    return student;
  }
}
