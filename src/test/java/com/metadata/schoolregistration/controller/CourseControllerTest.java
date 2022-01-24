package com.metadata.schoolregistration.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.metadata.schoolregistration.TestConstants;
import com.metadata.schoolregistration.configuration.AppConfig;
import com.metadata.schoolregistration.entity.CourseEntity;
import com.metadata.schoolregistration.entity.RoleEnum;
import com.metadata.schoolregistration.exception.CourseNotFoundException;
import com.metadata.schoolregistration.exception.GenericAlreadyExistsException;
import com.metadata.schoolregistration.hateoas.CourseRepresentationModelAssembler;
import com.metadata.schoolregistration.model.CourseRequest;
import com.metadata.schoolregistration.security.JwtManager;
import com.metadata.schoolregistration.service.CourseService;
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
@WebMvcTest(CourseController.class)
@ComponentScan(basePackages = "com.metadata.schoolregistration.security")
class CourseControllerTest {

  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper json;
  @Autowired
  private JwtManager tokenManager;

  @MockBean
  private CourseService courseService;
  @SpyBean
  private CourseRepresentationModelAssembler courseRepresentationModelAssembler;
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
  public void createCourse() throws Exception{

    UUID courseId = UUID.randomUUID();
    String courseName = "math";

    CourseRequest courseRequest = CourseRequest.builder()
        .name(courseName)
        .build();

    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);

    when(courseService.create(anyString())).thenReturn(courseEntity);

    mvc.perform(post("/api/v1/course/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(courseRequest))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isCreated());

  }

  @Test
  public void createCourseAlreadyExists() throws Exception{

    String courseName = "math";

    CourseRequest courseRequest = CourseRequest.builder()
        .name(courseName)
        .build();

    when(courseService.create(anyString())).thenThrow(GenericAlreadyExistsException.class);

    mvc.perform(post("/api/v1/course/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(courseRequest))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isConflict());

  }

  @Test
  public void updateCourse() throws Exception{

    UUID courseId = UUID.randomUUID();
    String courseName = "math";
    String courseNameUpdated = "biology";

    CourseRequest courseRequest = CourseRequest.builder()
        .id(courseId)
        .name(courseNameUpdated)
        .build();

    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);

    when(courseService.update(any(UUID.class),anyString())).thenReturn(courseEntity);

    mvc.perform(put("/api/v1/course/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(courseRequest))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isAccepted());

  }
  @Test
  public void updateCourseNotFoundException() throws Exception{

    UUID courseId = UUID.randomUUID();
    String courseNameUpdated = "biology";

    CourseRequest courseRequest = CourseRequest.builder()
        .id(courseId)
        .name(courseNameUpdated)
        .build();

    when(courseService.update(any(UUID.class),anyString())).thenThrow(CourseNotFoundException.class);

    mvc.perform(put("/api/v1/course/")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json.writeValueAsString(courseRequest))
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isNotFound());

  }

  @Test
  public void deleteCourse() throws Exception{

    UUID courseId = UUID.randomUUID();
    String courseName = "math";

    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);

    when(courseService.delete(any(UUID.class))).thenReturn(courseEntity);

    mvc.perform(delete("/api/v1/course/{courseId}",courseId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isAccepted());

  }

  @Test
  public void deleteCourseNotFoundException() throws Exception{

    UUID courseId = UUID.randomUUID();

    when(courseService.delete(any(UUID.class))).thenThrow(CourseNotFoundException.class);

    mvc.perform(delete("/api/v1/course/{courseId}",courseId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isNotFound());

  }

  @Test
  public void findCourse() throws Exception{

    UUID courseId = UUID.randomUUID();
    String courseName = "math";

    CourseEntity courseEntity = TestConstants.getTestCourseEntity(courseId, courseName);

    when(courseService.find(any(UUID.class))).thenReturn(courseEntity);

    mvc.perform(get("/api/v1/course/{courseId}",courseId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isOk());

  }

  @Test
  public void findCourseNotFoundException() throws Exception{

    UUID courseId = UUID.randomUUID();

    when(courseService.find(any(UUID.class))).thenThrow(CourseNotFoundException.class);

    mvc.perform(get("/api/v1/course/{courseId}",courseId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isNotFound());

  }

  @Test
  public void findAllCourses() throws Exception{
    UUID courseId = UUID.randomUUID();
    String courseName = "math";

    Page page = new PageImpl(List.of(TestConstants.getTestCourseEntity(courseId, courseName + 1),
        TestConstants.getTestCourseEntity(UUID.randomUUID(), courseName + 2)));

    when(courseService.findAll(any(Pageable.class))).thenReturn(page);

    mvc.perform(get("/api/v1/course/all")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isOk());
  }

  @Test
  public void getStudentCourses() throws Exception{
    UUID studentId = UUID.randomUUID();
    UUID courseId = UUID.randomUUID();
    String courseName = "math";

    Page page = new PageImpl(List.of(TestConstants.getTestCourseEntity(courseId, courseName + 1),
        TestConstants.getTestCourseEntity(UUID.randomUUID(), courseName + 2)));

    when(courseService.getStudentCourses(any(UUID.class),any(Pageable.class))).thenReturn(page);

    mvc.perform(get("/api/v1/course/student/{studentId}",studentId)
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isOk());
  }

  @Test
  public void getCoursesWithNoStudents() throws Exception{
    UUID courseId = UUID.randomUUID();
    String courseName = "math";

    Page page = new PageImpl(List.of(TestConstants.getTestCourseEntity(courseId, courseName + 1),
        TestConstants.getTestCourseEntity(UUID.randomUUID(), courseName + 2)));

    when(courseService.getCoursesWithNoStudents(any(Pageable.class))).thenReturn(page);

    mvc.perform(get("/api/v1/course/empty")
            .contentType(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminToken))
        .andExpect(status().isOk());
  }

}