package com.metadata.schoolregistration.controller;

import static org.springframework.http.ResponseEntity.status;

import com.metadata.schoolregistration.entity.RoleEnum.Const;
import com.metadata.schoolregistration.hateoas.StudentRepresentationModelAssembler;
import com.metadata.schoolregistration.model.Student;
import com.metadata.schoolregistration.model.StudentRequest;
import com.metadata.schoolregistration.service.StudentService;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/student")
@Api(value = "Student Controller")
public class StudentController {

  private final StudentService studentService;
  private final StudentRepresentationModelAssembler studentRepresentationModelAssembler;
  private final PagedResourcesAssembler pagedResourcesAssembler;

  public StudentController(StudentService studentService, StudentRepresentationModelAssembler studentRepresentationModelAssembler,
      PagedResourcesAssembler pagedResourcesAssembler) {
    this.studentService = studentService;
    this.studentRepresentationModelAssembler = studentRepresentationModelAssembler;
    this.pagedResourcesAssembler = pagedResourcesAssembler;
  }

  @PreAuthorize("hasRole('" + Const.ADMIN + "')")
  @ApiOperation(value = "create Student", nickname = "createStudent", notes = "Create a new student")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Student created"),
      @ApiResponse(code = 409, message = "Student Already exist")
  }

  )
  @PostMapping("/")
  public ResponseEntity<Student> createStudent(@RequestBody(required = true) StudentRequest studentRequest) {
    return status(HttpStatus.CREATED).body(studentRepresentationModelAssembler.toModel(
        studentService.create(studentRequest.getUsername(), studentRequest.getFirstName(), studentRequest.getLastName(),
            studentRequest.getEmail())));
  }

  @PreAuthorize("hasRole('" + Const.ADMIN + "')")
  @ApiOperation(value = "update Student", nickname = "updateStudent", notes = "Update an existing student")
  @ApiResponses(value={
      @ApiResponse(code = 202, message = "Student updated"),
      @ApiResponse(code = 404, message = "Student not found")
  }

  )
  @PutMapping("/")
  public ResponseEntity<Student> updateStudent(@RequestBody(required = true) StudentRequest studentRequest) {
    return status(HttpStatus.ACCEPTED).body(
        studentRepresentationModelAssembler.toModel(
            studentService.update(studentRequest.getId(), studentRequest.getFirstName(), studentRequest.getLastName(),
                studentRequest.getEmail())));
  }

  @PreAuthorize("hasRole('" + Const.ADMIN + "')")
  @ApiOperation(value = "delete Student", nickname = "deleteStudent", notes = "Delete an existing student")
  @ApiResponses(value ={
      @ApiResponse(code = 202, message = "Student deleted"),
      @ApiResponse(code = 404, message = "Student not found")
  }
  )
  @DeleteMapping("/{studentId}")
  public ResponseEntity<Student> deleteStudent(@PathVariable("studentId") UUID studentId) {
    return status(HttpStatus.ACCEPTED).body(studentRepresentationModelAssembler.toModel(studentService.delete(studentId)));
  }

  @PreAuthorize("hasRole('" + Const.ADMIN + "')")
  @ApiOperation(value = "find Student", nickname = "findStudent", notes = "Find an existing student")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Student found"),
      @ApiResponse(code = 404, message = "Student not found")
  })
  @GetMapping("/{studentId}")
  public ResponseEntity<Student> findStudent(@PathVariable("studentId") UUID studentId) {
    return status(HttpStatus.OK).body(studentRepresentationModelAssembler.toModel(studentService.find(studentId)));
  }

  @PreAuthorize("hasRole('" + Const.ADMIN + "')")
  @ApiOperation(value = "find all Student", nickname = "findAllStudent", notes = "Find all existing students")
  @ApiResponses(
      @ApiResponse(code = 200, message = "Students found")
  )
  @GetMapping("/all")
  public ResponseEntity<PagedModel<Student>> findAllStudents(Pageable pageable) {
    return status(HttpStatus.OK).body(
        pagedResourcesAssembler.toModel(studentService.findAll(pageable), studentRepresentationModelAssembler));
  }

  @ApiOperation(value = "enroll Student", nickname = "enrollStudent", notes = "Enroll an existing student")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Student enrolled"),
      @ApiResponse(code = 400, message = "enrollment Limits exceeded ")
  })
  @PatchMapping("/enroll/{studentId}/{courseId}")
  public ResponseEntity<Student> enrollStudent(@PathVariable("studentId") UUID studentId, @PathVariable("courseId") UUID courseId) {
    return status(HttpStatus.CREATED).body(studentRepresentationModelAssembler.toModel(studentService.enroll(studentId, courseId)));
  }

  @ApiOperation(value = "unroll Student", nickname = "unrollStudent", notes = "Unroll an existing student from a course")
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "Student unrolled"),
      @ApiResponse(code = 400, message = "Student not enrolled previously")
  })
  @PatchMapping("/unroll/{studentId}/{courseId}")
  public ResponseEntity<Student> unrollStudent(@PathVariable("studentId") UUID studentId, @PathVariable("courseId") UUID courseId) {
    return status(HttpStatus.CREATED).body(studentRepresentationModelAssembler.toModel(studentService.unroll(studentId, courseId)));
  }

  @PreAuthorize("hasRole('" + Const.ADMIN + "')")
  @ApiOperation(value = "find Student", nickname = "findStudent", notes = "Find an existing student")
  @ApiResponses(
      @ApiResponse(code = 200, message = "Student found")
  )
  @GetMapping("/course/{courseId}")
  public ResponseEntity<PagedModel<Student>> getStudentsEnrolledInCourse(@PathVariable("courseId") UUID courseId, Pageable pageable) {
    return status(HttpStatus.OK).body(
        pagedResourcesAssembler.toModel(studentService.getStudentsEnrolled(courseId, pageable), studentRepresentationModelAssembler));
  }

  @PreAuthorize("hasRole('" + Const.ADMIN + "')")
  @ApiOperation(value = "find Student without courses", nickname = "getStudentsWithoutCourses", notes = "Find aall existing student without courses enrollment ")
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "Student not enrolled")
  })
  @GetMapping("/no-courses")
  public ResponseEntity<PagedModel<Student>> getStudentsWithoutCourses(Pageable pageable) {
    return status(HttpStatus.OK).body(
        pagedResourcesAssembler.toModel(studentService.getStudentsWithoutCourses(pageable), studentRepresentationModelAssembler));
  }

}
