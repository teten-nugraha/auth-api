package id.ten.authapi.controllers;

import id.ten.authapi.dto.GenericResponse;
import id.ten.authapi.model.User;
import id.ten.authapi.service.UserService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  @PreAuthorize("isAuthenticated()")
  public ResponseEntity<GenericResponse<User>> authenticatedUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    User currentUser = (User) authentication.getPrincipal();

    return ResponseEntity.ok(GenericResponse.success(currentUser));
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
  public ResponseEntity<GenericResponse<List<User>>> allUsers() {
    List<User> users = userService.allUsers();

    return ResponseEntity.ok(GenericResponse.success(users));
  }
}
