package com.metadata.schoolregistration.model;

import com.metadata.schoolregistration.entity.RoleEnum;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class  User extends RepresentationModel<User> {

  private UUID id;
  private String username;
  private String firstName;
  private String lastName;
  private String email;
  @NotNull(message = "User password is required.")
  private String password;
  private RoleEnum role;
}
