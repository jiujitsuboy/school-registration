package com.metadata.schoolregistration.hateoas;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.metadata.schoolregistration.controller.AuthController;
import com.metadata.schoolregistration.entity.UserEntity;
import com.metadata.schoolregistration.model.SignInReq;
import com.metadata.schoolregistration.model.User;
import com.metadata.schoolregistration.service.UserService;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserRepresentationModelAssembler extends RepresentationModelAssemblerSupport<UserEntity, User> {

  private UserService userService;
  public UserRepresentationModelAssembler(UserService userService) {
    super(AuthController.class, User.class);
    this.userService = userService;
  }

  @Override
  public User toModel(UserEntity entity) {
    User user = entity.toModel();
    SignInReq signInReq = new SignInReq(user.getUsername(),user.getPassword());
    user.setPassword("Ciphered...");
    user.add(linkTo(methodOn(AuthController.class).signIn(signInReq)).withRel("user-signin"));
    return user;
  }
}