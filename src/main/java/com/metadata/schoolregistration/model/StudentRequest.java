package com.metadata.schoolregistration.model;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentRequest {

  private UUID id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
}
