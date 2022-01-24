package com.metadata.schoolregistration.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metadata.schoolregistration.TestConstants;
import com.metadata.schoolregistration.configuration.AppConfig;
import com.metadata.schoolregistration.entity.RoleEnum;
import com.metadata.schoolregistration.entity.StudentEntity;
import com.metadata.schoolregistration.exception.GenericAlreadyExistsException;
import com.metadata.schoolregistration.exception.MaxNumberOfCoursesAllowedException;
import com.metadata.schoolregistration.exception.MaxNumberOfStudentsAllowedPerCourseException;
import com.metadata.schoolregistration.exception.StudentAlreadyEnrolledException;
import com.metadata.schoolregistration.exception.StudentNotEnrolledException;
import com.metadata.schoolregistration.exception.StudentNotFoundException;
import com.metadata.schoolregistration.hateoas.StudentRepresentationModelAssembler;
import com.metadata.schoolregistration.hateoas.UserRepresentationModelAssembler;
import com.metadata.schoolregistration.model.StudentRequest;
import com.metadata.schoolregistration.security.JwtManager;
import com.metadata.schoolregistration.service.StudentService;
import com.metadata.schoolregistration.service.UserService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@Import(AppConfig.class)
@WebMvcTest(StudentController.class)
@ComponentScan(basePackages = "com.metadata.schoolregistration.security")
class StudentControllerTest {

  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper json;
  @Autowired
  private JwtManager tokenManager;

  @MockBean
  private StudentService studentService;
  @MockBean
  private UserService userService;
  @SpyBean
  private  UserRepresentationModelAssembler userRepresentationModelAssembler;
  @SpyBean
  private StudentRepresentationModelAssembler studentRepresentationModelAssembler;
  @SpyBean
  private PagedResourcesAssembler pagedResourcesAssembler;
  private String userToken;
  private String adminToken;

  @BeforeEach
  public void getToken() {
    userToken = TestConstants.getToken(tokenManager, RoleEnum.USER.name());
    adminToken = TestConstants.getToken(tokenManager, RoleEnum.ADMIN.name());

  }

  @Test
  public void createStudent() throws Exception{

    StudentRequest studentRequest = StudentRequest.builder()
        .username(TestConstants.USER_NAME_A)
        .firstName(TestConstants.USER_FIRST_NAME_A)
        .lastName(TestConstants.USER_LAST_NAME_A)
        .email(TestConstants.USER_EMAIL_A)
        .build();

    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);

    when(studentService.create(anyString(),anyString(),anyString(),anyString())).thenReturn(studentEntity);

    mvc.perform(post("/api/v1/student/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(studentRequest))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isCreated());

  }

  @Test
  public void createStudentAlreadyExists() throws Exception{

    StudentRequest studentRequest = StudentRequest.builder()
        .username(TestConstants.USER_NAME_A)
        .firstName(TestConstants.USER_FIRST_NAME_A)
        .lastName(TestConstants.USER_LAST_NAME_A)
        .email(TestConstants.USER_EMAIL_A)
        .build();

    when(studentService.create(anyString(),anyString(),anyString(),anyString())).thenThrow(GenericAlreadyExistsException.class);

    mvc.perform(post("/api/v1/student/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(studentRequest))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isConflict());

  }

  @Test
  public void updateStudent() throws Exception{

    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    StudentRequest studentRequest = StudentRequest.builder()
        .id(studentId)
        .username(TestConstants.USER_NAME_A)
        .firstName(TestConstants.USER_FIRST_NAME_B)
        .lastName(TestConstants.USER_LAST_NAME_B)
        .email(TestConstants.USER_EMAIL_B)
        .build();

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);

    when(studentService.update(any(UUID.class), anyString(),anyString(),anyString())).thenReturn(studentEntity);

    mvc.perform(put("/api/v1/student/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(studentRequest))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isAccepted());

  }

  @Test
  public void updateStudentNotFoundException() throws Exception{

    UUID studentId = UUID.randomUUID();

    StudentRequest studentRequest = StudentRequest.builder()
        .id(studentId)
        .username(TestConstants.USER_NAME_A)
        .firstName(TestConstants.USER_FIRST_NAME_B)
        .lastName(TestConstants.USER_LAST_NAME_B)
        .email(TestConstants.USER_EMAIL_B)
        .build();

    when(studentService.update(any(UUID.class), anyString(),anyString(),anyString())).thenThrow(StudentNotFoundException.class);

    mvc.perform(put("/api/v1/student/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(studentRequest))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isNotFound());

  }

  @Test
  public void deleteStudent() throws Exception{

    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);

    when(studentService.delete(any(UUID.class))).thenReturn(studentEntity);

    mvc.perform(delete("/api/v1/student/{studentId}",  studentId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isAccepted());

  }

  @Test
  public void deleteStudentNotFoundException() throws Exception{

    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);

    when(studentService.delete(any(UUID.class))).thenThrow(StudentNotFoundException.class);

    mvc.perform(delete("/api/v1/student/{studentId}",  studentId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isNotFound());

  }

  @Test
  public void findStudent() throws Exception{
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);

    when(studentService.find(any(UUID.class))).thenReturn(studentEntity);

    mvc.perform(get("/api/v1/student/{studentId}",  studentId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isOk());
  }

  @Test
  public void findStudentNotFoundException() throws Exception{
    UUID studentId = UUID.randomUUID();

    when(studentService.find(any(UUID.class))).thenThrow(StudentNotFoundException.class);

    mvc.perform(get("/api/v1/student/{studentId}",  studentId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isNotFound());
  }

  @Test
  public void findAllStudents() throws Exception{
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    Page page = new PageImpl(List.of(TestConstants.getTestStudentEntityA(studentId, userId),
        TestConstants.getTestStudentEntityB(UUID.randomUUID(), UUID.randomUUID())));

    when(studentService.findAll(any(Pageable.class))).thenReturn(page);

    mvc.perform(get("/api/v1/student/all")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isOk());
  }

  @Test
  public void enrollStudent() throws Exception{
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    UUID courseId = UUID.randomUUID();

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);

    when(studentService.enroll(any(UUID.class),any(UUID.class))).thenReturn(studentEntity);

    mvc.perform(patch("/api/v1/student/enroll/{studentId}/{courseId}",  studentId,courseId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
        .andExpect(status().isCreated());
  }

  @Test
  public void enrollStudentAlreadyEnrolledException() throws Exception{
    UUID studentId = UUID.randomUUID();
    UUID courseId = UUID.randomUUID();

    when(studentService.enroll(any(UUID.class),any(UUID.class))).thenThrow(StudentAlreadyEnrolledException.class);

    mvc.perform(patch("/api/v1/student/enroll/{studentId}/{courseId}",  studentId,courseId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void enrollStudentMaxNumberOfCoursesAllowedException() throws Exception{
    UUID studentId = UUID.randomUUID();
    UUID courseId = UUID.randomUUID();

    when(studentService.enroll(any(UUID.class),any(UUID.class))).thenThrow(MaxNumberOfCoursesAllowedException.class);

    mvc.perform(patch("/api/v1/student/enroll/{studentId}/{courseId}",  studentId,courseId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void enrollStudentMaxNumberOfStudentsAllowedPerCourseException() throws Exception{
    UUID studentId = UUID.randomUUID();
    UUID courseId = UUID.randomUUID();

    when(studentService.enroll(any(UUID.class),any(UUID.class))).thenThrow(MaxNumberOfStudentsAllowedPerCourseException.class);

    mvc.perform(patch("/api/v1/student/enroll/{studentId}/{courseId}",  studentId,courseId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void unrollStudent() throws Exception{
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    UUID courseId = UUID.randomUUID();

    StudentEntity studentEntity = TestConstants.getTestStudentEntityA(studentId, userId);

    when(studentService.unroll(any(UUID.class),any(UUID.class))).thenReturn(studentEntity);

    mvc.perform(patch("/api/v1/student/unroll/{studentId}/{courseId}",  studentId,courseId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
        .andExpect(status().isCreated());
  }

  @Test
  public void unrollStudentEnrollmentException() throws Exception{
    UUID studentId = UUID.randomUUID();
    UUID courseId = UUID.randomUUID();

    when(studentService.unroll(any(UUID.class),any(UUID.class))).thenThrow(StudentNotEnrolledException.class);

    mvc.perform(patch("/api/v1/student/unroll/{studentId}/{courseId}",  studentId,courseId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + userToken))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getStudentsEnrolledInCourse() throws Exception{
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();
    UUID courseId = UUID.randomUUID();

    Page page = new PageImpl(List.of(TestConstants.getTestStudentEntityA(studentId, userId),
        TestConstants.getTestStudentEntityB(UUID.randomUUID(), UUID.randomUUID())));

    when(studentService.getStudentsEnrolled(any(UUID.class),any(Pageable.class))).thenReturn(page);

    mvc.perform(get("/api/v1/student/course/{courseId}",courseId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isOk());
  }

  @Test
  public void getStudentsWithoutCourses() throws Exception{
    UUID studentId = UUID.randomUUID();
    UUID userId = UUID.randomUUID();

    Page page = new PageImpl(List.of(TestConstants.getTestStudentEntityA(studentId, userId),
        TestConstants.getTestStudentEntityB(UUID.randomUUID(), UUID.randomUUID())));

    when(studentService.getStudentsWithoutCourses(any(Pageable.class))).thenReturn(page);

    mvc.perform(get("/api/v1/student/no-courses")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isOk());
  }
}