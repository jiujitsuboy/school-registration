package com.metadata.schoolregistration.controller;

import static org.springframework.http.ResponseEntity.status;

import com.metadata.schoolregistration.entity.CourseEntity;
import com.metadata.schoolregistration.entity.RoleEnum.Const;
import com.metadata.schoolregistration.hateoas.CourseRepresentationModelAssembler;
import com.metadata.schoolregistration.model.Course;
import com.metadata.schoolregistration.model.CourseRequest;
import com.metadata.schoolregistration.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/course")
@Api(value = "Course Controller")
public class CourseController {

  private final CourseService courseService;
  private final CourseRepresentationModelAssembler courseRepresentationModelAssembler;
  private final PagedResourcesAssembler pagedResourcesAssembler;

  public CourseController(CourseService courseService, CourseRepresentationModelAssembler courseRepresentationModelAssembler,
      PagedResourcesAssembler pagedResourcesAssembler) {
    this.courseService = courseService;
    this.courseRepresentationModelAssembler = courseRepresentationModelAssembler;
    this.pagedResourcesAssembler = pagedResourcesAssembler;
  }

  @PreAuthorize("hasRole('" + Const.ADMIN + "')")
  @ApiOperation(value = "create Course", nickname = "createCourse", notes = "Create a new course")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Course created"),
      @ApiResponse(code = 409, message = "Course Already exist")
  }
  )
  @PostMapping("/")
  public ResponseEntity<Course> createCourse(@RequestBody(required = true) CourseRequest courseRequest) {
    return status(HttpStatus.CREATED).body(courseRepresentationModelAssembler.toModel(courseService.create(courseRequest.getName())));
  }

  @PreAuthorize("hasRole('" + Const.ADMIN + "')")
  @ApiOperation(value = "update Course", nickname = "updateCourse", notes = "Update an existing Course")
  @ApiResponses(value = {
      @ApiResponse(code = 202, message = "Course updated"),
      @ApiResponse(code = 404, message = "Course not found")
  }
  )
  @PutMapping("/")
  public ResponseEntity<Course> updateCourse(@RequestBody(required = true) CourseRequest courseRequest) {
    return status(HttpStatus.ACCEPTED).body(
        courseRepresentationModelAssembler.toModel(courseService.update(courseRequest.getId(), courseRequest.getName())));
  }

  @PreAuthorize("hasRole('" + Const.ADMIN + "')")
  @ApiOperation(value = "delete Course", nickname = "deleteCourse", notes = "Delete an existing Course")
  @ApiResponses(value = {
      @ApiResponse(code = 202, message = "Course deleted"),
      @ApiResponse(code = 404, message = "Course not found")
  }
  )
  @DeleteMapping("/{courseId}")
  public ResponseEntity<Course> deleteCourse(@PathVariable("courseId") UUID courseId) {
    return status(HttpStatus.ACCEPTED).body(courseRepresentationModelAssembler.toModel(courseService.delete(courseId)));
  }

  @ApiOperation(value = "find Course", nickname = "findCourse", notes = "Find an existing Course")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Course found"),
      @ApiResponse(code = 404, message = "Course not found")
  })
  @GetMapping("/{courseId}")
  public ResponseEntity<Course> findCourse(@PathVariable("courseId") UUID courseId) {
    return status(HttpStatus.OK).body(courseRepresentationModelAssembler.toModel(courseService.find(courseId)));
  }

  @ApiOperation(value = "find all Courses", nickname = "findAllCourse", notes = "Find all existing Courses")
  @ApiResponses(
      @ApiResponse(code = 200, message = "Courses found")
  )
  @GetMapping("/all")
  public ResponseEntity<PagedModel<CourseEntity>> findAllCourses(Pageable pageable) {
    return status(HttpStatus.OK).body(
        pagedResourcesAssembler.toModel(courseService.findAll(pageable), courseRepresentationModelAssembler));
  }

  @ApiOperation(value = "find student's courses", nickname = "getStudentCourses", notes = "Find student's enrolled courses")
  @ApiResponses(
      @ApiResponse(code = 200, message = "Student's courses found")
  )
  @GetMapping("/student/{studentId}")
  public ResponseEntity<PagedModel<Course>> getStudentCourses(@PathVariable("studentId") UUID studentId, Pageable pageable) {
    return status(HttpStatus.OK).body(
        pagedResourcesAssembler.toModel(courseService.getStudentCourses(studentId, pageable), courseRepresentationModelAssembler));
  }

  @PreAuthorize("hasRole('" + Const.ADMIN + "')")
  @ApiOperation(value = "find empty courses", nickname = "getCoursesWithNoStudents", notes = "Find courses with no students")
  @ApiResponses(
      @ApiResponse(code = 200, message = "Empty courses found")
  )
  @GetMapping("/empty")
  public ResponseEntity<PagedModel<Course>> getCoursesWithNoStudents(Pageable pageable) {
    return status(HttpStatus.OK).body(
        pagedResourcesAssembler.toModel(courseService.getCoursesWithNoStudents(pageable), courseRepresentationModelAssembler));
  }
}
