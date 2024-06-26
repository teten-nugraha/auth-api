package id.ten.authapi.controllers;

import id.ten.authapi.dto.GenericResponse;
import id.ten.authapi.model.User;
import id.ten.authapi.records.RegisterUserRecord;
import id.ten.authapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admins")
@RestController
public class AdminController {
  private final UserService userService;

  public AdminController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @PreAuthorize("hasRole('SUPER_ADMIN')")
  public ResponseEntity<GenericResponse<User>> createAdministrator(
      @RequestBody RegisterUserRecord registerUserDto) {
    User createdAdmin = userService.createAdministrator(registerUserDto);
    return ResponseEntity.ok(GenericResponse.success(createdAdmin));
  }
}
