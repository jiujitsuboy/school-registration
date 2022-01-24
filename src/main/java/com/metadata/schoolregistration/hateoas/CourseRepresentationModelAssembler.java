package com.metadata.schoolregistration.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.metadata.schoolregistration.controller.CourseController;
import com.metadata.schoolregistration.entity.CourseEntity;
import com.metadata.schoolregistration.model.Course;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CourseRepresentationModelAssembler extends RepresentationModelAssemblerSupport<CourseEntity, Course> {

  public CourseRepresentationModelAssembler() {
    super(CourseController.class, Course.class);
  }

  @Override
  public Course toModel(CourseEntity courseEntity) {
    Course course = courseEntity.toModel(true);
    course.add(linkTo(methodOn(CourseController.class).findCourse(course.getId())).slash(course.getId()).withSelfRel());
    return course;
  }

}
